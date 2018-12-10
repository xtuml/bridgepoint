package org.xtuml.bp.core.sorter;

import java.util.Arrays;

import org.xtuml.bp.core.TerminatorService_c;

public class TerminatorService_cSorter extends BaseTypeSpecificSorter {

    public TerminatorService_cSorter() {
        super(TerminatorService_c.class);
    }

    public void sort(Object[] elements) {
    	AlphaSorter sorter = new AlphaSorter();
        if (elements.length < 2) {
            return;
        }
        Arrays.sort(elements, (a, b) -> {
        	if ( ((TerminatorService_c)b).getIs_stale() && !((TerminatorService_c)a).getIs_stale() ) {
        		return -1;
        	}
        	else if ( ((TerminatorService_c)a).getIs_stale() && !((TerminatorService_c)b).getIs_stale() ) {
        		return 1;
        	}
        	else {
        		return sorter.compare(a, b);
        	}
        });
    }
}
