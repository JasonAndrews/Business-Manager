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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JTabbedPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ApplicationFrame extends JFrame {

	private static final String CHECK_MARK_CHARACTER = "\u2713";
	private static final String X_MARK_CHARACTER = "\u2716";
	private static final Color COLOR_RED = new Color(255, 51, 51);
	private static final Color COLOR_GREEN = new Color(0, 118, 0);
	private static final int guiLocationSubtractor = 35; 
	
	private ApplicationFrame appFrame; //Self reference object - used in particular instances.
	private AppManager appManager;

	private String fileDirectory;
	private Properties properties;
	
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
	private JCheckBox configureSaveDetailsChckbx;
	private JCheckBox loginSaveDetailsChckbx;
	
	private JLabel mainMenuConnectionLb;
	private JLabel mainMenuConnectionLbStatus;
	private JLabel statusResultLbl;
	private JLabel errorLbl;
	private JLabel loginErrorLbl;
	
	private String[] c_columnNames;
	
	private static final Color BUTTON_BACKGROUND_COLOR = Color.BLACK;
	private static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
	private JTextField loginUsernameTextField;
	private JPasswordField loginPasswordField;
	private JTextField customerSearchTextField;
	
	private JMenuBar homeMenuBar;	
	
	private ArrayList<Entity> customerList; //A list of customer objects represented in the current customer table.
	
	private JButton viewCustomerBtn;
	private JButton editCustomerBtn;
	private JButton deleteCustomerBtn;
	
	/**
	 * @wbp.nonvisual location=82,359
	 */
	private final JPopupMenu customerOptionsPopup = new JPopupMenu();

	/**
	 * Create the frame.
	 */
	public ApplicationFrame(AppManager appManager) {	
		this.appFrame = this; 
		this.appManager = appManager;
		
		setTitle("Business Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowAdapter exitListener = new WindowAdapter() {

	            @Override
	            public void windowClosing(WindowEvent e) {
	            	//The application has been shut down.
	            	//Store the properties (such as the database information.)
	            	
	            	storeProperties();
	            }
		};
		addWindowListener(exitListener);
		setBounds(100, 100, 687, 490);
		setResizable(false);		
		getContentPane().setLayout(new CardLayout(0, 0));
		
		popupFrame = new PopupFrame(this, appManager);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				//System.out.println("FRAME - click.");
				int mouseX = MouseInfo.getPointerInfo().getLocation().x; 
				int mouseY = MouseInfo.getPointerInfo().getLocation().y;
				//System.out.println("X: " + mouseX + " | Y: " + mouseY);
				//customerOptionsPopup.setVisible(false);
				
				//Hide the 'View', 'Edit' and 'Delete' buttons when the user deselects a row.
				if((customersTable != null && customersTable.getSelectedRow() < 0)/* || ((employeesTable != null && employeesTable.getSelectedRow() > -1)) || ((usersTable != null && usersTable.getSelectedRow() > -1))*/) {
					customersTable.clearSelection();
					viewCustomerBtn.setVisible(false);
					editCustomerBtn.setVisible(false);
					deleteCustomerBtn.setVisible(false);
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
		
		createContentPanels(); //Create the different panels.
		//createInternalFrames();	
				
		//super.setContentPane(customersPanel);
		setContentPane(mainMenuPanel);
	}
	
	//Create the content panes that will be used. These are essentially the different screens of the application, such as the main menu and login screens.
	void createContentPanels() {			
		
		//Loading screen panel.
		ImageIcon loadingScreenImageIcon = new ImageIcon("lib/images/please_wait_loading_default_02.gif");
		loadingScreenImage = new JLabel();
		loadingScreenImage.setBounds(0, 0, getWidth(), (getHeight() - 100));
		loadingScreenImage.setHorizontalAlignment(SwingConstants.CENTER);
		loadingScreenImage.setIcon(loadingScreenImageIcon);
		
		//Main Menu Panel.
		mainMenuPanel = new JPanel();
		mainMenuPanel.setBackground(Color.WHITE);
		mainMenuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainMenuPanel.setLayout(null);		
		
		mainMenuPanel.add(loadingScreenImage);
		loadingScreenImage.setVisible(false);
		
		mainMenuConnectionLb = new JLabel("Connected: ");
		mainMenuConnectionLb.setHorizontalAlignment(SwingConstants.RIGHT);
		mainMenuConnectionLb.setBounds(500, 15, 70, 10);
		mainMenuPanel.add(mainMenuConnectionLb);
		
		
		
		mainMenuConnectionLbStatus = new JLabel(X_MARK_CHARACTER);
		mainMenuConnectionLbStatus.setForeground(COLOR_RED);
		mainMenuConnectionLbStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		mainMenuConnectionLbStatus.setBounds(515, 15, 70, 10);
		mainMenuPanel.add(mainMenuConnectionLbStatus);
		
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
		configurationBtn.setBounds(268, 169, 122, 33);
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
		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispatchEvent(new WindowEvent(appFrame, WindowEvent.WINDOW_CLOSING)); //Exit the application.				
			}
			
		});
		exitBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		exitBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		exitBtn.setBounds(268, 213, 122, 33);
		mainMenuPanel.add(exitBtn);
		
		/*
		JButton testConnectionBtn = new JButton("Test Connection");
		testConnectionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String url = urlTextField.getText();
				String user = userTextField.getText();
				String password = passwordTextField.getText();
				
				if(!appManager.testConnectionToDatabase(url, user, password)) { //If the connection failed, then give them the error on the main menu.
					errorLbl.setText("Could not connect, please enter the correct credentials on the Configuration form.");		
					statusResultLbl.setText("Could not connect.");
					statusResultLbl.setForeground(COLOR_RED);			
					
					mainMenuConnectionLbStatus.setText(X_MARK_CHARACTER);
					mainMenuConnectionLbStatus.setForeground(COLOR_RED);
					
				} else { 
					errorLbl.setText("");
					mainMenuConnectionLbStatus.setText(CHECK_MARK_CHARACTER);
					mainMenuConnectionLbStatus.setForeground(COLOR_GREEN);
					statusResultLbl.setForeground(COLOR_GREEN);
					statusResultLbl.setText("Connected.");
				}
			}
		});
		testConnectionBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		testConnectionBtn.setForeground(BUTTON_FOREGROUND_COLOR);		
		testConnectionBtn.setBounds(268, 257, 122, 33);
		mainMenuPanel.add(testConnectionBtn);
		*/
		errorLbl = new JLabel("");
		errorLbl.setForeground(COLOR_RED);
		errorLbl.setBounds(100, 301, 540, 14);
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
		statusResultLbl.setForeground(COLOR_RED);
		statusResultLbl.setBounds(324, 111, 124, 14);
		configurePanel.add(statusResultLbl);
		
		urlTextField = new JTextField("");
		urlTextField.setBounds(242, 142, 226, 20);
		configurePanel.add(urlTextField);
		urlTextField.setColumns(10);
		
		JLabel urlLbl = new JLabel("URL:");
		urlLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		urlLbl.setBounds(173, 145, 61, 14);
		configurePanel.add(urlLbl);
		
		userTextField = new JTextField("");
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
		
		configureSaveDetailsChckbx = new JCheckBox("Save details");
		configureSaveDetailsChckbx.setBackground(Color.WHITE);
		configureSaveDetailsChckbx.setBounds(375, 225, 97, 23);
		configureSaveDetailsChckbx.setSelected(true);
		configurePanel.add(configureSaveDetailsChckbx);
		
		JButton connectBtn = new JButton("Test Connection");
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				showLoadingScreen();
				
				SwingWorker<Integer, Integer> sw = null;
				
				sw = new SwingWorker<Integer, Integer>() {

					@Override
					protected Integer doInBackground() throws Exception {
						String url = urlTextField.getText();
						String user = userTextField.getText();
						String password = passwordTextField.getText();
						
						//Test the connection to the database using the specified credentials.
						if(appManager.testConnectionToDatabase(url, user, password)) {
							//The connection succeeded.
							statusResultLbl.setForeground(COLOR_GREEN);
							statusResultLbl.setText("Connected.");
							mainMenuConnectionLbStatus.setText(CHECK_MARK_CHARACTER);
							mainMenuConnectionLbStatus.setForeground(COLOR_GREEN);
							
							hideLoadingScreen();
						} else {
							//The connection failed.
							statusResultLbl.setText("Could not connect.");
							statusResultLbl.setForeground(COLOR_RED);	
							mainMenuConnectionLbStatus.setText(X_MARK_CHARACTER);
							mainMenuConnectionLbStatus.setForeground(COLOR_RED);
							hideLoadingScreen();
						}
						
						return null;
					}
					
				};
				sw.execute();
				
				//try connect to the database specified.				
				
				//String url = "jdbc:mysql://localhost:3306/employee_manager";
				//String user = "root";
				//String pw = "";
				
				
			}
		});
		connectBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		connectBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		connectBtn.setBounds(272, 250, 83, 23);
		configurePanel.add(connectBtn);
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				urlTextField.setText("");
				userTextField.setText("");
				passwordTextField.setText("");
			}
		});
		resetBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		resetBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		resetBtn.setBounds(365, 250, 83, 23);
		configurePanel.add(resetBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayMainMenu();
			}
		});
		backBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		backBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		backBtn.setBounds(312, 297, 89, 23);
		configurePanel.add(backBtn);
		
		JSeparator configurationUpperSeparator = new JSeparator();
		configurationUpperSeparator.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		configurationUpperSeparator.setBounds(242, 129, 226, 2);
		configurePanel.add(configurationUpperSeparator);
		
		JSeparator configurationLowerSeparator = new JSeparator();
		configurationLowerSeparator.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		configurationLowerSeparator.setBounds(242, 284, 226, 2);
		configurePanel.add(configurationLowerSeparator);
		
		
		//Setting up the Login content pane.
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPanel.setLayout(null);
		loginPanel.setBounds(0,0,0,0);
		
		
		
		loginErrorLbl = new JLabel("");
		loginErrorLbl.setForeground(COLOR_RED);
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
		
		JButton login_loginBtn = new JButton("Login");
		login_loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				
				
				showLoadingScreen();
				
				SwingWorker<Integer, Integer> sw = null;
				
				sw = new SwingWorker<Integer, Integer>() {

					@Override
					protected Integer doInBackground() {
						
						loginErrorLbl.setText(""); //Resetting the error message.
						
						//Test the connection to the database.
						if(appManager.testConnectionToDatabase(urlTextField.getText(), userTextField.getText(), passwordTextField.getText())) {							
							//There was a connection.
							appFrame.triggerSuccess(ApplicationFrame.SUCCESS_CONNECTION_PASSED); //DO SOMETHING TO SHOW THE CONNECTION ACTUALLY WORKED HERE!! LIKE to show the 'checkmark'.
							
							//Attempt to log the user in.
							char[] passwordArray = loginPasswordField.getPassword();
							String password = new String(passwordArray);
							String username = loginUsernameTextField.getText();							
												
							//Attempt to log the user in (check if his username and password are correct).
							if(appManager.loginUser(username, password)) {
								//The user successfully logged in.
								hideLoadingScreen();
								displayHome();
							} else {
								//The user failed to log in.
								hideLoadingScreen();
								triggerError(ApplicationFrame.ERROR_LOGIN_FAILED, "Login failed, you have entered an incorrect username and/or password.");
								
							}
							
						} else {
							//The connection failed.
							hideLoadingScreen();
							appFrame.triggerError(ApplicationFrame.ERROR_CONNECTION_FAILED, "The connection to the database failed!\n\nPlease ensure your configuration credentials are correct!");
							
						}
												
						return null;
					}
					
				};				
				sw.execute();
			}
		});
		login_loginBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		login_loginBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		login_loginBtn.setBounds(270, 240, 89, 23);		
		loginPanel.add(login_loginBtn);
		
		JButton login_clearBtn = new JButton("Clear");
		login_clearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loginUsernameTextField.setText("");
				loginPasswordField.setText("");
			}
			
		});
		login_clearBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		login_clearBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		login_clearBtn.setBounds(369, 240, 89, 23);
		loginPanel.add(login_clearBtn);
		
		loginSaveDetailsChckbx = new JCheckBox("Save details");
		loginSaveDetailsChckbx.setBackground(Color.WHITE);
		loginSaveDetailsChckbx.setBounds(375, 215, 97, 23);
		loginSaveDetailsChckbx.setSelected(true);
		loginPanel.add(loginSaveDetailsChckbx);
		
		JSeparator loginSeparator = new JSeparator();
		loginSeparator.setBackground(Color.BLACK);
		loginSeparator.setBounds(260, 275, 207, 2);
		loginPanel.add(loginSeparator);
		
		JButton login_backBtn = new JButton("Back");
		login_backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayMainMenu();				
			}
			
		});
		login_backBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		login_backBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		login_backBtn.setBounds(324, 288, 89, 23);		
		loginPanel.add(login_backBtn);
		
		
		
		
		
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
		
		
		
		JMenuItem homeMnItem = new JMenuItem("Home");
		applicationMenu.add(homeMnItem);
		
		JMenuItem mainMenuMnItem = new JMenuItem("Main Menu");
		applicationMenu.add(mainMenuMnItem);
		
		JMenuItem exitMnItem = new JMenuItem("Exit");
		exitMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("EXIT");
			}
		});
		applicationMenu.add(exitMnItem);
		
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
				popupFrame.fillInForm("CUSTOMERS",  new Customer());
				popupFrame.setLocation((getLocation().x + (popupFrame.getWidth()/4)), ((getLocation().y + (popupFrame.getHeight()/4)))); //Set the position of the PopupFrame.
				popupFrame.setVisible(true);	
			}
		});
		newCustomerBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		newCustomerBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		newCustomerBtn.setBounds(20, 425, 120, 30);
		customersPanel.add(newCustomerBtn);
		newCustomerBtn.setVisible(false); //Hide this button for now.
		
		viewCustomerBtn = new JButton("View");
		viewCustomerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//View information about the customer.
				int selectedRow = customersTable.getSelectedRow();
				
				if(selectedRow >= 0) { //Make sure the selected row is valid.
					popupFrame.fillInForm("CUSTOMERS",  customerList.get(selectedRow));
					popupFrame.setLocation((getLocation().x + (popupFrame.getWidth()/4)), ((getLocation().y + (popupFrame.getHeight()/4))));
					popupFrame.setVisible(true);
					customerOptionsPopup.setVisible(false);
				}	
			}
		});
		viewCustomerBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		viewCustomerBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		viewCustomerBtn.setBounds(150, 425, 120, 30);
		viewCustomerBtn.setVisible(false);
		customersPanel.add(viewCustomerBtn);
		
		editCustomerBtn = new JButton("Edit");
		editCustomerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Edit information about the customer (can also be done when viewing the customer, but this is direct.)
				
				int selectedRow = customersTable.getSelectedRow();
				
				if(selectedRow >= 0) { //Make sure the selected row is valid.
					popupFrame.fillInForm("CUSTOMERS",  customerList.get(selectedRow));
					popupFrame.setLocation((getLocation().x + (popupFrame.getWidth()/4)), ((getLocation().y + (popupFrame.getHeight()/4))));
					popupFrame.setVisible(true);
					popupFrame.setEditingForm("CUSTOMERS", true);
					popupFrame.setFormEditable("CUSTOMERS", true);
					customerOptionsPopup.setVisible(false);
				}	
			}
		});
		editCustomerBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		editCustomerBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		editCustomerBtn.setBounds(300, 425, 120, 30);
		editCustomerBtn.setVisible(false);
		customersPanel.add(editCustomerBtn);
		
		deleteCustomerBtn = new JButton("Delete");
		deleteCustomerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Delete the customer from the database.
			}
		});
		deleteCustomerBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		deleteCustomerBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		deleteCustomerBtn.setBounds(450, 425, 120, 30);
		deleteCustomerBtn.setVisible(false);
		customersPanel.add(deleteCustomerBtn);
		
		//The search text field the user can use to filter the customers shown.
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
		customerSearchTextField.setBounds(55, 72 - guiLocationSubtractor, 165, 20);
		customersPanel.add(customerSearchTextField);
		customerSearchTextField.setColumns(10);
		
		final JCheckBox customerNoChckbx = new JCheckBox("Customer No.");
		customerNoChckbx.setBackground(Color.WHITE);
		customerNoChckbx.setSelected(true);
		customerNoChckbx.setBounds(236, 71 - guiLocationSubtractor, 102, 23);
		customersPanel.add(customerNoChckbx);
		
		final JCheckBox customerNameChckbx = new JCheckBox("Name");
		customerNameChckbx.setBackground(Color.WHITE);
		customerNameChckbx.setSelected(true);
		customerNameChckbx.setBounds(346, 71 - guiLocationSubtractor, 102, 23);
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
				//popupIsShown = false;
			}
		});
		popupCloseMnItem.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) { }

			@Override
			public void mouseMoved(MouseEvent arg0) {				
				popupCloseMnItem.setForeground(COLOR_RED);
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
					popupFrame.fillInForm("CUSTOMERS",  customerList.get(selectedRow));
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
		scrollPane.setBounds(10, 103 - guiLocationSubtractor, 661, 348);
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
						
						viewCustomerBtn.setVisible(true);
						editCustomerBtn.setVisible(true);
						deleteCustomerBtn.setVisible(true);
						
						//customerOptionsPopup.setLocation(mouseX, mouseY); //Reposition the popup menu.
						//customerOptionsPopup.setVisible(true); //Show the popup menu.
						//customerOptionsPopup.requestFocus();
						
						
						//popupIsShown = true;
					} else {
						viewCustomerBtn.setVisible(false);
						editCustomerBtn.setVisible(false);
						deleteCustomerBtn.setVisible(false);
						if(customerOptionsPopup != null) {
							customerOptionsPopup.setVisible(false); //Hide the popup menu.
							//popupIsShown = false;
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
					
					
					refreshTable("CUSTOMERS");					
					
				} else {
					//If both of the checkboxes are unticked, then simply show them a row with the "Please tick atleast one of the checkboxes" notice.
					String[] temp = new String[]{""};
					DefaultTableModel model = new DefaultTableModel(null, temp) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

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
		refreshCustomersTableBtn.setBounds(10, 60 - guiLocationSubtractor, 40, 40);
		ImageIcon refreshImageIcon = new ImageIcon("lib/images/refresh_image_icon");
		refreshCustomersTableBtn.setIcon(refreshImageIcon);
		customersPanel.add(refreshCustomersTableBtn);	
	}	
	
	//Sets the Login screens username and password.
	public void login_setLoginFields(String username, String password) {	
		loginUsernameTextField.setText(username);
		loginPasswordField.setText(password);		
	}
	
	//Shows the loading screen on a particular panel.
	public void showLoadingScreen() {
		
		
		if(loadingScreenImage != null) {
			
			if(currentPanel != null) {
				Component[] comps = currentPanel.getComponents();
				for(Component c : comps) {
					c.setVisible(false);
				}
			}
			
			System.out.println("Showing loading screen");
			loadingScreenImage.setVisible(true);
		}
	}
	
	public void hideLoadingScreen() {
		if(loadingScreenImage != null) {
			
			if(currentPanel != null) {
				Component[] comps = currentPanel.getComponents();
				for(Component c : comps) {
					c.setVisible(true);
				}
			}
			
			System.out.println("Hiding loading screen");
			loadingScreenImage.setVisible(false);
		}
	}
	
	public static final int ERROR_CONNECTION_FAILED = 1;
	public static final int ERROR_CONNECTION_DROPPED = 2;
	public static final int ERROR_LOGIN_FAILED = 3; 
	
	public static final int SUCCESS_CONNECTION_PASSED = 1;
	
	private JOptionPane errorDialog;
	
	/*
	 * A way to trigger error messages within the application. 
	 * In most instances, this will just show an error message in a label or a popup dialog.
	 */
	public void triggerError(int errorID, String message) {
		switch(errorID) {
			case ERROR_CONNECTION_FAILED: {
				
				//Show them a dialog (JOptionPane) that waits until they click 'Ok' if the database connection fails.
				errorDialog = new JOptionPane();
				JOptionPane.showMessageDialog(
						this,
					    message,
					    "Connection error",
					    JOptionPane.ERROR_MESSAGE);
				errorDialog.setVisible(true);
				break; 
			}
			case ERROR_LOGIN_FAILED: { 
				loginErrorLbl.setText(message);
				break; 
			}			
		}
	}
	
	/*
	 * A way to trigger a success within the application. 
	 * An example would be to change the connection status if the connection to the database was successful. 
	 */
	public void triggerSuccess(int successID) {
		switch(successID) {
			case SUCCESS_CONNECTION_PASSED: {
				
				break; 
			}			
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
	
	//Return the currently loaded ArrayList of customers.
	public ArrayList<Entity> getCustomerList() {
		return customerList;
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
	
	private JLabel loadingScreenImage;
	
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
		
		if(currentPanel != null) {
			Component[] comps = currentPanel.getComponents();
			for(Component c : comps) {
				if(c == loadingScreenImage) { //If the loading screen label is on the current (will be the previous after reassignment) panel.
					
					currentPanel.remove(c); //Remove the component (loading screen label)
				}
			}
		}
		
		this.currentPanel = panel;
		if(currentPanel != null) {
			this.currentPanel.setVisible(true);
			currentPanel.add(loadingScreenImage);
			loadingScreenImage.setVisible(false);
		}
		
		if(currentPanel != null && currentPanel != mainMenuPanel && currentPanel != configurePanel && currentPanel != loginPanel) {
			currentPanel.add(homeMenuBar);
		}
		
		
	}
	
	/*
	 * Refresh the data within the appropriate table with the information taken from the list of objects (Customers, Employees, Users).
	 */
	public void refreshTable(String tableName) {
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
	
	/*
	 * Loads the properties file, if it exists.
	 * Check if the configuration file exists. If it does, load the properties. If it doesn't, create the directory (if needed) and file.
	 */
	public void loadProperties() {	
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File dirCheck = null;
		
		try {
			
			properties = new Properties();
			
			fileDirectory = (System.getProperty("user.home")+"\\Business Manager\\config.ini");			
			dirCheck = new File(fileDirectory); //Create a new file (to check if it exists.)
			
			if(dirCheck.exists()) { //The file exists.
				
				fis = new FileInputStream(fileDirectory); //Create a new FileInputStream for the configuration file.
				
				properties.load(fis); //Load the properties from the file.
				
				//Set the details on the configuration form to the details that were saved.
				urlTextField.setText(properties.getProperty("dbUrl"));
				userTextField.setText(properties.getProperty("dbUser"));
				passwordTextField.setText(properties.getProperty("dbPassword"));
				
				//Set the details on the login form to the details that were saved.
				loginUsernameTextField.setText(properties.getProperty("loginUser"));
				loginPasswordField.setText(properties.getProperty("loginPassword"));	
				
			} else { //The file doesn't exist.
				
				dirCheck = new File(System.getProperty("user.home")+"\\Business Manager"); //Create a new file to see if the directory exists.
				if(!dirCheck.exists()) dirCheck.mkdirs(); //Create the directory if it doesn't exist (the previous check is if the file exists.)
				
				fos = new FileOutputStream(fileDirectory); //Create a new file so we can store the properties in it.				
				
				properties.setProperty("dbUrl", "");
				properties.setProperty("dbUser", "");
				properties.setProperty("dbPassword", "");
				
				properties.setProperty("loginUser", "");
				properties.setProperty("loginPassword", "");
				
				properties.store(fos, "");
			}			
			
		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}		
	}
	
	/*
	 * Save all appropriate data into the config.ini file.
	 * Saved properties depend on what the user wants saved, for example, ticking the checkbox for the database configuration will save the credentials.
	 */
	public void storeProperties() {
		FileOutputStream fos = null;
		
		try {
			
			fos = new FileOutputStream(fileDirectory);
			
			//Check if the 'Save details' button is selected under the Configuration panel.
			if(configureSaveDetailsChckbx.isSelected()) {
				//if it is selected, save the details.	
				properties.setProperty("dbUrl", urlTextField.getText());
				properties.setProperty("dbUser", userTextField.getText());
				properties.setProperty("dbPassword", passwordTextField.getText());
			} else {
				//if it's not selected, do not save the details.
				properties.setProperty("dbUrl", "");
				properties.setProperty("dbUser", "");
				properties.setProperty("dbPassword", "");
			}
			
			if(loginSaveDetailsChckbx.isSelected()) {
				//if it is selected, save the details.
				properties.setProperty("loginUser", loginUsernameTextField.getText());
				properties.setProperty("loginPassword", new String(loginPasswordField.getPassword()));	
			} else {
				//if it's not selected, do not save the details.
				properties.setProperty("loginUser", "");
				properties.setProperty("loginPassword", "");
			}
			
			
			
			properties.store(fos, null);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(fos != null) {
				try { 
					fos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}		
	}
	
	
	
}