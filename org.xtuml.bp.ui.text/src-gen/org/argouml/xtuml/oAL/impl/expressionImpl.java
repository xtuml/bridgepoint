/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL.impl;

import java.util.Collection;

import org.argouml.xtuml.oAL.OALPackage;
import org.argouml.xtuml.oAL.expr2;
import org.argouml.xtuml.oAL.expression;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.argouml.xtuml.oAL.impl.expressionImpl#getNe <em>Ne</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.impl.expressionImpl#getLs <em>Ls</em>}</li>
 *   <li>{@link org.argouml.xtuml.oAL.impl.expressionImpl#getRs <em>Rs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class expressionImpl extends select_statementImpl implements expression
{
  /**
   * The cached value of the '{@link #getNe() <em>Ne</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNe()
   * @generated
   * @ordered
   */
  protected expression ne;

  /**
   * The cached value of the '{@link #getLs() <em>Ls</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLs()
   * @generated
   * @ordered
   */
  protected expr2 ls;

  /**
   * The cached value of the '{@link #getRs() <em>Rs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRs()
   * @generated
   * @ordered
   */
  protected EList<expr2> rs;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected expressionImpl()
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
    return OALPackage.Literals.EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public expression getNe()
  {
    return ne;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetNe(expression newNe, NotificationChain msgs)
  {
    expression oldNe = ne;
    ne = newNe;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OALPackage.EXPRESSION__NE, oldNe, newNe);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNe(expression newNe)
  {
    if (newNe != ne)
    {
      NotificationChain msgs = null;
      if (ne != null)
        msgs = ((InternalEObject)ne).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OALPackage.EXPRESSION__NE, null, msgs);
      if (newNe != null)
        msgs = ((InternalEObject)newNe).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OALPackage.EXPRESSION__NE, null, msgs);
      msgs = basicSetNe(newNe, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OALPackage.EXPRESSION__NE, newNe, newNe));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public expr2 getLs()
  {
    return ls;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLs(expr2 newLs, NotificationChain msgs)
  {
    expr2 oldLs = ls;
    ls = newLs;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OALPackage.EXPRESSION__LS, oldLs, newLs);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLs(expr2 newLs)
  {
    if (newLs != ls)
    {
      NotificationChain msgs = null;
      if (ls != null)
        msgs = ((InternalEObject)ls).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OALPackage.EXPRESSION__LS, null, msgs);
      if (newLs != null)
        msgs = ((InternalEObject)newLs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OALPackage.EXPRESSION__LS, null, msgs);
      msgs = basicSetLs(newLs, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OALPackage.EXPRESSION__LS, newLs, newLs));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<expr2> getRs()
  {
    if (rs == null)
    {
      rs = new EObjectContainmentEList<expr2>(expr2.class, this, OALPackage.EXPRESSION__RS);
    }
    return rs;
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
      case OALPackage.EXPRESSION__NE:
        return basicSetNe(null, msgs);
      case OALPackage.EXPRESSION__LS:
        return basicSetLs(null, msgs);
      case OALPackage.EXPRESSION__RS:
        return ((InternalEList<?>)getRs()).basicRemove(otherEnd, msgs);
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
      case OALPackage.EXPRESSION__NE:
        return getNe();
      case OALPackage.EXPRESSION__LS:
        return getLs();
      case OALPackage.EXPRESSION__RS:
        return getRs();
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
      case OALPackage.EXPRESSION__NE:
        setNe((expression)newValue);
        return;
      case OALPackage.EXPRESSION__LS:
        setLs((expr2)newValue);
        return;
      case OALPackage.EXPRESSION__RS:
        getRs().clear();
        getRs().addAll((Collection<? extends expr2>)newValue);
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
      case OALPackage.EXPRESSION__NE:
        setNe((expression)null);
        return;
      case OALPackage.EXPRESSION__LS:
        setLs((expr2)null);
        return;
      case OALPackage.EXPRESSION__RS:
        getRs().clear();
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
      case OALPackage.EXPRESSION__NE:
        return ne != null;
      case OALPackage.EXPRESSION__LS:
        return ls != null;
      case OALPackage.EXPRESSION__RS:
        return rs != null && !rs.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //expressionImpl
