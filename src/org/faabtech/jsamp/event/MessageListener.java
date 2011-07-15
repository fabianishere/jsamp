package org.faabtech.jsamp.event;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.MessageEvent;

public interface MessageListener {

	public void send(ChannelFuture future);

	public void get(MessageEvent e);

}
