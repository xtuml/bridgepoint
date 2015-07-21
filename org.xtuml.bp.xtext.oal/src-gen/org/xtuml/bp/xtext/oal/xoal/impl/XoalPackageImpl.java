/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.xtuml.bp.xtext.oal.xoal.Code;
import org.xtuml.bp.xtext.oal.xoal.XoalFactory;
import org.xtuml.bp.xtext.oal.xoal.XoalPackage;
import org.xtuml.bp.xtext.oal.xoal.addition;
import org.xtuml.bp.xtext.oal.xoal.array_refs;
import org.xtuml.bp.xtext.oal.xoal.assignment_expr;
import org.xtuml.bp.xtext.oal.xoal.assignment_statement;
import org.xtuml.bp.xtext.oal.xoal.assoc_obj_inst_ref_var;
import org.xtuml.bp.xtext.oal.xoal.block;
import org.xtuml.bp.xtext.oal.xoal.boolean_negation;
import org.xtuml.bp.xtext.oal.xoal.break_statement;
import org.xtuml.bp.xtext.oal.xoal.bridge_expr;
import org.xtuml.bp.xtext.oal.xoal.bridge_invocation;
import org.xtuml.bp.xtext.oal.xoal.bridge_statement;
import org.xtuml.bp.xtext.oal.xoal.conjunction;
import org.xtuml.bp.xtext.oal.xoal.continue_statement;
import org.xtuml.bp.xtext.oal.xoal.control_statement;
import org.xtuml.bp.xtext.oal.xoal.create_event_statement;
import org.xtuml.bp.xtext.oal.xoal.create_object_statement;
import org.xtuml.bp.xtext.oal.xoal.delete_statement;
import org.xtuml.bp.xtext.oal.xoal.expr;
import org.xtuml.bp.xtext.oal.xoal.for_statement;
import org.xtuml.bp.xtext.oal.xoal.generate_statement;
import org.xtuml.bp.xtext.oal.xoal.if_statement;
import org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var;
import org.xtuml.bp.xtext.oal.xoal.inst_ref_var;
import org.xtuml.bp.xtext.oal.xoal.instance_start_segment;
import org.xtuml.bp.xtext.oal.xoal.member_access;
import org.xtuml.bp.xtext.oal.xoal.multiplication;
import org.xtuml.bp.xtext.oal.xoal.relate_statement;
import org.xtuml.bp.xtext.oal.xoal.relational_expr;
import org.xtuml.bp.xtext.oal.xoal.return_statement;
import org.xtuml.bp.xtext.oal.xoal.select_statement;
import org.xtuml.bp.xtext.oal.xoal.send_statement;
import org.xtuml.bp.xtext.oal.xoal.sign_expr;
import org.xtuml.bp.xtext.oal.xoal.statement;
import org.xtuml.bp.xtext.oal.xoal.sub_expr;
import org.xtuml.bp.xtext.oal.xoal.term;
import org.xtuml.bp.xtext.oal.xoal.unrelate_statement;
import org.xtuml.bp.xtext.oal.xoal.while_statement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XoalPackageImpl extends EPackageImpl implements XoalPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass codeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass blockEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assignment_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass break_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bridge_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass send_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass control_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass continue_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass create_event_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass create_object_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass delete_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass for_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass generate_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass if_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass relate_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass return_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass select_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass unrelate_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass while_statementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assignment_exprEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bridge_invocationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bridge_exprEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass assoc_obj_inst_ref_varEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass inst_ref_set_varEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass inst_ref_varEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass exprEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass sub_exprEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass conjunctionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass relational_exprEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass additionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass multiplicationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass sign_exprEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass boolean_negationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass termEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass instance_start_segmentEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass member_accessEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass array_refsEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.xtuml.bp.xtext.oal.xoal.XoalPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private XoalPackageImpl()
  {
    super(eNS_URI, XoalFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link XoalPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static XoalPackage init()
  {
    if (isInited) return (XoalPackage)EPackage.Registry.INSTANCE.getEPackage(XoalPackage.eNS_URI);

    // Obtain or create and register package
    XoalPackageImpl theXoalPackage = (XoalPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof XoalPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new XoalPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theXoalPackage.createPackageContents();

    // Initialize created meta-data
    theXoalPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theXoalPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(XoalPackage.eNS_URI, theXoalPackage);
    return theXoalPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCode()
  {
    return codeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getblock()
  {
    return blockEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getblock_Statements()
  {
    return (EReference)blockEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getstatement()
  {
    return statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getassignment_statement()
  {
    return assignment_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getassignment_statement_A1()
  {
    return (EReference)assignment_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getbreak_statement()
  {
    return break_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getbreak_statement_A1()
  {
    return (EAttribute)break_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getbridge_statement()
  {
    return bridge_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getbridge_statement_A1()
  {
    return (EAttribute)bridge_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getsend_statement()
  {
    return send_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getsend_statement_A1()
  {
    return (EAttribute)send_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getcontrol_statement()
  {
    return control_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getcontrol_statement_A1()
  {
    return (EAttribute)control_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getcontinue_statement()
  {
    return continue_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getcontinue_statement_A1()
  {
    return (EAttribute)continue_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getcreate_event_statement()
  {
    return create_event_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getcreate_event_statement_A1()
  {
    return (EAttribute)create_event_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getcreate_event_statement_A2()
  {
    return (EAttribute)create_event_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getcreate_object_statement()
  {
    return create_object_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getcreate_object_statement_A1()
  {
    return (EAttribute)create_object_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getcreate_object_statement_A2()
  {
    return (EAttribute)create_object_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getdelete_statement()
  {
    return delete_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getdelete_statement_A1()
  {
    return (EReference)delete_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getfor_statement()
  {
    return for_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getfor_statement_A1()
  {
    return (EAttribute)for_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getfor_statement_A2()
  {
    return (EReference)for_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getfor_statement_A3()
  {
    return (EReference)for_statementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getgenerate_statement()
  {
    return generate_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getgenerate_statement_A1()
  {
    return (EAttribute)generate_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getif_statement()
  {
    return if_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getif_statement_A1()
  {
    return (EReference)if_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getif_statement_A2()
  {
    return (EReference)if_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getif_statement_A3()
  {
    return (EReference)if_statementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getif_statement_A4()
  {
    return (EReference)if_statementEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getif_statement_A5()
  {
    return (EReference)if_statementEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getrelate_statement()
  {
    return relate_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getrelate_statement_A1()
  {
    return (EReference)relate_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getrelate_statement_A2()
  {
    return (EReference)relate_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getrelate_statement_A3()
  {
    return (EAttribute)relate_statementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getreturn_statement()
  {
    return return_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getreturn_statement_A1()
  {
    return (EAttribute)return_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getreturn_statement_A2()
  {
    return (EReference)return_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getselect_statement()
  {
    return select_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getselect_statement_A1()
  {
    return (EAttribute)select_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getselect_statement_A2()
  {
    return (EAttribute)select_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getselect_statement_A3()
  {
    return (EAttribute)select_statementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getunrelate_statement()
  {
    return unrelate_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getunrelate_statement_A1()
  {
    return (EReference)unrelate_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getunrelate_statement_A2()
  {
    return (EReference)unrelate_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getunrelate_statement_A3()
  {
    return (EAttribute)unrelate_statementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getwhile_statement()
  {
    return while_statementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getwhile_statement_A1()
  {
    return (EReference)while_statementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getwhile_statement_A2()
  {
    return (EReference)while_statementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getassignment_expr()
  {
    return assignment_exprEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getassignment_expr_A1()
  {
    return (EReference)assignment_exprEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getbridge_invocation()
  {
    return bridge_invocationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getbridge_invocation_A1()
  {
    return (EAttribute)bridge_invocationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getbridge_expr()
  {
    return bridge_exprEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getbridge_expr_A1()
  {
    return (EReference)bridge_exprEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getassoc_obj_inst_ref_var()
  {
    return assoc_obj_inst_ref_varEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getinst_ref_set_var()
  {
    return inst_ref_set_varEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getinst_ref_set_var_A1()
  {
    return (EAttribute)inst_ref_set_varEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getinst_ref_var()
  {
    return inst_ref_varEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getinst_ref_var_A1()
  {
    return (EAttribute)inst_ref_varEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getexpr()
  {
    return exprEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getexpr_A1()
  {
    return (EReference)exprEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getsub_expr()
  {
    return sub_exprEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getsub_expr_A1()
  {
    return (EReference)sub_exprEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getsub_expr_A2()
  {
    return (EReference)sub_exprEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getconjunction()
  {
    return conjunctionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getconjunction_A1()
  {
    return (EReference)conjunctionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getconjunction_A2()
  {
    return (EReference)conjunctionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getrelational_expr()
  {
    return relational_exprEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getrelational_expr_A1()
  {
    return (EReference)relational_exprEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getrelational_expr_A2()
  {
    return (EReference)relational_exprEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getaddition()
  {
    return additionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getaddition_A1()
  {
    return (EReference)additionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getaddition_A2()
  {
    return (EReference)additionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getmultiplication()
  {
    return multiplicationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getmultiplication_A1()
  {
    return (EReference)multiplicationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getmultiplication_A2()
  {
    return (EReference)multiplicationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getsign_expr()
  {
    return sign_exprEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getboolean_negation()
  {
    return boolean_negationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getterm()
  {
    return termEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getterm_A1()
  {
    return (EAttribute)termEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getterm_A2()
  {
    return (EReference)termEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getinstance_start_segment()
  {
    return instance_start_segmentEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getmember_access()
  {
    return member_accessEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getarray_refs()
  {
    return array_refsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getarray_refs_A1()
  {
    return (EReference)array_refsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public XoalFactory getXoalFactory()
  {
    return (XoalFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    codeEClass = createEClass(CODE);

    blockEClass = createEClass(BLOCK);
    createEReference(blockEClass, BLOCK__STATEMENTS);

    statementEClass = createEClass(STATEMENT);

    assignment_statementEClass = createEClass(ASSIGNMENT_STATEMENT);
    createEReference(assignment_statementEClass, ASSIGNMENT_STATEMENT__A1);

    break_statementEClass = createEClass(BREAK_STATEMENT);
    createEAttribute(break_statementEClass, BREAK_STATEMENT__A1);

    bridge_statementEClass = createEClass(BRIDGE_STATEMENT);
    createEAttribute(bridge_statementEClass, BRIDGE_STATEMENT__A1);

    send_statementEClass = createEClass(SEND_STATEMENT);
    createEAttribute(send_statementEClass, SEND_STATEMENT__A1);

    control_statementEClass = createEClass(CONTROL_STATEMENT);
    createEAttribute(control_statementEClass, CONTROL_STATEMENT__A1);

    continue_statementEClass = createEClass(CONTINUE_STATEMENT);
    createEAttribute(continue_statementEClass, CONTINUE_STATEMENT__A1);

    create_event_statementEClass = createEClass(CREATE_EVENT_STATEMENT);
    createEAttribute(create_event_statementEClass, CREATE_EVENT_STATEMENT__A1);
    createEAttribute(create_event_statementEClass, CREATE_EVENT_STATEMENT__A2);

    create_object_statementEClass = createEClass(CREATE_OBJECT_STATEMENT);
    createEAttribute(create_object_statementEClass, CREATE_OBJECT_STATEMENT__A1);
    createEAttribute(create_object_statementEClass, CREATE_OBJECT_STATEMENT__A2);

    delete_statementEClass = createEClass(DELETE_STATEMENT);
    createEReference(delete_statementEClass, DELETE_STATEMENT__A1);

    for_statementEClass = createEClass(FOR_STATEMENT);
    createEAttribute(for_statementEClass, FOR_STATEMENT__A1);
    createEReference(for_statementEClass, FOR_STATEMENT__A2);
    createEReference(for_statementEClass, FOR_STATEMENT__A3);

    generate_statementEClass = createEClass(GENERATE_STATEMENT);
    createEAttribute(generate_statementEClass, GENERATE_STATEMENT__A1);

    if_statementEClass = createEClass(IF_STATEMENT);
    createEReference(if_statementEClass, IF_STATEMENT__A1);
    createEReference(if_statementEClass, IF_STATEMENT__A2);
    createEReference(if_statementEClass, IF_STATEMENT__A3);
    createEReference(if_statementEClass, IF_STATEMENT__A4);
    createEReference(if_statementEClass, IF_STATEMENT__A5);

    relate_statementEClass = createEClass(RELATE_STATEMENT);
    createEReference(relate_statementEClass, RELATE_STATEMENT__A1);
    createEReference(relate_statementEClass, RELATE_STATEMENT__A2);
    createEAttribute(relate_statementEClass, RELATE_STATEMENT__A3);

    return_statementEClass = createEClass(RETURN_STATEMENT);
    createEAttribute(return_statementEClass, RETURN_STATEMENT__A1);
    createEReference(return_statementEClass, RETURN_STATEMENT__A2);

    select_statementEClass = createEClass(SELECT_STATEMENT);
    createEAttribute(select_statementEClass, SELECT_STATEMENT__A1);
    createEAttribute(select_statementEClass, SELECT_STATEMENT__A2);
    createEAttribute(select_statementEClass, SELECT_STATEMENT__A3);

    unrelate_statementEClass = createEClass(UNRELATE_STATEMENT);
    createEReference(unrelate_statementEClass, UNRELATE_STATEMENT__A1);
    createEReference(unrelate_statementEClass, UNRELATE_STATEMENT__A2);
    createEAttribute(unrelate_statementEClass, UNRELATE_STATEMENT__A3);

    while_statementEClass = createEClass(WHILE_STATEMENT);
    createEReference(while_statementEClass, WHILE_STATEMENT__A1);
    createEReference(while_statementEClass, WHILE_STATEMENT__A2);

    assignment_exprEClass = createEClass(ASSIGNMENT_EXPR);
    createEReference(assignment_exprEClass, ASSIGNMENT_EXPR__A1);

    bridge_invocationEClass = createEClass(BRIDGE_INVOCATION);
    createEAttribute(bridge_invocationEClass, BRIDGE_INVOCATION__A1);

    bridge_exprEClass = createEClass(BRIDGE_EXPR);
    createEReference(bridge_exprEClass, BRIDGE_EXPR__A1);

    assoc_obj_inst_ref_varEClass = createEClass(ASSOC_OBJ_INST_REF_VAR);

    inst_ref_set_varEClass = createEClass(INST_REF_SET_VAR);
    createEAttribute(inst_ref_set_varEClass, INST_REF_SET_VAR__A1);

    inst_ref_varEClass = createEClass(INST_REF_VAR);
    createEAttribute(inst_ref_varEClass, INST_REF_VAR__A1);

    exprEClass = createEClass(EXPR);
    createEReference(exprEClass, EXPR__A1);

    sub_exprEClass = createEClass(SUB_EXPR);
    createEReference(sub_exprEClass, SUB_EXPR__A1);
    createEReference(sub_exprEClass, SUB_EXPR__A2);

    conjunctionEClass = createEClass(CONJUNCTION);
    createEReference(conjunctionEClass, CONJUNCTION__A1);
    createEReference(conjunctionEClass, CONJUNCTION__A2);

    relational_exprEClass = createEClass(RELATIONAL_EXPR);
    createEReference(relational_exprEClass, RELATIONAL_EXPR__A1);
    createEReference(relational_exprEClass, RELATIONAL_EXPR__A2);

    additionEClass = createEClass(ADDITION);
    createEReference(additionEClass, ADDITION__A1);
    createEReference(additionEClass, ADDITION__A2);

    multiplicationEClass = createEClass(MULTIPLICATION);
    createEReference(multiplicationEClass, MULTIPLICATION__A1);
    createEReference(multiplicationEClass, MULTIPLICATION__A2);

    sign_exprEClass = createEClass(SIGN_EXPR);

    boolean_negationEClass = createEClass(BOOLEAN_NEGATION);

    termEClass = createEClass(TERM);
    createEAttribute(termEClass, TERM__A1);
    createEReference(termEClass, TERM__A2);

    instance_start_segmentEClass = createEClass(INSTANCE_START_SEGMENT);

    member_accessEClass = createEClass(MEMBER_ACCESS);

    array_refsEClass = createEClass(ARRAY_REFS);
    createEReference(array_refsEClass, ARRAY_REFS__A1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    blockEClass.getESuperTypes().add(this.getCode());
    assignment_statementEClass.getESuperTypes().add(this.getstatement());
    break_statementEClass.getESuperTypes().add(this.getstatement());
    bridge_statementEClass.getESuperTypes().add(this.getstatement());
    send_statementEClass.getESuperTypes().add(this.getstatement());
    control_statementEClass.getESuperTypes().add(this.getstatement());
    continue_statementEClass.getESuperTypes().add(this.getstatement());
    create_event_statementEClass.getESuperTypes().add(this.getstatement());
    create_object_statementEClass.getESuperTypes().add(this.getstatement());
    delete_statementEClass.getESuperTypes().add(this.getstatement());
    for_statementEClass.getESuperTypes().add(this.getstatement());
    generate_statementEClass.getESuperTypes().add(this.getstatement());
    if_statementEClass.getESuperTypes().add(this.getstatement());
    relate_statementEClass.getESuperTypes().add(this.getstatement());
    return_statementEClass.getESuperTypes().add(this.getstatement());
    select_statementEClass.getESuperTypes().add(this.getstatement());
    unrelate_statementEClass.getESuperTypes().add(this.getstatement());
    while_statementEClass.getESuperTypes().add(this.getstatement());
    inst_ref_varEClass.getESuperTypes().add(this.getassoc_obj_inst_ref_var());
    termEClass.getESuperTypes().add(this.getsign_expr());
    termEClass.getESuperTypes().add(this.getboolean_negation());
    instance_start_segmentEClass.getESuperTypes().add(this.getmember_access());
    array_refsEClass.getESuperTypes().add(this.getinstance_start_segment());

    // Initialize classes and features; add operations and parameters
    initEClass(codeEClass, Code.class, "Code", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(blockEClass, block.class, "block", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getblock_Statements(), this.getstatement(), null, "statements", null, 0, -1, block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(statementEClass, statement.class, "statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(assignment_statementEClass, assignment_statement.class, "assignment_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getassignment_statement_A1(), this.getassignment_expr(), null, "a1", null, 0, 1, assignment_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(break_statementEClass, break_statement.class, "break_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getbreak_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, break_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(bridge_statementEClass, bridge_statement.class, "bridge_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getbridge_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, bridge_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(send_statementEClass, send_statement.class, "send_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getsend_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, send_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(control_statementEClass, control_statement.class, "control_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getcontrol_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, control_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(continue_statementEClass, continue_statement.class, "continue_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getcontinue_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, continue_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(create_event_statementEClass, create_event_statement.class, "create_event_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getcreate_event_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, create_event_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getcreate_event_statement_A2(), ecorePackage.getEString(), "a2", null, 0, 1, create_event_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(create_object_statementEClass, create_object_statement.class, "create_object_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getcreate_object_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, create_object_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getcreate_object_statement_A2(), ecorePackage.getEString(), "a2", null, 0, 1, create_object_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(delete_statementEClass, delete_statement.class, "delete_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getdelete_statement_A1(), this.getinst_ref_var(), null, "a1", null, 0, 1, delete_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(for_statementEClass, for_statement.class, "for_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getfor_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, for_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getfor_statement_A2(), this.getinst_ref_set_var(), null, "a2", null, 0, 1, for_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getfor_statement_A3(), this.getblock(), null, "a3", null, 0, 1, for_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(generate_statementEClass, generate_statement.class, "generate_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getgenerate_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, generate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(if_statementEClass, if_statement.class, "if_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getif_statement_A1(), this.getexpr(), null, "a1", null, 0, 1, if_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getif_statement_A2(), this.getblock(), null, "a2", null, 0, 1, if_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getif_statement_A3(), this.getexpr(), null, "a3", null, 0, -1, if_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getif_statement_A4(), this.getblock(), null, "a4", null, 0, -1, if_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getif_statement_A5(), this.getblock(), null, "a5", null, 0, 1, if_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(relate_statementEClass, relate_statement.class, "relate_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getrelate_statement_A1(), this.getinst_ref_var(), null, "a1", null, 0, 1, relate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getrelate_statement_A2(), this.getinst_ref_var(), null, "a2", null, 0, 1, relate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getrelate_statement_A3(), ecorePackage.getEString(), "a3", null, 0, 1, relate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(return_statementEClass, return_statement.class, "return_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getreturn_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, return_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getreturn_statement_A2(), this.getexpr(), null, "a2", null, 0, 1, return_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(select_statementEClass, select_statement.class, "select_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getselect_statement_A1(), ecorePackage.getEString(), "a1", null, 0, 1, select_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getselect_statement_A2(), ecorePackage.getEString(), "a2", null, 0, 1, select_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getselect_statement_A3(), ecorePackage.getEString(), "a3", null, 0, 1, select_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(unrelate_statementEClass, unrelate_statement.class, "unrelate_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getunrelate_statement_A1(), this.getinst_ref_var(), null, "a1", null, 0, 1, unrelate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getunrelate_statement_A2(), this.getinst_ref_var(), null, "a2", null, 0, 1, unrelate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getunrelate_statement_A3(), ecorePackage.getEString(), "a3", null, 0, 1, unrelate_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(while_statementEClass, while_statement.class, "while_statement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getwhile_statement_A1(), this.getexpr(), null, "a1", null, 0, 1, while_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getwhile_statement_A2(), this.getblock(), null, "a2", null, 0, 1, while_statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(assignment_exprEClass, assignment_expr.class, "assignment_expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getassignment_expr_A1(), this.getexpr(), null, "a1", null, 0, 1, assignment_expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(bridge_invocationEClass, bridge_invocation.class, "bridge_invocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getbridge_invocation_A1(), ecorePackage.getEString(), "a1", null, 0, 1, bridge_invocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(bridge_exprEClass, bridge_expr.class, "bridge_expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getbridge_expr_A1(), this.getbridge_invocation(), null, "a1", null, 0, 1, bridge_expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(assoc_obj_inst_ref_varEClass, assoc_obj_inst_ref_var.class, "assoc_obj_inst_ref_var", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(inst_ref_set_varEClass, inst_ref_set_var.class, "inst_ref_set_var", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getinst_ref_set_var_A1(), ecorePackage.getEString(), "a1", null, 0, 1, inst_ref_set_var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(inst_ref_varEClass, inst_ref_var.class, "inst_ref_var", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getinst_ref_var_A1(), ecorePackage.getEString(), "a1", null, 0, 1, inst_ref_var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(exprEClass, expr.class, "expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getexpr_A1(), this.getsub_expr(), null, "a1", null, 0, 1, expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(sub_exprEClass, sub_expr.class, "sub_expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getsub_expr_A1(), this.getconjunction(), null, "a1", null, 0, 1, sub_expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getsub_expr_A2(), this.getconjunction(), null, "a2", null, 0, -1, sub_expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(conjunctionEClass, conjunction.class, "conjunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getconjunction_A1(), this.getrelational_expr(), null, "a1", null, 0, 1, conjunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getconjunction_A2(), this.getrelational_expr(), null, "a2", null, 0, -1, conjunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(relational_exprEClass, relational_expr.class, "relational_expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getrelational_expr_A1(), this.getaddition(), null, "a1", null, 0, 1, relational_expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getrelational_expr_A2(), this.getaddition(), null, "a2", null, 0, 1, relational_expr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(additionEClass, addition.class, "addition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getaddition_A1(), this.getmultiplication(), null, "a1", null, 0, 1, addition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getaddition_A2(), this.getmultiplication(), null, "a2", null, 0, -1, addition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(multiplicationEClass, multiplication.class, "multiplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getmultiplication_A1(), this.getsign_expr(), null, "a1", null, 0, 1, multiplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getmultiplication_A2(), this.getsign_expr(), null, "a2", null, 0, -1, multiplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(sign_exprEClass, sign_expr.class, "sign_expr", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(boolean_negationEClass, boolean_negation.class, "boolean_negation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(termEClass, term.class, "term", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getterm_A1(), ecorePackage.getEString(), "a1", null, 0, 1, term.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getterm_A2(), this.getexpr(), null, "a2", null, 0, 1, term.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(instance_start_segmentEClass, instance_start_segment.class, "instance_start_segment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(member_accessEClass, member_access.class, "member_access", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(array_refsEClass, array_refs.class, "array_refs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getarray_refs_A1(), this.getexpr(), null, "a1", null, 0, -1, array_refs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //XoalPackageImpl
