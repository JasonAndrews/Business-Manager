package com.jasonandrews.projects.employeemanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class GuiFrame extends JFrame {

	private JPanel mainMenuPane;
	private JPanel configurePane;
	private JPanel loginPane;
	
	private JTable table;
	private JTable table_1;
	private JTextField urlTextField;
	private JTextField userTextField;
	private JPasswordField pwPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiFrame frame = new GuiFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
		
		try {
			GuiFrame frame = new GuiFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public GuiFrame() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 490);
		setResizable(false);
		
		mainMenuPane = new JPanel();
		mainMenuPane.setBackground(Color.WHITE);
		mainMenuPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainMenuPane.setLayout(null);		
		
		/*
		JButton configureBtn = new JButton("Configure");		
		configureBtn.setToolTipText("Configure the database settings.");
		configureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayConfiguration();
				//When the Configure button is clicked/pressed.
			}
		});
		configureBtn.setBounds(268, 172, 122, 33);
		mainMenuPane.add(configureBtn);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(268, 128, 122, 33);
		mainMenuPane.add(loginBtn);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBounds(268, 216, 122, 33);
		mainMenuPane.add(exitBtn);
		*/
		//Creating the elements of the Configuration pane.
		configurePane = new JPanel();
		configurePane.setBackground(Color.WHITE);
		configurePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		configurePane.setLayout(null);
		configurePane.setBounds(0,0,0,0);
		
		urlTextField = new JTextField();
		urlTextField.setBounds(242, 142, 226, 20);
		mainMenuPane.add(urlTextField);
		urlTextField.setColumns(10);
		//configurePane
		/*
		 
		// Creates a table, adds a scroll to it and fills in the table with the dummy info. 
		String[] columns = {"Employee Number", "First Name", "Surname", "Age"};
		
		Object[][] tableData = {
				{"0434", "Jayceon", "Zrews", "22"},	
				{"0003", "Jayceon", "Andrews", "22"},
				{"0001", "Jayceon", "Dndrews", "22"},
				{"0055", "Jayceon", "Cndrews", "22"},
				{"0105", "Jayceon", "Rndrews", "22"},
				{"0565", "Jayceon", "Endrews", "22"},
				{"0335", "Jayceon", "Fndrews", "22"},
				{"0006", "Jayceon", "Hndrews", "22"},
				{"0115", "Jayceon", "Xndrews", "22"},
				{"0095", "Jayceon", "Lndrews", "22"},
				{"0033", "Jayceon", "Gndrews", "22"},
				{"1015", "Jayceon", "Rndrews", "22"},
				{"1555", "Jayceon", "Vndrews", "22"},
				{"0002", "Jayceon", "Sndrews", "22"},
				{"0555", "Jayceon", "Kndrews", "22"},
				{"0335", "Jayceon", "Ondrews", "22"},
				
				{"0003", "Robert", "Stark", "26"}
				
		};
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(103, 125, 350, 200);
		contentPane.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		table_1.setAutoCreateRowSorter(true);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setColumnSelectionAllowed(true);
		table_1.setModel(new DefaultTableModel(tableData, columns));
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		*/
		setContentPane(mainMenuPane);
		
		JLabel urlLbl = new JLabel("URL:");
		urlLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		urlLbl.setBounds(173, 145, 61, 14);
		mainMenuPane.add(urlLbl);
		
		userTextField = new JTextField();
		userTextField.setColumns(10);
		userTextField.setBounds(242, 173, 226, 20);
		mainMenuPane.add(userTextField);
		
		JLabel userLbl = new JLabel("User:");
		userLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		userLbl.setBounds(173, 176, 61, 14);
		mainMenuPane.add(userLbl);
		
		JLabel pwLbl = new JLabel("Password:");
		pwLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLbl.setBounds(151, 207, 83, 14);
		mainMenuPane.add(pwLbl);
		
		pwPasswordField = new JPasswordField();
		pwPasswordField.setBounds(242, 204, 226, 20);
		mainMenuPane.add(pwPasswordField);
		
		JButton connectBtn = new JButton("Connect");
		connectBtn.setBounds(282, 235, 73, 23);
		mainMenuPane.add(connectBtn);
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.setBounds(365, 235, 73, 23);
		mainMenuPane.add(resetBtn);
		
	
		
	}
	
	void displayMainMenu() {
		setContentPane(mainMenuPane);
		configurePane.setVisible(false);
		loginPane.setVisible(false);
	}
	

	void displayConfiguration() {
		mainMenuPane.setVisible(false);
		loginPane.setVisible(false);
		setContentPane(configurePane);
	}
	
	void displayLogin() {
		mainMenuPane.setVisible(false);
		loginPane.setVisible(false);
		setContentPane(loginPane);
	}
}
