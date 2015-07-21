/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.xtuml.bp.xtext.oal.xoal.XoalPackage;
import org.xtuml.bp.xtext.oal.xoal.block;
import org.xtuml.bp.xtext.oal.xoal.expr;
import org.xtuml.bp.xtext.oal.xoal.if_statement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>if statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl#getA1 <em>A1</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl#getA2 <em>A2</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl#getA3 <em>A3</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl#getA4 <em>A4</em>}</li>
 *   <li>{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl#getA5 <em>A5</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class if_statementImpl extends statementImpl implements if_statement
{
  /**
   * The cached value of the '{@link #getA1() <em>A1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA1()
   * @generated
   * @ordered
   */
  protected expr a1;

  /**
   * The cached value of the '{@link #getA2() <em>A2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA2()
   * @generated
   * @ordered
   */
  protected block a2;

  /**
   * The cached value of the '{@link #getA3() <em>A3</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA3()
   * @generated
   * @ordered
   */
  protected EList<expr> a3;

  /**
   * The cached value of the '{@link #getA4() <em>A4</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA4()
   * @generated
   * @ordered
   */
  protected EList<block> a4;

  /**
   * The cached value of the '{@link #getA5() <em>A5</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getA5()
   * @generated
   * @ordered
   */
  protected block a5;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected if_statementImpl()
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
    return XoalPackage.Literals.IF_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public expr getA1()
  {
    return a1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetA1(expr newA1, NotificationChain msgs)
  {
    expr oldA1 = a1;
    a1 = newA1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XoalPackage.IF_STATEMENT__A1, oldA1, newA1);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA1(expr newA1)
  {
    if (newA1 != a1)
    {
      NotificationChain msgs = null;
      if (a1 != null)
        msgs = ((InternalEObject)a1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XoalPackage.IF_STATEMENT__A1, null, msgs);
      if (newA1 != null)
        msgs = ((InternalEObject)newA1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XoalPackage.IF_STATEMENT__A1, null, msgs);
      msgs = basicSetA1(newA1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.IF_STATEMENT__A1, newA1, newA1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public block getA2()
  {
    return a2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetA2(block newA2, NotificationChain msgs)
  {
    block oldA2 = a2;
    a2 = newA2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XoalPackage.IF_STATEMENT__A2, oldA2, newA2);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA2(block newA2)
  {
    if (newA2 != a2)
    {
      NotificationChain msgs = null;
      if (a2 != null)
        msgs = ((InternalEObject)a2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XoalPackage.IF_STATEMENT__A2, null, msgs);
      if (newA2 != null)
        msgs = ((InternalEObject)newA2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XoalPackage.IF_STATEMENT__A2, null, msgs);
      msgs = basicSetA2(newA2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.IF_STATEMENT__A2, newA2, newA2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<expr> getA3()
  {
    if (a3 == null)
    {
      a3 = new EObjectContainmentEList<expr>(expr.class, this, XoalPackage.IF_STATEMENT__A3);
    }
    return a3;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<block> getA4()
  {
    if (a4 == null)
    {
      a4 = new EObjectContainmentEList<block>(block.class, this, XoalPackage.IF_STATEMENT__A4);
    }
    return a4;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public block getA5()
  {
    return a5;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetA5(block newA5, NotificationChain msgs)
  {
    block oldA5 = a5;
    a5 = newA5;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XoalPackage.IF_STATEMENT__A5, oldA5, newA5);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setA5(block newA5)
  {
    if (newA5 != a5)
    {
      NotificationChain msgs = null;
      if (a5 != null)
        msgs = ((InternalEObject)a5).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XoalPackage.IF_STATEMENT__A5, null, msgs);
      if (newA5 != null)
        msgs = ((InternalEObject)newA5).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XoalPackage.IF_STATEMENT__A5, null, msgs);
      msgs = basicSetA5(newA5, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XoalPackage.IF_STATEMENT__A5, newA5, newA5));
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
      case XoalPackage.IF_STATEMENT__A1:
        return basicSetA1(null, msgs);
      case XoalPackage.IF_STATEMENT__A2:
        return basicSetA2(null, msgs);
      case XoalPackage.IF_STATEMENT__A3:
        return ((InternalEList<?>)getA3()).basicRemove(otherEnd, msgs);
      case XoalPackage.IF_STATEMENT__A4:
        return ((InternalEList<?>)getA4()).basicRemove(otherEnd, msgs);
      case XoalPackage.IF_STATEMENT__A5:
        return basicSetA5(null, msgs);
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
      case XoalPackage.IF_STATEMENT__A1:
        return getA1();
      case XoalPackage.IF_STATEMENT__A2:
        return getA2();
      case XoalPackage.IF_STATEMENT__A3:
        return getA3();
      case XoalPackage.IF_STATEMENT__A4:
        return getA4();
      case XoalPackage.IF_STATEMENT__A5:
        return getA5();
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
      case XoalPackage.IF_STATEMENT__A1:
        setA1((expr)newValue);
        return;
      case XoalPackage.IF_STATEMENT__A2:
        setA2((block)newValue);
        return;
      case XoalPackage.IF_STATEMENT__A3:
        getA3().clear();
        getA3().addAll((Collection<? extends expr>)newValue);
        return;
      case XoalPackage.IF_STATEMENT__A4:
        getA4().clear();
        getA4().addAll((Collection<? extends block>)newValue);
        return;
      case XoalPackage.IF_STATEMENT__A5:
        setA5((block)newValue);
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
      case XoalPackage.IF_STATEMENT__A1:
        setA1((expr)null);
        return;
      case XoalPackage.IF_STATEMENT__A2:
        setA2((block)null);
        return;
      case XoalPackage.IF_STATEMENT__A3:
        getA3().clear();
        return;
      case XoalPackage.IF_STATEMENT__A4:
        getA4().clear();
        return;
      case XoalPackage.IF_STATEMENT__A5:
        setA5((block)null);
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
      case XoalPackage.IF_STATEMENT__A1:
        return a1 != null;
      case XoalPackage.IF_STATEMENT__A2:
        return a2 != null;
      case XoalPackage.IF_STATEMENT__A3:
        return a3 != null && !a3.isEmpty();
      case XoalPackage.IF_STATEMENT__A4:
        return a4 != null && !a4.isEmpty();
      case XoalPackage.IF_STATEMENT__A5:
        return a5 != null;
    }
    return super.eIsSet(featureID);
  }

} //if_statementImpl
