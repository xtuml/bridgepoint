package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: ModelMergeViewer.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:34 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mentor.nucleus.bp.core.Ooaofooa;

public abstract class ModelMergeViewer extends Viewer {
	
	public static int LEFT = 0;
	public static int RIGHT = 1;
	public static int ANCESTOR = 2;
	
	private Object key;
	private int type;
	private Ooaofooa compareRoot;
	
	public abstract Control createControl(Composite parent);
	
	public Object getKey() {
		return key;
	}
	
	public int getType() {
		return type;
	}
	
	public Ooaofooa getCompareRoot() {
		return compareRoot;
	}
	
	public void setKey(Object key) {
		this.key = key;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setCompareRoot(Ooaofooa compareRoot) {
		this.compareRoot = compareRoot;
	}

	public abstract String getTitle();
}
