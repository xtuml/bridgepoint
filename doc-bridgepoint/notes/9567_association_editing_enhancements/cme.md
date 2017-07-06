5.1.2.1 Another CME Approach

After further brainstorming and discussion, another option using a context
menu was identified.  This approach would provide a CME with access only
to the cardinality (combined Multiplicity and Conditionality).  Role
phrases and formalization are not addressed (not touched).  

See picture below.  The flow would be as follows:  
- Right click anywhere on association.
- 'Cardinality' appears in conext menu.
- Click on 'Cardinality', and sub-menu appears with the names of the
participating classes (`dog` and `owner` in the example).  In the case
of a reflexive, the role phrases would appear with the class names.
- Click on the class name for the desired "end" of the association,
and a sub-sub-menu appears listing the 4 possibilities for relationship
cardinality on that end.

![Cardinality Context Menu](9567_cardinality_CME_dog_owner.jpg)

Not shown, but clicking on the link association (dotted line) would
display the Cardinality menu item, too.  However, when this Cardinality
menu is clicked only ` ` (blank, meaning One) or `{*}` (Many).
Another possibility for the linked association Cardinality sub-menu
is that only the opposite of the existing setting is shown.  Namely,
`{*}` is shown when starting with multiplicity One; ` ` is shown when
the current multiplicity is Many.
