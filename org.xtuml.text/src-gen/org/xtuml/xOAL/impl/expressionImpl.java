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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.xtuml.xOAL.XOALPackage;
import org.xtuml.xOAL.expr2;
import org.xtuml.xOAL.expression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.impl.expressionImpl#getNe <em>Ne</em>}</li>
 *   <li>{@link org.xtuml.xOAL.impl.expressionImpl#getLs <em>Ls</em>}</li>
 *   <li>{@link org.xtuml.xOAL.impl.expressionImpl#getRs <em>Rs</em>}</li>
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
    return XOALPackage.Literals.EXPRESSION;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XOALPackage.EXPRESSION__NE, oldNe, newNe);
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
        msgs = ((InternalEObject)ne).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XOALPackage.EXPRESSION__NE, null, msgs);
      if (newNe != null)
        msgs = ((InternalEObject)newNe).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XOALPackage.EXPRESSION__NE, null, msgs);
      msgs = basicSetNe(newNe, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XOALPackage.EXPRESSION__NE, newNe, newNe));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XOALPackage.EXPRESSION__LS, oldLs, newLs);
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
        msgs = ((InternalEObject)ls).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XOALPackage.EXPRESSION__LS, null, msgs);
      if (newLs != null)
        msgs = ((InternalEObject)newLs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XOALPackage.EXPRESSION__LS, null, msgs);
      msgs = basicSetLs(newLs, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XOALPackage.EXPRESSION__LS, newLs, newLs));
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
      rs = new EObjectContainmentEList<expr2>(expr2.class, this, XOALPackage.EXPRESSION__RS);
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
      case XOALPackage.EXPRESSION__NE:
        return basicSetNe(null, msgs);
      case XOALPackage.EXPRESSION__LS:
        return basicSetLs(null, msgs);
      case XOALPackage.EXPRESSION__RS:
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
      case XOALPackage.EXPRESSION__NE:
        return getNe();
      case XOALPackage.EXPRESSION__LS:
        return getLs();
      case XOALPackage.EXPRESSION__RS:
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
      case XOALPackage.EXPRESSION__NE:
        setNe((expression)newValue);
        return;
      case XOALPackage.EXPRESSION__LS:
        setLs((expr2)newValue);
        return;
      case XOALPackage.EXPRESSION__RS:
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
      case XOALPackage.EXPRESSION__NE:
        setNe((expression)null);
        return;
      case XOALPackage.EXPRESSION__LS:
        setLs((expr2)null);
        return;
      case XOALPackage.EXPRESSION__RS:
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
      case XOALPackage.EXPRESSION__NE:
        return ne != null;
      case XOALPackage.EXPRESSION__LS:
        return ls != null;
      case XOALPackage.EXPRESSION__RS:
        return rs != null && !rs.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //expressionImpl
