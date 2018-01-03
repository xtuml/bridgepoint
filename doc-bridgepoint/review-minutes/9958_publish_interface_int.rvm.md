---

This work is licensed under the Creative Commons CC0 License

---

# Simplify interface creation for MASL domains
### xtUML Project Review Minutes

Reviewed:  9958_publish_interface.int.md  (e2761e737d223e3c65b42ce0d29ce507484adc7d)
Present: Levi, Keith, Bob  

<pre>

--  Num Type  Who  Section  Comment
_ - 01  min   KB   8        Keith notes that capitalization matters in the documentation for hiding CMEs  
_ - 02  min   KB   8        Keith asks Bob to update the documentation for "Sync references" and "Sync with library". Bob will add a note to implementation commments.  
_ - 03  min   LS   code     Remove the change to `generate.xml`. The "cvs_java_files" are files that are not to be cleaned (non-generated files).
_ - 04  min   BM   code     Something that gets updated is parameter and return type dimensions (call out in note)  
_ - 05  min   LS   code     Missing work to update return dimensions in the routine  
_ - 06  min   LS   code     Updating the "Dimensions" attribute might not be enough. You may have to invoke the operation "resizeDimensions"  
_ - 07  min   LS   code     The pull request looks really weird; there are a lot of extraneous commits. Bob is going to try to rebase with master to see what happens  

</pre>
   
No major observations, a re-review is not required.


End
---
