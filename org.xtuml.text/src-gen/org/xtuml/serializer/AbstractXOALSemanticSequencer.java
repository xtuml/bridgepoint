package org.xtuml.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;
import org.xtuml.services.XOALGrammarAccess;
import org.xtuml.xOAL.Code;
import org.xtuml.xOAL.TypeConstant;
import org.xtuml.xOAL.TypeCreate;
import org.xtuml.xOAL.TypeDelete;
import org.xtuml.xOAL.TypeRelate;
import org.xtuml.xOAL.TypeStatementFor;
import org.xtuml.xOAL.TypeStatementIf;
import org.xtuml.xOAL.TypeStatementWhile;
import org.xtuml.xOAL.TypeValueVariable;
import org.xtuml.xOAL.XOALPackage;
import org.xtuml.xOAL.assignment;
import org.xtuml.xOAL.expr2;
import org.xtuml.xOAL.expression;
import org.xtuml.xOAL.product;
import org.xtuml.xOAL.select_statement;
import org.xtuml.xOAL.statement;
import org.xtuml.xOAL.sum;

@SuppressWarnings("restriction")
public class AbstractXOALSemanticSequencer extends AbstractSemanticSequencer {

	@Inject
	protected XOALGrammarAccess grammarAccess;
	
	@Inject
	protected ISemanticSequencerDiagnosticProvider diagnosticProvider;
	
	@Inject
	protected ITransientValueService transientValues;
	
	@Inject
	@GenericSequencer
	protected Provider<ISemanticSequencer> genericSequencerProvider;
	
