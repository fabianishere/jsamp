package org.faabtech.jsamp.data.impl;

import org.faabtech.jsamp.data.DataProvider;
import org.faabtech.jsamp.server.Rule;

/**
 * The {@link DataProvider} for the Rules opcode.
 * 
 * @author Fabian M.
 */
public class RuleDataProvider extends DataProvider {

	/**
	 * The rules of the server.
	 */
	private Rule[] rules;

	/**
	 * Constructs a new Rule DataProvider.
	 * 
	 * @param rules
	 *            The rules of the server.
	 */
	public RuleDataProvider(Rule[] rules) {
		this.rules = rules;
	}

	/**
	 * Get the rules of this server.
	 * 
	 * @return {@link #rules}
	 */
	public Rule[] getRules() {
		return rules;
	}

}
