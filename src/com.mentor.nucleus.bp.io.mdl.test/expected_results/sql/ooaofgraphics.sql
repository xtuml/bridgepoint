-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (192646601,
	'ooaofgraphics',
	'========================================================================

(c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.

========================================================================
Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy
of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
License for the specific language governing permissions and limitations under
the License.
========================================================================

This is the graphics domain for the Tiger project.

The processing in this model describes the activities
required for model diagram display and editing.

Import:com.mentor.nucleus.bp.core.*
-------------------------------------------------------------------------------------------------------------
Notify_Changes:false
',
	0,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	192646601,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	192646601,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	192646601,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	192646601,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	192646601,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	192646601,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	192646601,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	192646601,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	192646601,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	192646601,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	192646601,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	192646601,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	192646601,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	192646601,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	192646601,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	192646601,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524305,
	192646601,
	'GCDelegate',
	'');
INSERT INTO S_UDT
	VALUES (524306,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524306,
	192646601,
	'instance',
	'');
INSERT INTO S_UDT
	VALUES (524307,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524307,
	192646601,
	'class',
	'');
INSERT INTO S_EDT
	VALUES (524308);
INSERT INTO S_ENUM
	VALUES (524289,
	'Center_in_X',
	'Places the given text block on the center line of the symbol
or centered on the x coordinate passed.',
	524308);
INSERT INTO S_ENUM
	VALUES (524290,
	'Left',
	'Places the given text block so that it starts at the left side of the symbol
or starts at the x coordinate passed.',
	524308);
INSERT INTO S_ENUM
	VALUES (524291,
	'Right',
	'Places the given text block so that it ends at the right side of the symbol
or so that it ends at the x coordinate passed.',
	524308);
INSERT INTO S_ENUM
	VALUES (524292,
	'Bottom',
	'Places the given text block at the bottom of the symbol.',
	524308);
INSERT INTO S_ENUM
	VALUES (524293,
	'Center',
	'Places the given text block in the center of the symbol.',
	524308);
INSERT INTO S_DT
	VALUES (524308,
	192646601,
	'Justification',
	'Translate:false');
INSERT INTO S_EDT
	VALUES (524309);
INSERT INTO S_ENUM
	VALUES (524294,
	'X',
	'Represents the X axis.',
	524309);
INSERT INTO S_ENUM
	VALUES (524295,
	'Y',
	'Represents the Y axis.',
	524309);
INSERT INTO S_DT
	VALUES (524309,
	192646601,
	'Axis',
	'Specifies the X or Y axis.');
INSERT INTO S_EDT
	VALUES (524310);
INSERT INTO S_ENUM
	VALUES (524296,
	'Box',
	'Defines a plain unadorned box symbol.',
	524310);
INSERT INTO S_ENUM
	VALUES (524297,
	'RoundBox',
	'Defines a box with rounded corners.',
	524310);
INSERT INTO S_ENUM
	VALUES (524298,
	'Folder',
	'Defines a rectangle with a tab above it on the left hand end.',
	524310);
INSERT INTO S_ENUM
	VALUES (524299,
	'Broken',
	'Defines a broken (or dashed) line.',
	524310);
INSERT INTO S_ENUM
	VALUES (524300,
	'Solid',
	'Defines a solid line, the opposite of Broken.',
	524310);
INSERT INTO S_ENUM
	VALUES (524301,
	'Triangle',
	'Defines an open unfilled Triangle.',
	524310);
INSERT INTO S_ENUM
	VALUES (524302,
	'OpenArrow',
	'Defines an unfilled arrow head that is symmetrical.',
	524310);
INSERT INTO S_ENUM
	VALUES (524303,
	'FilledCircle',
	'Defines a solid circle filled with the foreground color.',
	524310);
INSERT INTO S_ENUM
	VALUES (524304,
	'BullsEye',
	'Defines a solid circle surrounded by a bold circular outline.',
	524310);
INSERT INTO S_ENUM
	VALUES (524305,
	'None',
	'An unadorned line end.',
	524310);
INSERT INTO S_DT
	VALUES (524310,
	192646601,
	'Style',
	'Translate:false');
INSERT INTO S_EDT
	VALUES (524311);
INSERT INTO S_ENUM
	VALUES (524306,
	'Start',
	'Defines the floating text at the start of a connector.',
	524311);
INSERT INTO S_ENUM
	VALUES (524307,
	'End',
	'Defines the floating text at the end of a connector.',
	524311);
INSERT INTO S_ENUM
	VALUES (524308,
	'Middle',
	'Defines the floating text at the middle of a connector.',
	524311);
INSERT INTO S_ENUM
	VALUES (524309,
	'Start_Fixed',
	'Defines the fixed position text at the start of a connector.',
	524311);
INSERT INTO S_ENUM
	VALUES (524310,
	'End_Fixed',
	'Defines the fixed position text at the end of a connector.',
	524311);
INSERT INTO S_ENUM
	VALUES (524311,
	'None',
	'Specifies that no end has been selected or is meaningful.',
	524311);
INSERT INTO S_DT
	VALUES (524311,
	192646601,
	'End',
	'Translate:false');
INSERT INTO S_EDT
	VALUES (524312);
INSERT INTO S_ENUM
	VALUES (524312,
	'North',
	'Defines the direction above a symbol.',
	524312);
INSERT INTO S_ENUM
	VALUES (524313,
	'South',
	'Defines the direction below a symbol.',
	524312);
INSERT INTO S_ENUM
	VALUES (524314,
	'East',
	'Defines the direction to the right of a symbol.',
	524312);
INSERT INTO S_ENUM
	VALUES (524315,
	'West',
	'Defines the direction to the left of a symbol.',
	524312);
INSERT INTO S_ENUM
	VALUES (524316,
	'None',
	'Specifies that no direction has been selected or is meaningful.',
	524312);
INSERT INTO S_DT
	VALUES (524312,
	192646601,
	'Direction',
	'');
INSERT INTO S_UDT
	VALUES (524313,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524313,
	192646601,
	'Color',
	'');
INSERT INTO S_UDT
	VALUES (524314,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524314,
	192646601,
	'GC',
	'');
INSERT INTO S_UDT
	VALUES (524315,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524315,
	192646601,
	'Object',
	'');
INSERT INTO S_EDT
	VALUES (524316);
INSERT INTO S_ENUM
	VALUES (524317,
	'Default',
	'Represents the default black arrow mouse cursor (or whatever
else is the default for the host OS).',
	524316);
INSERT INTO S_ENUM
	VALUES (524318,
	'OpenHand',
	'An open hand symbol indicating the canvas is ready to be moved.',
	524316);
INSERT INTO S_ENUM
	VALUES (524319,
	'ClosedHand',
	'An closed hand symbol indicating the canvas is being moved.',
	524316);
INSERT INTO S_ENUM
	VALUES (524320,
	'Connector Node Handle',
	'An arrow pointing into a circle image that indicates that
the cursor is hovering over a waypoint.',
	524316);
INSERT INTO S_DT
	VALUES (524316,
	192646601,
	'CursorType',
	'');
INSERT INTO S_UDT
	VALUES (524317,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524317,
	192646601,
	'Class',
	'');
INSERT INTO S_EDT
	VALUES (524318);
INSERT INTO S_ENUM
	VALUES (524321,
	'NE',
	'Defines the north east vertex.',
	524318);
INSERT INTO S_ENUM
	VALUES (524322,
	'NW',
	'Defines the north west vertex.',
	524318);
INSERT INTO S_ENUM
	VALUES (524323,
	'SW',
	'Defines the south west vertex.',
	524318);
INSERT INTO S_ENUM
	VALUES (524324,
	'SE',
	'Defines the south east vertex.',
	524318);
INSERT INTO S_ENUM
	VALUES (524325,
	'None',
	'Specifies that no vertex has been selected or is meaningful.',
	524318);
INSERT INTO S_DT
	VALUES (524318,
	192646601,
	'Vertex',
	'');
INSERT INTO S_UDT
	VALUES (524319,
	524301,
	0);
INSERT INTO S_DT
	VALUES (524319,
	192646601,
	'operation',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	192646601,
	'max',
	'Returns the larger of the two values passed.',
	'if (param.a > param.b)
  return param.a;
else
  return param.b;
end if;',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524456,
	524289,
	'a',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524457,
	524289,
	'b',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524290,
	192646601,
	'min',
	'Returns the smaller of the two values passed.',
	'if (param.a < param.b)
  return param.a;
else
  return param.b;
end if;',
	524291,
	1);
INSERT INTO S_SPARM
	VALUES (524458,
	524290,
	'a',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524459,
	524290,
	'b',
	524291,
	0);
INSERT INTO S_EE
	VALUES (524292,
	'Client',
	'Represents the interface with the client domain.
----------------------------------------------------------
Import:com.mentor.nucleus.bp.core.*',
	'CL',
	192646601);
INSERT INTO S_BRG
	VALUES (524354,
	524292,
	'getConnectorText',
	'Translate:native',
	0,
	524293,
	'    return s_invoke_lii(From, "Get_connector_text", End_ooa_id, End_ooa_type, At);
  }
  //
  // s_invoke_lii - invoke a method that takes one long and two int parameters and returns a string
  //
  private static String s_invoke_lii(Object target, String methodName, long arg1, int arg2, int arg3) {
    if (target != null) {
      try {
         Class [] argTypes = new Class[3];
         argTypes[0] = long.class;
         argTypes[1] = int.class;
         argTypes[2] = int.class;
         Method method = target.getClass().getMethod(methodName, argTypes);
         Object [] args = new Object[3];
         args[0] = new Long(arg1);
         args[1] = new Integer(arg2);
         args[2] = new Integer(arg3);
         Object result = method.invoke(target, args);
         return (String)result;
       }
      catch (NoSuchMethodException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " not found: " + e);
      }
      catch (IllegalAccessException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " cannot be accessed: " + e);
      }
      catch (InvocationTargetException e) {
         Ooaofgraphics.log.println("Client method, " + methodName + " is illegal target: " + e);
      }
    }
    return "";',
	0);
INSERT INTO S_BPARM
	VALUES (524460,
	524354,
	'from',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524461,
	524354,
	'end_OOA_ID',
	524294,
	0);
INSERT INTO S_BPARM
	VALUES (524462,
	524354,
	'end_OOA_Type',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524463,
	524354,
	'at',
	524311,
	0);
INSERT INTO S_BRG
	VALUES (524355,
	524292,
	'getCompartmentText',
	'Translate:native',
	0,
	524293,
	'    return s_invoke_iii(From, "Get_compartment_text", At, Compartment_id, Entry_id);
  }
  //
  // s_invoke_iii - invoke a method that takes three int parameters and returns a string
  //
  private static String s_invoke_iii(Object target, String methodName, int arg1, int arg2, int arg3) {
    if (target != null) {
      try {
        Class [] argTypes = new Class[3];
        argTypes[0] = int.class;
        argTypes[1] = int.class;
        argTypes[2] = int.class;
        Method method = target.getClass().getMethod(methodName, argTypes);
        Object [] args = new Object[3];
        args[0] = new Integer(arg1);
        args[1] = new Integer(arg2);
        args[2] = new Integer(arg3);
        Object result = method.invoke(target, args);
        return (String)result;
      }
      catch (NoSuchMethodException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " not found: " + e);
      }
      catch (IllegalAccessException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " cannot be accessed: " + e);
      }
      catch (InvocationTargetException e) {
         Ooaofgraphics.log.println("Client method, " + methodName + " is illegal target: " + e);
      }
    }
    return "";
',
	0);
INSERT INTO S_BPARM
	VALUES (524464,
	524355,
	'from',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524465,
	524355,
	'compartment_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524466,
	524355,
	'entry_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524467,
	524355,
	'at',
	524308,
	0);
INSERT INTO S_BRG
	VALUES (524356,
	524292,
	'getCompartments',
	'Translate:native
Import:java.lang.reflect.*',
	0,
	524291,
	'    return i_invoke(From, "Get_compartments");
  }
  //
  // i_invoke - invoke a method that takes no parameters and returns an int
  //
  private static int i_invoke(Object target, String methodName) {
    if (target != null) {
      try {
        Method method = target.getClass().getMethod(methodName, new Class[0]);
        Object result = method.invoke(target, new Object[0]);
        return ((Integer)result).intValue();
      }
      catch (NoSuchMethodException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " not found: " + e);
      }
      catch (IllegalAccessException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " cannot be accessed: " + e);
      }
      catch (InvocationTargetException e) {
         Ooaofgraphics.log.println("Client method, " + methodName + " is illegal target: " + e);
      }
    }
    return 0;',
	0);
INSERT INTO S_BPARM
	VALUES (524468,
	524356,
	'from',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524357,
	524292,
	'isEmpty',
	'Translate:native',
	0,
	524290,
	'  return Element == null;',
	0);
INSERT INTO S_BPARM
	VALUES (524469,
	524357,
	'element',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524358,
	524292,
	'getInstanceFromOOA_ID',
	'Translate:native',
	0,
	524306,
	'if ( Ooa_type == 1 ) {
  // select any represents from instances of S_DOM where (selected.Dom_ID == self.OOA_ID)
  class Domain_test20_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((Domain_c)selected).getDom_id() == Ooa_id ;
    }
  }
  return Domain_c.DomainInstance(new Domain_test20_c()) ;
}
else if ( Ooa_type == 11 ) {
  // select any represents from instances of S_SS where (selected.SS_ID == self.OOA_ID)
  class Subsystem_test21_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((Subsystem_c)selected).getSs_id() == Ooa_id ;
    }
  }
  return Subsystem_c.SubsystemInstance(new Subsystem_test21_c()) ;
}
else if ( Ooa_type == 12 ) {
  // select any represents from instances of S_EE where (selected.EE_ID == self.OOA_ID)
  class ExternalEntity_test21_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((ExternalEntity_c)selected).getEe_id() == Ooa_id ;
    }
  }
  return ExternalEntity_c.ExternalEntityInstance(new ExternalEntity_test21_c()) ;
}
else if ( Ooa_type == 21 ) {
  // select any represents from instances of O_OBJ where (selected.Obj_ID == self.OOA_ID)
  class ModelClass_test21_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((ModelClass_c)selected).getObj_id() == Ooa_id ;
    }
  }
  return ModelClass_c.ModelClassInstance(new ModelClass_test21_c()) ;
}
else if ( Ooa_type == 23 ) {
  // select any represents from instances of O_IOBJ where (selected.IObj_ID == self.OOA_ID)
  class ImportedClass_test21_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((ImportedClass_c)selected).getIobj_id() == Ooa_id ;
    }
  }
  return ImportedClass_c.ImportedClassInstance(new ImportedClass_test21_c()) ;
}
else if ( Ooa_type == 24 ) {
  // select any represents from instances of R_REL where (selected.Rel_ID == self.OOA_ID)
  class Association_test21_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((Association_c)selected).getRel_id() == Ooa_id ;
    }
  }
  return Association_c.AssociationInstance(new Association_test21_c()) ;
}
else if ( Ooa_type == 40 ) {
  // select any represents from instances of SM_SM where (selected.SM_ID == self.OOA_ID)
  class StateMachine_test22_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((StateMachine_c)selected).getSm_id() == Ooa_id ;
    }
  }
  return StateMachine_c.StateMachineInstance(new StateMachine_test22_c()) ;
}
else if ( Ooa_type == 41 ) {
  // select any represents from instances of SM_STATE where (selected.SMStt_ID == self.OOA_ID)
  class StateMachineState_test22_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((StateMachineState_c)selected).getSmstt_id() == Ooa_id ;
    }
  }
  return StateMachineState_c.StateMachineStateInstance(new StateMachineState_test22_c()) ;
}
else if ( Ooa_type == 42 ) {
  // select any represents from instances of SM_TXN where (selected.SMTxn_ID == self.OOA_ID)
  class Transition_test22_c implements com.mentor.nucleus.bp.core.ClassQueryInterface_c {
    public boolean evaluate (Object selected) {
      return ((Transition_c)selected).getTrans_id() == Ooa_id ;
    }
  }
  return Transition_c.TransitionInstance(new Transition_test22_c()) ;
}
return null;
',
	0);
INSERT INTO S_BPARM
	VALUES (524470,
	524358,
	'OOA_Type',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524471,
	524358,
	'OOA_ID',
	524294,
	0);
INSERT INTO S_BRG
	VALUES (524359,
	524292,
	'getShapeStyle',
	'Translate:native',
	0,
	524310,
	'    return i_invoke(From, "Get_style");
',
	0);
INSERT INTO S_BPARM
	VALUES (524472,
	524359,
	'from',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524360,
	524292,
	'isSelected',
	'Translate:native',
	0,
	524290,
	'  return Ooaofooa.selection.contains(Element);',
	0);
INSERT INTO S_BPARM
	VALUES (524473,
	524360,
	'element',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524361,
	524292,
	'getConnectorStyle',
	'Translate:native',
	0,
	524310,
	'    return i_invoke_i(From, "Get_style", At);
  }
  //
  // i_invoke_i - invoke a method that takes one int parameter and returns an int
  //
  private static int i_invoke_i(Object target, String methodName, int arg1) {
    if (target != null) {
      try {
        Class [] argTypes = new Class[1];
        argTypes[0] = int.class;
        Method method = target.getClass().getMethod(methodName, argTypes);
        Object [] args = new Object[1];
        args[0] = new Integer(arg1);
        Object result = method.invoke(target, args);
        return ((Integer)result).intValue();
      }
      catch (NoSuchMethodException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " not found: " + e);
      }
      catch (IllegalAccessException e) {
        Ooaofgraphics.log.println("Client method, " + methodName + " cannot be accessed: " + e);
      }
      catch (InvocationTargetException e) {
         Ooaofgraphics.log.println("Client method, " + methodName + " is illegal target: " + e);
      }
    }
    return 0;
',
	0);
INSERT INTO S_BPARM
	VALUES (524474,
	524361,
	'from',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524475,
	524361,
	'at',
	524311,
	0);
INSERT INTO S_BRG
	VALUES (524362,
	524292,
	'getOOA_IDFromInstance',
	'Translate:native',
	0,
	524294,
	'       if (Element instanceof Domain_c)
         return ((Domain_c)Element).getDom_id();
      if (Element instanceof Subsystem_c)
        return ((Subsystem_c)Element).getSs_id();
      if (Element instanceof ExternalEntity_c)
        return ((ExternalEntity_c)Element).getEe_id();
      if (Element instanceof ModelClass_c)
        return ((ModelClass_c)Element).getObj_id();
      if (Element instanceof ImportedClass_c)
        return ((ImportedClass_c)Element).getIobj_id();
      if (Element instanceof Association_c)
        return ((Association_c)Element).getRel_id();
      if (Element instanceof CommunicationPath_c)
        return ((CommunicationPath_c)Element).getCpath_id();
      if (Element instanceof AccessPath_c)
        return ((AccessPath_c)Element).getApath_id();
      if (Element instanceof StateMachineState_c)
        return ((StateMachineState_c)Element).getSm_id();
      if (Element instanceof Transition_c)
        return ((Transition_c)Element).getTrans_id();
      if (Element instanceof InstanceStateMachine_c)
        return ((InstanceStateMachine_c)Element).getSm_id();
      if (Element instanceof ClassStateMachine_c)
        return ((ClassStateMachine_c)Element).getSm_id();
      return Long.MIN_VALUE;
',
	0);
INSERT INTO S_BPARM
	VALUES (524476,
	524362,
	'element',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524363,
	524292,
	'getEntries',
	'Translate:native',
	0,
	524291,
	'    return i_invoke_i(From, "Get_entries", Compartment_id);

',
	0);
INSERT INTO S_BPARM
	VALUES (524477,
	524363,
	'from',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524478,
	524363,
	'compartment_id',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524364,
	524292,
	'getTextStyle',
	'Translate:native',
	0,
	524310,
	'       return i_invoke_iii(From, "Get_text_style", At, Compartment_id, Entry_id);
    }
    //
    // i_invoke_iii - invoke a method that takes three int parameters and returns an int
    //
    private static int i_invoke_iii(Object target, String methodName, int arg1, int arg2, int arg3) {
      if (target != null) {
        try {
          Class [] argTypes = new Class[3];
          argTypes[0] = int.class;
          argTypes[1] = int.class;
          argTypes[2] = int.class;
          Method method = target.getClass().getMethod(methodName, argTypes);
          Object [] args = new Object[3];
          args[0] = new Integer(arg1);
          args[1] = new Integer(arg2);
          args[2] = new Integer(arg3);
          Object result = method.invoke(target, args);
          return ((Integer)result).intValue();
        }
        catch (NoSuchMethodException e) {
          Ooaofgraphics.log.println("Client method, " + methodName + " not found: " + e);
        }
        catch (IllegalAccessException e) {
          Ooaofgraphics.log.println("Client method, " + methodName + " cannot be accessed: " + e);
        }
        catch (InvocationTargetException e) {
           Ooaofgraphics.log.println("Client method, " + methodName + " is illegal target: " + e);
        }
      }
      return 0;
       
',
	0);
INSERT INTO S_BPARM
	VALUES (524479,
	524364,
	'from',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524480,
	524364,
	'compartment_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524481,
	524364,
	'entry_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524482,
	524364,
	'at',
	524308,
	0);
INSERT INTO S_BRG
	VALUES (524365,
	524292,
	'addToSelection',
	'Translate:native',
	0,
	524289,
	'Ooaofooa.instance().addToSelection(Element);',
	0);
INSERT INTO S_BPARM
	VALUES (524483,
	524365,
	'element',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524366,
	524292,
	'removeFromSelection',
	'Translate:native',
	0,
	524289,
	'Ooaofooa.instance().removeFromSelection(Element);',
	0);
INSERT INTO S_BPARM
	VALUES (524484,
	524366,
	'element',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524367,
	524292,
	'clearSelection',
	'Translate:native',
	0,
	524289,
	'Ooaofooa.selection.removeAllElements();',
	0);
INSERT INTO S_EE
	VALUES (524293,
	'Graphics Platform',
	'Represents the low level graphics routines which allow the Graphics
Domain to carry out its function.
-----------------------------------------------------------------------------------
Import:com.mentor.nucleus.bp.core.*
Translate:false',
	'GR',
	192646601);
INSERT INTO S_BRG
	VALUES (524368,
	524293,
	'drawText',
	'Draws the passed text at the location given by x,y. If the justification
is Left, Center or Right, the text is located appropriately. The default
is Left justification.
',
	0,
	524289,
	' // implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524485,
	524368,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524486,
	524368,
	'text',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524487,
	524368,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524488,
	524368,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524489,
	524368,
	'justified_to',
	524308,
	0);
INSERT INTO S_BPARM
	VALUES (524490,
	524368,
	'text_style',
	524310,
	0);
INSERT INTO S_BRG
	VALUES (524369,
	524293,
	'drawRect',
	'Draws a simple rectangle.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524491,
	524369,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524492,
	524369,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524493,
	524369,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524494,
	524369,
	'w',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524495,
	524369,
	'h',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524370,
	524293,
	'clipTo',
	'Sets the underlying graphic environment so that no
graphics will be rendered outside the specified area.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524496,
	524370,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524497,
	524370,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524498,
	524370,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524499,
	524370,
	'w',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524500,
	524370,
	'h',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524371,
	524293,
	'getTextExtent',
	'Finds the X or Y size of the passed in text, given the current
graphics context. The actual returned value is dependent on
the font style and size and the type of display in use.
',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524501,
	524371,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524502,
	524371,
	'text',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524503,
	524371,
	'axis',
	524309,
	0);
INSERT INTO S_BRG
	VALUES (524372,
	524293,
	'getGraphicSpacing',
	'Returns the required space between one graphic and another.
Typically used to provide a small amount of space between a
block of text and its enclosing symbol.',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524373,
	524293,
	'drawLine',
	'Draws a line from x,y to x2,y2.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524504,
	524373,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524505,
	524373,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524506,
	524373,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524507,
	524373,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524508,
	524373,
	'y2',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524374,
	524293,
	'unClip',
	'Sets the underlying graphic environment so that
graphics can be rendered anywhere in the 
client area.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524509,
	524374,
	'context',
	524305,
	0);
INSERT INTO S_BRG
	VALUES (524375,
	524293,
	'drawRoundRect',
	'Draws a rectangle with rounded corners.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524510,
	524375,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524511,
	524375,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524512,
	524375,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524513,
	524375,
	'w',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524514,
	524375,
	'h',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524376,
	524293,
	'drawFolder',
	'Draws a folder symbol at x,y with
width w and height h
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524515,
	524376,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524516,
	524376,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524517,
	524376,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524518,
	524376,
	'w',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524519,
	524376,
	'h',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524520,
	524376,
	't',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524377,
	524293,
	'setLineWidth',
	'Sets the required width of the line. Note that the 
line width is scaled if scaling is greater than 100%.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524521,
	524377,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524522,
	524377,
	'w',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524378,
	524293,
	'setLineStyle',
	'Set the style of line to be drawn, either solid or broken.
The target SWT class library supports many styles, but
the requirement calss for only two at this time. LINE_DOT
most nearly resembles the required broken line density of
the existing tool.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524523,
	524378,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524524,
	524378,
	'new_style',
	524310,
	0);
