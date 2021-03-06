package com.starbattle.client.views.reset;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.starbattle.client.layout.DesignLabel;
import com.starbattle.client.layout.VerticalLayout;
import com.starbattle.client.layout.ViewModel;
import com.starbattle.client.resource.ResourceLoader;

public class ResetPasswordModel extends ViewModel {

	private JTextField username = new JTextField(22);
	private JTextField email = new JTextField(22);
	private JTextArea info = new JTextArea(5, 22);
	private Color labelColor=new Color(50, 50, 50);
	
	public ResetPasswordModel() {

		view.setLayout(new VerticalLayout());
		view.setBorder(BorderFactory.createEmptyBorder(20, 70, 20, 50));
		view.setBackground(new Color(200, 200, 200));

		view.add(new DesignLabel("Accountname", "user.png",labelColor));
		view.add(username);
		view.add(Box.createVerticalStrut(5));
		view.add(new DesignLabel("EMail", "email.png",labelColor));
		view.add(email);
		view.add(Box.createVerticalStrut(50));
		view.add(info);
		info.setEditable(false);
		info.setOpaque(false);
		info.append("Password reset: \n");
		info.append("You will get a new password by email. \n");

	}


	public String getUserName() {
		return username.getText();
	}

	public String getEmail() {
		return email.getText();
	}

}
