package wgh.util;

import java.sql.*;

public class DbUtils {
	private static String DRIVER = "net.sourceforge.jtds.jdbc.Driver";
	private static String URL = "jdbc:jtds:sqlserver://58.216.237.150:1433/Library";
	public final static int PAGE_SIZE=2;
	

	private static String USERID = "test";
	private static String UERPASSWORD = "test123$%^";

	// 禁止实例对象
	private DbUtils() {
	}

	static {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得打开的数据连接
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERID, UERPASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 关闭数据集/语句/连接对象
	public static void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}