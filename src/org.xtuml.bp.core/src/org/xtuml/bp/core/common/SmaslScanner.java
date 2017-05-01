//
package org.xtuml.bp.core.common;

import java.util.Scanner;

public class SmaslScanner {

	private Scanner smasl;

	public SmaslScanner(String smasl_string) {
		if (null == smasl_string) {
			smasl = null;
		} else {
			smasl = new Scanner(smasl_string);
		}
	}

	public boolean hasNext() {
		if (null == smasl)
			return false;
		return smasl.hasNextLine();
	}

	public String[] next() {
		if (hasNext()) {
			String line = smasl.nextLine();
			return line.split(",", 9);
		} else {
			return null;
		}
	}

}
