/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.TypeStatement#getSubstatements <em>Substatements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.argouml.xtuml.oAL.OALPackage#getTypeStatement()
 * @model
 * @generated
 */
public interface TypeStatement extends EObject
{
  /**
   * Returns the value of the '<em><b>Substatements</b></em>' containment reference list.
   * The list contents are of type {@link org.argouml.xtuml.oAL.statement}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Substatements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Substatements</em>' containment reference list.
   * @see org.argouml.xtuml.oAL.OALPackage#getTypeStatement_Substatements()
   * @model containment="true"
   * @generated
   */
  EList<statement> getSubstatements();

} // TypeStatement
