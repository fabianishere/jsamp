package org.fabianm.jsamp.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.fabianm.jsamp.event.MessageListener;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;

/**
 * Our seperate intance of the 'Client', not directly related to jBoss Netty NIO.
 * 
 * @author Fabian M.
 */
public class Client {

	/**
	 * The address used for connecting.
	 */
	private String address;

	/**
	 * Get the address used for connecting.
	 * 
	 * @return {@link #address}
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * The port used for connecting.
	 */
	private int port;

	/**
	 * Get the port used for connecting.
	 * 
	 * @return {@link #port}
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Our bootstrap instance.
	 */
	private ConnectionlessBootstrap bootstrap;

	/**
	 * The message listener of this instance.
	 */
	protected MessageListener messageListener;

	/**
	 * Our channel future instance.
	 */
	private ChannelFuture future;

	/**
	 * Creates a new client initialization.
	 * 
	 * @param address
	 *            The address to connect to.
	 * @param port
	 *            The port to connect to.
	 */
	public Client(String address, int port) {
		this.address = address;
		this.port = port;

		this.bootstrap = new ConnectionlessBootstrap(
				new NioDatagramChannelFactory(Executors.newCachedThreadPool()));

		bootstrap.getPipeline().addLast("handler", new SAMPHandler(this));
	}

	/**
	 * Check if the given address and port exists.
	 * 
	 * @return <code>true</code> if you can connect to the given address.
	 *         Otherwise, <code>false</code>
	 */
	public boolean canConnect() {
		future = bootstrap.connect(new InetSocketAddress(this.address,
				this.port));
		try {
			future.await();
		} catch (InterruptedException e) {
			return false;
		}
		return future.getChannel().isConnected();
	}

	/**
	 * Connect to the given address and port.
	 * 
	 * @param messageListener
	 *            Handles client's events.
	 * @throws Exception 
	 */
	public void connect(final MessageListener messageListener) throws Exception {
		this.messageListener = messageListener;

		// Connect to the given address.
		future = bootstrap.connect(new InetSocketAddress(this.address,
				this.port));
		// Send the request here.
		future.await();

		messageListener.send(future);
		
	}
}
