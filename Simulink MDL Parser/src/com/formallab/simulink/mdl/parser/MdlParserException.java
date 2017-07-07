package com.formallab.simulink.mdl.parser;

import com.formallab.simulink.mdl.node.Token;

@SuppressWarnings("serial")
public class MdlParserException extends Exception {

	private Token token;

	public MdlParserException(String message, Token token) {
		super(message);
		this.token = token;
	}

	public MdlParserException(String message) {
		this(message, null);
	}

	public Token getToken() {
		return token;
	}

	public String getMessage() {
		String result = "MDL parsing error";
		if (token != null) {
			result += " at token <" + token + ">, line " + token.getLine();
		}
		result += ": " + super.getMessage() + ".";
		return result;
	}
}
