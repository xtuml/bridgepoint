/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage
 * @generated
 */
public interface XoalFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  XoalFactory eINSTANCE = org.xtuml.bp.xtext.oal.xoal.impl.XoalFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Code</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Code</em>'.
   * @generated
   */
  Code createCode();

  /**
   * Returns a new object of class '<em>block</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>block</em>'.
   * @generated
   */
  block createblock();

  /**
   * Returns a new object of class '<em>statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>statement</em>'.
   * @generated
   */
  statement createstatement();

  /**
   * Returns a new object of class '<em>assignment statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>assignment statement</em>'.
   * @generated
   */
  assignment_statement createassignment_statement();

  /**
   * Returns a new object of class '<em>break statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>break statement</em>'.
   * @generated
   */
  break_statement createbreak_statement();

  /**
   * Returns a new object of class '<em>bridge statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>bridge statement</em>'.
   * @generated
   */
  bridge_statement createbridge_statement();

  /**
   * Returns a new object of class '<em>send statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>send statement</em>'.
   * @generated
   */
  send_statement createsend_statement();

  /**
   * Returns a new object of class '<em>control statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>control statement</em>'.
   * @generated
   */
  control_statement createcontrol_statement();

  /**
   * Returns a new object of class '<em>continue statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>continue statement</em>'.
   * @generated
   */
  continue_statement createcontinue_statement();

  /**
   * Returns a new object of class '<em>create event statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>create event statement</em>'.
   * @generated
   */
  create_event_statement createcreate_event_statement();

  /**
   * Returns a new object of class '<em>create object statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>create object statement</em>'.
   * @generated
   */
  create_object_statement createcreate_object_statement();

  /**
   * Returns a new object of class '<em>delete statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>delete statement</em>'.
   * @generated
   */
  delete_statement createdelete_statement();

  /**
   * Returns a new object of class '<em>for statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>for statement</em>'.
   * @generated
   */
  for_statement createfor_statement();

  /**
   * Returns a new object of class '<em>generate statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>generate statement</em>'.
   * @generated
   */
  generate_statement creategenerate_statement();

  /**
   * Returns a new object of class '<em>if statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>if statement</em>'.
   * @generated
   */
  if_statement createif_statement();

  /**
   * Returns a new object of class '<em>relate statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>relate statement</em>'.
   * @generated
   */
  relate_statement createrelate_statement();

  /**
   * Returns a new object of class '<em>return statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>return statement</em>'.
   * @generated
   */
  return_statement createreturn_statement();

  /**
   * Returns a new object of class '<em>select statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>select statement</em>'.
   * @generated
   */
  select_statement createselect_statement();

  /**
   * Returns a new object of class '<em>unrelate statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>unrelate statement</em>'.
   * @generated
   */
  unrelate_statement createunrelate_statement();

  /**
   * Returns a new object of class '<em>while statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>while statement</em>'.
   * @generated
   */
  while_statement createwhile_statement();

  /**
   * Returns a new object of class '<em>assignment expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>assignment expr</em>'.
   * @generated
   */
  assignment_expr createassignment_expr();

  /**
   * Returns a new object of class '<em>bridge invocation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>bridge invocation</em>'.
   * @generated
   */
  bridge_invocation createbridge_invocation();

  /**
   * Returns a new object of class '<em>bridge expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>bridge expr</em>'.
   * @generated
   */
  bridge_expr createbridge_expr();

  /**
   * Returns a new object of class '<em>assoc obj inst ref var</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>assoc obj inst ref var</em>'.
   * @generated
   */
  assoc_obj_inst_ref_var createassoc_obj_inst_ref_var();

  /**
   * Returns a new object of class '<em>inst ref set var</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>inst ref set var</em>'.
   * @generated
   */
  inst_ref_set_var createinst_ref_set_var();

  /**
   * Returns a new object of class '<em>inst ref var</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>inst ref var</em>'.
   * @generated
   */
  inst_ref_var createinst_ref_var();

  /**
   * Returns a new object of class '<em>expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>expr</em>'.
   * @generated
   */
  expr createexpr();

  /**
   * Returns a new object of class '<em>sub expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>sub expr</em>'.
   * @generated
   */
  sub_expr createsub_expr();

  /**
   * Returns a new object of class '<em>conjunction</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>conjunction</em>'.
   * @generated
   */
  conjunction createconjunction();

  /**
   * Returns a new object of class '<em>relational expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>relational expr</em>'.
   * @generated
   */
  relational_expr createrelational_expr();

  /**
   * Returns a new object of class '<em>addition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>addition</em>'.
   * @generated
   */
  addition createaddition();

  /**
   * Returns a new object of class '<em>multiplication</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>multiplication</em>'.
   * @generated
   */
  multiplication createmultiplication();

  /**
   * Returns a new object of class '<em>sign expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>sign expr</em>'.
   * @generated
   */
  sign_expr createsign_expr();

  /**
   * Returns a new object of class '<em>boolean negation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>boolean negation</em>'.
   * @generated
   */
  boolean_negation createboolean_negation();

  /**
   * Returns a new object of class '<em>term</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>term</em>'.
   * @generated
   */
  term createterm();

  /**
   * Returns a new object of class '<em>instance start segment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>instance start segment</em>'.
   * @generated
   */
  instance_start_segment createinstance_start_segment();

  /**
   * Returns a new object of class '<em>member access</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>member access</em>'.
   * @generated
   */
  member_access createmember_access();

  /**
   * Returns a new object of class '<em>array refs</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>array refs</em>'.
   * @generated
   */
  array_refs createarray_refs();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  XoalPackage getXoalPackage();

} //XoalFactory
