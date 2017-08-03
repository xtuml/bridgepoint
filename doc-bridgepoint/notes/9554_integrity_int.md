---

This work is licensed under the Creative Commons CC0 License

---

# Complete Integrity Checking
### xtUML Project Implementation Note

### 1. Abstract

Perform complete (referential) integrity checking.

### 2. Document References

<a id="2.1"></a>2.1 [9554](https://support.onefact.net/issues/9554) complete integrity checking  
<a id="2.2"></a>2.2 [9554 Integrity Analysis Note](9554_integrity_ant.md) 9554 Integrity Checker Analysis Note  
<a id="2.3"></a>2.3 [9554 Integrity Design Note](9554_integrity_dnt.md) 9554 Integrity Checker Design Note  
<a id="2.4"></a>2.4 [9719](https://support.onefact.net/issues/9719) Automate build of integrity.exe.  

### 3. Background

See [[2.2]](#2.2).  

### 4. Requirements

See [[2.2]](#2.2).  

### 5. Work Required

See [[2.2]](#2.2).  

### 6. Implementation Comments

6.1 Loading File Data Directly  
It was noted during development that the Prebuilder cannot be used
for this work.  This is because Prebuilder presumes a well-formed
and well-linked model.  Prebuilder begins at the top-level of the
model (`S_SYS`) and navigates down across associations to emit the
model data.  Thus, if model data is not well-linked, then the Prebuilder
will omit data.  This work is intended to identify exactly these
types of missing links.  Thus, it is a requirement of the integrity
checker to load all instances independently of the linkage.  The
MC-3020 loader does exactly this.  It loads all instances first, and
then _batch relates_ next.  When a link is not found, the instance
remains unlinked.  The integrity checker can then detect and report
the missing link by selecting all instances and exhaustively interrogating
the linkage.

6.2 Inter-Project References (IPRs)  
We will need to consider that checking across project boundaries
means we need to have all of the data from all included projects.
A multi-select is required.  In the GUI, this will mean selecting
all inter-dependent projects to run the Check Referential Integrity.
From the command line, all inter-dependent projects must be passed
as parameters on the command line.

6.3 Command Line  
Most of the interface to the integrity checker is supplied through
`xtumlmc_build` passing `xtuml_integrity` as the first argument.
Additional argument
Update `xtumlmc_build` to simplify the interface and allow multiple
folders to be passed in (or grep the proxies for the referred to projects).

6.4 Global Native Types  
BridgePoint supplies global, native xtUML types to projects.  These types
are not stored as part of the project xtUML model data.  Instead, they
are loaded from within the tool.  This file is found in the org.xtuml.bp.pkg
project at `globals/Globals.xtuml`.

The GUI will automatically include this data along with the selected
project(s) model data.  When using the command line, the user must supply
the path to this file as part of the invocation.

6.5 Pathing  
I am thinking about selecting all of PEs together, because I can create
a path for these.


### 7. Unit Test

7.1 Two Instances with Duplicate Identifiers
7.2 Missing Link
7.2.1 Link Flavors
7.2.1.1 Missing Simple Participant
7.2.1.2 Missing Simple Formalizer
7.2.1.3 Missing Simple Reflexive Participant
7.2.1.4 Missing Simple Reflexive Formalizer
7.2.1.5 Missing Associative Associator
7.2.1.6 Missing Associative One Side
7.2.1.7 Missing Associative Other Side
7.2.1.8 Missing Subtype
7.2.1.9 Extra Subtype
7.2.1.10 Missing Supertype
7.3 Null Referential
7.3.1 Null Formalizer Referential
7.3.2 Null Reflexive Formalizer Referential
7.3.3 Null Associator Referential
7.3.3 Null Subtype Referential
7.4 Non-Null Referential
7.4.1 Invalid Non-Null Conditional Formalizer Referential
7.4.2 Invalid Non-Null Conditional Reflexive Formalizer Referential

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Code Changes

Fork/Repository: cortlandstarrett/bridgepoint   Branch: integrity2  
Fork/Repository: cortlandstarrett/mc   Branch: integrity2  
Fork/Repository: cortlandstarrett/buildmt   Branch: integrity2  

<pre>
Fork/Repository: cortlandstarrett/bridgepoint   Branch: integrity2

Fork/Repository: cortlandstarrett/mc   Branch: integrity2

Fork/Repository: cortlandstarrett/buildmt   Branch: integrity2

</pre>

### End
