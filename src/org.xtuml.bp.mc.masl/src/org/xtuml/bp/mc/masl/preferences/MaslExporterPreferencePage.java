package org.xtuml.bp.mc.masl.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.explorer.ModelLabelProvider;

public class MaslExporterPreferencePage extends PropertyPage {
    
    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        
        Group elementListGroup = new Group(composite, SWT.NONE);
        elementListGroup.setLayout(new GridLayout(2, false));
        elementListGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        elementListGroup.setText("Build elements");
        
        TreeViewer elementListViewer = new CheckboxTreeViewer(elementListGroup, SWT.NONE | SWT.BORDER | SWT.MULTI);
        elementListViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
        elementListViewer.setLabelProvider(new ModelLabelProvider());
        elementListViewer.setContentProvider(new ITreeContentProvider() {
            @Override
            public boolean hasChildren(Object parentElement) {
                return false;
            }
            @Override
            public Object getParent(Object element) {
                return null;
            }
            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof List) {
                    return ((List<?>)inputElement).toArray();
                }
                else {
                    return new Object[0];
                }
            }
            @Override
            public Object[] getChildren(Object element) {
                return null;
            }
        });
        elementListViewer.setInput(getBuildElements());
        
        Group outputControlGroup = new Group(composite, SWT.NONE);
        outputControlGroup.setLayout(new GridLayout(2, true));
        outputControlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        outputControlGroup.setText("Output settings");
        
        Button enableFormatButton = new Button(outputControlGroup, SWT.CHECK);
        enableFormatButton.setText("Format output MASL");

        Button emitActionLanguageButton = new Button(outputControlGroup, SWT.CHECK);
        emitActionLanguageButton.setText("Emit activity definition files");
        
        Label outputFolderLabel = new Label(outputControlGroup, SWT.NONE);
        outputFolderLabel.setText("Output destination:");
        Text outputFolderTextbox = new Text(outputControlGroup, SWT.LEFT | SWT.BORDER);
        outputFolderTextbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        return composite;
    }
    
    private List<NonRootModelElement> getBuildElements() {
        SystemModel_c s_sys = null;
        if (getElement() instanceof SystemModel_c) {
            s_sys = (SystemModel_c)getElement();
        }
        else if (getElement() instanceof IProject) {
            s_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), (selected) -> ((SystemModel_c)selected).getName().equals(((IProject)getElement()).getName()));
        }
        if (null != s_sys) {
            List<NonRootModelElement> elements = new ArrayList<>();
            Component_c[] comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405((SystemModel_c)getElement())));
            for (Component_c comp : comps) {
                elements.add(comp);
            }
            Deployment_c[] depls = Deployment_c.getManyD_DEPLsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405((SystemModel_c)getElement())));
            for (Deployment_c depl : depls) {
                elements.add(depl);
            }
            // TODO packages
            return elements;
        }
        else {
            return null;
        }
    }

}