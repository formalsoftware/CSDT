package com.fware.csdt.simulink.mdl.editor.scanner;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class WhitespaceDetector implements IWhitespaceDetector {

	/**
	 * Detects is the given character a white space character.
	 * 
	 * @param c
	 *            A character to test.
	 * 
	 * @return <code>true</code> if the character is a white space character,
	 *         <code>false</code> otherwise.
	 * 
	 * @see org.eclipse.jface.text.rules.IWhitespaceDetector#isWhitespace(char)
	 */
	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}