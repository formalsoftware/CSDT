package com.formallab.simulink.mdl.lexer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.LinkedList;
import java.util.List;

import com.formallab.simulink.mdl.node.Token;
import com.formallab.simulink.mdl.node.TokenType;

/* 
 * I decided to make use of the StreamTokenizer class to implement the lexer.
 * This clearly resulted in certain limitations which are hinted in the code,
 * Nevertheless the use of StreamTokenizer saved the effort of writing a lexer
 * from scratch, for the problem at hand this solutions seems sufficient.
 */
/**
 * @author Frank Zeyda
 * @author Joabe Jesus
 */
public class MdlLexer {

	private String fileName;

	private StreamTokenizer tokeniser;
	private Token[] tokens;

	private int index;

	public MdlLexer(String fileName) throws FileNotFoundException {
		this(new FileReader(fileName));
		this.fileName = fileName;
	}

	public MdlLexer(Reader r) {
		tokeniser = new StreamTokenizer(r);
		tokens = new Token[0];
		configureSyntax();
	}

	public String getFileName() {
		return fileName;
	}

	private void configureSyntax() {
		tokeniser.resetSyntax();
		tokeniser.wordChars('\u0000', '\u00ff');
		tokeniser.quoteChar('\"');
		tokeniser.quoteChar('[');
		tokeniser.ordinaryChar('{');
		tokeniser.ordinaryChar('}');
		tokeniser.whitespaceChars(' ', ' ');
		tokeniser.whitespaceChars('\t', '\t');
		tokeniser.whitespaceChars('\n', '\n');
		tokeniser.whitespaceChars('\r', '\r');
		tokeniser.commentChar('#');
		tokeniser.eolIsSignificant(false);
		tokeniser.slashSlashComments(false);
		tokeniser.slashStarComments(false);
	}

	public void load() throws MdlLexerException {
		generateTokens();
		reset();
	}

	private void generateTokens() throws MdlLexerException {
		List<Token> token_list = new LinkedList<Token>();
		Token token;
		do {
			int line = nextToken();
			switch (tokeniser.ttype) {
			case StreamTokenizer.TT_WORD:
				assert tokeniser.sval != null;
				token = new Token(TokenType.TEXT, tokeniser.sval, line);
				break;

			case '"':
				assert tokeniser.sval != null;
				/*
				 * The following is a slight correction, StreamTokenizer for
				 * some reason renders \" as ".
				 */
				tokeniser.sval = tokeniser.sval.replace("\"", "\\\"");
				tokeniser.sval = tokeniser.sval.replace("\n", "\\n");
				/* There might be more special characters to consider here.. */
				token = new Token(TokenType.QUOTED_TEXT, tokeniser.sval, line);
				break;

			/*
			 * Note that here the opening [ is matched against the end of the
			 * line rather than the closing ]. This is a limitation imposed by
			 * the StreamTokenizer class which assumes opening and closing
			 * quotes to be similar characters.
			 */
			case '[':
				assert tokeniser.sval != null;
				token = new Token(TokenType.TEXT, "[" + tokeniser.sval, line);
				break;

			case '{':
				token = new Token(TokenType.LCURLY, line);
				break;

			case '}':
				token = new Token(TokenType.RCURLY, line);
				break;

			case '#':
				token = new Token(TokenType.COMMENT, line);
				break;

			case StreamTokenizer.TT_EOF:
				token = new Token(TokenType.EOF, line);
				break;

			default:
				throw new MdlLexerException(line);
			}
			token_list.add(token);
		} while (!token.isEOF());
		tokens = token_list.toArray(tokens);
	}

	private int nextToken() throws MdlLexerException {
		try {
			tokeniser.nextToken();
			return tokeniser.lineno();
		} catch (IOException e) {
			throw new MdlLexerException(e.getMessage(), tokeniser.lineno());
		}
	}

	public void dumpTokens() { /* For debugging use. */
		for (Token token : tokens) {
			System.out.println("[" + token + "]");
		}
	}

	public void reset() {
		index = 0;
	}

	/* Note that the lookahead parameter may as well be negative. */

	public Token LL(int lookahead) {
		assert index + lookahead >= 0;
		if (index + lookahead < tokens.length) {
			return tokens[index + lookahead];
		} else {
			if (tokens[tokens.length - 1].getType() == TokenType.EOF) {
				return tokens[tokens.length - 1];
			} else {
				return Token.EOF_TOKEN; /* Return a generic EOF token. */
			}
		}
	}

	public Token consume() {
		Token result = current();
		if (index < tokens.length) {
			index++;
		}
		return result;
	}

	public Token last() {
		return LL(-1);
	}

	public Token next() {
		return LL(1);
	}

	public Token current() {
		return LL(0);
	}
}
