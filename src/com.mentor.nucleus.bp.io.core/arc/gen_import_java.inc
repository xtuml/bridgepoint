.//====================================================================
.//
.// File:      $RCSfile: gen_import_java.inc,v $
.// Version:   $Revision: 1.111 $
.// Modified:  $Date: 2013/06/12 13:08:05 $
.//
.// (c) Copyright 2007-2014 by Mentor Graphics Corp.  All rights reserved.
.//
.//====================================================================
.//
.// Generate the Java code that validates/translates data from BP SQL
.// into a format that Tiger uses.
.//
.//====================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.include "../com.mentor.nucleus.bp.io.core/arc/import_functions.inc"
.//
.include "../com.mentor.nucleus.bp.core/color/ooaofooa_package_spec.clr"
.//
.function sourceVariables
  .invoke package = get_package()
  .if(package.application_root_class == "Ooaofooa")
    .invoke compMark = markComponentsAndContainments()
  .end if
  .assign attr_main_class_name = "${package.application_root_class}"
  .// Subordinate package: Canvas
  .assign attr_sub_class_name = "Ooaofgraphics"
.end function
.//
.//====================================================================
.//
.function get_column_number
  .param inst_ref p_col   .// C
  .//
  .select one table related by p_col->T[R5]
  .select many tcol_set related by table->C[R5]
  .select any last_tcol related by table->C[R5] where ( selected.nextName == "" )
  .assign x = (cardinality tcol_set) - 1
  .while ( x >= 0 )
    .if ( last_tcol.Name == p_col.Name )
      .break while
    .end if
    .assign x = x - 1
    .select one last_tcol related by last_tcol->C[R6.'follows']
  .end while
  .assign attr_result = x
