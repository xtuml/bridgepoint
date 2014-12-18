/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Statement While</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.TypeStatementWhile#getExpr <em>Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.argouml.xtuml.oAL.OALPackage#getTypeStatementWhile()
 * @model
 * @generated
 */
public interface TypeStatementWhile extends TypeStatement
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
   * @see org.argouml.xtuml.oAL.OALPackage#getTypeStatementWhile_Expr()
   * @model containment="true"
   * @generated
   */
  expression getExpr();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.TypeStatementWhile#getExpr <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expr</em>' containment reference.
   * @see #getExpr()
   * @generated
   */
  void setExpr(expression value);

} // TypeStatementWhile
