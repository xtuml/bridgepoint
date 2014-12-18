/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.xOAL;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>select statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.select_statement#getVar <em>Var</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.xOAL.XOALPackage#getselect_statement()
 * @model
 * @generated
 */
public interface select_statement extends TypeObjectStatement
{
  /**
   * Returns the value of the '<em><b>Var</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Var</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Var</em>' attribute.
   * @see #setVar(String)
   * @see org.xtuml.xOAL.XOALPackage#getselect_statement_Var()
   * @model
   * @generated
   */
  String getVar();

  /**
   * Sets the value of the '{@link org.xtuml.xOAL.select_statement#getVar <em>Var</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Var</em>' attribute.
   * @see #getVar()
   * @generated
   */
  void setVar(String value);

} // select_statement
