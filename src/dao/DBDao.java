package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBDao {
	private static String USER = "root";
	private static String PASSWORD = "lklj";
	private static String DB_URL = "jdbc:mysql://localhost:3306/xinzhi_edu1";
	private static String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static Connection connection = null;
	
	//连接数据库
	public static Connection getConnection(){
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			System.out.println("数据库连接异常");
			e.printStackTrace();
		}
		return connection;
	}
	//关闭数据库
	public static void closeConnection(Connection connection){
		if(connection!=null){
			try {
				connection.close();//关闭语句
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
