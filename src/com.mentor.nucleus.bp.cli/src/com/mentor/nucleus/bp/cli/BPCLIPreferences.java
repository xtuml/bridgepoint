//=====================================================================
//
//File:      $RCSfile: BPCLIPreferences.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/06/12 13:08:01 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
package com.mentor.nucleus.bp.cli;

import org.eclipse.equinox.app.IApplicationContext;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * This class manages command-line options
 *
 */
public class BPCLIPreferences {
	private CommandLineOption[] preferenceData = null;

	public static class CommandLineOption {
		public String key;
		public Object value;
		public String optionDescription;

		public CommandLineOption(String p_key, Object p_value, String p_description) {
			key = p_key;
			value = p_value;
			optionDescription = p_description;
		}
	};


	/**
	 * 
	 * @param context The holds the actual arguments passed from the command-line
	 * @param defaultOptions This array holds all options that are available.
	 *                       It gives a default value for each option.
	 *                       
	 * @throws BPCLIException An Exception is thrown if unsupported arguments are encountered.
	 *                        All arguments must be in the provided list of defaults.  If the
	 *                        actualArguments includes something not in the default it is an 
	 *                        error.
	 */
	public BPCLIPreferences(IApplicationContext appContext,
			CommandLineOption[] defaultOptions) throws BPCLIException {
		preferenceData = defaultOptions;
		Object appArgs = appContext.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);
		if (appArgs instanceof String[]) {
			String[] strs = (String[]) appArgs;
			for (int i = 0; i < strs.length; i++) {
				String key = strs[i];
				i++;
				String value = "";
				if (i < strs.length) {
					// If the current key does not have an associated value
					// then we must take action here to assure we do not
					// skip the next key.
					if (strs[i].isEmpty() || strs[i].charAt(0) != '-') {
						value = strs[i];
					} else {
						i--;
					}
				}
				if (!key.isEmpty()) {
					setPreference(key, value);
				}
			}
		}
	}

	/**
	 * This function is used to update the default arguments with the 
	 * provided data.
	 * 
	 * @param key
	 * @param value
	 * @throws BPCLIException
	 */
	private final void setPreference(String key, String value) throws BPCLIException {
		boolean foundKey = false;
		for (int i = 0; !foundKey && i < preferenceData.length; i++) {
			if (key.equalsIgnoreCase(preferenceData[i].key)) {
				foundKey = true;
				if (preferenceData[i].value instanceof String) {
					if ((String) preferenceData[i].value != "") {
						throw new BPCLIException("The command-line option \""
								+ key + "\""
								+ " was specified more than one time.");
					}
					preferenceData[i].value = value;
				} else if (preferenceData[i].value instanceof Integer) {
					foundKey = true;
					preferenceData[i].value = value;
				} else if (preferenceData[i].value instanceof Boolean) {
					foundKey = true;
					preferenceData[i].value = true;
				}
			}
		}
		
		if (!foundKey) {
			throw new BPCLIException(
					"An unsupported command-line argument was specified: "
							+ key + ".  For help use the -help option.");
		}
	}

	/**
	 * Display a usage screen for this given command
	 */
	public void usage(String command) {
		System.out.println("Options for the "
				+ command + " command.");
		for (int i = 0; i < preferenceData.length; i++) {
			System.out.println("\t" + preferenceData[i].key + " = "
					+ preferenceData[i].optionDescription);
		}
	}

	/**
	 * Find the given option and return its value.
	 * 
	 * @param opt The option to find the value for.
	 * @return The valion for the given option
	 * @throws BPCLIException An exeception is thrown if the given option is not valid.
	 */
	private Object getValue(String opt) throws BPCLIException {
		Object result = null;
		boolean foundKey = false;
		for (int i = 0; !foundKey && i < preferenceData.length; i++) {
			if (opt.equalsIgnoreCase(preferenceData[i].key)) {
				foundKey = true;
				result = preferenceData[i].value;
			}
		}
		
		if (result == null) {
			throw new BPCLIException("The requested option, " + opt + ", was not found.");
		}
		
		return result;
	}

	public String getStringValue(String opt) {
		String result = null;
		try {
			Object temp = getValue(opt);
			if (temp instanceof String) {
				result = (String)temp;
			} else {
				BPCLIPreferences.logError("The requested option was not of type String: " + opt, null);
			}
		} catch (Exception e) {
			BPCLIPreferences.logError(e.getMessage(), e);
		}
		return result;
	}
	
	public boolean getBooleanValue(String opt) {
		Boolean result = null;
		try {
			Object temp = getValue(opt);
			if (temp instanceof Boolean) {
				result = (Boolean)temp;
			} else {
				BPCLIPreferences.logError("The requested option was not of type String: " + opt, null);
			}
		} catch (Exception e) {
			BPCLIPreferences.logError(e.getMessage(), e);
		}
		return result;
	}
	
	public static void logError(String msg, Throwable e) {
		System.err.println("Error! " + msg);
		if (e != null) {
			e.printStackTrace();
		}
	}
}
