package simulinkeditor.scanner;

import org.eclipse.jface.text.rules.IWordDetector;

public class NameDetector implements IWordDetector {

	public boolean isWordStart(char c) {
		return Character.isLetter(c) || '_' == c;
	}

	public boolean isWordPart(char c) {
		return Character.isLetterOrDigit(c) || '_' == c;
	}

}