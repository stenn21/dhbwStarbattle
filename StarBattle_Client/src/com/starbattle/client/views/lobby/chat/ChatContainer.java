package com.starbattle.client.views.lobby.chat;

import java.awt.Dimension;

import com.starbattle.client.window.ContentPanel;
import com.starbattle.network.connection.objects.NP_ChatMessage;

public class ChatContainer {

	private String friendName;
	private ChatPopupWindow window;
	private ChatContentPanel view;
	
	public ChatContainer(String name, ContentPanel parent, WriteMessageListener writeMessageListener)
	{		
		this.friendName=name;
		window=new ChatPopupWindow(new Dimension(300,400), "Chat with "+friendName);
		view=new ChatContentPanel(friendName,writeMessageListener);
		window.setContent(view);
		window.init(parent);
		window.open();
	}
	
	public void forceOpen()
	{
		window.open();
	}
	
	public void receiveMessage(String text)
	{
		view.receive(text);
		//open chat window on receive (if its closed)
		window.open();
	}

	public void forceClose() {
		window.close();
	}
	
}