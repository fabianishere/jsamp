package org.fabianm.jsamp.data.impl;

import org.fabianm.jsamp.data.DataProvider;

/**
 * The {@link DataProvider} for the info opcode.
 * 
 * @author Fabian M.
 */
public class InfoDataProvider extends DataProvider {

	/**
	 * Gamemode of the server.
	 */
	private String gamemode;

	/**
	 * Hostname of the server.
	 */
	private String hostname;

	/**
	 * Mapname of the server.
	 */
	private String mapname;

	/**
	 * Maximum amount of players that can join the server.
	 */
	private int maxPlayers;

	/**
	 * Is the server using a password?
	 */
	private boolean password;

	/**
	 * Current amount of players online on the server.
	 */
	private int players;

	/**
	 * Constructs the info class
	 * 
	 * @param password
	 *            Either 0 or 1, depending whether if the password has been set.
	 * @param players
	 *            Current amount of players online on the server.
	 * @param maxPlayers
	 *            Maximum amount of players that can join the server.
	 * @param hostname
	 *            Hostname of the server.
	 * @param gamemode
	 *            Gamemode of the server.
	 * @param mapname
	 *            Mapname of the server.
	 */
	public InfoDataProvider(int password, int players, int maxPlayers,
			String hostname, String gamemode, String mapname) {
		this.password = password == 0 ? false : true;
		this.players = players;
		this.maxPlayers = maxPlayers;
		this.hostname = hostname;
		this.gamemode = gamemode;
		this.mapname = mapname;
	}

	/**
	 * Gamemode of the server.
	 * 
	 * @return {@link #gamemode}
	 */
	public String getGamemode() {
		return gamemode;
	}

	/**
	 * Hostname of the server.
	 * 
	 * @return {@link #hostname}
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Mapname of the server.
	 * 
	 * @return {@link #mapname}
	 */
	public String getMapname() {
		return mapname;
	}

	/**
	 * Maximum amount of players that can join the server.
	 * 
	 * @return {@link #maxPlayers}
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Current amount of players online on the server.
	 * 
	 * @return {@link #players}
	 */
	public int getPlayers() {
		return players;
	}

	/**
	 * Does the server uses a password?
	 * 
	 * @return {@link #password}
	 */
	public boolean passwordSet() {
		return password;
	}

}
