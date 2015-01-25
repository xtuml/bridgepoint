====================================================================

 File:      $Source: /arch1/products/tiger/repository/MC-Java/README.TXT,v $
 Version:   $Revision: 1.11 $
 Modified:  $Date: 2013/01/10 23:16:30 $
 By:        $Author: rmulvey $

 (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.

====================================================================

Introduction and Overview
-------------------------
MC-Java provides translation of a model to Java code. MC-Java is written 
primarily to support plugin development for eclipse, how ever it also generates 
generic java code. The configuration can be done by setting different coloring 
information.

Until completion of issue #35, MC-Java did a two pass parse to translate a model
into code. This process relied on the action language parser built into the
generator executable. This tool is implemented in C++ and maintenance is limited
to critical defects only. For this reason, MC-Java now uses the fully supported
BridgePoint Pre-Builder infrastructure which uses a single parse step before
export.  See section �Translating model using MC-Java� for further details. The
original second step parse archetypes are modified to use the output of the Pre-
Builder directly to generate the action code.

Note that the BridgePoint internal parser emits association traversal cardinality
data in lower case, whereas the previous fragment based model compiler emitted
upper case values. This should be bourne in mind when porting other utilities to
the pre-builder infrastructure.

MC-Java Components
------------------
Following are different files that constitutes the MC-Java, along with general 
description of functionality that each of them covers:

java.arc:
			This acts as the controller archetype for MC-Java. 
arch_utils.inc:
			The functions in this file define the naming convention for the code
			that it generates for entities. For example the naming convention 
			used for class names is currently <Classname>_c, which can be 
			changed by editing this archetype, if required.  It also contains
			utility functions used by more than one archetype.
			
do_type.inc:
			This file defines mapping of model specific data types to java 
			specific types. Any futher mappings should be defined in this 
			archetype.
			
enums.inc:
			The functions in this file generate the classes for enumeration data
			types.
			
values.inc:
			The function in this file implements the different values used in 
			assignments etc.
			
statement.inc:
			The functions in this file are used to generate the code for all 
			kind of statements.
			
block.inc:
			This file contains only one function which serves as controller for 
			translating a given outer block of action language. For each of 
			constructs that it encounters, it delegates the translation to 
			relevant archetype functions.
			
translate_oal.inc:
			The functions in this file generate action language from the 
			populated OOA. This file is included in java.arc after the inclusion
			of values.inc, statement.inc, and block.inc. It also uses the file 
			reffering.inc and reffered_to.inc 
			
model_element.inc:
			This file generates code for different classes to be used of event 
			handling for an eclipse plugin.
			
pg_schema_gen.arc:
			This archetype extracts the schema for a user model from a .gen 
			database, which is output as an SQL file suitable for use in a 
			subsequent gen_import for building an instance database.
			
schema_gen.arc:
			This archetype transforms a user model into a SQL file suitable for
            use in a subsequent gen_import for building an instance database.
			
referring.inc:
			This file generates code for association references made from a 
			class (whose code is being generated) to the other.
			
referred_to.inc:
			This file generates code for association referring to the class 
			(whose code is being generated) to the other.

sql_schema.inc:
			These functions provide the encoded names for properties of the 
			database.
			
Build.xml:
			This ANT build file is used for the two pass translation of the 
			model.
			
Other Files:
			Some other files are also part of the MC- Java project which are, 
			some the files have a self description header available:
			a)	BUILD.SH
			b)	GEN.SH
			c)	README.TXT
			d)	clearooa2.sql
			e)	mc_java.depends

Coloring
---------
MC-Java provides a primitive support of coloring the solution. The coloring 
information is primarily used for specifying the application type, some behavior
such as persistence and threading model and initialization information. The 
coloring information is provided in three different coloring (.clr) files. The 
following sections explain each of these files:

1) ${DomaniName}_import_spec.clr:
	.function get_imports
	  .assign attr_import = ""
	.end function

This coloring file provides method of customizing import definition at domain 
level. It contains single function with single attribute which is described 
below:

- attr_import
Define import statements for all classes (excluding external entities) that are 
included in given domain name. The value of this attribute should be:
a.	Complete import statements including semicolon (import java.util.*;) 
b.	Separation of multiple imports by an end of line escape sequence (\n).

- Sample File Contents:
.function get_imports
  .assign attr_import = "import java.net.*;\nimport org.xml.*;"
.end function
 
2)	${DomaniName}_package_spec.clr
	.function get_package
	  .assign attr_location = ""	
	  .assign attr_name = "com.pt.MicrowaveOven"
	  .assign attr_application_root_class = "MicrowaveOven"
	  .assign attr_threading_model = "one_shot"
	  .assign attr_is_persistent = false
	  .assign attr_is_eclipse_plugin = true
	  .assign attr_db_driver = "org.postgresql.Driver"
	  .assign attr_db_name = "jdbc:postgresql:ooa2"
	  .assign attr_db_user = "Siddique"
	  .assign attr_db_password = ""
	  .assign attr_ui_root_class_name = ""
	  .assign attr_ui_root_class_accessor = ""
	  .assign attr_is_root = false
	.end function

