package com.mentor.nucleus.bp.ui.text.activity;
//====================================================================
//
// File:      $RCSfile: ActivityAnnotationModel.java,v $
// Version:   $Revision: 1.21 $
// Modified:  $Date: 2013/01/10 23:20:48 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.mentor.nucleus.bp.ui.text.AbstractModelElementListener;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityMarkerAnnotation;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityProblem;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityProblemAnnotation;
import com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation;
import com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemReporter;

public class ActivityAnnotationModel
	extends AbstractMarkerAnnotationModel
	implements IActivityProblemReporter
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
			IResourceDelta delta = e.getDelta();
			try {
				if (delta != null)
					delta.accept(fResourceDeltaVisitor);
			} catch (CoreException x) {
				TextPlugin.logError("ActionAnnotationModel.resourceChanged", x); //$NON-NLS-1$
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

		if (markerDeltas.length == 0)
			return;

		for (int i= 0; i < markerDeltas.length; i++) {
			IMarkerDelta delta = markerDeltas[i];
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

	protected static class ReverseMap
	{
		static class Entry
		{
			Position fPosition;
			Object fValue;
		};
		private List fList = new ArrayList(2);
		private int fAnchor = 0;
		public ReverseMap()
		{
		}
		public Object get(Position position)
		{
			Entry entry;
			// behind anchor
			int length = fList.size();
			for (int i = fAnchor; i < length; i++)
			{
				entry = (Entry) fList.get(i);
				if (entry.fPosition.equals(position))
				{
					fAnchor = i;
					return entry.fValue;
				}
			}
			// before anchor
			for (int i = 0; i < fAnchor; i++)
			{
				entry = (Entry) fList.get(i);
				if (entry.fPosition.equals(position))
				{
					fAnchor = i;
					return entry.fValue;
				}
			}
			return null;
		}
		private int getIndex(Position position)
		{
			Entry entry;
			int length = fList.size();
			for (int i = 0; i < length; i++)
			{
				entry = (Entry) fList.get(i);
				if (entry.fPosition.equals(position))
					return i;
			}
			return -1;
		}
		public void put(Position position, Object value)
		{
			int index = getIndex(position);
			if (index == -1)
			{
				Entry entry = new Entry();
				entry.fPosition = position;
				entry.fValue = value;
				fList.add(entry);
			}
			else
			{
				Entry entry = (Entry) fList.get(index);
				entry.fValue = value;
			}
		}
		public void remove(Position position)
		{
			int index = getIndex(position);
			if (index > -1)
				fList.remove(index);
		}
		public void clear()
		{
			fList.clear();
		}
	}

	public IFileEditorInput fInput;
	private List fGeneratedAnnotations = new ArrayList();
	private List fCollectedProblems = new ArrayList();
	private ReverseMap fReverseMap = new ReverseMap();
	private List fPreviouslyExistingMarkers = null;
	private List fCurrentlyExistingMarkers = new ArrayList();
	/**
	 * Constructor for ActionAnnotationModel.
	 * @param resource
	 */
	public ActivityAnnotationModel(IFileEditorInput input)
	{
		super();
		fInput = input;
	}
	/*
	 * @see org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel#createMarkerAnnotation(org.eclipse.core.resources.IMarker)
	 */
	protected MarkerAnnotation createMarkerAnnotation(IMarker marker)
	{
		try{
			if(marker.isSubtypeOf(IMarker.PROBLEM)){
				return new ActivityMarkerAnnotation(marker);
			}
		} catch (CoreException e) {
			TextPlugin.logError("Can not create marker", e); //$NON-NLS-1$
		}
		
		return super.createMarkerAnnotation(marker);
	}
	protected Position createPositionFromProblem(ActivityProblem problem)
	{
		int start = problem.getSourceStart();
		if (start >= 0)
		{
			int length = problem.getSourceEnd() - problem.getSourceStart() + 1;
			if (length >= 0)
				return new Position(start, length);
		}
		return null;
	}
	/*
	 * @see org.eclipse.ui.examples.xmleditor2.model.IActionProblemRequestor#acceptProblem(org.eclipse.ui.examples.xmleditor2.model.IActionProblem)
	 */
	public void acceptProblem(ActivityProblem problem)
	{
		fCollectedProblems.add(problem);
	}

	private void getPreviousMarkers()
	{
		if (fPreviouslyExistingMarkers == null)
		{
			fPreviouslyExistingMarkers = new ArrayList();
		}
		else
		{
			fPreviouslyExistingMarkers.clear();
		}

        Iterator iter = getAnnotationMap().entrySet().iterator();
        while (iter.hasNext())
        {
           Entry e = (Entry) iter.next();
           if (e.getKey() instanceof IActivityProblemAnnotation){
             IActivityProblemAnnotation apa =
               (IActivityProblemAnnotation) e.getKey();
             if (apa instanceof ActivityMarkerAnnotation)
             {
               ((ActivityMarkerAnnotation)apa).setRelevant(true);
               fPreviouslyExistingMarkers.add(apa);
             }
           }
        }
	}
	/*
	 * @see org.eclipse.ui.examples.xmleditor2.model.IActionProblemRequestor#beginReporting()
	 */
	public void beginReporting()
	{
	}
	/*
	 * @see IActionProblemRequestor#endReporting()
	 */
	public void endReporting()
	{
		boolean temporaryProblemsChanged = false;
		getPreviousMarkers();
		fCurrentlyExistingMarkers = new ArrayList();
		synchronized (getAnnotationMap())
		{
			if (fGeneratedAnnotations.size() > 0)
			{
				temporaryProblemsChanged = true;
				removeAnnotations(fGeneratedAnnotations, false, true);
				fGeneratedAnnotations.clear();
			}
			if (fCollectedProblems != null && fCollectedProblems.size() > 0)
			{
				Iterator e = fCollectedProblems.iterator();
				while (e.hasNext())
				{
					ActivityProblem problem = (ActivityProblem) e.next();
					Position position = createPositionFromProblem(problem);
					if (position != null)
					{
						boolean createAnnotation = true;
						Object value = getAnnotations(position);
						if ( value instanceof ActivityMarkerAnnotation )
						{
							ActivityMarkerAnnotation ama = (ActivityMarkerAnnotation)value;
							if (problem.getMessage().equals(ama.getMessage()))
							{
								// there is already a marker present for this error
								createAnnotation = false;
								setOverlay(ama, null);  // move to currently existing collection
								temporaryProblemsChanged = true;  // force a model refresh
							}
						}
						if ( createAnnotation )
						{
							ActivityProblemAnnotation annotation =
								new ActivityProblemAnnotation(problem);
							overlayMarkers(position, annotation);
							fGeneratedAnnotations.add(annotation);
							addAnnotation(annotation, position, false);
							temporaryProblemsChanged = true;
						}
					}
				}
				fCollectedProblems.clear();
			}
			boolean markersRemoved = removeIrrelevantMarkers();
			temporaryProblemsChanged = temporaryProblemsChanged || markersRemoved;
			
			fPreviouslyExistingMarkers.clear();
			fPreviouslyExistingMarkers = null;
		}
		if (temporaryProblemsChanged)
			fireModelChanged(new AnnotationModelEvent(this));
	}
	private boolean removeIrrelevantMarkers()
	{
		// all items remaining in fPreviouslyExistingMarkers when
		// this method is called are assumed to be irrelevant
		
		boolean ret_val = false;
		Iterator e = fPreviouslyExistingMarkers.iterator();
		while (e.hasNext())
		{
			ActivityMarkerAnnotation annotation =
				(ActivityMarkerAnnotation) e.next();
			annotation.setRelevant(false);
			ret_val = true;
		}
		return ret_val;
	}
	/**
	 * Overlays value with problem annotation.
	 * @param problemAnnotation
	 */
	private void setOverlay(
		Object value,
		ActivityProblemAnnotation problemAnnotation)
	{
		if (value instanceof ActivityMarkerAnnotation)
		{
      ActivityMarkerAnnotation annotation = (ActivityMarkerAnnotation) value;
			if (annotation.isProblem())
			{
				annotation.setOverlay(problemAnnotation);
				fPreviouslyExistingMarkers.remove(annotation);
				fCurrentlyExistingMarkers.add(annotation);
			}
		}
	}
	private void overlayMarkers(
		Position position,
		ActivityProblemAnnotation problemAnnotation)
	{
		Object value = getAnnotations(position);
		if (value instanceof List)
		{
			List list = (List) value;
			for (Iterator e = list.iterator(); e.hasNext();)
				setOverlay(e.next(), problemAnnotation);
		}
		else
		{
			setOverlay(value, problemAnnotation);
		}
	}
	private Object getAnnotations(Position position)
	{
		return fReverseMap.get(position);
	}
	/*
	 * @see AnnotationModel#addAnnotation(Annotation, Position, boolean)
	 */
	protected void addAnnotation(
		Annotation annotation,
		Position position,
		boolean fireModelChanged)
	{
		try {
			// for empty body that needs return value
			if (position.offset==1 && position.length==1) {
				position.offset = 0;
				position.length = 0;	
			}
			super.addAnnotation(annotation, position, fireModelChanged);
		} catch (BadLocationException e) {
			TextPlugin.logError("Unknown position " + position, e); //$NON-NLS-1$
		}
		
		Object cached = fReverseMap.get(position);
		if (cached == null)
			fReverseMap.put(position, annotation);
		else if (cached instanceof List)
		{
			List list = (List) cached;
			list.add(annotation);
		}
		else if (cached instanceof Annotation)
		{
			List list = new ArrayList(2);
			list.add(cached);
			list.add(annotation);
			fReverseMap.put(position, list);
		}
	}

	public void removeAnnotations(Vector markers) {
		if (fDocument != null) {
			if (markers.size() > 0)
			{
				try	{
					IMarker parms[] = new IMarker[markers.size()];
					markers.copyInto(parms);
					deleteMarkers(parms);
				}
				catch (CoreException e)
				{ TextPlugin.logError("ActionAnnotationModel.removeAnnotations", e); } //$NON-NLS-1$
			}
		}
	}

	/*
	 * @see AnnotationModel#removeAllAnnotations(boolean)
	 */
	protected void removeAllAnnotations(boolean fireModelChanged)
	{
		super.removeAllAnnotations(fireModelChanged);
		fReverseMap.clear();
    	try {
			deleteMarkers(retrieveMarkers());
		}
		catch (CoreException e)
		{
		TextPlugin.logError("ActionAnnotationModel.removeAllAnnotations", e); //$NON-NLS-1$
		}
	}
	/*
	 * @see AnnotationModel#removeAnnotation(Annotation, boolean)
	 */
	protected void removeAnnotation(
		Annotation annotation,
		boolean fireModelChanged)
	{
		Position position = getPosition(annotation);
		Object cached = fReverseMap.get(position);
		if (cached instanceof List)
		{
			List list = (List) cached;
			list.remove(annotation);
			if (list.size() == 1)
			{
				fReverseMap.put(position, list.get(0));
				list.clear();
			}
		}
		else if (cached instanceof Annotation)
		{
			fReverseMap.remove(position);
		}
		super.removeAnnotation(annotation, fireModelChanged);
	}

	public void updateMarkers(IDocument document) throws CoreException
	{

		final List deletedMarkers = new ArrayList();

		// update all markers with the positions known by the annotation model
		for (Iterator e = getAnnotationIterator(false); e.hasNext();)
		{
			Object o = e.next();
			if (o instanceof ActivityMarkerAnnotation)
			{
				ActivityMarkerAnnotation a = (ActivityMarkerAnnotation) o;
				if (!a.isRelevant())
				{
					if (!deletedMarkers.contains(a))
						deletedMarkers.add(a);
				}
			}
		}
          IWorkspace workspace = ResourcesPlugin.getWorkspace();
          IResourceRuleFactory ruleFactory = workspace.getRuleFactory();
          ISchedulingRule markerRule = ruleFactory.modifyRule(fInput.getFile());
    	  AbstractModelElementListener.setIgnoreResourceChangesMarker(true);
    	  try {
	          workspace.run(new IWorkspaceRunnable() {
	              public void run(IProgressMonitor monitor) throws CoreException {
	                  removeAnnotations(deletedMarkers, false, true);
	              }
	            }, markerRule, IWorkspace.AVOID_UPDATE, null);
    	  } finally {
    		  AbstractModelElementListener.setIgnoreResourceChangesMarker(false);
    	  }
		super.updateMarkers(document);
	}

	/*
	 * @see AbstractMarkerAnnotationModel#deleteMarkers(IMarker[])
	 */
  protected void deleteMarkers(final IMarker[] markers) throws CoreException {
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      IResourceRuleFactory ruleFactory = workspace.getRuleFactory();
      ISchedulingRule markerRule = ruleFactory.modifyRule(fInput.getFile());
	  workspace.run(new IWorkspaceRunnable() {
		  public void run(IProgressMonitor monitor) throws CoreException {
			  for (int i= 0; i < markers.length; ++i) {
					markers[i].delete();
				}
			}
		}, markerRule, IWorkspace.AVOID_UPDATE, null);
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

	public IMarker[] retrieveMarkers()
	{
		try
		{
			return fInput.getFile().findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
		}
		catch (CoreException e)
		{
			TextPlugin.logError("ActionAnnotationModel.retrieveMarkers", e); //$NON-NLS-1$
		}
		return null;
	}
    
    /**
     * Returns whether this model holds any problem annotations.
     */
    public boolean containsProblems()
    {
        return !fCollectedProblems.isEmpty();
    }
}
