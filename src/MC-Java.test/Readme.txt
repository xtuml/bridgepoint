To perform this test highlight MC-Java.test and select Project -> Clean ...
Check the 'Clean selected projects' box and click OK.

The output from each test is compared with output found in the expected_results
folder of each test.

If the compare_results target shows that the outputs are not identical, then
highlight the two non-identical outputs and select Compare -> Each Other.

The parse chain tests require the latest version of libTRANS.dll (1.4 or
greater).  This file is installed by default in 
\mgc\embedded\bridgepoint\win32\client\lib.