INSERT INTO S_BRG
	VALUES (524379,
	524293,
	'scale',
	'Converts the passed integer to the scaled form to be used
on the drawing canvas.
',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524525,
	524379,
	'value',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524380,
	524293,
	'unScale',
	'Converts the passed integer from the scaled form used
on the drawing canvas to the logical form cached in the
model.
',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524526,
	524380,
	'value',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524381,
	524293,
	'drawResizeHandles',
	'Draws selection handles at the four vertices of 
graphic shape.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524527,
	524381,
	'Context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524528,
	524381,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524529,
	524381,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524530,
	524381,
	'w',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524531,
	524381,
	'h',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524382,
	524293,
	'getHotspotSize',
	'Returns the size of the area around a graphic vertex that will
cause a cursor to change appropriately.
',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524383,
	524293,
	'getAngle',
	'Returns the angle made by the line x1, y1 to x2, y2.
',
	0,
	524292,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524532,
	524383,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524533,
	524383,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524534,
	524383,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524535,
	524383,
	'y2',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524384,
	524293,
	'drawTriangle',
	'Draws a simple unfilled triangle.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524536,
	524384,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524537,
	524384,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524538,
	524384,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524539,
	524384,
	'rotation',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524385,
	524293,
	'wrapTextTo',
	'Wraps text so that it fits in the specified width. If the
text already fits, it is returned unmodified. If it does not,
LF/CR''s are inserted in the output string at word
boundaries until the text has been consumed.
',
	0,
	524293,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524540,
	524385,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524541,
	524385,
	'input',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524542,
	524385,
	'width',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524386,
	524293,
	'getDirection',
	'Tests the value of angle to return a Direction enumeration
i.e. North, South, East or West.
',
	0,
	524312,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524543,
	524386,
	'angle',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524387,
	524293,
	'getMargin',
	'Returns the required space between the edge of the 
screen or page.
',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524388,
	524293,
	'fitTextTo',
	'Determines if text can be fitted into a given width. If yes,
the text is returned. If not, the text is truncated and ellipses
are appended (within the constraints of the width) to
denote the truncation.
',
	0,
	524293,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524544,
	524388,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524545,
	524388,
	'input',
	524293,
	0);
INSERT INTO S_BPARM
	VALUES (524546,
	524388,
	'width',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524389,
	524293,
	'drawOpenArrow',
	'Draws an open arrow (i.e. one with just two
arms unfilled).
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524547,
	524389,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524548,
	524389,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524549,
	524389,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524550,
	524389,
	'rotation',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524390,
	524293,
	'setFillTo',
	'Sets the fill color to the value passed.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524551,
	524390,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524552,
	524390,
	'new',
	524313,
	0);
INSERT INTO S_BRG
	VALUES (524391,
	524293,
	'setDefaultFill',
	'Sets the fill color to WHITE.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524553,
	524391,
	'context',
	524305,
	0);
INSERT INTO S_BRG
	VALUES (524392,
	524293,
	'clearBackgroundTo',
	'Clears the background to the color passed.
',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524554,
	524392,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524555,
	524392,
	'new',
	524313,
	0);
INSERT INTO S_BRG
	VALUES (524393,
	524293,
	'setZoomFactor',
	'Sets the canvas plotting scale used by Scale and unScale.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524556,
	524393,
	'value',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524394,
	524293,
	'drawFilledCircle',
	'Draws a circle filled withe current foreground color.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524557,
	524394,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524558,
	524394,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524559,
	524394,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524560,
	524394,
	'radius',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524395,
	524293,
	'getDefaultTextWidth',
	'Specifies a nominal initial value for the width
of a currently unknown text block. Text is
never left with this width, since the block size
is immediately calculated once the true text is
known.',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524396,
	524293,
	'setMouseCursor',
	'Sets the mouse cursor to the specified type.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524561,
	524396,
	'type',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524397,
	524293,
	'reDraw',
	'Forces the canvas to redraw itself.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524398,
	524293,
	'getAbsoluteXPosition',
	'Returns the absolute current position of the mouse.',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524399,
	524293,
	'getAbsoluteYPosition',
	'Returns the absolute position of the mouse.',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524400,
	524293,
	'getGradient',
	'Returns the gradient of the line.',
	0,
	524292,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524562,
	524400,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524563,
	524400,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524564,
	524400,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524565,
	524400,
	'y2',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524401,
	524293,
	'drawFlexHandles',
	'Draws the circles used to show the waypoints when
a connector can be modified.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524566,
	524401,
	'context',
	524305,
	0);
INSERT INTO S_BPARM
	VALUES (524567,
	524401,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524568,
	524401,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524569,
	524401,
	'radius',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524402,
	524293,
	'scrollBy',
	'Scrolls the canvas by the specified number of pixels.',
	0,
	524289,
	'// implementation will supplied by native code',
	1);
INSERT INTO S_BPARM
	VALUES (524570,
	524402,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524571,
	524402,
	'y',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524403,
	524293,
	'isWithinHotspot',
	'Determines whether the x and y values are within
hotspot distance of the point (x2, y2).',
	0,
	524290,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524572,
	524403,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524573,
	524403,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524574,
	524403,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524575,
	524403,
	'y2',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524404,
	524293,
	'getInterpolatedX',
	'Returns the X value found along the line (x1, y1), (x2 + dX, y2 + dY),
based on the distance (x3, y3) along the line (x1, y1), (x2, y2).',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524576,
	524404,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524577,
	524404,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524578,
	524404,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524579,
	524404,
	'y2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524580,
	524404,
	'x3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524581,
	524404,
	'y3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524582,
	524404,
	'dX',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524583,
	524404,
	'dY',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524405,
	524293,
	'getInterpolatedY',
	'Returns the Y value found along the line (x1, y1), (x2 + dX, y2 + dY),
based on the distance (x3, y3) along the line (x1, y1), (x2, y2).',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524584,
	524405,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524585,
	524405,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524586,
	524405,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524587,
	524405,
	'y2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524588,
	524405,
	'x3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524589,
	524405,
	'y3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524590,
	524405,
	'dX',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524591,
	524405,
	'dY',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524406,
	524293,
	'getXIntersect',
	'Get the intersection point between the supplied lines, or zero if no intersection.',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524592,
	524406,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524593,
	524406,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524594,
	524406,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524595,
	524406,
	'y2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524596,
	524406,
	'x3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524597,
	524406,
	'y3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524598,
	524406,
	'x4',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524599,
	524406,
	'y4',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524407,
	524293,
	'getYIntersect',
	'Get the intersection between the two lines, or zero if there is no intersection.',
	0,
	524291,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524600,
	524407,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524601,
	524407,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524602,
	524407,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524603,
	524407,
	'y2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524604,
	524407,
	'x3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524605,
	524407,
	'y3',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524606,
	524407,
	'x4',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524607,
	524407,
	'y4',
	524291,
	0);
