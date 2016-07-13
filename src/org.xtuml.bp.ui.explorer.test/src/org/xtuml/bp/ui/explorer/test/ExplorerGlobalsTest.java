package org.xtuml.bp.ui.explorer.test;

import org.junit.runner.RunWith;
import org.xtuml.bp.test.GlobalsTestEnabler;
import org.xtuml.bp.test.common.OrderedRunner;

@RunWith(OrderedRunner.class)
public class ExplorerGlobalsTest extends GlobalsTestEnabler {

	private static final String packageName = "org.xtuml.bp.ui.explorer.test";

    public ExplorerGlobalsTest() {
        super(packageName, null);
    }
    
}
