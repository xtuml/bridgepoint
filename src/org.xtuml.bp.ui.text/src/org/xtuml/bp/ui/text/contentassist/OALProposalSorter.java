package org.xtuml.bp.ui.text.contentassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalSorter;
import org.xtuml.bp.core.Proposaltypes_c;

public class OALProposalSorter implements ICompletionProposalSorter {

    private static final int[] ORDERING = {
        Proposaltypes_c.Attribute,
        Proposaltypes_c.Operation,
        Proposaltypes_c.Association,
        Proposaltypes_c.Variable,
        Proposaltypes_c.EE,
        Proposaltypes_c.Class,
        Proposaltypes_c.Keyword
    };

    @Override
    public int compare( ICompletionProposal p1, ICompletionProposal p2 ) {
        if ( p1 instanceof OALCompletionProposal && p2 instanceof OALCompletionProposal ) {
            return compare( (OALCompletionProposal)p1, (OALCompletionProposal)p2 );
        }
        return 0;
    }

    public int compare( OALCompletionProposal p1, OALCompletionProposal p2 ) {
        // if the types are the same
        if ( p1.getType() == p2.getType() ) {
            switch ( p1.getType() ) {
                // the following are sorted alphanumerically by display string
                case Proposaltypes_c.Attribute:
                case Proposaltypes_c.Operation:
                case Proposaltypes_c.Variable:
                case Proposaltypes_c.EE:
                case Proposaltypes_c.Class:
                case Proposaltypes_c.Keyword:
                    return p1.getDisplayString().compareTo( p2.getDisplayString() );
                // sorted by rel num
                case Proposaltypes_c.Association:
                    int num1 = 0, num2 = 0; 
                    Pattern pattern = Pattern.compile( "\\[R([1-9][0-9]*)" );
                    Matcher match = pattern.matcher( p1.getDisplayString() );
                    if ( match.find() ) {
                        num1 = Integer.parseInt( match.group(1) );
                    }
                    match = pattern.matcher( p2.getDisplayString() );
                    if ( match.find() ) {
                        num2 = Integer.parseInt( match.group(1) );
                    }
                    return Integer.compare( num1, num2 );
                default:
                    return 0;
            }
        }
        else {
            return Integer.compare( indexOf( ORDERING, p1.getType() ), indexOf( ORDERING, p2.getType() ) );
        }
    }
    
    private static int indexOf( int[] array, int value ) {
        for ( int i = 0; i < array.length; i++ ) {
            if ( array[i] == value ) return i;
        }
        return -1;
    }

}
