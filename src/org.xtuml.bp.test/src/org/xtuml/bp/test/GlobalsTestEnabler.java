package org.xtuml.bp.test;

import org.xtuml.bp.test.common.BaseTest;

public class GlobalsTestEnabler extends BaseTest {

  public GlobalsTestEnabler(String packagename, String name) {
		super(packagename, name);
  }

  public void testGlobally() {
		testGlobals = true;
	  if(getName().contains("FormalizeUnformalizeTestGenerics")) {
		  testGlobals = false;
	  }
  }

}
