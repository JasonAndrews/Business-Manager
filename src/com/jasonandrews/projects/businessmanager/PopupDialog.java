package com.jasonandrews.projects.businessmanager;


import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JCheckBox;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JSeparator;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PopupDialog extends JDialog {
	
	private static final ImageIcon EDIT_ICON_BLACK = new ImageIcon("lib/images/edit_icon_black_40x40.png");
	private static final ImageIcon EDIT_ICON_GREY = new ImageIcon("lib/images/edit_icon_grey_40x40.png");
	
	private static final ImageIcon SAVE_ICON_BLACK = new ImageIcon("lib/images/save_icon_black_40x40.png");
	private static final ImageIcon SAVE_ICON_GREY = new ImageIcon("lib/images/save_icon_grey_40x40.png");
	
	private static final ImageIcon CLOSE_ICON_BLACK = new ImageIcon("lib/images/close_icon_black_40x40.png");
	private static final ImageIcon CLOSE_ICON_GREY = new ImageIcon("lib/images/close_icon_grey_40x40.png");
	
	private AppManager appManager;
	private ApplicationFrame appFrame; //Object reference to the main frame of the application.
	
	private JPanel customersFormPanel;
	private JPanel employeesFormPanel;
	private JPanel usersFormPanel;

	private Entity loadedObject; //The customer / employee / user object that is currently loaded.
	
	//Customer related objects.
	private JTextField c_customerNoTextField;
	private JTextField c_firstNameTextField;
	private JTextField c_lastNameTextField;	
	private JTextField c_addressOneTextField;
	private JTextField c_addressTwoTextField;
	private JTextField c_addressCityTextField;
	private JTextField c_addressCountryTextField;

	private JLabel c_firstNameLbl;
	private JLabel c_lastNameLbl;
	private JLabel c_addressOneLbl;
	private JLabel c_addressTwoLbl;
	private JLabel c_addressCityLbl;
	private JLabel c_addressCountryLbl;	
	
	private JLabel c_customerNoLbl;
	
	private JButton c_confirmBtn;
	private JButton c_closeBtn;
	
	private boolean c_isEditingCustomer;
	private boolean c_isCreatingNewCustomer;
	//Employee related objects.
	
	//User related objects.
	
	/**
	 * Constructor.
	 * @param appFrame - The ApplicationFrame object of the application.
	 * @param appManager - The AppManager object of the application.
	 */
	public PopupDialog(ApplicationFrame appFrame, AppManager appManager) {
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				System.out.println("HIDDEN");
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				resetForm(Entity.EntityTypes.CUSTOMER);
				//Make a void resetCustomerForm method to reset it.
				//WORKSSS
			}
		});
		
		this.appManager = appManager;
		this.appFrame = appFrame;
		setResizable(false);
		setBounds(100, 100, 206, 482);
		getContentPane().setLayout(new CardLayout(0, 0));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		createPanels();
	}
	
	/**
	 * Creates the content panels for the dialog.
	 */
	private void createPanels() {
		
		//Customer Panel.
		customersFormPanel = new JPanel();
		customersFormPanel.setBackground(Color.WHITE);
		customersFormPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("CLICKED CUSTOMER PANEL");
			}
		});
		getContentPane().add(customersFormPanel, "name_26635925853738");
		customersFormPanel.setLayout(null);
		
		
		c_customerNoLbl = new JLabel("Customer Number");
		c_customerNoLbl.setFocusable(false);
		c_customerNoLbl.setForeground(Color.DARK_GRAY);
		c_customerNoLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_customerNoLbl.setBounds(28, 22, 135, 14);
		customersFormPanel.add(c_customerNoLbl);
		
		c_customerNoTextField = new JTextField();		
		c_customerNoTextField.setEditable(false);		
		c_customerNoTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_customerNoTextField.setBackground(Color.WHITE);
		c_customerNoTextField.setColumns(10);			
		c_customerNoTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_customerNoTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_customerNoTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_customerNoTextField);
		
		c_firstNameLbl = new JLabel("First Name");
		c_firstNameLbl.setFocusable(false);
		c_firstNameLbl.setForeground(Color.DARK_GRAY);
		c_firstNameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_firstNameLbl.setBounds(28, 83, 99, 14);
		customersFormPanel.add(c_firstNameLbl);
		
		c_lastNameLbl = new JLabel("Last Name");
		c_lastNameLbl.setFocusable(false);
		c_lastNameLbl.setForeground(Color.DARK_GRAY);
		c_lastNameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_lastNameLbl.setBounds(28, 129, 99, 14);
		customersFormPanel.add(c_lastNameLbl);
		
		c_firstNameTextField = new JTextField();
		c_firstNameTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_firstNameTextField.setEditable(false);
		c_firstNameTextField.setBackground(Color.WHITE);
		c_firstNameTextField.setBounds(28, 98, 136, 20);
		c_firstNameTextField.setColumns(10);
		c_firstNameTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_firstNameTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_firstNameTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_firstNameTextField);
		
		
		c_lastNameTextField = new JTextField();
		c_lastNameTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_lastNameTextField.setEditable(false);
		c_lastNameTextField.setColumns(10);
		c_lastNameTextField.setBackground(Color.WHITE);
		c_lastNameTextField.setBounds(28, 145, 136, 20);
		c_lastNameTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_lastNameTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_lastNameTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_lastNameTextField);
		
		c_addressTwoLbl = new JLabel("Address Two:");
		c_addressTwoLbl.setFocusable(false);
		c_addressTwoLbl.setForeground(Color.DARK_GRAY);
		c_addressTwoLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_addressTwoLbl.setBounds(28, 242, 99, 14);
		customersFormPanel.add(c_addressTwoLbl);
		
		c_addressOneLbl = new JLabel("Address One");
		c_addressOneLbl.setFocusable(false);
		c_addressOneLbl.setForeground(Color.DARK_GRAY);
		c_addressOneLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_addressOneLbl.setBounds(28, 196, 99, 14);
		customersFormPanel.add(c_addressOneLbl);
		
		c_addressOneTextField = new JTextField();
		c_addressOneTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_addressOneTextField.setEditable(false);
		c_addressOneTextField.setBackground(Color.WHITE);
		c_addressOneTextField.setColumns(10);
		c_addressOneTextField.setBounds(28, 211, 136, 20);
		c_addressOneTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_addressOneTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_addressOneTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_addressOneTextField);
		
		c_addressTwoTextField = new JTextField();
		c_addressTwoTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_addressTwoTextField.setEditable(false);
		c_addressTwoTextField.setBackground(Color.WHITE);
		c_addressTwoTextField.setColumns(10);
		c_addressTwoTextField.setBounds(28, 256, 136, 20);
		c_addressTwoTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_addressTwoTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_addressTwoTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_addressTwoTextField);
		
		c_addressCountryLbl = new JLabel("Country");
		c_addressCountryLbl.setFocusable(false);
		c_addressCountryLbl.setForeground(Color.DARK_GRAY);
		c_addressCountryLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_addressCountryLbl.setBounds(28, 332, 99, 14);
		customersFormPanel.add(c_addressCountryLbl);
		
		c_addressCityLbl = new JLabel("City");
		c_addressCityLbl.setFocusable(false);
		c_addressCityLbl.setForeground(Color.DARK_GRAY);
		c_addressCityLbl.setHorizontalAlignment(SwingConstants.LEFT);
		c_addressCityLbl.setBounds(28, 287, 99, 14);
		customersFormPanel.add(c_addressCityLbl);
		
		c_addressCityTextField = new JTextField();
		c_addressCityTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_addressCityTextField.setEditable(false);
		c_addressCityTextField.setBackground(Color.WHITE);
		c_addressCityTextField.setColumns(10);
		c_addressCityTextField.setBounds(28, 301, 136, 20);
		c_addressCityTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_addressCityTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_addressCityTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_addressCityTextField);
		
		c_addressCountryTextField = new JTextField();
		c_addressCountryTextField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		c_addressCountryTextField.setEditable(false);
		c_addressCountryTextField.setBackground(Color.WHITE);
		c_addressCountryTextField.setColumns(10);
		c_addressCountryTextField.setBounds(28, 346, 136, 20);
		c_addressCountryTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				c_addressCountryTextField.setBorder(new LineBorder(Color.BLACK));
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_addressCountryTextField.setBorder(new LineBorder(Color.GRAY));
			}
		});
		customersFormPanel.add(c_addressCountryTextField);
		
		c_confirmBtn = new JButton("");
		c_confirmBtn.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if(c_confirmBtn.getIcon() == EDIT_ICON_BLACK) {
					c_confirmBtn.setIcon(EDIT_ICON_GREY);
				} else if(c_confirmBtn.getIcon() == SAVE_ICON_BLACK) {
					c_confirmBtn.setIcon(SAVE_ICON_GREY);
				}
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(c_confirmBtn.getIcon() == EDIT_ICON_GREY) {
					c_confirmBtn.setIcon(EDIT_ICON_BLACK);
				} else if(c_confirmBtn.getIcon() == SAVE_ICON_GREY) {
					c_confirmBtn.setIcon(SAVE_ICON_BLACK);
				}
			}
		});
		c_confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//Check if the user is currently creating a new customer.
				if(c_isCreatingNewCustomer) { 
					
					//Check if the user has entered in enough information for the form.
					if(checkRequiredFields(Entity.EntityTypes.CUSTOMER)) {
						Customer customer = (Customer) loadedObject; //Cast the loaded object to a customer object so we can call Customer methods.
						customer.update(c_firstNameTextField.getText(), c_lastNameTextField.getText(), c_addressOneTextField.getText(), c_addressTwoTextField.getText(), c_addressCityTextField.getText(), c_addressCountryTextField.getText());
						appManager.updateDatabase(Entity.EntityTypes.CUSTOMER, customer);
						
						
						
						c_customerNoTextField.setVisible(true); 
						c_customerNoLbl.setVisible(true);
						
						c_isCreatingNewCustomer = false;
						
						resetForm(Entity.EntityTypes.CUSTOMER); //reset the form.
						
						setVisible(false); //Hide the dialog.
					}
					
					return;
				}
				
				//Check if the user is currently editing a customer's information.
				if(c_isEditingCustomer) {
					
					
					//Check if the user has entered in enough information for the form.
					if(checkRequiredFields(Entity.EntityTypes.CUSTOMER)) {
						//The user is editing a customer's information so save the updated information and set the user to NOT BE editing the information.
						
						Customer customer = (Customer) loadedObject; //Cast the loaded object to a customer object so we can call Customer methods.
						
						//Update the customer object with the updated information.
						customer.update(c_firstNameTextField.getText(), c_lastNameTextField.getText(), c_addressOneTextField.getText(), c_addressTwoTextField.getText(), c_addressCityTextField.getText(), c_addressCountryTextField.getText());
						appManager.updateDatabase(Entity.EntityTypes.CUSTOMER, customer);
						
						c_isEditingCustomer = false;
						//c_confirmBtn.setText("Edit"); //Change the text on the button to "Edit".
						c_confirmBtn.setIcon(EDIT_ICON_BLACK);
						c_confirmBtn.setRolloverIcon(EDIT_ICON_GREY);
						setFormEditable(Entity.EntityTypes.CUSTOMER, false); //Do allow users to edit the information on the form.
					} 					
					
				} else {
					//The user is not currently editing a customer's information, so set them to BE editing the information.
					setFormEditable(Entity.EntityTypes.CUSTOMER, true);
					
					c_isEditingCustomer = true;
					//c_confirmBtn.setText("Save");
					c_confirmBtn.setIcon(SAVE_ICON_BLACK);
					c_confirmBtn.setRolloverIcon(SAVE_ICON_GREY);
					c_firstNameTextField.requestFocus();
				}			
			}
		});	
		c_confirmBtn.setBackground(Color.WHITE);
		c_confirmBtn.setBounds(50, 385, 40, 40);
		c_confirmBtn.setIcon(EDIT_ICON_BLACK);
		c_confirmBtn.setRolloverIcon(EDIT_ICON_GREY);
		c_confirmBtn.setBorder(null);
		customersFormPanel.add(c_confirmBtn);
		
		c_closeBtn = new JButton("");
		c_closeBtn.setBackground(Color.WHITE);
		c_closeBtn.setBorder(null);
		c_closeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				resetForm(Entity.EntityTypes.CUSTOMER);
			}
		});
		c_closeBtn.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent arg0) {
				c_closeBtn.setIcon(CLOSE_ICON_GREY);
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				c_closeBtn.setIcon(CLOSE_ICON_BLACK);				
			}
		});
		c_closeBtn.setIcon(CLOSE_ICON_BLACK);
		c_closeBtn.setRolloverIcon(CLOSE_ICON_GREY);
		c_closeBtn.setBounds(100, 385, 40, 40);
		customersFormPanel.add(c_closeBtn);
		
		JSeparator cust_separatorOne = new JSeparator();
		cust_separatorOne.setForeground(Color.BLACK);
		cust_separatorOne.setBounds(19, 67, 151, 5);
		customersFormPanel.add(cust_separatorOne);
		
		JSeparator cust_separatorTwo = new JSeparator();
		cust_separatorTwo.setForeground(Color.BLACK);
		cust_separatorTwo.setBounds(21, 177, 151, 9);
		customersFormPanel.add(cust_separatorTwo);
				
		//Employee Panel.
		JPanel employeePanel = new JPanel();
		getContentPane().add(employeePanel, "name_26990095477004");
		
		//User Panel.
	}
	
	/**
	 * Sets the editing state of the current form.
	 * Example - Setting the customer form to be editable, so that the user may edit the customer's information.
	 * @param entityType - The type of form to update, for example, "CUSTOMERS".
	 * @param editingForm - Whether to enable (true) or disable (false) the ability to edit the form.
	 */
	public void setEditingForm(Entity.EntityTypes entityType, boolean editingForm) {
		switch(entityType) {
			case CUSTOMER: {				
				c_isEditingCustomer = editingForm;
				c_confirmBtn.setIcon(SAVE_ICON_BLACK);
				c_confirmBtn.setRolloverIcon(SAVE_ICON_GREY);
				//c_confirmBtn.setText("Save");
				
				break;
			}
			case EMPLOYEE: {
				break;
			}
			case USER: {
				break;
			}
		}
	}
	
	/**
	 * Populate the form with the given information, depending on the given panel type.
	 * @param entityType
	 * @param entity
	 */
	public void fillInForm(Entity.EntityTypes entityType, Entity entity) {
		this.loadedObject = entity;
		c_isCreatingNewCustomer = false;
		c_isEditingCustomer = false;
		switch(entityType) {
		
			case CUSTOMER: {
				Object[] info = ((Customer) entity).getInformation();
				customersFormPanel.setVisible(true);
				
				Integer customerNumber = ((Integer) info[0]).intValue();
				//System.out.println("Customer Number: " + customerNumber);
				
				//employeePanel.setVisible(false);
				//userPanel.setVisible(false);
				if(customerNumber > 0) { //If they're viewing an existing customer.
					//c_confirmBtn.setText("Edit");
					
					c_customerNoTextField.setText(info[0].toString());
					c_firstNameTextField.setText(info[1].toString());
					c_lastNameTextField.setText(info[2].toString());				
					c_addressOneTextField.setText(info[3].toString());
					c_addressTwoTextField.setText(info[4].toString());
					c_addressCityTextField.setText(info[5].toString());
					c_addressCountryTextField.setText(info[6].toString());
				} else { //If they're going to be creating a new user.
					c_isCreatingNewCustomer = true;
					
					//Hide the Customer Number components.					
					c_customerNoTextField.setVisible(false); 
					c_customerNoLbl.setVisible(false);
					
					c_customerNoTextField.setText("");					
					c_firstNameTextField.setText("");
					c_lastNameTextField.setText("");		
					c_addressOneTextField.setText("");
					c_addressTwoTextField.setText("");
					c_addressCityTextField.setText("");
					c_addressCountryTextField.setText("");
					
					//c_confirmBtn.setText("Create");
					c_confirmBtn.setIcon(SAVE_ICON_BLACK);
					c_confirmBtn.setRolloverIcon(SAVE_ICON_GREY);
					
					setFormEditable(Entity.EntityTypes.CUSTOMER, true);
				}
				break;
			}
			case EMPLOYEE: {
				//customerPanel.setVisible(false);
				//employeePanel.setVisible(true);
				//userPanel.setVisible(false);
				break;
			}
			case USER: {
				//customerPanel.setVisible(false);
				//employeePanel.setVisible(false);
				//userPanel.setVisible(true);
				
				break;
			}
		}
	}
	
	//Set the ability for some components to be edited (mainly JTextFields). 
	public void setFormEditable(Entity.EntityTypes entityType, boolean toggle) {		
		switch(entityType) {
			case CUSTOMER: {			
				c_firstNameTextField.setEditable(toggle);
				c_lastNameTextField.setEditable(toggle);
				c_addressOneTextField.setEditable(toggle);
				c_addressTwoTextField.setEditable(toggle);
				c_addressCityTextField.setEditable(toggle);
				c_addressCountryTextField.setEditable(toggle);	
				
				if(toggle) {
					c_firstNameLbl.setText("First Name *");
					c_lastNameLbl.setText("Last Name *");
					c_addressOneLbl.setText("Address One *");
					c_addressTwoLbl.setText("Address Two *");
					c_addressCityLbl.setText("City *");
					c_addressCountryLbl.setText("Country *");
					
				} else {
					c_firstNameLbl.setText("First Name");
					c_lastNameLbl.setText("Last Name");
					c_addressOneLbl.setText("Address One");
					c_addressTwoLbl.setText("Address Two");
					c_addressCityLbl.setText("City");
					c_addressCountryLbl.setText("Country");
				}
				
			}
			case EMPLOYEE: { }
			case USER: { }
		}		
	}
	
	/**
	 * Reset the information shown on a form (such as the Customer form when a user views a specific customer's information.)
	 * @param entityType - The String representative of the form type.	
	 */
	private void resetForm(Entity.EntityTypes entityType) {		
		switch(entityType) {
			case CUSTOMER: {
				
				setEditingForm(entityType, false); 
								
				c_firstNameTextField.setEditable(false);
				c_firstNameTextField.setText("");
				c_lastNameTextField.setEditable(false);
				c_lastNameTextField.setText("");
				c_addressOneTextField.setEditable(false);
				c_addressOneTextField.setText("");
				c_addressTwoTextField.setEditable(false);
				c_addressTwoTextField.setText("");
				c_addressCityTextField.setEditable(false);
				c_addressCityTextField.setText("");
				c_addressCountryTextField.setEditable(false);
				c_addressCountryTextField.setText("");
				
				c_firstNameLbl.setText("First Name");
				c_lastNameLbl.setText("Last Name");
				c_addressOneLbl.setText("Address One");
				c_addressTwoLbl.setText("Address Two");
				c_addressCityLbl.setText("City");
				c_addressCountryLbl.setText("Country");
				
				c_firstNameLbl.setForeground(Color.BLACK);
				c_lastNameLbl.setForeground(Color.BLACK);
				c_addressOneLbl.setForeground(Color.BLACK);
				c_addressCityLbl.setForeground(Color.BLACK);
				c_addressCountryLbl.setForeground(Color.BLACK);
				
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				
				c_confirmBtn.setIcon(EDIT_ICON_BLACK);
				c_confirmBtn.setRolloverIcon(EDIT_ICON_GREY);
				
				//c_confirmBtn.setText("Edit");
				break;				
			}
			case EMPLOYEE: { }
			case USER: { }
		}		
	}
	
	private boolean checkRequiredFields(Entity.EntityTypes entityType) {
		
		boolean isValid = true;
		
		switch(entityType) {
			case CUSTOMER: {
				if(c_firstNameTextField.getText().length() < 1) {
					c_firstNameLbl.setForeground(Color.RED);
					isValid = false;
				} else c_firstNameLbl.setForeground(Color.BLACK);
				
				if(c_lastNameTextField.getText().length() < 1) {
					c_lastNameLbl.setForeground(Color.RED);
					isValid = false;
				} else c_lastNameLbl.setForeground(Color.BLACK);
				
				if(c_addressOneTextField.getText().length() < 1) {
					c_addressOneLbl.setForeground(Color.RED);
					isValid = false;
				} else c_addressOneLbl.setForeground(Color.BLACK);
				
				if(c_addressCityTextField.getText().length() < 1) {
					c_addressCityLbl.setForeground(Color.RED);
					isValid = false;
				} else c_addressCityLbl.setForeground(Color.BLACK);
				
				if(c_addressCountryTextField.getText().length() < 1) {
					c_addressCountryLbl.setForeground(Color.RED);
					isValid = false;
				} else c_addressCountryLbl.setForeground(Color.BLACK);
					
				break;
			}
			case EMPLOYEE: {
				break;
			}
			case USER: {
				break;
			}
		}
		
		return isValid;
	}
}