INSERT INTO S_BRG
	VALUES (524408,
	524293,
	'getContext',
	'Obtains and returns a Graphics Context.',
	0,
	524305,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BRG
	VALUES (524409,
	524293,
	'disposeContext',
	'Releases a Graphics Context.',
	0,
	524289,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524608,
	524409,
	'context',
	524305,
	0);
INSERT INTO S_BRG
	VALUES (524410,
	524293,
	'isOver',
	'Checks to see if a point is over a line.',
	0,
	524290,
	'// implementation will supplied by native code',
	0);
INSERT INTO S_BPARM
	VALUES (524609,
	524410,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524610,
	524410,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524611,
	524410,
	'x1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524612,
	524410,
	'y1',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524613,
	524410,
	'x2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524614,
	524410,
	'y2',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524615,
	524410,
	'tolerance',
	524291,
	0);
INSERT INTO S_EE
	VALUES (524294,
	'Operating System',
	'Encapsulates basic functions that could be thought of as, but are not necessarily actually,
provided by the Operating System.',
	'OS',
	192646601);
INSERT INTO S_BRG
	VALUES (524411,
	524294,
	'NULL_UNIQUE_ID',
	'Translate:native',
	0,
	524294,
	'return 0;',
	0);
INSERT INTO S_BRG
	VALUES (524412,
	524294,
	'MAX_INT',
	'Translate:native',
	0,
	524291,
	'return Integer.MAX_VALUE;',
	0);
INSERT INTO S_BRG
	VALUES (524413,
	524294,
	'isMultiLine',
	'Translate:native',
	0,
	524290,
	'return Text.indexOf(''\n'') != -1;',
	0);
INSERT INTO S_BPARM
	VALUES (524616,
	524413,
	'text',
	524293,
	0);
INSERT INTO S_BRG
	VALUES (524414,
	524294,
	'realToInt',
	'Translate:native',
	0,
	524291,
	'return (int)Value;',
	0);
INSERT INTO S_BPARM
	VALUES (524617,
	524414,
	'value',
	524292,
	0);
INSERT INTO S_BRG
	VALUES (524415,
	524294,
	'intToReal',
	'Translate:native',
	0,
	524292,
	'return (float)Value;',
	0);
INSERT INTO S_BPARM
	VALUES (524618,
	524415,
	'value',
	524291,
	0);
INSERT INTO S_SS
	VALUES (3145734,
	'Graphical Data',
	'The GD (Graphics Domain) subsystem models the graphical elements for a BridgePoint model.
-------------------------------------------------------
Import:org.eclipse.swt.graphics.Color',
	'GD',
	1,
	192646601,
	3145734);
INSERT INTO O_OBJ
	VALUES (3145729,
	'Model',
	1,
	'GD_MD',
	'A model is a drawing for one particular view of the analysis. There is an instance of model for each glyph on the index window, and each entry in the model element list on the index window. For example, every domain will have four models (DPD, PDD(R), PDD(A), PDD(S)).  Every subsystem will have three models (CD, OCD(A), OCD(S)).  Every state chart has one model (SC).  The STT is not a graphical model.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145729,
	3145729,
	'draw',
	'To draw the model, select all the graphical elements and
delegate the draw operation to each element in turn.',
	524289,
	1,
	'select one mdlSpec related by self->GD_MS[R9];
if (empty mdlSpec)
  select any mdlSpec from instances of GD_MS where (selected.Model_Type == self.Model_Type);
  if (not_empty mdlSpec)
    relate mdlSpec to self across R9;
  end if;
end if;
if (not_empty mdlSpec AND NOT param.is_hard_copy)
  GR::clearBackgroundTo(context:param.context, new:mdlSpec.background);
end if;
//
// Ensure the represented instance is cached
//
if (CL::isEmpty(element:self.represents))
  self.represents = CL::getInstanceFromOOA_ID(OOA_Type:self.OOA_Type,
                                                                              OOA_ID:self.OOA_ID);
end if;
select many elements related  by self->GD_GE[R1];
for each element in elements
  element.draw(context:param.context, disable_selection:param.is_hard_copy);
end for;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145729,
	3145729,
	'context',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (3145730,
	3145729,
	'is_hard_copy',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145730,
	3145729,
	'getMaxX',
	'Gets the maximum X co-ordinate on the canvas.
This calculation currently allows for connectors but
not for large text blocks that might affect the sizing.',
	524291,
	1,
	'result = 0;
select many elems related by self->GD_GE[R1];
for each elem in elems
  select one shape related by elem->GD_SHP[R2];
  select one connector related by elem->GD_CON[R2];
  if (not_empty shape)
    if (shape.SE_X > result)
      if (param.all)
        result = shape.SE_X;
      else
        if (elem.isSelected())
          result = shape.SE_X;
        end if;
      end if;
    end if;
  elif (not_empty connector)
    select many segs related by connector->GD_LS[R6];
    for each seg in segs
      if (seg.Start_X > result OR seg.End_X > result)
        if (param.all)
          result = ::max(a:seg.Start_X, b:seg.End_X);
        else
          if (elem.isSelected())
            result = ::max(a:seg.Start_X, b:seg.End_X);
          end if;
        end if;
      end if;
    end for;
  end if;
end for;
return result;',
	1);
INSERT INTO O_TPARM
	VALUES (3145731,
	3145730,
	'all',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145731,
	3145729,
	'getMaxY',
	'Gets the maximum Y co-ordinate on the canvas.',
	524291,
	1,
	'result = 0;
select many elems related by self->GD_GE[R1];
for each elem in elems
  select one shape related by elem->GD_SHP[R2];
  select one connector related by elem->GD_CON[R2];
  if (not_empty shape)
    if (shape.SE_Y > result)
      if (param.all)
        result = shape.SE_Y;
      else
        if (elem.isSelected())
          result = shape.SE_Y;
        end if;
      end if;
    end if;
  elif (not_empty connector)
    select many segs related by connector->GD_LS[R6];
    for each seg in segs
      if (seg.Start_Y > result OR seg.End_Y > result)
        if (param.all)
          result = ::max(a:seg.Start_Y, b:seg.End_Y);
        else
          if (elem.isSelected())
            result = ::max(a:seg.Start_Y, b:seg.End_Y);
          end if;
        end if;
      end if;
    end for;
  end if;
end for;
return result;',
	1);
INSERT INTO O_TPARM
	VALUES (3145732,
	3145731,
	'all',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145732,
	3145729,
	'getMinX',
	'Gets the minimum X co-ordinate on the canvas.',
	524291,
	1,
	'result = OS::MAX_INT();
select many elems related by self->GD_GE[R1];
for each elem in elems
  select one shape related by elem->GD_SHP[R2];
  select one connector related by elem->GD_CON[R2];
  if (not_empty shape)
    if (shape.NW_X < result)
      if (param.all)
        result = shape.NW_X;
      else
        if (elem.isSelected())
          result = shape.NW_X;
        end if;
      end if;
    end if;
  elif (not_empty connector)
    select many segs related by connector->GD_LS[R6];
    for each seg in segs
      if (seg.Start_X < result OR seg.End_X < result)
        if (param.all)
          result = ::min(a:seg.Start_X, b:seg.End_X);
        else
          if (elem.isSelected())
            result = ::min(a:seg.Start_X, b:seg.End_X);
          end if;
        end if;
      end if;
    end for;
  end if;
end for;
return result;',
	1);
INSERT INTO O_TPARM
	VALUES (3145733,
	3145732,
	'all',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145733,
	3145729,
	'getMinY',
	'Gets the minimum Y co-ordinate on the canvas.',
	524291,
	1,
	'result = OS::MAX_INT();
select many elems related by self->GD_GE[R1];
for each elem in elems
  select one shape related by elem->GD_SHP[R2];
  select one connector related by elem->GD_CON[R2];
  if (not_empty shape)
    if (shape.NW_Y < result)
      if (param.all)
        result = shape.NW_Y;
      else
        if (elem.isSelected())
          result = shape.NW_Y;
        end if;
      end if;
    end if;
  elif (not_empty connector)
    select many segs related by connector->GD_LS[R6];
    for each seg in segs
      if (seg.Start_Y < result OR seg.End_Y < result)
        if (param.all)
          result = ::min(a:seg.Start_Y, b:seg.End_Y);
        else
          if (elem.isSelected())
            result = ::min(a:seg.Start_Y, b:seg.End_Y);
          end if;
        end if;
      end if;
    end for;
  end if;
end for;
return result;',
	1);
INSERT INTO O_TPARM
	VALUES (3145734,
	3145733,
	'all',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145734,
	3145729,
	'zoomTo',
	'',
	524289,
	1,
	'newX = param.toX;
newY = param.toY;
width = param.toW;
height = param.toH;
if (param.all)
  // Add margins to required render area
  considerAll = not param.selection;
  newX = self.getMinX(all:considerAll) - GR::getMargin();
  newY = self.getMinY(all:considerAll) - GR::getMargin();
  width = self.getMaxX(all:considerAll) - self.getMinX(all:considerAll) + (GR::getMargin() * 2);
  height = self.getMaxY(all:considerAll) - self.getMinY(all:considerAll) + (GR::getMargin() * 2);
end if;
// Calculate ratios of required to available areas
widthFactor = OS::intToReal(value:param.avW) /  OS::intToReal(value:width);
heightFactor =  OS::intToReal(value:param.avH) /  OS::intToReal(value:height);
if (widthFactor < heightFactor)
  self.ZoomFactor = widthFactor;
  GR::setZoomFactor(value:self.ZoomFactor);
  unscaledHeight = OS::realToInt(value:height * heightFactor);
  actualHeight = GR::scale(value:unscaledHeight);
  // Center the canvas in the Y axis
  newY = newY - ((actualHeight - height) / 2);
  if (newY < 0)
    newY = 0;
  end if;
else
  self.ZoomFactor = heightFactor;
  GR::setZoomFactor(value:self.ZoomFactor);
  unscaledWidth = OS::realToInt(value:width * widthFactor);
  actualWidth = GR::scale(value:unscaledWidth);
  // Center the canvas in the X axis
  newX = newX - ((actualWidth - width) / 2);
  if (newX < 0)
    newX = 0;
  end if;
end if;
self.ScrollXPos = newX;
self.ScrollYPos = newY;',
	1);
INSERT INTO O_TPARM
	VALUES (3145735,
	3145734,
	'all',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3145736,
	3145734,
	'selection',
	524290,
	0);
INSERT INTO O_TPARM
	VALUES (3145737,
	3145734,
	'toX',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145738,
	3145734,
	'toY',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145739,
	3145734,
	'toW',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145740,
	3145734,
	'toH',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145741,
	3145734,
	'avW',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145742,
	3145734,
	'avH',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145735,
	3145729,
	'unSelectAll',
	'',
	524289,
	1,
	'CL::clearSelection();',
	1);
INSERT INTO O_TFR
	VALUES (3145736,
	3145729,
	'enableTextMoveUnder',
	'',
	524289,
	1,
	'select one con_text related by self->GD_CTXT[R14];
select many symbols related by self->GD_GE[R1];
sel_symb_count = 0;
for each symb in symbols
  if (symb.isSelected())
    sel_symb_count = sel_symb_count + 1;
  end if;
end for;
select many con_elems related by self->GD_GE[R1]->GD_CON[R2]->GD_GE[R2];
sel_con_count = 0;
for each elem in con_elems
  if (elem.isSelected())
    sel_con_count = sel_con_count + 1;
  end if;
end for;
if (sel_con_count == sel_symb_count)
  for each elem in con_elems
    if (elem.isSelected())
      select one con_text related by elem->GD_CON[R2]->GD_CTXT[R8];
      if (not_empty con_text)
        if (con_text.isOver(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition(), End:End::Start))
          create object instance tim of GD_TIM;
          relate self to con_text across R14 using tim;
          tim.End =End::Start;
          break;  // We are only allowed to move one text block at a time . . . . .
        elif (con_text.isOver(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition(), End:End::Middle))
          create object instance tim of GD_TIM;
          relate self to con_text across R14 using tim;
          tim.End =End::Middle;
          break;
        elif  (con_text.isOver(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition(), End:End::End))
          create object instance tim of GD_TIM;
          relate self to con_text across R14 using tim;
          tim.End =End::End;
          break;
        end if;
      end if;
    end if;
  end for;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3145743,
	3145736,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145744,
	3145736,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145737,
	3145729,
	'disableTextMove',
	'',
	524289,
	1,
	'select one con_text related by self->GD_CTXT[R14];
if (not_empty con_text)
  select one tim related by self->GD_TIM[R14];
  unrelate self from con_text across R14 using tim;
  delete object instance tim;
end if;',
	1);
INSERT INTO O_NBATTR
	VALUES (3145729,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145729,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145729,
	3145729,
	0,
	'Model_ID',
	'Description: This is an arbitrary unique identifier for the graphical model.
Full Name: Model Identifier
',
	'',
	'Model_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145729,
	3145735,
	0,
	3145789,
	3145737,
	3145747,
	3145746,
	3145730,
	3145733,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145730,
	3145729,
	3145789,
	3145735,
	1);
INSERT INTO O_ATTR
	VALUES (3145730,
	3145729,
	3145729,
	'Model_Type',
	'',
	'',
	'Model_Type',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145731,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145731,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145731,
	3145729,
	3145730,
	'OOA_ID',
	'Description: The identifier of the corresponding OOA model for this model (GD_MD).
Full Name: OOA Model Identifier',
	'',
	'OOA_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3145732,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145732,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145732,
	3145729,
	3145731,
	'OOA_Type',
	'',
	'',
	'OOA_Type',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145733,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145733,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145733,
	3145729,
	3145732,
	'UseGlobalPrint',
	'Description: Defines the print rules for the model.
Full Name: Global Print Indicator
Data Domain: If True, use the globally defined rules for printing this model.  If False, use the print rules defined by this model (PrintMode, PrintRows, PrintCols, IsLandscape, ZoomFontSize).',
	'',
	'UseGlobalPrint',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145734,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145734,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145734,
	3145729,
	3145733,
	'PrintMode',
	'Description: Determines how a model is printed.
Full Name: Print Mode Indicator
Data Domain: If False, then use the PrintCols and PrintRows values to determine the size of the output.  If True, then use the ZoomFontSize to determine the size of the output.
',
	'',
	'PrintMode',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145735,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145735,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145735,
	3145729,
	3145734,
	'PrintRows',
	'Description: When printing the model in mosaic mode, this is the number of rows that are printed.
Full Name: Number of Print Rows',
	'',
	'PrintRows',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145736,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145736,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145736,
	3145729,
	3145735,
	'PrintCols',
	'Description: When printing the model in mosaic mode, this is the number of columns that are printed.
Full Name: Number of Print Columns
',
	'',
	'PrintCols',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145737,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145737,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145737,
	3145729,
	3145736,
	'IsLandscape',
	'Description: Determines if printing in landscape or portrait mode.
Full Name: Landscape Indicator
Data Domain: If true, print the model using the landscape (long axis horizontal) orientation. If false, print the model using the portrait orientation.
',
	'',
	'IsLandscape',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145738,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145738,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145738,
	3145729,
	3145737,
	'ZoomFontSize',
	'Description: When sizing the diagram, if the user requests Zoom to Font Size, use this font size to determine the zoom factor.
Full Name: Zoom Font Size
',
	'',
	'ZoomFontSize',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145739,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145739,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145739,
	3145729,
	3145738,
	'ScrollXPos',
	'Description: This is the X position the model was last scrolled to.
Full Name: Last Scroll X Position',
	'',
	'ScrollXPos',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145740,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145740,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145740,
	3145729,
	3145739,
	'ScrollYPos',
	'Description: This is the Y position the model was last scrolled to.
Full Name: Last Scroll Y Position',
	'',
	'ScrollYPos',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145741,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145741,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145741,
	3145729,
	3145740,
	'ZoomFactor',
	'Description: This is the scaling percentage used for the model.  
Full Name: Zoom Factor
Data Domain: 20 - 300 inclusive.',
	'',
	'ZoomFactor',
	0,
	524292);
INSERT INTO O_NBATTR
	VALUES (3145742,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145742,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145742,
	3145729,
	3145741,
	'GridOn',
	'Description: Indicates if the grid should be shown.
Full Name: Grid On Indicator
Data Domain: If true, the drawing grid is shown on the model. If false, it is not.
',
	'',
	'GridOn',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145743,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145743,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145743,
	3145729,
	3145742,
	'SelRectX',
	'',
	'',
	'SelRectX',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145744,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145744,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145744,
	3145729,
	3145743,
	'SelRectY',
	'',
	'',
	'SelRectY',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145745,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145745,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145745,
	3145729,
	3145744,
	'SelRectW',
	'',
	'',
	'SelRectW',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145746,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145746,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145746,
	3145729,
	3145745,
	'SelRectH',
	'',
	'',
	'SelRectH',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145747,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145747,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145747,
	3145729,
	3145746,
	'represents',
	'',
	'',
	'represents',
	0,
	524306);
INSERT INTO O_NBATTR
	VALUES (3145748,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145748,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145748,
	3145729,
	3145747,
	'current_state',
	'',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	3145729);
INSERT INTO O_OIDA
	VALUES (3145729,
	3145729,
	0);
INSERT INTO O_RTIDA
	VALUES (3145729,
	3145729,
	0,
	3145729,
	3145729);
INSERT INTO O_RTIDA
	VALUES (3145729,
	3145729,
	0,
	3145740,
	3145753);
INSERT INTO O_RTIDA
	VALUES (3145729,
	3145729,
	0,
	3145741,
	3145756);
INSERT INTO O_RTIDA
	VALUES (3145729,
	3145729,
	0,
	3145742,
	3145759);
INSERT INTO SM_ISM
	VALUES (3670023,
	3145729);
INSERT INTO SM_SM
	VALUES (3670023,
	'This statechart controls the users interaction with a canvas.

It organizes and controls the editor''s response to mouse
events such as click and move. Specifically, it controls selection
and movement of symbols on the canvas. It also controls
resizing and allows the user to ''slide'' the canvas, adjusting
both X and Y scroll axes simultaneously.',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_EVTDI
	VALUES (3670017,
	3670023,
	'Retain_Selection',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (3670018,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (3670019,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (153154967,
	3670023,
	'Retain_Selection',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (98752860,
	3670023,
	'Retain_Selection',
	'',
	524290);
INSERT INTO SM_EVTDI
	VALUES (244393658,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (266424091,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (55001582,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (89750853,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (221904749,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (90301756,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (65554409,
	3670023,
	'X',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (189258725,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (99977769,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (116171100,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (104686111,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (116896810,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (121442358,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_EVTDI
	VALUES (213367429,
	3670023,
	'Y',
	'',
	524291);
INSERT INTO SM_LEVT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670017,
	3670023,
	0,
	4,
	'Mouse Moved',
	0,
	'',
	'GD_MD4',
	'');
INSERT INTO SM_LEVT
	VALUES (3670018,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670018,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670018,
	3670023,
	0,
	3,
	'Mouse Released',
	0,
	'',
	'GD_MD3',
	'');
INSERT INTO SM_LEVT
	VALUES (3670019,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670019,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670019,
	3670023,
	0,
	9,
	'Mouse Press over Symbol',
	0,
	'',
	'GD_MD9',
	'');
INSERT INTO SM_LEVT
	VALUES (3670020,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670020,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670020,
	3670023,
	0,
	2,
	'Mouse Pressed',
	0,
	'',
	'GD_MD2',
	'');
INSERT INTO SM_LEVT
	VALUES (3670021,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670021,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670021,
	3670023,
	0,
	8,
	'Mouse Press over Whitespace',
	0,
	'',
	'GD_MD8',
	'');
INSERT INTO SM_LEVT
	VALUES (3670022,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670022,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670022,
	3670023,
	0,
	6,
	'Done',
	0,
	'',
	'GD_MD6',
	'');
INSERT INTO SM_LEVT
	VALUES (3670023,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670023,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670023,
	3670023,
	0,
	12,
	'Mouse Press over Selected Symbol',
	0,
	'',
	'GD_MD12',
	'');
INSERT INTO SM_LEVT
	VALUES (3670024,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670024,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670024,
	3670023,
	0,
	5,
	'Mouse Context Press',
	0,
	'',
	'GD_MD5',
	'');
INSERT INTO SM_LEVT
	VALUES (3670025,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670025,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670025,
	3670023,
	0,
	14,
	'Space Pressed',
	0,
	'',
	'GD_MD14',
	'');
INSERT INTO SM_LEVT
	VALUES (3670026,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670026,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670026,
	3670023,
	0,
	15,
	'Space Released',
	0,
	'',
	'GD_MD15',
	'');
INSERT INTO SM_LEVT
	VALUES (3670027,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670027,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670027,
	3670023,
	0,
	18,
	'Mouse Moved over Whitespace',
	0,
	'',
	'GD_MD18',
	'');
INSERT INTO SM_LEVT
	VALUES (3670028,
	3670023,
	0);
INSERT INTO SM_SEVT
	VALUES (3670028,
	3670023,
	0);
INSERT INTO SM_EVT
	VALUES (3670028,
	3670023,
	0,
	19,
	'Mouse Moved over Hotspot',
	0,
	'',
	'GD_MD19',
	'');
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	0,
	'Checking Mouse Press',
	12,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670018,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670020,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670022,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670017,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_AH
	VALUES (3670017,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670017,
	3670023,
	1,
	'select many symbols related by self->GD_GE[R1];
over_unselected = false;
symbol_found = false;
for each symbol in symbols
  if (symbol.isOver(x:rcvd_evt.X, y:rcvd_evt.Y))
    symbol_found = true;
    if (symbol.isSelected())
      if (not rcvd_evt.Retain_Selection)
        generate GD_MD12:''Mouse Press over Selected Symbol''(X:rcvd_evt.X, Y:rcvd_evt.Y) to self ;
      else
        generate GD_MD9:''Mouse Press over Symbol''(Retain_Selection:rcvd_evt.Retain_Selection, X:rcvd_evt.X, Y:rcvd_evt.Y) to self ;
      end if;
      over_unselected = false;
      break;
    else
      over_unselected = true;
    end if;
  end if ;
end for;
if (not symbol_found)
  generate  GD_MD8:''Mouse Press over Whitespace''(Retain_Selection:rcvd_evt.Retain_Selection, X:rcvd_evt.X, Y:rcvd_evt.Y) to self ;
elif (over_unselected)
  generate GD_MD9:''Mouse Press over Symbol''(Retain_Selection:rcvd_evt.Retain_Selection, X:rcvd_evt.X, Y:rcvd_evt.Y) to self ;
end if;
',
	'');
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	0,
	'Preparing Selection Rectangle',
	14,
	0);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670018,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670018,
	3670023,
	3670018);
INSERT INTO SM_AH
	VALUES (3670018,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670018,
	3670023,
	1,
	'if (not rcvd_evt.Retain_Selection)
  self.unSelectAll();
end if;
self.SelRectX = rcvd_evt.X;
self.SelRectY = rcvd_evt.Y;
self.SelRectW =0 ;
self.SelRectH = 0 ;
',
	'');
INSERT INTO SM_STATE
	VALUES (3670019,
	3670023,
	0,
	'Selecting Symbol',
	16,
	0);
INSERT INTO SM_SEME
	VALUES (3670019,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670019,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670019,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670019,
	3670023,
	3670019);
INSERT INTO SM_AH
	VALUES (3670019,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670019,
	3670023,
	1,
	'if (not rcvd_evt.Retain_Selection)
  self.unSelectAll() ;
end if;
select many symbols related by self->GD_GE[R1];
for each symbol in symbols
  if (symbol.isOver(x:rcvd_evt.X, y:rcvd_evt.Y))
    symbol.toggleSelect() ;
  end if;
end for;
self.enableTextMoveUnder(x:rcvd_evt.X, y:rcvd_evt.Y);',
	'');
INSERT INTO SM_STATE
	VALUES (3670020,
	3670023,
	0,
	'Dragging Selection Rectangle',
	15,
	0);
INSERT INTO SM_SEME
	VALUES (3670020,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670020,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670020,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670020,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670020,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670020,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670020,
	3670023,
	3670020);
INSERT INTO SM_AH
	VALUES (3670020,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670020,
	3670023,
	1,
	'self.SelRectW = self.SelRectW + rcvd_evt.X;
self.SelRectH = self.SelRectH + rcvd_evt.Y;
GR::reDraw();
',
	'');
INSERT INTO SM_STATE
	VALUES (3670021,
	3670023,
	0,
	'Dragging Symbols',
	20,
	0);
INSERT INTO SM_SEME
	VALUES (3670021,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670021,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670021,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670021,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670021,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670021,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670021,
	3670023,
	3670021);
INSERT INTO SM_AH
	VALUES (3670021,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670021,
	3670023,
	1,
	'select one con_text related by self->GD_CTXT[R14];
if (not_empty con_text)
  select one tim related by self->GD_TIM[R14];
  if (tim.End == End::Start)
    con_text.moveStart(xDelta:GR::scale(value:rcvd_evt.X), yDelta:GR::scale(value:rcvd_evt.Y));
  elif (tim.End == End::Middle)
    con_text.moveMiddle(xDelta:GR::scale(value:rcvd_evt.X), yDelta:GR::scale(value:rcvd_evt.Y));
  elif  (tim.End == End::End)
     con_text.moveEnd(xDelta:GR::scale(value:rcvd_evt.X), yDelta:GR::scale(value:rcvd_evt.Y));
  end if;
else
  select many symbols related by self->GD_GE[R1];
  for each symbol in symbols
    if (symbol.isSelected())
      symbol.move(xDelta:rcvd_evt.X, yDelta:rcvd_evt.Y);
    end if ;
  end for ;
end if;
GR::reDraw();',
	'');
INSERT INTO SM_STATE
	VALUES (3670022,
	3670023,
	0,
	'Toggling Symbols Caught By Selection Rectangle',
	18,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670021,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670022,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670022,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670022,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670022,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670022,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670022,
	3670023,
	3670022);
INSERT INTO SM_AH
	VALUES (3670022,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670022,
	3670023,
	1,
	'//
// Allow for selection rectangle to be drawn backwards
//
if (self.SelRectW < 0)
  self.SelRectW = - self.SelRectW;
  self.SelRectX = self.SelRectX - self.SelRectW;
end if; 
if (self.SelRectH < 0)
  self.SelRectH = - self.SelRectH;
  self.SelRectY = self.SelRectY - self.SelRectH;
end if; 
select many symbols related by self->GD_GE[R1];
for each symbol in symbols
  if (symbol.isInside(x:self.SelRectX, y:self.SelRectY, w:self.SelRectW, h:self.SelRectH))
    symbol.toggleSelect();
  end if;
end for;
//
// Zero the height and width of the selection rectangle to hide it
//
self.SelRectW = 0;
self.SelRectH = 0;
generate GD_MD6:Done() to self ;',
	'');
INSERT INTO SM_STATE
	VALUES (3670023,
	3670023,
	0,
	'Idle',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3670023,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670019,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670023,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670023,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670023,
	3670024,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670023,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670023,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670023,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670023,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670023,
	3670023,
	3670023);
INSERT INTO SM_AH
	VALUES (3670023,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670023,
	3670023,
	1,
	'GR::setMouseCursor(type:"default");',
	'');
INSERT INTO SM_STATE
	VALUES (3670024,
	3670023,
	0,
	'Picking Up Selected Symbols',
	19,
	0);
INSERT INTO SM_SEME
	VALUES (3670024,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670024,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670024,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670024,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670024,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670024,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670024,
	3670023,
	3670024);
INSERT INTO SM_AH
	VALUES (3670024,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670024,
	3670023,
	1,
	'self.enableTextMoveUnder(x:rcvd_evt.X, y:rcvd_evt.Y);',
	'');
INSERT INTO SM_STATE
	VALUES (3670025,
	3670023,
	0,
	'Opening Context Menu',
	2,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670025,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670025,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670025,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670025,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670025,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670025,
	3670023,
	3670025);
INSERT INTO SM_AH
	VALUES (3670025,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670025,
	3670023,
	1,
	'select many symbols related by self->GD_GE[R1];
for each symbol in symbols
  // If the symbol we''re over is unselected, clear other 
  // selections and select it, otherwise leave the selection 
  // as it is.
  if (symbol.isOver(x:rcvd_evt.X, y:rcvd_evt.Y))
    if (NOT symbol.isSelected())
      self.unSelectAll() ;
      symbol.toggleSelect() ;
    end if;
   break;
  end if;
end for;',
	'');
INSERT INTO SM_STATE
	VALUES (3670026,
	3670023,
	0,
	'Waiting for Mouse Press to Slide Canvas',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (3670026,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670019,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670026,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670023,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670026,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670026,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670025,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670026,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670026,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670026,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670026,
	3670023,
	3670026);
INSERT INTO SM_AH
	VALUES (3670026,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670026,
	3670023,
	1,
	'GR::setMouseCursor(type:"open hand");
',
	'');
INSERT INTO SM_STATE
	VALUES (3670027,
	3670023,
	0,
	'Sliding Canvas',
	9,
	0);
INSERT INTO SM_SEME
	VALUES (3670027,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670027,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670027,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670025,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670027,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670027,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670027,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670027,
	3670023,
	3670027);
INSERT INTO SM_AH
	VALUES (3670027,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670027,
	3670023,
	1,
	'GR::scrollBy(x:rcvd_evt.X, y:rcvd_evt.Y);
GR::reDraw();',
	'');
INSERT INTO SM_STATE
	VALUES (3670028,
	3670023,
	0,
	'Awaiting Mouse Release',
	11,
	0);
INSERT INTO SM_EIGN
	VALUES (3670028,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670028,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670028,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670028,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670028,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670028,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670028,
	3670023,
	3670028);
INSERT INTO SM_AH
	VALUES (3670028,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670028,
	3670023,
	1,
	'GR::setMouseCursor(type:"default");',
	'');
INSERT INTO SM_STATE
	VALUES (3670029,
	3670023,
	0,
	'Mouse Released',
	13,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670021,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670029,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670029,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670029,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670029,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670029,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670029,
	3670023,
	3670029);
INSERT INTO SM_AH
	VALUES (3670029,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670029,
	3670023,
	1,
	'generate GD_MD6:Done to self;',
	'');
INSERT INTO SM_STATE
	VALUES (3670030,
	3670023,
	0,
	'Resting while Sliding Canvas',
	10,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670021,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670030,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670030,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670030,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670030,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670030,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670030,
	3670023,
	3670030);
INSERT INTO SM_AH
	VALUES (3670030,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670030,
	3670023,
	1,
	'generate GD_MD6:Done to self;',
	'');
INSERT INTO SM_STATE
	VALUES (3670031,
	3670023,
	0,
	'Checking for Hotspots',
	4,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670031,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670031,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670031,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670031,
	3670026,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670031,
	3670027,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670031,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670031,
	3670023,
	3670031);
INSERT INTO SM_AH
	VALUES (3670031,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670031,
	3670023,
	1,
	'// Check all symbols and vertices to see if we''re over a corner
select many symbols related by self->GD_GE[R1];
onHotspot = false;
for each symbol in symbols
  if(symbol.isSelected() AND 
     symbol.isOverVertex(x:GR::getAbsoluteXPosition(),
                                        y:GR::getAbsoluteYPosition()))
    if (symbol.isOverNWSE(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition()))
      GR::setMouseCursor(type:"nwse");
    else
      GR::setMouseCursor(type:"nesw");
    end if;
    onHotspot = true;
    break;
  end if;
end for;
if (not onHotspot)
  select many segs related by symbols->GD_CON[R2]->GD_LS[R6];
  for each seg in segs
    if (seg.isSelected() AND 
      seg.isOverVertex(x:GR::getAbsoluteXPosition(),
                                    y:GR::getAbsoluteYPosition())) 
      GR::setMouseCursor(type:"waypoint tool");
      onHotspot = true;
      break;
    end if;
  end for;
end if;
if (onHotspot)
  generate GD_MD19:''Mouse Moved over Hotspot''
                                     (X:GR::getAbsoluteXPosition(),
                                      Y:GR::getAbsoluteYPosition()) to self;
else
  generate GD_MD18:''Mouse Moved over Whitespace'' to self;
end if;
',
	'');
INSERT INTO SM_STATE
	VALUES (3670032,
	3670023,
	0,
	'Waiting for Mouse Press over Hotspot',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (3670032,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670019,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670032,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670023,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670032,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670032,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670032,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670032,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670032,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670032,
	3670023,
	3670032);
INSERT INTO SM_AH
	VALUES (3670032,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670032,
	3670023,
	1,
	'// If a symbol is being resized, unrelate the canvas from it
select one elem related by self->GD_GE[R12];
if (not_empty elem)
  select one resize related by self->GD_EIR[R12];
  resize.finish();
  unrelate self from elem across R12 using resize;
  delete object instance resize;
end if;
// If a line segment is being flexed, unrelate the canvas
select many flexes related by self->GD_SIF[R13];
for each flex in flexes
  flex.finish();
  select one seg related by flex->GD_LS[R13];
  unrelate self from seg across R13 using flex;
  delete object instance flex;
end for;
if (not_empty flexes)
  // Cause a transition back to Checking for Hotspots
  // to restore appropriate cursor shape.
  generate GD_MD4:''Mouse Moved''(X:0, Y:0) to self;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (3670033,
	3670023,
	0,
	'Moving Hotspot',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (3670033,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670033,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670033,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670033,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670033,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670033,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670033,
	3670023,
	3670033);
INSERT INTO SM_AH
	VALUES (3670033,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670033,
	3670023,
	1,
	'select one resize related by self->GD_EIR[R12];
if (not_empty resize)
  select one elem related by resize->GD_GE[R12];
  elem.moveVertex(xDelta:rcvd_evt.X, yDelta:rcvd_evt.Y,
                                                  Vertex:resize.Vertex, End:resize.End);
else
  select many flexes related by self->GD_SIF[R13];
  for each flex in flexes
    select one seg related by flex->GD_LS[R13];
    //
    // Note: Deltas passed to segment.move operations are scaled. This is because
    // Shape.moveVertex prescales the values before moving the connectors.
    // This should be refactored so that scaling happens either all at this level or
    // all at the lowest level.
    //
    if (flex.End == End::Start)
      seg.moveStart(xDelta:GR::scale(value:rcvd_evt.X), yDelta:GR::scale(value:rcvd_evt.Y));
    elif (flex.End == End::End)
      seg.moveEnd(xDelta:GR::scale(value:rcvd_evt.X), yDelta:GR::scale(value:rcvd_evt.Y));
    end if;
  end for;
end if;
GR::reDraw();',
	'');
INSERT INTO SM_STATE
	VALUES (3670034,
	3670023,
	0,
	'Waiting to Move Hotspot',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (3670034,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670034,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670034,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670034,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670034,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670034,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670034,
	3670023,
	3670034);
INSERT INTO SM_AH
	VALUES (3670034,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670034,
	3670023,
	1,
	'// Find the relevant symbol and relate the canvas to the symbol
found_elem = false;
select many symbols related by self->GD_GE[R1];
for each symbol in symbols
  if (symbol.isSelected() AND
      symbol.isOverVertex(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition()))
    create object instance resize of GD_EIR;
    relate self to symbol across R12 using resize;
    resize.initialize();
    found_elem = true;
    break;
  end if;
end for;
//
// We are not over a vertex of a shape, how about a waypoint?
//
if (not found_elem)
  select many cons related by symbols->GD_CON[R2];
  //
  // Check connectors ending on connectors first, because their waypoints can get overlaid on other waypoints.
  //
  checkingConsEndingOnCons = true;
  waypointCheckComplete = false;
  while (not waypointCheckComplete)
    for each con in cons
      select one targCon related by con->GD_GE[R4]->GD_CON[R2];
      if ((checkingConsEndingOnCons and not_empty targCon) or (not checkingConsEndingOnCons and empty targCon))
        select many segs related by con->GD_LS[R6];
        for each seg in segs
          if (seg.isSelected() AND seg.isOverVertex(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition()))
            create object instance flex of GD_SIF;
            relate self to seg across R13 using flex;
            flex.initialize();
            //
            // We want to stop early for associatives so that the user can pick up
            // an associative waypoint on top of another waypoint. Supertype connectors
            // do not have a target segment related via R5 and so we can tell one of those
            // from an Associative if there is a segment found.
            //
            select one targSeg related by targCon->GD_LS[R5];
            if (not_empty targSeg)
              found_elem = true;
            end if;
          end if;
        end for;
      end if;
    end for;
    if (not checkingConsEndingOnCons or found_elem)
      waypointCheckComplete = true;
    end if;
    checkingConsEndingOnCons = false;
  end while;
end if;',
	'');
INSERT INTO SM_STATE
	VALUES (3670035,
	3670023,
	0,
	'Ready to Slide Canvas',
	8,
	0);
INSERT INTO SM_SEME
	VALUES (3670035,
	3670017,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670035,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670021,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670022,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670035,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670025,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670035,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670035,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670035,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670035,
	3670023,
	3670035);
INSERT INTO SM_AH
	VALUES (3670035,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670035,
	3670023,
	1,
	'GR::setMouseCursor(type:"closed hand");
',
	'');
INSERT INTO SM_STATE
	VALUES (3670036,
	3670023,
	0,
	'Releasing Text in Move',
	17,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670017,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670017,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670018,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670018,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670019,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670019,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670020,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670020,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670021,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670021,
	3670023,
	0);
INSERT INTO SM_SEME
	VALUES (3670036,
	3670022,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670023,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670023,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670024,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670024,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670036,
	3670025,
	3670023,
	0,
	'The space bar can be pressed at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670025,
	3670023,
	0);
INSERT INTO SM_EIGN
	VALUES (3670036,
	3670026,
	3670023,
	0,
	'The space bar can be released at any time.  It can be ignored in all states except those
causing a transition.');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670026,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670027,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670027,
	3670023,
	0);
INSERT INTO SM_CH
	VALUES (3670036,
	3670028,
	3670023,
	0,
	'');
INSERT INTO SM_SEME
	VALUES (3670036,
	3670028,
	3670023,
	0);
INSERT INTO SM_MOAH
	VALUES (3670036,
	3670023,
	3670036);
INSERT INTO SM_AH
	VALUES (3670036,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670036,
	3670023,
	1,
	'self.disableTextMove();
generate GD_MD6:Done to self;',
	'');
INSERT INTO SM_NSTXN
	VALUES (3670017,
	3670023,
	3670018,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670017,
	3670023,
	3670020,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670018,
	3670023,
	3670020,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670022,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670020,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670020,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670020,
	3670023,
	3670017,
	3670019,
	0);
INSERT INTO SM_TXN
	VALUES (3670020,
	3670023,
	3670019,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670021,
	3670023,
	3670019,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670021,
	3670023,
	3670021,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670022,
	3670023,
	3670021,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670022,
	3670023,
	3670021,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670023,
	3670023,
	3670023,
	3670020,
	0);
INSERT INTO SM_TXN
	VALUES (3670023,
	3670023,
	3670017,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670024,
	3670023,
	3670022,
	3670022,
	0);
INSERT INTO SM_TXN
	VALUES (3670024,
	3670023,
	3670023,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670025,
	3670023,
	3670017,
	3670023,
	0);
INSERT INTO SM_TXN
	VALUES (3670025,
	3670023,
	3670024,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670026,
	3670023,
	3670024,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670026,
	3670023,
	3670021,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670027,
	3670023,
	3670023,
	3670024,
	0);
INSERT INTO SM_TXN
	VALUES (3670027,
	3670023,
	3670025,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670028,
	3670023,
	3670023,
	3670025,
	0);
INSERT INTO SM_TXN
	VALUES (3670028,
	3670023,
	3670026,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670029,
	3670023,
	3670027,
	3670026,
	0);
INSERT INTO SM_TXN
	VALUES (3670029,
	3670023,
	3670028,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670030,
	3670023,
	3670027,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670030,
	3670023,
	3670027,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670031,
	3670023,
	3670029,
	3670022,
	0);
INSERT INTO SM_TXN
	VALUES (3670031,
	3670023,
	3670023,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670032,
	3670023,
	3670028,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670032,
	3670023,
	3670029,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670033,
	3670023,
	3670030,
	3670022,
	0);
INSERT INTO SM_TXN
	VALUES (3670033,
	3670023,
	3670026,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670034,
	3670023,
	3670027,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670034,
	3670023,
	3670030,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670035,
	3670023,
	3670018,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670035,
	3670023,
	3670029,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670036,
	3670023,
	3670023,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670036,
	3670023,
	3670031,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670037,
	3670023,
	3670031,
	3670027,
	0);
INSERT INTO SM_TXN
	VALUES (3670037,
	3670023,
	3670023,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670038,
	3670023,
	3670031,
	3670028,
	0);
INSERT INTO SM_TXN
	VALUES (3670038,
	3670023,
	3670032,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670039,
	3670023,
	3670032,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670039,
	3670023,
	3670031,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670040,
	3670023,
	3670033,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670040,
	3670023,
	3670033,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670041,
	3670023,
	3670033,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670041,
	3670023,
	3670032,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670042,
	3670023,
	3670032,
	3670020,
	0);
INSERT INTO SM_TXN
	VALUES (3670042,
	3670023,
	3670034,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670043,
	3670023,
	3670034,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670043,
	3670023,
	3670033,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670044,
	3670023,
	3670034,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670044,
	3670023,
	3670032,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670045,
	3670023,
	3670026,
	3670020,
	0);
INSERT INTO SM_TXN
	VALUES (3670045,
	3670023,
	3670035,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670046,
	3670023,
	3670035,
	3670017,
	0);
INSERT INTO SM_TXN
	VALUES (3670046,
	3670023,
	3670027,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670047,
	3670023,
	3670035,
	3670026,
	0);
INSERT INTO SM_TXN
	VALUES (3670047,
	3670023,
	3670028,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670048,
	3670023,
	3670035,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670048,
	3670023,
	3670030,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670049,
	3670023,
	3670021,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670049,
	3670023,
	3670036,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670050,
	3670023,
	3670036,
	3670022,
	0);
INSERT INTO SM_TXN
	VALUES (3670050,
	3670023,
	3670023,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670051,
	3670023,
	3670024,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670051,
	3670023,
	3670036,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670052,
	3670023,
	3670019,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670052,
	3670023,
	3670036,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670053,
	3670023,
	3670026,
	3670026,
	0);
INSERT INTO SM_TXN
	VALUES (3670053,
	3670023,
	3670023,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670054,
	3670023,
	3670017,
	3670021,
	0);
INSERT INTO SM_TXN
	VALUES (3670054,
	3670023,
	3670018,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670055,
	3670023,
	3670025,
	3670018,
	0);
INSERT INTO SM_TXN
	VALUES (3670055,
	3670023,
	3670031,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670056,
	3670023,
	3670032,
	3670024,
	0);
INSERT INTO SM_TXN
	VALUES (3670056,
	3670023,
	3670025,
	0);
INSERT INTO SM_NSTXN
	VALUES (3670057,
	3670023,
	3670026,
	3670024,
	0);
INSERT INTO SM_TXN
	VALUES (3670057,
	3670023,
	3670025,
	0);
INSERT INTO O_OBJ
	VALUES (3145730,
	'Graphical Element',
	2,
	'GD_GE',
	'Graphical element abstracts the different shapes and lines that make up the graphical representation of a model on the canvas.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145738,
	3145730,
	'draw',
	'To draw an element, first ensure that the ''represents'' cache
value is initialized, then set the line width as appropriate for
the selection status. Finally, delegate the draw operation to
the appropriate subtype instance.',
	524289,
	1,
	'select one elemSpec related by self->GD_ES[R10];
if (empty elemSpec)
  select any matchingElemSpec related by self->GD_MD[R1]->GD_MS[R9]->GD_EMS[R11]->
                                          GD_ES[R11] where (selected.OOA_Type == self.OOA_Type);
  if (not_empty matchingElemSpec)
    elemSpec = matchingElemSpec;
    relate elemSpec to self across R10;
  end if;
end if;
if (not_empty elemSpec)
  GR::setFillTo(context:param.context, new:elemSpec.internal);
else
  GR::setDefaultFill(context:param.context);
end if;
//
// Ensure the represented instance is cached
//
if (CL::isEmpty(element:self.represents))
  self.represents = CL::getInstanceFromOOA_ID(OOA_Type:self.OOA_Type,
                                                                              OOA_ID:self.OOA_ID);
end if;
//
// if the symbol is selected, set the line width to bold
//
if (self.isSelected() == true AND NOT param.disable_selection)
  GR::setLineWidth(context:param.context, w:2);
else
  GR::setLineWidth(context:param.context, w:1);
end if;
//
// Delegate to Subtypes
//
select one connector related by self->GD_CON[R2];
if (not_empty connector)
  connector.draw(context:param.context, disable_selection:param.disable_selection);
  return;
end if;
select one shape related by self->GD_SHP[R2];
if (not_empty shape)
  shape.draw(context:param.context, disable_selection:param.disable_selection);
  return;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145745,
	3145738,
	'context',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (3145746,
	3145738,
	'disable_selection',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145739,
	3145730,
	'isSelected',
	'To determine if a Graphical_Element is selected,
delegate the operation call to the appropriate 
subtype. If for some reason no subtype is
found return false (this is an internal error).',
	524290,
	1,
	'select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  return shp.isSelected();
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    return con.isSelected();
  end if;
end if;
return false;
',
	1);
INSERT INTO O_TFR
	VALUES (3145740,
	3145730,
	'isOver',
	'',
	524290,
	1,
	'select one shape related by self->GD_SHP[R2];
if (not_empty shape)
  return shape.isOver(x:param.x, y:param.y);
end if;
select one con related by self->GD_CON[R2];
if (not_empty con)
  return con.isOver(x:param.x, y:param.y);
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145747,
	3145740,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145748,
	3145740,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145741,
	3145730,
	'toggleSelect',
	'',
	524289,
	1,
	'if (CL::isEmpty(element:self.represents))
  self.represents = CL::getInstanceFromOOA_ID(OOA_Type:self.OOA_Type, OOA_ID:self.OOA_ID);
end if;
if (CL::isSelected(element:self.represents))
  CL::removeFromSelection(element:self.represents);
else
  CL::addToSelection(element:self.represents);
end if;',
	1);
INSERT INTO O_TFR
	VALUES (3145742,
	3145730,
	'move',
	'',
	524289,
	1,
	'select one shape related by self->GD_SHP[R2];
if (not_empty shape)
  shape.move(xDelta:param.xDelta, yDelta:param.yDelta);
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3145749,
	3145742,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145750,
	3145742,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145743,
	3145730,
	'isInside',
	'',
	524290,
	1,
	'select one shape related by self->GD_SHP[R2];
if (not_empty shape)
    return shape.isInside(x:param.x, y:param.y, w:param.w, h:param.h);
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    return con.isInside(x:param.x, y:param.y, w:param.w, h:param.h);
  end if;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145751,
	3145743,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145752,
	3145743,
	'y',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145753,
	3145743,
	'w',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145754,
	3145743,
	'h',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145744,
	3145730,
	'isOverVertex',
	'',
	524290,
	1,
	'select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  return shp.isOverVertex(x:param.x, y:param.y);
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    return con.isOverVertex(x:param.x, y:param.y);
  end if;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145755,
	3145744,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145756,
	3145744,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145745,
	3145730,
	'moveVertex',
	'',
	524289,
	1,
	'//
// Move the relevant vertex
//
select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  shp.moveVertex(xDelta:param.xDelta, yDelta:param.yDelta,
                              Vertex:param.Vertex);
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    con.moveVertex(xDelta:param.xDelta, yDelta:param.yDelta,
                                Vertex:param.Vertex, End:param.End);
  end if;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145757,
	3145745,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145758,
	3145745,
	'yDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145759,
	3145745,
	'Vertex',
	524318,
	0);
INSERT INTO O_TPARM
	VALUES (3145760,
	3145745,
	'End',
	524311,
	0);
INSERT INTO O_TFR
	VALUES (3145746,
	3145730,
	'getVertexUnder',
	'',
	524318,
	1,
	'select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  if (shp.isOverNW(x:param.x, y:param.y))
    return Vertex::NW;
  end if;
  if (shp.isOverSE(x:param.x, y:param.y))
    return Vertex::SE;
  end if;
  if (shp.isOverNE(x:param.x, y:param.y))
    return Vertex::NE;
  end if;
  if (shp.isOverSW(x:param.x, y:param.y))
    return Vertex::SW;
  end if;  
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    return con.getVertexUnder(x:param.x, y:param.y);
  end if;
end if;
return Vertex::None;',
	1);
INSERT INTO O_TPARM
	VALUES (3145761,
	3145746,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145762,
	3145746,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145747,
	3145730,
	'getEndUnder',
	'',
	524311,
	1,
	'select one con related by self->GD_CON[R2];
if (not_empty con)
  return con.getEndUnder(x:param.x, y:param.y);
end if;
return End::None;',
	1);
INSERT INTO O_TPARM
	VALUES (3145763,
	3145747,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145764,
	3145747,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145748,
	3145730,
	'isOverNWSE',
	'',
	524290,
	1,
	'select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  if (shp.isOverNW(x:param.x, y:param.y))
    return true;
  end if;
  if (shp.isOverSE(x:param.x, y:param.y))
    return true;
  end if;
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    vertex = self.getVertexUnder(x:param.x, y:param.y);
    return (vertex == Vertex::NW OR vertex == Vertex::SE);
  end if;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145765,
	3145748,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145766,
	3145748,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145749,
	3145730,
	'getXIntersect',
	'',
	524291,
	1,
	'intersect = 0;
select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  intersect = shp.getXIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2);
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    intersect = con.getIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2, axis:Axis::X);
  end if;
end if;
return intersect;',
	1);
INSERT INTO O_TPARM
	VALUES (3145767,
	3145749,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145768,
	3145749,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145769,
	3145749,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145770,
	3145749,
	'y2',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145750,
	3145730,
	'getYIntersect',
	'',
	524291,
	1,
	'intersect = 0;
select one shp related by self->GD_SHP[R2];
if (not_empty shp)
  intersect = shp.getYIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2);
else
  select one con related by self->GD_CON[R2];
  if (not_empty con)
    intersect = con.getIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2, axis:Axis::Y);
  end if;
end if;
return intersect;',
	1);
INSERT INTO O_TPARM
	VALUES (3145771,
	3145750,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145772,
	3145750,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145773,
	3145750,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145774,
	3145750,
	'y2',
	524291,
	0);
INSERT INTO O_NBATTR
	VALUES (3145749,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145749,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145749,
	3145730,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the graphical element.
Full Name: Graphical Element Identifier
',
	'',
	'GE_ID',
	0,
	524291);
INSERT INTO O_REF
	VALUES (3145730,
	3145729,
	0,
	3145729,
	3145729,
	3145730,
	3145729,
	3145750,
	3145734,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145750,
	3145730,
	3145729,
	3145729,
	1);
INSERT INTO O_ATTR
	VALUES (3145750,
	3145730,
	3145749,
	'Model_ID',
	'Description: This is the model that this graphical element is part of.
',
	'',
	'Model_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145751,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145751,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145751,
	3145730,
	3145750,
	'OOA_ID',
	'Description: The identifier of the OOA model element that this graphical element  is graphical information for.   Note that there may be multiple graphical elements needed to capture all the graphical data for one OOA model element.  E.g., relationships.
Full Name: OOA Identifier

',
	'',
	'OOA_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145730,
	3145736,
	0,
	3145793,
	3145738,
	3145749,
	3145748,
	3145752,
	3145735,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145752,
	3145730,
	3145793,
	3145736,
	1);
INSERT INTO O_ATTR
	VALUES (3145752,
	3145730,
	3145751,
	'OOA_Type',
	'',
	'',
	'OOA_Type',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145753,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145753,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145753,
	3145730,
	3145752,
	'represents',
	'',
	'',
	'represents',
	0,
	524306);
INSERT INTO O_ID
	VALUES (0,
	3145730);
INSERT INTO O_OIDA
	VALUES (3145749,
	3145730,
	0);
INSERT INTO O_RTIDA
	VALUES (3145749,
	3145730,
	0,
	3145730,
	3145731);
INSERT INTO O_RTIDA
	VALUES (3145749,
	3145730,
	0,
	3145735,
	3145743);
INSERT INTO O_RTIDA
	VALUES (3145749,
	3145730,
	0,
	3145736,
	3145745);
INSERT INTO O_RTIDA
	VALUES (3145749,
	3145730,
	0,
	3145741,
	3145757);
INSERT INTO O_OBJ
	VALUES (3145731,
	'Shape',
	3,
	'GD_SHP',
	'This represents the rectangular space taken by a shape on the model.  The actual shape drawn may be an oval, partitioned rectangle, etc.  Note: sometimes BridgePoint reverses the NW and SE coordinates of the shape when populating the graphical subsystem model.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145751,
	3145731,
	'draw',
	'First adjust the x and y values for the canvas scroll position.
Then draw the appropriate shape (this is obtained from the
client).

Set clipping to the bounding rectangle of the shape, so that
text that is too long for the symbol does not spill onto the 
canvas. Then show the text for each symbol compartment.

Each compartment can potentially show four different blocks
of text, Left Justified, Centered, Right Justified and Bottom
Justified. The deepest text block of the first three is used to
determine where the bottom of the compartment is.

If there is more than one compartment, draw a horizontal
dividing line between the text blocks. Repeat the text drawing
procedure for each compartment.
',
	524289,
	1,
	'tabHeightRatio = 5;
tabWidthRatio = 10;
select one shape related by self->GD_GE[R2];
select one canvas related by shape->GD_MD[R1];
//
// Correct for scroll position
//
x = self.NW_X  - canvas.ScrollXPos; y = self.NW_Y - canvas.ScrollYPos;
w = self.SE_X - self.NW_X; h = self.SE_Y - self.NW_Y;
//
// Draw the shape
//
if (CL::getShapeStyle(from:shape.represents) == Style::RoundBox)
  GR::drawRoundRect(context:param.context, x:x, y:y, w:w, h:h);
elif (CL::getShapeStyle(from:shape.represents) == Style::Folder)
  tabHeight = h/tabHeightRatio;
  if (w < h*2)
    tabHeight = w/tabWidthRatio;
  end if;
  GR::drawFolder(context:param.context, x:x, y:y, w:w, h:h, t:tabHeight);
else
  // default symbol is Style::Box
  GR::drawRect(context:param.context, x:x, y:y, w:w, h:h);
end if;
//
// If we''re drawing a folder, increase y by the tabSize to limit the text area to the box below the tab
//
if (CL::getShapeStyle(from:shape.represents) == Style::Folder)
  tabHeight = h/tabHeightRatio;
  if (w < h*2)
    tabHeight = w/tabWidthRatio;
  end if;
  y = y + tabHeight;
  h = h - tabHeight;
end if;
lastAvailableVerticalPosn = y + h;
//
// Do not let any graphics spill out onto the canvas
//
GR::clipTo(context:param.context, x:x, y:y, w:w, h:h);
compartment_id = 1;
unScaledSpacing = GR::getGraphicSpacing();
spacing = GR::scale(value:unScaledSpacing);
compartments = CL::getCompartments(from:shape.represents);
while (compartment_id <= compartments)
  entry_id = 1;
  entries = CL::getEntries(from:shape.represents, compartment_id:compartment_id);
  while (entry_id <= entries)
    //
    // Draw the left justified text for the compartment
    //
    leftResult = CL::getCompartmentText(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:entry_id, at:Justification::Left);
    leftStyle = CL::getTextStyle(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:entry_id, at:Justification::Left);
    rightResult = CL::getCompartmentText(from:shape.represents,  compartment_id:compartment_id,
                                                          entry_id:entry_id, at:Justification::Right);
    rightStyle = CL::getTextStyle(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:entry_id, at:Justification::Right);
    availableRHSpace = GR::getTextExtent(context:param.context, text:rightResult,axis:Axis::X);
    if (rightResult != "")
      widthLeftForLeftText = availableRHSpace + spacing;
      if (w - widthLeftForLeftText > 36) // (pixels)
        leftResult = GR::fitTextTo(context:param.context, input:leftResult, width:w - widthLeftForLeftText);
      else
        // Not enough room for left and right text blocks, so split the symbol 50-50
        availableRHSpace = w/2 - spacing;
        leftResult = GR::fitTextTo(context:param.context, input:leftResult, width:availableRHSpace);
      end if;
    end if;
    Y_extent = 0;
    if (leftResult != "")
      leftResult = GR::fitTextTo(context:param.context, input:leftResult, width:w - (unScaledSpacing * 2));
      verticalPosnAfterPrinting = y + GR::getTextExtent(context:param.context, text:leftResult,axis:Axis::Y);
      //
      // If the text won''t fit completely, then don''t show it unless it is a single entry like a state action
      //
      if ((verticalPosnAfterPrinting < lastAvailableVerticalPosn) OR (entries == 1 AND OS::isMultiLine(text:leftResult)))
        GR::drawText(context:param.context, text:leftResult,
                                                                      x:x + spacing, 
                                                                      y:y + spacing,
                                                                      justified_to:Justification::Left, text_style:leftStyle);
      end if;
      Y_extent = GR::getTextExtent(context:param.context, text:leftResult, axis:Axis::Y);
    end if;
    //
    // Draw the right justified text for the compartment
    //
    if (rightResult != "")
      if (availableRHSpace < GR::getTextExtent(context:param.context, text:rightResult, axis:Axis::X))
        rightResult = GR::fitTextTo(context:param.context, input:rightResult, width:availableRHSpace);
      end if;
      verticalPosnAfterPrinting = y + GR::getTextExtent(context:param.context, text:rightResult,axis:Axis::Y);
      //
      // As for left justified text, if the text won''t fit completely, then don''t
      //  show it (we don''t care about multiline-ism for right justified text).
      //
      if (verticalPosnAfterPrinting < lastAvailableVerticalPosn)
        GR::drawText(context:param.context, text:rightResult, 
                                                                    x:x + w - spacing,
                                                                    y:y + spacing,
                                                                    justified_to:Justification::Right, text_style:rightStyle);
      end if;
      Y_extent = ::max(a:Y_extent, b:GR::getTextExtent(context:param.context, text:rightResult, axis:Axis::Y));
    end if;
    //
    // Draw the centered text for the compartment
    //
    centerResult = CL::getCompartmentText(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:entry_id, at:Justification::Center_in_X);
    centerXStyle = CL::getTextStyle(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:entry_id, at:Justification::Center_in_X);
    if (centerResult != "")
      centerResult = GR::fitTextTo(context:param.context, input:centerResult, width:w - (unScaledSpacing * 2));
      verticalPosnAfterPrinting = y + GR::getTextExtent(context:param.context, text:centerResult,axis:Axis::Y);
      //
      // Again for centered text, don''t print partially visible text
      //
      if (verticalPosnAfterPrinting < lastAvailableVerticalPosn)
        GR::drawText(context:param.context, text:centerResult, 
                                                                  x:x + (w/2),
                                                                  y:y + spacing,
                                                                  justified_to:Justification::Center_in_X, text_style:centerXStyle);
      end if;
      Y_extent = ::max(a:Y_extent, b:GR::getTextExtent(context:param.context, text:centerResult, axis:Axis::Y));
    end if;
    //
    // Move y down for next entry
    //
    y = y + Y_extent;
    entry_id = entry_id + 1;
  end while;
  //
  // Move y down for next compartment
  //
  if (compartment_id != CL::getCompartments(from:shape.represents))
    y = y +  (spacing * 2);
    GR::drawLine(context:param.context, x:x, y:y, x2:x + w, y2:y);
  end if;
  compartment_id = compartment_id + 1;
end while;
//
//  Preserve last y position for later comparison to prevent xy
//  centered text overwriting other previously written text.
//
lastVerticalPosn = y;
//
// Restore y and h to start positions
//
y = self.NW_Y - canvas.ScrollYPos;
h = self.SE_Y - self.NW_Y;
//
// Draw the bottom text for the symbol
//
result = CL::getCompartmentText(from:shape.represents,  compartment_id:compartment_id, 
                                                      entry_id:0, at:Justification::Bottom);
bottomStyle = CL::getTextStyle(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:0, at:Justification::Bottom);
if (result != "")
  GR::drawText(context:param.context, text:result,  x:x + w/2,  y:y + h, 
                                                        justified_to:Justification::Bottom, text_style:bottomStyle);
end if;
//
// If we''re drawing a folder, increase y by the tabSize to limit the text area to the box below the tab
//
if (CL::getShapeStyle(from:shape.represents) == Style::Folder)
  tabHeight = h/tabHeightRatio;
  if (w < h*2)
    tabHeight = w/tabWidthRatio;
  end if;
  y = y + tabHeight;
  h = h - tabHeight;
end if;
//
// Draw the centered text for the symbol
//
result = CL::getCompartmentText(from:shape.represents,  compartment_id:compartment_id,
                                                      entry_id:0, at:Justification::Center);
centerStyle = CL::getTextStyle(from:shape.represents, compartment_id:compartment_id,
                                                          entry_id:0, at:Justification::Center);
if (result != "")
  result = GR::fitTextTo(context:param.context, input:result, width:w - (unScaledSpacing * 2));
  labelVerticalPosn = y + (h/2) - (GR::getTextExtent(context:param.context, text:result, axis:Axis::Y) / 2);
  if (lastVerticalPosn < labelVerticalPosn)
    GR::drawText(context:param.context, text:result, 
                          x:x + (w/2),
                          y:y + (h/2),
                          justified_to:Justification::Center, text_style:centerStyle);
  end if;
end if;
//
// Derestrict canvas access
//
GR::unClip(context:param.context);
//
// If selected, draw the resize handles
//
if (CL::isSelected(element:shape.represents) AND NOT param.disable_selection)
  //
  // Restore y and h to start positions
  //
  y = self.NW_Y - canvas.ScrollYPos;
  h = self.SE_Y - self.NW_Y;
  GR::drawResizeHandles(Context:param.context, x:x, y:y, w:w, h:h);
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145775,
	3145751,
	'context',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (3145776,
	3145751,
	'disable_selection',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145752,
	3145731,
	'isOverNW',
	'',
	524290,
	1,
	'select one mdl related by self->GD_GE[R2]->GD_MD[R1];
return GR::isWithinHotspot(x:GR::scale(value:param.x) + mdl.ScrollXPos,
                                            y:GR::scale(value:param.y) + mdl.ScrollYPos,
                                            x2:self.NW_X, y2:self.NW_Y);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145777,
	3145752,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145778,
	3145752,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145753,
	3145731,
	'isOverSE',
	'',
	524290,
	1,
	'select one mdl related by self->GD_GE[R2]->GD_MD[R1];
return GR::isWithinHotspot(x:GR::scale(value:param.x) + mdl.ScrollXPos,
                                            y:GR::scale(value:param.y) + mdl.ScrollYPos,
                                            x2:self.SE_X, y2:self.SE_Y);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145779,
	3145753,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145780,
	3145753,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145754,
	3145731,
	'isOverNE',
	'',
	524290,
	1,
	'select one mdl related by self->GD_GE[R2]->GD_MD[R1];
return GR::isWithinHotspot(x:GR::scale(value:param.x) + mdl.ScrollXPos,
                                            y:GR::scale(value:param.y) + mdl.ScrollYPos,
                                            x2:self.SE_X, y2:self.NW_Y);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145781,
	3145754,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145782,
	3145754,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145755,
	3145731,
	'isOverSW',
	'',
	524290,
	1,
	'select one mdl related by self->GD_GE[R2]->GD_MD[R1];
return GR::isWithinHotspot(x:GR::scale(value:param.x) + mdl.ScrollXPos,
                                            y:GR::scale(value:param.y) + mdl.ScrollYPos,
                                            x2:self.NW_X, y2:self.SE_Y);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145783,
	3145755,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145784,
	3145755,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145756,
	3145731,
	'isSelected',
	'',
	524290,
	1,
	'select one gr_elem related by self->GD_GE[R2];
if (CL::isEmpty(element:gr_elem.represents))
 return false;
else
  return CL::isSelected(element:gr_elem.represents);
end if;',
	1);
INSERT INTO O_TFR
	VALUES (3145757,
	3145731,
	'isOverVertex',
	'',
	524290,
	1,
	'if (self.isOverNW(x:param.x, y:param.y))
  return true;
end if;
if (self.isOverSW(x:param.x, y:param.y))
  return true;
end if;
if (self.isOverNE(x:param.x, y:param.y))
  return true;
end if;
if (self.isOverSE(x:param.x, y:param.y))
  return true;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145785,
	3145757,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145786,
	3145757,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145758,
	3145731,
	'isOver',
	'',
	524290,
	1,
	'select one canvas related by self->GD_GE[R2]->GD_MD[R1];
x = self.NW_X  - canvas.ScrollXPos;
y = self.NW_Y - canvas.ScrollYPos;
w = self.SE_X - self.NW_X;
h = self.SE_Y - self.NW_Y;
if (GR::scale(value:param.x) > x and GR::scale(value:param.x) < x +w)
  if (GR::scale(value:param.y) > y and GR::scale(value:param.y) < y +h)
    return true;
  end if;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145787,
	3145758,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145788,
	3145758,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145759,
	3145731,
	'move',
	'',
	524289,
	1,
	'//
// Move the shape coordinates
//
self.NW_X = self.NW_X + GR::scale(value:param.xDelta);
self.NW_Y = self.NW_Y + GR::scale(value:param.yDelta);
self.SE_X = self.SE_X + GR::scale(value:param.xDelta);
self.SE_Y = self.SE_Y + GR::scale(value:param.yDelta);
select one elem related by self->GD_GE[R2];
select many cons related by elem->GD_CON[R3];
for each con in cons
  con.moveStart(xDelta:GR::scale(value:param.xDelta), yDelta:GR::scale(value:param.yDelta));
end for;
select many cons related by elem->GD_CON[R4];
for each con in cons
  con.moveEnd(xDelta:GR::scale(value:param.xDelta), yDelta:GR::scale(value:param.yDelta));
end for;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145789,
	3145759,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145790,
	3145759,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145760,
	3145731,
	'isInside',
	'',
	524290,
	1,
	'select one canvas related by self->GD_GE[R2]->GD_MD[R1];
x = self.NW_X - canvas.ScrollXPos;
y = self.NW_Y - canvas.ScrollYPos;
w = self.SE_X - self.NW_X;
h = self.SE_Y - self.NW_Y;
if (GR::scale(value:param.x) < x and 
         GR::scale(value:param.x + param.w) > (x+w))
  if (GR::scale(value:param.y) < y and
             GR::scale(value:param.y +param.h) > (y+h))
    return true;
  end if;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145791,
	3145760,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145792,
	3145760,
	'y',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145793,
	3145760,
	'w',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145794,
	3145760,
	'h',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145761,
	3145731,
	'moveConnectors',
	'',
	524289,
	1,
	'//
// Move all connectors which start at this shape
//
select many cons related by self->GD_GE[R2]->GD_CON[R3];
for each con in cons
  moveX = con.getXDeltaForCropTo(delta:param.xDelta, vertex:param.Vertex, end:End::Start);
  moveY = con.getYDeltaForCropTo(delta:param.yDelta, vertex:param.Vertex, end:End::Start);
  if (moveX != 0 or moveY != 0)
    con.moveStart(xDelta:moveX, yDelta:moveY);
  end if;
end for;
//
// Repeat for connectors which end at this shape . . . 
//
select many cons related by self->GD_GE[R2]->GD_CON[R4];
for each con in cons
  moveX = con.getXDeltaForCropTo(delta:param.xDelta, vertex:param.Vertex, end:End::End);
  moveY = con.getYDeltaForCropTo(delta:param.yDelta, vertex:param.Vertex, end:End::End);
  if (moveX != 0 or moveY != 0)
    con.moveEnd(xDelta:moveX, yDelta:moveY);
  end if;
end for;',
	1);
INSERT INTO O_TPARM
	VALUES (3145795,
	3145761,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145796,
	3145761,
	'yDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145797,
	3145761,
	'Vertex',
	524318,
	0);
INSERT INTO O_TFR
	VALUES (3145762,
	3145731,
	'moveVertex',
	'',
	524289,
	1,
	'select one canvas related by self->GD_GE[R2]->GD_MD[R1];
nw_x = self.NW_X;
nw_y = self.NW_Y;
se_x = self.SE_X;
se_y = self.SE_Y;
if (param.Vertex == Vertex::NW)
  nw_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  nw_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if (param.Vertex == Vertex::SE)
  se_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  se_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if (param.Vertex == Vertex::NE)
  se_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  nw_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if (param.Vertex == Vertex::SW)
  nw_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  se_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
//
// Only change the shape size if it is not too small
//
if ((se_x - nw_x > GR::getHotspotSize() * 3) AND (se_y- nw_y > GR::getHotspotSize() * 3))
  //
  // Only move a connector if the shape has actually changed.
  // Connectors must be moved in the x and y axes simultaneously.
  //
  if (self.NW_X != nw_x);
    if (self.NW_Y != nw_y);
      self.moveConnectors(xDelta:nw_x - self.NW_X, yDelta:nw_y - self.NW_Y, Vertex:param.Vertex);
      self.NW_Y = nw_y;
    elif (self.SE_Y != se_y);
      self.moveConnectors(xDelta:nw_x - self.NW_X, yDelta:se_y - self.SE_Y, Vertex:param.Vertex);
      self.SE_Y = se_y;
    else
      self.moveConnectors(xDelta:nw_x - self.NW_X, yDelta:0, Vertex:param.Vertex);
    end if;
    self.NW_X = nw_x;
  elif (self.SE_X != se_x);
    if (self.NW_Y != nw_y);
      self.moveConnectors(xDelta:se_x - self.SE_X, yDelta:nw_y - self.NW_Y, Vertex:param.Vertex);
      self.NW_Y = nw_y;
    elif (self.SE_Y != se_y);
      self.moveConnectors(xDelta:se_x - self.SE_X, yDelta:se_y - self.SE_Y, Vertex:param.Vertex);
      self.SE_Y = se_y;
    else
      self.moveConnectors(xDelta:se_x - self.SE_X, yDelta:0, Vertex:param.Vertex);
    end if;
    self.SE_X = se_x;
  else
    if (self.NW_Y != nw_y);
      self.moveConnectors(xDelta:0, yDelta:nw_y - self.NW_Y, Vertex:param.Vertex);
      self.NW_Y = nw_y;
    end if;
    if (self.SE_Y != se_y);
      self.moveConnectors(xDelta:0, yDelta:se_y - self.SE_Y, Vertex:param.Vertex);
      self.SE_Y = se_y;
    end if;
  end if;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145798,
	3145762,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145799,
	3145762,
	'yDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145800,
	3145762,
	'Vertex',
	524318,
	0);
INSERT INTO O_TFR
	VALUES (3145763,
	3145731,
	'getXIntersect',
	'',
	524291,
	1,
	'return self.getIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2, axis:Axis::X);',
	1);
INSERT INTO O_TPARM
	VALUES (3145801,
	3145763,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145802,
	3145763,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145803,
	3145763,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145804,
	3145763,
	'y2',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145764,
	3145731,
	'getYIntersect',
	'',
	524291,
	1,
	'return self.getIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2, axis:Axis::Y);',
	1);
INSERT INTO O_TPARM
	VALUES (3145805,
	3145764,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145806,
	3145764,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145807,
	3145764,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145808,
	3145764,
	'y2',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145765,
	3145731,
	'getIntersect',
	'',
	524291,
	1,
	'minX = ::min(a:param.x1, b:param.x2);
maxX = ::max(a:param.x1, b:param.x2);
minY = ::min(a:param.y1, b:param.y2);
maxY = ::max(a:param.y1, b:param.y2);
//
// Look for an intersection with all four sides of the shape.
//
// Top
intersectX = GR::getXIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.NW_X, y3:self.NW_Y, x4:self.SE_X, y4:self.NW_Y);
intersectY = GR::getYIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.NW_X, y3:self.NW_Y, x4:self.SE_X, y4:self.NW_Y);
if ((intersectX >= minX and intersectX <= maxX) and
    (intersectY >= minY and intersectY <= maxY))
  if ((intersectX >= self.NW_X and intersectX <= self.SE_X) and
    (intersectY == self.NW_Y))
    if (param.axis == Axis::X)
      return intersectX;
    else
      return intersectY;
    end if;
  end if;
end if;
// Bottom
intersectX = GR::getXIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.NW_X, y3:self.SE_Y, x4:self.SE_X, y4:self.SE_Y);
intersectY = GR::getYIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.NW_X, y3:self.SE_Y, x4:self.SE_X, y4:self.SE_Y);
if ((intersectX >= minX and intersectX <= maxX) and
    (intersectY >= minY and intersectY <= maxY))
  if ((intersectX >= self.NW_X and intersectX <= self.SE_X) and
    (intersectY == self.SE_Y))
    if (param.axis == Axis::X)
      return intersectX;
    else
      return intersectY;
    end if;
  end if;
end if;
// Left
intersectX = GR::getXIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.NW_X, y3:self.NW_Y, x4:self.NW_X, y4:self.SE_Y);
intersectY = GR::getYIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.NW_X, y3:self.NW_Y, x4:self.NW_X, y4:self.SE_Y);
if ((intersectX >= minX and intersectX <= maxX) and
    (intersectY >= minY and intersectY <= maxY))
  if ((intersectX == self.NW_X) and
    (intersectY >= self.NW_Y and intersectY <= self.SE_Y))
    if (param.axis == Axis::X)
      return intersectX;
    else
      return intersectY;
    end if;
  end if;
end if;
// Right
intersectX = GR::getXIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.SE_X, y3:self.NW_Y, x4:self.SE_X, y4:self.SE_Y);
intersectY = GR::getYIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:self.SE_X, y3:self.NW_Y, x4:self.SE_X, y4:self.SE_Y);
if ((intersectX >= minX and intersectX <= maxX) and
    (intersectY >= minY and intersectY <= maxY))
  if ((intersectX == self.SE_X) and
    (intersectY >= self.NW_Y and intersectY <= self.SE_Y))
    if (param.axis == Axis::X)
      return intersectX;
    else
      return intersectY;
    end if;
  end if;
end if;
return 0;',
	1);
INSERT INTO O_TPARM
	VALUES (3145809,
	3145765,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145810,
	3145765,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145811,
	3145765,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145812,
	3145765,
	'y2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145813,
	3145765,
	'axis',
	524309,
	0);
INSERT INTO O_REF
	VALUES (3145731,
	3145730,
	0,
	3145749,
	3145730,
	3145732,
	3145731,
	3145754,
	3145729,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145754,
	3145731,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145754,
	3145731,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the shape.
Full Name: Graphical Element Identifier',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145755,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145755,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145755,
	3145731,
	3145754,
	'NW_X',
	'Description: The X coordinate of the upper left corner of the shape.
Full Name: Northwest X coordinate',
	'',
	'NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145756,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145756,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145756,
	3145731,
	3145755,
	'NW_Y',
	'Description: The Y coordinate of the upper left corner of the shape.
Full Name: Northwest Y coordinate',
	'',
	'NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145757,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145757,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145757,
	3145731,
	3145756,
	'SE_X',
	'Description: The X coordinate of the lower right corner of the shape.
Full Name: Southeast X coordinate',
	'',
	'SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145758,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145758,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145758,
	3145731,
	3145757,
	'SE_Y',
	'Description: The Y coordinate of the lower right corner of the shape.
Full Name: Southeast Y coordinate',
	'',
	'SE_Y',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145731);
INSERT INTO O_OIDA
	VALUES (3145754,
	3145731,
	0);
INSERT INTO O_OBJ
	VALUES (3145732,
	'Connector',
	4,
	'GD_CON',
	'A connector represents a connection between two graphical elements.  The connection is represented by a set of lines.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145766,
	3145732,
	'draw',
	'Draws a connector by selecting all the segments
and drawing them. This operation also takes care
of obtaining and printing the fixed and floating text
associated with a connector.',
	524289,
	1,
	'select one con related by self->GD_GE[R2];
//
// Set the connector line style.
// Most connectors are solid lines, but associative connectors are broken lines
//
GR::setLineStyle(context:param.context, new_style:Style::Solid);
select one assocCon related by self->GD_GE[R4]->GD_CON[R2]->GD_GE[R2];
if (not_empty assocCon)
  if(CL::getConnectorStyle(from:assocCon.represents, at:End::Start) == Style::None)
    //
    // This test needed to disambiguate associative lines from subtype lines.
    // These also end at a connector, but in that case its a supertype connector
    // so the style at the start of the line would be Style::Triangle.
    //
    GR::setLineStyle(context:param.context, new_style:Style::Broken);
  end if;
end if;
select one canvas related by con->GD_MD[R1];
//
// Draw the connector
//
select many segments related by self->GD_LS[R6];
for each con_segment in segments
  //
  // Correct for scroll position
  //
  x = con_segment.Start_X  - canvas.ScrollXPos;
  y = con_segment.Start_Y - canvas.ScrollYPos;
  x2 = con_segment.End_X  - canvas.ScrollXPos;
  y2 = con_segment.End_Y - canvas.ScrollYPos;
  GR::drawLine(context:param.context, x:x, y:y,x2:x2,y2:y2);
  if (self.isSelected()  and not param.disable_selection)
    GR::drawFlexHandles(context:param.context, x:x, y:y, radius:GR::getHotspotSize());
    select one nextSegment related by con_segment->GD_LS[R7.''precedes''];
    if (empty nextSegment)
      //
      // Only draw the end point on the last segment
      //
    GR::drawFlexHandles(context:param.context, x:x2, y:y2, radius:GR::getHotspotSize());
    end if;
  end if;
end for;
//
// Draw text phrases
//
// Start text
//
select one startElem related by self->GD_GE[R3];
select any seg related by self->GD_LS[R6];
//
// Correct for scroll position
//
x1 = seg.Start_X  - canvas.ScrollXPos;
y1 = seg.Start_Y - canvas.ScrollYPos;
x2 = seg.End_X  - canvas.ScrollXPos;
y2 = seg.End_Y - canvas.ScrollYPos;
self.drawText(context:param.context,
                        text:CL::getConnectorText(from:con.represents, end_OOA_ID:startElem.OOA_ID,
                                                                    end_OOA_Type:startElem.OOA_Type, at:End::Start),
                        x1:x1, y1:y1, x2:x2, y2:y2, at:End::Start, disable_selection:param.disable_selection);
self.drawFixedText(context:param.context,
                                 text:CL::getConnectorText(from:con.represents, end_OOA_ID:startElem.OOA_ID,
                                                                    end_OOA_Type:startElem.OOA_Type, at:End::Start_Fixed),
                        x1:x1, y1:y1, x2:x2, y2:y2, at:End::Start_Fixed);
if (CL::getConnectorStyle(from:con.represents, at:End::Start) == Style::Triangle)
  angle = GR::getAngle(x1:x1, y1:y1, x2:x2, y2:y2);
  GR::drawTriangle(context:param.context,x:x1, y:y1, rotation:angle);
end if;
//
// Middle Text
//
middle_OOA_ID = OS::NULL_UNIQUE_ID();
segCount = cardinality segments;
segCount = segCount /2;
while (segCount > 1)
  select one seg related by seg->GD_LS[R7.''precedes''];
  segCount = segCount - 1;
end while;
//
// Correct for scroll position
//
x1 = seg.Start_X  - canvas.ScrollXPos;
y1 = seg.Start_Y - canvas.ScrollYPos;
x2 = seg.End_X  - canvas.ScrollXPos;
y2 = seg.End_Y - canvas.ScrollYPos;
self.drawText(context:param.context,
                        text:CL::getConnectorText(from:con.represents, end_OOA_ID:middle_OOA_ID, 
                                                                   end_OOA_Type:0, at:End::Middle),
                        x1:x1, y1:y1, x2:x2, y2:y2, at:End::Middle, disable_selection:param.disable_selection);
//
// End Text
//
select one endElem related by self->GD_GE[R4];
end_OOA_ID = OS::NULL_UNIQUE_ID();
end_OOA_Type = 0;
if (not_empty endElem)
  end_OOA_ID = endElem.OOA_ID;
  end_OOA_Type = endElem.OOA_Type;
end if;
select any seg related by self->GD_LS[R6];
segCount = cardinality segments;
while (segCount > 1)
  select one seg related by seg->GD_LS[R7.''precedes''];
  segCount = segCount - 1;
end while;
//
// Correct for scroll position
//
x1 = seg.Start_X  - canvas.ScrollXPos;
y1 = seg.Start_Y - canvas.ScrollYPos;
x2 = seg.End_X  - canvas.ScrollXPos;
y2 = seg.End_Y - canvas.ScrollYPos;
self.drawText(context:param.context,
                        text:CL::getConnectorText(from:con.represents, end_OOA_ID:end_OOA_ID,
                                                                   end_OOA_Type:end_OOA_Type, at:End::End),
                        x1:x1, y1:y1, x2:x2, y2:y2, at:End::End, disable_selection:param.disable_selection);
self.drawFixedText(context:param.context,
                                 text:CL::getConnectorText(from:con.represents, end_OOA_ID:end_OOA_ID,
                                                                    end_OOA_Type:end_OOA_Type, at:End::End_Fixed),
                        x1:x1, y1:y1, x2:x2, y2:y2, at:End::End_Fixed);
if (not_empty assocCon)
  if(CL::getConnectorStyle(from:assocCon.represents, at:End::Start) == Style::None)
    //
    // If this connector is an associator, draw the associator cardinality
    //
self.drawFixedText(context:param.context,
                                 text:CL::getConnectorText(from:assocCon.represents, end_OOA_ID:startElem.OOA_ID,
                                                                    end_OOA_Type:startElem.OOA_Type, at:End::End_Fixed),
                        x1:x1, y1:y1, x2:x2, y2:y2, at:End::End_Fixed);
  end if;
end if;
//
// Adornments
//
if (CL::getConnectorStyle(from:con.represents, at:End::Start) == Style::OpenArrow)
  angle = GR::getAngle(x1:x1, y1:y1, x2:x2, y2:y2);
  GR::drawOpenArrow(context:param.context,x:x1, y:y1, rotation:angle);
end if;
if (CL::getConnectorStyle(from:con.represents, at:End::End) == Style::OpenArrow)
  // Note start and end points are reversed because this adornment is on the end
  angle = GR::getAngle(x1:x2, y1:y2, x2:x1, y2:y1);
  GR::drawOpenArrow(context:param.context,x:x2, y:y2, rotation:angle);
end if;
if (CL::getConnectorStyle(from:con.represents, at:End::Start) == Style::FilledCircle)
  GR::drawFilledCircle(context:param.context,x:x1, y:y1, radius:8);
end if;
if (CL::getConnectorStyle(from:con.represents, at:End::End) == Style::FilledCircle)
  GR::drawFilledCircle(context:param.context,x:x2, y:y2, radius:8);
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3145814,
	3145766,
	'context',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (3145815,
	3145766,
	'disable_selection',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145767,
	3145732,
	'drawText',
	'Draws the floating text at each end and at the center point
of a connector. Floating text can be moved by the user. If
it has been moved, non-zero values are found in the associated
Connector Text (C_TXT) instance for the relevant text block.',
	524289,
	1,
	'if (param.text != "")
  select one canvas related by self->GD_GE[R2]->GD_MD[R1];
  select one con_text related by self-> GD_CTXT[R8];
  if (not_empty con_text)
    text_x = 0;
    text_y = 0;
    text_w = 0;
    text_h = 0;
    if (param.at == End::Start)
      text_x = con_text.Start_NW_X - canvas.ScrollXPos;
      text_y = con_text.Start_NW_Y - canvas.ScrollYPos;
      text_w = con_text.Start_SE_X - con_text.Start_NW_X;
      text_h = con_text.Start_SE_Y - con_text.Start_NW_Y;
    elif (param.at == End::End)
      text_x = con_text.End_NW_X - canvas.ScrollXPos;
      text_y = con_text.End_NW_Y - canvas.ScrollYPos;
      text_w = con_text.End_SE_X - con_text.End_NW_X;
      text_h = con_text.End_SE_Y - con_text.End_NW_Y;
    else
      // at == End::Middle
      text_x = con_text.Middle_NW_X - canvas.ScrollXPos;
      text_y = con_text.Middle_NW_Y - canvas.ScrollYPos;
      text_w = con_text.Middle_SE_X - con_text.Middle_NW_X;
      text_h = con_text.Middle_SE_Y - con_text.Middle_NW_Y;
    end if;
    if (text_w == 0 and text_h == 0)
      //
      // This label has default placement, initialize its position
      //
      text_w = GR::getDefaultTextWidth();
    end if;
    textToDraw = GR::wrapTextTo(context:param.context, input:param.text, width:text_w);
    if (text_x == - canvas.ScrollXPos AND text_y == - canvas.ScrollYPos)
      //
      // Text is at default position
      //
      angle = 0.0;
      if (param.at == End::Start)
        angle = GR::getAngle(x1:param.x1,y1:param.y1,x2:param.x2,y2:param.y2) ;
        text_x = param.x1 + con_text.Start_delta_X;
        text_y = param.y1 + con_text.Start_delta_Y;
      elif (param.at == End::End)
        angle = GR::getAngle(x1:param.x2,y1:param.y2,x2:param.x1,y2:param.y1) ;
        text_x = param.x2 + con_text.End_delta_X;
        text_y = param.y2 + con_text.End_delta_Y;
      else
        // at == End::Middle, use forward going angle and midway point of line
        angle = GR::getAngle(x1:param.x1,y1:param.y1,x2:param.x2,y2:param.y2) ;
        text_x = param.x1 + ((param.x2 - param.x1) / 2) + con_text.Middle_delta_X;
        text_y = param.y1 + ((param.y2 - param.y1) / 2) + con_text.Middle_delta_Y;
      end if;
      dir = GR::getDirection(angle:angle);
      if (param.at == End::Middle)
        if (dir == Direction::North OR dir == Direction::South)
          text_x = text_x - 10 - GR::getTextExtent(context:param.context,
                                                                            text:textToDraw, axis:Axis::X);
          text_y = text_y - (GR::getTextExtent(context:param.context, 
                                                                            text:textToDraw, axis:Axis::Y) / 2);
        else
          text_x = text_x - (GR::getTextExtent(context:param.context,
                                                                            text:textToDraw, axis:Axis::X) / 2);
          text_y = text_y - 10 - GR::getTextExtent(context:param.context, 
                                                                            text:textToDraw, axis:Axis::Y);
        end if;
      else // text block is at an end
        if (dir == Direction::North)
          text_x = text_x + 10;
          text_y = text_y - 10 - GR::getTextExtent(context:param.context, 
                                                                           text:textToDraw, axis:Axis::Y);
        elif (dir == Direction::West)
          text_x = text_x - 10 - GR::getTextExtent(context:param.context, 
                                                                           text:textToDraw, axis:Axis::X);
          text_y = text_y + 20;
        else // dir == Direction::South OR dir == Direction::East
          text_x = text_x + 10;
          text_y = text_y + 10;
        end if;
      end if;
      //
      // Initialize text width and height
      //
      text_w = GR::getTextExtent(context:param.context, text:textToDraw, axis:Axis::X);
      text_h = GR::getTextExtent(context:param.context, text:textToDraw, axis:Axis::Y);
      if (text_w < GR::getHotspotSize() * 3)
        text_w = GR::getHotspotSize() * 3;
      end if;
      if (text_h < GR::getHotspotSize() * 3)
        text_h = GR::getHotspotSize() * 3;
      end if;
      if (param.at == End::Start)
        con_text.Start_NW_X = text_x + canvas.ScrollXPos;
        con_text.Start_NW_Y = text_y + canvas.ScrollYPos;
        con_text.Start_SE_X = text_w + con_text.Start_NW_X;
        con_text.Start_SE_Y = text_h + con_text.Start_NW_Y;
      elif (param.at == End::End)
        con_text.End_NW_X = text_x + canvas.ScrollXPos;
        con_text.End_NW_Y = text_y + canvas.ScrollYPos;
        con_text.End_SE_X = text_w + con_text.End_NW_X;
        con_text.End_SE_Y = text_h + con_text.End_NW_Y;
      else
        // at == End::Middle
        con_text.Middle_NW_X = text_x + canvas.ScrollXPos;
        con_text.Middle_NW_Y = text_y + canvas.ScrollYPos;
        con_text.Middle_SE_X = text_w + con_text.Middle_NW_X;
        con_text.Middle_SE_Y = text_h + con_text.Middle_NW_Y;
      end if;
    end if;
    GR::drawText(context:param.context, 
                            text:textToDraw,
                            x:text_x,
                            y:text_y,
                            justified_to:Justification::Left, text_style:Style::None) ;
    //
    // If selected, draw the resize handles
    //
    select one elem related by self->GD_GE[R2];
    if (CL::isSelected(element:elem.represents) AND NOT param.disable_selection)
      GR::drawResizeHandles(Context:param.context, x:text_x, y:text_y, w:text_w, h:text_h);
    end if;
  end if;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145816,
	3145767,
	'context',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (3145817,
	3145767,
	'text',
	524293,
	0);
INSERT INTO O_TPARM
	VALUES (3145818,
	3145767,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145819,
	3145767,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145820,
	3145767,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145821,
	3145767,
	'y2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145822,
	3145767,
	'at',
	524311,
	0);
INSERT INTO O_TPARM
	VALUES (3145823,
	3145767,
	'disable_selection',
	524290,
	0);
INSERT INTO O_TFR
	VALUES (3145768,
	3145732,
	'drawFixedText',
	'Draws the fixed text at each end of the connector.
Fixed text stays at a fixed offset from the connectors
endpoint. It cannot be moved by the user.',
	524289,
	1,
	'if (param.text != "")
  angle = 0.0;
  text_x = 0;
  text_y = 0;
  if (param.at == End::Start_Fixed)
    text_x = param.x1;
    text_y = param.y1;
    angle = GR::getAngle(x1:param.x1,y1:param.y1,x2:param.x2,y2:param.y2) ;
  else // param.at == End::End_Fixed
    text_x = param.x2;
    text_y = param.y2;
    angle = GR::getAngle(x1:param.x2,y1:param.y2,x2:param.x1,y2:param.y1) ;
  end if;
  dir = GR::getDirection(angle:angle);
  if (dir == Direction::East)
    text_x = text_x + GR::getGraphicSpacing();
    text_y = text_y - GR::getGraphicSpacing() - GR::getTextExtent(context:param.context, 
                                                                      text:param.text, axis:Axis::Y);
  elif (dir == Direction::South)
    text_x = text_x - GR::getGraphicSpacing() - GR::getTextExtent(context:param.context, 
                                                                      text:param.text, axis:Axis::X);
    text_y = text_y + GR::getGraphicSpacing();
  else // dir == Direction::North OR dir == Direction::West
    text_x = text_x - GR::getGraphicSpacing()  - GR::getTextExtent(context:param.context, 
                                                                      text:param.text, axis:Axis::X);
    text_y = text_y - GR::getGraphicSpacing() - GR::getTextExtent(context:param.context, 
                                                                      text:param.text, axis:Axis::Y);
  end if;
  GR::drawText(context:param.context, 
                           text:param.text,
                           x:text_x,
                           y:text_y,
                           justified_to:Justification::Left, text_style:Style::None);
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145824,
	3145768,
	'context',
	524305,
	0);
INSERT INTO O_TPARM
	VALUES (3145825,
	3145768,
	'text',
	524293,
	0);
INSERT INTO O_TPARM
	VALUES (3145826,
	3145768,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145827,
	3145768,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145828,
	3145768,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145829,
	3145768,
	'y2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145830,
	3145768,
	'at',
	524311,
	0);
INSERT INTO O_TFR
	VALUES (3145769,
	3145732,
	'isOver',
	'',
	524290,
	1,
	'select one canvas related by self->GD_GE[R2]->GD_MD[R1];
select many segs related by self->GD_LS[R6];
scaled_x = GR::scale(value:param.x);
scaled_y = GR::scale(value:param.y);
for each seg in segs
  if (seg.isOver(x:scaled_x, y:scaled_y, scrollX:canvas.ScrollXPos, scrollY:canvas.ScrollYPos))
    return true;
  end if;
end for;
select one assocCon related by self->GD_GE[R4]->GD_CON[R2]->GD_GE[R2];
if (not_empty assocCon)
  select many segs related by assocCon->GD_CON[R2]->GD_LS[R6];
  for each seg in segs
    if (seg.isOver(x:scaled_x, y:scaled_y, scrollX:canvas.ScrollXPos, scrollY:canvas.ScrollYPos))
      return true;
    end if;
  end for;
end if;
select many end_cons related by self->GD_GE[R2]->GD_CON[R4];
for each con in end_cons
  if (con.isOver(x:param.x, y:param.y) == true)
    return true;
  end if;
end for;
select one con_text related by self->GD_CTXT[R8];
if (con_text.isOver(x:param.x, y:param.y, End:End::Start))
  return true;
elif (con_text.isOver(x:param.x, y:param.y, End:End::Middle))
  return true;
elif (con_text.isOver(x:param.x, y:param.y, End:End::End))
  return true;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145831,
	3145769,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145832,
	3145769,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145770,
	3145732,
	'moveStart',
	'',
	524289,
	1,
	'if (self.isEndSelected() or self.endsOnWS())
  //
  // Both ends are selected or the connector ends in whitespace.
  // Either way, move the whole thing
  //
  // Note that this also covers the reflexive connector case, since the
  // element being moved is selected and will be at both ends.
  //
  // Do the move only from the perspective of the start shape so that the
  // connector is only moved once (ie do nothing for this case in moveEnd).
  //
  select many segments related by self->GD_LS[R6];
  for each segment in segments
    select one nextSegment related by segment->GD_LS[R7.''precedes''];
    if (not_empty nextSegment)
      segment.move(xDelta:param.xDelta, yDelta:param.yDelta);
    else
      //
      // This is the last line segment
      //
      select one end_elem related by self->GD_GE[R4];
      if (empty end_elem)
        //
        // Connector ends on whitespace, move the whole segment
        //
        segment.move(xDelta:param.xDelta, yDelta:param.yDelta);
        select many end_cons related by self->GD_GE[R2]->GD_CON[R4];
        for each con in end_cons
          con.moveEnd(xDelta:param.xDelta, yDelta:param.yDelta);
        end for;
      else
        //
        // Connector ends on a shape, only move the start of the segment,
        // if the connected to shape is moving, it will command the other 
        // end to be moved.
        //
        segment.moveStart(xDelta:param.xDelta, yDelta:param.yDelta);
      end if;
    end if;
  end for;
else
  //
  // Only one end is selected, just move the first segment
  //
  select many segments related by self->GD_LS[R6];
  for each segment in segments
    if (segment.Next_GE_ID == 0)
      segment.moveStart(xDelta:param.xDelta, yDelta:param.yDelta);
    end if;
  end for;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3145833,
	3145770,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145834,
	3145770,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145771,
	3145732,
	'moveEnd',
	'',
	524289,
	1,
	'select many segments related by self->GD_LS[R6];
for each segment in segments
  select one nextSegment related by segment->GD_LS[R7.''precedes''];
  if (empty nextSegment)
    segment.moveEnd(xDelta:param.xDelta, yDelta:param.yDelta);
  end if;
end for;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145835,
	3145771,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145836,
	3145771,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145772,
	3145732,
	'isSelected',
	'',
	524290,
	1,
	'select one assocCon related by self->GD_GE[R4]->GD_CON[R2]->GD_GE[R2];
if (not_empty assocCon)
  return assocCon.isSelected();
end if;
select one gr_elem related by self->GD_GE[R2];
if (CL::isEmpty(element:gr_elem.represents))
 return false;
else
  return CL::isSelected(element:gr_elem.represents);
end if;',
	1);
INSERT INTO O_TFR
	VALUES (3145773,
	3145732,
	'isInside',
	'',
	524290,
	1,
	'select one canvas related by self->GD_GE[R2]->GD_MD[R1];
result = true;
select many segs related by self->GD_LS[R6];
for each seg in segs
  startX = seg.Start_X - canvas.ScrollXPos;
  startY = seg.Start_Y - canvas.ScrollYPos;
  endX = seg.End_X - canvas.ScrollXPos;
  endY = seg.End_Y - canvas.ScrollYPos;
  if (GR::scale(value:param.x) > startX or GR::scale(value:param.x + param.w) < startX)
    result = false;
    break;
  elif  (GR::scale(value:param.x) > endX or GR::scale(value:param.x + param.w) < endX)
    result = false;
    break;
  elif  (GR::scale(value:param.y) > startY or GR::scale(value:param.y + param.h) < startY)
    result = false;
    break;
  elif  (GR::scale(value:param.y) > endY or GR::scale(value:param.y + param.h) < endY)
    result = false;
    break;
  end if;
end for;
return result;',
	1);
INSERT INTO O_TPARM
	VALUES (3145837,
	3145773,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145838,
	3145773,
	'y',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145839,
	3145773,
	'w',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145840,
	3145773,
	'h',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145774,
	3145732,
	'isEndSelected',
	'',
	524290,
	1,
	'select one end_elem related by self->GD_GE[R4];
if (not_empty end_elem)
  return end_elem.isSelected();
else
  select many end_cons related by self->GD_GE[R2]->GD_CON[R4];
  for each con in end_cons
    if (con.isStartSelected())
      return true;
    end if;
  end for;
end if;
return false;',
	1);
INSERT INTO O_TFR
	VALUES (3145775,
	3145732,
	'isStartSelected',
	'',
	524290,
	1,
	'select one start_elem related by self->GD_GE[R3];
if (not_empty start_elem)
  return start_elem.isSelected();
else
  return false;
end if;
',
	1);
INSERT INTO O_TFR
	VALUES (3145776,
	3145732,
	'endsOnWS',
	'',
	524290,
	1,
	'select one end_elem related by self->GD_GE[R4];
if (not_empty end_elem)
  return false;
else
  select many end_cons related by self->GD_GE[R2]->GD_CON[R4];
  return empty end_cons;
end if;',
	1);
INSERT INTO O_TFR
	VALUES (3145777,
	3145732,
	'isOverVertex',
	'',
	524290,
	1,
	'//
// if the vertex type we are over is not none, then we must be over a vertex . . 
//
return self.getVertexUnder(x:param.x, y:param.y) != Vertex::None;',
	1);
INSERT INTO O_TPARM
	VALUES (3145841,
	3145777,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145842,
	3145777,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145778,
	3145732,
	'getEndUnder',
	'',
	524311,
	1,
	'select one model related by self->GD_GE[R2]->GD_MD[R1];
select one con_text related by self->GD_CTXT[R8];
if (not_empty con_text)
  scaled_x = GR::scale(value:param.x) + model.ScrollXPos;
  scaled_y = GR::scale(value:param.y) + model.ScrollYPos;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_NW_X, y2:con_text.Start_NW_Y))
    return End::Start;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_NW_X, y2:con_text.Middle_NW_Y))
    return End::Middle;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_NW_X, y2:con_text.End_NW_Y))
    return End::End;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_NW_X, y2:con_text.Start_SE_Y))
    return End::Start;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_NW_X, y2:con_text.Middle_SE_Y))
    return End::Middle;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_NW_X, y2:con_text.End_SE_Y))
    return End::End;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_SE_X, y2:con_text.Start_SE_Y))
    return End::Start;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_SE_X, y2:con_text.Middle_SE_Y))
    return End::Middle;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_SE_X, y2:con_text.End_SE_Y))
    return End::End;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_SE_X, y2:con_text.Start_NW_Y))
    return End::Start;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_SE_X, y2:con_text.Middle_NW_Y))
    return End::Middle;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_SE_X, y2:con_text.End_NW_Y))
    return End::End;
  end if;
