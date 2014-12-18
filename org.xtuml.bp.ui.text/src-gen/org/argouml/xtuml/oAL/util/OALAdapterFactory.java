/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL.util;

import org.argouml.xtuml.oAL.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.argouml.xtuml.oAL.OALPackage
 * @generated
 */
public class OALAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static OALPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OALAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = OALPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OALSwitch<Adapter> modelSwitch =
    new OALSwitch<Adapter>()
    {
      @Override
      public Adapter caseCode(Code object)
      {
        return createCodeAdapter();
      }
      @Override
      public Adapter casestatement(statement object)
      {
        return createstatementAdapter();
      }
      @Override
      public Adapter caseTypeObjectStatement(TypeObjectStatement object)
      {
        return createTypeObjectStatementAdapter();
      }
      @Override
      public Adapter caseselect_statement(select_statement object)
      {
        return createselect_statementAdapter();
      }
      @Override
      public Adapter caseassignment(assignment object)
      {
        return createassignmentAdapter();
      }
      @Override
      public Adapter caseTypeStatement(TypeStatement object)
      {
        return createTypeStatementAdapter();
      }
      @Override
      public Adapter caseexpression(expression object)
      {
        return createexpressionAdapter();
      }
      @Override
      public Adapter caseexpr2(expr2 object)
      {
        return createexpr2Adapter();
      }
      @Override
      public Adapter casesum(sum object)
      {
        return createsumAdapter();
      }
      @Override
      public Adapter caseproduct(product object)
      {
        return createproductAdapter();
      }
      @Override
      public Adapter caseTypeValue(TypeValue object)
      {
        return createTypeValueAdapter();
      }
      @Override
      public Adapter caseTypeCreate(TypeCreate object)
      {
        return createTypeCreateAdapter();
      }
      @Override
      public Adapter caseTypeRelate(TypeRelate object)
      {
        return createTypeRelateAdapter();
      }
      @Override
      public Adapter caseTypeDelete(TypeDelete object)
      {
        return createTypeDeleteAdapter();
      }
      @Override
      public Adapter caseTypeStatementIf(TypeStatementIf object)
      {
        return createTypeStatementIfAdapter();
      }
      @Override
      public Adapter caseTypeStatementFor(TypeStatementFor object)
      {
        return createTypeStatementForAdapter();
      }
      @Override
      public Adapter caseTypeStatementWhile(TypeStatementWhile object)
      {
        return createTypeStatementWhileAdapter();
      }
      @Override
      public Adapter caseTypeValueVariable(TypeValueVariable object)
      {
        return createTypeValueVariableAdapter();
      }
      @Override
      public Adapter caseTypeConstant(TypeConstant object)
      {
        return createTypeConstantAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.Code <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.Code
   * @generated
   */
  public Adapter createCodeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.statement <em>statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.statement
   * @generated
   */
  public Adapter createstatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeObjectStatement <em>Type Object Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeObjectStatement
   * @generated
   */
  public Adapter createTypeObjectStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.select_statement <em>select statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.select_statement
   * @generated
   */
  public Adapter createselect_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.assignment <em>assignment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.assignment
   * @generated
   */
  public Adapter createassignmentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeStatement <em>Type Statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeStatement
   * @generated
   */
  public Adapter createTypeStatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.expression <em>expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.expression
   * @generated
   */
  public Adapter createexpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.expr2 <em>expr2</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.expr2
   * @generated
   */
  public Adapter createexpr2Adapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.sum <em>sum</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.sum
   * @generated
   */
  public Adapter createsumAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.product <em>product</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.product
   * @generated
   */
  public Adapter createproductAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeValue <em>Type Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeValue
   * @generated
   */
  public Adapter createTypeValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeCreate <em>Type Create</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeCreate
   * @generated
   */
  public Adapter createTypeCreateAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeRelate <em>Type Relate</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeRelate
   * @generated
   */
  public Adapter createTypeRelateAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeDelete <em>Type Delete</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeDelete
   * @generated
   */
  public Adapter createTypeDeleteAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeStatementIf <em>Type Statement If</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeStatementIf
   * @generated
   */
  public Adapter createTypeStatementIfAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeStatementFor <em>Type Statement For</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeStatementFor
   * @generated
   */
  public Adapter createTypeStatementForAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeStatementWhile <em>Type Statement While</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeStatementWhile
   * @generated
   */
  public Adapter createTypeStatementWhileAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeValueVariable <em>Type Value Variable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeValueVariable
   * @generated
   */
  public Adapter createTypeValueVariableAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.argouml.xtuml.oAL.TypeConstant <em>Type Constant</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.argouml.xtuml.oAL.TypeConstant
   * @generated
   */
  public Adapter createTypeConstantAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //OALAdapterFactory
