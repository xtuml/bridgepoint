/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.xtuml.bp.xtext.oal.xoal.XoalPackage;
import org.xtuml.bp.xtext.oal.xoal.inst_ref_var;
import org.xtuml.bp.xtext.oal.xoal.unrelate_statement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>unrelate statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl#getA2 <em>A2</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl#getA3 <em>A3</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class unrelate_statementImpl extends statementImpl implements unrelate_statement
{
  /**
   * The cached value of the '{@link #getA1() <em>A1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA1()
   * @generated
   * @ordered
   */
  protected inst_ref_var a1;

  /**
   * The cached value of the '{@link #getA2() <em>A2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA2()
   * @generated
   * @ordered
   */
  protected inst_ref_var a2;

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
  protected unrelate_statementImpl()
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
    return XoalPackage.Literals.UNRELATE_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public inst_ref_var getA1()
  {
    return a1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetA1(inst_ref_var newA1, NotificationChain msgs)
  {
    inst_ref_var oldA1 = a1;
    a1 = newA1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XoalPackage.UNRELATE_STATEMENT__A1, oldA1, newA1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA1(inst_ref_var newA1)
  {
    if (newA1 != a1)
    {
      NotificationChain msgs = null;
      if (a1 != null)
        msgs = ((InternalEObject)a1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XoalPackage.UNRELATE_STATEMENT__A1, null, msgs);
      if (newA1 != null)
        msgs = ((InternalEObject)newA1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XoalPackage.UNRELATE_STATEMENT__A1, null, msgs);
      msgs = basicSetA1(newA1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.UNRELATE_STATEMENT__A1, newA1, newA1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public inst_ref_var getA2()
  {
    return a2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetA2(inst_ref_var newA2, NotificationChain msgs)
  {
    inst_ref_var oldA2 = a2;
    a2 = newA2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XoalPackage.UNRELATE_STATEMENT__A2, oldA2, newA2);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA2(inst_ref_var newA2)
  {
    if (newA2 != a2)
    {
      NotificationChain msgs = null;
      if (a2 != null)
        msgs = ((InternalEObject)a2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XoalPackage.UNRELATE_STATEMENT__A2, null, msgs);
      if (newA2 != null)
        msgs = ((InternalEObject)newA2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XoalPackage.UNRELATE_STATEMENT__A2, null, msgs);
      msgs = basicSetA2(newA2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.UNRELATE_STATEMENT__A2, newA2, newA2));
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
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.UNRELATE_STATEMENT__A3, oldA3, a3));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case XoalPackage.UNRELATE_STATEMENT__A1:
        return basicSetA1(null, msgs);
      case XoalPackage.UNRELATE_STATEMENT__A2:
        return basicSetA2(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case XoalPackage.UNRELATE_STATEMENT__A1:
        return getA1();
      case XoalPackage.UNRELATE_STATEMENT__A2:
        return getA2();
      case XoalPackage.UNRELATE_STATEMENT__A3:
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
      case XoalPackage.UNRELATE_STATEMENT__A1:
        setA1((inst_ref_var)newValue);
        return;
      case XoalPackage.UNRELATE_STATEMENT__A2:
        setA2((inst_ref_var)newValue);
        return;
      case XoalPackage.UNRELATE_STATEMENT__A3:
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
      case XoalPackage.UNRELATE_STATEMENT__A1:
        setA1((inst_ref_var)null);
        return;
      case XoalPackage.UNRELATE_STATEMENT__A2:
        setA2((inst_ref_var)null);
        return;
      case XoalPackage.UNRELATE_STATEMENT__A3:
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
      case XoalPackage.UNRELATE_STATEMENT__A1:
        return a1 != null;
      case XoalPackage.UNRELATE_STATEMENT__A2:
        return a2 != null;
      case XoalPackage.UNRELATE_STATEMENT__A3:
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
    result.append(" (a3: ");
    result.append(a3);
    result.append(')');
    return result.toString();
  }

} //unrelate_statementImpl
