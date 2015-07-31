package com.jasonandrews.projects.businessmanager;

public class User extends Entity {

	private int userNumber;
	private String username;
	private String password;
	private boolean isAdmin;
	
	public User(int userNumber, String username, String password, boolean isAdmin) {
		this.userNumber = userNumber;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public Object[] getInformation() {
		Object info[] = {userNumber, username, password, isAdmin};
		
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
	
}
