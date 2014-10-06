package org.fabianm.jsamp.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * Our direct implementation of the jBoss Netty client.
 * 
 * @author Fabian M.
 */
public class SAMPHandler extends SimpleChannelUpstreamHandler {

	/**
	 * The client of this handler.
	 */
	private Client client;

	/**
	 * Constructs a new SAMP Handler.
	 * 
	 * @param client
	 *            The client of this handler.
	 */
	public SAMPHandler(Client client) {
		this.client = client;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		try {
			client.messageListener.get(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {

	}
}
