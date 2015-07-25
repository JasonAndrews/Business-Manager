

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PopupFrame extends JInternalFrame {
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField customerNoTextField;
	private JTextField addressOneTextField;
	private JTextField addressTwoTextField;
	private JTextField addressCityTextField;
	private JTextField addressCountryTextField;

	/**
	 * Create the frame.
	 */
	public PopupFrame() {
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(null);
		
		JLabel firstNameLbl = new JLabel("First Name:");
		firstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		firstNameLbl.setBounds(120, 50, 68, 14);
		getContentPane().add(firstNameLbl);
		
		JLabel lastNameLbl = new JLabel("Last Name:");
		lastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lastNameLbl.setBounds(120, 72, 68, 14);
		getContentPane().add(lastNameLbl);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setEditable(false);
		firstNameTextField.setBounds(198, 47, 136, 20);
		getContentPane().add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setEditable(false);
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(198, 69, 136, 20);
		getContentPane().add(lastNameTextField);
		
		JLabel customerNoLbl = new JLabel("Customer Number:");
		customerNoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		customerNoLbl.setBounds(85, 22, 103, 14);
		getContentPane().add(customerNoLbl);
		
		customerNoTextField = new JTextField();
		customerNoTextField.setEditable(false);
		customerNoTextField.setColumns(10);
		customerNoTextField.setBounds(198, 19, 136, 20);
		getContentPane().add(customerNoTextField);
		
		JLabel addressTwoLbl = new JLabel("Address Two:");
		addressTwoLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressTwoLbl.setBounds(120, 121, 68, 14);
		getContentPane().add(addressTwoLbl);
		
		JLabel addressOneLbl = new JLabel("Address One:");
		addressOneLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressOneLbl.setBounds(120, 99, 68, 14);
		getContentPane().add(addressOneLbl);
		
		addressOneTextField = new JTextField();
		addressOneTextField.setEditable(false);
		addressOneTextField.setColumns(10);
		addressOneTextField.setBounds(198, 96, 136, 20);
		getContentPane().add(addressOneTextField);
		
		addressTwoTextField = new JTextField();
		addressTwoTextField.setEditable(false);
		addressTwoTextField.setColumns(10);
		addressTwoTextField.setBounds(198, 118, 136, 20);
		getContentPane().add(addressTwoTextField);
		
		JLabel addressCountryLbl = new JLabel("Country:");
		addressCountryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressCountryLbl.setBounds(120, 167, 68, 14);
		getContentPane().add(addressCountryLbl);
		
		JLabel addressCityLbl = new JLabel("City:");
		addressCityLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		addressCityLbl.setBounds(120, 145, 68, 14);
		getContentPane().add(addressCityLbl);
		
		addressCityTextField = new JTextField();
		addressCityTextField.setEditable(false);
		addressCityTextField.setColumns(10);
		addressCityTextField.setBounds(198, 142, 136, 20);
		getContentPane().add(addressCityTextField);
		
		addressCountryTextField = new JTextField();
		addressCountryTextField.setEditable(false);
		addressCountryTextField.setColumns(10);
		addressCountryTextField.setBounds(198, 164, 136, 20);
		getContentPane().add(addressCountryTextField);
		
		JButton confirmBtn = new JButton("Edit");
		confirmBtn.setBounds(106, 283, 89, 23);
		getContentPane().add(confirmBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(205, 283, 89, 23);
		getContentPane().add(closeBtn);

	}
}
