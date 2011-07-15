package org.faabtech.jsamp.server;

/**
 * Represents a server rule.
 * 
 * @author Fabian M.
 */
public class Rule {

	/**
	 * The name of this rule.
	 */
	private String name;

	/**
	 * The value of this rule.
	 */
	private String value;

	/**
	 * Constructs a new Rule.
	 * 
	 * @param name
	 *            The name of this rule.
	 * @param value
	 *            The value of this rule.
	 */
	public Rule(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Get the name of this rule.
	 * 
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the value of this rule.
	 * 
	 * @return {@link #value}
	 */
	public String getValue() {
		return value;
	}

}
