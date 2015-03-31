package com.starbattle.server.manager;

import java.util.ArrayList;
import java.util.List;

import com.starbattle.gameserver.exceptions.ServerStartException;
import com.starbattle.gameserver.main.BattleEndListener;
import com.starbattle.gameserver.main.BattleResults;
import com.starbattle.gameserver.main.BattleSettings;
import com.starbattle.gameserver.main.StarbattleGameServer;

public class GameManager {

	private List<StarbattleGameServer> servers = new ArrayList<StarbattleGameServer>();

	public GameManager() {

	}

	public void openGame(BattleSettings battleSettings) {

		final StarbattleGameServer server = new StarbattleGameServer(0,0);
		servers.add(server);
		try {
			server.start(battleSettings, new BattleEndListener() {

				@Override
				public void serverError() {

					removeServer(server);
				}

				@Override
				public void battleEnd(BattleResults results) {
					removeServer(server);

				}
			});
		} catch (ServerStartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void removeServer(StarbattleGameServer server) {
		servers.remove(server);
	}

}
