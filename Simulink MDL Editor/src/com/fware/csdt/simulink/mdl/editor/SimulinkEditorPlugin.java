package com.fware.csdt.simulink.mdl.editor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SimulinkEditorPlugin extends AbstractUIPlugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "SimulinkEditor";

	/** The shared instance. */
	private static SimulinkEditorPlugin plugin;

	/** Cache for icons. */
	private HashMap<String, Image> imageRegistry;

	/** Used by the current project solving mechanism. */
	protected static IWorkbenchWindow currentWindow;

	/**
	 * Constructs a new CSP SUITE plugin object.
	 */
	public SimulinkEditorPlugin() {
		plugin = this;

		imageRegistry = new HashMap<String, Image>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SimulinkEditorPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the name of the plugin.
	 * 
	 * Used by project creation wizard.
	 * 
	 * @return Unique id of this plugin
	 */
	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Return a value from the plugin's preferences.
	 * 
	 * @param key
	 *            Preference name
	 * @return Value of the named preference
	 */
	public static String getPreference(String key) {
		return getDefault().getPreferenceStore().getString(key);
	}

	/**
	 * Return an image from the plugin's icons-directory.
	 * 
	 * @param name
	 *            Name of the icon
	 * @return The icon as an image object
	 */
	public static Image getImage(String name) {
		return getDefault().getCachedImage(name);
	}

	/**
	 * Cache the image if it is found.
	 * 
	 * @param key
	 *            Name of the image
	 * @return Image from the cache or from disk, null if image is not found in
	 *         either.
	 */
	protected Image getCachedImage(String key) {
		if (key == null) {
			return null;
		}

		Image g = (Image) imageRegistry.get(key);
		if (g != null) {
			return g;
		}

		String path = "icons/" + key;
		if (!key.endsWith(".png")) {
			path += ".gif";
		}

		ImageDescriptor d = ImageDescriptor.createFromURL(getBundle().getEntry(path));
		if (d == null) {
			return null;
		}

		// we want null instead of default missing image
		if (d.equals(ImageDescriptor.getMissingImageDescriptor())) {
			return null;
		}

		g = d.createImage();
		imageRegistry.put(key, g);
		return g;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path (from eclipse help).
	 * 
	 * @param path
	 *            Relative path of the image.
	 * @return The corresponding image descriptor or
	 *         <code>MissingImageDescriptor</code> if none is found.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		String iconPath = "icons/";
		try {
			URL installURL = getDefault().getBundle().getEntry("/");
			URL url = new URL(installURL, iconPath + path + ".gif");
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
			// should not happen
			return ImageDescriptor.getMissingImageDescriptor();
		}
		// imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Returns the current workbench page.
	 * 
	 * Used by getCurrentProject() and by gotoMarker().
	 * 
	 * @return The currently open WorkbenchPage or <code>null</code> if none
	 */
	public static IWorkbenchPage getCurrentWorkbenchPage() {
		IWorkbench workbench = getDefault().getWorkbench();

		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null) {
			Display display = workbench.getDisplay();
			display.syncExec(new Runnable() {
				public void run() {
					currentWindow = getDefault().getWorkbench().getActiveWorkbenchWindow();
				}
			});
			window = currentWindow;
		}

		return window.getActivePage();
	}

	/* LOG */

	/**
	 * This is equivalent to calling <code>stat(msg, null)</code>.
	 * 
	 * @param msg
	 *            Error message to display in error log
	 * @return New Error-level status message
	 */
	public static IStatus stat(String msg) {
		return stat(msg, null);
	}

	/**
	 * Create an error-level status object out of textual message. These
	 * status-objects are needed when creating new CoreExceptions. Used by e.g.
	 * the project creation wizard.
	 * 
	 * @param msg
	 *            Error message to display in error log
	 * @param t
	 *            Exception
	 * @return New error-level status message
	 */
	public static IStatus stat(String msg, Throwable t) {
		return new Status(IStatus.ERROR, getPluginId(), IStatus.ERROR, msg, t);
	}

	/**
	 * Display a message in the Eclipse's Error Log. This is equivalent to
	 * calling <code>log(msg, t, IStatus.ERROR)</code>.
	 * 
	 * @param msg
	 *            Error message to display in error log
	 * @param t
	 *            Exception
	 */
	public static void log(String msg, Throwable t) {
		log(msg, t, IStatus.ERROR);
	}

	/**
	 * Display a message in the Eclipse's Error Log. Used by e.g. the project
	 * creation wizard.
	 * 
	 * @param msg
	 *            Error message
	 * @param t
	 *            Exception
	 * @param level
	 *            One of the error levels defined in the <code>IStatus</code>
	 *            -interface
	 */
	public static void log(String msg, Throwable t, int level) {
		IStatus stat = new Status(level, getPluginId(), level, msg, t);
		getDefault().getLog().log(stat);
	}

}
