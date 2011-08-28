package org.faabtech.jsamp;

import org.faabtech.jsamp.SAMPRequest.Opcode;
import org.faabtech.jsamp.data.DataProvider;
import org.faabtech.jsamp.data.impl.InfoDataProvider;
import org.faabtech.jsamp.data.impl.RuleDataProvider;
import org.faabtech.jsamp.event.SAMPResponseListener;
import org.faabtech.jsamp.exception.MalformedIpException;
import org.faabtech.jsamp.net.Client;
import org.faabtech.jsamp.server.Rule;

/**
 * Example class.
 * 
 * @author Fabian M.
 */
public class Example {

	public static void main(String[] args) {
		Client client = new Client("188.165.252.63", 7852);
		SAMPRequest request = new SAMPRequest(client, Opcode.RULES);
		try {
			request.send(new SAMPResponseListener() {

				@Override
				public void messageReceived(DataProvider dataProvider) {
					RuleDataProvider ruleDataProvider = (RuleDataProvider) dataProvider;

					for (Rule rule : ruleDataProvider.getRules()) {
						System.out.println(rule.getName() + ": " + rule.getValue());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
