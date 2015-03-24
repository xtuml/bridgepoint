---

This work is licensed under the Creative Commons CC0 License

---

# Breakpoint still around after removal
### xtUML Project Implementation Note

 

1. Abstract
-----------
The verifier, after terminate and relaunch, still stops at a breakpoint that 
was previously removed from the breakpoint view.


2. Document References
----------------------
[1] Issues 239, https://github.com/xtuml/internal/issues/239  
[2] CQ DEI dts0100722456 - Breakpoint still around after removal  


3. Background
-------------
Stated in the design note

4. Requirements
---------------
4.1 The verifier shall not stop at a breakpoint that is unchecked from the 
   breakpoint view.
   
   
5. Work Required
----------------
Detailed in the design section on the design note 


6. Implementation Comments
--------------------------
6.1 A method removeBreakPoints() is added to BPDebugTarget java class.
    This method does access the InstanceList as follows 
    ...
    
	private void removeBreakPoints(ModelRoot modelRoot) {

		InstanceList breakPointsList = modelRoot
				.getInstanceList(Breakpoint_c.class);

		synchronized (breakPointsList) {
			for (int i = 0; i < breakPointsList.size(); i++) {
				Breakpoint_c breakPoint = (Breakpoint_c) breakPointsList.get(i);
				breakPoint.Dispose();
			}
		}

	}
	....
	
6.2 During code review there was a concern for using the synchronized keyword 
    That it may cause troubles and seeking consultancy was requested 

6.3 After discussing it further with Campbell it was agreed it's ok to use the synchronized 
    keyword because  the instance list is shared storage. 
    Everywhere else we use synchronized with it.  
    He doesn't think it will cause any trouble.
    We use it everywhere for instance lists to prevent concurrent modification exceptions.
    
    
6.4 GPS model is added to the models tests for testing this issue     
    

7. Unit Test
------------
7.1 Run Verifier test suite.  


8. Code Changes
---------------
Branch name: < rdmn7526_git239_UpdateBranch_a >

<pre>
org.xtuml.bp.debug.ui [bridgepoint rdmn7526_git239_UpdateBranch_a]/src/org/
    xtuml/bp/debug/ui/model/BPDebugTarget.java

org.xtuml.bp.debug.ui.test [bridgepoint rdmn7526_git239_UpdateBranch_a]/src/
    VerifierTestSuite.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7526_git239_UpdateBranch_a]/src/
    org/xtuml/bp/debug/test/VerifierUDTAsUDTInitializationTests.java
org.xtuml.bp.debug.ui.test [bridgepoint rdmn7526_git239_UpdateBranch_a]/src/
    org/xtuml/bp/debug/test/breakpoint/BreakpointRemovalTest.java


</pre>


Branch name: < rdmn7526_git239_UpdateBranch  >

<pre>
 > GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/
    .externalToolBuilders/Model Compiler.launch
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.launches/
    GPS Watch.launch
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.launches/
    GPS Watch UI.launch
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.launches/
    GPS Watch With Initializer.launch
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.settings/
    org.eclipse.cdt.managedbuilder.core.prefs
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/gen/class.mark
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/gen/
    datatype.mark
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/gen/domain.mark
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/gen/system.mark
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/gen/
    UI_GuiBridge_bridge.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    GuiBridge.java
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    SetData.java
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    SetTime.java
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    SignalData.java
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    WatchGui.java
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/0_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/0_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/1_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/1_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/2_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/2_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/3_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/3_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/4_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/4_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/5_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/5_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/6_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/6_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/7_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/7_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/8_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/8_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/9_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/9_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/app_icon.gif
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/display_hover.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/display_pressed.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/dots_large.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/dots_small.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/lap_reset_hover.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/lap_reset_pressed.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/light_hover.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/light_pressed.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/mode_hover.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/mode_pressed.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/start_stop_hover.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/start_stop_pressed.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/javasrc/lib/
    img/watch.png
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/GPS Watch.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Activities/Activities.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Activities/Exercise/Exercise.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Analysis/Analysis.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Analysis/Start Stop Reset/Start Stop Reset.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/HeartRateMonitorInterfaces/HeartRateMonitorInterfaces.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/HeartRateMonitorInterfaces/HeartRateProvider/
    HeartRateProvider.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Library.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/HeartRateMonitor/HeartRateMonitor.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/HeartRateMonitor/HeartRateMonitor/HeartRateMonitor.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/HeartRateMonitor/HeartRateMonitor/HeartRateMonitor/
    HeartRateMonitor.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/HeartRateMonitor/HeartRateMonitor/HeartRateMonitor/
    ClassStateMachine/ClassStateMachine.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Location/Location.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Location/Location/Location.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Location/Location/GPS/GPS.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Location/Location/GPS/ClassStateMachine/
    ClassStateMachine.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/Tracking.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/Display/Display.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/Display/ClassStateMachine/
    ClassStateMachine.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/HeartRateSample/HeartRateSample.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/LapMarker/LapMarker.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/TrackLog/TrackLog.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/TrackPoint/TrackPoint.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/WorkoutTimer/WorkoutTimer.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/Tracking/Tracking/WorkoutTimer/InstanceStateMachine/
    InstanceStateMachine.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/UI.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/External Entities/External Entities.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/Functions/Functions.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/TestCases/TestCases.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/UI/UI.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/UI/TestCase/TestCase.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/UI/TestCase/InstanceStateMachine/
    InstanceStateMachine.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/UI/UI/UI.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Library/UI/UI/UI/ClassStateMachine/ClassStateMachine.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/LocationDataTypes/LocationDataTypes.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/LocationInterfaces/LocationInterfaces.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/LocationInterfaces/LocationProvider/LocationProvider.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/LocationInterfaces/LocationUtil/LocationUtil.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Sequences/Sequences.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Sequences/Backlight/Backlight.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Sequences/Start Stop Reset Formalized/
    Start Stop Reset Formalized.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Sequences/Stopwatch/Stopwatch.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Shared EEs/Shared EEs.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/System/System.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/UIDataTypes/UIDataTypes.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/UIInterfaces/UIInterfaces.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/UIInterfaces/UI/UI.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Use Cases/Use Cases.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/models/
    GPS Watch/Use Cases/Record Info/Record Info.xtuml
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    GPSWatch_sys_main.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    GPSWatch_sys_types.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    HeartRateMonitor_classes.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    HeartRateMonitor_HeartRateMonitor_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    HeartRateMonitor_HeartRateMonitor_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    HeartRateMonitor.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    HeartRateMonitor.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Location_classes.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Location_GPS_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Location_GPS_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/Location.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/Location.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    LOG_bridge.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    LOG_bridge.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    sys_user_co.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    sys_user_co.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/sys_xtuml.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/sys_xtuml.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    TIM_bridge.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    TIM_bridge.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_classes.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_Display_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_Display_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_HeartRateSample_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_HeartRateSample_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_LapMarker_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_LapMarker_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_TrackLog_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_TrackLog_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_TrackPoint_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_TrackPoint_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_WorkoutTimer_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    Tracking_WorkoutTimer_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/Tracking.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/Tracking.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_classes.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_GuiBridge_bridge.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_GuiBridge_bridge.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_TestCase_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_TestCase_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_UI_class.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/
    UI_UI_class.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/UI.c
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/src/UI.h
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.classpath
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.cproject
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.gitignore
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/.project
> GPS Watch [models origin/rdmn7526_git239_UpdateBranch 497c04a]/README


</pre>



End
---

