/**
 * This application's purpose is to get a connection to a specific MySQL database.
 * @author Jason Andrews
 */

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
		
	public DatabaseConnector(String url, String user, String password) {
		
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