.end function
.//
.//====================================================================
.//
.function generate_params
  .param inst_ref table
    .param string callingProject
    .select many col_set related by table->C[R5];
    .assign attr_col_num = 0;
    .// for each column
    .select any col related by table->C[R5] where (selected.nextName == "")
    .for each col in col_set
        .// if this column doesn't follow any other
        .select one follows related by col->C[R6.'follows'];
        .if (empty follows)
            .// it is the starting column in the order, and
            .// the one we should start with
            .break for
        .end if
    .end for
    .//
    .while (not_empty col)
      .assign post_string = ""
      .assign special_case = false
      .if (( (table.Key_Lett == "CA_SMSMC") and (col.Name == "DIObj_ID") ) and (callingProject == "BP") )
        .//
        .// this column is special, because it isn't always output
        .// due to a bug in Model Builder bp issue 2837
        .// so just set it to the value parsed above
           val4\
        .assign special_case = true
      .elif (( (table.Key_Lett == "CA_SMSMC") and (col.Name == "OIObj_ID")) and (callingProject == "BP") )
        .//
        .// this column is special, because BP outputs the last
        .// two attributes in reverse order.  If the ooaofooa
        .// model is changed to have the same order as BP, this reversal can be removed.
           IdAssigner.createUUIDFromString((String)parms.elementAt(3))\
        .assign special_case = true
      .elif (( (table.Key_Lett == "GD_MD") and (col.Name == "ScrollXPos")) and (callingProject == "BP") )
        .//
        .// this column is special, because the scroll position is -1 for
        .// diagrams that have never been viewed.
           scrollx\
        .assign special_case = true
      .elif (( (table.Key_Lett == "GD_MD") and (col.Name == "ScrollYPos")) and (callingProject == "BP") )
        .//
        .// this column is special, because the vertical scroll
        .// position needs to be adjusted for the different coordinate
        .// systems between BP 6.1 and Tiger
        .// assume that the ZoomFactor attribute is the next column
        .assign zf_col_num = attr_col_num + 1
           6000 - scrolly - (int)(621/Float.parseFloat((String)parms.elementAt(${zf_col_num})))\
        .assign special_case = true
      .elif(((table.Key_Lett == "GD_MD") and (col.Name == "version")) and (callingProject == "NBP"))
	    .// add an empty string for this column
		((parms.size() > ${attr_col_num}) && parms.elementAt(${attr_col_num}) != null) ? removeTics((String) parms.elementAt(${attr_col_num})):""\
		.assign special_case = true
      .elif(((table.Key_Lett == "GD_MD") and (col.Name == "represents_path")) and (callingProject == "NBP"))
	    .// add an empty string for this column
		((parms.size() > ${attr_col_num}) && parms.elementAt(${attr_col_num}) != null) ? removeTics((String) parms.elementAt(${attr_col_num})):""\
		.assign special_case = true
      .elif(((table.Key_Lett == "GD_GE") and (col.Name == "represents_path")) and (callingProject == "NBP"))
	    .// add an empty string for this column
		((parms.size() > ${attr_col_num}) && parms.elementAt(${attr_col_num}) != null) ? removeTics((String) parms.elementAt(${attr_col_num})):""\
		.assign special_case = true
	  .elif (( (table.Key_Lett == "R_RTO") and (col.Name == "Oid_ID")) and (callingProject == "BP") )
        .//
        .// this column is special, change 99 to -1
           Integer.parseInt((String)parms.elementAt(${attr_col_num}))==99?-1:Integer.parseInt((String)parms.elementAt(${attr_col_num}))\
        .assign special_case = true
      .elif ( table.Key_Lett == "O_TFR")
        .if ((col.Name == "Suc_Pars") and (callingProject == "BP"))
          .//
          .// this column is special, we need to add a new default attribute value
           Integer.parseInt((String)parms.elementAt(${attr_col_num})),
           "",
           Gd_c.Null_unique_id()\
          .assign special_case = true
        .elif ((col.Name == "Previous_Tfr_ID") and (callingProject == "NBP"))
           parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
           .assign special_case = true
        .end if
      .elif ( ((table.Key_Lett == "S_BRG") OR (table.Key_Lett == "S_SYNC")) )
        .if ((col.Name == "Suc_Pars") and (callingProject == "BP"))
          .//
          .// this column is special, we need to add a new default attribute value
           Integer.parseInt((String)parms.elementAt(${attr_col_num})),
           ""\
          .assign special_case = true
        .end if
      .elif ( ((table.Key_Lett == "S_BPARM") OR (table.Key_Lett == "S_SPARM")) OR (table.Key_Lett == "O_TPARM"))
        .if ((col.Name == "By_Ref") and (callingProject == "BP"))
          .//
          .// this column is special, we need to add a new default attribute value
           Integer.parseInt((String)parms.elementAt(${attr_col_num})),
           "",
           Gd_c.Null_unique_id(),
           ""\
          .assign special_case = true
        .elif (callingProject == "NBP")
          .if ( ((col.Name == "Previous_BParm_ID") OR (col.Name == "Previous_SParm_ID")) OR (col.Name == "Previous_TParm_ID") )
           parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
           .assign special_case = true
          .end if
        .end if
      .elif (table.Key_Lett == "C_IO")
        .if ((col.Name == "Direction") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
          parms.elementAt(${attr_col_num}) != null && ImportInteger.isInt(parms.elementAt(${attr_col_num})) ?Integer.parseInt((String)parms.elementAt(${attr_col_num})):0\
          .assign special_case = true
        .elif ((col.Name == "Previous_Id") and (callingProject == "NBP"))
          parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true          
        .end if
      .elif ( (table.Key_Lett == "C_EP") )
        .if ((col.Name == "Direction") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
           parms.elementAt(${attr_col_num}) != null && ImportInteger.isInt(parms.elementAt(${attr_col_num})) ?Integer.parseInt((String)parms.elementAt(${attr_col_num})):0\
          .assign special_case = true
        .end if
        .if((col.Name == "Name") and (callingProject == "NBP"))
          .// add a new string value for the added attribute
          parms.elementAt(${attr_col_num}) != null ? removeTics((String) parms.elementAt(${attr_col_num})).trim() : ""\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "MSG_E")
        .if(col.Name == "SMevt_ID")
           .// SM_ID was removed from the first identifier set in SM_EVT, this
           .// class at the parameters ordered with the SM_ID first
          parms.size() == 3 ? IdAssigner.createUUIDFromString((String)parms.elementAt(2)):IdAssigner.createUUIDFromString((String)parms.elementAt(1))
           .assign special_case = true
        .end if
      .elif ( (table.Key_Lett == "C_AS") )        
        .if ((col.Name == "Direction") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
           parms.elementAt(${attr_col_num}) != null && ImportInteger.isInt(parms.elementAt(${attr_col_num})) ?Integer.parseInt((String)parms.elementAt(${attr_col_num})):0
          .assign special_case = true          
        .elif ((col.Name == "Previous_Id") and (callingProject == "NBP"))
          parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true          
        .end if
      .elif (table.Key_Lett == "C_DG")
        .if(col.Name == "Name")
         parms.elementAt(${attr_col_num}) != null ? removeTics((String)parms.elementAt(${attr_col_num})).trim():""
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "O_ATTR")
        .if ((col.Name == "DT_ID") and (callingProject == "BP"))
           .//
           .// this column is special, we need to add a new default attribute value
          IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num}), true),
           "",
           ""\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "S_DT")
        .if ((col.Name == "Descrip") and (callingProject == "BP"))
           "",
           ""\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "C_PP")
        .if ((col.Name == "Previous_PP_Id") and (callingProject == "NBP"))
        parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "SM_EVTDI")
        .if ((col.Name == "DT_ID") and (callingProject == "BP"))
           .//
           .// this column is special, we need to add some new default attribute values
          IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num}), true),
           "",
           IdAssigner.NULL_UUID,
           IdAssigner.NULL_UUID\
          .assign special_case = true
        .elif ((col.Name == "SMevt_ID") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
           parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner
                            ..createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true
        .elif ((col.Name == "Previous_SMedi_ID") and (callingProject == "NBP"))
           parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner
                            ..createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "V_VAR")
        .if (col.Name == "DT_ID")
          .//
          .// this column is special, we may need to add a new default attribute value
          parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner
                           ..createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true
        .end if
      .elif ((table.Key_Lett == "S_DOM") and (col.Name == "Name"))
        .//
        .// this column is special, the import code will set the domain name.
        .// The initial value needs to be the empty string to prevent
        .// change listeners from responding to the import code setting
        .// the domain name
           m_templateFile ? "" : removeTics((String) parms.elementAt(${attr_col_num})).trim()\
        .assign special_case = true
      .elif (table.Key_Lett == "S_DID")
        .if ((col.Name == "DatPack_ID") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
          .// TODO: This special case is temporary and should be removed for the 1.5.0 release
           parms.elementAt(${attr_col_num}) != null && ImportInteger.isInt(parms.elementAt(${attr_col_num})) ?Integer.parseInt((String)parms.elementAt(${attr_col_num})):0\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "V_PVL")
        .if ((col.Name == "PP_Id") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
          .// TODO: This special case is temporary and should be removed for the 1.5.0 release
           parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner
                            ..createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
          .assign special_case = true
        .end if
      .elif (table.Key_Lett == "S_ENUM")
        .if ( (col.Name == "EDT_ID") and (callingProject == "BP") )
          IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num}), true),
          Gd_c.Null_unique_id()\
          .assign special_case = true
        .elif ((col.Name == "Previous_Enum_ID") and (callingProject == "NBP"))
           parms.elementAt(${attr_col_num}) != null && IdAssigner.isUUID((String)parms.elementAt(${attr_col_num}))?IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num})):Gd_c.Null_unique_id()\
           .assign special_case = true
        .end if
      .elif ( (table.Key_Lett == "EP_PKG") )        
        .if ((col.Name == "Num_Rng") and (callingProject == "NBP"))
          .//
          .// this column is special, we may need to add a new default attribute value
           parms.elementAt(${attr_col_num}) != null && ImportInteger.isInt(parms.elementAt(${attr_col_num})) ?Integer.parseInt((String)parms.elementAt(${attr_col_num})):0
          .assign special_case = true     
        .end if     
	  .elif (table.Key_Lett == "MSG_M")
	    .if(col.Name == "participatesInCommunication")
		  false\
		  .assign special_case = true
		.end if
	  .elif (table.Key_lett == "S_SYS")
	    .if(col.Name == "useGlobals")
		  parms.elementAt(${attr_col_num}) != null && ((String)parms.elementAt(${attr_col_num})).equals( "true" ) ? true : false\
		  .assign special_case = true
	  .end if
      .elif (table.Key_lett == "C_C")
        .if(col.Name == "isRealized")
          parms.elementAt(${attr_col_num}) != null && ((String)parms.elementAt(${attr_col_num})).equals( "true" ) ? true : false\
          .assign special_case = true
        .elif(col.Name == "Realized_Class_Path")
          parms.elementAt(${attr_col_num}) != null ? removeTics((String)parms.elementAt(${attr_col_num})) : ""\
          .assign special_case = true
        .end if
      .elif(table.Key_lett == "O_OBJ")
        .// NOTE: version 4.1.10 had two UUID attributes that are no longer
        .// present, if a new attribute is added special upgrade code may need
        .// to be added to check for this case.  See dts0101056206, and 
        .// doc-internal/notes/227/227.dnt.md for details
      .elif(table.Key_lett == "S_EE")
        .if(col.Name == "Realized_Class_Path")
          parms.elementAt(${attr_col_num}) != null ? removeTics((String)parms.elementAt(${attr_col_num})) : ""\
          .assign special_case = true
        .elif(col.Name == "isRealized")
          parms.elementAt(${attr_col_num}) != null && ((String)parms.elementAt(${attr_col_num})).equals( "false" ) ? false : true\
          .assign special_case = true        
        .end if
        .if(col.Name == "Label")
          parms.elementAt(${attr_col_num}) != null ? removeTics((String)parms.elementAt(${attr_col_num})) : ""\
          .assign special_case = true
        .end if
	  .end if
      .if (special_case == false)
        .if ( col.type == "boolean" )
           ((String)parms.elementAt(${attr_col_num})).equals( "false" ) ? false : true\
           .assign post_string = "  //$$NON-NLS-1$$"
        .elif ( col.type == "integer" )
           Integer.parseInt((String)parms.elementAt(${attr_col_num}))\
        .elif ( col.type == "real" )
           Float.parseFloat((String)parms.elementAt(${attr_col_num}))\
        .elif ( col.type == "unique_id" )
          .if (callingProject == "BP")
          IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num}), true)\
          .else
          IdAssigner.createUUIDFromString((String)parms.elementAt(${attr_col_num}))\
          .end if
        .elif ( col.type == "string" )
          .if (col.Name == "Name")
           removeTics((String)parms.elementAt(${attr_col_num})).trim()\
          .else
           removeTics((String)parms.elementAt(${attr_col_num}))\
          .end if
		.elif ( col.Name == "expiration" )
		  Long.parseLong((String)parms.elementAt(${attr_col_num}))\
        .else
           null\
        .end if
      .end if
      .select one nextCol related by col->C[R6.'precedes'];
      .select many ec_set related by col->EC[R9]
      .for each ec in ec_set
