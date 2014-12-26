/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.bp.xtext.oal.xoal;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.oal.xoal.XoalFactory
 * @model kind="package"
 * @generated
 */
public interface XoalPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "xoal";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.xtuml.org/bp/xtext/oal/Xoal";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "xoal";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  XoalPackage eINSTANCE = org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl.init();

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.CodeImpl <em>Code</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.CodeImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getCode()
   * @generated
   */
  int CODE = 0;

  /**
   * The number of structural features of the '<em>Code</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CODE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.blockImpl <em>block</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.blockImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getblock()
   * @generated
   */
  int BLOCK = 1;

  /**
   * The feature id for the '<em><b>Statements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK__STATEMENTS = CODE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>block</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BLOCK_FEATURE_COUNT = CODE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.statementImpl <em>statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getstatement()
   * @generated
   */
  int STATEMENT = 2;

  /**
   * The number of structural features of the '<em>statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.assignment_statementImpl <em>assignment statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.assignment_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getassignment_statement()
   * @generated
   */
  int ASSIGNMENT_STATEMENT = 3;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>assignment statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.break_statementImpl <em>break statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.break_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbreak_statement()
   * @generated
   */
  int BREAK_STATEMENT = 4;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>break statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BREAK_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.bridge_statementImpl <em>bridge statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.bridge_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbridge_statement()
   * @generated
   */
  int BRIDGE_STATEMENT = 5;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRIDGE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>bridge statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRIDGE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.send_statementImpl <em>send statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.send_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getsend_statement()
   * @generated
   */
  int SEND_STATEMENT = 6;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEND_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>send statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SEND_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.control_statementImpl <em>control statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.control_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcontrol_statement()
   * @generated
   */
  int CONTROL_STATEMENT = 7;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROL_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>control statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTROL_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.continue_statementImpl <em>continue statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.continue_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcontinue_statement()
   * @generated
   */
  int CONTINUE_STATEMENT = 8;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTINUE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>continue statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTINUE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.create_event_statementImpl <em>create event statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.create_event_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcreate_event_statement()
   * @generated
   */
  int CREATE_EVENT_STATEMENT = 9;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_EVENT_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_EVENT_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>create event statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_EVENT_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.create_object_statementImpl <em>create object statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.create_object_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcreate_object_statement()
   * @generated
   */
  int CREATE_OBJECT_STATEMENT = 10;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_OBJECT_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_OBJECT_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>create object statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CREATE_OBJECT_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.delete_statementImpl <em>delete statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.delete_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getdelete_statement()
   * @generated
   */
  int DELETE_STATEMENT = 11;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DELETE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>delete statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DELETE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.for_statementImpl <em>for statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.for_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getfor_statement()
   * @generated
   */
  int FOR_STATEMENT = 12;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>A3</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_STATEMENT__A3 = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>for statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOR_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.generate_statementImpl <em>generate statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.generate_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getgenerate_statement()
   * @generated
   */
  int GENERATE_STATEMENT = 13;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERATE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>generate statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERATE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl <em>if statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getif_statement()
   * @generated
   */
  int IF_STATEMENT = 14;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>A3</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_STATEMENT__A3 = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>A4</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_STATEMENT__A4 = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>A5</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_STATEMENT__A5 = STATEMENT_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>if statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IF_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.relate_statementImpl <em>relate statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.relate_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getrelate_statement()
   * @generated
   */
  int RELATE_STATEMENT = 15;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATE_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>A3</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATE_STATEMENT__A3 = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>relate statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.return_statementImpl <em>return statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.return_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getreturn_statement()
   * @generated
   */
  int RETURN_STATEMENT = 16;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>return statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RETURN_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl <em>select statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getselect_statement()
   * @generated
   */
  int SELECT_STATEMENT = 17;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECT_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECT_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>A3</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECT_STATEMENT__A3 = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>select statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECT_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl <em>unrelate statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getunrelate_statement()
   * @generated
   */
  int UNRELATE_STATEMENT = 18;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRELATE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRELATE_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>A3</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRELATE_STATEMENT__A3 = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>unrelate statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int UNRELATE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.while_statementImpl <em>while statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.while_statementImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getwhile_statement()
   * @generated
   */
  int WHILE_STATEMENT = 19;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_STATEMENT__A1 = STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_STATEMENT__A2 = STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>while statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int WHILE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.assignment_exprImpl <em>assignment expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.assignment_exprImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getassignment_expr()
   * @generated
   */
  int ASSIGNMENT_EXPR = 20;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_EXPR__A1 = 0;

  /**
   * The number of structural features of the '<em>assignment expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_EXPR_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.bridge_invocationImpl <em>bridge invocation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.bridge_invocationImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbridge_invocation()
   * @generated
   */
  int BRIDGE_INVOCATION = 21;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRIDGE_INVOCATION__A1 = 0;

  /**
   * The number of structural features of the '<em>bridge invocation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRIDGE_INVOCATION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.bridge_exprImpl <em>bridge expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.bridge_exprImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbridge_expr()
   * @generated
   */
  int BRIDGE_EXPR = 22;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRIDGE_EXPR__A1 = 0;

  /**
   * The number of structural features of the '<em>bridge expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BRIDGE_EXPR_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.assoc_obj_inst_ref_varImpl <em>assoc obj inst ref var</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.assoc_obj_inst_ref_varImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getassoc_obj_inst_ref_var()
   * @generated
   */
  int ASSOC_OBJ_INST_REF_VAR = 23;

  /**
   * The number of structural features of the '<em>assoc obj inst ref var</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSOC_OBJ_INST_REF_VAR_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_set_varImpl <em>inst ref set var</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_set_varImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getinst_ref_set_var()
   * @generated
   */
  int INST_REF_SET_VAR = 24;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INST_REF_SET_VAR__A1 = 0;

  /**
   * The number of structural features of the '<em>inst ref set var</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INST_REF_SET_VAR_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_varImpl <em>inst ref var</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_varImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getinst_ref_var()
   * @generated
   */
  int INST_REF_VAR = 25;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INST_REF_VAR__A1 = ASSOC_OBJ_INST_REF_VAR_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>inst ref var</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INST_REF_VAR_FEATURE_COUNT = ASSOC_OBJ_INST_REF_VAR_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.exprImpl <em>expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.exprImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getexpr()
   * @generated
   */
  int EXPR = 26;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPR__A1 = 0;

  /**
   * The number of structural features of the '<em>expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPR_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.sub_exprImpl <em>sub expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.sub_exprImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getsub_expr()
   * @generated
   */
  int SUB_EXPR = 27;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_EXPR__A1 = 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_EXPR__A2 = 1;

  /**
   * The number of structural features of the '<em>sub expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUB_EXPR_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.conjunctionImpl <em>conjunction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.conjunctionImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getconjunction()
   * @generated
   */
  int CONJUNCTION = 28;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONJUNCTION__A1 = 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONJUNCTION__A2 = 1;

  /**
   * The number of structural features of the '<em>conjunction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONJUNCTION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.relational_exprImpl <em>relational expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.relational_exprImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getrelational_expr()
   * @generated
   */
  int RELATIONAL_EXPR = 29;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATIONAL_EXPR__A1 = 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATIONAL_EXPR__A2 = 1;

  /**
   * The number of structural features of the '<em>relational expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RELATIONAL_EXPR_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.additionImpl <em>addition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.additionImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getaddition()
   * @generated
   */
  int ADDITION = 30;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDITION__A1 = 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDITION__A2 = 1;

  /**
   * The number of structural features of the '<em>addition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADDITION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.multiplicationImpl <em>multiplication</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.multiplicationImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getmultiplication()
   * @generated
   */
  int MULTIPLICATION = 31;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MULTIPLICATION__A1 = 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MULTIPLICATION__A2 = 1;

  /**
   * The number of structural features of the '<em>multiplication</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MULTIPLICATION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.sign_exprImpl <em>sign expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.sign_exprImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getsign_expr()
   * @generated
   */
  int SIGN_EXPR = 32;

  /**
   * The number of structural features of the '<em>sign expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SIGN_EXPR_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.boolean_negationImpl <em>boolean negation</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.boolean_negationImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getboolean_negation()
   * @generated
   */
  int BOOLEAN_NEGATION = 33;

  /**
   * The number of structural features of the '<em>boolean negation</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BOOLEAN_NEGATION_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.termImpl <em>term</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.termImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getterm()
   * @generated
   */
  int TERM = 34;

  /**
   * The feature id for the '<em><b>A1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TERM__A1 = SIGN_EXPR_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>A2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TERM__A2 = SIGN_EXPR_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>term</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TERM_FEATURE_COUNT = SIGN_EXPR_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.member_accessImpl <em>member access</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.member_accessImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getmember_access()
   * @generated
   */
  int MEMBER_ACCESS = 36;

  /**
   * The number of structural features of the '<em>member access</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MEMBER_ACCESS_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.instance_start_segmentImpl <em>instance start segment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.instance_start_segmentImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getinstance_start_segment()
   * @generated
   */
  int INSTANCE_START_SEGMENT = 35;

  /**
   * The number of structural features of the '<em>instance start segment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTANCE_START_SEGMENT_FEATURE_COUNT = MEMBER_ACCESS_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.array_refsImpl <em>array refs</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.bp.xtext.oal.xoal.impl.array_refsImpl
   * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getarray_refs()
   * @generated
   */
  int ARRAY_REFS = 37;

  /**
   * The feature id for the '<em><b>A1</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_REFS__A1 = INSTANCE_START_SEGMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>array refs</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARRAY_REFS_FEATURE_COUNT = INSTANCE_START_SEGMENT_FEATURE_COUNT + 1;


  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.Code <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Code</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.Code
   * @generated
   */
  EClass getCode();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.block <em>block</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>block</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.block
   * @generated
   */
  EClass getblock();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.block#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.block#getStatements()
   * @see #getblock()
   * @generated
   */
  EReference getblock_Statements();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.statement <em>statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.statement
   * @generated
   */
  EClass getstatement();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.assignment_statement <em>assignment statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>assignment statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.assignment_statement
   * @generated
   */
  EClass getassignment_statement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.assignment_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.assignment_statement#getA1()
   * @see #getassignment_statement()
   * @generated
   */
  EReference getassignment_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.break_statement <em>break statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>break statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.break_statement
   * @generated
   */
  EClass getbreak_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.break_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.break_statement#getA1()
   * @see #getbreak_statement()
   * @generated
   */
  EAttribute getbreak_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.bridge_statement <em>bridge statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>bridge statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_statement
   * @generated
   */
  EClass getbridge_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.bridge_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_statement#getA1()
   * @see #getbridge_statement()
   * @generated
   */
  EAttribute getbridge_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.send_statement <em>send statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>send statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.send_statement
   * @generated
   */
  EClass getsend_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.send_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.send_statement#getA1()
   * @see #getsend_statement()
   * @generated
   */
  EAttribute getsend_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.control_statement <em>control statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>control statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.control_statement
   * @generated
   */
  EClass getcontrol_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.control_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.control_statement#getA1()
   * @see #getcontrol_statement()
   * @generated
   */
  EAttribute getcontrol_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.continue_statement <em>continue statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>continue statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.continue_statement
   * @generated
   */
  EClass getcontinue_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.continue_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.continue_statement#getA1()
   * @see #getcontinue_statement()
   * @generated
   */
  EAttribute getcontinue_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.create_event_statement <em>create event statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>create event statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.create_event_statement
   * @generated
   */
  EClass getcreate_event_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.create_event_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.create_event_statement#getA1()
   * @see #getcreate_event_statement()
   * @generated
   */
  EAttribute getcreate_event_statement_A1();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.create_event_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.create_event_statement#getA2()
   * @see #getcreate_event_statement()
   * @generated
   */
  EAttribute getcreate_event_statement_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.create_object_statement <em>create object statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>create object statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.create_object_statement
   * @generated
   */
  EClass getcreate_object_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.create_object_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.create_object_statement#getA1()
   * @see #getcreate_object_statement()
   * @generated
   */
  EAttribute getcreate_object_statement_A1();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.create_object_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.create_object_statement#getA2()
   * @see #getcreate_object_statement()
   * @generated
   */
  EAttribute getcreate_object_statement_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.delete_statement <em>delete statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>delete statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.delete_statement
   * @generated
   */
  EClass getdelete_statement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.delete_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.delete_statement#getA1()
   * @see #getdelete_statement()
   * @generated
   */
  EReference getdelete_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.for_statement <em>for statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>for statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.for_statement
   * @generated
   */
  EClass getfor_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.for_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.for_statement#getA1()
   * @see #getfor_statement()
   * @generated
   */
  EAttribute getfor_statement_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.for_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.for_statement#getA2()
   * @see #getfor_statement()
   * @generated
   */
  EReference getfor_statement_A2();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.for_statement#getA3 <em>A3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A3</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.for_statement#getA3()
   * @see #getfor_statement()
   * @generated
   */
  EReference getfor_statement_A3();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.generate_statement <em>generate statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>generate statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.generate_statement
   * @generated
   */
  EClass getgenerate_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.generate_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.generate_statement#getA1()
   * @see #getgenerate_statement()
   * @generated
   */
  EAttribute getgenerate_statement_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.if_statement <em>if statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>if statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement
   * @generated
   */
  EClass getif_statement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement#getA1()
   * @see #getif_statement()
   * @generated
   */
  EReference getif_statement_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement#getA2()
   * @see #getif_statement()
   * @generated
   */
  EReference getif_statement_A2();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA3 <em>A3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A3</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement#getA3()
   * @see #getif_statement()
   * @generated
   */
  EReference getif_statement_A3();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA4 <em>A4</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A4</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement#getA4()
   * @see #getif_statement()
   * @generated
   */
  EReference getif_statement_A4();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.if_statement#getA5 <em>A5</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A5</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.if_statement#getA5()
   * @see #getif_statement()
   * @generated
   */
  EReference getif_statement_A5();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.relate_statement <em>relate statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>relate statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relate_statement
   * @generated
   */
  EClass getrelate_statement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.relate_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relate_statement#getA1()
   * @see #getrelate_statement()
   * @generated
   */
  EReference getrelate_statement_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.relate_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relate_statement#getA2()
   * @see #getrelate_statement()
   * @generated
   */
  EReference getrelate_statement_A2();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.relate_statement#getA3 <em>A3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A3</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relate_statement#getA3()
   * @see #getrelate_statement()
   * @generated
   */
  EAttribute getrelate_statement_A3();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.return_statement <em>return statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>return statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.return_statement
   * @generated
   */
  EClass getreturn_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.return_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.return_statement#getA1()
   * @see #getreturn_statement()
   * @generated
   */
  EAttribute getreturn_statement_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.return_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.return_statement#getA2()
   * @see #getreturn_statement()
   * @generated
   */
  EReference getreturn_statement_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.select_statement <em>select statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>select statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.select_statement
   * @generated
   */
  EClass getselect_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.select_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.select_statement#getA1()
   * @see #getselect_statement()
   * @generated
   */
  EAttribute getselect_statement_A1();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.select_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.select_statement#getA2()
   * @see #getselect_statement()
   * @generated
   */
  EAttribute getselect_statement_A2();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.select_statement#getA3 <em>A3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A3</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.select_statement#getA3()
   * @see #getselect_statement()
   * @generated
   */
  EAttribute getselect_statement_A3();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.unrelate_statement <em>unrelate statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>unrelate statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.unrelate_statement
   * @generated
   */
  EClass getunrelate_statement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.unrelate_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.unrelate_statement#getA1()
   * @see #getunrelate_statement()
   * @generated
   */
  EReference getunrelate_statement_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.unrelate_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.unrelate_statement#getA2()
   * @see #getunrelate_statement()
   * @generated
   */
  EReference getunrelate_statement_A2();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.unrelate_statement#getA3 <em>A3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A3</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.unrelate_statement#getA3()
   * @see #getunrelate_statement()
   * @generated
   */
  EAttribute getunrelate_statement_A3();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.while_statement <em>while statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>while statement</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.while_statement
   * @generated
   */
  EClass getwhile_statement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.while_statement#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.while_statement#getA1()
   * @see #getwhile_statement()
   * @generated
   */
  EReference getwhile_statement_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.while_statement#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.while_statement#getA2()
   * @see #getwhile_statement()
   * @generated
   */
  EReference getwhile_statement_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.assignment_expr <em>assignment expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>assignment expr</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.assignment_expr
   * @generated
   */
  EClass getassignment_expr();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.assignment_expr#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.assignment_expr#getA1()
   * @see #getassignment_expr()
   * @generated
   */
  EReference getassignment_expr_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.bridge_invocation <em>bridge invocation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>bridge invocation</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_invocation
   * @generated
   */
  EClass getbridge_invocation();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.bridge_invocation#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_invocation#getA1()
   * @see #getbridge_invocation()
   * @generated
   */
  EAttribute getbridge_invocation_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.bridge_expr <em>bridge expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>bridge expr</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_expr
   * @generated
   */
  EClass getbridge_expr();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.bridge_expr#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.bridge_expr#getA1()
   * @see #getbridge_expr()
   * @generated
   */
  EReference getbridge_expr_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.assoc_obj_inst_ref_var <em>assoc obj inst ref var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>assoc obj inst ref var</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.assoc_obj_inst_ref_var
   * @generated
   */
  EClass getassoc_obj_inst_ref_var();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var <em>inst ref set var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>inst ref set var</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var
   * @generated
   */
  EClass getinst_ref_set_var();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.inst_ref_set_var#getA1()
   * @see #getinst_ref_set_var()
   * @generated
   */
  EAttribute getinst_ref_set_var_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.inst_ref_var <em>inst ref var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>inst ref var</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.inst_ref_var
   * @generated
   */
  EClass getinst_ref_var();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.inst_ref_var#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.inst_ref_var#getA1()
   * @see #getinst_ref_var()
   * @generated
   */
  EAttribute getinst_ref_var_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.expr <em>expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>expr</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.expr
   * @generated
   */
  EClass getexpr();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.expr#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.expr#getA1()
   * @see #getexpr()
   * @generated
   */
  EReference getexpr_A1();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.sub_expr <em>sub expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>sub expr</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.sub_expr
   * @generated
   */
  EClass getsub_expr();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.sub_expr#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.sub_expr#getA1()
   * @see #getsub_expr()
   * @generated
   */
  EReference getsub_expr_A1();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.sub_expr#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.sub_expr#getA2()
   * @see #getsub_expr()
   * @generated
   */
  EReference getsub_expr_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.conjunction <em>conjunction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>conjunction</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.conjunction
   * @generated
   */
  EClass getconjunction();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.conjunction#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.conjunction#getA1()
   * @see #getconjunction()
   * @generated
   */
  EReference getconjunction_A1();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.conjunction#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.conjunction#getA2()
   * @see #getconjunction()
   * @generated
   */
  EReference getconjunction_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.relational_expr <em>relational expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>relational expr</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relational_expr
   * @generated
   */
  EClass getrelational_expr();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.relational_expr#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relational_expr#getA1()
   * @see #getrelational_expr()
   * @generated
   */
  EReference getrelational_expr_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.relational_expr#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.relational_expr#getA2()
   * @see #getrelational_expr()
   * @generated
   */
  EReference getrelational_expr_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.addition <em>addition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>addition</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.addition
   * @generated
   */
  EClass getaddition();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.addition#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.addition#getA1()
   * @see #getaddition()
   * @generated
   */
  EReference getaddition_A1();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.addition#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.addition#getA2()
   * @see #getaddition()
   * @generated
   */
  EReference getaddition_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.multiplication <em>multiplication</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>multiplication</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.multiplication
   * @generated
   */
  EClass getmultiplication();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.multiplication#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.multiplication#getA1()
   * @see #getmultiplication()
   * @generated
   */
  EReference getmultiplication_A1();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.multiplication#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.multiplication#getA2()
   * @see #getmultiplication()
   * @generated
   */
  EReference getmultiplication_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.sign_expr <em>sign expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>sign expr</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.sign_expr
   * @generated
   */
  EClass getsign_expr();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.boolean_negation <em>boolean negation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>boolean negation</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.boolean_negation
   * @generated
   */
  EClass getboolean_negation();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.term <em>term</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>term</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.term
   * @generated
   */
  EClass getterm();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.bp.xtext.oal.xoal.term#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.term#getA1()
   * @see #getterm()
   * @generated
   */
  EAttribute getterm_A1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.bp.xtext.oal.xoal.term#getA2 <em>A2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>A2</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.term#getA2()
   * @see #getterm()
   * @generated
   */
  EReference getterm_A2();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.instance_start_segment <em>instance start segment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>instance start segment</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.instance_start_segment
   * @generated
   */
  EClass getinstance_start_segment();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.member_access <em>member access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>member access</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.member_access
   * @generated
   */
  EClass getmember_access();

  /**
   * Returns the meta object for class '{@link org.xtuml.bp.xtext.oal.xoal.array_refs <em>array refs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>array refs</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.array_refs
   * @generated
   */
  EClass getarray_refs();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.bp.xtext.oal.xoal.array_refs#getA1 <em>A1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>A1</em>'.
   * @see org.xtuml.bp.xtext.oal.xoal.array_refs#getA1()
   * @see #getarray_refs()
   * @generated
   */
  EReference getarray_refs_A1();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  XoalFactory getXoalFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.CodeImpl <em>Code</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.CodeImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getCode()
     * @generated
     */
    EClass CODE = eINSTANCE.getCode();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.blockImpl <em>block</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.blockImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getblock()
     * @generated
     */
    EClass BLOCK = eINSTANCE.getblock();

    /**
     * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BLOCK__STATEMENTS = eINSTANCE.getblock_Statements();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.statementImpl <em>statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getstatement()
     * @generated
     */
    EClass STATEMENT = eINSTANCE.getstatement();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.assignment_statementImpl <em>assignment statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.assignment_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getassignment_statement()
     * @generated
     */
    EClass ASSIGNMENT_STATEMENT = eINSTANCE.getassignment_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT_STATEMENT__A1 = eINSTANCE.getassignment_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.break_statementImpl <em>break statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.break_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbreak_statement()
     * @generated
     */
    EClass BREAK_STATEMENT = eINSTANCE.getbreak_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BREAK_STATEMENT__A1 = eINSTANCE.getbreak_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.bridge_statementImpl <em>bridge statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.bridge_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbridge_statement()
     * @generated
     */
    EClass BRIDGE_STATEMENT = eINSTANCE.getbridge_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BRIDGE_STATEMENT__A1 = eINSTANCE.getbridge_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.send_statementImpl <em>send statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.send_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getsend_statement()
     * @generated
     */
    EClass SEND_STATEMENT = eINSTANCE.getsend_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SEND_STATEMENT__A1 = eINSTANCE.getsend_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.control_statementImpl <em>control statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.control_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcontrol_statement()
     * @generated
     */
    EClass CONTROL_STATEMENT = eINSTANCE.getcontrol_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTROL_STATEMENT__A1 = eINSTANCE.getcontrol_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.continue_statementImpl <em>continue statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.continue_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcontinue_statement()
     * @generated
     */
    EClass CONTINUE_STATEMENT = eINSTANCE.getcontinue_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTINUE_STATEMENT__A1 = eINSTANCE.getcontinue_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.create_event_statementImpl <em>create event statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.create_event_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcreate_event_statement()
     * @generated
     */
    EClass CREATE_EVENT_STATEMENT = eINSTANCE.getcreate_event_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CREATE_EVENT_STATEMENT__A1 = eINSTANCE.getcreate_event_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CREATE_EVENT_STATEMENT__A2 = eINSTANCE.getcreate_event_statement_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.create_object_statementImpl <em>create object statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.create_object_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getcreate_object_statement()
     * @generated
     */
    EClass CREATE_OBJECT_STATEMENT = eINSTANCE.getcreate_object_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CREATE_OBJECT_STATEMENT__A1 = eINSTANCE.getcreate_object_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CREATE_OBJECT_STATEMENT__A2 = eINSTANCE.getcreate_object_statement_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.delete_statementImpl <em>delete statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.delete_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getdelete_statement()
     * @generated
     */
    EClass DELETE_STATEMENT = eINSTANCE.getdelete_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DELETE_STATEMENT__A1 = eINSTANCE.getdelete_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.for_statementImpl <em>for statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.for_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getfor_statement()
     * @generated
     */
    EClass FOR_STATEMENT = eINSTANCE.getfor_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FOR_STATEMENT__A1 = eINSTANCE.getfor_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FOR_STATEMENT__A2 = eINSTANCE.getfor_statement_A2();

    /**
     * The meta object literal for the '<em><b>A3</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FOR_STATEMENT__A3 = eINSTANCE.getfor_statement_A3();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.generate_statementImpl <em>generate statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.generate_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getgenerate_statement()
     * @generated
     */
    EClass GENERATE_STATEMENT = eINSTANCE.getgenerate_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GENERATE_STATEMENT__A1 = eINSTANCE.getgenerate_statement_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl <em>if statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.if_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getif_statement()
     * @generated
     */
    EClass IF_STATEMENT = eINSTANCE.getif_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_STATEMENT__A1 = eINSTANCE.getif_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_STATEMENT__A2 = eINSTANCE.getif_statement_A2();

    /**
     * The meta object literal for the '<em><b>A3</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_STATEMENT__A3 = eINSTANCE.getif_statement_A3();

    /**
     * The meta object literal for the '<em><b>A4</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_STATEMENT__A4 = eINSTANCE.getif_statement_A4();

    /**
     * The meta object literal for the '<em><b>A5</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IF_STATEMENT__A5 = eINSTANCE.getif_statement_A5();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.relate_statementImpl <em>relate statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.relate_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getrelate_statement()
     * @generated
     */
    EClass RELATE_STATEMENT = eINSTANCE.getrelate_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RELATE_STATEMENT__A1 = eINSTANCE.getrelate_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RELATE_STATEMENT__A2 = eINSTANCE.getrelate_statement_A2();

    /**
     * The meta object literal for the '<em><b>A3</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RELATE_STATEMENT__A3 = eINSTANCE.getrelate_statement_A3();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.return_statementImpl <em>return statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.return_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getreturn_statement()
     * @generated
     */
    EClass RETURN_STATEMENT = eINSTANCE.getreturn_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RETURN_STATEMENT__A1 = eINSTANCE.getreturn_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RETURN_STATEMENT__A2 = eINSTANCE.getreturn_statement_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl <em>select statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.select_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getselect_statement()
     * @generated
     */
    EClass SELECT_STATEMENT = eINSTANCE.getselect_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECT_STATEMENT__A1 = eINSTANCE.getselect_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECT_STATEMENT__A2 = eINSTANCE.getselect_statement_A2();

    /**
     * The meta object literal for the '<em><b>A3</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECT_STATEMENT__A3 = eINSTANCE.getselect_statement_A3();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl <em>unrelate statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.unrelate_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getunrelate_statement()
     * @generated
     */
    EClass UNRELATE_STATEMENT = eINSTANCE.getunrelate_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNRELATE_STATEMENT__A1 = eINSTANCE.getunrelate_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference UNRELATE_STATEMENT__A2 = eINSTANCE.getunrelate_statement_A2();

    /**
     * The meta object literal for the '<em><b>A3</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute UNRELATE_STATEMENT__A3 = eINSTANCE.getunrelate_statement_A3();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.while_statementImpl <em>while statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.while_statementImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getwhile_statement()
     * @generated
     */
    EClass WHILE_STATEMENT = eINSTANCE.getwhile_statement();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHILE_STATEMENT__A1 = eINSTANCE.getwhile_statement_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference WHILE_STATEMENT__A2 = eINSTANCE.getwhile_statement_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.assignment_exprImpl <em>assignment expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.assignment_exprImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getassignment_expr()
     * @generated
     */
    EClass ASSIGNMENT_EXPR = eINSTANCE.getassignment_expr();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT_EXPR__A1 = eINSTANCE.getassignment_expr_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.bridge_invocationImpl <em>bridge invocation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.bridge_invocationImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbridge_invocation()
     * @generated
     */
    EClass BRIDGE_INVOCATION = eINSTANCE.getbridge_invocation();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BRIDGE_INVOCATION__A1 = eINSTANCE.getbridge_invocation_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.bridge_exprImpl <em>bridge expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.bridge_exprImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getbridge_expr()
     * @generated
     */
    EClass BRIDGE_EXPR = eINSTANCE.getbridge_expr();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference BRIDGE_EXPR__A1 = eINSTANCE.getbridge_expr_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.assoc_obj_inst_ref_varImpl <em>assoc obj inst ref var</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.assoc_obj_inst_ref_varImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getassoc_obj_inst_ref_var()
     * @generated
     */
    EClass ASSOC_OBJ_INST_REF_VAR = eINSTANCE.getassoc_obj_inst_ref_var();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_set_varImpl <em>inst ref set var</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_set_varImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getinst_ref_set_var()
     * @generated
     */
    EClass INST_REF_SET_VAR = eINSTANCE.getinst_ref_set_var();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INST_REF_SET_VAR__A1 = eINSTANCE.getinst_ref_set_var_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_varImpl <em>inst ref var</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.inst_ref_varImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getinst_ref_var()
     * @generated
     */
    EClass INST_REF_VAR = eINSTANCE.getinst_ref_var();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INST_REF_VAR__A1 = eINSTANCE.getinst_ref_var_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.exprImpl <em>expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.exprImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getexpr()
     * @generated
     */
    EClass EXPR = eINSTANCE.getexpr();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPR__A1 = eINSTANCE.getexpr_A1();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.sub_exprImpl <em>sub expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.sub_exprImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getsub_expr()
     * @generated
     */
    EClass SUB_EXPR = eINSTANCE.getsub_expr();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUB_EXPR__A1 = eINSTANCE.getsub_expr_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUB_EXPR__A2 = eINSTANCE.getsub_expr_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.conjunctionImpl <em>conjunction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.conjunctionImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getconjunction()
     * @generated
     */
    EClass CONJUNCTION = eINSTANCE.getconjunction();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONJUNCTION__A1 = eINSTANCE.getconjunction_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONJUNCTION__A2 = eINSTANCE.getconjunction_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.relational_exprImpl <em>relational expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.relational_exprImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getrelational_expr()
     * @generated
     */
    EClass RELATIONAL_EXPR = eINSTANCE.getrelational_expr();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RELATIONAL_EXPR__A1 = eINSTANCE.getrelational_expr_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference RELATIONAL_EXPR__A2 = eINSTANCE.getrelational_expr_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.additionImpl <em>addition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.additionImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getaddition()
     * @generated
     */
    EClass ADDITION = eINSTANCE.getaddition();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ADDITION__A1 = eINSTANCE.getaddition_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ADDITION__A2 = eINSTANCE.getaddition_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.multiplicationImpl <em>multiplication</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.multiplicationImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getmultiplication()
     * @generated
     */
    EClass MULTIPLICATION = eINSTANCE.getmultiplication();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MULTIPLICATION__A1 = eINSTANCE.getmultiplication_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MULTIPLICATION__A2 = eINSTANCE.getmultiplication_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.sign_exprImpl <em>sign expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.sign_exprImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getsign_expr()
     * @generated
     */
    EClass SIGN_EXPR = eINSTANCE.getsign_expr();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.boolean_negationImpl <em>boolean negation</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.boolean_negationImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getboolean_negation()
     * @generated
     */
    EClass BOOLEAN_NEGATION = eINSTANCE.getboolean_negation();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.termImpl <em>term</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.termImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getterm()
     * @generated
     */
    EClass TERM = eINSTANCE.getterm();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TERM__A1 = eINSTANCE.getterm_A1();

    /**
     * The meta object literal for the '<em><b>A2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TERM__A2 = eINSTANCE.getterm_A2();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.instance_start_segmentImpl <em>instance start segment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.instance_start_segmentImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getinstance_start_segment()
     * @generated
     */
    EClass INSTANCE_START_SEGMENT = eINSTANCE.getinstance_start_segment();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.member_accessImpl <em>member access</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.member_accessImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getmember_access()
     * @generated
     */
    EClass MEMBER_ACCESS = eINSTANCE.getmember_access();

    /**
     * The meta object literal for the '{@link org.xtuml.bp.xtext.oal.xoal.impl.array_refsImpl <em>array refs</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.bp.xtext.oal.xoal.impl.array_refsImpl
     * @see org.xtuml.bp.xtext.oal.xoal.impl.XoalPackageImpl#getarray_refs()
     * @generated
     */
    EClass ARRAY_REFS = eINSTANCE.getarray_refs();

    /**
     * The meta object literal for the '<em><b>A1</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARRAY_REFS__A1 = eINSTANCE.getarray_refs_A1();

  }

} //XoalPackage
