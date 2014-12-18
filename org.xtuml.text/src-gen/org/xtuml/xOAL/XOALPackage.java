/**
 * <copyright>
 * </copyright>
 *

 */
package org.xtuml.xOAL;

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
 * @see org.xtuml.xOAL.XOALFactory
 * @model kind="package"
 * @generated
 */
public interface XOALPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "xOAL";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.xtuml.org/xOAL";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "xOAL";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  XOALPackage eINSTANCE = org.xtuml.xOAL.impl.XOALPackageImpl.init();

  /**
   * The meta object id for the '{@link org.xtuml.xOAL.impl.CodeImpl <em>Code</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.CodeImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getCode()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.statementImpl <em>statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.statementImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getstatement()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeObjectStatementImpl <em>Type Object Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeObjectStatementImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeObjectStatement()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.select_statementImpl <em>select statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.select_statementImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getselect_statement()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.assignmentImpl <em>assignment</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.assignmentImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getassignment()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeStatementImpl <em>Type Statement</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeStatementImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatement()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.expressionImpl <em>expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.expressionImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getexpression()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.expr2Impl <em>expr2</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.expr2Impl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getexpr2()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.sumImpl <em>sum</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.sumImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getsum()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.productImpl <em>product</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.productImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getproduct()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeValueImpl <em>Type Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeValueImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeValue()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeCreateImpl <em>Type Create</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeCreateImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeCreate()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeRelateImpl <em>Type Relate</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeRelateImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeRelate()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeDeleteImpl <em>Type Delete</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeDeleteImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeDelete()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeStatementIfImpl <em>Type Statement If</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeStatementIfImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatementIf()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeStatementForImpl <em>Type Statement For</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeStatementForImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatementFor()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeStatementWhileImpl <em>Type Statement While</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeStatementWhileImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatementWhile()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeValueVariableImpl <em>Type Value Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeValueVariableImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeValueVariable()
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
   * The meta object id for the '{@link org.xtuml.xOAL.impl.TypeConstantImpl <em>Type Constant</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.xtuml.xOAL.impl.TypeConstantImpl
   * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeConstant()
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
   * Returns the meta object for class '{@link org.xtuml.xOAL.Code <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Code</em>'.
   * @see org.xtuml.xOAL.Code
   * @generated
   */
  EClass getCode();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.xOAL.Code#getStatements <em>Statements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Statements</em>'.
   * @see org.xtuml.xOAL.Code#getStatements()
   * @see #getCode()
   * @generated
   */
  EReference getCode_Statements();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.statement <em>statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>statement</em>'.
   * @see org.xtuml.xOAL.statement
   * @generated
   */
  EClass getstatement();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.statement#getSt1 <em>St1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>St1</em>'.
   * @see org.xtuml.xOAL.statement#getSt1()
   * @see #getstatement()
   * @generated
   */
  EReference getstatement_St1();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.statement#getSt2 <em>St2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>St2</em>'.
   * @see org.xtuml.xOAL.statement#getSt2()
   * @see #getstatement()
   * @generated
   */
  EReference getstatement_St2();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.statement#getSt3 <em>St3</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>St3</em>'.
   * @see org.xtuml.xOAL.statement#getSt3()
   * @see #getstatement()
   * @generated
   */
  EReference getstatement_St3();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeObjectStatement <em>Type Object Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Object Statement</em>'.
   * @see org.xtuml.xOAL.TypeObjectStatement
   * @generated
   */
  EClass getTypeObjectStatement();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.select_statement <em>select statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>select statement</em>'.
   * @see org.xtuml.xOAL.select_statement
   * @generated
   */
  EClass getselect_statement();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.xOAL.select_statement#getVar <em>Var</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Var</em>'.
   * @see org.xtuml.xOAL.select_statement#getVar()
   * @see #getselect_statement()
   * @generated
   */
  EAttribute getselect_statement_Var();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.assignment <em>assignment</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>assignment</em>'.
   * @see org.xtuml.xOAL.assignment
   * @generated
   */
  EClass getassignment();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.assignment#getE <em>E</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>E</em>'.
   * @see org.xtuml.xOAL.assignment#getE()
   * @see #getassignment()
   * @generated
   */
  EReference getassignment_E();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeStatement <em>Type Statement</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement</em>'.
   * @see org.xtuml.xOAL.TypeStatement
   * @generated
   */
  EClass getTypeStatement();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.xOAL.TypeStatement#getSubstatements <em>Substatements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Substatements</em>'.
   * @see org.xtuml.xOAL.TypeStatement#getSubstatements()
   * @see #getTypeStatement()
   * @generated
   */
  EReference getTypeStatement_Substatements();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.expression <em>expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>expression</em>'.
   * @see org.xtuml.xOAL.expression
   * @generated
   */
  EClass getexpression();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.expression#getNe <em>Ne</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Ne</em>'.
   * @see org.xtuml.xOAL.expression#getNe()
   * @see #getexpression()
   * @generated
   */
  EReference getexpression_Ne();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.expression#getLs <em>Ls</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Ls</em>'.
   * @see org.xtuml.xOAL.expression#getLs()
   * @see #getexpression()
   * @generated
   */
  EReference getexpression_Ls();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.xOAL.expression#getRs <em>Rs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rs</em>'.
   * @see org.xtuml.xOAL.expression#getRs()
   * @see #getexpression()
   * @generated
   */
  EReference getexpression_Rs();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.expr2 <em>expr2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>expr2</em>'.
   * @see org.xtuml.xOAL.expr2
   * @generated
   */
  EClass getexpr2();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.xOAL.expr2#getN <em>N</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>N</em>'.
   * @see org.xtuml.xOAL.expr2#getN()
   * @see #getexpr2()
   * @generated
   */
  EAttribute getexpr2_N();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.expr2#getS <em>S</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>S</em>'.
   * @see org.xtuml.xOAL.expr2#getS()
   * @see #getexpr2()
   * @generated
   */
  EReference getexpr2_S();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.sum <em>sum</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>sum</em>'.
   * @see org.xtuml.xOAL.sum
   * @generated
   */
  EClass getsum();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.sum#getLt <em>Lt</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lt</em>'.
   * @see org.xtuml.xOAL.sum#getLt()
   * @see #getsum()
   * @generated
   */
  EReference getsum_Lt();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.xOAL.sum#getRt <em>Rt</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rt</em>'.
   * @see org.xtuml.xOAL.sum#getRt()
   * @see #getsum()
   * @generated
   */
  EReference getsum_Rt();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.product <em>product</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>product</em>'.
   * @see org.xtuml.xOAL.product
   * @generated
   */
  EClass getproduct();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.product#getLf <em>Lf</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lf</em>'.
   * @see org.xtuml.xOAL.product#getLf()
   * @see #getproduct()
   * @generated
   */
  EReference getproduct_Lf();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.xOAL.product#getRf <em>Rf</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Rf</em>'.
   * @see org.xtuml.xOAL.product#getRf()
   * @see #getproduct()
   * @generated
   */
  EReference getproduct_Rf();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeValue <em>Type Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Value</em>'.
   * @see org.xtuml.xOAL.TypeValue
   * @generated
   */
  EClass getTypeValue();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeCreate <em>Type Create</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Create</em>'.
   * @see org.xtuml.xOAL.TypeCreate
   * @generated
   */
  EClass getTypeCreate();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeRelate <em>Type Relate</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Relate</em>'.
   * @see org.xtuml.xOAL.TypeRelate
   * @generated
   */
  EClass getTypeRelate();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeDelete <em>Type Delete</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Delete</em>'.
   * @see org.xtuml.xOAL.TypeDelete
   * @generated
   */
  EClass getTypeDelete();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeStatementIf <em>Type Statement If</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement If</em>'.
   * @see org.xtuml.xOAL.TypeStatementIf
   * @generated
   */
  EClass getTypeStatementIf();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.TypeStatementIf#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see org.xtuml.xOAL.TypeStatementIf#getExpr()
   * @see #getTypeStatementIf()
   * @generated
   */
  EReference getTypeStatementIf_Expr();

  /**
   * Returns the meta object for the containment reference list '{@link org.xtuml.xOAL.TypeStatementIf#getElifexpr <em>Elifexpr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Elifexpr</em>'.
   * @see org.xtuml.xOAL.TypeStatementIf#getElifexpr()
   * @see #getTypeStatementIf()
   * @generated
   */
  EReference getTypeStatementIf_Elifexpr();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeStatementFor <em>Type Statement For</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement For</em>'.
   * @see org.xtuml.xOAL.TypeStatementFor
   * @generated
   */
  EClass getTypeStatementFor();

  /**
   * Returns the meta object for the attribute '{@link org.xtuml.xOAL.TypeStatementFor#getList <em>List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>List</em>'.
   * @see org.xtuml.xOAL.TypeStatementFor#getList()
   * @see #getTypeStatementFor()
   * @generated
   */
  EAttribute getTypeStatementFor_List();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeStatementWhile <em>Type Statement While</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Statement While</em>'.
   * @see org.xtuml.xOAL.TypeStatementWhile
   * @generated
   */
  EClass getTypeStatementWhile();

  /**
   * Returns the meta object for the containment reference '{@link org.xtuml.xOAL.TypeStatementWhile#getExpr <em>Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expr</em>'.
   * @see org.xtuml.xOAL.TypeStatementWhile#getExpr()
   * @see #getTypeStatementWhile()
   * @generated
   */
  EReference getTypeStatementWhile_Expr();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeValueVariable <em>Type Value Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Value Variable</em>'.
   * @see org.xtuml.xOAL.TypeValueVariable
   * @generated
   */
  EClass getTypeValueVariable();

  /**
   * Returns the meta object for class '{@link org.xtuml.xOAL.TypeConstant <em>Type Constant</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type Constant</em>'.
   * @see org.xtuml.xOAL.TypeConstant
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
  XOALFactory getXOALFactory();

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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.CodeImpl <em>Code</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.CodeImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getCode()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.statementImpl <em>statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.statementImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getstatement()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeObjectStatementImpl <em>Type Object Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeObjectStatementImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeObjectStatement()
     * @generated
     */
    EClass TYPE_OBJECT_STATEMENT = eINSTANCE.getTypeObjectStatement();

    /**
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.select_statementImpl <em>select statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.select_statementImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getselect_statement()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.assignmentImpl <em>assignment</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.assignmentImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getassignment()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeStatementImpl <em>Type Statement</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeStatementImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatement()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.expressionImpl <em>expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.expressionImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getexpression()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.expr2Impl <em>expr2</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.expr2Impl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getexpr2()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.sumImpl <em>sum</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.sumImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getsum()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.productImpl <em>product</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.productImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getproduct()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeValueImpl <em>Type Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeValueImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeValue()
     * @generated
     */
    EClass TYPE_VALUE = eINSTANCE.getTypeValue();

    /**
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeCreateImpl <em>Type Create</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeCreateImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeCreate()
     * @generated
     */
    EClass TYPE_CREATE = eINSTANCE.getTypeCreate();

    /**
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeRelateImpl <em>Type Relate</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeRelateImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeRelate()
     * @generated
     */
    EClass TYPE_RELATE = eINSTANCE.getTypeRelate();

    /**
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeDeleteImpl <em>Type Delete</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeDeleteImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeDelete()
     * @generated
     */
    EClass TYPE_DELETE = eINSTANCE.getTypeDelete();

    /**
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeStatementIfImpl <em>Type Statement If</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeStatementIfImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatementIf()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeStatementForImpl <em>Type Statement For</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeStatementForImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatementFor()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeStatementWhileImpl <em>Type Statement While</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeStatementWhileImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeStatementWhile()
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
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeValueVariableImpl <em>Type Value Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeValueVariableImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeValueVariable()
     * @generated
     */
    EClass TYPE_VALUE_VARIABLE = eINSTANCE.getTypeValueVariable();

    /**
     * The meta object literal for the '{@link org.xtuml.xOAL.impl.TypeConstantImpl <em>Type Constant</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.xtuml.xOAL.impl.TypeConstantImpl
     * @see org.xtuml.xOAL.impl.XOALPackageImpl#getTypeConstant()
     * @generated
     */
    EClass TYPE_CONSTANT = eINSTANCE.getTypeConstant();

  }

} //XOALPackage
