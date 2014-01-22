---

Copyright 2013-2014 Mentor Graphics Corporation.  All Rights Reserved.

---

# Remove Deprecated bp.mc.mc3020 Plug-in
### xtUML Project Implementation Note


1. Abstract
-----------
This note describes the work and testing done to remove an unused plug-in.  

2. Document References
----------------------
[1] Issues 136, https://github.com/xtuml/internal/issues/136    
[2] CQ DEI dts0101019774  

3. Background
--------------
The ```com.mentor.nucleus.bp.mc.mc3020``` plug-in has not been used for quite a
few releases now.  We have carried it simply to provide backwards compatibility
for customer projects that may not yet have been updated to one of the new
model-compiler plug-ins.  We have decided that we no longer need to carry this
plug-in forward and thus we will remove it.  

4. Requirements
---------------
4.1  The bp.mc.mc3020 plug-in is no longer shipped with the product.  

5. Work Required
---------------- 
5.1  Remove the plug-ins: ```com.mentor.nucleus.bp.mc.mc3020, com.mentor.nucleus.bp.mc.mc3020.test, com.mentor.nucleus.bp.mc.mc3020.pkg, com.mentor.nucleus.bp.mc.mc3020.pkg-feature```  
5.2  Remove all references in other plug-ins & projects to the removed plug-ins  

6. Implementation Comments
--------------------------
6.1  While performing the clean-up, the author found that the ```bp.io.mdl.test```
  plug-in still contained the test model projects: Library, Watch, and watchGenerics.
  The Watch project is old and no longer used.  The Library and watchGenerics 
  projects are now used from the test model repository and they no longer need
  to exist in the io.mdl.test plug-in.  They are removed with this work.    

7. Unit Test
------------
7.1  Verify all JUnit tests pass    
7.2  Perform a branch build on the build server and verify it passes    

8. Code Changes
---------------
Branch name: 136_del_mc3020  

<pre>

com.mentor.nucleus.bp.core.test/src/com/mentor/
    nucleus/bp/core/test/TigerNatureWorkspaceSetupTestGenerics.java
com.mentor.nucleus.bp.core.test/plugin.xml

com.mentor.nucleus.bp.docgen/src/com/mentor/nucleus/
    bp/docgen/generator/Generator.java
com.mentor.nucleus.bp.docgen/generate.xml
com.mentor.nucleus.bp.docgen/plugin.xml

com.mentor.nucleus.bp.io.mdl.test/models/
    InstanceReferenceTestMatrixModel/.externalToolBuilders/
    com.mentor.nucleus.bp.mc.xmiexport.XMIExportBuilder.launch
com.mentor.nucleus.bp.io.mdl.test/models/
    InstanceReferenceTestMatrixModel/.externalToolBuilders/
    MC-3020 Model Compiler.launch
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    .cproject
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    .project
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    .externalToolBuilders/
    com.mentor.nucleus.bp.mc.xmiexport.XMIExportBuilder.launch
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    .externalToolBuilders/Model Compiler.launch
com.mentor.nucleus.bp.io.mdl.test/models/Library/gen/
    code_generation/Library.sql
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library/Library.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library/component/component.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library/component/Unnamed Package/Unnamed Package.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library/component/Unnamed Package/Unnamed Class/
    Unnamed Class.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library/component/Unnamed Package/Unnamed Class/
    InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Library/InternalComponent/InternalComponent.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Pasted/Pasted.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Library/
    models/Library/Referenced/Referenced.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    .project
