package org.xtuml.bp.io.xmi.translate.processors;

/**
 * Ignore types:
 * 
 * NOT_HANDLED - A case where this is a UML feature we cannot support at this
 * time HANDLED - A case where the processor ignoring is not the correct one to
 * handle processing
 */
public enum IgnoreType {
    NOT_HANDLED, HANDLED, NOT_IGNORED
}
