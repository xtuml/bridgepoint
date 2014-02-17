package lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.VariablesPlugin;

import com.mentor.nucleus.bp.core.AttributeValue_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BlockInStackFrame_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceHandle_c;
import com.mentor.nucleus.bp.core.InstanceInReference_c;
import com.mentor.nucleus.bp.core.InstanceReferenceValue_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.LocalReference_c;
import com.mentor.nucleus.bp.core.LocalValue_c;
import com.mentor.nucleus.bp.core.Local_c;
import com.mentor.nucleus.bp.core.RuntimeValue_c;
import com.mentor.nucleus.bp.core.SimpleValue_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.TransientVar_c;
import com.mentor.nucleus.bp.core.Variable_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;

//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

public class T {
	
	private static String outputBuffer = new String();
	
	private static String templatePath = "";
	
	public static void include(StackFrame_c stackFrame, String templateFileName) {
		// Obtain the set of local values in the calling context
		Object persistenceResource = stackFrame.getModelRoot().getPersistenceFile();
        if (persistenceResource instanceof IFile) {
        	templatePath = ((IFile)persistenceResource).getProject().
        	                getLocation().append("gen\\").toOSString();
        }
		
	    // Open the file
		File templateFile = new File(templatePath + templateFileName);
		try {
		  BufferedReader br = new BufferedReader(new FileReader(templateFile));

		  // Get ready to read and match
		  String inputBuffer = "";
		  while (inputBuffer != null) {
			inputBuffer = br.readLine();
			if (inputBuffer != null) {
			  outputBuffer += process(stackFrame, inputBuffer) + "\n";
			}
		  }
		  br.close();
		}
		catch (FileNotFoundException fne) {
			CorePlugin.logError("File not found.", fne);
		}
		catch (IOException ioe) {
			CorePlugin.logError("Unable to read file.", ioe);
		}
	}
	
	public static String template(StackFrame_c stackFrame, String substitutionSpec) {
		// Obtain the set of local values in the calling context
		return process(stackFrame, substitutionSpec);
	}

	public static String process(StackFrame_c stackFrame, String input) {
		  // Set up the matching expression
	      // Pattern below matches:
		  // "$<up to 2 format characters>{<a sequence of alpha characters, possibly containing a '.'>}"
		  // The optional 'eclipse:' prefix to the name inside the braces allows
          // specification of eclipse variable values in the output.
		  // Also sets up 2 to 4 groups with the useful string values.
		  String pattern = "\\$([UuCcLl_RrOoXx]{0,2})\\{(eclipse:)?([\\w]+)(\\.[\\w]+)?\\}";
		  Pattern pat = Pattern.compile(pattern);

		  Matcher mat = pat.matcher(input);
		  
		  String result = "";
		  int posn = 0;
		  boolean eclipseVar = false;
		  while (mat.find(posn)) {
			MatchResult res = mat.toMatchResult();
			String plainText = input.substring(posn, res.start());
			posn = res.end();
			String formatChars = mat.group(1);
			if (mat.group(2) != null) {
				eclipseVar = true;
			}
			String instRefName = mat.group(3);
			String attrName = "";
			if (mat.group(4) != null) {
				attrName = mat.group(4).substring(1); // lose a leading '.'
			}
			if (eclipseVar) {
			  result += plainText + format(formatChars, getEclipseVar(instRefName));	
			}
			else {
			  result += plainText + format(formatChars,
					                          getValue(stackFrame,
                                                    instRefName, attrName));
			}
		  }
		  // Copy out trailing plain text
		  return result + input.substring(posn);
    }
	private static String getEclipseVar(String instRefName) {
		try {
		  return VariablesPlugin.getDefault().getStringVariableManager().
		                    performStringSubstitution("${" + instRefName + "}");
		}
		catch (CoreException ce){
			CorePlugin.logError(
				    "Error obtaining the value of eclipse variable, '" +
                                                         instRefName +"'.", ce);			
		}
		return null;
	}

