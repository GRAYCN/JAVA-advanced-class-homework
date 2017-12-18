package homework01.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import homework01.po.ClassInfo;
import homework01.po.MethodInfo;
import homework01.po.ParamInfo;
import homework01.util.DbUtils;

public class JsonImport {
	public static void main(String[] args) throws SQLException {

		// 先要将字符串中的内容清空

		// 获得数据库连接
		Connection conn = DbUtils.getConnection();
		// 数据库执行语句
		String classDeleteSQL = "DELETE FROM dbo.ClassInfo";
		String methodDeleteSQL = "DELETE FROM dbo.MethodInfo";
		String paramDeleteSQL = "DELETE FROM dbo.ParamInfo";
		PreparedStatement ps = conn.prepareStatement(paramDeleteSQL);
		ps.executeUpdate();
		ps = conn.prepareStatement(methodDeleteSQL);
		ps.executeUpdate();
		ps = conn.prepareStatement(classDeleteSQL);
		ps.executeUpdate();
		List<ClassInfo> classInfos = new ArrayList<ClassInfo>();
		List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
		List<ParamInfo> paramInfos = new ArrayList<ParamInfo>();
		try {
			/* 读入TXT文件 */
			String pathname = "homework01.json"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的input。txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			int flag = -1; // 控制读取的json数据处理方式， 1:ClassInfo 2:MethodInfo
							// 3:ParamInfo
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			while (line != null) {
				if (line.startsWith("ClassInfo")) { // 读取到ClassInfo相关数据
					flag = 1;
					line = br.readLine();
					continue;
				}
				if (line.startsWith("MethodInfo")) {
					flag = 2;
					line = br.readLine();
					continue;
				}
				if (line.startsWith("ParamInfo")) {
					flag = 3;
					line = br.readLine();
					continue;
				}
				if (line.startsWith(" ")) { // 处理空行
					line = br.readLine();
					continue;
				}
				// 处理 ClassInfo 的json数据
				if (flag == 1) {
					ClassInfo classInfo = (ClassInfo) gson.fromJson(line, ClassInfo.class);
					classInfos.add(classInfo);

				}
				if (flag == 2) {
					MethodInfo methodInfo = (MethodInfo) gson.fromJson(line, MethodInfo.class);
					methodInfos.add(methodInfo);
				}
				if (flag == 3) {
					ParamInfo paramInfo = (ParamInfo) gson.fromJson(line, ParamInfo.class);
					paramInfos.add(paramInfo);
				}
				line = br.readLine(); // 一次读入一行数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 通过HashMap记录新旧键值的对应关系
		HashMap<Integer, Integer> hashMap_classId = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> hashMap_methodId = new HashMap<Integer, Integer>();
		// 将ClassInfo信息插入数据库
		for (ClassInfo classInfo : classInfos) {

			String classInsertSQL = "insert into dbo.ClassInfo(className) " + " values( ?)";
			PreparedStatement classPs = conn.prepareStatement(classInsertSQL, Statement.RETURN_GENERATED_KEYS);

			classPs.setString(1, classInfo.getClassName());
			classPs.executeUpdate(); // 测试实例 java.lang.String
			ResultSet key_ClassInfo = classPs.getGeneratedKeys(); // 获取ClssInfo中自增的主键，可能这里需要使用到一个Hash映射存储先后的联系，以便让后面的method能够获取到
			// 获取ClassInfo自增的结果,需要在相应的插入执行完毕后才能获取到结果
			int classId = -1;
			while (key_ClassInfo.next()) {
				classId = key_ClassInfo.getInt(1);
			}
			// 原来的主键 classInfo.getClassId() 新的主键 classId
			hashMap_classId.put(classInfo.getClassId(), classId);
		}

		// method....
		for (MethodInfo methodInfo : methodInfos) {

			// 将查询到的方法的信息新增到MethodInfo表中
			String methodInsertSQL = "INSERT INTO dbo.MethodInfo ( classId, methodName, returnType ) VALUES  ( ?,?,? )";
			PreparedStatement methodPs = conn.prepareStatement(methodInsertSQL, Statement.RETURN_GENERATED_KEYS);
			// 由于数据库的主键定义为自增，此时ClassId已经与数据库中存储的不同，需要根据映射关系对应
			methodPs.setInt(1, hashMap_classId.get(methodInfo.getClassId()));
			methodPs.setString(2, methodInfo.getMethodName());
			methodPs.setString(3, methodInfo.getReturnType());
			methodPs.executeUpdate(); // 测试实例 java.lang.String
			ResultSet key_MethodInfo = methodPs.getGeneratedKeys(); // 获取MethodInfo中自增的主键
			// 获取MethodInfo自增的结果,需要在相应的插入执行完毕后才能获取到结果
			int methodId = -1;
			while (key_MethodInfo.next()) {
				methodId = key_MethodInfo.getInt(1);
			}
			hashMap_methodId.put(methodInfo.getMethodId(), methodId);
		}

		// param....
		for (ParamInfo paramInfo : paramInfos) {
			String paramInsertSQL = "INSERT INTO dbo.ParamInfo( methodId, paramName, paramType ) VALUES  ( ?,  ?, ?  )";
			PreparedStatement paramPs = conn.prepareStatement(paramInsertSQL, Statement.RETURN_GENERATED_KEYS);
			paramPs.setInt(1, hashMap_methodId.get(paramInfo.getMethodId()));
			paramPs.setString(2, paramInfo.getParamName());
			paramPs.setString(3, paramInfo.getParamType());
			paramPs.executeUpdate(); // 测试实例 java.lang.String
		}
		DbUtils.close(null, ps, conn);
		System.out.println("已成功将json数据导入数据库中");

	}
}
