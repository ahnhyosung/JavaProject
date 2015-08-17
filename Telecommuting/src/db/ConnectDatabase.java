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
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(url, id, password);
			System.out.println("데이터베이스 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}

	}

	public static void main(String[] args) {
		ConnectDatabase dbConnect = new ConnectDatabase(); // 생성자 호출을 통한 DB 연결
		new SQLSelect(dbConnect.con, "select * from attendance"); // 쿼리 실행
	}

}
