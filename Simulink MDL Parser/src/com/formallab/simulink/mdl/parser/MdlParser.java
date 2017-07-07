package com.formallab.simulink.mdl.parser;

import com.formallab.simulink.mdl.lexer.MdlLexer;
import com.formallab.simulink.mdl.lexer.MdlLexerException;
import com.formallab.simulink.mdl.node.MdlField;
import com.formallab.simulink.mdl.node.MdlFile;
import com.formallab.simulink.mdl.node.MdlSection;
import com.formallab.simulink.mdl.node.Token;

/**
 * @author Frank Zeyda
 * @author Joabe Jesus
 */
public class MdlParser {

	protected MdlLexer lexer;

	public MdlParser(MdlLexer lexer) {
		assert lexer != null;
		this.lexer = lexer;
	}

	public MdlFile parse() throws MdlLexerException, MdlParserException {
		MdlFile file = new MdlFile(this.lexer.getFileName());

		lexer.load();
		//lexer.reset();

		Token name = lexer.current();
		while (!name.isEOF()) {
			if (name.isComment()) {
				continue;
			}
			
			if (!isValidName(name) || !isValidTopSectionName(name.getText())) {
				throw new MdlParserException("Name \"Library\", \"Model\", \"Stateflow\" or \"MatData\" expected here.", name);
			}
			file.add(parseSection());

			name = lexer.current();
		}

		return file;
	}

	private boolean isValidTopSectionName(String text) {
		return text != null && text.equals("Library") || text.equals("Model")
			|| text.equals("Stateflow") || text.equals("MatData");
	}

	protected MdlSection parseSection() throws MdlParserException {
		Token name = lexer.consume();
		if (!isValidName(name)) {
			throw new MdlParserException("valid section name expected here", name);
		}
		if (!lexer.consume().isLCURLY()) {
			throw new MdlParserException("{ expected here", lexer.last());
		}
		MdlSection block = new MdlSection(name.toString());
		while (!lexer.current().isRCURLY()) {
			name = lexer.current();
			if (!isValidName(name)) {
				throw new MdlParserException(
						"valid field / section name or } expected here", name);
			}
			if (lexer.next().isLCURLY()) {
				block.addEntry(parseSection());
			} else {
				block.addEntry(parseField());
			}
		}
		lexer.consume(); /* Consume closing curly bracket. */
		return block;
	}

	protected MdlField parseField() throws MdlParserException {
		Token name = lexer.consume();
		if (!isValidName(name)) {
			throw new MdlParserException("valid field name expected here",
					name);
		}
		Token value = lexer.consume();
		if (!value.isText() && !value.isQuotedText()) {
			throw new MdlParserException("valid field value expected here",
					value);
		}
		String value_str = value.toString();
		if (value.isQuotedText()) {
			while (lexer.current().isQuotedText()) {
				value_str += "\n" + lexer.consume().toString();
			}
		}
		return new MdlField(name.toString(), value_str.replaceAll("\"", ""));
	}

	/* The following two methods are useful when performing the parsing. */

	public boolean isValidName(Token token) {
		/* TODO: check validity of characters. */
		return token.isText() /* && .. */;
	}

}
