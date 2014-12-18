/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.assignment#getE <em>E</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.argouml.xtuml.oAL.OALPackage#getassignment()
 * @model
 * @generated
 */
public interface assignment extends EObject
{
  /**
   * Returns the value of the '<em><b>E</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>E</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>E</em>' containment reference.
   * @see #setE(expression)
   * @see org.argouml.xtuml.oAL.OALPackage#getassignment_E()
   * @model containment="true"
   * @generated
   */
  expression getE();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.assignment#getE <em>E</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>E</em>' containment reference.
   * @see #getE()
   * @generated
   */
  void setE(expression value);

} // assignment
