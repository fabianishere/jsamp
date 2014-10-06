package org.fabianm.jsamp;

import org.fabianm.jsamp.SAMPRequest.Opcode;
import org.fabianm.jsamp.data.DataProvider;
import org.fabianm.jsamp.data.impl.RuleDataProvider;
import org.fabianm.jsamp.event.SAMPResponseListener;
import org.fabianm.jsamp.net.Client;
import org.fabianm.jsamp.server.Rule;

/**
 * Example class.
 * 
 * @author Fabian M.
 */
public class Example {

	public static void main(String[] args) {
		Client client = new Client("93.119.30.66", 7777);
		SAMPRequest request = new SAMPRequest(client, Opcode.RULES);
		try {
			request.send(new SAMPResponseListener() {

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
