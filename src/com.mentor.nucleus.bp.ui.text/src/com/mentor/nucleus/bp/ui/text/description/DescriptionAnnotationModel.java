package com.mentor.nucleus.bp.ui.text.description;
//====================================================================
//
// File:      $RCSfile: DescriptionAnnotationModel.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:21:01 $
//
// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;

import com.mentor.nucleus.bp.ui.text.TextPlugin;

public class DescriptionAnnotationModel
  extends AbstractMarkerAnnotationModel
{
	/**
	 * Internal resource change listener.
	 * 
	 * @see IResourceChangeListener
	 */
	class ResourceChangeListener implements IResourceChangeListener {
		/*
		 * @see IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent e) {
			IResourceDelta delta= e.getDelta();
			try {
				if (delta != null)
					delta.accept(fResourceDeltaVisitor);
			} catch (CoreException x) {
				TextPlugin.logError("ActionAnnotationModel.resourceChanged", x);
			}
		}
	};
	
	/**
	 * Internal resource delta visitor.
	 * 
	 * @see IResourceDeltaVisitor
	 */
	class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		/*
		 * @see IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
			if (delta != null && fInput.getFile().equals(delta.getResource())) {
				update(delta.getMarkerDeltas());
				return false;
			}
			return true;
		}
	};
	
	/**
	 * Updates this model to the given marker deltas.
	 *
	 * @param markerDeltas the array of marker deltas
	 */
	protected void update(IMarkerDelta[] markerDeltas) {
		
		if (markerDeltas.length ==  0)
			return;
		
		for (int i= 0; i < markerDeltas.length; i++) {
			IMarkerDelta delta= markerDeltas[i];
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :
					addMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.REMOVED :
					removeMarkerAnnotation(delta.getMarker());
					break;
				case IResourceDelta.CHANGED :
					modifyMarkerAnnotation(delta.getMarker());
					break;
			}
		}
		
		fireModelChanged();
	}
	
  protected IResourceDeltaVisitor fResourceDeltaVisitor= new ResourceDeltaVisitor();
  protected IResourceChangeListener fResourceChangeListener= new ResourceChangeListener();
	
  public IFileEditorInput fInput;
  /**
   * Constructor for ActionAnnotationModel.
   * @param resource
   */
  public DescriptionAnnotationModel(IFileEditorInput input)
  {
    super();
    fInput = input;
  }

  /*
   * @see AbstractMarkerAnnotationModel#deleteMarkers(IMarker[])
   */
  protected void deleteMarkers(final IMarker[] markers) throws CoreException {
	  fInput.getFile().getWorkspace().run(new IWorkspaceRunnable() {
		  public void run(IProgressMonitor monitor) throws CoreException {
			  for (int i= 0; i < markers.length; ++i) {
				  markers[i].delete();
			  }
		  }
	  }, null);
  }

  /*
   * @see AbstractMarkerAnnotationModel#isAcceptable(IMarker)
   */
  protected boolean isAcceptable(IMarker marker) {
	  return marker != null && fInput.getFile().equals(marker.getResource());
  }
	
		
  /*
   * @see AbstractMarkerAnnotationModel#listenToMarkerChanges(boolean)
   */
  protected void listenToMarkerChanges(boolean listen) {
	  if (listen)
	fInput.getFile().getWorkspace().addResourceChangeListener(fResourceChangeListener);
	  else
	fInput.getFile().getWorkspace().removeResourceChangeListener(fResourceChangeListener);
  }
	
  /*
   * @see AbstractMarkerAnnotationModel#retrieveMarkers()
   */
  public IMarker[] retrieveMarkers()
  {
    try
    {
      if(fInput.getFile().exists()){
      	return fInput.getFile().findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);	
      }
    }
    catch (CoreException e)
    {
		TextPlugin.logError("ActionAnnotationModel.retrieveMarkers", e);
    }
    return null;
  }

   /*
	* @see AnnotationModel#removeAllAnnotations(boolean)
	*/
   protected void removeAllAnnotations(boolean fireModelChanged)
   {
	 super.removeAllAnnotations(fireModelChanged);
	 try {
		 deleteMarkers(retrieveMarkers());
	 }
	 catch(CoreException e)
	 {
		 TextPlugin.logError("ActionAnnotationModel.removeAllAnnotations", e);
	 }
   }
}

