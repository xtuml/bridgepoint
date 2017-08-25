---

This work is licensed under the Creative Commons CC0 License

---

# Foramlized Association Text Boxes are Too Small  
### xtUML Project Implementation Note

### 1. Abstract

Recently, a feature was added to BridgePoint to highlight formalized
associations with bold lines and bold text.  But now the text for these
relationships does not fit in its bounding boxes, and characters get
"cut off".

### 2. Document References

<a id="2.1"></a>2.1 [9746](https://support.onefact.net/issues/9746) expand boxes around rel phrases and other boldened items  
<a id="2.2"></a>2.2 [9564](https://support.onefact.net/issues/9564) Feedback on formalized relations  

### 3. Background

In [[2.2]](#2.2) work was done to highlight formalized associations.
Now we need to expand the boxes that contain the relationship text.

### 4. Requirements

4.1 Expand text boxes around the text of formalized associations to
account for bold fonts.

### 5. Work Required
<pre>
[8/22/17, 11:46:52 AM] Bob Mulvey: WRT, the 9564 bug with “feedback on formalized relations” (captured as 9746 - expand boxes on rel phrases)
[8/22/17, 11:47:00 AM] Bob Mulvey: [8/22/17, 11:24:27 AM] Bob Mulvey: rmulvey/9746_boxes (i just used Cort’s branch as a base too)
[8/22/17, 11:33:52 AM] Bob Mulvey: I don’t see it yet?
[8/22/17, 11:35:08 AM] Travis London: Just created it
[8/22/17, 11:40:08 AM] Bob Mulvey: Cool, that looks good. It makes it clear that this was just missed in the issue implementation and not a bug in gef. That is good. It makes sense.
[8/22/17, 11:41:24 AM] Travis London: We use a centralized font, which we cannot change for this.  So we just modify it at draw time.  Text extents were not considered.
[8/22/17, 11:47:31 AM] Bob Mulvey: I do not understand the difference in implementation for text vs line. Is there a difference?
[8/22/17, 12:22:48 PM] Travis London: Lines and text have separate graphical edit parts.  The line edit part uses the PolylineConnection figure, which simply draws line points.  The text edit part uses a FlowPage figure, which allows for word wrapping.  It also draws text vs a simple poly line.
[8/22/17, 12:24:07 PM] Travis London: As far as fonts go, in GEF each figure can have its own font.  The default implementation is to have a null font for children and walk the hierarchy until a font is found.  We have a centralized font to allow our preferences to account for all elements/diagrams.
[8/22/17, 12:43:08 PM] Bob Mulvey: Your change here updates FloatingTextLocator::getMinimumWidth() which is where the textbox size is determined. In this function, this change modifies the font to bold (if formalized) to account for the additional size required for bold text. DiagramEditPart.java::propertyChange() is updated to assure that when SHOW_FORMALIZATIONS a refresh of these parts occurs to assure it is drawn with the bold font. Is that a good summary?
[8/22/17, 12:51:41 PM] Travis London: Yes
</pre>

### 6. Implementation Comments

### 7. Unit Test

7.1 All existing unit tests must pass.

7.2 Open class diagrams in OOA of OOA and observe that text is not cut off.

### 8. User Documentation

No change to user documentation required.

### 9. Code Changes

<pre>
Fork/Repository: rmulvey
Branch: 9746_boxes

doc-bridgepoint/notes/9746_boxes_int.md
src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/layout/FloatingTextLocator.java
src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/parts/DiagramEditPart.java
src/org.xtuml.bp.ui.graphics/src/org/xtuml/bp/ui/graphics/parts/TextEditPart.java

</pre>

### End

