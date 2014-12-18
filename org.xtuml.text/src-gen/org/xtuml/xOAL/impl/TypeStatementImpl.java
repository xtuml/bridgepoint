/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.xOAL.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.xtuml.xOAL.TypeStatement;
import org.xtuml.xOAL.XOALPackage;
import org.xtuml.xOAL.statement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.xtuml.xOAL.impl.TypeStatementImpl#getSubstatements <em>Substatements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypeStatementImpl extends MinimalEObjectImpl.Container implements TypeStatement
{
  /**
   * The cached value of the '{@link #getSubstatements() <em>Substatements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubstatements()
   * @generated
   * @ordered
   */
  protected EList<statement> substatements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TypeStatementImpl()
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
    return XOALPackage.Literals.TYPE_STATEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<statement> getSubstatements()
  {
    if (substatements == null)
    {
      substatements = new EObjectContainmentEList<statement>(statement.class, this, XOALPackage.TYPE_STATEMENT__SUBSTATEMENTS);
    }
    return substatements;
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
      case XOALPackage.TYPE_STATEMENT__SUBSTATEMENTS:
        return ((InternalEList<?>)getSubstatements()).basicRemove(otherEnd, msgs);
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
      case XOALPackage.TYPE_STATEMENT__SUBSTATEMENTS:
        return getSubstatements();
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
      case XOALPackage.TYPE_STATEMENT__SUBSTATEMENTS:
        getSubstatements().clear();
        getSubstatements().addAll((Collection<? extends statement>)newValue);
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
      case XOALPackage.TYPE_STATEMENT__SUBSTATEMENTS:
        getSubstatements().clear();
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
      case XOALPackage.TYPE_STATEMENT__SUBSTATEMENTS:
        return substatements != null && !substatements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TypeStatementImpl
