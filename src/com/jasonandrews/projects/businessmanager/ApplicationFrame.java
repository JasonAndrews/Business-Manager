/**
 * This is the core class behind the application's GUI. It internally handles almost all of the GUI elements (exceptions being the popup dialogs that display information or errors.)
 * Variables / Object Reference Variables have been prefixed so that it is more clear as to what panel they belong too.
 * For example, a JButton on the Login Form will be prefixed with 'login_' so it may appear as follows: login_loginBtn. 
 * Prefixes:
 * - Main Menu = mm_
 * - Configuration - config_
 * - Login - login_
 * - Home - home_
 * - Customer - cust_
 * - Employee - emp_
 * - User - user_
 * 
 * @author Jason Andrews
 * @version 0.30
 * @dependencies AppManager.java, PopupDialog.java
 */

package com.jasonandrews.projects.businessmanager;

import java.awt.MouseInfo;

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
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.CardLayout;

import javax.swing.JCheckBox;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ApplicationFrame extends JFrame {

	private static final String CHECK_MARK_CHARACTER = "\u2713";
	private static final String X_MARK_CHARACTER = "\u2716";
	private static final Color COLOR_RED = new Color(255, 51, 51);
	private static final Color COLOR_GREEN = new Color(0, 118, 0);
	//private static final int guiLocationSubtractor = 35; 
	
	private ApplicationFrame appFrame; //Self reference object variable - used in particular instances.
	private AppManager appManager;

	private String fileDirectory;
	private Properties properties;
	
	private PopupDialog popupDialog;	
	private JPanel currentPanel;
	private JTable currentlyDisplayedTable;
	
	//ORVs related to the main menu screen.
		private JPanel mm_contentPanel;
	
		//The main menus buttons.
		private JButton mm_loginBtn;
		private JButton mm_configurationBtn;
		private JButton mm_exitBtn;
	
	
	//ORVs related to the configuration screen.
		private JPanel config_contentPanel;
		private JTextField config_urlTextField;
		private JTextField config_userTextField;
		private JPasswordField config_passwordTextField;
		private JCheckBox config_saveDetailsChckbx;
		private JLabel config_statusResultLbl;
	
	//ORVs related to the login screen.
		private JPanel login_contentPanel;
	
		//The login buttons.
		private JButton login_loginBtn;
		private JButton login_clearBtn;
		private JButton login_backBtn;
	
		private JCheckBox login_saveDetailsChckbx;
		private JLabel login_errorLbl;
		private JTextField login_usernameTextField;
		private JPasswordField login_passwordField;
	
	//ORVs related to the home screen.
		private JPanel home_contentPanel;
		private JLabel home_welcomeUserLbl;
		
		//The menu bar that appears on the home panel (and other panels linked with the bar.)
		private JMenuBar menu_menuBar;
		private JMenu menu_usersMenu;
		
		
	//ORVs related to the customer screen.
		private JPanel cust_contentPanel;
		private JTable cust_customersTable;
	
		private String[] cust_tableColumnNames;
		private JTextField cust_searchTextField;
		
		private JCheckBox cust_customerNoChckbx;
		private JCheckBox cust_customerNameChckbx;
		
	
	//ORVs related to the employee menu.
		private JPanel emp_contentPanel;
		private JTable emp_employeesTable;
	
	
	//ORVs related to the user screen.
		private JPanel user_contentPanel;
		private JTable user_usersTable;
	
		private String[] user_tableColumnNames;
		private JTextField user_searchTextField;
	
	
	
	
	private static final Color BUTTON_BACKGROUND_COLOR = Color.WHITE;
	private static final Color BUTTON_FOREGROUND_COLOR = Color.BLACK;
	
	private ArrayList<Entity> customerList; //A list of Customer objects represented in the current customer table.
	private ArrayList<Entity> userList; //A list of User objects represented in the current user table.
	
	//private JButton cust_refreshTableBtn; //The 'Refresh table' button.
	
	private JLabel loadingScreenImage;
	
	private JButton viewTableRowBtn;
	private JButton editTableRowBtn;
	private JButton deleteTableRowBtn;

	
	/**
	 * 
	 * @param appManager - The AppManager object.
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
		setBounds(100, 100, 820, 500);
		setResizable(false);		
		getContentPane().setLayout(new CardLayout(0, 0));
		
		popupDialog = new PopupDialog(this, appManager);
		
		
		createContentPanels(); //Create the different panels.
		//createInternalFrames();	
				
		//super.setContentPane(customersPanel);
		setContentPane(mm_contentPanel);
	}
	
	/**
	 * Create the content panels that will be used within the application. 
	 * These are essentially the different screens of the application, such as the main menu and login screens.
	 */
	void createContentPanels() {			
		
		//Loading screen panel.
		ImageIcon loadingScreenImageIcon = new ImageIcon("lib/images/please_wait_loading_default_02.gif");
		loadingScreenImage = new JLabel();
		loadingScreenImage.setBounds(0, 0, getWidth(), (getHeight() - 100));
		loadingScreenImage.setHorizontalAlignment(SwingConstants.CENTER);
		loadingScreenImage.setIcon(loadingScreenImageIcon);
		
		//The main menu content panel.
		mm_contentPanel = new JPanel();
		mm_contentPanel.setBackground(Color.WHITE);
		mm_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mm_contentPanel.setLayout(null);		
		mm_contentPanel.add(loadingScreenImage);
		loadingScreenImage.setVisible(false);
		
		//The login button on the main menu.
		mm_loginBtn = new JButton("Login");
		mm_loginBtn.setToolTipText("Log into the application.");	
		mm_loginBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		mm_loginBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		mm_loginBtn.setBounds(268, 128, 122, 33);		
		mm_loginBtn.setBorder(null);
		mm_loginBtn.setFocusPainted(false);
		mm_loginBtn.setContentAreaFilled(false);
		mm_loginBtn.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {					
				display("LOGIN");
			}				
		});
		mm_loginBtn.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				mm_loginBtn.setForeground(Color.GRAY);
				mm_configurationBtn.setForeground(Color.BLACK);
				mm_exitBtn.setForeground(Color.BLACK);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				mm_loginBtn.setForeground(Color.BLACK);				
			}
			
		});
		mm_loginBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { }

			@Override
			public void mouseEntered(MouseEvent arg0) { 
				//When the mouse hovers over the button, set the text to gray.
				mm_loginBtn.setForeground(Color.GRAY);
				mm_configurationBtn.setForeground(Color.BLACK);
				mm_exitBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//When the mouse stops hovering over the button, set the text back to black.
				mm_loginBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mousePressed(MouseEvent arg0) { }

			@Override
			public void mouseReleased(MouseEvent arg0) { }
			
		});
		mm_contentPanel.add(mm_loginBtn);
		
		//The configuration button on the main menu.
		mm_configurationBtn = new JButton("Configuration");		
		mm_configurationBtn.setToolTipText("Configure the database settings.");		
		mm_configurationBtn.setBorder(null);
		mm_configurationBtn.setFocusPainted(false);
		mm_configurationBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		mm_configurationBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		mm_configurationBtn.setBounds(268, 169, 122, 33);
		mm_configurationBtn.setContentAreaFilled(false);
		mm_configurationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				display("CONFIGURATION");
				//When the Configure button is clicked/pressed.
			}
		});
		mm_configurationBtn.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				mm_loginBtn.setForeground(Color.BLACK);
				mm_configurationBtn.setForeground(Color.GRAY);
				mm_exitBtn.setForeground(Color.BLACK);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				mm_configurationBtn.setForeground(Color.BLACK);				
			}
			
		});
		mm_configurationBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { }

			@Override
			public void mouseEntered(MouseEvent arg0) { 
				//When the mouse hovers over the button, set the text to gray.
				mm_loginBtn.setForeground(Color.BLACK);
				mm_configurationBtn.setForeground(Color.GRAY);
				mm_exitBtn.setForeground(Color.BLACK);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//When the mouse stops hovering over the button, set the text back to black.
				mm_configurationBtn.setForeground(Color.BLACK);
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
		mm_contentPanel.add(mm_configurationBtn);
		
		//The exit button on the main menu.
		mm_exitBtn = new JButton("Exit");
		mm_exitBtn.setToolTipText("Exit the application");
		mm_exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispatchEvent(new WindowEvent(appFrame, WindowEvent.WINDOW_CLOSING)); //Exit the application.				
			}
			
		});
		mm_exitBtn.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				mm_loginBtn.setForeground(Color.BLACK);
				mm_configurationBtn.setForeground(Color.BLACK);
				mm_exitBtn.setForeground(Color.GRAY);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				mm_exitBtn.setForeground(Color.BLACK);				
			}
			
		});
		mm_exitBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { }

			@Override
			public void mouseEntered(MouseEvent arg0) { 
				//When the mouse hovers over the button, set the text to gray.
				mm_loginBtn.setForeground(Color.BLACK);
				mm_configurationBtn.setForeground(Color.BLACK);
				mm_exitBtn.setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				//When the mouse stops hovering over the button, set the text back to black.
				mm_exitBtn.setForeground(Color.BLACK);
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
		mm_exitBtn.setBackground(BUTTON_BACKGROUND_COLOR);
		mm_exitBtn.setForeground(BUTTON_FOREGROUND_COLOR);
		mm_exitBtn.setFocusPainted(false);
		mm_exitBtn.setBorder(null);
		mm_exitBtn.setBounds(268, 213, 122, 33);
		mm_exitBtn.setContentAreaFilled(false);
		mm_contentPanel.add(mm_exitBtn);
		
		
		//The configuration content panel.
		config_contentPanel = new JPanel();
		config_contentPanel.setBackground(Color.WHITE);		
		config_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		config_contentPanel.setLayout(null);
		config_contentPanel.setBounds(0,0,0,0);
		
		JLabel config_statusTextLbl = new JLabel("Status:");
		config_statusTextLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		config_statusTextLbl.setBounds(253, 111, 61, 14);
		config_contentPanel.add(config_statusTextLbl);
		
		config_statusResultLbl = new JLabel("Not connected.");
		config_statusResultLbl.setForeground(COLOR_RED);
		config_statusResultLbl.setBounds(324, 111, 124, 14);
		config_contentPanel.add(config_statusResultLbl);
		
		config_urlTextField = new JTextField("");
		config_urlTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				properties.setProperty("dbUrl", config_urlTextField.getText());
			}
		});
		config_urlTextField.setBounds(242, 142, 226, 20);
		config_urlTextField.setColumns(10);
		config_contentPanel.add(config_urlTextField);
		
		JLabel config_urlLbl = new JLabel("URL:");
		config_urlLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		config_urlLbl.setBounds(173, 145, 61, 14);
		config_contentPanel.add(config_urlLbl);
		
		config_userTextField = new JTextField("");
		config_userTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				//When the user types in the database username JTextField, update the properties object with the newest username.
				properties.setProperty("dbUser", config_userTextField.getText());
			}
		});
		config_userTextField.setColumns(10);
		config_userTextField.setBounds(242, 173, 226, 20);
		config_contentPanel.add(config_userTextField);
		
		JLabel config_userLbl = new JLabel("User:");
		config_userLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		config_userLbl.setBounds(173, 176, 61, 14);
		config_contentPanel.add(config_userLbl);
		
		JLabel config_pwLbl = new JLabel("Password:");
		config_pwLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		config_pwLbl.setBounds(151, 207, 83, 14);
		config_contentPanel.add(config_pwLbl);
		
		config_passwordTextField = new JPasswordField();
		config_passwordTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				//When the user types in the database password JPasswordField, update the properties object with the newest password.
				properties.setProperty("dbPassword", new String(config_passwordTextField.getPassword()));
			}
		});
		config_passwordTextField.setBounds(242, 204, 226, 20);
		config_contentPanel.add(config_passwordTextField);
		
		config_saveDetailsChckbx = new JCheckBox("Save details");
		config_saveDetailsChckbx.setBackground(Color.WHITE);
		config_saveDetailsChckbx.setBounds(375, 225, 97, 23);
		config_saveDetailsChckbx.setSelected(true);
		config_contentPanel.add(config_saveDetailsChckbx);
		
		JButton config_testConnectionBtn = new JButton("Test");
		config_testConnectionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Show the loading screen to the player so they know something is going on behind the scenes.
				showLoadingScreen();
				
				SwingWorker<Integer, Integer> sw = null;
				
				sw = new SwingWorker<Integer, Integer>() {

					@Override
					protected Integer doInBackground() throws Exception {
						String url = config_urlTextField.getText();
						String user = config_userTextField.getText();
						String password = new String(config_passwordTextField.getPassword());
						
						//Test the connection to the database using the specified credentials.
						if(appManager.testConnectionToDatabase(url, user, password)) {
							//The connection succeeded.
							config_statusResultLbl.setForeground(COLOR_GREEN);
							config_statusResultLbl.setText("Connected.");							
							hideLoadingScreen();
						} else {
							//The connection failed.
							config_statusResultLbl.setText("Connection failed.");
							config_statusResultLbl.setForeground(COLOR_RED);	
							hideLoadingScreen();
						}
						
						return null;
					}
					
				};
				sw.execute();
											
			}
		});
		config_testConnectionBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		config_testConnectionBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		config_testConnectionBtn.setBounds(272, 250, 83, 23);
		config_contentPanel.add(config_testConnectionBtn);
		
		JButton config_resetFormBtn = new JButton("Reset");
		config_resetFormBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Reset the configuration form.
				config_urlTextField.setText("");
				config_userTextField.setText("");
				config_passwordTextField.setText("");
			}
		});
		config_resetFormBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		config_resetFormBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		config_resetFormBtn.setBounds(365, 250, 83, 23);
		config_contentPanel.add(config_resetFormBtn);
		
		JButton config_backBtn = new JButton("Back");
		config_backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				display("MAIN MENU");
			}
		});
		config_backBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		config_backBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		config_backBtn.setBounds(312, 297, 89, 23);
		config_contentPanel.add(config_backBtn);
		
		JSeparator config_upperSeparator = new JSeparator();
		config_upperSeparator.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		config_upperSeparator.setBounds(242, 129, 226, 2);
		config_contentPanel.add(config_upperSeparator);
		
		JSeparator config_lowerSeparator = new JSeparator();
		config_lowerSeparator.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		config_lowerSeparator.setBounds(242, 284, 226, 2);
		config_contentPanel.add(config_lowerSeparator);
		
		
		//The login content panel.
		login_contentPanel = new JPanel();
		login_contentPanel.setBackground(Color.WHITE);
		login_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		login_contentPanel.setLayout(null);
		login_contentPanel.setBounds(0,0,0,0);	
		
		
		login_errorLbl = new JLabel("");
		login_errorLbl.setForeground(COLOR_RED);
		login_errorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		login_errorLbl.setBounds(125, 136, 420, 14);
		login_contentPanel.add(login_errorLbl);
		
		JLabel login_usernameLbl = new JLabel("Username:");
		login_usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		login_usernameLbl.setBounds(169, 160, 81, 22);		
		login_contentPanel.add(login_usernameLbl);
		
		login_usernameTextField = new JTextField();
		login_usernameTextField.setBounds(260, 161, 207, 20);
		login_usernameTextField.setColumns(10);
		login_usernameTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				login_usernameTextField.setBorder(new LineBorder(Color.BLACK));
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				login_usernameTextField.setBorder(new LineBorder(Color.GRAY));				
			}
			
		});
		login_contentPanel.add(login_usernameTextField);		
		
		JLabel login_passwordLbl = new JLabel("Password:");
		login_passwordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		login_passwordLbl.setBounds(169, 193, 81, 22);
		login_contentPanel.add(login_passwordLbl);
		
		login_passwordField = new JPasswordField();
		login_passwordField.setBounds(260, 194, 207, 21);
		login_passwordField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				login_passwordField.setBorder(new LineBorder(Color.BLACK));				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				login_passwordField.setBorder(new LineBorder(Color.GRAY));				
			}
			
		});
		login_contentPanel.add(login_passwordField);
		
		login_loginBtn = new CustomButton("Login");
		login_loginBtn.setBorder(null);
		login_loginBtn.setFocusPainted(false);
		login_loginBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				login_loginBtn.setForeground(Color.GRAY);
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				login_loginBtn.setForeground(Color.BLACK);				
			}

			@Override
			public void mousePressed(MouseEvent arg0) { }

			@Override
			public void mouseReleased(MouseEvent arg0) { }
			
		});
		login_loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {						
				showLoadingScreen();
				
				SwingWorker<Integer, Integer> sw = null;
				
				sw = new SwingWorker<Integer, Integer>() {

					@Override
					protected Integer doInBackground() {
						
						login_errorLbl.setText(""); //Resetting the error message.
						
						//Update the database properties as the user may have altered the credentials for the database.
						properties.setProperty("dbUrl", config_urlTextField.getText());
						properties.setProperty("dbUser", config_userTextField.getText());
						properties.setProperty("dbPassword", new String(config_passwordTextField.getPassword()));
						
						//Test the connection to the database.
						if(appManager.testConnectionToDatabase(properties.getProperty("dbUrl"), properties.getProperty("dbUser"), properties.getProperty("dbPassword"))) {							
							//There connection attempt was successful.							
							
							//Get the username and password specified by the user.
							char[] passwordArray = login_passwordField.getPassword();
							String password = new String(passwordArray);
							String username = login_usernameTextField.getText();
																		
							//Attempt to log the user in (check if the username and password are correct).
							if(appManager.loginUser(username, password)) {
								//The user successfully logged in.
								//hideLoadingScreen(); //Was causing a bug where once the user successfully logged in, a blank gray panel would only be shown.
								display("HOME");
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
						return 1;
					}
					
				};				
				sw.execute();
			}
		});
		login_loginBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		login_loginBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		login_loginBtn.setBounds(270, 240, 89, 23);		
		login_contentPanel.add(login_loginBtn);
		
		login_clearBtn = new JButton("Clear");
		login_clearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				login_usernameTextField.setText("");
				login_passwordField.setText("");
			}
			
		});
		login_clearBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		login_clearBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		login_clearBtn.setBounds(369, 240, 89, 23);
		login_contentPanel.add(login_clearBtn);
		
		login_saveDetailsChckbx = new JCheckBox("Save details");
		login_saveDetailsChckbx.setBackground(Color.WHITE);
		login_saveDetailsChckbx.setBounds(375, 215, 97, 23);
		login_saveDetailsChckbx.setSelected(true);
		login_contentPanel.add(login_saveDetailsChckbx);
		
		JSeparator login_separator = new JSeparator();
		login_separator.setBackground(Color.BLACK);
		login_separator.setBounds(260, 275, 207, 2);
		login_contentPanel.add(login_separator);
		
		login_backBtn = new JButton("Back");
		login_backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display("MAIN MENU");				
			}
			
		});
		login_backBtn.setBackground(ApplicationFrame.BUTTON_BACKGROUND_COLOR);
		login_backBtn.setForeground(ApplicationFrame.BUTTON_FOREGROUND_COLOR);
		login_backBtn.setBounds(324, 288, 89, 23);		
		login_contentPanel.add(login_backBtn);
		
		
		//The home content panel.
		home_contentPanel = new JPanel();
		home_contentPanel.setBackground(Color.WHITE);
		home_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		home_contentPanel.setLayout(null);
		
		menu_menuBar = new JMenuBar();
		menu_menuBar.setBounds(0, 0, this.getWidth(), 20);
		home_contentPanel.add(menu_menuBar);
		
		JMenu applicationMenu = new JMenu("Application");
		menu_menuBar.add(applicationMenu);
		
				
		JMenuItem menu_homeMnItem = new JMenuItem("Home");
		menu_homeMnItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display("HOME");
			}
			
		});
		applicationMenu.add(menu_homeMnItem);
		
		JMenuItem menu_logoutMnItem = new JMenuItem("Logout");
		menu_logoutMnItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display("MAIN MENU");			
			}	
			
		});
		applicationMenu.add(menu_logoutMnItem);
		
		JMenuItem menu_exitMnItem = new JMenuItem("Exit");
		menu_exitMnItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispatchEvent(new WindowEvent(appFrame, WindowEvent.WINDOW_CLOSING)); //Exit the application.				
			}
			
		});
		applicationMenu.add(menu_exitMnItem);		
		
		final JMenu menu_settingsMenu = new JMenu("Settings");
		
		menu_settingsMenu.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				menu_settingsMenu.setSelected(false);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) { 
				menu_settingsMenu.setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) { 
				menu_settingsMenu.setForeground(Color.BLACK);
			}

			@Override
			public void mousePressed(MouseEvent arg0) { }

			@Override
			public void mouseReleased(MouseEvent arg0) { }
			
		});
		menu_menuBar.add(menu_settingsMenu);
		
		JMenu customersMenu = new JMenu("Customers");
		menu_menuBar.add(customersMenu);
		
		JMenuItem menu_viewCustomersMnItem = new JMenuItem("View");
		menu_viewCustomersMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//currentPanel.setVisible(false);
				//setContentPane(cust_contentPanel);
				display("CUSTOMERS");
			}
		});
		customersMenu.add(menu_viewCustomersMnItem);
		
		JMenuItem menu_newCustomersMnItem = new JMenuItem("New");
		menu_newCustomersMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				popupDialog.fillInForm(Entity.EntityTypes.CUSTOMER,  new Customer());
				popupDialog.setLocation((getLocation().x + (popupDialog.getWidth()/4)), ((getLocation().y + (popupDialog.getHeight()/4)))); //Set the position of the PopupFrame.
				popupDialog.setVisible(true);	
			}
		});
		customersMenu.add(menu_newCustomersMnItem);
		
		JMenu menu_employeesMenu = new JMenu("Employees");
		menu_menuBar.add(menu_employeesMenu);
		
		JMenuItem menu_viewEmployeesMnItem = new JMenuItem("View");
		menu_viewEmployeesMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//display("EMPLOYEES");
			}
		});
		menu_employeesMenu.add(menu_viewEmployeesMnItem);
		
		JMenuItem menu_newEmployeesMnItem = new JMenuItem("New");
		menu_newEmployeesMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//popupDialog.fillInForm(Entity.EntityTypes.EMPLOYEE,  new Employee());
				//popupDialog.setLocation((getLocation().x + (popupDialog.getWidth()/4)), ((getLocation().y + (popupDialog.getHeight()/4)))); //Set the position of the PopupFrame.
				//popupDialog.setVisible(true);	
			}
		});
		menu_employeesMenu.add(menu_newEmployeesMnItem);
		
		
				
		menu_usersMenu = new JMenu("Users");
		menu_usersMenu.setVisible(false);
		menu_menuBar.add(menu_usersMenu);
		
		JMenuItem menu_viewUsersMnItem = new JMenuItem("View");
		menu_viewUsersMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				display("USERS");
			}
		});
		menu_usersMenu.add(menu_viewUsersMnItem);
		
		JMenuItem menu_newUsersMnItem = new JMenuItem("New");
		menu_newUsersMnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				popupDialog.fillInForm(Entity.EntityTypes.USER,  new User());
				popupDialog.setLocation((getLocation().x + (popupDialog.getWidth()/4)), ((getLocation().y + (popupDialog.getHeight()/4)))); //Set the position of the PopupFrame.
				popupDialog.setVisible(true);	
			}
		});
		menu_usersMenu.add(menu_newUsersMnItem);
		
		home_welcomeUserLbl = new JLabel("");
		home_welcomeUserLbl.setBounds(5, 5, 671, 86);
		home_welcomeUserLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		home_welcomeUserLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		home_welcomeUserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		home_contentPanel.add(home_welcomeUserLbl);
		
		JLabel home_informationLbl = new JLabel("Information");
		home_informationLbl.setBounds(5, 102, 671, 75);
		home_informationLbl.setHorizontalAlignment(SwingConstants.CENTER);
		home_contentPanel.add(home_informationLbl);
		
		JTextPane home_newsTextPane = new JTextPane();
		home_newsTextPane.setText("\u2022 This application is currently in the BETA stage of development.\n\u2022 Please provide feedback to the developer by emailing jasonandrews271192@gmail.com, thanks!");		
		home_newsTextPane.setBounds(5, 167, 666, 285);
		home_newsTextPane.setEditable(false);		
		home_contentPanel.add(home_newsTextPane);
		
		
		
		
		//The customers content panel (after selecting 'View' from the dropdown menu for Customers.)		
		cust_contentPanel = new JPanel();
		cust_contentPanel.setBackground(Color.WHITE);
		cust_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		cust_contentPanel.setLayout(null);
						
		//The title label for the customer panel.
		JLabel cust_titleLbl = new JLabel("Customer Management");
		cust_titleLbl.setBounds(300, 25, 250, 20);
		cust_titleLbl.setFont(new Font("Arial", Font.PLAIN, 15));
		cust_contentPanel.add(cust_titleLbl);
		
		//The search text field the user can use to filter the customers shown.
		cust_searchTextField = new JTextField();
		cust_searchTextField.setText("Search...");
		cust_searchTextField.setBounds(80, 50, 165, 20);
		cust_searchTextField.setColumns(10);
		cust_searchTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) { }

			@Override
			public void keyReleased(KeyEvent arg0) { }

			@Override
			public void keyTyped(KeyEvent arg0) {
				//Whenever the user types a key in the search textfield, refresh the table.
				refreshTable(Entity.EntityTypes.CUSTOMER);
			}
			
		});		
		//Add a focus adapter so we can set the textfield's text according to what method is called.
		cust_searchTextField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if(cust_searchTextField != null && cust_searchTextField.getText().equals("Search...")) cust_searchTextField.setText(""); //Set the textfield's text to an empty string.
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(cust_searchTextField != null && cust_searchTextField.getText().length() == 0) {
					cust_searchTextField.setText("Search..."); //If the user never typed anything into the textfield, set the text to the default message.
				}
			}
		});
		cust_contentPanel.add(cust_searchTextField);
		
				
		//The checkboxes on the customer panel.
		cust_customerNoChckbx = new JCheckBox("Customer No.");
		cust_customerNoChckbx.setBackground(Color.WHITE);
		cust_customerNoChckbx.setSelected(true);
		cust_customerNoChckbx.setBorderPaintedFlat(true);
		cust_customerNoChckbx.setBounds(250, 48, 105, 23);
		cust_contentPanel.add(cust_customerNoChckbx);
		
		cust_customerNameChckbx = new JCheckBox("Name");
		cust_customerNameChckbx.setBackground(Color.WHITE);
		cust_customerNameChckbx.setSelected(true);
		cust_customerNameChckbx.setBounds(360, 48, 100, 23);
		cust_contentPanel.add(cust_customerNameChckbx);
		
		
		//Customer Table
		final JScrollPane cust_scrollPane = new JScrollPane();
		cust_scrollPane.setBackground(Color.WHITE);
		cust_scrollPane.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//If they left clicked.
				if(arg0.getButton() == MouseEvent.BUTTON1) {
					viewTableRowBtn.setVisible(false);
					editTableRowBtn.setVisible(false);
					deleteTableRowBtn.setVisible(false);
					
					cust_customersTable.clearSelection();
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
		cust_scrollPane.setBounds(80, 80, 660, 348);
		cust_contentPanel.add(cust_scrollPane);

		cust_customersTable = new JTable();
		cust_scrollPane.setViewportView(cust_customersTable);

		cust_customersTable.setAutoCreateRowSorter(true);
		cust_customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cust_customersTable.setColumnSelectionAllowed(true);		
		cust_customersTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		cust_customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cust_customersTable.setColumnSelectionAllowed(false);
		cust_customersTable.setCellEditor(null);
		cust_tableColumnNames = new String[]{"Customer No.", "First Name", "Last Name"};
		cust_customersTable.setModel(new DefaultTableModel(null, cust_tableColumnNames));		
		cust_customersTable.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				//If they left clicked.
				if(arg0.getButton() == MouseEvent.BUTTON1) {
									
					int selectedRow = cust_customersTable.getSelectedRow(); //Get the id of the row the user selected. The return int value will be the value used in the array to retrieve information on the customer.					
					int columnCount = cust_customersTable.getColumnCount(); //Get the total amount of columns, if it's only 1, then that means that they have no check boxes ticked and the notice was shown.
					
					if(selectedRow >= 0 && columnCount > 1) { //Only show the options if the person clicked on a valid row.

						//Get the Y position of the mouse.
						int mouseY = arg0.getPoint().y;

						//Reposition the 'View', 'Edit' & 'Delete' icons (the icons that appear to the left of the tables).
						viewTableRowBtn.setLocation(viewTableRowBtn.getLocation().x, (mouseY + cust_scrollPane.getLocation().y + 10));
						editTableRowBtn.setLocation(editTableRowBtn.getLocation().x, (mouseY + cust_scrollPane.getLocation().y + 10));
						deleteTableRowBtn.setLocation(deleteTableRowBtn.getLocation().x, (mouseY + cust_scrollPane.getLocation().y + 6));
						
						//Set the icons visible.
						viewTableRowBtn.setVisible(true);
						editTableRowBtn.setVisible(true);
						deleteTableRowBtn.setVisible(true);
						
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
		
		/*
		cust_refreshTableBtn = new JButton();		
		cust_refreshTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable(Entity.EntityTypes.CUSTOMER);				
			}
		});
		cust_refreshTableBtn.setBorderPainted(false);
		cust_refreshTableBtn.setBackground(Color.WHITE);
		cust_refreshTableBtn.setBounds(255, 25, 30, 30);
		//ImageIcon cust_refreshImageIcon = new ImageIcon("lib/images/refresh_image_icon");
		ImageIcon cust_refreshImageIcon = new ImageIcon("lib/images/table_search_button.png");
		cust_refreshTableBtn.setIcon(cust_refreshImageIcon);
		cust_contentPanel.add(cust_refreshTableBtn);	
		*/
		
		//The users (after selecting 'View' from the dropdown menu for Users.)		
		user_contentPanel = new JPanel();
		user_contentPanel.setBackground(Color.WHITE);
		user_contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		user_contentPanel.setLayout(null);
						
		//The title label for the customer panel.
		JLabel user_titleLbl = new JLabel("User Management");
		user_titleLbl.setBounds(300, 25, 250, 20);
		user_titleLbl.setFont(new Font("Arial", Font.PLAIN, 15));
		user_contentPanel.add(user_titleLbl);
		
		//The search text field the user can use to filter the customers shown.
		user_searchTextField = new JTextField();
		user_searchTextField.setText("Search...");
		user_searchTextField.setBounds(80, 50, 165, 20);
		user_searchTextField.setColumns(10);
		user_searchTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) { }

			@Override
			public void keyReleased(KeyEvent arg0) { }

			@Override
			public void keyTyped(KeyEvent arg0) {
				//Whenever the user types a key in the search textfield, refresh the table.
				refreshTable(Entity.EntityTypes.USER);
			}
			
		});		
		//Add a focus adapter so we can set the textfield's text according to what method is called.
		user_searchTextField.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if(user_searchTextField != null && user_searchTextField.getText().equals("Search...")) user_searchTextField.setText(""); //Set the textfield's text to an empty string.
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(user_searchTextField != null && user_searchTextField.getText().length() == 0) {
					user_searchTextField.setText("Search..."); //If the user never typed anything into the textfield, set the text to the default message.
				}
			}
		});
		user_contentPanel.add(user_searchTextField);
		
				
		/*The checkboxes on the customer panel.
		user_customerNoChckbx = new JCheckBox("Customer No.");
		cust_customerNoChckbx.setBackground(Color.WHITE);
		cust_customerNoChckbx.setSelected(true);
		cust_customerNoChckbx.setBorderPaintedFlat(true);
		cust_customerNoChckbx.setBounds(250, 48, 105, 23);
		user_contentPanel.add(cust_customerNoChckbx);
		
		cust_customerNameChckbx = new JCheckBox("Name");
		cust_customerNameChckbx.setBackground(Color.WHITE);
		cust_customerNameChckbx.setSelected(true);
		cust_customerNameChckbx.setBounds(360, 48, 100, 23);
		user_contentPanel.add(cust_customerNameChckbx);
		*/
		
		//Customer Table
		final JScrollPane user_scrollPane = new JScrollPane();
		user_scrollPane.setBackground(Color.WHITE);
		user_scrollPane.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//If they left clicked.
				if(arg0.getButton() == MouseEvent.BUTTON1) {
					viewTableRowBtn.setVisible(false);
					editTableRowBtn.setVisible(false);
					deleteTableRowBtn.setVisible(false);
					
					user_usersTable.clearSelection();
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
		user_scrollPane.setBounds(80, 80, 660, 348);
		user_contentPanel.add(user_scrollPane);

		user_usersTable = new JTable();
		user_scrollPane.setViewportView(user_usersTable);

		user_usersTable.setAutoCreateRowSorter(true);
		user_usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		user_usersTable.setColumnSelectionAllowed(true);		
		user_usersTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		user_usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		user_usersTable.setColumnSelectionAllowed(false);
		user_usersTable.setCellEditor(null);
		user_tableColumnNames = new String[]{"User No.", "Username", "Administrator", "Last logon"};
		user_usersTable.setModel(new DefaultTableModel(null, user_tableColumnNames));		
		user_usersTable.addMouseListener(new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				//If they left clicked.
				if(arg0.getButton() == MouseEvent.BUTTON1) {
									
					int selectedRow = user_usersTable.getSelectedRow(); //Get the id of the row the user selected. The return int value will be the value used in the array to retrieve information on the customer.					
					int columnCount = user_usersTable.getColumnCount(); //Get the total amount of columns, if it's only 1, then that means that they have no check boxes ticked and the notice was shown.
					
					if(selectedRow >= 0 && columnCount > 1) { //Only show the options if the person clicked on a valid row.

						//Get the Y position of the mouse.
						int mouseY = arg0.getPoint().y;

						//Reposition the 'View', 'Edit' & 'Delete' icons (the icons that appear to the left of the tables).
						viewTableRowBtn.setLocation(viewTableRowBtn.getLocation().x, (mouseY + cust_scrollPane.getLocation().y + 10));
						editTableRowBtn.setLocation(editTableRowBtn.getLocation().x, (mouseY + cust_scrollPane.getLocation().y + 10));
						deleteTableRowBtn.setLocation(deleteTableRowBtn.getLocation().x, (mouseY + cust_scrollPane.getLocation().y + 6));
						
						//Set the icons visible.
						viewTableRowBtn.setVisible(true);
						editTableRowBtn.setVisible(true);
						deleteTableRowBtn.setVisible(true);
						
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
		
		
		//The side icons (View, Edit and Delete) that appear to the left of the table when a user clicks on a valid row in the table.
		//The 'View' side icon.
		viewTableRowBtn = new JButton("");
		viewTableRowBtn.setBounds(55, 100, 25, 20);
		viewTableRowBtn.setBackground(Color.WHITE);
		viewTableRowBtn.setFocusPainted(false);
		viewTableRowBtn.setBorder(null);
		viewTableRowBtn.setIcon(new ImageIcon("lib/images/view_icon_black_20x20.png"));
		viewTableRowBtn.setRolloverIcon(new ImageIcon("lib/images/view_icon_grey_20x20.png"));
		viewTableRowBtn.setContentAreaFilled(false);
		viewTableRowBtn.setVisible(false);
		viewTableRowBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//View information about the customer.
				
				if(null == currentlyDisplayedTable) return;
								
				int selectedRow = currentlyDisplayedTable.getSelectedRow();
				
				if(selectedRow >= 0) { //Make sure the selected row is valid.
					
					if(currentlyDisplayedTable == cust_customersTable) {
						popupDialog.fillInForm(Entity.EntityTypes.CUSTOMER, customerList.get(selectedRow));
					} else if(currentlyDisplayedTable == emp_employeesTable) {
						//popupDialog.fillInForm(Entity.EntityTypes.EMPLOYEE, employeeList.get(selectedRow));
					} else if(currentlyDisplayedTable == user_usersTable) {
						popupDialog.fillInForm(Entity.EntityTypes.USER, userList.get(selectedRow));
					}
					
					popupDialog.setLocation((getLocation().x + (popupDialog.getWidth()/4)), ((getLocation().y + (popupDialog.getHeight()/4))));
					popupDialog.setVisible(true);
				}
				
			}
			
		});
		cust_contentPanel.add(viewTableRowBtn);
				
		//The 'Edit' side icon.
		editTableRowBtn = new JButton("");
		editTableRowBtn.setBounds(30, 100, 20, 20);
		editTableRowBtn.setBackground(Color.WHITE);
		editTableRowBtn.setBorder(null);
		editTableRowBtn.setIcon(new ImageIcon("lib/images/edit_icon_black_20x20.png"));
		editTableRowBtn.setRolloverIcon(new ImageIcon("lib/images/edit_icon_grey_20x20.png"));
		editTableRowBtn.setContentAreaFilled(false);
		editTableRowBtn.setVisible(false);
		editTableRowBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Edit information about the customer (can also be done when viewing the customer, but this is direct.)
				
				if(null == currentlyDisplayedTable) return;
				
				System.out.println("DEBUG 001");
				
				int selectedRow = currentlyDisplayedTable.getSelectedRow();
				
				if(selectedRow >= 0) { //Make sure the selected row is valid.
					
					if(currentlyDisplayedTable == cust_customersTable) {
						System.out.println("DEBUG 002");
						popupDialog.fillInForm(Entity.EntityTypes.CUSTOMER, customerList.get(selectedRow));
						popupDialog.setEditingForm(Entity.EntityTypes.CUSTOMER, true);
						popupDialog.setFormEditable(Entity.EntityTypes.CUSTOMER, true);
					} else if(currentlyDisplayedTable == emp_employeesTable) {
						//popupDialog.fillInForm(Entity.EntityTypes.EMPLOYEE, employeeList.get(selectedRow));
						//popupDialog.setEditingForm(Entity.EntityTypes.EMPLOYEE, true);
						//popupDialog.setFormEditable(Entity.EntityTypes.EMPLOYEE, true);
					} else if(currentlyDisplayedTable == user_usersTable) {
						System.out.println("DEBUG 003");
						popupDialog.fillInForm(Entity.EntityTypes.USER, userList.get(selectedRow));
						popupDialog.setEditingForm(Entity.EntityTypes.USER, true);
						popupDialog.setFormEditable(Entity.EntityTypes.USER, true);
					}			
					popupDialog.setLocation((getLocation().x + (popupDialog.getWidth()/4)), ((getLocation().y + (popupDialog.getHeight()/4))));
					popupDialog.setVisible(true);
				}	
				
				
				
			}
			
		});
		cust_contentPanel.add(editTableRowBtn);
		
		//The 'Delete' side icon.
		deleteTableRowBtn = new JButton("");
		deleteTableRowBtn.setBounds(5, 98, 20, 25);
		deleteTableRowBtn.setBackground(Color.WHITE);
		deleteTableRowBtn.setBorder(null);
		deleteTableRowBtn.setIcon(new ImageIcon("lib/images/delete_icon_black_20x20.png"));
		deleteTableRowBtn.setRolloverIcon(new ImageIcon("lib/images/delete_icon_grey_20x20.png"));
		deleteTableRowBtn.setContentAreaFilled(false);
		deleteTableRowBtn.setVisible(false);
		deleteTableRowBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Delete the customer from the database.
				//Make a popup for a confirmation.
				
				int selectedRow = cust_customersTable.getSelectedRow();
				
				if(selectedRow >= 0) {
					
					Customer customer = (Customer) customerList.get(selectedRow);
					
					if(null != customer) {
												
						String fullName = (customer.getFirstName() + " " + customer.getLastName());
						int selectedOption = 
								JOptionPane.showConfirmDialog(null, 
										"You are about to delete the following customer from the system.\n\nCustomer Number: " + customer.getCustomerNumber() + ".\nName: " + fullName + ".\n\nAre you sure you wish to continue?", 
										"Delete customer - " + fullName + "?", 
										JOptionPane.YES_NO_OPTION);
						
						//If they clicked the 'Yes' button.
						if(selectedOption == JOptionPane.OK_OPTION) {
							appManager.deleteEntity(Entity.EntityTypes.CUSTOMER, customer);
						}						
					}
					
				}				
			}
			
		});
		cust_contentPanel.add(deleteTableRowBtn);		
	}	
	
	/**
	 * Sets the Login screen's username and password fields.
	 * @param username - Set the text in the usernames JTextField.
	 * @param password - Set the text in the passwords JPasswordField.
	 */
	public void login_setLoginFields(String username, String password) {	
		login_usernameTextField.setText(username);
		login_passwordField.setText(password);		
	}
	
	/**
	 * Show the loading screen on a particular panel.
	 */
	public void showLoadingScreen() {	
		//System.out.println("Attempting to show the loading screen");
		if(loadingScreenImage != null) {
			//System.out.println("Attempting to show the loading screen 02");
			if(currentPanel != null) {
				//System.out.println("Attempting to show the loading screen 03");
				Component[] comps = currentPanel.getComponents();
				if(currentPanel != cust_contentPanel/*May need to add the other table content panels here*/) { //A hacky way of dealing with an issue that allows certain GUI elements to be visible when the loading screen is on. Temporary fix.
					for(Component c : comps) {
						c.setVisible(false);
					}
				}
			}
			
			//System.out.println("Showing loading screen");
			loadingScreenImage.setVisible(true);
		}
	}
	
	/**
	 * Hide the loading screen.
	 */
	public void hideLoadingScreen() {
		//System.out.println("Attempting to hide the loading screen");
		if(loadingScreenImage != null) {
			//System.out.println("Attempting to hide the loading screen part 2");
			if(currentPanel != null) {
				//System.out.println("Attempting to hide the loading screen 3");
				Component[] comps = currentPanel.getComponents();
				if(currentPanel != cust_contentPanel) { //A hacky way of dealing with an issue that allows certain GUI elements to be visible when the loading screen is on. Temporary fix.
					for(Component c : comps) {
						c.setVisible(true);
					}
				}
			}
			
			//System.out.println("Hiding loading screen");
			loadingScreenImage.setVisible(false);
		}
	}
	
	public static final int ERROR_CONNECTION_FAILED = 1;
	public static final int ERROR_CONNECTION_DROPPED = 2;
	public static final int ERROR_LOGIN_FAILED = 3; 
	
	public static final int SUCCESS_CONNECTION_PASSED = 1;
	
	private JOptionPane errorDialog;
	
	/**
	 * A way to trigger error messages within the application. 
	 * In most instances, this will just show an error message in a label or a popup dialog.
	 * @param errorID - The Error ID for the error.
	 * @param message - The message that should be displayed to the user.
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
				login_errorLbl.setText(message);
				break; 
			}			
		}
	}
	
	/**
	 * A way to trigger a success within the application. 
	 * An example would be to change the connection status if the connection to the database was successful. 
	 * Currently unused.
	 */
	public void triggerSuccess(int successID) {
		switch(successID) {
			case SUCCESS_CONNECTION_PASSED: {
				
				break; 
			}			
		}
	}
	
	/**
	 * Display a JPanel depending on the given name.
	 * To display the main menu, display("MAIN MENU") would work.
	 * @param panelName - The name of the panel to display. Example - "HOME" would be used to display the home panel.
	 */
	private void display(String panelName) {
		JPanel panelToDisplay = null;
		switch(panelName) {		
			case "MAIN MENU": {

				appManager.clearUsers(); //Clear all the User objects currently loaded.
				appManager.setLoggedInUser(null); //Reset the 'Currently logged in user'.
				
				panelToDisplay = mm_contentPanel;
				break;
			}
			case "CONFIGURATION": {

				panelToDisplay = config_contentPanel;
				break; 
			}
			case "LOGIN": {

				panelToDisplay = login_contentPanel;
				break;
			}	
			case "HOME": {

				String username = ""; 
				User loggedInUser = appManager.getLoggedInUser();
				
				if(null != loggedInUser) {
					username = appManager.getLoggedInUser().getUsername();
					
					//If the user is an administrator, then show the 'Users' menu.
					if(loggedInUser.isAdmin()) {
						menu_usersMenu.setVisible(true);
					} else {
						//The user is not an administrator so hide the Users menu.
						menu_usersMenu.setVisible(false);
					}
				}
				
				
				if(username.length() > 0) {
					home_welcomeUserLbl.setText("Welcome, " + username + "!");
				} else {
					home_welcomeUserLbl.setText("Welcome!");
				}
				
				panelToDisplay = home_contentPanel;
				break;
			}
			case "CUSTOMERS": {
				currentlyDisplayedTable = cust_customersTable;		
				panelToDisplay = cust_contentPanel;
				break;
			}
			case "EMPLOYEES": {
				//currentlyDisplayedTable = emp_employeesTable;
				//panelToDisplay = emp_contentPanel;
				break;
			}
			case "USERS": {
				currentlyDisplayedTable = user_usersTable;
				panelToDisplay = user_contentPanel;
			}
		}

		if(panelToDisplay != null) setContentPane(panelToDisplay); 
	}
	
	/*
	//Display the content pane that contains all the elements for the main menu screen.
	void displayMainMenu() {		
		//mm_errorLbl.setText("");
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
	*/
	
	/**
	 * Return the currently loaded ArrayList of customers.
	 * @return Returns the ArrayList of Customer objects. 
	 */
	public ArrayList<Entity> getCustomerList() {
		return customerList;
	}
	
	/**
	 * Get the text the user has entered into the 'Search' part of the customers area.
	 * @return Returns the text within the table's appropriate search field. 
	 */
	public String getCustomerTableSearchQuery() {
		return (cust_searchTextField.getText());
	}
	
	/**
	 * Get the text the user has entered into the 'Search' part of the employees area.
	 * @return
	 */
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
	
	
	
	/**
	 * This will internally handle the showing/hiding of different panels. It will also move the JMenuBar around so it fits on all panels where appropiate.
	 * @param panel - The JPanel to set as the JFrames content panel.
	 */
	public void setContentPane(JPanel panel) {
		if(currentPanel != null) currentPanel.setVisible(false);
		super.setContentPane(panel);		
		
		if(null != viewTableRowBtn) viewTableRowBtn.setVisible(false);
		if(null != editTableRowBtn) editTableRowBtn.setVisible(false);
		if(null != deleteTableRowBtn) deleteTableRowBtn.setVisible(false);
		
		
		if(currentPanel != null && currentPanel != mm_contentPanel && currentPanel != config_contentPanel && currentPanel != login_contentPanel) {
			currentPanel.remove(menu_menuBar);			
		}
		
		if(currentPanel != null && (currentPanel == cust_contentPanel || currentPanel == emp_contentPanel || currentPanel == user_contentPanel)) {
			currentPanel.remove(viewTableRowBtn);
			currentPanel.remove(editTableRowBtn);
			currentPanel.remove(deleteTableRowBtn);
			
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
			currentPanel.add(loadingScreenImage, 1);
			//loadingScreenImage.setVisible(false);
			hideLoadingScreen();
		}
		
		if(currentPanel != null && currentPanel != mm_contentPanel && currentPanel != config_contentPanel && currentPanel != login_contentPanel) {
			currentPanel.add(menu_menuBar);
		}
		
		if(currentPanel != null && (currentPanel == cust_contentPanel || currentPanel == emp_contentPanel || currentPanel == user_contentPanel)) {
			currentPanel.add(viewTableRowBtn);
			currentPanel.add(editTableRowBtn);
			currentPanel.add(deleteTableRowBtn);
			
		}
		
	}
	
	/**
	 * Resets the elements of a JPanel. Some elements are meant to be hidden by default, this method does just that. 
	 * @param panelName - The name of the panel to reset the GUI elements of.
	 */
	/*
	private void resetPanelElements(String panelName) {
		switch(panelName) {
			case CUSTOMER: {
				cust_viewCustomerBtn.setVisible(false);
				cust_deleteCustomerBtn.setVisible(false);
				cust_editCustomerBtn.setVisible(false);
			}
		}
	}
	*/
	
	/**
	 * Get a prepared MySQL query for the appropriate table, specified by the parameter entityType.
	 * @param entityType - The Entity.EntityTypes of the table the query is for. For example - Entity.EntityTypes.CUSTOMER for the customers table. 
	 * @return Returns the finished query as a String for the appropriate table.
	 */
	public String prepareTableQuery(Entity.EntityTypes entityType) {
		String query = null;
		
		switch(entityType) {
			case CUSTOMER: {
				
				if(cust_customerNoChckbx.isSelected() || cust_customerNameChckbx.isSelected()) {
					
					query = "SELECT * FROM `customers` ";
					
					query += "WHERE ";
					
					//Concatenate the query depending on if the search by employee number check box is ticked.
					if(cust_customerNoChckbx.isSelected()) { 
						query += "`customer_number` LIKE '"+cust_searchTextField.getText()+"%' ";
					}
					
					//Concatenate the query depending on if the search by customer name check box is ticked.
					if(cust_customerNameChckbx.isSelected()) {
						
						if(cust_customerNoChckbx.isSelected()) query += "OR "; //Easy way to alter the query so the query will work properly. "OR " is needed for the query to actually work if both checkboxes are ticked.
						
						query += "`first_name` LIKE '"+cust_searchTextField.getText()+"%' OR `last_name` LIKE '"+cust_searchTextField.getText()+"%'";	
					}
					
					//If the search field is just left as "Search..." or there's nothing in it, then select everything from the table.
					if(cust_searchTextField.getText().equals("Search...") || cust_searchTextField.getText().length() == 0) query = "SELECT * FROM `customers`";
					
				} 
				
				break;
			}
			case EMPLOYEE: {
				break;
			}
			case USER: {
				
				query = "SELECT `user_number`, `username`, `admin`, `last_logon` FROM `users` ";
				
				query += "WHERE ";
				
				query += "`user_number` LIKE '"+user_searchTextField.getText()+"%' OR `username` LIKE '"+user_searchTextField.getText()+"%'";
								
				//If the search field is just left as "Search..." or there's nothing in it, then select everything from the table.
				if(user_searchTextField.getText().equals("Search...") || user_searchTextField.getText().length() == 0) query = "SELECT `user_number`, `username`, `admin`, `last_logon` FROM `users` ";
				
				break;
			}
		}
				
		return query; 
	}
	
	private String[] columnNames = null;
	private Object[][] rowData = null;
	private JTable table = null;
	private String query = null;
	
	/**
	 * Refresh the appropriate table. The table to be refreshed is decided by the parameter entityType.
	 * Get the list of Customer, Employee or User objects, depending on the query (prepared in the prepareTableQuery method) sent.
	 * From the list of objects, prepare the tables rows and then populate the table with the data.
	 * @param entityType - The name of the table to refresh. Entity.EntityTypes.CUSTOMER, "EMPLOYEES", "USERS".
	 */
	public void refreshTable(final Entity.EntityTypes entityType) {
		
		this.columnNames = null;
		this.rowData = null;
		this.table = null;
		this.query = null;
		
		//Set the 'View', 'Edit' & 'Delete' icons to be invisible.
		if(null != viewTableRowBtn) viewTableRowBtn.setVisible(false);
		if(null != editTableRowBtn) editTableRowBtn.setVisible(false);
		if(null != deleteTableRowBtn) deleteTableRowBtn.setVisible(false);
		
		
		SwingWorker<Integer, Integer> sw = new SwingWorker<Integer, Integer>() {

			@Override
			protected Integer doInBackground() throws Exception {
				
				switch(entityType) {		
					case CUSTOMER: {
					
						table = cust_customersTable;
						
						query = prepareTableQuery(entityType);
						
						//If query is not null at this point, then atleast one of the checkboxes are ticked so get the data.
						if(null != query) {
							
							//Get the list of Customer objects generated from the query. Then get an array containing all the data from the objects so it can be displayed in the table.
							customerList = appManager.getTableRowData(Entity.EntityTypes.CUSTOMER, query);  
							
							columnNames = cust_tableColumnNames;
							
							rowData = appManager.getRowData(Entity.EntityTypes.CUSTOMER, customerList);
							
						}			
						
						break;
					} 
					case EMPLOYEE: {
						break;
					}
					case USER: {

						table = user_usersTable;
						
						query = prepareTableQuery(entityType);
						
						if(null != query) {
							
							//Get the list of User objects generated from the query. Then get an array containing all the data from the objects so it can be displayed in the table.
							userList = appManager.getTableRowData(Entity.EntityTypes.USER, query);  
														
							columnNames = user_tableColumnNames;
														
							rowData = appManager.getRowData(Entity.EntityTypes.USER, userList);
							
						}			
						
						break;
					}
				}
				return 1;
			}
			
			@Override
			public void done() {
				//If there are no results, then create a notice to the user that nothing was returned so that they are notified.	
				if(rowData == null || rowData.length < 1) {
					rowData = new Object[1][];
					rowData[0] = new Object[]{"No results matched the search criteria!"};
					
					columnNames = new String[]{""};
				}
								
				//Display the new data.
				if(table != null) {
					//Create a new table model for the table.
					DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
						//Overriding the method so no cell is editable.
						@Override
					    public boolean isCellEditable(int row, int column) {
					       //set all cells false
					       return false;
					    }
					};
					table.setModel(tableModel); //Set the tables model.
				}		
				table = null;
			}
		};
		sw.execute();
		
	}
	
	/**
	 * Get the instanced Properties object.
	 * @return Returns the instanced Properties object.
	 */
	public Properties getProperties() {		
		return this.properties;
	}
	
	/**
	 * Loads the properties file, if it exists.
	 * Checks if the configuration file exists. If it does, load the properties from the file. If it doesn't, create the directory (if needed) and file.
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
				config_urlTextField.setText(properties.getProperty("dbUrl"));
				config_userTextField.setText(properties.getProperty("dbUser"));
				config_passwordTextField.setText(properties.getProperty("dbPassword"));
				
				//Set the details on the login form to the details that were saved.
				login_usernameTextField.setText(properties.getProperty("loginUser"));
				login_passwordField.setText(properties.getProperty("loginPassword"));	
				
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
	
	/**
	 * Save all appropriate data into the config.ini file.
	 * Saved properties depend on what the user wants saved, for example, ticking the checkbox for the database configuration will save the credentials.
	 */
	public void storeProperties() {
		FileOutputStream fos = null;
		
		try {
			
			fos = new FileOutputStream(fileDirectory);
			
			//Check if the 'Save details' button is selected under the Configuration panel.
			if(config_saveDetailsChckbx.isSelected()) {
				//if it is selected, save the details.	
				properties.setProperty("dbUrl", config_urlTextField.getText());
				properties.setProperty("dbUser", config_userTextField.getText());
				properties.setProperty("dbPassword", new String(config_passwordTextField.getPassword()));
			} else {
				//if it's not selected, do not save the details.
				properties.setProperty("dbUrl", "");
				properties.setProperty("dbUser", "");
				properties.setProperty("dbPassword", "");
			}
			
			if(login_saveDetailsChckbx.isSelected()) {
				//if it is selected, save the details.
				properties.setProperty("loginUser", login_usernameTextField.getText());
				properties.setProperty("loginPassword", new String(login_passwordField.getPassword()));	
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