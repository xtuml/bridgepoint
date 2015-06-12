# Move BridgePoint build away from special configuration and tools
### xtUML Project Review Minutes

Reviewed:  https://github.com/travislondon/bridgepoint/blob/master/doc-bridgepoint/notes/36_Update_eclipse_base/36_Update_eclipse_base.ant.md     3fa5ae3   
Present:  Travis, Keith, Bob   

<pre>

-- Num Type  Who  Section  Comment
x- 01  min   bob  2        add short descriptions
x- 02  min   bob  3        note that we use the EMT version of 3.7.2
x- 03  min   SKB  4.3      modify requirement to make sure the new eclipse base includes the new egit
x- 04  min   bob  4.1      strike
x- 05  min   bob  4        add requirement that says, the design shall call out exactly what package of eclipse we'll use and say it "must allow users to develop eclipse plugins and model with BridgePoint"
x- 06  min   bob  5        Make a new point ahead of "building" that is "install and run".  Note that "install and run" is very distict of being able to build.
x- 07  min   Bob  5.1      The first step is to choose the Eclipse base to use
x- 08  min   Bob  5.1      In the new "Install and run", explain why we are using the dropins folder right now rather than the plugins/ folder
x- 09  min   Bob  5.3      Call out what we were doing with DefaultClassLoader being pulled in.  Note any place where eclipse internal APIs are used.
x- 10  min   bob  5.3      Remove the "and author" and reference the documentation to consider
x- 11  min   Bob  5.4      Look at how eclipse projects handle launch configs.  See if that helps us here.
x- 12  min   SKB  5.4      We should be able to get away from the os-specific launch configs and go back to the "use all plugins in workspace and environment".  SKB thinks there is an issue for this.
TRL: This was made a requirement for this issue.
x- 13  min   Bob  5.4.2    If launch groups are gone, we will not recreate the old work.  We will look at wha eclipse does and use that as a guide
x- 14  min   Bob  5.4      Create an issue for new work to create doc "HOWTO run unit tests"
x- 15  min   Bob  general  change 4.x to 4.4 throughout and create a reference to the Luna website
x- 16  min   TRL  5.5.2    change to 5.5.1.1
x- 17  min   Bob  6        Start this section with "install and run"
x- 18  min   Bob  6        Add references to anything that is called out to be installed.  That way someone else can follow the task list and do the work and not have to find stuff again themselves
x- 19  min   Bob  6.1.1    install the eclipse base, then reference the one installed later
x- 20  min   Bob  6        Specify which BridgePoint is being used
x- 21  min   TL   6        Add a section early on to copy in the unit test plugins and run them
x- 22  min   Bob  6        Clarify the difference in what is working (or attempted) in translation and compilation
x- 23  min   TL   6.2      6.2 and 6.3 are out of order
x- 24  min   bob  7        Categorize the acceptance test section 
x- 25  min   Bob  4        Add requirement "After this work is done, the BridgePoint plugins shall still work on eclipse 3.7.2"

Re-review comments:

_- 1  min   TRL  5.3       Bad reference number, should be [6] locate original documentation
                          TRL: Waiting on access to old documents
x- 2  min   Bob  General   All fixes shall be applied in the eclipse 3.7 version
x- 3  min   TRL  General   Install fixed 3.7 version changes into a 4.4 version
x- 4  min   TRL  General   Run smoke test with new installation
x- 5  min   KB   6.1       Numbering issue 6.1.2.3
x- 6  min   KB   6.2       Numbering issue 6.2.5
x- 7  min   KB   6.2.6     Clarify what this section is describing (converting plug-ins to 3.x format)
x- 8  min   KB   6.2.8     Change to mention we update the build scripts to support a 4.4 build
x- 9  min   Bob  6         Add a section to describe addressing 4.4 issues in 3.7 and testing both versions

</pre>


Major observations, a re-review is required.


End
---
