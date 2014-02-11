.//========================================================================
.//
.//File:      $RCSfile: generate_cell_modifiers.arc,v $
.//Version:   $Revision: 1.2 $
.//Modified:  $Date: 2013/01/17 03:38:58 $
.//
.//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
.//
.//========================================================================
.// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
.// use this file except in compliance with the License.  You may obtain a copy 
.// of the License at
.//
.//       http://www.apache.org/licenses/LICENSE-2.0
.//
.// Unless required by applicable law or agreed to in writing, software 
.// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
.// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
.// License for the specific language governing permissions and limitations under
.// the License.
.//========================================================================
.//
.//  Main code
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.invoke arc_env = GET_ENV_VAR( "OUTPUT_PATH" )
.assign output_path = arc_env.result
.invoke arc_env = GET_ENV_VAR( "SOURCE_PACKAGE" )
.assign source_package = arc_env.result
.invoke arc_env = GET_ENV_VAR( "MODEL_ROOT" )
.assign modelRoot = arc_env.result
.//
.include "${mc_archetypes}/arch_utils.inc"
.assign path = "com/mentor/nucleus/bp/core/ui/cells"
.if(output_path != "")
  .assign path = output_path
.end if
.assign rel_path = "src/${path}"
.assign package_initial = "com.mentor.nucleus.bp.core.ui.cells"
.if(source_package != "")
  .assign package_initial = source_package
.end if
.assign root = "Ooaofooa"
.if(modelRoot != "")
  .assign root = modelRoot
.end if
.function simple_attr_setvalue
  .param inst_ref_set attrs   .// O_ATTR
  .param string modelroot
  .//
  .assign else_stmt = ""
  .for each attr in attrs
    .select one class related by attr->O_OBJ[R102]
    .invoke cn = get_class_name(class)
    .invoke ssa = show_simple_attr( attr )
    .if ( ssa.result )
      .select one dbattr related by attr->O_BATTR[R106]->O_DBATTR[R107]
      .if ( empty dbattr )
        .assign aa = "set$cr{attr.Name}"
        if(data.getName().equals("${attr.Name}"))
        {
        .select one dt related by attr->S_DT[R114]
        .select one edt related by dt->S_EDT[R17]
        .if (dt.Name == "boolean" )
            boolean new_val = ((Boolean)value).booleanValue();
            if ( new_val != ((${cn.body}) element).get$cr{attr.Name}() )
            {
                ((${cn.body}) element).${aa}(new_val);
            }
        .elif ((dt.Name == "integer" ) or (not_empty edt))
            int new_val = Integer.parseInt(value.toString());
            if ( new_val != ((${cn.body}) element).get$cr{attr.Name}() )
            {
                ((${cn.body}) element).${aa}(new_val);
            }
        .elif(dt.Name == "real")
            float new_val = Float.parseFloat(value.toString());
            if ( new_val != ((${cn.body}) element).get$cr{attr.Name}() )
            {
                ((${cn.body}) element).${aa}(new_val);
            }        
        .elif (dt.Name == "string" )
            if ( ! value.toString().equals(((${cn.body}) element).get$cr{attr.Name}()) )
            {
          .if ( (attr.Name == "Dimensions") or (attr.Name == "Return_Dimensions") )
                Vector dims = DimensionsUtil.getDimensionsData(value.toString(),(${cn.body}) element);
                if (dims.size() > 0) {
                    for (int i = 0; i < dims.size(); i++) {
                        int numElements = ((Integer)dims.get(i)).intValue();
                        ((${cn.body}) element).Resize$LR{attr.Name}(i, numElements, dims.size());
                    }
                } else {
                    ((${cn.body}) element).Resize$LR{attr.Name}(0, 0, 0);
                }
          .end if
                ((${cn.body}) element).${aa}(value.toString());
            }
        .end if
        }
      .end if   // empty dbattr
    .end if  // asa.result
  .end for
