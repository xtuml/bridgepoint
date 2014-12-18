/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.xOAL.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.xtuml.xOAL.XOALPackage;
import org.xtuml.xOAL.product;
import org.xtuml.xOAL.sum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>sum</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.impl.sumImpl#getLt <em>Lt</em>}</li>
 *   <li>{@link org.xtuml.xOAL.impl.sumImpl#getRt <em>Rt</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class sumImpl extends MinimalEObjectImpl.Container implements sum
{
  /**
   * The cached value of the '{@link #getLt() <em>Lt</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLt()
   * @generated
   * @ordered
   */
  protected product lt;

  /**
   * The cached value of the '{@link #getRt() <em>Rt</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRt()
   * @generated
   * @ordered
   */
  protected EList<product> rt;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected sumImpl()
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
    return XOALPackage.Literals.SUM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public product getLt()
  {
    return lt;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLt(product newLt, NotificationChain msgs)
  {
    product oldLt = lt;
    lt = newLt;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XOALPackage.SUM__LT, oldLt, newLt);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLt(product newLt)
  {
    if (newLt != lt)
    {
      NotificationChain msgs = null;
      if (lt != null)
        msgs = ((InternalEObject)lt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XOALPackage.SUM__LT, null, msgs);
      if (newLt != null)
        msgs = ((InternalEObject)newLt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XOALPackage.SUM__LT, null, msgs);
      msgs = basicSetLt(newLt, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XOALPackage.SUM__LT, newLt, newLt));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<product> getRt()
  {
    if (rt == null)
    {
      rt = new EObjectContainmentEList<product>(product.class, this, XOALPackage.SUM__RT);
    }
    return rt;
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
      case XOALPackage.SUM__LT:
        return basicSetLt(null, msgs);
      case XOALPackage.SUM__RT:
        return ((InternalEList<?>)getRt()).basicRemove(otherEnd, msgs);
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
      case XOALPackage.SUM__LT:
        return getLt();
      case XOALPackage.SUM__RT:
        return getRt();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case XOALPackage.SUM__LT:
        setLt((product)newValue);
        return;
      case XOALPackage.SUM__RT:
        getRt().clear();
        getRt().addAll((Collection<? extends product>)newValue);
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
      case XOALPackage.SUM__LT:
        setLt((product)null);
        return;
      case XOALPackage.SUM__RT:
        getRt().clear();
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
      case XOALPackage.SUM__LT:
        return lt != null;
      case XOALPackage.SUM__RT:
        return rt != null && !rt.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //sumImpl
