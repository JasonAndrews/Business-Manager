package com.jasonandrews.projects.businessmanager;

import java.awt.Color;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		
		//dbConnector = new DatabaseConnector("", "", "");
		
		customerTableColumns = new String[]{"Customer No.", "First Name", "Last Name"};
		
		//firstTimeUseCheck();
		
	}
	
	//Get the column names for a table (employee or customer).
	String[] getTableColumnNames(String tableName) {
		String[] columnNames = null;
		
		try {
			
		} catch (Exception ex) {
			ex.printStackTrace();
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
	
	//Get row data depending on the query passed.
	Object[][] getTableRowData(String query) {
		System.out.println("QUERY: ["+query+"]");
		Object[][] rowData = null;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(query);			
			
			int count = 0;
			resultSet.last();
			count = resultSet.getRow();
			resultSet.beforeFirst();
			
			rowData = new Object[count][7];
			
			System.out.println("Counted rows: " + count);
			
			int arrayRow = 0;
			
			
			//NEED A THREAD HERE!!
			while(resultSet.next()) { //While there is another row of data.
				
				//Load the data into the array.
				rowData[arrayRow][0] = resultSet.getInt("customer_number");
				rowData[arrayRow][1] = resultSet.getString("first_name");
				rowData[arrayRow][2] = resultSet.getString("last_name");
				rowData[arrayRow][3] = resultSet.getString("address_one");
				rowData[arrayRow][4] = resultSet.getString("address_two");
				rowData[arrayRow][5] = resultSet.getString("address_city");
				rowData[arrayRow][6] = resultSet.getString("address_country");
				
				System.out.println(rowData[arrayRow][0] + " | " + rowData[arrayRow][1] + " | " + rowData[arrayRow][2]); //DEBUGGING.
				
				++arrayRow; //Increment the index for the array.				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(statement != null) { 
				try {
					statement.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(resultSet != null) {				
				try {
					resultSet.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		
		return rowData;	//return the row of data.
	}
	
	public boolean testConnectionToDatabase(String url, String user, String password) {
		//Add a thread here.
		
		dbConnector = new DatabaseConnector(url, user, password);	
		
		firstTimeUseCheck();
		
		return true;
	}
	
	//Check if this is the first time the application has been used previously (no users within the database).
	private void firstTimeUseCheck() {		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			statement = connection.createStatement(); //Creates a statement.
			
			resultSet = statement.executeQuery("SELECT * FROM `users`"); //Executes a SELECT query and store the result set.
			
			if(!resultSet.next()) { //There's no users, which means this is the first time the application has been used. 
				
				String newUserQuery = "INSERT INTO `users` (`username`, `password`, `admin`) VALUES ('admin', '" + Encryption.hash("password") + "', '1')"; //Creating a string that will store the INSERT query for the new user.
				//System.out.println("QUERY: " + newUserQuery);
				statement.executeUpdate(newUserQuery); //Execute the query.
				
				appFrame.setLoginFields("admin", "password");
				
				//POPUP DIALOG STATING "Because this is the first time you are using the application, an account with the username 'admin' and the password 'password' was created for you. You may add new users once logged in and you can also delete the temporary account provided.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(statement != null) { 
				try {
					statement.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(resultSet != null) {				
				try {
					resultSet.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}		
	}
	
	
	
	public boolean loginUser(String username, String password) {
		
		Connection connection = dbConnector.getConnection(); 
		
		try {			
			
			if(connection == null || !connection.isValid(0)) { //Check if the application is not connected to the database.
				//appFrame.triggerError(ApplicationFrame.ERROR_LOGIN_FAILED, "Login failed, there is no connection to the database.");
				System.out.println(connection + " | " + connection.isValid(0) +  " | Failed at 1");
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
						loggedIn = true;
						appFrame.displayHome();
						//Continue onto the next HOME panel where most of the content will actually be.
						//Check if the user is an administrator.
					}			
					
				} else {
					appFrame.triggerError(ApplicationFrame.ERROR_LOGIN_FAILED, "Login failed, the username you have specified does not exist.");
					//loginErrorLbl.setText("Login failed, the username you have specified does not exist.");
					System.out.println("Failed at 4");
					return false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Failed at 5");
			return false;
		}
		
		return true;			
	}
	
	public void setApplicationFrame(ApplicationFrame appFrame) {
		this.appFrame = appFrame;
	}
	public ApplicationFrame getApplicationFrame() {
		return this.appFrame;
	}
	
	public boolean isUserLoggedIn() {
		return this.loggedIn;
	}
	
	
	
}
