package org.xtuml.bp.als.oal;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamRecognitionException;

import org.xtuml.bp.core.Block_c;
import org.xtuml.bp.core.Body_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ElseStmt_c;
import org.xtuml.bp.core.ElseifStmt_c;
import org.xtuml.bp.core.ForStmt_c;
import org.xtuml.bp.core.IfStmt_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Statement_c;
import org.xtuml.bp.core.WhileStmt_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.DocumentUtil;

import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;

public class PartialParseRunnable extends ParseRunnable {

    public PartialParseRunnable(NonRootModelElement modelElement, String document, int contentAssistLine,
            int contentAssistCol) {
        super(modelElement, document, contentAssistLine, contentAssistCol);
    }

    public void run() {
        // if the model element whose activity text is to be parsed has been
        // deleted from its model, or this parse is for an editor which has
        // been disposed, then no parse should be performed
        if (m_modelElement == null || m_modelElement.isOrphaned())
            return;

        Statement_c mostRecentSmt = null;
        String parseText = m_document;
        int startLine = 0;
        int startCol = 0;

        if ( Pref_c.Getboolean( BridgePointPreferencesStore.CONTENT_ASSIST_ENABLE_PARTIAL_PARSING ) ) {
        
        // select the most recent parsed statement
        Statement_c[] smts = Statement_c.StatementInstances( m_modelElement.getModelRoot(), new ClassQueryInterface_c() {
            @Override
            public boolean evaluate(Object candidate) {
                return ((Statement_c)candidate).getLinenumber() < m_contentAssistLine ||
                       ( ((Statement_c)candidate).getLinenumber() == m_contentAssistLine && ((Statement_c)candidate).getStartposition() < m_contentAssistCol );
            }
        });
        // sort by line and column
        Arrays.sort( smts, new Comparator<Statement_c>() {
            @Override
            public int compare(Statement_c o1, Statement_c o2) {
                if ( o1.getLinenumber() == o2.getLinenumber() ) {
                    return ( o1.getStartposition() - o2.getStartposition() );
                }
                else return ( o1.getLinenumber() - o2.getLinenumber() );
            }
        });
        mostRecentSmt = smts.length > 0 ? smts[smts.length-1] : null;
        if ( null != mostRecentSmt ) {
        	// get all text between the end of the last statement and the line of content assist request
            parseText = m_document.substring( m_document.indexOf( ';', lineAndColumnToPosition( mostRecentSmt.getLinenumber(), mostRecentSmt.getStartposition() ) ) + 1,
                                              lineAndColumnToPosition( m_contentAssistLine, m_contentAssistCol) );
            // if the parse test spans a block we need to get the outer statement
            // sub block
            Pattern endBlockPattern = Pattern.compile( "\\A\\s*end\\s+(if|for|while);" ); // match any "end ...;" at the beginning of the input
            Matcher endBlockMatcher = endBlockPattern.matcher( parseText );
            while ( endBlockMatcher.find() ) {
                Statement_c upperSmt = Statement_c.getOneACT_SMTOnR603(IfStmt_c.getManyACT_IFsOnR607(Block_c.getOneACT_BLKOnR602(mostRecentSmt)));
                if ( null == upperSmt ) upperSmt = Statement_c.getOneACT_SMTOnR603(IfStmt_c.getManyACT_IFsOnR682(ElseifStmt_c.getOneACT_ELOnR658(Block_c.getOneACT_BLKOnR602(mostRecentSmt))));
                if ( null == upperSmt ) upperSmt = Statement_c.getOneACT_SMTOnR603(IfStmt_c.getManyACT_IFsOnR683(ElseStmt_c.getOneACT_EOnR606((Block_c.getOneACT_BLKOnR602(mostRecentSmt)))));
                if ( null == upperSmt ) upperSmt = Statement_c.getOneACT_SMTOnR603(WhileStmt_c.getManyACT_WHLsOnR608(Block_c.getOneACT_BLKOnR602(mostRecentSmt)));
                if ( null == upperSmt ) upperSmt = Statement_c.getOneACT_SMTOnR603(ForStmt_c.getManyACT_FORsOnR605(Block_c.getOneACT_BLKOnR602(mostRecentSmt)));
                if ( null != upperSmt ) {
                    mostRecentSmt = upperSmt;
                    parseText = parseText.substring( parseText.indexOf( ';' ) + 1 );
                }
                endBlockMatcher = endBlockPattern.matcher( parseText );
            }
            // set the start line and column
            startLine = positionToLine( m_document.indexOf(parseText) );
            startCol = positionToCol( m_document.indexOf(parseText) );
            // replace every non whitespace character before the start of the parse text
            // with a space. This preserves the line/col information during parse but allows
            // the parser to skip the body up to the interesting part
            String prefixText = m_document.substring( 0, m_document.indexOf( parseText ) ).replaceAll("\\S", " ");
            parseText = prefixText + parseText;
        }

        }

        Ooaofooa modelRoot = (Ooaofooa) m_modelElement.getModelRoot();
        OalLexer lexer = new OalLexer(new StringReader(parseText));
        TextParser parser = new TextParser(modelRoot, lexer, m_contentAssistLine, m_contentAssistCol);
        try {
            if ( null == mostRecentSmt ) {
                // Parse the input expression
                parser.action(m_modelElement, false);
            }
            else {
                // get the upper ID (action ID for root block, statement ID for inner block)
                Block_c blk = Block_c.getOneACT_BLKOnR602(mostRecentSmt);
                Body_c act = Body_c.getOneACT_ACTOnR612(blk);
                if ( null == act ) act = Body_c.getOneACT_ACTOnR601(blk);
                parser.m_oal_context = new Oal_validate( parser.getContainer( m_modelElement ), ( m_contentAssistLine != 0 && m_contentAssistCol != 0 ) );
                parser.m_oal_context.m_act_id = act.getAction_id();
                UUID upperID = parser.m_oal_context.m_act_id;
                boolean isRoot = true;
                Statement_c if_smt = Statement_c.getOneACT_SMTOnR603(IfStmt_c.getOneACT_IFOnR607(blk));
                Statement_c elif_smt = Statement_c.getOneACT_SMTOnR603(ElseifStmt_c.getOneACT_ELOnR658(blk));
                Statement_c else_smt = Statement_c.getOneACT_SMTOnR603(ElseStmt_c.getOneACT_EOnR606(blk));
                Statement_c for_smt = Statement_c.getOneACT_SMTOnR603(ForStmt_c.getOneACT_FOROnR605(blk));
                Statement_c while_smt = Statement_c.getOneACT_SMTOnR603(WhileStmt_c.getOneACT_WHLOnR608(blk));
                if ( null != if_smt ) {
                    upperID = if_smt.getStatement_id();
                    isRoot = false;
                }
                else if ( null != elif_smt ) {
                    upperID = elif_smt.getStatement_id();
                    isRoot = false;
                }
                else if ( null != else_smt ) {
                    upperID = else_smt.getStatement_id();
                    isRoot = false;
                }
                else if ( null != for_smt ) {
                    upperID = for_smt.getStatement_id();
                    isRoot = false;
                }
                else if ( null != while_smt ) {
                    upperID = while_smt.getStatement_id();
                    isRoot = false;
                }
                // clear the parse data from the starting statement
                blk.Deletestatementsafter( startCol, startLine );
                // parse a block starting with the most recent statement
                parser.partial_block( upperID, isRoot );
            }
        } catch (TokenStreamException e) {
            Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
            if (e instanceof TokenStreamRecognitionException) {
                TokenStreamRecognitionException tsre = (TokenStreamRecognitionException) e;
                parser.reportError(tsre.recog);
            } else {
                String errorMsg = "Internal parser error.  Token stream exception in parser.  OAL in this action home caused an exception in the parser."; //$NON-NLS-1$
                CorePlugin.logError(errorMsg, e);
            }
        } catch (RecognitionException e) {
            Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
            parser.reportError(e);
        } catch (InterruptedException e) {
            // The parse was canceled, we don't need to report an
            // error in this situation, but we do need to note that
            // the parse did not complete (parseCompleted is false).
            Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);
        } catch (Throwable t) {
            String errorMsg = "Internal parser error.  Parsing thread interrupted pre-maturely.  OAL in this action home caused an exception in the parser."; //$NON-NLS-1$
            Block_c.Clearcurrentscope(modelRoot, parser.m_oal_context.m_act_id);

            // This throwable catches all the un-checked exceptions that occur
            // in the thread, and logs them
            // appropriately.
            CorePlugin.logError(errorMsg, t); //$NON-NLS-1$    
        }
        
    }

    private int lineAndColumnToPosition( int line, int column ) {
        IDocument document = new Document( m_document );
        return DocumentUtil.lineAndColumnToPosition( line, column, document );
    }

    private int positionToLine( int position ) {
        IDocument document = new Document( m_document );
        return DocumentUtil.positionToLine( position, document );
    }
    
    private int positionToCol( int position ) {
        IDocument document = new Document( m_document );
        return DocumentUtil.positionToCol( position, document );
    }

}
