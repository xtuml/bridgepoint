
//=====================================================================
//
//File:      $RCSfile: UITextGlobalsSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:47:11 $
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


import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.ui.text.editor.oal.test.OALKeywordRuleTest;
import com.mentor.nucleus.bp.ui.text.test.DomainDeleteTestI744;
import com.mentor.nucleus.bp.ui.text.test.DuplicateRelationshipNumberParseAllTest;
import com.mentor.nucleus.bp.ui.text.test.ParseAllOnModelReloadTest;
import com.mentor.nucleus.bp.ui.text.test.PlaceHolderUpdateTest;
import com.mentor.nucleus.bp.ui.text.test.TextEditorReloadContentsTest;
import com.mentor.nucleus.bp.ui.text.test.UITextGlobalsTest;
import com.mentor.nucleus.bp.ui.text.test.activity.ActivityEditorTestSuite;
import com.mentor.nucleus.bp.ui.text.test.activity.I2046IncorrectPlaceHolderMappingTest;
import com.mentor.nucleus.bp.ui.text.test.activity.I643OALKeywordsHighlightingTest;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorTestSuite;
import com.mentor.nucleus.bp.ui.text.test.i372Tests.i372TestSuite;
import com.mentor.nucleus.bp.ui.text.test.i589Test.I589TestSuite;
import com.mentor.nucleus.bp.ui.text.test.i673Tests.placeholder.PlaceHolderLifecycleTestSuite;
import com.mentor.nucleus.bp.ui.text.test.i673Tests.rename.RenameEditorsTestSuite;

/**
 * Test all areas of the UI.
 */
public class UITextGlobalsSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new UITextGlobalsSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public UITextGlobalsSuite() {
        addTest(new TestSuite(UITextGlobalsTest.class));
        addTest(new TestSuite(I2046IncorrectPlaceHolderMappingTest.class));
		addTest(new I589TestSuite());
		addTest(new DescriptionEditorTestSuite());
        addTest(new ActivityEditorTestSuite());
        addTest(new TestSuite(OALKeywordRuleTest.class));
        addTest(new TestSuite(I643OALKeywordsHighlightingTest.class));
        addTest(new i372TestSuite());
        addTest(new RenameEditorsTestSuite());
        addTest(new TestSuite(PlaceHolderUpdateTest.class));
        addTest(new TestSuite(TextEditorReloadContentsTest.class));
        addTest(new PlaceHolderLifecycleTestSuite());
        addTest(new TestSuite(DomainDeleteTestI744.class));
        addTest(new TestSuite(DuplicateRelationshipNumberParseAllTest.class));
        addTest(new TestSuite(ParseAllOnModelReloadTest.class));
	}
}