	protected ISemanticSequencer genericSequencer;
	
	
	@Override
	public void init(ISemanticSequencer sequencer, ISemanticSequenceAcceptor sequenceAcceptor, Acceptor errorAcceptor) {
		super.init(sequencer, sequenceAcceptor, errorAcceptor);
		this.genericSequencer = genericSequencerProvider.get();
		this.genericSequencer.init(sequencer, sequenceAcceptor, errorAcceptor);
	}
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == XOALPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case XOALPackage.CODE:
				if(context == grammarAccess.getCodeRule()) {
					sequence_Code(context, (Code) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_CONSTANT:
				if(context == grammarAccess.getValueRule()) {
					sequence_value(context, (TypeConstant) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_CREATE:
				if(context == grammarAccess.getObject_statementRule()) {
					sequence_object_statement(context, (TypeCreate) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_DELETE:
				if(context == grammarAccess.getObject_statementRule()) {
					sequence_object_statement(context, (TypeDelete) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_RELATE:
				if(context == grammarAccess.getObject_statementRule()) {
					sequence_object_statement(context, (TypeRelate) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_STATEMENT_FOR:
				if(context == grammarAccess.getFlow_control_statementRule()) {
					sequence_flow_control_statement(context, (TypeStatementFor) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_STATEMENT_IF:
				if(context == grammarAccess.getFlow_control_statementRule()) {
					sequence_flow_control_statement(context, (TypeStatementIf) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_STATEMENT_WHILE:
				if(context == grammarAccess.getFlow_control_statementRule()) {
					sequence_flow_control_statement(context, (TypeStatementWhile) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.TYPE_VALUE_VARIABLE:
				if(context == grammarAccess.getValueRule()) {
					sequence_value(context, (TypeValueVariable) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.ASSIGNMENT:
				if(context == grammarAccess.getAssignmentRule()) {
					sequence_assignment(context, (assignment) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.EXPR2:
				if(context == grammarAccess.getExpr2Rule()) {
					sequence_expr2(context, (expr2) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.EXPRESSION:
				if(context == grammarAccess.getExpressionRule() ||
				   context == grammarAccess.getValueRule()) {
					sequence_expression(context, (expression) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getObject_statementRule() ||
				   context == grammarAccess.getSelect_statementRule()) {
					sequence_select_statement(context, (expression) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.PRODUCT:
				if(context == grammarAccess.getProductRule()) {
					sequence_product(context, (product) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.SELECT_STATEMENT:
				if(context == grammarAccess.getObject_statementRule() ||
				   context == grammarAccess.getSelect_statementRule()) {
					sequence_select_statement(context, (select_statement) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.STATEMENT:
				if(context == grammarAccess.getStatementRule()) {
					sequence_statement(context, (statement) semanticObject); 
					return; 
				}
				else break;
			case XOALPackage.SUM:
				if(context == grammarAccess.getSumRule()) {
					sequence_sum(context, (sum) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     statements+=statement*
	 *
	 * Features:
	 *    statements[0, *]
	 */
	protected void sequence_Code(EObject context, Code semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     e=expression
	 *
	 * Features:
	 *    e[1, 1]
	 */
	protected void sequence_assignment(EObject context, assignment semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, XOALPackage.Literals.ASSIGNMENT__E) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, XOALPackage.Literals.ASSIGNMENT__E));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getAssignmentAccess().getEExpressionParserRuleCall_3_0(), semanticObject.getE());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (n=name | s=sum)
	 *
	 * Features:
	 *    n[0, 1]
	 *         EXCLUDE_IF_SET s
	 *    s[0, 1]
	 *         EXCLUDE_IF_SET n
	 */
	protected void sequence_expr2(EObject context, expr2 semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (ne=expression | (ls=expr2 rs+=expr2*))
	 *
	 * Features:
	 *    ne[0, 1]
	 *         EXCLUDE_IF_SET ls
	 *         EXCLUDE_IF_SET rs
	 *    ls[0, 1]
	 *         MANDATORY_IF_SET rs
	 *         EXCLUDE_IF_SET ne
	 *    rs[0, *]
	 *         EXCLUDE_IF_UNSET ls
	 *         EXCLUDE_IF_SET ne
	 */
	protected void sequence_expression(EObject context, expression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (list=name substatements+=statement*)
	 *
	 * Features:
	 *    substatements[0, *]
	 *    list[1, 1]
	 */
	protected void sequence_flow_control_statement(EObject context, TypeStatementFor semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expr=expression substatements+=statement* (elifexpr+=expression substatements+=statement*)* substatements+=statement*)
	 *
	 * Features:
	 *    substatements[0, *]
	 *    expr[1, 1]
	 *    elifexpr[0, *]
	 *         MANDATORY_IF_SET substatements
	 */
	protected void sequence_flow_control_statement(EObject context, TypeStatementIf semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expr=expression substatements+=statement*)
	 *
	 * Features:
	 *    substatements[0, *]
	 *    expr[1, 1]
	 */
	protected void sequence_flow_control_statement(EObject context, TypeStatementWhile semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {TypeCreate}
	 *
	 * Features:
	 */
	protected void sequence_object_statement(EObject context, TypeCreate semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {TypeDelete}
	 *
	 * Features:
	 */
	protected void sequence_object_statement(EObject context, TypeDelete semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {TypeRelate}
	 *
	 * Features:
	 */
	protected void sequence_object_statement(EObject context, TypeRelate semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (lf=value rf+=value*)
	 *
	 * Features:
	 *    lf[1, 1]
	 *    rf[0, *]
	 */
	protected void sequence_product(EObject context, product semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (var=name (ne=expression | (ls=expr2 rs+=expr2*)))
	 *
	 * Features:
	 *    var[1, 1]
	 *    ne[0, 1]
	 *         EXCLUDE_IF_SET ls
	 *         EXCLUDE_IF_SET rs
	 *    ls[0, 1]
	 *         MANDATORY_IF_SET rs
	 *         EXCLUDE_IF_SET ne
	 *    rs[0, *]
	 *         EXCLUDE_IF_UNSET ls
	 *         EXCLUDE_IF_SET ne
	 */
	protected void sequence_select_statement(EObject context, expression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     var=name
	 *
	 * Features:
	 *    var[1, 1]
	 */
	protected void sequence_select_statement(EObject context, select_statement semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, XOALPackage.Literals.SELECT_STATEMENT__VAR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, XOALPackage.Literals.SELECT_STATEMENT__VAR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSelect_statementAccess().getVarNameParserRuleCall_2_0(), semanticObject.getVar());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (st1=assignment | st2=object_statement | st3=flow_control_statement)
	 *
	 * Features:
	 *    st1[0, 1]
	 *         EXCLUDE_IF_SET st2
	 *         EXCLUDE_IF_SET st3
	 *    st2[0, 1]
	 *         EXCLUDE_IF_SET st1
	 *         EXCLUDE_IF_SET st3
	 *    st3[0, 1]
	 *         EXCLUDE_IF_SET st1
	 *         EXCLUDE_IF_SET st2
	 */
	protected void sequence_statement(EObject context, statement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (lt=product rt+=product*)
	 *
	 * Features:
	 *    lt[1, 1]
	 *    rt[0, *]
	 */
	protected void sequence_sum(EObject context, sum semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {TypeConstant}
	 *
	 * Features:
	 */
	protected void sequence_value(EObject context, TypeConstant semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {TypeValueVariable}
	 *
	 * Features:
	 */
	protected void sequence_value(EObject context, TypeValueVariable semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
