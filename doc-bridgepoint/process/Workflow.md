# Workflow for BridgePoint Development

1. Abstract
-----------
This note describes the workflow the xtUML development team uses for BridgePoint.  It covers issue state and 
change management for the git repositories.

2. References
----------------------
[1] [BridgePoint Issue Tracker] (https://support.onefact.net)
[2] [xtUML github organization] (https://github.com/xtuml)
[3] [Developer's Getting Started Guide] (https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/Developer%20Getting%20Started%20Guide.md)

3. Issue Workflow
-------------
The BridgePoint Issue Tracker [1] is hosted by One Fact.

* issues status should be changed to “In progress” when you start working on it
* When you issue the pull request, the status need to be changed to “Resolved” to indicate that the issue 
  is resolved and waiting to be promoted, but not yet in master
* The person that performs the final review/promotion will close the issue when they promote it

4. Changeset Workflow
-------------
The BridgePoint source code and documentation repositories are hosted on github under the xtuml organization.

In the developer’s getting started guide [3] you will see that you must create a fork.  Your work will now 
always be done in your own fork. We still suggest you use branches within your fork, so that you can easily 
have multiple projects going on, as you did when we working in branches against master. However, the branches 
are now in your fork.  We recommend the branch be named "&lt;issue_num&gt;_&lt;short description&gt;" (for 
example: 7630_build_updates). 

* You pick an issue, or are assigned one. 
* make sure your fork is up to date with master
* create a new branch 
* Write the appropriate development note and investigate
* Review the note with us
* complete the work in your fork.  During development, commit early and often.
  * When a commit message is made in git we use the following format: "job #&lt;issue_num&gt; - &lt;change description&gt;"
  There is a cron job that looks for the “#&lt;issue_num&gt;” in a message and, when found, sends the comment to the 
    specified issue number in the issue tracking system. This serves as a means of requirements tracing. The issue in 
    the issue tracking system ends-up holding links to every individual change that was made (and the comment 
    associated with the change).
  * note that the cron job only runs against the xtuml master repositories, not forks. This means, you will not see 
  all your comments go into the issue until your fork’s branch is promoted.
* review again as needed (depending on issue size)
* run tests
* when ready for promotion you will create a pull request on your fork
* Someone with permission to service that pull request will review and promote


