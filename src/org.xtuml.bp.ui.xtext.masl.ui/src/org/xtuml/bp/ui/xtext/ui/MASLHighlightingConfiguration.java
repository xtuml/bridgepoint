package org.xtuml.bp.ui.xtext.ui;

import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import static org.eclipse.swt.SWT.*;
 

public class MASLHighlightingConfiguration implements IHighlightingConfiguration {

	// provide an id string for the highlighting calculator
	 public static final String KEYWORD = "keyword";
	 public static final String LITERAL = "literal";
	 public static final String STRING =  "string";
	 public static final String COMMENT = "comment";
	 public static final String DEFAULT = "default";

	 public static final String[] ALL_STRINGS = { KEYWORD, COMMENT, DEFAULT };

	 
	 // configure the acceptor providing the id, the description string
	 // that will appear in the preference page and the initial text style
	 public void configure(IHighlightingConfigurationAcceptor acceptor) {
		 addType( acceptor, KEYWORD, 165, 42, 42, BOLD );
		 addType( acceptor, LITERAL, 175, 0, 125, BOLD );
		 addType( acceptor, STRING, 0, 0, 255, NORMAL );
		 addType( acceptor, COMMENT, 63, 127, 95, NORMAL );
		 addType( acceptor, DEFAULT, 0, 0, 0, NORMAL );
	 }
	 
	 public void addType( IHighlightingConfigurationAcceptor acceptor, String s, int r, int g, int b, int style ) {
		 TextStyle textStyle = new TextStyle();
		 textStyle.setBackgroundColor(new RGB(255, 255, 255));
		 textStyle.setColor(new RGB(r, g, b));
		 textStyle.setStyle(style);
		 acceptor.acceptDefaultHighlighting(s, s, textStyle);
	 }

}
