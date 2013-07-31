---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Issues resolved during January and February 2013 Onsite visits with customers
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes bugs fixes put in place and tested at customer sites.

2. History
----------

3. Document References
----------------------
[1] ClearQuest Issue dts0100722456
   
	Breakpoint still around after removal
    
[2] ClearQuest Issue dts0100926603 

	Upgrade to generic packages fails with 
                                     error
[3] ClearQuest Issue dts0100941736 

	Element creation errors and canvas refresh errors while working with 
	pessimistic RCS (usability issue)
	
[4] ClearQuest Issue dts0100941741 

	Unsafe 'select any' statements in Verifier relate logic
	
[5] ClearQuest Issue dts0100941910 

	Missing interface references not being created by reconciler
	
[6] ClearQuest Issue dts0100943481 

	Cannot draw associative to imported class from outside the current package
	
[7] ClearQuest Issue dts0100841787 
	
	Verifier check gives incorrect error after relating over association to 
	many side using an associative class
	
[8] ClearQuest Issue dts0100926328 

	Pasting a Constant Specification with a project causes other projects to be 
	modified
	
[9] ClearQuest Issue dts0100942085 
	
	All files under a component are checked out when an interface is added to 
	a component.
	
[10] ClearQuest Issue dts0100942162 

	NPE occurs when performing an xtUML search
	
[11] ClearQuest Issue dts0100729739 

	Display the port name instead of the interface name in the explorer view 
	for a referenced component


4. Background
-------------
Between January 19, 2013 and February 2, 2013 Bob Mulvey visited several 
customers in Europe and worked together with them and the rest of the 
BridgePoint team to isolate and/or fix many problems.  This note captures a 
large group of these fixes which are small enough that they are being promoted 
together.

5. Requirements
---------------
5.1 [1] Breakpoint still around after removal
    
	The BPEventBreakpoint class needs to override Breakpoint.delete and 
	actually remove the modeled breakpoint so:
		
		    public void delete() throws CoreException {
		        deleteTargetBreakpoint();
		        super.delete();
		    }
		    
	This removes the breakpoint implementation and then delegates up to the 
	supertype to get the UI elements taken care of. This was tested and fixes 
	the problem described by the customer.
	
	There were additional places where this same problem could occur.  To 
	resolve this for all situations, the above override of the delete	
	implementation was added to the BPBreakpoint.java abstract class.	
	Since deleteTargetBreakpoint() is an interface in the IBPBreakpoint class 
	which is implemented but not defined by this BPBreakpoint class, it shall 
	get called by the appropriate derived breakpoint class for each case.	
		
5.2 [2] Upgrade to generic packages fails with error
	    
    The problem is in 
    bp/io/mdl/upgrade/GenericPacageUpgrade.java::upgradeComponent(). In 
    revision 1.8 it is on line 2696.  The code looks like this:

	compGe = Graphelement_c.getOneDIM_GEOnR23(childGe);
	point.x = (int) compGe.getPositionx();

	The problem is that the childGe variable is null.  Note that 
	code AFTER this NPE actually checks childGe for null, but this spot does 
	not.   The proposed solution is to use the coordinates from the element 
	as it existed under the formalized domain (variable elem) as a default and 
	then check for null before using compGe coordinates.

	The situation here is that a formalized component is being migrated to 
	generic packages and the tool is migrating graphics from the elements that 
	were in the domain the component was formalized to.   This problem is 
	difficult to reproduce, but the above fix did resolve it onsite.

    
5.3 [3] Element creation errors and canvas refresh errors while 
        working with pessimistic RCS (usability issue)
    
    TODO - Describe
    
5.4 [4] Unsafe 'select any' statements in Verifier relate logic
    
    In Relate Using.execute, there are two groups of select statements that 
    start with an unguarded select any statement. These groups start at 
    lines 128 and 144 in revision 1.17 of the file.

    The fix is to add 'where selected.Rel_ID == relationship_spec.Rel_ID' to 
    each of the select any statements.

	Note that instead of using relationship_spec.Rel_ID as was used while
	onsite, srcIil.Rel_ID and dstIil.Rel_ID were used because this was deemed
	more appropriate after encountering [7], which is a different issue but 
	requires the same fix.
    
5.5 [5] Missing interface references not being created by reconciler
    
    TODO - Describe
    
