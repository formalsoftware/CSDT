package simulinkeditor.config;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * @author Cauanne e Gustavo
 */

public final class ColorManager {

	private Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	/**
	 * 
	 * @param rgb
	 * @return
	 */
	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}

	/**
	 * 
	 * 
	 */
	public void dispose() {
		for (Color c : fColorTable.values()) {
			c.dispose();
		}
	}
}