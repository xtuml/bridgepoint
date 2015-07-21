/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.xtuml.bp.xtext.oal.xoal.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage
 * @generated
 */
public class XoalAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static XoalPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XoalAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = XoalPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected XoalSwitch<Adapter> modelSwitch =
    new XoalSwitch<Adapter>()
    {
      @Override
      public Adapter caseCode(Code object)
      {
        return createCodeAdapter();
      }
      @Override
      public Adapter caseblock(block object)
      {
        return createblockAdapter();
      }
      @Override
      public Adapter casestatement(statement object)
      {
        return createstatementAdapter();
      }
      @Override
      public Adapter caseassignment_statement(assignment_statement object)
      {
        return createassignment_statementAdapter();
      }
      @Override
      public Adapter casebreak_statement(break_statement object)
      {
        return createbreak_statementAdapter();
      }
      @Override
      public Adapter casebridge_statement(bridge_statement object)
      {
        return createbridge_statementAdapter();
      }
      @Override
      public Adapter casesend_statement(send_statement object)
      {
        return createsend_statementAdapter();
      }
      @Override
      public Adapter casecontrol_statement(control_statement object)
      {
        return createcontrol_statementAdapter();
      }
      @Override
      public Adapter casecontinue_statement(continue_statement object)
      {
        return createcontinue_statementAdapter();
      }
      @Override
      public Adapter casecreate_event_statement(create_event_statement object)
      {
        return createcreate_event_statementAdapter();
      }
      @Override
      public Adapter casecreate_object_statement(create_object_statement object)
      {
        return createcreate_object_statementAdapter();
      }
      @Override
      public Adapter casedelete_statement(delete_statement object)
      {
        return createdelete_statementAdapter();
      }
      @Override
      public Adapter casefor_statement(for_statement object)
      {
        return createfor_statementAdapter();
      }
      @Override
      public Adapter casegenerate_statement(generate_statement object)
      {
        return creategenerate_statementAdapter();
      }
      @Override
      public Adapter caseif_statement(if_statement object)
      {
        return createif_statementAdapter();
      }
      @Override
      public Adapter caserelate_statement(relate_statement object)
      {
        return createrelate_statementAdapter();
      }
      @Override
      public Adapter casereturn_statement(return_statement object)
      {
        return createreturn_statementAdapter();
      }
      @Override
      public Adapter caseselect_statement(select_statement object)
      {
        return createselect_statementAdapter();
      }
      @Override
      public Adapter caseunrelate_statement(unrelate_statement object)
      {
        return createunrelate_statementAdapter();
      }
      @Override
      public Adapter casewhile_statement(while_statement object)
      {
        return createwhile_statementAdapter();
      }
      @Override
      public Adapter caseassignment_expr(assignment_expr object)
      {
        return createassignment_exprAdapter();
      }
      @Override
      public Adapter casebridge_invocation(bridge_invocation object)
      {
        return createbridge_invocationAdapter();
      }
      @Override
      public Adapter casebridge_expr(bridge_expr object)
      {
        return createbridge_exprAdapter();
      }
      @Override
      public Adapter caseassoc_obj_inst_ref_var(assoc_obj_inst_ref_var object)
      {
        return createassoc_obj_inst_ref_varAdapter();
      }
      @Override
      public Adapter caseinst_ref_set_var(inst_ref_set_var object)
      {
        return createinst_ref_set_varAdapter();
      }
      @Override
      public Adapter caseinst_ref_var(inst_ref_var object)
      {
        return createinst_ref_varAdapter();
      }
      @Override
      public Adapter caseexpr(expr object)
      {
        return createexprAdapter();
      }
      @Override
      public Adapter casesub_expr(sub_expr object)
      {
        return createsub_exprAdapter();
      }
      @Override
      public Adapter caseconjunction(conjunction object)
      {
        return createconjunctionAdapter();
      }
      @Override
      public Adapter caserelational_expr(relational_expr object)
      {
        return createrelational_exprAdapter();
      }
      @Override
      public Adapter caseaddition(addition object)
      {
        return createadditionAdapter();
      }
      @Override
      public Adapter casemultiplication(multiplication object)
      {
        return createmultiplicationAdapter();
      }
      @Override
      public Adapter casesign_expr(sign_expr object)
      {
        return createsign_exprAdapter();
      }
      @Override
      public Adapter caseboolean_negation(boolean_negation object)
      {
        return createboolean_negationAdapter();
      }
      @Override
      public Adapter caseterm(term object)
      {
        return createtermAdapter();
      }
      @Override
      public Adapter caseinstance_start_segment(instance_start_segment object)
      {
        return createinstance_start_segmentAdapter();
      }
      @Override
      public Adapter casemember_access(member_access object)
      {
        return createmember_accessAdapter();
      }
      @Override
      public Adapter casearray_refs(array_refs object)
      {
        return createarray_refsAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.Code <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.Code
   * @generated
   */
  public Adapter createCodeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.block <em>block</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.block
   * @generated
   */
  public Adapter createblockAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.statement <em>statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.statement
   * @generated
   */
  public Adapter createstatementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.assignment_statement <em>assignment statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.assignment_statement
   * @generated
   */
  public Adapter createassignment_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.break_statement <em>break statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.break_statement
   * @generated
   */
  public Adapter createbreak_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.bridge_statement <em>bridge statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_statement
   * @generated
   */
  public Adapter createbridge_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.send_statement <em>send statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.send_statement
   * @generated
   */
  public Adapter createsend_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.control_statement <em>control statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.control_statement
   * @generated
   */
  public Adapter createcontrol_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.continue_statement <em>continue statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.continue_statement
   * @generated
   */
  public Adapter createcontinue_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.create_event_statement <em>create event statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.create_event_statement
   * @generated
   */
  public Adapter createcreate_event_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.create_object_statement <em>create object statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.create_object_statement
   * @generated
   */
  public Adapter createcreate_object_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.delete_statement <em>delete statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.delete_statement
   * @generated
   */
  public Adapter createdelete_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.for_statement <em>for statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.for_statement
   * @generated
   */
  public Adapter createfor_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.generate_statement <em>generate statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.generate_statement
   * @generated
   */
  public Adapter creategenerate_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.if_statement <em>if statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement
   * @generated
   */
  public Adapter createif_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.relate_statement <em>relate statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.relate_statement
   * @generated
   */
  public Adapter createrelate_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.return_statement <em>return statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.return_statement
   * @generated
   */
  public Adapter createreturn_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.select_statement <em>select statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.select_statement
   * @generated
   */
  public Adapter createselect_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.unrelate_statement <em>unrelate statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.unrelate_statement
   * @generated
   */
  public Adapter createunrelate_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.while_statement <em>while statement</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.while_statement
   * @generated
   */
  public Adapter createwhile_statementAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.assignment_expr <em>assignment expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.assignment_expr
   * @generated
   */
  public Adapter createassignment_exprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.bridge_invocation <em>bridge invocation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_invocation
   * @generated
   */
  public Adapter createbridge_invocationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.bridge_expr <em>bridge expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_expr
   * @generated
   */
  public Adapter createbridge_exprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.assoc_obj_inst_ref_var <em>assoc obj inst ref var</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.assoc_obj_inst_ref_var
   * @generated
   */
  public Adapter createassoc_obj_inst_ref_varAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var <em>inst ref set var</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var
   * @generated
   */
  public Adapter createinst_ref_set_varAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.inst_ref_var <em>inst ref var</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.inst_ref_var
   * @generated
   */
  public Adapter createinst_ref_varAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.expr <em>expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.expr
   * @generated
   */
  public Adapter createexprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.sub_expr <em>sub expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.sub_expr
   * @generated
   */
  public Adapter createsub_exprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.conjunction <em>conjunction</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.conjunction
   * @generated
   */
  public Adapter createconjunctionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.relational_expr <em>relational expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.relational_expr
   * @generated
   */
  public Adapter createrelational_exprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.addition <em>addition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.addition
   * @generated
   */
  public Adapter createadditionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.multiplication <em>multiplication</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.multiplication
   * @generated
   */
  public Adapter createmultiplicationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.sign_expr <em>sign expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.sign_expr
   * @generated
   */
  public Adapter createsign_exprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.boolean_negation <em>boolean negation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.boolean_negation
   * @generated
   */
  public Adapter createboolean_negationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.term <em>term</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.term
   * @generated
   */
  public Adapter createtermAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.instance_start_segment <em>instance start segment</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.instance_start_segment
   * @generated
   */
  public Adapter createinstance_start_segmentAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.member_access <em>member access</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.member_access
   * @generated
   */
  public Adapter createmember_accessAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.xtuml.bp.xtext.oal.xoal.array_refs <em>array refs</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.xtuml.bp.xtext.oal.xoal.array_refs
   * @generated
   */
  public Adapter createarray_refsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //XoalAdapterFactory
