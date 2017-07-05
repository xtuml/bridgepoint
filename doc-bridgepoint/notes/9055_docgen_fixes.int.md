---

This work is licensed under the Creative Commons CC0 License

---

# Docgen is failing 
### xtUML Project Implementation Note


### 1. Abstract

This note describes the changes made to resolve problems with Document Generation.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9055](https://support.onefact.net/issues/9055) Headline issue    

### 3. Background

Document generation is a feature of BridgePoint that processes a model and creates and HTML 
document from the model description data and screen captures of the canvases.  This process
relies on a custom ```docgen``` model compiler, eclipse-based BridgePoint tooling, and a common 
document processing tool known as xsltproc.   

BridgePoint ships with xsltproc binaries for Windows and Linux.  We found that the version shipped
for Linux works for Ubuntu 14 but not Ubuntu 16.  Also, BridgePoint recently introduced MacOS 
support and Document Generation does not work on Mac because it can't find xsltproc (which is
included in Mac distributions).  

### 4. Requirements

4.1 Document Generation shall work on Windows, Linux, and Mac  

### 5. Work Required

5.2 Item 2  

### 6. Implementation Comments

6.2 Item 2  

### 7. Unit Test

7.2 Example sub-item

### 8. User Documentation

Describe the end user documentation that was added for this change. 

### 9. Code Changes

Fork/Repository: < enter your fork and repo name name >
Branch: < enter your branch name here >

<pre>

 Put the file list here 

</pre>

### End

