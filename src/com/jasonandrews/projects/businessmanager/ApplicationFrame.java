package com.jasonandrews.projects.businessmanager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.JProgressBar;
import javax.swing.Box;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JLayeredPane;
import javax.swing.JInternalFrame;

import java.awt.CardLayout;

import javax.swing.JTextArea;
import javax.swing.JCheckBox;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class ApplicationFrame extends JFrame {

	private AppManager appManager;
	
	private PopupFrame popupFrame;	
	private JPanel currentPanel;
		
	private JPanel mainMenuPanel;
	private JPanel configurePanel;
	private JPanel loginPanel;
	private JPanel homePanel;
	
	private JPanel customersPanel;
	
	private JTable table;
	private JTable customersTable;
	private JTextField urlTextField;
	private JTextField userTextField;
	private JTextField passwordTextField;
	
	private JLabel statusResultLbl;
	private JLabel errorLbl;
	private JLabel loginErrorLbl;
	
	private String url;
	private String user;
	private String password;
	private String[] c_columnNames;

	private Connection connection;

	private boolean popupIsShown;
	
	private static final Color BUTTON_BACKGROUND_COLOR = Color.BLACK;
	private static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
	private JTextField loginUsernameTextField;
	private JPasswordField loginPasswordField;
	private JTextField customerSearchTextField;
	
	private JMenuBar homeMenuBar;
	
	
	private JFrame customerInternalFrame;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField customerNoTextField;
	private JTextField addressOneTextField;
	private JTextField addressTwoTextField;
	private JTextField addressCityTextField;
	private JTextField addressCountryTextField;
	
	private ArrayList<Object> customerList; //A list of customer objects represented in the current customer table.
	
	/**
	 * @wbp.nonvisual location=82,359
	 */
	private final JPopupMenu customerOptionsPopup = new JPopupMenu();

	/**
	 * Create the frame.
	 */
	public ApplicationFrame(AppManager appManager) {		
		this.appManager = appManager;
		
		setTitle("Business Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 490);
		setResizable(false);		
		getContentPane().setLayout(new CardLayout(0, 0));
		
		popupFrame = new PopupFrame(appManager);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("FRAME - click.");
				//customerOptionsPopup.setVisible(false);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		createContentPanels(); //Create the different panels.
		//createInternalFrames();	
				
		//super.setContentPane(customersPanel);
		setContentPane(mainMenuPanel);
	}
	
	//Create the content panes that will be used. These are essentially the different screens of the application, such as the main menu and login screens.
	void createContentPanels() {			
		
		//Main Menu Panel.
		mainMenuPanel = new JPanel();
		mainMenuPanel.setBackground(Color.WHITE);
		mainMenuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainMenuPanel.setLayout(null);		
		
		
		JButton configurationBtn = new JButton("Configuration");		
		configurationBtn.setToolTipText("Configure the database settings.");
		configurationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayConfiguration();
				//When the Configure button is clicked/pressed.
			}
		});
		configurationBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		configurationBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		configurationBtn.setBounds(268, 213, 122, 33);
		mainMenuPanel.add(configurationBtn);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {				
				displayLogin();
			}				
		});
		loginBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		loginBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		loginBtn.setBounds(268, 128, 122, 33);		
		mainMenuPanel.add(loginBtn);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		exitBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		exitBtn.setBounds(268, 257, 122, 33);
		mainMenuPanel.add(exitBtn);
				
		JButton quickConnectBtn = new JButton("Quick Connect");
		quickConnectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String url = urlTextField.getText();
				String user = userTextField.getText();
				String password = passwordTextField.getText();
				
				if(!appManager.testConnectionToDatabase(url, user, password)) { //If the connection failed, then give them the error on the main menu.
					errorLbl.setText("Could not connect, please enter the correct credentials on the Configuration form.");		
					statusResultLbl.setText("Could not connect.");
					statusResultLbl.setForeground(Color.RED);			
					
				} else { 
					errorLbl.setText("");
					statusResultLbl.setForeground(Color.GREEN);
					statusResultLbl.setText("Connected.");
				}
			}
		});
		quickConnectBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		quickConnectBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		quickConnectBtn.setBounds(268, 169, 122, 33);
		mainMenuPanel.add(quickConnectBtn);
		
		errorLbl = new JLabel("");
		errorLbl.setForeground(Color.RED);
		errorLbl.setBounds(112, 301, 454, 14);
		mainMenuPanel.add(errorLbl);
				
		//Configure Panel
		
		
		//Creating the elements of the Configuration content pane.
		configurePanel = new JPanel();
		configurePanel.setBackground(Color.WHITE);
		configurePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		configurePanel.setLayout(null);
		configurePanel.setBounds(0,0,0,0);
		
		JLabel statusTextLbl = new JLabel("Status:");
		statusTextLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		statusTextLbl.setBounds(253, 111, 61, 14);
		configurePanel.add(statusTextLbl);
		
		statusResultLbl = new JLabel("Not connected.");
		statusResultLbl.setForeground(Color.RED);
		statusResultLbl.setBounds(324, 111, 124, 14);
		configurePanel.add(statusResultLbl);
		
		urlTextField = new JTextField("jdbc:mysql://localhost:3306/employee_manager");
		urlTextField.setBounds(242, 142, 226, 20);
		configurePanel.add(urlTextField);
		urlTextField.setColumns(10);
		
		JLabel urlLbl = new JLabel("URL:");
		urlLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		urlLbl.setBounds(173, 145, 61, 14);
		configurePanel.add(urlLbl);
		
		userTextField = new JTextField("root");
		userTextField.setColumns(10);
		userTextField.setBounds(242, 173, 226, 20);
		configurePanel.add(userTextField);
		
		JLabel userLbl = new JLabel("User:");
		userLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		userLbl.setBounds(173, 176, 61, 14);
		configurePanel.add(userLbl);
		
		JLabel pwLbl = new JLabel("Password:");
		pwLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLbl.setBounds(151, 207, 83, 14);
		configurePanel.add(pwLbl);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(242, 204, 226, 20);
		configurePanel.add(passwordTextField);
		
		JButton connectBtn = new JButton("Test Connection");
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String url = urlTextField.getText();
				String user = userTextField.getText();
				String password = passwordTextField.getText();
				
				//Attempt to connect to the database using the credentials.
				if(appManager.testConnectionToDatabase(url, user, password)) {
					statusResultLbl.setForeground(Color.GREEN);
					statusResultLbl.setText("Connected.");
				} else {
					statusResultLbl.setText("Could not connect.");
					statusResultLbl.setForeground(Color.RED);	
				}
				//try connect to the database specified.				
				
				//String url = "jdbc:mysql://localhost:3306/employee_manager";
				//String user = "root";
				//String pw = "";
				
				
			}
		});
		connectBtn.setBounds(272, 235, 83, 23);
		configurePanel.add(connectBtn);
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				urlTextField.setText("");
				userTextField.setText("");
				passwordTextField.setText("");
			}
		});
		resetBtn.setBounds(365, 235, 83, 23);
		configurePanel.add(resetBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayMainMenu();
			}
		});
		backBtn.setBounds(312, 282, 89, 23);
		configurePanel.add(backBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(242, 129, 226, 2);
		configurePanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(242, 269, 226, 2);
		configurePanel.add(separator_1);
		
		
		//Setting up the Login content pane.
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPanel.setLayout(null);
		loginPanel.setBounds(0,0,0,0);
		
		
		
		loginErrorLbl = new JLabel("");
		loginErrorLbl.setForeground(Color.RED);
		loginErrorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		loginErrorLbl.setBounds(169, 136, 380, 14);
		loginPanel.add(loginErrorLbl);
		
		JLabel loginUsernameLbl = new JLabel("Username:");
		loginUsernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		loginUsernameLbl.setBounds(169, 160, 81, 22);
		loginPanel.add(loginUsernameLbl);
		
		loginUsernameTextField = new JTextField();
		loginUsernameTextField.setBounds(260, 161, 207, 20);
		loginPanel.add(loginUsernameTextField);
		loginUsernameTextField.setColumns(10);
		
		JLabel loginPasswordLbl = new JLabel("Password:");
		loginPasswordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		loginPasswordLbl.setBounds(169, 193, 81, 22);
		loginPanel.add(loginPasswordLbl);
		
		loginPasswordField = new JPasswordField();
		loginPasswordField.setBounds(260, 194, 207, 21);
		loginPanel.add(loginPasswordField);
		
		JButton loginLoginBtn = new JButton("Login");
		loginLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				
				loginErrorLbl.setText(""); //Resetting the error message so users can see the next error message.
				
				char[] passwordArray = loginPasswordField.getPassword();
				String password = new String(passwordArray);
				String username = loginUsernameTextField.getText();
				//System.out.println(connection);
				if(appManager.loginUser(username, password)) {
					System.out.println("Logged in - ApplicationFrame");
					//They logged in. Load next panel.
				} else {
					
					//They failed to login.
					System.out.println("Failed Logged in - ApplicationFrame");
				}
			}
		});
		
		loginLoginBtn.setBounds(270, 226, 89, 23);
		loginPanel.add(loginLoginBtn);
		
		JButton logiClearBtn = new JButton("Clear");
		logiClearBtn.setBounds(369, 226, 89, 23);
		loginPanel.add(logiClearBtn);
		
		JSeparator loginSeparator = new JSeparator();
		loginSeparator.setBounds(260, 260, 207, 2);
		loginPanel.add(loginSeparator);
		
		JButton loginBackBtn = new JButton("Back");
		loginBackBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayMainMenu();				
			}
			
		});
		loginBackBtn.setBounds(324, 273, 89, 23);		
		loginPanel.add(loginBackBtn);
		
		
		
		
		
		//Home Panel
		homePanel = new JPanel();
		homePanel.setBackground(Color.WHITE);
		homePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		homePanel.setLayout(null);
		
		homeMenuBar = new JMenuBar();
		homeMenuBar.setBounds(0, 0, 681, 21);
		homePanel.add(homeMenuBar);
		
		JMenu applicationMenu = new JMenu("Application");
		homeMenuBar.add(applicationMenu);
		
		JMenuItem exitMnItem = new JMenuItem("Exit");
		exitMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("EXIT");
			}
		});
		applicationMenu.add(exitMnItem);
		
		JMenuItem homeMnItem = new JMenuItem("Home");
		applicationMenu.add(homeMnItem);
		
		JMenuItem mainMenuMnItem = new JMenuItem("Main Menu");
		applicationMenu.add(mainMenuMnItem);
		
		
		JMenu customersMenu = new JMenu("Customers");
		homeMenuBar.add(customersMenu);
		
		JMenuItem viewCustomersMnItem = new JMenuItem("View");
		viewCustomersMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//currentPanel.setVisible(false);
				setContentPane(customersPanel);
			}
		});
		customersMenu.add(viewCustomersMnItem);
		
		JMenuItem newCustomersMnItem = new JMenuItem("New");
		customersMenu.add(newCustomersMnItem);
		
		JMenu employeesMenu = new JMenu("Employees");
		homeMenuBar.add(employeesMenu);
		
		JMenuItem viewEmployeesMnItem = new JMenuItem("View");
		employeesMenu.add(viewEmployeesMnItem);
		
		JMenuItem newEmployeesMnItem = new JMenuItem("New");
		employeesMenu.add(newEmployeesMnItem);
		
		JMenu settingsMenu = new JMenu("Settings");
		homeMenuBar.add(settingsMenu);
		
		JMenu accountMenu = new JMenu("Account");
		homeMenuBar.add(accountMenu);
		
		JMenuItem changePasswordMnItem = new JMenuItem("Change password");
		accountMenu.add(changePasswordMnItem);
		
		JMenu adminMenu = new JMenu("Admin");
		homeMenuBar.add(adminMenu);
		
		JMenuItem newUserMnItem = new JMenuItem("New User");
		newUserMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Yooo");
			}
		});
		adminMenu.add(newUserMnItem);
		//homePanel.setLayout(null);		
		
		JLabel lblBusinessManagerApplication = new JLabel("Business Manager Application");
		lblBusinessManagerApplication.setBounds(5, 5, 671, 86);
		lblBusinessManagerApplication.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblBusinessManagerApplication.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblBusinessManagerApplication.setHorizontalAlignment(SwingConstants.CENTER);
		homePanel.add(lblBusinessManagerApplication);
		
		JLabel lblHome = new JLabel("Home");
		lblHome.setBounds(5, 102, 671, 75);
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		homePanel.add(lblHome);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(5, 167, 666, 285);
		textPane.setEditable(false);
		homePanel.add(textPane);
		
		//Customers Panel
		customersPanel = new JPanel();		
		customersPanel.setBackground(Color.WHITE);
		customersPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		customersPanel.setLayout(null);
		
		JButton newCustomerBtn = new JButton("New customer");
		newCustomerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				popupFrame.fillInForm("CUSTOMER",  new Customer());
				popupFrame.setLocation((getLocation().x + (popupFrame.getWidth()/4)), ((getLocation().y + (popupFrame.getHeight()/4))));
				popupFrame.setVisible(true);	
			}
		});
		newCustomerBtn.setLocation(10, 23);
		newCustomerBtn.setSize(118, 30);
		customersPanel.add(newCustomerBtn);
		
		customerSearchTextField = new JTextField();
		customerSearchTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(customerSearchTextField.getText().equals("Search...")) customerSearchTextField.setText("");
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(customerSearchTextField.getText().length() == 0) {
					customerSearchTextField.setText("Search...");
				}
			}
		});
		customerSearchTextField.setText("Search...");
		customerSearchTextField.setBounds(55, 72, 165, 20);
		customersPanel.add(customerSearchTextField);
		customerSearchTextField.setColumns(10);
		
		final JCheckBox customerNoChckbx = new JCheckBox("Customer No.");
		customerNoChckbx.setBackground(Color.WHITE);
		customerNoChckbx.setSelected(true);
		customerNoChckbx.setBounds(236, 71, 102, 23);
		customersPanel.add(customerNoChckbx);
		
		final JCheckBox customerNameChckbx = new JCheckBox("Name");
		customerNameChckbx.setBackground(Color.WHITE);
		customerNameChckbx.setSelected(true);
		customerNameChckbx.setBounds(346, 71, 102, 23);
		customersPanel.add(customerNameChckbx);
		
		//Popup Menu (When a user left clicks a row, a menu will pop up with a list of different options). 
		customerOptionsPopup.setPopupSize(60, 60);
		
		final JMenuItem popupCloseMnItem = new JMenuItem("Close");
		final JMenuItem popupViewMnItem = new JMenuItem("View");
		final JMenuItem popupEditMnItem = new JMenuItem("Edit");
		final JMenuItem popupDeleteMnItem = new JMenuItem("Delete");
		
		//The Close menu item.
		popupCloseMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("CLOSING");
				customerOptionsPopup.setVisible(false);
				popupIsShown = false;
			}
		});
		popupCloseMnItem.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) { }

			@Override
			public void mouseMoved(MouseEvent arg0) {				
				popupCloseMnItem.setForeground(Color.RED);
				popupViewMnItem.setForeground(Color.BLACK);
				popupEditMnItem.setForeground(Color.BLACK);
				popupDeleteMnItem.setForeground(Color.BLACK);
			}
			
		});
		customerOptionsPopup.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) { }
			
			@Override
			public void focusLost(FocusEvent arg0) {
				customerOptionsPopup.setVisible(false);
				//System.out.println("Focus Lost: customerOptionsPopup");
			}
		});
		popupCloseMnItem.setBounds(0, 0, 20, 20);
		customerOptionsPopup.add(popupCloseMnItem);
		
		//The View menu item.
		popupViewMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("VIEWING");
				int selectedRow = customersTable.getSelectedRow();
				
				if(selectedRow >= 0) { //Make sure the selected row is valid.
					popupFrame.fillInForm("CUSTOMER",  customerList.get(selectedRow));
					popupFrame.setLocation((getLocation().x + (popupFrame.getWidth()/4)), ((getLocation().y + (popupFrame.getHeight()/4))));
					popupFrame.setVisible(true);
					customerOptionsPopup.setVisible(false);
				}				
			}
		});
		popupViewMnItem.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) { }

			@Override
			public void mouseMoved(MouseEvent arg0) {		
				popupCloseMnItem.setForeground(Color.BLACK);
				popupViewMnItem.setForeground(Color.RED);
				popupEditMnItem.setForeground(Color.BLACK);
				popupDeleteMnItem.setForeground(Color.BLACK);
			}
			
		});
		popupViewMnItem.setBounds(0, 0, 20, 20);
		customerOptionsPopup.add(popupViewMnItem);
		
		//The Edit menu item.
		popupEditMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("EDITING");
			}
		});
		popupEditMnItem.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) { }

			@Override
			public void mouseMoved(MouseEvent arg0) {			
				popupCloseMnItem.setForeground(Color.BLACK);	
				popupViewMnItem.setForeground(Color.BLACK);	
				popupEditMnItem.setForeground(Color.RED);
				popupDeleteMnItem.setForeground(Color.BLACK);
			}
			
		});
		customerOptionsPopup.add(popupEditMnItem);
		
		//The Delete menu item.
		popupDeleteMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("DELETING");
			}
		});
		popupDeleteMnItem.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) { }

			@Override
			public void mouseMoved(MouseEvent arg0) {	
				popupCloseMnItem.setForeground(Color.BLACK);	
				popupEditMnItem.setForeground(Color.BLACK);
				popupViewMnItem.setForeground(Color.BLACK);				
				popupDeleteMnItem.setForeground(Color.RED);
			}
			
		});
		customerOptionsPopup.add(popupDeleteMnItem);
		
		
		//Customer Table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(10, 103, 661, 348);
		customersPanel.add(scrollPane);

		customersTable = new JTable();
		scrollPane.setViewportView(customersTable);

		customersTable.setAutoCreateRowSorter(true);
		customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customersTable.setColumnSelectionAllowed(true);		
		customersTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customersTable.setColumnSelectionAllowed(false);
		customersTable.setCellEditor(null);
		c_columnNames = new String[]{"Customer No.", "First Name", "Last Name"};
		customersTable.setModel(new DefaultTableModel(null, c_columnNames));		
		customersTable.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//If they left clicked.
				if(arg0.getButton() == MouseEvent.BUTTON1) {
					
					int selectedRow = customersTable.getSelectedRow(); //Get the id of the row the user selected. The return int value will be the value used in the array to retrieve information on the customer.					
					int columnCount = customersTable.getColumnCount(); //Get the total amount of columns, if it's only 1, then that means that they have no check boxes ticked and the notice was shown.
					
					if(selectedRow >= 0 && columnCount > 1) { //Only show the options if the person clicked on a valid row.
						//System.out.println("Left Clicked");		
						//System.out.println("Selected row " + selectedRow + ".");
						
						//Get the location of the mouse when the user clicked. Will be used to display the popup menu.
						int mouseX = MouseInfo.getPointerInfo().getLocation().x; 
						int mouseY = MouseInfo.getPointerInfo().getLocation().y;						
						
						customerOptionsPopup.setLocation(mouseX, mouseY); //Reposition the popup menu.
						customerOptionsPopup.setVisible(true); //Show the popup menu.
						customerOptionsPopup.requestFocus();
						popupIsShown = true;
					} else {
						if(customerOptionsPopup != null) {
							customerOptionsPopup.setVisible(false); //Hide the popup menu.
							popupIsShown = false;
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) { }

			@Override
			public void mouseExited(MouseEvent arg0) { }

			@Override
			public void mousePressed(MouseEvent arg0) { }

			@Override
			public void mouseReleased(MouseEvent arg0) { }
		});
		
		
		JButton refreshCustomersTableBtn = new JButton("");
		refreshCustomersTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "SELECT * FROM `customers` ";
				if(customerNoChckbx.isSelected() || customerNameChckbx.isSelected()) {
					query += "WHERE ";
					
					//Concat the query depending on if the search by employee number check box is ticked.
					if(customerNoChckbx.isSelected()) { 
						query += "`customer_number` LIKE '"+customerSearchTextField.getText()+"%' ";
					}
					//Concat the query depending on if the search by customer name check box is ticked.
					if(customerNameChckbx.isSelected()) {
						
						if(customerNoChckbx.isSelected()) query += "OR "; //Easy way to alter the query so the query will work properly. "OR " is needed for the query to actually work if both checkboxes are ticked.
						
						query += "`first_name` LIKE '"+customerSearchTextField.getText()+"%' OR `last_name` LIKE '"+customerSearchTextField.getText()+"%'";	
					}
					
					//If the search field is just left as "Search...", then select everything from the table.
					if(customerSearchTextField.getText().equals("Search...")) query = "SELECT * FROM `customers`";
					
					//Get the list of Customer objects generated from the query. Then get an array containing all the data from the objects so it can be displayed in the table.
					customerList = appManager.getTableRowData("CUSTOMERS", query);  
					
					
					refreshTable("CUSTOMERS", customerList);					
					
				} else {
					//If both of the checkboxes are unticked, then simply show them a row with the "Please tick atleast one of the checkboxes" notice.
					String[] temp = new String[]{""};
					DefaultTableModel model = new DefaultTableModel(null, temp) {
						@Override
						public boolean isCellEditable(int row, int column) {
							//all cells false
							return false;
						}
					};
					customersTable.setModel(model);
					model = (DefaultTableModel) customersTable.getModel();
					
					model.addRow(new String[]{"Please tick atleast one of the checkboxes."});
					
				}
			}
		});
		refreshCustomersTableBtn.setBorderPainted(false);
		refreshCustomersTableBtn.setBounds(10, 60, 40, 40);
		ImageIcon refreshImageIcon = new ImageIcon("lib/images/refresh_image_icon");
		refreshCustomersTableBtn.setIcon(refreshImageIcon);
		customersPanel.add(refreshCustomersTableBtn);	
	}
	
	/*
	private void createInternalFrames() {
		customerInternalFrame = new JFrame();
		JPanel panel = new JPanel();
		
		customerInternalFrame.setBounds(getLocation().x + (getWidth() / 4), getLocation().y + (getHeight() / 4), 450, 300);
		customerInternalFrame.setResizable(false);
		panel.setLayout(null);
		
		JLabel firstNameLbl = new JLabel("First Name:");
		firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLbl.setBounds(123, 37, 68, 14);
		panel.add(firstNameLbl);
		
		JLabel lastNameLbl = new JLabel("Last Name:");
		lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lastNameLbl.setBounds(123, 59, 68, 14);
		panel.add(lastNameLbl);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setEditable(false);
		firstNameTextField.setBounds(201, 34, 136, 20);
		panel.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setEditable(false);
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(201, 56, 136, 20);
		panel.add(lastNameTextField);
		
		JLabel customerNoLbl = new JLabel("Customer Number:");
		customerNoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		customerNoLbl.setBounds(88, 9, 103, 14);
		panel.add(customerNoLbl);
		
		customerNoTextField = new JTextField();
		customerNoTextField.setEditable(false);
		customerNoTextField.setColumns(10);
		customerNoTextField.setBounds(201, 6, 136, 20);
		panel.add(customerNoTextField);
		
		JLabel addressTwoLbl = new JLabel("Address Two:");
		addressTwoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressTwoLbl.setBounds(123, 108, 68, 14);
		panel.add(addressTwoLbl);
		
		JLabel addressOneLbl = new JLabel("Address One:");
		addressOneLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressOneLbl.setBounds(123, 86, 68, 14);
		panel.add(addressOneLbl);
		
		addressOneTextField = new JTextField();
		addressOneTextField.setEditable(false);
		addressOneTextField.setColumns(10);
		addressOneTextField.setBounds(201, 83, 136, 20);
		panel.add(addressOneTextField);
		
		addressTwoTextField = new JTextField();
		addressTwoTextField.setEditable(false);
		addressTwoTextField.setColumns(10);
		addressTwoTextField.setBounds(201, 105, 136, 20);
		panel.add(addressTwoTextField);
		
		JLabel addressCountryLbl = new JLabel("Country:");
		addressCountryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressCountryLbl.setBounds(123, 154, 68, 14);
		panel.add(addressCountryLbl);
		
		JLabel addressCityLbl = new JLabel("City:");
		addressCityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressCityLbl.setBounds(123, 132, 68, 14);
		panel.add(addressCityLbl);
		
		addressCityTextField = new JTextField();
		addressCityTextField.setEditable(false);
		addressCityTextField.setColumns(10);
		addressCityTextField.setBounds(201, 129, 136, 20);
		panel.add(addressCityTextField);
		
		addressCountryTextField = new JTextField();
		addressCountryTextField.setEditable(false);
		addressCountryTextField.setColumns(10);
		addressCountryTextField.setBounds(201, 151, 136, 20);
		panel.add(addressCountryTextField);
		
		JButton confirmBtn = new JButton("Edit");
		confirmBtn.setBounds(102, 237, 89, 23);
		panel.add(confirmBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(201, 237, 89, 23);
		panel.add(closeBtn);
		
		customerInternalFrame.setContentPane(panel);
	}
	*/
	
	
	//Sets the Login screens username and password.
	public void setLoginFields(String username, String password) {	
		loginUsernameTextField.setText(username);
		loginPasswordField.setText(password);		
	}
	
	
	public static final int ERROR_CONNECTION_FAILED = 1;
	public static final int ERROR_CONNECTION_DROPPED = 2;
	public static final int ERROR_LOGIN_FAILED = 3;  
	
	/*
	 * A way to trigger error messages within the application. 
	 * In most instances, this will just show an error message in a label.
	 */
	public void triggerError(int errorID, String message) {
		switch(errorID) {
			case ERROR_LOGIN_FAILED: { loginErrorLbl.setText(message); break; }
			
		}
	}
	
	//Display the content pane that contains all the elements for the main menu screen.
	void displayMainMenu() {		
		errorLbl.setText("");
		
		configurePanel.setVisible(false);
		loginPanel.setVisible(false);
		homePanel.setVisible(false);
		
		mainMenuPanel.setVisible(true);
		setContentPane(mainMenuPanel);
	}
	
	//Display the content pane that contains all the elements for the configuration screen.
	void displayConfiguration() {		
		mainMenuPanel.setVisible(false);
		loginPanel.setVisible(false);
		homePanel.setVisible(false);
		
		setContentPane(configurePanel);
		configurePanel.setVisible(true);
	}
	
	//Display the content pane that contains all the elements for the login screen.
	void displayLogin() {
		mainMenuPanel.setVisible(false);
		configurePanel.setVisible(false);
		homePanel.setVisible(false);
		
		setContentPane(loginPanel);
		loginPanel.setVisible(true);
	}
	
	void displayHome() {
		mainMenuPanel.setVisible(false);
		configurePanel.setVisible(false);
		loginPanel.setVisible(false);
		
		setContentPane(homePanel);
		homePanel.setVisible(true);
	}
	
	//Get the text the user has entered into the 'Search' part of the customers area.
	public String getCustomerTableSearchQuery() {
		return (customerSearchTextField.getText());
	}
	
	//Get the text the user has entered into the 'Search' part of the employees area.
	public String getEmployeeTableSearchQuery() { //#WIP
		//return (employeeSearchTextField.getText());
		return null;
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	/*
	 * This will internally handle the showing/hiding of different panels. It will also move the JMenuBar around so it fits on all panels where appropiate.
	 * 
	 */
	public void setContentPane(JPanel panel) {
		if(currentPanel != null) currentPanel.setVisible(false);
		super.setContentPane(panel);		
		
		if(currentPanel != null && currentPanel != mainMenuPanel && currentPanel != configurePanel && currentPanel != loginPanel) {
			currentPanel.remove(homeMenuBar);			
		}
		
		this.currentPanel = panel;
		if(currentPanel != null) this.currentPanel.setVisible(true);
		
		if(currentPanel != null && currentPanel != mainMenuPanel && currentPanel != configurePanel && currentPanel != loginPanel) {
			currentPanel.add(homeMenuBar);
		}
	}
	
	/*
	 * Refresh the data within the appropriate table with the information taken from the list of objects (Customers, Employees, Users).
	 */
	public void refreshTable(String tableName, ArrayList<Object> objectList) {
		String[] columnNames = null;
		Object[][] rowData = null;
		JTable table = null;
		
		switch(tableName) {
		
			case "CUSTOMERS": {
				columnNames = c_columnNames;				
				table = customersTable;
				
				rowData = appManager.getRowData("CUSTOMER", customerList);
				
				break;
			} 
			case "EMPLOYEES": {
				break;
			}
			case "USERS": {
				break;
			}
		}		 
		
		if(table != null) {
			//Create a new table model for the table.
			DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
				//Overriding the method so no cell is editable.
				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			table.setModel(tableModel); //Set the tables model.
		}
	}
	
}


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

