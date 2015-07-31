package com.jasonandrews.projects.businessmanager;

//String url = "jdbc:mysql://localhost:3306/TABLE_NAME";
//String user = "root";
//String pw = "";

import java.sql.*;

public class DatabaseConnector {
	private String url;
	private String user;
	private String password;
	private Connection connection;
	
	public DatabaseConnector(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
		
	//Return the connection object.
	public Connection getConnection() {		
		try {
			
			connection = DriverManager.getConnection(url, user, password);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} 		
		return connection;
	}
	

}