.end function
.function get_validmult_decl
  .param String class_name
    private String[] validMult_vals(${class_name} element)
    {
        String [] onlyOne = { "One" };
        if ( element.Manymultallowed() )
        {
            return Mult_vals;
        }
        return onlyOne;
    }
.end function
.function show_simple_attr
  .param inst_ref attr   .// O_ATTR
  .//
  .assign attr_result = true
  .select one dt related by attr->S_DT[R114]
  .select one rattr related by attr->O_RATTR[R106]
  .if ( dt.Name == "unique_id" )
    .assign attr_result = false
  .elif (not_empty rattr)
    .assign attr_result = false
  .elif ("$l{attr.Descrip:User_Visible}" == "false")
    .assign attr_result = false
  .end if
.end function
.function get_enum_descriptor
  .param inst_ref attr   .// O_ATTR
  .//
  .select one dt related by attr->S_DT[R114]
  .select one edt related by dt->S_EDT[R17]
  .assign attr_enum_decl_var = "$r{attr.Name}_vals"
  .assign attr_enum_decl = "    private String[] ${attr_enum_decl_var} = { "
  .if ( empty edt )
  .assign enum0 = "${attr.Descrip:enum0}"
  .assign attr_enum_decl = attr_enum_decl + """${enum0}"""
  .assign enum1 = "${attr.Descrip:enum1}"
  .if ( enum1 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum1}"""
  .end if
  .assign enum2 = "${attr.Descrip:enum2}"
  .if ( enum2 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum2}"""
  .end if
  .assign enum3 = "${attr.Descrip:enum3}"
  .if ( enum3 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum3}"""
  .end if
  .assign enum4 = "${attr.Descrip:enum4}"
  .if ( enum4 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum4}"""
  .end if
  .assign enum5 = "${attr.Descrip:enum5}"
  .if ( enum5 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum5}"""
  .end if
  .assign enum6 = "${attr.Descrip:enum6}"
  .if ( enum6 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum6}"""
  .end if
  .assign enum7 = "${attr.Descrip:enum7}"
  .if ( enum7 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum7}"""
  .end if
  .assign enum8 = "${attr.Descrip:enum8}"
  .if ( enum8 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum8}"""
  .end if
  .assign enum9 = "${attr.Descrip:enum9}"
  .if ( enum9 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum9}"""
  .end if
  .assign enum10 = "${attr.Descrip:enum10}"
  .if ( enum10 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum10}"""
  .end if
  .assign enum11 = "${attr.Descrip:enum11}"
  .if ( enum11 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum11}"""
  .end if
  .assign enum12 = "${attr.Descrip:enum12}"
  .if ( enum12 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum12}"""
  .end if
  .assign enum13 = "${attr.Descrip:enum13}"
  .if ( enum13 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum13}"""
  .end if
  .assign enum14 = "${attr.Descrip:enum14}"
  .if ( enum14 != "" )
    .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum14}"""
  .end if
  .else
    .select many enum_set related by edt->S_ENUM[R27];
    .assign enum_num = 0
    .while ( enum_num < (cardinality enum_set))
      .for each enum in enum_set
        .if ( "${enum.Descrip:Value}" == "${enum_num}" )
          .assign enum_name = enum.Name
          .if ( "${enum.Descrip:Full Name}" != "" )
            .assign enum_name = "${enum.Descrip:Full Name}"
          .end if
          .if ( enum_num == 0 )
            .assign attr_enum_decl = attr_enum_decl + """${enum_name}"""
          .else
            .assign attr_enum_decl = attr_enum_decl + ",\n        ""${enum_name}"""
          .end if
          .break for
        .end if
      .end for
      .assign enum_num = enum_num + 1
    .end while
  .end if
  .assign attr_enum_decl = attr_enum_decl + " };"
  .if ( attr.Name == "Mult" )
    .select one class related by attr->O_OBJ[R102];
    .invoke cn = get_class_name(class)
    .invoke gvmd = get_validmult_decl("${cn.body}")
    .assign attr_enum_decl = attr_enum_decl + "\n${gvmd.body}"
    .assign attr_enum_decl_var = "valid${attr_enum_decl_var}((${cn.body}) element)"
  .end if
