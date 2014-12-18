/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL.impl;

import org.argouml.xtuml.oAL.OALPackage;
import org.argouml.xtuml.oAL.TypeObjectStatement;
import org.argouml.xtuml.oAL.TypeStatement;
import org.argouml.xtuml.oAL.assignment;
import org.argouml.xtuml.oAL.statement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.impl.statementImpl#getSt1 <em>St1</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.impl.statementImpl#getSt2 <em>St2</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.impl.statementImpl#getSt3 <em>St3</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class statementImpl extends MinimalEObjectImpl.Container implements statement
{
  /**
   * The cached value of the '{@link #getSt1() <em>St1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSt1()
   * @generated
   * @ordered
   */
  protected assignment st1;

  /**
   * The cached value of the '{@link #getSt2() <em>St2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSt2()
   * @generated
   * @ordered
   */
  protected TypeObjectStatement st2;

  /**
   * The cached value of the '{@link #getSt3() <em>St3</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSt3()
   * @generated
   * @ordered
   */
  protected TypeStatement st3;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected statementImpl()
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
    return OALPackage.Literals.STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public assignment getSt1()
  {
    return st1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSt1(assignment newSt1, NotificationChain msgs)
  {
    assignment oldSt1 = st1;
    st1 = newSt1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OALPackage.STATEMENT__ST1, oldSt1, newSt1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSt1(assignment newSt1)
  {
    if (newSt1 != st1)
    {
      NotificationChain msgs = null;
      if (st1 != null)
        msgs = ((InternalEObject)st1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OALPackage.STATEMENT__ST1, null, msgs);
      if (newSt1 != null)
        msgs = ((InternalEObject)newSt1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OALPackage.STATEMENT__ST1, null, msgs);
      msgs = basicSetSt1(newSt1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OALPackage.STATEMENT__ST1, newSt1, newSt1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeObjectStatement getSt2()
  {
    return st2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSt2(TypeObjectStatement newSt2, NotificationChain msgs)
  {
    TypeObjectStatement oldSt2 = st2;
    st2 = newSt2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OALPackage.STATEMENT__ST2, oldSt2, newSt2);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSt2(TypeObjectStatement newSt2)
  {
    if (newSt2 != st2)
    {
      NotificationChain msgs = null;
      if (st2 != null)
        msgs = ((InternalEObject)st2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OALPackage.STATEMENT__ST2, null, msgs);
      if (newSt2 != null)
        msgs = ((InternalEObject)newSt2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OALPackage.STATEMENT__ST2, null, msgs);
      msgs = basicSetSt2(newSt2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OALPackage.STATEMENT__ST2, newSt2, newSt2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TypeStatement getSt3()
  {
    return st3;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSt3(TypeStatement newSt3, NotificationChain msgs)
  {
    TypeStatement oldSt3 = st3;
    st3 = newSt3;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OALPackage.STATEMENT__ST3, oldSt3, newSt3);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSt3(TypeStatement newSt3)
  {
    if (newSt3 != st3)
    {
      NotificationChain msgs = null;
      if (st3 != null)
        msgs = ((InternalEObject)st3).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OALPackage.STATEMENT__ST3, null, msgs);
      if (newSt3 != null)
        msgs = ((InternalEObject)newSt3).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OALPackage.STATEMENT__ST3, null, msgs);
      msgs = basicSetSt3(newSt3, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OALPackage.STATEMENT__ST3, newSt3, newSt3));
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
      case OALPackage.STATEMENT__ST1:
        return basicSetSt1(null, msgs);
      case OALPackage.STATEMENT__ST2:
        return basicSetSt2(null, msgs);
      case OALPackage.STATEMENT__ST3:
        return basicSetSt3(null, msgs);
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
      case OALPackage.STATEMENT__ST1:
        return getSt1();
      case OALPackage.STATEMENT__ST2:
        return getSt2();
      case OALPackage.STATEMENT__ST3:
        return getSt3();
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
      case OALPackage.STATEMENT__ST1:
        setSt1((assignment)newValue);
        return;
      case OALPackage.STATEMENT__ST2:
        setSt2((TypeObjectStatement)newValue);
        return;
      case OALPackage.STATEMENT__ST3:
        setSt3((TypeStatement)newValue);
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
      case OALPackage.STATEMENT__ST1:
        setSt1((assignment)null);
        return;
      case OALPackage.STATEMENT__ST2:
        setSt2((TypeObjectStatement)null);
        return;
      case OALPackage.STATEMENT__ST3:
        setSt3((TypeStatement)null);
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
      case OALPackage.STATEMENT__ST1:
        return st1 != null;
      case OALPackage.STATEMENT__ST2:
        return st2 != null;
      case OALPackage.STATEMENT__ST3:
        return st3 != null;
    }
    return super.eIsSet(featureID);
  }

} //statementImpl
