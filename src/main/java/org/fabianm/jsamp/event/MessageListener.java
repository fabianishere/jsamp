package org.fabianm.jsamp.event;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.MessageEvent;

public interface MessageListener {

	public void send(ChannelFuture future) throws Exception;

	public void get(MessageEvent e) throws Exception;

}