, ${post_string}
           ${ec.defaultValue}\
      .end for
      .if ( not_empty nextCol )
, ${post_string}
      .end if
      .assign attr_col_num = attr_col_num + 1
      .select one col related by col->C[R6.'precedes'];
    .end while
.end function
.//
.//====================================================================
.//
.function gen_import
  .param string callingProject
  .param string class_name
  .param boolean multilevel
  .param string version
  .param boolean setComponent
    .invoke var = sourceVariables()
    .invoke package = get_package()
    .invoke compMark = markComponentsAndContainments()
    .assign suite = "BP"
    .assign tool = version
    .if (callingProject == "BP")
      .include "arc/class_rename.inc"
package com.mentor.nucleus.bp.io.sql;
    .elif (callingProject == "NBP")
package com.mentor.nucleus.bp.io.mdl;
    .end if

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.List;
.if(multilevel)
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
.else
import java.io.FileNotFoundException;
.end if
import org.eclipse.core.runtime.IPath;
import java.util.Map;
import java.util.Vector;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.*;
import com.mentor.nucleus.bp.core.common.*;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.marker.UmlProblem;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.core.util.PersistenceUtil;
import com.mentor.nucleus.bp.io.core.CoreImport;
.if (callingProject == "NBP")
import com.mentor.nucleus.bp.io.core.ImportHelper;
.end if
import com.mentor.nucleus.bp.io.core.ImportInteger;
import com.mentor.nucleus.bp.ui.canvas.*;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