.end function
.function simple_attr_editors
  .param inst_ref_set attributes  .// O_ATTR
  .//
  .assign attr_num_attr = 0
  .assign attr_enum_decls = ""
  .for each attr in attributes
    .invoke ssa = show_simple_attr( attr )
    .if ( ssa.result )
      .assign name = "${attr.Name}"
      .assign readonly = "$l{attr.Descrip:readonly}"
      .assign dynamicReadOnly = "$l{attr.Descrip:DynamicReadOnly}"
      .select one dt related by attr->S_DT[R114]
      .select one edt related by dt->S_EDT[R17]
      .select one dbattr related by attr->O_BATTR[R106]->O_DBATTR[R107]
      .select one obj related by attr->O_OBJ[R102]
      .assign readOnlyFlag = "readonly"
      .if (dynamicReadOnly != "")
        .assign readOnlyFlag = "readonly || m_inst.$Cr{dynamicReadOnly}()"
      .end if
      .if ( ((not_empty dbattr) or (readonly == "true")) and (attr.Name != "Action_Semantics") )
          .if ( ("${attr.Descrip:enum0}" != "") and (not_empty edt) )
            .invoke ged = get_enum_descriptor(attr)
            if(data.getName().equals("${attr.Name}")) {
                CellEditor editor = new EnumCellEditor(parent, ${ged.enum_decl_var});
                return editor;
            }
            .assign attr_enum_decls = attr_enum_decls + ged.enum_decl
          .end if
      .else
        .if (dt.Name == "boolean" )
                if(data.getName().equals("${attr.Name}")) {
                	CellEditor editor = new BooleanCellEditor(parent);
                	return editor;
                }
        .elif ((dt.Name == "integer") or (not_empty edt))
          .if ( ("${attr.Descrip:enum0}" == "") and (empty edt) )
            .assign min_value = "${attr.Descrip:min_value}"
            .if (min_value == "" )
              .assign min_value = "Integer.MIN_VALUE"
            .end if
            .assign max_value = "${attr.Descrip:max_value}"
            .if (max_value == "" )
              .assign max_value = "Integer.MAX_VALUE"
            .end if
                if(data.getName().equals("${attr.Name}")) {
                	CellEditor editor = new IntegerCellEditor(parent, ${min_value}, ${max_value});
                	return editor;
               	}
          .else
            .invoke ged = get_enum_descriptor(attr)
            .// assume all attrs are read-write
                if(data.getName().equals("${attr.Name}")) {
                	CellEditor editor = new EnumCellEditor(parent, ${ged.enum_decl_var});
                	return editor;
                }
            .assign attr_enum_decls = attr_enum_decls + ged.enum_decl
          .end if
        .elif (dt.Name == "string" )
          .if ( (attr.Name == "Dimensions") or (attr.Name == "Return_Dimensions") )
			  if(data.getName().equals("${attr.Name}")) {
			  	CellEditor editor = new TextCellEditor(parent);
			  	editor.setValidator(new DimensionsValidator(element));
			  	return editor;
			  }
          .else
            .// special case for not renaming model elements that are
            .// not meant to be renamed (the "Datatypes" package for example)
            .if ( attr.Name == "Name" )
              .select one obj related by attr->O_OBJ[R102]
              .select one op related by obj->O_TFR[R115] where (selected.Name == "canRename" )
              .invoke cn = get_class_name(obj)
              .if ( not_empty op )
                if(data.getName().equals("${attr.Name}")) {
	                if (((${cn.body})element).Canrename()) {
	                    CellEditor editor = new TextCellEditor(parent);
                .if ((attr.Name == "Name") or (attr.Name == "Root_Nam"))
					editor.setValidator(new ModelElementNameValidator(element));
                .end if
	                    return editor;
	                }
	            }
              .else
                if(data.getName().equals("${attr.Name}")) {
	                CellEditor editor = new TextCellEditor(parent);
                .if ((attr.Name == "Name") or (attr.Name == "Root_Nam"))
					editor.setValidator(new ModelElementNameValidator(element));
                .end if
	                return editor;
	            }
              .end if
            .else
                if(data.getName().equals("${attr.Name}")) {
	                CellEditor editor = new TextCellEditor(parent);
              .if ((attr.Name == "Name") or (attr.Name == "Root_Nam"))
					editor.setValidator(new ModelElementNameValidator(element));
              .end if
	                return editor;
	            }
            .end if
          .end if
        .elif(dt.Name == "real")
			  if(data.getName().equals("${attr.Name}")) {
			  	CellEditor editor = new FloatCellEditor(parent);
			  	return editor;
			  }
        .end if
      .end if  .// if not_empty dbattr
      .assign attr_num_attr = attr_num_attr + 1
    .end if  .// asa.result
  .end for
