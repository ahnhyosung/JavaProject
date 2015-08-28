package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import admin.AdminRegistPanel;

public class DBProcess {

	public Connection con;

	public static String[] tempImage = { "test_1.jpg", "test_2.jpg",
			"test_3.jpg" };

	public DBProcess() {
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

	public void closeCon() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertUser(String id, String name, File f) {
		try {
			FileInputStream fis = new FileInputStream(f);
			PreparedStatement pstmt = con
					.prepareStatement("insert into user values(?,?,?)");

			pstmt.setString(1, id);
			pstmt.setString(2, name);
			System.out.println(name);
			pstmt.setBinaryStream(3, fis, (int) f.length());

			pstmt.executeUpdate();

			pstmt.close();
			fis.close();

			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectUser(int flag) {
		try {
			String query = "select user_code, image from user";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			int num = 1;

			InputStream is = null;
			FileOutputStream fos = null;

			while (rs.next()) {

				String user_code = rs.getString(1);
				is = rs.getBinaryStream("image");

				switch (flag) {
				case 0:
					fos = new FileOutputStream("C:\\Temp\\" + user_code + "_"
							+ num + ".jpg");
					break;
				case 1:
					fos = new FileOutputStream("C:\\Temp\\facematch\\"
							+ user_code + "_" + num + ".jpg");
					break;
				}

				byte[] buff = new byte[8192];
				int len;

				while (0 < (len = is.read(buff))) {
					fos.write(buff, 0, len);
				}
				num++;
			}

			fos.close();
			is.close();
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String createUniqueID() {
		Random rd = new Random();
		String idNum = "";
		for (int i = 0; i < 7; i++) {
			idNum += rd.nextInt(10);
		}
		return idNum;
	}

	public void fileIO(String name, int count) {
		InputStream in;
		try {
			in = new FileInputStream("C:\\Temp\\test.jpg");
			OutputStream out = new FileOutputStream("C:\\Temp\\test" + "_"
					+ count + ".jpg");

			int bData;

			while (true) {
				bData = in.read();
				if (bData == -1)
					break;

				out.write(bData);
			}
			in.close();
			out.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (count == 3) {
			fileReady(name);
		}
	}

	public void pullImage() {
		selectUser(0);
	}

	public void fileReady(String name) {
		String id = createUniqueID();
		for (String str : tempImage) {
			File f = new File("C:\\Temp\\" + str);

			insertUser(id, name, f);
		}

	}

}
