package simulinkeditor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import simulinkeditor.SimulinkEditorPlugin;
import simulinkeditor.config.ColorConstants;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 *      initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = SimulinkEditorPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.COLOR_DEFAULT, ColorConstants.DEFAULT.getCor().toString());
		store.setDefault(PreferenceConstants.COLOR_KEYWORD, ColorConstants.KEYWORD.getCor().toString());
		store.setDefault(PreferenceConstants.COLOR_PROCESS, ColorConstants.PROCESS.getCor().toString());
		store.setDefault(PreferenceConstants.COLOR_INT, ColorConstants.INT.getCor().toString());
		store.setDefault(PreferenceConstants.COLOR_COMMENT, ColorConstants.COMMENT.getCor().toString());
		store.setDefault(PreferenceConstants.COLOR_MULTILINE_COMMENT, ColorConstants.MULTILINE_COMMENT.getCor().toString());
	}
}
