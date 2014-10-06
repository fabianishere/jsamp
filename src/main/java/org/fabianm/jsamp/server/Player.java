package org.fabianm.jsamp.server;

/**
 * Represent an SAMP Player.
 * 
 * @author Fabian M.
 */
public class Player {

	/**
	 * The id of this player. <note>This variable is only available if you are
	 * using the {@link SAMPRequest.Opcode#DETAILED_PLAYER_INFO} opcode.</note>
	 */
	private int playerId;

	/**
	 * The username of this player.
	 */
	private String username;

	/**
	 * The score of this player.
	 */
	private int score;

	/**
	 * The ping of this player. <note>This variable is only available if you are
	 * using the {@link SAMPRequest.Opcode#DETAILED_PLAYER_INFO} opcode.</note>
	 */
	private int ping;

	/**
	 * Constructs a new player.
	 * 
	 * @param username
	 *            The username of this player.
	 * @param score
	 *            The score of this player.
	 */
	public Player(String username, int score) {
		this.username = username;
		this.score = score;
	}

	/**
	 * Constructs a new player.
	 * 
	 * @param playerId
	 *            The playerId of this player..
	 * @param username
	 *            The username of this player.
	 * @param score
	 *            The score of this player.
	 * @param ping
	 *            The ping of this player.
	 */
	public Player(int playerId, String username, int score, int ping) {
		this.username = username;
		this.score = score;
	}

	/**
	 * Get the id of this player. <note>This variable is only available if you
	 * are using the {@link SAMPRequest.Opcode#DETAILED_PLAYER_INFO}
	 * opcode.</note>
	 * 
	 * @return {@link #playerId}
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Get the username of this player.
	 * 
	 * @return {@link #username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the score of this player.
	 * 
	 * @return {@link #score}
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Get the ping of this player. <note>This variable is only available if you
	 * are using the {@link SAMPRequest.Opcode#DETAILED_PLAYER_INFO}
	 * opcode.</note>
	 * 
	 * @return {@link #ping}
	 */
	public int getPing() {
		return ping;
	}
}
