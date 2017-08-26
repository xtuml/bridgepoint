---

This work is licensed under the Creative Commons CC0 License

---

# Address offline mode issues with maven  
### xtUML Project Analysis Note

### 1. Abstract

This note describes a different approach to support offline mode.  

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9726](https://support.onefact.net/issues/9726) Provide analysis and implementation for a solution to #9647    
<a id="2.2"></a>2.2 [BridgePoint DEI #9647](https://support.onefact.net/issues/9647) Original offline mode issue    
<a id="2.3"></a>2.3 [Eclipse tycho dependency bug](https://bugs.eclipse.org/bugs/show_bug.cgi?id=461787) Bug of example with dependency issues  
<a id="2.4"></a>2.4 [Maven Documentation](https://maven.apache.org/guides/introduction/introduction-to-repositories.html) Maven documentation explaining that some plug-ins may or may not support the -offline option    

### 3. Background

Issue 9647 [[2.1]](2.2) added -o to all maven invocations.  The work was promoted but reverted as problems arose after more use.  

### 4. Requirements

4.1 Builds shall work offline after a local repository has been setup  
  NOTE: Covers all development cases, build server, command line, and UI.  
4.2 Build infinite loops shall not occur  
4.3 Incremental builds shall be quick  

### 5. Analysis

5 Allow offline  

The goal here is to allow building offline without issue for all cases.  A few issues have been noted using just the offline option with the maven command (-offline).  Most of these seem to be related to dependency issues and maven wanting to download another artifact during an incremental build.  When having a data connection these remain a problem as maven will fail with the build mentioning that downloads cannot occur while in offline mode.  Another noted issue is that during builds an infinite build loop can occur.  

5.1 Dependency issues  

The changes for the orginal issue [[2.1]](#2.2) assumed that a connection was no longer required once a maven build was done using the install goal.  If truly without a connection the build will fail early during the checks for updates.  Once offline mode was enabled, the build would work until an update was found.  These updates should be ignored but are not.  According to [[2.1]](#2.4) some plugins may not honor the offline mode setting.  There is another eclipse bug, [[2.1]](#2.3) that is claimed to be fixed in the version of tycho that we use.   

5.1.1 Use local repository as remote  

It is possible to take a good local repository and copy it to a new location locally.  Then configure maven to use the local repository as a remote repository.  This has the following benefits:  

* No external plugin may change your dependencies   
* It is still offline so dependency checks are local  
* offline mode and any issues that come with it are no longer required  

It has a few drawbacks as well:  

* The last measured repository was ~5 GB  
* It would require two locals, one as a false remote and one being the actual local  
* It would require a process for updating

The benefits here outweigh the drawbacks.  The consistency, near same speed, and more stable offline building are much better than worrying about 9 to 10 GB of required disk space for building BridgePoint.  The disk space required is not affected by the number of repository clones, workspaces, or builds one has.  As for updating the remote repository we would have to add process only in the following situations:  

* New plug-in dependency is added that was not a dependency before  
* Existing plug-in is updated externally and we want it (eclipse version update, xtext, antlr, etc.)  

Updating of the maven repositories are controlled by maintaining a timestamp for the last download.  For the build server this timestamp would always indicate that the latest maven repositories are used.      

5.1.2 Use dependency:resolve-plugins    

Research shows that the dependency:resolve-plugins option locates dependencies in a better way, than just a build.  Such research mostly included digging through stackoverflow entries.  Use of this option would occur before any build and would be used to setup the local repository once.  This would be called in the prepare_build.sh script when using the build_and_test_bp.sh script.  This is also a good place to put the timestamp checking mentioned in [[5.1]](#5.1.1).  

5.1.3 Stop using -offline  

Using the approach given earlier in this document we would simply exclude adding the -offline option.  According to maven we would always be working online.  

5.1.4 Stop using install goal for maven  

The install goal for maven does a bit more than a developer requires.  It builds the jars and installs them into the local repository.  This takes more time and disk space as it also builds the OS jars, four of them.  Once [[5.1]](#5.1.2) is in use we no longer require the install goal.  We can then start using the test goal.  This goal runs everything required for development use.  With Jenkins we will still require the install goal.  If we switch Jenkins to use the build_and_test_bp.sh script we would simply have to parameterize the build_and_test_bp.sh script.  

5.2 Infinite build loop  

The infinite build loop seems to have started appearing with the offline mode.  Analysis shows that there are two ant targets which are running at each maven build.  Another one that does not always show is causing code regeneration in bp.core.  The other two slow the build but take much less time as they do not retrigger other projects to regenerate.  It has been verified that the other two have been occuring prior to the introduction of the offline mode.  The infinite loop is caused by the third ant target which recreates the ooaofooa sql files.  Moving to the solution in section [[5.1]](#5.1.1) will prevent the infinite loop as we will not be using -offline anymore.   

5.3 Building in eclipse  

Using maven to build incrementally does not work.  It is not the right tool for the job.  On the other hand eclipse JDT was built for this.  JDT though is only java related, therefore it stands that only java builds should be used when working in eclipse.  The problem is that if any of these files change:  

* xtuml  
* sql  
* arc  
* inc  

a regeneration of code is required.  For regeneration maven must be used.  

Currently there is a build_project.sh script used to build test plug-ins.  This script shall be modified to support non-test plug-ins and all non-test plug-ins shall have their builders updated to use it.  Additionally the script shall be modified to check for any modified files of the above type.  If none have been modified it shall exit immediately.  This will result in maven only being run in eclipse if such a file was modified.  Otherwise JDT will build the java changes.  The build_and_test_bp.sh script shall be changed to use the build_project.sh script.  This way all projects are built with a single script.  The build_and_test_bp.sh still exists as it focuses on an entire build, including testing and publishing.  

5.4 Synchronization with JDT and command line  

Currently the command line build does not affect JDT.  The eclipse JDT builder must run in eclipse to get problem markers.  Even running JDT from the command line will not update the problems view or the code line numbers during debugging.  The build automatically feature shall be enabled by default again.  The user documentation shall be updated to add a mention of requiring a build all if the user has disabled build automatically.

### 6. Work Required  

6.1 Modify prepare_build.sh  
6.1.1 Call mvn dependency:resolve-plugins if the current time is less than the timestamp created below  
6.1.2 Manually create a version file under the bridgepoint repository and add the same version number in the script, this should only be changed according to [[5.1]](#5.1.1)  
6.1.3 Move ~/.m2 to ~/.m2_bridgepoint  
6.1.4 Create settings.xml file under ~/.m2/settings.xml (create the directory)  
``` xml
<settings>
    <mirrors>
        <mirror>
            <id>Local BP Repository</id>
            <name>Local BP Repository</name>
            <url>file:///~/.m2_bridgepoint</url>
            <mirrorOf>central</mirrorOf>
        </mirror>
    </mirrors>
</settings>
```   
6.1.5 If the script version does not match the repository version delete the ~/.m2_bridgepoint and ~/.m2 folders  
6.1.6 Update the version in the script  
6.2 Modify build_project.sh script  
6.2.1 Support building production projects  
6.2.2 Create a timestamp in the workspace metadata area, bridgepoint_build.txt  
6.2.3 Check for modification of any of the files described in [[5.1]](#5.3) if they have been modified since the timestamp run the maven build, otherwise exit  
6.2.4 Modify the script to allow passing multiple projects to build/test  
6.3 Change all maven calls to use the test goal rather than install, with the exception of the Jenkins calls  
6.4 Modify build_and_test_bp.sh to call build_project.sh rather than calling maven directly  

### 7. Acceptance Test

7.1  For build server/command line  
  7.1.1 Delete the ~/.m2 and ~/.m2_bridgepoint directories if they exists  
  7.1.2 Run build_and_test_bp.sh  
  7.1.3 Result is that the repositories are downloaded and copied locally  
  7.1.4 Disconnect from network  
  7.1.5 Run build_and_test_bp.sh  
  7.1.6 Result is that the repositories are locally downloaded and build speed has increased  
  7.1.7 Manually update the version file  
  7.1.8 Run build_and_test_bp.sh  
  7.1.9 Result is that the build fails, explaining you are not connected to a network  
  7.1.10 Connect to a network  
  7.1.11 Run build_and_test_bp.sh  
  7.1.12 Result is that the repositories are downloaded and copied locally  
  7.1.13 Once again disconnect from a network  
  7.1.14 Run build_and_test_bp.sh  
  7.1.15 Result is that the repositories are locally downloaded and build speed has increased  
7.2 For eclipse builds  
  7.2.1 On the command line run build_and_test_bp.sh  
  7.2.2 Start eclipse and run build all  
  7.2.3 Result is there is no maven script run  
  7.2.4 Result is there is no infinite loop  
  7.2.5 Exit Eclipse  
  7.2.6 Clear the repositories of temporary data, using git clean -fdx  
  7.2.7 Create a problem in java on the command line any file  
  7.2.8 Run build_and_test_bp.sh  
  7.2.9 Result is there is a build failure  
  7.2.10 Start eclipse  
  7.2.11 Run build all  
  7.2.12 Result the problem created is listed in the problems view  
  
### End
