---

This work is licensed under the Creative Commons CC0 License

---

# Render graphics including connectors
### xtUML Project Analysis Note

1. Abstract
-----------
Upon import of xtUML, render graphics for the input model elements including, components/domains, classes, state machines and external entities / terminators.

2. Document References
----------------------
In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
<a id="2.1"></a>2.1 [BridgePoint DEI #8238](https://support.onefact.net/redmine/issues/8238) This issue.  
<a id="2.2"></a>2.3 [BridgePoint DEI #8086](https://support.onefact.net/redmine/issues/8086) Graphics Auto-creation Not Working.  
<a id="2.3"></a>2.3 [BridgePoint DEI #TODO](https://support.onefact.net/redmine/issues/TODO) TODO-add description.  

3. Background
-------------
Reconciliation was initially introduced just after the introduction of components in BridgePoint. 
It was needed for the case of formalization of components. This was needed because, when formalized, 
the interfaces needed to be drawn on the component references to allow wiring of the component diagrams. 
Additionally, it is worth noting that the reconcile also had to consider the case where the component was 
unformalized, the reason being that when unformalized, the interfaces, if any, had to be removed from the 
graphics.

As time went by, it become desirable to make the tool easier to use, and it was determined that to 
facilitate this users should not have to "select, drag, and drop" in order to create shapes. The tool was 
thus enhanced so that shapes could be created by simply selecting an option in the configuration menu entry (CME). 
When that was done it was further determined that the CME options should be consistent regardless of which CME was 
used (canvas or model explorer). Furthermore, it was determined that the "new" CME options should remain consistent 
with the palette creation options (which remained to allow creation via drag/drop). The introduction of this ability to 
draw shapes from the CME caused the reconciler's behavior to grow.

Once the CME project was complete the tool was primed to be able to create all graphics from an imported model that 
had no graphics. There was a project that pushed this further, autosar model import, but that project was never 
completed and its work was not done by the mainstream team, and thus it was never fully put into the tool. The memory 
of this autosar project led me to think that more had been done with reconciliation than has. The tool can reconcile, 
as designed for the cases described above, but it does not reconcile for cases it was not designed for. This issue 
reads that reconciliation is broken because graphics are not created for elements for existing model elements when a 
reconciliation transaction is started (for example when a new element is added to a package that has existing elements 
that do NOT have graphics). The tool was not designed to create graphics for all the OTHER elements in the package. While 
this functionality should not be difficult to perform, and in fact has been done as part of the work of investigating this 
issue, it does not mean reconciliation is broken. It is not broken.

4. Requirements
---------------
The SRS assocaited with this project defines requirements for this task. The following requirements are in 
addition to those requirements. Note that when this analysis was written the SRS had not yet been completed. Therefore, this 
issues' design note shall call out the links between the requirements below and the SRS, and shall account for descrepencies 
(for example, addtional requirements needed).

4.1 Create shapes in correct packages for all xtUML elements in a project.  
4.2 Created shapes shall be grid into a matrix with reasonable spacing.  
4.2.1 The grid shall be greater than 1 dimension (not just 1 row).  
4.3 Create connnectors in the correct packages.  
4.3.1 Connectors shall be drawn between all participatin widgets.  
4.3.2 Connectors with just one participating widget shall also be drawn.  


5. Analysis
-----------
This section enumerates different approaches considered, and at the end defines the selected 
approach with the reasons why the selected approach was selected.  

5.1 Create a new model compiler to create graphics.  
5.1.1 Example sub-item
* Example List Element

5.2 Use the existing BridgePoint graphics reconciliation infrastructure to create graphics.  
5.2.1 Example sub-item
* Example List Element

6. Work Required
----------------
In this section, break out the consequential work (as a numbered list) needed
to meet the requirements specified in the Requirements section. Here is an example reference to the Document References section [[2.1]](#2.1)

6.1 Item 1  
6.1.1 Example sub-item
* Example List Element

6.2 Item 2  
6.2.1 Example sub-item
* Example List Element

7. Acceptance Test
------------------
In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

End
---

