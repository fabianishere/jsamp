package org.fabianm.jsamp.data.impl;

import org.fabianm.jsamp.data.DataProvider;
import org.fabianm.jsamp.server.Player;

/**
 * The {@link DataProvider} for the Client list opcode.
 * 
 * @author Fabian M.
 */
public class ClientListDataProvider extends DataProvider {

	/**
	 * Array with players of the server.
	 */
	private Player[] players;

	/**
	 * Constructs a new ClientList data provider.
	 * 
	 * @param players
	 *            The players of this client list.
	 */
	public ClientListDataProvider(Player[] players) {
		this.players = players;
	}

	/**
	 * Get the players of this server.
	 * 
	 * @return {@link #players}
	 */
	public Player[] getPlayers() {
		return players;
	}

}
