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
3. Workflow
-------------
The BridgePoint Issue Tracker [1] is hosted by One Fact.  The BridgePoint source code and documentation repositories are hosted on github under the xtuml organization [2].

The Developer’s Getting Started Guide [3] explains how to set up a development environment and pull the BridgePoint source code.  It explains how developers use git forks to create their development environment. Each developer's work is be done in their own fork. The xtUML team strongly recommends you use branches within forks.  We recommend the branch be named ```<issue_num>_<short description>``` (for example: ```7630_build_updates```).  This adds valuable information that is visible to other developers on the repository's network graph.  

Once your development environment is configured, the workflow is:
* Issue is created.  Email notifications of the issue creation are automatically sent by the system. (Issue state is __New__)
* A manager looks at the new issue and asks for clarifications if necessary (Manager changes issue state from __New &gt; Acknowledged__)
* Developer (you) starts working on an issue. (Developer changes issue state from __Acknowledged &gt; In progress__) 
* You makes sure your fork is up to date with xtuml's repository master.  Merge the latest xtuml repository master branch into your fork's master.
* You create a new branch using the naming scheme described above
* Investigate and write the appropriate analysis and/or design notes
* Review the note with us
* Complete the work in your fork.  During development, commit early and often.
  * When a commit message is made in git we use the following format: ```job #<issue_num> - <change description>```.  There is a cron job that looks for the ```#<issue_num>``` in a message and, when found, sends the comment to the  specified issue number in the issue tracking system. This serves as a means of requirements tracing. The issue in the issue tracking system ends-up holding links to every individual change that was made (and the comment associated with the change).
  * Note that the cron job only runs against the xtuml master repositories, not forks. This means, you will not see all your comments go into the issue until your fork’s branch is promoted.
* Review again as needed (depending on issue size)
* Run tests
* When ready for promotion you will create a pull request to merge your branch into the xtuml master branch. (Developer changes issue state from __In progress &gt; Resolved__)
* A manager with permission to service that pull request will review and promote the changes. (Manager changes issue state from __Resolved &gt; Closed__)
  * We do not delete branches after the promotion is complete.

