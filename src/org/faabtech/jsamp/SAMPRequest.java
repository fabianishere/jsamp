package org.faabtech.jsamp;

import java.util.logging.Logger;

import org.faabtech.jsamp.data.DataProvider;
import org.faabtech.jsamp.data.impl.ClientListDataProvider;
import org.faabtech.jsamp.data.impl.InfoDataProvider;
import org.faabtech.jsamp.data.impl.PlayerDataProvider;
import org.faabtech.jsamp.data.impl.RuleDataProvider;
import org.faabtech.jsamp.event.MessageListener;
import org.faabtech.jsamp.event.SAMPResponseListener;
import org.faabtech.jsamp.exception.MalformedIpException;
import org.faabtech.jsamp.exception.NonSupportedOpcodeException;
import org.faabtech.jsamp.net.Client;
import org.faabtech.jsamp.server.Player;
import org.faabtech.jsamp.server.Rule;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.MessageEvent;

/**
 * Represents a SAMP Server data request.
 * 
 * For more info about the query mechanism, go to 
 * 	<a href="http://wiki.sa-mp.com/wiki/Query_Mechanism">http://wiki.sa-mp.com/wiki/Query_Mechanism</a>
 * 
 * @author Fabian M.
 */
public class SAMPRequest {

	/**
	 * The logger of this instance.
	 */
	private Logger logger = Logger.getLogger(SAMPRequest.class.getName());

	/**
	 * The opcode of this request.
	 */
	private Opcode opcode;

	/**
	 * Representation of available query opcodes.
	 * 
	 * @author Fabian M.
	 */
	public enum Opcode {
		INFO, RULES, CLIENT_LIST, DETAILED_PLAYER_INFO, RCON_COMMAND, PSUEDO_RANDOM;
	}

	/**
	 * The client we use to send data.
	 */
	private Client client;

	/**
	 * Creates a new SAMP request.
	 * 
	 * @param client
	 *            The client to use.
	 * @param opcode
	 *            The opcode of this request.
	 */
	public SAMPRequest(Client client, Opcode opcode) {
		this.client = client;
		this.opcode = opcode;
	}

