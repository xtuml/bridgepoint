/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL.util;

import org.argouml.xtuml.oAL.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.argouml.xtuml.oAL.OALPackage
 * @generated
 */
public class OALSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static OALPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OALSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = OALPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case OALPackage.CODE:
      {
        Code code = (Code)theEObject;
        T result = caseCode(code);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.STATEMENT:
      {
        statement statement = (statement)theEObject;
        T result = casestatement(statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_OBJECT_STATEMENT:
      {
        TypeObjectStatement typeObjectStatement = (TypeObjectStatement)theEObject;
        T result = caseTypeObjectStatement(typeObjectStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.SELECT_STATEMENT:
      {
        select_statement select_statement = (select_statement)theEObject;
        T result = caseselect_statement(select_statement);
        if (result == null) result = caseTypeObjectStatement(select_statement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.ASSIGNMENT:
      {
        assignment assignment = (assignment)theEObject;
        T result = caseassignment(assignment);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_STATEMENT:
      {
        TypeStatement typeStatement = (TypeStatement)theEObject;
        T result = caseTypeStatement(typeStatement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.EXPRESSION:
      {
        expression expression = (expression)theEObject;
        T result = caseexpression(expression);
        if (result == null) result = caseselect_statement(expression);
        if (result == null) result = caseTypeValue(expression);
        if (result == null) result = caseTypeObjectStatement(expression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.EXPR2:
      {
        expr2 expr2 = (expr2)theEObject;
        T result = caseexpr2(expr2);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.SUM:
      {
        sum sum = (sum)theEObject;
        T result = casesum(sum);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.PRODUCT:
      {
        product product = (product)theEObject;
        T result = caseproduct(product);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_VALUE:
      {
        TypeValue typeValue = (TypeValue)theEObject;
        T result = caseTypeValue(typeValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_CREATE:
      {
        TypeCreate typeCreate = (TypeCreate)theEObject;
        T result = caseTypeCreate(typeCreate);
        if (result == null) result = caseTypeObjectStatement(typeCreate);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_RELATE:
      {
        TypeRelate typeRelate = (TypeRelate)theEObject;
        T result = caseTypeRelate(typeRelate);
        if (result == null) result = caseTypeObjectStatement(typeRelate);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_DELETE:
      {
        TypeDelete typeDelete = (TypeDelete)theEObject;
        T result = caseTypeDelete(typeDelete);
        if (result == null) result = caseTypeObjectStatement(typeDelete);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_STATEMENT_IF:
      {
        TypeStatementIf typeStatementIf = (TypeStatementIf)theEObject;
        T result = caseTypeStatementIf(typeStatementIf);
        if (result == null) result = caseTypeStatement(typeStatementIf);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_STATEMENT_FOR:
      {
        TypeStatementFor typeStatementFor = (TypeStatementFor)theEObject;
        T result = caseTypeStatementFor(typeStatementFor);
        if (result == null) result = caseTypeStatement(typeStatementFor);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_STATEMENT_WHILE:
      {
        TypeStatementWhile typeStatementWhile = (TypeStatementWhile)theEObject;
        T result = caseTypeStatementWhile(typeStatementWhile);
        if (result == null) result = caseTypeStatement(typeStatementWhile);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_VALUE_VARIABLE:
      {
        TypeValueVariable typeValueVariable = (TypeValueVariable)theEObject;
        T result = caseTypeValueVariable(typeValueVariable);
        if (result == null) result = caseTypeValue(typeValueVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case OALPackage.TYPE_CONSTANT:
      {
        TypeConstant typeConstant = (TypeConstant)theEObject;
        T result = caseTypeConstant(typeConstant);
        if (result == null) result = caseTypeValue(typeConstant);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Code</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Code</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCode(Code object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casestatement(statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Object Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Object Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeObjectStatement(TypeObjectStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>select statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>select statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseselect_statement(select_statement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>assignment</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>assignment</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseassignment(assignment object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Statement</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Statement</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeStatement(TypeStatement object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseexpression(expression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>expr2</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>expr2</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseexpr2(expr2 object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>sum</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>sum</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casesum(sum object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>product</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>product</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseproduct(product object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeValue(TypeValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Create</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Create</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeCreate(TypeCreate object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Relate</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Relate</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeRelate(TypeRelate object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Delete</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Delete</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeDelete(TypeDelete object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Statement If</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Statement If</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeStatementIf(TypeStatementIf object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Statement For</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Statement For</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeStatementFor(TypeStatementFor object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Statement While</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Statement While</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeStatementWhile(TypeStatementWhile object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Value Variable</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Value Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeValueVariable(TypeValueVariable object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type Constant</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type Constant</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTypeConstant(TypeConstant object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //OALSwitch
