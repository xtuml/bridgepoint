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

### 6. Implementation Comments

### 7. Unit Test

7.1 All existing unit tests must pass.

7.2 Open class diagrams in OOA of OOA and observe that text is not cut off.

### 8. User Documentation

No change to user documentation required.

### 9. Code Changes

<pre>
Fork/Repository: cortlandstarrett
Branch: 9746_boxes

</pre>

### End

