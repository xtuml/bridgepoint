---

This work is licensed under the Creative Commons CC0 License

---

# Duplicate ports appear in model after restart 
### xtUML Project Implementation Note

### 1. Abstract

This note captures raw notes from the investigation of the duplicate ports issue. It 
should be noted that some of the comments or thinking may have changed or even be
incorrect as captured before the issue is completely understood.       

### 2. Notes 

- With just BMSTestbench and TestFramework I could restart BP and not see the duplicate.  I 
then added the WV_BatteryManagement project and got the same.  I then added the AsimulationConfiguration
project and on restart I see the duplicate in BMSTestbench.  
    - AsimulationConfiguration contains a component reference to BMSTestbench.  Maybe this is related?
    - If I take AsimulationConfiguration back out and restart the duplicate goes away.
    - When there is a duplicate, I exported BMSTestbench to xtUML file and this is what I see in the 
    export data (snipped to relevant part):
```
1084 INSERT INTO C_PO
1085     VALUES ("0867a61e-c920-45fb-a777-b0c5ed4ca0ea",
1086     "c0a756d8-d9d2-461d-9c2f-444915eb7ed2",
1087     'TESTSEQ',
1088     0,
1089     0);
1090 INSERT INTO C_IR
1091     VALUES ("cc9f7adc-7806-4808-b35b-addffe4b1280",
1092     "eff7aa83-70b9-46a6-bfc0-08d66d36431e",
1093     "00000000-0000-0000-0000-000000000000",
1094     "0867a61e-c920-45fb-a777-b0c5ed4ca0ea");
1095 INSERT INTO C_R
1096     VALUES ("cc9f7adc-7806-4808-b35b-addffe4b1280",
1097     'TestSequencing',
1098     '',
1099     'Unnamed Interface',
1100     'BMStestbench::TESTSEQ::TestSequencing');
1101 INSERT INTO C_IR
1102     VALUES ("cc9f7adc-7806-4808-b35b-addffe4b1280",
1103     "eff7aa83-70b9-46a6-bfc0-08d66d36431e",
1104     "00000000-0000-0000-0000-000000000000",
1105     "0867a61e-c920-45fb-a777-b0c5ed4ca0ea");
1106 INSERT INTO C_PO
1107     VALUES ("0867a61e-c920-45fb-a777-b0c5ed4ca0ea",
1108     "c0a756d8-d9d2-461d-9c2f-444915eb7ed2",
1109     'TESTSEQ',
1110     0,
1111     0);
```  

    Notice that the same C_PO and C_IR data is included twice, but there is just one C_R.
    
    - Note that if the project that contains the component reference to BMSTestbench has a name
    that starts with a letter like "Z", there is no duplicate port after load with all the projects.  
        - This seems to indicate that when the ASimulationConfiguration project is used (loaded) it 
        creates a C_C_PROXY and C_PO_PROXY that then should get converted to real instances when
        the BMSTestbench is loaded.  But something goes wrong and we end up with two ports tied to
        the component.
        - When the ZConf project is used, the real component is created first when BMSTestbench is 
        loaded and then when ZConf is loaded the C_C_PROXY and C_PO_PROXY instances are hooked to 
        the real instances.

- Is data of BMSTestbench on disk good?
    - YES
    
    
questions:
- In ImportModelComponent.java, if the newInst that we get back from resolveInstance is a proxy, 
why is it added to the "loadedInstances" and not "loadedProxies"? Here is an example from createC_PO:

```
      else if ( table.equalsIgnoreCase("C_PO_PROXY") ) //$NON-NLS-1$
      {
    String path = (String)parms.lastElement();
        newInst = Port_c.createProxy(modelRoot,
              IdAssigner.createUUIDFromString((String)parms.elementAt(0)), 
          IdAssigner.createUUIDFromString((String)parms.elementAt(1)), 
           removeTics((String)parms.elementAt(2)).trim(), 
           Integer.parseInt((String)parms.elementAt(3)), 
           ((String)parms.elementAt(4)).equals( "false" ) ? false : true, removeTics(path), projRelPath);
        if (newInst.isProxy()) {
          loadedInstances.add(newInst);
          pm.worked(1);
        } else {
          loadedProxies.add(newInst);
        }
```        
        