end if;
return End::None;',
	1);
INSERT INTO O_TPARM
	VALUES (3145843,
	3145778,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145844,
	3145778,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145779,
	3145732,
	'getVertexUnder',
	'',
	524318,
	1,
	'select one model related by self->GD_GE[R2]->GD_MD[R1];
select one con_text related by self->GD_CTXT[R8];
if (not_empty con_text)
  scaled_x = GR::scale(value:param.x) + model.ScrollXPos;
  scaled_y = GR::scale(value:param.y) + model.ScrollYPos;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_NW_X, y2:con_text.Start_NW_Y))
  return Vertex::NW;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_NW_X, y2:con_text.Middle_NW_Y))
  return Vertex::NW;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_NW_X, y2:con_text.End_NW_Y))
  return Vertex::NW;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_NW_X, y2:con_text.Start_SE_Y))
  return Vertex::SW;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_NW_X, y2:con_text.Middle_SE_Y))
  return Vertex::SW;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_NW_X, y2:con_text.End_SE_Y))
  return Vertex::SW;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_SE_X, y2:con_text.Start_SE_Y))
  return Vertex::SE;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_SE_X, y2:con_text.Middle_SE_Y))
  return Vertex::SE;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_SE_X, y2:con_text.End_SE_Y))
  return Vertex::SE;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Start_SE_X, y2:con_text.Start_NW_Y))
  return Vertex::NE;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.Middle_SE_X, y2:con_text.Middle_NW_Y))
  return Vertex::NE;
  end if;
  if (GR::isWithinHotspot(x:scaled_x, y:scaled_y, x2:con_text.End_SE_X, y2:con_text.End_NW_Y))
  return Vertex::NE;
  end if;
