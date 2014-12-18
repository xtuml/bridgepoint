/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.argouml.xtuml.oAL.OALPackage
 * @generated
 */
public interface OALFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  OALFactory eINSTANCE = org.argouml.xtuml.oAL.impl.OALFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Code</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Code</em>'.
   * @generated
   */
  Code createCode();

  /**
   * Returns a new object of class '<em>statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>statement</em>'.
   * @generated
   */
  statement createstatement();

  /**
   * Returns a new object of class '<em>Type Object Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Object Statement</em>'.
   * @generated
   */
  TypeObjectStatement createTypeObjectStatement();

  /**
   * Returns a new object of class '<em>select statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>select statement</em>'.
   * @generated
   */
  select_statement createselect_statement();

  /**
   * Returns a new object of class '<em>assignment</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>assignment</em>'.
   * @generated
   */
  assignment createassignment();

  /**
   * Returns a new object of class '<em>Type Statement</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Statement</em>'.
   * @generated
   */
  TypeStatement createTypeStatement();

  /**
   * Returns a new object of class '<em>expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>expression</em>'.
   * @generated
   */
  expression createexpression();

  /**
   * Returns a new object of class '<em>expr2</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>expr2</em>'.
   * @generated
   */
  expr2 createexpr2();

  /**
   * Returns a new object of class '<em>sum</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>sum</em>'.
   * @generated
   */
  sum createsum();

  /**
   * Returns a new object of class '<em>product</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>product</em>'.
   * @generated
   */
  product createproduct();

  /**
   * Returns a new object of class '<em>Type Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Value</em>'.
   * @generated
   */
  TypeValue createTypeValue();

  /**
   * Returns a new object of class '<em>Type Create</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Create</em>'.
   * @generated
   */
  TypeCreate createTypeCreate();

  /**
   * Returns a new object of class '<em>Type Relate</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Relate</em>'.
   * @generated
   */
  TypeRelate createTypeRelate();

  /**
   * Returns a new object of class '<em>Type Delete</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Delete</em>'.
   * @generated
   */
  TypeDelete createTypeDelete();

  /**
   * Returns a new object of class '<em>Type Statement If</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Statement If</em>'.
   * @generated
   */
  TypeStatementIf createTypeStatementIf();

  /**
   * Returns a new object of class '<em>Type Statement For</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Statement For</em>'.
   * @generated
   */
  TypeStatementFor createTypeStatementFor();

  /**
   * Returns a new object of class '<em>Type Statement While</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Statement While</em>'.
   * @generated
   */
  TypeStatementWhile createTypeStatementWhile();

  /**
   * Returns a new object of class '<em>Type Value Variable</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Value Variable</em>'.
   * @generated
   */
  TypeValueVariable createTypeValueVariable();

  /**
   * Returns a new object of class '<em>Type Constant</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Type Constant</em>'.
   * @generated
   */
  TypeConstant createTypeConstant();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  OALPackage getOALPackage();

} //OALFactory
