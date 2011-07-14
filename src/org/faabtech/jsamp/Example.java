package org.faabtech.jsamp;

import org.faabtech.jsamp.SAMPRequest.Opcode;
import org.faabtech.jsamp.data.DataProvider;
import org.faabtech.jsamp.data.impl.InfoDataProvider;
import org.faabtech.jsamp.event.SAMPResponseListener;
import org.faabtech.jsamp.net.Client;

/**
 * Example class.
 * @author Fabian M.
 */
public class Example {

	public static void main(String[] args) {
		SAMPRequest request = new SAMPRequest(new Client("188.165.206.114", 7777), Opcode.INFO);
		request.send(new SAMPResponseListener(){

			@Override
			public void messageReceived(DataProvider dataProvider) {
				InfoDataProvider info = (InfoDataProvider) dataProvider;
				System.out.println(info.getPlayers());
			}
		});
		
	}

}