end if;
return Vertex::None;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145845,
	3145779,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145846,
	3145779,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145780,
	3145732,
	'getStartX',
	'',
	524291,
	1,
	'select any segment related by self->GD_LS[R6] where (selected.Next_GE_ID == 0);
if (not_empty segment)
  return segment.Start_X;
else
  return 0;
end if;
',
	1);
INSERT INTO O_TFR
	VALUES (3145781,
	3145732,
	'getStartY',
	'',
	524291,
	1,
	'select any segment related by self->GD_LS[R6] where (selected.Next_GE_ID == 0);
if (not_empty segment)
  return segment.Start_Y;
else
  return 0;
end if;',
	1);
INSERT INTO O_TFR
	VALUES (3145782,
	3145732,
	'getEndX',
	'',
	524291,
	1,
	'x = 0;
select many segments related by self->GD_LS[R6];
for each segment in segments
  select one nextSegment related by segment->GD_LS[R7.''precedes''];
  if (empty nextSegment)
    x = segment.End_X;
    break;
  end if;
end for;
return x;
',
	1);
INSERT INTO O_TFR
	VALUES (3145783,
	3145732,
	'getEndY',
	'',
	524291,
	1,
	'y = 0;
select many segments related by self->GD_LS[R6];
for each segment in segments
  select one nextSegment related by segment->GD_LS[R7.''precedes''];
  if (empty nextSegment)
    y = segment.End_Y;
    break;
  end if;
end for;
return y;
',
	1);
