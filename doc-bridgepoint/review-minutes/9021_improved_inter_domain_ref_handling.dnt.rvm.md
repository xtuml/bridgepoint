---

This work is licensed under the Creative Commons CC0 License

---

# Improve mechanism for inter-domain references - Ensure .int (interface) files are provided to MASL editor
### xtUML Project Review Minutes

Reviewed:  9021_dependency_dnt.md   (33d57729ba88efae51a70dca5116bdac9904c74a)
Present: Cort, Levi, Keith, Bob  

<pre>

--  Num Type  Who  Section  Comment
o - 01  min   XX   3        form -> from
x - 02  min   all   3.3     There was a long discussion here.  Clarify (some notes):
                            Cort: When does action language invoke a service using domain::service and NOT be a port in Domain 
                            component? When do a have name::service where I do NOT have a port of name
                            Answer: When using a utility domain.
                            
                            If you use that other domain's type or if you call the other domain's service (via a library)                            
                            There is either a port or there is a utility domain.
                            
                            (Levi)From inside a domain there are 2 ways to bridge out:
                            1. terminator - you define your own and nothing needs to be on the other side. You invoke 
                            your terminator from your domain. So, you do not care who implements the terminator.                            
                            2. calls to library domains 
                            
                            keith says sac has calls to ini::getdata(). 
                              However, note that it is not wired. It is a utility domain. These are like 
                              EEs, but they are "worse" they are like implied EEs. You can call them without seeing them. You do 
                              need the int file if you are using a type from another domain.
                              
x - 03  min   4   cds       Should we have 4.1.5: Users should be able to specify a file-system relative path.  ?     
x - 04  maj   4   bob       We must refer to the requirement(s) from the SRS here too. They are the root requirements the customer
                            signed-off on. 
                            We must refer to them and assure these requirements are here because they help 
                            satisfy those higher-level requirements. There is only 1 for this issue:
                            
                          9679-1
                          Inter-domain references shall be managed without manual intervention from the user.

x - 05  min   4.3/4.4  Levi     The contents of "depended upon folders"
x - 06  maj   6   bob     This was just a brain dump from keith to get input. It will be redone     
x - 07  min   n/a   cort    Cort brings up the idea of using the marking editor and type in a path. Somehow hand that 
                          path to xtext, or link things in the file system to use it. Consider this.
x - 08  min   n/a   bob     keith wants to share requirements with the customer ASAP to get sign-off. Bob suggests creating an 
                          analysis note and put these requirements there, then just leave this design note as-is. Then
                          share the stubbed-out analysis that just has requirements and background.
                          
                          

</pre>

major observations, re-review is required.


End
---
