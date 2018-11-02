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

4.1 OAL editor shall properly honor the tab-width preference.   
4.1.1  When tabs are specified to be used, tabs shall follow the width specified in the preference  
4.1.2  When spaces are specified to be used, the correct number of spaces shall be inserted to match the tab width  
     
4.2 Ranges shall be displayed on the canvas with `..` instead of `,`  

4.3 OAL editor shall automatically indent the next line when the user hits enter at the end of a line that
contains an `if`, `elif`, `else`, `while`, or `for each` statement.  
4.3.1 This automatic indentation shall follow the rules in 4.1.  

### 5. Analysis
5.1 All settings appear to work just fine in text editor   

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
__NOTE: 9.1 to 9.3 is captured in reusable manual test [2.8]__  

9.1 Test Preferences Link     
* Open the BridgePoint Preferences
* Navigate to xtUML > Activity Editor
* Click on the "Text Editors" link
* __R__ Text Editors preference page opens
* Set the Displayed Tab Width to 4
* Set "Insert Spaces for Tabs" to off (unchecked)

9.2 Test auto-indent with tab   
* Create the MicrowaveOven sample model
* Navigate to `MicrowaveOven > components > MicrowaveOven > Functions`
* Add a new function named testTabs
* Enter the following OAL by hand (do not copy paste). Verify that lines
are automatically indented after creating new conditional or loop blocks  

```java
a = 1;

if ( a == 1 )
    a = 0;
    b = 2;
elif ( a == 2 )
    c = 0;
else
    b = 1;
end if;

select many doors from instances of MO_D;
for each door in doors
    a = 1;
    if ( a == 1 )
        c = 0;
    end if;
end for;

while ( a != 0 )
    a = a - 1;
end while;
```
* Use your arrow keys to navigate around the OAL and verify the indentation 
is performed with tabs and not spaces  

9.2.1  Change width and add the following lines, verify new width is honored
* Open Text Editor preferences
* Set the Displayed tab width to 2
* Create a new function named `testTabs2`
* Re-enter the OAL from 9.2 and verify the new tab width is used
* Use your arrow keys to navigate around the OAL and verify the indentation 
is performed with tabs and not spaces

9.3 Test auto-indent with spaces   
* Open Text Editor preferences
* Set the Displayed tab width to 4
* Turn on (check) the "Insert Spaces for Tabs" option
* Create a new function named `testSpaces`
* Re-enter the OAL from 9.2 and verify the new tab width is used
* Use your arrow keys to navigate around the OAL and verify the indentation 
is performed with spaces and not tabs
  
9.3.1  Change width and add the following lines, verify new width is honored
* Open Text Editor preferences
* Set the Displayed tab width to 2
* Turn on (check) the "Insert Spaces for Tabs" option
* Create a new function named `testSpaces2`
* Re-enter the OAL from 9.2 and verify the new tab width is used
* Use your arrow keys to navigate around the OAL and verify the indentation 
is performed with spaces and not tabs

9.4 Test range separator   
* Open the `MicrowaveOven > components > MicrowaveOven > Datatypes` package
* Add a new User Data Type named rangetest
* Use the context menu "Range" on `rangetest` to set a minimum and maximum value
* __R__ Verify the displayed element on the canvas shows the range with `..` as the
separator between the min and max values.  
 

### End
