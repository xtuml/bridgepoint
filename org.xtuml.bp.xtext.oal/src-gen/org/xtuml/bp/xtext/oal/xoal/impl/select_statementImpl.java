/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.xtuml.bp.xtext.oal.xoal.XoalPackage;
import org.xtuml.bp.xtext.oal.xoal.select_statement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>select statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl#getA2 <em>A2</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl#getA3 <em>A3</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class select_statementImpl extends statementImpl implements select_statement
{
  /**
   * The default value of the '{@link #getA1() <em>A1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA1()
   * @generated
   * @ordered
   */
  protected static final String A1_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getA1() <em>A1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA1()
   * @generated
   * @ordered
   */
  protected String a1 = A1_EDEFAULT;

  /**
   * The default value of the '{@link #getA2() <em>A2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA2()
   * @generated
   * @ordered
   */
  protected static final String A2_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getA2() <em>A2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA2()
   * @generated
   * @ordered
   */
  protected String a2 = A2_EDEFAULT;

  /**
   * The default value of the '{@link #getA3() <em>A3</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA3()
   * @generated
   * @ordered
   */
  protected static final String A3_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getA3() <em>A3</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA3()
   * @generated
   * @ordered
   */
  protected String a3 = A3_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected select_statementImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return XoalPackage.Literals.SELECT_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getA1()
  {
    return a1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA1(String newA1)
  {
    String oldA1 = a1;
    a1 = newA1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.SELECT_STATEMENT__A1, oldA1, a1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getA2()
  {
    return a2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA2(String newA2)
  {
    String oldA2 = a2;
    a2 = newA2;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.SELECT_STATEMENT__A2, oldA2, a2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getA3()
  {
    return a3;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA3(String newA3)
  {
    String oldA3 = a3;
    a3 = newA3;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.SELECT_STATEMENT__A3, oldA3, a3));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case XoalPackage.SELECT_STATEMENT__A1:
        return getA1();
      case XoalPackage.SELECT_STATEMENT__A2:
        return getA2();
      case XoalPackage.SELECT_STATEMENT__A3:
        return getA3();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case XoalPackage.SELECT_STATEMENT__A1:
        setA1((String)newValue);
        return;
      case XoalPackage.SELECT_STATEMENT__A2:
        setA2((String)newValue);
        return;
      case XoalPackage.SELECT_STATEMENT__A3:
        setA3((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case XoalPackage.SELECT_STATEMENT__A1:
        setA1(A1_EDEFAULT);
        return;
      case XoalPackage.SELECT_STATEMENT__A2:
        setA2(A2_EDEFAULT);
        return;
      case XoalPackage.SELECT_STATEMENT__A3:
        setA3(A3_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case XoalPackage.SELECT_STATEMENT__A1:
        return A1_EDEFAULT == null ? a1 != null : !A1_EDEFAULT.equals(a1);
      case XoalPackage.SELECT_STATEMENT__A2:
        return A2_EDEFAULT == null ? a2 != null : !A2_EDEFAULT.equals(a2);
      case XoalPackage.SELECT_STATEMENT__A3:
        return A3_EDEFAULT == null ? a3 != null : !A3_EDEFAULT.equals(a3);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (a1: ");
    result.append(a1);
    result.append(", a2: ");
    result.append(a2);
    result.append(", a3: ");
    result.append(a3);
    result.append(')');
    return result.toString();
  }

} //select_statementImpl
