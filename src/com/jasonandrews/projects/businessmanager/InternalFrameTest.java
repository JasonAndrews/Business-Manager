package com.jasonandrews.projects.businessmanager;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class InternalFrameTest extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InternalFrameTest frame = new InternalFrameTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InternalFrameTest() {
		setBounds(100, 100, 450, 300);

	}

}
