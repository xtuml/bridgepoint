
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

package org.xtuml.bp.ui.text.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.texteditor.IElementStateListener;

import org.xtuml.bp.als.oal.ParserAllActivityModifier;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.ui.text.DocumentProvider;
import org.xtuml.bp.ui.text.IModelElementEditorInputFactory;
import org.xtuml.bp.ui.text.ModelAdapter;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.placeholder.PlaceHolderManager;



public class AllActivityModifier extends ParserAllActivityModifier
{
    private static IModelElementEditorInputFactory activityInputFactory;
    
    private ActivityEditor.ParseRunnable parseRunner = new ActivityEditor.ParseRunnable();

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
    
    public AllActivityModifier(SystemModel_c parent, IProgressMonitor monitor){
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
        // get dialect
        int dialect = -1;
		// see if the current element should open
		// something other than itself
		Object dialectObj = modelElement;
		if (dialectObj instanceof StateMachineState_c) {
		    StateMachineState_c state = (StateMachineState_c) dialectObj;
			Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513((MooreActionHome_c.getOneSM_MOAHOnR511(state))));
			if (action != null) {
				dialectObj = action;
			}
		}
		else if (dialectObj instanceof Transition_c) {
			Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530((Transition_c)dialectObj)));
			if (action != null) {
				dialectObj = action;
			}
		}
		else if ( dialectObj instanceof Attribute_c ) {
			DerivedBaseAttribute_c dbattr = DerivedBaseAttribute_c.getOneO_DBATTROnR107(
					BaseAttribute_c.getOneO_BATTROnR106((Attribute_c)dialectObj));
			if ( dbattr != null ) {
				dialectObj = dbattr;
			}
		}
		// Get the value of the dialect attribute
        try {
            Method getDialectMethod = dialectObj.getClass().getMethod("getDialect"); //$$NON-NLS-1$$
            dialect = (int) getDialectMethod.invoke(dialectObj);
        } catch ( NoSuchMethodException e ) {
            System.out.println( e );
        } catch ( NullPointerException e ) {
            System.out.println( e );
        } catch ( SecurityException e ) {
            System.out.println( e );
        } catch ( IllegalAccessException e ) {
            System.out.println( e );
        } catch ( IllegalArgumentException e ) {
            System.out.println( e );
        } catch ( InvocationTargetException e ) {
            System.out.println( e );
        } catch ( ExceptionInInitializerError e ) {
            System.out.println( e );
        }
        // if none dialect, do not parse
        if ( dialect == Actiondialect_c.none ) return;

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
