package com.starbattle.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.starbattle.client.connection.NetworkConnection;
import com.starbattle.client.connection.RegistrationListener;
import com.starbattle.client.model.LoginModel;
import com.starbattle.client.resource.ResourceLoader;
import com.starbattle.client.window.ContentView;
import com.starbattle.network.client.SendServerConnection;
import com.starbattle.network.connection.objects.NP_Login;

public class LoginView extends ContentView {

	public final static int VIEW_ID = 0;
	private Dimension windowSize=new Dimension(400,400);
	
	private JButton loginButton = new JButton("Login");
	private JButton registerButton = new JButton("Create Account");
	private LoginModel loginModel=new LoginModel("TimoTester","test123");
	private SendServerConnection sendConnection;

	public LoginView(NetworkConnection connection) {

		sendConnection = connection.getSendConnection();
		connection.setRegistrationListener(new Registration());
		
		view.setBackgroundImage(ResourceLoader.loadImage("loginBackground.jpg"));

		view.setLayout(new BorderLayout());
		
		loginModel.addKeyListener(new KeyEnter());
		
		JLabel title=new JLabel(ResourceLoader.loadIcon("title.png"));
		view.add(title,BorderLayout.NORTH);
		
		JPanel footer = new JPanel();
		footer.setLayout(new FlowLayout());
		footer.setOpaque(false);
		footer.add(registerButton);
		footer.add(loginButton);
				
		JPanel block=new JPanel();
		block.setLayout(new BorderLayout());
		block.setOpaque(false);
		block.add(loginModel.getView(),BorderLayout.NORTH);
		block.add(footer,BorderLayout.SOUTH);
		view.add(block, BorderLayout.SOUTH);

		// change to register view on button click
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openView(RegisterView.VIEW_ID);
			}
		});

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryLogin();
			}
		});
	}

	private void tryLogin() {
		String name = loginModel.getUserName();
		String password = loginModel.getHashedPassword();

		NP_Login login = new NP_Login();
		login.playerName = name;
		login.password = password;
		sendConnection.sendTCP(login);
	}

	private class Registration implements RegistrationListener {

		@Override
		public void registrationOk() {
			// TODO Auto-generated method stub
			openView(GameView.VIEW_ID);
		}

		@Override
		public void registrationFailed(String error) {
			// TODO Auto-generated method stub
			loginModel.setErrorText(error);
		}
	}

	private class KeyEnter implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				tryLogin();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	@Override
	protected void initView() {
		loginModel.setErrorText("");
		resizeWindow(windowSize);
	}

	@Override
	protected void onClosing() {

	}

	@Override
	public int getViewID() {

		return VIEW_ID;
	}

}
