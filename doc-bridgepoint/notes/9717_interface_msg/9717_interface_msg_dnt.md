---

This work is licensed under the Creative Commons CC0 License

---

# Interface Management Errors
### xtUML Project Design Note


### 1. Abstract

This note describes an issue experience by a user and a change to the 
tool to help the modeler avoid the problem.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9717](https://support.onefact.net/issues/9717) Headline issue   
<a id="2.2"></a>2.2 [BridgePoint SR #9708](https://support.onefact.net/issues/9708) Headline SR    

### 3. Background

A user reported problems related to performing operations on interfaces and having those changes lead
to problems with the underlying MASL data being out of synch with the modeled application.  A typical 
error dialog for this scenario is shown in Figure 1.

![Error Dialog](activities_mismatch_dialog.png)  
Figure 1.  

Once the model is in this state it is often quite challenging to recover.  BridgePoint does not allow
you to forge ahead with one set of data or the other.  Generally the modeler is forced to go outside 
the tool to edit files by hand or to revert back to a prior version in revision control and redo work.

### 4. Requirements

4.1 BridgePoint shall provide assistance to help the user avoid mismatched activities errors  

### 5. Analysis

5.1 Item 1  


### 6. Design

6.1 Item 1  

![Synchronization Needed Dialog](synch_warning_dialog.png)  


### 7. Design Comments

7.1 During the development, a typo in a dialog was noticed and fixed.  The changed file
  is ```TransactionManager.java``` in ```org.xtuml.bp.core```.   

### 8. User Documentation

None.  

### 9. Unit Test

9.1 Test 1  
* Create example model GPS Watch
* Create another model "Foo", enable IPRs on this project
* Inside Foo
  * Create package P
  * Create component C
  * Add required interface to C
  * Formalize the interface to GPS Watch > LocationInterfaces > LocationUtil
  * Add a new operation "testop" to LocationUtil
  * __R__ Synchronization Needed dialog is shown
  * Choose Cancel
  * __R__ Foo is decorated with warning symbols
  * __R__ Foo's component C > Port 1 > LocationUtil does not show testop
  * Run "Synchronize with library" on Foo
  * __R__ Warning symbols on Foo go away
  * __R__ Foo's component C > Port 1 > LocationUtil shows testop
  * Delete operation "testop" from LocationUtil definition in GPS Watch
  * __R__ Synchronization Needed dialog is shown
  * Choose OK
  * __R__ Foo is not decorated with warning symbols
  * __R__ Foo's component C > Port 1 > LocationUtil > testop is removed


### End
