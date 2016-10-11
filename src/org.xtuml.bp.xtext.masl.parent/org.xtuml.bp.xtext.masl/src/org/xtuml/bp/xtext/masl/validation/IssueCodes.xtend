package org.xtuml.bp.xtext.masl.validation

/**
 * Codes to identify errors and warnings.
 */
interface IssueCodes {
	
	val PREFIX = IssueCodes.name + '.'
	
	val ILLEGAL_EMPTY_LIST = PREFIX + 'illegalEmptyList'
	val INCONSISTENT_RELATIONSHIP_ENDS = PREFIX + 'inconsistentRelationshipEnds' 
	val INCONSISTENT_RELATIONSHIP_NAVIGATION = PREFIX + 'inconsistentRelationshipNavigation' 
	val DUPLICATE_NAME = PREFIX + 'duplicateName' 
	val CYCLIC_INHERITANCE = PREFIX + 'cyclicInheritance'
	val MISSING_DEFINITION = PREFIX + 'missingDefinition' 
	val MISSING_DECLARATION = PREFIX + 'missingDeclaration' 
	val WRONG_TYPE = PREFIX + 'wrongType'
	val INVALID_OPERATION_CALL = PREFIX + 'invalidOperationCall'
	val INVALID_FEATURE_CALL = PREFIX + 'invalidFeatureCall'
}