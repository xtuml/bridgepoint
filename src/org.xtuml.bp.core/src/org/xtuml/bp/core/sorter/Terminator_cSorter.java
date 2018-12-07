package org.xtuml.bp.core.sorter;

import java.util.Arrays;

import org.xtuml.bp.core.Terminator_c;

public class Terminator_cSorter extends BaseTypeSpecificSorter {

    public Terminator_cSorter() {
        super(Terminator_c.class);
    }

    public void sort(Object[] elements) {
    	AlphaSorter sorter = new AlphaSorter();
        if (elements.length < 2) {
            return;
        }
        Arrays.sort(elements, (a, b) -> {
        	if ( ((Terminator_c)a).getProvider() && !((Terminator_c)b).getProvider() ) {
        		return -1;
        	}
        	if ( ((Terminator_c)b).getProvider() && !((Terminator_c)a).getProvider() ) {
        		return 1;
        	}
        	else {
        		return sorter.compare(a, b);
        	}
        });
    }
}
