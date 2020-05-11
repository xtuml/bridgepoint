---

This work is licensed under the Creative Commons CC0 License

---

# Search of xtUML structural elements
### xtUML Project Implementation Note


### 1. Abstract

This note describes the implementation of the enhancement to add element 
name search to the existing xtUML search facility.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10174](https://support.onefact.net/issues/10174) Headline issue  
<a id="2.2"></a>2.2 [BridgePoint DEI #10049](https://support.onefact.net/issues/10049) Initial service request  
<a id="2.3"></a>2.3 [Design note](10174_search_dnt.md)   

### 3. Background

Users wish to search for xtUML elements by name.  The current search only
looks at action language and descriptions.  This work adds support for element
name searching based on what is discussed in the design note [[2.3]](#2.3).

### 4. Requirements

See [[2.3]](#2.3).  

### 5. Work Required

5.1  Search dialog  
 
This diagram shows the enhanced search dialog.   

![xtUML Search](xtuml_search.png)

5.2  The implementation followed the design specified in [[2.3]](#2.3). 

### 6. Implementation Comments

6.1  Initial feedback for the feature was good, but users thought it would be 
nice to also be able to search for class key letters and role phrases.  This 
support is outside the original scope that only considered element names.  However,
it was determined that the feature could be added fairly cheaply since we're already
in the area.  Keyletter and role phrase searching is added as an extension to the
name search and displays results just like a name match.   
  
6.2  `OpenDeclarationAction.java`  
The `showElement()` operation is enhanced to properly open canvases besides 
just packages.  For example, opening state machine canvas when a `Transition_c`
is double-clicked in the search results.  

### 7. Unit Test

7.1 JUnits    
7.1.1  The `org.xtuml.bp.search.test` JUnit test suite is extended to perform
name-based searches, exercising the enhanced search dialog and also opening 
results that are name matches.  

7.2  Manual promotion test  
A manual smoke test for promotion is easy to do:  
* Download the branch build  
* Install and start BP
* Create the GPS Watch example project  
* Search for known elements like "Tracking" and "getDistance"
* Verify the expected results are found and can be double-click opened in the search results

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: __keithbrown/bridgepoint__ 
Branch: __10174_structural_search__ 

<pre>
doc-bridgepoint/notes/10049_search/10049_search_ant.md            |   2 +-
 doc-bridgepoint/notes/10049_search/10174_search_dnt.md            | 263 ++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/10049_search/xtuml_search.png               | Bin 0 -> 155012 bytes
 src/org.xtuml.bp.core/arc/action_language_description_util.arc    | 174 ++++++++++++++++++--
 src/org.xtuml.bp.core/arc/name_search_utils.inc                   | 126 ++++++++++++++
 .../ooaofooa/External Entities/External Entities.xtuml            |  38 +++++
 .../Search/Engine/Declarations Engine/Declarations Engine.xtuml   |  93 +++++++++++
 .../ooaofooa/Search/Engine/Search Engine/Search Engine.xtuml      |   4 +
 .../Search/Query/Declaration Query/Declaration Query.xtuml        |  89 ++++++++++
 .../org.xtuml.bp.core/ooaofooa/Search/Query/Query/Query.xtuml     |  23 ++-
 .../Search/Result/Name Match Result/Name Match Result.xtuml       |  73 +++++++++
 .../ooaofooa/Search/Result/Name Match/Name Match.xtuml            |   6 +-
 .../models/org.xtuml.bp.core/ooaofooa/Search/Result/Result.xtuml  |  26 ++-
 src/org.xtuml.bp.core/src/org/xtuml/bp/core/Search_c.java         | 102 +++++++++++-
 .../src/org/xtuml/bp/search/query/ModelSearchInput.java           |  36 ++--
 .../src/org/xtuml/bp/search/query/ModelSearchQuery.java           |  23 ++-
 .../src/org/xtuml/bp/search/results/ModelMatch.java               |   9 +-
 .../src/org/xtuml/bp/search/results/ModelSearchResult.java        |  49 ++++--
 .../src/org/xtuml/bp/ui/search/pages/ModelSearchPage.java         |  38 +++--
 .../src/org/xtuml/bp/ui/search/pages/ModelSearchResultPage.java   |  92 +++++++++--
 .../xtuml/bp/ui/search/providers/ModelSearchContentProvider.java  |  22 ++-
 .../xtuml/bp/ui/search/providers/ModelSearchLabelProvider.java    |  27 ++-
 .../src/org/xtuml/bp/ui/text/activity/OpenDeclarationAction.java  |  81 ++++++---
</pre>

Fork/Repository: __keithbrown/bptest__ 
Branch: __10174_structural_search__ 

<pre>
 .../src/org/xtuml/bp/search/test/SearchTests.java                 | 231 +++++++++++++++++++++++++-----
 .../src/org/xtuml/bp/test/common/SearchUtilities.java             |  47 +++++-
</pre>

### End

