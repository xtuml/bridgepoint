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

import org.xtuml.xOAL.TypeStatementIf;
import org.xtuml.xOAL.XOALPackage;
import org.xtuml.xOAL.expression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Statement If</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.impl.TypeStatementIfImpl#getExpr <em>Expr</em>}</li>
 *   <li>{@link org.xtuml.xOAL.impl.TypeStatementIfImpl#getElifexpr <em>Elifexpr</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypeStatementIfImpl extends TypeStatementImpl implements TypeStatementIf
{
  /**
   * The cached value of the '{@link #getExpr() <em>Expr</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpr()
   * @generated
   * @ordered
   */
  protected expression expr;

  /**
   * The cached value of the '{@link #getElifexpr() <em>Elifexpr</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElifexpr()
   * @generated
   * @ordered
   */
  protected EList<expression> elifexpr;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TypeStatementIfImpl()
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
    return XOALPackage.Literals.TYPE_STATEMENT_IF;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public expression getExpr()
  {
    return expr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpr(expression newExpr, NotificationChain msgs)
  {
    expression oldExpr = expr;
    expr = newExpr;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, XOALPackage.TYPE_STATEMENT_IF__EXPR, oldExpr, newExpr);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpr(expression newExpr)
  {
    if (newExpr != expr)
    {
      NotificationChain msgs = null;
      if (expr != null)
        msgs = ((InternalEObject)expr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - XOALPackage.TYPE_STATEMENT_IF__EXPR, null, msgs);
      if (newExpr != null)
        msgs = ((InternalEObject)newExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - XOALPackage.TYPE_STATEMENT_IF__EXPR, null, msgs);
      msgs = basicSetExpr(newExpr, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, XOALPackage.TYPE_STATEMENT_IF__EXPR, newExpr, newExpr));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<expression> getElifexpr()
  {
    if (elifexpr == null)
    {
      elifexpr = new EObjectContainmentEList<expression>(expression.class, this, XOALPackage.TYPE_STATEMENT_IF__ELIFEXPR);
    }
    return elifexpr;
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
      case XOALPackage.TYPE_STATEMENT_IF__EXPR:
        return basicSetExpr(null, msgs);
      case XOALPackage.TYPE_STATEMENT_IF__ELIFEXPR:
        return ((InternalEList<?>)getElifexpr()).basicRemove(otherEnd, msgs);
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
      case XOALPackage.TYPE_STATEMENT_IF__EXPR:
        return getExpr();
      case XOALPackage.TYPE_STATEMENT_IF__ELIFEXPR:
        return getElifexpr();
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
      case XOALPackage.TYPE_STATEMENT_IF__EXPR:
        setExpr((expression)newValue);
        return;
      case XOALPackage.TYPE_STATEMENT_IF__ELIFEXPR:
        getElifexpr().clear();
        getElifexpr().addAll((Collection<? extends expression>)newValue);
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
      case XOALPackage.TYPE_STATEMENT_IF__EXPR:
        setExpr((expression)null);
        return;
      case XOALPackage.TYPE_STATEMENT_IF__ELIFEXPR:
        getElifexpr().clear();
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
      case XOALPackage.TYPE_STATEMENT_IF__EXPR:
        return expr != null;
      case XOALPackage.TYPE_STATEMENT_IF__ELIFEXPR:
        return elifexpr != null && !elifexpr.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TypeStatementIfImpl