5.6 [6] Cannot draw associative to imported class from outside the current 
        package
    
	In Simple Association.migrateToLinked() the using class is identified 
	using the line:
	
	   // see if this is in a package (isInGenericPackage)
	   select any fromClass related by self->R_REL[R206]->PE_PE[R8001]->EP_PKG[R8000]->PE_PE[R8000]->O_OBJ[R8001]  where (selected.Obj_ID == param.using);
	
	This is incorrect, since it assumes that the class exists within the same 
	package as the association being migrated. Instead it should be:
	
	   // see if this is in a package (isInGenericPackage)
	   select any fromClass related by self->R_REL[R206]->PE_PE[R8001]->EP_PKG[R8000]->S_SYS[R1405]->EP_PKG[R1405]->PE_PE[R8000]->O_OBJ[R8001]  where (selected.Obj_ID == param.using);
	
	Making this change allows the resulting association to be formalized and 
	persisted without errors.    
    
5.7 [7] Verifier check gives incorrect error after relating over association 
        to many side using an associative class
    
	Where verifier instance population checks are on, there is a problem in a 
	customer model where relating a new class instance into an association 
	that contains an associative class causes the error:
	 
	"Warning relate using: Already related on R5 (phrase) at rr: line: xx at tt:Pacjage::The_Cpm::Functions::rr "
	
	This is very much like DR841787 which was marked fixed in BP3.6.0.  
	However, it does not happen all the time.
	
	This problem was traced to a bug in Relate Using::execute.
	There are 2 blind selects:
	line 128: select any classInAssoc related by srcClass->R_OIR[R201] where selected.Rel_ID == srcIil.Rel_ID;
	line 144: select any classInAssoc related by dstClass->R_OIR[R201] where selected.Rel_ID == dstIil.Rel_ID;

    Note that this is the same change as was required to resolve 
    dts0100941741 [4].
	
   
5.8 [8] Pasting a Constant Specification with a project causes other projects 
        to be modified
    
	During the paste of the constant, persistence is being triggered, and the 
	in-memory model-update changes are being persisted.   There is NOT 
	corruption in the models (or any weird data being put in these other 
	models).  Of course persistence should not be getting triggered in these 
	other models.
	
	The problem is directly related to RGO proxy export.  RGO proxy export 
	should not be supported for constants.  The fix, which was tested on-site
	with the customer, is to remove the special 
	case in io.core/arc/export_functions.inc:
	
		.if((rel.Numb == 1500) and (obj.Name == "Data Type"))
	
	There are a couple there that look similar which is why it was likely 
	added.  However, for constants not even that type of special case is 
	required.

5.8.1 Further investigation into why the original change was made.
	
	I did some due-diligence to see why this was introduced, to assure the 
	change is safe.  I found that it was added for this:
	
		dts0100530239-Support for Constants still not in place in BridgePoint
		io.core/arc/export_functions.inc v1.25.6.1
	
		Check-in comment:
		Job:dts0100530239
		Add special case to make sure we always export datatype proxies when 
		exporting a constant spec.
	
		Documentation:
		<CVS Documentation_archive>/20100712/technical/notes/dts0100530239*
	
	The following chat explains this further and explains why the change called 
	out above is safe. 
	
	[12:33:54 PM] Bob: I am not sure why this was added?   Constants can only 
	be of type integer, real, string, right?    This means there is never a 
	need to export the proxy in this case, and therefore that special-case can 
	indeed be safely removed.
	[12:33:59 PM] Bob: Agreed?
	
	[12:36:09 PM] Travis London: No it will in fact need it, for resolution 
	purposes.  Though only when it exists in a project that is not yet global.  
	The reason the special case was added is because the constant can live in 
	the same package as the dt, which would then prevent the proxy for being 
	exported in the normal case.  So I would suggest leaving the special case 
	in, but removing the incorrect call to export the RGOs.
	
	[12:48:27 PM | Edited 12:54:03 PM] Bob: Generic package models all have 
	global data types.  In the "UpgradeToGenericsAction.java" as well as new 
	project creation we take this action to UseGlobals. In this version we are 
	now working on, SP models are no longer allowed to exist.   Therefore it is 
	safe to simply delete this specialized case now.   Correct?
	
	[1:03:16 PM] Travis London: As long as old models are automatically 
	converted to generics, or copy/paste is disabled from a non-global 
	package to a global package.


    
5.9 [9] All files under a component are checked out when an interface is added 
        to a component.
    
	TransactionManager.java revision 1.5.10.1
	
		Address issue where multiple children components are considered modified, when they truly are not.
		
		Address multiple project check out issues.
    
5.10 [10] NPE occurs when performing an xtUML search
    
    TODO - Describe
    
5.11 [11] Display the port name instead of the interface name in the explorer 
         view for a referenced component
    
    TODO - Describe
    

6. Work Required
----------------

7. Implementation Comments
--------------------------
None

8. Unit Test
------------
8.1 Each issue contains a manual test that was performed while onsite.  Each of
    these tests shall now pass.
    
9. Code Changes
---------------
Branch name: <enter your branch name here>
Enumerate all the files that have been modified while you worked on this issue.

End
---

