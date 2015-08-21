package com.jasonandrews.projects.businessmanager;

import java.sql.Date;
import java.util.ArrayList;

public class User extends Entity {
	
	private int userNumber;
	private String username;
	private String password;
	private boolean isAdmin;
	private Date lastLogonDate;
	//private Date date;
	
	public User(int userNumber, String username, String password, boolean isAdmin, Date lastLogonDate) {
		this.userNumber = userNumber;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.lastLogonDate = lastLogonDate;
	}
	
	
	
	public Object[] getInformation() {
		Object info[] = {userNumber, username, (isAdmin ? "Yes" : "No"), lastLogonDate};		
		return info;
	}
	
	public int getUserNumber() {
		return this.userNumber;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
	public Date getLastLogonDate() {
		return this.lastLogonDate;
	}
	
	/**
	 * Static method. Converts an ArrayList of User objects into displayable data for a JTable.
	 * @param appManager - The AppManager object.
	 * @param objectList - An ArrayList of Entity objects to convert to displayable data.
	 * @return - Returns a 2-Dimensional array of Objects containing the information from the different User objects within the ArrayList. 
	 */
	public static Object[][] convertObjectsToRowData(AppManager appManager, ArrayList<Entity> objectList) {
		Object[][] rowData = new Object[objectList.size()][appManager.getTableColumnCount(Entity.EntityTypes.USER)];		
		
		int index = 0;
		for(Object user : objectList) {
			rowData[index] = ((User) user).getInformation();
			index++;
		}		
		return rowData;
	}
}