public class ${class_name} extends CoreImport
{
    String m_inFile; // used for error messages
    private int m_num_s_parent_found = 0;
    private boolean m_parseAll = true;
    private boolean m_parseGraphics = true;
    private boolean IPR_WindowSelection = false;
    private boolean initialCheck = false;
    private List<NonRootModelElement> loadedInstances = new Vector<NonRootModelElement>();
    private List<NonRootModelElement> loadedProxies = new Vector<NonRootModelElement>();
    private List<NonRootModelElement> loadedGraphicsInstances = new Vector<NonRootModelElement>();
    .if(multilevel)
    private PersistableModelComponent m_component;
    private IPath projRelPath = new Path("");

    private PersistenceHierarchyMetaData metadata = (PersistenceHierarchyMetaData)PersistenceManager.getHierarchyMetaData();
    .else
    private SystemModel_c m_parent;
    .end if
    private static String streamFileName = "stream";

    private UUID dataPkgUUID = null;

    /**
     * Holds Java-only functionality employed by this importer.
     */
    .assign helperClass = "ImportHelper"
    .if (callingProject == "BP")
        .assign helperClass = "ImportBPSqlHelper"
    .end if
    private ${helperClass} helper = new ${helperClass}(this);

    .if (callingProject == "NBP")
    /**
     * The version number found at the beginning of legacy BridgePoint 7.0 model files.
     */
    private final static String bp7_0VersionNumber = "7.0";

