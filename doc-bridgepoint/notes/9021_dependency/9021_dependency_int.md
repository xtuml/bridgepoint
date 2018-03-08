---

This work is licensed under the Creative Commons CC0 License

---

# Improve mechanism for inter-domain references - Ensure .int (interface) files are provided to MASL editor  
### xtUML Project Implementation Note

### 1. Abstract

This note describes the implementation of a new dependency-management feature for
MASL models.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9021](https://support.onefact.net/issues/9021) Headline issue    
<a id="2.2"></a>2.2 [BridgePoint DEI #9679](https://support.onefact.net/issues/9679) Headline SR    
<a id="2.3"></a>2.3 [Design note for DEI #9021](9021_dependency.md)      
<a id="2.4"></a>2.4 [BridgePoint DEI #10123](https://support.onefact.net/issues/10123) Update Preferences help documentation    
<a id="2.5"></a>2.5 [BridgePoint DEI #10128](https://support.onefact.net/issues/10128) Documentation issue for tables that want to display | operator  

### 3. Background

See 2.3  

### 4. Requirements

See 2.3   

### 5. Work Required

5.1  Added new page "Dependencies" to the project preferences wizard.  This dialog
allows for per-project dependency information to be stored in `<project>/.dependencies`  
5.1.1  The page may be disabled by setting the `-Dbridgepoint.Dependencies=disabled` in 
`bridgepoint.ini`  

5.2  Add new class `DepenencyData` to `bp.core` that provides a static function-based
API for reading and writing the dependency file.  

5.3  Performed some general cleanup in the project dependency classes.  Removed unused
imports, fixed typos, etc.  

5.4  Removed unnecessary `.project` file in the Command Line Interface documentation folder.  

5.5  Added `MaslProjectDependencyProvider` class to handle the files and folders found in
the dependency information as elements that should be added to the MASL validation index
in support of the validation process.   

5.6  Update `MaslWorkspaceProjectState` to use the new dependency provider.  

5.7  Add a new README to the `masl.ui` plug-in that describes the special steps 
BridgePoint developers must take when making modifications to xtend files that reference
data from `bp.core`  


### 6. Implementation Comments

None.  

### 7. Unit Test

See 2.3  

### 8. User Documentation

8.1  Updated the MASL conversion guide with information about using the dependency
manager in the project preferences.  

8.2  Completely re-wrote the preferences help documentation.  This resolves [2.4].  

8.3  Updated the Expressions help doc to fix problem with pipe operator inside tables. 
This resolves [2.5].  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint   
Branch: 9021_dependency

<pre>


</pre>

### End

