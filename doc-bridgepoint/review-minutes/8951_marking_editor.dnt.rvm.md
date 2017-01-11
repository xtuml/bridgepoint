---

This work is licensed under the Creative Commons CC0 License

---

# MArking Editor
### xtUML Project Review Minutes

Reviewed:  https://github.com/keithbrown/bridgepoint/blob/8951_marking_editor/doc-bridgepoint/notes/8951_marking_editor/8951_marking_editor_dnt.md
24b4bd75cdbcd1b6e7e1fa0694a18566ce7569f3

Present:  Keith, Cort, Levi, Bob

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   bob  5         typo 5.2.1.1
x- 02  min   cds  3      In mc3020 there is a set of feature not associated with any element at all, this would not address that
                         Such a mark would be done against System Model. This is a documentation issue.  
x- 03  min   john  4    Validate the feature.mark, example: validate against ooaofooa
                          Action: not required for January, required for Feb, so add a follow on issue for 6.2. We could fullfil this
                                  in the x2m flow.
x- 04  min   bob  4    Allow comments in feature.mark
                          -perhaps include it as a line in the feature.mark 
                          -(3rd element if there, or full line preceeding each element?)
                          Action: desired, not requried, add to section 7 enhancements
    Captured in #8984
x- 05  min   john  5.2.3 John describes this as a combination of both.
                          -Perhaps allow the user to select the model elements in ME and have cme that does not 
                          need the filter. It uses the selection from Model Explorer. 
                          -This allows multi-select of the model elements.
                          Action: document this. This goes into the possible post Feb release as enhancement (section 7?)
    Captured in #8984
x- 06  min   john  6.2.1   Where does the user go to get this list of model elements. We need to document this.
x- 07  min   bob  6.2.2/6.3.3      Is the space after the comma important? Check this (add to test), it should not be.
x- 08  min   skb  6.2.2      "<ooa element type name>"
x- 09  min   all  6.3.2/6.6.2      should the first element, project name, be removed for portablity?
                          -If the user exports and imports this markng file to another model name this format would
                          potentially not work because the editor uses exact matching.
                          -Action: omit the project name from this
x- 10  min   bob  6.3.3    A feature name can not contain a comma. this should be documented
    Added to #8983
x- 11  min   levi  6.3.3      assure that the value field can contain commas
x- 12  min   levi  6.4      why isn't the model of marking part of ooa, included in the persistence hierarchy? Levi points out
                            that if this was done, it could be used by xtm/m2x.
                            -During implementation it seemed the cost of doing this was high for the work needing to be done.
                            -we could put this model in mcooa (probably faster in short run). We would like it modeled. It is a
                            pragmatic question. 
                            Action: this can be recorded in the x2m/m2x follow on issues to decide there. 
x- 13  min   cds  6.6.1/6.8      During the convert/import flow we do NOT want to create features.mark. For this version we will
                            assume it is there, created by the user. Capture this decision in 8980.
x- 14  min   bob  7      raise issues for these possible future enhancements
x- 15  min   cds  document      The fact that marks are not updated for rename/move should be documented. 
x- 16  min   john  document      What happens when a mark is not applied? We should document this too. For example this may happen
                          if model elements are renamed/moved.
x- 17  min   bob  7?      Add hover text to help the user with the new dialog. One example is perhaps it can call out where the
                          feature names are defined.
x- 18  min   bob  8.1      Raise an issue so we can track this
  Raised #8983
x- 19  min   bob  9      Capture unit test in redmine, mark it with MASL tag and refer to it so we maintain it there.
  Raised #8985
x- 20  min   cds  9      Add test for delete use case
x- 21  min   bob  9      Add test for rename use case

</pre>
   
No major observations, a re-review is not required.


End
---
