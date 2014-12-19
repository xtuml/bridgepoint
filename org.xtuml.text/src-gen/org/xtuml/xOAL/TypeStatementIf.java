/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.xOAL;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Statement If</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.TypeStatementIf#getExpr <em>Expr</em>}</li>
 *   <li>{@link org.xtuml.xOAL.TypeStatementIf#getElifexpr <em>Elifexpr</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.xOAL.XOALPackage#getTypeStatementIf()
 * @model
 * @generated
 */
public interface TypeStatementIf extends TypeStatement
{
  /**
   * Returns the value of the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expr</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expr</em>' containment reference.
   * @see #setExpr(expression)
   * @see org.xtuml.xOAL.XOALPackage#getTypeStatementIf_Expr()
   * @model containment="true"
   * @generated
   */
  expression getExpr();

  /**
   * Sets the value of the '{@link org.xtuml.xOAL.TypeStatementIf#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(expression value);

  /**
   * Returns the value of the '<em><b>Elifexpr</b></em>' containment reference list.
   * The list contents are of type {@link org.xtuml.xOAL.expression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Elifexpr</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Elifexpr</em>' containment reference list.
   * @see org.xtuml.xOAL.XOALPackage#getTypeStatementIf_Elifexpr()
   * @model containment="true"
   * @generated
   */
  EList<expression> getElifexpr();

} // TypeStatementIf
