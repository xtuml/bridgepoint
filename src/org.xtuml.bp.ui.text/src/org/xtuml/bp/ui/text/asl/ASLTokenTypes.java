package org.xtuml.bp.ui.text.asl;

// TODO - same as OAL.  Refactor to ActionLanguageTokenTypes or some such
public interface ASLTokenTypes {
	public static final int TOKEN_TYPE_other = -1;
	public static final int TOKEN_TYPE_single_line_comment = 0;
	public static final int TOKEN_TYPE_multi_line_comment = 1;
	public static final int TOKEN_TYPE_string = 2;
	public static final int TOKEN_TYPE_keyword = 3;
}
