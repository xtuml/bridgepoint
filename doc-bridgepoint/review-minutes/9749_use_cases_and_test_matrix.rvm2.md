---

This work is licensed under the Creative Commons CC0 License

---

# Review #2 of Use Case Document (9749_usecases.md) and review #1 of the test matrix (oal_autocomplete_matrix.txt)
### xtUML Project Review Minutes

Reviewed:  
   1. Use Case Document (hash: f0dd)   https://github.com/travislondon/bridgepoint/blob/9749_usecases/doc-bridgepoint/notes/9749_usecases/9749_usecases.md

   2. Use Case Test Matrix    https://raw.githubusercontent.com/travislondon/bptest/9750_matrix_creation/src/org.xtuml.bp.als.oal.test/matrices/non-generated/oal_autocomplete_matrix.txt
         
Present:  Cort, Levi,Bob, Travis

Link to the google doc that was used during this review (this google doc is obsolete with creation of this document in git): https://docs.google.com/document/d/1nnOwB6wyl5ygOgCQjvq9ccqM8HLmGm8rPZmRbA2hjqY/edit

<pre>


Agenda (with observations)
X-1. Use case document/text matrix flexibility. When are these documents frozen (if ever)? What is our responsibility to the customer in terms of requirements?
   1. This can be the description in Section 6 (Use Cases):
      1. We have taken a mixed and balanced approach of generating test sequences from the OOA of OOA and from grammar analysis.  This will produce reasonable albeit not exhaustive coverage.  Brute force exhaustive coverage would produce an unmanageable number of tests.
                
X-2. DOF for BNF production rule instead of "Locations"?
   1. We decided not to do this due to concerns about test counts (see statement in 1.a.i above)
X-3. I.e. how to test filtering of valid inst_ref vars and relationship numbers for relate and unrelate statements.  DOF for "in relationship with"? 
   1. Travis was thinking there this would not be in the matrix, it will be manual (or separate tests).  
      1. This Use Case document should call this out.
   2. It could be a separate matrix (This is what John T thought of)
   3. Levi says “we don’t have to test this”. (Filtering based on what has a relationship with what).
      1. Bob points out that perhaps this is not about just “not testing”, it is not supporting this feature in this “phase 1”. This is perhaps a place where we can “scale back” to assure timely delivery.
X-4. Consistency of use cases/test matrix. We need to decide how far we are going to go
   1. See statement in 1.a above
   2. We will test the grammar independent of the UI
      1. Most tests will use the proposal list, it does not use the UI
   3. UI test(s) are smaller, perhaps even manual. TRL NOTE: what will be tested for 9751 will not include UI.  Phase 2 will further test for any type of filtering.  See section 5, below.
   4. Should we specify what is NOT tested in this document?
      1. No
         1. There is an infinite amount of things that we are not (or cannot) support. It is a waste of time to try to specify all this
         2. It could be specified that anything not explicitly stated would not be supported.
X-5. Filtering by type? what about complex expressions that change type based on operation”
   1. Levi notes that JDT does this via something named “by relevance”. This is a complex issue
   2. This seems like a good category to remove from this phase
      1. This means the user will see “more than they want” right now
   3. We will just list all choice sorted just as we would any other list
      1. Exception is we will filter out return type “void”
         1. Void types are not allowed in expressions
   4. Anyplace, in the grammar, where there is an expression we will NOT filter.


X-6. Review documents carefully
   X-1. Use Case Document (hash: f0dd)
      0. TODO: Raise an issue for AutoCompletion phase2, create an analysis note, 
        start putting items in this note as we find them. The thing we call out as 
        "striking" from this document should be moved here
      X-1. Section 5 (Analysis)
         X-1. 5.2.1 - Remove “.”
         X-2. 5.2.6 - Port names are offered and interface names are not, even though interface are syntactically correct (this is best practice) 
         X-3. 5.2.9 - Call out polymorphic events
         X-4. 5.2.11.4 Make sure it is called out when a statement is nested underneath a while or for block (make sure there is a test for such)
         X-5. Possible place(s) to “simplify” phase 1
            1. 5.2.19.1 Full signature versus parameter completion
               1. Go forwarded as designed, but there was a length discussion about.
         X-1. 5.2.22 they->the
      X-2. Section 6 (Use Cases)
         X-1.  6.1.26-28 remove filtering based on “void” return type. Still filter based on existence of at least one activity
         O-2. 6.2.1 Numbering weirdness here. Please take another look
         X-3. 6.2.2.1 Strike type checking
         X-4. 6.2.2.2.1-2 Strike
         X-5. 6.2.4.1.1-2 Strike
         X-6. 6.2.5.1 Add “have at least one outbound message”
         X-7. 6.2.5.2.1.1-2 Strike
         X-8. 6.2.5.3 (sender is an option here)
         X-9. 6.2.7.1.1 should be 6.2.7.1.2
         X-10. Second 6.2.7.2 moves to 6.2.7.2.1
         X-11. 6.2.7.2.1 -> 6.2.7.2.2
         X-12. 6.2.7
         X-13.  (new) Note: after select one|many|any we will not have a proposal (for var1 or var2), but this is a possible phase 2 item.
         X-14. 6.2.7.1.1 numbering issue
         X-15. 6.2.7.3.1.1 Strike
         X-16. 6.2.8 There is a preference for this, it is NOT only for reflexive.
         X-17. 6.2.9.3 strike
         X-18. 6.2.9.4.1 “assigner” -> “class”
         X-19. 6.2,9,4.3 “,” typo
         X-20.  6.2.10 - 6.2.10.2 (formatting)
         X-21.  6.2.10 This is reference the wrong section, should be 6.2.22
         X-22. 6.2.10.2 strike
         X-23. 6.2.11.5.2 If “void” return type, nothing. If non-void, 6.2.22
         X-24. 6.2.13.2.1 Give all instance references
         X-25. 6.2.13.3.1 Strike
         X-26. 6.2.13.5 Typo add “using”
         X-27. 6.2.13.6  (formatting)
         X-28. 6.2.13.5.1 Strike parenthesized part
         X-29. 6.2.13.6 make consistent with “relate” section
         X-30. 6.2.14 Strike
         X-31. 6.2.16 Strike
         X-32. 6.2.18 Get rid of “not empty”
         X-33. 6.2.18.1 make distinction between “cardinality” and “not_empty”/”empty” -- the latter can also take inst_ref types
         X-34. 6.2.19 Strike everything after “all available parameters”
         X-35. 6.2.22.* remove type checking
         X-36. 6.2.22.9 only suggest the enumeration type name itself, not the specific enumerators
         X-37. 6.2.23 rework to match 6.2.9
         X-38. 6.2.24.* remove all type checking


   X-2. Use Case Test Matrix
      1. Perhaps change this matrix to make it clear this is testing population of proposal list
         1. This makes it clear it is NOT a UI test
      1. Note that the ordering is tested by the UI, so the unit testing of the proposal lists will not include ordering tests
      2. Call out in a comment on “Locations” that we know it is not exhaustive based on the grammar, but we chose to do that based on testability


</pre>
   
No major observations, a re-review is not required.
