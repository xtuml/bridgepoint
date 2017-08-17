//====================================================================
//
//File:      $RCSfile: DimensionsValidator.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:38:52 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package org.xtuml.bp.core.ui.cells.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.xtuml.bp.core.ConstantSpecification_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.LeafSymbolicConstant_c;
import org.xtuml.bp.core.LiteralSymbolicConstant_c;
import org.xtuml.bp.core.SymbolicConstant_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;

public class DimensionsValidator implements ICellEditorValidator {
    

	/**
	 * This regular expression allows dimensions in the form "[x][][y]...".
	 * It allows both fixed-length and dynamically-sized arrays. 
	 * It allows spaces to exist between the dimensions and inside 
	 * the dimensions (it just ignores the spaces).
	 */
	public final static String DimensionRegExpAllowFLAsAllowDSAs = "(\\s*\\[\\s*((\\w+::)?\\w)*\\s*\\])*\\s*";
	public final static String DimensionHelpAllowFLAsAllowDSAs = "Dimensions must be entered as follows: [x][y][][z]...";//$NON-NLS-1$
	
	/**
	 * This regular expression allows dimensions in the form "[x][y][z]...".
	 * It allows only fixed-length arrays. 
	 * It allows spaces to exist between the dimensions and inside 
	 * the dimensions (it just ignores the spaces).
	 */
	public final static String DimensionRegExpAllowFLAsDisAllowDSAs = "(\\s*\\[\\s*((\\w+::)?\\w)+\\s*\\])*\\s*";
	public final static String DimensionHelpAllowFLAsDisAllowDSAs = "Dimensions must be entered as follows: [x][y][z]...";//$NON-NLS-1$
	
	/**
	 * This regular expression allows dimensions in the form "[][][]...".
	 * It allows only dynamically-sized arrays. 
	 * It allows spaces to exist between the dimensions and inside 
	 * the dimensions (it just ignores the spaces).
	 * 
	 */
	public final static String DimensionRegExpDisAllowFLAsAllowDSAs = "(\\s*\\[\\s*\\])*\\s*";
	public final static String DimensionHelpDisAllowFLAsAllowDSAs = "Dimensions must be entered as follows: [][][]...";//$NON-NLS-1$
	
	/**
	 * Don't allow anything
	 */
	public final static String DimensionRegExpDisAllowFLAsDisAllowDSAs = "$can't be anything after the end of a string so this will never match";
	public final static String DimensionHelpDisAllowFLAsDisAllowDSAs = "Arrays are disabled.";//$NON-NLS-1$
    
	/**
	 * Warning prepended to the help-text when a bad string is being entered.
	 */
	public final static String DimensionHelpWarning = "Warning: The format is not correct.  ";//$NON-NLS-1$
	
	public final static String DimensionsNotAllowed = "Dimensions are not allowed."; //$NON-NLS-1$
	private NonRootModelElement m_inst;
	
    /**
     * Constructor.
     */
    public DimensionsValidator() 
    {
    }
    public DimensionsValidator(NonRootModelElement p_inst ) 
    {
    	m_inst = p_inst;
    }


    /**
     * Determine the regular expression to use by looking at the 
     * preference seeting to see if fixed-length and/or dynamically-sized
     * arrays are allowed.
     * 
     * @return Regular expression used to validate user input.
     */
    public static String getDimensionsRegExpression() 
    {
		String regExpression;
		
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        Boolean enableFLAs = store.getBoolean(BridgePointPreferencesStore.ENABLE_FIXED_LENGTH_ARRAYS);
        Boolean enableDSAs = store.getBoolean(BridgePointPreferencesStore.ENABLE_DYNAMICALLY_SIZED_ARRAYS);        
		
		if (!enableFLAs && !enableDSAs) {
			regExpression = DimensionRegExpDisAllowFLAsDisAllowDSAs;
		} else if (enableFLAs && !enableDSAs) {
			regExpression = DimensionRegExpAllowFLAsDisAllowDSAs;
		} else if (!enableFLAs && enableDSAs) {
			regExpression = DimensionRegExpDisAllowFLAsAllowDSAs;
		} else { // ( enableFLAs && enableDSAs )
			regExpression = DimensionRegExpAllowFLAsAllowDSAs;
		}
		return regExpression;
    }
    
