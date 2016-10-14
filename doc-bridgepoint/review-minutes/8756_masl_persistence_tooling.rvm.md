---

This work is licensed under the Creative Commons CC0 License

---
**NOTE: This review mins contains content for two note reviews**

# Storing action semantics as files
### xtUML Project Review Minutes

Reviewed: https://github.com/leviathan747/bridgepoint/blob/8756_masl_persistence_tooling/doc-bridgepoint/notes/8417_action_dialect_files/8417_action_dialect_files.int.md f8260f5    
Present: Bob, Keith, Levi, Cort 

<pre>
-- Num Type  Who  Section  Comment
x- 01  min   CS   5        formatting issues.  Some numbered items do not start on new line
x- 02  min   BM   5.2.1    add link to design note for description of "labeled action body"
x- 03  min   BM   7        Create a new manual test in QA manual tests for this work
</pre>
   
   
# MASL Persistence Tooling
### xtUML Project Review Minutes

Reviewed:  https://github.com/leviathan747/mc/blob/8756_masl_persistence_tooling/doc/notes/8756_masl_persistence_tooling.int.md  dcac8ef     
Present: Bob, Keith, Levi, Cort 

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   CS   3        "the tic part" > "the tic is part"
x- 02  min   CS   3        note that tics are part of OAL too
x- 03  min   KB   5        We need to make sure that xtumlmc_build's handling of tics is not adversely
  affected by the new tic conversions.  Test with MASL input data that has a tic as the
  last character.  Test doing x2m on an xtuml model that has a tic as the last character
  in a description field used by MASL.  These tests should be captured in manual tests.
x- 04  min   BM   4        Make sure all the requirements are covered by the manual tests
x- 05  min   KB   general  Our discussion led to the decision to turn off the Dialect attribute that
  was added to the schema.  We don't want to commit to this change yet.
x- 06  min   KB   6        There are two 6.2.2.3 elements
x- 07  min   KB   6.2.5.4  formatting
x- 08  min   BM   2        check all the links in the document references, some appear to be wrong
x- 09  min   CS   general  we want to persist the file names as .masl.  We need to raise an
  issue (blocker to Saab 2016) to stop persisting errors to .oal since we will
  soon be persisting OAL as .oal.  We shall have to document and fix any of our 
  .gitignore files that ignore .oal and we shall have to update the release notes
  and tell users to update their .gitignore files too.
</pre>

No major observations, a re-review is not required

End
---
