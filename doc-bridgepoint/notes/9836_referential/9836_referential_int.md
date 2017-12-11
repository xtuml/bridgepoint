---

This work is licensed under the Creative Commons CC0 License

---

# Cannot merge referential with identifier when formalising super type   
### xtUML Project Implementation Note


### 1. Abstract
This note captures the changes for the headline issue.

### 2. Document References

<a id="2.1"></a>2.1 [BridgePoint DEI #9836](https://support.onefact.net/issues/9836)    
<a id="2.2"></a>2.2 [9836 Design Note](https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/notes/9836_referential/9836_referential_dnt.md)    

### 3. Background

See [2.2]  

### 4. Requirements

See [2.2]  

### 5. Work Required

See [2.2]  

### 6. Implementation Comments

None.  

### 7. Unit Test

See [2.2]  

### 8. User Documentation

None.  

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint   
Branch: 9836_referential

<pre>
 doc-bridgepoint/notes/9836_referential/9836_referential_dnt.md                  | 221 ++++++++++++++++++++++++++++++++++++++
 doc-bridgepoint/notes/9836_referential/9836_referential_int.md                  | 105 ++++++++++++++++++
 doc-bridgepoint/notes/9836_referential/Step2.png                                | Bin 0 -> 83173 bytes
 doc-bridgepoint/notes/9836_referential/Step5.png                                | Bin 0 -> 79778 bytes
 doc-bridgepoint/notes/9836_referential/fig263.png                               | Bin 0 -> 298975 bytes
 doc-bridgepoint/notes/9836_referential/fig421.png                               | Bin 0 -> 289322 bytes
 doc-bridgepoint/review-minutes/9836_referential_dnt_rvm.md                      |  45 ++++++++
 .../models/org.xtuml.bp.core/ooaofooa/Subsystem/Attribute/Attribute.xtuml       | 151 ++++++++++++++++++++++----
 8 files changed, 502 insertions(+), 27 deletions(-)

</pre>

Fork/Repository: keithbrown/bptest   
Branch: 9836_referential

<pre>
 .../CombineSplitReferentials_12/CombineSplitReferentials_12-112                 |   4 +-
 .../CombineSplitReferentials_12/CombineSplitReferentials_12-112.jpg             | Bin 118700 -> 124572 bytes
 .../CombineSplitReferentials_13/CombineSplitReferentials_13-112                 |   4 +-
 .../CombineSplitReferentials_13/CombineSplitReferentials_13-112.jpg             | Bin 117859 -> 123701 bytes
 .../CombineSplitReferentials_14/CombineSplitReferentials_14-112                 | 117 ++++++++++++
 .../CombineSplitReferentials_14/CombineSplitReferentials_14-112.jpg             | Bin 0 -> 98537 bytes
 .../CombineSplitReferentials_15/CombineSplitReferentials_15-112                 | 117 ++++++++++++
 .../CombineSplitReferentials_15/CombineSplitReferentials_15-112.jpg             | Bin 0 -> 98537 bytes
 .../expected_results/CombineSplitReferentials_6/CombineSplitReferentials_6-112  |   4 +-
 .../CombineSplitReferentials_6/CombineSplitReferentials_6-112.jpg               | Bin 120789 -> 126591 bytes
 .../expected_results/CombineSplitReferentials_8/CombineSplitReferentials_8-112  |   4 +-
 .../CombineSplitReferentials_8/CombineSplitReferentials_8-112.jpg               | Bin 120223 -> 126032 bytes
 .../expected_results/CombineSplitReferentials_9/CombineSplitReferentials_9-112  |   4 +-
 .../CombineSplitReferentials_9/CombineSplitReferentials_9-112.jpg               | Bin 119584 -> 125480 bytes
 src/org.xtuml.bp.core.test/src/CoreGlobalsTestSuite2Generics.java               |  15 +-
 .../src/org/xtuml/bp/core/test/CombineSplitReferentialsTestGenerics.java        | 363 +++++++++++++++++++++++---------------
 16 files changed, 466 insertions(+), 166 deletions(-)

</pre>

Fork/Repository: keithbrown/models   
Branch: 9836_referential

<pre>
 .../models/CombineSplitReferentialsTest/CombineSplitReferentialsTest.xtuml      |   30 +-
 .../CombineSplitReferentialsTest/CombineSplitReferentialsTest.xtuml             |   62 +-
 .../CombineSplitReferentialsTest/brewing/airlock/airlock.xtuml                  |  147 +++
 .../CombineSplitReferentialsTest/brewing/brew pot/brew pot.xtuml                |   88 ++
 .../CombineSplitReferentialsTest/brewing/brewing.xtuml                          | 2356 +++++++++++++++++++++++++++++++++++++
 .../CombineSplitReferentialsTest/brewing/brewmaster/brewmaster.xtuml            |   73 ++
 .../CombineSplitReferentialsTest/brewing/conditioning/conditioning.xtuml        |  135 +++
 .../brewing/fermentation stage/fermentation stage.xtuml                         |  193 +++
 .../CombineSplitReferentialsTest/brewing/fermentor/fermentor.xtuml              |  207 ++++
 .../brewing/primary fermentation/primary fermentation.xtuml                     |  135 +++
 .../brewing/secondary fermentation/secondary fermentation.xtuml                 |  135 +++
 .../CombineSplitReferentialsTest/brewing/vessel/vessel.xtuml                    |   55 +
 .../CombineSplitReferentialsTest/brewing/wort/wort.xtuml                        |  142 +++
 .../CombineSplitReferentialsTest/dog_test/Dog/Dog.xtuml                         |  203 ++++
 .../CombineSplitReferentialsTest/dog_test/DogOwner/DogOwner.xtuml               |  168 +++
 .../CombineSplitReferentialsTest/dog_test/Owner/Owner.xtuml                     |   55 +
 .../CombineSplitReferentialsTest/dog_test/ajb/ajb.xtuml                         |   55 +
 .../CombineSplitReferentialsTest/dog_test/dog_test.xtuml                        | 1257 ++++++++++++++++++++
 .../CombineSplitReferentialsTest/dog_test/egb/egb.xtuml                         |   55 +
 .../CombineSplitReferentialsTest/dog_test/skb/skb.xtuml                         |  129 ++
 20 files changed, 5670 insertions(+), 10 deletions(-)

</pre>

### End

