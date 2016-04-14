package org.xtuml.bp.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;

public class GlobalsTestEnabler extends BaseTest {

	@Rule public TestName name = new TestName();
	
  public GlobalsTestEnabler(String packagename, String name) {
		super(packagename, name);
  }
  
  @Before
  public void setUp() throws Exception{
	  super.setUp();
	  
  }

  @Test
  public void testGlobally() {
		testGlobals = true;
	  if(name != null && name.getMethodName() != null && name.getMethodName().contains("FormalizeUnformalizeTestGenerics")) {
		  testGlobals = false;
	  }
  }

}
