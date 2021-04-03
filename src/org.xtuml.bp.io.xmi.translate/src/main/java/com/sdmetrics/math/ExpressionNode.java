/*
 * SDMetrics Open Core for UML design measurement
 * Copyright (c) Juergen Wuest
 * To contact the author, see <http://www.sdmetrics.com/Contact.html>.
 * 
 * This file is part of the SDMetrics Open Core.
 * 
 * SDMetrics Open Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
    
 * SDMetrics Open Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with SDMetrics Open Core.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.sdmetrics.math;

/** Represents a node in the operator tree for a math expression. */
public class ExpressionNode {

	/** Type of the node: operator, identifier, constant etc. */
	private NodeType type;
	/** Value of the node. The operator, the identifier, the constant etc. */
	private String value;
	/** List of operands. */
	ExpressionNode[] operands;

	/**
	 * Creates a new node representing an identifier.
	 * 
	 * @param identifier The identifier
	 */
	public ExpressionNode(String identifier) {
		this(NodeType.IDENTIFIER, identifier);
	}

	/**
	 * Creates a new node of a given type and value, without operands.
	 * 
	 * @param type Type of the node
	 * @param value Value of the node
	 */
	ExpressionNode(NodeType type, String value) {
		setValue(type, value, null);
	}

	/**
	 * Creates a new operation node.
	 * 
	 * @param value The operation of the node
	 * @param operands The operands of the node
	 */
	ExpressionNode(String value, ExpressionNode[] operands) {
		setValue(NodeType.OPERATION, value, operands);
	}

	/**
	 * Retrieves the value of this node. The meaning of the value depends on the
	 * node type:
	 * <ul>
	 * <li>For operations, the value is a string indicating the operation: +,-,
	 * etc or a function or relation name.
	 * <li>For numbers, the value is the string representation of the number.
	 * <li>For string constants, the value is the constant string (without the
	 * single quotes).
	 * <li>For identifiers, the value is the identifier.
	 * </ul>
	 * 
	 * @return value of the node
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets type, value, and operands of this node.
	 * 
	 * @param type New type of the node
	 * @param value New value of the node
	 * @param operands The new operands of the node (may be <code>null</code>
	 *        for constants and identifiers)
	 */
	final void setValue(NodeType type, String value, ExpressionNode[] operands) {
		this.type = type;
		this.value = value;
		this.operands = operands;
	}

	/**
	 * Gets the number of operands of this node.
	 * 
	 * @return The number of operands of this node
	 * @since 2.3
	 */
	public int getOperandCount() {
		return operands == null ? 0 : operands.length;
	}

	/***
	 * Gets an operand of the node.
	 * 
	 * @param index Index of the operand. Valid indices run from 0 to
	 *        {@link #getOperandCount()}.
	 * @return The operand at the given index.
	 * @throws IllegalArgumentException Index out of range
	 * @since 2.3
	 */
	public ExpressionNode getOperand(int index) {
		if (index >= getOperandCount()) {
			throw new IllegalArgumentException("No argument at index " + index
					+ " for operator '" + getValue() + "'.");
		}
		return operands[index];
	}

	/**
	 * Retrieves the node representing the left hand side of a binary operation,
	 * or the operand of a unary operation. Convenience method for unary/binary
	 * operations.
	 * 
	 * @return the left node, or <code>null</code> if there is no left operand
	 */
	public ExpressionNode getLeftNode() {
		return operands == null ? null : operands[0];
	}

	/**
	 * Retrieves the node representing the right hand side of a binary
	 * operation. Convenience method for binary operations.
	 * 
	 * @return the right node, or <code>null</code> if there is no right operand
	 */
	public ExpressionNode getRightNode() {
		return operands == null || operands.length < 2 ? null : operands[1];
	}

	/**
	 * Tests whether this node represents an operation.
	 * 
	 * @return <code>true</code> if this node represents an operation.
	 */
	public boolean isOperation() {
		return type == NodeType.OPERATION;
	}

	/**
	 * Tests whether this node represents a number constant.
	 * 
	 * @return <code>true</code> if this node represents a number constant.
	 */
	public boolean isNumberConstant() {
		return type == NodeType.NUMBER;
	}

	/**
	 * Tests whether this node represents a string constant.
	 * 
	 * @return <code>true</code> if this node represents a string constant.
	 */
	public boolean isStringConstant() {
		return type == NodeType.STRING;
	}

	/**
	 * Tests whether this node represents an identifier.
	 * 
	 * @return <code>true</code> if this node represents an identifier.
	 */
	public boolean isIdentifier() {
		return type == NodeType.IDENTIFIER;
	}

}
