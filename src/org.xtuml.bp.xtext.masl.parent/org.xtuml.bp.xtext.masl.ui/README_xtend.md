README - Working with xtend and bp.core
===================

The xtend compiler has issues trying to generate java from xtend source files
when the source code references classes in `org.xtuml.bp.*`.  The issue may 
not appear until the project is compiled with maven.    

[Issue 9141](https://support.onefact.net/issues/9141) captures the problem.  

Until this issue is fixed, xtend files that reference `org.xtuml.bp.*` must be generated 
and then the generated Java file committed to revision control and the source 
xtend file named to have the file extension `.xtend_`.  An example of this is 
found in `git/bridgepoint/src/org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.ui/src/org/xtuml/bp/xtext/masl/ui/document/`.  

BridgePoint developers are encouraged to make enhancements to the xtend source 
and regenerate the java file to be committed.  The process is roughly:

1. Delete the existing `.java` file in `<project>/src/...`
2. Rename the `.xtend_` file to `.xtend`
3. Make the required changes.  Build cleanly in an Eclipse environment.
4. Copy the `.java` file that refers to `bp.*` from `<project>/xtend-gen/...` to `<project>/src/...`
5. Rename the source `.xtend` file back to `.xtend_`
6. Commit changes
7. Clean and rebuild to assure that there is no behavior change  

