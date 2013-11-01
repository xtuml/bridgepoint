---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# The avpace model needs to be updated 
### xtUML Project Implementation Note

1. Abstract
-----------
During final release testing of bp4.1.0 we found that the avpace model, which 
is used by the BP cheetsheets, needed to be updated.

2. Document References
----------------------
[1] Issues 1, https://github.com/xtuml/doc/issues/99  

3. Background
-------------
Between BP 3.7.0 and BP 4.0 a change was made to the BridgePoint persistence 
format.  This change introduced a check in model update to assure models being
loaded are at the proper persistence level.  If they are not, it asks the 
user if they want to update.  If the users selects yes, then the update occurs, 
and if the user says no, then BridgePoint does not load the model.

When this change was made all internal models were updated.   The avpace model
was accidentally left out.

4. Requirements
---------------
4.1 Update the avpace mode to the current BP persistence format.

5. Work Required
----------------
5.1. Import avpace from its location under the bp.welcome plugin, and when
promoted to update the model, allow the update to occur.  Check the updated 
model back into its location under bp.welcome.

6. Implementation Comments
--------------------------
None

7. Unit Test
------------
7.1 Assure the avpace model is updated
* Run the BridgePoint cheet sheet named "Heart Pacer Case Study" to the point
   it loads the avpace test model
* Result - The model is loaded and no update message is displayed.

8. Code Changes
---------------
Branch name: 99_update_avpace

com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/avpace.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/analysis/analysis.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/analysis/Increased Activity activity/
    Increased Activity activity.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/analysis/Increased Activity comm/Increased Activity comm.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/analysis/Increased Activity sd/Increased Activity sd.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/Datatypes/Datatypes.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/interfaces/interfaces.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/interfaces/host/host.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/interfaces/monitor/monitor.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/interfaces/synchronization/synchronization.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/library.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/External Entities/External Entities.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/functions/functions.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart/heart.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart/heart/heart.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart/heart/ClassStateMachine/ClassStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart/heart/InstanceStateMachine/
    InstanceStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart/sinus node/sinus node.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/heart/heart/sinus node/InstanceStateMachine/
    InstanceStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor/hostmonitor.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor/Host Monitor/Host Monitor.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor/Host Monitor/ClassStateMachine/
    ClassStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor/Host Monitor/InstanceStateMachine/
    InstanceStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor/Respiratory Monitor/
    Respiratory Monitor.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/hostmonitor/hostmonitor/Temperature Monitor/
    Temperature Monitor.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacemaker/pacemaker.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacemaker/External Entities/External Entities.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacemaker/pacemaker/pacemaker.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacemaker/pacemaker/pacer/pacer.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacemaker/pacemaker/pacer/ClassStateMachine/
    ClassStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacemaker/pacemaker/pacer/InstanceStateMachine/
    InstanceStateMachine.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/library/pacer/pacer.xtuml
com.mentor.nucleus.bp.welcome [internal 99_update_avpace]/models/avpace/models/
    avpace/system/system.xtuml



End
---

