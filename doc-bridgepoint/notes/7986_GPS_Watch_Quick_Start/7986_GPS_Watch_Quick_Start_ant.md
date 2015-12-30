---

This work is licensed under the Creative Commons CC0 License

---

# GPS Watch Quick Start Change Analysis
### xtUML Project Analysis Note


### 1. Abstract
-----------
Currently three GPS Watch models reside in the xtuml repositories. One is in the
xtuml/bridgepoint repository and two are in the xtuml/models repository. The
goal is to reduce this to one model in the xtuml/models repository and provide a
way to launch this model from the BridgePoint Welcome Quick Start screen.

## 2. Document References
----------------------
[1] [BridgePoint DEI #7986](https://support.onefact.net/redmine/issues/7986)  
[2] [BridgePoint DEI #7927](https://support.onefact.net/redmine/issues/7927) 

### 3. Background
-------------
1. BridgePoint is often run in secure environments that are cut off from the internet, so we have long had a policy that we include all the necessary bits instead of requiring them to be retrieved from the net.

### 4. Requirements
---------------
1. There will be one GPS Watch model and it will be managed in the xtuml/models repository.
2. The GPS Watch model will include Goals by default, but a version without goals can be included with special releases of BridgePoint.  
3. The GPS Watch model will be shipped with BridgePoint and importable from the Welcome Quick Start screen. 

### 5. Analysis
-----------
### bridgepoint repository GPS Watch compared to models repository applications GPS Watch model
1. models model has additions for Goals
2. models model has non-Goal updates
3. bridgepoint model Tracking/Display class state machine is an instence
   state machine in models model.
4. bridgepoint model Library/UI Functions is ButtonFunctions models model.
5. new model elements in models model: HeartRateTypes, TargetTesting,
   Achievement, Goal, GoalSpec, WorkoutSession, RequirementsClarification,
   TrackingDataTypes
6. new gen sources in models model: MATH_bridge.c, UI_GuiBridge_bridge.redo_c
7. new java sources in models model: MATH.java, SetIndicator.java, Unit.java,
   IActionsAndDataFromProvider.java, IActionsAndDataToProvider.java
8. new img files: blank.png, down.png, flat.png, up.png
9. models model has Tracking GoalTest_1 marked as initialization function.

### models repository application GPS Watch compared to models repository test GPS Watch
1. test model doesn't differe much from bridgepoint model, so comparisons in 5.1 apply.

### bridgepoint repository GPS Watch compared to models repository test GPS Watch
1. test model doesn't have Linux support
2. test model not using abbreviations for port names
3. HeartRateMonitor/ClassStateMachine appears to have significant differences, but an inspection of the model shows this isn't true.

### Conclusion
1. From the above comparisons, the models repository application model of GPS Watch can be used without needing any additional elements from the other two models.

### 6. Work Required
----------------
1. Ensure GPS Watch is ready to run without building. i.e, source, executables and library files are available. 
2. Capture the models/applications/gps/GPS Watch for use within a stand-alone BridgePoint installation.
3. Add a mechanism to launch the GPS Watch model from the Quick Start screen.
4. Ensure all dependencies on the existing GPS Watch model in the xtuml/bridgepoint repository are satisfied.

### 7. Acceptance Test
------------------
1. GPS Watch project gets loaded from Quick Start.
2. GPS Watch is launched according to README without building.
3. GPS Watch shows no compatibility errors with BridgePoint.

End
---

