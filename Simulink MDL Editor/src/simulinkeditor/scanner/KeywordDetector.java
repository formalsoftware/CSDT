package simulinkeditor.scanner;

import org.eclipse.jface.text.rules.IWordDetector;

public class KeywordDetector implements IWordDetector {

	public boolean isWordStart(char c) {
		return Character.isLetter(c);
	}

	public boolean isWordPart(char c) {
		return Character.isLetter(c);
	}
}
