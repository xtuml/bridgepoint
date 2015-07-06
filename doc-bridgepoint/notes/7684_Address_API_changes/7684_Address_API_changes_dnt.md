---

This work is licensed under the Creative Commons CC0 License

---

# Address eclipse 4.4 API changes
### xtUML Project Design Note

1. Abstract
-----------
This note describes the design to address any API changes with the move to   
eclipse 4.4 stream.   

2. Document References
----------------------
[1] [BridgePoint DEI #7684](https://support.onefact.net/redmine/issues/7684)  
[2] https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/36_Update_eclipse_base/36_Update_eclipse_base.ant.md  

3. Background
-------------
See [2].   

4. Requirements
---------------
4.1 BridgePoint shall build without errors in eclipse 4.4   
4.2 Backward compatibility shall exist, changes shall build in eclipse 3.7 without error    
4.3 Unit tests shall pass in eclipse 3.7   

5. Analysis
-----------
See [2].   

6. Design
---------
6.1 Convert all BridgePoint plug-ins to the 3.7 format   

In order to build the BridgePoint plug-ins in 4.4 we must upgrade to the eclipse   
3.7 style format.  Most of the plug-ins are currently in the eclipse 2.x style.   

The PDE Tools > Convert to OSGi bundle tool was used to upgrade all of the   
existing BridgePoint plug-ins that did not yet contain a MANIFEST.MF file.   

The changes from this included creating a new MF file as well as removing   
dependency and plugin data.  The remaining parts in the plugin.xml file are any   
extensions to the eclipse platform.

Additionally, the build.properties files in all projects was changed to include   
the new MANIFEST.MF file.   

6.2 Address IFile API changes   

The move to eclipse 4.4 requires an interface change.  This change is related to   
the eclipse IFile interface.  The change was to add a new method, as [2] calls   
out we simply need to add a new method to the classes using the interface.   

For the case of the PlaceHolderEntry use we need to use java reflection in order   
to provide a working implementation in both the 3.7 and 4.4 versions of eclipse.   
This is required as we must supply the method and in the PlaceHolderEntry case   
we forward the call on to the existing IFile instance.  The code will look for   
the appropriate method signature and call it if it exists.  In the case where we   
are executing in eclipse 3.7 we will do nothing as the method will never be   
called.   

6.3 Address utility methods   

In ProjectUtilities there is a method which selects buttons in the UI during   
project creation.  This is used in both testing and in the getting started page.   
The method is setRootFolderOptionsInternal and was changed to test for both a   
Combo widget and the simple Text widget.  In Eclipse 4.4 the widget used was   
changed to a Combo.

7. Design Comments
------------------
7.1 Unit test issues   

Testing has shown that some expected results require update.  Work was recently   
done to update test models and get rid of some integrity issues.  This work was   
done under linux and the windows results were not updated.  This issue updates   
those results.    

8. Unit Test
------------
_- All existing unit tests must pass in Eclipse 3.7

End
---

