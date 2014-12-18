/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL.impl;

import java.util.Collection;

import org.argouml.xtuml.oAL.OALPackage;
import org.argouml.xtuml.oAL.TypeValue;
import org.argouml.xtuml.oAL.product;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.impl.productImpl#getLf <em>Lf</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.impl.productImpl#getRf <em>Rf</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class productImpl extends MinimalEObjectImpl.Container implements product
{
  /**
   * The cached value of the '{@link #getLf() <em>Lf</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLf()
   * @generated
   * @ordered
   */
  protected TypeValue lf;

  /**
   * The cached value of the '{@link #getRf() <em>Rf</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRf()
   * @generated
   * @ordered
   */
  protected EList<TypeValue> rf;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected productImpl()
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
    return OALPackage.Literals.PRODUCT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeValue getLf()
  {
    return lf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLf(TypeValue newLf, NotificationChain msgs)
  {
    TypeValue oldLf = lf;
    lf = newLf;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OALPackage.PRODUCT__LF, oldLf, newLf);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLf(TypeValue newLf)
  {
    if (newLf != lf)
    {
      NotificationChain msgs = null;
      if (lf != null)
        msgs = ((InternalEObject)lf).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OALPackage.PRODUCT__LF, null, msgs);
      if (newLf != null)
        msgs = ((InternalEObject)newLf).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OALPackage.PRODUCT__LF, null, msgs);
      msgs = basicSetLf(newLf, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OALPackage.PRODUCT__LF, newLf, newLf));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TypeValue> getRf()
  {
    if (rf == null)
    {
      rf = new EObjectContainmentEList<TypeValue>(TypeValue.class, this, OALPackage.PRODUCT__RF);
    }
    return rf;
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
      case OALPackage.PRODUCT__LF:
        return basicSetLf(null, msgs);
      case OALPackage.PRODUCT__RF:
        return ((InternalEList<?>)getRf()).basicRemove(otherEnd, msgs);
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
      case OALPackage.PRODUCT__LF:
        return getLf();
      case OALPackage.PRODUCT__RF:
        return getRf();
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
      case OALPackage.PRODUCT__LF:
        setLf((TypeValue)newValue);
        return;
      case OALPackage.PRODUCT__RF:
        getRf().clear();
        getRf().addAll((Collection<? extends TypeValue>)newValue);
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
      case OALPackage.PRODUCT__LF:
        setLf((TypeValue)null);
        return;
      case OALPackage.PRODUCT__RF:
        getRf().clear();
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
      case OALPackage.PRODUCT__LF:
        return lf != null;
      case OALPackage.PRODUCT__RF:
        return rf != null && !rf.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //productImpl
