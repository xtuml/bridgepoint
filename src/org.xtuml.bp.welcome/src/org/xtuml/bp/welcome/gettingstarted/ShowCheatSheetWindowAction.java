package org.xtuml.bp.welcome.gettingstarted;


import java.util.Properties;

import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

public class ShowCheatSheetWindowAction implements IIntroAction {

    public void run(IIntroSite site, Properties params) {
    	/*We intentionally chose (a while ago) not to open the cheatsheet 
    	 * selection dialog because there are a couple of BridgePoint cheatsheets 
    	 * and the description text tells the user how to open. There is a class 
    	 * inside eclipse to open the Cheat Sheet Selection dialog but it is in 
    	 * an internal package and we don't want to reference it. 
    	 * See https://www.cct.lsu.edu/~rguidry/ecl31docs/api/org/eclipse/ui/internal/cheatsheets/actions/CheatSheetSelectionAction.html
    	 * 
    	 * This class prevents an ugly NoClassDefException to the console if the
    	 * user clicks the button.
    	 */
    }
	
}