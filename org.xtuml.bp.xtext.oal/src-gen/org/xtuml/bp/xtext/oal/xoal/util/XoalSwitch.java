/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.xtuml.bp.xtext.oal.xoal.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage
 * @generated
 */
public class XoalSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static XoalPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XoalSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = XoalPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case XoalPackage.CODE:
      {
        Code code = (Code)theEObject;
        T result = caseCode(code);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.BLOCK:
      {
        block block = (block)theEObject;
        T result = caseblock(block);
        if (result == null) result = caseCode(block);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.STATEMENT:
      {
        statement statement = (statement)theEObject;
        T result = casestatement(statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.ASSIGNMENT_STATEMENT:
      {
        assignment_statement assignment_statement = (assignment_statement)theEObject;
        T result = caseassignment_statement(assignment_statement);
        if (result == null) result = casestatement(assignment_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.BREAK_STATEMENT:
      {
        break_statement break_statement = (break_statement)theEObject;
        T result = casebreak_statement(break_statement);
        if (result == null) result = casestatement(break_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.BRIDGE_STATEMENT:
      {
        bridge_statement bridge_statement = (bridge_statement)theEObject;
        T result = casebridge_statement(bridge_statement);
        if (result == null) result = casestatement(bridge_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.SEND_STATEMENT:
      {
        send_statement send_statement = (send_statement)theEObject;
        T result = casesend_statement(send_statement);
        if (result == null) result = casestatement(send_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.CONTROL_STATEMENT:
      {
        control_statement control_statement = (control_statement)theEObject;
        T result = casecontrol_statement(control_statement);
        if (result == null) result = casestatement(control_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.CONTINUE_STATEMENT:
      {
        continue_statement continue_statement = (continue_statement)theEObject;
        T result = casecontinue_statement(continue_statement);
        if (result == null) result = casestatement(continue_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.CREATE_EVENT_STATEMENT:
      {
        create_event_statement create_event_statement = (create_event_statement)theEObject;
        T result = casecreate_event_statement(create_event_statement);
        if (result == null) result = casestatement(create_event_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.CREATE_OBJECT_STATEMENT:
      {
        create_object_statement create_object_statement = (create_object_statement)theEObject;
        T result = casecreate_object_statement(create_object_statement);
        if (result == null) result = casestatement(create_object_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.DELETE_STATEMENT:
      {
        delete_statement delete_statement = (delete_statement)theEObject;
        T result = casedelete_statement(delete_statement);
        if (result == null) result = casestatement(delete_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.FOR_STATEMENT:
      {
        for_statement for_statement = (for_statement)theEObject;
        T result = casefor_statement(for_statement);
        if (result == null) result = casestatement(for_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.GENERATE_STATEMENT:
      {
        generate_statement generate_statement = (generate_statement)theEObject;
        T result = casegenerate_statement(generate_statement);
        if (result == null) result = casestatement(generate_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.IF_STATEMENT:
      {
        if_statement if_statement = (if_statement)theEObject;
        T result = caseif_statement(if_statement);
        if (result == null) result = casestatement(if_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.RELATE_STATEMENT:
      {
        relate_statement relate_statement = (relate_statement)theEObject;
        T result = caserelate_statement(relate_statement);
        if (result == null) result = casestatement(relate_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.RETURN_STATEMENT:
      {
        return_statement return_statement = (return_statement)theEObject;
        T result = casereturn_statement(return_statement);
        if (result == null) result = casestatement(return_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.SELECT_STATEMENT:
      {
        select_statement select_statement = (select_statement)theEObject;
        T result = caseselect_statement(select_statement);
        if (result == null) result = casestatement(select_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.UNRELATE_STATEMENT:
      {
        unrelate_statement unrelate_statement = (unrelate_statement)theEObject;
        T result = caseunrelate_statement(unrelate_statement);
        if (result == null) result = casestatement(unrelate_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.WHILE_STATEMENT:
      {
        while_statement while_statement = (while_statement)theEObject;
        T result = casewhile_statement(while_statement);
        if (result == null) result = casestatement(while_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.ASSIGNMENT_EXPR:
      {
        assignment_expr assignment_expr = (assignment_expr)theEObject;
        T result = caseassignment_expr(assignment_expr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.BRIDGE_INVOCATION:
      {
        bridge_invocation bridge_invocation = (bridge_invocation)theEObject;
        T result = casebridge_invocation(bridge_invocation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.BRIDGE_EXPR:
      {
        bridge_expr bridge_expr = (bridge_expr)theEObject;
        T result = casebridge_expr(bridge_expr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.ASSOC_OBJ_INST_REF_VAR:
      {
        assoc_obj_inst_ref_var assoc_obj_inst_ref_var = (assoc_obj_inst_ref_var)theEObject;
        T result = caseassoc_obj_inst_ref_var(assoc_obj_inst_ref_var);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.INST_REF_SET_VAR:
      {
        inst_ref_set_var inst_ref_set_var = (inst_ref_set_var)theEObject;
        T result = caseinst_ref_set_var(inst_ref_set_var);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.INST_REF_VAR:
      {
        inst_ref_var inst_ref_var = (inst_ref_var)theEObject;
        T result = caseinst_ref_var(inst_ref_var);
        if (result == null) result = caseassoc_obj_inst_ref_var(inst_ref_var);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.EXPR:
      {
        expr expr = (expr)theEObject;
        T result = caseexpr(expr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.SUB_EXPR:
      {
        sub_expr sub_expr = (sub_expr)theEObject;
        T result = casesub_expr(sub_expr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.CONJUNCTION:
      {
        conjunction conjunction = (conjunction)theEObject;
        T result = caseconjunction(conjunction);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.RELATIONAL_EXPR:
      {
        relational_expr relational_expr = (relational_expr)theEObject;
        T result = caserelational_expr(relational_expr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.ADDITION:
      {
        addition addition = (addition)theEObject;
        T result = caseaddition(addition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.MULTIPLICATION:
      {
        multiplication multiplication = (multiplication)theEObject;
        T result = casemultiplication(multiplication);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.SIGN_EXPR:
      {
        sign_expr sign_expr = (sign_expr)theEObject;
        T result = casesign_expr(sign_expr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.BOOLEAN_NEGATION:
      {
        boolean_negation boolean_negation = (boolean_negation)theEObject;
        T result = caseboolean_negation(boolean_negation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.TERM:
      {
        term term = (term)theEObject;
        T result = caseterm(term);
        if (result == null) result = casesign_expr(term);
        if (result == null) result = caseboolean_negation(term);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.INSTANCE_START_SEGMENT:
      {
        instance_start_segment instance_start_segment = (instance_start_segment)theEObject;
        T result = caseinstance_start_segment(instance_start_segment);
        if (result == null) result = casemember_access(instance_start_segment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.MEMBER_ACCESS:
      {
        member_access member_access = (member_access)theEObject;
        T result = casemember_access(member_access);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case XoalPackage.ARRAY_REFS:
      {
        array_refs array_refs = (array_refs)theEObject;
        T result = casearray_refs(array_refs);
        if (result == null) result = caseinstance_start_segment(array_refs);
        if (result == null) result = casemember_access(array_refs);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Code</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Code</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCode(Code object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>block</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>block</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseblock(block object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casestatement(statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>assignment statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>assignment statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseassignment_statement(assignment_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>break statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>break statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casebreak_statement(break_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>bridge statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>bridge statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casebridge_statement(bridge_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>send statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>send statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casesend_statement(send_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>control statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>control statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casecontrol_statement(control_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>continue statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>continue statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casecontinue_statement(continue_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>create event statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>create event statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casecreate_event_statement(create_event_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>create object statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>create object statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casecreate_object_statement(create_object_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>delete statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>delete statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casedelete_statement(delete_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>for statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>for statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casefor_statement(for_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>generate statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>generate statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casegenerate_statement(generate_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>if statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>if statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseif_statement(if_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>relate statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>relate statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caserelate_statement(relate_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>return statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>return statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casereturn_statement(return_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>select statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>select statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseselect_statement(select_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>unrelate statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>unrelate statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseunrelate_statement(unrelate_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>while statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>while statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casewhile_statement(while_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>assignment expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>assignment expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseassignment_expr(assignment_expr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>bridge invocation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>bridge invocation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casebridge_invocation(bridge_invocation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>bridge expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>bridge expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casebridge_expr(bridge_expr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>assoc obj inst ref var</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>assoc obj inst ref var</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseassoc_obj_inst_ref_var(assoc_obj_inst_ref_var object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>inst ref set var</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>inst ref set var</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseinst_ref_set_var(inst_ref_set_var object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>inst ref var</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>inst ref var</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseinst_ref_var(inst_ref_var object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseexpr(expr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>sub expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>sub expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casesub_expr(sub_expr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>conjunction</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>conjunction</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseconjunction(conjunction object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>relational expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>relational expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caserelational_expr(relational_expr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>addition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>addition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseaddition(addition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>multiplication</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>multiplication</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casemultiplication(multiplication object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>sign expr</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>sign expr</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casesign_expr(sign_expr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>boolean negation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>boolean negation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseboolean_negation(boolean_negation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>term</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>term</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseterm(term object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>instance start segment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>instance start segment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseinstance_start_segment(instance_start_segment object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>member access</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>member access</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casemember_access(member_access object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>array refs</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>array refs</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casearray_refs(array_refs object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //XoalSwitch
