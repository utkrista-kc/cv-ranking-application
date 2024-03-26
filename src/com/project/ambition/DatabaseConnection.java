package com.project.ambition;

import java.sql.Connection;
import java.sql.DriverManager;



public class DatabaseConnection {
public static Connection getDB()
	
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cv", "root", "utkrista1234");
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
