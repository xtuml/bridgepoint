---

This work is licensed under the Creative Commons CC0 License

---

# OAL Editor Enhancement 
### xtUML Project Design Note


### 1. Abstract

This note describes work performed to fix tabs and spaces handling in the OAL
editor as well as add auto-indentation functionality.   

A simple, user-requested canvas update is also addressed.   

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #10232](https://support.onefact.net/issues/10232) Headline issue.    
<a id="2.2"></a>2.2 [BridgePoint DEI #10279](https://support.onefact.net/issues/10279) Change range separator from "," to ".."    
<a id="2.3"></a>2.3 [BridgePoint SR #4995](https://support.onefact.net/issues/4995) Put support for action language auto-indent/auto-format in place in editors  
<a id="2.4"></a>2.4 [BridgePoint SR #5038](https://support.onefact.net/issues/5038) Tab-width setting "Insert spaces for tabs" ignores "Displayed tab width"-setting and always inserts four spaces regardless of setting.  	
<a id="2.5"></a>2.5 [Create a commercial-quality Eclipse IDE, Part 2: The user interface](https://www.ibm.com/developerworks/opensource/tutorials/os-ecl-commplgin2/os-ecl-commplgin2-pdf.pdf)   
<a id="2.6"></a>2.6 [List of Preference Page IDs](http://grepcode.com/file/repository.grepcode.com/java/eclipse.org/4.2/org.eclipse.ui/editors/3.8.0/plugin.xml?av=f)  
<a id="2.7"></a>2.7 [BridgePoint DEI #10345](https://support.onefact.net/issues/10345) Update unit tests to account for move to eclipse oxygen   
<a id="2.8"></a>2.8 [BridgePoint DEI #10527](https://support.onefact.net/issues/10527) Test auto-indent    
<a id="2.9"></a>2.9 [SRS_SAAB_Sortie-2](https://docs.google.com/document/d/11k8433quu1mXWicvWtbVRn0hfEvbt8bRQGxyE2GTR0s/edit#heading=h.qtczt4yvcn0) - One Fact Private document   

### 3. Background

BridgePoint's OAL editor would feel more modern if it performed some smart auto-indentation [2.1][2.3]. Current
editors automatically indent upon hitting return if the current statement opens a conditional or 
loop block.   

The OAL editor is also not good at honoring the user preferences for tab width and whether spaces or
tabs should be used for indentation [2.4].   

Currently UDTs with a specified range are showed on the canvas using the comma separator between min and 
max values (e.g. `[0,11]`).  European users read this as a North American would read a decimal number that
uses `.`.  We shall change the range separator to use `..` instead (e.g. `[0..11]`) [2.2].   
 
### 4. Requirements

The requirements shown here are copied from the SRS [2.9] for convenience of the reader.  

| Req #   | Requirement |  
|---------|----------|
| 4995-1  | The OAL editor shall automatically indent newly inserted lines at the same indentation level by duplicating the indentation of the previous line. |
| 4995-2  | When the user inserts a new line and the previous line begins with one of the following OAL statements (case insensitive), an additional level of indentation to the previous line shall be automatically inserted: `if`, `elif`, `else`, `for each`, `while` |
| 4995-3  | When preference settings are configured to use tabs the OAL editor shall render all tabs using the size of the tab width specified in the preferences.|
| 4995-4  | Changes to tab width shall not affect existing indentation where spaces were used instead of tabs. |
| 5038-1  | The OAL editor shall honor the tab width preference setting when the user performs manual indentation and tab characters are in use.  Each indent will result in a single tab of the configured width being inserted into the editor. |
| 5038-2  | The OAL editor shall honor the tab width preference setting when the user performs manual indentation and is configured to use spaces instead of tabs. Each indent will result in the configured tab width number of spaces being inserted into the editor. |
| 5038-3  | When performing automatic indentation, the OAL editor shall duplicate the indentation of the previous line. If the previous line contains a special control statement that triggers additional indentation, the additional indent will use the configured preferences for tab width and spaces or tabs. |
| 10279-1 | Change the separator character used to show range values from “,” to “..”. |


### 5. Analysis
5.1 All settings appear to work just fine in the current generic text editor 
inside eclipse.     

5.2 Existing OAL editor  
5.2.1 Auto indent does not work after creating if() statement   
5.2.2 If we change the preferences, then start a new if() block, the preference 
changes are honored for spaces/tabs and width  
5.2.3 It appears that tab width is currently honored, but when spaces for tabs 
are used it always uses 4 as tab stop
  
5.3 A helpful article is found at [2.5]. Specifically the section titled "Auto Edits"  
  
5.4 Because our OAL editor preferences will rely on the general text editor preferences
for tab width and "spaces for tabs", we should add a link on the OAL Editor preferences 
page to the Text editor preference page.  
5.4.1 This will be like the "Colors and Fonts" link on the Text Editor preference page
which is implemented as:   
```java
    Link link= new Link(appearanceComposite, SWT.NONE);
    link.setText(TextEditorMessages.TextEditorPreferencePage_colorsAndFonts_link);
    link.addSelectionListener(new SelectionAdapter() {
      public void More ...widgetSelected(SelectionEvent e) {
        PreferencesUtil.createPreferenceDialogOn(getShell(), "org.eclipse.ui.preferencePages.ColorsAndFonts", null, "selectCategory:org.eclipse.ui.workbenchMisc"); //$NON-NLS-1$ //$NON-NLS-2$
      }
    });
```
5.4.2  A helpful reference for page IDs is [2.6].   

5.5 Key API that will be used during implementation  
5.5.1 Jface `SourceViewerConfiguration` contains the following we'll override
```java
getTabWidth(ISourceViewer sourceViewer)  
getAutoEditStrategies(...)
getIndentPrefixesForTab(int tabWidth)
```  
>  NOTE: Similar to other eclipse code editors, the automatic indentation we provide
>  is only applied on lines directly following one of the keywords called out in the requirements.
>  Once we are inside one of these blocks, any line after the first follows the indentation 
>  of the previous line.  Thus, if a block already contains some lines, the existing indent is
>  reused when adding additional lines after the first.  If a new first line is created, our
>  automatic indentation is applied.  

5.5.2 Text Editor preferences settings:  
```java
IPreferenceStore store = EditorsUI.getPreferenceStore();
boolean spacesForTabs = store.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
int tabWidth = store.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
```  

### 6. Design

6.1 Implement our own class `OALAutoEditStrategy` (implements `IAutoEditStrategy`)  
6.1.1  Set the OAL editor configuration to use this new strategy class.    
6.1.2  The strategy class methods shall leverage the text editor tab preferences and 
implement functionality to determine when automatic indentation is needed and what the indent
depth should be.  
6.1.3  Our implementation uses simple pattern matching rather than the complexity and "weight" 
of a full parser.  Therefore, we do not handle complex cases such as writing an `if` statement with
body and `end if` all on a single line.   

6.2 Update `OALEditorConfiguration.java` to handle the overrides called out in 5.5.1  

6.3 Add a link to the Text Editors pref page from BridgePoint's Activity Editor preference page
at `OALEditorSyntaxPreferencePage.java`    

6.4 Change the range separator in the OOAofOOA metamodel `Range::get_name` function  

### 7. Design Comments

7.1  The move to Eclipse oxygen has made the Verifier JUnit tests unstable on the build server. They
are consistently hanging and preventing builds with test from completing.  The changes in this branch
in the `bptest` repository disable the verifier tests.  Issue 10345 [2.7] is open to address these 
problems and re-enable the unit tests.   

### 8. User Documentation

None      

### 9. Unit Test
9.1 Run the reusable manual test [2.8]    

9.2 Test range separator   
* Open the `MicrowaveOven > components > MicrowaveOven > Datatypes` package
* Add a new User Data Type named rangetest
* Use the context menu "Range" on `rangetest` to set a minimum and maximum value
* __R__ Verify the displayed element on the canvas shows the range with `..` as the
separator between the min and max values.  
 

### End
