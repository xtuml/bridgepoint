/**
 */
package org.xtuml.bp.xtext.masl.maslBase;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.masl.maslBase.MaslBaseFactory
 * @model kind="package"
 * @generated
 */
public interface MaslBasePackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "maslBase";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.xtuml.org/bp/xtext/masl/MASLbase";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "maslBase";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MaslBasePackage eINSTANCE = org.xtuml.bp.xtext.masl.maslBase.impl.MaslBasePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.xtuml.bp.xtext.masl.maslBase.impl.AbstractNamedImpl <em>Abstract Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.xtuml.bp.xtext.masl.maslBase.impl.AbstractNamedImpl
	 * @see org.xtuml.bp.xtext.masl.maslBase.impl.MaslBasePackageImpl#getAbstractNamed()
	 * @generated
	 */
	int ABSTRACT_NAMED = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NAMED__NAME = 0;

	/**
	 * The number of structural features of the '<em>Abstract Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NAMED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_NAMED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.xtuml.bp.xtext.masl.maslBase.StateType <em>State Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.xtuml.bp.xtext.masl.maslBase.StateType
	 * @see org.xtuml.bp.xtext.masl.maslBase.impl.MaslBasePackageImpl#getStateType()
	 * @generated
	 */
	int STATE_TYPE = 1;


	/**
	 * Returns the meta object for class '{@link org.xtuml.bp.xtext.masl.maslBase.AbstractNamed <em>Abstract Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Named</em>'.
	 * @see org.xtuml.bp.xtext.masl.maslBase.AbstractNamed
	 * @generated
	 */
	EClass getAbstractNamed();

	/**
	 * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.masl.maslBase.AbstractNamed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.xtuml.bp.xtext.masl.maslBase.AbstractNamed#getName()
	 * @see #getAbstractNamed()
	 * @generated
	 */
	EAttribute getAbstractNamed_Name();

	/**
	 * Returns the meta object for enum '{@link org.xtuml.bp.xtext.masl.maslBase.StateType <em>State Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>State Type</em>'.
	 * @see org.xtuml.bp.xtext.masl.maslBase.StateType
	 * @generated
	 */
	EEnum getStateType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MaslBaseFactory getMaslBaseFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.xtuml.bp.xtext.masl.maslBase.impl.AbstractNamedImpl <em>Abstract Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.xtuml.bp.xtext.masl.maslBase.impl.AbstractNamedImpl
		 * @see org.xtuml.bp.xtext.masl.maslBase.impl.MaslBasePackageImpl#getAbstractNamed()
		 * @generated
		 */
		EClass ABSTRACT_NAMED = eINSTANCE.getAbstractNamed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_NAMED__NAME = eINSTANCE.getAbstractNamed_Name();

		/**
		 * The meta object literal for the '{@link org.xtuml.bp.xtext.masl.maslBase.StateType <em>State Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.xtuml.bp.xtext.masl.maslBase.StateType
		 * @see org.xtuml.bp.xtext.masl.maslBase.impl.MaslBasePackageImpl#getStateType()
		 * @generated
		 */
		EEnum STATE_TYPE = eINSTANCE.getStateType();

	}

} //MaslBasePackage
