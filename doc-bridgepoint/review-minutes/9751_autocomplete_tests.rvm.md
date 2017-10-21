---

This work is licensed under the Creative Commons CC0 License

---

# Review implementation note for auto complete tests
### xtUML Project Review Minutes

Reviewed:  https://github.com/travislondon/bridgepoint/blob/9751_autocomplete_tests/doc-bridgepoint/notes/9751/9751_auto_complete_tests.md  
8e1b26e  
Present:  Travis, Bob, Levi, Keith

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   bob  9750      Update https://github.com/xtuml/bptest/pull/53 for 9750 to include an implemenation note and reissue the pull. This introduces the matrix but no tests yet.
x- 02  min   skb  4.1      sugesstions
x- 03  min   skb  6.1      add reference to the test model
x- 04  min   skb  6.2      "In test implementation these are lines 26, 52 and 135."  There are more than 3, plus calling out line numbers is not helpful to the reader (lines will change). Everything is being tested under the "if block", not for or while. This should be described here. Call this out and raise follow-on issues as needed.
x- 05  min   skb  6.2      "This triggers the parser to run and create the necessary Proposal_c instances." There was confusing about this. It is invoked through the content assist. Describe this.
x- 06  maj   bob  6,2,2      The test run durations need to be called out. How we move forward with this test will require this information
x- 07  min   bob  5       For each of these 5 items include a short description to assist the reader. If the description can be found in the matrix tell the read and give them a link.
x- 08  min   bob  7      use numbers not bullets to help us talk about the specific items
x- 10  min   bob  7.1      describe what this means
x- 11  min   bob  6.1.1    Add a description that helps the reader understand this OAL. Travis described this in the meeting,
                           but that descrition is needed for the reader.
x- 12  min   bob  7      Use can raise follow-on issues as needed for 7.1 to 7.3 and descriptions for each here 
x- 13  maj   bob  ??      Define a mechanism that allows this test work to be promoted but has a way to leave these tests   disabled until the implentation is promoted. 

</pre>

Major observations were recorded, a re-review is required.

-----
Re-review

Reviewed:  https://github.com/travislondon/bridgepoint/blob/9751_autocomplete_tests/doc-bridgepoint/notes/9751/9751_auto_complete_tests.md  8adcc4c    
Present:  Travis, Bob, Levi, Keith

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   KB   general  Just a general style comment that when writing notes and making references we enclose them in square brackets.  So for example:

“A new test model has been created, see reference 2.4.” > “A new test model [2.4] has been created.”

“Each degree of freedom listed below is described fully in the analysis note from reference 2.3.” > “Each degree of freedom listed below is described fully in the analysis note [2.3].”

x- 02  min   KB   6.2     1) the follow on issue should be put in section 2 and referenced as [2.5] here.
2) In the third paragraph, there is text about “these line numbers” that should be removed.

x- 03  min   KB   7       7 (point 3) - imcomplete > incomplete

x- 04  min   KB   7       “master cannot have failing tests add.” > “master cannot have failing tests added.”

_- 05  min   KB   7       the times for the test suite are not showing up in the rendered markdown

x- 06  min   KB   5       Can you link the actual document in section 5.  That would fulfill review comment 7 better than simply a link to the issue.

x- 07  min   KB   7       I don’t see follow on issue links as called out by review item 12

x- 08  min   KB   9       the reference says 2.1 but links to 2.3

x- 09  min   bob   7.1     Reword the last sentence of the paragraph in section 7 to make it clear this is the path being taken. Make this "7.1 Promotion of testing - Leaving tests disabled"

</pre>


End
---
