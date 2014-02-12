package com.mentor.nucleus.bp.ui.text.annotation;

//====================================================================
//
// File: $RCSfile: ActivityMarkerAnnotation.java,v $
// Version: $Revision: 1.17 $
// Modified: $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.mentor.nucleus.bp.ui.text.TextPlugin;

/**
 * Marker-annotations that are specific to activities.  
 */
public class ActivityMarkerAnnotation extends MarkerAnnotation 
	implements IActivityProblemAnnotation, IAnnotationPresentation
{
	/**
     * The image to display for this annotation when it is relevant.
     * This image is shared by all annotations of the same type as
     * this one.
     * 
     * Methods of this class should call getNormalImage() to retrieve
     * this image, rather than accessing it directly.  
	 */
    private Image normalImage;
    
    /**
     * The image to display for this annotation when it is *not* relevant.
     * This image is shared by all annotations of the same type as
     * this one.
     * 
     * Methods of this class should call getGrayImage() to retrieve
     * this image, rather than accessing it directly.  
     */
    private Image grayImage;
    
    /**
	 * What kind of annotation this is (e.g. error, bookmark, ...).
	 */
	private ActivityAnnotationType type = ActivityAnnotationType.UNKNOWN;

	/**
	 * Whether this annotation still applies to its context (since the context might be edited such 
	 * that this annotation is no longer valid).
	 */
	private boolean relevant = true;

	/**
	 * Stores the normal versions of the various marker images, to be displayed 
	 * for relevant annotations.  The key is the hashcode of the
     * normal image's descriptor.  Only one image is stored for annotations
     * of a particular type.  
     * 
     * Methods of this class should call getNormalImageRegistry() to 
     * retrieve this registry, rather than accessing it directly. 
	 */
	private static ImageRegistry normalImageRegistry;

    /**
     * Stores the grayed versions of the various marker images, to be used 
     * for non-relevant annotations.  The key is the normal image's 
     * hashcode.  Only one image is stored for annotations
     * of a particular type.  
     * 
     * Methods of this class should call getGrayImageRegistry() 
     * to retrieve this registry, rather than accessing it directly.
     */
    private static ImageRegistry grayImageRegistry;

	/**
	 * The different kinds of images that may be displayed for an annotation
	 * of this class.
	 */
	private static class ImageType
	{
		public static ImageType 
			noImage = new ImageType(),
			originalMarkerImage = new ImageType(),
			quickFixImage = new ImageType(),
			quickFixErrorImage = new ImageType(),
			overlayImage = new ImageType(),
			grayImage = new ImageType(),
			breakpointImage = new ImageType();
	}

	/**
	 * The kind of image to be displayed for this annotation.
	 */
	private ImageType imageType = ImageType.noImage;

	private IActivityProblemAnnotation overlay;

	/**
	 * Constructor
	 */
	public ActivityMarkerAnnotation(IMarker marker)
	{
		super(marker);

		// detm this annotation's type from the given marker's type
		int severity = marker.getAttribute(IMarker.SEVERITY, -1);
		switch (severity) {
			case IMarker.SEVERITY_ERROR:
				type = ActivityAnnotationType.ERROR;
				break;
			case IMarker.SEVERITY_WARNING:
				type = ActivityAnnotationType.WARNING;
				break;
			case IMarker.SEVERITY_INFO:
				type = ActivityAnnotationType.INFO;
				break;
		}		
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#getMessage()
	 */
	public String getMessage()
	{
		IMarker marker = getMarker();
		if (marker == null || !marker.exists()) return "";  //$NON-NLS-1$
		else return marker.getAttribute(IMarker.MESSAGE, "");  //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#isProblem()
	 */
	public boolean isProblem()
	{
		return true;
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.source.Annotation#isPersistent()
     */
    public boolean isPersistent()
    {
        return true;
    }
    
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#isRelevant()
	 */
	public boolean isRelevant()
	{
		return relevant;
	}

	/**
	 * See field.
	 */
	public void setRelevant(boolean relevant)
	{
		this.relevant = relevant;
	}

	/**
	 * Overlays this annotation with the given actionAnnotation.
	 */
	public void setOverlay(IActivityProblemAnnotation actionAnnotation)
	{
		if (overlay != null) overlay.removeOverlaid(this);
		overlay = actionAnnotation;
		if (actionAnnotation != null) actionAnnotation.addOverlaid(this);
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#hasOverlay()
	 */
	public boolean hasOverlay()
	{
		return overlay != null;
	}

	/**
     * See field. 
	 */
    private Image getNormalImage(Display display)
    {
        // if we haven't already found this annotation's normal image 
        if (normalImage == null) {
            // if we can get the descriptor for the normal image shared 
            // by all annotations of the same type as this one
            IMarker marker = getMarker();
            if(marker.exists()){
                IWorkbenchAdapter adapter = (IWorkbenchAdapter)marker.getAdapter(
                        IWorkbenchAdapter.class);
                    ImageDescriptor descriptor = adapter.getImageDescriptor(marker);
                    if (descriptor != null) {
                        // get the shared normal image from the descriptor
                        ImageRegistry registry = getNormalImageRegistry(display);
                        String key = Integer.toString(descriptor.hashCode());
                        normalImage = (Image)registry.get(key);
                        
                        // if no such shared normal image exists
                        if (normalImage == null) {
                            // create one and store it in our table of such images
                            normalImage = descriptor.createImage();
                            registry.put(key, normalImage);
                        }
                    }
            }
        }
        
        return normalImage;
    }
    
    /**
     * See field. 
     */
    private Image getGrayImage(Display display)
    {
        // if we haven't already found this annotation's gray image 
        if (grayImage == null) {
        	// if there is a normal image for this annotation
            Image normalImage = getNormalImage(display);
            if (normalImage != null) {
	            // get the shared grayed image that corresponds to this  
	            // annotation's shared normal image
	            String key = Integer.toString(normalImage.hashCode());
	            ImageRegistry registry = getGrayImageRegistry(display);
	            grayImage = registry.get(key);
	            
	            // if no such shared grayed image exists
	            if (grayImage == null) {
	                // create one and store it in our table of such images
	                grayImage = new Image(display, normalImage, SWT.IMAGE_GRAY);
	                registry.put(key, grayImage);
	            }
            }
        }

        return grayImage;
    }
    
    /**
     * See field.
     */
    private ImageRegistry getNormalImageRegistry(Display display)
    {
        // create the registry if we haven't done so before
        if (normalImageRegistry == null) normalImageRegistry = new ImageRegistry(display);
        
        return normalImageRegistry;
    }

	/**
	 * See field.
	 */
	private ImageRegistry getGrayImageRegistry(Display display)
	{
		// create the registry if we haven't done so before
		if (grayImageRegistry == null) grayImageRegistry = new ImageRegistry(display);
		
		return grayImageRegistry;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#addOverlaid(com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation)
	 */
	public void addOverlaid(IActivityProblemAnnotation annotation)
	{
		// not supported
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#removeOverlaid(com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation)
	 */
	public void removeOverlaid(IActivityProblemAnnotation annotation)
	{
		// not supported
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#getOverlaidIterator()
	 */
	public Iterator getOverlaidIterator()
	{
		// not supported
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#getAnnotationType()
	 */
	public ActivityAnnotationType getAnnotationType()
	{
		return type;
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.source.Annotation#getType()
     */
    public String getType() 
    {
        return relevant ? type.toString() : TYPE_UNKNOWN;
    }
    
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#isError()
	 */
	public boolean isError()
	{
		return type == ActivityAnnotationType.ERROR;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.text.annotation.IActivityProblemAnnotation#isWarning()
	 */
	public boolean isWarning()
	{
		return type == ActivityAnnotationType.WARNING;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.MarkerAnnotation#getLayer()
	 */
	public int getLayer()
	{
		return IAnnotationPresentation.DEFAULT_LAYER;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.MarkerAnnotation#paint(org.eclipse.swt.graphics.GC, org.eclipse.swt.widgets.Canvas, org.eclipse.swt.graphics.Rectangle)
	 */
	public void paint(GC gc, Canvas canvas, Rectangle rectangle)
	{
        // detm the image to paint for this marker
        Display display = canvas.getDisplay();
        Image image = isRelevant() ? getNormalImage(display) : getGrayImage(display);

        // draw this annotation's image within the given rectangle
		if (image != null) {
			ImageUtilities.drawImage(image, gc, canvas, rectangle, SWT.CENTER, SWT.TOP);
		}else{
			try {
				TextPlugin.logError("Marker image not found for type: " + getMarker().getType(), new Exception("Marker image not found for type: " + getMarker().getType())); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (CoreException e) {
			}
		}
	}
}