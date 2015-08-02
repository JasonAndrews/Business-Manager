package com.jasonandrews.projects.businessmanager;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

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
	
	private ArrayList<User> userList;
	private boolean loggedIn;
	
	
	public AppManager() {
		//this.appFrame = appFrame;
		
		this.loggedIn = false;
		
		
		
		//dbConnector = new DatabaseConnector("", "", "");
		
		customerTableColumns = new String[]{"Customer No.", "First Name", "Last Name"};
		
		//loadUsers();
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
	ArrayList<Entity> getTableRowData(String tableName, String query) {
		System.out.println("QUERY: ["+query+"]");
		Object[][] rowData = null;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		ArrayList<Entity> objectList = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(query);			
			
			int count = 0;
			resultSet.last();
			count = resultSet.getRow();
			resultSet.beforeFirst();
			
			rowData = new Object[count][this.getTableColumnCount(tableName)];
			
			objectList = new ArrayList<Entity>();
			//System.out.println("Counted rows: " + count);
			
			int arrayRow = 0;
			
			
			//NEED A THREAD HERE!!
			while(resultSet.next()) { //While there is another row of data.		
				
				//Switch to the appropriate case so the data is loaded correctly.
				switch(tableName) {
					case "CUSTOMERS": {
						objectList.add(new Customer(resultSet.getInt("customer_number"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("address_one"), resultSet.getString("address_two"), resultSet.getString("address_city"), resultSet.getString("address_country")));
						//Load the data into the array.
						//rowData[arrayRow][0] = resultSet.getInt("customer_number");
						//rowData[arrayRow][1] = resultSet.getString("first_name");
						//rowData[arrayRow][2] = resultSet.getString("last_name");
						//rowData[arrayRow][3] = resultSet.getString("address_one");
						//rowData[arrayRow][4] = resultSet.getString("address_two");
						//rowData[arrayRow][5] = resultSet.getString("address_city");
						//rowData[arrayRow][6] = resultSet.getString("address_country");
						
						//System.out.println(rowData[arrayRow][0] + " | " + rowData[arrayRow][1] + " | " + rowData[arrayRow][2]); //DEBUGGING.
						break;
					}
					case "EMPLOYEES": {
						break;
					}
					case "USERS": {
						break;
					}
				}
				
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
		return objectList;	//return the row of data.
	}
	
	public boolean testConnectionToDatabase(String url, String user, String password) {
		//Add a thread here.
		//System.out.println("testConnectionToDatabase()..");
		dbConnector = new DatabaseConnector(this.appFrame, url, user, password);	
		
		if(dbConnector.getConnection() == null) { //If the firstTimeUseCheck returns false, then there is a connection issue.
			//System.out.println("Failed connection.");
			return false;
		}
		
		firstTimeUseCheck();
		
		return true;
	}
	
	//Check if this is the first time the application has been used previously (no users within the database).
	private boolean firstTimeUseCheck() {		
		
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
				
				//POPUP DIALOG STATING "Because this is the first time you are using the application, an account with the username 'admin' and the password 'password' was created for you. You may add new users once logged in and you can also delete the temporary account provided.
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
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
		
		return true;
	}
	
	
	/*
	 * Loads all users from the database into the application.
	 */
	public void loadUsers() { 
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("SELECT * from `users`");
			
			userList = new ArrayList<User>(); //Initialize a new ArrayList to store User objects.
			User tempUser = null; 
			
			while(resultSet.next()) {
				tempUser = new User(resultSet.getInt("user_number"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getBoolean("admin"));
				
				System.out.println(tempUser.getInformation());
				
				userList.add(tempUser);
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
	
	/*
	 * Log a user into the application.
	 */
	public boolean loginUser(String username, String password) {		
				
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {			
			connection = dbConnector.getConnection(); 

			if(connection != null) { //Check if the application successfully connected to the database.
				//The connection succeeded.
				
				statement = connection.createStatement();
				
				resultSet = statement.executeQuery("SELECT * FROM `users` WHERE `username` = '"+username+"'");
				
				//Check if the result set has atleast one row of data.
				if(resultSet.next()) { 
					//If their username exists in the database.
					String hashedPassword = Encryption.hash(password);
					//System.out.println("PASSWORD: " + password + " | HASHED: " + hashedPassword);
					
					//Retrieve the password and compare the stored password with the password specified by the user.
					if(hashedPassword.equals(resultSet.getString("password"))) { //If the passwords do not match.
						//The passwords did match so return true. 
						return true;
					} else {
						//The passwords did not match so return false.
						return false;
					}					
				}
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
		return false;
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
	
	public int getTableColumnCount(String tableName) {
		int columns = 0;
		
		switch(tableName) {
		
			case "CUSTOMERS": {
				columns = 7;
				break;
			}
			case "EMPLOYEES": {
				columns = 3;		
				break;
			}
			case "USERS": {
				columns = 4;
				break;
			}
			
		}
		return columns;
	}
	
	public Object[][] getRowData(String tableName, ArrayList<Entity> objectList) {
		Object[][] rowData = Customer.convertObjectsToRowData(this, objectList);
		
		return rowData;
	}
	
	//Send a query to the database. Could be an INSERT query or an UPDATE query.
	public void updateDatabase(String tableName, Object objectToUpdate) {
		
		Connection connection = null;
		Statement statement = null;
		
		String query = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			switch(tableName) {
			
				case "CUSTOMERS": {
					Customer customer = (Customer) objectToUpdate;
					
					if(customer.getCustomerNumber() > 0) { //If the customer already exists and needs to be updated.
						
						query = "UPDATE `customers` SET `first_name` = '"+customer.getFirstName()+"', `last_name` = '"+customer.getLastName()+"', `address_one` = '"+customer.getAddressOne()+"', `address_two` = '"+customer.getAddressTwo()+"', `address_city` = '"+customer.getAddressCity()+
								"', `address_country` = '"+customer.getAddressCountry()+"' WHERE `customer_number` = '"+customer.getCustomerNumber()+"'";
						System.out.println("UPDATE QUERY: " + query);
					} else { //If the customer is being created.
						
						query = "INSERT INTO `customers` (`first_name`, `last_name`, `address_one`, `address_two`, `address_city`, `address_country`) VALUES ('"+customer.getFirstName()+"', '"+customer.getLastName()+"', '"+customer.getAddressOne()+"','"+customer.getAddressTwo()+"',"
								+ "'"+customer.getAddressCity()+"', '"+customer.getAddressCountry()+"')";
						System.out.println("INSERT QUERY: " + query);
					}
					
					statement = connection.createStatement();
					
					statement.executeUpdate(query);
					
					break;
				}
				case "EMPLOYEES": {
			
					break;
				}
				case "USERS": {
		
					break;
				}
		
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
		}
		
	}
		
}
