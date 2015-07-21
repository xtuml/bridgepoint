/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>expr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.expr#getA1 <em>A1</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getexpr()
 * @model
 * @generated
 */
public interface expr extends EObject
{
  /**
   * Returns the value of the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A1</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A1</em>' containment reference.
   * @see #setA1(sub_expr)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getexpr_A1()
   * @model containment="true"
   * @generated
   */
  sub_expr getA1();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.expr#getA1 <em>A1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A1</em>' containment reference.
   * @see #getA1()
   * @generated
   */
  void setA1(sub_expr value);

} // expr
