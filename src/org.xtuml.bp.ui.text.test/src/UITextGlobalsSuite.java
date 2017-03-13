
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


/**
 * Test all areas of the UI.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.ui.text.editor.oal.test.OALKeywordRuleTest;
import org.xtuml.bp.ui.text.test.DomainDeleteTestI744;
import org.xtuml.bp.ui.text.test.DuplicateRelationshipNumberParseAllTest;
import org.xtuml.bp.ui.text.test.ParseAllActivitiesOnSystemModelTest;
import org.xtuml.bp.ui.text.test.ParseAllOnModelReloadTest;
import org.xtuml.bp.ui.text.test.PlaceHolderUpdateTest;
import org.xtuml.bp.ui.text.test.TextEditorReloadContentsTest;
import org.xtuml.bp.ui.text.test.UITextGlobalsTest;
import org.xtuml.bp.ui.text.test.activity.ActivityEditorTestSuite2;
import org.xtuml.bp.ui.text.test.activity.I2046IncorrectPlaceHolderMappingTest;
import org.xtuml.bp.ui.text.test.activity.I643OALKeywordsHighlightingTest;
import org.xtuml.bp.ui.text.test.description.DescriptionEditorTestSuite;
import org.xtuml.bp.ui.text.test.i372Tests.i372TestSuite;
import org.xtuml.bp.ui.text.test.i589Test.I589TestSuite;
import org.xtuml.bp.ui.text.test.i673Tests.placeholder.PlaceHolderLifecycleTestSuite;
import org.xtuml.bp.ui.text.test.i673Tests.rename.RenameEditorsTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UITextGlobalsTest.class,
    I2046IncorrectPlaceHolderMappingTest.class,
	I589TestSuite.class,
	DescriptionEditorTestSuite.class,
    ActivityEditorTestSuite2.class,
    OALKeywordRuleTest.class,
    I643OALKeywordsHighlightingTest.class,
    i372TestSuite.class,
    RenameEditorsTestSuite.class,
    PlaceHolderUpdateTest.class,
    TextEditorReloadContentsTest.class,
    PlaceHolderLifecycleTestSuite.class,
    DomainDeleteTestI744.class,
    DuplicateRelationshipNumberParseAllTest.class,
    ParseAllOnModelReloadTest.class,
    ParseAllActivitiesOnSystemModelTest.class
})

public class UITextGlobalsSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new UITextGlobalsSuite();
	}
	
}