INSERT INTO O_TFR
	VALUES (3145784,
	3145732,
	'moveVertex',
	'',
	524289,
	1,
	'select one con_text related by self->GD_CTXT[R8];
select one canvas related by self->GD_GE[R2]->GD_MD[R1];
nw_x = 0;
nw_y = 0;
se_x = 0;
se_y = 0;
if (param.End == End::Start)
  nw_x = con_text.Start_NW_X;
  nw_y = con_text.Start_NW_Y;
  se_x = con_text.Start_SE_X;
  se_y = con_text.Start_SE_Y;
elif (param.End == End::Middle)
  nw_x = con_text.Middle_NW_X;
  nw_y = con_text.Middle_NW_Y;
  se_x = con_text.Middle_SE_X;
  se_y = con_text.Middle_SE_Y;
elif (param.End == End::End)
  nw_x = con_text.End_NW_X;
  nw_y = con_text.End_NW_Y;
  se_x = con_text.End_SE_X;
  se_y = con_text.End_SE_Y;
end if;
if (param.Vertex == Vertex::NW)
  nw_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  nw_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if (param.Vertex == Vertex::SE)
  se_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  se_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if (param.Vertex == Vertex::NE)
  se_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  nw_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if (param.Vertex == Vertex::SW)
  nw_x = GR::scale(value:GR::getAbsoluteXPosition()) + canvas.ScrollXPos;
  se_y = GR::scale(value:GR::getAbsoluteYPosition()) + canvas.ScrollYPos;
end if;
if ((se_x - nw_x > GR::getHotspotSize() * 3) AND (se_y- nw_y > GR::getHotspotSize() * 3))
  if (param.End == End::Start)
    con_text.Start_NW_X = nw_x;
    con_text.Start_NW_Y = nw_y;
    con_text.Start_SE_X = se_x;
    con_text.Start_SE_Y = se_y;
  elif (param.End == End::Middle)
    con_text.Middle_NW_X = nw_x;
    con_text.Middle_NW_Y = nw_y;
    con_text.Middle_SE_X = se_x;
    con_text.Middle_SE_Y = se_y;
  elif (param.End == End::End)
    con_text.End_NW_X = nw_x;
    con_text.End_NW_Y = nw_y;
    con_text.End_SE_X = se_x;
    con_text.End_SE_Y = se_y;
  end if;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145847,
	3145784,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145848,
	3145784,
	'yDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145849,
	3145784,
	'Vertex',
	524318,
	0);
INSERT INTO O_TPARM
	VALUES (3145850,
	3145784,
	'End',
	524311,
	0);
INSERT INTO O_TFR
	VALUES (3145785,
	3145732,
	'getIntersect',
	'',
	524291,
	1,
	'XIntersect = 0;
YIntersect = 0;
intersect_found = false;
minX = param.x1;
maxX = param.x2;
if (minX > maxX)
  temp = minX; minX = maxX; maxX = temp;
end if;
minY = param.y1;
maxY = param.y2;
if (minY > maxY)
  temp = minY; minY = maxY; maxY = temp;
end if;
//
// Look for an intersection with all segments.
//
select many segs related by self->GD_LS[R6];
for each seg in segs
  segMinX = seg.Start_X;
  segMaxX = seg.End_X;
  if (segMinX > segMaxX)
    temp = segMinX; segMinX = segMaxX; segMaxX = temp;
  end if;
  segMinY = seg.Start_Y;
  segMaxY = seg.End_Y;
  if (segMinY > segMaxY)
    temp = segMinY; segMinY = segMaxY; segMaxY = temp;
  end if;
  XIntersect = GR::getXIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:seg.Start_X, y3:seg.Start_Y, x4:seg.End_X, y4:seg.End_Y);
  YIntersect = GR::getYIntersect(x1:param.x1, y1:param.y1, x2:param.x2, y2:param.y2,
                                                  x3:seg.Start_X, y3:seg.Start_Y, x4:seg.End_X, y4:seg.End_Y);
  if ((XIntersect >= minX and XIntersect <= maxX) and
                                                  (XIntersect >= segMinX and XIntersect <= segMaxX))
    if ((YIntersect >= minY and YIntersect <= maxY) and 
                                                    (YIntersect >= segMinY and YIntersect <= segMaxY))
      intersect_found = true;
      break;
    end if;
  end if;
end for;
if (intersect_found)
  if (param.axis == Axis::X)
    return XIntersect;
  else
    return YIntersect;
  end if;
else
  return 0;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3145851,
	3145785,
	'x1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145852,
	3145785,
	'y1',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145853,
	3145785,
	'x2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145854,
	3145785,
	'y2',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145855,
	3145785,
	'axis',
	524309,
	0);
INSERT INTO O_TFR
	VALUES (3145786,
	3145732,
	'getXDeltaForCropTo',
	'',
	524291,
	1,
	'moveX = 0;
if (param.delta != 0)
  htspSz = GR::getHotspotSize();
  if (param.end == End::Start)
    select one shp related by self->GD_GE[R3]->GD_SHP[R2];
    if (param.vertex == Vertex::NE or param.vertex == Vertex::SE)
      // Moving East side.
      if (self.getStartX() == shp.SE_X)
        // Connector is attached to the east side, move it by xDelta
        moveX = param.delta;
      end if;
      if (self.getStartY() == shp.NW_Y or self.getStartY() == shp.SE_Y)
        // Connector is attached to the top or bottom
        rightOrdLimit = shp.SE_X - htspSz;
        if (self.getStartX() > rightOrdLimit + param.delta)
          moveX = rightOrdLimit + param.delta - self.getStartX();
        end if;
      end if;
    elif (param.vertex == Vertex::NW or param.vertex == Vertex::SW)
      // Moving West side (We can''t move East and West simultaneously, hence elif)
      if (self.getStartX() == shp.NW_X)
        // Connector is attached to the west side, move it by xDelta
        moveX = param.delta;
      end if;
      if (self.getStartY() == shp.NW_Y or self.getStartY() == shp.SE_Y)
        // Connector is attached to the top or bottom
        leftOrdLimit = shp.NW_X + htspSz;
        if (self.getStartX() < leftOrdLimit + param.delta)
          moveX = leftOrdLimit + param.delta - self.getStartX();
        end if;
      end if;
    end if;
  else
    select one shp related by self->GD_GE[R4]->GD_SHP[R2];
    if (param.vertex == Vertex::NE or param.vertex == Vertex::SE)
      // Moving East side.
      if (self.getEndX() == shp.SE_X)
        // Connector is attached to the east side, move it by xDelta
        moveX = param.delta;
      end if;
      if (self.getEndY() == shp.NW_Y or self.getEndY() == shp.SE_Y)
        // Connector is attached to the top or bottom
        rightOrdLimit = shp.SE_X - htspSz;
        if (self.getEndX() > rightOrdLimit + param.delta)
          moveX = rightOrdLimit + param.delta - self.getEndX();
        end if;
      end if;
    elif (param.vertex == Vertex::NW or param.vertex == Vertex::SW)
      // Moving West side (We can''t move East and West simultaneously, hence elif)
      if (self.getEndX() == shp.NW_X)
        // Connector is attached to the west side, move it by xDelta
        moveX = param.delta;
      end if;
      if (self.getEndY() == shp.NW_Y or self.getEndY() == shp.SE_Y)
        // Connector is attached to the top or bottom
        leftOrdLimit = shp.NW_X + htspSz;
        if (self.getEndX() < leftOrdLimit + param.delta)
          moveX = leftOrdLimit + param.delta - self.getEndX();
        end if;
      end if;
    end if;
  end if;
end if;
return moveX;',
	1);
INSERT INTO O_TPARM
	VALUES (3145856,
	3145786,
	'delta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145857,
	3145786,
	'vertex',
	524318,
	0);
INSERT INTO O_TPARM
	VALUES (3145858,
	3145786,
	'end',
	524311,
	0);
INSERT INTO O_TFR
	VALUES (3145787,
	3145732,
	'getYDeltaForCropTo',
	'',
	524291,
	1,
	'moveY = 0;
if (param.delta != 0)
  htspSz = GR::getHotspotSize();
  if (param.end == End::Start)
    select one shp related by self->GD_GE[R3]->GD_SHP[R2];
    if (param.vertex == Vertex::NW or param.vertex == Vertex::NE)
      // Moving North side
      if (self.getStartY() == shp.NW_Y)
        // Connector is attached to the north side, move it by yDelta
        moveY = param.delta;
      end if;
      if (self.getStartX() == shp.NW_X or self.getStartX() == shp.SE_X)
        // Connector is attached to one of the sides
        topOrdLimit = shp.NW_Y + htspSz;
        if (self.getStartY() < topOrdLimit + param.delta)
          moveY = topOrdLimit + param.delta - self.getStartY();
        end if;
      end if;
    elif (param.vertex == Vertex::SW or param.vertex == Vertex::SE)
      // Moving South side (We can''t move North and South simultaneously, hence elif)
      if (self.getStartY() == shp.SE_Y)
        // Connector is attached to the south side, move it by yDelta
        moveY = param.delta;
      end if;
      if (self.getStartX() == shp.NW_X or self.getStartX() == shp.SE_X)
        // Connector is attached to one of the sides
        bottomOrdLimit = shp.SE_Y - htspSz;
        if (self.getStartY() > bottomOrdLimit + param.delta)
          moveY = bottomOrdLimit + param.delta - self.getStartY();
        end if;
      end if;
    end if;
  else
    select one shp related by self->GD_GE[R4]->GD_SHP[R2];
    if (param.vertex == Vertex::NW or param.vertex == Vertex::NE)
      // Moving North side
      if (self.getEndY() == shp.NW_Y)
        // Connector is attached to the north side, move it by yDelta
        moveY = param.delta;
      end if;
      if (self.getEndX() == shp.NW_X or self.getEndX() == shp.SE_X)
        // Connector is attached to one of the sides
        topOrdLimit = shp.NW_Y + htspSz;
        if (self.getEndY() < topOrdLimit + param.delta)
          moveY = topOrdLimit + param.delta - self.getEndY();
        end if;
      end if;
    elif (param.vertex == Vertex::SW or param.vertex == Vertex::SE)
      // Moving South side (We can''t move North and South simultaneously, hence elif)
      if (self.getEndY() == shp.SE_Y)
        // Connector is attached to the south side, move it by yDelta
        moveY = param.delta;
      end if;
      if (self.getEndX() == shp.NW_X or self.getEndX() == shp.SE_X)
        // Connector is attached to one of the sides
        bottomOrdLimit = shp.SE_Y - htspSz;
        if (self.getEndY() > bottomOrdLimit + param.delta)
          moveY = bottomOrdLimit + param.delta - self.getEndY();
        end if;
      end if;
    end if;
  end if;
end if;
return moveY;',
	1);
INSERT INTO O_TPARM
	VALUES (3145859,
	3145787,
	'delta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145860,
	3145787,
	'vertex',
	524318,
	0);
INSERT INTO O_TPARM
	VALUES (3145861,
	3145787,
	'end',
	524311,
	0);
INSERT INTO O_TFR
	VALUES (3145788,
	3145732,
	'isOverEnd',
	'',
	524290,
	1,
	'result = false;
select many segments related by self->GD_LS[R6];
// Find last segment, test against end co-odinates
for each segment in segments
  select one nextSegment related by segment->GD_LS[R7.''precedes''];
  if (empty nextSegment)
    result = segment.isOverEnd(x:param.x, y:param.y);
  end if;
end for;
return result;',
	1);
INSERT INTO O_TPARM
	VALUES (3145862,
	3145788,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145863,
	3145788,
	'y',
	524291,
	0);
