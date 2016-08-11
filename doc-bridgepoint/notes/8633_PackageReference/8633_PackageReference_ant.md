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
executable configurations require components for Verifier or model compilers
to process them, this limitation greatly impacted the ability to share a
common class diagram.

To address this weakness, a patched version of BridgePoint was created that
allowed classes to be made visible across projects (Inter-Project References
for classes and functions).  A clever hack was made to the model compiler to
allow an empty Package to refer to another Package somewhere in the workspace
(using labels in the `Descrip` fields).  The combination of opened up IPRs
and hacked Package importing proved powerful and extremely useful.  A major
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
persisted in proximity (same file) to the Package that was created to serve
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
includes classes that define the structure of messages.  The classes contain
state machines that govern the sequencing of message sending and receiving.

Such class diagrams and state machines need to be (re)used within multiple
subsystems and configurations of a networked system.  Namely, the _Master_
and the _Slave_, or two peers on the communication network that have different
purposes but exchange messages using the same protocol.

5.1.1 xtUML Project _Messaging_  
Inside Messaging is a package called "Messaging" containing classes
Message, Header, Payload and Address.  The Message class has a state
machine that sequences sending and receiving data using a particular
protocol.  
5.1.2 xtUML Project _Master_  
5.1.2.1 Inside Master is a package called "Heart Rate Monitor"
containing classes such as Sample Rate, Threshold and Display.  
5.1.2.2 Inside Master is a package called "Deployment" containing
a component called "Heart Rate Node".  Inside Heart Rate Node are two
Package References.  The first Package Reference refers to (imports)
Heart Rate Monitor.  The second Package Reference imports Messaging.
5.1.3 xtUML Project _Slave_  
5.1.3.1 Inside Slave is a package called "Data Aquisition" containing
classes such as Sample, Filter and Buffer.  
5.1.3.2 Inside Slave is a package called "Deployment" containing
a component called "Sensor Node".  Inside Sensor Node are two
Package References.  The first Package Reference imports Data Aqcuisition.
The second Package Reference imports Messaging.

#### 5.2 User Interface Considerations  
5.2.1 New Graphical Element  
The approach proposed in the requirements section assumes and actually
dictates that no new graphical elements will be introduced.  Such an
approach is inconsistent with what is currently found with Imported Classes
(`O_IOBJ`) and Component References (`CL_IC`).  Both of these elements have
items in the Palette and have (minor) distinctives in their graphical
representations.

An alternative approach would be to introduce a new glyph in the Palette
and a distinctive look on the graphical representation of a Package
Reference.

This approach might clarify some of the user interface actions (such
as avoiding the placement of model elements inside of a Package Reference).
But the cost is added graphical complexity.

5.2.2 Fix the Others  
Another thought is to "fix" what is overly complex with Component
References and Imported Classes.  Perhaps these could be simplified to
remove the distinctive graphical elements and changed to be assignments
of the base element (Component gets assigned as Component Reference,
and Class gets assigned as Imported Class).  These elements are already
only minorly distinctive with the fully qualified path being the 
primary means of discrimination.

While fixing Component Reference and Imported Class, the metamodel
could also be simplified.  There is a great deal of duplication around
these reference-oriented model elements.

6. Work Required
----------------
6.1 OOA of OOA  
6.1.1 Suggested changes to bp.core/ooaofooa and mcooa/ooaofooa.  
![Package Reference](pkgref.png)

6.1.2 A "Name" attribute should be considered for Package Reference.
The name displayed on the Package Reference could be implemented as
a derived attribute on Package (`EP_PKG`).  But it may make sense to
keep the original Name on Package (`EP_PKG`) and use an attribute (derived
or otherwise) on Package Reference (`EP_PKGREF`).

7. Acceptance Test
------------------
7.1 Editor Test  
7.2 Verifier Test  
7.3 Model Compiler Test  
7.3.1 escher  
7.3.2 docgen  

End
---

