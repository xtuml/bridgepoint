---

Copyright 2013 Mentor Graphics Corp.  All Rights Reserved.

---

# Build xtumlmc_build.exe on the Server
### xtUML Project Design Note


1. Abstract
-----------
This note describes the work to allow the team to compile the xtumlmc_build
perl script to a Windows EXE on the server.

2. Document References
----------------------
[1] Issues 114, https://github.com/xtuml/internal/issues/114  
[2] CQ DEI dts0101013505 - CQ twin issue  
[3] Strawberry Perl, http://strawberryperl.com/  
[4] PAR FAQ, http://par.wikia.com/wiki/FAQ  
[5] PAR::Packer::pp usage.  http://search.cpan.org/~rschupp/PAR-Packer-1.015/lib/pp.pm  

3. Background
-------------
As of 2013, we have built the xtumlmc_build perl script to a Windows EXE using 
one of two licensed copies of ActiveState Perl for at least five years.  The 
licenses are held by Keith Brown and Bob Mulvey.  This is problematic for two reasons:  

1) Every time Keith or Bob get new machines, they have to go through the process
  of re-installing ActiveState Perl and getting new license keys from ActiveState.  This
  is annoying and time consuming.    

2) Keith and Bob are bottlenecks for others who want to make changes to 
  xtumlmc_build and test the changes in compiled form on Windows.  

Keith is now at a point where he needs to reinstall ActiveState on a new machine. Bob 
got a new machine a while back and hasn't reinstalled yet.  Instead of doing this, 
we will continue our general goal of improving build process and install a tool 
on the server to build the EXE.  

4. Requirements
---------------
4.1  The xtumlmc_build EXE shall be built on a server accessible by any member of
  the development team.  

5. Analysis
-----------
5.1  Using ActiveState perl has been functionally fine, but reinstalling every two
  years is annoying.  The process is time consuming and ActiveState has not been 
  keen to re-issue licenses to what is now old versions of their software.  As
  mentioned above, it is also limiting to only have two licensed users.  To meet 
  our requirements, we need to install ActiveState Perl on the server, or we need
  to find a different tool to build the required EXE.  
5.1.1  A web search turned up several references to Strawberry Perl [3], an 
  open-source perl implementation for Windows.  Strawberry Perl is very similar
  to ActiveState Perl, only it's open-source rather than pay-ware.  
5.1.2  Cygwin Perl can theoretically be configured to build EXEs.  

5.2  Strawberry Perl is the primary OSS competitor to ActivePerl from ActiveState.
  Strawberry Perl packages EXEs is a manner that is nearly identical to the EXEs 
  created by the ActiveState tool.      
  
5.3  Cygwin perl is not very user-friendly when adding additional modules.  There
  is also a concern about the resultant EXE being dependent on cygwin.dll, which
  we definitely do not want.    

5.4  Due to its similarity to ActivePerl and the ease of installation of the 
  additional tools inside perl to build EXEs, we will use Strawberry Perl.  
   
6. Design
---------
6.1  Install Strawberry Perl (5.16.3.1 32-bit) on svr-azt-eng-03 to c:\strawberry  

6.2  Using the Strawberry Perl command shell (```Start > All Programs > Strawberry Perl > Perl (command line)```), 
  install the PAR::Packer module [4]:    
```
C:\> cpan PAR::Packer
```
  
6.3  Build the EXE with pp [5]:
```
C:\temp> copy c:\cygwin\git\xtuml\mc\bin\xtumlmc_build xtumlmc_build
C:\temp> pp -o xtumlmc_build.exe xtumlmc_build
```  

6.4  After the new EXE is tested, it can be included in the nightly build by
  checking it into the SVN ```extra_files_for_build``` project using the eclipse 
  ```C:\workspaces\build_artifacts``` workspace on this server.   
  
