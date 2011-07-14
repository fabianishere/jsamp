package org.faabtech.jsamp.event;

import org.faabtech.jsamp.data.DataProvider;

public interface SAMPResponseListener {
	
	public void messageReceived(DataProvider dataProvider);

}
