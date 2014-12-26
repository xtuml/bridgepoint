/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.xtuml.bp.xtext.oal.xoal.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XoalFactoryImpl extends EFactoryImpl implements XoalFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static XoalFactory init()
  {
    try
    {
      XoalFactory theXoalFactory = (XoalFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.xtuml.org/bp/xtext/oal/Xoal"); 
      if (theXoalFactory != null)
      {
        return theXoalFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new XoalFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XoalFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case XoalPackage.CODE: return createCode();
      case XoalPackage.BLOCK: return createblock();
      case XoalPackage.STATEMENT: return createstatement();
      case XoalPackage.ASSIGNMENT_STATEMENT: return createassignment_statement();
      case XoalPackage.BREAK_STATEMENT: return createbreak_statement();
      case XoalPackage.BRIDGE_STATEMENT: return createbridge_statement();
      case XoalPackage.SEND_STATEMENT: return createsend_statement();
      case XoalPackage.CONTROL_STATEMENT: return createcontrol_statement();
      case XoalPackage.CONTINUE_STATEMENT: return createcontinue_statement();
      case XoalPackage.CREATE_EVENT_STATEMENT: return createcreate_event_statement();
      case XoalPackage.CREATE_OBJECT_STATEMENT: return createcreate_object_statement();
      case XoalPackage.DELETE_STATEMENT: return createdelete_statement();
      case XoalPackage.FOR_STATEMENT: return createfor_statement();
      case XoalPackage.GENERATE_STATEMENT: return creategenerate_statement();
      case XoalPackage.IF_STATEMENT: return createif_statement();
      case XoalPackage.RELATE_STATEMENT: return createrelate_statement();
      case XoalPackage.RETURN_STATEMENT: return createreturn_statement();
      case XoalPackage.SELECT_STATEMENT: return createselect_statement();
      case XoalPackage.UNRELATE_STATEMENT: return createunrelate_statement();
      case XoalPackage.WHILE_STATEMENT: return createwhile_statement();
      case XoalPackage.ASSIGNMENT_EXPR: return createassignment_expr();
      case XoalPackage.BRIDGE_INVOCATION: return createbridge_invocation();
      case XoalPackage.BRIDGE_EXPR: return createbridge_expr();
      case XoalPackage.ASSOC_OBJ_INST_REF_VAR: return createassoc_obj_inst_ref_var();
      case XoalPackage.INST_REF_SET_VAR: return createinst_ref_set_var();
      case XoalPackage.INST_REF_VAR: return createinst_ref_var();
      case XoalPackage.EXPR: return createexpr();
      case XoalPackage.SUB_EXPR: return createsub_expr();
      case XoalPackage.CONJUNCTION: return createconjunction();
      case XoalPackage.RELATIONAL_EXPR: return createrelational_expr();
      case XoalPackage.ADDITION: return createaddition();
      case XoalPackage.MULTIPLICATION: return createmultiplication();
      case XoalPackage.SIGN_EXPR: return createsign_expr();
      case XoalPackage.BOOLEAN_NEGATION: return createboolean_negation();
      case XoalPackage.TERM: return createterm();
      case XoalPackage.INSTANCE_START_SEGMENT: return createinstance_start_segment();
      case XoalPackage.MEMBER_ACCESS: return createmember_access();
      case XoalPackage.ARRAY_REFS: return createarray_refs();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Code createCode()
  {
    CodeImpl code = new CodeImpl();
    return code;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public block createblock()
  {
    blockImpl block = new blockImpl();
    return block;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public statement createstatement()
  {
    statementImpl statement = new statementImpl();
    return statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public assignment_statement createassignment_statement()
  {
    assignment_statementImpl assignment_statement = new assignment_statementImpl();
    return assignment_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public break_statement createbreak_statement()
  {
    break_statementImpl break_statement = new break_statementImpl();
    return break_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public bridge_statement createbridge_statement()
  {
    bridge_statementImpl bridge_statement = new bridge_statementImpl();
    return bridge_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public send_statement createsend_statement()
  {
    send_statementImpl send_statement = new send_statementImpl();
    return send_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public control_statement createcontrol_statement()
  {
    control_statementImpl control_statement = new control_statementImpl();
    return control_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public continue_statement createcontinue_statement()
  {
    continue_statementImpl continue_statement = new continue_statementImpl();
    return continue_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public create_event_statement createcreate_event_statement()
  {
    create_event_statementImpl create_event_statement = new create_event_statementImpl();
    return create_event_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public create_object_statement createcreate_object_statement()
  {
    create_object_statementImpl create_object_statement = new create_object_statementImpl();
    return create_object_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public delete_statement createdelete_statement()
  {
    delete_statementImpl delete_statement = new delete_statementImpl();
    return delete_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public for_statement createfor_statement()
  {
    for_statementImpl for_statement = new for_statementImpl();
    return for_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public generate_statement creategenerate_statement()
  {
    generate_statementImpl generate_statement = new generate_statementImpl();
    return generate_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public if_statement createif_statement()
  {
    if_statementImpl if_statement = new if_statementImpl();
    return if_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public relate_statement createrelate_statement()
  {
    relate_statementImpl relate_statement = new relate_statementImpl();
    return relate_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public return_statement createreturn_statement()
  {
    return_statementImpl return_statement = new return_statementImpl();
    return return_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public select_statement createselect_statement()
  {
    select_statementImpl select_statement = new select_statementImpl();
    return select_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public unrelate_statement createunrelate_statement()
  {
    unrelate_statementImpl unrelate_statement = new unrelate_statementImpl();
    return unrelate_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public while_statement createwhile_statement()
  {
    while_statementImpl while_statement = new while_statementImpl();
    return while_statement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public assignment_expr createassignment_expr()
  {
    assignment_exprImpl assignment_expr = new assignment_exprImpl();
    return assignment_expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public bridge_invocation createbridge_invocation()
  {
    bridge_invocationImpl bridge_invocation = new bridge_invocationImpl();
    return bridge_invocation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public bridge_expr createbridge_expr()
  {
    bridge_exprImpl bridge_expr = new bridge_exprImpl();
    return bridge_expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public assoc_obj_inst_ref_var createassoc_obj_inst_ref_var()
  {
    assoc_obj_inst_ref_varImpl assoc_obj_inst_ref_var = new assoc_obj_inst_ref_varImpl();
    return assoc_obj_inst_ref_var;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public inst_ref_set_var createinst_ref_set_var()
  {
    inst_ref_set_varImpl inst_ref_set_var = new inst_ref_set_varImpl();
    return inst_ref_set_var;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public inst_ref_var createinst_ref_var()
  {
    inst_ref_varImpl inst_ref_var = new inst_ref_varImpl();
    return inst_ref_var;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public expr createexpr()
  {
    exprImpl expr = new exprImpl();
    return expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public sub_expr createsub_expr()
  {
    sub_exprImpl sub_expr = new sub_exprImpl();
    return sub_expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public conjunction createconjunction()
  {
    conjunctionImpl conjunction = new conjunctionImpl();
    return conjunction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public relational_expr createrelational_expr()
  {
    relational_exprImpl relational_expr = new relational_exprImpl();
    return relational_expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public addition createaddition()
  {
    additionImpl addition = new additionImpl();
    return addition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public multiplication createmultiplication()
  {
    multiplicationImpl multiplication = new multiplicationImpl();
    return multiplication;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public sign_expr createsign_expr()
  {
    sign_exprImpl sign_expr = new sign_exprImpl();
    return sign_expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean_negation createboolean_negation()
  {
    boolean_negationImpl boolean_negation = new boolean_negationImpl();
    return boolean_negation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public term createterm()
  {
    termImpl term = new termImpl();
    return term;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public instance_start_segment createinstance_start_segment()
  {
    instance_start_segmentImpl instance_start_segment = new instance_start_segmentImpl();
    return instance_start_segment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public member_access createmember_access()
  {
    member_accessImpl member_access = new member_accessImpl();
    return member_access;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public array_refs createarray_refs()
  {
    array_refsImpl array_refs = new array_refsImpl();
    return array_refs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XoalPackage getXoalPackage()
  {
    return (XoalPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static XoalPackage getPackage()
  {
    return XoalPackage.eINSTANCE;
  }

} //XoalFactoryImpl
