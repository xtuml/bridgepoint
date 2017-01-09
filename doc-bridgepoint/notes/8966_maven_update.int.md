---

This work is licensed under the Creative Commons CC0 License

---

# Clean up maven build issues
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes additional changes to clean up maven build problems.  

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8966](https://support.onefact.net/issues/8966) Headline issue.     
<a id="2.2"></a>2.2 [BridgePoint DEI #8965](https://support.onefact.net/issues/8965) Eclipse is slow and hangs when running on Ubuntu 16.04      
<a id="2.3"></a>2.3 [BridgePoint DEI #8942](https://support.onefact.net/issues/8942) Segregate tests out of xtuml/bridgepoint     

3. Background
-------------
Recent changes [2.3] broke the maven build.  An issue was found [2.2] that has a 
simply fix we want to get into our build.   

4. Requirements
---------------
4.1 Maven-based build of BridgePoint works.     

5. Work Required
----------------
5.1  bridgepoint repository   
5.1.1  Remove all references to the ```bp.bld.pkg(-feature)``` and 
  ```bp.verifier.pkg(-feature)``` plugins from maven POM files, the BridgePoint
  product definition, and BridgePoint feature configuration.   
5.1.2  To resolve [2.2], add the required argument in ```org.xtuml.bp.releng.parent.product/bridgepoint.product``` 
  on the Launching tab in "Program Arguments".

5.2  packaging repository   
5.2.1  We have long used the packaging repository to store the eclipse bases that
  we ship with BridgePoint.  With the move to the maven-based build, these are 
  no longer used.  Maven downloads and packages the eclipse for each OS.   
5.2.2  With a --depth 1 flag to git clone on the build server, we no longer clone
  an entire repository, just the tip.  Therefore, we can remove these eclipse bases
  from the repository and speed up the build server build because we don't clone
  Gigabytes worth of data we don't need.  But, the data is still in the repo if 
  we need to branch and build an old version of BP.   

5.3  Hudson BridgePoint job configuration    
5.3.1  Comment out the clone of the models repository.  Since we are not running
  unit tests (yet), we don't need this data.  
5.3.2  Comment out the CLI-based build of bp.core.test project.  Don't need that
  right now.  Again since we're not doing tests on the server yet.   
5.3.3  Change the email line to remove "," and add " "   

6. Implementation Comments
--------------------------
None.   

7. Unit Test
------------
7.1 Run a maven build.  See it succeed.     

8. User Documentation
---------------------
None.  

9. Code Changes
---------------
Fork/Repository: keithbrown/bridgepoint   
Branch: 8966_maven_update   

<pre>
doc-bridgepoint/notes/8966_maven_update.int.md

org.xtuml.bp.pkg-feature/feature.xml

org.xtuml.bp.releng.p2/category.xml

org.xtuml.bp.releng.parent/pom.xml

org.xtuml.bp.releng.parent.product/bridgepoint.product

org.xtuml.bp.releng.parent.tests/pom.xml

org.xtuml.bp.xtext.masl.parent/org.xtuml.bp.xtext.masl.target/
    bridgepoint_on_mac.target


</pre>

Fork/Repository: xtuml/packaging   
Branch: 8966_maven_update   

<pre>
Removed install_bases/ folder.  Changes underneath are too many to list.   
</pre>

End
---

