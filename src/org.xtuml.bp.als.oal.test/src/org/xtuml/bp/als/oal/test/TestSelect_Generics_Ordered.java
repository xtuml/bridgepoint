//========================================================================
//
//File:      $RCSfile: TestSelect_Generics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:00:32 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================
//
package org.xtuml.bp.als.oal.test;

import org.eclipse.jface.preference.IPreferenceStore;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;

public class TestSelect_Generics_Ordered extends TestCase {
	public TestSelect_Generics_Ordered() {
		super();
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
		store.setValue(BridgePointPreferencesStore.ALLOW_OPERATIONS_IN_WHERE, true);
	}

	protected void tearDown() throws Exception {
		try {
			super.tearDown();
			OalParserTest_Generics.tearDownActionData();
		} catch (RecognitionException re) {
			// do nothing
		} catch (TokenStreamException te) {
			// do nothing
		}
	}

	/**
	 * Note that these tests must be run in order, therefore the methods in this
	 * test should not be changed.
	 * 
	 * @throws Exception
	 */
	public void testSelectOutsideOfWhere() throws Exception {
		doTestSelectedVarOutsideWhere();
		doTestSelectedAttrOutsideWhere();
	}

	public void doTestSelectedVarOutsideWhere() throws RecognitionException, TokenStreamException {
		String act = "selected = 1;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(3, lines.length);
		assertEquals(":1:1-8: Keyword ->Selected<- cannot be used outside a where expression", lines[0]); //$NON-NLS-1$
		assertEquals("line 1:13: expecting TOK_EQUAL, found ';'", lines[1]); //$NON-NLS-1$
		assertEquals("line 1:14: expecting Semicolon, found 'null'", lines[2]); //$NON-NLS-1$
		String[] var_list = {};
		TestSelect_Generics.badSelectValidate(var_list, 0, 0, 1);
	}

	public void doTestSelectedAttrOutsideWhere() throws RecognitionException, TokenStreamException {
		String act = "select any selected from instances of D_D;\nselected.Disk_ID = 1;"; //$NON-NLS-1$
		String x = OalParserTest_Generics.parseAction(act, OalParserTest_Generics.ACTIVITY_TYPE_FUNC,
				OalParserTest_Generics.TEST_VOID_NO_PARM);
		String lines[] = x.split("\n");//$NON-NLS-1$
		assertEquals(4, lines.length);
		assertEquals(":1:12-19: Keyword ->Selected<- cannot be used outside a where expression", lines[0]); //$NON-NLS-1$
		assertEquals(":2:10-16: Keyword ->Selected<- cannot be used outside a where expression", lines[1]); //$NON-NLS-1$
		assertEquals("line 2:21: expecting TOK_EQUAL, found ';'", lines[2]); //$NON-NLS-1$
		assertEquals("line 2:22: expecting Semicolon, found 'null'", lines[3]); //$NON-NLS-1$
		String[] var_list = {};
		TestSelect_Generics.badSelectValidate(var_list, 0, 0, 3);
	}
}
