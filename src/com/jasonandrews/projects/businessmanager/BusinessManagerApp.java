package com.jasonandrews.projects.businessmanager;

import java.awt.EventQueue;

public class BusinessManagerApp {

	private static AppManager appManager;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					appManager = new AppManager();
					ApplicationFrame frame = new ApplicationFrame(appManager);					
					frame.setVisible(true);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
