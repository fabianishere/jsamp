package org.fabianm.jsamp.event;

import org.fabianm.jsamp.data.DataProvider;

public interface SAMPResponseListener {

	public void messageReceived(DataProvider dataProvider);

}
