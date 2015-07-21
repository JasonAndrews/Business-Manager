package com.jasonandrews.projects.businessmanager;

import java.awt.EventQueue;

public class BusinessManagerApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationFrame frame = new ApplicationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