    /**
     * The version number found at the beginning of legacy BridgePoint 7.1 model files.
     */
    private final static String bp7_1VersionNumber = "7.1";

    /**
     * The version number found at the beginning of some legacy BridgePoint 7.1 model files.
     */
    private final static String bp7_1_1VersionNumber = "7.1.1";

    /**
     * The version number found at the beginning of some legacy BridgePoint 7.1 model files.
     */
    private final static String bp7_1_2VersionNumber = "7.1.2";

    /**
     * The version number found at the beginning of some legacy BridgePoint 7.1 model files.
     */
    private final static String bp7_1_3VersionNumber = "7.1.3";

    /**
     * The version number found at the beginning of some legacy BridgePoint 7.1 model files.
     */
    private final static String bp7_1_4VersionNumber = "7.1.4";

	private final static String bp7_1_5VersionNumber = "7.1.5";
    /**
     * The current persistence version
     */
    private final static String persistenceVersion = "7.1.6";

    .end if
    String actualVersion = getHeader().getFileFormatVersion();

     .assign parentC = ""
    .if(multilevel)
            .assign parentC = "ModelElement"
      .else
            .assign parentC = "SystemModel_c"
      .end if
    .if(multilevel and setComponent)
    public ${class_name}(IFile file, ${var.main_class_name} aModelRoot, PersistableModelComponent component, boolean parseAll, boolean clearDatabase, boolean parseGraphics, boolean isTemplate) throws IOException {
        super(aModelRoot, file.getLocation().toOSString(), clearDatabase, isTemplate);
        m_component = component;
        m_parseAll = parseAll;
        m_parseGraphics = parseGraphics;

         String fileName = super.getFilename();
        // Produce a path from the root of the project

        if (fileName != null) {
            IPath curPath = new Path(fileName);
            IPath projLoc = null;
                projLoc = file.getProject().getLocation();
                if (projLoc.isPrefixOf(curPath)) {
                    projRelPath = curPath.removeFirstSegments(projLoc.segmentCount() - 1);
                }
        }
    }
    public ${class_name}(InputStream inStream, ${var.main_class_name} aModelRoot, PersistableModelComponent component, boolean parseAll, boolean clearDatabase, boolean parseGraphics, boolean isTemplate) throws IOException {
        super(aModelRoot, inStream, clearDatabase, isTemplate);
        m_parseAll = parseAll;
        m_inFile = streamFileName;
        m_parseGraphics = parseGraphics;
        m_component = component;
    }
    .else
        .if (callingProject == "NBP")
          .if(not multilevel)
    public ${class_name}(InputStream inStream, ${var.main_class_name} aModelRoot, ${parentC} parent, boolean parseAll, boolean clearDatabase, boolean parseGraphics, boolean isTemplate) throws IOException {
        super(aModelRoot, inStream, clearDatabase, isTemplate);
        m_parseAll = parseAll;
        m_inFile = streamFileName;
        m_parseGraphics = parseGraphics;
        m_parent = parent;
    }
            .else
    public ${class_name}(InputStream inStream, ${var.main_class_name} aModelRoot, boolean clearDatabase, IPath projectRelativePath) throws IOException {
        super(aModelRoot, inStream, clearDatabase, false);
        m_inFile = streamFileName;
        projRelPath = projectRelativePath;
    }
            .end if
        .end if
        .if(not multilevel)
    public ${class_name}(String fileName, ${var.main_class_name} aModelRoot, ${parentC} parent, boolean parseAll, boolean clearDatabase, boolean isTemplate) throws FileNotFoundException {
        super(aModelRoot, fileName, clearDatabase, isTemplate);
        m_inFile = fileName;
        m_parseAll = parseAll;
        m_parent = parent;
    }

    public ${class_name}(IPath filePath) throws FileNotFoundException {
        super(filePath);
    }
       .end if
    .end if

