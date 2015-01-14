# HOWTO Use xoal editor

1. Abstract
-----------
This note describes how to install and use the xoal editor.

2. Document References
----------------------
None

3. Install Xtext
----------------
3.1 To use the xtext editors provided you must install Xtext.  The
    instructions below will guide you through that process.   
 3.2 In your BridgePoint installation select Help > Install New Software...   
 3.3 In the Work with field choose Indigo  
 3.4 In the filter field type xtext (note there may be a slight delay at
     this point)  
 3.5 Choose the Xtext found under the modeling category  
 3.6 Click Next  
 3.7 Accept the license and click Finish     
 3.8 Restart the tool when asked   

4. Use xoal editor
------------------
4.1 Create a new file with the .xoal extension    
4.2 Begin typing the OAL language     
4.3 Use Ctrl + Space to bring up auto-completion   
4.4 When done editing you can save the contents and then copy and paste the
    OAL into an older version of the BridgePoint OAL editor   
4.5 At this point the OAL will be ready for use (in Verifier as well as the
    model compilers)   

5. Building the xoal editor
---------------------------

5.1 Configure your eGit repository by adding this location:   

https://github.com/xtuml/bridgepoint.git   

This is done in the Git Repositories view by right clicking and choosing Paste
Repository Path or URI, after copying the above location   

5.2 Click Next on the following two pages, then click Finish.   
5.3 Right click on the bridgepoint repository and choose
    Switch To > New Branch...   
5.4 In the Source ref field click the pull down menu and choose this branch:
    506_stage1_enhanced_oal_editor_2   
5.5 Right click on the bridgepoint repository and choose
    Import Projects...   
5.6 In the dialog that opens choose Next, then click finish.   
5.7 At this point you will have four new projects added to your workspace   
    doc-bridgepoint   
    org.xtuml.bp.xtext.oal   
    org.xtuml.bp.xtext.oal.tests   
    org.xtuml.bp.xtext.oal.ui   
5.8 Enable Build Automatically by right clicking the Project menu and enabling
    the check on Build Automatically   
5.8 Expand org.xtuml.bp.xtext.oal/src/org/xtuml/bp/xtext/oal/   
5.9 Right click on GenerateXoal.mwe2 and choose Run As > MWE2 Workflow   
5.10 You will get a message asking to install Antlr 3.0, type y in the Console
     view and hit enter   
5.11 At this point the plugins will have been built   

 