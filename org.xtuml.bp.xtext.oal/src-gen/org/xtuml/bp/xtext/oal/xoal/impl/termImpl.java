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
import org.xtuml.bp.xtext.oal.xoal.expr;
import org.xtuml.bp.xtext.oal.xoal.term;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.termImpl#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.termImpl#getA2 <em>A2</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class termImpl extends sign_exprImpl implements term
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
   * The cached value of the '{@link #getA2() <em>A2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA2()
   * @generated
   * @ordered
   */
  protected expr a2;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected termImpl()
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
    return XoalPackage.Literals.TERM;
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
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.TERM__A1, oldA1, a1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public expr getA2()
  {
    return a2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetA2(expr newA2, NotificationChain msgs)
  {
    expr oldA2 = a2;
    a2 = newA2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XoalPackage.TERM__A2, oldA2, newA2);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA2(expr newA2)
  {
    if (newA2 != a2)
    {
      NotificationChain msgs = null;
      if (a2 != null)
        msgs = ((InternalEObject)a2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XoalPackage.TERM__A2, null, msgs);
      if (newA2 != null)
        msgs = ((InternalEObject)newA2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XoalPackage.TERM__A2, null, msgs);
      msgs = basicSetA2(newA2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.TERM__A2, newA2, newA2));
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
      case XoalPackage.TERM__A2:
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
      case XoalPackage.TERM__A1:
        return getA1();
      case XoalPackage.TERM__A2:
        return getA2();
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
      case XoalPackage.TERM__A1:
        setA1((String)newValue);
        return;
      case XoalPackage.TERM__A2:
        setA2((expr)newValue);
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
      case XoalPackage.TERM__A1:
        setA1(A1_EDEFAULT);
        return;
      case XoalPackage.TERM__A2:
        setA2((expr)null);
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
      case XoalPackage.TERM__A1:
        return A1_EDEFAULT == null ? a1 != null : !A1_EDEFAULT.equals(a1);
      case XoalPackage.TERM__A2:
        return a2 != null;
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
    result.append(')');
    return result.toString();
  }

} //termImpl
