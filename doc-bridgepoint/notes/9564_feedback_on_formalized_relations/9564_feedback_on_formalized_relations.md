---

This work is licensed under the Creative Commons CC0 License

---

# Feedback for formalized associations  
### xtUML Project Design Note

### 1. Abstract

This note describes a change that shall allow visual indication that an association is formal.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9564](https://support.onefact.net/issues/9564) Feedback on formalized relations    

### 3. Background

Currently there is only one way to determine if an association has been formalized and that requires looking at the formalizers class attributes.  

### 4. Requirements

4.1 Formalization state shall be determinable by looking at the diagram without the need of referential attributes being visible.    

### 5. Analysis

5.1 Possibilities  
5.1.1 Coloring  

We could modify the color of the association line when said association is formalized.  However, for the amount of work it does not seem worth it.  On top of that, one can manually color a line to match leaving a misguided understanding of the association.  

5.1.2 Text appending  

Another approach would be to modify the drawn connector text to include a list of attributes which formalize the association.  The end result would be something of this affect: R1 {identifier1_attr}.  

5.1.3 Dashed line  

We could modify the line to be dashed when the association is formalized.  We would have to make sure to use a different dash policy so that it is not confused with associative links.  

5.1.4 Append symbol to line  

A symbol could also be drawn just off of center and towards the formalizer class along the association line.  This could be a filled triangle pointing at the formalizer class for instance.  If this approach is taken we must consider direction of the arrow and whether or not it should be on or off the line.  

### 6. Design

Depends on direction taken.  

### 7. Design Comments


### 8. User Documentation


### 9. Unit Test


### End
