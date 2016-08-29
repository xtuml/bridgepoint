/**
 */
package org.xtuml.bp.xtext.masl.maslBase;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.masl.maslBase.MaslBasePackage
 * @generated
 */
public interface MaslBaseFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MaslBaseFactory eINSTANCE = org.xtuml.bp.xtext.masl.maslBase.impl.MaslBaseFactoryImpl.init();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MaslBasePackage getMaslBasePackage();

} //MaslBaseFactory
