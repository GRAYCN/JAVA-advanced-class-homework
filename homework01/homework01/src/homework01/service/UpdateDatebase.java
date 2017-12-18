package homework01.service;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


import homework01.util.DbUtils;

public class UpdateDatebase {

	public static Class<?> getClass(String string) throws ClassNotFindException {
		Class<?> cls = null;
		try {
			cls = Class.forName(string);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFindException();
		}
		return cls;
	}

	public static void main(String[] args) throws SQLException {
		Scanner in = new Scanner(System.in); // 使用Scanner类,读取输入
		while (in.hasNext()) {
			String string = in.nextLine();
			Class<?> cls;
			try { // 通过自定义异常类处理输出
				cls = getClass(string);
			} catch (ClassNotFindException e) {
				e.printStackTrace();
				continue;
			} // 获取类的名称
			String className = cls.getName();
			System.out.println("装载成功，类名:" + className);
			// 获得数据库连接
			Connection conn = DbUtils.getConnection();
			// 将查询到的类的信息新增到ClassInfo表中

			// 先判断这个Class的信息是否已经存在在表中，如果已经存在则不进行添加操作
			String checkSQL = "select * from dbo.ClassInfo where className=?";
			PreparedStatement Ps_check = conn.prepareStatement(checkSQL);
			Ps_check.setString(1, cls.getName());
			ResultSet executeQuery = Ps_check.executeQuery(); // 测试实例
																// java.lang.String
			if (executeQuery.next() == true) {
				System.out.println("该类的相关信息已经存储在数据库中，不再进行对数据库的Insert操作");
				continue;
			}

			String classInsertSQL = "insert into dbo.ClassInfo(className)  values( ?)";
			PreparedStatement classPs = conn.prepareStatement(classInsertSQL, Statement.RETURN_GENERATED_KEYS);

			classPs.setString(1, cls.getName());
			classPs.executeUpdate(); // 测试实例 java.lang.String
			ResultSet key_ClassInfo = classPs.getGeneratedKeys(); // 获取ClssInfo中自增的主键
			// 获取ClassInfo自增的结果,需要在相应的插入执行完毕后才能获取到结果
			int classId = -1;
			while (key_ClassInfo.next()) {
				classId = key_ClassInfo.getInt(1);
			}

			// 获取类的方法信息
			Method[] declaredMethods = cls.getDeclaredMethods(); // 获取类中的方法数组
			for (Method method : declaredMethods) {
				// 查询方法的返回值类型
				Class<?> returnType = method.getReturnType();
				// 将查询到的方法的信息新增到MethodInfo表中
				String methodInsertSQL = "INSERT INTO dbo.MethodInfo ( classId, methodName, returnType )"
						+ "VALUES  ( ?,?,? )";
				PreparedStatement methodPs = conn.prepareStatement(methodInsertSQL, Statement.RETURN_GENERATED_KEYS);
				methodPs.setInt(1, classId);
				methodPs.setString(2, method.getName());
				methodPs.setString(3, returnType.getName());
				methodPs.executeUpdate(); // 测试实例 java.lang.String
				ResultSet key_MethodInfo = methodPs.getGeneratedKeys(); // 获取MethodInfo中自增的主键
				// 获取MethodInfo自增的结果,需要在相应的插入执行完毕后才能获取到结果
				int methodId = -1;
				while (key_MethodInfo.next()) {
					methodId = key_MethodInfo.getInt(1);
				}

				// 查询方法的参数信息
				Class<?>[] parameterTypes = method.getParameterTypes();
				for (int i = 0; i < parameterTypes.length; i++) {
					// 将查询到的方法的信息新增到MethodInfo表中
					String paramInsertSQL = "INSERT INTO dbo.ParamInfo( methodId, paramName, paramType )"
							+ "VALUES  ( ?,  ?, ?  )";
					PreparedStatement paramPs = conn.prepareStatement(paramInsertSQL, Statement.RETURN_GENERATED_KEYS);
					paramPs.setInt(1, methodId);
					paramPs.setString(2, "arg[" + i + "]");
					paramPs.setString(3, parameterTypes[i].getCanonicalName());
					paramPs.executeUpdate(); // 测试实例 java.lang.String
				}
			}
			System.out.println("已成功将类的信息存储到数据库中");
		}
		in.close();
	}
}