I've debugged the duplicate ports issue (10218) far enough to see from a high level that 
something is going sideways with the instanceListMap in the modelRoot:
- A Port proxy is loaded from disk.  A Port_c instance is created and the Port_c instance 
is put into the modelRoot's instanceListMap
- BP then loads the real "C_PO" from disk.  It finds the proxy instance in the map and 
converts it from a proxy
- BP then goes to load the same C_PO data again (not sure why), but here resolveInstance() 
does not find the Port_c instance in the modelRoot's instanceListMap and it should.  

It's like the Port_c was somehow removed from the map.  So I'm continuing down the trail 
to try to understand why the Port_c instance disappeared from the map.

I can see from looking at the IDs that the modelRoot instance is correct and the same in 
all three passes and the same is true for the instanceListMap of the modelRoot and the map 
spot that holds the Port_c instances is the same.

-------
During a later call to resolveInstance, the instanceListMap of the modelRoot has an
empty Port_c list even though it had the Port in it previously at the point where the 
proxy was converted to real...

ImportModelComponent::performCleanUp() 
  - m_clear_database flag is true and this nukes the existing instances in the map
  via PersistableModelComponent.clearDatabase()  
  
---------

1) Looks like we are adding to the modelRoot's map in both the Port_c constructor call to 
super() which calls NonRootModelElement constructor which adds to the instanceList and at
the end of Port_c constructor.
    - This boils down to a HashMap.put(key, value) call, since the key is the same the put will
    just overwrite the data associated with the existing key.

2) ModelRoot.getInstanceList() seems to have a bug.  We create a "new" instanceList for 
the map if the existing is null __OR__ empty.  I see no reason to create a new list for
the map if the existing one is empty.  Seems like it should only do this if it is null!

3) In ImportModelComponent and ImportModelStream, the loadedProxies list seems pointless.  We never,
AFAICT, go through the code path to populate it.  And, on the flip side, I see no code where we ever 
try to retrieve instances from this list.

---------  
Tracing PMC loading:  
  L/ASim/models/ASim/p/p.xtuml
  
  L/BMStestbench/models/BMStestbench/Components/BMStestbench/BMStestbench/BMStestbench.xtuml
  
  L/ASim/models/ASim/p/p.xtuml
  
  L/BMStestbench/models/BMStestbench/Components/BMStestbench/BMStestbench/BMStestbench.xtuml
  
  
  try to load BMStestbench
    - load parent (note, have not set status == LOADING on BMStestbench PMC yet)
        - ImportModelComponent.finishComponentLoad() (on parent)
            - batchRelate() on graphics with searchAllRoots == true
                - calls into FloatingText_c.batchRelate():1109, where it does not find an instance it's
                  looking for.  So it calls ensureAllInstancesLoaded().
                    - Inside ensureAllInstancesLoaded() we end up initiating a load of BMStestbench again 
                      because the condition sees we're not loaded and, due to the aforementioned flag, not
                      in the LOADING process
                        - This tries to load the parent AGAIN!!!


---------  
Comments from Levi:  
```
  from what keith is describing, it sounds like there are two bugs:
1. doesn't find an instance in the instance list that should be there (resulting in duplicated instances)

2. the instance itself gets loaded twice which it shouldn't be doing in the first place

I think number 2 is the silver bullet bob is talking about. we have known about this for a long time, 
but it's tricky to fix without making a big change to the underlying architecture

it's something that in theory "shouldn't cause problems" but in practice it does cause all sorts of 
unexpected load behaviors

The load has 3 phases: load instances from SQL data, batch relate, finish up

it performs these 3 phases on each .xtuml file iteratively

the problem is, in our current architecture, during batch relate, there is a call to something 
called "ensureAllInstancesLoaded" which makes sure all instances of that class are loaded

this causes a situation where nested loading is occurring and instead of the 3 phases running in 
series like they are designed, they are running in a nested way

during the finish up phase lots of model upgrade and graphics operations are performed and 
sometimes conditions are checked which fail because instances are loaded but not fully related yet

this could be the explanation for the C_PO is being loaded twice

it's loaded on the initial load and then again when "ensureAllInstancesLoaded" is invoked

in this case, if the instance list stuff wasn't broken it may never be noticed
```

### End
