/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>expr2</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.expr2#getN <em>N</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.expr2#getS <em>S</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.argouml.xtuml.oAL.OALPackage#getexpr2()
 * @model
 * @generated
 */
public interface expr2 extends EObject
{
  /**
   * Returns the value of the '<em><b>N</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>N</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>N</em>' attribute.
   * @see #setN(String)
   * @see org.argouml.xtuml.oAL.OALPackage#getexpr2_N()
   * @model
   * @generated
   */
  String getN();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.expr2#getN <em>N</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>N</em>' attribute.
   * @see #getN()
   * @generated
   */
  void setN(String value);

  /**
   * Returns the value of the '<em><b>S</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>S</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>S</em>' containment reference.
   * @see #setS(sum)
   * @see org.argouml.xtuml.oAL.OALPackage#getexpr2_S()
   * @model containment="true"
   * @generated
   */
  sum getS();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.expr2#getS <em>S</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>S</em>' containment reference.
   * @see #getS()
   * @generated
   */
  void setS(sum value);

} // expr2
