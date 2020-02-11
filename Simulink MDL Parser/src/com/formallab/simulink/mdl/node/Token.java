package com.formallab.simulink.mdl.node;

public class Token {

	public static final Token EOF_TOKEN = new Token(TokenType.EOF, 0);

	private TokenType type;
	private String text;
	private int line;

	public Token(TokenType type, String text, int line) {
		this.type = type;
		this.text = text;
		this.line = line;
	}

	public Token(TokenType type, int line) {
		this(type, null, line);
	}

	public TokenType getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public int getLine() {
		return line;
	}

	public boolean isText() {
		return type == TokenType.TEXT;
	}

	public boolean isQuotedText() {
		return type == TokenType.QUOTED_TEXT;
	}

	public boolean isLCURLY() {
		return type == TokenType.LCURLY;
	}

	public boolean isRCURLY() {
		return type == TokenType.RCURLY;
	}

	public boolean isComment() {
		return type == TokenType.COMMENT;
	}

	public boolean isEOF() {
		return type == TokenType.EOF;
	}

	public String toString() {
		String result;
		switch (type) {
		case TEXT:
			result = text;
			break;

		case QUOTED_TEXT:
			result = "\"" + text + "\"";
			break;

		case LCURLY:
			result = "{";
			break;

		case RCURLY:
			result = "}";
			break;

		case COMMENT:
			result = "#" + text;
			break;

		case EOF:
			result = "EOF";
			break;

		default:
			throw new AssertionError();
		}
		return result;
	}

}
