package com.jasonandrews.projects.businessmanager;

import java.awt.EventQueue;

/*
 * Continuing reworking the app for OOP.
 * Create customer table with column names from database.
 * Create customer table rows with info from database, if there is no customers, then a message saying "There are no customers yet!" or so.
 * 
 * Partial searching -  SELECT * FROM TABLE WHERE 'file-id' LIKE '32-%' OR 'file-id' LIKE '46-%';
 */

public class BusinessManagerApp {

	private static AppManager appManager;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					appManager = new AppManager();
					ApplicationFrame frame = new ApplicationFrame(appManager);					
					frame.setVisible(true);
					
					appManager.setApplicationFrame(frame);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