.end function
.function create_cell_modifier
  .param Inst_Ref node
  .param String package
  .param String root
  .param String rel_path
    .select any class from instances of O_OBJ where (selected.Key_Lett == node.Key_Lett)
    .assign attr_class_name = "$r{class.Name}CellProvider"
    .invoke header = generate_header(rel_path, package, attr_class_name);
${header.body}
package ${package};

import java.util.Vector;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;

.if(root == "Ooaofooa")
  import com.mentor.nucleus.bp.core.*;
.else
  import com.mentor.nucleus.bp.ui.canvas.*;
.end if
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.core.ui.cells.ICellProvider;
import com.mentor.nucleus.bp.core.ui.cells.editors.*;
import com.mentor.nucleus.bp.core.util.*;

public class $r{class.Name}CellProvider implements ICellProvider {
	.select many attrs related by class->O_ATTR[R102];
	.select many external_attrs related by node->T_EDL[R1005]->T_EA[R1006]
	.for each external_attr in external_attrs
      .select one edl related by external_attr->T_EDL[R1006]
      .select any external_class from instances of O_OBJ where (selected.Key_Lett == edl.Key_Lett)
      .select many e_attrs related by external_class->O_ATTR[R102] where (selected.Name == external_attr.Attribute_Name);
      .assign attrs = attrs | e_attrs
    .end for
    .invoke sae = simple_attr_editors(attrs)
${sae.enum_decls}
    
	@Override
	public CellEditor getCellEditor(final NonRootModelElement element,
			Composite parent, ObjectElement data) {
${sae.body}
		return null;
	}

	@Override
	public boolean supportsEdit(NonRootModelElement element,
			ObjectElement data, Composite parent) {
		CellEditor editor = getCellEditor(element, parent, data);
		if(editor != null) {
			editor.dispose();
			return true;
		}
		return false;
	}
	
	@Override
	public String getValue(NonRootModelElement element, ObjectElement data) {
    .for each attr in attrs
      .invoke ssa = show_simple_attr( attr )
      .if ( ssa.result )
        .assign readonly = "$l{attr.Descrip:readonly}"
        .select one dt related by attr->S_DT[R114]
        .select one edt related by dt->S_EDT[R17]
        .select one dbattr related by attr->O_BATTR[R106]->O_DBATTR[R107]
        .if ( ((not_empty dbattr) or (readonly == "true")) and (attr.Name != "Action_Semantics") )
          .if ( ("${attr.Descrip:enum0}" != "") and (not_empty edt) )
		if(data.getName().equals("${attr.Name}")) {
	        Integer $r{attr.Name}_value = (Integer) data.getValue();
			if($r{attr.Name}_value >= 0 && $r{attr.Name}_value < $r{attr.Name}_vals.length) {
				return $r{attr.Name}_vals[$r{attr.Name}_value];
			}
		}
          .end if
        .elif (not_empty edt)
		if(data.getName().equals("${attr.Name}")) {
	        Integer $r{attr.Name}_value = (Integer) data.getValue();
			if($r{attr.Name}_value >= 0 && $r{attr.Name}_value < $r{attr.Name}_vals.length) {
				return $r{attr.Name}_vals[$r{attr.Name}_value];
			}
		}
		.elif(("${attr.Descrip:enum0}" != "") and (dt.Name == "integer"))
		if(data.getName().equals("${attr.Name}")) {
	        Integer $r{attr.Name}_value = (Integer) data.getValue();
			if($r{attr.Name}_value >= 0 && $r{attr.Name}_value < $r{attr.Name}_vals.length) {
				return $r{attr.Name}_vals[$r{attr.Name}_value];
			}
		}
        .end if
      .end if
    .end for;
		return null;
	}
	
