package com.starbattle.gameserver.game.mode;

import com.starbattle.gameserver.game.action.Damage;
import com.starbattle.gameserver.game.item.GameItem;
import com.starbattle.gameserver.player.GamePlayer;

public interface GameModeInterface {

	
	public void onGameInit();
	
	public void onTakingDamage(GamePlayer player, Damage damage);
	
	public void onCollectingItem(GamePlayer player, GameItem item);
	
	public void onEnteringTile(GamePlayer player, int tileID);
	
	public void onLandingOnTile(GamePlayer player, int tileID);
	
}
