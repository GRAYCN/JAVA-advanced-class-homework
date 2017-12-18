package homework01.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.CaretListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import homework01.po.ClassInfo;
import homework01.po.MethodInfo;
import homework01.po.ParamInfo;
import homework01.util.DbUtils;

public class JsonExport {

	// 关系数据-》对象 函数参照了授课PPT相关例子
	public static ClassInfo[] roMapping_ClassInfo(ResultSet rs) {
		List<ClassInfo> classInfos = new ArrayList<ClassInfo>();
		try {
			while (rs.next() == true) {
				ClassInfo classInfo = new ClassInfo(rs.getInt("classId"), rs.getString("className"));
				classInfos.add(classInfo);
			}
			return classInfos.toArray(new ClassInfo[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return new ClassInfo[0];
		}
	}

	public static MethodInfo[] roMapping_MethodInfo(ResultSet rs) {
		List<MethodInfo> methodInfos = new ArrayList<MethodInfo>();
		try {
			while (rs.next() == true) {
				MethodInfo methodInfo = new MethodInfo(rs.getInt("methodId"), rs.getInt("classId"),
						rs.getString("methodName"), rs.getString("returnType"));
				methodInfos.add(methodInfo);
			}
			return methodInfos.toArray(new MethodInfo[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return new MethodInfo[0];
		}
	}

	public static ParamInfo[] roMapping_ParamInfo(ResultSet rs) {
		List<ParamInfo> paramInfos = new ArrayList<ParamInfo>();
		try {
			while (rs.next() == true) {
				ParamInfo paramInfo = new ParamInfo(rs.getInt("paramId"), rs.getInt("methodId"),
						rs.getString("paramName"), rs.getString("paramType"));
				paramInfos.add(paramInfo);
			}
			return paramInfos.toArray(new ParamInfo[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return new ParamInfo[0];
		}
	}

	public static void writeToTxt(String filepath, String content) {
		/* 写入Txt文件 */
		try {
			File writename = new File(filepath); // 相对路径，如果没有则要建立一个新的output。txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(content); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		// 获得数据库连接
		Connection conn = DbUtils.getConnection();
		// 数据库执行语句
		String classSelectSQL = "SELECT classId ,className FROM dbo.ClassInfo";
		String methodSelectSQL = "SELECT methodId ,classId ,methodName ,returnType FROM dbo.MethodInfo";
		String paramSelectSQL = "SELECT paramId ,methodId ,paramName ,paramType FROM dbo.ParamInfo";
		// 生成classInfo的po对象
		PreparedStatement ps = conn.prepareStatement(classSelectSQL);
		ResultSet rs = ps.executeQuery();
		ClassInfo[] classInfos = roMapping_ClassInfo(rs);
		// 生成methodInfo的po对象
		ps = conn.prepareStatement(methodSelectSQL);
		rs = ps.executeQuery();
		MethodInfo[] methodInfos = roMapping_MethodInfo(rs);
		// 生成paramInfo的po对象
		ps = conn.prepareStatement(paramSelectSQL);
		rs = ps.executeQuery();
		ParamInfo[] paramInfos = roMapping_ParamInfo(rs);

		// 创建一个Gson对象
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		// 存储与ClassInfo信息有关的JSON文件
		String str_Json = "ClassInfo:\n";
		for (int i = 0; i < classInfos.length; i++) {
			String str = gson.toJson(classInfos[i]);
			str_Json += str+"\n";
		}
		// 存储与MethodInfo信息有关的JSON文件
		str_Json += "MethodInfo:\n";
		for (int i = 0; i < methodInfos.length; i++) {
			String str = gson.toJson(methodInfos[i]);
			str_Json += str+"\n";
		}
		// 存储与ParamInfo信息有关的JSON文件
		str_Json += "ParamInfo:\n";
		for (int i = 0; i < paramInfos.length; i++) {
			String str = gson.toJson(paramInfos[i]);
			str_Json += str+"\n";
		}

		// 将json格式文件存入txt文件中
		writeToTxt("homework01.json", str_Json);
		System.out.println("数据库数据导出json文件成功，请查看相对路径下的homework01.json文件");
		// 关闭数据库连接
		DbUtils.close(rs, ps, conn);
	}
}
