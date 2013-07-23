//=====================================================================
//
//File:      $RCSfile: IdAssigner.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.core.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * This class serves as single place for all UUID related functions. 
 */
public class IdAssigner
{
	private SecureRandom randomGenerator = null; 
	
	public static final UUID MAX_UUID = new UUID(0xffffffffffffffffL, 0xffffffffffffffffL);
	public static final UUID NULL_UUID = new UUID(0, 0);
	
    /**
     * The instances of this class that been created so far.  This is here
     * because this class has methods that need to operate on all the 
     * instances, such as seedForAllInstances(). 
     */
    private static List<IdAssigner> instances = new ArrayList<IdAssigner>();
    
    /**
     * The seed supplied to the last call made to seedForAllInstances(), to 
     * be assigned to any future instances of this class that are created.
     */
    private static long seedForAllInstances = 0;

	public long seed = 0;
    
    /**
     * Constructor.
     */
    public IdAssigner()
    {
        instances.add(this);
        
        // if a seed for all instances has been set
        if (seedForAllInstances != 0) {
            // apply to this instance, as well
            setSeed(seedForAllInstances);
        }
    }
    
    public UUID createUUID(){
    	if(randomGenerator != null){
    		return new UUID(randomGenerator.nextLong(), randomGenerator.nextLong());
    	}
		return UUID.randomUUID();
	}
    
    /*
     * This method is only provide for unit tests so that we can have predicted
     * series of UUID often required in unit tests which determine the name of
     * expected results by the identifer of given model element. 
     */
    private void setSeed(long seed){
		this.seed = seed;
    	if(seed == 0){
    		randomGenerator = null;
    	}else{
    		try {
				randomGenerator = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				CorePlugin.logError("Unable to locate SHA1PRNG algorithm.", e);
			}
			if(randomGenerator == null) {
				randomGenerator = new SecureRandom();
			}
    		randomGenerator.setSeed(seed);
    	}
    }
    
    public static final UUID createUUIDFromString(String id){
    	return createUUIDFromString(id, false);
    }
    
    public static final UUID createUUIDFromString(String id, boolean stripDomainCode){
    	if(isUUID(id)){
    		return UUID.fromString(id);
    	}else{
    		if (id == null) {
    			id = "0";
    		}
    		Long longval = 0L;
    		try {
    			longval = Long.parseLong(id);
    		} catch (NumberFormatException nfe) {   
    			CorePlugin.logError(
    					"Expected a numeric UUID value and encountered a non-numeric.  This value is being changed to 0.",
    					nfe);
    		}
    		
    		if(stripDomainCode){
        		return new UUID(0, longval & 0xfffffff);
    		}else{
        		return new UUID(0, longval);
    		}
    	}
    }
	
    public static final UUID preprocessUUID(UUID id){
    	if(NULL_UUID.equals(id)){
    		return NULL_UUID;
    	}
    	return id;
    }	
	
    public static final boolean isUUID(String idString){
    	//  The format is: 00000000-0000-0000-0000-000000000000
    	return (idString != null && (idString.length() == 36 && 
    			   idString.charAt(8) == '-' &&
    			   idString.charAt(13) == '-' &&
    			   idString.charAt(18) == '-' &&
    			   idString.charAt(23) == '-' ));
    }
    
    public static final boolean isUUIDDummy(UUID id){
    	return (id.getMostSignificantBits() == 0);
    }
    
    /**
     * Sets the seed of all instances of this class that have been created,
     * which is required by test cases so they may have repeatable results.
     * Future instances of this class will also be started with the given 
     * seed.  
     */
    public static void setSeedOfAllInstances(long seed) 
    {
        seedForAllInstances = seed;
        
        for (IdAssigner instance: instances) instance.setSeed(seed);
    }
    public static void setSeedOfAllInstances(long seed, boolean allocateHeterogeneousSeeds) 
    {
    	if (allocateHeterogeneousSeeds) {
          seedForAllInstances = seed;
          for (IdAssigner instance: instances) instance.setSeed(seed++);
    	}
    	else {
    		setSeedOfAllInstances(seed);
    	}
    }
}
