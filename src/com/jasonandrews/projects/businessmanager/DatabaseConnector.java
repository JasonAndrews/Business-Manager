package com.jasonandrews.projects.businessmanager;

//String url = "jdbc:mysql://localhost:3306/TABLE_NAME";
//String user = "root";
//String pw = "";

import java.sql.*;

import javax.swing.SwingWorker;

public class DatabaseConnector {	
	private String url;
	private String user;
	private String password;
	private Connection connection;
	
	private ApplicationFrame appFrame;
	
	public DatabaseConnector(ApplicationFrame appFrame, String url, String user, String password) {
		this.appFrame = appFrame;
		this.url = url;
		this.user = user;
		this.password = password;
	}
		
	//Return the connection object.
	public Connection getConnection() {		
		try {
			
			//Attempt to connect to the database.
			connection = DriverManager.getConnection(url, user, password);
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
		return connection;
	}	
	
	

}
