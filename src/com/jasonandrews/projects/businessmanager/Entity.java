package com.jasonandrews.projects.businessmanager;

public abstract class Entity {
	
	public static enum EntityTypes {CUSTOMER, EMPLOYEE, USER}
	
	public abstract Object[] getInformation();
}
