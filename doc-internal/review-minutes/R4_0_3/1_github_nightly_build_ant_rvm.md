---

# Title goes here
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/internal/blob/master/doc-internal/notes/1_github_nightly_build_dnt.md
           917389a4314fbbc5fd5e9b0ceab8fa1de01d5c19  
Present: Bob,Keith,Cort

-- Num Type  Who  Section  Comment  
x- 01  min   bob  3.3      bad reference, needs to be doc-internal  
x- 02  min   CDS  7.2      Are these things checked out fresh each time even if they are already there?  

                           Yes, they are checked out.  The reason is because of supporting head and branch builds,
                           we should explain this in the document.
                           It seems a heavy hammer to wipe it out every time.
                           
                           Raise a seperate issue to add more intelligence for a speed up.  For example, 
                           maybe we can run a 
                             svn diff -cREVISION "^/branches"
                           just to see if there is a difference.
                           
x- 03  min   bob  7.2.2    Note that these were copied from <cvs>bp.core.  
                           We may need to update documentation.  
                           
x- 04  min   bob  8.2      Update the item referenced in [6] and add an item to review the HOWTO documents 
                           and move them to github and update them as needed.  
                              
No major observations, a re-review is not required.


End
---
