package org.faabtech.jsamp;

import java.util.logging.Logger;

import org.faabtech.jsamp.data.DataProvider;
import org.faabtech.jsamp.data.impl.InfoDataProvider;
import org.faabtech.jsamp.event.MessageListener;
import org.faabtech.jsamp.event.SAMPResponseListener;
import org.faabtech.jsamp.net.Client;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.MessageEvent;

/**
 * Represents a SAMP Server data request.
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
	 * Representation of opcodes the Query Mechanism.
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
	 */
	public void send(final SAMPResponseListener listener) {
		this.client.connect(new MessageListener() {

			@Override
			public void send(ChannelFuture future) {
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
					logger.severe("IP is malformed. Terminating..");
					System.exit(0);
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
			public void get(MessageEvent e) {
				if (!(e.getMessage() instanceof ChannelBuffer))
					return;
				ChannelBuffer buf = (ChannelBuffer) e.getMessage();
				switch (opcode) {
				case INFO:
					/**
					 * Skip the first 11 bytes, it's only shit.
					 */
					buf.skipBytes(11);

					/**
					 * Is the server using a password?
					 */
					int password = buf.readUnsignedByte();

					/**
					 * Players on the server.
					 */
					int players = buf.readUnsignedByte()
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
							players, maxPlayers, hostname.toString(), gamemode
									.toString(), mapname.toString()));

					break;
				default:
					logger.severe("Opcode not supported yet.");
					System.exit(0);
					break;
				}
			}

		});
	}

}