/*
//Connect to the database with the given credentials.
boolean connectToDatabase(String url, String user, String password) {		
			
	//Add a thread here.
	try {			
		
		if(connection != null && connection.isValid(0)) { //If there's already a connection, close it before continuing.
			System.out.println("Already has a connection.");
			connection.close(); //Close the connection.
		}
		
		//Some debugging.
		//System.out.println("Attempting to connect to URL: [" + url + "] | USER: [" + user + "] | PASSWORD: [" + password + "]");
		
		connection = DriverManager.getConnection(url, user, password);
		
		statusResultLbl.setForeground(Color.GREEN);
		statusResultLbl.setText("Connected.");
		
		//Check if this is the first time the application has been used (no users within the database). 
		firstTimeUseCheck();
	} catch(Exception ex) {
		statusResultLbl.setText("Could not connect.");
		statusResultLbl.setForeground(Color.RED);	
		return false;
	}
	
	//String url = "jdbc:mysql://localhost:3306/employee_manager";
	//String user = "root";
	//String pw = "";
 
	return true;
}

//Attempts to log a user into the application through the Login screen.
void loginUser(String username, String password) {
	
	try {
		
		if(connection == null || !connection.isValid(0)) { //Check if the application is not connected to the database.
			loginErrorLbl.setText("Login failed, there is no connection to the database.");
		} else {
		
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `users` WHERE `username` = '"+username+"'");
			
			if(resultSet.next()) { //If their username exists in the database.
				//System.out.println("Username: [" + username + "] | Admin ID: [" + resultSet.getInt("adminId") + "]"); //Debugging
				String hashedPassword = SHA1(password);
				//System.out.println("PASSWORD: " + password + " | HASHED: " + hashedPassword);
				
				//Retrieve the password and compare the stored password with the password specified by the user.
				if(!hashedPassword.equals(resultSet.getString("password"))) { //If the passwords do not match.
					loginErrorLbl.setText("Login failed, you have entered an incorrect password.");
				} else {
					//Continue onto the next pane where most of the content will actually be.
					//Check if the user is an administrator.
				}
				
			} else {
				loginErrorLbl.setText("Login failed, the username you have specified does not exist.");
			}
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}			
}

//Checks whether or not the application has been used previously (there's no users created yet).
void firstTimeUseCheck() throws SQLException, NoSuchAlgorithmException {
	if(connection == null) return;
	
	Statement statement = connection.createStatement(); //Creates a statement.
	
	ResultSet resultSet = statement.executeQuery("SELECT * FROM `users`"); //Executes a SELECT query and store the result set.
	
	if(!resultSet.next()) { //There's no users, which means this is the first time the application has been used. 
		
		String newUserQuery = "INSERT INTO `users` (`username`, `password`, `admin`) VALUES ('admin', '" + SHA1("password") + "', '1')"; //Creating a string that will store the INSERT query for the new user.
		//System.out.println("QUERY: " + newUserQuery);
		statement.executeUpdate(newUserQuery); //Execute the query.
		
		loginUsernameTextField.setText("admin");
		loginPasswordField.setText("password");
	}
}

*/