Pre-builder performance brainstorming session
-------------------------------------------------
Present: Keith, Bob, Campbell, Travis, Cort  

1) Initial data  
<pre>
Local i7 Intel Xeon E5620 @ 2.4 GHz 12 GB RAM
Prebuild:  ~6 mins
Ant build:              -    started 10:03
                        -1   ended   10:12
                        -2   ended   10:37
                        -cme ended   11:01 57 mins 23 seconds

VM Intel Xeon E5-2680 @2.7 GHz 6 GB RAM
Prebuild:  ~20 mins (!)
Ant build:              -    started 02:26
                        -1   ended   02:34
                        -2   ended   03:04 (not accurate, but not later than this)
                        -cme ended   03:16 49 mins 20 seconds
</pre>						
- Generally feel like we cannot trust the VM times
- Both of these measurements are for bp.core using the new way
  - java pre-builder for bp.core now splits the output into two pieces ooaofooa-1 and ooaofooa-2
  - we then have another step called CME (using ooaofooa-2 which contains the Functions package) which picks up the rest of the bits we generate (CME, inspectors, wizards, etc)
  
2) One optimization is to provide a smaller input file to the "CME" step  
  
3) Another optimization would be to break the build up into three or four pieces rather than two, simply to provide less instances for gen_erate to handle  

4) If we could get the action language compiles, then have a sql files with ACT_ and V_ omitted, the parts of code generation that don't need these would run much much faster  
  - Our code is OAL heavy
  - The structural generation is probably a small part of our build time, so this optimization may not benefit us much
  
5) We currently run generate on the Function package twice, once for OAL and once for bp.als.  Maybe we could only do this once?   

6) Modify the pre-builder to stop outputting V_LOC.  Take these out of the stream PEI data.  

7) If we have multiple files (ooaofooa-1 to -4) we could run generator concurrently on these files  


Outcome
-----
Next steps are to do 3 (and this gets us 2) and 6 before promotion.  We'll pursue 7 as a separate step. 
