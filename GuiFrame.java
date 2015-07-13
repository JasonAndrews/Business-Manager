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

public class GuiFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

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
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton configureBtn = new JButton("Configure");
		
		configureBtn.setToolTipText("Configure the database settings.");
		configureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//When the Configure button is clicked/pressed.
			}
		});
		configureBtn.setBounds(268, 128, 122, 33);
		contentPane.add(configureBtn);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(268, 172, 122, 33);
		contentPane.add(loginBtn);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBounds(268, 216, 122, 33);
		contentPane.add(exitBtn);
		
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
	}
}
