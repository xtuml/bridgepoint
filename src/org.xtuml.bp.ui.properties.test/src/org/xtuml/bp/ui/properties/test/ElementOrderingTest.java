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

package org.xtuml.bp.ui.properties.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.ui.properties.BridgeParameterS_BPARMPropertySource;
import org.xtuml.bp.ui.properties.BridgeS_BRGPropertySource;

/**
 * Contains tests that exercise various aspects of the properties view.
 */
public class ElementOrderingTest extends BaseTest 
{
    /**
     * Whether the first test of this class is the one that's currently 
     * being run.
     */
    private static boolean firstTest = true;

    public ElementOrderingTest(String name) {
        super(null, name);
    }    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
	protected void setUp() throws Exception {
        super.setUp();

        // if it's the first test of this class that's being setup
        if (firstTest) {
        	loadProject("BridgeParameterOrderingTest");
            firstTest = false;
        }	
        // reset perspective
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.resetPerspective();
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
    }
    
    public void testElementOrderingInView() throws CoreException {
    	Package_c eePkg = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("External Entities");
			}
		});
    	assertNotNull(eePkg);
    	Bridge_c bridge = Bridge_c.getOneS_BRGOnR19(ExternalEntity_c.getManyS_EEsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(eePkg)), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Bridge_c) candidate).getName().equals("concat_string");
			}
		});
    	BridgeS_BRGPropertySource source = new BridgeS_BRGPropertySource(bridge);
    	IPropertyDescriptor[] propertyDescriptors = source.getPropertyDescriptors();
    	boolean foundCorrectOrder = false;
    	boolean foundOne = false;
    	for(IPropertyDescriptor descriptor : propertyDescriptors) {
    		Object propertyValue = source.getPropertyValue(descriptor.getId());
    		if(propertyValue instanceof BridgeParameterS_BPARMPropertySource) {
    			BridgeParameterS_BPARMPropertySource paramSource = (BridgeParameterS_BPARMPropertySource) propertyValue;
    			if(paramSource.toString().equals("s1")) {
    				foundOne = true;
    			}
    			if(paramSource.toString().equals("s2")) {
    				if(foundOne) {
    					foundCorrectOrder = true;
    				}
    				break;
    			}
    		}
    	}
    	assertTrue("User-defined ordering is not honored in the properties view.", foundCorrectOrder);
    }
    
}
