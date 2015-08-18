/**
 * A child to the javax.swing.JButton class, this class will add both a FocusListener and a MouseListener to itself.
 * This is done in the constructor and it's done so that the ApplicationFrame class will have less repeated code snippets.
 * 
 * Will not be used as of yet.
 * @author Jason Andrews
 */
package com.jasonandrews.projects.businessmanager;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;



public class CustomButton extends JButton {

	public CustomButton() {
		this("");
	}

	public CustomButton(String text) {
		super(text);
		
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				setForeground(Color.GRAY);
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				setForeground(Color.BLACK);				
			}
			
		});
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { }

			@Override
			public void mouseEntered(MouseEvent arg0) { 
				setForeground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				setForeground(Color.BLACK);				
			}

			@Override
			public void mousePressed(MouseEvent arg0) { }

			@Override
			public void mouseReleased(MouseEvent arg0) { }
			
		});
	}
}
