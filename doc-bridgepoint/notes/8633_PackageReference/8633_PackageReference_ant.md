---

This work is licensed under the Creative Commons CC0 License

---

# Package References (a.k.a. Imported Packages)
### xtUML Project Analysis Note

1. Abstract
-----------
A new feature is introduced that allows Packages to refer to other Packages
and thus cause an _import_ of the contained elements.

2. Document References
----------------------
<a id="2.1"></a>2.1 [8633](https://support.onefact.net/issues/8633) Package References (Imported Packages)  

3. Background
-------------
During development of modeled model compiler features, the BridgePoint team
realized that a feature was missing from BridgePoint.  There was no way to
easily reuse class diagrams in multiple deployments.  Classes themselves
were not sharable between projects.  Together with the requirement that all
executable configuration require components for Verifier or model compilers
to process them, this limitation greatly impacted the ability to share a
common class diagram.

To address this weakness, a patched version of BridgePoint was created that
allowed classes to be made visible across projects (Inter-Project References
for classes and functions).  A clever hack was made to the model compiler to
allow an empty Package to refer to another Package somewhere in the workspace
(using labels in the `Descrip` fields).  The combination of opened up IPRs
and hacked Package importing proved power and extremely useful.  A major
refactoring of the model compiler models (escher, mcooa, Document, docgen,
etc.) followed.  The result is cleaner and exhibits strong reuse.

This note analyzes the work required to enable Package References as a
first-class feature.  Support in the metamodel is added.  The parser is
updated to follow Package References, the Verifier is tested with the
feature and the editor supports designating Packages as Package References.

4. Requirements
---------------
#### 4.1 Package Reference  

4.1.1 Allow a Package to be designated as a Package Reference.  
4.1.1.1 Only a Package located as a child of a Component shall be
eligible to be a Package Reference.  

4.1.2 Allow a Package Reference to refer to ("import", be linked to)
any eligible Package visible within the workspace.  
4.1.2.1 The user interface shall provide a list of Packages in the
workspace which are eligible for assignment (import).  
4.1.2.2 The user interface shall exclude Package References from the
list of Packages eligible for assignment.  
4.1.2.3 The user interface shall exclude Packages which _contain_ the
location of the Package Reference.  

4.1.3 Empty Package  
4.1.3.1 A Package Reference shall not itself contain elements.  
4.1.3.2 Upon an attempt to assign a Package Reference containing elements,
the user interface shall supply a message to the user explaining that the
assignment cannot occur due to the presence of elements within the Package.  
4.1.3.3 Upon an attempt to add or paste an element into a Package Reference,
the user interface shall supply a message to the user explaining that the
add or paste cannot occur.  

#### 4.2 Editor Representation  
4.2.1 No new graphical model elements are introduced.  A Package Reference
looks like a Package with a longer (full-path) name.  
4.2.2 Upon assignment, the name of the Package Reference becomes a fully
qualified (with double-colons) name of the referred to Package.

#### 4.3 Persistence  
4.3.1 The underlying model element data for a Package Reference shall be
persisted in proximity (same file) to Package that was created to serve
as the reference.

#### 4.4 Navigation  
4.4.1 Double-clicking a Package Reference will open the referred to
Package.  

#### 4.5 Element Visibility  
4.5.1 Scoping and Element Visibility are affected by this work.  This
functionality shall be adjusted such that Package References behave as
if the referenced Package had been copied to the location of the Package
Reference.  

#### 4.6 Verifier  
4.6.1 Verifier shall behave as if referenced Packages had been copied
to the locations of the Package References.  

#### 4.7 Model Compilation  
4.7.1 The prebuilder shall emit model element data capturing the semantics
of the Package Reference.  
4.7.2 The model compilers shall be enhanced to traverse the Package Reference
associations.  The behavior of the model compilers will be the same as if
the referenced Package had been copied into the Package Reference location.

#### 4.8 Documentation  
4.8.1 User documentation shall be updated to explain the rules of Package
References and the way they are edited.  

5. Analysis
-----------
#### 5.1 Use Case 1  
Consider a system involving communication.  The model for the communication
involves classes that define the structure of messages.  The model contains
state machines the govern the sequencing of sending and receiving these
messages.  Such class diagrams and state machines need to be used
5.1.1 Class diagram defined in 
* Example List Element

5.2 Item 2  
5.2.1 Example sub-item
* Example List Element

6. Work Required
----------------
6.1 OOA of OOA  
6.1.1 Suggested changes to bp.core/ooaofooa and mcooa/ooaofooa.  
![Package Reference](pkgref.png)

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

