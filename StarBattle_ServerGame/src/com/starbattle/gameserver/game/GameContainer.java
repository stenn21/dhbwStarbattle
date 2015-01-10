package com.starbattle.gameserver.game;

import com.starbattle.gameserver.game.timer.GameLoop;
import com.starbattle.gameserver.game.timer.UpdateListener;
import com.starbattle.gameserver.map.ServerMap;
import com.starbattle.gameserver.player.PlayerList;

public class GameContainer {

	private GameLoop gameLoop;
	private PlayerList playerList;
	private ServerMap serverMap;
	
	public GameContainer()
	{
		
	}
	
	public void startGame()
	{
		gameLoop=new GameLoop(new UpdateListener() {
			public void update(double delta) {
				updateGame(delta);
			}
		});
		gameLoop.setFPS(30);//Logical Update Units 
		gameLoop.start();
	}
	
	public void stopGame()
	{
		gameLoop.stop();
	}
	
	private void updateGame(double delta)
	{
		
	}
	
	public PlayerList getPlayerList() {
		return playerList;
	}
	
	public ServerMap getServerMap() {
		return serverMap;
	}
}
