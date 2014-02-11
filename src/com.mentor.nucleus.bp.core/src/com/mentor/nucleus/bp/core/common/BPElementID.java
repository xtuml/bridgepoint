//=====================================================================
//
//File:      $RCSfile: BPElementID.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 22:54:11 $
//
//(c) Copyright 2010-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.util.UUID;

public class BPElementID implements java.lang.Comparable<Object> {
  private Object [] mkey;
  public BPElementID(String key) {
    String[] ids = key.split("%");
    mkey = new UUID[ids.length];
    for (int i = 0; i < ids.length; i++) {
      mkey[i] = IdAssigner.createUUIDFromString(ids[i]);
    }
  }
  public BPElementID(Object[] ids) {
    mkey = ids;
  }
  @Override
  public int compareTo(Object arg0) {
    if (arg0 instanceof UUID) {
      if (mkey.length == 1 && mkey[0] instanceof UUID) 
        return ((UUID)mkey[0]).compareTo((UUID)arg0);
    }
    if (arg0 instanceof BPElementID) {
      if (((BPElementID)arg0).mkey.length == mkey.length) {
        for (int i = 0; i < mkey.length; i++) {
          if (mkey[i] instanceof UUID && ((BPElementID)arg0).mkey[i] instanceof UUID)
          if (((UUID)mkey[i]).compareTo((UUID)((BPElementID)arg0).mkey[i]) != 0) {
            return ((UUID)mkey[i]).compareTo((UUID)((BPElementID)arg0).mkey[i]);
          }
        }
        return 0;
      }
      return mkey.length - ((BPElementID)arg0).mkey.length;
    }
    return -1;
  }
  public int hashCode() {
	  int hash = 1;
	  if(mkey != null) {
		  for (int i = 0; i < mkey.length; i ++) {
			  hash = hash + 17 * mkey[i].hashCode();
        }
		  }
	  return hash;
	  }
  
  public boolean equals(Object arg0) {
    if (arg0 instanceof BPElementID) {
      if (((BPElementID)arg0).mkey.length == mkey.length) {
        for (int i = 0; i < mkey.length; i++) {
          if (mkey[i].equals(((BPElementID)arg0).mkey[i]) == false) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
  
	public int size() {
		return mkey.length;
	}

}