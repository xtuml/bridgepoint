---

This work is licensed under the Creative Commons CC0 License

---

# Title goes here
### xtUML Project Review Minutes

Reviewed:  doc-bridgepoint/notes/8636_papyrus-xtuml_initial_contribution_int.md
ECdbs04

Present:  Cort,Keith,bob,jason

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   cds  3      create a follow on issue for the work that remains (parent task). 
                         Created:  https://support.onefact.net/issues/8926
                           We have done a lot of work, but are
                           unsure what to do next. Section 3 calls out what was submitted but much was not.
                           We must deal with what was not submitted.
_- 02  min   cds  5.1      No action taken on headers, raise issue (child)
                           --Note: during review we decided no action is needed. This was after long 
                           review of the implementation comments.
                           
x- 03  min   cds  5.2      Provide some history about why the old licenses are included (Mentor history)
x- 04  min   skb  5.3      This was moved to a different repo, add the link to where we put it.
_- 05  min   bob  5.4      This item already calls out that this is not exhaustive, but note that 
                           this list is mcooaofooa and all these will almost certainly also be in ooaofooa, 
                           so if mcooaofooa is editied ooaofooa will also need to be modified.
                           
                           Note: No action needed. The line that caused this comment was removed from ooaofooa.
                           
x- 06  min   cds  4        Declaration (raise issue under new parent task):
                           Remove all Mentor Graphics copyrights.
                           
                           We will remove all Mentor Graphics copyrights anywhere they exist whenever we see them from this
                           point forward.
                           Created:  https://support.onefact.net/issues/8927
                           
x- 07  min   jr  5.7      they did not respond with a list, we (jason)grepped and found them.
_- 08  min   cds  5.9      Note that the words "One.*Fact" should probably not exist in the source code anywhere.
_- 09  min   bob  6.1.2    Raise a child issue from the new parent. Cort has a stretagy for this. We do not feel every 
                           file should have a header.
_- 10  min   cds  6.2.2   What packages will be include, and what is the justifaction for what is and is not included?

                          Cort: In Papyrus-xtuml we will not distribute any of this stuff.
                                Our BP Pro is what will contain this stuff.
                           
                           Discussion (undecided):
                             Keith belives we can not include ANTRL and MCs.
                             Generator is not listed here (it needs to be listed here).
                             MASL binaries
                           
                           Conclusion: Mingw and docbook is not part of it.
                           
                           Requirements:
                           1. participation in papyrus community
                           2. governance by Eclipse

                           Proposals 
                           1. submit bp.core and bp.core only
                            Recognize you can not build it without a model compiler
                            -Keith calls out the JRE is an excempt prereq.
                           2. xtext masl editor (and that is all)
                            Full independant Eclipse plugin
                            
                            -PERhaps no one really cares about the product being under Eclipsefoundation governance. We 
                          are not being paid for this. We can do enough to get a very small piece throught the process. If it has value
                          someone can do it or pay One FAct to do it.
                                                        
                           3.Push the generated product
                           Perhaps not even with models.
                           -Runs risk of making people unhappy in a way similar to the editor
                          4. Push the product as is but don't deal with building it at all
                          This had the most discussion. Perhaps just bp.core, perhaps more. However, if tey require a header
                          on every file it is potenitally a problem.
                          5. Just make another submission with what has been done and a couple small things to complete this 
                          promotion and resubmit.
                    
                        Discussion:
                          -Because of the licensing problems and dependencies we are a long way from being able to build on
                          eclipse.org.
                          -Key question: Do project on eclipse have to be buildable on eclipse.org. If not, we just take
                          build out of the equation. We link to the build instructions.
                          -Do we turn over the whole git organization, or just a plugin?
                          
                        Decision:
                          We will do #5
x- 11  min   skb  general      Add a link to xtuml.org/download from papyrus-xtuml on eclipse.org make sure that download works.
                           This lets us know a new user can download something useful.
                           NOTE:  After the meeting Cort discovered any links added must be to an Eclipse Foundation property.

</pre>
   
No major observations, a re-review is not required.
(or... Major observations were recorded, a re-review is required.)


End
---
