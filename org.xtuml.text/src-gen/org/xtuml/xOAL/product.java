/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.xOAL;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>product</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.product#getLf <em>Lf</em>}</li>
 *   <li>{@link org.xtuml.xOAL.product#getRf <em>Rf</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.xtuml.xOAL.XOALPackage#getproduct()
 * @model
 * @generated
 */
public interface product extends EObject
{
  /**
   * Returns the value of the '<em><b>Lf</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lf</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lf</em>' containment reference.
   * @see #setLf(TypeValue)
   * @see org.xtuml.xOAL.XOALPackage#getproduct_Lf()
   * @model containment="true"
   * @generated
   */
  TypeValue getLf();

  /**
   * Sets the value of the '{@link org.xtuml.xOAL.product#getLf <em>Lf</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lf</em>' containment reference.
   * @see #getLf()
   * @generated
   */
  void setLf(TypeValue value);

  /**
   * Returns the value of the '<em><b>Rf</b></em>' containment reference list.
   * The list contents are of type {@link org.xtuml.xOAL.TypeValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Rf</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rf</em>' containment reference list.
   * @see org.xtuml.xOAL.XOALPackage#getproduct_Rf()
   * @model containment="true"
   * @generated
   */
  EList<TypeValue> getRf();

} // product