	private static String format(String formatChars, String substitute) {
		String toFormat = "";
		String lcFormatChars = formatChars.toLowerCase();
		if (substitute != null) {
		  toFormat = substitute;
		  if (lcFormatChars.contains("l")) {
			toFormat = toFormat.toLowerCase(); 
		  }
		  else {
			if (lcFormatChars.contains("u")) {
				toFormat = toFormat.toUpperCase();
			}
		  }
		  String [] nonWS = toFormat.split(" ");
		  String sep = "";
		  toFormat = "";
		  for (int i = 0; i < nonWS.length; i++) {
		    if (lcFormatChars.contains("c")) {
			  // Capitalize the first letter
			  nonWS[i] = nonWS[i].substring(0, 1).toUpperCase() +
                                                          nonWS[i].substring(1);
		    }
		    toFormat += sep + nonWS[i];
		    if (lcFormatChars.contains("_")) {
		      sep = "_";
		    }
		    else if (!lcFormatChars.toLowerCase().contains("r")) {
			  // If r is _not_ present, separator is a space,
			  sep = " ";
		    }
		    // else its the empty string because we want to concatenate.
		  }
		}
		if (lcFormatChars.contains("x")) {
			return xmlify(toFormat);
		}
		else {
		  return toFormat;
		}
	}

