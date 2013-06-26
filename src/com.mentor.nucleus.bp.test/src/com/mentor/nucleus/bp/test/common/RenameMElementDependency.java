package com.mentor.nucleus.bp.test.common;

import java.util.Vector;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;

public class RenameMElementDependency {

	public static Object[] getRenameDependentElements(Class baseType){
		Vector clsz = new Vector();
		
		//baseType is no doubt dependent on its rename
		clsz.add(baseType);
		//note nothing depends on Domain_c,Subsystem_c,DTP
		//...
		
		if (baseType == ModelClass_c.class) {
			clsz.add(InstanceStateMachine_c.class);
			clsz.add(ClassStateMachine_c.class);
		}
		return clsz.toArray();
	}
}
