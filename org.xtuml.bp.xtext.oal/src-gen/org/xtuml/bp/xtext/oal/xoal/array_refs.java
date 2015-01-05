/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>array refs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.array_refs#getA1 <em>A1</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getarray_refs()
 * @model
 * @generated
 */
public interface array_refs extends instance_start_segment
{
  /**
   * Returns the value of the '<em><b>A1</b></em>' containment reference list.
   * The list contents are of type {@link org.xtuml.bp.xtext.oal.xoal.expr}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>A1</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>A1</em>' containment reference list.
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#getarray_refs_A1()
   * @model containment="true"
   * @generated
   */
  EList<expr> getA1();

} // array_refs
