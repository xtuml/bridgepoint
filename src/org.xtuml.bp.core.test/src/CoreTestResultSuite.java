import junit.framework.Test;
import junit.framework.TestSuite;

import org.xtuml.bp.core.test.CoreTestResultCreator;

public class CoreTestResultSuite extends TestSuite {

    public static Test suite() {
        return new CoreTestResultSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public CoreTestResultSuite() {
        addTest(new TestSuite(CoreTestResultCreator.class));
    }
}
