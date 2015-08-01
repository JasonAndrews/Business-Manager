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
		
		SwingWorker<Integer, Integer> sw = null;
		
		sw = new SwingWorker<Integer, Integer>() {

			@Override
			protected Integer doInBackground() {
				try {
					
					
					//System.out.println("Attempting connection..");
					connection = DriverManager.getConnection(url, user, password);
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				return 1;
			}
			
			@Override
			protected void done() {
				
				//System.out.println("Finished attempting connection..");
				
				if(connection == null) {
					//System.out.println("Connection attempt failed..");
					appFrame.triggerError(ApplicationFrame.ERROR_CONNECTION_FAILED, "The connection to the database failed!\n\nPlease ensure your configuration credentials are correct!");
				} else {
					//System.out.println("Connection attempt succeeded..");
					connection = null;
					appFrame.triggerSuccess(ApplicationFrame.SUCCESS_CONNECTION_PASSED); //DO SOMETHING TO SHOW THE CONNECTION ACTUALLY WORKED HERE!! LIKE to show the 'checkmark'.					
				}
			}
			
		};
		sw.execute();
		
		return connection;
	}
	

}
