package antlr;

import java.io.*;
import antlr.Token;
import antlr.collections.AST;
import antlr.CommonAST;
import antlr.ANTLRTokenTypes;
import java.lang.Character;

public class PTPopulatorAST extends antlr.CommonAST implements ANTLRTokenTypes {
    String v;
	static Writer sqlWriter;
    static String glob_ruleName = "Root";
    static String glob_rule_nodeId = "0";
    static boolean emitRule = false;

    public PTPopulatorAST() {
        super();
    }

    public PTPopulatorAST(Token tok) {
        super(tok);
        v = tok.getText();
    }

    public String toString() {
  		String l = super.toString();
  		// l may contain "[junk,<token name>]"
  		// or it may just be the token name
  		//  we want to extract <token name>
  		String [] s1 = l.split( "<" );
  		if ( s1.length == 1 )
  		{
  			// there was no <, it's just the token name
  			return l;
  		}
  		else
  		{
	  		String [] s2 = s1[1].split( ">" );
  			return s2[0];
  		}
    }

    private final String removeQuotes(String in) {
        String out = in;
        if (out.startsWith("\"")) {
            out = out.substring(1);
        }
        if (out.endsWith("\"")) {
            out = out.substring(0, out.length()-1);
        }
        if (out.startsWith("'")) {
            out = out.substring(1);
        }
        if (out.endsWith("'")) {
            out = out.substring(0, out.length()-1);
        }
        Character nl = new Character('\n');
        Character cr = new Character('\r');
        String old_nl = "" + nl + cr;
        String new_nl = " ";
        out = out.replaceAll(old_nl, new_nl);
        old_nl = "" + nl;
        out = out.replaceAll(old_nl, new_nl);
        old_nl = "" + cr;
        out = out.replaceAll(old_nl, new_nl);
        String old_tic = "" + '\\' + '\\' + '\'';
        String new_tic = "" + '\'' + '\''; 
        out = out.replaceAll(old_tic, new_tic);
        return out;
    }

    private static final String ticced(String in) {
        String out = "'" + in + "'";
        return out;
    }

    private static final String ticced(int in) {
        String out = "'" + in + "'";
        return out;
    }

    public final String getNodeName(int nodeType) {
        String name = "";
        if (nodeType == Grammar) {name = "Grammar";}
        if (nodeType == Rule) {name = "Rule";}
        if (nodeType == Id) {name = "Id";}
        if (nodeType == Block) {name = "Block";}
        if (nodeType == Alternative) {name = "Alternative";}
        if (nodeType == Element) {name = "Element";}
        if (nodeType == Range) {name = "Range";}
        if (nodeType == Terminal) {name = "Terminal";}
        if (nodeType == Ebnf) {name = "Ebnf";}
        return name;
    }

    private void emit_node(String nodeId,
                                  String parent_nodeId,
                                  int order,
                                  String nodeType)
        throws IOException {
        String t;
        String containing_nodeId = glob_rule_nodeId;
        String next_node_same_level = parent_nodeId + "_" + (order + 1);
        t = "\nINSERT INTO N    VALUES (" +
            "" + ticced(nodeId) +
            ", " + ticced(parent_nodeId) +
            ", " + ticced(containing_nodeId) +
            ", " + ticced(next_node_same_level) +
            ", " + ticced(nodeType) +
            ");";
        sqlWriter.write(t);
    }                                  

    private void emit_nonleafnode(String p_nodeId,
                                  String parent_nodeId,
                                  int order,
                                  String p_nodetype)
        throws IOException {
        String t;
        String first_child_nodeid = p_nodeId + "_" + 1;
        emit_node(p_nodeId, parent_nodeId, order, "Nonleaf");
        t = "\nINSERT INTO NLN  VALUES (" +
            "" + ticced(p_nodeId) +
            ", " + ticced(first_child_nodeid) +
            ", " + ticced(p_nodetype) +
            ");";
        sqlWriter.write(t);
    }                                  

    private void emit_leafnode(String np_nodeId,
                                  String parent_nodeId,
                                  int order,
                                  String np_nodetype)
        throws IOException {
        String t;
        emit_node(np_nodeId, parent_nodeId, order, "Leaf");
        t = "\nINSERT INTO LN   VALUES (" +
            "" + ticced(np_nodeId) +
            ", " + ticced(np_nodetype) +
            ");";
        sqlWriter.write(t);
    }                                  

