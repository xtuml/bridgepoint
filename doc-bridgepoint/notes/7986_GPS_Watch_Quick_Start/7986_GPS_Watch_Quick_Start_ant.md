---

This work is licensed under the Creative Commons CC0 License

---

# GPS Watch Quick Start Change Analysis
### xtUML Project Analysis Note


1. Abstract
-----------
Currently three GPS Watch models reside in the xtuml repositories. One is in the
xtuml/bridgepoint repository and two are in the xtuml/models repository. The
goal is to reduce this to one model in the xtuml/models repository and provide a
way to launch this model from the BridgePoint Welcome Quick Start screen.

2. Document References
----------------------
[1] [BridgePoint DEI #1](https://support.onefact.net/redmine/issues/7986)  
[2] [BridgePoint DEI #2](https://support.onefact.net/redmine/issues/7927) 

3. Background
-------------
3.1 BridgePoint is often run in secure environments that are cut off from the 
internet, so we have long had a policy that we include all the necessary bits
instead of requiring them to be retrieved from the net.

4. Requirements
---------------
4.1 There will be one GPS Watch model and it will live in xtuml/models
4.2 The GPS Watch model will include Goals.  
4.3 Item 3  

5. Analysis
-----------
5.1 bridgepoint repository GPS Watch compared to models repository applications
    GPS Watch model
5.1.1 models model has additions for Goals
5.1.2 models model has non-Goal updates
5.1.3 bridgepoint model Tracking/Display class state machine is an instence
      state machine in models model.
5.1.4 bridgepoint model Library/UI Functions is ButtonFunctions models model.
5.1.5 new model elements in models model: HeartRateTypes, TargetTesting,
      Achievement, Goal, GoalSpec, WorkoutSession, RequirementsClarification,
      TrackingDataTypes
5.1.6 new gen sources in models model: MATH_bridge.c, UI_GuiBridge_bridge.redo_c
5.1.7 new java sources in models model: MATH.java, SetIndicator.java, Unit.java,
      IActionsAndDataFromProvider.java, IActionsAndDataToProvider.java
5.1.8 new img files: blank.png, down.png, flat.png, up.png
5.1.9 models model has Tracking GoalTest_1 marked as initialization function.
5.2 models repository application GPS Watch compared to models repository test
    GPS Watch
    test model doesn't differe much from bridgepoint model, so comparisons in
    5.1 apply.
5.3 bridgepoint repository GPS Watch compared to models repository test GPS 
    Watch
5.3.1 test model doesn't have Linux support
5.3.2 test model not using abbreviations for port names
5.3.3 HeartRateMonitor/ClassStateMachine appears to have significant
      differences, but an inspection of the model shows this isn't true.
5.4 Conclusion from the above comparisons is that the models repository
    application model of GPS Watch can be used without needing any additional
    elements from the other two models.

6. Work Required
----------------
6.1 Ensure GPS Watch is ready to run without building. i.e, source, executables
and library files are available. 
6.2 Capture the models/applications/gps/GPS Watch for use within a stand-alone
BridgePoint installation.
6.3 Add a mechanism to launch the GPS Watch model from the Quick Start screen.
6.4 Ensure the version of GPS Watch is aligned with the build version of
BridgePoint.

7. Acceptance Test
------------------
7.1 GPS Watch project gets loaded from Quick Start.
7.2 GPS Watch is launched according to README without building.
7.3 GPS Watch shows no compatibility errors with BridgePoint.

End
---

