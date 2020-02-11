package com.formallab.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Strings {

	public static String removeBrackets(String str) {
        if (str == null) {
            return null;
        }
		return str.replaceAll("\\[", "").replaceAll("\\]", "");
	}

	public static String removeDashs(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\"", "");
    }

	public static String spaces(int len) {
		StringBuilder b = new StringBuilder(len);
		for (int j = 0; j < len; j++) {
			b.append(' ');
		}
		return b.toString();
	}

	public static String[] csvToArray(String commaSeparatedValues) {
		if (commaSeparatedValues == null) {
			return null;
		}
		return commaSeparatedValues.replaceAll(" +", ",").replaceAll("\\n", "").split(",");
	}

	public static String toSeparatedValues(Collection<?> c, String separator, int depth, int maxColumn) {
		StringBuilder temp = new StringBuilder();

		final boolean startsWithSpaces = separator.startsWith(" ");
		final int separatorLen = separator.length() + 1;
		final String spaces = spaces(depth);

		String s;
		int column = depth;

		Iterator<?> it = c.iterator();
		while (it.hasNext()) {
			s = it.next().toString();
			temp.append(s);

			if (it.hasNext()) {
				column += s.length() + separatorLen;
				
				if (column <= maxColumn) {
					temp.append(separator).append(' ');
				} else {
					if (startsWithSpaces) { // Allow putting a new line before putting separator.
						temp.append('\n').append(spaces).append(separator).append(' ');
					} else {
						temp.append(separator).append('\n').append(spaces);
					}
					column = depth;
				}
			}
		}
		return temp.toString();
	}

	public static String toSeparatedValues(Collection<?> set, String separator) {
		return toSeparatedValues(set, separator, 0, Integer.MAX_VALUE);
	}

	/**
	 * Convert to CSV (comma-separated values)
	 * 
	 * @param set
	 * @param depth
	 * @param maxColumn
	 * @return
	 */
	public static String toCsv(Set<?> set, int depth, int maxColumn) {
		return toSeparatedValues(set, ",", depth, maxColumn);
	}

	/**
	 * Convert to CSV (comma-separated values)
	 * 
	 * @param set
	 * @return
	 */
	public static String toCsv(Set<?> set) {
		return toSeparatedValues(set, ",", 0, Integer.MAX_VALUE);
	}

	/**
	 * Convert to CSV (comma-separated values)
	 * 
	 * @param list
	 * @param depth
	 * @param maxColumn
	 * @return
	 */
	public static String toCsv(List<?> list, int depth, int maxColumn) {
		return toSeparatedValues(list, ",", depth, maxColumn);
	}

	/**
	 * Convert to CSV (comma-separated values)
	 * 
	 * @param list
	 * @return
	 */
	public static String toCsv(List<?> list) {
		return toSeparatedValues(list, ",", 0, Integer.MAX_VALUE);
	}

	public static <T> String toSeparatedValues(T[] c, String separator, int depth, int maxColumn) {
		StringBuilder temp = new StringBuilder();

		final boolean startsWithSpaces = separator.startsWith(" ");
		final int separatorLen = separator.length() + 1;
		final String spaces = spaces(depth);

		String s;
		int column = depth;

		for (int i = 0; i < c.length; i++) {
			s = c[i].toString();
			temp.append(s);

			if (i + 1 < c.length) {
				column += s.length() + separatorLen;
				
				if (column <= maxColumn) {
					temp.append(separator).append(' ');
				} else {
					if (startsWithSpaces) { // Allow putting a new line before putting separator.
						temp.append('\n').append(spaces).append(separator).append(' ');
					} else {
						temp.append(separator).append('\n').append(spaces);
					}
					column = depth;
				}
			}
		}
		return temp.toString();
	}

	public static <T> String toCsv(T[] array) {
		return toSeparatedValues(array, ",", 0, Integer.MAX_VALUE);
	}

	public static boolean isNullOrEmpty(String s) {
		return s != null && s.equals("");
	}

}