INSERT INTO O_REF
	VALUES (3145732,
	3145730,
	0,
	3145749,
	3145730,
	3145733,
	3145731,
	3145759,
	3145730,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145759,
	3145732,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145759,
	3145732,
	0,
	'GE_ID',
	'This is an arbitrary identifier for the connector.',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145732,
	3145730,
	0,
	3145749,
	3145735,
	3145742,
	3145743,
	3145760,
	3145731,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145760,
	3145732,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145760,
	3145732,
	3145759,
	'Starting_GE_ID',
	'Description: This is the ID of the graphical element that the connector starts at.
',
	'Starting_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145732,
	3145730,
	0,
	3145749,
	3145736,
	3145744,
	3145745,
	3145761,
	3145732,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145761,
	3145732,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145761,
	3145732,
	3145760,
	'Ending_GE_ID',
	'Description: This is the ID of the graphical element that the connector ends at. The ID may be non-participating if the connector represents a creation transition, or the supertype part of a supertype/subtype relationship.
',
	'Ending_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145732,
	3145733,
	0,
	3145763,
	3145731,
	3145735,
	3145734,
	3145762,
	3145736,
	0,
	1,
	'The line segment referenced must belong to this connector.
In other words, this assertion must always be true:

Line_Segment[self.assoc_GE_ID].conn_GE_ID == self.GE_ID');
INSERT INTO O_RATTR
	VALUES (3145762,
	3145732,
	3145763,
	3145733,
	1);
INSERT INTO O_ATTR
	VALUES (3145762,
	3145732,
	3145761,
	'Assoc_GE_ID',
	'Description: When the connector represents an associative relationship, this is the ID of the line segment that the associative connector terminates at.',
	'Assoc_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145732);
INSERT INTO O_OIDA
	VALUES (3145759,
	3145732,
	0);
INSERT INTO O_RTIDA
	VALUES (3145759,
	3145732,
	0,
	3145732,
	3145736);
INSERT INTO O_RTIDA
	VALUES (3145759,
	3145732,
	0,
	3145734,
	3145740);
INSERT INTO O_OBJ
	VALUES (3145733,
	'Line Segment',
	5,
	'GD_LS',
	'The line segment is a single line drawn on the model. If the line segment is the first or last line segment, there may be adornments (cardinality, multiplicity) drawn near where the line segment attaches to the GE it starts/ends at.  Note: BridgePoint is very arbitrary about how it populates line segments in terms of begins and ends.  Line segments do not always run from start element to end element - sometimes they are reversed. ',
	3145734);
INSERT INTO O_TFR
	VALUES (3145789,
	3145733,
	'isSelected',
	'',
	524290,
	1,
	'// segment is selected if its connector is selected
select one seg_con related by self->GD_CON[R6];
return seg_con.isSelected();
',
	1);
INSERT INTO O_TFR
	VALUES (3145790,
	3145733,
	'isOverVertex',
	'',
	524290,
	1,
	'if (self.isOverStart(x:param.x, y:param.y))
  return true;
elif (self.isOverEnd(x:param.x, y:param.y))
  return true;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145864,
	3145790,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145865,
	3145790,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145791,
	3145733,
	'isOverStart',
	'',
	524290,
	1,
	'select one mdl related by self->GD_CON[R6]->GD_GE[R2]->GD_MD[R1];
return GR::isWithinHotspot(x:GR::scale(value:param.x) + mdl.ScrollXPos,
                                            y:GR::scale(value:param.y) + mdl.ScrollYPos,
                                            x2:self.Start_X, y2:self.Start_Y);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145866,
	3145791,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145867,
	3145791,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145792,
	3145733,
	'isOverEnd',
	'',
	524290,
	1,
	'select one mdl related by self->GD_CON[R6]->GD_GE[R2]->GD_MD[R1];
return GR::isWithinHotspot(x:GR::scale(value:param.x) + mdl.ScrollXPos,
                                            y:GR::scale(value:param.y) + mdl.ScrollYPos,
                                            x2:self.End_X, y2:self.End_Y);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145868,
	3145792,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145869,
	3145792,
	'y',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145793,
	3145733,
	'move',
	'',
	524289,
	1,
	'self.moveStart(xDelta:param.xDelta, yDelta:param.yDelta);
self.moveEnd(xDelta:param.xDelta, yDelta:param.yDelta);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145870,
	3145793,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145871,
	3145793,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145794,
	3145733,
	'moveStart',
	'',
	524289,
	1,
	'select one con related by self->GD_CON[R6];
if (not_empty con)
  select any con related by self->GD_CON[R5]->GD_GE[R2]->GD_CON[R4];
  if (not_empty con)
    // Move attached connectors by an interpolated amount
    select many segments related by con->GD_LS[R6];
    for each segment in segments
      select one flex related by segment->GD_SIF[R13];
      if (empty flex)
        //
        // If flex is not empty, the segment is already being flexed because it was
        //  picked up on a hotspot. We therefore do not want to move it again.
        //
        select one nextSegment related by segment->GD_LS[R7.''precedes''];
        if (empty nextSegment)
          x = GR::getInterpolatedX(x1:self.End_X, y1:self.End_Y, x2:self.Start_X, y2:self.Start_Y,
                                                  x3:segment.End_X, y3:segment.End_Y,
                                                  dX:param.xDelta, dY:param.yDelta);
          y = GR::getInterpolatedY(x1:self.End_X, y1:self.End_Y, x2:self.Start_X, y2:self.Start_Y,
                                                  x3:segment.End_X, y3:segment.End_Y,
                                                  dX:param.xDelta, dY:param.yDelta);
          con.moveEnd(xDelta:x - segment.End_X, yDelta:y - segment.End_Y);
        end if;
      end if;
    end for;
  end if;
end if;
if (self.Next_GE_ID == 0)
  //
  // This is the first segment of the connector
  //
  select one con_text related by self->GD_CON[R6]->GD_CTXT[R8];
  if (not_empty con_text)
    con_text.moveStart(xDelta:param.xDelta, yDelta:param.yDelta);
  end if;
end if;
if (self.isMiddleSegment())
  select one con_text related by self->GD_CON[R6]->GD_CTXT[R8];
  if (not_empty con_text)
    //
    // Move only half the distance because moveEnd will do the rest
    //
    con_text.moveMiddle(xDelta:param.xDelta / 2, yDelta:param.yDelta / 2);
  end if;
end if;
self.Start_X = self.Start_X + param.xDelta;
self.Start_Y = self.Start_Y + param.yDelta;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145872,
	3145794,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145873,
	3145794,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145795,
	3145733,
	'moveEnd',
	'',
	524289,
	1,
	'select one con related by self->GD_CON[R6];
if (not_empty con)
  select any con related by self->GD_CON[R5]->GD_GE[R2]->GD_CON[R4];
  if (not_empty con)
    // Move attached connectors by an interpolated amount
    select many segments related by con->GD_LS[R6];
    for each segment in segments
      select one flex related by segment->GD_SIF[R13];
      if (empty flex)
        //
        // If flex is not empty, the segment is already being flexed because it was
        //  picked up on a hotspot. We therefore do not want to move it again.
        //
        select one nextSegment related by segment->GD_LS[R7.''precedes''];
        if (empty nextSegment)
          x = GR::getInterpolatedX(x1:self.Start_X, y1:self.Start_Y, x2:self.End_X, y2:self.End_Y,
                                                  x3:segment.End_X, y3:segment.End_Y,
                                                  dX:param.xDelta, dY:param.yDelta);
          y = GR::getInterpolatedY(x1:self.Start_X, y1:self.Start_Y, x2:self.End_X, y2:self.End_Y,
                                                  x3:segment.End_X, y3:segment.End_Y,
                                                  dX:param.xDelta, dY:param.yDelta);
          con.moveEnd(xDelta:x - segment.End_X, yDelta:y - segment.End_Y);
        end if;
      end if;
    end for;
  end if;
end if;
select one nextSegment related by self->GD_LS[R7.''precedes''];
if (empty nextSegment)
  //
  // This is the last segment of the connector
  //
  select one con_text related by self->GD_CON[R6]->GD_CTXT[R8];
  if (not_empty con_text)
    con_text.moveEnd(xDelta:param.xDelta, yDelta:param.yDelta);
  end if;
end if;
if (self.isMiddleSegment())
  select one con_text related by self->GD_CON[R6]->GD_CTXT[R8];
  if (not_empty con_text)
    //
    // Move only half the distance because moveStart will do the rest
    //
    con_text.moveMiddle(xDelta:param.xDelta / 2, yDelta:param.yDelta / 2);
  end if;
end if;
self.End_X = self.End_X + param.xDelta;
self.End_Y = self.End_Y + param.yDelta;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145874,
	3145795,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145875,
	3145795,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145796,
	3145733,
	'isOver',
	'',
	524290,
	1,
	'return GR::isOver(x:param.x, y:param.y,
                              x1:self.Start_X - param.scrollX, y1:self.Start_Y - param.scrollY,
                              x2:self.End_X - param.scrollX, y2:self.End_Y - param.scrollY, 
                              tolerance:GR::getHotspotSize());
',
	1);
INSERT INTO O_TPARM
	VALUES (3145876,
	3145796,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145877,
	3145796,
	'y',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145878,
	3145796,
	'scrollX',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145879,
	3145796,
	'scrollY',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145797,
	3145733,
	'isMiddleSegment',
	'',
	524290,
	1,
	'select many segs related by self->GD_CON[R6]->GD_LS[R6];
//
// Find center segment
//
count = 0;
for each seg in segs
  if (seg.GE_ID == self.GE_ID)
    break;
  end if;
  count = count + 1;
end for;
total_segs = cardinality segs;
halfway = total_segs / 2;
return count == halfway;',
	1);
INSERT INTO O_NBATTR
	VALUES (3145763,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145763,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145763,
	3145733,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the line segment.
Full Name: Graphical Element Identifier',
	'',
	'GE_ID',
	0,
	524291);
INSERT INTO O_REF
	VALUES (3145733,
	3145732,
	0,
	3145759,
	3145732,
	3145737,
	3145736,
	3145764,
	3145737,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145764,
	3145733,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145764,
	3145733,
	3145763,
	'conn_GE_ID',
	'Description: This is the connector that the line segment is a part of.
Full Name: Connector Graphical Element Identifier
',
	'conn_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145765,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145765,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145765,
	3145733,
	3145764,
	'Start_X',
	'Description: This is the X position of the starting point for the line.
Full Name: Starting X Position
',
	'',
	'Start_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145766,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145766,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145766,
	3145733,
	3145765,
	'Start_Y',
	'Description: This is the Y position of the starting point for the line.
Full Name: Starting Y Position',
	'',
	'Start_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145767,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145767,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145767,
	3145733,
	3145766,
	'End_X',
	'Description: This is the X position of the ending point for the line.
Full Name: Ending X Position',
	'',
	'End_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145768,
	3145733);
INSERT INTO O_BATTR
	VALUES (3145768,
	3145733);
INSERT INTO O_ATTR
	VALUES (3145768,
	3145733,
	3145767,
	'End_Y',
	'Description: This is the Y position of the ending point for the line.
Full Name: Ending Y Position',
	'',
	'End_Y',
	0,
	524291);
INSERT INTO O_REF
	VALUES (3145733,
	3145733,
	0,
	3145763,
	3145733,
	3145738,
	3145739,
	3145769,
	3145738,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145769,
	3145733,
	3145763,
	3145733,
	1);
INSERT INTO O_ATTR
	VALUES (3145769,
	3145733,
	3145768,
	'Next_GE_ID',
	'Description: This is the identifier of the next line segment belonging to the connector.
Full Name: Next Graphical Element',
	'Next_',
	'GE_ID',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145733);
INSERT INTO O_OIDA
	VALUES (3145763,
	3145733,
	0);
INSERT INTO O_RTIDA
	VALUES (3145763,
	3145733,
	0,
	3145731,
	3145734);
INSERT INTO O_RTIDA
	VALUES (3145763,
	3145733,
	0,
	3145733,
	3145739);
INSERT INTO O_RTIDA
	VALUES (3145763,
	3145733,
	0,
	3145740,
	3145754);
INSERT INTO O_OBJ
	VALUES (3145734,
	'Connector Text',
	6,
	'GD_CTXT',
	'This class contains the positions for the text at the start, middle and end of the connector.  The positions have a upper left and lower right coordinate, which specifies the size of the box containing the text. The delta coordinate specifies how much to move the box from the default position.  Note: For non supertype/subtype relationships, Start and End are the two relationship phrases and Middle is the relationship number.  For supertype/subtype relationships, Start is the hard-coded phrase, End is the relationship number, and Middle is nothing.  Also note: sometimes BridgePoint reverses the NW and SE coordinates for the connector text when populating the graphical subsystem model.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145798,
	3145734,
	'move',
	'',
	524289,
	1,
	'self.moveStart(xDelta:param.xDelta, yDelta:param.yDelta);
self.moveMiddle(xDelta:param.xDelta, yDelta:param.yDelta);
self.moveEnd(xDelta:param.xDelta, yDelta:param.yDelta);
',
	1);
INSERT INTO O_TPARM
	VALUES (3145880,
	3145798,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145881,
	3145798,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145799,
	3145734,
	'moveStart',
	'',
	524289,
	1,
	'if (self.Start_NW_X != 0 and self.Start_NW_Y != 0)
  self.Start_NW_X = self.Start_NW_X + param.xDelta;
  self.Start_NW_Y = self.Start_NW_Y + param.yDelta;
  self.Start_SE_X = self.Start_SE_X + param.xDelta;
  self.Start_SE_Y = self.Start_SE_Y + param.yDelta;
end if;',
	1);
INSERT INTO O_TPARM
	VALUES (3145882,
	3145799,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145883,
	3145799,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145800,
	3145734,
	'moveEnd',
	'',
	524289,
	1,
	'if (self.End_NW_X != 0 and self.End_NW_Y != 0)
  self.End_NW_X = self.End_NW_X +param.xDelta;
  self.End_NW_Y = self.End_NW_Y + param.yDelta;
  self.End_SE_X = self.End_SE_X + param.xDelta;
  self.End_SE_Y = self.End_SE_Y + param.yDelta;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145884,
	3145800,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145885,
	3145800,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145801,
	3145734,
	'moveMiddle',
	'',
	524289,
	1,
	'if (self.Middle_NW_X != 0 and self.Middle_NW_Y != 0)
  self.Middle_NW_X = self.Middle_NW_X + param.xDelta;
  self.Middle_NW_Y = self.Middle_NW_Y + param.yDelta;
  self.Middle_SE_X = self.Middle_SE_X + param.xDelta;
  self.Middle_SE_Y = self.Middle_SE_Y + param.yDelta;
end if;
',
	1);
INSERT INTO O_TPARM
	VALUES (3145886,
	3145801,
	'xDelta',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145887,
	3145801,
	'yDelta',
	524291,
	0);
INSERT INTO O_TFR
	VALUES (3145802,
	3145734,
	'isOver',
	'',
	524290,
	1,
	'select one canvas related by self->GD_CON[R8]->GD_GE[R2]->GD_MD[R1];
x = 0;
y = 0;
w = 0;
h = 0;
if (param.End == End::Start)
  x = self.Start_NW_X  - canvas.ScrollXPos;
  y = self.Start_NW_Y - canvas.ScrollYPos;
  w = self.Start_SE_X - self.Start_NW_X;
  h = self.Start_SE_Y - self.Start_NW_Y;
elif (param.End == End::Middle)
  x = self.Middle_NW_X  - canvas.ScrollXPos;
  y = self.Middle_NW_Y - canvas.ScrollYPos;
  w = self.Middle_SE_X - self.Middle_NW_X;
  h = self.Middle_SE_Y - self.Middle_NW_Y;
elif (param.End == End::End)
  x = self.End_NW_X  - canvas.ScrollXPos;
  y = self.End_NW_Y - canvas.ScrollYPos;
  w = self.End_SE_X - self.End_NW_X;
  h = self.End_SE_Y - self.End_NW_Y;
end if;
if (GR::scale(value:param.x) > x and GR::scale(value:param.x) < x +w)
  if (GR::scale(value:param.y) > y and GR::scale(value:param.y) < y +h)
    return true;
  end if;
end if;
return false;',
	1);
INSERT INTO O_TPARM
	VALUES (3145888,
	3145802,
	'x',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145889,
	3145802,
	'y',
	524291,
	0);
INSERT INTO O_TPARM
	VALUES (3145890,
	3145802,
	'End',
	524311,
	0);
INSERT INTO O_REF
	VALUES (3145734,
	3145732,
	0,
	3145759,
	3145734,
	3145741,
	3145740,
	3145770,
	3145739,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145770,
	3145734,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145770,
	3145734,
	0,
	'GE_ID',
	'Description: This is an arbitrary identifier for the shape.
Full Name: Graphical Element ID',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145771,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145771,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145771,
	3145734,
	3145770,
	'Start_NW_X',
	'Description: This is the northwest x coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text northwest X coordinate.',
	'',
	'Start_NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145772,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145772,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145772,
	3145734,
	3145771,
	'Start_NW_Y',
	'Description: This is the northwest Y coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text northwest Y coordinate.',
	'',
	'Start_NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145773,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145773,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145773,
	3145734,
	3145772,
	'Start_SE_X',
	'Description: This is the southeast X coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text southeast X coordinate.',
	'',
	'Start_SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145774,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145774,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145774,
	3145734,
	3145773,
	'Start_SE_Y',
	'Description: This is the southeast Y coordinate of the starting connector text.  For regular relationships, this is the starting text phrase.  For supertype/subtype relationships, this is the hard-coded phrase disjoint, complete.
Full Name: Starting connector text southeast Y coordinate.',
	'',
	'Start_SE_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145775,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145775,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145775,
	3145734,
	3145774,
	'Start_delta_X',
	'Description: The X offset of the starting connector text from the default position.
Full Name: Start Connector Text Delta X Value',
	'',
	'Start_delta_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145776,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145776,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145776,
	3145734,
	3145775,
	'Start_delta_Y',
	'Description: The Y offset of the starting connector text from the default position.
Full Name: Start Connector Text Delta Y Value',
	'',
	'Start_delta_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145777,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145777,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145777,
	3145734,
	3145776,
	'Middle_NW_X',
	'Description: This is the northwest X coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text northwest X coordinate.',
	'',
	'Middle_NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145778,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145778,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145778,
	3145734,
	3145777,
	'Middle_NW_Y',
	'Description: This is the northwest Y coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text northwest Y coordinate.',
	'',
	'Middle_NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145779,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145779,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145779,
	3145734,
	3145778,
	'Middle_SE_X',
	'Description: This is the southeast X coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text southeast X coordinate.',
	'',
	'Middle_SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145780,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145780,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145780,
	3145734,
	3145779,
	'Middle_SE_Y',
	'Description: This is the southeast Y coordinate of the middle connector text.  For regular relationships, this is the relationship number.  For supertype/subtype relationships this has no value.
Full Name: Middle connector text southeast Y coordinate.',
	'',
	'Middle_SE_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145781,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145781,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145781,
	3145734,
	3145780,
	'Middle_delta_X',
	'Description: The X offset of the middle connector text from the default position.
Full Name: Middle Connector Text Delta X Value',
	'',
	'Middle_delta_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145782,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145782,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145782,
	3145734,
	3145781,
	'Middle_delta_Y',
	'Description: The Y offset of the middle connector text from the default position.
Full Name: Middle Connector Text Delta Y Value',
	'',
	'Middle_delta_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145783,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145783,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145783,
	3145734,
	3145782,
	'End_NW_X',
	'Description: This is the northwest X coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text northwest X coordinate.',
	'',
	'End_NW_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145784,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145784,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145784,
	3145734,
	3145783,
	'End_NW_Y',
	'Description: This is the northwest Y coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text northwest Y coordinate.',
	'',
	'End_NW_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145785,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145785,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145785,
	3145734,
	3145784,
	'End_SE_X',
	'Description: This is the southeast X coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text southeast X coordinate.',
	'',
	'End_SE_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145786,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145786,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145786,
	3145734,
	3145785,
	'End_SE_Y',
	'Description: This is the southeast Y coordinate of the end connector text.  For regular relationships, this is the end relationship phrase.  For supertype/subtype relationships this is the relationship number.
Full Name: End connector text southeast Y coordinate.',
	'',
	'End_SE_Y',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145787,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145787,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145787,
	3145734,
	3145786,
	'End_delta_X',
	'Description: The X offset of the ending connector text from the default position.
Full Name: End Connector Text Delta X Value',
	'',
	'End_delta_X',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145788,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145788,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145788,
	3145734,
	3145787,
	'End_delta_Y',
	'Description: The Y offset of the ending connector text from the default position.
Full Name: End Connector Text  Delta Y Value',
	'',
	'End_delta_Y',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145734);
INSERT INTO O_OIDA
	VALUES (3145770,
	3145734,
	0);
INSERT INTO O_RTIDA
	VALUES (3145770,
	3145734,
	0,
	3145742,
	3145760);
INSERT INTO O_OBJ
	VALUES (3145735,
	'Model Specification',
	7,
	'GD_MS',
	'A model specification instance defines a type of canvas.
We use the OOA_Type and the Model_Type as identifiers
because the OOA_Type defines the OOA of OOA element
type that the canvas type represents, but there can be 
more than one canvas type per OOA of OOA element type.

Instances of this class are instantiated at startup from a
pre-existing instance specification.
---------------------------------------------------------------------------

PEI:true',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145789,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145789,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145789,
	3145735,
	0,
	'Model_Type',
	'Description: An enumeration of the values that identify the type of a model.
Full Name: Graphical Model Type
Data Domain:  None= 0, DPD= 1, PDD(R)=2, PDD(A)=3, PDD(S)=4, CD=5, OCD(A)=6, OCD(S)=7, SC(Instance)=8, SC(Assigner)=10',
	'',
	'Model_Type',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145790,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145790,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145790,
	3145735,
	3145789,
	'Name',
	'',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145791,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145791,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145791,
	3145735,
	3145790,
	'background',
	'',
	'',
	'background',
	0,
	524313);
INSERT INTO O_NBATTR
	VALUES (3145792,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145792,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145792,
	3145735,
	3145791,
	'represents',
	'',
	'',
	'represents',
	0,
	524307);
INSERT INTO O_ID
	VALUES (0,
	3145735);
INSERT INTO O_OIDA
	VALUES (3145789,
	3145735,
	0);
INSERT INTO O_RTIDA
	VALUES (3145789,
	3145735,
	0,
	3145737,
	3145746);
INSERT INTO O_RTIDA
	VALUES (3145789,
	3145735,
	0,
	3145739,
	3145750);
INSERT INTO O_OBJ
	VALUES (3145736,
	'Element Specification',
	8,
	'GD_ES',
	'An element specification instance defines a type of symbol.
The OOA_Type defines the OOA of OOA element represented
by this symbol.

Instances of this class are instantiated at startup from a
pre-existing instance specification.
---------------------------------------------------------------------------

PEI:true',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145793,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145793,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145793,
	3145736,
	0,
	'OOA_Type',
	'Description: This is the type of the OOA model element that OOA_ID identifies.
Full Name: OOA Type
Data Domain: Subsystem=11, EE=12, Class=21, Imported Class=23, Relationship=24, Communication Path=28, Access Path=29, State=41, Transition=42, Derived Communication Path=47, Derived Access Path=48
',
	'',
	'OOA_Type',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145794,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145794,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145794,
	3145736,
	3145793,
	'Name',
	'',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145795,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145795,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145795,
	3145736,
	3145794,
	'isConnector',
	'',
	'',
	'isConnector',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145796,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145796,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145796,
	3145736,
	3145795,
	'internal',
	'',
	'',
	'internal',
	0,
	524313);
INSERT INTO O_NBATTR
	VALUES (3145797,
	3145736);
INSERT INTO O_BATTR
	VALUES (3145797,
	3145736);
INSERT INTO O_ATTR
	VALUES (3145797,
	3145736,
	3145796,
	'represents',
	'',
	'',
	'represents',
	0,
	524307);
INSERT INTO O_ID
	VALUES (0,
	3145736);
INSERT INTO O_OIDA
	VALUES (3145793,
	3145736,
	0);
INSERT INTO O_RTIDA
	VALUES (3145793,
	3145736,
	0,
	3145738,
	3145748);
INSERT INTO O_RTIDA
	VALUES (3145793,
	3145736,
	0,
	3145739,
	3145751);
INSERT INTO O_OBJ
	VALUES (3145737,
	'Element in Model Specification',
	9,
	'GD_EMS',
	'An instance of this associative class exists for each time a symbol
type (represented by an Element_Specification) appears on a
canvas type (represented by a Model_Specification).

Instances of this class are instantiated at startup from a
pre-existing instance specification.
---------------------------------------------------------------------------------

PEI:true',
	3145734);
INSERT INTO O_REF
	VALUES (3145737,
	3145736,
	0,
	3145793,
	3145739,
	3145752,
	3145751,
	3145798,
	3145740,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145798,
	3145737,
	3145793,
	3145736,
	1);
INSERT INTO O_ATTR
	VALUES (3145798,
	3145737,
	0,
	'OOA_Type',
	'',
	'',
	'OOA_Type',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	3145735,
	0,
	3145789,
	3145739,
	3145752,
	3145750,
	3145799,
	3145741,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145799,
	3145737,
	3145789,
	3145735,
	1);
INSERT INTO O_ATTR
	VALUES (3145799,
	3145737,
	3145798,
	'Model_Type',
	'',
	'',
	'Model_Type',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145737);
INSERT INTO O_OIDA
	VALUES (3145799,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145798,
	3145737,
	0);
INSERT INTO O_OBJ
	VALUES (3145738,
	'Element In Resize',
	10,
	'GD_EIR',
	'One instance of this class exists during the time a graphical element
is being resized. Both Shapes and connectors can be resized. In the
case of a connector, resizing can occur on any of the text blocks
associated with it.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145803,
	3145738,
	'initialize',
	'',
	524289,
	1,
	'select one symbol related by self->GD_GE[R12];
self.Vertex = symbol.getVertexUnder(x:GR::getAbsoluteXPosition(),
                                                                  y:GR::getAbsoluteYPosition());
self.End = symbol.getEndUnder(x:GR::getAbsoluteXPosition(),
                                                        y:GR::getAbsoluteYPosition());
',
	1);
INSERT INTO O_TFR
	VALUES (3145804,
	3145738,
	'finish',
	'',
	524289,
	1,
	'select one con related by self->GD_GE[R12]->GD_CON[R2];
if (not_empty con)
  //
  // If we were resizing a connector text box, we Need to
  // resize it to reflect the size of the text as it is laid out.
  //
  w = 0;
  select one con_elem related by con->GD_GE[R2];
  elemOOA_ID = con_elem.OOA_ID;
  elemOOA_Type = 0;
  select one con_text related by con->GD_CTXT[R8];
  if (self.End == End::Start)
    w = con_text.Start_SE_X - con_text.Start_NW_X;
    select one start_elem related by con->GD_GE[R3];
    if (not_empty start_elem)
      elemOOA_ID = start_elem.OOA_ID;
      elemOOA_Type = start_elem.OOA_Type;
    end if;
  elif (self.End == End::Middle)
    w = con_text.Middle_SE_X - con_text.Middle_NW_X;
  elif (self.End == End::End)
    w = con_text.End_SE_X - con_text.End_NW_X;
    select one end_elem related by con->GD_GE[R4];
    if (not_empty end_elem)
      elemOOA_ID = end_elem.OOA_ID;
      elemOOA_Type = end_elem.OOA_Type;
    end if;
  end if;
  //
  // Get the text . . . .
  //
  text = CL::getConnectorText(from:con_elem.represents, end_OOA_ID:elemOOA_ID,
                                                                   end_OOA_Type:elemOOA_Type, at:self.End);
  //
  // Size it to the users wishes
  //
  context = GR::getContext();
  drawnText = GR::wrapTextTo(context:context, input:text, width:w);
  //
  // Obtain the new extent of the text
  //
  w = GR::getTextExtent(context:context, text:drawnText, axis:Axis::X);
  h = GR::getTextExtent(context:context, text:drawnText, axis:Axis::Y);
  GR::disposeContext(context:context);
  if (w < GR::getHotspotSize() * 3)
    w = GR::getHotspotSize() * 3;
  end if;
  if (h < GR::getHotspotSize() * 3)
    h = GR::getHotspotSize() * 3;
  end if;
  //
  // Finally, set the text size to the new extent.
  //
  if (self.End == End::Start)
    con_text.Start_SE_X = con_text.Start_NW_X + w ;
    con_text.Start_SE_Y = con_text.Start_NW_Y + h ;
  elif (self.End == End::Middle)
    con_text.Middle_SE_X = con_text.Middle_NW_X + w ;
    con_text.Middle_SE_Y = con_text.Middle_NW_Y + h ;
  elif (self.End == End::End)
    con_text.End_SE_X = con_text.End_NW_X + w ;
    con_text.End_SE_Y = con_text.End_NW_Y + h ;
  end if;
end if;
',
	1);
INSERT INTO O_REF
	VALUES (3145738,
	3145730,
	0,
	3145749,
	3145741,
	3145758,
	3145757,
	3145800,
	3145742,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145800,
	3145738,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145800,
	3145738,
	0,
	'GE_ID',
	'',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145738,
	3145729,
	0,
	3145729,
	3145741,
	3145758,
	3145756,
	3145801,
	3145743,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145801,
	3145738,
	3145729,
	3145729,
	1);
INSERT INTO O_ATTR
	VALUES (3145801,
	3145738,
	3145800,
	'Model_ID',
	'',
	'',
	'Model_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145802,
	3145738);
INSERT INTO O_BATTR
	VALUES (3145802,
	3145738);
INSERT INTO O_ATTR
	VALUES (3145802,
	3145738,
	3145801,
	'Vertex',
	'This attribute captures which of the four possible corners of
an element is being moved by the user; North East, North West,
South East or South West.',
	'',
	'Vertex',
	0,
	524318);
INSERT INTO O_NBATTR
	VALUES (3145803,
	3145738);
INSERT INTO O_BATTR
	VALUES (3145803,
	3145738);
INSERT INTO O_ATTR
	VALUES (3145803,
	3145738,
	3145802,
	'End',
	'This attribute captures which of the text blocks of a connector is
being resized; Start, Middle or End. In the case where a Shape is
being resized, this attribute value is "None".',
	'',
	'End',
	0,
	524311);
INSERT INTO O_ID
	VALUES (0,
	3145738);
INSERT INTO O_OIDA
	VALUES (3145801,
	3145738,
	0);
INSERT INTO O_OIDA
	VALUES (3145800,
	3145738,
	0);
INSERT INTO O_OBJ
	VALUES (3145739,
	'Segment In Flex',
	11,
	'GD_SIF',
	'One instance of this class exists for each segment being flexed on the
diagram. Flexing means that a waypoint is being moved. Because instances
of this class are created when a flex begins (when the user holds down
the mouse over a waypoint) and are destroyed at the end of flexing (when
the user releases the mouse during a flex), their existence is temporary. For
the same reason, they are not persistent.',
	3145734);
INSERT INTO O_TFR
	VALUES (3145805,
	3145739,
	'initialize',
	'',
	524289,
	1,
	'//
// Cache the end being moved in case the user puts
// the segment down in an inappropriate place.
//
select one seg related by self->GD_LS[R13];
if (seg.isOverStart(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition()))
  self.End = End::Start;
  self.XOrigin = seg.Start_X;
  self.YOrigin = seg.Start_Y;
end if;
if (seg.isOverEnd(x:GR::getAbsoluteXPosition(), y:GR::getAbsoluteYPosition()))
  self.End = End::End;
  self.XOrigin = seg.End_X;
  self.YOrigin = seg.End_Y;
end if;
',
	1);
INSERT INTO O_TFR
	VALUES (3145806,
	3145739,
	'finish',
	'',
	524289,
	1,
	'//
// The start and end of connectors require special processing after a resize
//
select one segment related by self->GD_LS[R13];
if (segment.Next_GE_ID == 0 and self.End == End::Start)
  //
  // Handle case where the beginning of the line is being moved.
  //
  appropriate_start_point = false;
  select one start_elem related by segment->GD_CON[R6]->GD_GE[R3];
  if (not_empty start_elem)
    //
    // The position must be somewhere inside the
    // shape at which this connector starts.
    //
    select one shp related by start_elem->GD_SHP[R2];
    if (not_empty shp)
      select one model related by start_elem->GD_MD[R1];
      if (start_elem.isOver(x:GR::unScale(value:segment.Start_X - model.ScrollXPos),
                                        y:GR::unScale(value:segment.Start_Y - model.ScrollYPos)))
        intersectX = start_elem.getXIntersect(x1:segment.Start_X, y1:segment.Start_Y,
                                                                    x2:segment.End_X, y2:segment.End_Y);
        intersectY = start_elem.getYIntersect(x1:segment.Start_X, y1:segment.Start_Y,
                                                                     x2:segment.End_X, y2:segment.End_Y);
        if (intersectX != 0 and intersectY != 0)
          appropriate_start_point = true;
          segment.moveStart(xDelta:intersectX - segment.Start_X, yDelta:intersectY - segment.Start_Y);
        end if;
      end if;
    end if;
    if (not appropriate_start_point)
      //
      // Snap back to original position.
      //
      segment.moveStart(xDelta:self.XOrigin - segment.Start_X, yDelta:self.YOrigin - segment.Start_Y);
    end if;
  end if;
elif  (self.End == End::End)
  //
  // Handle case where the end of the line is being moved.
  //
  select one nextSegment related by segment->GD_LS[R7.''precedes''];
  if (empty nextSegment)
    appropriate_end_point = false;
    select one end_elem related by segment->GD_CON[R6]->GD_GE[R4];
    if (not_empty end_elem)
      //
      // The position must be somewhere inside the
      // shape at which this connector ends or in the
      // case of ending on a connector, the segment
      // being moved must intersect the target line
      // somewhere.
      //
      select one shp related by end_elem->GD_SHP[R2];
      select one model related by end_elem->GD_MD[R1];
      if (not_empty shp)
        if (end_elem.isOver(x:GR::unScale(value:segment.End_X - model.ScrollXPos),
                                        y:GR::unScale(value:segment.End_Y - model.ScrollYPos)))
          intersectX = end_elem.getXIntersect(x1:segment.Start_X, y1:segment.Start_Y,
                                                                      x2:segment.End_X, y2:segment.End_Y);
          intersectY = end_elem.getYIntersect(x1:segment.Start_X, y1:segment.Start_Y,
                                                                      x2:segment.End_X, y2:segment.End_Y);
          if (intersectX != 0 and intersectY != 0)
            appropriate_end_point = true;
            segment.moveEnd(xDelta:intersectX - segment.End_X, yDelta:intersectY - segment.End_Y);
          end if;
        end if;
      end if;
      select one con related by end_elem->GD_CON[R2];
      if (not_empty con)
        intersectX = end_elem.getXIntersect(x1:segment.Start_X, y1:segment.Start_Y,
                                                                    x2:segment.End_X, y2:segment.End_Y);
        intersectY = end_elem.getYIntersect(x1:segment.Start_X, y1:segment.Start_Y,
                                                                    x2:segment.End_X, y2:segment.End_Y);
        if (intersectX != 0 and intersectY != 0)
          appropriate_end_point = true;
          segment.moveEnd(xDelta:intersectX - segment.End_X, yDelta:intersectY - segment.End_Y);
        else
          //
          // There is no intersection, check for end overlap
          //
          if (con.isOverEnd(x:GR::unScale(value:segment.End_X - model.ScrollXPos),
                                        y:GR::unScale(value:segment.End_Y - model.ScrollYPos)))
            appropriate_end_point = true;
            segment.moveEnd(xDelta:con.getEndX() - segment.End_X, yDelta:con.getEndY() - segment.End_Y);
          end if;
        end if;
      end if;
    else
      //
      // No end shape attached, anywhere should be OK
      //
      appropriate_end_point = true;
    end if;
    if (not appropriate_end_point)
      //
      // Snap back to original position.
      //
      segment.moveEnd(xDelta:self.XOrigin - segment.End_X, yDelta:self.YOrigin - segment.End_Y);
    end if;
  end if;
end if;
',
	1);
INSERT INTO O_REF
	VALUES (3145739,
	3145733,
	0,
	3145763,
	3145740,
	3145755,
	3145754,
	3145804,
	3145744,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145804,
	3145739,
	3145763,
	3145733,
	1);
INSERT INTO O_ATTR
	VALUES (3145804,
	3145739,
	0,
	'GE_ID',
	'',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145739,
	3145729,
	0,
	3145729,
	3145740,
	3145755,
	3145753,
	3145805,
	3145745,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145805,
	3145739,
	3145729,
	3145729,
	1);
INSERT INTO O_ATTR
	VALUES (3145805,
	3145739,
	3145804,
	'Model_ID',
	'',
	'',
	'Model_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145806,
	3145739);
INSERT INTO O_BATTR
	VALUES (3145806,
	3145739);
INSERT INTO O_ATTR
	VALUES (3145806,
	3145739,
	3145805,
	'End',
	'This attribute caches which end of the segment is being flexed.',
	'',
	'End',
	0,
	524311);
INSERT INTO O_NBATTR
	VALUES (3145807,
	3145739);
INSERT INTO O_BATTR
	VALUES (3145807,
	3145739);
INSERT INTO O_ATTR
	VALUES (3145807,
	3145739,
	3145806,
	'XOrigin',
	'This attribute caches the x position value at the moment
flexing begins. The value is used to snap the segment back
to its original position if the user releases the mouse when
the segment start or end is not over an appropriate start or
end point (depending on which end is being moved).',
	'',
	'XOrigin',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145808,
	3145739);
INSERT INTO O_BATTR
	VALUES (3145808,
	3145739);
INSERT INTO O_ATTR
	VALUES (3145808,
	3145739,
	3145807,
	'YOrigin',
	'This attribute caches the y position value at the moment
flexing begins. For more details about use of this attribute,
see the description for Segment_In_Flex.XOrigin.',
	'',
	'YOrigin',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145739);
INSERT INTO O_OIDA
	VALUES (3145804,
	3145739,
	0);
INSERT INTO O_OIDA
	VALUES (3145805,
	3145739,
	0);
INSERT INTO O_OBJ
	VALUES (3145740,
	'Text In Move',
	12,
	'GD_TIM',
	'One instance of this class exists when a text block is being 
moved. As documented by the 1:1 by conditional relationship,
only one text block can be moved at a time. Text blocks cannot
be selected individually, they form part of the connector to
which they are attached. It therefore follows they cannot be
grouped and moved together.',
	3145734);
INSERT INTO O_REF
	VALUES (3145740,
	3145734,
	0,
	3145770,
	3145742,
	3145761,
	3145760,
	3145809,
	3145746,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145809,
	3145740,
	3145749,
	3145730,
	1);
INSERT INTO O_ATTR
	VALUES (3145809,
	3145740,
	0,
	'GE_ID',
	'',
	'',
	'GE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145740,
	3145729,
	0,
	3145729,
	3145742,
	3145761,
	3145759,
	3145810,
	3145747,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145810,
	3145740,
	3145729,
	3145729,
	1);
INSERT INTO O_ATTR
	VALUES (3145810,
	3145740,
	3145809,
	'Model_ID',
	'',
	'',
	'Model_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145811,
	3145740);
INSERT INTO O_BATTR
	VALUES (3145811,
	3145740);
INSERT INTO O_ATTR
	VALUES (3145811,
	3145740,
	3145810,
	'End',
	'This attribute captures which text block is being moved; Start,
Middle or End.',
	'',
	'End',
	0,
	524311);
INSERT INTO O_ID
	VALUES (0,
	3145740);
INSERT INTO O_OIDA
	VALUES (3145809,
	3145740,
	0);
INSERT INTO O_OIDA
	VALUES (3145810,
	3145740,
	0);
INSERT INTO R_SIMP
	VALUES (3145729);
INSERT INTO R_REL
	VALUES (3145729,
	1,
	'A model contains zero or more (it may be empty) graphical elements.',
	3145734);
INSERT INTO R_PART
	VALUES (3145729,
	3145729,
	3145729,
	0,
	0,
	'is part of');
INSERT INTO R_RTO
	VALUES (3145729,
	3145729,
	3145729,
	0);
INSERT INTO R_OIR
	VALUES (3145729,
	3145729,
	3145729,
	0);
INSERT INTO R_FORM
	VALUES (3145730,
	3145729,
	3145730,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (3145730,
	3145729,
	3145730);
INSERT INTO R_OIR
	VALUES (3145730,
	3145729,
	3145730,
	0);
INSERT INTO R_SUBSUP
	VALUES (3145730);
INSERT INTO R_REL
	VALUES (3145730,
	2,
	'A graphical element can be a shape, connector, or line segment. A connector has no graphical representation, it acts as a collection of line segments that make up the connector.',
	3145734);
INSERT INTO R_SUPER
	VALUES (3145730,
	3145730,
	3145731);
INSERT INTO R_RTO
	VALUES (3145730,
	3145730,
	3145731,
	0);
INSERT INTO R_OIR
	VALUES (3145730,
	3145730,
	3145731,
	0);
INSERT INTO R_SUB
	VALUES (3145731,
	3145730,
	3145732);
INSERT INTO R_RGO
	VALUES (3145731,
	3145730,
	3145732);
INSERT INTO R_OIR
	VALUES (3145731,
	3145730,
	3145732,
	0);
INSERT INTO R_SUB
	VALUES (3145732,
	3145730,
	3145733);
INSERT INTO R_RGO
	VALUES (3145732,
	3145730,
	3145733);
INSERT INTO R_OIR
	VALUES (3145732,
	3145730,
	3145733,
	0);
INSERT INTO R_SIMP
	VALUES (3145731);
INSERT INTO R_REL
	VALUES (3145731,
	5,
	'When a connector has another connector ending on it, this association captures which line segment in the connector the other connector''s line ends at. The associative connector is not the one at the ''is the destination of connector ending at'' end of this association but is found by navigating from that connector to its supertype and across R4.''is ending point for''.',
	3145734);
INSERT INTO R_PART
	VALUES (3145733,
	3145731,
	3145734,
	0,
	1,
	'has connector which ends at');
INSERT INTO R_RTO
	VALUES (3145733,
	3145731,
	3145734,
	0);
INSERT INTO R_OIR
	VALUES (3145733,
	3145731,
	3145734,
	0);
INSERT INTO R_FORM
	VALUES (3145732,
	3145731,
	3145735,
	0,
	1,
	'is the destination of connector ending at');
INSERT INTO R_RGO
	VALUES (3145732,
	3145731,
	3145735);
INSERT INTO R_OIR
	VALUES (3145732,
	3145731,
	3145735,
	0);
INSERT INTO R_SIMP
	VALUES (3145732);
INSERT INTO R_REL
	VALUES (3145732,
	6,
	'A connector has one or more line segments.',
	3145734);
INSERT INTO R_PART
	VALUES (3145732,
	3145732,
	3145736,
	0,
	0,
	'is part of');
INSERT INTO R_RTO
	VALUES (3145732,
	3145732,
	3145736,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	3145732,
	3145736,
	0);
INSERT INTO R_FORM
	VALUES (3145733,
	3145732,
	3145737,
	1,
	0,
	'has');
INSERT INTO R_RGO
	VALUES (3145733,
	3145732,
	3145737);
INSERT INTO R_OIR
	VALUES (3145733,
	3145732,
	3145737,
	0);
INSERT INTO R_SIMP
	VALUES (3145733);
INSERT INTO R_REL
	VALUES (3145733,
	7,
	'Line segments are ordered from first to last for each connector.',
	3145734);
INSERT INTO R_FORM
	VALUES (3145733,
	3145733,
	3145738,
	0,
	1,
	'precedes');
INSERT INTO R_RGO
	VALUES (3145733,
	3145733,
	3145738);
INSERT INTO R_OIR
	VALUES (3145733,
	3145733,
	3145738,
	0);
INSERT INTO R_PART
	VALUES (3145733,
	3145733,
	3145739,
	0,
	1,
	'follows');
INSERT INTO R_RTO
	VALUES (3145733,
	3145733,
	3145739,
	0);
INSERT INTO R_OIR
	VALUES (3145733,
	3145733,
	3145739,
	0);
INSERT INTO R_SIMP
	VALUES (3145734);
INSERT INTO R_REL
	VALUES (3145734,
	8,
	'Each connector has text associated with it.  The text is placed at the beginning, middle and end of the connector. ',
	3145734);
INSERT INTO R_PART
	VALUES (3145732,
	3145734,
	3145740,
	0,
	0,
	'is text for');
INSERT INTO R_RTO
	VALUES (3145732,
	3145734,
	3145740,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	3145734,
	3145740,
	0);
INSERT INTO R_FORM
	VALUES (3145734,
	3145734,
	3145741,
	0,
	0,
	'has text');
INSERT INTO R_RGO
	VALUES (3145734,
	3145734,
	3145741);
INSERT INTO R_OIR
	VALUES (3145734,
	3145734,
	3145741,
	0);
INSERT INTO R_SIMP
	VALUES (3145735);
INSERT INTO R_REL
	VALUES (3145735,
	3,
	'The graphical elements can have zero or more connectors using them for a starting point.',
	3145734);
INSERT INTO R_FORM
	VALUES (3145732,
	3145735,
	3145742,
	1,
	1,
	'is starting point for');
INSERT INTO R_RGO
	VALUES (3145732,
	3145735,
	3145742);
INSERT INTO R_OIR
	VALUES (3145732,
	3145735,
	3145742,
	0);
INSERT INTO R_PART
	VALUES (3145730,
	3145735,
	3145743,
	0,
	0,
	'starts at');
INSERT INTO R_RTO
	VALUES (3145730,
	3145735,
	3145743,
	0);
INSERT INTO R_OIR
	VALUES (3145730,
	3145735,
	3145743,
	0);
INSERT INTO R_SIMP
	VALUES (3145736);
INSERT INTO R_REL
	VALUES (3145736,
	4,
	'A graphical element can be the ending point for zero or more connectors. A connector will not have an ending point shape when it is a creation transition, or the supertype line for a supertype/subtype relationship.',
	3145734);
INSERT INTO R_FORM
	VALUES (3145732,
	3145736,
	3145744,
	1,
	1,
	'is ending point for');
INSERT INTO R_RGO
	VALUES (3145732,
	3145736,
	3145744);
INSERT INTO R_OIR
	VALUES (3145732,
	3145736,
	3145744,
	0);
INSERT INTO R_PART
	VALUES (3145730,
	3145736,
	3145745,
	0,
	1,
	'ends at');
INSERT INTO R_RTO
	VALUES (3145730,
	3145736,
	3145745,
	0);
INSERT INTO R_OIR
	VALUES (3145730,
	3145736,
	3145745,
	0);
INSERT INTO R_SIMP
	VALUES (3145737);
INSERT INTO R_REL
	VALUES (3145737,
	9,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145735,
	3145737,
	3145746,
	0,
	0,
	'is instance of');
INSERT INTO R_RTO
	VALUES (3145735,
	3145737,
	3145746,
	0);
INSERT INTO R_OIR
	VALUES (3145735,
	3145737,
	3145746,
	0);
INSERT INTO R_FORM
	VALUES (3145729,
	3145737,
	3145747,
	1,
	1,
	'has instance');
INSERT INTO R_RGO
	VALUES (3145729,
	3145737,
	3145747);
INSERT INTO R_OIR
	VALUES (3145729,
	3145737,
	3145747,
	0);
INSERT INTO R_SIMP
	VALUES (3145738);
INSERT INTO R_REL
	VALUES (3145738,
	10,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145736,
	3145738,
	3145748,
	0,
	0,
	'is instance of');
INSERT INTO R_RTO
	VALUES (3145736,
	3145738,
	3145748,
	0);
INSERT INTO R_OIR
	VALUES (3145736,
	3145738,
	3145748,
	0);
INSERT INTO R_FORM
	VALUES (3145730,
	3145738,
	3145749,
	1,
	1,
	'has instance');
INSERT INTO R_RGO
	VALUES (3145730,
	3145738,
	3145749);
INSERT INTO R_OIR
	VALUES (3145730,
	3145738,
	3145749,
	0);
INSERT INTO R_ASSOC
	VALUES (3145739);
INSERT INTO R_REL
	VALUES (3145739,
	11,
	'This relationship determines which symbols may appear on which
diagrams.',
	3145734);
INSERT INTO R_AONE
	VALUES (3145735,
	3145739,
	3145750,
	1,
	1,
	'can appear on');
INSERT INTO R_RTO
	VALUES (3145735,
	3145739,
	3145750,
	0);
INSERT INTO R_OIR
	VALUES (3145735,
	3145739,
	3145750,
	0);
INSERT INTO R_AOTH
	VALUES (3145736,
	3145739,
	3145751,
	1,
	1,
	'can show');
INSERT INTO R_RTO
	VALUES (3145736,
	3145739,
	3145751,
	0);
INSERT INTO R_OIR
	VALUES (3145736,
	3145739,
	3145751,
	0);
INSERT INTO R_ASSR
	VALUES (3145737,
	3145739,
	3145752,
	0);
INSERT INTO R_RGO
	VALUES (3145737,
	3145739,
	3145752);
INSERT INTO R_OIR
	VALUES (3145737,
	3145739,
	3145752,
	0);
INSERT INTO R_ASSOC
	VALUES (3145740);
INSERT INTO R_REL
	VALUES (3145740,
	13,
	'',
	3145734);
INSERT INTO R_AONE
	VALUES (3145729,
	3145740,
	3145753,
	0,
	1,
	'is flexed by');
INSERT INTO R_RTO
	VALUES (3145729,
	3145740,
	3145753,
	0);
INSERT INTO R_OIR
	VALUES (3145729,
	3145740,
	3145753,
	0);
INSERT INTO R_AOTH
	VALUES (3145733,
	3145740,
	3145754,
	1,
	1,
	'is flexing');
INSERT INTO R_RTO
	VALUES (3145733,
	3145740,
	3145754,
	0);
INSERT INTO R_OIR
	VALUES (3145733,
	3145740,
	3145754,
	0);
INSERT INTO R_ASSR
	VALUES (3145739,
	3145740,
	3145755,
	0);
INSERT INTO R_RGO
	VALUES (3145739,
	3145740,
	3145755);
INSERT INTO R_OIR
	VALUES (3145739,
	3145740,
	3145755,
	0);
INSERT INTO R_ASSOC
	VALUES (3145741);
INSERT INTO R_REL
	VALUES (3145741,
	12,
	'',
	3145734);
INSERT INTO R_AOTH
	VALUES (3145729,
	3145741,
	3145756,
	0,
	1,
	'is resized in');
INSERT INTO R_RTO
	VALUES (3145729,
	3145741,
	3145756,
	0);
INSERT INTO R_OIR
	VALUES (3145729,
	3145741,
	3145756,
	0);
INSERT INTO R_AONE
	VALUES (3145730,
	3145741,
	3145757,
	0,
	1,
	'is resizing');
INSERT INTO R_RTO
	VALUES (3145730,
	3145741,
	3145757,
	0);
INSERT INTO R_OIR
	VALUES (3145730,
	3145741,
	3145757,
	0);
INSERT INTO R_ASSR
	VALUES (3145738,
	3145741,
	3145758,
	0);
INSERT INTO R_RGO
	VALUES (3145738,
	3145741,
	3145758);
INSERT INTO R_OIR
	VALUES (3145738,
	3145741,
	3145758,
	0);
INSERT INTO R_ASSOC
	VALUES (3145742);
INSERT INTO R_REL
	VALUES (3145742,
	14,
	'',
	3145734);
INSERT INTO R_AONE
	VALUES (3145729,
	3145742,
	3145759,
	0,
	1,
	'');
INSERT INTO R_RTO
	VALUES (3145729,
	3145742,
	3145759,
	0);
INSERT INTO R_OIR
	VALUES (3145729,
	3145742,
	3145759,
	0);
INSERT INTO R_AOTH
	VALUES (3145734,
	3145742,
	3145760,
	0,
	1,
	'');
INSERT INTO R_RTO
	VALUES (3145734,
	3145742,
	3145760,
	0);
INSERT INTO R_OIR
	VALUES (3145734,
	3145742,
	3145760,
	0);
INSERT INTO R_ASSR
	VALUES (3145740,
	3145742,
	3145761,
	0);
INSERT INTO R_RGO
	VALUES (3145740,
	3145742,
	3145761);
INSERT INTO R_OIR
	VALUES (3145740,
	3145742,
	3145761,
	0);
