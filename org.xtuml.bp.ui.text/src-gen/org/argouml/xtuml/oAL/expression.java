/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.expression#getNe <em>Ne</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.expression#getLs <em>Ls</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.expression#getRs <em>Rs</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.argouml.xtuml.oAL.OALPackage#getexpression()
 * @model
 * @generated
 */
public interface expression extends select_statement, TypeValue
{
  /**
   * Returns the value of the '<em><b>Ne</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ne</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ne</em>' containment reference.
   * @see #setNe(expression)
   * @see org.argouml.xtuml.oAL.OALPackage#getexpression_Ne()
   * @model containment="true"
   * @generated
   */
  expression getNe();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.expression#getNe <em>Ne</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ne</em>' containment reference.
   * @see #getNe()
   * @generated
   */
  void setNe(expression value);

  /**
   * Returns the value of the '<em><b>Ls</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ls</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ls</em>' containment reference.
   * @see #setLs(expr2)
   * @see org.argouml.xtuml.oAL.OALPackage#getexpression_Ls()
   * @model containment="true"
   * @generated
   */
  expr2 getLs();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.expression#getLs <em>Ls</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ls</em>' containment reference.
   * @see #getLs()
   * @generated
   */
  void setLs(expr2 value);

  /**
   * Returns the value of the '<em><b>Rs</b></em>' containment reference list.
   * The list contents are of type {@link org.argouml.xtuml.oAL.expr2}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Rs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rs</em>' containment reference list.
   * @see org.argouml.xtuml.oAL.OALPackage#getexpression_Rs()
   * @model containment="true"
   * @generated
   */
  EList<expr2> getRs();

} // expression
