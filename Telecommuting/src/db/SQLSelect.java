package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSelect {

	public SQLSelect(Connection con, String query) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				
				String user_code = rs.getString("user_code");
				String name = rs.getString("name");
				String datetime = rs.getString("datetime");
				String state = rs.getString("state");
				
				System.out.println(user_code + ", " + name + ", " + datetime + ", " + state);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
