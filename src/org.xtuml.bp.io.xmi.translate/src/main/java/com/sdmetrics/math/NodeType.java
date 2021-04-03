package com.sdmetrics.math;

/**
 * The node types of the expression operator tree.
 */
enum NodeType {
	/** A node representing a unary or binary operation (+, -, exp() etc.) */
	OPERATION,
	/** A node representing a number constant. */
	NUMBER,
	/** A node representing a string constant. */
	STRING,
	/** A node representing the name of identifier. */
	IDENTIFIER,
}