---

This work is licensed under the Creative Commons CC0 License

---

# Title goes here
### xtUML Project Analysis Note


Note: Each section has a description that states the purpose of that section.
Delete these section descriptions before checking in your note.  Delete this
note as well.

### 1. Abstract

In this section, give a summary of the design that this note aims to
describe.

### 2. Document References

In this section, list all the documents that the reader may need to refer to.
Give the full path to reference a file.  
<a id="2.1"></a>2.1 [BridgePoint DEI #xxx1](https://support.onefact.net/issues/xxx1) TODO: Add description here.  
<a id="2.2"></a>2.2 [BridgePoint DEI #xxx2](https://support.onefact.net/issues/xxx2) TODO: Add description here.  
<a id="2.3"></a>2.3 [Eclipse tycho dependency bug](https://bugs.eclipse.org/bugs/show_bug.cgi?id=461787) Bug of example with dependency issues  

### 3. Background

(TODO)

### 4. Requirements

4.1 Builds shall work offline after a local repository has been setup  
4.2 Build infinite loops shall not occur  

### 5. Analysis

5. Allow offline  

The goal here is to allow building without issue for both the command line and the UI.  A few issues have been noted using just the offline option with the maven command (-offline).  Most of these seem to be related to dependency issues and maven wanting to download another artifact during an incremental build.  When having a data connection these remain a problem as maven will fail with the build mentioning that downloads cannot occur while in offline mode.  Another noted issue is that during builds an infinite build loop can occur.  

5.1 Dependency issues  

The changes for the orginal issue [[2]](2.2) assumed that a connection was no longer required once a maven build was done using the install goal.  If truly without a connection the build will fail early during the checks for updates.  Once offline mode was enabled, the build would work until an update was found.  These updates should be ignored but are not.  According to [[2.1]](2.4) some plugins may not honor the offline mode setting.  There is another eclipse bug, [[2.1](2.3) that is claimed to be fixed in the version of tycho that we use.   

5.1.1 Use local repository as remote  

It is possible to take a good local repository and copy it to a new location locally.  Then configure maven to use the local repository as a remote repository.  This has the following benefits:  

* No external plugin may change your dependencies   
* It is still offline so dependency checks are local  
* offline mode and any issues that come with it are no longer required  

It has a few drawbacks as well:  

* The last measured repository was ~5 GB  
* It would require two locals, one faking as the remote and one being the actual local  
* It would require a process for updating

The benefits here outweigh the drawbacks.  The consistency, near same speed, and more stable offline building are much better than worrying about 9 to 10 GB of required disk space for building BridgePoint.  The disk space required is not effected by the number of repository clones, workspaces, or builds one has.  As for updating the remote repository we would have to add process only in the following situations:  

* New plug-in dependency is added that was not a dependency before  
* Existing plug-in is updated externally and we want it (eclipse version update, xtext, antlr, etc.)  

Update would occur automatically by placing a timestamp file somewhere in the bridgepoint repository.  Then some script would always check this timestamp and delete the local and local-remote repository caches.  This would trigger the build to download all of the latest plug-ins from maven.    

5.1.2 Use dependency:resolve-plugins    

Research shows that he dependency:resolve-plugins option locates dependencies in a better way, than just a build.  Such research mostly included digging through stackoverflow entries.  Use of this option would occur before any build and would just be used to setup the local repository once.  This would be called in the prepare_build.sh script when using the build_and_test_bp.sh script.  This is also a good place to put the timestamp checking mentioned in [[5.1]](5.1.1).  

5.1.3 Stop using -offline  

Using the approach given earlier in this document we would simply exclude adding the -offline option.  According to maven we would always be working online.  

5.1.4 Stop using install goal for maven  

The install goal for maven does a bit more than a developer requires.  It builds the jars and installs them into the local repository.  This takes more time and disk space as it also builds the OS jars (4).  Once [[5.1]](5.1.2) is in use we no longer require the install goal.  We can then switch back to using the test goal.  This goal runs everything required with the exception of Jenkins.  In Jenkins we would still use the install goal.  If we switch Jenkins to using the build_and_test_bp.sh script we would simply have to parameterize it.  

5.2 Infinite build loop  

The infinite build loop seems to have started appearing with the offline mode.  Analysis shows that there are three ant targets which are running at each maven build.  The problematic one is causing code regeneration in bp.core.  The other two slow the build but take much less time as they do not retrigger other projects to regenerate.  It has been verified that the other two have been occuring prior to the introduction of the offline mode.  Moving to the solution in section [[5.1]](5.1.1) will prevent the infinite loop as we will not be using -offline anymore.   

### 6. Work Required

(Todo)
- Run mvn -o test (no install, includes tests unless configured otherwise. also install performs more work than required) for build_and_test_bp.sh
- For individual builds using build_and_test_bp.sh <project_name> extend the build_and_test_bp.sh script to support it just as individual testing, modifying so that not only tests are included:

build_and_test_bp.sh core
build_and_test_bp.sh core.test

- Modify the build_and_test_bp.sh script to allow multiple parameters including:
build project1 project2
test project1 project2 (test builds as well)
clean project1 project2
- In the case a dependency across repository lines is changed, when a test is run modify build_and_test_bp.sh so that bp.pkg-feature is built first.  The current build_project.sh script in the same folder does this for within eclipse.
- Modify all eclipse builders bp and test to use build_and_test_bp.sh

### 7. Acceptance Test

In this section, list the tests that need to be performed in order to
verify that all the requirements are satisfied. Here is an example reference to the Document References section [[2.1]](#2.1)

7.1 Item 1  
7.1.1 Example sub-item
* Example List Element

7.2 Item 2  
7.2.1 Example sub-item
* Example List Element

### End
