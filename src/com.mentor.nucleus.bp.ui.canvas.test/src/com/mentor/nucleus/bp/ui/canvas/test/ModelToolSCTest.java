package com.mentor.nucleus.bp.ui.canvas.test;
//=====================================================================
//
//File: $RCSfile: ModelToolSCTest.java,v $
//Version: $Revision: 1.12 $
//Modified: $Date: 2013/01/10 22:43:49 $
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


/**
 * Performs automated testing of the more interesting transitions between states
 * in the model-tool statechart.
 * 
 * Note that the tests contained herein are meant to be run sequentially in the order of their
 * declaration, each one using as its initial state the final state of the previous test. 
 */
public class ModelToolSCTest extends StatechartTest
{
//	/**
//	 * The model-tool exercised during these tests.
//	 */
//	private static ModelTool_c modelTool;

	/**
	 * Constructor
	 */
	public ModelToolSCTest(String arg0)
	{
		super(arg0);
	}

//	/**
//	 * See parent method overridden.
//	 */
//	public void setUp() throws Exception
//	{
//		super.setUp();
//		
//		// use the already existing (and activated) selection-tool as our model-tool 
//	    // to test
//		modelTool = ModelTool_c.getOneCT_MTLOnR102(
//		    SelectionTool_c.SelectionToolInstance(graphicsModelRoot));
//	}
//	
//	/**
//	 * See parent class overridden.
//	 */
//	private abstract class Inputs implements StatechartTest.Inputs
//	{
//		public int getToolCurrentState() {
//			return modelTool.get_current_state();
//		}
//		public void cleanup() {
//		}
//	}
//	
//	public void test_101CT_MTL6InActive() throws Exception
//	{
//		performTest("test_101", 
//				new Inputs() {
//					public void perform() {
//						modelTool.Deactivate(false);
//					}
//				},
//				ModelTool_c.ST_MODEL_TOOL_IDLE);
//	}
//
//	public void test_102CT_MTL5InIdle() throws Exception
//	{
//		performTest("test_102", 
//				new Inputs() {
//					public void perform() {
//						modelTool.Activate(false);
//					}
//				},
//				ModelTool_c.ST_MODEL_TOOL_ACTIVE);
//	}
//
//	public void test_103CT_MTL1InActive() throws Exception
//	{
//		performTest("test_103", 
//				new Inputs() {
//					public void perform() {
//						CanvasTestUtils.doMousePress(100, 100);
//					}
//				},
//				ModelTool_c.ST_MODEL_TOOL_ACTIVE);
//	}
//
//	public void test_104CT_MTL3InActive() throws Exception
//	{
//		performTest("test_104", 
//				new Inputs() {
//					public void perform() {
//						CanvasTestUtils.doMouseRelease(100, 100);
//					}
//				},
//				ModelTool_c.ST_MODEL_TOOL_ACTIVE);
//	}
//
//	public void test_105CT_MTL4InActive() throws Exception
//	{
//		performTest("test_105", 
//				new Inputs() {
//					public void perform() {
//						CanvasTestUtils.doMouseMove(110, 110);
//					}
//				},
//				ModelTool_c.ST_MODEL_TOOL_ACTIVE);
//	}
}