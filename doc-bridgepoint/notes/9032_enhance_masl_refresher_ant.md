---

This work is licensed under the Creative Commons CC0 License

---

# Enhance the MASL refresher
### xtUML Project Analysis Note


1. Abstract
-----------
The MASL refresher is run periodically by BridgePoint to update an internal cache
of structural MASL information for validating MASL activities.  This note proposes 
work to enhance the MASL refresher to not create unnecessary files.   
 
2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #9032](https://support.onefact.net/issues/9032) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #8797](https://support.onefact.net/issues/8797) MASL refresher.       
<a id="2.3"></a>2.3 [BridgePoint DEI #8801](https://support.onefact.net/issues/8801) MASL Round Trip Test     

3. Background
-------------
The initial implementation of the MASL Refresher (see [[2.2]](#2.2) ) does not 
prevent/skip the output of MASL action language files. Instead, it lets the existing 
MASL flow run like it does already and then deletes the files we don't want (we 
are only interested in keeping structural MASL and not activities in the refresher).  

A better solution is to enhance ```x2m``` or ```masl``` (the exe) to filter the 
activity data out or take a flag that tells it not to generate the activity files 
at all.   

4. Requirements
---------------
4.1  The xtuml2masl chain shall have the ability to run and produce only structural
  MASL output files (```.prj```, ```.mod```, and ```.int```).   
4.1.1  This shall work without deleting the files after they are created.     

5. Analysis
-----------
5.1  Implement in ```x2m``` or ```masl```?  
5.1.1  Upon investigation, implementing the solution in ```masl``` can be constrained
  to inspecting and using the flag in a single place, during file render.  
5.1.2  We considered implementing in ```x2m``` because this would seem to provide
  slightly more efficiency by "stripping out" the unwanted data earlier.  However,
  it seems this would mean sprinkling the logic around in lots of functions.  It
  would also require special care to make sure we are still creating the right 
  function and operation signatures (so they can appear in the structural output) 
  without including the activity bodies.   
5.1.3  We choose to implement in masl for clarity and simplicity.   

6. Work Required
----------------
6.1  Edit ```masl/sys_user_co.c::UserPostOoaInitializationCalloutf(...)```   
6.1.1  Add handling for command line param ```-s``` indicating to output 
  structural MASL only.  Set a boolean flag to use internally.   
6.1.2  Pass the flag to ```masl_gen_render``` on lines 144, 146, & 147   

6.2  Update the interface ```masl/lib/gen```   
6.2.1  Have the ```render``` operation accept "structural_only" flag, and pass 
  it on to ```file::render(...)```   

6.3  Edit class operation ```masl/maslpopulation/file::render()```   
6.3.1  Take "structural_only" flag as a parameter   
6.3.2  Use the flag to skip rendering activity files, terminator files, etc.   
  
6.4  Update ```xtuml2masl``` operation in ```xtumlmc_build```   
6.4.1  Pass the ```-s``` flag to ```masl``` when the user has called ```xtuml2masl```
  with ```-xl```, an existing flag telling the script to skip output action language.      
6.4.2  Remove existing code to delete the activity files.   

7. Acceptance Test
------------------
7.1  Run the MASL Round Trip Test [[2.3]](#2.3) up to Import step 2.9 
* When the MASL is opened there are no red squigglies under elements in this domain.    
* Open navigator view and verify the ```.mod``` and ```.int``` files are inside the
models folder next to the SAC.xtuml that contains the C_C instance.   

End
---

