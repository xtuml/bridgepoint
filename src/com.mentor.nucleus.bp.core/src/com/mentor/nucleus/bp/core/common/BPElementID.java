//=====================================================================
//
//File:      $RCSfile: BPElementID.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 22:54:11 $
//
//(c) Copyright 2010-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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