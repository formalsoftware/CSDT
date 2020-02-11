package simulinkeditor.scanner;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

//import com.fware.cspdt.cspm.editor.CspMEditorPlugin;
import simulinkeditor.SimulinkEditorPlugin;
//import com.fware.cspdt.cspm.editor.config.CspMColorManager;
import simulinkeditor.config.ColorManager;
//import com.fware.cspdt.cspm.editor.config.Keywords;
import simulinkeditor.config.Keywords;
//import com.fware.cspdt.cspm.editor.preferences.CspMEditorPreferenceConstants;
import simulinkeditor.preferences.PreferenceConstants;

public class Scanner extends RuleBasedScanner {

	private WordRule processRule;
	private WordRule keywordRule;
	private IToken numberToken;
	private IToken keywordToken;
	private IToken processesToken;
	private IToken defaultToken;
	private static String[] PROCESSES = { "SKIP", "STOP" };
	private IPreferenceStore prefs;
	private PreferenceConstants colorConstant;
	
	public Scanner(ColorManager colorManager) {
		prefs = SimulinkEditorPlugin.getDefault().getPreferenceStore();
		// A token that defines how to color normal text.
		defaultToken = new Token(new TextAttribute(colorManager.getColor(PreferenceConverter.getColor(
				prefs, PreferenceConstants.COLOR_DEFAULT))));
		
		// A token that defines how to color numbers.
		numberToken = new Token(new TextAttribute(colorManager.getColor(PreferenceConverter.getColor(
				prefs, PreferenceConstants.COLOR_INT))));

		// A token that defines how to color keywords.
		keywordToken = new Token(new TextAttribute(colorManager.getColor(PreferenceConverter.getColor(
				prefs, PreferenceConstants.COLOR_KEYWORD)), null, SWT.BOLD));
		
		// A token that defines how to color predefined identifiers.
		processesToken = new Token(new TextAttribute(colorManager.getColor(PreferenceConverter.getColor(
				prefs, PreferenceConstants.COLOR_PROCESS)), null, SWT.ITALIC));
		
		keywordRule = new WordRule(new KeywordDetector());
		Keywords[] values = Keywords.values();
		for (int i = 0; i < values.length; i++) {
			keywordRule.addWord(values[i].getValue(), keywordToken);
		}

		processRule = new WordRule(new NameDetector());
		for (int i = 0; i < PROCESSES.length; i++) {
			processRule.addWord(PROCESSES[i], processesToken);
		}

		IRule[] rules = new IRule[] {
				new WhitespaceRule(new WhitespaceDetector()), 
				keywordRule, 
				processRule, 
				new NumberRule(numberToken) 
		};
		
		setDefaultReturnToken(defaultToken);
		setRules(rules);
	}

	public void addProcess(String processName) {
		if (processName != null && !"".equals(processName)) {
			processRule.addWord(processName, processesToken);
		}
	}
}