package org.xtuml.bp.ui.xtext.ui;

import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;

import com.google.inject.Singleton;

@Singleton
public class MASLAntlrTokenToAttributeIdMapper extends AbstractAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		//System.out.printf("name: %s, type: %d\n", tokenName, tokenType);
		if("RULE_COMMENT".equals(tokenName)) {
		    return MASLHighlightingConfiguration.COMMENT;
		}
		else if("RULE_STRINGLITERAL".equals(tokenName) ) {
		    return MASLHighlightingConfiguration.STRING;
                }
		else if( isLiteral(tokenName) ) {
		    return MASLHighlightingConfiguration.LITERAL;
                }
		else if( isKeyword(tokenName) ) {
		    return MASLHighlightingConfiguration.KEYWORD;
		}
		return MASLHighlightingConfiguration.DEFAULT;
	}

        private boolean isKeyword( String tokenName ) {
            if( "RULE_ARRAY".equals(tokenName) ||
                "RULE_ANONYMOUS".equals(tokenName) ||
                "RULE_ASSIGNER".equals(tokenName) ||
                "RULE_AT".equals(tokenName) ||
                "RULE_BAG".equals(tokenName) ||
                "RULE_BEGIN".equals(tokenName) ||
                "RULE_CANNOT_HAPPEN".equals(tokenName) ||
                "RULE_CANCEL".equals(tokenName) ||
                "RULE_CASE".equals(tokenName) ||
                "RULE_CONDITIONALLY".equals(tokenName) ||
                "RULE_CREATE".equals(tokenName) ||
                "RULE_CREATION".equals(tokenName) ||
                "RULE_CURRENT_STATE".equals(tokenName) ||
                "RULE_DECLARE".equals(tokenName) ||
                "RULE_DEFERRED".equals(tokenName) ||
                "RULE_DELAY".equals(tokenName) ||
                "RULE_DELETE".equals(tokenName) ||
                "RULE_DELTA".equals(tokenName) ||
                "RULE_DICTIONARY".equals(tokenName) ||
                "RULE_DIGITS".equals(tokenName) ||
                "RULE_DOMAIN".equals(tokenName) ||
                "RULE_ELSE".equals(tokenName) ||
                "RULE_ELSIF".equals(tokenName) ||
                "RULE_END".equals(tokenName) ||
                "RULE_ENUM".equals(tokenName) ||
                "RULE_ERASE".equals(tokenName) ||
                "RULE_EVENT".equals(tokenName) ||
                "RULE_EXCEPTION".equals(tokenName) ||
                "RULE_EXIT".equals(tokenName) ||
                "RULE_FIND".equals(tokenName) ||
                "RULE_FIND_ONE".equals(tokenName) ||
                "RULE_FIND_ONLY".equals(tokenName) ||
                "RULE_FOR".equals(tokenName) ||
                "RULE_FUNCTION".equals(tokenName) ||
                "RULE_GENERATE".equals(tokenName) ||
                "RULE_IDENTIF".equals(tokenName) ||
                "RULE_IF".equals(tokenName) ||
                "RULE_IGNORE".equals(tokenName) ||
                "RULE_IN".equals(tokenName) ||
                "RULE_INSTANCE".equals(tokenName) ||
                "RULE_IS_A".equals(tokenName) ||
                "RULE_IS".equals(tokenName) ||
                "RULE_LINK".equals(tokenName) ||
                "RULE_LOOP".equals(tokenName) ||
                "RULE_MANY".equals(tokenName) ||
                "RULE_NON_EXISTENT".equals(tokenName) ||
                "RULE_OBJECT".equals(tokenName) ||
                "RULE_OF".equals(tokenName) ||
                "RULE_ONE".equals(tokenName) ||
                "RULE_ORDERED_BY".equals(tokenName) ||
                "RULE_OTHERS".equals(tokenName) ||
                "RULE_OUT".equals(tokenName) ||
                "RULE_PRAGMA".equals(tokenName) ||
                "RULE_PREFERRED".equals(tokenName) ||
                "RULE_PRIVATE".equals(tokenName) ||
                "RULE_PROJECT".equals(tokenName) ||
                "RULE_PUBLIC".equals(tokenName) ||
                "RULE_RAISE".equals(tokenName) ||
                "RULE_RANGE".equals(tokenName) ||
                "RULE_READONLY".equals(tokenName) ||
                "RULE_REFERENTIAL".equals(tokenName) ||
                "RULE_RELATIONSHIP".equals(tokenName) ||
                "RULE_RETURN".equals(tokenName) ||
                "RULE_REVERSE".equals(tokenName) ||
                "RULE_REVERSE_ORDERED_BY".equals(tokenName) ||
                "RULE_SCHEDULE".equals(tokenName) ||
                "RULE_SEQUENCE".equals(tokenName) ||
                "RULE_SERVICE".equals(tokenName) ||
                "RULE_SET".equals(tokenName) ||
                "RULE_START".equals(tokenName) ||
                "RULE_STATE".equals(tokenName) ||
                "RULE_STRUCTURE".equals(tokenName) ||
                "RULE_TERMINAL".equals(tokenName) ||
                "RULE_TERMINATOR".equals(tokenName) ||
                "RULE_THEN".equals(tokenName) ||
                "RULE_TO".equals(tokenName) ||
                "RULE_TRANSITION".equals(tokenName) ||
                "RULE_TYPE".equals(tokenName) ||
                "RULE_UNCONDITIONALLY".equals(tokenName) ||
                "RULE_UNIQUE".equals(tokenName) ||
                "RULE_UNLINK".equals(tokenName) ||
                "RULE_USING".equals(tokenName) ||
                "RULE_WHEN".equals(tokenName) ||
                "RULE_WHILE".equals(tokenName) ||
                "RULE_WITH".equals(tokenName) ||
                "RULE_NULL".equals(tokenName) ) {
                return true;
            }
            else {
                return false;
            }
        }

        private boolean isLiteral( String tokenName ) {
            if( "RULE_INTEGERLITERAL".equals(tokenName) ||
                "RULE_REALLITERAL".equals(tokenName) ||
                "RULE_TIMESTAMPLITERAL".equals(tokenName) ||
                "RULE_DURATIONLITERAL".equals(tokenName) ||
                "RULE_TRUE".equals(tokenName) ||
                "RULE_FALSE".equals(tokenName) ||
                "RULE_FLUSH".equals(tokenName) ||
                "RULE_ENDL".equals(tokenName) ||
                "RULE_THIS".equals(tokenName) ||
                "RULE_CONSOLE".equals(tokenName) ||
                "RULE_LINE_NO".equals(tokenName) ||
                "RULE_FILE_NAME".equals(tokenName) ) {
                return true;
            }
            else {
                return false;
            }
        }
}
