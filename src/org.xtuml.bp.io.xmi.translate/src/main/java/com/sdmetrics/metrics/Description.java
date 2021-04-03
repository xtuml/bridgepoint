/*
 * SDMetrics Open Core for UML design measurement
 * Copyright (c) Juergen Wuest
 * To contact the author, see <http://www.sdmetrics.com/Contact.html>.
 * 
 * This file is part of the SDMetrics Open Core.
 * 
 * SDMetrics Open Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
    
 * SDMetrics Open Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with SDMetrics Open Core.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.sdmetrics.metrics;

/**
 * Represents the description of an entry in the metric definition file.
 * <p>
 * Similar to Java docs, the first sentence of the description provides a brief
 * description of the entry. This brief description is usually terminated by a
 * period. Also like Java docs, descriptions contain HTML markup for formatting
 * of paragraphs, bulleted lists etc.
 */
class Description {

	/** Builder for the raw description string during XML parsing. */
	private StringBuilder text = new StringBuilder();
	/** The final description as HTML fragment. */
	private String description = null;

	/**
	 * The separator string that indicates the end of the brief description.
	 */
	private String descriptionSeparator = ".";

	/**
	 * Appends text to the description.
	 * 
	 * @param str Character array that contains the text
	 * @param offset Index of the first character to use
	 * @param len length of the text to use
	 */
	void add(char[] str, int offset, int len) {
		int startIndex = offset;
		int charsToCopy = len;
		if (text.length() == 0) {
			// First text fragment, skip any leading whitespace
			while (charsToCopy > 0 && Character.isWhitespace(str[startIndex])) {
				startIndex++;
				charsToCopy--;
			}
		}
		if (charsToCopy > 0) {
			text.append(str, startIndex, charsToCopy);
		}
	}

	/**
	 * Sets the separator string to indicate the end of the brief description.
	 * By default, this is the period (".")
	 * 
	 * @param sep The new separator string.
	 */
	void setSeparator(String sep) {
		descriptionSeparator = sep;
	}

	/**
	 * Obtains the short version of the description. This is the description
	 * text up to the first occurrence of the separator string, or the entire
	 * description text if the text does not contain the separator string.
	 * 
	 * @return Description summary.
	 */
	String getBriefDescription() {
		getDescription();
		int firstSeparatorIndex = description.indexOf(descriptionSeparator, 0);
		if (firstSeparatorIndex < 0) {
			return description;
		}
		return description.substring(0, firstSeparatorIndex
				+ descriptionSeparator.length());
	}

	/**
	 * Obtains the full description text. This includes the brief description
	 * and all additional description that follows.
	 * 
	 * @return the full description text
	 */
	String getDescription() {
		if (description == null) {
			processHTML(text);
			description = text.toString();
			text = null; // string buffer no longer needed.
		}
		return description;
	}

	/** Fixes the HTML tags and locator references in the description text. */
	private static void processHTML(StringBuilder buf) {
		// replace (( with < and )) with >, and newlines with spaces
		replaceAll(buf, "((", "<");
		replaceAll(buf, "))", ">");
		replaceAll(buf, "\n", " ");

		// turn metric locators etc. into hyperlinks
		for (DescriptionLocator loc : DescriptionLocator.values()) {
			replaceLinks(buf, loc);
		}
	}

	/**
	 * Replaces all occurrences of a string in a string builder.
	 * 
	 * @param buf Text to operate on.
	 * @param what The string in the buffer to be replaced.
	 * @param with The replacement string.
	 */
	private static void replaceAll(StringBuilder buf, String what, String with) {
		int index = 0;
		while ((index = buf.indexOf(what, index)) >= 0) {
			buf.replace(index, index + what.length(), with);
			index += with.length();
		}
	}

	/**
	 * Replaces all occurrences of a locator with HTML hyperlinks.
	 * <p>
	 * The hyperlink has the locator as its target, and the last parameter of
	 * the link as its text. For example the text
	 * <code>metric://class/NumOps/</code> will be replaced by the hyperlink
	 * <code>&lt;a href="metric://class/NumOps/"&gt;NumOps&lt;/a&gt;</code>. In
	 * addition, literature references are set in square brackets so the link
	 * looks likes this: [<a href="ref://BW02/">BW02</a>].
	 * 
	 * @param buf The text to operate on.
	 * @param loc The locator to replace.
	 */
	private static void replaceLinks(StringBuilder buf,
			DescriptionLocator locator) {
		int prefixLength = locator.getPrefix().length();
		int index = 0;
		while ((index = buf.indexOf(locator.getPrefix(), index)) >= 0) {
			// extract the start and end index of the last parameter
			int lastParamStart = index + prefixLength;
			for (int i = 0; i < locator.getParameterCount() - 1; i++) {
				if (lastParamStart > 0) {
					lastParamStart = buf.indexOf("/", lastParamStart) + 1;
				}
			}
			int lastParamEnd = lastParamStart > 0 ? buf.indexOf("/",
					lastParamStart) : -1;

			// replace the HTML for a hyperlink
			if (lastParamEnd >= 0) {
				StringBuilder link = new StringBuilder(); // the hyperlink text
				if (locator == DescriptionLocator.REFERENCE) {
					link.append('[');
				}
				link.append("<a href=\"");
				link.append(buf.substring(index, lastParamEnd + 1));
				link.append("\">");
				String linkText;
				if (lastParamStart == lastParamEnd
						&& locator == DescriptionLocator.GLOSSARY) {
					// last parameter of glossary reference is empty => use
					// first parameter as text.
					int firstParEndInd = buf.indexOf("/", index + prefixLength);
					linkText = buf.substring(index + prefixLength,
							firstParEndInd);
				} else {
					linkText = buf.substring(lastParamStart, lastParamEnd);
				}
				link.append(linkText);
				link.append("</a>");
				if (locator == DescriptionLocator.REFERENCE) {
					link.append(']');
				}

				// replace the locator, keep searching the remaining text
				buf.replace(index, lastParamEnd + 1, link.toString());
				index += link.length();
			} else {
				// no parameters found, leave as is and continue
				index += prefixLength;
			}
		}
	}
}