This coloring file allows customization of overall code architecture. Each 
attribute is described below:

- attr_location
This attribute defines destination folder where code will be generated. The path
must be specified relative to project root directory and must exist prior to 
generation.
This is used in .emit statements for generating the source code file in the 
required destination.


- attr_name
The attribute specifies the package name for each class. The package name is 
case sensitive.

- attr_application_root_class
It should have the domain name of your model (recommended). The generated code 
will have a class of this name. The class has a main function as well. One of 
the tasks that this class does is writing log of the different things across the 
code (specially in cases of exceptions). 

If the attr_is_eclipse_plugin is false, it generates a main function, hence 
acting as the main class for the program.

- attr_threading_model
The values that this can possibly have are �polling� or �one_shot�. This 
variable effects the behavior of the application_root_class, by making it an 
event based class or a one go class. 
By making the application one_shot, the one_shot function of activePoller_c 
class is to be called manually from application_root_class, and than after each 
event is received one manually needs to add a call to one_shot function.
The �polling� makes the activePoller_c to continue polling for event until the 
halt() is not called. Method start() of activePoller_c must be called to start 
the polling thread.

- attr_is_eclipse_plugin
This attribute is of type boolean (true/false)
This variable has lot of importance in generating code. It is used in generating
eclipse plugin specific code specially for event handling. It adds quite a few 
classes and also extends and implements the domain classes from some of them.

If true following classes from model_element.inc are added to the project:
DeltaEvent.java
IDdeltaListener.java
NullDeltaListener.java
ModelElement.java
ModelChangedEvent.java
IModelChangeListener.java
IModelChangeProvider.java
ModelDelta.java

If this variable is true then every class of domain �extends� ModelElement and 
implements IAdaptable and Cloneable.
This variable is used in various decisions across the code generation. 


- attr_is_persistent
This attribute is of type boolean. (true/false)
A lot of persistence related code is generated by checking this value.

The following variables are used only when attr_is_persistent is true. 
attr_db_driver
attr_db_name
attr_db_user
attr_db_password

- attr_ui_root_class_name
The name of root class for User Interface. A root UI class variable is declared 
in the application_root_class, if this variable is not empty.

- attr_ui_root_class_accessor
The name of a valid acessor function available in the UI root class, which is 
used in the application_root_class for getting an object of the UI root class.

- attr_is_root
This variable is of type boolean(true/false)
If true then the attr_name (the package name) is specified for all the class 
files generated which includes non-domain classes as well. 
This value is checked before inserting the package name just before emitting 
non-domain classes (which include classes for Event Generation and timers etc.).


- Sample File
	.function get_package
	  .assign attr_location = "src/com/pt/MicrowaveOven"
	  .assign attr_name = "com.pt.MicrowaveOven"
	  .assign attr_application_root_class = "MicrowaveOven"
	  .assign attr_threading_model = "one_shot"
	  .assign attr_is_persistent = false
	  .assign attr_is_eclipse_plugin = false
	  .assign attr_db_driver = "org.postgresql.Driver"
	  .assign attr_db_name = "jdbc:postgresql:ooa2"
	  .assign attr_db_user = "Siddique"
	  .assign attr_db_password = ""
	  .assign attr_ui_root_class_name = ""
	  .assign attr_ui_root_class_accessor = ""
	  .assign attr_is_root = true
	.end function
 
3.	${DomaniName}_startspec.clr
	.function define_startspec
	  .assign attr_init_type  = ""
	  .assign attr_init_var_name = ""
	  .assign attr_init_class = ""
	  .assign attr_init_stim  = ""
	  .assign attr_parameter1 = ""
	  .assign attr_parameter2 = ""
	.end function

This coloring file is used only by the application_root_class in its init() 
function to create the initial or main object of the domain and sending the 
created object an event or calling a function to instentiate the program.

- attr_init_class
It is the name of the class that is used to initialize the Model. 
For example it can be Oven in case of MicrowaveOven example. 

- attr_init_var_name
It is the name of the instance of the init_class, created by the 
application_root_class.

- attr_init_type
It specifies the type of system initialization call i.e. event or function of 
init_class. Event type initialization generates an event to the instance of 
init_class. Function type initialization calls a function of the init_class.
The value for this attribute are "event" or "function".

- attr_init_stim
It is the name of the init_class method (in the generated code) used to generate
a particular event for the instance or a function call that initializes the 
system.

attr_parameter1
attr_parameter2
These are the arguments passed to event generation function or a simple function.
In case of event generation function these are 2nd and 3rd arguments as the 
first argument is a Boolean (specifying a self event or otherwise, in this case
this value is false as it is not a self event).  
For function call initialization these are 1st and 2nd arguments.

The application_root_class first create an instance of init_class with name of 
init_var_name. Then it calls the init_stim by passing the paramater1 and 
parameter2 in the right position based on value of inti_type.