com.mentor.nucleus.bp.io.mdl.test/models/Watch/.xpj
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    .externalToolBuilders/
    com.mentor.nucleus.bp.mc.xmiexport.XMIExportBuilder.launch
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    .externalToolBuilders/MC-3020 Model Compiler.launch
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    bridge.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    datatype.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    registry.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    sys_user_co.c
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    sys_user_co.h
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    system.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    tracking_class.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    tracking_domain.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    tracking_event.mark
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/Makefile
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/Makefile.in
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/Makefile.user
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/Makefile.xtumlmc
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/model_temp_108542235_2104
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking.sql
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/reports/html/a.gif/a.gif
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/reports/html/p_a.gif/p_a.gif
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/reports/html/p.gif/p.gif
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/reports/html/r_p.gif/r_p.gif
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/reports/html/v_p.gif/v_p.gif
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/Makefile
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/color/bridge.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/color/datatype.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/color/domain.rep
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/color/registry.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/color/sys_functions.arc
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/color/system.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/schema/sql/sys_root.sql
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/translate/pt_transInit.txt
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/translate/pt_transTable.csv
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/user/include/sys_user_co.h
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/user/source/Makefile
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/system/user/source/sys_user_co.c
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/____file.txt
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/Makefile
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/color/dom_functions.arc
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/color/domain.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/color/event.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/color/object.clr
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/gen/source/Makefile.depends
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/schema/sql/tracking_config.sql
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/schema/sql/tracking.sql
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/translate/pt_transInit.txt
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/translate/pt_transTable.csv
com.mentor.nucleus.bp.io.mdl.test/models/Watch/gen/
    code_generation/tracking/user/source/Makefile
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/Watch.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/Datatypes/Datatypes.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/System Library.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/data recording/data recording.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/data recording/data xfer/data xfer.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/data recording/peripherals/peripherals.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/data recording/workout/workout.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_Library/GPS_Library.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_Library/GPS_IP/GPS_IP.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/GPS_watch.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/antenna/antenna.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/charging system/charging system.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/comm/comm.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/tracking.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    Data Aquisition.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/clock/
    clock.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    current_location/current_location.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    heart rate sample/heart rate sample.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/lap/lap.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    lap reset button/lap reset button.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    lap reset button/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/track log/
    track log.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/track log/
    InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/trackpoint/
    trackpoint.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    workout timer/workout timer.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Data Aquisition/
    workout timer/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/External Entities/
    External Entities.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/
    Specification.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/
    current settings/current settings.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/distance/
    distance.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/duration/
    duration.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/pace/pace.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/pulse/
    pulse.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/repeat step/
    repeat step.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/step/step.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/step goal/
    step goal.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/
    termination criterion/termination criterion.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/Workout Plan/
    Workout Plan.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Specification/workout step/
    workout step.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/test.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/GPS simulator/
    GPS simulator.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/GPS simulator/
    InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/lap button depress/
    lap button depress.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/lap button depress/
    InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/scenario1/
    scenario1.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/test/scenario1/
    InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Utility/Utility.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/Workout.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/counter/counter.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/distance notifier/
    distance notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/manual notifier/
    manual notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/notifier/
    notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/time notifier/
    time notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/Workout Exercise/
    Workout Exercise.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/tracking/Workout/Workout Exercise/
    InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/UI/UI.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS_watch/USB/USB.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS Interfaces/GPS Interfaces.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS Interfaces/GPS_control/GPS_control.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS Interfaces/NMEA_serial/NMEA_serial.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/Watch/
    models/Watch/System Library/GPS Interfaces/signal/signal.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/.project
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/.xpj
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/.externalToolBuilders/
    com.mentor.nucleus.bp.mc.xmiexport.XMIExportBuilder.launch
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/.externalToolBuilders/MC-3020 Model Compiler.launch
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/watchGenerics.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/Datatypes/Datatypes.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/System Library.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/data recording/
    data recording.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/data recording/data xfer/
    data xfer.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/data recording/
    peripherals/peripherals.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/data recording/workout/
    workout.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_Library/
    GPS_Library.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_Library/GPS_IP/
    GPS_IP.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/GPS_watch.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/antenna/
    antenna.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/
    charging system/charging system.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/comm/comm.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    tracking.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/Data Aquisition.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/clock/clock.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/current_location/current_location.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/heart rate sample/heart rate sample.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/lap/lap.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/lap reset button/lap reset button.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/lap reset button/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/track log/track log.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/track log/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/trackpoint/trackpoint.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/workout timer/workout timer.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Data Aquisition/workout timer/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    External Entities/External Entities.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/Specification.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/current settings/current settings.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/distance/distance.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/duration/duration.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/pace/pace.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/pulse/pulse.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/repeat step/repeat step.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/step/step.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/step goal/step goal.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/termination criterion/termination criterion.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/Workout Plan/Workout Plan.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Specification/workout step/workout step.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    test.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    GPS simulator/GPS simulator.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    GPS simulator/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    lap button depress/lap button depress.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    lap button depress/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    scenario1/scenario1.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/test/
    scenario1/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Utility/Utility.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/Workout.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/counter/counter.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/distance notifier/distance notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/manual notifier/manual notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/notifier/notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/time notifier/time notifier.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/Workout Exercise/Workout Exercise.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/tracking/
    Workout/Workout Exercise/InstanceStateMachine/InstanceStateMachine.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/UI/UI.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS_watch/USB/USB.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS Interfaces/
    GPS Interfaces.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS Interfaces/
    GPS_control/GPS_control.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS Interfaces/
    NMEA_serial/NMEA_serial.xtuml