    /* (non-Javadoc)
     * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void run(IProgressMonitor pm) {
    .if (callingProject == "NBP")
    	helper.eesToUpgradeForIsRealized.clear();
    .end if
        m_success = doLoadSql(pm);
        .if(not setComponent)
        finishLoad(pm);
    .end if
    }

        public NonRootModelElement[] getLoadedInstances() {
            return loadedInstances.toArray(new NonRootModelElement[loadedInstances.size()]);
        }

        public NonRootModelElement[] getLoadedGraphicalInstances() {
            .if(multilevel)
            return loadedGraphicsInstances.toArray(new NonRootModelElement[loadedGraphicsInstances.size()]);
          .else
          return new NonRootModelElement[0];
          .end if
        }

     public void performCleanUp(IProgressMonitor pm) throws IOException{
        if (m_clear_database) {
        .if(multilevel)
          .if(not setComponent)
            getModelRoot().clearDatabase(pm);
            getGraphicsModelRoot().clearDatabase(pm);
          .else
            // only if the model-root into which the import is being performed
            // holds any elements
            if ( m_component != null && m_component.isLoaded() )
            {
                AllActivityModifier aam = new AllActivityModifier(m_component.getRootModelElement(), pm);
                aam.processAllActivities(AllActivityModifier.CLEAR);
                m_component.clearDatabase();
            }
        .end if
      .else
                .// if it is not a generation of multilevel file import.
               Domain_c domain = Domain_c.DomainInstance(getModelRoot());
               if (domain != null) {
                // clear out all elements held by the model-root
                AllActivityModifier aam = new AllActivityModifier(domain, pm);
                aam.processAllActivities(AllActivityModifier.CLEAR);
                getModelRoot().clearDatabase(pm);
                }
      .end if
        }
    }

    public boolean isExpectedContent(String actualContent) {
    .if(not multilevel)
        return actualContent.equals("domain");
    .else
        .invoke result = get_all_seperately_configurable_elements()
            .assign configurableElements = result.value
            .for each ce in configurableElements
                .// each ce is an eo
                .select one table related by ce->EI[R3]->T[R4]
                .assign className  = "$Cr{table.Name}"
                if(actualContent.equals("${className}"))
                {
                    return true;
                }
            .end for
                .// TODO - this is special case code to handle data upgrades
                .// from 1.4.2 to 1.5
                if(actualContent.equals("ComponentDiagram"))
                {
                    return true;
                }
                if(actualContent.equals("InterfaceDiagram"))
                {
                    return true;
                }
                return false;
    .end if
    }

    public boolean isExpectedVersion(String actualVersion)
    {
        return actualVersion.equals("${tool}")
    .if (callingProject == "NBP")
            || actualVersion.equals(bp7_1_3VersionNumber)
          || actualVersion.equals(bp7_1_2VersionNumber)
                || actualVersion.equals(bp7_1_1VersionNumber)
            || actualVersion.equals(bp7_1VersionNumber)
            || actualVersion.equals(bp7_0VersionNumber)
            || actualVersion.equals("7.1.6")
    .end if
        ;
    }

    public String getErrorMessage() {
        return m_errorMessage;
    }

    public void preprocessStatement(String s, BufferedReader is)
        throws IOException {
    .if(multilevel)
        if(s.toUpperCase().startsWith("INSERT INTO")){
                   s = s.replaceFirst("INSERT INTO ","");
                   s = s.trim();
                   //component_keyLetter =  line;
            .select many componentElements from instances of EO where (selected.componentRoot == true)
            .select many filteredComponentElements from instances of EO where (selected.componentRoot == true)
            .assign filteredComponentElements = filteredComponentElements - filteredComponentElements
            .for each componentElement in componentElements
              .assign matchFound = false
              .for each fce in filteredComponentElements
                .if(componentElement.Name == fce.Name)
                  .assign matchFound = true
                .end if
              .end for
              .if(not matchFound)
                .assign filteredComponentElements = filteredComponentElements | componentElement
              .end if
            .end for
            String modelComponentType = getHeader().getModelComponentType();
            .for each ce in filteredComponentElements
                .// each ce is an eo
                .select one table related by ce->EI[R3]->T[R4]
                .assign className  = "$Cr{table.Name}"
                .assign keyLetter = "${table.Key_Lett}"
                .assign elseIf = " "

        ${elseIf} if (modelComponentType.equals("${className}")) //$$NON-NLS-1$$
        {
            .assign elseIf = "else "
            if(s.equalsIgnoreCase("${keyLetter}"))//$$NON-NLS-1$$
                    m_num_s_parent_found += 1;

        }
        .end for
        else if(modelComponentType.equals("ComponentDiagram")) {
            if(s.equalsIgnoreCase("CD_CD")) {
                m_num_s_parent_found += 1;
            }
        }
        else if(modelComponentType.equals("InterfaceDiagram")) {
            if(s.equalsIgnoreCase("ID_ID")) {
                m_num_s_parent_found += 1;
            }
        }
    }
    .else
        if (s.toUpperCase().equals("INSERT INTO S_DOM")) //$$NON-NLS-1$$
        {
            m_num_s_parent_found += 1;
        }
    .end if
    }

    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.io.sql.CoreImport#postprocessStatements()
     */
    public int postprocessStatements() {
      .if((multilevel) and (not setComponent))
        return PPS_OK;
      .else
        if (m_num_s_parent_found == 0) {
            m_errorMessage =
                "The file "
                    + m_inFile
                    + " is not a valid UML domain model file.";
            return PPS_ERROR;
        } else if (m_num_s_parent_found > 1) {
            m_errorMessage =
                "The file " + m_inFile + " contains multiple domains.";
            return PPS_ERROR;
        }
        return PPS_OK;
        .end if
    }

