
//=====================================================================
//
//File:      $RCSfile: AllActivityModifier.java,v $
//Version:   $Revision: 1.43 $
//Modified:  $Date: 2013/01/10 23:20:48 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
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

package com.mentor.nucleus.bp.ui.text.activity;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.texteditor.IElementStateListener;

import com.mentor.nucleus.bp.als.oal.ParserAllActivityModifier;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Parsestatus_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.ui.text.DocumentProvider;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.ModelAdapter;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderManager;



public class AllActivityModifier extends ParserAllActivityModifier
{
    private static IModelElementEditorInputFactory activityInputFactory;
    
    private ActivityEditor.ParseRunnable parseRunner = new ActivityEditor.ParseRunnable();

    public AllActivityModifier(Domain_c parent, IProgressMonitor monitor){
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    public AllActivityModifier(Component_c parent, IProgressMonitor monitor){
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    public AllActivityModifier(Package_c parent, IProgressMonitor monitor){
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    public AllActivityModifier(Subsystem_c parent, IProgressMonitor monitor){
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    public AllActivityModifier(FunctionPackage_c parent, IProgressMonitor monitor){
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    public AllActivityModifier(ExternalEntityPackage_c parent, IProgressMonitor monitor){
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    public AllActivityModifier(ModelRoot modelRoot, Object[] activities, IProgressMonitor monitor)
    {
    	super(modelRoot, activities, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    

    public AllActivityModifier(NonRootModelElement parent, IProgressMonitor monitor)
    {
    	super(parent, monitor);
        if(activityInputFactory == null){
        	activityInputFactory = ActivityEditorInputFactory.getDefaultInstance();
        }
    }
    
    
    public void processAllActivities(int op, boolean disposeBeforeParse, boolean includeSharedFragments) {

		PlaceHolderManager placeHolderManager = PlaceHolderManager.getDefaultInstance();
		placeHolderManager.setParseInProgress(true);

    	super.processAllActivities(op, disposeBeforeParse, includeSharedFragments);
    	
    	placeHolderManager.setParseInProgress(false);
    }

    public void parseAction(Object modelElement)
    {
    	if(!activityInputFactory.isSupported(modelElement)){
    		return;
        }

        int toParse = AllActivityModifier.accessSuc_Pars( false, 0, modelElement );
        if ( toParse == Parsestatus_c.doNotParse )
        {
        	if (modelElement instanceof ProvidedOperation_c) {
        		((ProvidedOperation_c)modelElement).Initializeunparsed();
        	}
        	else if (modelElement instanceof RequiredOperation_c) {
        		((RequiredOperation_c)modelElement).Initializeunparsed();
        	}
        	else if (modelElement instanceof ProvidedSignal_c) {
        		((ProvidedSignal_c)modelElement).Initializeunparsed();
        	}
        	else if (modelElement instanceof RequiredSignal_c) {
        		((RequiredSignal_c)modelElement).Initializeunparsed();
        	}
            return;
        }
        
       	ActivityEditorInput editorInput = null;
       	try {
			editorInput = (ActivityEditorInput)activityInputFactory.createInstance(modelElement);
		} catch (CoreException e) {
			//TODO: Log exception when test code introduced by 561-2 is fixed
			return;
		}

        final DocumentProvider dp = new DocumentProvider();
        try {
            dp.connect( editorInput );
            dp.resetDocument( editorInput );
        }
        catch ( CoreException ce )
        {
            return;   // ignore
        }
        ActivityAnnotationModel m_myAnnotationModel = (ActivityAnnotationModel)dp.getAnnotationModel(editorInput);
        m_myAnnotationModel.connect(dp.getDocument(editorInput));

        parseRunner.m_ae_input = editorInput;
        parseRunner.m_document = dp.getDocument(editorInput);
        parseRunner.m_myAnnotationModel = m_myAnnotationModel;
        parseRunner.m_modelElement = (NonRootModelElement)modelElement;
        parseRunner.run();
        
        //
        // doSaveDocument will do the correct thing 
        //    to update the Suc_Pars attribute
        //    if the editor was open or not
        //    if there were previously errors or not
    	doSaveDocument(editorInput, dp);
    	dp.disconnect(editorInput);
        if ( m_pm != null ) {
        	m_pm.worked(1);
            UIUtil.dispatchAll();
        }
    }

    
    private void doSaveDocument(ActivityEditorInput aei, final DocumentProvider dp) {
		class DocumentDirtyStateChanged implements IElementStateListener {
			public void elementDirtyStateChanged(Object element, boolean isDirty) {
				if (!isDirty) {
					ActivityEditorInput aei = (ActivityEditorInput) element;
					dp.getAnnotationModel(aei).disconnect(dp.getDocument(aei));
					dp.removeElementStateListener(this);
					
				}
			}

			public void elementContentAboutToBeReplaced(Object element) {
			}
			public void elementContentReplaced(Object element) {
			}
			public void elementDeleted(Object element) {
			}
			public void elementMoved(Object originalElement, Object movedElement) {
			}
		}
		DocumentDirtyStateChanged listener = new DocumentDirtyStateChanged();
		dp.addElementStateListener(listener);
		try {
            aei.doSaveDocument(new NullProgressMonitor(), aei.getModelElement(), dp.getDocument(aei), true);
		    dp.saveDocument(new NullProgressMonitor(), aei, dp.getDocument(aei), true);
		}
		catch ( CoreException ce3 )
		{
		    // ignore
		}
	}

	@Override
    public void clearActionPlaceholder(Object o_input)
    {
    	if(!activityInputFactory.isSupported(o_input)){
    		return;
        }

    	NonRootModelElement modelElement = (NonRootModelElement)o_input;
		ModelElementID modelElementID = ModelAdapter.getModelElementAdapter(modelElement).createModelElementID(modelElement);
		
		
    	IFile placeHolderFile = PlaceHolderManager.getDefaultInstance().getPlaceholderFile(modelElementID, 
    			ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this, false);
    	
    	if(placeHolderFile != null && placeHolderFile.exists()){
    	
    		try {
				placeHolderFile.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
			} catch (CoreException e) {
				TextPlugin.logError("Error while clearing markers", e); //$NON-NLS-1$
			}
    		
    		PlaceHolderManager.getDefaultInstance().releasePlaceholderFile(modelElementID, 
        			ActivityEditorInputFactory.PLACEHOLDER_EXTENSION, this);    		
    	}
        if ( m_pm != null ) m_pm.worked(1);
    }
}
