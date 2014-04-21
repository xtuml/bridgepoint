//=====================================================================
//
//File:      $RCSfile: IdAssigner.java,v $
//Version:   $Revision: 1.18 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
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
     * This method allows one to configure the IdAssigner such that
     * the randomness is removed.  It is used by creation of the state
     * machine identifiers as well as by unit tests.
     */
    public void setSeed(long seed){
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

	public static UUID createRuntimeUUIDFromString(Object value, ComponentInstance_c ci) {
		// Runtime UUID's are created by Verifier. A remotely created UUID is one that
		// was created in raw java, or originated from a remote component. They are
		// stored as full UUID strings. Locally created UUID's have a particular format
		// starting with 'ideeda7a'.
		if (isUUID((String)value)) {
			// This is a remotely created UUID, convert and return it
			return UUID.fromString((String)value);
		}
		else {
			// This is a locally created UUID, synthesize it
			String id = String.format("%012x",Integer.parseInt((String)value));
			String comp_id = "00000000";
			if (ci != null) {
			  // Component Instance can be null when called from a bridge
			  comp_id = String.format("%08x",ci.Getenginenumber());
			}
			return UUID.fromString("1deeda7a-0000-" +
					         comp_id.substring(0,3) + "-" +
					         comp_id.substring(4) + "-" +
					         id);
		}
	}

	public static String convertFromRuntimeUUID(Object value, ComponentInstance_c ci) {
		if (value instanceof UUID) {
			String [] fields = ((UUID)value).toString().split("-");
			if (fields[0].equals("1deeda7a")) {
				// Its an xtUML originated UUID, parse it to see what to do
				// We have to ignore the most significant digit of field 2, else it won't fit in an
				// integer. A genuine xtUML originated UUID will never have these top bits set anyway.
				String field2 = fields[2].substring(1);
				int comp_id = Integer.parseInt(field2+fields[3], 16);
				if  (comp_id == 0 || comp_id == ci.Getenginenumber()) {
					// It's a UUID that originated from this running component
	                // or was passed out of a bridge call from any component. Turn
                    // it back into an integer
					return Integer.toString(Integer.parseInt(fields[4]));
			    }
			}
			// Either it's not an xtUML UUID, it's not local to this component and it didn't originate from a bridge
			return ((UUID)value).toString();
		}
		else {
			return null;
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