    .if((multilevel) and (setComponent))
    public void finishComponentLoad(IProgressMonitor pm, boolean searchAllRootsForBatchRelate) {
        if(m_success){
            Ooaofooa modelRoot = getModelRoot();

            if (modelRoot.getId().equals(Ooaofooa.DEFAULT_WORKING_MODELSPACE) &&
                            m_component.getRootModelElement() instanceof SystemModel_c) {
                    SystemModel_c system = (SystemModel_c) m_component.getRootModelElement();
					// We always set them up, even if we don't use them
                    helper.setUpGlobals(system);
            }

            ModelRoot.disableChangeNotification();
          try {
            for(int i=0; i<loadedInstances.size(); i++){
                ((NonRootModelElement)loadedInstances.get(i)).batchRelate(modelRoot, false, searchAllRootsForBatchRelate);
            }
              Ooaofooa mr = Ooaofooa.getDefaultInstance();
              Ooaofgraphics gmr = Ooaofgraphics.getDefaultInstance();
              try {
				mr.disableDeltaCollection();
				gmr.disableDeltaCollection();
            if(m_parseGraphics){
                ModelRoot graphicsModelRoot = getGraphicsModelRoot();

                for(int i=0; i<loadedGraphicsInstances.size(); i++){
                    ((NonRootModelElement)loadedGraphicsInstances.get(i)).batchRelate(graphicsModelRoot, false, searchAllRootsForBatchRelate);
                }

                helper.importPreBP7_1LineSegments();
                helper.importPreBP7_1Connectors();
                helper.importPreBP7_1_1ConnectorTexts();
                helper.createShapeSubtypes(getGraphicsModelRoot());
				helper.upgradeGraphicalAnchors(loadedGraphicsInstances);
				helper.configureMessages(loadedGraphicsInstances);
            }
            loadedInstances.addAll(helper.upgradeTransitions(loadedInstances));
            helper.upgradeEventData(getModelRoot(), getRootModelElement());
            loadedInstances.addAll( helper.upgradePackages(getRootModelElement(), getModelRoot()) );
            helper.upgradeLifespans(getRootModelElement());
            if(actualVersion.equals(bp7_1_4VersionNumber)) {
            	  helper.associateSatisfactionsThroughIdentifier(getModelRoot());
            }
            if (m_component.getComponentType().equals("Component") && actualVersion.equals(bp7_1_3VersionNumber))
	            helper.migrateComponents(getModelRoot());
            helper.upgradeDSAs(getModelRoot());
            helper.associateDTsWithDomain(getModelRoot());
            helper.upgradeElementOrder(getModelRoot());
			loadedInstances.addAll(helper.upgradeDatatypes(loadedInstances));
			helper.fixSatisfactionAssociations(loadedInstances);
			helper.loadDataTypesFromCoreTypeProxy(loadedInstances);
            loadedInstances.addAll(helper.upgradeSystemLevelPackages(loadedInstances));
			loadedInstances.addAll(helper.upgradeNestedPackages(getRootModelElement()));
			loadedInstances.addAll(helper.addLinkClassesForContainment(loadedInstances));
			helper.removeSupertypePackageableElement(loadedInstances);
			helper.addPortReferenceInstances(loadedInstances);
			helper.upgradeEEsForIsRealized();
          }
          finally {
            mr.enableDeltaCollection();
            gmr.enableDeltaCollection();
          }
        }
          finally {
            // model change listeners must be enabled for the following
            // as they may trigger a load, which would require notifying
            // listeners
                        ModelRoot.enableChangeNotification();
          }
      for (int i = 0; i < loadedInstances.size(); i++) {
          NonRootModelElement loadedInstance = (NonRootModelElement) loadedInstances.get(i);
          if (loadedInstance instanceof ModelClass_c
             && !loadedInstance.isProxy()) {
               ((ModelClass_c) loadedInstance).Addidentifiers();
          } else if (loadedInstance instanceof ReferentialAttribute_c
             && !loadedInstance.isProxy()) {
               ((ReferentialAttribute_c) loadedInstance).Setdatatype();
          }
      }
            ModelRoot.disableChangeNotification();
          try {
            for (int i = 0; i < loadedGraphicsInstances.size(); i++) {
              if (loadedGraphicsInstances.get(i) instanceof Model_c) {
                Model_c model = (Model_c) loadedGraphicsInstances.get(i);
                model.setRepresents(Cl_c.Getinstancefromooa_id(
                       getModelRoot(), model.getOoa_id(), model.getOoa_type()));
              }
              if(loadedGraphicsInstances.get(i) instanceof GraphicalElement_c) {
					GraphicalElement_c element = (GraphicalElement_c) loadedGraphicsInstances.get(i);
						element.setRepresents(Cl_c.Getinstancefromooa_id(
								getModelRoot(), element.getOoa_id(), element.getOoa_type()));
              }
          }
            pm.done();
            if (m_parseAll) {
                AllActivityModifier aam = new AllActivityModifier(rootModelElement, pm);
                aam.processAllActivities(AllActivityModifier.PARSE);
            }
          }
          finally {
                        ModelRoot.enableChangeNotification();
        }
    }
    }
    .end if
  .invoke result = genFinishLoadMethod(multilevel, setComponent, callingProject)
${result.body}\

