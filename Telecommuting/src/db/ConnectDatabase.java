package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
	
	public Connection con;
	
	public ConnectDatabase() {
		makeConnection();
	}

	public void makeConnection() {
		String url = "jdbc:mysql://localhost:3306/javaproject";
		String id = "root";
		String password = "1006";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("����̹� ���� ����");
			con = DriverManager.getConnection(url, id, password);
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�.");
		} catch (SQLException e) {
			System.out.println("���ῡ �����Ͽ����ϴ�.");
		}

	}

	public static void main(String[] args) {
		ConnectDatabase dbConnect = new ConnectDatabase(); // ������ ȣ���� ���� DB ����
		new SQLSelect(dbConnect.con, "select * from attendance"); // ���� ����
	}

}
