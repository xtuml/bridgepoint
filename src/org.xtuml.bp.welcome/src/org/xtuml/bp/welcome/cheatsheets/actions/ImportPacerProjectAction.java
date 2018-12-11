/**
 * 
 */
package org.xtuml.bp.welcome.cheatsheets.actions;
import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.xtuml.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;

/**
 * @author hkhaled
 *
 */
public class ImportPacerProjectAction extends Action {
	@Override
	public void run() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				// get handle to help shell in order to display after completing below actions
				SampleProjectGettingStartedAction action = new SampleProjectGettingStartedAction();
				Properties props = new Properties();
				props.put("model", "avpace");
				props.put("SingleFileModel", "false");
				action.run(null, props);
                
                Shell helpShell = Display.getCurrent().getActiveShell();
                if (! helpShell.isDisposed())
                    helpShell.forceActive();
			}
    	});
	
	}  

}