    /**
     * Determine the validation error message to display by looking at the
     * preference seeting to see if fixed-length and/or dynamically-sized
     * arrays are allowed.
     * 
     * @return Message to display to the user when an incorrect string is
     *         being entered.
     */
    public static String getDimensionsHelpTextWithWarning(boolean addWarning)
    {
		String dimensionHelp;
		
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        Boolean enableFLAs = store.getBoolean(BridgePointPreferencesStore.ENABLE_FIXED_LENGTH_ARRAYS);
        Boolean enableDSAs = store.getBoolean(BridgePointPreferencesStore.ENABLE_DYNAMICALLY_SIZED_ARRAYS);
		
		if (!enableFLAs && !enableDSAs) {
			dimensionHelp = DimensionHelpDisAllowFLAsDisAllowDSAs;
		} else if (enableFLAs && !enableDSAs) {
			dimensionHelp = DimensionHelpAllowFLAsDisAllowDSAs;
		} else if (!enableFLAs && enableDSAs) {
			dimensionHelp = DimensionHelpDisAllowFLAsAllowDSAs;
		} else { // ( enableFLAs && enableDSAs )
			dimensionHelp = DimensionHelpAllowFLAsAllowDSAs;
		}
		
		if (addWarning) {
			return DimensionHelpWarning + dimensionHelp;
		} else {
			return dimensionHelp;
		}
    }

    /**
     * @see getDimensionsHelpTextWithWarning
     */
    public static String getDimensionsHelpText( boolean readonly ) {
    	if (readonly) {
    		return DimensionsNotAllowed;
    	} else {
    		return getDimensionsHelpTextWithWarning(false);
    	}
    }
    
	/**
	 * 
	 * @return Empty string if the given object is valid, and 
	 * a string indicating the problem when the given
	 * object is not valid.
	 * 
	 */
	public String isValid(Object obj){
		String regExpression = getDimensionsRegExpression();
		String result = "";

		String dimensions = (String) obj;
		if ( !dimensions.equals("") && !dimensions.matches(regExpression) ) {
			result = getDimensionsHelpTextWithWarning(true);
			return result;
		}
		dimensions = dimensions.replace(" ", "");
		Pattern ZeroDimension = Pattern.compile("\\[0+\\]");
		Matcher ZeroDimensionMatch = ZeroDimension.matcher(dimensions);
		if ( ZeroDimensionMatch.find()){
			result = "Only non-zero positive integer values are allowed in array dimensions";
			return result;
		}
		
        Pattern pattern = Pattern.compile("\\[((\\w*([a-z]|[A-Z])\\w*)::)?(\\w*([a-z]|[A-Z])\\w*)\\]");
        Matcher match = pattern.matcher(dimensions);
        while ( match.find()){
            final String csp_name = match.group(2);
            final String const_name = match.group(4);
            SymbolicConstant_c[] sycs = null;
            if ( null == csp_name ) { // non scoped constant access
                sycs = SymbolicConstant_c.SymbolicConstantInstances( m_inst.getModelRoot(), new ClassQueryInterface_c() {
                    @Override
                    public boolean evaluate(Object candidate) {
                        return ((SymbolicConstant_c)candidate).getName().equals(const_name);
                    }
                });
            }
            else {
                ConstantSpecification_c[] csps = ConstantSpecification_c.ConstantSpecificationInstances( m_inst.getModelRoot(), new ClassQueryInterface_c() {
                    @Override
                    public boolean evaluate(Object candidate) {
                        return ((ConstantSpecification_c)candidate).getInformalgroupname().equals(csp_name);
                    }
                });
                if ( csps.length == 0 ) {
                   result = "No constant specification found with name " + csp_name;
                   return result;
                }
                else if ( csps.length > 1 ) {
                   result = "Multiple constant specifications found with name " + csp_name;
                   return result;
                }
                sycs = SymbolicConstant_c.getManyCNST_SYCsOnR1504( csps, new ClassQueryInterface_c() {
                    @Override
                    public boolean evaluate(Object candidate) {
                        return ((SymbolicConstant_c)candidate).getName().equals(const_name);
                    }
                });
            }
            if (sycs.length != 1) {
                result = const_name + " is not a valid constant name";
                if ( null != csp_name ) {
                    result = csp_name + "::" + result;
                }
                return result;
            }
            String constantValue = LiteralSymbolicConstant_c.getOneCNST_LSCOnR1503(LeafSymbolicConstant_c.getOneCNST_LFSCOnR1502(sycs[0])).getValue();
            if ( !DataType_c.getOneS_DTOnR1500(sycs[0]).getName().equalsIgnoreCase("integer")
                 || constantValue.charAt(0) == '-'
                 || constantValue.equalsIgnoreCase("0")) {
                result = "Only non-zero positive integer values are allowed in array dimensions";
                return result;
            }
        }
		return result;
	}
}
