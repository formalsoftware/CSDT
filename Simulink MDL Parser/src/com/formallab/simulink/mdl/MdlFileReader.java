package com.formallab.simulink.mdl;

import java.io.FileNotFoundException;

import com.formallab.simulink.mdl.lexer.MdlLexer;
import com.formallab.simulink.mdl.lexer.MdlLexerException;
import com.formallab.simulink.mdl.node.MdlFile;
import com.formallab.simulink.mdl.parser.MdlParser;
import com.formallab.simulink.mdl.parser.MdlParserException;

public class MdlFileReader {

	private static void welcome() {
		System.out.println("Simulink Model Parser");
		System.out.println("2008 (c) by Joabe Jesus, Federal University of Pernambuco.");
	}

	private static void usage() {
		System.out.println("Usage: MdlTree <input-file>.mdl");
		System.exit(-1);
	}

	public static void main(String[] args) throws Exception {
		welcome();
		if (args.length != 1) {
			usage();
		}

		System.out.println("Parsing file...");
		MdlFile file = open(args[0]);
		System.out.println(file);
	}

	public static MdlFile open(String fileName)
			throws FileNotFoundException, MdlLexerException, MdlParserException {
		try {
			MdlLexer lexer = new MdlLexer(fileName);
			MdlParser parser = new MdlParser(lexer);
			return parser.parse();
		} catch (MdlLexerException e) {
			System.out.println(e.getMessage());
			throw e;
		} catch (MdlParserException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

}
