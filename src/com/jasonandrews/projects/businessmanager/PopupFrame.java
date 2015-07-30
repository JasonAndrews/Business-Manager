package com.jasonandrews.projects.businessmanager;


import java.awt.EventQueue;

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

public class PopupFrame extends JFrame {
	
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

	private JLabel c_customerNoLbl;
	
	private JButton c_confirmBtn;
	private JButton c_closeBtn;
	
	private boolean c_isEditingCustomer;
	private boolean c_isCreatingNewCustomer;
	//Employee related objects.
	
	//User related objects.
	
	
	public PopupFrame(ApplicationFrame appFrame, AppManager appManager) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				System.out.println("HIDDEN");
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				resetForm("CUSTOMERS");
				//Make a void resetCustomerForm method to reset it.
				//WORKSSS
			}
		});
		
		this.appManager = appManager;
		this.appFrame = appFrame;
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(new CardLayout(0, 0));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		createPanels();
	}
	
	private void createPanels() {
		
		//Customer Panel.
		customersFormPanel = new JPanel();
		getContentPane().add(customersFormPanel, "name_26635925853738");
		customersFormPanel.setLayout(null);
		
		JLabel firstNameLbl = new JLabel("First Name:");
		firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLbl.setBounds(61, 56, 99, 14);
		customersFormPanel.add(firstNameLbl);
		
		JLabel c_lastNameLbl = new JLabel("Last Name:");
		c_lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_lastNameLbl.setBounds(61, 78, 99, 14);
		customersFormPanel.add(c_lastNameLbl);
		
		c_firstNameTextField = new JTextField();
		c_firstNameTextField.setBorder(new LineBorder(Color.BLACK));
		c_firstNameTextField.setEditable(false);
		c_firstNameTextField.setBackground(Color.WHITE);
		c_firstNameTextField.setBounds(170, 53, 136, 20);
		customersFormPanel.add(c_firstNameTextField);
		c_firstNameTextField.setColumns(10);
		
		c_lastNameTextField = new JTextField();
		c_lastNameTextField.setBorder(new LineBorder(Color.BLACK));
		c_lastNameTextField.setEditable(false);
		c_lastNameTextField.setColumns(10);
		c_lastNameTextField.setBackground(Color.WHITE);
		c_lastNameTextField.setBounds(170, 75, 136, 20);
		customersFormPanel.add(c_lastNameTextField);
		
		c_customerNoLbl = new JLabel("Customer Number:");
		c_customerNoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_customerNoLbl.setBounds(42, 28, 118, 14);
		customersFormPanel.add(c_customerNoLbl);
		
		c_customerNoTextField = new JTextField();
		c_customerNoTextField.setBorder(new LineBorder(Color.BLACK));
		c_customerNoTextField.setEditable(false);
		c_customerNoTextField.setBackground(Color.WHITE);
		c_customerNoTextField.setColumns(10);
		c_customerNoTextField.setBounds(170, 25, 136, 20);
		customersFormPanel.add(c_customerNoTextField);
		
		JLabel c_addressTwoLbl = new JLabel("Address Two:");
		c_addressTwoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressTwoLbl.setBounds(61, 127, 99, 14);
		customersFormPanel.add(c_addressTwoLbl);
		
		JLabel c_addressOneLbl = new JLabel("Address One:");
		c_addressOneLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressOneLbl.setBounds(61, 105, 99, 14);
		customersFormPanel.add(c_addressOneLbl);
		
		c_addressOneTextField = new JTextField();
		c_addressOneTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressOneTextField.setEditable(false);
		c_addressOneTextField.setBackground(Color.WHITE);
		c_addressOneTextField.setColumns(10);
		c_addressOneTextField.setBounds(170, 102, 136, 20);
		customersFormPanel.add(c_addressOneTextField);
		
		c_addressTwoTextField = new JTextField();
		c_addressTwoTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressTwoTextField.setEditable(false);
		c_addressTwoTextField.setBackground(Color.WHITE);
		c_addressTwoTextField.setColumns(10);
		c_addressTwoTextField.setBounds(170, 124, 136, 20);
		customersFormPanel.add(c_addressTwoTextField);
		
		JLabel c_addressCountryLbl = new JLabel("Country:");
		c_addressCountryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressCountryLbl.setBounds(61, 173, 99, 14);
		customersFormPanel.add(c_addressCountryLbl);
		
		JLabel c_addressCityLbl = new JLabel("City:");
		c_addressCityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressCityLbl.setBounds(61, 151, 99, 14);
		customersFormPanel.add(c_addressCityLbl);
		
		c_addressCityTextField = new JTextField();
		c_addressCityTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressCityTextField.setEditable(false);
		c_addressCityTextField.setBackground(Color.WHITE);
		c_addressCityTextField.setColumns(10);
		c_addressCityTextField.setBounds(170, 148, 136, 20);
		customersFormPanel.add(c_addressCityTextField);
		
		c_addressCountryTextField = new JTextField();
		c_addressCountryTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressCountryTextField.setEditable(false);
		c_addressCountryTextField.setBackground(Color.WHITE);
		c_addressCountryTextField.setColumns(10);
		c_addressCountryTextField.setBounds(170, 170, 136, 20);
		customersFormPanel.add(c_addressCountryTextField);
		
		c_confirmBtn = new JButton("Edit");
		c_confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(c_isCreatingNewCustomer) { //If the user clicked the button when it said "Create".
					Customer customer = (Customer) loadedObject; //Cast the loaded object to a customer object so we can call Customer methods.
					customer.update(c_firstNameTextField.getText(), c_lastNameTextField.getText(), c_addressOneTextField.getText(), c_addressTwoTextField.getText(), c_addressCityTextField.getText(), c_addressCountryTextField.getText());
					appManager.updateDatabase("CUSTOMERS", customer);
					
					setVisible(false); //Hide the frame.
					
					c_customerNoTextField.setVisible(true); 
					c_customerNoLbl.setVisible(true);
					
				} else if(!c_isEditingCustomer) { //If they click the button when it says "Edit", then set all the appropriate fields to be editable.
					c_firstNameTextField.setEditable(true);
					c_lastNameTextField.setEditable(true);
					c_addressOneTextField.setEditable(true);
					c_addressTwoTextField.setEditable(true);
					c_addressCityTextField.setEditable(true);
					c_addressCountryTextField.setEditable(true);
					
					c_isEditingCustomer = true;
					c_confirmBtn.setText("Save");
					c_firstNameTextField.requestFocus();
				} else {
					//If they have edited the user and clicked the button when it says "Save".
					Customer customer = (Customer) loadedObject; //Cast the loaded object to a customer object so we can call Customer methods.
					//Update the customer object with the updated information.
					customer.update(c_firstNameTextField.getText(), c_lastNameTextField.getText(), c_addressOneTextField.getText(), c_addressTwoTextField.getText(), c_addressCityTextField.getText(), c_addressCountryTextField.getText());
					appManager.updateDatabase("CUSTOMERS", customer);
					
					c_isEditingCustomer = true;
					c_confirmBtn.setText("Edit"); //Change the text on the button to "Edit".
					setFormEditable("CUSTOMERS", false); //Do allow users to edit the information on the form.
					appFrame.refreshTable("CUSTOMERS");
					//setVisible(false); //Hide the frame.
					//resetForm(PopupFrame.FORM_TYPE_CUSTOMER);
				}				
			}
		});
		c_confirmBtn.setBounds(102, 237, 89, 23);
		customersFormPanel.add(c_confirmBtn);
		
		c_closeBtn = new JButton("Close");
		c_closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				resetForm("CUSTOMERS");
			}
		});
		c_closeBtn.setBounds(201, 237, 89, 23);
		customersFormPanel.add(c_closeBtn);
		
		
		
		//Employee Panel.
		JPanel employeePanel = new JPanel();
		getContentPane().add(employeePanel, "name_26990095477004");
		
		//User Panel.
	}
	
	public void setEditingForm(String formType, boolean editingForm) {
		switch(formType) {
			case "CUSTOMERS": {				
				c_isEditingCustomer = editingForm;
				c_confirmBtn.setText("Save");
				
				break;
			}
			case "EMPLOYEES": {
				break;
			}
			case "USERS": {
				break;
			}
		}
	}
	
	//Populate the form with the given information, depending on the given panel type.
	public void fillInForm(String formType, Entity entity) {
		this.loadedObject = entity;
		c_isCreatingNewCustomer = false;
		c_isEditingCustomer = false;
		switch(formType) {
		
			case "CUSTOMERS": {
				Object[] info = ((Customer) entity).getInformation();
				customersFormPanel.setVisible(true);
				
				Integer customerNumber = ((Integer) info[0]).intValue();
				System.out.println("Customer Number: " + customerNumber);
				
				//employeePanel.setVisible(false);
				//userPanel.setVisible(false);
				if(customerNumber > 0) { //If they're viewing an existing customer.
					c_confirmBtn.setText("Edit");
					
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
					
					c_confirmBtn.setText("Create");
					
					setFormEditable("CUSTOMERS", true);
				}
				break;
			}
			case "EMPLOYEES": {
				//customerPanel.setVisible(false);
				//employeePanel.setVisible(true);
				//userPanel.setVisible(false);
				break;
			}
			case "USERS": {
				//customerPanel.setVisible(false);
				//employeePanel.setVisible(false);
				//userPanel.setVisible(true);
				
				break;
			}
		}
	}
	
	//Set the ability for some components to be edited (mainly JTextFields). 
	public void setFormEditable(String formType, boolean toggle) {		
		switch(formType) {
			case "CUSTOMERS": {			
				c_firstNameTextField.setEditable(toggle);
				c_lastNameTextField.setEditable(toggle);
				c_addressOneTextField.setEditable(toggle);
				c_addressTwoTextField.setEditable(toggle);
				c_addressCityTextField.setEditable(toggle);
				c_addressCountryTextField.setEditable(toggle);				
			}
			case "EMPLOYEES": { }
			case "USERS": { }
		}		
	}
	
	/*
	 * Reset the information shown on a form (such as the Customer form when a user views a specific customer's information.)	
	 */
	private void resetForm(String formType) {		
		switch(formType) {
			case "CUSTOMERS": {
				
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
				
				c_isEditingCustomer = false;
				c_confirmBtn.setText("Edit");
				break;				
			}
			case "EMPLOYEES": { }
			case "USERS": { }
		}
		
	}
}

