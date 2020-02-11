package simulinkeditor.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
//import org.eclipse.jface.preference.BooleanFieldEditor;
//import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
//import org.eclipse.jface.preference.RadioGroupFieldEditor;
//import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import simulinkeditor.SimulinkEditorPlugin;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(SimulinkEditorPlugin.getDefault().getPreferenceStore());
		setDescription("CspM Preferences Page");
	}

	public void createFieldEditors() {
//		StringFieldEditor fieldEditor = new StringFieldEditor(CspMEditorPreferenceConstants.PREFIX_COLOR, "Folder Name: ", getFieldEditorParent());
//		addField(fieldEditor); 
		
		ColorFieldEditor defaultColor = new ColorFieldEditor(
				PreferenceConstants.COLOR_DEFAULT,
				"Default:",
		 		getFieldEditorParent());
			addField(defaultColor);
		
		ColorFieldEditor keywordColor = new ColorFieldEditor(
				PreferenceConstants.COLOR_KEYWORD,
				"Keywords:",
		 		getFieldEditorParent());
			addField(keywordColor);
			
		ColorFieldEditor processColor = new ColorFieldEditor(
				PreferenceConstants.COLOR_PROCESS,
				"Processes:",
		 		getFieldEditorParent());
			addField(processColor);	
			
		ColorFieldEditor intColor = new ColorFieldEditor(
				PreferenceConstants.COLOR_INT,
				"INT:",
		 		getFieldEditorParent());
			addField(intColor);	
			
		ColorFieldEditor commentColor = new ColorFieldEditor(
				PreferenceConstants.COLOR_COMMENT,
				"C&omments:",
		 		getFieldEditorParent());
			addField(commentColor);
			
		ColorFieldEditor multilineCommentColor = new ColorFieldEditor(
				PreferenceConstants.COLOR_MULTILINE_COMMENT,
				"Multiline C&omments:",
		 		getFieldEditorParent());
			addField(multilineCommentColor);	
	}

	
	public void init(IWorkbench workbench) {
	}
}