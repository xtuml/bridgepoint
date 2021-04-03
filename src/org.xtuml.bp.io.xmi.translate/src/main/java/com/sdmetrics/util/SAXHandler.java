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
package com.sdmetrics.util;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Base class for SDMetrics' XML handlers. Provides locator information and
 * version attribute checking.
 */
public class SAXHandler extends DefaultHandler {
	/** Locator of the handler. */
	protected Locator locator;

	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	/**
	 * Tests if the indicated "version" number in an XML document is still
	 * supported.
	 * 
	 * @param attrs The list of XML attributes of a document's root element.
	 * @param since The smallest version number that can be accepted, or
	 *        <code>null</code> if all known versions are accepted.
	 * @throws SAXException If no "version" attribute exists, or if the
	 *         indicated version is not known or no longer accepted.
	 * @throws IllegalArgumentException If the "since" version is not known.
	 */
	protected void checkVersion(Attributes attrs, String since)
			throws SAXException {
		String vstring = attrs.getValue("version");
		if (vstring == null) {
			throw buildSAXException("Version number specification missing.");
		}

		int index = VersionInfo.XML_FILE_VERSIONS.indexOf(vstring);
		if (index < 0) {
			throw buildSAXException("File version \"" + vstring + "\" not supported.");
		}

		if (since != null) {
			int sinceind = VersionInfo.XML_FILE_VERSIONS.indexOf(since);
			if (sinceind < 0) {
				throw new IllegalArgumentException("Since file version \""
						+ since + "\" unknown.");
			}
			if (index < sinceind) {
				throw buildSAXException("File version \"" + vstring
						+ "\" not supported anymore.");
			}
		}
	}

	/**
	 * Reports a severe error that calls for a SAX Exception. The method
	 * prepends the error message with file location information and throws a
	 * SAXException.
	 * 
	 * @param message Message of the SAXException
	 * @throws SAXException To report the error to the SAX error handler.
	 * @deprecated Use {@link #buildSAXException(String)} and throw the
	 * exception yourself.
	 */
	@Deprecated
	protected void reportError(String message) throws SAXException {
		throw buildSAXException(message);
	}
	
	/**
	 * Builds a SAXException with a given message text. The method
	 * prepends the error message with file location information.
	 * The exception object is returned, but not yet thrown. Callers must
	 * throw the exception themselves.
	 * SAXException.
	 * 
	 * @param message Message of the SAXException
	 * @return SAXException with that message and location information.
	 */
	protected SAXException buildSAXException(String message) {
		StringBuilder msg = new StringBuilder();
		if (locator != null) {
			msg.append("Error in line ");
			msg.append(locator.getLineNumber());
			msg.append(": ");
		}
		msg.append(message);
		return new SAXException(msg.toString());
	}
}
