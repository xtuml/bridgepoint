---

This work is licensed under the Creative Commons CC0 License

---

# Bridge By-Reference Parameter Restoration
### xtUML Project Implementation Note


1. Abstract
-----------
Bridge execution in Model Verifier is done by the execute method in the Bridge 
Invocation class. This method is setup to handle execution of both realized 
and modeled bridges, and would handle modification required for by-reference 
parameters before executing the bridge. As the object used for the 
by-reference parameter might be handled elsewhere in Model Verifier, the
modification needs to be undone after the bridge has been executed.

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #59](https://support.onefact.net/issues/59)  
<a id="2.2"></a>2.2 [Test Model](https://github.com/xtuml/models/tree/master/test/BridgeByRefParameterRestore)

3. Background
-------------
If OAL didn't exist for the bridge, the current implementation of Bridge 
Invocation.execute() would perform the modification of the by-reference 
parameter before checking to see if a realized bridge existed. If the realized
bridge didn't exist, the modification of the by-reference parameter was left in
place, so another usage of the parameter within the same action processing would
result in the parameter being of the wrong type.
This was discovered by a user and the issue [[2.1]](#2.1) was generated.  

4. Requirements
---------------
4.1 Ensure any modification to the by-reference parameter is undone before the
execute method is exited.  

5. Work Required
----------------
5.1 Move the modification of the by-reference parameter to after the check for
existence of a realized implementation of the bridge.
* This was the root cause of the issue. [[2.1]](#2.1)  

5.2 Remove the check for successful executaion of the bridge from around the
restore of the by-reference parameter to it's initial type.
* This check would also block the restoration, but in case of execution error,
    you'd probably never get to the check.  

6. Implementation Comments
--------------------------
If the design cannot be implemented as written or if it needs some modification,
enumerate the changes to the design in this section.

7. Unit Test
------------
Execute the function in the test model. [[2.2]](#2.2)

7.1 Launch test model in Verifier. No errors should be reported.  

8. User Documentation
---------------------

9. Code Changes
---------------
Fork/Repository: https://github.com/lwriemen/bridgepoint.git
Branch: 59_bridge_by_ref

9.1 Before  
<pre>

   while (not_empty parm)
     for each arg in args
       if (parm.Name == arg.Name)
         select one cval related by arg->V_VAL[R800];
         select one ctype related by parm->S_DT[R22];
         select any result related by stack_frame->I_VSF[R2951] where
                                              selected.Value_ID == cval.Value_ID;
         select one rtVal related by result->RV_RVL[R3305];
         isByRef = false;
         if (parm.By_Ref == 1)
           isByRef = true;
           newValue = GD::convertToBPDataType(type: ctype.Name,
                                                        value: rtVal.getValue());
           rtVal.setValue(value:newValue);
           cval.setValue(stack_frame_id: stack_frame.Stack_Frame_ID,
                                value: newValue, member_id:GD::NULL_UNIQUE_ID()
 
         end if;
 
         VM::addArgumentValue(value:rtVal.getValue(), type:ctype.Name,
                                                                 byRef: isByRef);
       end if;
     end for;
     select one parm related by parm->S_BPARM[R55.''precedes''];
   end while;
   // Look for a user''s class that matches the EE name
   if(ee.Realized_Class_Path != "")
     VM::addUserClassPath(System_ID:sys.Sys_ID, path:ee.Realized_Class_Path);
   else
     VM::setUserClassPath(System_ID:sys.Sys_ID);
   end if;
   success = VM::loadClass(system_id: sys.Sys_ID, name:ee.Key_Lett);
   if (success)

</pre>
9.2 After  
<pre>
  // Look for a user's class that matches the EE name
  if(ee.Realized_Class_Path != "")
    VM::addUserClassPath(System_ID:sys.Sys_ID, path:ee.Realized_Class_Path);
  else
    VM::setUserClassPath(System_ID:sys.Sys_ID);
  end if;
  success = VM::loadClass(system_id: sys.Sys_ID, name:ee.Key_Lett);
  if (success)
    while (not_empty parm)
      for each arg in args
        if (parm.Name == arg.Name)
          select one cval related by arg->V_VAL[R800];
          select one ctype related by parm->S_DT[R22];
          select any result related by stack_frame->I_VSF[R2951] where
                                               selected.Value_ID == cval.Value_ID;
          select one rtVal related by result->RV_RVL[R3305];
          isByRef = false;
          if (parm.By_Ref == 1)
            isByRef = true;
            newValue = GD::convertToBPDataType(type: ctype.Name,
                                                         value: rtVal.getValue());
            rtVal.setValue(value:newValue);
            cval.setValue(stack_frame_id: stack_frame.Stack_Frame_ID,
                                 value: newValue, member_id:GD::NULL_UNIQUE_ID());
          end if;
 
             VM::addArgumentValue(value:rtVal.getValue(), type:ctype.Name,
                                                                  byRef: isByRef);
           end if;
         end for;
         select one parm related by parm->S_BPARM[R55.'precedes'];
       end while;
</pre>
9.3 Before  
<pre>
    // Execute a method matching the bridge
    success = VM::execute(stack_frame_id:param.stack_frame_id, name: brg.Name);
    if (success)
      // Now convert any by ref parameters back to the original data type
      for each parm in parms
        for each arg in args
          if (parm.By_Ref == 1 and parm.Name == arg.Name)
            select one cval related by arg->V_VAL[R800];
            select one ctype related by parm->S_DT[R22];
            select any result related by stack_frame->I_VSF[R2951] where
                                           selected.Value_ID == cval.Value_ID;
            select one rtVal related by result->RV_RVL[R3305];
            select one udt related by ctype->S_UDT[R17]; // is it core?
            if (not_empty udt)// if not, check user data type
              id = udt.getCoreType();
              select any dt_pe from instances of PE_PE where (selected.Element_ID == id);
              select one ctype related by dt_pe->S_DT[R8001];
            end if;
            newValue = GD::convertFromBPDataType(type: ctype.Name, value:
                                                            rtVal.getValue());
            cval.setValue(stack_frame_id: stack_frame.Stack_Frame_ID,
                             value: newValue, member_id:GD::NULL_UNIQUE_ID());
          end if;
        end for;
      end for;
    end if;
</pre>  
9.4 After  
<pre>
    // Execute a method matching the bridge
    success = VM::execute(stack_frame_id:param.stack_frame_id, name: brg.Name);
    // Now convert any by ref parameters back to the original data type
    for each parm in parms
      for each arg in args
        if (parm.By_Ref == 1 and parm.Name == arg.Name)
          select one cval related by arg->V_VAL[R800];
          select one ctype related by parm->S_DT[R22];
          select any result related by stack_frame->I_VSF[R2951] where
                                           selected.Value_ID == cval.Value_ID;
          select one rtVal related by result->RV_RVL[R3305];
          select one udt related by ctype->S_UDT[R17]; // is it core?
          if (not_empty udt)// if not, check user data type
            id = udt.getCoreType();
            select any dt_pe from instances of PE_PE where (selected.Element_ID == id);
            select one ctype related by dt_pe->S_DT[R8001];
          end if;
          newValue = GD::convertFromBPDataType(type: ctype.Name, value:
                                                            rtVal.getValue());
          cval.setValue(stack_frame_id: stack_frame.Stack_Frame_ID,
                             value: newValue, member_id:GD::NULL_UNIQUE_ID());
        end if;
      end for;
    end for;
</pre>  

End
---

