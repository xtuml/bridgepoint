package org.xtuml.bp.core.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.SystemModel_c;

public class BridgePointProjectActionLanguagePreferencesPage extends PropertyPage {
    
    BridgePointProjectPreferences page;
    
    public BridgePointProjectActionLanguagePreferencesPage() {
        page = null;
    }

    @Override
    protected Control createContents(Composite parent) {
        IScopeContext projectScope = new ProjectScope(getProject());
        Preferences projectNode = projectScope.getNode(
                BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
        page = new BridgePointProjectActionLanguagePreferences(projectNode);
        return page.createContents(parent);
    }
    
    @Override
    public void performDefaults() {
        page.performDefaults();
    }

    @Override
    public boolean performOk() {
        return page.performOk();
    }

    @Override
    public void performApply() {
        performOk();
    }
 
    private IProject getProject() {                                                                                                                  
         if (getElement() instanceof IProject) {                                                                                                      
              return (IProject) getElement();                                                                                                          
          } else if (getElement() instanceof SystemModel_c) {                                                                                          
              return (IProject) ((SystemModel_c) getElement()).getAdapter(IProject.class);                                                             
          } else {                                                                                                                                     
             return null;                                                                                                                             
          }                                                                                                                                            
     }     

}
