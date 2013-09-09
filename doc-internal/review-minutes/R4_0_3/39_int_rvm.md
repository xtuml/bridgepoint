---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Reflexive Association have no clear connection between association end and attribute
### xtUML Project Review Minutes

Reviewed: https://github.com/xtuml/internal/blob/master/doc-internal/notes/dts0100835691/dts0100835691dnt.md
          
Pull Request: none
          
Present: Nehad, Bob, Travis

-- Num Type  Who  Section  Comment

_- 01  min   bob  Title         Make Association plural
_- 02  min   bob  Abstract      Change second sentence in second para to: There is no posisibility
                                to connect each attribute to the specific desired association end.
_- 03  min   bob  Background    Change Textboxes to Text boxes
_- 04  min   bob  Requirements  Change attribute each -> attribute in each
_- 05  min   bob  5.1           prefixe -> prefix
_- 06  min   bob  5.1.4         cosistent -> consistent
_- 07  min   bob  6.1.3         Remove causing a crash and add a 6.1.4 section describing bug and fix
_- 08  min   bob  tests         Formating issues, either surround with <pre> </pre> or add double spaces
_- 09  min   bob  tests         flooowing -> following
_- 10  min   bob  tests         Note last test is already tested and point to existing test
_- 11  min   TRL  code review   Remove //Nehad comment
_- 12  min   bob  code review   Add null check and log an error if null is found line 233 of page.inc
_- 13  min   TRL  code review   Remove space in string test page.inc line 264
_- 14  min   bob  code review   Investigate whether or not we want to short circuit on null or an empty
                                string for v_${field.Instance_Handle}
_- 15  min   cort general       "is associated with" is not so good, because we are working on an actual
                                 association Prefix [  ] goes with identifier [ ] of the class XYZ for
                                 the [  ] side.

No major observations, a re-review is not required.


End
---
