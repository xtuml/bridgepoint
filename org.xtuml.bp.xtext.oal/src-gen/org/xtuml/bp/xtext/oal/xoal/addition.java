/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>addition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.addition#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.addition#getA2 <em>A2</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getaddition()
 * @model
 * @generated
 */
public interface addition extends EObject
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
   * @see #setA1(multiplication)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getaddition_A1()
   * @model containment="true"
   * @generated
   */
  multiplication getA1();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.addition#getA1 <em>A1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A1</em>' containment reference.
   * @see #getA1()
   * @generated
   */
  void setA1(multiplication value);

  /**
   * Returns the value of the '<em><b>A2</b></em>' containment reference list.
   * The list contents are of type {@link org.xtuml.bp.xtext.oal.xoal.multiplication}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A2</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A2</em>' containment reference list.
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getaddition_A2()
   * @model containment="true"
   * @generated
   */
  EList<multiplication> getA2();

} // addition