com.mentor.nucleus.bp.io.mdl.test/models/
    watchGenerics/models/watchGenerics/System Library/GPS Interfaces/signal/
    signal.xtuml

com.mentor.nucleus.bp.mc/generate.xml

com.mentor.nucleus.bp.mc.c.binary/generate.xml

com.mentor.nucleus.bp.mc.c.source/generate.xml

com.mentor.nucleus.bp.mc.cpp.source/generate.xml

com.mentor.nucleus.bp.mc.mcpaas/generate.xml

com.mentor.nucleus.bp.mc.systemc.source/generate.xml

com.mentor.nucleus.bp.mc.template/templates/
    model_compiler/generate.xml
com.mentor.nucleus.bp.mc.template/generate.xml

com.mentor.nucleus.bp.mc.vhdl.source/generate.xml

com.mentor.nucleus.bp.pkg-feature/feature.xml

com.mentor.nucleus.bp.utilities/META-INF/MANIFEST.MF
com.mentor.nucleus.bp.utilities/src/com/mentor/
    nucleus/bp/utilities/ui/BPCDTConversionAction.java
com.mentor.nucleus.bp.utilities/src/com/mentor/
    nucleus/bp/utilities/ui/VerifyPreBuilderPresentAction.java
com.mentor.nucleus.bp.utilities/generate.xml
com.mentor.nucleus.bp.utilities/plugin.xml

com.mentor.nucleus.bp.welcome/models/avpace/.cproject
com.mentor.nucleus.bp.welcome/models/avpace/.project
com.mentor.nucleus.bp.welcome/models/avpace/.xpj
com.mentor.nucleus.bp.welcome/models/avpace/
    .externalToolBuilders/
    com.mentor.nucleus.bp.mc.xmiexport.XMIExportBuilder.launch
com.mentor.nucleus.bp.welcome/models/avpace/
    .externalToolBuilders/com.mentor.nucleus.builder.CodeLabBuilder.launch
com.mentor.nucleus.bp.welcome/models/avpace/
    .externalToolBuilders/MC-3020 Model Compiler.launch
com.mentor.nucleus.bp.welcome/models/avpace/
    .externalToolBuilders/Model Compiler.launch
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_system.sql
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/avpace.sql
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/bridge.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/class.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/datatype.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/domain.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/event.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/registry.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/sys_functions.arc
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/system.mark
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/Makefile
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/object.lst
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_events.c
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_events.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_factory.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_main.c
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_sets.c
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_sets.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_thread.c
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_thread.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_trace.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_types.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_user_co.c
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/sys_user_co.h
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/TIM_bridge.c
com.mentor.nucleus.bp.welcome/models/avpace/gen/
    code_generation/_ch/TIM_bridge.h
com.mentor.nucleus.bp.welcome/models/GPS Watch/
    .externalToolBuilders/Model Compiler.launch
com.mentor.nucleus.bp.welcome/src/com/mentor/nucleus/
    bp/welcome/gettingstarted/SampleProjectGettingStartedAction.java
com.mentor.nucleus.bp.welcome/src/com/mentor/nucleus/
    bp/welcome/gettingstarted/ShowCheatSheetWindowAction.java
com.mentor.nucleus.bp.welcome/generate.xml
com.mentor.nucleus.bp.welcome/plugin.xml

doc-internal/process/
    HOWTO-create-new-development-workspace.md
doc-internal/process/
    HOWTO-update-the-BP-copyright.txt

Installer_MIP_MIMIC/post_install_script.sh

utilities/build/configure_external_dependencies.sh
utilities/build/create_release_functions.sh


</pre>

End
---

