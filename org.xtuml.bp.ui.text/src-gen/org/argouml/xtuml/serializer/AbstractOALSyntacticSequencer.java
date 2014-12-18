package org.argouml.xtuml.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.argouml.xtuml.services.OALGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AlternativeAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("restriction")
public class AbstractOALSyntacticSequencer extends AbstractSyntacticSequencer {

	protected OALGrammarAccess grammarAccess;
	protected AbstractElementAlias match_assignment_AssignKeyword_0_q;
	protected AbstractElementAlias match_expr2_EmptyKeyword_0_0_0_or_Not_emptyKeyword_0_0_1;
	protected AbstractElementAlias match_expression_EqualsSignEqualsSignKeyword_1_1_0_0_or_GreaterThanSignEqualsSignKeyword_1_1_0_4_or_GreaterThanSignKeyword_1_1_0_3_or_LessThanSignEqualsSignKeyword_1_1_0_5_or_LessThanSignGreaterThanSignKeyword_1_1_0_1_or_LessThanSignKeyword_1_1_0_2;
	protected AbstractElementAlias match_flow_control_statement_ElseKeyword_0_6_0_q;
	protected AbstractElementAlias match_object_statement_Relate_statementParserRuleCall_2_0_or_Unrelate_statementParserRuleCall_3_0;
	protected AbstractElementAlias match_product_AsteriskKeyword_1_0_0_or_PercentSignKeyword_1_0_2_or_SolidusKeyword_1_0_1;
	protected AbstractElementAlias match_select_statement_AnyKeyword_1_0_or_ManyKeyword_1_1_or_OneKeyword_1_2;
	protected AbstractElementAlias match_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3_WhereKeyword_3_0_4_0___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p_WhereKeyword_3_1_4_0__;
	protected AbstractElementAlias match_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p__;
	protected AbstractElementAlias match_sum_HyphenMinusKeyword_1_0_1_or_PlusSignKeyword_1_0_0;
	protected AbstractElementAlias match_value_FalseKeyword_3_0_or_INTTerminalRuleCall_4_0_or_TrueKeyword_2_0;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (OALGrammarAccess) access;
		match_assignment_AssignKeyword_0_q = new TokenAlias(true, false, grammarAccess.getAssignmentAccess().getAssignKeyword_0());
		match_expr2_EmptyKeyword_0_0_0_or_Not_emptyKeyword_0_0_1 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getExpr2Access().getEmptyKeyword_0_0_0()), new TokenAlias(false, false, grammarAccess.getExpr2Access().getNot_emptyKeyword_0_0_1()));
		match_expression_EqualsSignEqualsSignKeyword_1_1_0_0_or_GreaterThanSignEqualsSignKeyword_1_1_0_4_or_GreaterThanSignKeyword_1_1_0_3_or_LessThanSignEqualsSignKeyword_1_1_0_5_or_LessThanSignGreaterThanSignKeyword_1_1_0_1_or_LessThanSignKeyword_1_1_0_2 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getExpressionAccess().getEqualsSignEqualsSignKeyword_1_1_0_0()), new TokenAlias(false, false, grammarAccess.getExpressionAccess().getGreaterThanSignEqualsSignKeyword_1_1_0_4()), new TokenAlias(false, false, grammarAccess.getExpressionAccess().getGreaterThanSignKeyword_1_1_0_3()), new TokenAlias(false, false, grammarAccess.getExpressionAccess().getLessThanSignEqualsSignKeyword_1_1_0_5()), new TokenAlias(false, false, grammarAccess.getExpressionAccess().getLessThanSignGreaterThanSignKeyword_1_1_0_1()), new TokenAlias(false, false, grammarAccess.getExpressionAccess().getLessThanSignKeyword_1_1_0_2()));
		match_flow_control_statement_ElseKeyword_0_6_0_q = new TokenAlias(true, false, grammarAccess.getFlow_control_statementAccess().getElseKeyword_0_6_0());
		match_object_statement_Relate_statementParserRuleCall_2_0_or_Unrelate_statementParserRuleCall_3_0 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getObject_statementAccess().getRelate_statementParserRuleCall_2_0()), new TokenAlias(false, false, grammarAccess.getObject_statementAccess().getUnrelate_statementParserRuleCall_3_0()));
		match_product_AsteriskKeyword_1_0_0_or_PercentSignKeyword_1_0_2_or_SolidusKeyword_1_0_1 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getProductAccess().getAsteriskKeyword_1_0_0()), new TokenAlias(false, false, grammarAccess.getProductAccess().getPercentSignKeyword_1_0_2()), new TokenAlias(false, false, grammarAccess.getProductAccess().getSolidusKeyword_1_0_1()));
		match_select_statement_AnyKeyword_1_0_or_ManyKeyword_1_1_or_OneKeyword_1_2 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getAnyKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getManyKeyword_1_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getOneKeyword_1_2()));
		match_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3_WhereKeyword_3_0_4_0___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p_WhereKeyword_3_1_4_0__ = new AlternativeAlias(false, false, new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getFromKeyword_3_0_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getInstancesKeyword_3_0_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getOfKeyword_3_0_2()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getClass_nameParserRuleCall_3_0_3()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getWhereKeyword_3_0_4_0())), new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getRelatedKeyword_3_1_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getByKeyword_3_1_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getObject_referenceParserRuleCall_3_1_2()), new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getHyphenMinusGreaterThanSignKeyword_3_1_3_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getClass_nameParserRuleCall_3_1_3_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getLeftSquareBracketKeyword_3_1_3_2()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getRelationParserRuleCall_3_1_3_3()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getRightSquareBracketKeyword_3_1_3_4())), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getWhereKeyword_3_1_4_0())));
		match_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p__ = new AlternativeAlias(false, false, new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getFromKeyword_3_0_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getInstancesKeyword_3_0_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getOfKeyword_3_0_2()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getClass_nameParserRuleCall_3_0_3())), new GroupAlias(false, false, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getRelatedKeyword_3_1_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getByKeyword_3_1_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getObject_referenceParserRuleCall_3_1_2()), new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getHyphenMinusGreaterThanSignKeyword_3_1_3_0()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getClass_nameParserRuleCall_3_1_3_1()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getLeftSquareBracketKeyword_3_1_3_2()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getRelationParserRuleCall_3_1_3_3()), new TokenAlias(false, false, grammarAccess.getSelect_statementAccess().getRightSquareBracketKeyword_3_1_3_4()))));
		match_sum_HyphenMinusKeyword_1_0_1_or_PlusSignKeyword_1_0_0 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getSumAccess().getHyphenMinusKeyword_1_0_1()), new TokenAlias(false, false, grammarAccess.getSumAccess().getPlusSignKeyword_1_0_0()));
		match_value_FalseKeyword_3_0_or_INTTerminalRuleCall_4_0_or_TrueKeyword_2_0 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getValueAccess().getFalseKeyword_3_0()), new TokenAlias(false, false, grammarAccess.getValueAccess().getINTTerminalRuleCall_4_0()), new TokenAlias(false, false, grammarAccess.getValueAccess().getTrueKeyword_2_0()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if(ruleCall.getRule() == grammarAccess.getINTRule())
			return getINTToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getClass_nameRule())
			return getclass_nameToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getCreate_statementRule())
			return getcreate_statementToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getDelete_statementRule())
			return getdelete_statementToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getLvalueRule())
			return getlvalueToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getObject_referenceRule())
			return getobject_referenceToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getRelate_statementRule())
			return getrelate_statementToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getRelationRule())
			return getrelationToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getUnrelate_statementRule())
			return getunrelate_statementToken(semanticObject, ruleCall, node);
		else if(ruleCall.getRule() == grammarAccess.getVariableRule())
			return getvariableToken(semanticObject, ruleCall, node);
		return "";
	}
	
	protected String getINTToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "";
	}
	protected String getclass_nameToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "";
	}
	protected String getcreate_statementToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "createobjectof";
	}
	protected String getdelete_statementToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "deleteobjectinstanceself";
	}
	protected String getlvalueToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "";
	}
	protected String getobject_referenceToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "self";
	}
	protected String getrelate_statementToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "relateselftoacrossR";
	}
	protected String getrelationToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "R";
	}
	protected String getunrelate_statementToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "unrelateselffromacrossR";
	}
	protected String getvariableToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_assignment_AssignKeyword_0_q.equals(syntax))
				emit_assignment_AssignKeyword_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_expr2_EmptyKeyword_0_0_0_or_Not_emptyKeyword_0_0_1.equals(syntax))
				emit_expr2_EmptyKeyword_0_0_0_or_Not_emptyKeyword_0_0_1(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_expression_EqualsSignEqualsSignKeyword_1_1_0_0_or_GreaterThanSignEqualsSignKeyword_1_1_0_4_or_GreaterThanSignKeyword_1_1_0_3_or_LessThanSignEqualsSignKeyword_1_1_0_5_or_LessThanSignGreaterThanSignKeyword_1_1_0_1_or_LessThanSignKeyword_1_1_0_2.equals(syntax))
				emit_expression_EqualsSignEqualsSignKeyword_1_1_0_0_or_GreaterThanSignEqualsSignKeyword_1_1_0_4_or_GreaterThanSignKeyword_1_1_0_3_or_LessThanSignEqualsSignKeyword_1_1_0_5_or_LessThanSignGreaterThanSignKeyword_1_1_0_1_or_LessThanSignKeyword_1_1_0_2(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_flow_control_statement_ElseKeyword_0_6_0_q.equals(syntax))
				emit_flow_control_statement_ElseKeyword_0_6_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_object_statement_Relate_statementParserRuleCall_2_0_or_Unrelate_statementParserRuleCall_3_0.equals(syntax))
				emit_object_statement_Relate_statementParserRuleCall_2_0_or_Unrelate_statementParserRuleCall_3_0(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_product_AsteriskKeyword_1_0_0_or_PercentSignKeyword_1_0_2_or_SolidusKeyword_1_0_1.equals(syntax))
				emit_product_AsteriskKeyword_1_0_0_or_PercentSignKeyword_1_0_2_or_SolidusKeyword_1_0_1(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_select_statement_AnyKeyword_1_0_or_ManyKeyword_1_1_or_OneKeyword_1_2.equals(syntax))
				emit_select_statement_AnyKeyword_1_0_or_ManyKeyword_1_1_or_OneKeyword_1_2(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3_WhereKeyword_3_0_4_0___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p_WhereKeyword_3_1_4_0__.equals(syntax))
				emit_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3_WhereKeyword_3_0_4_0___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p_WhereKeyword_3_1_4_0__(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p__.equals(syntax))
				emit_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p__(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_sum_HyphenMinusKeyword_1_0_1_or_PlusSignKeyword_1_0_0.equals(syntax))
				emit_sum_HyphenMinusKeyword_1_0_1_or_PlusSignKeyword_1_0_0(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_value_FalseKeyword_3_0_or_INTTerminalRuleCall_4_0_or_TrueKeyword_2_0.equals(syntax))
				emit_value_FalseKeyword_3_0_or_INTTerminalRuleCall_4_0_or_TrueKeyword_2_0(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     'assign'?
	 */
	protected void emit_assignment_AssignKeyword_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     'not_empty' | 'empty'
	 */
	protected void emit_expr2_EmptyKeyword_0_0_0_or_Not_emptyKeyword_0_0_1(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (
	     '<>' | 
	     '>' | 
	     '<' | 
	     '<=' | 
	     '==' | 
	     '>='
	 )
	 */
	protected void emit_expression_EqualsSignEqualsSignKeyword_1_1_0_0_or_GreaterThanSignEqualsSignKeyword_1_1_0_4_or_GreaterThanSignKeyword_1_1_0_3_or_LessThanSignEqualsSignKeyword_1_1_0_5_or_LessThanSignGreaterThanSignKeyword_1_1_0_1_or_LessThanSignKeyword_1_1_0_2(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     'else'?
	 */
	protected void emit_flow_control_statement_ElseKeyword_0_6_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     relate_statement | unrelate_statement
	 */
	protected void emit_object_statement_Relate_statementParserRuleCall_2_0_or_Unrelate_statementParserRuleCall_3_0(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '%' | '/' | '*'
	 */
	protected void emit_product_AsteriskKeyword_1_0_0_or_PercentSignKeyword_1_0_2_or_SolidusKeyword_1_0_1(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     'any' | 'one' | 'many'
	 */
	protected void emit_select_statement_AnyKeyword_1_0_or_ManyKeyword_1_1_or_OneKeyword_1_2(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('related' 'by' object_reference ('->' class_name '[' relation ']')+ 'where') | ('from' 'instances' 'of' class_name 'where')
	 */
	protected void emit_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3_WhereKeyword_3_0_4_0___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p_WhereKeyword_3_1_4_0__(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('related' 'by' object_reference ('->' class_name '[' relation ']')+) | ('from' 'instances' 'of' class_name)
	 */
	protected void emit_select_statement___FromKeyword_3_0_0_InstancesKeyword_3_0_1_OfKeyword_3_0_2_Class_nameParserRuleCall_3_0_3___or___RelatedKeyword_3_1_0_ByKeyword_3_1_1_Object_referenceParserRuleCall_3_1_2___HyphenMinusGreaterThanSignKeyword_3_1_3_0_Class_nameParserRuleCall_3_1_3_1_LeftSquareBracketKeyword_3_1_3_2_RelationParserRuleCall_3_1_3_3_RightSquareBracketKeyword_3_1_3_4__p__(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     '-' | '+'
	 */
	protected void emit_sum_HyphenMinusKeyword_1_0_1_or_PlusSignKeyword_1_0_0(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     'false' | INT | 'true'
	 */
	protected void emit_value_FalseKeyword_3_0_or_INTTerminalRuleCall_4_0_or_TrueKeyword_2_0(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