7. Design Comments
------------------
7.1  The file size of the EXE is 4MB vs 800KB for the ActiveState version.  The 
  reason is that Strawberry Perl has made the design decision to include a lot
  of perl modules into the EXEs it creates to increase the likelyhood that most
  scripts the user creates will work out of the box.  ActivePerl is much more 
  judicious in the modules it includes.  The PAR FAQ [4] talks about this and 
  gives some user suggestions for reducing the file size.  
  
  I modified the EXE created by each to output the %INC hash that lists what 
  modules are included in the compiled EXE.  I spent a little time trying to 
  exclude unneeded modules from Strawberry's compile (using ```pp -X < module > ...```),
  but I wasn't able to make any progress.  I was also concerned that there were 
  dependencies between modules that I might unknowingly break.  Additionally, I 
  thought that the extra time during a BridgePoint build to load the larger EXE 
  is insignificant compared to the translation process time.  In the end, I 
  decided it wasn't worth any more development time to attempt to trim and test
  new EXEs and I just let Strawberry Perl work in it's default manner.  
  
  Here is the %INC hash outputs from the two EXEs for future reference if someone
  decides later to revisit this choice.  
  
```
===================================================================
ActiveState Perl:   Module Includes
===================================================================
Carp.pm /PerlApp/Carp.pm
Exporter.pm /PerlApp/Exporter.pm
XSLoader.pm /PerlApp/XSLoader.pm
File/Copy.pm /PerlApp/File/Copy.pm
File/Compare.pm /PerlApp/File/Compare.pm
auto/POSIX/load_imports.al /PerlApp/auto/POSIX/load_imports.al
/PerlApp/auto/POSIX/autosplit.ix /PerlApp/auto/POSIX/autosplit.ix
strict.pm /PerlApp/strict.pm
POSIX.pm /PerlApp/POSIX.pm
base.pm /PerlApp/base.pm
re.pm /PerlApp/re.pm
warnings.pm /PerlApp/warnings.pm
File/Find.pm /PerlApp/File/Find.pm
File/Basename.pm /PerlApp/File/Basename.pm
File/Path.pm /PerlApp/File/Path.pm
AutoLoader.pm /PerlApp/AutoLoader.pm
Cwd.pm /PerlApp/Cwd.pm

===================================================================
Strawberry perl: Module includes
===================================================================
attributes.pm /loader/HASH(0x217972c)/attributes.pm
Compress/Raw/Zlib.pm /loader/HASH(0x1dd6104)/Compress/Raw/Zlib.pm
POSIX.pm C:\Users\kbrown\AppData\Local\Temp\par-6b62726f776e\cache-8aec1169b1e84e456c94e8ba51ad7c0f3a52cdd4\inc\lib/POSIX.pm
List/Util.pm /loader/HASH(0x217930c)/List/Util.pm
Tie/Hash.pm C:\Users\kbrown\AppData\Local\Temp\par-6b62726f776e\cache-8aec1169b1e84e456c94e8ba51ad7c0f3a52cdd4\inc\lib/Tie/Hash.pm
File/Find.pm /loader/HASH(0x1dd65e4)/File/Find.pm
Cwd.pm /loader/HASH(0x1dd62e4)/Cwd.pm
IO/Uncompress/Adapter/Inflate.pm /loader/HASH(0x217918c)/IO/Uncompress/Adapter/Inflate.pm
Carp/Heavy.pm /loader/HASH(0x1d80eac)/Carp/Heavy.pm
Fcntl.pm /loader/HASH(0x1dd64c4)/Fcntl.pm
Symbol.pm /loader/HASH(0x21794ec)/Symbol.pm
Scalar/Util.pm /loader/HASH(0x217942c)/Scalar/Util.pm
Exporter.pm /loader/HASH(0x1dd6404)/Exporter.pm
Win32.pm /loader/HASH(0x217966c)/Win32.pm
Errno.pm /loader/HASH(0x1dd63a4)/Errno.pm
File/Spec.pm /loader/HASH(0x1dd6764)/File/Spec.pm
File/Glob.pm /loader/HASH(0x1dd6644)/File/Glob.pm
File/GlobMapper.pm /loader/HASH(0x1dd66a4)/File/GlobMapper.pm
PAR/Filter.pm /loader/HASH(0x2179dec)/PAR/Filter.pm
XSLoader.pm /loader/HASH(0x21796cc)/XSLoader.pm
PAR/Heavy.pm /loader/HASH(0x21d31cc)/PAR/Heavy.pm
warnings/register.pm /loader/HASH(0x2179d8c)/warnings/register.pm
IO/Compress/Gzip.pm /loader/HASH(0x1dd6ac4)/IO/Compress/Gzip.pm
Archive/Zip/DirectoryMember.pm /loader/HASH(0x2179fcc)/Archive/Zip/DirectoryMember.pm
IO/Uncompress/Base.pm /loader/HASH(0x21791ec)/IO/Uncompress/Base.pm
Archive/Zip/Member.pm /loader/HASH(0x217a08c)/Archive/Zip/Member.pm
Archive/Zip/StringMember.pm /loader/HASH(0x21d304c)/Archive/Zip/StringMember.pm
Config_git.pl /loader/HASH(0x1dd6224)/Config_git.pl
utf8.pm /loader/HASH(0x2179c0c)/utf8.pm
overloading.pm /loader/HASH(0x2179a8c)/overloading.pm
IO/Uncompress/Gunzip.pm /loader/HASH(0x217924c)/IO/Uncompress/Gunzip.pm
Archive/Zip.pm /loader/HASH(0x2179f0c)/Archive/Zip.pm
bytes.pm /loader/HASH(0x217984c)/bytes.pm
File/Spec/Unix.pm /loader/HASH(0x1dd67c4)/File/Spec/Unix.pm
Exporter/Heavy.pm /loader/HASH(0x1dd6464)/Exporter/Heavy.pm
vars.pm /loader/HASH(0x2179ccc)/vars.pm
strict.pm /loader/HASH(0x2179aec)/strict.pm
PAR/SetupTemp.pm /loader/HASH(0x21d328c)/PAR/SetupTemp.pm
PAR/Filter/PodStrip.pm /loader/HASH(0x2179eac)/PAR/Filter/PodStrip.pm
Config_heavy.pl /loader/HASH(0x1dd6284)/Config_heavy.pl
IO/Compress/Base.pm /loader/HASH(0x1dd6a04)/IO/Compress/Base.pm
AutoLoader.pm /loader/HASH(0x1d65e2c)/AutoLoader.pm
Archive/Zip/FileMember.pm /loader/HASH(0x217a02c)/Archive/Zip/FileMember.pm
lib.pm /loader/HASH(0x21799cc)/lib.pm
IO/Compress/Zlib/Extra.pm /loader/HASH(0x1dd6be4)/IO/Compress/Zlib/Extra.pm
auto/Compress/Raw/Zlib/autosplit.ix /loader/HASH(0x217978c)/auto/Compress/Raw/Zlib/autosplit.ix
IO/Handle.pm /loader/HASH(0x1dd6ca4)/IO/Handle.pm
Tie/Hash/NamedCapture.pm /loader/HASH(0x217954c)/Tie/Hash/NamedCapture.pm
SelectSaver.pm /loader/HASH(0x217948c)/SelectSaver.pm
unicore/lib/Perl/_PerlIDS.pl /loader/HASH(0x2179bac)/unicore/lib/Perl/_PerlIDS.pl
Compress/Zlib.pm /loader/HASH(0x1dd6164)/Compress/Zlib.pm
unicore/Heavy.pl /loader/HASH(0x2179b4c)/unicore/Heavy.pl
Time/Local.pm /loader/HASH(0x21795ac)/Time/Local.pm
warnings.pm /loader/HASH(0x2179d2c)/warnings.pm
Archive/Zip/NewFileMember.pm /loader/HASH(0x21d2fec)/Archive/Zip/NewFileMember.pm
File/Compare.pm C:\Users\kbrown\AppData\Local\Temp\par-6b62726f776e\cache-8aec1169b1e84e456c94e8ba51ad7c0f3a52cdd4\inc\lib/File/Compare.pm
UNIVERSAL.pm /loader/HASH(0x217960c)/UNIVERSAL.pm
File/Temp.pm /loader/HASH(0x1dd6884)/File/Temp.pm
main CODE(0x2a44794)
PAR.pm /loader/HASH(0x21d310c)/PAR.pm
PAR/Filter/PatchContent.pm /loader/HASH(0x2179e4c)/PAR/Filter/PatchContent.pm
File/Path.pm /loader/HASH(0x1dd6704)/File/Path.pm
IO/Compress/RawDeflate.pm /loader/HASH(0x1dd6b84)/IO/Compress/RawDeflate.pm
IO/Compress/Gzip/Constants.pm /loader/HASH(0x1dd6b24)/IO/Compress/Gzip/Constants.pm
File/Spec/Win32.pm /loader/HASH(0x1dd6824)/File/Spec/Win32.pm
IO/Compress/Adapter/Deflate.pm /loader/HASH(0x1dd69a4)/IO/Compress/Adapter/Deflate.pm
PAR/Dist.pm /loader/HASH(0x21d316c)/PAR/Dist.pm
IO/Seekable.pm /loader/HASH(0x217912c)/IO/Seekable.pm
File/Copy.pm /loader/HASH(0x1dd6584)/File/Copy.pm
base.pm /loader/HASH(0x21797ec)/base.pm
IO/Uncompress/RawInflate.pm /loader/HASH(0x21792ac)/IO/Uncompress/RawInflate.pm
Config.pm /loader/HASH(0x1dd61c4)/Config.pm
File/Basename.pm /loader/HASH(0x1dd6524)/File/Basename.pm
integer.pm /loader/HASH(0x217996c)/integer.pm
Archive/Zip/Archive.pm /loader/HASH(0x2179f6c)/Archive/Zip/Archive.pm
IO.pm /loader/HASH(0x1dd6944)/IO.pm
Carp.pm /loader/HASH(0x1d6604c)/Carp.pm
PAR/SetupProgname.pm /loader/HASH(0x21d322c)/PAR/SetupProgname.pm
utf8_heavy.pl /loader/HASH(0x2179c6c)/utf8_heavy.pl
FileHandle.pm /loader/HASH(0x1dd68e4)/FileHandle.pm
IO/Compress/Base/Common.pm /loader/HASH(0x1dd6a64)/IO/Compress/Base/Common.pm
constant.pm /loader/HASH(0x21798ac)/constant.pm
PerlIO/scalar.pm /loader/HASH(0x21793cc)/PerlIO/scalar.pm
PerlIO.pm /loader/HASH(0x217936c)/PerlIO.pm
Archive/Zip/ZipFileMember.pm /loader/HASH(0x21d30ac)/Archive/Zip/ZipFileMember.pm
IO/File.pm /loader/HASH(0x1dd6c44)/IO/File.pm
overload.pm /loader/HASH(0x2179a2c)/overload.pm
DynaLoader.pm /loader/HASH(0x1dd6344)/DynaLoader.pm
feature.pm /loader/HASH(0x217990c)/feature.pm
```
  
7.2  On svr-azt-eng-03, our Windows 2003 build server, I first installed Strawberry Perl
  5.18.1.1-32bit.  However, I was getting crashes when I tried to install the ```PAR::Packer```
  perl module.  I dropped back to the previous stable release (5.16.3.1-32bit) and things
  worked fine.  
  
8. Unit Test
------------
8.1  Put the new xtumlmc_build.exe into a BridgePoint installation and build
  the MicrowaveOven and GPS Watch projects.  Verify that they build successfully.  
8.2  Perform a BridgePoint build with the new xtumlmc_build.exe to verify it 
  succeeds since this build uses some other internal functions of the script that
  a standard project build does not use.  
  
End
---

