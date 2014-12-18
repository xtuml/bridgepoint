/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.statement#getSt1 <em>St1</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.statement#getSt2 <em>St2</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.statement#getSt3 <em>St3</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.argouml.xtuml.oAL.OALPackage#getstatement()
 * @model
 * @generated
 */
public interface statement extends EObject
{
  /**
   * Returns the value of the '<em><b>St1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>St1</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>St1</em>' containment reference.
   * @see #setSt1(assignment)
   * @see org.argouml.xtuml.oAL.OALPackage#getstatement_St1()
   * @model containment="true"
   * @generated
   */
  assignment getSt1();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.statement#getSt1 <em>St1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>St1</em>' containment reference.
   * @see #getSt1()
   * @generated
   */
  void setSt1(assignment value);

  /**
   * Returns the value of the '<em><b>St2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>St2</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>St2</em>' containment reference.
   * @see #setSt2(TypeObjectStatement)
   * @see org.argouml.xtuml.oAL.OALPackage#getstatement_St2()
   * @model containment="true"
   * @generated
   */
  TypeObjectStatement getSt2();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.statement#getSt2 <em>St2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>St2</em>' containment reference.
   * @see #getSt2()
   * @generated
   */
  void setSt2(TypeObjectStatement value);

  /**
   * Returns the value of the '<em><b>St3</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>St3</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>St3</em>' containment reference.
   * @see #setSt3(TypeStatement)
   * @see org.argouml.xtuml.oAL.OALPackage#getstatement_St3()
   * @model containment="true"
   * @generated
   */
  TypeStatement getSt3();

  /**
   * Sets the value of the '{@link org.argouml.xtuml.oAL.statement#getSt3 <em>St3</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>St3</em>' containment reference.
   * @see #getSt3()
   * @generated
   */
  void setSt3(TypeStatement value);

} // statement