	@Override
	public void setValue(NonRootModelElement element, ObjectElement data, Object value) {
	.invoke set = simple_attr_setvalue(attrs, root)
${set.body}
	}
	
}
.end function
.function generate_cell_modifier_provider
  .param Inst_Ref_Set nodes
  .param String package_initial
  .param String root
  .param String rel_path
    .assign attr_class_name = "CellModifierProvider"
    .invoke header = generate_header(rel_path, package_initial, attr_class_name);
${header.body}\
package ${package_initial};

import java.util.HashMap;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

.if(root == "Ooaofooa")
import com.mentor.nucleus.bp.core.*;
.else
import com.mentor.nucleus.bp.core.ui.cells.*;
import com.mentor.nucleus.bp.ui.canvas.*;
.end if
import com.mentor.nucleus.bp.core.common.*;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
.if(root == "Ooaofooa")
import com.mentor.nucleus.bp.core.ui.cells.providers.*;
.else
import com.mentor.nucleus.bp.ui.canvas.cells.providers.*;
.end if

public class CellModifierProvider {

	private static HashMap<Class<?>, ICellProvider> cellProviders = new HashMap<Class<?>, ICellProvider>();
	
	static {
    .for each node in nodes
      .select any class from instances of O_OBJ where (selected.Key_Lett == node.Key_Lett)
      .invoke cn = get_class_name(class)
      cellProviders.put(${cn.body}.class, new $r{class.Name}CellProvider());
    .end for
	}
	
	public static CellEditor getCellEditor(NonRootModelElement element, Composite parent, ObjectElement data) {
		ICellProvider cellProvider = cellProviders .get(element.getClass());
		if(cellProvider != null) {
			return cellProvider.getCellEditor(element, parent, data);
		}
		return null;
	}

	public static boolean supportsEdit(NonRootModelElement element,
			ObjectElement data, Composite parent) {
		ICellProvider cellProvider = cellProviders.get(element.getClass());
		if(cellProvider != null) {
			return cellProvider.supportsEdit(element, data, parent);
		}
		return false;
	}
	
	public static String getValue(NonRootModelElement element, ObjectElement data) {
		ICellProvider cellProvider = cellProviders.get(element.getClass());
		if(cellProvider != null) {
			return cellProvider.getValue(element, data);
		}
		return null;
	}
	
	public static void setValue(NonRootModelElement element, ObjectElement data, Object value, NonRootModelElement attributeOwner) {
		ICellProvider cellProvider = cellProviders.get(element.getClass());
		if(cellProvider != null) {
		    if(attributeOwner != null) {
		    	element = attributeOwner;
		    }
			cellProvider.setValue(element, data, value);
		}
	}

}
.end function
.function generate_header
  .param String rel_path
  .param String package
  .param String class_name
//========================================================================
//
// File: ${rel_path}/${class_name}.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.2 $$
//
// Copyright 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
//
//  Optional Comments about the generated java file
//
.end function
.select many nodes from instances of T_TNS
.assign provider_package = package_initial + ".providers"
.for each tree_node in nodes
  .invoke cm = create_cell_modifier( tree_node, provider_package, root, rel_path)
${cm.body}\
  .emit to file "${rel_path}/providers/${cm.class_name}.java"
.end for
.invoke cmp = generate_cell_modifier_provider(nodes, package_initial, root, rel_path)
${cmp.body}\
.emit to file "${rel_path}/${cmp.class_name}.java"
.//
