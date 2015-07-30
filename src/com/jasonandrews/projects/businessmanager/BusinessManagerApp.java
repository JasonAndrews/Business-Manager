package com.jasonandrews.projects.businessmanager;

import java.awt.EventQueue;

public class BusinessManagerApp {

	private static AppManager appManager;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					appManager = new AppManager();
					ApplicationFrame appFrame = new ApplicationFrame(appManager);					
					appFrame.setVisible(true);
					
					appManager.setApplicationFrame(appFrame);
					
					appFrame.loadProperties();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
