package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildStaticParameters {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/Psych";
	static final String USER = "root";
	static final String PASS = "Abcde@12345";
	public static Connection conn = null;
	public static Statement stmt =null;

	public static void buildConnectionWithSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt=BuildStaticParameters.conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getDBConnection() {

		Connection dbConnection = null;

		try {
			Class.forName(JDBC_DRIVER).newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dbConnection = DriverManager.getConnection(DB_URL, USER,PASS);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return dbConnection;

	}
}
