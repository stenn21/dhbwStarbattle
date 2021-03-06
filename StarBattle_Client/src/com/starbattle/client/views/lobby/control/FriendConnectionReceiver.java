package com.starbattle.client.views.lobby.control;

import com.starbattle.client.connection.listener.NetworkFriendListener;
import com.starbattle.client.player.PlayerValues;
import com.starbattle.client.views.lobby.chat.ChatManager;
import com.starbattle.client.views.lobby.friends.FriendPanel;
import com.starbattle.network.connection.objects.NP_ChatException;
import com.starbattle.network.connection.objects.NP_ChatMessage;
import com.starbattle.network.connection.objects.NP_Constants;
import com.starbattle.network.connection.objects.NP_FriendUpdate;
import com.starbattle.network.connection.objects.NP_LobbyFriends;

public class FriendConnectionReceiver implements NetworkFriendListener{

	
	private ChatManager chatManager;
	private FriendPanel friendPanel;
	
	public  FriendConnectionReceiver(ChatManager chat, FriendPanel friends) {
		this.chatManager=chat;
		this.friendPanel=friends;
	}
	
	@Override
	public void receivedChat(NP_ChatMessage chat) {
		chatManager.receiveMessage(chat);
	}

	@Override
	public void receivedFriendList(NP_LobbyFriends friends) {
		friendPanel.updateFriendList(friends);
	}

	@Override
	public void receivedFriendUpdate(NP_FriendUpdate update) {
		
		System.out.println(PlayerValues.getPlayerDisplayName()+": received update: "+update.name+" type:"+update.updateType);
		friendPanel.friendUpdate( update);
	}

	@Override
	public void receivedChatException(NP_ChatException chat) {
		chatManager.receiveException(chat);
	}

}
