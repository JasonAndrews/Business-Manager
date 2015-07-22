package com.jasonandrews.projects.businessmanager;


import java.awt.Color;
import java.security.NoSuchAlgorithmException;
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
	
	//Close the connection to the database.
	public void close() {
		try {
			if(connection != null) {
		}
			connection.close(); //Close the connection.
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	//Return the connection object.
	public Connection getConnection() {
		
		try {
			
			//Some debugging.
			//System.out.println("Attempting to connect to URL: [" + url + "] | USER: [" + user + "] | PASSWORD: [" + password + "]");
			//System.out.println(url + " | " + user + " | " + " | " + password);
			connection = DriverManager.getConnection(url, user, password);
			
			//return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			//return false;
		} 
		
		return connection;
	}
	

}
