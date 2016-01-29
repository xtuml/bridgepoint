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
<a id="2.1"></a>2.1 [BridgePoint DEI #8238](https://support.onefact.net/issues/8238) This issue.  
<a id="2.2"></a>2.2 [BridgePoint DEI #8086](https://support.onefact.net/issues/8086) Graphics Auto-creation Not Working.  
<a id="2.3"></a>2.3 [BridgePoint DEI #8239](https://support.onefact.net/issues/8239) Connector layout of auto-created connectors must be laid out in a way that allows manual reconfiguration.  
<a id="2.4"></a>2.4 [BridgePoint Development notes for  dts0100573206 - #1432](https://github.com/rmulvey/bridgepoint/tree/8238_render_graphics_including_connectors/doc-bridgepoint/notes/dts0100655903-1403) Consistent context menu for creating model elements.  
<a id="2.5"></a>2.5 [BridgePoint Design Note for dts0100655903](https://github.com/rmulvey/bridgepoint/blob/8238_render_graphics_including_connectors/doc-bridgepoint/notes/dts0100655903-1403/dts0100655903.dnt) Support drawing of missed xtUML Connectors on Canvas.  
<a id="2.6"></a>2.6 [BridgePoint Review observations for dts0100655903](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20100712/review_minutes/R3_1_1/dts0100655903-01.rvm) Design note review observations for [[2.5]](#2.5).  
<a id="2.7"></a>2.7 [Link to the reason dts0100655903 was not promoted](https://support.onefact.net/issues/1432#note-58) This is a comment from the old CQ issue tracking system where the decision not to promote was made. This was AFTER a successful final review of the work [[2.6]](#2.6).  
<a id="2.8"></a>2.8 [dts0100711482.dnt](https://github.com/xtuml/internal/blob/71c842bdcd937f946f977d529dc90e0f9a5f2486/Documentation_archive/20110614/technical/notes/dts0100711482.dnt) Design note for supporting hover text at connector ends. This is just another issue whose work may be useful for the task at hand. 
vey/bridgepoint.git

3. Background
-------------
Reconciliation was initially introduced just after the introduction of components in BridgePoint. 
It was needed for the case of formalization of components. This was needed because, when formalized, 
the interfaces needed to be auto-created on the component references to allow wiring of the component diagrams. 
Additionally, it is worth noting that this initial "graphics reconciliation" also had to consider the case where the component was unformalized because when unformalized, the interfaces, if any, had to be removed from the 
graphics.

As time went by, it became desirable to make the tool easier to use, and it was determined that to 
facilitate this users should not have to "select, drag, and drop" in order to create shapes. The tool was 
then enhanced so shapes could be created by simply selecting an option in the configuration menu entry (CME) [[2.4]](#2.4). 
When that work was analyzed, it was further determined that the CME options should be consistent regardless of which 
CME was used (canvas or model explorer). Furthermore, it was determined that the "new" CME options introduced should 
remain consistent with the palette creation options (which remained to allow creation via drag/drop). The introduction of this ability to draw shapes from the CME caused the reconciler's behavior to grow.

Since the completion of the CME project [[2.4]](#2.4) BridgePoint is primed to be able to create all graphics from an imported model that has no graphics. There was a project that pushed this further, Autosar model import, but that project was never completed and its work was not done by the main stream team, and thus it was never fully put into the tool. The memory of this Autosar project initially led me to think that more had been done with reconciliation than has. The tool can reconcile, as designed for the cases described above, but it does not reconcile for cases it was not designed for. 

An issue investigated as part of the initial analysis for this work [[2.3]](#2.3) read that reconciliation is broken because graphics are not created for elements where there are existing model elements that contain no graphics. Investigation shows that the tool was not designed to create graphics for all the OTHER elements in the package. While 
this functionality should not be difficult to add given the current infrastructure, and in fact has been done as part of 
the work of investigating this issue, it does not mean reconciliation is broken. Reconciliation is not broken.

4. Requirements
---------------
The SRS associated with this project shall define requirements for this task. The following requirements are in 
addition to those requirements. Note that when this analysis was written the SRS had not yet been completed. Therefore, when written, this issue's design note shall call out the links between the requirements below and the SRS, and shall account for discrepancies (for example, additional requirements needed).  

4.1 Create shapes in correct packages for all xtUML elements in a project.  
4.2 Created shapes shall be grid into a matrix with reasonable spacing.  
4.2.1 The grid shall be greater than 1 dimension (not just 1 row).  
4.3 Create connectors in the correct packages.  
4.3.1 Connectors shall be drawn between all participating widgets.  
4.3.2 Connectors with just one participating widget shall also be drawn.  


5. Analysis
-----------
This section enumerates different approaches considered, and at the end defines the selected 
approach with the reasons why the selected approach was selected.  

5.1 Create a new model compiler to create graphics.  
The project at hand requires graphics creation starting from a model that contains no graphics. It is possible to create a model compiler that would read the xtUML model file being imported and create graphics instances. It is observed that the graphics instances in an model file that contains graphics follow a pattern. One can carefully add model elements one at a time and observe the graphics elements created as a result of each model element creation. By repeating this process for shapes, and for each type of connector, and one could learn what graphic model element are required for each model element needing graphics. With this information, one could then create a model complier to create the graphics instances without ever even looking into the implementation of graphics in BridgePoint. This would be a very mechanical process.

5.2 Use the existing BridgePoint graphics reconciliation infrastructure to create graphics.  
As described in the background BridgePoint has infrastructure in place that allows for graphics reconciliation. This was not designed to create graphics from a model that contains no graphics at all. However, the infrastructure has been enhanced a few times, and it could be enhanced again now to provide the reconciliation needed for this project.  
5.2.1 Shape creation  
Shape reconciliation is already complete and works with one exception, described in the following section.  
5.2.1.1 Current graphics shape reconciliation assumes a graphics model root is present.  
The current reconciliation infrastructure was designed to reconcile from an existing model that contains graphics, there is an assumption present that the graphics model root exists for each model element root. A GD_MD instance represents the graphics model root. An xtUML model with no graphics contains no GD_MD elements. The graphics model root is associated with the container that a model element is being added to. Every xtUML file that has graphics contains a single GD_MD element. To reconcile all graphics, a reconciliation pass is required to create these GD_MD elements before elements under the GD_MD can be created.  
5.2.2 Connector creation  
BridgePoint contains support for some connector creation already. Additionally, work has been completed, but not promoted, for additional connector creation. The section below describes what is done already and what needs to be added.  
5.2.2.1 Interfaces  
As described in the background section, the need for interface reconciliation was the reason graphics reconciliation was initially added to the tool. However, that work did not need to account for satisfactions (and so it did not). However, the work done for [[2.5]](#2.5) did complete this task. The good news is, the design note for that work calls out what was done, the change set is only 10 files, and we can use it as a guide. The bad news is, the work was never promoted [[2.6]](#2.6), and it is now over 5 years old.  
5.2.2.2 Associations  
There is no support for associations. This would be completed by following the lead of the work done for reconciliation of interfaces [[2.5]](#2.5). The Relationship subsystem is complex, but this work should not be affected by that complexity. The graphics reconciliation interface is the same for all types of connectors.  
5.2.2.3 Transitions  
There is no support for transitions. This would be completed by following the lead of the work done for reconciliation of interfaces [[2.5]](#2.5). The graphics reconciliation interface is the same for all types of connectors.  
5.3 Selection  
There is a lot of work to either approach. However, the approach form section 5.2 has been followed successfully in the past. Section 5.1 will hit unknowns that we have not encountered before this SHOULD note be true of 5.2. Section 5.2, using  the existing infrastructure is selected.  

6. Work Required
----------------
6.1 Shape reconciliation    
6.1.1 Refactor the auto-reconciliation front-end interface out of the CanvasTransactonListener.  
Current the graphics reconciliation runs only from the end-transaction processing of the CanvasTransactionListener. This makes is hard to reuse reconciliation in multiple places. Additionally, the reconciliation front-end interface is not insignificant. From a design stand point it deserves to be a first-class object. The work shall make it so.  
6.1.1.1 Remove artifacts in the reconciliation interface that are only present to prevent unit testing problems.  
The CanvasTransactonListener contains static operations enableReconciler()/disableReconciler() that are only used by unit testing. When 6.1.1 is complete, the unit tests can simply override the abstract interface with a testing interface.   
6.1.2 Modify model import to allow graphics to be created on import   
6.1.2.1 A preference shall be introduced, under the new xtUML preferences section named Model Import. The new preference in this new section shall be named: Create Graphics. This new preference shall be disabled by default.  
6.1.2.2 Modify model import and the reconciler to run during import to create GD_MD elements for all model roots.  
6.1.2.2 When the Create Graphics preference is enabled, automatically run the graphics reconciler again after import to force reconciliation of all graphics under the GD_MD instances that were created on initial import.  
6.2 Connector reconciliation    
6.2.1 Interfaces  
Complete the work described in [[2.5]](#2.5).
6.2.2 Associations  
Follow the pattern of the work done to complete graphics creation for interfaces [6.2.1] to complete association reconciliation.  
6.2.3 Transitions  
Follow the pattern of the work done to complete graphics creation for interfaces [6.2.1] to complete association reconciliation.  


7. Acceptance Test
------------------
A test matrix was created for [[2.5]](#2.5). This matrix shall be extended to include the new reconciliation introduced by this issue. The unit test generation utility shall be used to generate the tests for this work.   

End
---