	/**
	 * Send the request and get the data.
	 * 
	 * @param listener
	 *            The SAMP Response listener.
	 *            
	 * @throws Exception 
	 */
	public void send(final SAMPResponseListener listener) throws Exception {
		this.client.connect(new MessageListener() {

			@Override
			public void send(ChannelFuture future) throws MalformedIpException {
				ChannelBuffer buf = ChannelBuffers.buffer(26);
				/**
				 * This part denotes that this packet is indeed a SA-MP packet.
				 */
				buf.writeBytes(new byte[] { (byte) 'S', (byte) 'A', (byte) 'M',
						(byte) 'P' });

				/**
				 * Here we write the ip 4 bytes to the server.
				 */
				String[] ip = client.getAddress().split("\\.");

				if (ip.length != 4) {
					throw new MalformedIpException();
				}

				for (String ipPart : ip) {
					buf.writeByte((byte) (char) Integer.parseInt(ipPart));
				}

				/**
				 * Here we write the port into the buffer.
				 */
				buf.writeByte((byte) (client.getPort() & 0xFF));
				buf.writeByte((byte) (client.getPort() >> 8 & 0xFF));

				/**
				 * Here we write the opcode into the buffer.
				 */
				switch (opcode) {
				case INFO:
					buf.writeByte((byte) 'i');
					break;
				case RULES:
					buf.writeByte((byte) 'r');
					break;
				case CLIENT_LIST:
					buf.writeByte((byte) 'c');
					break;
				case DETAILED_PLAYER_INFO:
					buf.writeByte((byte) 'd');
					break;
				case RCON_COMMAND:
					buf.writeByte((byte) 'x');
					break;
				case PSUEDO_RANDOM:
					buf.writeByte((byte) 'p');
					break;
				default:
					logger.info("Please use a correct opcode.");
					System.exit(0);
					break;

				}
				if (future.isSuccess())
					future.getChannel().write(buf);
			}

			@Override
			public void get(MessageEvent e) throws NonSupportedOpcodeException {
				if (!(e.getMessage() instanceof ChannelBuffer))
					return;
				ChannelBuffer buf = (ChannelBuffer) e.getMessage();
				/**
				 * Skip the first 11 bytes, it's only shit.
				 */
				buf.skipBytes(11);
				switch (opcode) {
				case INFO:
					/**
					 * Is the server using a password?
					 */
					int password = buf.readUnsignedByte();

					/**
					 * Players on the server.
					 */
					int playerCount = buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					/**
					 * Player count the server allows.
					 */
					int maxPlayers = buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					/**
					 * Hostname lenght.
					 */
					long hostnameLenght = buf.readUnsignedByte()
							+ buf.readUnsignedByte() + buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					/**
					 * Hostname
					 */
					StringBuilder hostname = new StringBuilder();
					/**
					 * Forloop
					 */
					for (int i = 0; i < hostnameLenght; i++) {
						/**
						 * Add cast to the unsigned byte and append the char to
						 * the hostname.
						 */
						hostname.append((char) buf.readUnsignedByte());
					}

					/**
					 * Gamemode Length
					 */
					int gamemodeLen = buf.readUnsignedByte()
							+ buf.readUnsignedByte() + buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					/**
					 * Gamemode
					 */
					StringBuilder gamemode = new StringBuilder();
					/**
					 * Forloop
					 */
					for (int i = 0; i < gamemodeLen; i++) {
						/**
						 * Add cast to the unsigned byte and append the char to
						 * the gamemode. <note>it is an unsigned byte</note>
						 */
						gamemode.append((char) buf.readUnsignedByte());
					}

					/**
					 * Mapname Length <note>it is an unsigned byte</note>
					 */
					int mapnameLen = buf.readUnsignedByte()
							+ buf.readUnsignedByte() + buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					/**
					 * Mapname
					 */
					StringBuilder mapname = new StringBuilder();
					/**
					 * Forloop
					 */
					for (int i = 0; i < mapnameLen; i++) {
						/**
						 * Add cast to the unsigned byte and append the char to
						 * the mapname. <note>it is an unsigned byte</note>
						 */
						mapname.append((char) buf.readUnsignedByte());
					}

					listener.messageReceived(new InfoDataProvider(password,
							playerCount, maxPlayers, hostname.toString(),
							gamemode.toString(), mapname.toString()));

					break;
				case RULES:
					int ruleCount = buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					Rule[] rules = new Rule[ruleCount];

					for (int i = 0; i < ruleCount; i++) {
						/**
						 * Rule name block here.
						 */
						int nameLenght = buf.readUnsignedByte();

						String name = "";

						for (int i2 = 0; i2 < nameLenght; i2++)
							name += (char) buf.readUnsignedByte();

						/**
						 * Rule value block here.
						 */
						int valueLenght = buf.readUnsignedByte();

						String value = "";

						for (int i2 = 0; i2 < valueLenght; i2++)
							value += (char) buf.readUnsignedByte();
						rules[i] = new Rule(name, value);
					}
					listener.messageReceived(new RuleDataProvider(rules));

					break;
				case CLIENT_LIST:
					playerCount = buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					Player[] players = new Player[playerCount];

					for (int i = 0; i < playerCount; i++) {
						/**
						 * Username block here.
						 */
						int usernameLenght = buf.readUnsignedByte();

						String username = "";

						for (int i2 = 0; i2 < usernameLenght; i2++)
							username += (char) buf.readUnsignedByte();

						/**
						 * Player score block here.
						 */
						int score = buf.readUnsignedByte()
								+ buf.readUnsignedByte()
								+ buf.readUnsignedByte()
								+ buf.readUnsignedByte();

						players[i] = new Player(username, score);
					}
					listener
							.messageReceived(new ClientListDataProvider(players));
					break;
				case DETAILED_PLAYER_INFO:
					playerCount = buf.readUnsignedByte()
							+ buf.readUnsignedByte();

					players = new Player[playerCount];

					for (int i = 0; i < playerCount; i++) {
						int playerId = buf.readUnsignedByte();

						int usernameLenght = buf.readUnsignedByte();

						String username = "";

						for (int i2 = 0; i2 < usernameLenght; i2++)
							username += (char) buf.readUnsignedByte();

						/**
						 * Player score block here.
						 */
						int score = buf.readUnsignedByte()
								+ buf.readUnsignedByte()
								+ buf.readUnsignedByte()
								+ buf.readUnsignedByte();

						/**
						 * Player ping block here.
						 */
						int ping = buf.readUnsignedByte()
								+ buf.readUnsignedByte()
								+ buf.readUnsignedByte()
								+ buf.readUnsignedByte();

						players[i] = new Player(playerId, username, score, ping);
					}
					listener.messageReceived(new PlayerDataProvider(players));
					break;
				default:
					throw new NonSupportedOpcodeException();
				}
			}

		});
	}

}