    private void emit_root(String nodeId,
                                  String parent_nodeId,
                                  int order)
        throws IOException {
        String t;
        emit_nonleafnode(nodeId, parent_nodeId, order, "Root");
        t = "\nINSERT INTO ROOT VALUES (" +
            "" + ticced(nodeId) +
            ");";
        sqlWriter.write(t);
    }                                  

    private void emit_rule(String rule_nodeId,
                                  String rule_name,
                                  String parent_nodeId,
                                  int order)
        throws IOException {
        String t;
        emit_nonleafnode(rule_nodeId, parent_nodeId, order, "Rule");
        t = "\nINSERT INTO R    VALUES (" +
            "" + ticced(rule_nodeId) +
            ", " + ticced(rule_name) +
            ");";
		sqlWriter.write(t);
    }                                  

    private void emit_ebnf(String ebnf_nodeId,
                                  String decoration,
                                  String parent_nodeId,
                                  int order)
        throws IOException {
        String t;
        emit_nonleafnode(ebnf_nodeId, parent_nodeId, order, "Ebnf");
        t = "\nINSERT INTO EBNF VALUES (" +
            "" + ticced(ebnf_nodeId) +
            ", " + ticced(decoration) +
            ");";
		sqlWriter.write(t);
    }                                  

    private void emit_rref(String rref_nodeId,
                                  String rule_name,
                                  String parent_nodeId,
                                  int order)
        throws IOException {
        String t;
        emit_leafnode(rref_nodeId, parent_nodeId, order, "Rref");
        t = "\nINSERT INTO RR   VALUES (" +
            "" + ticced(rref_nodeId) +
            ", " + ticced(rule_name) +
            ");";
		sqlWriter.write(t);
    }

    private void emit_term(String term_nodeId,
                                  String token_name,
                                  String value,
                                  String parent_nodeId,
                                  int order)
        throws IOException {
        String t;
        emit_leafnode(term_nodeId, parent_nodeId, order, "Term");
        t = "\nINSERT INTO T    VALUES (" +
            "" + ticced(term_nodeId) +
            ", " + ticced(token_name) +
            ", " + ticced(value) +
            ");";
		sqlWriter.write(t);
    }


    public void sqlSerialize() throws IOException {
        int order = 1;
        String parentId = "";
        String nodeId = "";
        glob_ruleName = "Root";
        glob_rule_nodeId  = "0";
        sqlSerializeData(order, parentId);
    }

    public void sqlSerializeData(int order,
                                  String parentId)
        throws IOException {
        String nodeId = "";
        // print out this node and all siblings
        for (PTPopulatorAST node = (PTPopulatorAST)this;
             node != null;
             node = (PTPopulatorAST)node.getNextSibling()) {
            if (parentId.length() == 0) {
                nodeId = "" + order;
            }
            else {
                nodeId = parentId + "_" + order;
            }
            if (node.getType() == Rule) {
                glob_ruleName = node.getText();
                glob_rule_nodeId = nodeId;
            }
            if (node.getFirstChild() == null) {
                // print guts (class name, attributes)
                node.sqlSerializeNode(order, parentId, nodeId, false);
            }
            else {
                node.sqlSerializeNode(order, parentId, nodeId, true);
                // print children
                ((PTPopulatorAST)node.getFirstChild()).sqlSerializeData(1, nodeId);
            }
            order++;
        }
    }

    public void sqlSerializeNode(int order,
                                 String parentId,
                                 String nodeId, 
                                 boolean useGlobalRuleName)
        throws IOException 
    {
        String txt = removeQuotes(getText());
        String parent_nodeId = parentId;
        if (getType() == Grammar) {
            emit_root(nodeId, parent_nodeId, order);
        }
        else if (getType() == Rule) {
        	if ( useGlobalRuleName ) {
				emit_rule(nodeId, glob_ruleName, parent_nodeId, order);
        	}
        	else {
				String tname = getNodeName(getType());
				if (tname.length() == 0) {
					tname = toString();
				}
				emit_rule(nodeId, tname, parent_nodeId, order);
        	}
        }
        else if (getType() == RULE_REF) {
            emit_rref(nodeId, txt, parent_nodeId, order);
        }
        else if (getType() == Ebnf) {
            emit_ebnf(nodeId, txt, parent_nodeId, order);
        }
        else {
			String tname = getNodeName(getType());
			if (tname.length() == 0) {
				tname = toString();
			}
            emit_term(nodeId, tname, txt, parent_nodeId, order);
        }
    }
}
