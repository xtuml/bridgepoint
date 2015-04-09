package antlr.collections.impl;

/* ANTLR Translator Generator
 * Project led by Terence Parr at http://www.jGuru.com
 * Software rights: http://www.antlr.org/RIGHTS.html
 *
 * $Id: VectorEnumerator.java,v 1.1 2003/06/04 20:52:05 greg Exp $
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;

import antlr.collections.Enumerator;

// based on java.lang.Vector; returns any null indices between non-null ones.

class VectorEnumerator implements Enumeration {
    Vector vector;
    int i;


    VectorEnumerator(Vector v) {
        vector = v;
        i = 0;
    }

    public boolean hasMoreElements() {
        synchronized (vector) {
            return i <= vector.lastElement;
        }
    }

    public Object nextElement() {
        synchronized (vector) {
            if (i <= vector.lastElement) {
                return vector.data[i++];
            }
            throw new NoSuchElementException("VectorEnumerator");
        }
    }
}
