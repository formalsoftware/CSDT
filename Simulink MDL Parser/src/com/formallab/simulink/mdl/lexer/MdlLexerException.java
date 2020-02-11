package com.formallab.simulink.mdl.lexer;

@SuppressWarnings("serial")
public class MdlLexerException extends Exception {

	private int line;

	public MdlLexerException(String message, int line) {
		super(message);
		this.line = line;
	}

	public MdlLexerException(String message) {
		this(message, 0);
	}

	public MdlLexerException(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public String getMessage() {
		return "MDL lexer error at line " + line + ": " + super.getMessage() + ".";
	}
}