	public static String getValue(StackFrame_c stackFrame,
			                        final String instRefName, String optionalAttrName) {
        Local_c[] locals = Local_c.getManyL_LCLsOnR3000(
        		          BlockInStackFrame_c.getManyI_BSFsOnR2923(stackFrame));
		if (optionalAttrName != "") {
		  // select many variables related by locals->L_LCR[R3001]->
		  //       V_INT[R3004]->V_VAR[R814] where selected.Name == instRefName;
		
		  class VariableTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				Variable_c selected = (Variable_c) candidate;
				return selected.getName().equals(instRefName);
			}
		  }
		  Variable_c [] vars = Variable_c.getManyV_VARsOnR814(
				           InstanceHandle_c.getManyV_INTsOnR3004(
						     LocalReference_c.getManyL_LCRsOnR3001(locals)),
                                                            new VariableTest());
		  // if cardinality vars == 1
		  if (vars.length == 1) {
			// There is exactly one variable of that name in scope
			// select many filteredLocals related by vars->V_INT[R814]->
			//                                       L_LCR[R3004]->L_LCL[R3001];
			Local_c [] filteredLocals = Local_c.getManyL_LCLsOnR3001(
					      LocalReference_c.getManyL_LCRsOnR3004(
							    InstanceHandle_c.getManyV_INTsOnR814(vars[0])));
			// for each local in filteredLocals
			boolean localFound = false;
			Local_c local = null;
			for (int i = 0; i < filteredLocals.length; i++) {
				local = filteredLocals[i];
				// for each passedLocal in locals
				for (int j = 0; j < locals.length; j++) {
					Local_c passedLocal = locals[j];
					// if local == passedLocal
					if (local.equals(passedLocal)) {
						localFound = true;
						break;
					}
				}
				if (localFound) {
					break;
				}
			}
			if (localFound) {
			  // select many instances related by local->RV_RVL[R3306]->
			  //       RV_SMV[R3300]->RV_IRV[R3308]->L_IIR[R3311]->I_INS[R3013];
			  Instance_c [] instances = Instance_c.getManyI_INSsOnR3013(
					  InstanceInReference_c.getManyL_IIRsOnR3311(
							  InstanceReferenceValue_c.getOneRV_IRVOnR3308(
									SimpleValue_c.getOneRV_SMVOnR3300(
										RuntimeValue_c.getOneRV_RVLOnR3306(
												                     local)))));
			  // if cardinality instances == 1
			  if (instances.length == 1) {
				// select many attribute values related by instances->
				//                                                 I_AVL[R2909];
				AttributeValue_c [] avls =
                            AttributeValue_c.getManyI_AVLsOnR2909(instances[0]);
				// for each avl in avls
				for (int i = 0; i < avls.length; i++) {
					AttributeValue_c avl = avls[i];
					// select one attr related by avl->O_ATTR[R2910];
					Attribute_c attr = Attribute_c.getOneO_ATTROnR2910(avl);
					// if attr.Name == attrName
					if (attr.getName().equals(optionalAttrName)) {
						return avl.getValue();
					}
				}
				// If we get here, the attribute name was not found
				CorePlugin.logError(
				    "Cannot find attribute value for " + instRefName + "."
				                                      + optionalAttrName +
                 ". The attribute value must have been written by Verifier," +
                                  " even if the desired value is empty.", null);
			  }
			  else {
				CorePlugin.logError(
						    "Cannot reference a set in a template file.", null);
			  }
				  
			}
			else {
			  if (vars.length == 0) {
				CorePlugin.logError("Variable " + instRefName +
                                                           " not found.", null);
			  }
			  else {
				CorePlugin.logError("More than one variable " +
                                                 instRefName + " found.", null);
			  }
			}
		  }
		}
		else {
		  // select many variables related by locals->L_LVL[R3001]->
		  //       V_TRN[R3005]->V_VAR[R814] where selected.Name == instRefName;
			
		  class VariableTest implements ClassQueryInterface_c {
			public boolean evaluate(Object candidate) {
				Variable_c selected = (Variable_c) candidate;
				return selected.getName().equals(instRefName);
			}
		  }
		  Variable_c [] vars = Variable_c.getManyV_VARsOnR814(
					           TransientVar_c.getManyV_TRNsOnR3005(
							     LocalValue_c.getManyL_LVLsOnR3001(locals)),
	                                                        new VariableTest());
		  // if cardinality vars == 1
		  if (vars.length == 1) {
			// There is exactly one variable of that name in scope
			// select many filteredLocals related by vars->V_INT[R814]->
			//                                       L_LCR[R3004]->L_LCL[R3001];
			Local_c [] filteredLocals = Local_c.getManyL_LCLsOnR3001(
						      LocalValue_c.getManyL_LVLsOnR3005(
								  TransientVar_c.getManyV_TRNsOnR814(vars[0])));
			// for each local in filteredLocals
			boolean localFound = false;
			Local_c local = null;
			for (int i = 0; i < filteredLocals.length; i++) {
				local = filteredLocals[i];
				// for each passedLocal in locals
				for (int j = 0; j < locals.length; j++) {
					Local_c passedLocal = locals[j];
					// if local == passedLocal
					if (local.equals(passedLocal)) {
						localFound = true;
						break;
					}
				}
				if (localFound) {
					break;
				}
			}
			if (localFound) {
				RuntimeValue_c rv = RuntimeValue_c.getOneRV_RVLOnR3306(local);
				Object value = rv.Getvalue().toString();
				if (value instanceof String || value instanceof Integer ||
                                                       value instanceof Float) {
				  return rv.Getvalue().toString();
				}
				else {
					CorePlugin.logError("Substitution variable: " +
                                                             instRefName +
                             " must be a string, integer or real value.", null);
				}
			}
			else {
				CorePlugin.logError("Substitution variable: " +
                                             instRefName + " not found.", null);
			}
	      }
		  else {
			if (vars.length == 0) {
			  CorePlugin.logError("Variable " + instRefName +
                                                           " not found.", null);
			}
			else {
			  CorePlugin.logError("More than one variable " +
                                                 instRefName + " found.", null);
			}
		  }
		}
		return null;
	}

	private static String xmlify(String input) {
		String result = null;
		if (input != null) {
			result = new String();
			StringCharacterIterator sci = new StringCharacterIterator(input);
			char character = sci.current();
			while (character != CharacterIterator.DONE) {
				if (character == '<') {
					result += "&lt;";
				}
				else if (character == '>') {
					result += "&gt;";
				}
				else if (character == '&') {
					result += "&amp;";
				}
				else if (character == '\"') {
					result += "&quot;";
				}
				else if (character == '\'') {
					result += "&apos;";
				}
				else {
					result += character;
				}
				character = sci.next();
			}
		}
		return result;
	}

	public static void emit(StackFrame_c stackFrame, String outputFileName) {
		Object persistenceResource = stackFrame.getModelRoot().getPersistenceFile();
        if (persistenceResource instanceof IFile) {
        	templatePath = ((IFile)persistenceResource).getProject().
        	                getLocation().append("gen\\").toOSString();
        }
		
		File outputFile = new File(templatePath + outputFileName);
		try {
		  BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		  bw.append(outputBuffer);
		  bw.flush();
		  bw.close();
		}
		catch (FileNotFoundException fne) {
			CorePlugin.logError("File not found.", fne);
		}
		catch (IOException ioe) {
			CorePlugin.logError("Unable to open file for writing.", ioe);
		}
		// Docgen assumes that files have an append functionality.
		// Otherwise there would be a call to clear() here.
	}
	
	public static void clear() {
		outputBuffer = "";
	}
}
