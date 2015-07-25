package com.jasonandrews.projects.businessmanager;

import java.util.ArrayList;

public class Customer {
	
	
	private int customerNumber;
	private String firstName;
	private String lastName;
	private String addressOne;
	private String addressTwo;
	private String addressCity;
	private String addressCountry;
	
	public Customer(int customerNumber, String firstName, String lastName, String addressOne, String addressTwo, String addressCity, String addressCountry) {
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.addressCity = addressCity;
		this.addressCountry = addressCountry;
		
		System.out.println("NEW CUSTOMER: " + customerNumber + " " + firstName + " " + lastName + " " + addressOne + " " + addressTwo + " " + addressCity + " " + addressCountry);
	}
	
	public void update(String firstName, String lastName, String addressOne, String addressTwo, String addressCity, String addressCountry) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.addressCity = addressCity;
		this.addressCountry = addressCountry;
	}
	
	private Object[] getCustomerInformation() {
		Object[] info = {this.customerNumber, this.firstName, this.lastName, this.addressOne, this.addressTwo, this.addressCity, this.addressCountry};		
		return info;
	}
		
	/*
	 * Static method.
	 * Convert the customer objects to arrays so that they can be displayed on a table. 
	 */
	public static Object[][] convertObjectsToRowData(AppManager appManager, ArrayList<Object> objectList) {
		Object[][] rowData = new Object[objectList.size()][appManager.getTableColumnCount("CUSTOMER")];		
		
		int index = 0;
		for(Object customer : objectList) {
			rowData[index] = ((Customer) customer).getCustomerInformation();
			index++;
		}		
		return rowData;
	}
}