- Sample File
.function define_startspec
  .assign attr_init_type  = "function"
  .assign attr_init_var_name = "m_Oven"
  .assign attr_init_class = "Oven"
  .assign attr_init_stim  = "Defineoven"
  .assign attr_parameter1 = ""
  .assign attr_parameter2 = ""
.end function
 
Use of Parse keywords for special Code generation Commands
-----------------------------------------------------------
A parse keyword is a piece of text places in a string attribute of a modeling 
element by an analyst create a model with the Model Builder tool. A parse 
keyword is followed by colon (:) and then  the value of the keyword e.g. 
(import:import java.net.*;) See Design Guide (design.pdf) Page 198 for the usage
of Parse Keywords. MC-Java uses a few parse keywords for element specific 
information, and looks for these keywords in any Elements Description. Element 
here can be a Domain, Subsystem, Class, Operation, Bridge, Event and Attribute.

Following are the a few Parse keywords that can be used
1.	import
This is a string type variable.		
This keyword can be included in Domain Description, External Entity Description, 
Bridge Description and Subsystem Description. 
The values of import should be a complete import statement. This statement is 
used to import special classes and packages that are required by a specific 
element. 

2.	Translate
This is a string enumeration type variable, available in Function Description, 
Bridge Description, Subsystem Description, External Entity Description, 
Operation Description.
The keyword can be used to instruct MC-Java to generate code (or not to 
generate) for a Function or External Entity or Subsystem or Bridge

For Functions, Bridges and Class operations (also called Transformers) if this 
value is native then the output is the text written in action_semantics, else it
parses the OAL in it and generate code accordingly.

For an External entity if this value is �false� then MC-Java skips that External 
Entity, else generates a class for each External Entity and parses the bridges 
as well.

For a subsystem if this value is �false� then MC-Java skips code generation for 
all the classes in the subsystem.

3.	Data
An External Entity and its bridges may wish to add some specific variables in 
the class. These variables can be added using this keyword. The variables are by
default static and initialized to null.

4.	PEI
This has string values �true� to mention that this is PEI class. Hence exist 
only in Class Description.

5.	Persistent


Description
-----------

  This directory contains the files for the MC-Java architecture.
  One main shell script, gen.sh, runs the architecture and produces
  the Java files in the 'generated' directory.  The file
  ext_ooa_schema.gen should be placed in this directory prior to
  running gen.sh.  

  The file ext_ooa_schema.gen is produced by a gen.sh shell script
  located in ../ooa_bpal.

  The current implementation does not support the prettying of the
  generated code.  Prettying is done somewhat by producing the file
  beautify.bat in the generated directory.  This file depends on
  astyle which is available at gene.md.huji.ac.il/~tald/astyle.
  beautify.bat is run in the 'generated' directory _after_
  translation has occurred.

File list for tinyjava
----------------------

ext_ooa_schema.gen: This file is a pre-made generation database
                    that contains tables for the OOA of OOA and the
                    OOA of BPAL.  This file needs to be generated
                    by running gen.sh in the ooa_bpal directory.
                    This file should be copied to ../tinyjava prior
                    to running the tinyjava architecture.

fragment.inc      : This archetype generates the SQL INSERT
                    statements for each BPAL statement as it is
                    parsed. 

gen.sh            : Shell script that runs the tinyjava
                    architecture.  It takes the name of a domain as
                    a parameter.  The .sql for the domain should
                    reside in the tinyjava directory.

                    Usage: ./gen.sh <domain>, i.e., ./gen.sh odms

translate_oal.inc : This is an archetype file that organizes the
                    generation of the Java code fragments for
                    instances of V_VAL and ACT_SMT.  It is run to
                    have each of the values and statements in the
                    action language translate themselves.

java.arc          : This is the main archetype that generates the
                    Java code for the system.  The structural
                    archetypes as well as some mechanisms are
                    buried in this archetype.  The function
                    blck_xlate (in processing.arc) is called to
                    get the code for each of the state actions.

block.inc         : This archetype contains the function blck_xlate
                    that orders the statements in an action and
                    creates a buffer that contains the code for an
                    action.   

readme.txt        : This file, which explains the directory and
                    files here within.

release_notes.txt : File that describes the details of the lastest
                    release of the tinyjava architecture.

rgo.arc           : This archetype translates relationships in
                    referring objects into Java reference variables
                    and functions.

rto.arc           : This archetype translates relationships in
                    referred to objects into functions that allow
                    relationship navigation.


ooa_bpal_pop.arc  : This archetype invokes AL_xlate, thus executing
                    the fragment generation functions in
                    fragment.arc, for the actions in the
                    application.  The result is a set of INSERT
                    statements that are used to populate the OOA of
                    BPAL schema.

statement.inc     : This archetype contains functions for each of
                    the subtypes of ACT_SMT.  These functions are
                    used to have each subtype 'translate itself'
                    and place the resulting Java code into the
                    buffer and buffer2 attributes of the related
                    ACT_SMT instance.

value.inc         : This archetype contains functions for each of
                    the subtypes of V_VAL.  These functions are
                    used to have each subtype 'translate itself'
                    and place the resulting Java code into the
                    buffer attribute of the related V_VAL instance.

