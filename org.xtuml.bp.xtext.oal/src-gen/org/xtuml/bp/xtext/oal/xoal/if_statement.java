/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>if statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA2 <em>A2</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA3 <em>A3</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA4 <em>A4</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA5 <em>A5</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getif_statement()
 * @model
 * @generated
 */
public interface if_statement extends statement
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
   * @see #setA1(expr)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getif_statement_A1()
   * @model containment="true"
   * @generated
   */
  expr getA1();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA1 <em>A1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A1</em>' containment reference.
   * @see #getA1()
   * @generated
   */
  void setA1(expr value);

  /**
   * Returns the value of the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A2</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A2</em>' containment reference.
   * @see #setA2(block)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getif_statement_A2()
   * @model containment="true"
   * @generated
   */
  block getA2();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA2 <em>A2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A2</em>' containment reference.
   * @see #getA2()
   * @generated
   */
  void setA2(block value);

  /**
   * Returns the value of the '<em><b>A3</b></em>' containment reference list.
   * The list contents are of type {@link org.xtuml.bp.xtext.oal.xoal.expr}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A3</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A3</em>' containment reference list.
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getif_statement_A3()
   * @model containment="true"
   * @generated
   */
  EList<expr> getA3();

  /**
   * Returns the value of the '<em><b>A4</b></em>' containment reference list.
   * The list contents are of type {@link org.xtuml.bp.xtext.oal.xoal.block}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A4</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A4</em>' containment reference list.
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getif_statement_A4()
   * @model containment="true"
   * @generated
   */
  EList<block> getA4();

  /**
   * Returns the value of the '<em><b>A5</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A5</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A5</em>' containment reference.
   * @see #setA5(block)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getif_statement_A5()
   * @model containment="true"
   * @generated
   */
  block getA5();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA5 <em>A5</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A5</em>' containment reference.
   * @see #getA5()
   * @generated
   */
  void setA5(block value);

} // if_statement
