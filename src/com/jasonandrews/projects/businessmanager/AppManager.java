package com.jasonandrews.projects.businessmanager;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

/* 
 * This class will be the core of the application. It will handle most non-related GUI actions.
 * 
 */
public class AppManager {

	private DatabaseConnector dbConnector;
	private ApplicationFrame appFrame;
	
	private String[] customerTableColumns;
	private String[] customerTableRowData;
	
	private String[] employeeTableColumns;
	private String[] employeeTableRowData;
	
	private boolean loggedIn;
	
	public AppManager() {
		//this.appFrame = appFrame;
		
		this.loggedIn = false;
		
		dbConnector = new DatabaseConnector("", "", "");
		
		customerTableColumns = new String[]{"Customer No.", "First Name", "Last Name"};
		
		try {
			firstTimeUseCheck();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Get the column names for a table (employee or customer).
	String[] getTableColumnNames(String tableName) {
		String[] columnNames = null;
		
		try {
			
		} catch (Exception ex) {
			
		}
		switch(tableName) {
			case "Customers": { 
				columnNames = customerTableColumns;
				break;
			}
			case "Employees": {
				columnNames = employeeTableColumns;
				break;
			}
		}
		return columnNames;
	}
	
	String[] getTableRowData(String tableName) {
		
		return customerTableColumns;		
	}
	
	//Check if this is the first time the application has been used previously (no users within the database).
	private void firstTimeUseCheck() throws SQLException, NoSuchAlgorithmException {
		if(dbConnector.getConnection() == null) return;
		
		Statement statement = dbConnector.getConnection().createStatement(); //Creates a statement.
		
		ResultSet resultSet = statement.executeQuery("SELECT * FROM `users`"); //Executes a SELECT query and store the result set.
		
		if(!resultSet.next()) { //There's no users, which means this is the first time the application has been used. 
			
			String newUserQuery = "INSERT INTO `users` (`username`, `password`, `admin`) VALUES ('admin', '" + Encryption.hash("password") + "', '1')"; //Creating a string that will store the INSERT query for the new user.
			//System.out.println("QUERY: " + newUserQuery);
			statement.executeUpdate(newUserQuery); //Execute the query.
			
			appFrame.setLoginFields("admin", "password");
		}
	}
	
	private boolean loginUser(String username, String password) {
		
		Connection connection = dbConnector.getConnection(); 
		
		try {			
			if(connection == null || !connection.isValid(0)) { //Check if the application is not connected to the database.
				appFrame.triggerError(ApplicationFrame.ERROR_LOGIN_FAILED, "Login failed, there is no connection to the database.");
				return false;
				//loginErrorLbl.setText("Login failed, there is no connection to the database.");
			} else {
			
				Statement statement = connection.createStatement();
				
				ResultSet resultSet = statement.executeQuery("SELECT * FROM `users` WHERE `username` = '"+username+"'");
				
				if(resultSet.next()) { //If their username exists in the database.
					//System.out.println("Username: [" + username + "] | Admin ID: [" + resultSet.getInt("adminId") + "]"); //Debugging
					String hashedPassword = Encryption.hash(password);
					//System.out.println("PASSWORD: " + password + " | HASHED: " + hashedPassword);
					
					//Retrieve the password and compare the stored password with the password specified by the user.
					if(!hashedPassword.equals(resultSet.getString("password"))) { //If the passwords do not match.
						appFrame.triggerError(ApplicationFrame.ERROR_LOGIN_FAILED, "Login failed, you have entered an incorrect password.");
						//loginErrorLbl.setText("Login failed, you have entered an incorrect password.");
					} else {
						//THEY LOGGED IN SUCCESSFULLY
						
						//Continue onto the next pane where most of the content will actually be.
						//Check if the user is an administrator.
					}
					
				} else {
					appFrame.triggerError(ApplicationFrame.ERROR_LOGIN_FAILED, "Login failed, the username you have specified does not exist.");
					//loginErrorLbl.setText("Login failed, the username you have specified does not exist.");
					return false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;			
	}
	
}
