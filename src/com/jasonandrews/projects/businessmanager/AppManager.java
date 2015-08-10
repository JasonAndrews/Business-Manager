package com.jasonandrews.projects.businessmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This class will be the core of the application. It will handle most non-related GUI actions, such as getting information from the MySQL database. 
 * @author Jason Andrews
 * @version 0.30
 * @dependencies ApplicationFrame.java, DatabaseConnector.java
 */
public class AppManager {

	private DatabaseConnector dbConnector;
	private ApplicationFrame appFrame;

	private String[] customerTableColumns;
	private String[] customerTableRowData;
	
	private String[] employeeTableColumns;
	private String[] employeeTableRowData;
	
	private ArrayList<User> userList; //The list of User objects loaded from the database.
	private User loggedInUser;
	
	
	public AppManager() {		
		this.customerTableColumns = new String[]{"Customer No.", "First Name", "Last Name"};		
	}
	
	
	/**
	 * Get the column names for a table (employee or customer).
	 * @param tableName
	 * @return Return the column names for a table, depending on the String passed as a parameter.
	 */
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
	
	/**
	 * Get row data depending on the query passed.
	 * @param tableName
	 * @param query
	 * @return Returns an ArrayList of child Entity objects, depending on the string tableName. Passing "CUSTOMERS" will result in an ArrayList of Customer objects being returned.
	 */
	ArrayList<Entity> getTableRowData(String tableName, String query) {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		ArrayList<Entity> objectList = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(query);			
			
			objectList = new ArrayList<Entity>();
			//System.out.println("Counted rows: " + count);
			
			
			
			
			//NEED A THREAD HERE!!
			while(resultSet.next()) { //While there is another row of data.		
				
				//Switch to the appropriate case so the data is loaded correctly.
				switch(tableName) {
					case "CUSTOMERS": {
						
						//Add a new Customer object with the given information to the ArrayList.
						objectList.add(new Customer(resultSet.getInt("customer_number"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("address_one"), resultSet.getString("address_two"), resultSet.getString("address_city"), resultSet.getString("address_country")));						
						
						break;
					}
					case "EMPLOYEES": {
						break;
					}
					case "USERS": {
						break;
					}
				}				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//Close all of the objects connections.
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
		return objectList;	//return the ArrayList of child Entity objects (Customer / Employee / User.)
	}
	
	/**
	 * Test the connection to the database. 
	 * This method is only used from the Configuration screen when users click the 'Test Connection' button so users can see if they're credentials are correct.
	 * @param url
	 * @param user
	 * @param password
	 * @return Returns whether or not the application successfully connected to the database.
	 */
	public boolean testConnectionToDatabase(String url, String user, String password) {		
		
		DatabaseConnector dbConn = new DatabaseConnector(url, user, password);
		
		if(dbConn.getConnection() == null) { //If the Connection object that was returned is null, then the connection failed and so return false.
			return false;
		}		
		return true;
	}
	
	/**
	 * Check if this is the first time the application has been used previously (no users within the database).
	 * @return Return's whether or not it's the applications first run time with the given database.
	 */
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
	
	
	/**
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
				
				//System.out.println(tempUser.getInformation()); //Debugging.
				
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
	 * Attempt to log a user into the application.
	 * Load all the users from the database.
	 * Compare the given password (from the Login Form) with each of the User object's password.
	 */
	public boolean loginUser(String username, String password) {	
		
		boolean successful = false;
		
		try {
			//Create a new DatabaseConnector object.
			Properties properties = this.appFrame.getProperties();
			dbConnector = new DatabaseConnector(properties.getProperty("dbUrl"), properties.getProperty("dbUser"), properties.getProperty("dbPassword"));
			
			
			
			//Load all of the users from the database.
			this.loadUsers();
			
			//Check the number of users within the ArrayList. If it's less than one, then create a temporary user with the credentials; Username: "admin", Password: "password".
			if(this.userList.size() < 1) {
				this.firstTimeUseCheck(); //Checks if there is any users within the database. If there isn't, create one with the credentials; Username: "admin", Password: "password".
			}
			
			//A for-each loop that loops through every user within the ArrayList.
			for(User user : userList) {
				//Compare the username and password entered in by the user from the Login Form with that of the User object's username and password.
				if(username.equals(user.getUsername()) && Encryption.hash(password).equals(user.getPassword())) {					
					//The passwords match so return true.
					this.loggedInUser = user;
					successful = true; 
					break;
				}
			}
			
			if(this.loggedInUser != null) recordLogin(this.loggedInUser);

		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return successful;
	}
	
	public void recordLogin(User user) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			
			connection = dbConnector.getConnection();
			
			statement = connection.createStatement();
			
			java.util.Date dt = new java.util.Date();

			java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String currentTime = sdf.format(dt);
			
			statement.executeUpdate("UPDATE `users` SET `last_logon` = '" + currentTime + "' WHERE `user_number` = '" + user.getUserNumber() + "'");
			
			
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
	
	public void setApplicationFrame(ApplicationFrame appFrame) {
		this.appFrame = appFrame;
	}
	public ApplicationFrame getApplicationFrame() {
		return this.appFrame;
	}
	
	public boolean isUserLoggedIn() {
		return (this.loggedInUser != null);
	}
	public User getLoggedInUser() {
		return this.loggedInUser;
	}
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
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
