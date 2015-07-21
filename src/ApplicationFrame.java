

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

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

public class ApplicationFrame extends JFrame {

	private JPanel mainMenuPanel;
	private JPanel configurePanel;
	private JPanel loginPanel;
	private JPanel homePanel;
	
	private JTable table;
	private JTable table_1;
	private JTextField urlTextField;
	private JTextField userTextField;
	private JTextField passwordTextField;
	
	private JLabel statusResultLbl;
	private JLabel errorLbl;
	private JLabel loginErrorLbl;
	
	private String url;
	private String user;
	private String password;

	private Connection connection;

	private static final Color BUTTON_BACKGROUND_COLOR = Color.BLACK;
	private static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
	private JTextField loginUsernameTextField;
	private JPasswordField loginPasswordField;
	
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public ApplicationFrame() {
		setTitle("Business Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 490);
		setResizable(false);		
		
		createContentPanes();
		
		homePanel = new JPanel();
		homePanel.setBackground(Color.WHITE);
		homePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		homePanel.setLayout(null);		
		
		setContentPane(homePanel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 681, 21);
		homePanel.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("EXIT");
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenu mnAdmin = new JMenu("Admin");
		menuBar.add(mnAdmin);
		
		JMenuItem mntmNewUser = new JMenuItem("New User");
		mnAdmin.add(mntmNewUser);
		mntmNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Yooo");
			}
		});
	}
	
	//Create the content panes that will be used. These are essentially the different screens of the application, such as the main menu and login screens.
	void createContentPanes() {
		
		
		
		/*
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
				
				if(!connectToDatabase(url, user, password)) { //If the connection failed, then give them the error on the main menu.
					errorLbl.setText("Could not connect, please enter the credentials on the Configuration form.");					
				} else errorLbl.setText("");
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
		
		
		
		
		
		//configurePanel
		
		
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
		
		JButton connectBtn = new JButton("Connect");
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String url = urlTextField.getText();
				String user = userTextField.getText();
				String password = passwordTextField.getText();
				
				//Attempt to connect to the database using the credentials.
				connectToDatabase(url, user, password);
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
				loginUser(username, password);
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
		
		
		*/
	}
	
	//Connect to the database with the given credentials.
	boolean connectToDatabase(String url, String user, String password) {		
				
		//Add a thread here.
		try {			
			
			if(connection != null && connection.isValid(0)) { //If there's already a connection, close it before continuing.
				System.out.println("Already has a connection.");
				connection.close(); //Close the connection.
			}
			
			//Some debugging.
			System.out.println("Attempting to connect to URL: [" + url + "] | USER: [" + user + "] | PASSWORD: [" + password + "]");
			
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
	
	//Display the content pane that contains all the elements for the main menu screen.
	void displayMainMenu() {		
		errorLbl.setText("");
		
		configurePanel.setVisible(false);
		loginPanel.setVisible(false);
		
		mainMenuPanel.setVisible(true);
		setContentPane(mainMenuPanel);
	}
	
	//Display the content pane that contains all the elements for the configuration screen.
	void displayConfiguration() {		
		mainMenuPanel.setVisible(false);
		loginPanel.setVisible(false);
		
		setContentPane(configurePanel);
		configurePanel.setVisible(true);
	}
	
	//Display the content pane that contains all the elements for the login screen.
	void displayLogin() {
		mainMenuPanel.setVisible(false);
		configurePanel.setVisible(false);
		
		setContentPane(loginPanel);
		loginPanel.setVisible(true);
	}
	
	//Returns a hashed string of the string that was passed as a parameter.
	public String SHA1(String input) throws NoSuchAlgorithmException {
		MessageDigest msgDigest = MessageDigest.getInstance("SHA1");
		byte[] result = msgDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		
		return sb.toString();	
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