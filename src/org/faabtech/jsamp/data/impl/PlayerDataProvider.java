package org.faabtech.jsamp.data.impl;

import org.faabtech.jsamp.data.DataProvider;
import org.faabtech.jsamp.server.Player;

/**
 * The {@link DataProvider} for the DETAILED_PLAYER_INFO opcode.
 * 
 * @author Fabian M.
 */
public class PlayerDataProvider extends DataProvider {

	/**
	 * The players of the server.
	 */
	private Player[] players;

	/**
	 * Constructs a new PlayerDataProvider.
	 * 
	 * @param players
	 *            The players of the server.
	 */
	public PlayerDataProvider(Player[] players) {
		this.players = players;
	}

	/**
	 * Get the players of the server.
	 * 
	 * @return {@link #players}
	 */
	public Player[] getPlayers() {
		return players;
	}

}
