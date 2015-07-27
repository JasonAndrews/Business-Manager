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

public class PopupFrame extends JFrame {
	
	private static final int FORM_TYPE_CUSTOMER = 1;
	private static final int FORM_TYPE_EMPLOYEE = 2;
	private static final int FORM_TYPE_USER = 3;
	
	private AppManager appManager;
	
	private JPanel customerPanel;
	private JPanel employeePanel;
	private JPanel userPanel;

	private Object loadedObject; //The customer / employee / user object that is currently loaded.
	
	//Customer related objects.
	private JTextField c_firstNameTextField;
	private JTextField c_lastNameTextField;
	private JTextField c_customerNoTextField;
	private JTextField c_addressOneTextField;
	private JTextField c_addressTwoTextField;
	private JTextField c_addressCityTextField;
	private JTextField c_addressCountryTextField;

	private JButton c_confirmBtn;
	private JButton c_closeBtn;
	
	private boolean c_isEditingCustomer;
	private boolean c_isCreatingNewCustomer;
	//Employee related objects.
	
	//User related objects.
	
	
	public PopupFrame(AppManager appManager) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
				System.out.println("HIDDEN");
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				resetForm(PopupFrame.FORM_TYPE_CUSTOMER);
				//Make a void resetCustomerForm method to reset it.
				//WORKSSS
			}
		});
		
		this.appManager = appManager;
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(new CardLayout(0, 0));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		createPanels();
	}
	
	private void createPanels() {
		
		//Customer Panel.
		customerPanel = new JPanel();
		getContentPane().add(customerPanel, "name_26635925853738");
		customerPanel.setLayout(null);
		
		JLabel firstNameLbl = new JLabel("First Name:");
		firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLbl.setBounds(124, 58, 68, 14);
		customerPanel.add(firstNameLbl);
		
		JLabel c_lastNameLbl = new JLabel("Last Name:");
		c_lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_lastNameLbl.setBounds(124, 80, 68, 14);
		customerPanel.add(c_lastNameLbl);
		
		c_firstNameTextField = new JTextField();
		c_firstNameTextField.setBorder(new LineBorder(Color.BLACK));
		c_firstNameTextField.setEditable(false);
		c_firstNameTextField.setBounds(202, 55, 136, 20);
		customerPanel.add(c_firstNameTextField);
		c_firstNameTextField.setColumns(10);
		
		c_lastNameTextField = new JTextField();
		c_lastNameTextField.setBorder(new LineBorder(Color.BLACK));
		c_lastNameTextField.setEditable(false);
		c_lastNameTextField.setColumns(10);
		c_lastNameTextField.setBounds(202, 77, 136, 20);
		customerPanel.add(c_lastNameTextField);
		
		JLabel c_customerNoLbl = new JLabel("Customer Number:");
		c_customerNoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_customerNoLbl.setBounds(89, 30, 103, 14);
		customerPanel.add(c_customerNoLbl);
		
		c_customerNoTextField = new JTextField();
		c_customerNoTextField.setBorder(new LineBorder(Color.BLACK));
		c_customerNoTextField.setEditable(false);
		c_customerNoTextField.setColumns(10);
		c_customerNoTextField.setBounds(202, 27, 136, 20);
		customerPanel.add(c_customerNoTextField);
		
		JLabel c_addressTwoLbl = new JLabel("Address Two:");
		c_addressTwoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressTwoLbl.setBounds(124, 129, 68, 14);
		customerPanel.add(c_addressTwoLbl);
		
		JLabel c_addressOneLbl = new JLabel("Address One:");
		c_addressOneLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressOneLbl.setBounds(124, 107, 68, 14);
		customerPanel.add(c_addressOneLbl);
		
		c_addressOneTextField = new JTextField();
		c_addressOneTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressOneTextField.setEditable(false);
		c_addressOneTextField.setColumns(10);
		c_addressOneTextField.setBounds(202, 104, 136, 20);
		customerPanel.add(c_addressOneTextField);
		
		c_addressTwoTextField = new JTextField();
		c_addressTwoTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressTwoTextField.setEditable(false);
		c_addressTwoTextField.setColumns(10);
		c_addressTwoTextField.setBounds(202, 126, 136, 20);
		customerPanel.add(c_addressTwoTextField);
		
		JLabel c_addressCountryLbl = new JLabel("Country:");
		c_addressCountryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressCountryLbl.setBounds(124, 175, 68, 14);
		customerPanel.add(c_addressCountryLbl);
		
		JLabel c_addressCityLbl = new JLabel("City:");
		c_addressCityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		c_addressCityLbl.setBounds(124, 153, 68, 14);
		customerPanel.add(c_addressCityLbl);
		
		c_addressCityTextField = new JTextField();
		c_addressCityTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressCityTextField.setEditable(false);
		c_addressCityTextField.setColumns(10);
		c_addressCityTextField.setBounds(202, 150, 136, 20);
		customerPanel.add(c_addressCityTextField);
		
		c_addressCountryTextField = new JTextField();
		c_addressCountryTextField.setBorder(new LineBorder(Color.BLACK));
		c_addressCountryTextField.setEditable(false);
		c_addressCountryTextField.setColumns(10);
		c_addressCountryTextField.setBounds(202, 172, 136, 20);
		customerPanel.add(c_addressCountryTextField);
		
		c_confirmBtn = new JButton("Edit");
		c_confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(c_isCreatingNewCustomer) { //If they clicked the button when it said "Create".
					Customer customer = (Customer) loadedObject; //Cast the loaded object to a customer object so we can call Customer methods.
					customer.update(c_firstNameTextField.getText(), c_lastNameTextField.getText(), c_addressOneTextField.getText(), c_addressTwoTextField.getText(), c_addressCityTextField.getText(), c_addressCountryTextField.getText());
					appManager.updateDatabase("CUSTOMERS", customer);
					
					setVisible(false); //Hide the frame.
					
				} else if(!c_isEditingCustomer) { //If they click the button when it says "Edit", then set all the appropriate fields to be editable.
					c_firstNameTextField.setEditable(true);
					c_lastNameTextField.setEditable(true);
					c_addressOneTextField.setEditable(true);
					c_addressTwoTextField.setEditable(true);
					c_addressCityTextField.setEditable(true);
					c_addressCountryTextField.setEditable(true);
					
					c_isEditingCustomer = true;
					c_confirmBtn.setText("Save");
				} else {
					//If they have edited the user and clicked the button when it says "Save".
					Customer customer = (Customer) loadedObject; //Cast the loaded object to a customer object so we can call Customer methods.
					//Update the customer object with the updated information.
					customer.update(c_firstNameTextField.getText(), c_lastNameTextField.getText(), c_addressOneTextField.getText(), c_addressTwoTextField.getText(), c_addressCityTextField.getText(), c_addressCountryTextField.getText());
					appManager.updateDatabase("CUSTOMERS", customer);
					
					setVisible(false); //Hide the frame.
					//resetForm(PopupFrame.FORM_TYPE_CUSTOMER);
				}				
			}
		});
		c_confirmBtn.setBounds(102, 237, 89, 23);
		customerPanel.add(c_confirmBtn);
		
		c_closeBtn = new JButton("Close");
		c_closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
				c_isEditingCustomer = false;
				c_isCreatingNewCustomer = false;
				resetForm(PopupFrame.FORM_TYPE_CUSTOMER);
			}
		});
		c_closeBtn.setBounds(201, 237, 89, 23);
		customerPanel.add(c_closeBtn);
		
		//Employee Panel.
		JPanel employeePanel = new JPanel();
		getContentPane().add(employeePanel, "name_26990095477004");
		
		//User Panel.
	}
	
	//Populate the form with the given information, depending on the given panel type.
	public void fillInForm(String panelType, Object object) {
		this.loadedObject = object;
		c_isCreatingNewCustomer = false;
		c_isEditingCustomer = false;
		switch(panelType) {
			case "CUSTOMER": {
				Object[] info = ((Customer) object).getCustomerInformation();
				customerPanel.setVisible(true);
				
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
					
					c_customerNoTextField.setText("");
					c_firstNameTextField.setText("");
					c_lastNameTextField.setText("");		
					c_addressOneTextField.setText("");
					c_addressTwoTextField.setText("");
					c_addressCityTextField.setText("");
					c_addressCountryTextField.setText("");
					
					c_confirmBtn.setText("Create");
					
					toggleFormEditable(FORM_TYPE_CUSTOMER, true);
				}
				break;
			}
			case "EMPLOYEE": {
				//customerPanel.setVisible(false);
				//employeePanel.setVisible(true);
				//userPanel.setVisible(false);
				break;
			}
			case "USER": {
				//customerPanel.setVisible(false);
				//employeePanel.setVisible(false);
				//userPanel.setVisible(true);
				
				break;
			}
		}
	}
	
	private void toggleFormEditable(int formType, boolean toggle) {
		
		switch(formType) {
			case FORM_TYPE_CUSTOMER: {
				if(toggle) {
					c_firstNameTextField.setEditable(true);
					c_lastNameTextField.setEditable(true);
					c_addressOneTextField.setEditable(true);
					c_addressTwoTextField.setEditable(true);
					c_addressCityTextField.setEditable(true);
					c_addressCountryTextField.setEditable(true);
				} else {
					c_firstNameTextField.setEditable(false);
					c_lastNameTextField.setEditable(false);
					c_addressOneTextField.setEditable(false);
					c_addressTwoTextField.setEditable(false);
					c_addressCityTextField.setEditable(false);
					c_addressCountryTextField.setEditable(false);
				}
			}
			case FORM_TYPE_EMPLOYEE: { }
			case FORM_TYPE_USER: { }
		}
		
	}
	
	private void resetForm(int formType) {
		
		switch(formType) {
			case FORM_TYPE_CUSTOMER: {
				
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
			case FORM_TYPE_EMPLOYEE: { }
			case FORM_TYPE_USER: { }
		}
		
	}
}

