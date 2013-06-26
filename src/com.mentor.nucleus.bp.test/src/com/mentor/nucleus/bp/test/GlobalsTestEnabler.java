package com.mentor.nucleus.bp.test;

import com.mentor.nucleus.bp.test.common.BaseTest;

public class GlobalsTestEnabler extends BaseTest {

  public GlobalsTestEnabler(String packagename, String name) {
		super(packagename, name);
  }

  public void testGlobally() {
	testGlobals = true;
  }

}
