package com.starbattle.server.manager;

import com.starbattle.accounts.manager.AccountException;
import com.starbattle.accounts.manager.AccountManager;
import com.starbattle.accounts.manager.impl.AccountManagerImpl;
import com.starbattle.accounts.player.FriendRelation;
import com.starbattle.accounts.player.PlayerAccount;
import com.starbattle.accounts.player.PlayerFriends;
import com.starbattle.accounts.validation.LoginState;
import com.starbattle.accounts.validation.RegisterState;
import com.starbattle.network.connection.objects.NP_ChatMessage;
import com.starbattle.network.connection.objects.NP_FriendRequest;
import com.starbattle.network.connection.objects.NP_HandleFriendRequest;
import com.starbattle.network.connection.objects.NP_LobbyFriends;
import com.starbattle.network.connection.objects.NP_Login;
import com.starbattle.network.connection.objects.NP_Register;
import com.starbattle.network.connection.objects.NP_ResetEmail;
import com.starbattle.network.connection.objects.NP_StartAnswer;
import com.starbattle.network.server.PlayerConnection;
import com.starbattle.network.server.SendClientConnection;
import com.starbattle.server.player.PlayerContainer;

public class PlayerManager {

	private SendClientConnection sendConnection;
	private AccountManager accountManager;
	private PlayerContainer playerContainer;

	public PlayerManager(SendClientConnection sendConnection, PlayerContainer playerContainer) {
		// TODO Auto-generated constructor stub
		this.sendConnection = sendConnection;
		this.playerContainer = playerContainer;
		accountManager = new AccountManagerImpl();
	}

	public void handleFriendRequest(PlayerConnection player, NP_HandleFriendRequest handle) {
		String name = handle.friendName;
		boolean accept = handle.accept;
		try {
			accountManager.handleFriendRequest(player.getAccountName(), name, accept);
			sendCurrentFriends(player); // update me
			// TODO: update friend
		} catch (AccountException e) {
			e.printStackTrace();
		}
	}

	public void trySendFriendRequest(PlayerConnection player, NP_FriendRequest friendRequest) {
		String name = friendRequest.inputName;
		try {
			if (accountManager.newFriendRequest(player.getAccountName(), name)) {
				sendCurrentFriends(player); // send update to client
			}
		} catch (AccountException e) {
			e.printStackTrace();
		}
	}

	public void sendCurrentFriends(PlayerConnection player) {
		try {
			PlayerFriends friends = accountManager.getFriendRelations(player.getAccountName());
			NP_LobbyFriends npfriends = new NP_LobbyFriends();
			for (FriendRelation f : friends.getFriends()) {
				npfriends.friendNames.add(f.getAccountName());
				npfriends.relationStates.add((byte) f.getRelationState().getId());
				npfriends.friendOnline.add(false);
			}
			player.sendTCP(npfriends);
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tryResetEmail(NP_ResetEmail reset) {
		// TODO add email to account manager interface
		String user = reset.name;
		String email = reset.email;
		try {
			accountManager.tryResetPassword(user, email);
		} catch (AccountException e) {
			e.printStackTrace();
		}
	}

	public void tryLogin(PlayerConnection player, NP_Login login) {
		String name = login.playerName;
		String password = login.password;
		try {
			LoginState loginState = accountManager.canLogin(name, password);
			String errorText = loginState.getText();
			if (loginState == LoginState.Login_Ok) {
				// check if player is not already connected
				if (!playerContainer.playerConnected(name)) {
					// login player
					loginPlayer(player, name);
					return;
				}
				errorText = "Player is already logged in!";
			}
			// error case
			sendAnswer(player, false, errorText);

		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendAnswer(player, false, "Internal server error!");
		}
	}

	public void tryRegister(PlayerConnection player, NP_Register register) {
		PlayerAccount account = PlayerAccountGenerator.generateAccount(register);
		RegisterState registerState = accountManager.canRegisterAccount(account);
		if (registerState == RegisterState.Register_Ok) {
			// account is valid
			try {
				// create account
				accountManager.registerAccount(account);
				// login player
				loginPlayer(player, register.accountName);
				return;
			} catch (AccountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// error case
		sendAnswer(player, false, registerState.getText());
	}

	private void loginPlayer(PlayerConnection player, String name) {
		player.setAccountName(name);
		playerContainer.loginPlayer(player);
		// send no response string, just open-game-command to true
		System.out.println("Player login: " + name);
		sendAnswer(player, true, null);
		sendCurrentFriends(player); // send friends to player
	}

	public void logoutPlayer(PlayerConnection player) {
		if (player.isConnected()) {// if its connected, remove player object
									// from accountlist
			playerContainer.logoutPlayer(player.getAccountName());
			System.out.println("Player logout: " + player.getAccountName());
		}
	}

	private void sendAnswer(PlayerConnection player, boolean login, String text) {
		NP_StartAnswer answer = new NP_StartAnswer();
		answer.openGame = login;
		answer.errorMessage = text;
		player.sendTCP(answer);
	}

	public void sendChat(PlayerConnection player, NP_ChatMessage message) {
		try {
			// Get Displayname for Accountname of the sending player
			String fromDisplayname = accountManager.getDisplayName(player.getAccountName());
			// Get the Accountname (to get the connection) of the receiver
			String toAccountName = accountManager.getAccountName(message.name);

			// Create chat message
			NP_ChatMessage chat = new NP_ChatMessage();
			chat.message = message.message;
			chat.name = fromDisplayname;
			// Send chat to receiver player
			playerContainer.getPlayer(toAccountName).getConnection().sendTCP(chat);
		} catch (AccountException e) {
			e.printStackTrace();
		}
	}

}
