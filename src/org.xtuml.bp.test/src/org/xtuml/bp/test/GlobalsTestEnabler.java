package org.xtuml.bp.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.xtuml.bp.test.common.BaseTest;

public class GlobalsTestEnabler extends BaseTest {

	@Rule public TestName name = new TestName();
	
  public GlobalsTestEnabler(String packagename, String name) {
		super(packagename, name);
  }

  @Test
  public void testGlobally() {
		testGlobals = true;
	  if(name.getMethodName().contains("FormalizeUnformalizeTestGenerics")) {
		  testGlobals = false;
	  }
  }

}
