//=====================================================================
//
//File:      $RCSfile: OoaofgraphicsBase.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 23:18:59 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.canvas;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ILogger;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.TraceLogger;

/**
 * Holds all the Java-only methods of Ooaofgraphics.  All instances of this class
 * must also be instances of Ooaofgraphics.  Externally, other classes
 * should only know about Ooaofgraphics, and not this class.  Call
 * this class's thisAsOoaofgraphics() method to cast an instance of 
 * this class to an Ooaofgraphics.   
 */
public class OoaofgraphicsBase extends ModelRoot
{
    public static ILogger log = new TraceLogger("com.mentor.nucleus.bp.ui.canvas/debug");

    protected static Map<String, OoaofgraphicsBase> rootInstanceMap = new Hashtable<String, OoaofgraphicsBase>();

    /**
     * Constructor 
     */
    protected OoaofgraphicsBase(String aRootId)
    {
    	super(aRootId);

    	rootInstanceMap.put(aRootId, this);
    }

    protected static ILogger getLog() {return log;}

    /** 
     * Changes this graphics-root's ID to the one given. Is meant to be called 
     * only on graphics-root instances that have previously had their ID's 
     * fields set directly.  
     */
    public void setId(String id)
    {
        // assign the new ID
        rootInstanceMap.remove(rootId);
        rootId = id;
        rootInstanceMap.put(rootId, this);
    }
    
    /**
     * Returns this instance of this class as an instance of Ooaofgraphics,
     * which it is required to also be.
     */
    protected Ooaofgraphics thisAsOoaofgraphics() {return (Ooaofgraphics)this;}
    
    public void delete() 
    {
    	instanceListMap.clear();
        rootInstanceMap.remove(rootId);
    }
	
    public void batchUnrelateAll() {
    	for(Object key : instanceListMap.keySet()) {
    		InstanceList instanceList = instanceListMap.get(key);
    		for(NonRootModelElement object : instanceList) {
    			object.batchUnrelate();
    		}
    	}
    }
    
    /**
	 * Returns an array of all the currently existing instances of this class.
	 */
	public static Ooaofgraphics[] getInstances()
	{
		return (Ooaofgraphics[])rootInstanceMap.values().toArray(
				new Ooaofgraphics[rootInstanceMap.size()]);
	}
    
    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.core.common.ModelRoot#getPersistenceFile()
     */
    public Object getPersistenceFile()
    {
        return Ooaofooa.getInstance(getId(), false).getPersistenceFile();
    }

    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.core.common.ModelRoot#isPersisting()
     */
    public boolean isPersisting()
    {
        return Ooaofooa.getInstance(getId(), false).isPersisting();
    }

	@Override
	public boolean persistEnabled() {
		Ooaofooa ooaroot = Ooaofooa.getInstance(getId());
		return ooaroot.persistEnabled();
	}
	
	public static void addInstance(Ooaofgraphics root) {
		rootInstanceMap.put(root.getId(), root);
	}
	
	public static Ooaofgraphics findInstance(String id) {
		return (Ooaofgraphics) rootInstanceMap.get(id);
	}
}

