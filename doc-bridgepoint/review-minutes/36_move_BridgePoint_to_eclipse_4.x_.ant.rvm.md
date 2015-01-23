---

This work is licensed under the Creative Commons CC0 License

---

# Move BridgePoint to eclipse 4.x
### xtUML Project Review Minutes

Reviewed:  https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/36_Update_eclipse_base/36_Update_eclipse_base.ant.md
    22b0f09
Present:  Cort,Keith,Bob,Campbell

<pre>

-- Num Type  Who  Section  Comment
_- 01  min   bob  5.1.1      accept -> accepted
_- 02  min   bob  5.2      reference the documentation for the original implementation
_- 03  min   bob  5.2      What does the original documentation say?  
_- 04  min   bob  5.4.2    What about an earlier eclipse version (thus earlier egit)  
                           When did it start failing?
_- 05  min   bob  5.4      There is a numbering issue, it is not currently clear that most 
                           of these are egit related
_- 06  maj   bob  6        need to call out the work
_- 07  min   CMC  5.2      Campbell explained, in his memory, why this class loader was
                           created.  
                           Why is there a BP class loader?
							-class loaders are hierarchical, so one is chained to another
							-there were things in BP that would not be recognized in 
							  normal loader
							-we are looking for things in the users workspace that are in 
							he user's workspace.
							-bpclassloader finds them and loads them
							-the reason it needed to involve the eclipse java loader is 
							it needed to locate the workspace it is running in and locate 
							the user's data.
_- 08  maj   bob  all      It appears the general summary here is that we tried the latest 
                           and greatest version of Eclipse and had some trouble.  
                           Instead of digging-in, fall back to prior versions of eclipse,
                           find what we could update to and "walk forward" to find the
                           failures points in eclipse releases.  How many releases have 
                           there been?  Perhaps enough that we can "cut it half", for example
                           if there were 10 releases pick the 5th, if it works pick the
                           7th and try again, if it fails, pick the 3rd and try again.
                           It should be pretty quick to work through the release like this
                           to find the major failure points.      
                           Additionally, there really only seem to be 2 problems here.
                           1) BridgePoint class loader stuff
                           2) egit
                           #2 seems very unlikely to be an induced problem with egit.
                           It certainly isn't anything to do with Bridgepoint. In fact,
                           take BP out of the equation by using raw eclipse.  These items
                           seems almost certainly to be configuration issues.

</pre>
   
Major observations were recorded, a re-review is required.


End
---
