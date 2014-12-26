/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>return statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.return_statement#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.return_statement#getA2 <em>A2</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getreturn_statement()
 * @model
 * @generated
 */
public interface return_statement extends statement
{
  /**
   * Returns the value of the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A1</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A1</em>' attribute.
   * @see #setA1(String)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getreturn_statement_A1()
   * @model
   * @generated
   */
  String getA1();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.return_statement#getA1 <em>A1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A1</em>' attribute.
   * @see #getA1()
   * @generated
   */
  void setA1(String value);

  /**
   * Returns the value of the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A2</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A2</em>' containment reference.
   * @see #setA2(expr)
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getreturn_statement_A2()
   * @model containment="true"
   * @generated
   */
  expr getA2();

  /**
   * Sets the value of the '{@link org.xtuml.bp.xtext.oal.xoal.return_statement#getA2 <em>A2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>A2</em>' containment reference.
   * @see #getA2()
   * @generated
   */
  void setA2(expr value);

} // return_statement
