
//=====================================================================
//
//File:      $RCSfile: CloseCanvasEditor.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package org.xtuml.bp.ui.canvas.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;

import junit.framework.TestCase;

@RunWith(OrderedRunner.class)
public class CloseCanvasEditor extends TestCase {

	public CloseCanvasEditor() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void closeCanvasEditor( String title )
	{
		GraphicalEditor ce = CanvasTestUtils.getCanvasEditor(title);
		if ( ce != null )
		{
			assertFalse( ce.isDirty() );
			ce.getSite().getPage().closeEditor(ce, false);
		}
		else
		{
			fail("Unable to find editor with title: " + title);
		}
	}
	
	@Test
	public void testClosePD()
	{	
		closeCanvasEditor("odms");
	}

	@Test
	public void testCloseSC()
	{	
		closeCanvasEditor("Disk");
	}
	
}
