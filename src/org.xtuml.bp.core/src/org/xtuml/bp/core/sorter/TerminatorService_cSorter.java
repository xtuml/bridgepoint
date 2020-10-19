package org.xtuml.bp.core.sorter;
//======================================================================

import java.util.Arrays;

//
// File: org.xtuml.bp.core/src/org/xtuml/bp/core/sorter/TerminatorService_cSorter.java
// NOTE: This file is handwritten until the TNS infrastructure can support ordering through
// an associated classes ordering association.
//
// This work is licensed under the Creative Commons CC0 License
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
//
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ServiceInSequence_c;
import org.xtuml.bp.core.TerminatorService_c;

public class TerminatorService_cSorter extends BaseTypeSpecificSorter {

	public TerminatorService_cSorter() {
		super(TerminatorService_c.class);
	}

	public void sort(Object[] elements) {
		if (elements.length < 2) {
			return;
		}

		TerminatorService_c[] services = (TerminatorService_c[]) elements;
		// only sort through association if ServiceInSequence exists
		boolean associationOrdered = ServiceInSequence_c.getOneD_SISOnR1660(services[0]) != null;
		if (!associationOrdered) {
			sortAlpha(elements);
			return;
		}
		int index = 0;
		TerminatorService_c tail = null;

		// Search from the end as there are fair chances that array is already
		// sorted.
		for (index = services.length - 1; index >= 0; index--) {
			ServiceInSequence_c sis = ServiceInSequence_c.getOneD_SISOnR1660(services[index]);
			TerminatorService_c service = TerminatorService_c
					.getOneD_TSVCOnR1660(ServiceInSequence_c.getOneD_SISOnR1661Precedes(sis));
			if (service == null) {
				tail = services[index];
				break;
			}
		}

		if (tail == null) {
			CorePlugin.logError("ServiceInSequence ordering relationship R1661 is corrupt", null);
			return;
		}

		for (index = services.length - 1; index >= 0; index--) {
			services[index] = tail;
			ServiceInSequence_c sis = ServiceInSequence_c.getOneD_SISOnR1660(tail);
			tail = TerminatorService_c.getOneD_TSVCOnR1660(ServiceInSequence_c.getOneD_SISOnR1661Succeeds(sis));
		}
	}

	public void sortAlpha(Object[] elements) {
		AlphaSorter sorter = new AlphaSorter();
		Arrays.sort(elements, (a, b) -> {
			if (((TerminatorService_c) b).getIs_stale() && !((TerminatorService_c) a).getIs_stale()) {
				return -1;
			} else if (((TerminatorService_c) a).getIs_stale() && !((TerminatorService_c) b).getIs_stale()) {
				return 1;
			} else {
				return sorter.compare(a, b);
			}
		});
	}
}
