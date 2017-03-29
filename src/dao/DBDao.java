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
	
	//�������ݿ�
	public static Connection getConnection(){
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			System.out.println("���ݿ������쳣");
			e.printStackTrace();
		}
		return connection;
	}
	//�ر����ݿ�
	public static void closeConnection(Connection connection){
		if(connection!=null){
			try {
				connection.close();//�ر����
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
