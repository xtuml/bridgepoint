/**
 * <copyright>
 * </copyright>
 *

 */
package org.argouml.xtuml.oAL;

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
 * @see org.argouml.xtuml.oAL.OALFactory
 * @model kind="package"
 * @generated
 */
public interface OALPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "oAL";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.argouml.org/xtuml/OAL";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "oAL";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  OALPackage eINSTANCE = org.argouml.xtuml.oAL.impl.OALPackageImpl.init();

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.CodeImpl <em>Code</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.CodeImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getCode()
   * @generated
   */
  int CODE = 0;

  /**
   * The feature id for the '<em><b>Statements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CODE__STATEMENTS = 0;

  /**
   * The number of structural features of the '<em>Code</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CODE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.statementImpl <em>statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.statementImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getstatement()
   * @generated
   */
  int STATEMENT = 1;

  /**
   * The feature id for the '<em><b>St1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT__ST1 = 0;

  /**
   * The feature id for the '<em><b>St2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT__ST2 = 1;

  /**
   * The feature id for the '<em><b>St3</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT__ST3 = 2;

  /**
   * The number of structural features of the '<em>statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATEMENT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeObjectStatementImpl <em>Type Object Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeObjectStatementImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeObjectStatement()
   * @generated
   */
  int TYPE_OBJECT_STATEMENT = 2;

  /**
   * The number of structural features of the '<em>Type Object Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_OBJECT_STATEMENT_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.select_statementImpl <em>select statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.select_statementImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getselect_statement()
   * @generated
   */
  int SELECT_STATEMENT = 3;

  /**
   * The feature id for the '<em><b>Var</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECT_STATEMENT__VAR = TYPE_OBJECT_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>select statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SELECT_STATEMENT_FEATURE_COUNT = TYPE_OBJECT_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.assignmentImpl <em>assignment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.assignmentImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getassignment()
   * @generated
   */
  int ASSIGNMENT = 4;

  /**
   * The feature id for the '<em><b>E</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT__E = 0;

  /**
   * The number of structural features of the '<em>assignment</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNMENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementImpl <em>Type Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeStatementImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatement()
   * @generated
   */
  int TYPE_STATEMENT = 5;

  /**
   * The feature id for the '<em><b>Substatements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT__SUBSTATEMENTS = 0;

  /**
   * The number of structural features of the '<em>Type Statement</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.expressionImpl <em>expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.expressionImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getexpression()
   * @generated
   */
  int EXPRESSION = 6;

  /**
   * The feature id for the '<em><b>Var</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__VAR = SELECT_STATEMENT__VAR;

  /**
   * The feature id for the '<em><b>Ne</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__NE = SELECT_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Ls</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__LS = SELECT_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Rs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__RS = SELECT_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_FEATURE_COUNT = SELECT_STATEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.expr2Impl <em>expr2</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.expr2Impl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getexpr2()
   * @generated
   */
  int EXPR2 = 7;

  /**
   * The feature id for the '<em><b>N</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPR2__N = 0;

  /**
   * The feature id for the '<em><b>S</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPR2__S = 1;

  /**
   * The number of structural features of the '<em>expr2</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPR2_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.sumImpl <em>sum</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.sumImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getsum()
   * @generated
   */
  int SUM = 8;

  /**
   * The feature id for the '<em><b>Lt</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUM__LT = 0;

  /**
   * The feature id for the '<em><b>Rt</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUM__RT = 1;

  /**
   * The number of structural features of the '<em>sum</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SUM_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.productImpl <em>product</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.productImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getproduct()
   * @generated
   */
  int PRODUCT = 9;

  /**
   * The feature id for the '<em><b>Lf</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT__LF = 0;

  /**
   * The feature id for the '<em><b>Rf</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT__RF = 1;

  /**
   * The number of structural features of the '<em>product</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeValueImpl <em>Type Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeValueImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeValue()
   * @generated
   */
  int TYPE_VALUE = 10;

  /**
   * The number of structural features of the '<em>Type Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_VALUE_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeCreateImpl <em>Type Create</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeCreateImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeCreate()
   * @generated
   */
  int TYPE_CREATE = 11;

  /**
   * The number of structural features of the '<em>Type Create</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_CREATE_FEATURE_COUNT = TYPE_OBJECT_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeRelateImpl <em>Type Relate</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeRelateImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeRelate()
   * @generated
   */
  int TYPE_RELATE = 12;

  /**
   * The number of structural features of the '<em>Type Relate</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_RELATE_FEATURE_COUNT = TYPE_OBJECT_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeDeleteImpl <em>Type Delete</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeDeleteImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeDelete()
   * @generated
   */
  int TYPE_DELETE = 13;

  /**
   * The number of structural features of the '<em>Type Delete</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_DELETE_FEATURE_COUNT = TYPE_OBJECT_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementIfImpl <em>Type Statement If</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeStatementIfImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatementIf()
   * @generated
   */
  int TYPE_STATEMENT_IF = 14;

  /**
   * The feature id for the '<em><b>Substatements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_IF__SUBSTATEMENTS = TYPE_STATEMENT__SUBSTATEMENTS;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_IF__EXPR = TYPE_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Elifexpr</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_IF__ELIFEXPR = TYPE_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Type Statement If</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_IF_FEATURE_COUNT = TYPE_STATEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementForImpl <em>Type Statement For</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeStatementForImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatementFor()
   * @generated
   */
  int TYPE_STATEMENT_FOR = 15;

  /**
   * The feature id for the '<em><b>Substatements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_FOR__SUBSTATEMENTS = TYPE_STATEMENT__SUBSTATEMENTS;

  /**
   * The feature id for the '<em><b>List</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_FOR__LIST = TYPE_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Type Statement For</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_FOR_FEATURE_COUNT = TYPE_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementWhileImpl <em>Type Statement While</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeStatementWhileImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatementWhile()
   * @generated
   */
  int TYPE_STATEMENT_WHILE = 16;

  /**
   * The feature id for the '<em><b>Substatements</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_WHILE__SUBSTATEMENTS = TYPE_STATEMENT__SUBSTATEMENTS;

  /**
   * The feature id for the '<em><b>Expr</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_WHILE__EXPR = TYPE_STATEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Type Statement While</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_STATEMENT_WHILE_FEATURE_COUNT = TYPE_STATEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeValueVariableImpl <em>Type Value Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeValueVariableImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeValueVariable()
   * @generated
   */
  int TYPE_VALUE_VARIABLE = 17;

  /**
   * The number of structural features of the '<em>Type Value Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_VALUE_VARIABLE_FEATURE_COUNT = TYPE_VALUE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.argouml.xtuml.oAL.impl.TypeConstantImpl <em>Type Constant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.argouml.xtuml.oAL.impl.TypeConstantImpl
   * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeConstant()
   * @generated
   */
  int TYPE_CONSTANT = 18;

  /**
   * The number of structural features of the '<em>Type Constant</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_CONSTANT_FEATURE_COUNT = TYPE_VALUE_FEATURE_COUNT + 0;


  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.Code <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Code</em>'.
   * @see org.argouml.xtuml.oAL.Code
   * @generated
   */
  EClass getCode();

  /**
   * Returns the meta object for the containment reference list '{@link org.argouml.xtuml.oAL.Code#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see org.argouml.xtuml.oAL.Code#getStatements()
   * @see #getCode()
   * @generated
   */
  EReference getCode_Statements();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.statement <em>statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>statement</em>'.
   * @see org.argouml.xtuml.oAL.statement
   * @generated
   */
  EClass getstatement();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.statement#getSt1 <em>St1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>St1</em>'.
   * @see org.argouml.xtuml.oAL.statement#getSt1()
   * @see #getstatement()
   * @generated
   */
  EReference getstatement_St1();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.statement#getSt2 <em>St2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>St2</em>'.
   * @see org.argouml.xtuml.oAL.statement#getSt2()
   * @see #getstatement()
   * @generated
   */
  EReference getstatement_St2();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.statement#getSt3 <em>St3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>St3</em>'.
   * @see org.argouml.xtuml.oAL.statement#getSt3()
   * @see #getstatement()
   * @generated
   */
  EReference getstatement_St3();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeObjectStatement <em>Type Object Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Object Statement</em>'.
   * @see org.argouml.xtuml.oAL.TypeObjectStatement
   * @generated
   */
  EClass getTypeObjectStatement();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.select_statement <em>select statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>select statement</em>'.
   * @see org.argouml.xtuml.oAL.select_statement
   * @generated
   */
  EClass getselect_statement();

  /**
   * Returns the meta object for the attribute '{@link org.argouml.xtuml.oAL.select_statement#getVar <em>Var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Var</em>'.
   * @see org.argouml.xtuml.oAL.select_statement#getVar()
   * @see #getselect_statement()
   * @generated
   */
  EAttribute getselect_statement_Var();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.assignment <em>assignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>assignment</em>'.
   * @see org.argouml.xtuml.oAL.assignment
   * @generated
   */
  EClass getassignment();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.assignment#getE <em>E</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>E</em>'.
   * @see org.argouml.xtuml.oAL.assignment#getE()
   * @see #getassignment()
   * @generated
   */
  EReference getassignment_E();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeStatement <em>Type Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatement
   * @generated
   */
  EClass getTypeStatement();

  /**
   * Returns the meta object for the containment reference list '{@link org.argouml.xtuml.oAL.TypeStatement#getSubstatements <em>Substatements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Substatements</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatement#getSubstatements()
   * @see #getTypeStatement()
   * @generated
   */
  EReference getTypeStatement_Substatements();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.expression <em>expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>expression</em>'.
   * @see org.argouml.xtuml.oAL.expression
   * @generated
   */
  EClass getexpression();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.expression#getNe <em>Ne</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Ne</em>'.
   * @see org.argouml.xtuml.oAL.expression#getNe()
   * @see #getexpression()
   * @generated
   */
  EReference getexpression_Ne();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.expression#getLs <em>Ls</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Ls</em>'.
   * @see org.argouml.xtuml.oAL.expression#getLs()
   * @see #getexpression()
   * @generated
   */
  EReference getexpression_Ls();

  /**
   * Returns the meta object for the containment reference list '{@link org.argouml.xtuml.oAL.expression#getRs <em>Rs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rs</em>'.
   * @see org.argouml.xtuml.oAL.expression#getRs()
   * @see #getexpression()
   * @generated
   */
  EReference getexpression_Rs();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.expr2 <em>expr2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>expr2</em>'.
   * @see org.argouml.xtuml.oAL.expr2
   * @generated
   */
  EClass getexpr2();

  /**
   * Returns the meta object for the attribute '{@link org.argouml.xtuml.oAL.expr2#getN <em>N</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>N</em>'.
   * @see org.argouml.xtuml.oAL.expr2#getN()
   * @see #getexpr2()
   * @generated
   */
  EAttribute getexpr2_N();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.expr2#getS <em>S</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>S</em>'.
   * @see org.argouml.xtuml.oAL.expr2#getS()
   * @see #getexpr2()
   * @generated
   */
  EReference getexpr2_S();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.sum <em>sum</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>sum</em>'.
   * @see org.argouml.xtuml.oAL.sum
   * @generated
   */
  EClass getsum();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.sum#getLt <em>Lt</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lt</em>'.
   * @see org.argouml.xtuml.oAL.sum#getLt()
   * @see #getsum()
   * @generated
   */
  EReference getsum_Lt();

  /**
   * Returns the meta object for the containment reference list '{@link org.argouml.xtuml.oAL.sum#getRt <em>Rt</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rt</em>'.
   * @see org.argouml.xtuml.oAL.sum#getRt()
   * @see #getsum()
   * @generated
   */
  EReference getsum_Rt();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.product <em>product</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>product</em>'.
   * @see org.argouml.xtuml.oAL.product
   * @generated
   */
  EClass getproduct();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.product#getLf <em>Lf</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lf</em>'.
   * @see org.argouml.xtuml.oAL.product#getLf()
   * @see #getproduct()
   * @generated
   */
  EReference getproduct_Lf();

  /**
   * Returns the meta object for the containment reference list '{@link org.argouml.xtuml.oAL.product#getRf <em>Rf</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rf</em>'.
   * @see org.argouml.xtuml.oAL.product#getRf()
   * @see #getproduct()
   * @generated
   */
  EReference getproduct_Rf();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeValue <em>Type Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Value</em>'.
   * @see org.argouml.xtuml.oAL.TypeValue
   * @generated
   */
  EClass getTypeValue();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeCreate <em>Type Create</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Create</em>'.
   * @see org.argouml.xtuml.oAL.TypeCreate
   * @generated
   */
  EClass getTypeCreate();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeRelate <em>Type Relate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Relate</em>'.
   * @see org.argouml.xtuml.oAL.TypeRelate
   * @generated
   */
  EClass getTypeRelate();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeDelete <em>Type Delete</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Delete</em>'.
   * @see org.argouml.xtuml.oAL.TypeDelete
   * @generated
   */
  EClass getTypeDelete();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeStatementIf <em>Type Statement If</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement If</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementIf
   * @generated
   */
  EClass getTypeStatementIf();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.TypeStatementIf#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementIf#getExpr()
   * @see #getTypeStatementIf()
   * @generated
   */
  EReference getTypeStatementIf_Expr();

  /**
   * Returns the meta object for the containment reference list '{@link org.argouml.xtuml.oAL.TypeStatementIf#getElifexpr <em>Elifexpr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elifexpr</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementIf#getElifexpr()
   * @see #getTypeStatementIf()
   * @generated
   */
  EReference getTypeStatementIf_Elifexpr();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeStatementFor <em>Type Statement For</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement For</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementFor
   * @generated
   */
  EClass getTypeStatementFor();

  /**
   * Returns the meta object for the attribute '{@link org.argouml.xtuml.oAL.TypeStatementFor#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>List</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementFor#getList()
   * @see #getTypeStatementFor()
   * @generated
   */
  EAttribute getTypeStatementFor_List();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeStatementWhile <em>Type Statement While</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement While</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementWhile
   * @generated
   */
  EClass getTypeStatementWhile();

  /**
   * Returns the meta object for the containment reference '{@link org.argouml.xtuml.oAL.TypeStatementWhile#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see org.argouml.xtuml.oAL.TypeStatementWhile#getExpr()
   * @see #getTypeStatementWhile()
   * @generated
   */
  EReference getTypeStatementWhile_Expr();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeValueVariable <em>Type Value Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Value Variable</em>'.
   * @see org.argouml.xtuml.oAL.TypeValueVariable
   * @generated
   */
  EClass getTypeValueVariable();

  /**
   * Returns the meta object for class '{@link org.argouml.xtuml.oAL.TypeConstant <em>Type Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Constant</em>'.
   * @see org.argouml.xtuml.oAL.TypeConstant
   * @generated
   */
  EClass getTypeConstant();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  OALFactory getOALFactory();

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
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.CodeImpl <em>Code</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.CodeImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getCode()
     * @generated
     */
    EClass CODE = eINSTANCE.getCode();

    /**
     * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CODE__STATEMENTS = eINSTANCE.getCode_Statements();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.statementImpl <em>statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.statementImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getstatement()
     * @generated
     */
    EClass STATEMENT = eINSTANCE.getstatement();

    /**
     * The meta object literal for the '<em><b>St1</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT__ST1 = eINSTANCE.getstatement_St1();

    /**
     * The meta object literal for the '<em><b>St2</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT__ST2 = eINSTANCE.getstatement_St2();

    /**
     * The meta object literal for the '<em><b>St3</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATEMENT__ST3 = eINSTANCE.getstatement_St3();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeObjectStatementImpl <em>Type Object Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeObjectStatementImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeObjectStatement()
     * @generated
     */
    EClass TYPE_OBJECT_STATEMENT = eINSTANCE.getTypeObjectStatement();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.select_statementImpl <em>select statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.select_statementImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getselect_statement()
     * @generated
     */
    EClass SELECT_STATEMENT = eINSTANCE.getselect_statement();

    /**
     * The meta object literal for the '<em><b>Var</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SELECT_STATEMENT__VAR = eINSTANCE.getselect_statement_Var();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.assignmentImpl <em>assignment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.assignmentImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getassignment()
     * @generated
     */
    EClass ASSIGNMENT = eINSTANCE.getassignment();

    /**
     * The meta object literal for the '<em><b>E</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNMENT__E = eINSTANCE.getassignment_E();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementImpl <em>Type Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeStatementImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatement()
     * @generated
     */
    EClass TYPE_STATEMENT = eINSTANCE.getTypeStatement();

    /**
     * The meta object literal for the '<em><b>Substatements</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_STATEMENT__SUBSTATEMENTS = eINSTANCE.getTypeStatement_Substatements();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.expressionImpl <em>expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.expressionImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getexpression()
     * @generated
     */
    EClass EXPRESSION = eINSTANCE.getexpression();

    /**
     * The meta object literal for the '<em><b>Ne</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__NE = eINSTANCE.getexpression_Ne();

    /**
     * The meta object literal for the '<em><b>Ls</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__LS = eINSTANCE.getexpression_Ls();

    /**
     * The meta object literal for the '<em><b>Rs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__RS = eINSTANCE.getexpression_Rs();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.expr2Impl <em>expr2</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.expr2Impl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getexpr2()
     * @generated
     */
    EClass EXPR2 = eINSTANCE.getexpr2();

    /**
     * The meta object literal for the '<em><b>N</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPR2__N = eINSTANCE.getexpr2_N();

    /**
     * The meta object literal for the '<em><b>S</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPR2__S = eINSTANCE.getexpr2_S();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.sumImpl <em>sum</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.sumImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getsum()
     * @generated
     */
    EClass SUM = eINSTANCE.getsum();

    /**
     * The meta object literal for the '<em><b>Lt</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUM__LT = eINSTANCE.getsum_Lt();

    /**
     * The meta object literal for the '<em><b>Rt</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SUM__RT = eINSTANCE.getsum_Rt();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.productImpl <em>product</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.productImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getproduct()
     * @generated
     */
    EClass PRODUCT = eINSTANCE.getproduct();

    /**
     * The meta object literal for the '<em><b>Lf</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PRODUCT__LF = eINSTANCE.getproduct_Lf();

    /**
     * The meta object literal for the '<em><b>Rf</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PRODUCT__RF = eINSTANCE.getproduct_Rf();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeValueImpl <em>Type Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeValueImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeValue()
     * @generated
     */
    EClass TYPE_VALUE = eINSTANCE.getTypeValue();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeCreateImpl <em>Type Create</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeCreateImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeCreate()
     * @generated
     */
    EClass TYPE_CREATE = eINSTANCE.getTypeCreate();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeRelateImpl <em>Type Relate</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeRelateImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeRelate()
     * @generated
     */
    EClass TYPE_RELATE = eINSTANCE.getTypeRelate();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeDeleteImpl <em>Type Delete</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeDeleteImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeDelete()
     * @generated
     */
    EClass TYPE_DELETE = eINSTANCE.getTypeDelete();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementIfImpl <em>Type Statement If</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeStatementIfImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatementIf()
     * @generated
     */
    EClass TYPE_STATEMENT_IF = eINSTANCE.getTypeStatementIf();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_STATEMENT_IF__EXPR = eINSTANCE.getTypeStatementIf_Expr();

    /**
     * The meta object literal for the '<em><b>Elifexpr</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_STATEMENT_IF__ELIFEXPR = eINSTANCE.getTypeStatementIf_Elifexpr();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementForImpl <em>Type Statement For</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeStatementForImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatementFor()
     * @generated
     */
    EClass TYPE_STATEMENT_FOR = eINSTANCE.getTypeStatementFor();

    /**
     * The meta object literal for the '<em><b>List</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE_STATEMENT_FOR__LIST = eINSTANCE.getTypeStatementFor_List();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeStatementWhileImpl <em>Type Statement While</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeStatementWhileImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeStatementWhile()
     * @generated
     */
    EClass TYPE_STATEMENT_WHILE = eINSTANCE.getTypeStatementWhile();

    /**
     * The meta object literal for the '<em><b>Expr</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TYPE_STATEMENT_WHILE__EXPR = eINSTANCE.getTypeStatementWhile_Expr();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeValueVariableImpl <em>Type Value Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeValueVariableImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeValueVariable()
     * @generated
     */
    EClass TYPE_VALUE_VARIABLE = eINSTANCE.getTypeValueVariable();

    /**
     * The meta object literal for the '{@link org.argouml.xtuml.oAL.impl.TypeConstantImpl <em>Type Constant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.argouml.xtuml.oAL.impl.TypeConstantImpl
     * @see org.argouml.xtuml.oAL.impl.OALPackageImpl#getTypeConstant()
     * @generated
     */
    EClass TYPE_CONSTANT = eINSTANCE.getTypeConstant();

  }

} //OALPackage
