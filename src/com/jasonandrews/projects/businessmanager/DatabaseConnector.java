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
	
	//Connect to the database.
	public boolean connect() {		
		
		try {
			
			if(connection != null && connection.isValid(0)) { //If there's already a connection, close it before continuing.
				//System.out.println("Already has a connection.");
				connection.close(); //Close the connection.
			}
			
			//Some debugging.
			//System.out.println("Attempting to connect to URL: [" + url + "] | USER: [" + user + "] | PASSWORD: [" + password + "]");
			
			connection = DriverManager.getConnection(url, user, password);
			
			return true;
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}		
	}
	
	//Close the connection to the database.
	public void close() throws SQLException {
		if(connection != null) {
			connection.close(); //Close the connection.
		}
		
	}
	
	//Return the connection object.
	public Connection getConnection() {
		return connection;
	}
	

}