	.if (class_name == "ImportModelStream")
     public boolean setIPRPreference(String projectName) {
    	boolean preferenceIPR = false;
    	IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    	if (selectedProject != null) {
    		IScopeContext projectScope = new ProjectScope(selectedProject);
    		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
    		preferenceIPR = Pref_c.Getsystemboolean( "com.mentor.nucleus.bp.ui.project.references" ,projectName);
    		if (!preferenceIPR ){
    			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
    				public void run() {
    					IPR_WindowSelection = org.eclipse.jface.dialogs.MessageDialog.openQuestion(PlatformUI.getWorkbench().getDisplay().getActiveShell(), 
    							"Enable Inter-Project Preference", 
    					"One or more elements refer to another element outside of this project. Would you like to enable Inter-project referencing?");
    				}
    			});
    			if (!IPR_WindowSelection){
    				return preferenceIPR;
    			}
    		}
    		projectNode.putBoolean("com.mentor.nucleus.bp.ui.project.references", true);
    		return preferenceIPR;
    	}
    	return preferenceIPR;
    }
    .end if
    
    
    public void processStatement(${var.main_class_name} modelRoot, String table, Vector parms, Vector rawParms, int numParms, IProgressMonitor pm ) {
      .// Note that this code only creates the instances in memory
      .// The relationship pointers are not initialized until later.
        ModelRoot.disableChangeNotification();
      try {
    	.if (class_name == "ImportModelStream")
    	if (!modelRoot.isCompareRoot() && !initialCheck && table.contains("_PROXY" )){
	    	String path = (String)parms.lastElement();
	    	String absolutePath = PersistenceUtil.resolveRelativePath(projRelPath, 
	    			new Path(removeTics(path))); 
	    	String projectName = PersistenceUtil.getProjectNameFromPath(absolutePath);
	    	String thisProjectName = projRelPath.segment(0);
	    	if(!thisProjectName.equals(projectName)) {
	    		this.setIPRPreference(thisProjectName);
	    	}  
	    	initialCheck = true;
	    }
        .end if
      .invoke result = genCreationMethodCalls()
${result.body}\
      .invoke result = genExtraCreationMethodCalls(callingProject)
${result.body}\
      .invoke result = genGraphicsCreationCode(callingProject, multilevel, setComponent)
${result.body}\
        pm.worked(1);
        } finally {
                    ModelRoot.enableChangeNotification();
        }
    }

     .invoke result = genCreateMethods(callingProject, multilevel, setComponent, var.main_class_name);
${result.body}\
     .invoke result = genExtraCreateMethods(callingProject, multilevel, setComponent, var.main_class_name);
${result.body}\
     .invoke result = genProcessValueMethod();
${result.body}\
}
.end function
