
package com.jasonandrews.projects.businessmanager;

import java.awt.EventQueue;
/**
 * This class contains the main method for the application.
 * @author Jason Andrews
 * @version 1.0
 * @dependencies ApplicationFrame.java, AppManager.java
 */
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
					
					//Request focus, this stops the 'Login' button on the main menu being 'focused' when the app starts.
					appFrame.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
