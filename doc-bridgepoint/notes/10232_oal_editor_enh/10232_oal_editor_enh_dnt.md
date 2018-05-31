---

This work is licensed under the Creative Commons CC0 License

---

# OAL Editor Enhancement 
### xtUML Project Design Note


### 1. Abstract

TODO 

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #xxx1](https://support.onefact.net/issues/xxx1) TODO: Add description here.  

### 3. Background

TODO    

### 4. Requirements

4.1 TODO    

### 5. Analysis

__TODO - Raw, unformatted notes follow__

- All settings appear to work just fine in text editor
- OAL editor
  - auto indent does not work after creating if() statement
  - If I change the preferences, then start a new if() block, the preference changes are honored for spaces/tabs and width
  - Once, for example, an if-block is started, changed settings do not override the formatting settings that already were
    being used in the block
  - appears that tab width is honored, but when spaces for tabs are used it always uses 4 as tab stop
  
Good read: https://www.ibm.com/developerworks/opensource/tutorials/os-ecl-commplgin2/os-ecl-commplgin2-pdf.pdf
  - auto edits
  
- We need to implement our own class OALAutoEditStrategy (implements IAutoEditStrategy)
  
- Java and C++ preferences both have Code Style > Formatter that is similar and gives lots of control over the styling.  Could
this be reused easily?

- test on all OSes

- link Text Editors pref page from activity editor pref page

- Key API
  - jface SourceViewerConfiguration
    - getTabWidth(ISourceViewer sourceViewer)
    Returns the visual width of the tab character. This implementation always returns 4.
    - getAutoEditStrategies(...)
    - getIndentPrefixesForTab(int tabWidth)
  - OALEditorConfiguration
    - If we can override getTabWidth or getAutoEditStrategies here and get the behavior we want, then maybe
      what we should do is to use our own prefs (set on Activity Editor pref page) for tab width and spaces for tabs and stop relying on the settings
      under text editor.
  - Text Editor preferences settings:
        IPreferenceStore store = EditorsUI.getPreferenceStore();
      boolean spacesForTabs = store.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
      int tabWidth = store.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
  - pref page colors and fonts link
    Link link= new Link(appearanceComposite, SWT.NONE);
    link.setText(TextEditorMessages.TextEditorPreferencePage_colorsAndFonts_link);
    link.addSelectionListener(new SelectionAdapter() {
      public void More ...widgetSelected(SelectionEvent e) {
        PreferencesUtil.createPreferenceDialogOn(getShell(), "org.eclipse.ui.preferencePages.ColorsAndFonts", null, "selectCategory:org.eclipse.ui.workbenchMisc"); //$NON-NLS-1$ //$NON-NLS-2$
      }
    });

   - Some useful pref page IDs are here: http://grepcode.com/file/repository.grepcode.com/java/eclipse.org/4.2/org.eclipse.ui/editors/3.8.0/plugin.xml?av=f

  - TODO: What should happen in the case where a line is indented with tabs, the user changes the setting from tabs to spaces in prefs,
          then goes to the editor and adds a newline?  Should we "copy" the indentation of the previous line, or "translate" 
          the indent to the new style with spaces based on the changed preference?
          
  - Error case: using tabs, not spaces.  inside an if where current indent uses spaces, add nested if, hit enter, extra 
      indent is not added
  
  - What about the MASL editor???    

### 6. Design

TODO   

### 7. Design Comments

TODO   

### 8. User Documentation

TODO   

### 9. Unit Test

TODO  

### End
