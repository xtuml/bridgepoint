/**
 */
package org.xtuml.bp.xtext.masl.maslBase.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.xtuml.bp.xtext.masl.maslBase.AbstractNamed;
import org.xtuml.bp.xtext.masl.maslBase.MaslBaseFactory;
import org.xtuml.bp.xtext.masl.maslBase.MaslBasePackage;
import org.xtuml.bp.xtext.masl.maslBase.StateType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MaslBasePackageImpl extends EPackageImpl implements MaslBasePackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractNamedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum stateTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.xtuml.bp.xtext.masl.maslBase.MaslBasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MaslBasePackageImpl()
	{
		super(eNS_URI, MaslBaseFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MaslBasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MaslBasePackage init()
	{
		if (isInited) return (MaslBasePackage)EPackage.Registry.INSTANCE.getEPackage(MaslBasePackage.eNS_URI);

		// Obtain or create and register package
		MaslBasePackageImpl theMaslBasePackage = (MaslBasePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MaslBasePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MaslBasePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMaslBasePackage.createPackageContents();

		// Initialize created meta-data
		theMaslBasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMaslBasePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MaslBasePackage.eNS_URI, theMaslBasePackage);
		return theMaslBasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractNamed()
	{
		return abstractNamedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractNamed_Name()
	{
		return (EAttribute)abstractNamedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStateType()
	{
		return stateTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MaslBaseFactory getMaslBaseFactory()
	{
		return (MaslBaseFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		abstractNamedEClass = createEClass(ABSTRACT_NAMED);
		createEAttribute(abstractNamedEClass, ABSTRACT_NAMED__NAME);

		// Create enums
		stateTypeEEnum = createEEnum(STATE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(abstractNamedEClass, AbstractNamed.class, "AbstractNamed", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractNamed_Name(), ecorePackage.getEString(), "name", null, 0, 1, AbstractNamed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(stateTypeEEnum, StateType.class, "StateType");
		addEEnumLiteral(stateTypeEEnum, StateType.ASSIGNER);
		addEEnumLiteral(stateTypeEEnum, StateType.ASSIGNER_START);
		addEEnumLiteral(stateTypeEEnum, StateType.CREATION);
		addEEnumLiteral(stateTypeEEnum, StateType.TERMINAL);

		// Create resource
		createResource(eNS_URI);
	}

} //MaslBasePackageImpl
