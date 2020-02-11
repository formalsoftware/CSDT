package simulinkeditor.preferences;

import simulinkeditor.SimulinkEditorPlugin;

import simulinkeditor.config.ColorConstants;

/**
 * Constant definitions for plug-in preferences
 */
public interface PreferenceConstants {

	public static final String PREFIX = SimulinkEditorPlugin.PLUGIN_ID + ".";
	public static final String PREFIX_COLOR = PREFIX + "color.";
	public static final String COLOR_DEFAULT = PREFIX_COLOR + ColorConstants.DEFAULT;
	public static final String COLOR_KEYWORD = PREFIX_COLOR + ColorConstants.KEYWORD;
	public static final String COLOR_PROCESS = PREFIX_COLOR + ColorConstants.PROCESS;
	public static final String COLOR_INT = PREFIX_COLOR + ColorConstants.INT;
	public static final String COLOR_COMMENT = PREFIX_COLOR + ColorConstants.COMMENT;
	public static final String COLOR_MULTILINE_COMMENT = PREFIX_COLOR + ColorConstants.MULTILINE_COMMENT;

}