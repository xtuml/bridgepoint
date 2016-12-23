---

This work is licensed under the Creative Commons CC0 License

---

# Update to eclipse.org-style build
### xtUML Project Implementation Note


1. Abstract
-----------
This note captures the files changes during the creation of the maven-based build.   

2. Document References
----------------------
<a id="2.1"></a>2.1 [BridgePoint DEI #8495](https://support.onefact.net/issues/8495) Headline issue.     
<a id="2.2"></a>2.2 [8495 Maven Build Design Note](8495_maven_build_dnt.md)     

3. Background
-------------
See [[2.2]](#2.2).   

4. Requirements
---------------
See [[2.2]](#2.2).   

5. Work Required
----------------
See [[2.2]](#2.2).   

6. Implementation Comments
--------------------------
None.   

7. Unit Test
------------
7.1  Run a successful build on One Fact Hudson server.   
  
8. User Documentation
---------------------
See [[2.2]](#2.2).   

9. Code Changes
---------------
This work modifies the ```bridgepoint```, ```mc```, and ```packaging``` repositories.   

Fork/Repository: keithbrown/bridgepoint   
Branch: 8495_maven_update   

<pre>
doc-bridgepoint/notes/8495_maven_build_dnt.md      | 239 +++++++++++++++++
 releng/org.xtuml.bp.mctools.parent/.project        |  11 +
 releng/org.xtuml.bp.mctools.parent/pom.xml         |  61 +++++
 releng/org.xtuml.bp.mctools/.project               |  17 ++
 releng/org.xtuml.bp.mctools/build.properties       |  11 +
 .../common/tools/mc/.gitignore                     |   2 +
 releng/org.xtuml.bp.mctools/feature.xml            |  19 ++
 .../linux.all/tools/mc/bin/.gitignore              |  12 +
 releng/org.xtuml.bp.mctools/pom.xml                | 151 +++++++++++
 .../win.all/tools/mc/bin/.gitignore                |   4 +
 releng/org.xtuml.bp.releng.p2/.project             |  11 +
 releng/org.xtuml.bp.releng.p2/category.xml         |  13 +
 releng/org.xtuml.bp.releng.p2/pom.xml              |  15 ++
 .../org.xtuml.bp.releng.parent.generation/.project |  11 +
 .../org.xtuml.bp.releng.parent.generation/pom.xml  |  53 ++++
 releng/org.xtuml.bp.releng.parent.product/.project |  11 +
 .../bridgepoint.product                            |  79 ++++++
 releng/org.xtuml.bp.releng.parent.product/pom.xml  |  34 +++
 releng/org.xtuml.bp.releng.parent.tests/.project   |  11 +
 releng/org.xtuml.bp.releng.parent.tests/pom.xml    | 103 ++++++++
 releng/org.xtuml.bp.releng.parent/.project         |  11 +
 releng/org.xtuml.bp.releng.parent/pom.xml          | 140 ++++++++++
 src/org.xtuml.bp.als.oal.test/META-INF/MANIFEST.MF |   2 +-
 src/org.xtuml.bp.als.oal.test/build.properties     |   4 +
 src/org.xtuml.bp.als.oal.test/pom.xml              |  31 +++
 src/org.xtuml.bp.als.oal/META-INF/MANIFEST.MF      |   3 +-
 src/org.xtuml.bp.als.oal/pom.xml                   |  16 ++
 src/org.xtuml.bp.als/pom.xml                       |  57 ++++
 src/org.xtuml.bp.bld.pkg-feature/feature.xml       |   2 +-
 src/org.xtuml.bp.bld.pkg-feature/pom.xml           |  15 ++
 src/org.xtuml.bp.bld.pkg/META-INF/MANIFEST.MF      |   1 +
 src/org.xtuml.bp.bld.pkg/pom.xml                   |  15 ++
 src/org.xtuml.bp.cdt/.classpath                    |   2 +-
 src/org.xtuml.bp.cdt/META-INF/MANIFEST.MF          |   2 +-
 src/org.xtuml.bp.cdt/pom.xml                       |  15 ++
 src/org.xtuml.bp.cli/.classpath                    |   2 +-
 src/org.xtuml.bp.cli/pom.xml                       |  15 ++
 src/org.xtuml.bp.compare/META-INF/MANIFEST.MF      |   3 +-
 src/org.xtuml.bp.compare/pom.xml                   |  49 ++++
 src/org.xtuml.bp.core.test/META-INF/MANIFEST.MF    |   2 +-
 src/org.xtuml.bp.core.test/build.properties        |   3 +-
 src/org.xtuml.bp.core.test/generate.xml            |   6 +
 src/org.xtuml.bp.core.test/pom.xml                 | 127 +++++++++
 src/org.xtuml.bp.core/META-INF/MANIFEST.MF         |   3 +-
 src/org.xtuml.bp.core/pom.xml                      |  49 ++++
 .../META-INF/MANIFEST.MF                           |   2 +-
 src/org.xtuml.bp.debug.ui.test/pom.xml             |  42 +++
 src/org.xtuml.bp.debug.ui/META-INF/MANIFEST.MF     |   3 +-
 src/org.xtuml.bp.debug.ui/pom.xml                  |  15 ++
 src/org.xtuml.bp.doc/META-INF/MANIFEST.MF          |   1 +
 src/org.xtuml.bp.doc/build.properties              |   1 -
 src/org.xtuml.bp.doc/pom.xml                       |  15 ++
 src/org.xtuml.bp.docgen/META-INF/MANIFEST.MF       |   3 +-
 src/org.xtuml.bp.docgen/pom.xml                    |  15 ++
 .../org/xtuml/bp/docgen/generator/Generator.java   |  14 +-
 .../META-INF/MANIFEST.MF                           |   3 +-
 src/org.xtuml.bp.internal.tools/pom.xml            |  15 ++
 src/org.xtuml.bp.io.core/META-INF/MANIFEST.MF      |   3 +-
 src/org.xtuml.bp.io.core/pom.xml                   |  58 ++++
 src/org.xtuml.bp.io.image/META-INF/MANIFEST.MF     |   3 +-
 src/org.xtuml.bp.io.image/pom.xml                  |  15 ++
 src/org.xtuml.bp.io.mdl.test/META-INF/MANIFEST.MF  |   2 +-
 src/org.xtuml.bp.io.mdl.test/build.properties      |   3 +-
 src/org.xtuml.bp.io.mdl.test/pom.xml               |  51 ++++
 src/org.xtuml.bp.io.mdl/META-INF/MANIFEST.MF       |   3 +-
 src/org.xtuml.bp.io.mdl/pom.xml                    |  49 ++++
 src/org.xtuml.bp.mc.c.source/.classpath            |   2 +-
 src/org.xtuml.bp.mc.c.source/META-INF/MANIFEST.MF  |   3 +-
 src/org.xtuml.bp.mc.c.source/build.properties      |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.c.source/pom.xml               |  15 ++
 src/org.xtuml.bp.mc.cpp.source/.classpath          |   2 +-
 .../META-INF/MANIFEST.MF                           |   3 +-
 src/org.xtuml.bp.mc.cpp.source/build.properties    |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.cpp.source/pom.xml             |  15 ++
 src/org.xtuml.bp.mc.java.source/.classpath         |   2 +-
 .../META-INF/MANIFEST.MF                           |   3 +-
 src/org.xtuml.bp.mc.java.source/build.properties   |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.java.source/pom.xml            |  15 ++
 src/org.xtuml.bp.mc.none/.classpath                |   2 +-
 src/org.xtuml.bp.mc.none/META-INF/MANIFEST.MF      |   3 +-
 src/org.xtuml.bp.mc.none/pom.xml                   |  15 ++
 src/org.xtuml.bp.mc.systemc.source/.classpath      |   2 +-
 .../META-INF/MANIFEST.MF                           |   3 +-
 .../build.properties                               |   2 -
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc.systemc.source/pom.xml         |  15 ++
 src/org.xtuml.bp.mc.template/pom.xml               |  15 ++
 .../build_settings/build_setting.properties        |   2 +-
 src/org.xtuml.bp.mc/.classpath                     |   2 +-
 src/org.xtuml.bp.mc/META-INF/MANIFEST.MF           |   3 +-
 src/org.xtuml.bp.mc/pom.xml                        |  15 ++
 .../src/org/xtuml/bp/mc/AbstractProperties.java    |  10 +-
 .../org/xtuml/bp/mc/MCBuilderArgumentHandler.java  |   9 +-
 src/org.xtuml.bp.model.compare.test/.classpath     |   2 +-
 .../META-INF/MANIFEST.MF                           |   2 +-
 src/org.xtuml.bp.model.compare.test/pom.xml        |  61 +++++
 .../META-INF/MANIFEST.MF                           |   3 +-
 src/org.xtuml.bp.model.compare/pom.xml             |  49 ++++
 src/org.xtuml.bp.pkg-feature/feature.xml           |   7 +-
 src/org.xtuml.bp.pkg-feature/pom.xml               |  15 ++
 src/org.xtuml.bp.pkg/META-INF/MANIFEST.MF          |   1 +
 src/org.xtuml.bp.pkg/build.properties              |   3 +-
 src/org.xtuml.bp.pkg/pom.xml                       |  15 ++
 src/org.xtuml.bp.pkg/root/LICENSE                  | 202 ++++++++++++++
 src/org.xtuml.bp.pkg/root/epl-v10.html             | 261 ++++++++++++++++++
 src/org.xtuml.bp.pkg/root/notice.html              | 108 ++++++++
 src/org.xtuml.bp.search.test/.classpath            |   2 +-
 src/org.xtuml.bp.search.test/META-INF/MANIFEST.MF  |   2 +-
 src/org.xtuml.bp.search.test/pom.xml               |  31 +++
 src/org.xtuml.bp.search/.classpath                 |   2 +-
 src/org.xtuml.bp.search/META-INF/MANIFEST.MF       |   2 +-
 src/org.xtuml.bp.search/pom.xml                    |  15 ++
 src/org.xtuml.bp.test/META-INF/MANIFEST.MF         |   3 +-
 src/org.xtuml.bp.test/pom.xml                      |  15 ++
 .../META-INF/MANIFEST.MF                           |   2 +-
 src/org.xtuml.bp.ui.canvas.test/pom.xml            |  81 ++++++
 src/org.xtuml.bp.ui.canvas/META-INF/MANIFEST.MF    |   3 +-
 src/org.xtuml.bp.ui.canvas/build.properties        |   1 -
 src/org.xtuml.bp.ui.canvas/pom.xml                 |  49 ++++
 .../META-INF/MANIFEST.MF                           |   2 +-
 src/org.xtuml.bp.ui.explorer.test/build.properties |   3 +-
 src/org.xtuml.bp.ui.explorer.test/pom.xml          |  34 +++
 src/org.xtuml.bp.ui.explorer/META-INF/MANIFEST.MF  |   3 +-
 src/org.xtuml.bp.ui.explorer/pom.xml               |  43 +++
 src/org.xtuml.bp.ui.graphics/.classpath            |   2 +-
 src/org.xtuml.bp.ui.graphics/META-INF/MANIFEST.MF  |   2 +-
 src/org.xtuml.bp.ui.graphics/pom.xml               |  15 ++
 .../META-INF/MANIFEST.MF                           |   2 +-
 .../build.properties                               |   3 +-
 src/org.xtuml.bp.ui.properties.test/generate.xml   |   5 +
 src/org.xtuml.bp.ui.properties.test/pom.xml        |  61 +++++
 .../META-INF/MANIFEST.MF                           |   3 +-
 src/org.xtuml.bp.ui.properties/pom.xml             |  49 ++++
 src/org.xtuml.bp.ui.search/.classpath              |   2 +-
 src/org.xtuml.bp.ui.search/META-INF/MANIFEST.MF    |   2 +-
 src/org.xtuml.bp.ui.search/pom.xml                 |  15 ++
 src/org.xtuml.bp.ui.sem/META-INF/MANIFEST.MF       |   3 +-
 src/org.xtuml.bp.ui.sem/pom.xml                    |  15 ++
 src/org.xtuml.bp.ui.session/META-INF/MANIFEST.MF   |   3 +-
 src/org.xtuml.bp.ui.session/pom.xml                |  49 ++++
 src/org.xtuml.bp.ui.text.test/META-INF/MANIFEST.MF |   2 +-
 src/org.xtuml.bp.ui.text.test/pom.xml              |  31 +++
 src/org.xtuml.bp.ui.text/META-INF/MANIFEST.MF      |   3 +-
 src/org.xtuml.bp.ui.text/pom.xml                   |  49 ++++
 src/org.xtuml.bp.utilities/.classpath              |   2 +-
 src/org.xtuml.bp.utilities/META-INF/MANIFEST.MF    |   2 +-
 src/org.xtuml.bp.utilities/pom.xml                 |  15 ++
 src/org.xtuml.bp.verifier.pkg-feature/feature.xml  |   2 +-
 src/org.xtuml.bp.verifier.pkg-feature/pom.xml      |  15 ++
 src/org.xtuml.bp.verifier.pkg/META-INF/MANIFEST.MF |   1 +
 src/org.xtuml.bp.verifier.pkg/build.properties     |   1 -
 src/org.xtuml.bp.verifier.pkg/pom.xml              |  15 ++
 src/org.xtuml.bp.welcome.test/pom.xml              |  31 +++
 src/org.xtuml.bp.welcome/META-INF/MANIFEST.MF      |   3 +-
 src/org.xtuml.bp.welcome/pom.xml                   |  15 ++
 src/org.xtuml.bp.x2m/META-INF/MANIFEST.MF          |   3 +-
 src/org.xtuml.bp.x2m/pom.xml                       |  15 ++
 .../src/org/xtuml/bp/x2m/generator/Generator.java  |  14 +-
 src/org.xtuml.bp.xtext.masl.parent/pom.xml         |   4 +-
 src/org.xtuml.help.bp.mc/META-INF/MANIFEST.MF      |   1 +
 src/org.xtuml.help.bp.mc/README.txt                |   8 -
 src/org.xtuml.help.bp.mc/doc.zip                   | Bin 0 -> 461037 bytes
 src/org.xtuml.help.bp.mc/pom.xml                   |  15 ++
 src/org.xtuml.help.bp.mc/techpub.css               | 161 ++++++++++++
 src/org.xtuml.help.bp.mc/toc.xml                   | 292 ++++++++++++++++++++-
 src/org.xtuml.internal.test/META-INF/MANIFEST.MF   |   2 +-
 src/org.xtuml.internal.test/pom.xml                |  16 ++
 171 files changed, 3773 insertions(+), 130 deletions(-)

</pre>

Fork/Repository: keithbrown/mc   
Branch: 8495_maven_update   

<pre>
 bin/.project          |  11 ++++++
 bin/MASLParser.jar    | Bin 0 -> 287683 bytes
 bin/docgen            | Bin 0 -> 1295014 bytes
 bin/docgen.exe        | Bin 0 -> 651939 bytes
 bin/gen_erate.exe     | Bin 0 -> 5732889 bytes
 bin/gen_erate.pyz     | Bin 0 -> 312484 bytes
 bin/m2x               | Bin 0 -> 646585 bytes
 bin/masl              | Bin 0 -> 239623 bytes
 bin/masl2xtuml        |   2 +-
 bin/masldiff          |   2 +-
 bin/mcmc              | Bin 0 -> 1856419 bytes
 bin/mcmc.exe          | Bin 0 -> 1175308 bytes
 bin/mcmc64            | Bin 0 -> 2170101 bytes
 bin/x2m               | Bin 0 -> 680533 bytes
 bin/xtuml2masl        |   4 +--
 bin/xtumlmc_build     |  92 ++++++++++++++++++++++++++------------------------
 bin/xtumlmc_build.exe | Bin 0 -> 3995124 bytes
 17 files changed, 62 insertions(+), 49 deletions(-)


</pre>

Fork/Repository: keithbrown/packaging      
Branch: 8495_maven_update   

<pre>
 features/.gitignore                                |     5 +
 features/org.xtuml.bp.MinGW.parent/.project        |    11 +
 features/org.xtuml.bp.MinGW.parent/pom.xml         |    51 +
 features/org.xtuml.bp.MinGW/.project               |    17 +
 features/org.xtuml.bp.MinGW/build.properties       |     3 +
 features/org.xtuml.bp.MinGW/feature.xml            |    19 +
 features/org.xtuml.bp.MinGW/pom.xml                |    15 +
 .../org.xtuml.bp.MinGW/win32.all/MinGW/TECHPUB.CSS |   571 +
 .../win32.all/MinGW/bin/addr2line.exe              |   Bin 0 -> 559577 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/ar.exe  |   Bin 0 -> 525951 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/as.exe  |   Bin 0 -> 792360 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/c++.exe |   Bin 0 -> 92160 bytes
 .../win32.all/MinGW/bin/c++filt.exe                |   Bin 0 -> 558024 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/cpp.exe |   Bin 0 -> 91648 bytes
 .../win32.all/MinGW/bin/dlltool.exe                |   Bin 0 -> 611153 bytes
 .../win32.all/MinGW/bin/dllwrap.exe                |   Bin 0 -> 63213 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/g++.exe |   Bin 0 -> 92160 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/gcc.exe |   Bin 0 -> 89600 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/gccbug  |   555 +
 .../win32.all/MinGW/bin/gcov.exe                   |   Bin 0 -> 26112 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/gdb.exe |   Bin 0 -> 3598848 bytes
 .../win32.all/MinGW/bin/gprof.exe                  |   Bin 0 -> 625174 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/ld.exe  |   Bin 0 -> 806237 bytes
 .../win32.all/MinGW/bin/libexpat-1.dll             |   Bin 0 -> 165513 bytes
 .../win32.all/MinGW/bin/mingw32-c++.exe            |   Bin 0 -> 92160 bytes
 .../win32.all/MinGW/bin/mingw32-g++.exe            |   Bin 0 -> 92160 bytes
 .../win32.all/MinGW/bin/mingw32-gcc-3.4.5          |   Bin 0 -> 89600 bytes
 .../win32.all/MinGW/bin/mingw32-gcc.exe            |   Bin 0 -> 89600 bytes
 .../win32.all/MinGW/bin/mingw32-make.exe           |   Bin 0 -> 166400 bytes
 .../win32.all/MinGW/bin/mingwm10.dll               |   Bin 0 -> 15964 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/nm.exe  |   Bin 0 -> 570469 bytes
 .../win32.all/MinGW/bin/objcopy.exe                |   Bin 0 -> 717083 bytes
 .../win32.all/MinGW/bin/objdump.exe                |   Bin 0 -> 849949 bytes
 .../win32.all/MinGW/bin/ranlib.exe                 |   Bin 0 -> 525951 bytes
 .../win32.all/MinGW/bin/readelf.exe                |   Bin 0 -> 268447 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/bin/rm.exe  |   Bin 0 -> 65536 bytes
 .../win32.all/MinGW/bin/size.exe                   |   Bin 0 -> 508399 bytes
 .../win32.all/MinGW/bin/strings.exe                |   Bin 0 -> 507802 bytes
 .../win32.all/MinGW/bin/strip.exe                  |   Bin 0 -> 717083 bytes
 .../win32.all/MinGW/bin/windres.exe                |   Bin 0 -> 617067 bytes
 .../win32.all/MinGW/doc/mingw-runtime/CONTRIBUTORS |    28 +
 .../win32.all/MinGW/doc/mingw-runtime/DISCLAIMER   |    12 +
 .../win32.all/MinGW/doc/mingw-runtime/README       |    10 +
 .../win32.all/MinGW/doc/mingw32-make/COPYING       |   340 +
 .../win32.all/MinGW/doc/mingw32-make/README.mingw  |    14 +
 .../win32.all/MinGW/include/GL/gl.h                |  1470 +
 .../win32.all/MinGW/include/GL/glext.h             |  4623 ++++
 .../win32.all/MinGW/include/GL/glu.h               |   289 +
 .../win32.all/MinGW/include/_mingw.h               |   195 +
 .../win32.all/MinGW/include/accctrl.h              |   329 +
 .../win32.all/MinGW/include/aclapi.h               |   117 +
 .../win32.all/MinGW/include/aclui.h                |   134 +
 .../win32.all/MinGW/include/adsprop.h              |    59 +
 .../win32.all/MinGW/include/afxres.h               |    23 +
 .../win32.all/MinGW/include/amaudio.h              |    33 +
 .../win32.all/MinGW/include/amvideo.h              |   179 +
 .../win32.all/MinGW/include/ansidecl.h             |   371 +
 .../win32.all/MinGW/include/assert.h               |    51 +
 .../win32.all/MinGW/include/audevcod.h             |    31 +
 .../win32.all/MinGW/include/aviriff.h              |    87 +
 .../win32.all/MinGW/include/aygshell.h             |    49 +
 .../win32.all/MinGW/include/basetsd.h              |   119 +
 .../win32.all/MinGW/include/basetyps.h             |   182 +
 .../win32.all/MinGW/include/bdatypes.h             |    32 +
 .../win32.all/MinGW/include/bfd.h                  |  5206 ++++
 .../win32.all/MinGW/include/bfdlink.h              |   727 +
 .../win32.all/MinGW/include/c++/3.4.5/algorithm    |    71 +
 .../MinGW/include/c++/3.4.5/backward/algo.h        |   149 +
 .../MinGW/include/c++/3.4.5/backward/algobase.h    |    95 +
 .../MinGW/include/c++/3.4.5/backward/alloc.h       |    52 +
 .../include/c++/3.4.5/backward/backward_warning.h  |    39 +
 .../MinGW/include/c++/3.4.5/backward/bvector.h     |    68 +
 .../MinGW/include/c++/3.4.5/backward/complex.h     |    43 +
 .../MinGW/include/c++/3.4.5/backward/defalloc.h    |   117 +
 .../MinGW/include/c++/3.4.5/backward/deque.h       |    70 +
 .../MinGW/include/c++/3.4.5/backward/fstream.h     |    52 +
 .../MinGW/include/c++/3.4.5/backward/function.h    |   130 +
 .../MinGW/include/c++/3.4.5/backward/hash_map.h    |    72 +
 .../MinGW/include/c++/3.4.5/backward/hash_set.h    |    69 +
 .../MinGW/include/c++/3.4.5/backward/hashtable.h   |    76 +
 .../MinGW/include/c++/3.4.5/backward/heap.h        |    71 +
 .../MinGW/include/c++/3.4.5/backward/iomanip.h     |    70 +
 .../MinGW/include/c++/3.4.5/backward/iostream.h    |    60 +
 .../MinGW/include/c++/3.4.5/backward/istream.h     |    43 +
 .../MinGW/include/c++/3.4.5/backward/iterator.h    |   191 +
 .../MinGW/include/c++/3.4.5/backward/list.h        |    70 +
 .../MinGW/include/c++/3.4.5/backward/map.h         |    69 +
 .../MinGW/include/c++/3.4.5/backward/multimap.h    |    69 +
 .../MinGW/include/c++/3.4.5/backward/multiset.h    |    69 +
 .../MinGW/include/c++/3.4.5/backward/new.h         |    42 +
 .../MinGW/include/c++/3.4.5/backward/ostream.h     |    38 +
 .../MinGW/include/c++/3.4.5/backward/pair.h        |    70 +
 .../MinGW/include/c++/3.4.5/backward/queue.h       |    41 +
 .../MinGW/include/c++/3.4.5/backward/rope.h        |    60 +
 .../MinGW/include/c++/3.4.5/backward/set.h         |    69 +
 .../MinGW/include/c++/3.4.5/backward/slist.h       |    56 +
 .../MinGW/include/c++/3.4.5/backward/stack.h       |    72 +
 .../MinGW/include/c++/3.4.5/backward/stream.h      |    38 +
 .../MinGW/include/c++/3.4.5/backward/streambuf.h   |    40 +
 .../MinGW/include/c++/3.4.5/backward/strstream     |   179 +
 .../MinGW/include/c++/3.4.5/backward/tempbuf.h     |    78 +
 .../MinGW/include/c++/3.4.5/backward/tree.h        |    55 +
 .../MinGW/include/c++/3.4.5/backward/vector.h      |    70 +
 .../MinGW/include/c++/3.4.5/bits/allocator.h       |   130 +
 .../MinGW/include/c++/3.4.5/bits/atomicity.h       |    46 +
 .../MinGW/include/c++/3.4.5/bits/basic_ios.h       |   467 +
 .../MinGW/include/c++/3.4.5/bits/basic_ios.tcc     |   200 +
 .../MinGW/include/c++/3.4.5/bits/basic_string.h    |  2365 ++
 .../MinGW/include/c++/3.4.5/bits/basic_string.tcc  |   975 +
 .../include/c++/3.4.5/bits/boost_concept_check.h   |   932 +
 .../MinGW/include/c++/3.4.5/bits/char_traits.h     |   376 +
 .../MinGW/include/c++/3.4.5/bits/cmath.tcc         |    54 +
 .../MinGW/include/c++/3.4.5/bits/codecvt.h         |   478 +
 .../MinGW/include/c++/3.4.5/bits/concept_check.h   |    85 +
 .../MinGW/include/c++/3.4.5/bits/concurrence.h     |    95 +
 .../MinGW/include/c++/3.4.5/bits/cpp_type_traits.h |   345 +
 .../MinGW/include/c++/3.4.5/bits/deque.tcc         |   719 +
 .../MinGW/include/c++/3.4.5/bits/fstream.tcc       |   891 +
 .../MinGW/include/c++/3.4.5/bits/functexcept.h     |    85 +
 .../MinGW/include/c++/3.4.5/bits/gslice.h          |   165 +
 .../MinGW/include/c++/3.4.5/bits/gslice_array.h    |   220 +
 .../MinGW/include/c++/3.4.5/bits/indirect_array.h  |   212 +
 .../MinGW/include/c++/3.4.5/bits/ios_base.h        |   969 +
 .../MinGW/include/c++/3.4.5/bits/istream.tcc       |  1192 +
 .../MinGW/include/c++/3.4.5/bits/list.tcc          |   377 +
 .../MinGW/include/c++/3.4.5/bits/locale_classes.h  |   599 +
 .../MinGW/include/c++/3.4.5/bits/locale_facets.h   |  4558 ++++
 .../MinGW/include/c++/3.4.5/bits/locale_facets.tcc |  2797 ++
 .../MinGW/include/c++/3.4.5/bits/localefwd.h       |   192 +
 .../MinGW/include/c++/3.4.5/bits/mask_array.h      |   209 +
 .../MinGW/include/c++/3.4.5/bits/ostream.tcc       |   699 +
 .../MinGW/include/c++/3.4.5/bits/postypes.h        |   215 +
 .../MinGW/include/c++/3.4.5/bits/slice_array.h     |   273 +
 .../MinGW/include/c++/3.4.5/bits/sstream.tcc       |   227 +
 .../MinGW/include/c++/3.4.5/bits/stl_algo.h        |  5148 ++++
 .../MinGW/include/c++/3.4.5/bits/stl_algobase.h    |   842 +
 .../MinGW/include/c++/3.4.5/bits/stl_bvector.h     |   876 +
 .../MinGW/include/c++/3.4.5/bits/stl_construct.h   |   157 +
 .../MinGW/include/c++/3.4.5/bits/stl_deque.h       |  1501 +
 .../MinGW/include/c++/3.4.5/bits/stl_function.h    |   898 +
 .../MinGW/include/c++/3.4.5/bits/stl_heap.h        |   467 +
 .../MinGW/include/c++/3.4.5/bits/stl_iterator.h    |   772 +
 .../c++/3.4.5/bits/stl_iterator_base_funcs.h       |   179 +
 .../c++/3.4.5/bits/stl_iterator_base_types.h       |   170 +
 .../MinGW/include/c++/3.4.5/bits/stl_list.h        |  1255 +
 .../MinGW/include/c++/3.4.5/bits/stl_map.h         |   694 +
 .../MinGW/include/c++/3.4.5/bits/stl_multimap.h    |   677 +
 .../MinGW/include/c++/3.4.5/bits/stl_multiset.h    |   585 +
 .../MinGW/include/c++/3.4.5/bits/stl_numeric.h     |   326 +
 .../MinGW/include/c++/3.4.5/bits/stl_pair.h        |   147 +
 .../MinGW/include/c++/3.4.5/bits/stl_queue.h       |   472 +
 .../include/c++/3.4.5/bits/stl_raw_storage_iter.h  |   113 +
 .../MinGW/include/c++/3.4.5/bits/stl_relops.h      |   137 +
 .../MinGW/include/c++/3.4.5/bits/stl_set.h         |   593 +
 .../MinGW/include/c++/3.4.5/bits/stl_stack.h       |   272 +
 .../MinGW/include/c++/3.4.5/bits/stl_tempbuf.h     |   171 +
 .../MinGW/include/c++/3.4.5/bits/stl_threads.h     |   150 +
 .../MinGW/include/c++/3.4.5/bits/stl_tree.h        |  1283 +
 .../include/c++/3.4.5/bits/stl_uninitialized.h     |   297 +
 .../MinGW/include/c++/3.4.5/bits/stl_vector.h      |   932 +
 .../MinGW/include/c++/3.4.5/bits/stream_iterator.h |   214 +
 .../MinGW/include/c++/3.4.5/bits/streambuf.tcc     |   163 +
 .../include/c++/3.4.5/bits/streambuf_iterator.h    |   258 +
 .../MinGW/include/c++/3.4.5/bits/stringfwd.h       |    69 +
 .../MinGW/include/c++/3.4.5/bits/type_traits.h     |   405 +
 .../MinGW/include/c++/3.4.5/bits/valarray_after.h  |   499 +
 .../MinGW/include/c++/3.4.5/bits/valarray_array.h  |   625 +
 .../include/c++/3.4.5/bits/valarray_array.tcc      |   240 +
 .../MinGW/include/c++/3.4.5/bits/valarray_before.h |   701 +
 .../MinGW/include/c++/3.4.5/bits/vector.tcc        |   414 +
 .../win32.all/MinGW/include/c++/3.4.5/bitset       |  1229 +
 .../win32.all/MinGW/include/c++/3.4.5/cassert      |    48 +
 .../win32.all/MinGW/include/c++/3.4.5/cctype       |    83 +
 .../win32.all/MinGW/include/c++/3.4.5/cerrno       |    55 +
 .../win32.all/MinGW/include/c++/3.4.5/cfloat       |    50 +
 .../win32.all/MinGW/include/c++/3.4.5/ciso646      |    37 +
 .../win32.all/MinGW/include/c++/3.4.5/climits      |    51 +
 .../win32.all/MinGW/include/c++/3.4.5/clocale      |    62 +
 .../win32.all/MinGW/include/c++/3.4.5/cmath        |   595 +
 .../win32.all/MinGW/include/c++/3.4.5/complex      |  1226 +
 .../win32.all/MinGW/include/c++/3.4.5/csetjmp      |    65 +
 .../win32.all/MinGW/include/c++/3.4.5/csignal      |    61 +
 .../win32.all/MinGW/include/c++/3.4.5/cstdarg      |    60 +
 .../win32.all/MinGW/include/c++/3.4.5/cstddef      |    56 +
 .../win32.all/MinGW/include/c++/3.4.5/cstdio       |   185 +
 .../win32.all/MinGW/include/c++/3.4.5/cstdlib      |   204 +
 .../win32.all/MinGW/include/c++/3.4.5/cstring      |   128 +
 .../win32.all/MinGW/include/c++/3.4.5/ctime        |    81 +
 .../win32.all/MinGW/include/c++/3.4.5/cwchar       |   273 +
 .../win32.all/MinGW/include/c++/3.4.5/cwctype      |   110 +
 .../win32.all/MinGW/include/c++/3.4.5/cxxabi.h     |   528 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/bitset |   299 +
 .../MinGW/include/c++/3.4.5/debug/debug.h          |   531 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/deque  |   386 +
 .../MinGW/include/c++/3.4.5/debug/formatter.h      |   391 +
 .../MinGW/include/c++/3.4.5/debug/hash_map         |    38 +
 .../MinGW/include/c++/3.4.5/debug/hash_map.h       |   270 +
 .../MinGW/include/c++/3.4.5/debug/hash_multimap.h  |   261 +
 .../MinGW/include/c++/3.4.5/debug/hash_multiset.h  |   236 +
 .../MinGW/include/c++/3.4.5/debug/hash_set         |    38 +
 .../MinGW/include/c++/3.4.5/debug/hash_set.h       |   245 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/list   |   505 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/map    |    38 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/map.h  |   323 +
 .../MinGW/include/c++/3.4.5/debug/multimap.h       |   314 +
 .../MinGW/include/c++/3.4.5/debug/multiset.h       |   320 +
 .../MinGW/include/c++/3.4.5/debug/safe_base.h      |   207 +
 .../MinGW/include/c++/3.4.5/debug/safe_iterator.h  |   618 +
 .../include/c++/3.4.5/debug/safe_iterator.tcc      |   140 +
 .../MinGW/include/c++/3.4.5/debug/safe_sequence.h  |   180 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/set    |    38 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/set.h  |   325 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/string |  1001 +
 .../win32.all/MinGW/include/c++/3.4.5/debug/vector |   412 +
 .../win32.all/MinGW/include/c++/3.4.5/deque        |    81 +
 .../win32.all/MinGW/include/c++/3.4.5/exception    |   120 +
 .../MinGW/include/c++/3.4.5/exception_defines.h    |    47 +
 .../MinGW/include/c++/3.4.5/ext/algorithm          |   518 +
 .../MinGW/include/c++/3.4.5/ext/bitmap_allocator.h |   859 +
 .../MinGW/include/c++/3.4.5/ext/debug_allocator.h  |   121 +
 .../MinGW/include/c++/3.4.5/ext/enc_filebuf.h      |    68 +
 .../MinGW/include/c++/3.4.5/ext/functional         |   395 +
 .../MinGW/include/c++/3.4.5/ext/hash_fun.h         |   122 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/hash_map |   447 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/hash_set |   439 +
 .../MinGW/include/c++/3.4.5/ext/hashtable.h        |   994 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/iterator |   113 +
 .../MinGW/include/c++/3.4.5/ext/malloc_allocator.h |   118 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/memory   |   171 +
 .../MinGW/include/c++/3.4.5/ext/mt_allocator.h     |   718 +
 .../MinGW/include/c++/3.4.5/ext/new_allocator.h    |   113 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/numeric  |   148 +
 .../MinGW/include/c++/3.4.5/ext/pod_char_traits.h  |   158 +
 .../MinGW/include/c++/3.4.5/ext/pool_allocator.h   |   255 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/rb_tree  |    97 +
 .../win32.all/MinGW/include/c++/3.4.5/ext/rope     |  2494 ++
 .../MinGW/include/c++/3.4.5/ext/ropeimpl.h         |  1539 ++
 .../win32.all/MinGW/include/c++/3.4.5/ext/slist    |   906 +
 .../MinGW/include/c++/3.4.5/ext/stdio_filebuf.h    |   162 +
 .../include/c++/3.4.5/ext/stdio_sync_filebuf.h     |   281 +
 .../win32.all/MinGW/include/c++/3.4.5/fstream      |   843 +
 .../win32.all/MinGW/include/c++/3.4.5/functional   |    58 +
 .../win32.all/MinGW/include/c++/3.4.5/iomanip      |   300 +
 .../win32.all/MinGW/include/c++/3.4.5/ios          |    53 +
 .../win32.all/MinGW/include/c++/3.4.5/iosfwd       |   168 +
 .../win32.all/MinGW/include/c++/3.4.5/iostream     |    80 +
 .../win32.all/MinGW/include/c++/3.4.5/istream      |   774 +
 .../win32.all/MinGW/include/c++/3.4.5/iterator     |    76 +
 .../win32.all/MinGW/include/c++/3.4.5/limits       |  1143 +
 .../win32.all/MinGW/include/c++/3.4.5/list         |    82 +
 .../win32.all/MinGW/include/c++/3.4.5/locale       |    49 +
 .../win32.all/MinGW/include/c++/3.4.5/map          |    74 +
 .../win32.all/MinGW/include/c++/3.4.5/memory       |   375 +
 .../include/c++/3.4.5/mingw32/bits/atomic_word.h   |    35 +
 .../include/c++/3.4.5/mingw32/bits/basic_file.h    |   110 +
 .../include/c++/3.4.5/mingw32/bits/c++allocator.h  |    37 +
 .../include/c++/3.4.5/mingw32/bits/c++config.h     |  1297 +
 .../MinGW/include/c++/3.4.5/mingw32/bits/c++io.h   |    86 +
 .../include/c++/3.4.5/mingw32/bits/c++locale.h     |    86 +
 .../3.4.5/mingw32/bits/codecvt_specializations.h   |    38 +
 .../include/c++/3.4.5/mingw32/bits/ctype_base.h    |    58 +
 .../include/c++/3.4.5/mingw32/bits/ctype_inline.h  |    71 +
 .../c++/3.4.5/mingw32/bits/ctype_noninline.h       |    99 +
 .../include/c++/3.4.5/mingw32/bits/gthr-default.h  |   621 +
 .../include/c++/3.4.5/mingw32/bits/gthr-posix.h    |   517 +
 .../include/c++/3.4.5/mingw32/bits/gthr-single.h   |   239 +
 .../MinGW/include/c++/3.4.5/mingw32/bits/gthr.h    |   103 +
 .../c++/3.4.5/mingw32/bits/messages_members.h      |    84 +
 .../include/c++/3.4.5/mingw32/bits/os_defines.h    |    55 +
 .../include/c++/3.4.5/mingw32/bits/time_members.h  |    71 +
 .../win32.all/MinGW/include/c++/3.4.5/new          |   101 +
 .../win32.all/MinGW/include/c++/3.4.5/numeric      |    72 +
 .../win32.all/MinGW/include/c++/3.4.5/ostream      |   548 +
 .../win32.all/MinGW/include/c++/3.4.5/queue        |    78 +
 .../win32.all/MinGW/include/c++/3.4.5/set          |    74 +
 .../win32.all/MinGW/include/c++/3.4.5/sstream      |   643 +
 .../win32.all/MinGW/include/c++/3.4.5/stack        |    73 +
 .../win32.all/MinGW/include/c++/3.4.5/stdexcept    |   148 +
 .../win32.all/MinGW/include/c++/3.4.5/streambuf    |   784 +
 .../win32.all/MinGW/include/c++/3.4.5/string       |    60 +
 .../win32.all/MinGW/include/c++/3.4.5/typeinfo     |   156 +
 .../win32.all/MinGW/include/c++/3.4.5/utility      |    70 +
 .../win32.all/MinGW/include/c++/3.4.5/valarray     |  1017 +
 .../win32.all/MinGW/include/c++/3.4.5/vector       |    83 +
 .../win32.all/MinGW/include/cderr.h                |    44 +
 .../win32.all/MinGW/include/cguid.h                |    83 +
 .../win32.all/MinGW/include/cmnquery.h             |    81 +
 .../win32.all/MinGW/include/comcat.h               |   175 +
 .../win32.all/MinGW/include/commctrl.h             |  3742 +++
 .../win32.all/MinGW/include/commdlg.h              |   609 +
 .../win32.all/MinGW/include/complex.h              |   205 +
 .../win32.all/MinGW/include/conio.h                |    53 +
 .../win32.all/MinGW/include/control.h              |    18 +
 .../win32.all/MinGW/include/cpl.h                  |    61 +
 .../win32.all/MinGW/include/cplext.h               |    12 +
 .../win32.all/MinGW/include/ctype.h                |   275 +
 .../win32.all/MinGW/include/custcntl.h             |   102 +
 .../win32.all/MinGW/include/d3d9.h                 |  1288 +
 .../win32.all/MinGW/include/d3d9caps.h             |   338 +
 .../win32.all/MinGW/include/d3d9types.h            |  1272 +
 .../win32.all/MinGW/include/dbt.h                  |   154 +
 .../win32.all/MinGW/include/dde.h                  |    64 +
 .../win32.all/MinGW/include/ddeml.h                |   314 +
 .../win32.all/MinGW/include/ddk/atm.h              |   507 +
 .../win32.all/MinGW/include/ddk/batclass.h         |   298 +
 .../win32.all/MinGW/include/ddk/cfg.h              |   139 +
 .../win32.all/MinGW/include/ddk/cfgmgr32.h         |  1533 ++
 .../win32.all/MinGW/include/ddk/d4drvif.h          |   104 +
 .../win32.all/MinGW/include/ddk/d4iface.h          |    84 +
 .../win32.all/MinGW/include/ddk/ddkmapi.h          |   334 +
 .../win32.all/MinGW/include/ddk/hidclass.h         |   153 +
 .../win32.all/MinGW/include/ddk/hidpi.h            |   604 +
 .../win32.all/MinGW/include/ddk/hidsdi.h           |    73 +
 .../win32.all/MinGW/include/ddk/hidusage.h         |   210 +
 .../win32.all/MinGW/include/ddk/kbdmou.h           |    91 +
 .../win32.all/MinGW/include/ddk/mcd.h              |   143 +
 .../win32.all/MinGW/include/ddk/miniport.h         |    77 +
 .../win32.all/MinGW/include/ddk/minitape.h         |   223 +
 .../win32.all/MinGW/include/ddk/mountdev.h         |    79 +
 .../win32.all/MinGW/include/ddk/mountmgr.h         |   139 +
 .../win32.all/MinGW/include/ddk/ndis.h             |  5227 ++++
 .../win32.all/MinGW/include/ddk/ndisguid.h         |   439 +
 .../win32.all/MinGW/include/ddk/ndistapi.h         |  1308 +
 .../win32.all/MinGW/include/ddk/ndiswan.h          |   251 +
 .../win32.all/MinGW/include/ddk/netevent.h         |    42 +
 .../win32.all/MinGW/include/ddk/netpnp.h           |    69 +
 .../win32.all/MinGW/include/ddk/newdev.h           |    66 +
 .../win32.all/MinGW/include/ddk/ntapi.h            |  2906 ++
 .../win32.all/MinGW/include/ddk/ntdd8042.h         |   213 +
 .../win32.all/MinGW/include/ddk/ntddbeep.h         |    54 +
 .../win32.all/MinGW/include/ddk/ntddcdrm.h         |   347 +
 .../win32.all/MinGW/include/ddk/ntddcdvd.h         |   213 +
 .../win32.all/MinGW/include/ddk/ntddchgr.h         |   353 +
 .../win32.all/MinGW/include/ddk/ntdddisk.h         |   521 +
 .../win32.all/MinGW/include/ddk/ntddk.h            |    91 +
 .../win32.all/MinGW/include/ddk/ntddkbd.h          |   135 +
 .../win32.all/MinGW/include/ddk/ntddmou.h          |   115 +
 .../win32.all/MinGW/include/ddk/ntddndis.h         |   188 +
 .../win32.all/MinGW/include/ddk/ntddpar.h          |   119 +
 .../win32.all/MinGW/include/ddk/ntddpcm.h          |   165 +
 .../win32.all/MinGW/include/ddk/ntddscsi.h         |   171 +
 .../win32.all/MinGW/include/ddk/ntddser.h          |   449 +
 .../win32.all/MinGW/include/ddk/ntddstor.h         |   333 +
 .../win32.all/MinGW/include/ddk/ntddtape.h         |    79 +
 .../win32.all/MinGW/include/ddk/ntddtdi.h          |    61 +
 .../win32.all/MinGW/include/ddk/ntddvdeo.h         |   440 +
 .../win32.all/MinGW/include/ddk/ntddvol.h          |   141 +
 .../win32.all/MinGW/include/ddk/ntifs.h            |  4726 ++++
 .../win32.all/MinGW/include/ddk/ntpoapi.h          |   229 +
 .../win32.all/MinGW/include/ddk/ntstatus.h         |  1105 +
 .../win32.all/MinGW/include/ddk/parallel.h         |   277 +
 .../win32.all/MinGW/include/ddk/pfhook.h           |    76 +
 .../win32.all/MinGW/include/ddk/poclass.h          |   118 +
 .../win32.all/MinGW/include/ddk/scsi.h             |  1694 ++
 .../win32.all/MinGW/include/ddk/scsiscan.h         |   130 +
 .../win32.all/MinGW/include/ddk/scsiwmi.h          |   215 +
 .../win32.all/MinGW/include/ddk/smbus.h            |   190 +
 .../win32.all/MinGW/include/ddk/srb.h              |   753 +
 .../win32.all/MinGW/include/ddk/storport.h         |   422 +
 .../win32.all/MinGW/include/ddk/tdi.h              |   593 +
 .../win32.all/MinGW/include/ddk/tdiinfo.h          |   110 +
 .../win32.all/MinGW/include/ddk/tdikrnl.h          |  1162 +
 .../win32.all/MinGW/include/ddk/tdistat.h          |    83 +
 .../win32.all/MinGW/include/ddk/tvout.h            |   116 +
 .../win32.all/MinGW/include/ddk/upssvc.h           |    94 +
 .../win32.all/MinGW/include/ddk/usb.h              |   471 +
 .../win32.all/MinGW/include/ddk/usb100.h           |   237 +
 .../win32.all/MinGW/include/ddk/usbcamdi.h         |   404 +
 .../win32.all/MinGW/include/ddk/usbdi.h            |   407 +
 .../win32.all/MinGW/include/ddk/usbioctl.h         |   353 +
 .../win32.all/MinGW/include/ddk/usbiodef.h         |   106 +
 .../win32.all/MinGW/include/ddk/usbscan.h          |   158 +
 .../win32.all/MinGW/include/ddk/usbuser.h          |   328 +
 .../win32.all/MinGW/include/ddk/video.h            |  1566 ++
 .../win32.all/MinGW/include/ddk/videoagp.h         |   129 +
 .../win32.all/MinGW/include/ddk/win2k.h            |   106 +
 .../win32.all/MinGW/include/ddk/winddi.h           |  4258 +++
 .../win32.all/MinGW/include/ddk/winddk.h           |  9249 +++++++
 .../win32.all/MinGW/include/ddk/winnt4.h           |   623 +
 .../win32.all/MinGW/include/ddk/winxp.h            |    38 +
 .../win32.all/MinGW/include/ddk/ws2san.h           |   248 +
 .../win32.all/MinGW/include/ddk/xfilter.h          |   239 +
 .../win32.all/MinGW/include/devguid.h              |    65 +
 .../win32.all/MinGW/include/dhcpcsdk.h             |    42 +
 .../win32.all/MinGW/include/dir.h                  |    26 +
 .../win32.all/MinGW/include/direct.h               |    73 +
 .../win32.all/MinGW/include/dirent.h               |   123 +
 .../win32.all/MinGW/include/dis-asm.h              |   339 +
 .../win32.all/MinGW/include/dlgs.h                 |   186 +
 .../win32.all/MinGW/include/docobj.h               |   147 +
 .../win32.all/MinGW/include/dos.h                  |    89 +
 .../win32.all/MinGW/include/dsadmin.h              |    38 +
 .../win32.all/MinGW/include/dsclient.h             |   186 +
 .../win32.all/MinGW/include/dsgetdc.h              |    98 +
 .../win32.all/MinGW/include/dshow.h                |   108 +
 .../win32.all/MinGW/include/dsquery.h              |    72 +
 .../win32.all/MinGW/include/dsrole.h               |    75 +
 .../win32.all/MinGW/include/dvdevcod.h             |    76 +
 .../win32.all/MinGW/include/dvdmedia.h             |   101 +
 .../win32.all/MinGW/include/dxerr8.h               |    53 +
 .../win32.all/MinGW/include/dxerr9.h               |    53 +
 .../win32.all/MinGW/include/edevdefs.h             |   346 +
 .../win32.all/MinGW/include/errno.h                |   101 +
 .../win32.all/MinGW/include/errorrep.h             |    40 +
 .../win32.all/MinGW/include/errors.h               |   169 +
 .../win32.all/MinGW/include/evcode.h               |    68 +
 .../win32.all/MinGW/include/excpt.h                |   102 +
 .../win32.all/MinGW/include/exdisp.h               |   225 +
 .../win32.all/MinGW/include/exdispid.h             |    14 +
 .../win32.all/MinGW/include/fcntl.h                |    75 +
 .../win32.all/MinGW/include/fenv.h                 |   107 +
 .../win32.all/MinGW/include/float.h                |   152 +
 .../win32.all/MinGW/include/fltdefs.h              |    50 +
 .../win32.all/MinGW/include/getopt.h               |    84 +
 .../win32.all/MinGW/include/gmon.h                 |   181 +
 .../win32.all/MinGW/include/httpext.h              |    98 +
 .../win32.all/MinGW/include/icm.h                  |   399 +
 .../win32.all/MinGW/include/idispids.h             |    10 +
 .../win32.all/MinGW/include/il21dec.h              |    35 +
 .../win32.all/MinGW/include/imagehlp.h             |   329 +
 .../win32.all/MinGW/include/imm.h                  |   452 +
 .../win32.all/MinGW/include/initguid.h             |    12 +
 .../win32.all/MinGW/include/intshcut.h             |    82 +
 .../win32.all/MinGW/include/inttypes.h             |   278 +
 .../win32.all/MinGW/include/io.h                   |   342 +
 .../win32.all/MinGW/include/ipexport.h             |    88 +
 .../win32.all/MinGW/include/iphlpapi.h             |    74 +
 .../win32.all/MinGW/include/ipifcons.h             |   199 +
 .../win32.all/MinGW/include/ipinfoid.h             |    43 +
 .../win32.all/MinGW/include/iprtrmib.h             |   209 +
 .../win32.all/MinGW/include/iptypes.h              |   229 +
 .../win32.all/MinGW/include/ipxconst.h             |    36 +
 .../win32.all/MinGW/include/ipxrtdef.h             |    58 +
 .../win32.all/MinGW/include/ipxtfflt.h             |    38 +
 .../win32.all/MinGW/include/isguids.h              |    15 +
 .../win32.all/MinGW/include/ks.h                   |    20 +
 .../win32.all/MinGW/include/ksmedia.h              |    23 +
 .../win32.all/MinGW/include/largeint.h             |   112 +
 .../win32.all/MinGW/include/libgen.h               |    31 +
 .../win32.all/MinGW/include/limits.h               |   112 +
 .../win32.all/MinGW/include/lm.h                   |    27 +
 .../win32.all/MinGW/include/lmaccess.h             |   610 +
 .../win32.all/MinGW/include/lmalert.h              |    60 +
 .../win32.all/MinGW/include/lmapibuf.h             |    18 +
 .../win32.all/MinGW/include/lmat.h                 |    39 +
 .../win32.all/MinGW/include/lmaudit.h              |   250 +
 .../win32.all/MinGW/include/lmbrowsr.h             |    74 +
 .../win32.all/MinGW/include/lmchdev.h              |    61 +
 .../win32.all/MinGW/include/lmconfig.h             |    21 +
 .../win32.all/MinGW/include/lmcons.h               |    75 +
 .../win32.all/MinGW/include/lmerr.h                |   306 +
 .../win32.all/MinGW/include/lmerrlog.h             |   211 +
 .../win32.all/MinGW/include/lmmsg.h                |    27 +
 .../win32.all/MinGW/include/lmremutl.h             |    45 +
 .../win32.all/MinGW/include/lmrepl.h               |    94 +
 .../win32.all/MinGW/include/lmserver.h             |   606 +
 .../win32.all/MinGW/include/lmshare.h              |   147 +
 .../win32.all/MinGW/include/lmsname.h              |    58 +
 .../win32.all/MinGW/include/lmstats.h              |   114 +
 .../win32.all/MinGW/include/lmsvc.h                |   134 +
 .../win32.all/MinGW/include/lmuse.h                |    60 +
 .../win32.all/MinGW/include/lmuseflg.h             |    10 +
 .../win32.all/MinGW/include/lmwksta.h              |   233 +
 .../win32.all/MinGW/include/locale.h               |    88 +
 .../win32.all/MinGW/include/lzexpand.h             |    40 +
 .../win32.all/MinGW/include/malloc.h               |   103 +
 .../win32.all/MinGW/include/mapi.h                 |   163 +
 .../win32.all/MinGW/include/math.h                 |   848 +
 .../win32.all/MinGW/include/mbctype.h              |    97 +
 .../win32.all/MinGW/include/mbstring.h             |   132 +
 .../win32.all/MinGW/include/mciavi.h               |    25 +
 .../win32.all/MinGW/include/mcx.h                  |    77 +
 .../win32.all/MinGW/include/mem.h                  |     6 +
 .../win32.all/MinGW/include/memory.h               |     7 +
 .../win32.all/MinGW/include/mgm.h                  |    75 +
 .../win32.all/MinGW/include/mgmtapi.h              |    58 +
 .../win32.all/MinGW/include/mlang.h                |   349 +
 .../win32.all/MinGW/include/mmreg.h                |    71 +
 .../win32.all/MinGW/include/mmsystem.h             |  1943 ++
 .../win32.all/MinGW/include/mpegtype.h             |    29 +
 .../win32.all/MinGW/include/mprapi.h               |   572 +
 .../win32.all/MinGW/include/mq.h                   |   534 +
 .../win32.all/MinGW/include/msacm.h                |   186 +
 .../win32.all/MinGW/include/mshtml.h               |   704 +
 .../win32.all/MinGW/include/mswsock.h              |   113 +
 .../win32.all/MinGW/include/nb30.h                 |   186 +
 .../win32.all/MinGW/include/nddeapi.h              |   133 +
 .../win32.all/MinGW/include/nspapi.h               |   126 +
 .../win32.all/MinGW/include/ntdef.h                |    60 +
 .../win32.all/MinGW/include/ntdll.h                |    15 +
 .../win32.all/MinGW/include/ntdsapi.h              |   115 +
 .../win32.all/MinGW/include/ntdsbcli.h             |    45 +
 .../win32.all/MinGW/include/ntldap.h               |    60 +
 .../win32.all/MinGW/include/ntsecapi.h             |   612 +
 .../win32.all/MinGW/include/ntsecpkg.h             |    44 +
 .../win32.all/MinGW/include/oaidl.h                |   778 +
 .../win32.all/MinGW/include/objbase.h              |   203 +
 .../win32.all/MinGW/include/objfwd.h               |    57 +
 .../win32.all/MinGW/include/objidl.h               |  1771 ++
 .../win32.all/MinGW/include/objsafe.h              |    26 +
 .../win32.all/MinGW/include/objsel.h               |   118 +
 .../win32.all/MinGW/include/ocidl.h                |   837 +
 .../win32.all/MinGW/include/odbcinst.h             |   148 +
 .../win32.all/MinGW/include/ole.h                  |   308 +
 .../win32.all/MinGW/include/ole2.h                 |   110 +
 .../win32.all/MinGW/include/ole2ver.h              |     8 +
 .../win32.all/MinGW/include/oleacc.h               |   219 +
 .../win32.all/MinGW/include/oleauto.h              |   656 +
 .../win32.all/MinGW/include/olectl.h               |   323 +
 .../win32.all/MinGW/include/olectlid.h             |   114 +
 .../win32.all/MinGW/include/oledlg.h               |   935 +
 .../win32.all/MinGW/include/oleidl.h               |   575 +
 .../win32.all/MinGW/include/pbt.h                  |    24 +
 .../win32.all/MinGW/include/poppack.h              |     3 +
 .../win32.all/MinGW/include/powrprof.h             |   120 +
 .../win32.all/MinGW/include/process.h              |   138 +
 .../win32.all/MinGW/include/profil.h               |    51 +
 .../win32.all/MinGW/include/profile.h              |    83 +
 .../win32.all/MinGW/include/prsht.h                |   304 +
 .../win32.all/MinGW/include/psapi.h                |    95 +
 .../win32.all/MinGW/include/pshpack1.h             |     3 +
 .../win32.all/MinGW/include/pshpack2.h             |     3 +
 .../win32.all/MinGW/include/pshpack4.h             |     3 +
 .../win32.all/MinGW/include/pshpack8.h             |     3 +
 .../win32.all/MinGW/include/qedit.h                |    70 +
 .../win32.all/MinGW/include/rapi.h                 |    54 +
 .../win32.all/MinGW/include/ras.h                  |   964 +
 .../win32.all/MinGW/include/rasdlg.h               |   152 +
 .../win32.all/MinGW/include/raserror.h             |   210 +
 .../win32.all/MinGW/include/rassapi.h              |   182 +
 .../win32.all/MinGW/include/reason.h               |    47 +
 .../win32.all/MinGW/include/regstr.h               |   769 +
 .../win32.all/MinGW/include/richedit.h             |   523 +
 .../win32.all/MinGW/include/richole.h              |   107 +
 .../win32.all/MinGW/include/routprot.h             |    70 +
 .../win32.all/MinGW/include/rpc.h                  |    65 +
 .../win32.all/MinGW/include/rpcdce.h               |   396 +
 .../win32.all/MinGW/include/rpcdce2.h              |    56 +
 .../win32.all/MinGW/include/rpcdcep.h              |   129 +
 .../win32.all/MinGW/include/rpcndr.h               |   518 +
 .../win32.all/MinGW/include/rpcnsi.h               |   122 +
 .../win32.all/MinGW/include/rpcnsip.h              |    25 +
 .../win32.all/MinGW/include/rpcnterr.h             |    23 +
 .../win32.all/MinGW/include/rpcproxy.h             |   204 +
 .../win32.all/MinGW/include/rtutils.h              |    90 +
 .../win32.all/MinGW/include/schannel.h             |    90 +
 .../win32.all/MinGW/include/schnlsp.h              |    14 +
 .../win32.all/MinGW/include/scrnsave.h             |    81 +
 .../win32.all/MinGW/include/sddl.h                 |    30 +
 .../win32.all/MinGW/include/search.h               |   106 +
 .../win32.all/MinGW/include/secext.h               |    52 +
 .../win32.all/MinGW/include/security.h             |    42 +
 .../win32.all/MinGW/include/servprov.h             |    33 +
 .../win32.all/MinGW/include/setjmp.h               |    56 +
 .../win32.all/MinGW/include/setupapi.h             |  1590 ++
 .../win32.all/MinGW/include/share.h                |    33 +
 .../win32.all/MinGW/include/shellapi.h             |   363 +
 .../win32.all/MinGW/include/shldisp.h              |    61 +
 .../win32.all/MinGW/include/shlguid.h              |   102 +
 .../win32.all/MinGW/include/shlobj.h               |  1535 ++
 .../win32.all/MinGW/include/shlwapi.h              |   712 +
 .../win32.all/MinGW/include/signal.h               |    98 +
 .../win32.all/MinGW/include/snmp.h                 |   259 +
 .../win32.all/MinGW/include/sql.h                  |   408 +
 .../win32.all/MinGW/include/sqlext.h               |  1218 +
 .../win32.all/MinGW/include/sqltypes.h             |   165 +
 .../win32.all/MinGW/include/sqlucode.h             |   142 +
 .../win32.all/MinGW/include/sspi.h                 |   338 +
 .../win32.all/MinGW/include/stdint.h               |   206 +
 .../win32.all/MinGW/include/stdio.h                |   524 +
 .../win32.all/MinGW/include/stdlib.h               |   542 +
 .../win32.all/MinGW/include/stm.h                  |    25 +
 .../win32.all/MinGW/include/string.h               |   195 +
 .../win32.all/MinGW/include/strings.h              |    12 +
 .../win32.all/MinGW/include/strmif.h               |  1159 +
 .../win32.all/MinGW/include/subauth.h              |   209 +
 .../win32.all/MinGW/include/svcguid.h              |    33 +
 .../win32.all/MinGW/include/symcat.h               |    49 +
 .../win32.all/MinGW/include/sys/fcntl.h            |     7 +
 .../win32.all/MinGW/include/sys/file.h             |     7 +
 .../win32.all/MinGW/include/sys/locking.h          |    31 +
 .../win32.all/MinGW/include/sys/param.h            |    22 +
 .../win32.all/MinGW/include/sys/stat.h             |   196 +
 .../win32.all/MinGW/include/sys/time.h             |    47 +
 .../win32.all/MinGW/include/sys/timeb.h            |    74 +
 .../win32.all/MinGW/include/sys/types.h            |   120 +
 .../win32.all/MinGW/include/sys/unistd.h           |     6 +
 .../win32.all/MinGW/include/sys/utime.h            |    81 +
 .../win32.all/MinGW/include/tchar.h                |   420 +
 .../win32.all/MinGW/include/time.h                 |   218 +
 .../win32.all/MinGW/include/tlhelp32.h             |   140 +
 .../win32.all/MinGW/include/tmschema.h             |   664 +
 .../win32.all/MinGW/include/unistd.h               |    47 +
 .../win32.all/MinGW/include/unknwn.h               |    76 +
 .../win32.all/MinGW/include/userenv.h              |    57 +
 .../win32.all/MinGW/include/usp10.h                |   229 +
 .../win32.all/MinGW/include/utime.h                |     1 +
 .../win32.all/MinGW/include/uxtheme.h              |   273 +
 .../win32.all/MinGW/include/values.h               |     4 +
 .../win32.all/MinGW/include/varargs.h              |     7 +
 .../win32.all/MinGW/include/vfw.h                  |  1144 +
 .../win32.all/MinGW/include/vidcap.h               |    23 +
 .../win32.all/MinGW/include/vmr9.h                 |   211 +
 .../win32.all/MinGW/include/vptype.h               |    55 +
 .../win32.all/MinGW/include/w32api.h               |    52 +
 .../win32.all/MinGW/include/wchar.h                |   510 +
 .../win32.all/MinGW/include/wctype.h               |   168 +
 .../win32.all/MinGW/include/winable.h              |    99 +
 .../win32.all/MinGW/include/winbase.h              |  2435 ++
 .../win32.all/MinGW/include/winber.h               |    67 +
 .../win32.all/MinGW/include/wincon.h               |   232 +
 .../win32.all/MinGW/include/wincrypt.h             |  1138 +
 .../win32.all/MinGW/include/windef.h               |   337 +
 .../win32.all/MinGW/include/windns.h               |   405 +
 .../win32.all/MinGW/include/windows.h              |   131 +
 .../win32.all/MinGW/include/windowsx.h             |   544 +
 .../win32.all/MinGW/include/winerror.h             |  2264 ++
 .../win32.all/MinGW/include/wingdi.h               |  3226 +++
 .../win32.all/MinGW/include/wininet.h              |   986 +
 .../win32.all/MinGW/include/winioctl.h             |   554 +
 .../win32.all/MinGW/include/winldap.h              |   713 +
 .../win32.all/MinGW/include/winnetwk.h             |   350 +
 .../win32.all/MinGW/include/winnls.h               |   734 +
 .../win32.all/MinGW/include/winnt.h                |  3929 +++
 .../win32.all/MinGW/include/winperf.h              |   139 +
 .../win32.all/MinGW/include/winreg.h               |   180 +
 .../win32.all/MinGW/include/winresrc.h             |    14 +
 .../win32.all/MinGW/include/winsnmp.h              |   329 +
 .../win32.all/MinGW/include/winsock.h              |   536 +
 .../win32.all/MinGW/include/winsock2.h             |  1303 +
 .../win32.all/MinGW/include/winspool.h             |   982 +
 .../win32.all/MinGW/include/winsvc.h               |   313 +
 .../win32.all/MinGW/include/winuser.h              |  4411 +++
 .../win32.all/MinGW/include/winver.h               |   133 +
 .../win32.all/MinGW/include/ws2spi.h               |   202 +
 .../win32.all/MinGW/include/ws2tcpip.h             |   379 +
 .../win32.all/MinGW/include/wsahelp.h              |    98 +
 .../win32.all/MinGW/include/wsipx.h                |    28 +
 .../win32.all/MinGW/include/wsnetbs.h              |    35 +
 .../win32.all/MinGW/include/wtsapi32.h             |    62 +
 .../win32.all/MinGW/include/wtypes.h               |   171 +
 .../win32.all/MinGW/include/xprtdefs.h             |    11 +
 .../win32.all/MinGW/include/zmouse.h               |    36 +
 .../win32.all/MinGW/info/as.info                   | 18833 +++++++++++++
 .../win32.all/MinGW/info/bfd.info                  | 10438 +++++++
 .../win32.all/MinGW/info/binutils.info             |  3842 +++
 .../win32.all/MinGW/info/configure.info            |  2773 ++
 .../win32.all/MinGW/info/cpp.info                  |  5166 ++++
 .../win32.all/MinGW/info/cppinternals.info         |  1035 +
 .../org.xtuml.bp.MinGW/win32.all/MinGW/info/dir    |    50 +
 .../win32.all/MinGW/info/gcc.info                  | 27178 +++++++++++++++++++
 .../win32.all/MinGW/info/gccinstall.info           |  3822 +++
 .../win32.all/MinGW/info/gccint.info               | 27029 ++++++++++++++++++
 .../win32.all/MinGW/info/gprof.info                |  2313 ++
 .../win32.all/MinGW/info/ld.info                   |  6738 +++++
 .../win32.all/MinGW/info/standards.info            |  4930 ++++
 .../win32.all/MinGW/lib/CRT_fp10.o                 |   Bin 0 -> 368 bytes
 .../win32.all/MinGW/lib/CRT_fp8.o                  |   Bin 0 -> 412 bytes
 .../win32.all/MinGW/lib/CRT_noglob.o               |   Bin 0 -> 317 bytes
 .../win32.all/MinGW/lib/binmode.o                  |   Bin 0 -> 322 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/crt1.o  |   Bin 0 -> 2192 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/crt2.o  |   Bin 0 -> 2288 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/crtmt.o |   Bin 0 -> 322 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/crtst.o |   Bin 0 -> 306 bytes
 .../win32.all/MinGW/lib/debug/libstdc++.a          |   Bin 0 -> 6924998 bytes
 .../win32.all/MinGW/lib/debug/libstdc++.la         |    32 +
 .../win32.all/MinGW/lib/dllcrt1.o                  |   Bin 0 -> 1239 bytes
 .../win32.all/MinGW/lib/dllcrt2.o                  |   Bin 0 -> 1239 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/crtbegin.o         |   Bin 0 -> 412 bytes
 .../win32.all/MinGW/lib/gcc/mingw32/3.4.5/crtend.o |   Bin 0 -> 492 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/include/README     |    14 +
 .../lib/gcc/mingw32/3.4.5/include/emmintrin.h      |  1491 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/float.h    |   162 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/iso646.h   |    48 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/limits.h   |   125 +
 .../lib/gcc/mingw32/3.4.5/include/mm_malloc.h      |    77 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/mmintrin.h |   917 +
 .../gcc/mingw32/3.4.5/include/objc/NXConstStr.h    |    44 +
 .../lib/gcc/mingw32/3.4.5/include/objc/Object.h    |   124 +
 .../lib/gcc/mingw32/3.4.5/include/objc/Protocol.h  |    58 +
 .../lib/gcc/mingw32/3.4.5/include/objc/encoding.h  |    99 +
 .../lib/gcc/mingw32/3.4.5/include/objc/hash.h      |   207 +
 .../lib/gcc/mingw32/3.4.5/include/objc/objc-api.h  |   607 +
 .../lib/gcc/mingw32/3.4.5/include/objc/objc-list.h |   147 +
 .../lib/gcc/mingw32/3.4.5/include/objc/objc.h      |   165 +
 .../lib/gcc/mingw32/3.4.5/include/objc/sarray.h    |   237 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/objc/thr.h |   145 +
 .../gcc/mingw32/3.4.5/include/objc/typedstream.h   |   132 +
 .../lib/gcc/mingw32/3.4.5/include/pmmintrin.h      |   132 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/stdarg.h   |   135 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/stdbool.h  |    53 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/stddef.h   |   426 +
 .../lib/gcc/mingw32/3.4.5/include/syslimits.h      |     8 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/unwind.h   |   232 +
 .../MinGW/lib/gcc/mingw32/3.4.5/include/varargs.h  |     7 +
 .../lib/gcc/mingw32/3.4.5/include/xmmintrin.h      |  1222 +
 .../gcc/mingw32/3.4.5/install-tools/gsyslimits.h   |     8 +
 .../gcc/mingw32/3.4.5/install-tools/include/README |    14 +
 .../3.4.5/install-tools/include/emmintrin.h        |  1491 +
 .../mingw32/3.4.5/install-tools/include/float.h    |   162 +
 .../mingw32/3.4.5/install-tools/include/iso646.h   |    48 +
 .../mingw32/3.4.5/install-tools/include/limits.h   |   125 +
 .../3.4.5/install-tools/include/mm_malloc.h        |    77 +
 .../mingw32/3.4.5/install-tools/include/mmintrin.h |   917 +
 .../3.4.5/install-tools/include/pmmintrin.h        |   132 +
 .../mingw32/3.4.5/install-tools/include/stdarg.h   |   135 +
 .../mingw32/3.4.5/install-tools/include/stdbool.h  |    53 +
 .../mingw32/3.4.5/install-tools/include/stddef.h   |   426 +
 .../mingw32/3.4.5/install-tools/include/unwind.h   |   232 +
 .../mingw32/3.4.5/install-tools/include/varargs.h  |     7 +
 .../3.4.5/install-tools/include/xmmintrin.h        |  1222 +
 .../gcc/mingw32/3.4.5/install-tools/mkheaders.conf |     5 +
 .../win32.all/MinGW/lib/gcc/mingw32/3.4.5/libgcc.a |   Bin 0 -> 52846 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/libgcov.a          |   Bin 0 -> 9772 bytes
 .../win32.all/MinGW/lib/gcc/mingw32/3.4.5/specs    |   127 +
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/gcrt1.o |   Bin 0 -> 692 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/gcrt2.o |   Bin 0 -> 692 bytes
 .../win32.all/MinGW/lib/libaclui.a                 |   Bin 0 -> 3654 bytes
 .../win32.all/MinGW/lib/libadvapi32.a              |   Bin 0 -> 411648 bytes
 .../win32.all/MinGW/lib/libapcups.a                |   Bin 0 -> 5832 bytes
 .../win32.all/MinGW/lib/libavicap32.a              |   Bin 0 -> 6114 bytes
 .../win32.all/MinGW/lib/libavifil32.a              |   Bin 0 -> 56844 bytes
 .../win32.all/MinGW/lib/libbfd.a                   |   Bin 0 -> 3550064 bytes
 .../win32.all/MinGW/lib/libbfd.la                  |    32 +
 .../win32.all/MinGW/lib/libcap.a                   |   Bin 0 -> 4254 bytes
 .../win32.all/MinGW/lib/libcfgmgr32.a              |   Bin 0 -> 62924 bytes
 .../win32.all/MinGW/lib/libcoldname.a              |   Bin 0 -> 80600 bytes
 .../win32.all/MinGW/lib/libcomctl32.a              |   Bin 0 -> 98306 bytes
 .../win32.all/MinGW/lib/libcomdlg32.a              |   Bin 0 -> 19752 bytes
 .../win32.all/MinGW/lib/libcrtdll.a                |   Bin 0 -> 357874 bytes
 .../win32.all/MinGW/lib/libcrypt32.a               |   Bin 0 -> 27730 bytes
 .../win32.all/MinGW/lib/libctl3d32.a               |   Bin 0 -> 20062 bytes
 .../win32.all/MinGW/lib/libd3d8.a                  |   Bin 0 -> 4718 bytes
 .../win32.all/MinGW/lib/libd3d9.a                  |   Bin 0 -> 6350 bytes
 .../win32.all/MinGW/lib/libd3dim.a                 |   Bin 0 -> 6758 bytes
 .../win32.all/MinGW/lib/libd3drm.a                 |   Bin 0 -> 17462 bytes
 .../win32.all/MinGW/lib/libd3dx8d.a                |   Bin 0 -> 162892 bytes
 .../win32.all/MinGW/lib/libd3dx9d.a                |   Bin 0 -> 211092 bytes
 .../win32.all/MinGW/lib/libd3dxof.a                |   Bin 0 -> 2248 bytes
 .../win32.all/MinGW/lib/libddraw.a                 |   Bin 0 -> 11334 bytes
 .../win32.all/MinGW/lib/libdhcpcsvc.a              |   Bin 0 -> 6166 bytes
 .../win32.all/MinGW/lib/libdinput.a                |   Bin 0 -> 18086 bytes
 .../win32.all/MinGW/lib/libdinput8.a               |   Bin 0 -> 16568 bytes
 .../win32.all/MinGW/lib/libdlcapi.a                |   Bin 0 -> 3636 bytes
 .../win32.all/MinGW/lib/libdmoguids.a              |   Bin 0 -> 2342 bytes
 .../win32.all/MinGW/lib/libdnsapi.a                |   Bin 0 -> 22992 bytes
 .../win32.all/MinGW/lib/libdplayx.a                |   Bin 0 -> 6096 bytes
 .../win32.all/MinGW/lib/libdpnaddr.a               |   Bin 0 -> 2294 bytes
 .../win32.all/MinGW/lib/libdpnet.a                 |   Bin 0 -> 2246 bytes
 .../win32.all/MinGW/lib/libdpnlobby.a              |   Bin 0 -> 2296 bytes
 .../win32.all/MinGW/lib/libdpvoice.a               |   Bin 0 -> 2278 bytes
 .../win32.all/MinGW/lib/libdsetup.a                |   Bin 0 -> 15412 bytes
 .../win32.all/MinGW/lib/libdsound.a                |   Bin 0 -> 9260 bytes
 .../win32.all/MinGW/lib/libdxapi.a                 |   Bin 0 -> 1496 bytes
 .../win32.all/MinGW/lib/libdxerr8.a                |   Bin 0 -> 160852 bytes
 .../win32.all/MinGW/lib/libdxerr9.a                |   Bin 0 -> 194508 bytes
 .../win32.all/MinGW/lib/libdxguid.a                |   Bin 0 -> 50754 bytes
 .../win32.all/MinGW/lib/libfaultrep.a              |   Bin 0 -> 3816 bytes
 .../win32.all/MinGW/lib/libgdi32.a                 |   Bin 0 -> 255422 bytes
 .../win32.all/MinGW/lib/libglaux.a                 |   Bin 0 -> 126282 bytes
 .../win32.all/MinGW/lib/libglu32.a                 |   Bin 0 -> 39872 bytes
 .../win32.all/MinGW/lib/libglut.a                  |   Bin 0 -> 86446 bytes
 .../win32.all/MinGW/lib/libglut32.a                |   Bin 0 -> 86684 bytes
 .../win32.all/MinGW/lib/libgmon.a                  |   Bin 0 -> 5360 bytes
 .../win32.all/MinGW/lib/libhal.a                   |   Bin 0 -> 34142 bytes
 .../win32.all/MinGW/lib/libhid.a                   |   Bin 0 -> 35186 bytes
 .../win32.all/MinGW/lib/libhidparse.a              |   Bin 0 -> 20282 bytes
 .../win32.all/MinGW/lib/libiberty.a                |   Bin 0 -> 589800 bytes
 .../win32.all/MinGW/lib/libicmui.a                 |   Bin 0 -> 3012 bytes
 .../win32.all/MinGW/lib/libigmpagnt.a              |   Bin 0 -> 4542 bytes
 .../win32.all/MinGW/lib/libimagehlp.a              |   Bin 0 -> 86386 bytes
 .../win32.all/MinGW/lib/libimm32.a                 |   Bin 0 -> 58756 bytes
 .../win32.all/MinGW/lib/libiphlpapi.a              |   Bin 0 -> 35738 bytes
 .../win32.all/MinGW/lib/libkernel32.a              |   Bin 0 -> 657818 bytes
 .../win32.all/MinGW/lib/libksproxy.a               |   Bin 0 -> 6182 bytes
 .../win32.all/MinGW/lib/libksuser.a                |   Bin 0 -> 4476 bytes
 .../win32.all/MinGW/lib/liblargeint.a              |   Bin 0 -> 2548 bytes
 .../win32.all/MinGW/lib/liblz32.a                  |   Bin 0 -> 10006 bytes
 .../org.xtuml.bp.MinGW/win32.all/MinGW/lib/libm.a  |   Bin 0 -> 458 bytes
 .../win32.all/MinGW/lib/libmapi32.a                |   Bin 0 -> 121098 bytes
 .../win32.all/MinGW/lib/libmcd.a                   |   Bin 0 -> 5386 bytes
 .../win32.all/MinGW/lib/libmfcuia32.a              |   Bin 0 -> 8970 bytes
 .../win32.all/MinGW/lib/libmgmtapi.a               |   Bin 0 -> 8874 bytes
 .../win32.all/MinGW/lib/libmingw32.a               |   Bin 0 -> 7514 bytes
 .../win32.all/MinGW/lib/libmingwex.a               |   Bin 0 -> 267880 bytes
 .../win32.all/MinGW/lib/libmingwthrd.a             |   Bin 0 -> 3458 bytes
 .../win32.all/MinGW/lib/libmoldname.a              |   Bin 0 -> 82558 bytes
 .../win32.all/MinGW/lib/libmoldnamed.a             |   Bin 0 -> 82656 bytes
 .../win32.all/MinGW/lib/libmpr.a                   |   Bin 0 -> 54878 bytes
 .../win32.all/MinGW/lib/libmprapi.a                |   Bin 0 -> 96220 bytes
 .../win32.all/MinGW/lib/libmqrt.a                  |   Bin 0 -> 30046 bytes
 .../win32.all/MinGW/lib/libmsacm32.a               |   Bin 0 -> 34326 bytes
 .../win32.all/MinGW/lib/libmscms.a                 |   Bin 0 -> 42602 bytes
 .../win32.all/MinGW/lib/libmsdmo.a                 |   Bin 0 -> 12454 bytes
 .../win32.all/MinGW/lib/libmsimg32.a               |   Bin 0 -> 5162 bytes
 .../win32.all/MinGW/lib/libmsvcp60.a               |   Bin 0 -> 8366 bytes
 .../win32.all/MinGW/lib/libmsvcr70.a               |   Bin 0 -> 533872 bytes
 .../win32.all/MinGW/lib/libmsvcr70d.a              |   Bin 0 -> 534958 bytes
 .../win32.all/MinGW/lib/libmsvcr71.a               |   Bin 0 -> 546712 bytes
 .../win32.all/MinGW/lib/libmsvcr71d.a              |   Bin 0 -> 547832 bytes
 .../win32.all/MinGW/lib/libmsvcr80.a               |   Bin 0 -> 554136 bytes
 .../win32.all/MinGW/lib/libmsvcr80d.a              |   Bin 0 -> 555276 bytes
 .../win32.all/MinGW/lib/libmsvcr90.a               |   Bin 0 -> 554136 bytes
 .../win32.all/MinGW/lib/libmsvcr90d.a              |   Bin 0 -> 555910 bytes
 .../win32.all/MinGW/lib/libmsvcrt.a                |   Bin 0 -> 503692 bytes
 .../win32.all/MinGW/lib/libmsvcrtd.a               |   Bin 0 -> 504146 bytes
 .../win32.all/MinGW/lib/libmsvfw32.a               |   Bin 0 -> 36118 bytes
 .../win32.all/MinGW/lib/libmswsock.a               |   Bin 0 -> 20470 bytes
 .../win32.all/MinGW/lib/libnddeapi.a               |   Bin 0 -> 22786 bytes
 .../win32.all/MinGW/lib/libndis.a                  |   Bin 0 -> 130602 bytes
 .../win32.all/MinGW/lib/libnetapi32.a              |   Bin 0 -> 187092 bytes
 .../win32.all/MinGW/lib/libnewdev.a                |   Bin 0 -> 4836 bytes
 .../win32.all/MinGW/lib/libntdll.a                 |   Bin 0 -> 764276 bytes
 .../win32.all/MinGW/lib/libntoskrnl.a              |   Bin 0 -> 539254 bytes
 .../win32.all/MinGW/lib/libobjc.a                  |   Bin 0 -> 117904 bytes
 .../win32.all/MinGW/lib/libobjc.la                 |    32 +
 .../win32.all/MinGW/lib/libodbc32.a                |   Bin 0 -> 136948 bytes
 .../win32.all/MinGW/lib/libodbccp32.a              |   Bin 0 -> 41294 bytes
 .../win32.all/MinGW/lib/libole32.a                 |   Bin 0 -> 192226 bytes
 .../win32.all/MinGW/lib/liboleacc.a                |   Bin 0 -> 13004 bytes
 .../win32.all/MinGW/lib/liboleaut32.a              |   Bin 0 -> 262040 bytes
 .../win32.all/MinGW/lib/libolecli32.a              |   Bin 0 -> 42380 bytes
 .../win32.all/MinGW/lib/liboledlg.a                |   Bin 0 -> 18728 bytes
 .../win32.all/MinGW/lib/libolepro32.a              |   Bin 0 -> 6916 bytes
 .../win32.all/MinGW/lib/libolesvr32.a              |   Bin 0 -> 9800 bytes
 .../win32.all/MinGW/lib/libopcodes.a               |   Bin 0 -> 360786 bytes
 .../win32.all/MinGW/lib/libopcodes.la              |    32 +
 .../win32.all/MinGW/lib/libopengl32.a              |   Bin 0 -> 269134 bytes
 .../win32.all/MinGW/lib/libpenwin32.a              |   Bin 0 -> 75528 bytes
 .../win32.all/MinGW/lib/libpkpd32.a                |   Bin 0 -> 27088 bytes
 .../win32.all/MinGW/lib/libpowrprof.a              |   Bin 0 -> 19072 bytes
 .../win32.all/MinGW/lib/libpsapi.a                 |   Bin 0 -> 15978 bytes
 .../win32.all/MinGW/lib/libquartz.a                |   Bin 0 -> 4444 bytes
 .../win32.all/MinGW/lib/librapi.a                  |   Bin 0 -> 61850 bytes
 .../win32.all/MinGW/lib/librasapi32.a              |   Bin 0 -> 112510 bytes
 .../win32.all/MinGW/lib/librasdlg.a                |   Bin 0 -> 5900 bytes
 .../win32.all/MinGW/lib/librpcdce4.a               |   Bin 0 -> 19674 bytes
 .../win32.all/MinGW/lib/librpcns4.a                |   Bin 0 -> 46260 bytes
 .../win32.all/MinGW/lib/librpcrt4.a                |   Bin 0 -> 278240 bytes
 .../win32.all/MinGW/lib/librtm.a                   |   Bin 0 -> 13778 bytes
 .../win32.all/MinGW/lib/librtutils.a               |   Bin 0 -> 40722 bytes
 .../win32.all/MinGW/lib/libscrnsave.a              |   Bin 0 -> 7110 bytes
 .../win32.all/MinGW/lib/libscrnsavw.a              |   Bin 0 -> 7254 bytes
 .../win32.all/MinGW/lib/libscsiport.a              |   Bin 0 -> 37802 bytes
 .../win32.all/MinGW/lib/libsecur32.a               |   Bin 0 -> 31630 bytes
 .../win32.all/MinGW/lib/libsetupapi.a              |   Bin 0 -> 361106 bytes
 .../win32.all/MinGW/lib/libshell32.a               |   Bin 0 -> 142490 bytes
 .../win32.all/MinGW/lib/libshfolder.a              |   Bin 0 -> 3014 bytes
 .../win32.all/MinGW/lib/libshlwapi.a               |   Bin 0 -> 218782 bytes
 .../win32.all/MinGW/lib/libsnmpapi.a               |   Bin 0 -> 30374 bytes
 .../win32.all/MinGW/lib/libstdc++.a                |   Bin 0 -> 1063794 bytes
 .../win32.all/MinGW/lib/libstdc++.la               |    32 +
 .../win32.all/MinGW/lib/libstrmiids.a              |   Bin 0 -> 80834 bytes
 .../win32.all/MinGW/lib/libsupc++.a                |   Bin 0 -> 115964 bytes
 .../win32.all/MinGW/lib/libsupc++.la               |    32 +
 .../win32.all/MinGW/lib/libsvrapi.a                |   Bin 0 -> 16292 bytes
 .../win32.all/MinGW/lib/libtapi32.a                |   Bin 0 -> 86704 bytes
 .../win32.all/MinGW/lib/libtdi.a                   |   Bin 0 -> 28158 bytes
 .../win32.all/MinGW/lib/libth32.a                  |   Bin 0 -> 10342 bytes
 .../win32.all/MinGW/lib/libthunk32.a               |   Bin 0 -> 48894 bytes
 .../win32.all/MinGW/lib/liburl.a                   |   Bin 0 -> 6750 bytes
 .../win32.all/MinGW/lib/libusbcamd.a               |   Bin 0 -> 7042 bytes
 .../win32.all/MinGW/lib/libusbcamd2.a              |   Bin 0 -> 7064 bytes
 .../win32.all/MinGW/lib/libuser32.a                |   Bin 0 -> 472330 bytes
 .../win32.all/MinGW/lib/libuserenv.a               |   Bin 0 -> 8414 bytes
 .../win32.all/MinGW/lib/libusp10.a                 |   Bin 0 -> 31908 bytes
 .../win32.all/MinGW/lib/libuuid.a                  |   Bin 0 -> 43388 bytes
 .../win32.all/MinGW/lib/libuxtheme.a               |   Bin 0 -> 37758 bytes
 .../win32.all/MinGW/lib/libvdmdbg.a                |   Bin 0 -> 13448 bytes
 .../win32.all/MinGW/lib/libversion.a               |   Bin 0 -> 12042 bytes
 .../win32.all/MinGW/lib/libvfw32.a                 |   Bin 0 -> 98930 bytes
 .../win32.all/MinGW/lib/libvideoprt.a              |   Bin 0 -> 89216 bytes
 .../win32.all/MinGW/lib/libwin32k.a                |   Bin 0 -> 149756 bytes
 .../win32.all/MinGW/lib/libwin32spl.a              |   Bin 0 -> 11666 bytes
 .../win32.all/MinGW/lib/libwininet.a               |   Bin 0 -> 186898 bytes
 .../win32.all/MinGW/lib/libwinmm.a                 |   Bin 0 -> 144884 bytes
 .../win32.all/MinGW/lib/libwinspool.a              |   Bin 0 -> 99412 bytes
 .../win32.all/MinGW/lib/libwinstrm.a               |   Bin 0 -> 6410 bytes
 .../win32.all/MinGW/lib/libwldap32.a               |   Bin 0 -> 182062 bytes
 .../win32.all/MinGW/lib/libwow32.a                 |   Bin 0 -> 14138 bytes
 .../win32.all/MinGW/lib/libws2_32.a                |   Bin 0 -> 83372 bytes
 .../win32.all/MinGW/lib/libwsnmp32.a               |   Bin 0 -> 35262 bytes
 .../win32.all/MinGW/lib/libwsock32.a               |   Bin 0 -> 54478 bytes
 .../win32.all/MinGW/lib/libwst.a                   |   Bin 0 -> 2162 bytes
 .../win32.all/MinGW/lib/libwtsapi32.a              |   Bin 0 -> 26944 bytes
 .../win32.all/MinGW/lib/txtmode.o                  |   Bin 0 -> 322 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/cc1.exe        |   Bin 0 -> 4424704 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/cc1obj.exe     |   Bin 0 -> 4488704 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/cc1plus.exe    |   Bin 0 -> 4901376 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/collect2.exe   |   Bin 0 -> 87552 bytes
 .../gcc/mingw32/3.4.5/install-tools/fixinc.sh      |     2 +
 .../gcc/mingw32/3.4.5/install-tools/mkheaders      |   105 +
 .../win32.all/MinGW/man/man1/addr2line.1           |   265 +
 .../win32.all/MinGW/man/man1/ar.1                  |   390 +
 .../win32.all/MinGW/man/man1/as.1                  |  1120 +
 .../win32.all/MinGW/man/man1/c++filt.1             |   345 +
 .../win32.all/MinGW/man/man1/cpp.1                 |   918 +
 .../win32.all/MinGW/man/man1/dlltool.1             |   478 +
 .../win32.all/MinGW/man/man1/g++.1                 | 10714 ++++++++
 .../win32.all/MinGW/man/man1/gcc.1                 | 10714 ++++++++
 .../win32.all/MinGW/man/man1/gcov.1                |   607 +
 .../win32.all/MinGW/man/man1/gprof.1               |   742 +
 .../win32.all/MinGW/man/man1/ld.1                  |  2103 ++
 .../win32.all/MinGW/man/man1/mingw32-make.1        |   365 +
 .../win32.all/MinGW/man/man1/nlmconv.1             |   243 +
 .../win32.all/MinGW/man/man1/nm.1                  |   449 +
 .../win32.all/MinGW/man/man1/objcopy.1             |   801 +
 .../win32.all/MinGW/man/man1/objdump.1             |   634 +
 .../win32.all/MinGW/man/man1/ranlib.1              |   188 +
 .../win32.all/MinGW/man/man1/readelf.1             |   376 +
 .../win32.all/MinGW/man/man1/size.1                |   263 +
 .../win32.all/MinGW/man/man1/strings.1             |   253 +
 .../win32.all/MinGW/man/man1/strip.1               |   383 +
 .../win32.all/MinGW/man/man1/windres.1             |   343 +
 .../win32.all/MinGW/man/man3/basename.3            |   474 +
 .../win32.all/MinGW/man/man3/dirname.3             |   474 +
 .../win32.all/MinGW/man/man7/fsf-funding.7         |   185 +
 .../win32.all/MinGW/man/man7/gfdl.7                |   561 +
 .../win32.all/MinGW/man/man7/gpl.7                 |   535 +
 .../win32.all/MinGW/minGNUtoc.xml                  |     6 +
 .../win32.all/MinGW/mingw32/bin/ar.exe             |   Bin 0 -> 525951 bytes
 .../win32.all/MinGW/mingw32/bin/as.exe             |   Bin 0 -> 792360 bytes
 .../win32.all/MinGW/mingw32/bin/dlltool.exe        |   Bin 0 -> 611153 bytes
 .../win32.all/MinGW/mingw32/bin/ld.exe             |   Bin 0 -> 806237 bytes
 .../win32.all/MinGW/mingw32/bin/nm.exe             |   Bin 0 -> 570469 bytes
 .../win32.all/MinGW/mingw32/bin/objdump.exe        |   Bin 0 -> 849949 bytes
 .../win32.all/MinGW/mingw32/bin/ranlib.exe         |   Bin 0 -> 525951 bytes
 .../win32.all/MinGW/mingw32/bin/strip.exe          |   Bin 0 -> 717083 bytes
 .../win32.all/MinGW/mingw32/lib/ldscripts/i386pe.x |   212 +
 .../MinGW/mingw32/lib/ldscripts/i386pe.xbn         |   212 +
 .../MinGW/mingw32/lib/ldscripts/i386pe.xn          |   212 +
 .../MinGW/mingw32/lib/ldscripts/i386pe.xr          |   147 +
 .../MinGW/mingw32/lib/ldscripts/i386pe.xu          |   161 +
 .../win32.all/MinGW/mingwgnu.bat                   |    20 +
 features/org.xtuml.bp.docgen.parent/.project       |    11 +
 features/org.xtuml.bp.docgen.parent/pom.xml        |    61 +
 features/org.xtuml.bp.docgen/.project              |    17 +
 features/org.xtuml.bp.docgen/build.properties      |    11 +
 .../tools/docgen/docbook/docbook-xml-4.5/ChangeLog |   106 +
 .../tools/docgen/docbook/docbook-xml-4.5/README    |     8 +
 .../docgen/docbook/docbook-xml-4.5/calstblx.dtd    |   215 +
 .../docgen/docbook/docbook-xml-4.5/catalog.xml     |   124 +
 .../docgen/docbook/docbook-xml-4.5/dbcentx.mod     |   384 +
 .../docgen/docbook/docbook-xml-4.5/dbgenent.mod    |    41 +
 .../docgen/docbook/docbook-xml-4.5/dbhierx.mod     |  2193 ++
 .../docgen/docbook/docbook-xml-4.5/dbnotnx.mod     |   101 +
 .../docgen/docbook/docbook-xml-4.5/dbpoolx.mod     |  8701 ++++++
 .../docgen/docbook/docbook-xml-4.5/docbook.cat     |   113 +
 .../docgen/docbook/docbook-xml-4.5/docbookx.dtd    |   170 +
 .../docgen/docbook/docbook-xml-4.5/ent/README      |    14 +
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsa.ent |    97 +
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsb.ent |    83 +
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsc.ent |    51 +
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsn.ent |   103 +
 .../docgen/docbook/docbook-xml-4.5/ent/isoamso.ent |    59 +
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsr.ent |   125 +
 .../docgen/docbook/docbook-xml-4.5/ent/isobox.ent  |    81 +
 .../docgen/docbook/docbook-xml-4.5/ent/isocyr1.ent |   108 +
 .../docgen/docbook/docbook-xml-4.5/ent/isocyr2.ent |    67 +
 .../docgen/docbook/docbook-xml-4.5/ent/isodia.ent  |    55 +
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk1.ent |    90 +
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk2.ent |    61 +
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk3.ent |    84 +
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk4.ent |    84 +
 .../docgen/docbook/docbook-xml-4.5/ent/isolat1.ent |   103 +
 .../docgen/docbook/docbook-xml-4.5/ent/isolat2.ent |   162 +
 .../docgen/docbook/docbook-xml-4.5/ent/isonum.ent  |   117 +
 .../docgen/docbook/docbook-xml-4.5/ent/isopub.ent  |   125 +
 .../docgen/docbook/docbook-xml-4.5/ent/isotech.ent |   103 +
 .../docgen/docbook/docbook-xml-4.5/htmltblx.mod    |   245 +
 .../docgen/docbook/docbook-xml-4.5/soextblx.dtd    |   321 +
 .../.CatalogManager.properties.example             |    61 +
 .../docgen/docbook/docbook-xsl-1.75.1/.urilist     |     1 +
 .../docgen/docbook/docbook-xsl-1.75.1/AUTHORS      |     4 +
 .../tools/docgen/docbook/docbook-xsl-1.75.1/BUGS   |    21 +
 .../docgen/docbook/docbook-xsl-1.75.1/COPYING      |    47 +
 .../docgen/docbook/docbook-xsl-1.75.1/INSTALL      |    88 +
 .../docgen/docbook/docbook-xsl-1.75.1/Makefile     |    83 +
 .../tools/docgen/docbook/docbook-xsl-1.75.1/NEWS   |    75 +
 .../docgen/docbook/docbook-xsl-1.75.1/NEWS.html    |    18 +
 .../docgen/docbook/docbook-xsl-1.75.1/NEWS.xml     |    84 +
 .../tools/docgen/docbook/docbook-xsl-1.75.1/README |   158 +
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.html  |  7427 +++++
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.pdf   | 19594 +++++++++++++
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.txt   |  7792 ++++++
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.xml   |  9240 +++++++
 .../tools/docgen/docbook/docbook-xsl-1.75.1/TODO   |    23 +
 .../docgen/docbook/docbook-xsl-1.75.1/VERSION      |   115 +
 .../docgen/docbook/docbook-xsl-1.75.1/catalog.xml  |     8 +
 .../docbook/docbook-xsl-1.75.1/common/af.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/am.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/ar.xml       |  1230 +
 .../docbook-xsl-1.75.1/common/autoidx-kimber.xsl   |    43 +
 .../docbook-xsl-1.75.1/common/autoidx-kosek.xsl    |   150 +
 .../docbook/docbook-xsl-1.75.1/common/az.xml       |   673 +
 .../docbook/docbook-xsl-1.75.1/common/bg.xml       |   725 +
 .../docbook/docbook-xsl-1.75.1/common/bn.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/bs.xml       |   663 +
 .../docbook/docbook-xsl-1.75.1/common/ca.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/charmap.xml  |   185 +
 .../docbook/docbook-xsl-1.75.1/common/charmap.xsl  |   221 +
 .../docbook/docbook-xsl-1.75.1/common/common.xml   |   622 +
 .../docbook/docbook-xsl-1.75.1/common/common.xsl   |  2039 ++
 .../docbook/docbook-xsl-1.75.1/common/cs.xml       |   701 +
 .../docbook/docbook-xsl-1.75.1/common/cy.xml       |  1246 +
 .../docbook/docbook-xsl-1.75.1/common/da.xml       |   665 +
 .../docbook/docbook-xsl-1.75.1/common/de.xml       |   667 +
 .../docbook/docbook-xsl-1.75.1/common/el.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/en.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/entities.ent |    60 +
 .../docbook/docbook-xsl-1.75.1/common/eo.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/es.xml       |   677 +
 .../docbook/docbook-xsl-1.75.1/common/et.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/eu.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/fa.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/fi.xml       |   671 +
 .../docbook/docbook-xsl-1.75.1/common/fr.xml       |   691 +
 .../docbook/docbook-xsl-1.75.1/common/ga.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/gentext.xsl  |   836 +
 .../docbook/docbook-xsl-1.75.1/common/gl.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/gu.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/he.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/hi.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/hr.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/hu.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/id.xml       |  1230 +
 .../docbook-xsl-1.75.1/common/insertfile.xsl       |   111 +
 .../docbook/docbook-xsl-1.75.1/common/it.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/ja.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/kn.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/ko.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/ky.xml       |   733 +
 .../docbook/docbook-xsl-1.75.1/common/l10n.dtd     |    63 +
 .../docbook/docbook-xsl-1.75.1/common/l10n.xml     |   131 +
 .../docbook/docbook-xsl-1.75.1/common/l10n.xsl     |   497 +
 .../docbook/docbook-xsl-1.75.1/common/la.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/labels.xsl   |   890 +
 .../docbook/docbook-xsl-1.75.1/common/lt.xml       |   679 +
 .../docbook/docbook-xsl-1.75.1/common/lv.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/mn.xml       |   731 +
 .../docbook/docbook-xsl-1.75.1/common/nb.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/nl.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/nn.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/olink.xsl    |  1215 +
 .../docbook/docbook-xsl-1.75.1/common/or.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/pa.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/pi.xml       |   165 +
 .../docbook/docbook-xsl-1.75.1/common/pi.xsl       |   344 +
 .../docbook/docbook-xsl-1.75.1/common/pl.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/pt.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/pt_br.xml    |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/refentry.xml |   781 +
 .../docbook/docbook-xsl-1.75.1/common/refentry.xsl |  1352 +
 .../docbook/docbook-xsl-1.75.1/common/ro.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/ru.xml       |   727 +
 .../docbook/docbook-xsl-1.75.1/common/sk.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/sl.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/sq.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/sr.xml       |   721 +
 .../docbook/docbook-xsl-1.75.1/common/sr_Latn.xml  |   680 +
 .../docbook/docbook-xsl-1.75.1/common/stripns.xsl  |   287 +
 .../docbook-xsl-1.75.1/common/subtitles.xsl        |   155 +
 .../docbook/docbook-xsl-1.75.1/common/sv.xml       |   665 +
 .../docbook/docbook-xsl-1.75.1/common/ta.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/table.xsl    |   514 +
 .../docbook-xsl-1.75.1/common/targetdatabase.dtd   |    49 +
 .../docbook/docbook-xsl-1.75.1/common/targets.xsl  |   333 +
 .../docbook/docbook-xsl-1.75.1/common/th.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/titles.xsl   |   798 +
 .../docbook/docbook-xsl-1.75.1/common/tl.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/tr.xml       |   667 +
 .../docbook/docbook-xsl-1.75.1/common/uk.xml       |   727 +
 .../docbook/docbook-xsl-1.75.1/common/utility.xml  |   259 +
 .../docbook/docbook-xsl-1.75.1/common/utility.xsl  |   290 +
 .../docbook/docbook-xsl-1.75.1/common/vi.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/xh.xml       |  1230 +
 .../docbook/docbook-xsl-1.75.1/common/zh.xml       |   661 +
 .../docbook/docbook-xsl-1.75.1/common/zh_cn.xml    |   661 +
 .../docbook/docbook-xsl-1.75.1/common/zh_tw.xml    |  1230 +
 .../docbook/docbook-xsl-1.75.1/eclipse/eclipse.xsl |   306 +
 .../docbook-xsl-1.75.1/eclipse/profile-eclipse.xsl |   269 +
 .../docgen/docbook/docbook-xsl-1.75.1/epub/README  |    88 +
 .../docbook/docbook-xsl-1.75.1/epub/bin/dbtoepub   |    72 +
 .../docbook-xsl-1.75.1/epub/bin/lib/docbook.rb     |   226 +
 .../docbook/docbook-xsl-1.75.1/epub/docbook.xsl    |  1694 ++
 .../docbook-xsl-1.75.1/extensions/README.LIBXSLT   |    52 +
 .../docbook-xsl-1.75.1/extensions/docbook.py       |   239 +
 .../docbook-xsl-1.75.1/extensions/saxon65.jar      |   Bin 0 -> 80674 bytes
 .../docbook-xsl-1.75.1/extensions/xalan27.jar      |   Bin 0 -> 57506 bytes
 .../docbook/docbook-xsl-1.75.1/extensions/xslt.py  |    84 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/admon.xsl |   130 +
 .../docbook/docbook-xsl-1.75.1/fo/annotations.xsl  |    18 +
 .../docbook-xsl-1.75.1/fo/autoidx-kimber.xsl       |   178 +
 .../docbook-xsl-1.75.1/fo/autoidx-kosek.xsl        |   149 +
 .../docbook/docbook-xsl-1.75.1/fo/autoidx-ng.xsl   |    20 +
 .../docbook/docbook-xsl-1.75.1/fo/autoidx.xsl      |  1330 +
 .../docbook/docbook-xsl-1.75.1/fo/autotoc.xsl      |   915 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/axf.xsl   |   110 +
 .../docbook-xsl-1.75.1/fo/biblio-iso690.xsl        |  1300 +
 .../docbook/docbook-xsl-1.75.1/fo/biblio.xsl       |  1169 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/block.xsl |   643 +
 .../docbook/docbook-xsl-1.75.1/fo/callout.xsl      |   231 +
 .../docbook/docbook-xsl-1.75.1/fo/component.xsl    |   887 +
 .../docbook/docbook-xsl-1.75.1/fo/division.xsl     |   612 +
 .../docbook/docbook-xsl-1.75.1/fo/docbook.xsl      |   333 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/ebnf.xsl  |   325 +
 .../docbook/docbook-xsl-1.75.1/fo/fo-rtf.xsl       |   154 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/fo.xsl    |   117 +
 .../docbook/docbook-xsl-1.75.1/fo/footnote.xsl     |   220 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/fop.xsl   |    93 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/fop1.xsl  |   188 +
 .../docbook/docbook-xsl-1.75.1/fo/formal.xsl       |   618 +
 .../docbook/docbook-xsl-1.75.1/fo/glossary.xsl     |  1144 +
 .../docbook/docbook-xsl-1.75.1/fo/graphics.xsl     |   642 +
 .../docbook/docbook-xsl-1.75.1/fo/highlight.xsl    |    77 +
 .../docbook/docbook-xsl-1.75.1/fo/htmltbl.xsl      |   425 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/index.xsl |   485 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/info.xsl  |    34 +
 .../docbook/docbook-xsl-1.75.1/fo/inline.xsl       |  1286 +
 .../docbook/docbook-xsl-1.75.1/fo/keywords.xsl     |    21 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/lists.xsl |  1393 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/math.xsl  |   140 +
 .../docbook/docbook-xsl-1.75.1/fo/pagesetup.xsl    |  2567 ++
 .../docgen/docbook/docbook-xsl-1.75.1/fo/param.xml | 12413 +++++++++
 .../docgen/docbook/docbook-xsl-1.75.1/fo/param.xsl |   942 +
 .../docbook/docbook-xsl-1.75.1/fo/passivetex.xsl   |    36 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/pdf2index |   140 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/pi.xml    |  1002 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/pi.xsl    |  1086 +
 .../docbook-xsl-1.75.1/fo/profile-docbook.xsl      |   288 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/ptc.xsl   |    77 +
 .../docbook/docbook-xsl-1.75.1/fo/qandaset.xsl     |   395 +
 .../docbook/docbook-xsl-1.75.1/fo/refentry.xsl     |   637 +
 .../docbook/docbook-xsl-1.75.1/fo/sections.xsl     |   764 +
 .../docbook/docbook-xsl-1.75.1/fo/spaces.xsl       |   274 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/synop.xsl |  1007 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/table.xml |   135 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/table.xsl |  1621 ++
 .../docgen/docbook/docbook-xsl-1.75.1/fo/task.xsl  |    91 +
 .../docbook-xsl-1.75.1/fo/titlepage.templates.xml  |  1354 +
 .../docbook-xsl-1.75.1/fo/titlepage.templates.xsl  |  5182 ++++
 .../docbook/docbook-xsl-1.75.1/fo/titlepage.xsl    |   760 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/toc.xsl   |   332 +
 .../docbook/docbook-xsl-1.75.1/fo/verbatim.xsl     |   465 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/xep.xsl   |   180 +
 .../docgen/docbook/docbook-xsl-1.75.1/fo/xref.xsl  |  1519 ++
 .../docbook/docbook-xsl-1.75.1/highlighting/README |    16 +
 .../docbook-xsl-1.75.1/highlighting/c-hl.xml       |   101 +
 .../docbook-xsl-1.75.1/highlighting/common.xsl     |   120 +
 .../docbook-xsl-1.75.1/highlighting/cpp-hl.xml     |   150 +
 .../docbook-xsl-1.75.1/highlighting/csharp-hl.xml  |   187 +
 .../docbook-xsl-1.75.1/highlighting/delphi-hl.xml  |   200 +
 .../docbook-xsl-1.75.1/highlighting/ini-hl.xml     |    45 +
 .../docbook-xsl-1.75.1/highlighting/java-hl.xml    |   117 +
 .../highlighting/javascript-hl.xml                 |   147 +
 .../docbook-xsl-1.75.1/highlighting/m2-hl.xml      |    90 +
 .../docbook-xsl-1.75.1/highlighting/myxml-hl.xml   |   116 +
 .../docbook-xsl-1.75.1/highlighting/perl-hl.xml    |   120 +
 .../docbook-xsl-1.75.1/highlighting/php-hl.xml     |   149 +
 .../docbook-xsl-1.75.1/highlighting/python-hl.xml  |   100 +
 .../docbook-xsl-1.75.1/highlighting/ruby-hl.xml    |   109 +
 .../docbook-xsl-1.75.1/highlighting/tcl-hl.xml     |   180 +
 .../highlighting/xslthl-config.xml                 |    46 +
 .../docbook/docbook-xsl-1.75.1/html/admon.xsl      |   134 +
 .../docbook-xsl-1.75.1/html/annotations.xsl        |   169 +
 .../docbook-xsl-1.75.1/html/autoidx-kimber.xsl     |   168 +
 .../docbook-xsl-1.75.1/html/autoidx-kosek.xsl      |   124 +
 .../docbook/docbook-xsl-1.75.1/html/autoidx-ng.xsl |    20 +
 .../docbook/docbook-xsl-1.75.1/html/autoidx.xsl    |   712 +
 .../docbook/docbook-xsl-1.75.1/html/autotoc.xsl    |   676 +
 .../docbook-xsl-1.75.1/html/biblio-iso690.xsl      |  1300 +
 .../docbook/docbook-xsl-1.75.1/html/biblio.xsl     |  1253 +
 .../docbook/docbook-xsl-1.75.1/html/block.xsl      |   437 +
 .../docbook/docbook-xsl-1.75.1/html/callout.xsl    |   201 +
 .../docbook/docbook-xsl-1.75.1/html/changebars.xsl |   121 +
 .../docbook-xsl-1.75.1/html/chunk-changebars.xsl   |    99 +
 .../docbook/docbook-xsl-1.75.1/html/chunk-code.xsl |   667 +
 .../docbook-xsl-1.75.1/html/chunk-common.xsl       |  1920 ++
 .../docbook/docbook-xsl-1.75.1/html/chunk.xsl      |    52 +
 .../docbook/docbook-xsl-1.75.1/html/chunker.xsl    |   439 +
 .../docbook/docbook-xsl-1.75.1/html/chunkfast.xsl  |    72 +
 .../docbook/docbook-xsl-1.75.1/html/chunktoc.xsl   |   544 +
 .../docbook/docbook-xsl-1.75.1/html/component.xsl  |   425 +
 .../docbook/docbook-xsl-1.75.1/html/division.xsl   |   217 +
 .../docbook/docbook-xsl-1.75.1/html/docbook.xsl    |   481 +
 .../docbook/docbook-xsl-1.75.1/html/ebnf.xsl       |   329 +
 .../docbook/docbook-xsl-1.75.1/html/footnote.xsl   |   310 +
 .../docbook/docbook-xsl-1.75.1/html/formal.xsl     |   404 +
 .../docbook/docbook-xsl-1.75.1/html/glossary.xsl   |   492 +
 .../docbook/docbook-xsl-1.75.1/html/graphics.xsl   |  1515 ++
 .../docbook/docbook-xsl-1.75.1/html/highlight.xsl  |    73 +
 .../docbook/docbook-xsl-1.75.1/html/html-rtf.xsl   |   336 +
 .../docbook/docbook-xsl-1.75.1/html/html.xsl       |   370 +
 .../docbook/docbook-xsl-1.75.1/html/htmltbl.xsl    |   138 +
 .../docbook/docbook-xsl-1.75.1/html/index.xsl      |   288 +
 .../docbook/docbook-xsl-1.75.1/html/info.xsl       |    43 +
 .../docbook/docbook-xsl-1.75.1/html/inline.xsl     |  1485 +
 .../docbook/docbook-xsl-1.75.1/html/keywords.xsl   |    35 +
 .../docbook/docbook-xsl-1.75.1/html/lists.xsl      |  1183 +
 .../docbook/docbook-xsl-1.75.1/html/maketoc.xsl    |    86 +
 .../docbook/docbook-xsl-1.75.1/html/manifest.xsl   |    22 +
 .../docbook/docbook-xsl-1.75.1/html/math.xsl       |   270 +
 .../docbook/docbook-xsl-1.75.1/html/oldchunker.xsl |   202 +
 .../docbook/docbook-xsl-1.75.1/html/onechunk.xsl   |    37 +
 .../docbook/docbook-xsl-1.75.1/html/param.xml      | 10584 ++++++++
 .../docbook/docbook-xsl-1.75.1/html/param.xsl      |   429 +
 .../docgen/docbook/docbook-xsl-1.75.1/html/pi.xml  |  1113 +
 .../docgen/docbook/docbook-xsl-1.75.1/html/pi.xsl  |  1263 +
 .../docbook-xsl-1.75.1/html/profile-chunk-code.xsl |   608 +
 .../docbook-xsl-1.75.1/html/profile-chunk.xsl      |    52 +
 .../docbook-xsl-1.75.1/html/profile-docbook.xsl    |   414 +
 .../docbook-xsl-1.75.1/html/profile-onechunk.xsl   |    37 +
 .../docbook/docbook-xsl-1.75.1/html/qandaset.xsl   |   437 +
 .../docbook/docbook-xsl-1.75.1/html/refentry.xsl   |   299 +
 .../docbook/docbook-xsl-1.75.1/html/sections.xsl   |   615 +
 .../docbook/docbook-xsl-1.75.1/html/synop.xsl      |  1554 ++
 .../docbook/docbook-xsl-1.75.1/html/table.xsl      |  1147 +
 .../docbook/docbook-xsl-1.75.1/html/task.xsl       |    76 +
 .../html/titlepage.templates.xml                   |   686 +
 .../html/titlepage.templates.xsl                   |  3710 +++
 .../docbook/docbook-xsl-1.75.1/html/titlepage.xsl  |  1043 +
 .../docgen/docbook/docbook-xsl-1.75.1/html/toc.xsl |   350 +
 .../docbook/docbook-xsl-1.75.1/html/verbatim.xsl   |   403 +
 .../docbook/docbook-xsl-1.75.1/html/xref.xsl       |  1377 +
 .../htmlhelp/htmlhelp-common.xsl                   |  1120 +
 .../docbook-xsl-1.75.1/htmlhelp/htmlhelp.xsl       |    22 +
 .../htmlhelp/profile-htmlhelp-common.xsl           |  1083 +
 .../htmlhelp/profile-htmlhelp.xsl                  |    22 +
 .../docbook-xsl-1.75.1/images/annot-close.png      |   Bin 0 -> 207 bytes
 .../docbook-xsl-1.75.1/images/annot-open.png       |   Bin 0 -> 837 bytes
 .../docbook/docbook-xsl-1.75.1/images/blank.png    |   Bin 0 -> 374 bytes
 .../docbook-xsl-1.75.1/images/callouts/1.gif       |   Bin 0 -> 889 bytes
 .../docbook-xsl-1.75.1/images/callouts/1.png       |   Bin 0 -> 329 bytes
 .../docbook-xsl-1.75.1/images/callouts/1.svg       |    15 +
 .../docbook-xsl-1.75.1/images/callouts/10.gif      |   Bin 0 -> 929 bytes
 .../docbook-xsl-1.75.1/images/callouts/10.png      |   Bin 0 -> 361 bytes
 .../docbook-xsl-1.75.1/images/callouts/10.svg      |    18 +
 .../docbook-xsl-1.75.1/images/callouts/11.gif      |   Bin 0 -> 202 bytes
 .../docbook-xsl-1.75.1/images/callouts/11.png      |   Bin 0 -> 565 bytes
 .../docbook-xsl-1.75.1/images/callouts/11.svg      |    16 +
 .../docbook-xsl-1.75.1/images/callouts/12.gif      |   Bin 0 -> 210 bytes
 .../docbook-xsl-1.75.1/images/callouts/12.png      |   Bin 0 -> 617 bytes
 .../docbook-xsl-1.75.1/images/callouts/12.svg      |    18 +
 .../docbook-xsl-1.75.1/images/callouts/13.gif      |   Bin 0 -> 209 bytes
 .../docbook-xsl-1.75.1/images/callouts/13.png      |   Bin 0 -> 623 bytes
 .../docbook-xsl-1.75.1/images/callouts/13.svg      |    20 +
 .../docbook-xsl-1.75.1/images/callouts/14.gif      |   Bin 0 -> 205 bytes
 .../docbook-xsl-1.75.1/images/callouts/14.png      |   Bin 0 -> 411 bytes
 .../docbook-xsl-1.75.1/images/callouts/14.svg      |    17 +
 .../docbook-xsl-1.75.1/images/callouts/15.gif      |   Bin 0 -> 210 bytes
 .../docbook-xsl-1.75.1/images/callouts/15.png      |   Bin 0 -> 640 bytes
 .../docbook-xsl-1.75.1/images/callouts/15.svg      |    19 +
 .../docbook-xsl-1.75.1/images/callouts/16.svg      |    20 +
 .../docbook-xsl-1.75.1/images/callouts/17.svg      |    17 +
 .../docbook-xsl-1.75.1/images/callouts/18.svg      |    21 +
 .../docbook-xsl-1.75.1/images/callouts/19.svg      |    20 +
 .../docbook-xsl-1.75.1/images/callouts/2.gif       |   Bin 0 -> 907 bytes
 .../docbook-xsl-1.75.1/images/callouts/2.png       |   Bin 0 -> 353 bytes
 .../docbook-xsl-1.75.1/images/callouts/2.svg       |    17 +
 .../docbook-xsl-1.75.1/images/callouts/20.svg      |    20 +
 .../docbook-xsl-1.75.1/images/callouts/21.svg      |    18 +
 .../docbook-xsl-1.75.1/images/callouts/22.svg      |    20 +
 .../docbook-xsl-1.75.1/images/callouts/23.svg      |    22 +
 .../docbook-xsl-1.75.1/images/callouts/24.svg      |    19 +
 .../docbook-xsl-1.75.1/images/callouts/25.svg      |    21 +
 .../docbook-xsl-1.75.1/images/callouts/26.svg      |    22 +
 .../docbook-xsl-1.75.1/images/callouts/27.svg      |    19 +
 .../docbook-xsl-1.75.1/images/callouts/28.svg      |    23 +
 .../docbook-xsl-1.75.1/images/callouts/29.svg      |    22 +
 .../docbook-xsl-1.75.1/images/callouts/3.gif       |   Bin 0 -> 914 bytes
 .../docbook-xsl-1.75.1/images/callouts/3.png       |   Bin 0 -> 350 bytes
 .../docbook-xsl-1.75.1/images/callouts/3.svg       |    19 +
 .../docbook-xsl-1.75.1/images/callouts/30.svg      |    22 +
 .../docbook-xsl-1.75.1/images/callouts/4.gif       |   Bin 0 -> 907 bytes
 .../docbook-xsl-1.75.1/images/callouts/4.png       |   Bin 0 -> 345 bytes
 .../docbook-xsl-1.75.1/images/callouts/4.svg       |    16 +
 .../docbook-xsl-1.75.1/images/callouts/5.gif       |   Bin 0 -> 916 bytes
 .../docbook-xsl-1.75.1/images/callouts/5.png       |   Bin 0 -> 348 bytes
 .../docbook-xsl-1.75.1/images/callouts/5.svg       |    18 +
 .../docbook-xsl-1.75.1/images/callouts/6.gif       |   Bin 0 -> 218 bytes
 .../docbook-xsl-1.75.1/images/callouts/6.png       |   Bin 0 -> 355 bytes
 .../docbook-xsl-1.75.1/images/callouts/6.svg       |    19 +
 .../docbook-xsl-1.75.1/images/callouts/7.gif       |   Bin 0 -> 907 bytes
 .../docbook-xsl-1.75.1/images/callouts/7.png       |   Bin 0 -> 344 bytes
 .../docbook-xsl-1.75.1/images/callouts/7.svg       |    16 +
 .../docbook-xsl-1.75.1/images/callouts/8.gif       |   Bin 0 -> 918 bytes
 .../docbook-xsl-1.75.1/images/callouts/8.png       |   Bin 0 -> 357 bytes
 .../docbook-xsl-1.75.1/images/callouts/8.svg       |    20 +
 .../docbook-xsl-1.75.1/images/callouts/9.gif       |   Bin 0 -> 923 bytes
 .../docbook-xsl-1.75.1/images/callouts/9.png       |   Bin 0 -> 357 bytes
 .../docbook-xsl-1.75.1/images/callouts/9.svg       |    19 +
 .../docbook/docbook-xsl-1.75.1/images/caution.gif  |   Bin 0 -> 743 bytes
 .../docbook/docbook-xsl-1.75.1/images/caution.png  |   Bin 0 -> 1250 bytes
 .../docbook/docbook-xsl-1.75.1/images/caution.svg  |    25 +
 .../docbook/docbook-xsl-1.75.1/images/caution.tif  |   Bin 0 -> 1978 bytes
 .../docbook-xsl-1.75.1/images/colorsvg/caution.svg |   141 +
 .../docbook-xsl-1.75.1/images/colorsvg/home.svg    |   498 +
 .../images/colorsvg/important.svg                  |   239 +
 .../docbook-xsl-1.75.1/images/colorsvg/next.svg    |   338 +
 .../docbook-xsl-1.75.1/images/colorsvg/note.svg    |   200 +
 .../docbook-xsl-1.75.1/images/colorsvg/prev.svg    |   338 +
 .../docbook-xsl-1.75.1/images/colorsvg/tip.svg     |   367 +
 .../docbook-xsl-1.75.1/images/colorsvg/up.svg      |   338 +
 .../docbook-xsl-1.75.1/images/colorsvg/warning.svg |   232 +
 .../docbook/docbook-xsl-1.75.1/images/draft.png    |   Bin 0 -> 16150 bytes
 .../docbook/docbook-xsl-1.75.1/images/home.gif     |   Bin 0 -> 321 bytes
 .../docbook/docbook-xsl-1.75.1/images/home.png     |   Bin 0 -> 1156 bytes
 .../docbook/docbook-xsl-1.75.1/images/home.svg     |    26 +
 .../docbook-xsl-1.75.1/images/important.gif        |   Bin 0 -> 1003 bytes
 .../docbook-xsl-1.75.1/images/important.png        |   Bin 0 -> 722 bytes
 .../docbook-xsl-1.75.1/images/important.svg        |    25 +
 .../docbook-xsl-1.75.1/images/important.tif        |   Bin 0 -> 2020 bytes
 .../docbook/docbook-xsl-1.75.1/images/next.gif     |   Bin 0 -> 1083 bytes
 .../docbook/docbook-xsl-1.75.1/images/next.png     |   Bin 0 -> 1150 bytes
 .../docbook/docbook-xsl-1.75.1/images/next.svg     |    19 +
 .../docbook/docbook-xsl-1.75.1/images/note.gif     |   Bin 0 -> 580 bytes
 .../docbook/docbook-xsl-1.75.1/images/note.png     |   Bin 0 -> 490 bytes
 .../docbook/docbook-xsl-1.75.1/images/note.svg     |    33 +
 .../docbook/docbook-xsl-1.75.1/images/note.tif     |   Bin 0 -> 460 bytes
 .../docbook/docbook-xsl-1.75.1/images/prev.gif     |   Bin 0 -> 1118 bytes
 .../docbook/docbook-xsl-1.75.1/images/prev.png     |   Bin 0 -> 1132 bytes
 .../docbook/docbook-xsl-1.75.1/images/prev.svg     |    19 +
 .../docbook/docbook-xsl-1.75.1/images/tip.gif      |   Bin 0 -> 598 bytes
 .../docbook/docbook-xsl-1.75.1/images/tip.png      |   Bin 0 -> 449 bytes
 .../docbook/docbook-xsl-1.75.1/images/tip.svg      |    31 +
 .../docbook/docbook-xsl-1.75.1/images/tip.tif      |   Bin 0 -> 420 bytes
 .../docbook-xsl-1.75.1/images/toc-blank.png        |   Bin 0 -> 318 bytes
 .../docbook-xsl-1.75.1/images/toc-minus.png        |   Bin 0 -> 259 bytes
 .../docbook/docbook-xsl-1.75.1/images/toc-plus.png |   Bin 0 -> 264 bytes
 .../docbook/docbook-xsl-1.75.1/images/up.gif       |   Bin 0 -> 1089 bytes
 .../docbook/docbook-xsl-1.75.1/images/up.png       |   Bin 0 -> 1111 bytes
 .../docbook/docbook-xsl-1.75.1/images/up.svg       |    19 +
 .../docbook/docbook-xsl-1.75.1/images/warning.gif  |   Bin 0 -> 743 bytes
 .../docbook/docbook-xsl-1.75.1/images/warning.png  |   Bin 0 -> 1241 bytes
 .../docbook/docbook-xsl-1.75.1/images/warning.svg  |    23 +
 .../docbook/docbook-xsl-1.75.1/images/warning.tif  |   Bin 0 -> 1990 bytes
 .../docgen/docbook/docbook-xsl-1.75.1/install.sh   |   977 +
 .../docbook-xsl-1.75.1/javahelp/javahelp.xsl       |   625 +
 .../javahelp/profile-javahelp.xsl                  |   549 +
 .../docgen/docbook/docbook-xsl-1.75.1/lib/lib.xsl  |   480 +
 .../params/abstract.notitle.enabled.xml            |    22 +
 .../params/abstract.properties.xml                 |    32 +
 .../params/abstract.title.properties.xml           |    39 +
 .../docbook-xsl-1.75.1/params/active.toc.xml       |    29 +
 .../docbook-xsl-1.75.1/params/ade.extensions.xml   |    32 +
 .../params/admon.graphics.extension.xml            |    27 +
 .../params/admon.graphics.path.xml                 |    27 +
 .../docbook-xsl-1.75.1/params/admon.graphics.xml   |    29 +
 .../docbook-xsl-1.75.1/params/admon.style.xml      |    31 +
 .../docbook-xsl-1.75.1/params/admon.textlabel.xml  |    32 +
 .../params/admonition.properties.xml               |    25 +
 .../params/admonition.title.properties.xml         |    32 +
 .../docbook-xsl-1.75.1/params/alignment.xml        |    41 +
 .../docbook-xsl-1.75.1/params/annotate.toc.xml     |    28 +
 .../docbook-xsl-1.75.1/params/annotation.css.xml   |    71 +
 .../params/annotation.graphic.close.xml            |    31 +
 .../params/annotation.graphic.open.xml             |    28 +
 .../docbook-xsl-1.75.1/params/annotation.js.xml    |    33 +
 .../params/annotation.support.xml                  |    29 +
 .../params/appendix.autolabel.xml                  |    73 +
 .../params/arbortext.extensions.xml                |    30 +
 .../params/article.appendix.title.properties.xml   |    33 +
 .../params/author.othername.in.middle.xml          |    31 +
 .../docbook-xsl-1.75.1/params/autolayout-file.xml  |    29 +
 .../params/autotoc.label.in.hyperlink.xml          |    29 +
 .../params/autotoc.label.separator.xml             |    27 +
 .../docbook-xsl-1.75.1/params/axf.extensions.xml   |    33 +
 .../params/banner.before.navigation.xml            |    25 +
 .../docbook/docbook-xsl-1.75.1/params/base.dir.xml |    29 +
 .../params/biblioentry.item.separator.xml          |    26 +
 .../params/biblioentry.properties.xml              |    28 +
 .../params/bibliography.collection.xml             |   104 +
 .../params/bibliography.numbered.xml               |    27 +
 .../params/bibliography.style.xml                  |    35 +
 .../params/blockquote.properties.xml               |    34 +
 .../params/blurb.on.titlepage.enabled.xml          |    31 +
 .../docbook-xsl-1.75.1/params/body.attributes.xml  |    31 +
 .../docbook-xsl-1.75.1/params/body.bg.color.xml    |    28 +
 .../docbook-xsl-1.75.1/params/body.end.indent.xml  |    37 +
 .../docbook-xsl-1.75.1/params/body.font.family.xml |    32 +
 .../docbook-xsl-1.75.1/params/body.font.master.xml |    30 +
 .../docbook-xsl-1.75.1/params/body.font.size.xml   |    31 +
 .../params/body.margin.bottom.xml                  |    29 +
 .../docbook-xsl-1.75.1/params/body.margin.top.xml  |    28 +
 .../params/body.start.indent.xml                   |    64 +
 .../params/bookmarks.collapse.xml                  |    31 +
 .../params/bridgehead.in.toc.xml                   |    28 +
 .../docbook-xsl-1.75.1/params/bullet.image.xml     |    28 +
 .../params/callout.defaultcolumn.xml               |    30 +
 .../params/callout.graphics.extension.xml          |    33 +
 .../params/callout.graphics.number.limit.xml       |    34 +
 .../params/callout.graphics.path.xml               |    31 +
 .../docbook-xsl-1.75.1/params/callout.graphics.xml |    30 +
 .../params/callout.icon.size.xml                   |    28 +
 .../params/callout.list.table.xml                  |    32 +
 .../params/callout.unicode.font.xml                |    29 +
 .../params/callout.unicode.number.limit.xml        |    35 +
 .../params/callout.unicode.start.character.xml     |    33 +
 .../docbook-xsl-1.75.1/params/callout.unicode.xml  |    26 +
 .../params/callouts.extension.xml                  |    30 +
 .../params/chapter.autolabel.xml                   |    71 +
 .../docbook-xsl-1.75.1/params/chunk.append.xml     |    30 +
 .../params/chunk.first.sections.xml                |    31 +
 .../docbook-xsl-1.75.1/params/chunk.quietly.xml    |    30 +
 .../params/chunk.section.depth.xml                 |    27 +
 .../docbook-xsl-1.75.1/params/chunk.sections.xml   |    30 +
 .../params/chunk.separate.lots.xml                 |    36 +
 .../docbook-xsl-1.75.1/params/chunk.toc.xml        |    30 +
 .../params/chunk.tocs.and.lots.has.title.xml       |    28 +
 .../params/chunk.tocs.and.lots.xml                 |    32 +
 .../chunker.output.cdata-section-elements.xml      |    30 +
 .../params/chunker.output.doctype-public.xml       |    31 +
 .../params/chunker.output.doctype-system.xml       |    31 +
 .../params/chunker.output.encoding.xml             |    31 +
 .../params/chunker.output.indent.xml               |    30 +
 .../params/chunker.output.media-type.xml           |    35 +
 .../params/chunker.output.method.xml               |    32 +
 .../params/chunker.output.omit-xml-declaration.xml |    30 +
 .../params/chunker.output.standalone.xml           |    31 +
 .../params/citerefentry.link.xml                   |    29 +
 .../params/collect.xref.targets.xml                |    33 +
 .../params/column.count.back.xml                   |    27 +
 .../params/column.count.body.xml                   |    27 +
 .../params/column.count.front.xml                  |    27 +
 .../params/column.count.index.xml                  |    27 +
 .../docbook-xsl-1.75.1/params/column.count.lot.xml |    28 +
 .../params/column.count.titlepage.xml              |    27 +
 .../docbook-xsl-1.75.1/params/column.gap.back.xml  |    28 +
 .../docbook-xsl-1.75.1/params/column.gap.body.xml  |    28 +
 .../docbook-xsl-1.75.1/params/column.gap.front.xml |    28 +
 .../docbook-xsl-1.75.1/params/column.gap.index.xml |    28 +
 .../docbook-xsl-1.75.1/params/column.gap.lot.xml   |    28 +
 .../params/column.gap.titlepage.xml                |    29 +
 .../params/compact.list.item.spacing.xml           |    28 +
 .../params/component.label.includes.part.label.xml |    39 +
 .../params/component.title.properties.xml          |    40 +
 .../params/component.titlepage.properties.xml      |    33 +
 .../params/contrib.inline.enabled.xml              |    26 +
 .../docbook-xsl-1.75.1/params/crop.mark.bleed.xml  |    28 +
 .../docbook-xsl-1.75.1/params/crop.mark.offset.xml |    28 +
 .../docbook-xsl-1.75.1/params/crop.mark.width.xml  |    28 +
 .../docbook-xsl-1.75.1/params/crop.marks.xml       |    28 +
 .../docbook-xsl-1.75.1/params/css.decoration.xml   |    33 +
 .../params/css.stylesheet.dir.xml                  |    33 +
 .../docbook-xsl-1.75.1/params/css.stylesheet.xml   |    29 +
 .../docbook-xsl-1.75.1/params/current.docid.xml    |    27 +
 .../params/currentpage.marker.xml                  |    25 +
 .../params/default.float.class.xml                 |    34 +
 .../params/default.image.width.xml                 |    31 +
 .../params/default.table.frame.xml                 |    28 +
 .../params/default.table.rules.xml                 |    76 +
 .../params/default.table.width.xml                 |    26 +
 .../docbook-xsl-1.75.1/params/default.units.xml    |    37 +
 .../params/dingbat.font.family.xml                 |    33 +
 .../docbook-xsl-1.75.1/params/double.sided.xml     |    31 +
 .../docbook-xsl-1.75.1/params/draft.mode.xml       |    36 +
 .../params/draft.watermark.image.xml               |    27 +
 .../docbook/docbook-xsl-1.75.1/params/dry-run.xml  |    27 +
 .../docbook-xsl-1.75.1/params/dynamic.toc.xml      |    29 +
 .../docbook-xsl-1.75.1/params/ebnf.assignment.xml  |    39 +
 .../params/ebnf.statement.terminator.xml           |    32 +
 .../params/ebnf.table.bgcolor.xml                  |    30 +
 .../params/ebnf.table.border.xml                   |    26 +
 .../params/eclipse.autolabel.xml                   |    28 +
 .../params/eclipse.plugin.id.xml                   |    28 +
 .../params/eclipse.plugin.name.xml                 |    27 +
 .../params/eclipse.plugin.provider.xml             |    27 +
 .../docbook-xsl-1.75.1/params/editedby.enabled.xml |    27 +
 .../params/email.delimiters.enabled.xml            |    34 +
 .../params/email.mailto.enabled.xml                |    29 +
 .../params/emphasis.propagates.style.xml           |    26 +
 .../params/entry.propagates.style.xml              |    30 +
 .../docbook-xsl-1.75.1/params/epub.autolabel.xml   |    28 +
 .../params/equation.properties.xml                 |    27 +
 .../params/example.properties.xml                  |    27 +
 .../params/exsl.node.set.available.xml             |    44 +
 .../docbook-xsl-1.75.1/params/feedback.href.xml    |    28 +
 .../params/feedback.link.text.xml                  |    28 +
 .../params/feedback.with.ids.xml                   |    27 +
 .../params/figure.properties.xml                   |    27 +
 .../docbook-xsl-1.75.1/params/filename-prefix.xml  |    28 +
 .../params/firstterm.only.link.xml                 |    29 +
 .../docbook-xsl-1.75.1/params/foil.properties.xml  |    37 +
 .../params/foil.subtitle.properties.xml            |    36 +
 .../params/foil.title.master.xml                   |    29 +
 .../docbook-xsl-1.75.1/params/foil.title.size.xml  |    32 +
 .../params/foilgroup.properties.xml                |    31 +
 .../docbook-xsl-1.75.1/params/foilgroup.toc.xml    |    29 +
 .../params/footer.column.widths.xml                |    80 +
 .../params/footer.content.properties.xml           |    34 +
 .../docbook-xsl-1.75.1/params/footer.hr.xml        |    26 +
 .../docbook-xsl-1.75.1/params/footer.rule.xml      |    27 +
 .../params/footer.table.height.xml                 |    32 +
 .../params/footer.table.properties.xml             |    30 +
 .../params/footers.on.blank.pages.xml              |    27 +
 .../params/footnote.font.size.xml                  |    28 +
 .../params/footnote.mark.properties.xml            |    41 +
 .../params/footnote.number.format.xml              |    33 +
 .../params/footnote.number.symbols.xml             |    39 +
 .../params/footnote.properties.xml                 |    43 +
 .../params/footnote.sep.leader.properties.xml      |    39 +
 .../docbook-xsl-1.75.1/params/fop.extensions.xml   |    36 +
 .../docbook-xsl-1.75.1/params/fop1.extensions.xml  |    34 +
 .../params/formal.object.properties.xml            |    36 +
 .../params/formal.procedures.xml                   |    28 +
 .../params/formal.title.placement.xml              |    41 +
 .../params/formal.title.properties.xml             |    34 +
 .../params/funcsynopsis.decoration.xml             |    30 +
 .../params/funcsynopsis.style.xml                  |    31 +
 .../docbook-xsl-1.75.1/params/function.parens.xml  |    29 +
 .../params/generate.id.attributes.xml              |    59 +
 .../docbook-xsl-1.75.1/params/generate.index.xml   |    25 +
 .../params/generate.legalnotice.link.xml           |    72 +
 .../params/generate.manifest.xml                   |    27 +
 .../params/generate.meta.abstract.xml              |    29 +
 .../params/generate.revhistory.link.xml            |    50 +
 .../params/generate.section.toc.level.xml          |    35 +
 .../docbook-xsl-1.75.1/params/generate.toc.xml     |   108 +
 .../params/glossary.as.blocks.xml                  |    38 +
 .../params/glossary.collection.xml                 |   252 +
 .../docbook-xsl-1.75.1/params/glossary.sort.xml    |    32 +
 .../params/glossdef.block.properties.xml           |    32 +
 .../params/glossdef.list.properties.xml            |    30 +
 .../params/glossentry.list.item.properties.xml     |    32 +
 .../params/glossentry.show.acronym.xml             |    37 +
 .../params/glosslist.as.blocks.xml                 |    27 +
 .../params/glossterm.auto.link.xml                 |    33 +
 .../params/glossterm.block.properties.xml          |    35 +
 .../params/glossterm.list.properties.xml           |    30 +
 .../params/glossterm.separation.xml                |    31 +
 .../docbook-xsl-1.75.1/params/glossterm.width.xml  |    28 +
 .../params/graphic.default.extension.xml           |    29 +
 .../params/graphical.admonition.properties.xml     |    42 +
 .../docbook-xsl-1.75.1/params/graphics.dir.xml     |    33 +
 .../params/graphicsize.extension.xml               |    30 +
 .../params/graphicsize.use.img.src.path.xml        |    30 +
 .../params/header.column.widths.xml                |    80 +
 .../params/header.content.properties.xml           |    34 +
 .../docbook-xsl-1.75.1/params/header.hr.xml        |    26 +
 .../docbook-xsl-1.75.1/params/header.rule.xml      |    27 +
 .../params/header.table.height.xml                 |    32 +
 .../params/header.table.properties.xml             |    30 +
 .../params/headers.on.blank.pages.xml              |    27 +
 .../docbook-xsl-1.75.1/params/hidetoc.image.xml    |    29 +
 .../params/highlight.default.language.xml          |    27 +
 .../docbook-xsl-1.75.1/params/highlight.source.xml |    82 +
 .../params/highlight.xslthl.config.xml             |    27 +
 .../docbook-xsl-1.75.1/params/home.image.xml       |    27 +
 .../docbook-xsl-1.75.1/params/html.append.xml      |    30 +
 .../docbook-xsl-1.75.1/params/html.base.xml        |    30 +
 .../docbook-xsl-1.75.1/params/html.cellpadding.xml |    29 +
 .../docbook-xsl-1.75.1/params/html.cellspacing.xml |    29 +
 .../docbook-xsl-1.75.1/params/html.cleanup.xml     |    34 +
 .../docbook/docbook-xsl-1.75.1/params/html.ext.xml |    29 +
 .../params/html.extra.head.links.xml               |    31 +
 .../params/html.head.legalnotice.link.multiple.xml |    44 +
 .../params/html.head.legalnotice.link.types.xml    |    75 +
 .../params/html.longdesc.link.xml                  |    39 +
 .../docbook-xsl-1.75.1/params/html.longdesc.xml    |    28 +
 .../params/html.stylesheet.type.xml                |    26 +
 .../docbook-xsl-1.75.1/params/html.stylesheet.xml  |    36 +
 .../params/htmlhelp.alias.file.xml                 |    27 +
 .../params/htmlhelp.autolabel.xml                  |    28 +
 .../params/htmlhelp.button.back.xml                |    27 +
 .../params/htmlhelp.button.forward.xml             |    27 +
 .../params/htmlhelp.button.hideshow.xml            |    27 +
 .../params/htmlhelp.button.home.url.xml            |    27 +
 .../params/htmlhelp.button.home.xml                |    27 +
 .../params/htmlhelp.button.jump1.title.xml         |    27 +
 .../params/htmlhelp.button.jump1.url.xml           |    27 +
 .../params/htmlhelp.button.jump1.xml               |    23 +
 .../params/htmlhelp.button.jump2.title.xml         |    27 +
 .../params/htmlhelp.button.jump2.url.xml           |    27 +
 .../params/htmlhelp.button.jump2.xml               |    27 +
 .../params/htmlhelp.button.locate.xml              |    28 +
 .../params/htmlhelp.button.next.xml                |    27 +
 .../params/htmlhelp.button.options.xml             |    28 +
 .../params/htmlhelp.button.prev.xml                |    28 +
 .../params/htmlhelp.button.print.xml               |    28 +
 .../params/htmlhelp.button.refresh.xml             |    27 +
 .../params/htmlhelp.button.stop.xml                |    28 +
 .../params/htmlhelp.button.zoom.xml                |    28 +
 .../docbook-xsl-1.75.1/params/htmlhelp.chm.xml     |    27 +
 .../params/htmlhelp.default.topic.xml              |    37 +
 .../params/htmlhelp.display.progress.xml           |    28 +
 .../params/htmlhelp.encoding.xml                   |    28 +
 .../params/htmlhelp.enhanced.decompilation.xml     |    27 +
 .../params/htmlhelp.enumerate.images.xml           |    28 +
 .../params/htmlhelp.force.map.and.alias.xml        |    26 +
 .../params/htmlhelp.hhc.binary.xml                 |    29 +
 .../params/htmlhelp.hhc.folders.instead.books.xml  |    33 +
 .../params/htmlhelp.hhc.section.depth.xml          |    27 +
 .../params/htmlhelp.hhc.show.root.xml              |    29 +
 .../params/htmlhelp.hhc.width.xml                  |    28 +
 .../docbook-xsl-1.75.1/params/htmlhelp.hhc.xml     |    27 +
 .../docbook-xsl-1.75.1/params/htmlhelp.hhk.xml     |    27 +
 .../params/htmlhelp.hhp.tail.xml                   |    28 +
 .../params/htmlhelp.hhp.window.xml                 |    28 +
 .../params/htmlhelp.hhp.windows.xml                |    29 +
 .../docbook-xsl-1.75.1/params/htmlhelp.hhp.xml     |    28 +
 .../params/htmlhelp.map.file.xml                   |    25 +
 .../docbook-xsl-1.75.1/params/htmlhelp.only.xml    |    32 +
 .../params/htmlhelp.remember.window.position.xml   |    27 +
 .../params/htmlhelp.show.advanced.search.xml       |    28 +
 .../params/htmlhelp.show.favorities.xml            |    28 +
 .../params/htmlhelp.show.menu.xml                  |    28 +
 .../params/htmlhelp.show.toolbar.text.xml          |    28 +
 .../docbook-xsl-1.75.1/params/htmlhelp.title.xml   |    28 +
 .../docbook-xsl-1.75.1/params/htmlhelp.use.hhk.xml |    28 +
 .../params/htmlhelp.window.geometry.xml            |    30 +
 .../params/hyphenate.verbatim.characters.xml       |    30 +
 .../params/hyphenate.verbatim.xml                  |    45 +
 .../docbook-xsl-1.75.1/params/hyphenate.xml        |    29 +
 .../docbook-xsl-1.75.1/params/id.warnings.xml      |    25 +
 .../params/ignore.image.scaling.xml                |    28 +
 .../docbook-xsl-1.75.1/params/img.src.path.xml     |    40 +
 .../params/index.div.title.properties.xml          |    39 +
 .../params/index.entry.properties.xml              |    33 +
 .../params/index.links.to.section.xml              |    76 +
 .../docbook-xsl-1.75.1/params/index.method.xml     |   162 +
 .../params/index.number.separator.xml              |    54 +
 .../docbook-xsl-1.75.1/params/index.on.role.xml    |    48 +
 .../docbook-xsl-1.75.1/params/index.on.type.xml    |    52 +
 .../params/index.page.number.properties.xml        |    31 +
 .../params/index.prefer.titleabbrev.xml            |    29 +
 .../params/index.preferred.page.properties.xml     |    32 +
 .../params/index.range.separator.xml               |    57 +
 .../params/index.term.separator.xml                |    54 +
 .../params/informal.object.properties.xml          |    29 +
 .../params/informalequation.properties.xml         |    27 +
 .../params/informalexample.properties.xml          |    27 +
 .../params/informalfigure.properties.xml           |    27 +
 .../params/informaltable.properties.xml            |    32 +
 .../docbook-xsl-1.75.1/params/inherit.keywords.xml |    31 +
 .../params/insert.link.page.number.xml             |    69 +
 .../params/insert.olink.page.number.xml            |    83 +
 .../params/insert.olink.pdf.frag.xml               |    68 +
 .../params/insert.xref.page.number.xml             |    60 +
 .../params/itemizedlist.label.properties.xml       |    26 +
 .../params/itemizedlist.label.width.xml            |    28 +
 .../params/itemizedlist.properties.xml             |    23 +
 .../params/javahelp.encoding.xml                   |    31 +
 .../params/keep.relative.image.uris.xml            |    34 +
 .../docbook-xsl-1.75.1/params/keyboard.nav.xml     |    29 +
 .../params/l10n.gentext.default.language.xml       |    30 +
 .../params/l10n.gentext.language.xml               |    33 +
 .../params/l10n.gentext.use.xref.language.xml      |    53 +
 .../params/l10n.lang.value.rfc.compliant.xml       |    57 +
 .../docbook-xsl-1.75.1/params/label.from.part.xml  |    38 +
 .../docbook-xsl-1.75.1/params/line-height.xml      |    27 +
 .../params/linenumbering.everyNth.xml              |    29 +
 .../params/linenumbering.extension.xml             |    30 +
 .../params/linenumbering.separator.xml             |    30 +
 .../params/linenumbering.width.xml                 |    29 +
 .../docbook-xsl-1.75.1/params/link.mailto.url.xml  |    29 +
 .../params/list.block.properties.xml               |    25 +
 .../params/list.block.spacing.xml                  |    29 +
 .../params/list.item.spacing.xml                   |    26 +
 .../params/make.graphic.viewport.xml               |    35 +
 .../params/make.index.markup.xml                   |    73 +
 .../params/make.single.year.ranges.xml             |    28 +
 .../docbook-xsl-1.75.1/params/make.valid.html.xml  |    35 +
 .../docbook-xsl-1.75.1/params/make.year.ranges.xml |    32 +
 .../params/man.authors.section.enabled.xml         |    46 +
 .../params/man.base.url.for.relative.links.xml     |    76 +
 .../params/man.break.after.slash.xml               |    46 +
 .../params/man.charmap.enabled.xml                 |    55 +
 .../params/man.charmap.subset.profile.english.xml  |    80 +
 .../params/man.charmap.subset.profile.xml          |   297 +
 .../docbook-xsl-1.75.1/params/man.charmap.uri.xml  |    42 +
 .../params/man.charmap.use.subset.xml              |    80 +
 .../params/man.copyright.section.enabled.xml       |    46 +
 .../params/man.endnotes.are.numbered.xml           |   106 +
 .../params/man.endnotes.list.enabled.xml           |   105 +
 .../params/man.endnotes.list.heading.xml           |    36 +
 .../params/man.font.funcprototype.xml              |    30 +
 .../params/man.font.funcsynopsisinfo.xml           |    30 +
 .../docbook-xsl-1.75.1/params/man.font.links.xml   |    64 +
 .../params/man.font.table.headings.xml             |    30 +
 .../params/man.font.table.title.xml                |    30 +
 .../params/man.funcsynopsis.style.xml              |    26 +
 .../params/man.hyphenate.computer.inlines.xml      |    53 +
 .../params/man.hyphenate.filenames.xml             |    47 +
 .../params/man.hyphenate.urls.xml                  |    46 +
 .../docbook-xsl-1.75.1/params/man.hyphenate.xml    |    59 +
 .../params/man.indent.blurbs.xml                   |    33 +
 .../docbook-xsl-1.75.1/params/man.indent.lists.xml |    35 +
 .../params/man.indent.refsect.xml                  |    70 +
 .../params/man.indent.verbatims.xml                |    33 +
 .../docbook-xsl-1.75.1/params/man.indent.width.xml |    39 +
 .../docbook-xsl-1.75.1/params/man.justify.xml      |    52 +
 .../params/man.output.base.dir.xml                 |    39 +
 .../params/man.output.better.ps.enabled.xml        |    61 +
 .../params/man.output.encoding.xml                 |    53 +
 .../params/man.output.in.separate.dir.xml          |    32 +
 .../params/man.output.lang.in.name.enabled.xml     |    50 +
 .../params/man.output.manifest.enabled.xml         |    27 +
 .../params/man.output.manifest.filename.xml        |    29 +
 .../params/man.output.quietly.xml                  |    37 +
 .../params/man.output.subdirs.enabled.xml          |    40 +
 .../params/man.segtitle.suppress.xml               |    28 +
 .../params/man.string.subst.map.local.post.xml     |    34 +
 .../params/man.string.subst.map.local.pre.xml      |    34 +
 .../params/man.string.subst.map.xml                |   162 +
 .../params/man.subheading.divider.enabled.xml      |    37 +
 .../params/man.subheading.divider.xml              |    37 +
 .../params/man.table.footnotes.divider.xml         |    29 +
 .../params/man.th.extra1.suppress.xml              |    32 +
 .../params/man.th.extra2.max.length.xml            |    43 +
 .../params/man.th.extra2.suppress.xml              |    44 +
 .../params/man.th.extra3.max.length.xml            |    42 +
 .../params/man.th.extra3.suppress.xml              |    34 +
 .../params/man.th.title.max.length.xml             |    63 +
 .../params/manifest.in.base.dir.xml                |    29 +
 .../docbook/docbook-xsl-1.75.1/params/manifest.xml |    29 +
 .../docbook-xsl-1.75.1/params/manual.toc.xml       |    29 +
 .../params/margin.note.float.type.xml              |    77 +
 .../params/margin.note.properties.xml              |    54 +
 .../params/margin.note.title.properties.xml        |    32 +
 .../params/margin.note.width.xml                   |    35 +
 .../params/marker.section.level.xml                |    50 +
 .../params/menuchoice.menu.separator.xml           |    42 +
 .../params/menuchoice.separator.xml                |    32 +
 .../docbook-xsl-1.75.1/params/minus.image.xml      |    29 +
 .../params/monospace.font.family.xml               |    29 +
 .../params/monospace.properties.xml                |    38 +
 .../params/monospace.verbatim.font.width.xml       |    40 +
 .../params/monospace.verbatim.properties.xml       |    27 +
 .../params/multiframe.bottom.bgcolor.xml           |    28 +
 .../params/multiframe.navigation.height.xml        |    28 +
 .../params/multiframe.top.bgcolor.xml              |    28 +
 .../docbook-xsl-1.75.1/params/multiframe.xml       |    31 +
 .../docbook-xsl-1.75.1/params/nav.separator.xml    |    28 +
 .../params/nav.table.summary.xml                   |    27 +
 .../docbook-xsl-1.75.1/params/navbgcolor.xml       |    26 +
 .../docbook-xsl-1.75.1/params/navbodywidth.xml     |    26 +
 .../params/navig.graphics.extension.xml            |    28 +
 .../params/navig.graphics.path.xml                 |    30 +
 .../docbook-xsl-1.75.1/params/navig.graphics.xml   |    31 +
 .../docbook-xsl-1.75.1/params/navig.showtitles.xml |    32 +
 .../docbook-xsl-1.75.1/params/navtocwidth.xml      |    26 +
 .../docbook-xsl-1.75.1/params/next.image.xml       |    27 +
 .../docbook-xsl-1.75.1/params/no.home.image.xml    |    27 +
 .../docbook-xsl-1.75.1/params/no.next.image.xml    |    27 +
 .../docbook-xsl-1.75.1/params/no.prev.image.xml    |    27 +
 .../docbook-xsl-1.75.1/params/no.toc.image.xml     |    27 +
 .../docbook-xsl-1.75.1/params/no.up.image.xml      |    27 +
 .../params/nominal.image.depth.xml                 |    27 +
 .../params/nominal.image.width.xml                 |    43 +
 .../params/nominal.table.width.xml                 |    30 +
 .../params/nongraphical.admonition.properties.xml  |    41 +
 .../params/normal.para.spacing.xml                 |    26 +
 .../docbook-xsl-1.75.1/params/olink.base.uri.xml   |    35 +
 .../docbook-xsl-1.75.1/params/olink.debug.xml      |    36 +
 .../docbook-xsl-1.75.1/params/olink.doctitle.xml   |   146 +
 .../docbook-xsl-1.75.1/params/olink.fragid.xml     |    23 +
 .../params/olink.lang.fallback.sequence.xml        |    83 +
 .../params/olink.outline.ext.xml                   |    28 +
 .../docbook-xsl-1.75.1/params/olink.properties.xml |    33 +
 .../docbook-xsl-1.75.1/params/olink.pubid.xml      |    27 +
 .../docbook-xsl-1.75.1/params/olink.resolver.xml   |    23 +
 .../docbook-xsl-1.75.1/params/olink.sysid.xml      |    27 +
 .../params/orderedlist.label.properties.xml        |    26 +
 .../params/orderedlist.label.width.xml             |    28 +
 .../params/orderedlist.properties.xml              |    24 +
 .../params/othercredit.like.author.enabled.xml     |    31 +
 .../docbook-xsl-1.75.1/params/output-root.xml      |    28 +
 .../docbook-xsl-1.75.1/params/output.indent.xml    |    32 +
 .../docbook-xsl-1.75.1/params/overlay.js.xml       |    28 +
 .../docbook-xsl-1.75.1/params/overlay.logo.xml     |    28 +
 .../docbook/docbook-xsl-1.75.1/params/overlay.xml  |    32 +
 .../params/page.height.portrait.xml                |    69 +
 .../docbook-xsl-1.75.1/params/page.height.xml      |    37 +
 .../params/page.margin.bottom.xml                  |    29 +
 .../params/page.margin.inner.xml                   |    56 +
 .../params/page.margin.outer.xml                   |    53 +
 .../docbook-xsl-1.75.1/params/page.margin.top.xml  |    28 +
 .../docbook-xsl-1.75.1/params/page.orientation.xml |    32 +
 .../params/page.width.portrait.xml                 |    67 +
 .../docbook-xsl-1.75.1/params/page.width.xml       |    36 +
 .../docbook-xsl-1.75.1/params/pages.template.xml   |    29 +
 .../docbook-xsl-1.75.1/params/paper.type.xml       |    71 +
 .../params/para.propagates.style.xml               |    29 +
 .../docbook-xsl-1.75.1/params/part.autolabel.xml   |    73 +
 .../params/passivetex.extensions.xml               |    37 +
 .../params/pgwide.properties.xml                   |    52 +
 .../params/phrase.propagates.style.xml             |    29 +
 .../docbook-xsl-1.75.1/params/pixels.per.inch.xml  |    31 +
 .../docbook-xsl-1.75.1/params/plus.image.xml       |    29 +
 .../docbook-xsl-1.75.1/params/points.per.em.xml    |    29 +
 .../params/preface.autolabel.xml                   |    71 +
 .../params/prefer.internal.olink.xml               |    78 +
 .../params/preferred.mediaobject.role.xml          |    40 +
 .../docbook-xsl-1.75.1/params/prev.image.xml       |    27 +
 .../params/procedure.properties.xml                |    29 +
 .../params/process.empty.source.toc.xml            |    39 +
 .../params/process.source.toc.xml                  |    39 +
 .../docbook-xsl-1.75.1/params/profile.arch.xml     |    39 +
 .../params/profile.attribute.xml                   |    34 +
 .../docbook-xsl-1.75.1/params/profile.audience.xml |    38 +
 .../params/profile.condition.xml                   |    38 +
 .../params/profile.conformance.xml                 |    38 +
 .../docbook-xsl-1.75.1/params/profile.lang.xml     |    38 +
 .../docbook-xsl-1.75.1/params/profile.os.xml       |    38 +
 .../docbook-xsl-1.75.1/params/profile.revision.xml |    38 +
 .../params/profile.revisionflag.xml                |    38 +
 .../docbook-xsl-1.75.1/params/profile.role.xml     |    54 +
 .../docbook-xsl-1.75.1/params/profile.security.xml |    38 +
 .../params/profile.separator.xml                   |    27 +
 .../docbook-xsl-1.75.1/params/profile.status.xml   |    38 +
 .../params/profile.userlevel.xml                   |    38 +
 .../docbook-xsl-1.75.1/params/profile.value.xml    |    41 +
 .../docbook-xsl-1.75.1/params/profile.vendor.xml   |    38 +
 .../docbook-xsl-1.75.1/params/profile.wordsize.xml |    38 +
 .../docbook-xsl-1.75.1/params/punct.honorific.xml  |    28 +
 .../params/qanda.defaultlabel.xml                  |    86 +
 .../docbook-xsl-1.75.1/params/qanda.in.toc.xml     |    34 +
 .../params/qanda.inherit.numeration.xml            |    30 +
 .../params/qanda.nested.in.toc.xml                 |    29 +
 .../params/qanda.title.level1.properties.xml       |    32 +
 .../params/qanda.title.level2.properties.xml       |    32 +
 .../params/qanda.title.level3.properties.xml       |    32 +
 .../params/qanda.title.level4.properties.xml       |    32 +
 .../params/qanda.title.level5.properties.xml       |    32 +
 .../params/qanda.title.level6.properties.xml       |    34 +
 .../params/qanda.title.properties.xml              |    37 +
 .../params/qandadiv.autolabel.xml                  |    26 +
 .../docbook-xsl-1.75.1/params/rebuild-all.xml      |    33 +
 .../params/refclass.suppress.xml                   |    28 +
 .../params/refentry.date.profile.enabled.xml       |    46 +
 .../params/refentry.date.profile.xml               |    38 +
 .../params/refentry.generate.name.xml              |    33 +
 .../params/refentry.generate.title.xml             |    33 +
 .../params/refentry.manual.fallback.profile.xml    |    48 +
 .../params/refentry.manual.profile.enabled.xml     |    47 +
 .../params/refentry.manual.profile.xml             |    72 +
 .../params/refentry.meta.get.quietly.xml           |    37 +
 .../params/refentry.pagebreak.xml                  |    33 +
 .../params/refentry.separator.xml                  |    29 +
 .../params/refentry.source.fallback.profile.xml    |    49 +
 .../refentry.source.name.profile.enabled.xml       |    48 +
 .../params/refentry.source.name.profile.xml        |    89 +
 .../params/refentry.source.name.suppress.xml       |    42 +
 .../params/refentry.title.properties.xml           |    59 +
 .../params/refentry.version.profile.enabled.xml    |    47 +
 .../params/refentry.version.profile.xml            |    41 +
 .../params/refentry.version.suppress.xml           |    43 +
 .../params/refentry.xref.manvolnum.xml             |    31 +
 .../params/reference.autolabel.xml                 |    67 +
 .../params/region.after.extent.xml                 |    29 +
 .../params/region.before.extent.xml                |    29 +
 .../params/revhistory.table.cell.properties.xml    |    28 +
 .../params/revhistory.table.properties.xml         |    28 +
 .../params/revhistory.title.properties.xml         |    28 +
 .../docbook-xsl-1.75.1/params/root.filename.xml    |    29 +
 .../docbook-xsl-1.75.1/params/root.properties.xml  |    46 +
 .../docbook/docbook-xsl-1.75.1/params/rootid.xml   |    33 +
 .../params/runinhead.default.title.end.punct.xml   |    27 +
 .../params/runinhead.title.end.punct.xml           |    32 +
 .../params/running.foot.properties.xml             |    34 +
 .../docbook-xsl-1.75.1/params/sans.font.family.xml |    29 +
 .../docbook-xsl-1.75.1/params/saxon.callouts.xml   |    30 +
 .../params/saxon.character.representation.xml      |    38 +
 .../params/saxon.linenumbering.xml                 |    32 +
 .../params/saxon.tablecolumns.xml                  |    30 +
 .../docbook-xsl-1.75.1/params/script.dir.xml       |    33 +
 .../params/section.autolabel.max.depth.xml         |    32 +
 .../params/section.autolabel.xml                   |    26 +
 .../params/section.container.element.xml           |    62 +
 .../section.label.includes.component.label.xml     |    27 +
 .../params/section.level1.properties.xml           |    43 +
 .../params/section.level2.properties.xml           |    43 +
 .../params/section.level3.properties.xml           |    43 +
 .../params/section.level4.properties.xml           |    43 +
 .../params/section.level5.properties.xml           |    43 +
 .../params/section.level6.properties.xml           |    43 +
 .../params/section.properties.xml                  |    35 +
 .../params/section.title.level1.properties.xml     |    32 +
 .../params/section.title.level2.properties.xml     |    33 +
 .../params/section.title.level3.properties.xml     |    32 +
 .../params/section.title.level4.properties.xml     |    32 +
 .../params/section.title.level5.properties.xml     |    32 +
 .../params/section.title.level6.properties.xml     |    33 +
 .../params/section.title.properties.xml            |    39 +
 .../params/segmentedlist.as.table.xml              |    28 +
 .../docbook-xsl-1.75.1/params/sequential.links.xml |    25 +
 .../params/shade.verbatim.style.xml                |    36 +
 .../docbook-xsl-1.75.1/params/shade.verbatim.xml   |    30 +
 .../docbook-xsl-1.75.1/params/show.comments.xml    |    32 +
 .../docbook-xsl-1.75.1/params/show.foil.number.xml |    28 +
 .../params/show.revisionflag.xml                   |    42 +
 .../docbook-xsl-1.75.1/params/showtoc.image.xml    |    29 +
 .../params/side.float.properties.xml               |    50 +
 .../params/sidebar.float.type.xml                  |    90 +
 .../params/sidebar.float.width.xml                 |    35 +
 .../params/sidebar.properties.xml                  |    42 +
 .../params/sidebar.title.properties.xml            |    32 +
 .../params/simplesect.in.toc.xml                   |    26 +
 .../params/slide.font.family.xml                   |    31 +
 .../params/slide.title.font.family.xml             |    31 +
 .../docbook-xsl-1.75.1/params/slides.js.xml        |    28 +
 .../params/slides.properties.xml                   |    31 +
 .../docbook-xsl-1.75.1/params/spacing.paras.xml    |    30 +
 .../params/speakernote.properties.xml              |    32 +
 .../params/subscript.properties.xml                |    29 +
 .../params/superscript.properties.xml              |    29 +
 .../params/suppress.footer.navigation.xml          |    26 +
 .../params/suppress.header.navigation.xml          |    27 +
 .../params/suppress.homepage.title.xml             |    25 +
 .../params/suppress.navigation.xml                 |    28 +
 .../params/symbol.font.family.xml                  |    45 +
 .../params/table.borders.with.css.xml              |    28 +
 .../params/table.cell.border.color.xml             |    39 +
 .../params/table.cell.border.style.xml             |    42 +
 .../params/table.cell.border.thickness.xml         |    35 +
 .../params/table.cell.padding.xml                  |    32 +
 .../params/table.entry.padding.xml                 |    27 +
 .../params/table.footnote.number.format.xml        |    33 +
 .../params/table.footnote.number.symbols.xml       |    39 +
 .../params/table.footnote.properties.xml           |    39 +
 .../params/table.frame.border.color.xml            |    28 +
 .../params/table.frame.border.style.xml            |    37 +
 .../params/table.frame.border.thickness.xml        |    27 +
 .../docbook-xsl-1.75.1/params/table.properties.xml |    34 +
 .../params/table.spacer.image.xml                  |    26 +
 .../params/table.table.properties.xml              |    36 +
 .../params/tablecolumns.extension.xml              |    30 +
 .../params/target.database.document.xml            |    37 +
 .../docbook-xsl-1.75.1/params/targets.filename.xml |    32 +
 .../docbook/docbook-xsl-1.75.1/params/template.xml |    27 +
 .../docbook-xsl-1.75.1/params/tex.math.delims.xml  |    47 +
 .../docbook-xsl-1.75.1/params/tex.math.file.xml    |    42 +
 .../docbook-xsl-1.75.1/params/tex.math.in.alt.xml  |    83 +
 .../docbook-xsl-1.75.1/params/text.home.xml        |    27 +
 .../docbook-xsl-1.75.1/params/text.next.xml        |    27 +
 .../docbook-xsl-1.75.1/params/text.prev.xml        |    27 +
 .../docbook/docbook-xsl-1.75.1/params/text.toc.xml |    27 +
 .../docbook/docbook-xsl-1.75.1/params/text.up.xml  |    27 +
 .../docbook-xsl-1.75.1/params/textbgcolor.xml      |    26 +
 .../params/textdata.default.encoding.xml           |    32 +
 .../params/textinsert.extension.xml                |    62 +
 .../params/title.font.family.xml                   |    33 +
 .../params/title.margin.left.xml                   |    65 +
 .../docbook-xsl-1.75.1/params/titlefoil.html.xml   |    27 +
 .../docbook-xsl-1.75.1/params/toc.bg.color.xml     |    27 +
 .../params/toc.blank.graphic.xml                   |    28 +
 .../docbook-xsl-1.75.1/params/toc.blank.image.xml  |    27 +
 .../docbook-xsl-1.75.1/params/toc.blank.text.xml   |    27 +
 .../docbook-xsl-1.75.1/params/toc.hide.show.xml    |    33 +
 .../docbook/docbook-xsl-1.75.1/params/toc.html.xml |    27 +
 .../docbook-xsl-1.75.1/params/toc.image.xml        |    27 +
 .../docbook-xsl-1.75.1/params/toc.indent.width.xml |    34 +
 .../params/toc.line.properties.xml                 |    42 +
 .../docbook-xsl-1.75.1/params/toc.list.type.xml    |    30 +
 .../params/toc.margin.properties.xml               |    33 +
 .../docbook-xsl-1.75.1/params/toc.max.depth.xml    |    25 +
 .../params/toc.pointer.graphic.xml                 |    28 +
 .../params/toc.pointer.image.xml                   |    27 +
 .../docbook-xsl-1.75.1/params/toc.pointer.text.xml |    27 +
 .../docbook-xsl-1.75.1/params/toc.row.height.xml   |    33 +
 .../params/toc.section.depth.xml                   |    28 +
 .../params/toc.spacer.graphic.xml                  |    28 +
 .../docbook-xsl-1.75.1/params/toc.spacer.image.xml |    27 +
 .../docbook-xsl-1.75.1/params/toc.spacer.text.xml  |    27 +
 .../docbook-xsl-1.75.1/params/toc.width.xml        |    28 +
 .../docbook/docbook-xsl-1.75.1/params/ua.js.xml    |    28 +
 .../docbook-xsl-1.75.1/params/ulink.footnotes.xml  |    34 +
 .../params/ulink.hyphenate.chars.xml               |    37 +
 .../docbook-xsl-1.75.1/params/ulink.hyphenate.xml  |    35 +
 .../docbook-xsl-1.75.1/params/ulink.show.xml       |    37 +
 .../docbook-xsl-1.75.1/params/ulink.target.xml     |    29 +
 .../docbook/docbook-xsl-1.75.1/params/up.image.xml |    27 +
 .../params/use.embed.for.svg.xml                   |    33 +
 .../docbook-xsl-1.75.1/params/use.extensions.xml   |    31 +
 .../params/use.id.as.filename.xml                  |    30 +
 .../docbook-xsl-1.75.1/params/use.id.function.xml  |    32 +
 .../params/use.local.olink.style.xml               |    28 +
 .../params/use.role.as.xrefstyle.xml               |    93 +
 .../params/use.role.for.mediaobject.xml            |    56 +
 .../docbook/docbook-xsl-1.75.1/params/use.svg.xml  |    30 +
 .../params/variablelist.as.blocks.xml              |    62 +
 .../params/variablelist.as.table.xml               |    54 +
 .../params/variablelist.max.termlength.xml         |    46 +
 .../params/variablelist.term.break.after.xml       |    39 +
 .../params/variablelist.term.properties.xml        |    29 +
 .../params/variablelist.term.separator.xml         |    40 +
 .../params/verbatim.properties.xml                 |    38 +
 .../docbook-xsl-1.75.1/params/wordml.template.xml  |    29 +
 .../docbook-xsl-1.75.1/params/writing.mode.xml     |    83 +
 .../params/xbCollapsibleLists.js.xml               |    28 +
 .../docbook/docbook-xsl-1.75.1/params/xbDOM.js.xml |    28 +
 .../docbook-xsl-1.75.1/params/xbLibrary.js.xml     |    28 +
 .../docbook-xsl-1.75.1/params/xbStyle.js.xml       |    28 +
 .../docbook-xsl-1.75.1/params/xep.extensions.xml   |    31 +
 .../params/xep.index.item.properties.xml           |    36 +
 .../params/xref.label-page.separator.xml           |    38 +
 .../params/xref.label-title.separator.xml          |    36 +
 .../docbook-xsl-1.75.1/params/xref.properties.xml  |    29 +
 .../params/xref.title-page.separator.xml           |    36 +
 .../params/xref.with.number.and.title.xml          |    30 +
 .../docbook-xsl-1.75.1/profiling/profile-mode.xsl  |   239 +
 .../docbook-xsl-1.75.1/profiling/profile.xsl       |    49 +
 .../profiling/strip-attributes.xsl                 |    27 +
 .../docbook-xsl-1.75.1/profiling/xsl2profile.xsl   |   159 +
 .../docbook-xsl-1.75.1/template/titlepage.xml      |   478 +
 .../docbook-xsl-1.75.1/template/titlepage.xsl      |  1280 +
 .../docbook-xsl-1.75.1/tests/refentry.007.ns.xml   |   325 +
 .../docbook-xsl-1.75.1/tests/refentry.007.xml      |   340 +
 .../tools/bin/docbook-xsl-update                   |    53 +
 .../docbook-xsl-1.75.1/tools/make/Makefile.DocBook |   698 +
 .../docbook-xsl-1.75.1/tools/make/Makefile.combine |   182 +
 .../tools/make/Makefile.docParam                   |    59 +
 .../docbook-xsl-1.75.1/website/autolayout.xsl      |   258 +
 .../docbook-xsl-1.75.1/website/chunk-common.xsl    |   227 +
 .../docbook-xsl-1.75.1/website/chunk-tabular.xsl   |    12 +
 .../docbook-xsl-1.75.1/website/chunk-website.xsl   |    12 +
 .../docbook/docbook-xsl-1.75.1/website/head.xsl    |   316 +
 .../docbook-xsl-1.75.1/website/makefile-dep.xsl    |   143 +
 .../docbook/docbook-xsl-1.75.1/website/olink.xsl   |   297 +
 .../docbook/docbook-xsl-1.75.1/website/param.xml   |   788 +
 .../docbook/docbook-xsl-1.75.1/website/param.xsl   |    53 +
 .../docbook/docbook-xsl-1.75.1/website/rss.xsl     |   143 +
 .../docbook/docbook-xsl-1.75.1/website/tabular.xsl |   213 +
 .../docbook-xsl-1.75.1/website/toc-tabular.xsl     |   480 +
 .../docbook/docbook-xsl-1.75.1/website/toc.xsl     |   286 +
 .../docbook-xsl-1.75.1/website/website-common.xsl  |   821 +
 .../docbook-xsl-1.75.1/website/website-targets.xsl |    27 +
 .../docbook/docbook-xsl-1.75.1/website/website.xsl |   132 +
 .../docbook/docbook-xsl-1.75.1/website/xbel.xsl    |   114 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/admon.xsl |   133 +
 .../docbook-xsl-1.75.1/xhtml-1_1/annotations.xsl   |   158 +
 .../xhtml-1_1/autoidx-kimber.xsl                   |   139 +
 .../docbook-xsl-1.75.1/xhtml-1_1/autoidx-kosek.xsl |   109 +
 .../docbook-xsl-1.75.1/xhtml-1_1/autoidx-ng.xsl    |    21 +
 .../docbook-xsl-1.75.1/xhtml-1_1/autoidx.xsl       |   656 +
 .../docbook-xsl-1.75.1/xhtml-1_1/autotoc.xsl       |   632 +
 .../docbook-xsl-1.75.1/xhtml-1_1/biblio-iso690.xsl |  1300 +
 .../docbook-xsl-1.75.1/xhtml-1_1/biblio.xsl        |  1240 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/block.xsl |   435 +
 .../docbook-xsl-1.75.1/xhtml-1_1/callout.xsl       |   188 +
 .../docbook-xsl-1.75.1/xhtml-1_1/changebars.xsl    |    78 +
 .../xhtml-1_1/chunk-changebars.xsl                 |    96 +
 .../docbook-xsl-1.75.1/xhtml-1_1/chunk-code.xsl    |   638 +
 .../docbook-xsl-1.75.1/xhtml-1_1/chunk-common.xsl  |  1561 ++
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/chunk.xsl |    52 +
 .../docbook-xsl-1.75.1/xhtml-1_1/chunker.xsl       |   302 +
 .../docbook-xsl-1.75.1/xhtml-1_1/chunkfast.xsl     |    69 +
 .../docbook-xsl-1.75.1/xhtml-1_1/chunktoc.xsl      |   532 +
 .../docbook-xsl-1.75.1/xhtml-1_1/component.xsl     |   395 +
 .../docbook-xsl-1.75.1/xhtml-1_1/division.xsl      |   217 +
 .../docbook-xsl-1.75.1/xhtml-1_1/docbook.xsl       |   447 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/ebnf.xsl  |   328 +
 .../docbook-xsl-1.75.1/xhtml-1_1/footnote.xsl      |   302 +
 .../docbook-xsl-1.75.1/xhtml-1_1/formal.xsl        |   390 +
 .../docbook-xsl-1.75.1/xhtml-1_1/glossary.xsl      |   564 +
 .../docbook-xsl-1.75.1/xhtml-1_1/graphics.xsl      |  1436 +
 .../docbook-xsl-1.75.1/xhtml-1_1/highlight.xsl     |    72 +
 .../docbook-xsl-1.75.1/xhtml-1_1/html-rtf.xsl      |   321 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/html.xsl  |   364 +
 .../docbook-xsl-1.75.1/xhtml-1_1/htmltbl.xsl       |   102 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/index.xsl |   264 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/info.xsl  |    44 +
 .../docbook-xsl-1.75.1/xhtml-1_1/inline.xsl        |  1445 +
 .../docbook-xsl-1.75.1/xhtml-1_1/keywords.xsl      |    36 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/lists.xsl |  1088 +
 .../docbook-xsl-1.75.1/xhtml-1_1/maketoc.xsl       |    91 +
 .../docbook-xsl-1.75.1/xhtml-1_1/manifest.xsl      |    22 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/math.xsl  |   284 +
 .../docbook-xsl-1.75.1/xhtml-1_1/oldchunker.xsl    |   176 +
 .../docbook-xsl-1.75.1/xhtml-1_1/onechunk.xsl      |    36 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/param.xsl |   431 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/pi.xsl    |  1203 +
 .../xhtml-1_1/profile-chunk-code.xsl               |   610 +
 .../docbook-xsl-1.75.1/xhtml-1_1/profile-chunk.xsl |    52 +
 .../xhtml-1_1/profile-docbook.xsl                  |   409 +
 .../xhtml-1_1/profile-onechunk.xsl                 |    36 +
 .../docbook-xsl-1.75.1/xhtml-1_1/qandaset.xsl      |   420 +
 .../docbook-xsl-1.75.1/xhtml-1_1/refentry.xsl      |   299 +
 .../docbook-xsl-1.75.1/xhtml-1_1/sections.xsl      |   541 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/synop.xsl |  1513 ++
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/table.xsl |  1118 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/task.xsl  |    73 +
 .../xhtml-1_1/titlepage.templates.xsl              |  3710 +++
 .../docbook-xsl-1.75.1/xhtml-1_1/titlepage.xsl     |  1027 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/toc.xsl   |   330 +
 .../docbook-xsl-1.75.1/xhtml-1_1/verbatim.xsl      |   381 +
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/xref.xsl  |  1316 +
 .../docbook/docbook-xsl-1.75.1/xhtml/admon.xsl     |   135 +
 .../docbook-xsl-1.75.1/xhtml/annotations.xsl       |   158 +
 .../docbook-xsl-1.75.1/xhtml/autoidx-kimber.xsl    |   139 +
 .../docbook-xsl-1.75.1/xhtml/autoidx-kosek.xsl     |   109 +
 .../docbook-xsl-1.75.1/xhtml/autoidx-ng.xsl        |    21 +
 .../docbook/docbook-xsl-1.75.1/xhtml/autoidx.xsl   |   656 +
 .../docbook/docbook-xsl-1.75.1/xhtml/autotoc.xsl   |   632 +
 .../docbook-xsl-1.75.1/xhtml/biblio-iso690.xsl     |  1300 +
 .../docbook/docbook-xsl-1.75.1/xhtml/biblio.xsl    |  1240 +
 .../docbook/docbook-xsl-1.75.1/xhtml/block.xsl     |   435 +
 .../docbook/docbook-xsl-1.75.1/xhtml/callout.xsl   |   188 +
 .../docbook-xsl-1.75.1/xhtml/changebars.xsl        |    78 +
 .../docbook-xsl-1.75.1/xhtml/chunk-changebars.xsl  |    96 +
 .../docbook-xsl-1.75.1/xhtml/chunk-code.xsl        |   638 +
 .../docbook-xsl-1.75.1/xhtml/chunk-common.xsl      |  1561 ++
 .../docbook/docbook-xsl-1.75.1/xhtml/chunk.xsl     |    52 +
 .../docbook/docbook-xsl-1.75.1/xhtml/chunker.xsl   |   302 +
 .../docbook/docbook-xsl-1.75.1/xhtml/chunkfast.xsl |    69 +
 .../docbook/docbook-xsl-1.75.1/xhtml/chunktoc.xsl  |   532 +
 .../docbook/docbook-xsl-1.75.1/xhtml/component.xsl |   395 +
 .../docbook/docbook-xsl-1.75.1/xhtml/division.xsl  |   217 +
 .../docbook/docbook-xsl-1.75.1/xhtml/docbook.xsl   |   447 +
 .../docbook/docbook-xsl-1.75.1/xhtml/docgen.xsl    |    22 +
 .../docbook/docbook-xsl-1.75.1/xhtml/ebnf.xsl      |   328 +
 .../docbook/docbook-xsl-1.75.1/xhtml/footnote.xsl  |   302 +
 .../docbook/docbook-xsl-1.75.1/xhtml/formal.xsl    |   390 +
 .../docbook/docbook-xsl-1.75.1/xhtml/glossary.xsl  |   564 +
 .../docbook/docbook-xsl-1.75.1/xhtml/graphics.xsl  |  1436 +
 .../docbook/docbook-xsl-1.75.1/xhtml/highlight.xsl |    72 +
 .../docbook/docbook-xsl-1.75.1/xhtml/html-rtf.xsl  |   321 +
 .../docbook/docbook-xsl-1.75.1/xhtml/html.xsl      |   364 +
 .../docbook/docbook-xsl-1.75.1/xhtml/htmltbl.xsl   |   102 +
 .../docbook/docbook-xsl-1.75.1/xhtml/index.xsl     |   264 +
 .../docbook/docbook-xsl-1.75.1/xhtml/info.xsl      |    44 +
 .../docbook/docbook-xsl-1.75.1/xhtml/inline.xsl    |  1445 +
 .../docbook/docbook-xsl-1.75.1/xhtml/keywords.xsl  |    36 +
 .../docbook/docbook-xsl-1.75.1/xhtml/lists.xsl     |  1121 +
 .../docbook/docbook-xsl-1.75.1/xhtml/maketoc.xsl   |    91 +
 .../docbook/docbook-xsl-1.75.1/xhtml/manifest.xsl  |    22 +
 .../docbook/docbook-xsl-1.75.1/xhtml/math.xsl      |   284 +
 .../docbook-xsl-1.75.1/xhtml/oldchunker.xsl        |   176 +
 .../docbook/docbook-xsl-1.75.1/xhtml/onechunk.xsl  |    36 +
 .../docbook/docbook-xsl-1.75.1/xhtml/param.xsl     |   431 +
 .../docgen/docbook/docbook-xsl-1.75.1/xhtml/pi.xsl |  1203 +
 .../xhtml/profile-chunk-code.xsl                   |   610 +
 .../docbook-xsl-1.75.1/xhtml/profile-chunk.xsl     |    52 +
 .../docbook-xsl-1.75.1/xhtml/profile-docbook.xsl   |   409 +
 .../docbook-xsl-1.75.1/xhtml/profile-onechunk.xsl  |    36 +
 .../docbook/docbook-xsl-1.75.1/xhtml/qandaset.xsl  |   420 +
 .../docbook/docbook-xsl-1.75.1/xhtml/refentry.xsl  |   299 +
 .../docbook/docbook-xsl-1.75.1/xhtml/sections.xsl  |   541 +
 .../docbook/docbook-xsl-1.75.1/xhtml/synop.xsl     |  1513 ++
 .../docbook/docbook-xsl-1.75.1/xhtml/table.xsl     |  1118 +
 .../docbook/docbook-xsl-1.75.1/xhtml/task.xsl      |    73 +
 .../xhtml/titlepage.templates.xsl                  |  3710 +++
 .../docbook/docbook-xsl-1.75.1/xhtml/titlepage.xsl |  1027 +
 .../docbook/docbook-xsl-1.75.1/xhtml/toc.xsl       |   330 +
 .../docbook/docbook-xsl-1.75.1/xhtml/verbatim.xsl  |   381 +
 .../docbook/docbook-xsl-1.75.1/xhtml/xref.xsl      |  1316 +
 .../common/tools/docgen/docgen.xsl                 |    22 +
 .../common/tools/docgen/techpub.css                |   245 +
 features/org.xtuml.bp.docgen/feature.xml           |    19 +
 .../linux.all/tools/docgen/docbook/xsltproc        |   Bin 0 -> 24960 bytes
 features/org.xtuml.bp.docgen/pom.xml               |    15 +
 .../win32.all/tools/docgen/docbook/iconv.dll       |   Bin 0 -> 888832 bytes
 .../win32.all/tools/docgen/docbook/libexslt.dll    |   Bin 0 -> 53760 bytes
 .../win32.all/tools/docgen/docbook/libxml2.dll     |   Bin 0 -> 976384 bytes
 .../win32.all/tools/docgen/docbook/libxslt.dll     |   Bin 0 -> 166400 bytes
 .../win32.all/tools/docgen/docbook/xsltproc.exe    |   Bin 0 -> 14336 bytes
 .../win32.all/tools/docgen/docbook/zlib1.dll       |   Bin 0 -> 73728 bytes
 .../win32.all/tools/docgen/template_engine.exe     |   Bin 0 -> 58216 bytes
 features/org.xtuml.bp.jre.parent/.project          |    11 +
 features/org.xtuml.bp.jre.parent/pom.xml           |    61 +
 features/org.xtuml.bp.jre/.project                 |    17 +
 features/org.xtuml.bp.jre/build.properties         |     7 +
 features/org.xtuml.bp.jre/feature.xml              |    19 +
 features/org.xtuml.bp.jre/linux.x64/jre/COPYRIGHT  |    69 +
 features/org.xtuml.bp.jre/linux.x64/jre/LICENSE    |     1 +
 features/org.xtuml.bp.jre/linux.x64/jre/README     |     1 +
 .../jre/THIRDPARTYLICENSEREADME-JAVAFX.txt         |  1531 ++
 .../linux.x64/jre/THIRDPARTYLICENSEREADME.txt      |  3574 +++
 .../org.xtuml.bp.jre/linux.x64/jre/Welcome.html    |    28 +
 .../linux.x64/jre/bin/ControlPanel                 |     1 +
 features/org.xtuml.bp.jre/linux.x64/jre/bin/java   |   Bin 0 -> 7734 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/bin/javaws |   Bin 0 -> 128695 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/jcontrol    |   245 +
 features/org.xtuml.bp.jre/linux.x64/jre/bin/jjs    |   Bin 0 -> 7941 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/keytool     |   Bin 0 -> 7941 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/bin/orbd   |   Bin 0 -> 8149 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/pack200     |   Bin 0 -> 7957 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/policytool  |   Bin 0 -> 7997 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/bin/rmid   |   Bin 0 -> 7941 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/rmiregistry |   Bin 0 -> 7949 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/servertool  |   Bin 0 -> 7965 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/tnameserv   |   Bin 0 -> 8181 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/bin/unpack200   |   Bin 0 -> 228485 bytes
 .../linux.x64/jre/lib/amd64/jli/libjli.so          |   Bin 0 -> 102352 bytes
 .../linux.x64/jre/lib/amd64/jvm.cfg                |    35 +
 .../linux.x64/jre/lib/amd64/libavplugin-53.so      |   Bin 0 -> 49474 bytes
 .../linux.x64/jre/lib/amd64/libavplugin-54.so      |   Bin 0 -> 50101 bytes
 .../linux.x64/jre/lib/amd64/libawt.so              |   Bin 0 -> 772804 bytes
 .../linux.x64/jre/lib/amd64/libawt_headless.so     |   Bin 0 -> 37420 bytes
 .../linux.x64/jre/lib/amd64/libawt_xawt.so         |   Bin 0 -> 442404 bytes
 .../linux.x64/jre/lib/amd64/libbci.so              |   Bin 0 -> 9594 bytes
 .../linux.x64/jre/lib/amd64/libdcpr.so             |   Bin 0 -> 207604 bytes
 .../linux.x64/jre/lib/amd64/libdecora_sse.so       |   Bin 0 -> 73636 bytes
 .../linux.x64/jre/lib/amd64/libdeploy.so           |   Bin 0 -> 161767 bytes
 .../linux.x64/jre/lib/amd64/libdt_socket.so        |   Bin 0 -> 22374 bytes
 .../linux.x64/jre/lib/amd64/libfontmanager.so      |   Bin 0 -> 525411 bytes
 .../linux.x64/jre/lib/amd64/libfxplugins.so        |   Bin 0 -> 151083 bytes
 .../linux.x64/jre/lib/amd64/libglass.so            |   Bin 0 -> 226196 bytes
 .../linux.x64/jre/lib/amd64/libgstreamer-lite.so   |   Bin 0 -> 1774212 bytes
 .../linux.x64/jre/lib/amd64/libhprof.so            |   Bin 0 -> 177596 bytes
 .../linux.x64/jre/lib/amd64/libinstrument.so       |   Bin 0 -> 49341 bytes
 .../linux.x64/jre/lib/amd64/libj2gss.so            |   Bin 0 -> 47509 bytes
 .../linux.x64/jre/lib/amd64/libj2pcsc.so           |   Bin 0 -> 15884 bytes
 .../linux.x64/jre/lib/amd64/libj2pkcs11.so         |   Bin 0 -> 78544 bytes
 .../linux.x64/jre/lib/amd64/libjaas_unix.so        |   Bin 0 -> 7652 bytes
 .../linux.x64/jre/lib/amd64/libjava.so             |   Bin 0 -> 225499 bytes
 .../linux.x64/jre/lib/amd64/libjava_crw_demo.so    |   Bin 0 -> 25734 bytes
 .../linux.x64/jre/lib/amd64/libjavafx_font.so      |   Bin 0 -> 16935 bytes
 .../jre/lib/amd64/libjavafx_font_freetype.so       |   Bin 0 -> 25939 bytes
 .../jre/lib/amd64/libjavafx_font_pango.so          |   Bin 0 -> 24452 bytes
 .../linux.x64/jre/lib/amd64/libjavafx_font_t2k.so  |   Bin 0 -> 890999 bytes
 .../linux.x64/jre/lib/amd64/libjavafx_iio.so       |   Bin 0 -> 242063 bytes
 .../linux.x64/jre/lib/amd64/libjawt.so             |   Bin 0 -> 7013 bytes
 .../linux.x64/jre/lib/amd64/libjdwp.so             |   Bin 0 -> 270293 bytes
 .../linux.x64/jre/lib/amd64/libjfr.so              |   Bin 0 -> 25913 bytes
 .../linux.x64/jre/lib/amd64/libjfxmedia.so         |   Bin 0 -> 229428 bytes
 .../linux.x64/jre/lib/amd64/libjfxwebkit.so        |   Bin 0 -> 57450616 bytes
 .../linux.x64/jre/lib/amd64/libjpeg.so             |   Bin 0 -> 263985 bytes
 .../linux.x64/jre/lib/amd64/libjsdt.so             |   Bin 0 -> 11678 bytes
 .../linux.x64/jre/lib/amd64/libjsig.so             |   Bin 0 -> 11052 bytes
 .../linux.x64/jre/lib/amd64/libjsound.so           |   Bin 0 -> 7350 bytes
 .../linux.x64/jre/lib/amd64/libjsoundalsa.so       |   Bin 0 -> 81242 bytes
 .../linux.x64/jre/lib/amd64/libkcms.so             |   Bin 0 -> 401494 bytes
 .../linux.x64/jre/lib/amd64/liblcms.so             |   Bin 0 -> 413979 bytes
 .../linux.x64/jre/lib/amd64/libmanagement.so       |   Bin 0 -> 50289 bytes
 .../linux.x64/jre/lib/amd64/libmlib_image.so       |   Bin 0 -> 912788 bytes
 .../linux.x64/jre/lib/amd64/libnet.so              |   Bin 0 -> 115857 bytes
 .../linux.x64/jre/lib/amd64/libnio.so              |   Bin 0 -> 93112 bytes
 .../linux.x64/jre/lib/amd64/libnpjp2.so            |   Bin 0 -> 221525 bytes
 .../linux.x64/jre/lib/amd64/libnpt.so              |   Bin 0 -> 14342 bytes
 .../linux.x64/jre/lib/amd64/libprism_common.so     |   Bin 0 -> 56933 bytes
 .../linux.x64/jre/lib/amd64/libprism_es2.so        |   Bin 0 -> 62579 bytes
 .../linux.x64/jre/lib/amd64/libprism_sw.so         |   Bin 0 -> 73014 bytes
 .../linux.x64/jre/lib/amd64/libresource.so         |   Bin 0 -> 10473 bytes
 .../linux.x64/jre/lib/amd64/libsctp.so             |   Bin 0 -> 28487 bytes
 .../linux.x64/jre/lib/amd64/libsplashscreen.so     |   Bin 0 -> 451129 bytes
 .../linux.x64/jre/lib/amd64/libsunec.so            |   Bin 0 -> 256702 bytes
 .../linux.x64/jre/lib/amd64/libt2k.so              |   Bin 0 -> 492629 bytes
 .../linux.x64/jre/lib/amd64/libunpack.so           |   Bin 0 -> 164183 bytes
 .../linux.x64/jre/lib/amd64/libverify.so           |   Bin 0 -> 66472 bytes
 .../linux.x64/jre/lib/amd64/libzip.so              |   Bin 0 -> 124327 bytes
 .../linux.x64/jre/lib/amd64/server/Xusage.txt      |    24 +
 .../linux.x64/jre/lib/amd64/server/libjsig.so      |     1 +
 .../linux.x64/jre/lib/amd64/server/libjvm.so       |   Bin 0 -> 16925812 bytes
 .../linux.x64/jre/lib/calendars.properties         |    60 +
 .../linux.x64/jre/lib/charsets.jar                 |   Bin 0 -> 3133473 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/classlist   |  2559 ++
 .../linux.x64/jre/lib/cmm/CIEXYZ.pf                |   Bin 0 -> 51236 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/cmm/GRAY.pf |   Bin 0 -> 632 bytes
 .../linux.x64/jre/lib/cmm/LINEAR_RGB.pf            |   Bin 0 -> 1044 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/cmm/PYCC.pf |   Bin 0 -> 274474 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/cmm/sRGB.pf |   Bin 0 -> 3144 bytes
 .../linux.x64/jre/lib/content-types.properties     |   280 +
 .../linux.x64/jre/lib/currency.data                |   Bin 0 -> 4122 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/deploy.jar  |   Bin 0 -> 2239472 bytes
 .../jre/lib/deploy/MixedCodeMainDialog.ui          |   154 +
 .../jre/lib/deploy/MixedCodeMainDialogJs.ui        |   210 +
 .../linux.x64/jre/lib/deploy/cautionshield.icns    |   Bin 0 -> 3588 bytes
 .../linux.x64/jre/lib/deploy/ffjcext.zip           |   Bin 0 -> 13428 bytes
 .../linux.x64/jre/lib/deploy/java-icon.ico         |   Bin 0 -> 29926 bytes
 .../linux.x64/jre/lib/deploy/messages.properties   |    57 +
 .../jre/lib/deploy/messages_de.properties          |    32 +
 .../jre/lib/deploy/messages_es.properties          |    32 +
 .../jre/lib/deploy/messages_fr.properties          |    32 +
 .../jre/lib/deploy/messages_it.properties          |    32 +
 .../jre/lib/deploy/messages_ja.properties          |    32 +
 .../jre/lib/deploy/messages_ko.properties          |    32 +
 .../jre/lib/deploy/messages_pt_BR.properties       |    32 +
 .../jre/lib/deploy/messages_sv.properties          |    32 +
 .../jre/lib/deploy/messages_zh_CN.properties       |    32 +
 .../jre/lib/deploy/messages_zh_HK.properties       |    32 +
 .../jre/lib/deploy/messages_zh_TW.properties       |    32 +
 .../linux.x64/jre/lib/deploy/mixcode_s.png         |   Bin 0 -> 3115 bytes
 .../linux.x64/jre/lib/deploy/splash.gif            |   Bin 0 -> 8590 bytes
 .../linux.x64/jre/lib/deploy/splash@2x.gif         |   Bin 0 -> 15276 bytes
 .../linux.x64/jre/lib/deploy/splash_11-lic.gif     |   Bin 0 -> 7805 bytes
 .../linux.x64/jre/lib/deploy/splash_11@2x-lic.gif  |   Bin 0 -> 12250 bytes
 .../jre/lib/desktop/applications/sun-java.desktop  |    25 +
 .../lib/desktop/applications/sun-javaws.desktop    |    22 +
 .../jre/lib/desktop/applications/sun_java.desktop  |    20 +
 .../icons/HighContrast/16x16/apps/sun-java.png     |   Bin 0 -> 417 bytes
 .../icons/HighContrast/16x16/apps/sun-javaws.png   |   Bin 0 -> 417 bytes
 .../icons/HighContrast/16x16/apps/sun-jcontrol.png |   Bin 0 -> 417 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 464 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 464 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 464 bytes
 .../icons/HighContrast/48x48/apps/sun-java.png     |   Bin 0 -> 3451 bytes
 .../icons/HighContrast/48x48/apps/sun-javaws.png   |   Bin 0 -> 3451 bytes
 .../icons/HighContrast/48x48/apps/sun-jcontrol.png |   Bin 0 -> 3451 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 2088 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 2088 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 2088 bytes
 .../HighContrastInverse/16x16/apps/sun-java.png    |   Bin 0 -> 402 bytes
 .../HighContrastInverse/16x16/apps/sun-javaws.png  |   Bin 0 -> 402 bytes
 .../16x16/apps/sun-jcontrol.png                    |   Bin 0 -> 402 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 473 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 473 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 473 bytes
 .../HighContrastInverse/48x48/apps/sun-java.png    |   Bin 0 -> 3410 bytes
 .../HighContrastInverse/48x48/apps/sun-javaws.png  |   Bin 0 -> 3410 bytes
 .../48x48/apps/sun-jcontrol.png                    |   Bin 0 -> 3410 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 2085 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 2085 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 2085 bytes
 .../icons/LowContrast/16x16/apps/sun-java.png      |   Bin 0 -> 519 bytes
 .../icons/LowContrast/16x16/apps/sun-javaws.png    |   Bin 0 -> 519 bytes
 .../icons/LowContrast/16x16/apps/sun-jcontrol.png  |   Bin 0 -> 519 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 525 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 525 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 525 bytes
 .../icons/LowContrast/48x48/apps/sun-java.png      |   Bin 0 -> 1507 bytes
 .../icons/LowContrast/48x48/apps/sun-javaws.png    |   Bin 0 -> 1507 bytes
 .../icons/LowContrast/48x48/apps/sun-jcontrol.png  |   Bin 0 -> 1507 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 1948 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 1948 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 1948 bytes
 .../desktop/icons/hicolor/16x16/apps/sun-java.png  |   Bin 0 -> 383 bytes
 .../icons/hicolor/16x16/apps/sun-javaws.png        |   Bin 0 -> 383 bytes
 .../icons/hicolor/16x16/apps/sun-jcontrol.png      |   Bin 0 -> 383 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 783 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 783 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 783 bytes
 .../desktop/icons/hicolor/48x48/apps/sun-java.png  |   Bin 0 -> 1439 bytes
 .../icons/hicolor/48x48/apps/sun-javaws.png        |   Bin 0 -> 1439 bytes
 .../icons/hicolor/48x48/apps/sun-jcontrol.png      |   Bin 0 -> 1439 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 3202 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 3202 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 3202 bytes
 .../lib/desktop/mime/packages/x-java-archive.xml   |    39 +
 .../lib/desktop/mime/packages/x-java-jnlp-file.xml |    11 +
 .../linux.x64/jre/lib/ext/cldrdata.jar             |   Bin 0 -> 3860522 bytes
 .../linux.x64/jre/lib/ext/dnsns.jar                |   Bin 0 -> 8286 bytes
 .../linux.x64/jre/lib/ext/jaccess.jar              |   Bin 0 -> 44516 bytes
 .../linux.x64/jre/lib/ext/jfxrt.jar                |   Bin 0 -> 18446960 bytes
 .../linux.x64/jre/lib/ext/localedata.jar           |   Bin 0 -> 1178848 bytes
 .../linux.x64/jre/lib/ext/meta-index               |    63 +
 .../linux.x64/jre/lib/ext/nashorn.jar              |   Bin 0 -> 2028225 bytes
 .../linux.x64/jre/lib/ext/sunec.jar                |   Bin 0 -> 39771 bytes
 .../linux.x64/jre/lib/ext/sunjce_provider.jar      |   Bin 0 -> 278718 bytes
 .../linux.x64/jre/lib/ext/sunpkcs11.jar            |   Bin 0 -> 250826 bytes
 .../linux.x64/jre/lib/ext/zipfs.jar                |   Bin 0 -> 68849 bytes
 .../linux.x64/jre/lib/flavormap.properties         |    78 +
 .../linux.x64/jre/lib/fontconfig.RedHat.5.bfc      |   Bin 0 -> 4532 bytes
 .../jre/lib/fontconfig.RedHat.5.properties.src     |   134 +
 .../linux.x64/jre/lib/fontconfig.RedHat.6.bfc      |   Bin 0 -> 4250 bytes
 .../jre/lib/fontconfig.RedHat.6.properties.src     |   134 +
 .../linux.x64/jre/lib/fontconfig.SuSE.10.bfc       |   Bin 0 -> 6702 bytes
 .../jre/lib/fontconfig.SuSE.10.properties.src      |   218 +
 .../linux.x64/jre/lib/fontconfig.SuSE.11.bfc       |   Bin 0 -> 7032 bytes
 .../jre/lib/fontconfig.SuSE.11.properties.src      |   227 +
 .../linux.x64/jre/lib/fontconfig.Turbo.bfc         |   Bin 0 -> 4668 bytes
 .../jre/lib/fontconfig.Turbo.properties.src        |   144 +
 .../linux.x64/jre/lib/fontconfig.bfc               |   Bin 0 -> 1678 bytes
 .../linux.x64/jre/lib/fontconfig.properties.src    |    46 +
 .../jre/lib/fonts/LucidaBrightDemiBold.ttf         |   Bin 0 -> 75144 bytes
 .../jre/lib/fonts/LucidaBrightDemiItalic.ttf       |   Bin 0 -> 75124 bytes
 .../linux.x64/jre/lib/fonts/LucidaBrightItalic.ttf |   Bin 0 -> 80856 bytes
 .../jre/lib/fonts/LucidaBrightRegular.ttf          |   Bin 0 -> 344908 bytes
 .../linux.x64/jre/lib/fonts/LucidaSansDemiBold.ttf |   Bin 0 -> 317896 bytes
 .../linux.x64/jre/lib/fonts/LucidaSansRegular.ttf  |   Bin 0 -> 698236 bytes
 .../jre/lib/fonts/LucidaTypewriterBold.ttf         |   Bin 0 -> 234068 bytes
 .../jre/lib/fonts/LucidaTypewriterRegular.ttf      |   Bin 0 -> 242700 bytes
 .../linux.x64/jre/lib/fonts/fonts.dir              |    49 +
 .../jre/lib/hijrah-config-umalqura.properties      |   369 +
 .../jre/lib/images/cursors/cursors.properties      |    40 +
 .../jre/lib/images/cursors/invalid32x32.gif        |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/motif_CopyDrop32x32.gif |   Bin 0 -> 158 bytes
 .../lib/images/cursors/motif_CopyNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/motif_LinkDrop32x32.gif |   Bin 0 -> 162 bytes
 .../lib/images/cursors/motif_LinkNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/motif_MoveDrop32x32.gif |   Bin 0 -> 141 bytes
 .../lib/images/cursors/motif_MoveNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../linux.x64/jre/lib/images/icons/sun-java.png    |   Bin 0 -> 4707 bytes
 .../jre/lib/images/icons/sun-java_HighContrast.png |   Bin 0 -> 3729 bytes
 .../images/icons/sun-java_HighContrastInverse.png  |   Bin 0 -> 3777 bytes
 .../jre/lib/images/icons/sun-java_LowContrast.png  |   Bin 0 -> 4012 bytes
 .../linux.x64/jre/lib/javafx.properties            |     2 +
 .../org.xtuml.bp.jre/linux.x64/jre/lib/javaws.jar  |   Bin 0 -> 480597 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/jce.jar     |   Bin 0 -> 114950 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/lib/jexec  |   Bin 0 -> 10584 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/jfr.jar     |   Bin 0 -> 560452 bytes
 .../linux.x64/jre/lib/jfr/default.jfc              |   561 +
 .../linux.x64/jre/lib/jfr/profile.jfc              |   561 +
 .../org.xtuml.bp.jre/linux.x64/jre/lib/jfxswt.jar  |   Bin 0 -> 33930 bytes
 .../org.xtuml.bp.jre/linux.x64/jre/lib/jsse.jar    |   Bin 0 -> 633834 bytes
 .../linux.x64/jre/lib/jvm.hprof.txt                |    86 +
 .../lib/locale/de/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2483 bytes
 .../lib/locale/es/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2477 bytes
 .../lib/locale/fr/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2746 bytes
 .../lib/locale/it/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2434 bytes
 .../lib/locale/ja/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2415 bytes
 .../ko.UTF-8/LC_MESSAGES/sunw_java_plugin.mo       |   Bin 0 -> 2753 bytes
 .../lib/locale/ko/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2399 bytes
 .../locale/pt_BR/LC_MESSAGES/sunw_java_plugin.mo   |   Bin 0 -> 2420 bytes
 .../lib/locale/sv/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2359 bytes
 .../locale/zh.GBK/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2002 bytes
 .../lib/locale/zh/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2002 bytes
 .../zh_HK.BIG5HK/LC_MESSAGES/sunw_java_plugin.mo   |   Bin 0 -> 2025 bytes
 .../zh_TW.BIG5/LC_MESSAGES/sunw_java_plugin.mo     |   Bin 0 -> 2025 bytes
 .../locale/zh_TW/LC_MESSAGES/sunw_java_plugin.mo   |   Bin 0 -> 2025 bytes
 .../linux.x64/jre/lib/logging.properties           |    59 +
 .../linux.x64/jre/lib/management-agent.jar         |   Bin 0 -> 381 bytes
 .../linux.x64/jre/lib/management/jmxremote.access  |    79 +
 .../jre/lib/management/jmxremote.password.template |    64 +
 .../jre/lib/management/management.properties       |   318 +
 .../linux.x64/jre/lib/management/snmp.acl.template |   110 +
 .../org.xtuml.bp.jre/linux.x64/jre/lib/meta-index  |    92 +
 .../linux.x64/jre/lib/net.properties               |    74 +
 .../lib/oblique-fonts/LucidaSansDemiOblique.ttf    |   Bin 0 -> 91352 bytes
 .../jre/lib/oblique-fonts/LucidaSansOblique.ttf    |   Bin 0 -> 253724 bytes
 .../oblique-fonts/LucidaTypewriterBoldOblique.ttf  |   Bin 0 -> 63168 bytes
 .../lib/oblique-fonts/LucidaTypewriterOblique.ttf  |   Bin 0 -> 137484 bytes
 .../linux.x64/jre/lib/oblique-fonts/fonts.dir      |    25 +
 .../org.xtuml.bp.jre/linux.x64/jre/lib/plugin.jar  |   Bin 0 -> 951520 bytes
 .../linux.x64/jre/lib/psfont.properties.ja         |   119 +
 .../linux.x64/jre/lib/psfontj2d.properties         |   323 +
 .../linux.x64/jre/lib/resources.jar                |   Bin 0 -> 3501646 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/lib/rt.jar |   Bin 0 -> 66024286 bytes
 .../jre/lib/security/US_export_policy.jar          |   Bin 0 -> 3026 bytes
 .../linux.x64/jre/lib/security/blacklist           |    95 +
 .../linux.x64/jre/lib/security/blacklisted.certs   |    20 +
 .../linux.x64/jre/lib/security/cacerts             |   Bin 0 -> 108873 bytes
 .../linux.x64/jre/lib/security/java.policy         |    49 +
 .../linux.x64/jre/lib/security/java.security       |   648 +
 .../linux.x64/jre/lib/security/javaws.policy       |     5 +
 .../linux.x64/jre/lib/security/local_policy.jar    |   Bin 0 -> 3527 bytes
 .../linux.x64/jre/lib/security/trusted.libraries   |     0
 .../linux.x64/jre/lib/sound.properties             |    39 +
 .../org.xtuml.bp.jre/linux.x64/jre/lib/tzdb.dat    |   Bin 0 -> 101908 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/man/ja     |     1 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/java.1      |  3117 +++
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/javaws.1    |   215 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/jjs.1       |   415 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/keytool.1   |  2229 ++
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/orbd.1      |   266 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/pack200.1   |   354 +
 .../jre/man/ja_JP.UTF-8/man1/policytool.1          |   158 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/rmid.1      |   354 +
 .../jre/man/ja_JP.UTF-8/man1/rmiregistry.1         |   116 +
 .../jre/man/ja_JP.UTF-8/man1/servertool.1          |   182 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/tnameserv.1 |   427 +
 .../linux.x64/jre/man/ja_JP.UTF-8/man1/unpack200.1 |   186 +
 .../org.xtuml.bp.jre/linux.x64/jre/man/man1/java.1 |  4418 +++
 .../linux.x64/jre/man/man1/javaws.1                |   157 +
 .../org.xtuml.bp.jre/linux.x64/jre/man/man1/jjs.1  |   228 +
 .../linux.x64/jre/man/man1/keytool.1               |  1599 ++
 .../org.xtuml.bp.jre/linux.x64/jre/man/man1/orbd.1 |   193 +
 .../linux.x64/jre/man/man1/pack200.1               |   270 +
 .../linux.x64/jre/man/man1/policytool.1            |    94 +
 .../org.xtuml.bp.jre/linux.x64/jre/man/man1/rmid.1 |   294 +
 .../linux.x64/jre/man/man1/rmiregistry.1           |    78 +
 .../linux.x64/jre/man/man1/servertool.1            |   117 +
 .../linux.x64/jre/man/man1/tnameserv.1             |   468 +
 .../linux.x64/jre/man/man1/unpack200.1             |   117 +
 .../linux.x64/jre/plugin/desktop/sun_java.desktop  |    20 +
 .../linux.x64/jre/plugin/desktop/sun_java.png      |   Bin 0 -> 4351 bytes
 features/org.xtuml.bp.jre/linux.x64/jre/release    |     6 +
 features/org.xtuml.bp.jre/linux.x86/jre/COPYRIGHT  |    69 +
 features/org.xtuml.bp.jre/linux.x86/jre/LICENSE    |     1 +
 features/org.xtuml.bp.jre/linux.x86/jre/README     |     1 +
 .../jre/THIRDPARTYLICENSEREADME-JAVAFX.txt         |  1531 ++
 .../linux.x86/jre/THIRDPARTYLICENSEREADME.txt      |  3605 +++
 .../org.xtuml.bp.jre/linux.x86/jre/Welcome.html    |    28 +
 .../linux.x86/jre/bin/ControlPanel                 |   245 +
 features/org.xtuml.bp.jre/linux.x86/jre/bin/java   |   Bin 0 -> 5730 bytes
 features/org.xtuml.bp.jre/linux.x86/jre/bin/javaws |   Bin 0 -> 113250 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/jcontrol    |   245 +
 features/org.xtuml.bp.jre/linux.x86/jre/bin/jjs    |   Bin 0 -> 5881 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/keytool     |   Bin 0 -> 5893 bytes
 features/org.xtuml.bp.jre/linux.x86/jre/bin/orbd   |   Bin 0 -> 6029 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/pack200     |   Bin 0 -> 5897 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/policytool  |   Bin 0 -> 5997 bytes
 features/org.xtuml.bp.jre/linux.x86/jre/bin/rmid   |   Bin 0 -> 5881 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/rmiregistry |   Bin 0 -> 5893 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/servertool  |   Bin 0 -> 5909 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/tnameserv   |   Bin 0 -> 6061 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/bin/unpack200   |   Bin 0 -> 230044 bytes
 .../linux.x86/jre/lib/calendars.properties         |    60 +
 .../linux.x86/jre/lib/charsets.jar                 |   Bin 0 -> 3131363 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/classlist   |  2454 ++
 .../linux.x86/jre/lib/cmm/CIEXYZ.pf                |   Bin 0 -> 51236 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/cmm/GRAY.pf |   Bin 0 -> 632 bytes
 .../linux.x86/jre/lib/cmm/LINEAR_RGB.pf            |   Bin 0 -> 1044 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/cmm/PYCC.pf |   Bin 0 -> 274474 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/cmm/sRGB.pf |   Bin 0 -> 3144 bytes
 .../linux.x86/jre/lib/content-types.properties     |   280 +
 .../linux.x86/jre/lib/currency.data                |   Bin 0 -> 4074 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/deploy.jar  |   Bin 0 -> 2164929 bytes
 .../jre/lib/deploy/MixedCodeMainDialog.ui          |   154 +
 .../jre/lib/deploy/MixedCodeMainDialogJs.ui        |   210 +
 .../linux.x86/jre/lib/deploy/cautionshield.icns    |   Bin 0 -> 3588 bytes
 .../linux.x86/jre/lib/deploy/ffjcext.zip           |   Bin 0 -> 13428 bytes
 .../linux.x86/jre/lib/deploy/java-icon.ico         |   Bin 0 -> 29926 bytes
 .../linux.x86/jre/lib/deploy/messages.properties   |    57 +
 .../jre/lib/deploy/messages_de.properties          |    32 +
 .../jre/lib/deploy/messages_es.properties          |    32 +
 .../jre/lib/deploy/messages_fr.properties          |    32 +
 .../jre/lib/deploy/messages_it.properties          |    32 +
 .../jre/lib/deploy/messages_ja.properties          |    32 +
 .../jre/lib/deploy/messages_ko.properties          |    32 +
 .../jre/lib/deploy/messages_pt_BR.properties       |    32 +
 .../jre/lib/deploy/messages_sv.properties          |    32 +
 .../jre/lib/deploy/messages_zh_CN.properties       |    32 +
 .../jre/lib/deploy/messages_zh_HK.properties       |    32 +
 .../jre/lib/deploy/messages_zh_TW.properties       |    32 +
 .../linux.x86/jre/lib/deploy/mixcode_s.png         |   Bin 0 -> 3115 bytes
 .../linux.x86/jre/lib/deploy/splash.gif            |   Bin 0 -> 8590 bytes
 .../linux.x86/jre/lib/deploy/splash@2x.gif         |   Bin 0 -> 15276 bytes
 .../jre/lib/desktop/applications/sun-java.desktop  |    25 +
 .../lib/desktop/applications/sun-javaws.desktop    |    22 +
 .../jre/lib/desktop/applications/sun_java.desktop  |    20 +
 .../icons/HighContrast/16x16/apps/sun-java.png     |   Bin 0 -> 417 bytes
 .../icons/HighContrast/16x16/apps/sun-javaws.png   |   Bin 0 -> 417 bytes
 .../icons/HighContrast/16x16/apps/sun-jcontrol.png |   Bin 0 -> 417 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 464 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 464 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 464 bytes
 .../icons/HighContrast/48x48/apps/sun-java.png     |   Bin 0 -> 3451 bytes
 .../icons/HighContrast/48x48/apps/sun-javaws.png   |   Bin 0 -> 3451 bytes
 .../icons/HighContrast/48x48/apps/sun-jcontrol.png |   Bin 0 -> 3451 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 2088 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 2088 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 2088 bytes
 .../HighContrastInverse/16x16/apps/sun-java.png    |   Bin 0 -> 402 bytes
 .../HighContrastInverse/16x16/apps/sun-javaws.png  |   Bin 0 -> 402 bytes
 .../16x16/apps/sun-jcontrol.png                    |   Bin 0 -> 402 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 473 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 473 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 473 bytes
 .../HighContrastInverse/48x48/apps/sun-java.png    |   Bin 0 -> 3410 bytes
 .../HighContrastInverse/48x48/apps/sun-javaws.png  |   Bin 0 -> 3410 bytes
 .../48x48/apps/sun-jcontrol.png                    |   Bin 0 -> 3410 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 2085 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 2085 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 2085 bytes
 .../icons/LowContrast/16x16/apps/sun-java.png      |   Bin 0 -> 519 bytes
 .../icons/LowContrast/16x16/apps/sun-javaws.png    |   Bin 0 -> 519 bytes
 .../icons/LowContrast/16x16/apps/sun-jcontrol.png  |   Bin 0 -> 519 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 525 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 525 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 525 bytes
 .../icons/LowContrast/48x48/apps/sun-java.png      |   Bin 0 -> 1507 bytes
 .../icons/LowContrast/48x48/apps/sun-javaws.png    |   Bin 0 -> 1507 bytes
 .../icons/LowContrast/48x48/apps/sun-jcontrol.png  |   Bin 0 -> 1507 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 1948 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 1948 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 1948 bytes
 .../desktop/icons/hicolor/16x16/apps/sun-java.png  |   Bin 0 -> 383 bytes
 .../icons/hicolor/16x16/apps/sun-javaws.png        |   Bin 0 -> 383 bytes
 .../icons/hicolor/16x16/apps/sun-jcontrol.png      |   Bin 0 -> 383 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 783 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 783 bytes
 .../16x16/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 783 bytes
 .../desktop/icons/hicolor/48x48/apps/sun-java.png  |   Bin 0 -> 1439 bytes
 .../icons/hicolor/48x48/apps/sun-javaws.png        |   Bin 0 -> 1439 bytes
 .../icons/hicolor/48x48/apps/sun-jcontrol.png      |   Bin 0 -> 1439 bytes
 .../gnome-mime-application-x-java-archive.png      |   Bin 0 -> 3202 bytes
 .../gnome-mime-application-x-java-jnlp-file.png    |   Bin 0 -> 3202 bytes
 .../48x48/mimetypes/gnome-mime-text-x-java.png     |   Bin 0 -> 3202 bytes
 .../lib/desktop/mime/packages/x-java-archive.xml   |    39 +
 .../lib/desktop/mime/packages/x-java-jnlp-file.xml |    11 +
 .../linux.x86/jre/lib/ext/cldrdata.jar             |   Bin 0 -> 3860522 bytes
 .../linux.x86/jre/lib/ext/dnsns.jar                |   Bin 0 -> 8286 bytes
 .../linux.x86/jre/lib/ext/jfxrt.jar                |   Bin 0 -> 18413856 bytes
 .../linux.x86/jre/lib/ext/localedata.jar           |   Bin 0 -> 1178946 bytes
 .../linux.x86/jre/lib/ext/meta-index               |    61 +
 .../linux.x86/jre/lib/ext/nashorn.jar              |   Bin 0 -> 2008812 bytes
 .../linux.x86/jre/lib/ext/sunec.jar                |   Bin 0 -> 39773 bytes
 .../linux.x86/jre/lib/ext/sunjce_provider.jar      |   Bin 0 -> 278433 bytes
 .../linux.x86/jre/lib/ext/sunpkcs11.jar            |   Bin 0 -> 249403 bytes
 .../linux.x86/jre/lib/ext/zipfs.jar                |   Bin 0 -> 68836 bytes
 .../linux.x86/jre/lib/flavormap.properties         |    78 +
 .../linux.x86/jre/lib/fontconfig.RedHat.5.bfc      |   Bin 0 -> 4532 bytes
 .../jre/lib/fontconfig.RedHat.5.properties.src     |   134 +
 .../linux.x86/jre/lib/fontconfig.RedHat.6.bfc      |   Bin 0 -> 4250 bytes
 .../jre/lib/fontconfig.RedHat.6.properties.src     |   134 +
 .../linux.x86/jre/lib/fontconfig.SuSE.10.bfc       |   Bin 0 -> 6702 bytes
 .../jre/lib/fontconfig.SuSE.10.properties.src      |   218 +
 .../linux.x86/jre/lib/fontconfig.SuSE.11.bfc       |   Bin 0 -> 7032 bytes
 .../jre/lib/fontconfig.SuSE.11.properties.src      |   227 +
 .../linux.x86/jre/lib/fontconfig.Turbo.bfc         |   Bin 0 -> 4668 bytes
 .../jre/lib/fontconfig.Turbo.properties.src        |   144 +
 .../linux.x86/jre/lib/fontconfig.bfc               |   Bin 0 -> 1678 bytes
 .../linux.x86/jre/lib/fontconfig.properties.src    |    46 +
 .../jre/lib/fonts/LucidaBrightDemiBold.ttf         |   Bin 0 -> 75144 bytes
 .../jre/lib/fonts/LucidaBrightDemiItalic.ttf       |   Bin 0 -> 75124 bytes
 .../linux.x86/jre/lib/fonts/LucidaBrightItalic.ttf |   Bin 0 -> 80856 bytes
 .../jre/lib/fonts/LucidaBrightRegular.ttf          |   Bin 0 -> 344908 bytes
 .../linux.x86/jre/lib/fonts/LucidaSansDemiBold.ttf |   Bin 0 -> 317896 bytes
 .../linux.x86/jre/lib/fonts/LucidaSansRegular.ttf  |   Bin 0 -> 698236 bytes
 .../jre/lib/fonts/LucidaTypewriterBold.ttf         |   Bin 0 -> 234068 bytes
 .../jre/lib/fonts/LucidaTypewriterRegular.ttf      |   Bin 0 -> 242700 bytes
 .../linux.x86/jre/lib/fonts/fonts.dir              |    49 +
 .../jre/lib/hijrah-config-umalqura.properties      |   369 +
 .../linux.x86/jre/lib/i386/client/Xusage.txt       |    24 +
 .../linux.x86/jre/lib/i386/client/libjsig.so       |   Bin 0 -> 8211 bytes
 .../linux.x86/jre/lib/i386/client/libjvm.so        |   Bin 0 -> 8270314 bytes
 .../linux.x86/jre/lib/i386/jli/libjli.so           |   Bin 0 -> 95046 bytes
 .../linux.x86/jre/lib/i386/jvm.cfg                 |    35 +
 .../linux.x86/jre/lib/i386/libavplugin-53.so       |   Bin 0 -> 44345 bytes
 .../linux.x86/jre/lib/i386/libavplugin-54.so       |   Bin 0 -> 44392 bytes
 .../linux.x86/jre/lib/i386/libawt.so               |   Bin 0 -> 629231 bytes
 .../linux.x86/jre/lib/i386/libawt_headless.so      |   Bin 0 -> 30307 bytes
 .../linux.x86/jre/lib/i386/libawt_xawt.so          |   Bin 0 -> 392795 bytes
 .../linux.x86/jre/lib/i386/libbci.so               |   Bin 0 -> 7357 bytes
 .../linux.x86/jre/lib/i386/libdcpr.so              |   Bin 0 -> 180583 bytes
 .../linux.x86/jre/lib/i386/libdecora_sse.so        |   Bin 0 -> 69027 bytes
 .../linux.x86/jre/lib/i386/libdeploy.so            |   Bin 0 -> 149265 bytes
 .../linux.x86/jre/lib/i386/libdt_socket.so         |   Bin 0 -> 18836 bytes
 .../linux.x86/jre/lib/i386/libfontmanager.so       |   Bin 0 -> 498796 bytes
 .../linux.x86/jre/lib/i386/libfxplugins.so         |   Bin 0 -> 134489 bytes
 .../linux.x86/jre/lib/i386/libglass.so             |   Bin 0 -> 210835 bytes
 .../linux.x86/jre/lib/i386/libgstreamer-lite.so    |   Bin 0 -> 1530623 bytes
 .../linux.x86/jre/lib/i386/libhprof.so             |   Bin 0 -> 161064 bytes
 .../linux.x86/jre/lib/i386/libinstrument.so        |   Bin 0 -> 45735 bytes
 .../linux.x86/jre/lib/i386/libj2gss.so             |   Bin 0 -> 41900 bytes
 .../linux.x86/jre/lib/i386/libj2pcsc.so            |   Bin 0 -> 13125 bytes
 .../linux.x86/jre/lib/i386/libj2pkcs11.so          |   Bin 0 -> 71250 bytes
 .../linux.x86/jre/lib/i386/libjaas_unix.so         |   Bin 0 -> 5795 bytes
 .../linux.x86/jre/lib/i386/libjava.so              |   Bin 0 -> 188061 bytes
 .../linux.x86/jre/lib/i386/libjava_crw_demo.so     |   Bin 0 -> 23763 bytes
 .../linux.x86/jre/lib/i386/libjavafx_font.so       |   Bin 0 -> 15884 bytes
 .../jre/lib/i386/libjavafx_font_freetype.so        |   Bin 0 -> 21616 bytes
 .../linux.x86/jre/lib/i386/libjavafx_font_pango.so |   Bin 0 -> 23532 bytes
 .../linux.x86/jre/lib/i386/libjavafx_font_t2k.so   |   Bin 0 -> 853379 bytes
 .../linux.x86/jre/lib/i386/libjavafx_iio.so        |   Bin 0 -> 226766 bytes
 .../linux.x86/jre/lib/i386/libjawt.so              |   Bin 0 -> 4920 bytes
 .../linux.x86/jre/lib/i386/libjdwp.so              |   Bin 0 -> 264221 bytes
 .../linux.x86/jre/lib/i386/libjfr.so               |   Bin 0 -> 21247 bytes
 .../linux.x86/jre/lib/i386/libjfxmedia.so          |   Bin 0 -> 224231 bytes
 .../linux.x86/jre/lib/i386/libjfxwebkit.so         |   Bin 0 -> 30883632 bytes
 .../linux.x86/jre/lib/i386/libjpeg.so              |   Bin 0 -> 231119 bytes
 .../linux.x86/jre/lib/i386/libjsdt.so              |   Bin 0 -> 9457 bytes
 .../linux.x86/jre/lib/i386/libjsig.so              |   Bin 0 -> 8211 bytes
 .../linux.x86/jre/lib/i386/libjsound.so            |   Bin 0 -> 5157 bytes
 .../linux.x86/jre/lib/i386/libjsoundalsa.so        |   Bin 0 -> 72770 bytes
 .../linux.x86/jre/lib/i386/libkcms.so              |   Bin 0 -> 379347 bytes
 .../linux.x86/jre/lib/i386/liblcms.so              |   Bin 0 -> 357516 bytes
 .../linux.x86/jre/lib/i386/libmanagement.so        |   Bin 0 -> 43906 bytes
 .../linux.x86/jre/lib/i386/libmlib_image.so        |   Bin 0 -> 917687 bytes
 .../linux.x86/jre/lib/i386/libnet.so               |   Bin 0 -> 104459 bytes
 .../linux.x86/jre/lib/i386/libnio.so               |   Bin 0 -> 80641 bytes
 .../linux.x86/jre/lib/i386/libnpjp2.so             |   Bin 0 -> 198579 bytes
 .../linux.x86/jre/lib/i386/libnpt.so               |   Bin 0 -> 10799 bytes
 .../linux.x86/jre/lib/i386/libprism_common.so      |   Bin 0 -> 48449 bytes
 .../linux.x86/jre/lib/i386/libprism_es2.so         |   Bin 0 -> 57820 bytes
 .../linux.x86/jre/lib/i386/libprism_sw.so          |   Bin 0 -> 69559 bytes
 .../linux.x86/jre/lib/i386/libresource.so          |   Bin 0 -> 7522 bytes
 .../linux.x86/jre/lib/i386/libsctp.so              |   Bin 0 -> 24301 bytes
 .../linux.x86/jre/lib/i386/libsplashscreen.so      |   Bin 0 -> 412729 bytes
 .../linux.x86/jre/lib/i386/libsunec.so             |   Bin 0 -> 252047 bytes
 .../linux.x86/jre/lib/i386/libt2k.so               |   Bin 0 -> 442590 bytes
 .../linux.x86/jre/lib/i386/libunpack.so            |   Bin 0 -> 159967 bytes
 .../linux.x86/jre/lib/i386/libverify.so            |   Bin 0 -> 56041 bytes
 .../linux.x86/jre/lib/i386/libzip.so               |   Bin 0 -> 116546 bytes
 .../linux.x86/jre/lib/i386/server/Xusage.txt       |    24 +
 .../linux.x86/jre/lib/i386/server/libjsig.so       |   Bin 0 -> 8211 bytes
 .../linux.x86/jre/lib/i386/server/libjvm.so        |   Bin 0 -> 12800017 bytes
 .../jre/lib/images/cursors/cursors.properties      |    40 +
 .../jre/lib/images/cursors/invalid32x32.gif        |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/motif_CopyDrop32x32.gif |   Bin 0 -> 158 bytes
 .../lib/images/cursors/motif_CopyNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/motif_LinkDrop32x32.gif |   Bin 0 -> 162 bytes
 .../lib/images/cursors/motif_LinkNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/motif_MoveDrop32x32.gif |   Bin 0 -> 141 bytes
 .../lib/images/cursors/motif_MoveNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../linux.x86/jre/lib/images/icons/sun-java.png    |   Bin 0 -> 4707 bytes
 .../jre/lib/images/icons/sun-java_HighContrast.png |   Bin 0 -> 3729 bytes
 .../images/icons/sun-java_HighContrastInverse.png  |   Bin 0 -> 3777 bytes
 .../jre/lib/images/icons/sun-java_LowContrast.png  |   Bin 0 -> 4012 bytes
 .../linux.x86/jre/lib/javafx.properties            |     2 +
 .../org.xtuml.bp.jre/linux.x86/jre/lib/javaws.jar  |   Bin 0 -> 491749 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/jce.jar     |   Bin 0 -> 114687 bytes
 features/org.xtuml.bp.jre/linux.x86/jre/lib/jexec  |   Bin 0 -> 8018 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/jfr.jar     |   Bin 0 -> 555272 bytes
 .../linux.x86/jre/lib/jfr/default.jfc              |   556 +
 .../linux.x86/jre/lib/jfr/profile.jfc              |   556 +
 .../org.xtuml.bp.jre/linux.x86/jre/lib/jfxswt.jar  |   Bin 0 -> 33793 bytes
 .../org.xtuml.bp.jre/linux.x86/jre/lib/jsse.jar    |   Bin 0 -> 624231 bytes
 .../linux.x86/jre/lib/jvm.hprof.txt                |    86 +
 .../lib/locale/de/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2483 bytes
 .../lib/locale/es/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2477 bytes
 .../lib/locale/fr/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2746 bytes
 .../lib/locale/it/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2434 bytes
 .../lib/locale/ja/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2415 bytes
 .../ko.UTF-8/LC_MESSAGES/sunw_java_plugin.mo       |   Bin 0 -> 2753 bytes
 .../lib/locale/ko/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2399 bytes
 .../locale/pt_BR/LC_MESSAGES/sunw_java_plugin.mo   |   Bin 0 -> 2420 bytes
 .../lib/locale/sv/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2359 bytes
 .../locale/zh.GBK/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2002 bytes
 .../lib/locale/zh/LC_MESSAGES/sunw_java_plugin.mo  |   Bin 0 -> 2002 bytes
 .../zh_HK.BIG5HK/LC_MESSAGES/sunw_java_plugin.mo   |   Bin 0 -> 2025 bytes
 .../zh_TW.BIG5/LC_MESSAGES/sunw_java_plugin.mo     |   Bin 0 -> 2025 bytes
 .../locale/zh_TW/LC_MESSAGES/sunw_java_plugin.mo   |   Bin 0 -> 2025 bytes
 .../linux.x86/jre/lib/logging.properties           |    59 +
 .../linux.x86/jre/lib/management-agent.jar         |   Bin 0 -> 381 bytes
 .../linux.x86/jre/lib/management/jmxremote.access  |    79 +
 .../jre/lib/management/jmxremote.password.template |    64 +
 .../jre/lib/management/management.properties       |   318 +
 .../linux.x86/jre/lib/management/snmp.acl.template |   110 +
 .../org.xtuml.bp.jre/linux.x86/jre/lib/meta-index  |    92 +
 .../linux.x86/jre/lib/net.properties               |    74 +
 .../lib/oblique-fonts/LucidaSansDemiOblique.ttf    |   Bin 0 -> 91352 bytes
 .../jre/lib/oblique-fonts/LucidaSansOblique.ttf    |   Bin 0 -> 253724 bytes
 .../oblique-fonts/LucidaTypewriterBoldOblique.ttf  |   Bin 0 -> 63168 bytes
 .../lib/oblique-fonts/LucidaTypewriterOblique.ttf  |   Bin 0 -> 137484 bytes
 .../linux.x86/jre/lib/oblique-fonts/fonts.dir      |    25 +
 .../org.xtuml.bp.jre/linux.x86/jre/lib/plugin.jar  |   Bin 0 -> 952576 bytes
 .../linux.x86/jre/lib/psfont.properties.ja         |   119 +
 .../linux.x86/jre/lib/psfontj2d.properties         |   323 +
 .../linux.x86/jre/lib/resources.jar                |   Bin 0 -> 3500533 bytes
 features/org.xtuml.bp.jre/linux.x86/jre/lib/rt.jar |   Bin 0 -> 65944575 bytes
 .../jre/lib/security/US_export_policy.jar          |   Bin 0 -> 3026 bytes
 .../linux.x86/jre/lib/security/blacklist           |    95 +
 .../linux.x86/jre/lib/security/blacklisted.certs   |    19 +
 .../linux.x86/jre/lib/security/cacerts             |   Bin 0 -> 99954 bytes
 .../linux.x86/jre/lib/security/java.policy         |    49 +
 .../linux.x86/jre/lib/security/java.security       |   582 +
 .../linux.x86/jre/lib/security/javaws.policy       |     5 +
 .../linux.x86/jre/lib/security/local_policy.jar    |   Bin 0 -> 3527 bytes
 .../linux.x86/jre/lib/security/trusted.libraries   |     0
 .../linux.x86/jre/lib/sound.properties             |    39 +
 .../org.xtuml.bp.jre/linux.x86/jre/lib/tzdb.dat    |   Bin 0 -> 101371 bytes
 .../linux.x86/jre/man/ja/man1/java.1               |  3117 +++
 .../linux.x86/jre/man/ja/man1/javaws.1             |   215 +
 .../linux.x86/jre/man/ja/man1/jjs.1                |   415 +
 .../linux.x86/jre/man/ja/man1/keytool.1            |  2229 ++
 .../linux.x86/jre/man/ja/man1/orbd.1               |   266 +
 .../linux.x86/jre/man/ja/man1/pack200.1            |   354 +
 .../linux.x86/jre/man/ja/man1/policytool.1         |   158 +
 .../linux.x86/jre/man/ja/man1/rmid.1               |   354 +
 .../linux.x86/jre/man/ja/man1/rmiregistry.1        |   116 +
 .../linux.x86/jre/man/ja/man1/servertool.1         |   182 +
 .../linux.x86/jre/man/ja/man1/tnameserv.1          |   427 +
 .../linux.x86/jre/man/ja/man1/unpack200.1          |   186 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/java.1      |  3117 +++
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/javaws.1    |   215 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/jjs.1       |   415 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/keytool.1   |  2229 ++
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/orbd.1      |   266 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/pack200.1   |   354 +
 .../jre/man/ja_JP.UTF-8/man1/policytool.1          |   158 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/rmid.1      |   354 +
 .../jre/man/ja_JP.UTF-8/man1/rmiregistry.1         |   116 +
 .../jre/man/ja_JP.UTF-8/man1/servertool.1          |   182 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/tnameserv.1 |   427 +
 .../linux.x86/jre/man/ja_JP.UTF-8/man1/unpack200.1 |   186 +
 .../org.xtuml.bp.jre/linux.x86/jre/man/man1/java.1 |  4418 +++
 .../linux.x86/jre/man/man1/javaws.1                |   157 +
 .../org.xtuml.bp.jre/linux.x86/jre/man/man1/jjs.1  |   228 +
 .../linux.x86/jre/man/man1/keytool.1               |  1599 ++
 .../org.xtuml.bp.jre/linux.x86/jre/man/man1/orbd.1 |   193 +
 .../linux.x86/jre/man/man1/pack200.1               |   270 +
 .../linux.x86/jre/man/man1/policytool.1            |    94 +
 .../org.xtuml.bp.jre/linux.x86/jre/man/man1/rmid.1 |   294 +
 .../linux.x86/jre/man/man1/rmiregistry.1           |    78 +
 .../linux.x86/jre/man/man1/servertool.1            |   117 +
 .../linux.x86/jre/man/man1/tnameserv.1             |   468 +
 .../linux.x86/jre/man/man1/unpack200.1             |   117 +
 .../linux.x86/jre/plugin/desktop/sun_java.desktop  |    20 +
 .../linux.x86/jre/plugin/desktop/sun_java.png      |   Bin 0 -> 4351 bytes
 features/org.xtuml.bp.jre/linux.x86/jre/release    |     6 +
 features/org.xtuml.bp.jre/pom.xml                  |    15 +
 features/org.xtuml.bp.jre/win32.x64/jre/COPYRIGHT  |    69 +
 features/org.xtuml.bp.jre/win32.x64/jre/LICENSE    |     1 +
 features/org.xtuml.bp.jre/win32.x64/jre/README.txt |     1 +
 .../jre/THIRDPARTYLICENSEREADME-JAVAFX.txt         |  1531 ++
 .../win32.x64/jre/THIRDPARTYLICENSEREADME.txt      |  3574 +++
 .../org.xtuml.bp.jre/win32.x64/jre/Welcome.html    |    28 +
 .../win32.x64/jre/bin/JAWTAccessBridge-64.dll      |   Bin 0 -> 15424 bytes
 .../win32.x64/jre/bin/JavaAccessBridge-64.dll      |   Bin 0 -> 142400 bytes
 .../win32.x64/jre/bin/WindowsAccessBridge-64.dll   |   Bin 0 -> 110144 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/awt.dll     |   Bin 0 -> 1516096 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/bci.dll     |   Bin 0 -> 16960 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/dcpr.dll    |   Bin 0 -> 159808 bytes
 .../win32.x64/jre/bin/decora_sse.dll               |   Bin 0 -> 85568 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/deploy.dll  |   Bin 0 -> 583744 bytes
 .../win32.x64/jre/bin/dt_shmem.dll                 |   Bin 0 -> 29760 bytes
 .../win32.x64/jre/bin/dt_socket.dll                |   Bin 0 -> 24640 bytes
 .../win32.x64/jre/bin/dtplugin/deployJava1.dll     |   Bin 0 -> 1023552 bytes
 .../win32.x64/jre/bin/dtplugin/npdeployJava1.dll   |   Bin 0 -> 1150016 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/eula.dll    |   Bin 0 -> 136256 bytes
 .../win32.x64/jre/bin/fontmanager.dll              |   Bin 0 -> 274496 bytes
 .../win32.x64/jre/bin/fxplugins.dll                |   Bin 0 -> 184384 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/glass.dll   |   Bin 0 -> 265280 bytes
 .../win32.x64/jre/bin/glib-lite.dll                |   Bin 0 -> 455232 bytes
 .../win32.x64/jre/bin/gstreamer-lite.dll           |   Bin 0 -> 619584 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/hprof.dll   |   Bin 0 -> 157760 bytes
 .../win32.x64/jre/bin/instrument.dll               |   Bin 0 -> 123456 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/j2pcsc.dll  |   Bin 0 -> 19008 bytes
 .../win32.x64/jre/bin/j2pkcs11.dll                 |   Bin 0 -> 63552 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jaas_nt.dll |   Bin 0 -> 21056 bytes
 .../win32.x64/jre/bin/jabswitch.exe                |   Bin 0 -> 34368 bytes
 .../win32.x64/jre/bin/java-rmi.exe                 |   Bin 0 -> 15936 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/java.dll    |   Bin 0 -> 159296 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/java.exe    |   Bin 0 -> 206912 bytes
 .../win32.x64/jre/bin/java_crw_demo.dll            |   Bin 0 -> 29760 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/javacpl.cpl |   Bin 0 -> 177152 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/javacpl.exe |   Bin 0 -> 77888 bytes
 .../win32.x64/jre/bin/javafx_font.dll              |   Bin 0 -> 69184 bytes
 .../win32.x64/jre/bin/javafx_font_t2k.dll          |   Bin 0 -> 537664 bytes
 .../win32.x64/jre/bin/javafx_iio.dll               |   Bin 0 -> 128064 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/javaw.exe   |   Bin 0 -> 206912 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/javaws.exe  |   Bin 0 -> 315456 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jawt.dll    |   Bin 0 -> 14400 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jdwp.dll    |   Bin 0 -> 201280 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jfr.dll     |   Bin 0 -> 26688 bytes
 .../win32.x64/jre/bin/jfxmedia.dll                 |   Bin 0 -> 139840 bytes
 .../win32.x64/jre/bin/jfxwebkit.dll                |   Bin 0 -> 39310912 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jjs.exe     |   Bin 0 -> 15936 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jli.dll     |   Bin 0 -> 174656 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jp2iexp.dll |   Bin 0 -> 291904 bytes
 .../win32.x64/jre/bin/jp2launcher.exe              |   Bin 0 -> 101440 bytes
 .../win32.x64/jre/bin/jp2native.dll                |   Bin 0 -> 20032 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jp2ssv.dll  |   Bin 0 -> 214080 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jpeg.dll    |   Bin 0 -> 185408 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jsdt.dll    |   Bin 0 -> 18496 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/jsound.dll  |   Bin 0 -> 35392 bytes
 .../win32.x64/jre/bin/jsoundds.dll                 |   Bin 0 -> 31296 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/kcms.dll    |   Bin 0 -> 220736 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/keytool.exe |   Bin 0 -> 16448 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/kinit.exe   |   Bin 0 -> 16448 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/klist.exe   |   Bin 0 -> 16448 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/ktab.exe    |   Bin 0 -> 16448 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/lcms.dll    |   Bin 0 -> 233536 bytes
 .../win32.x64/jre/bin/management.dll               |   Bin 0 -> 36928 bytes
 .../win32.x64/jre/bin/mlib_image.dll               |   Bin 0 -> 653888 bytes
 .../win32.x64/jre/bin/msvcp120.dll                 |   Bin 0 -> 660128 bytes
 .../win32.x64/jre/bin/msvcr100.dll                 |   Bin 0 -> 829264 bytes
 .../win32.x64/jre/bin/msvcr120.dll                 |   Bin 0 -> 963232 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/net.dll     |   Bin 0 -> 95808 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/nio.dll     |   Bin 0 -> 60480 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/npt.dll     |   Bin 0 -> 19008 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/orbd.exe    |   Bin 0 -> 16448 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/pack200.exe |   Bin 0 -> 16448 bytes
 .../win32.x64/jre/bin/plugin2/msvcr100.dll         |   Bin 0 -> 829264 bytes
 .../win32.x64/jre/bin/plugin2/npjp2.dll            |   Bin 0 -> 230464 bytes
 .../win32.x64/jre/bin/policytool.exe               |   Bin 0 -> 16448 bytes
 .../win32.x64/jre/bin/prism_common.dll             |   Bin 0 -> 57408 bytes
 .../win32.x64/jre/bin/prism_d3d.dll                |   Bin 0 -> 131648 bytes
 .../win32.x64/jre/bin/prism_sw.dll                 |   Bin 0 -> 97344 bytes
 .../win32.x64/jre/bin/resource.dll                 |   Bin 0 -> 15424 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/rmid.exe    |   Bin 0 -> 15936 bytes
 .../win32.x64/jre/bin/rmiregistry.exe              |   Bin 0 -> 16448 bytes
 .../win32.x64/jre/bin/server/Xusage.txt            |    24 +
 .../win32.x64/jre/bin/server/classes.jsa           |   Bin 0 -> 18677760 bytes
 .../win32.x64/jre/bin/server/jvm.dll               |   Bin 0 -> 8767040 bytes
 .../win32.x64/jre/bin/servertool.exe               |   Bin 0 -> 16448 bytes
 .../win32.x64/jre/bin/splashscreen.dll             |   Bin 0 -> 211008 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/ssv.dll     |   Bin 0 -> 553024 bytes
 .../win32.x64/jre/bin/ssvagent.exe                 |   Bin 0 -> 67136 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/sunec.dll   |   Bin 0 -> 134720 bytes
 .../win32.x64/jre/bin/sunmscapi.dll                |   Bin 0 -> 31808 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/t2k.dll     |   Bin 0 -> 255040 bytes
 .../win32.x64/jre/bin/tnameserv.exe                |   Bin 0 -> 16448 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/unpack.dll  |   Bin 0 -> 79936 bytes
 .../win32.x64/jre/bin/unpack200.exe                |   Bin 0 -> 197184 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/verify.dll  |   Bin 0 -> 49216 bytes
 .../win32.x64/jre/bin/w2k_lsa_auth.dll             |   Bin 0 -> 24128 bytes
 .../win32.x64/jre/bin/wsdetect.dll                 |   Bin 0 -> 192576 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/bin/zip.dll     |   Bin 0 -> 77888 bytes
 .../win32.x64/jre/lib/accessibility.properties     |     6 +
 .../win32.x64/jre/lib/amd64/jvm.cfg                |    38 +
 .../win32.x64/jre/lib/calendars.properties         |    60 +
 .../win32.x64/jre/lib/charsets.jar                 |   Bin 0 -> 3036922 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/classlist   |  2469 ++
 .../win32.x64/jre/lib/cmm/CIEXYZ.pf                |   Bin 0 -> 51236 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/cmm/GRAY.pf |   Bin 0 -> 632 bytes
 .../win32.x64/jre/lib/cmm/LINEAR_RGB.pf            |   Bin 0 -> 1044 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/cmm/PYCC.pf |   Bin 0 -> 274474 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/cmm/sRGB.pf |   Bin 0 -> 3144 bytes
 .../win32.x64/jre/lib/content-types.properties     |   276 +
 .../win32.x64/jre/lib/currency.data                |   Bin 0 -> 4122 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/deploy.jar  |   Bin 0 -> 5004871 bytes
 .../win32.x64/jre/lib/deploy/ffjcext.zip           |   Bin 0 -> 14130 bytes
 .../win32.x64/jre/lib/deploy/messages.properties   |    57 +
 .../jre/lib/deploy/messages_de.properties          |    32 +
 .../jre/lib/deploy/messages_es.properties          |    32 +
 .../jre/lib/deploy/messages_fr.properties          |    32 +
 .../jre/lib/deploy/messages_it.properties          |    32 +
 .../jre/lib/deploy/messages_ja.properties          |    32 +
 .../jre/lib/deploy/messages_ko.properties          |    32 +
 .../jre/lib/deploy/messages_pt_BR.properties       |    32 +
 .../jre/lib/deploy/messages_sv.properties          |    32 +
 .../jre/lib/deploy/messages_zh_CN.properties       |    32 +
 .../jre/lib/deploy/messages_zh_HK.properties       |    32 +
 .../jre/lib/deploy/messages_zh_TW.properties       |    32 +
 .../win32.x64/jre/lib/deploy/splash.gif            |   Bin 0 -> 8590 bytes
 .../win32.x64/jre/lib/deploy/splash@2x.gif         |   Bin 0 -> 15276 bytes
 .../win32.x64/jre/lib/deploy/splash_11-lic.gif     |   Bin 0 -> 7805 bytes
 .../win32.x64/jre/lib/deploy/splash_11@2x-lic.gif  |   Bin 0 -> 12250 bytes
 .../win32.x64/jre/lib/ext/access-bridge-64.jar     |   Bin 0 -> 187735 bytes
 .../win32.x64/jre/lib/ext/cldrdata.jar             |   Bin 0 -> 3860522 bytes
 .../win32.x64/jre/lib/ext/dnsns.jar                |   Bin 0 -> 8286 bytes
 .../win32.x64/jre/lib/ext/jaccess.jar              |   Bin 0 -> 44516 bytes
 .../win32.x64/jre/lib/ext/jfxrt.jar                |   Bin 0 -> 16612771 bytes
 .../win32.x64/jre/lib/ext/localedata.jar           |   Bin 0 -> 2204371 bytes
 .../win32.x64/jre/lib/ext/meta-index               |    69 +
 .../win32.x64/jre/lib/ext/nashorn.jar              |   Bin 0 -> 2028225 bytes
 .../win32.x64/jre/lib/ext/sunec.jar                |   Bin 0 -> 39771 bytes
 .../win32.x64/jre/lib/ext/sunjce_provider.jar      |   Bin 0 -> 278718 bytes
 .../win32.x64/jre/lib/ext/sunmscapi.jar            |   Bin 0 -> 32701 bytes
 .../win32.x64/jre/lib/ext/sunpkcs11.jar            |   Bin 0 -> 250826 bytes
 .../win32.x64/jre/lib/ext/zipfs.jar                |   Bin 0 -> 68849 bytes
 .../win32.x64/jre/lib/flavormap.properties         |    77 +
 .../win32.x64/jre/lib/fontconfig.bfc               |   Bin 0 -> 3670 bytes
 .../win32.x64/jre/lib/fontconfig.properties.src    |   300 +
 .../jre/lib/fonts/LucidaBrightDemiBold.ttf         |   Bin 0 -> 75144 bytes
 .../jre/lib/fonts/LucidaBrightDemiItalic.ttf       |   Bin 0 -> 75124 bytes
 .../win32.x64/jre/lib/fonts/LucidaBrightItalic.ttf |   Bin 0 -> 80856 bytes
 .../jre/lib/fonts/LucidaBrightRegular.ttf          |   Bin 0 -> 344908 bytes
 .../win32.x64/jre/lib/fonts/LucidaSansDemiBold.ttf |   Bin 0 -> 317896 bytes
 .../win32.x64/jre/lib/fonts/LucidaSansRegular.ttf  |   Bin 0 -> 698236 bytes
 .../jre/lib/fonts/LucidaTypewriterBold.ttf         |   Bin 0 -> 234068 bytes
 .../jre/lib/fonts/LucidaTypewriterRegular.ttf      |   Bin 0 -> 242700 bytes
 .../jre/lib/hijrah-config-umalqura.properties      |   369 +
 .../jre/lib/images/cursors/cursors.properties      |    40 +
 .../jre/lib/images/cursors/invalid32x32.gif        |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/win32_CopyDrop32x32.gif |   Bin 0 -> 165 bytes
 .../lib/images/cursors/win32_CopyNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/win32_LinkDrop32x32.gif |   Bin 0 -> 168 bytes
 .../lib/images/cursors/win32_LinkNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/win32_MoveDrop32x32.gif |   Bin 0 -> 147 bytes
 .../lib/images/cursors/win32_MoveNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../win32.x64/jre/lib/javafx.properties            |     2 +
 .../org.xtuml.bp.jre/win32.x64/jre/lib/javaws.jar  |   Bin 0 -> 942141 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/jce.jar     |   Bin 0 -> 114950 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/jfr.jar     |   Bin 0 -> 560452 bytes
 .../win32.x64/jre/lib/jfr/default.jfc              |   561 +
 .../win32.x64/jre/lib/jfr/profile.jfc              |   561 +
 .../org.xtuml.bp.jre/win32.x64/jre/lib/jfxswt.jar  |   Bin 0 -> 33932 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/jsse.jar    |   Bin 0 -> 571136 bytes
 .../win32.x64/jre/lib/jvm.hprof.txt                |    86 +
 .../win32.x64/jre/lib/logging.properties           |    59 +
 .../win32.x64/jre/lib/management-agent.jar         |   Bin 0 -> 381 bytes
 .../win32.x64/jre/lib/management/jmxremote.access  |    79 +
 .../jre/lib/management/jmxremote.password.template |    64 +
 .../jre/lib/management/management.properties       |   318 +
 .../win32.x64/jre/lib/management/snmp.acl.template |   110 +
 .../org.xtuml.bp.jre/win32.x64/jre/lib/meta-index  |    92 +
 .../win32.x64/jre/lib/net.properties               |    74 +
 .../org.xtuml.bp.jre/win32.x64/jre/lib/plugin.jar  |   Bin 0 -> 1925338 bytes
 .../win32.x64/jre/lib/psfont.properties.ja         |   119 +
 .../win32.x64/jre/lib/psfontj2d.properties         |   323 +
 .../win32.x64/jre/lib/resources.jar                |   Bin 0 -> 3488879 bytes
 features/org.xtuml.bp.jre/win32.x64/jre/lib/rt.jar |   Bin 0 -> 54400196 bytes
 .../jre/lib/security/US_export_policy.jar          |   Bin 0 -> 3026 bytes
 .../win32.x64/jre/lib/security/blacklist           |    95 +
 .../win32.x64/jre/lib/security/blacklisted.certs   |    20 +
 .../win32.x64/jre/lib/security/cacerts             |   Bin 0 -> 108873 bytes
 .../win32.x64/jre/lib/security/java.policy         |    49 +
 .../win32.x64/jre/lib/security/java.security       |   651 +
 .../win32.x64/jre/lib/security/javaws.policy       |     5 +
 .../win32.x64/jre/lib/security/local_policy.jar    |   Bin 0 -> 3527 bytes
 .../win32.x64/jre/lib/security/trusted.libraries   |     0
 .../win32.x64/jre/lib/sound.properties             |    39 +
 .../org.xtuml.bp.jre/win32.x64/jre/lib/tzdb.dat    |   Bin 0 -> 101908 bytes
 .../org.xtuml.bp.jre/win32.x64/jre/lib/tzmappings  |   202 +
 features/org.xtuml.bp.jre/win32.x64/jre/release    |     6 +
 features/org.xtuml.bp.jre/win32.x86/jre/COPYRIGHT  |    69 +
 features/org.xtuml.bp.jre/win32.x86/jre/LICENSE    |     1 +
 features/org.xtuml.bp.jre/win32.x86/jre/README.txt |     1 +
 .../jre/THIRDPARTYLICENSEREADME-JAVAFX.txt         |  1531 ++
 .../win32.x86/jre/THIRDPARTYLICENSEREADME.txt      |  3605 +++
 .../org.xtuml.bp.jre/win32.x86/jre/Welcome.html    |    28 +
 .../win32.x86/jre/bin/JAWTAccessBridge-32.dll      |   Bin 0 -> 14944 bytes
 .../win32.x86/jre/bin/JAWTAccessBridge.dll         |   Bin 0 -> 14944 bytes
 .../win32.x86/jre/bin/JavaAccessBridge-32.dll      |   Bin 0 -> 128096 bytes
 .../win32.x86/jre/bin/JavaAccessBridge.dll         |   Bin 0 -> 127584 bytes
 .../win32.x86/jre/bin/WindowsAccessBridge-32.dll   |   Bin 0 -> 97888 bytes
 .../win32.x86/jre/bin/WindowsAccessBridge.dll      |   Bin 0 -> 96352 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/awt.dll     |   Bin 0 -> 1182304 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/bci.dll     |   Bin 0 -> 15456 bytes
 .../win32.x86/jre/bin/client/Xusage.txt            |    24 +
 .../win32.x86/jre/bin/client/jvm.dll               |   Bin 0 -> 3814496 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/dcpr.dll    |   Bin 0 -> 142944 bytes
 .../win32.x86/jre/bin/decora_sse.dll               |   Bin 0 -> 63072 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/deploy.dll  |   Bin 0 -> 444000 bytes
 .../win32.x86/jre/bin/dt_shmem.dll                 |   Bin 0 -> 25184 bytes
 .../win32.x86/jre/bin/dt_socket.dll                |   Bin 0 -> 21600 bytes
 .../win32.x86/jre/bin/dtplugin/deployJava1.dll     |   Bin 0 -> 817760 bytes
 .../win32.x86/jre/bin/dtplugin/npdeployJava1.dll   |   Bin 0 -> 898144 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/eula.dll    |   Bin 0 -> 109152 bytes
 .../win32.x86/jre/bin/fontmanager.dll              |   Bin 0 -> 222304 bytes
 .../win32.x86/jre/bin/fxplugins.dll                |   Bin 0 -> 147552 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/glass.dll   |   Bin 0 -> 205408 bytes
 .../win32.x86/jre/bin/glib-lite.dll                |   Bin 0 -> 403552 bytes
 .../win32.x86/jre/bin/gstreamer-lite.dll           |   Bin 0 -> 495712 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/hprof.dll   |   Bin 0 -> 131680 bytes
 .../win32.x86/jre/bin/instrument.dll               |   Bin 0 -> 115808 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/j2pcsc.dll  |   Bin 0 -> 16480 bytes
 .../win32.x86/jre/bin/j2pkcs11.dll                 |   Bin 0 -> 50784 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jaas_nt.dll |   Bin 0 -> 19552 bytes
 .../win32.x86/jre/bin/jabswitch.exe                |   Bin 0 -> 30304 bytes
 .../win32.x86/jre/bin/java-rmi.exe                 |   Bin 0 -> 15456 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/java.dll    |   Bin 0 -> 125536 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/java.exe    |   Bin 0 -> 190560 bytes
 .../win32.x86/jre/bin/java_crw_demo.dll            |   Bin 0 -> 23648 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/javacpl.cpl |   Bin 0 -> 146432 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/javacpl.exe |   Bin 0 -> 68192 bytes
 .../win32.x86/jre/bin/javafx_font.dll              |   Bin 0 -> 60000 bytes
 .../win32.x86/jre/bin/javafx_font_t2k.dll          |   Bin 0 -> 436832 bytes
 .../win32.x86/jre/bin/javafx_iio.dll               |   Bin 0 -> 118368 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/javaw.exe   |   Bin 0 -> 191584 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/javaws.exe  |   Bin 0 -> 273504 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jawt.dll    |   Bin 0 -> 13920 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jdwp.dll    |   Bin 0 -> 163936 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jfr.dll     |   Bin 0 -> 22624 bytes
 .../win32.x86/jre/bin/jfxmedia.dll                 |   Bin 0 -> 116832 bytes
 .../win32.x86/jre/bin/jfxwebkit.dll                |   Bin 0 -> 16311392 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jjs.exe     |   Bin 0 -> 15456 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jli.dll     |   Bin 0 -> 158816 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jp2iexp.dll |   Bin 0 -> 204384 bytes
 .../win32.x86/jre/bin/jp2launcher.exe              |   Bin 0 -> 77920 bytes
 .../win32.x86/jre/bin/jp2native.dll                |   Bin 0 -> 19040 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jp2ssv.dll  |   Bin 0 -> 172640 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jpeg.dll    |   Bin 0 -> 145504 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jsdt.dll    |   Bin 0 -> 16480 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/jsound.dll  |   Bin 0 -> 30816 bytes
 .../win32.x86/jre/bin/jsoundds.dll                 |   Bin 0 -> 27744 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/kcms.dll    |   Bin 0 -> 178272 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/keytool.exe |   Bin 0 -> 15456 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/kinit.exe   |   Bin 0 -> 15456 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/klist.exe   |   Bin 0 -> 15456 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/ktab.exe    |   Bin 0 -> 15456 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/lcms.dll    |   Bin 0 -> 188000 bytes
 .../win32.x86/jre/bin/management.dll               |   Bin 0 -> 33376 bytes
 .../win32.x86/jre/bin/mlib_image.dll               |   Bin 0 -> 574560 bytes
 .../win32.x86/jre/bin/msvcr100.dll                 |   Bin 0 -> 773968 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/net.dll     |   Bin 0 -> 77920 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/nio.dll     |   Bin 0 -> 49760 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/npt.dll     |   Bin 0 -> 17504 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/orbd.exe    |   Bin 0 -> 15968 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/pack200.exe |   Bin 0 -> 15456 bytes
 .../win32.x86/jre/bin/plugin2/msvcr100.dll         |   Bin 0 -> 773968 bytes
 .../win32.x86/jre/bin/plugin2/npjp2.dll            |   Bin 0 -> 169056 bytes
 .../win32.x86/jre/bin/policytool.exe               |   Bin 0 -> 15968 bytes
 .../win32.x86/jre/bin/prism_common.dll             |   Bin 0 -> 47200 bytes
 .../win32.x86/jre/bin/prism_d3d.dll                |   Bin 0 -> 142432 bytes
 .../win32.x86/jre/bin/prism_es2.dll                |   Bin 0 -> 39520 bytes
 .../win32.x86/jre/bin/prism_sw.dll                 |   Bin 0 -> 47712 bytes
 .../win32.x86/jre/bin/resource.dll                 |   Bin 0 -> 14432 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/rmid.exe    |   Bin 0 -> 15456 bytes
 .../win32.x86/jre/bin/rmiregistry.exe              |   Bin 0 -> 15968 bytes
 .../win32.x86/jre/bin/servertool.exe               |   Bin 0 -> 15968 bytes
 .../win32.x86/jre/bin/splashscreen.dll             |   Bin 0 -> 176736 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/ssv.dll     |   Bin 0 -> 460384 bytes
 .../win32.x86/jre/bin/ssvagent.exe                 |   Bin 0 -> 50784 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/sunec.dll   |   Bin 0 -> 124000 bytes
 .../win32.x86/jre/bin/sunmscapi.dll                |   Bin 0 -> 25696 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/t2k.dll     |   Bin 0 -> 193120 bytes
 .../win32.x86/jre/bin/tnameserv.exe                |   Bin 0 -> 15968 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/unpack.dll  |   Bin 0 -> 65632 bytes
 .../win32.x86/jre/bin/unpack200.exe                |   Bin 0 -> 159328 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/verify.dll  |   Bin 0 -> 39520 bytes
 .../win32.x86/jre/bin/w2k_lsa_auth.dll             |   Bin 0 -> 21600 bytes
 .../win32.x86/jre/bin/wsdetect.dll                 |   Bin 0 -> 163936 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/bin/zip.dll     |   Bin 0 -> 68704 bytes
 .../win32.x86/jre/lib/accessibility.properties     |     6 +
 .../win32.x86/jre/lib/calendars.properties         |    60 +
 .../win32.x86/jre/lib/charsets.jar                 |   Bin 0 -> 3087946 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/classlist   |  2378 ++
 .../win32.x86/jre/lib/cmm/CIEXYZ.pf                |   Bin 0 -> 51236 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/cmm/GRAY.pf |   Bin 0 -> 632 bytes
 .../win32.x86/jre/lib/cmm/LINEAR_RGB.pf            |   Bin 0 -> 1044 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/cmm/PYCC.pf |   Bin 0 -> 274474 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/cmm/sRGB.pf |   Bin 0 -> 3144 bytes
 .../win32.x86/jre/lib/content-types.properties     |   276 +
 .../win32.x86/jre/lib/currency.data                |   Bin 0 -> 4074 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/deploy.jar  |   Bin 0 -> 2209842 bytes
 .../win32.x86/jre/lib/deploy/ffjcext.zip           |   Bin 0 -> 14130 bytes
 .../win32.x86/jre/lib/deploy/messages.properties   |    57 +
 .../jre/lib/deploy/messages_de.properties          |    32 +
 .../jre/lib/deploy/messages_es.properties          |    32 +
 .../jre/lib/deploy/messages_fr.properties          |    32 +
 .../jre/lib/deploy/messages_it.properties          |    32 +
 .../jre/lib/deploy/messages_ja.properties          |    32 +
 .../jre/lib/deploy/messages_ko.properties          |    32 +
 .../jre/lib/deploy/messages_pt_BR.properties       |    32 +
 .../jre/lib/deploy/messages_sv.properties          |    32 +
 .../jre/lib/deploy/messages_zh_CN.properties       |    32 +
 .../jre/lib/deploy/messages_zh_HK.properties       |    32 +
 .../jre/lib/deploy/messages_zh_TW.properties       |    32 +
 .../win32.x86/jre/lib/deploy/splash.gif            |   Bin 0 -> 8590 bytes
 .../win32.x86/jre/lib/deploy/splash@2x.gif         |   Bin 0 -> 15276 bytes
 .../win32.x86/jre/lib/ext/access-bridge-32.jar     |   Bin 0 -> 187725 bytes
 .../win32.x86/jre/lib/ext/access-bridge.jar        |   Bin 0 -> 187715 bytes
 .../win32.x86/jre/lib/ext/cldrdata.jar             |   Bin 0 -> 3860522 bytes
 .../win32.x86/jre/lib/ext/dnsns.jar                |   Bin 0 -> 8286 bytes
 .../win32.x86/jre/lib/ext/jaccess.jar              |   Bin 0 -> 44115 bytes
 .../win32.x86/jre/lib/ext/jfxrt.jar                |   Bin 0 -> 18168460 bytes
 .../win32.x86/jre/lib/ext/localedata.jar           |   Bin 0 -> 1178946 bytes
 .../win32.x86/jre/lib/ext/meta-index               |    71 +
 .../win32.x86/jre/lib/ext/nashorn.jar              |   Bin 0 -> 2008812 bytes
 .../win32.x86/jre/lib/ext/sunec.jar                |   Bin 0 -> 39773 bytes
 .../win32.x86/jre/lib/ext/sunjce_provider.jar      |   Bin 0 -> 278433 bytes
 .../win32.x86/jre/lib/ext/sunmscapi.jar            |   Bin 0 -> 32654 bytes
 .../win32.x86/jre/lib/ext/sunpkcs11.jar            |   Bin 0 -> 249403 bytes
 .../win32.x86/jre/lib/ext/zipfs.jar                |   Bin 0 -> 68836 bytes
 .../win32.x86/jre/lib/flavormap.properties         |    77 +
 .../win32.x86/jre/lib/fontconfig.bfc               |   Bin 0 -> 3670 bytes
 .../win32.x86/jre/lib/fontconfig.properties.src    |   300 +
 .../jre/lib/fonts/LucidaBrightDemiBold.ttf         |   Bin 0 -> 75144 bytes
 .../jre/lib/fonts/LucidaBrightDemiItalic.ttf       |   Bin 0 -> 75124 bytes
 .../win32.x86/jre/lib/fonts/LucidaBrightItalic.ttf |   Bin 0 -> 80856 bytes
 .../jre/lib/fonts/LucidaBrightRegular.ttf          |   Bin 0 -> 344908 bytes
 .../win32.x86/jre/lib/fonts/LucidaSansDemiBold.ttf |   Bin 0 -> 317896 bytes
 .../win32.x86/jre/lib/fonts/LucidaSansRegular.ttf  |   Bin 0 -> 698236 bytes
 .../jre/lib/fonts/LucidaTypewriterBold.ttf         |   Bin 0 -> 234068 bytes
 .../jre/lib/fonts/LucidaTypewriterRegular.ttf      |   Bin 0 -> 242700 bytes
 .../jre/lib/hijrah-config-umalqura.properties      |   369 +
 .../win32.x86/jre/lib/i386/jvm.cfg                 |    34 +
 .../jre/lib/images/cursors/cursors.properties      |    40 +
 .../jre/lib/images/cursors/invalid32x32.gif        |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/win32_CopyDrop32x32.gif |   Bin 0 -> 165 bytes
 .../lib/images/cursors/win32_CopyNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/win32_LinkDrop32x32.gif |   Bin 0 -> 168 bytes
 .../lib/images/cursors/win32_LinkNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../jre/lib/images/cursors/win32_MoveDrop32x32.gif |   Bin 0 -> 147 bytes
 .../lib/images/cursors/win32_MoveNoDrop32x32.gif   |   Bin 0 -> 153 bytes
 .../win32.x86/jre/lib/javafx.properties            |     2 +
 .../org.xtuml.bp.jre/win32.x86/jre/lib/javaws.jar  |   Bin 0 -> 487208 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/jce.jar     |   Bin 0 -> 114687 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/jfr.jar     |   Bin 0 -> 555272 bytes
 .../win32.x86/jre/lib/jfr/default.jfc              |   556 +
 .../win32.x86/jre/lib/jfr/profile.jfc              |   556 +
 .../org.xtuml.bp.jre/win32.x86/jre/lib/jfxswt.jar  |   Bin 0 -> 33795 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/jsse.jar    |   Bin 0 -> 624231 bytes
 .../win32.x86/jre/lib/jvm.hprof.txt                |    86 +
 .../win32.x86/jre/lib/logging.properties           |    59 +
 .../win32.x86/jre/lib/management-agent.jar         |   Bin 0 -> 381 bytes
 .../win32.x86/jre/lib/management/jmxremote.access  |    79 +
 .../jre/lib/management/jmxremote.password.template |    64 +
 .../jre/lib/management/management.properties       |   318 +
 .../win32.x86/jre/lib/management/snmp.acl.template |   110 +
 .../org.xtuml.bp.jre/win32.x86/jre/lib/meta-index  |    92 +
 .../win32.x86/jre/lib/net.properties               |    74 +
 .../org.xtuml.bp.jre/win32.x86/jre/lib/plugin.jar  |   Bin 0 -> 1012834 bytes
 .../win32.x86/jre/lib/psfont.properties.ja         |   119 +
 .../win32.x86/jre/lib/psfontj2d.properties         |   323 +
 .../win32.x86/jre/lib/resources.jar                |   Bin 0 -> 3487766 bytes
 features/org.xtuml.bp.jre/win32.x86/jre/lib/rt.jar |   Bin 0 -> 63484393 bytes
 .../jre/lib/security/US_export_policy.jar          |   Bin 0 -> 3026 bytes
 .../win32.x86/jre/lib/security/blacklist           |    95 +
 .../win32.x86/jre/lib/security/blacklisted.certs   |    19 +
 .../win32.x86/jre/lib/security/cacerts             |   Bin 0 -> 99954 bytes
 .../win32.x86/jre/lib/security/java.policy         |    49 +
 .../win32.x86/jre/lib/security/java.security       |   585 +
 .../win32.x86/jre/lib/security/javaws.policy       |     5 +
 .../win32.x86/jre/lib/security/local_policy.jar    |   Bin 0 -> 3527 bytes
 .../win32.x86/jre/lib/security/trusted.libraries   |     0
 .../win32.x86/jre/lib/sound.properties             |    39 +
 .../org.xtuml.bp.jre/win32.x86/jre/lib/tzdb.dat    |   Bin 0 -> 101371 bytes
 .../org.xtuml.bp.jre/win32.x86/jre/lib/tzmappings  |   202 +
 features/org.xtuml.bp.jre/win32.x86/jre/release    |     6 +
 .../BridgePointDeliverables/MinGW/TECHPUB.CSS      |   571 -
 .../MinGW/bin/addr2line.exe                        |   Bin 559577 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/ar.exe       |   Bin 525951 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/as.exe       |   Bin 792360 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/c++.exe      |   Bin 92160 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/c++filt.exe  |   Bin 558024 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/cpp.exe      |   Bin 91648 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/dlltool.exe  |   Bin 611153 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/dllwrap.exe  |   Bin 63213 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/g++.exe      |   Bin 92160 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/gcc.exe      |   Bin 89600 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/gccbug       |   555 -
 .../BridgePointDeliverables/MinGW/bin/gcov.exe     |   Bin 26112 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/gdb.exe      |   Bin 3598848 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/gprof.exe    |   Bin 625174 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/ld.exe       |   Bin 806237 -> 0 bytes
 .../MinGW/bin/libexpat-1.dll                       |   Bin 165513 -> 0 bytes
 .../MinGW/bin/mingw32-c++.exe                      |   Bin 92160 -> 0 bytes
 .../MinGW/bin/mingw32-g++.exe                      |   Bin 92160 -> 0 bytes
 .../MinGW/bin/mingw32-gcc-3.4.5                    |   Bin 89600 -> 0 bytes
 .../MinGW/bin/mingw32-gcc.exe                      |   Bin 89600 -> 0 bytes
 .../MinGW/bin/mingw32-make.exe                     |   Bin 166400 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/mingwm10.dll |   Bin 15964 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/nm.exe       |   Bin 570469 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/objcopy.exe  |   Bin 717083 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/objdump.exe  |   Bin 849949 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/ranlib.exe   |   Bin 525951 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/readelf.exe  |   Bin 268447 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/rm.exe       |   Bin 65536 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/size.exe     |   Bin 508399 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/strings.exe  |   Bin 507802 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/strip.exe    |   Bin 717083 -> 0 bytes
 .../BridgePointDeliverables/MinGW/bin/windres.exe  |   Bin 617067 -> 0 bytes
 .../MinGW/doc/mingw-runtime/CONTRIBUTORS           |    28 -
 .../MinGW/doc/mingw-runtime/DISCLAIMER             |    12 -
 .../MinGW/doc/mingw-runtime/README                 |    10 -
 .../MinGW/doc/mingw32-make/COPYING                 |   340 -
 .../MinGW/doc/mingw32-make/README.mingw            |    14 -
 .../BridgePointDeliverables/MinGW/include/GL/gl.h  |  1470 -
 .../MinGW/include/GL/glext.h                       |  4623 ----
 .../BridgePointDeliverables/MinGW/include/GL/glu.h |   289 -
 .../BridgePointDeliverables/MinGW/include/_mingw.h |   195 -
 .../MinGW/include/accctrl.h                        |   329 -
 .../BridgePointDeliverables/MinGW/include/aclapi.h |   117 -
 .../BridgePointDeliverables/MinGW/include/aclui.h  |   134 -
 .../MinGW/include/adsprop.h                        |    59 -
 .../BridgePointDeliverables/MinGW/include/afxres.h |    23 -
 .../MinGW/include/amaudio.h                        |    33 -
 .../MinGW/include/amvideo.h                        |   179 -
 .../MinGW/include/ansidecl.h                       |   371 -
 .../BridgePointDeliverables/MinGW/include/assert.h |    51 -
 .../MinGW/include/audevcod.h                       |    31 -
 .../MinGW/include/aviriff.h                        |    87 -
 .../MinGW/include/aygshell.h                       |    49 -
 .../MinGW/include/basetsd.h                        |   119 -
 .../MinGW/include/basetyps.h                       |   182 -
 .../MinGW/include/bdatypes.h                       |    32 -
 .../BridgePointDeliverables/MinGW/include/bfd.h    |  5206 ----
 .../MinGW/include/bfdlink.h                        |   727 -
 .../MinGW/include/c++/3.4.5/algorithm              |    71 -
 .../MinGW/include/c++/3.4.5/backward/algo.h        |   149 -
 .../MinGW/include/c++/3.4.5/backward/algobase.h    |    95 -
 .../MinGW/include/c++/3.4.5/backward/alloc.h       |    52 -
 .../include/c++/3.4.5/backward/backward_warning.h  |    39 -
 .../MinGW/include/c++/3.4.5/backward/bvector.h     |    68 -
 .../MinGW/include/c++/3.4.5/backward/complex.h     |    43 -
 .../MinGW/include/c++/3.4.5/backward/defalloc.h    |   117 -
 .../MinGW/include/c++/3.4.5/backward/deque.h       |    70 -
 .../MinGW/include/c++/3.4.5/backward/fstream.h     |    52 -
 .../MinGW/include/c++/3.4.5/backward/function.h    |   130 -
 .../MinGW/include/c++/3.4.5/backward/hash_map.h    |    72 -
 .../MinGW/include/c++/3.4.5/backward/hash_set.h    |    69 -
 .../MinGW/include/c++/3.4.5/backward/hashtable.h   |    76 -
 .../MinGW/include/c++/3.4.5/backward/heap.h        |    71 -
 .../MinGW/include/c++/3.4.5/backward/iomanip.h     |    70 -
 .../MinGW/include/c++/3.4.5/backward/iostream.h    |    60 -
 .../MinGW/include/c++/3.4.5/backward/istream.h     |    43 -
 .../MinGW/include/c++/3.4.5/backward/iterator.h    |   191 -
 .../MinGW/include/c++/3.4.5/backward/list.h        |    70 -
 .../MinGW/include/c++/3.4.5/backward/map.h         |    69 -
 .../MinGW/include/c++/3.4.5/backward/multimap.h    |    69 -
 .../MinGW/include/c++/3.4.5/backward/multiset.h    |    69 -
 .../MinGW/include/c++/3.4.5/backward/new.h         |    42 -
 .../MinGW/include/c++/3.4.5/backward/ostream.h     |    38 -
 .../MinGW/include/c++/3.4.5/backward/pair.h        |    70 -
 .../MinGW/include/c++/3.4.5/backward/queue.h       |    41 -
 .../MinGW/include/c++/3.4.5/backward/rope.h        |    60 -
 .../MinGW/include/c++/3.4.5/backward/set.h         |    69 -
 .../MinGW/include/c++/3.4.5/backward/slist.h       |    56 -
 .../MinGW/include/c++/3.4.5/backward/stack.h       |    72 -
 .../MinGW/include/c++/3.4.5/backward/stream.h      |    38 -
 .../MinGW/include/c++/3.4.5/backward/streambuf.h   |    40 -
 .../MinGW/include/c++/3.4.5/backward/strstream     |   179 -
 .../MinGW/include/c++/3.4.5/backward/tempbuf.h     |    78 -
 .../MinGW/include/c++/3.4.5/backward/tree.h        |    55 -
 .../MinGW/include/c++/3.4.5/backward/vector.h      |    70 -
 .../MinGW/include/c++/3.4.5/bits/allocator.h       |   130 -
 .../MinGW/include/c++/3.4.5/bits/atomicity.h       |    46 -
 .../MinGW/include/c++/3.4.5/bits/basic_ios.h       |   467 -
 .../MinGW/include/c++/3.4.5/bits/basic_ios.tcc     |   200 -
 .../MinGW/include/c++/3.4.5/bits/basic_string.h    |  2365 --
 .../MinGW/include/c++/3.4.5/bits/basic_string.tcc  |   975 -
 .../include/c++/3.4.5/bits/boost_concept_check.h   |   932 -
 .../MinGW/include/c++/3.4.5/bits/char_traits.h     |   376 -
 .../MinGW/include/c++/3.4.5/bits/cmath.tcc         |    54 -
 .../MinGW/include/c++/3.4.5/bits/codecvt.h         |   478 -
 .../MinGW/include/c++/3.4.5/bits/concept_check.h   |    85 -
 .../MinGW/include/c++/3.4.5/bits/concurrence.h     |    95 -
 .../MinGW/include/c++/3.4.5/bits/cpp_type_traits.h |   345 -
 .../MinGW/include/c++/3.4.5/bits/deque.tcc         |   719 -
 .../MinGW/include/c++/3.4.5/bits/fstream.tcc       |   891 -
 .../MinGW/include/c++/3.4.5/bits/functexcept.h     |    85 -
 .../MinGW/include/c++/3.4.5/bits/gslice.h          |   165 -
 .../MinGW/include/c++/3.4.5/bits/gslice_array.h    |   220 -
 .../MinGW/include/c++/3.4.5/bits/indirect_array.h  |   212 -
 .../MinGW/include/c++/3.4.5/bits/ios_base.h        |   969 -
 .../MinGW/include/c++/3.4.5/bits/istream.tcc       |  1192 -
 .../MinGW/include/c++/3.4.5/bits/list.tcc          |   377 -
 .../MinGW/include/c++/3.4.5/bits/locale_classes.h  |   599 -
 .../MinGW/include/c++/3.4.5/bits/locale_facets.h   |  4558 ----
 .../MinGW/include/c++/3.4.5/bits/locale_facets.tcc |  2797 --
 .../MinGW/include/c++/3.4.5/bits/localefwd.h       |   192 -
 .../MinGW/include/c++/3.4.5/bits/mask_array.h      |   209 -
 .../MinGW/include/c++/3.4.5/bits/ostream.tcc       |   699 -
 .../MinGW/include/c++/3.4.5/bits/postypes.h        |   215 -
 .../MinGW/include/c++/3.4.5/bits/slice_array.h     |   273 -
 .../MinGW/include/c++/3.4.5/bits/sstream.tcc       |   227 -
 .../MinGW/include/c++/3.4.5/bits/stl_algo.h        |  5148 ----
 .../MinGW/include/c++/3.4.5/bits/stl_algobase.h    |   842 -
 .../MinGW/include/c++/3.4.5/bits/stl_bvector.h     |   876 -
 .../MinGW/include/c++/3.4.5/bits/stl_construct.h   |   157 -
 .../MinGW/include/c++/3.4.5/bits/stl_deque.h       |  1501 -
 .../MinGW/include/c++/3.4.5/bits/stl_function.h    |   898 -
 .../MinGW/include/c++/3.4.5/bits/stl_heap.h        |   467 -
 .../MinGW/include/c++/3.4.5/bits/stl_iterator.h    |   772 -
 .../c++/3.4.5/bits/stl_iterator_base_funcs.h       |   179 -
 .../c++/3.4.5/bits/stl_iterator_base_types.h       |   170 -
 .../MinGW/include/c++/3.4.5/bits/stl_list.h        |  1255 -
 .../MinGW/include/c++/3.4.5/bits/stl_map.h         |   694 -
 .../MinGW/include/c++/3.4.5/bits/stl_multimap.h    |   677 -
 .../MinGW/include/c++/3.4.5/bits/stl_multiset.h    |   585 -
 .../MinGW/include/c++/3.4.5/bits/stl_numeric.h     |   326 -
 .../MinGW/include/c++/3.4.5/bits/stl_pair.h        |   147 -
 .../MinGW/include/c++/3.4.5/bits/stl_queue.h       |   472 -
 .../include/c++/3.4.5/bits/stl_raw_storage_iter.h  |   113 -
 .../MinGW/include/c++/3.4.5/bits/stl_relops.h      |   137 -
 .../MinGW/include/c++/3.4.5/bits/stl_set.h         |   593 -
 .../MinGW/include/c++/3.4.5/bits/stl_stack.h       |   272 -
 .../MinGW/include/c++/3.4.5/bits/stl_tempbuf.h     |   171 -
 .../MinGW/include/c++/3.4.5/bits/stl_threads.h     |   150 -
 .../MinGW/include/c++/3.4.5/bits/stl_tree.h        |  1283 -
 .../include/c++/3.4.5/bits/stl_uninitialized.h     |   297 -
 .../MinGW/include/c++/3.4.5/bits/stl_vector.h      |   932 -
 .../MinGW/include/c++/3.4.5/bits/stream_iterator.h |   214 -
 .../MinGW/include/c++/3.4.5/bits/streambuf.tcc     |   163 -
 .../include/c++/3.4.5/bits/streambuf_iterator.h    |   258 -
 .../MinGW/include/c++/3.4.5/bits/stringfwd.h       |    69 -
 .../MinGW/include/c++/3.4.5/bits/type_traits.h     |   405 -
 .../MinGW/include/c++/3.4.5/bits/valarray_after.h  |   499 -
 .../MinGW/include/c++/3.4.5/bits/valarray_array.h  |   625 -
 .../include/c++/3.4.5/bits/valarray_array.tcc      |   240 -
 .../MinGW/include/c++/3.4.5/bits/valarray_before.h |   701 -
 .../MinGW/include/c++/3.4.5/bits/vector.tcc        |   414 -
 .../MinGW/include/c++/3.4.5/bitset                 |  1229 -
 .../MinGW/include/c++/3.4.5/cassert                |    48 -
 .../MinGW/include/c++/3.4.5/cctype                 |    83 -
 .../MinGW/include/c++/3.4.5/cerrno                 |    55 -
 .../MinGW/include/c++/3.4.5/cfloat                 |    50 -
 .../MinGW/include/c++/3.4.5/ciso646                |    37 -
 .../MinGW/include/c++/3.4.5/climits                |    51 -
 .../MinGW/include/c++/3.4.5/clocale                |    62 -
 .../MinGW/include/c++/3.4.5/cmath                  |   595 -
 .../MinGW/include/c++/3.4.5/complex                |  1226 -
 .../MinGW/include/c++/3.4.5/csetjmp                |    65 -
 .../MinGW/include/c++/3.4.5/csignal                |    61 -
 .../MinGW/include/c++/3.4.5/cstdarg                |    60 -
 .../MinGW/include/c++/3.4.5/cstddef                |    56 -
 .../MinGW/include/c++/3.4.5/cstdio                 |   185 -
 .../MinGW/include/c++/3.4.5/cstdlib                |   204 -
 .../MinGW/include/c++/3.4.5/cstring                |   128 -
 .../MinGW/include/c++/3.4.5/ctime                  |    81 -
 .../MinGW/include/c++/3.4.5/cwchar                 |   273 -
 .../MinGW/include/c++/3.4.5/cwctype                |   110 -
 .../MinGW/include/c++/3.4.5/cxxabi.h               |   528 -
 .../MinGW/include/c++/3.4.5/debug/bitset           |   299 -
 .../MinGW/include/c++/3.4.5/debug/debug.h          |   531 -
 .../MinGW/include/c++/3.4.5/debug/deque            |   386 -
 .../MinGW/include/c++/3.4.5/debug/formatter.h      |   391 -
 .../MinGW/include/c++/3.4.5/debug/hash_map         |    38 -
 .../MinGW/include/c++/3.4.5/debug/hash_map.h       |   270 -
 .../MinGW/include/c++/3.4.5/debug/hash_multimap.h  |   261 -
 .../MinGW/include/c++/3.4.5/debug/hash_multiset.h  |   236 -
 .../MinGW/include/c++/3.4.5/debug/hash_set         |    38 -
 .../MinGW/include/c++/3.4.5/debug/hash_set.h       |   245 -
 .../MinGW/include/c++/3.4.5/debug/list             |   505 -
 .../MinGW/include/c++/3.4.5/debug/map              |    38 -
 .../MinGW/include/c++/3.4.5/debug/map.h            |   323 -
 .../MinGW/include/c++/3.4.5/debug/multimap.h       |   314 -
 .../MinGW/include/c++/3.4.5/debug/multiset.h       |   320 -
 .../MinGW/include/c++/3.4.5/debug/safe_base.h      |   207 -
 .../MinGW/include/c++/3.4.5/debug/safe_iterator.h  |   618 -
 .../include/c++/3.4.5/debug/safe_iterator.tcc      |   140 -
 .../MinGW/include/c++/3.4.5/debug/safe_sequence.h  |   180 -
 .../MinGW/include/c++/3.4.5/debug/set              |    38 -
 .../MinGW/include/c++/3.4.5/debug/set.h            |   325 -
 .../MinGW/include/c++/3.4.5/debug/string           |  1001 -
 .../MinGW/include/c++/3.4.5/debug/vector           |   412 -
 .../MinGW/include/c++/3.4.5/deque                  |    81 -
 .../MinGW/include/c++/3.4.5/exception              |   120 -
 .../MinGW/include/c++/3.4.5/exception_defines.h    |    47 -
 .../MinGW/include/c++/3.4.5/ext/algorithm          |   518 -
 .../MinGW/include/c++/3.4.5/ext/bitmap_allocator.h |   859 -
 .../MinGW/include/c++/3.4.5/ext/debug_allocator.h  |   121 -
 .../MinGW/include/c++/3.4.5/ext/enc_filebuf.h      |    68 -
 .../MinGW/include/c++/3.4.5/ext/functional         |   395 -
 .../MinGW/include/c++/3.4.5/ext/hash_fun.h         |   122 -
 .../MinGW/include/c++/3.4.5/ext/hash_map           |   447 -
 .../MinGW/include/c++/3.4.5/ext/hash_set           |   439 -
 .../MinGW/include/c++/3.4.5/ext/hashtable.h        |   994 -
 .../MinGW/include/c++/3.4.5/ext/iterator           |   113 -
 .../MinGW/include/c++/3.4.5/ext/malloc_allocator.h |   118 -
 .../MinGW/include/c++/3.4.5/ext/memory             |   171 -
 .../MinGW/include/c++/3.4.5/ext/mt_allocator.h     |   718 -
 .../MinGW/include/c++/3.4.5/ext/new_allocator.h    |   113 -
 .../MinGW/include/c++/3.4.5/ext/numeric            |   148 -
 .../MinGW/include/c++/3.4.5/ext/pod_char_traits.h  |   158 -
 .../MinGW/include/c++/3.4.5/ext/pool_allocator.h   |   255 -
 .../MinGW/include/c++/3.4.5/ext/rb_tree            |    97 -
 .../MinGW/include/c++/3.4.5/ext/rope               |  2494 --
 .../MinGW/include/c++/3.4.5/ext/ropeimpl.h         |  1539 --
 .../MinGW/include/c++/3.4.5/ext/slist              |   906 -
 .../MinGW/include/c++/3.4.5/ext/stdio_filebuf.h    |   162 -
 .../include/c++/3.4.5/ext/stdio_sync_filebuf.h     |   281 -
 .../MinGW/include/c++/3.4.5/fstream                |   843 -
 .../MinGW/include/c++/3.4.5/functional             |    58 -
 .../MinGW/include/c++/3.4.5/iomanip                |   300 -
 .../MinGW/include/c++/3.4.5/ios                    |    53 -
 .../MinGW/include/c++/3.4.5/iosfwd                 |   168 -
 .../MinGW/include/c++/3.4.5/iostream               |    80 -
 .../MinGW/include/c++/3.4.5/istream                |   774 -
 .../MinGW/include/c++/3.4.5/iterator               |    76 -
 .../MinGW/include/c++/3.4.5/limits                 |  1143 -
 .../MinGW/include/c++/3.4.5/list                   |    82 -
 .../MinGW/include/c++/3.4.5/locale                 |    49 -
 .../MinGW/include/c++/3.4.5/map                    |    74 -
 .../MinGW/include/c++/3.4.5/memory                 |   375 -
 .../include/c++/3.4.5/mingw32/bits/atomic_word.h   |    35 -
 .../include/c++/3.4.5/mingw32/bits/basic_file.h    |   110 -
 .../include/c++/3.4.5/mingw32/bits/c++allocator.h  |    37 -
 .../include/c++/3.4.5/mingw32/bits/c++config.h     |  1297 -
 .../MinGW/include/c++/3.4.5/mingw32/bits/c++io.h   |    86 -
 .../include/c++/3.4.5/mingw32/bits/c++locale.h     |    86 -
 .../3.4.5/mingw32/bits/codecvt_specializations.h   |    38 -
 .../include/c++/3.4.5/mingw32/bits/ctype_base.h    |    58 -
 .../include/c++/3.4.5/mingw32/bits/ctype_inline.h  |    71 -
 .../c++/3.4.5/mingw32/bits/ctype_noninline.h       |    99 -
 .../include/c++/3.4.5/mingw32/bits/gthr-default.h  |   621 -
 .../include/c++/3.4.5/mingw32/bits/gthr-posix.h    |   517 -
 .../include/c++/3.4.5/mingw32/bits/gthr-single.h   |   239 -
 .../MinGW/include/c++/3.4.5/mingw32/bits/gthr.h    |   103 -
 .../c++/3.4.5/mingw32/bits/messages_members.h      |    84 -
 .../include/c++/3.4.5/mingw32/bits/os_defines.h    |    55 -
 .../include/c++/3.4.5/mingw32/bits/time_members.h  |    71 -
 .../MinGW/include/c++/3.4.5/new                    |   101 -
 .../MinGW/include/c++/3.4.5/numeric                |    72 -
 .../MinGW/include/c++/3.4.5/ostream                |   548 -
 .../MinGW/include/c++/3.4.5/queue                  |    78 -
 .../MinGW/include/c++/3.4.5/set                    |    74 -
 .../MinGW/include/c++/3.4.5/sstream                |   643 -
 .../MinGW/include/c++/3.4.5/stack                  |    73 -
 .../MinGW/include/c++/3.4.5/stdexcept              |   148 -
 .../MinGW/include/c++/3.4.5/streambuf              |   784 -
 .../MinGW/include/c++/3.4.5/string                 |    60 -
 .../MinGW/include/c++/3.4.5/typeinfo               |   156 -
 .../MinGW/include/c++/3.4.5/utility                |    70 -
 .../MinGW/include/c++/3.4.5/valarray               |  1017 -
 .../MinGW/include/c++/3.4.5/vector                 |    83 -
 .../BridgePointDeliverables/MinGW/include/cderr.h  |    44 -
 .../BridgePointDeliverables/MinGW/include/cguid.h  |    83 -
 .../MinGW/include/cmnquery.h                       |    81 -
 .../BridgePointDeliverables/MinGW/include/comcat.h |   175 -
 .../MinGW/include/commctrl.h                       |  3742 ---
 .../MinGW/include/commdlg.h                        |   609 -
 .../MinGW/include/complex.h                        |   205 -
 .../BridgePointDeliverables/MinGW/include/conio.h  |    53 -
 .../MinGW/include/control.h                        |    18 -
 .../BridgePointDeliverables/MinGW/include/cpl.h    |    61 -
 .../BridgePointDeliverables/MinGW/include/cplext.h |    12 -
 .../BridgePointDeliverables/MinGW/include/ctype.h  |   275 -
 .../MinGW/include/custcntl.h                       |   102 -
 .../BridgePointDeliverables/MinGW/include/d3d9.h   |  1288 -
 .../MinGW/include/d3d9caps.h                       |   338 -
 .../MinGW/include/d3d9types.h                      |  1272 -
 .../BridgePointDeliverables/MinGW/include/dbt.h    |   154 -
 .../BridgePointDeliverables/MinGW/include/dde.h    |    64 -
 .../BridgePointDeliverables/MinGW/include/ddeml.h  |   314 -
 .../MinGW/include/ddk/atm.h                        |   507 -
 .../MinGW/include/ddk/batclass.h                   |   298 -
 .../MinGW/include/ddk/cfg.h                        |   139 -
 .../MinGW/include/ddk/cfgmgr32.h                   |  1533 --
 .../MinGW/include/ddk/d4drvif.h                    |   104 -
 .../MinGW/include/ddk/d4iface.h                    |    84 -
 .../MinGW/include/ddk/ddkmapi.h                    |   334 -
 .../MinGW/include/ddk/hidclass.h                   |   153 -
 .../MinGW/include/ddk/hidpi.h                      |   604 -
 .../MinGW/include/ddk/hidsdi.h                     |    73 -
 .../MinGW/include/ddk/hidusage.h                   |   210 -
 .../MinGW/include/ddk/kbdmou.h                     |    91 -
 .../MinGW/include/ddk/mcd.h                        |   143 -
 .../MinGW/include/ddk/miniport.h                   |    77 -
 .../MinGW/include/ddk/minitape.h                   |   223 -
 .../MinGW/include/ddk/mountdev.h                   |    79 -
 .../MinGW/include/ddk/mountmgr.h                   |   139 -
 .../MinGW/include/ddk/ndis.h                       |  5227 ----
 .../MinGW/include/ddk/ndisguid.h                   |   439 -
 .../MinGW/include/ddk/ndistapi.h                   |  1308 -
 .../MinGW/include/ddk/ndiswan.h                    |   251 -
 .../MinGW/include/ddk/netevent.h                   |    42 -
 .../MinGW/include/ddk/netpnp.h                     |    69 -
 .../MinGW/include/ddk/newdev.h                     |    66 -
 .../MinGW/include/ddk/ntapi.h                      |  2906 --
 .../MinGW/include/ddk/ntdd8042.h                   |   213 -
 .../MinGW/include/ddk/ntddbeep.h                   |    54 -
 .../MinGW/include/ddk/ntddcdrm.h                   |   347 -
 .../MinGW/include/ddk/ntddcdvd.h                   |   213 -
 .../MinGW/include/ddk/ntddchgr.h                   |   353 -
 .../MinGW/include/ddk/ntdddisk.h                   |   521 -
 .../MinGW/include/ddk/ntddk.h                      |    91 -
 .../MinGW/include/ddk/ntddkbd.h                    |   135 -
 .../MinGW/include/ddk/ntddmou.h                    |   115 -
 .../MinGW/include/ddk/ntddndis.h                   |   188 -
 .../MinGW/include/ddk/ntddpar.h                    |   119 -
 .../MinGW/include/ddk/ntddpcm.h                    |   165 -
 .../MinGW/include/ddk/ntddscsi.h                   |   171 -
 .../MinGW/include/ddk/ntddser.h                    |   449 -
 .../MinGW/include/ddk/ntddstor.h                   |   333 -
 .../MinGW/include/ddk/ntddtape.h                   |    79 -
 .../MinGW/include/ddk/ntddtdi.h                    |    61 -
 .../MinGW/include/ddk/ntddvdeo.h                   |   440 -
 .../MinGW/include/ddk/ntddvol.h                    |   141 -
 .../MinGW/include/ddk/ntifs.h                      |  4726 ----
 .../MinGW/include/ddk/ntpoapi.h                    |   229 -
 .../MinGW/include/ddk/ntstatus.h                   |  1105 -
 .../MinGW/include/ddk/parallel.h                   |   277 -
 .../MinGW/include/ddk/pfhook.h                     |    76 -
 .../MinGW/include/ddk/poclass.h                    |   118 -
 .../MinGW/include/ddk/scsi.h                       |  1694 --
 .../MinGW/include/ddk/scsiscan.h                   |   130 -
 .../MinGW/include/ddk/scsiwmi.h                    |   215 -
 .../MinGW/include/ddk/smbus.h                      |   190 -
 .../MinGW/include/ddk/srb.h                        |   753 -
 .../MinGW/include/ddk/storport.h                   |   422 -
 .../MinGW/include/ddk/tdi.h                        |   593 -
 .../MinGW/include/ddk/tdiinfo.h                    |   110 -
 .../MinGW/include/ddk/tdikrnl.h                    |  1162 -
 .../MinGW/include/ddk/tdistat.h                    |    83 -
 .../MinGW/include/ddk/tvout.h                      |   116 -
 .../MinGW/include/ddk/upssvc.h                     |    94 -
 .../MinGW/include/ddk/usb.h                        |   471 -
 .../MinGW/include/ddk/usb100.h                     |   237 -
 .../MinGW/include/ddk/usbcamdi.h                   |   404 -
 .../MinGW/include/ddk/usbdi.h                      |   407 -
 .../MinGW/include/ddk/usbioctl.h                   |   353 -
 .../MinGW/include/ddk/usbiodef.h                   |   106 -
 .../MinGW/include/ddk/usbscan.h                    |   158 -
 .../MinGW/include/ddk/usbuser.h                    |   328 -
 .../MinGW/include/ddk/video.h                      |  1566 --
 .../MinGW/include/ddk/videoagp.h                   |   129 -
 .../MinGW/include/ddk/win2k.h                      |   106 -
 .../MinGW/include/ddk/winddi.h                     |  4258 ---
 .../MinGW/include/ddk/winddk.h                     |  9249 -------
 .../MinGW/include/ddk/winnt4.h                     |   623 -
 .../MinGW/include/ddk/winxp.h                      |    38 -
 .../MinGW/include/ddk/ws2san.h                     |   248 -
 .../MinGW/include/ddk/xfilter.h                    |   239 -
 .../MinGW/include/devguid.h                        |    65 -
 .../MinGW/include/dhcpcsdk.h                       |    42 -
 .../BridgePointDeliverables/MinGW/include/dir.h    |    26 -
 .../BridgePointDeliverables/MinGW/include/direct.h |    73 -
 .../BridgePointDeliverables/MinGW/include/dirent.h |   123 -
 .../MinGW/include/dis-asm.h                        |   339 -
 .../BridgePointDeliverables/MinGW/include/dlgs.h   |   186 -
 .../BridgePointDeliverables/MinGW/include/docobj.h |   147 -
 .../BridgePointDeliverables/MinGW/include/dos.h    |    89 -
 .../MinGW/include/dsadmin.h                        |    38 -
 .../MinGW/include/dsclient.h                       |   186 -
 .../MinGW/include/dsgetdc.h                        |    98 -
 .../BridgePointDeliverables/MinGW/include/dshow.h  |   108 -
 .../MinGW/include/dsquery.h                        |    72 -
 .../BridgePointDeliverables/MinGW/include/dsrole.h |    75 -
 .../MinGW/include/dvdevcod.h                       |    76 -
 .../MinGW/include/dvdmedia.h                       |   101 -
 .../BridgePointDeliverables/MinGW/include/dxerr8.h |    53 -
 .../BridgePointDeliverables/MinGW/include/dxerr9.h |    53 -
 .../MinGW/include/edevdefs.h                       |   346 -
 .../BridgePointDeliverables/MinGW/include/errno.h  |   101 -
 .../MinGW/include/errorrep.h                       |    40 -
 .../BridgePointDeliverables/MinGW/include/errors.h |   169 -
 .../BridgePointDeliverables/MinGW/include/evcode.h |    68 -
 .../BridgePointDeliverables/MinGW/include/excpt.h  |   102 -
 .../BridgePointDeliverables/MinGW/include/exdisp.h |   225 -
 .../MinGW/include/exdispid.h                       |    14 -
 .../BridgePointDeliverables/MinGW/include/fcntl.h  |    75 -
 .../BridgePointDeliverables/MinGW/include/fenv.h   |   107 -
 .../BridgePointDeliverables/MinGW/include/float.h  |   152 -
 .../MinGW/include/fltdefs.h                        |    50 -
 .../BridgePointDeliverables/MinGW/include/getopt.h |    84 -
 .../BridgePointDeliverables/MinGW/include/gmon.h   |   181 -
 .../MinGW/include/httpext.h                        |    98 -
 .../BridgePointDeliverables/MinGW/include/icm.h    |   399 -
 .../MinGW/include/idispids.h                       |    10 -
 .../MinGW/include/il21dec.h                        |    35 -
 .../MinGW/include/imagehlp.h                       |   329 -
 .../BridgePointDeliverables/MinGW/include/imm.h    |   452 -
 .../MinGW/include/initguid.h                       |    12 -
 .../MinGW/include/intshcut.h                       |    82 -
 .../MinGW/include/inttypes.h                       |   278 -
 .../BridgePointDeliverables/MinGW/include/io.h     |   342 -
 .../MinGW/include/ipexport.h                       |    88 -
 .../MinGW/include/iphlpapi.h                       |    74 -
 .../MinGW/include/ipifcons.h                       |   199 -
 .../MinGW/include/ipinfoid.h                       |    43 -
 .../MinGW/include/iprtrmib.h                       |   209 -
 .../MinGW/include/iptypes.h                        |   229 -
 .../MinGW/include/ipxconst.h                       |    36 -
 .../MinGW/include/ipxrtdef.h                       |    58 -
 .../MinGW/include/ipxtfflt.h                       |    38 -
 .../MinGW/include/isguids.h                        |    15 -
 .../BridgePointDeliverables/MinGW/include/ks.h     |    20 -
 .../MinGW/include/ksmedia.h                        |    23 -
 .../MinGW/include/largeint.h                       |   112 -
 .../BridgePointDeliverables/MinGW/include/libgen.h |    31 -
 .../BridgePointDeliverables/MinGW/include/limits.h |   112 -
 .../BridgePointDeliverables/MinGW/include/lm.h     |    27 -
 .../MinGW/include/lmaccess.h                       |   610 -
 .../MinGW/include/lmalert.h                        |    60 -
 .../MinGW/include/lmapibuf.h                       |    18 -
 .../BridgePointDeliverables/MinGW/include/lmat.h   |    39 -
 .../MinGW/include/lmaudit.h                        |   250 -
 .../MinGW/include/lmbrowsr.h                       |    74 -
 .../MinGW/include/lmchdev.h                        |    61 -
 .../MinGW/include/lmconfig.h                       |    21 -
 .../BridgePointDeliverables/MinGW/include/lmcons.h |    75 -
 .../BridgePointDeliverables/MinGW/include/lmerr.h  |   306 -
 .../MinGW/include/lmerrlog.h                       |   211 -
 .../BridgePointDeliverables/MinGW/include/lmmsg.h  |    27 -
 .../MinGW/include/lmremutl.h                       |    45 -
 .../BridgePointDeliverables/MinGW/include/lmrepl.h |    94 -
 .../MinGW/include/lmserver.h                       |   606 -
 .../MinGW/include/lmshare.h                        |   147 -
 .../MinGW/include/lmsname.h                        |    58 -
 .../MinGW/include/lmstats.h                        |   114 -
 .../BridgePointDeliverables/MinGW/include/lmsvc.h  |   134 -
 .../BridgePointDeliverables/MinGW/include/lmuse.h  |    60 -
 .../MinGW/include/lmuseflg.h                       |    10 -
 .../MinGW/include/lmwksta.h                        |   233 -
 .../BridgePointDeliverables/MinGW/include/locale.h |    88 -
 .../MinGW/include/lzexpand.h                       |    40 -
 .../BridgePointDeliverables/MinGW/include/malloc.h |   103 -
 .../BridgePointDeliverables/MinGW/include/mapi.h   |   163 -
 .../BridgePointDeliverables/MinGW/include/math.h   |   848 -
 .../MinGW/include/mbctype.h                        |    97 -
 .../MinGW/include/mbstring.h                       |   132 -
 .../BridgePointDeliverables/MinGW/include/mciavi.h |    25 -
 .../BridgePointDeliverables/MinGW/include/mcx.h    |    77 -
 .../BridgePointDeliverables/MinGW/include/mem.h    |     6 -
 .../BridgePointDeliverables/MinGW/include/memory.h |     7 -
 .../BridgePointDeliverables/MinGW/include/mgm.h    |    75 -
 .../MinGW/include/mgmtapi.h                        |    58 -
 .../BridgePointDeliverables/MinGW/include/mlang.h  |   349 -
 .../BridgePointDeliverables/MinGW/include/mmreg.h  |    71 -
 .../MinGW/include/mmsystem.h                       |  1943 --
 .../MinGW/include/mpegtype.h                       |    29 -
 .../BridgePointDeliverables/MinGW/include/mprapi.h |   572 -
 .../BridgePointDeliverables/MinGW/include/mq.h     |   534 -
 .../BridgePointDeliverables/MinGW/include/msacm.h  |   186 -
 .../BridgePointDeliverables/MinGW/include/mshtml.h |   704 -
 .../MinGW/include/mswsock.h                        |   113 -
 .../BridgePointDeliverables/MinGW/include/nb30.h   |   186 -
 .../MinGW/include/nddeapi.h                        |   133 -
 .../BridgePointDeliverables/MinGW/include/nspapi.h |   126 -
 .../BridgePointDeliverables/MinGW/include/ntdef.h  |    60 -
 .../BridgePointDeliverables/MinGW/include/ntdll.h  |    15 -
 .../MinGW/include/ntdsapi.h                        |   115 -
 .../MinGW/include/ntdsbcli.h                       |    45 -
 .../BridgePointDeliverables/MinGW/include/ntldap.h |    60 -
 .../MinGW/include/ntsecapi.h                       |   612 -
 .../MinGW/include/ntsecpkg.h                       |    44 -
 .../BridgePointDeliverables/MinGW/include/oaidl.h  |   778 -
 .../MinGW/include/objbase.h                        |   203 -
 .../BridgePointDeliverables/MinGW/include/objfwd.h |    57 -
 .../BridgePointDeliverables/MinGW/include/objidl.h |  1771 --
 .../MinGW/include/objsafe.h                        |    26 -
 .../BridgePointDeliverables/MinGW/include/objsel.h |   118 -
 .../BridgePointDeliverables/MinGW/include/ocidl.h  |   837 -
 .../MinGW/include/odbcinst.h                       |   148 -
 .../BridgePointDeliverables/MinGW/include/ole.h    |   308 -
 .../BridgePointDeliverables/MinGW/include/ole2.h   |   110 -
 .../MinGW/include/ole2ver.h                        |     8 -
 .../BridgePointDeliverables/MinGW/include/oleacc.h |   219 -
 .../MinGW/include/oleauto.h                        |   656 -
 .../BridgePointDeliverables/MinGW/include/olectl.h |   323 -
 .../MinGW/include/olectlid.h                       |   114 -
 .../BridgePointDeliverables/MinGW/include/oledlg.h |   935 -
 .../BridgePointDeliverables/MinGW/include/oleidl.h |   575 -
 .../BridgePointDeliverables/MinGW/include/pbt.h    |    24 -
 .../MinGW/include/poppack.h                        |     3 -
 .../MinGW/include/powrprof.h                       |   120 -
 .../MinGW/include/process.h                        |   138 -
 .../BridgePointDeliverables/MinGW/include/profil.h |    51 -
 .../MinGW/include/profile.h                        |    83 -
 .../BridgePointDeliverables/MinGW/include/prsht.h  |   304 -
 .../BridgePointDeliverables/MinGW/include/psapi.h  |    95 -
 .../MinGW/include/pshpack1.h                       |     3 -
 .../MinGW/include/pshpack2.h                       |     3 -
 .../MinGW/include/pshpack4.h                       |     3 -
 .../MinGW/include/pshpack8.h                       |     3 -
 .../BridgePointDeliverables/MinGW/include/qedit.h  |    70 -
 .../BridgePointDeliverables/MinGW/include/rapi.h   |    54 -
 .../BridgePointDeliverables/MinGW/include/ras.h    |   964 -
 .../BridgePointDeliverables/MinGW/include/rasdlg.h |   152 -
 .../MinGW/include/raserror.h                       |   210 -
 .../MinGW/include/rassapi.h                        |   182 -
 .../BridgePointDeliverables/MinGW/include/reason.h |    47 -
 .../BridgePointDeliverables/MinGW/include/regstr.h |   769 -
 .../MinGW/include/richedit.h                       |   523 -
 .../MinGW/include/richole.h                        |   107 -
 .../MinGW/include/routprot.h                       |    70 -
 .../BridgePointDeliverables/MinGW/include/rpc.h    |    65 -
 .../BridgePointDeliverables/MinGW/include/rpcdce.h |   396 -
 .../MinGW/include/rpcdce2.h                        |    56 -
 .../MinGW/include/rpcdcep.h                        |   129 -
 .../BridgePointDeliverables/MinGW/include/rpcndr.h |   518 -
 .../BridgePointDeliverables/MinGW/include/rpcnsi.h |   122 -
 .../MinGW/include/rpcnsip.h                        |    25 -
 .../MinGW/include/rpcnterr.h                       |    23 -
 .../MinGW/include/rpcproxy.h                       |   204 -
 .../MinGW/include/rtutils.h                        |    90 -
 .../MinGW/include/schannel.h                       |    90 -
 .../MinGW/include/schnlsp.h                        |    14 -
 .../MinGW/include/scrnsave.h                       |    81 -
 .../BridgePointDeliverables/MinGW/include/sddl.h   |    30 -
 .../BridgePointDeliverables/MinGW/include/search.h |   106 -
 .../BridgePointDeliverables/MinGW/include/secext.h |    52 -
 .../MinGW/include/security.h                       |    42 -
 .../MinGW/include/servprov.h                       |    33 -
 .../BridgePointDeliverables/MinGW/include/setjmp.h |    56 -
 .../MinGW/include/setupapi.h                       |  1590 --
 .../BridgePointDeliverables/MinGW/include/share.h  |    33 -
 .../MinGW/include/shellapi.h                       |   363 -
 .../MinGW/include/shldisp.h                        |    61 -
 .../MinGW/include/shlguid.h                        |   102 -
 .../BridgePointDeliverables/MinGW/include/shlobj.h |  1535 --
 .../MinGW/include/shlwapi.h                        |   712 -
 .../BridgePointDeliverables/MinGW/include/signal.h |    98 -
 .../BridgePointDeliverables/MinGW/include/snmp.h   |   259 -
 .../BridgePointDeliverables/MinGW/include/sql.h    |   408 -
 .../BridgePointDeliverables/MinGW/include/sqlext.h |  1218 -
 .../MinGW/include/sqltypes.h                       |   165 -
 .../MinGW/include/sqlucode.h                       |   142 -
 .../BridgePointDeliverables/MinGW/include/sspi.h   |   338 -
 .../BridgePointDeliverables/MinGW/include/stdint.h |   206 -
 .../BridgePointDeliverables/MinGW/include/stdio.h  |   524 -
 .../BridgePointDeliverables/MinGW/include/stdlib.h |   542 -
 .../BridgePointDeliverables/MinGW/include/stm.h    |    25 -
 .../BridgePointDeliverables/MinGW/include/string.h |   195 -
 .../MinGW/include/strings.h                        |    12 -
 .../BridgePointDeliverables/MinGW/include/strmif.h |  1159 -
 .../MinGW/include/subauth.h                        |   209 -
 .../MinGW/include/svcguid.h                        |    33 -
 .../BridgePointDeliverables/MinGW/include/symcat.h |    49 -
 .../MinGW/include/sys/fcntl.h                      |     7 -
 .../MinGW/include/sys/file.h                       |     7 -
 .../MinGW/include/sys/locking.h                    |    31 -
 .../MinGW/include/sys/param.h                      |    22 -
 .../MinGW/include/sys/stat.h                       |   196 -
 .../MinGW/include/sys/time.h                       |    47 -
 .../MinGW/include/sys/timeb.h                      |    74 -
 .../MinGW/include/sys/types.h                      |   120 -
 .../MinGW/include/sys/unistd.h                     |     6 -
 .../MinGW/include/sys/utime.h                      |    81 -
 .../BridgePointDeliverables/MinGW/include/tchar.h  |   420 -
 .../BridgePointDeliverables/MinGW/include/time.h   |   218 -
 .../MinGW/include/tlhelp32.h                       |   140 -
 .../MinGW/include/tmschema.h                       |   664 -
 .../BridgePointDeliverables/MinGW/include/unistd.h |    47 -
 .../BridgePointDeliverables/MinGW/include/unknwn.h |    76 -
 .../MinGW/include/userenv.h                        |    57 -
 .../BridgePointDeliverables/MinGW/include/usp10.h  |   229 -
 .../BridgePointDeliverables/MinGW/include/utime.h  |     1 -
 .../MinGW/include/uxtheme.h                        |   273 -
 .../BridgePointDeliverables/MinGW/include/values.h |     4 -
 .../MinGW/include/varargs.h                        |     7 -
 .../BridgePointDeliverables/MinGW/include/vfw.h    |  1144 -
 .../BridgePointDeliverables/MinGW/include/vidcap.h |    23 -
 .../BridgePointDeliverables/MinGW/include/vmr9.h   |   211 -
 .../BridgePointDeliverables/MinGW/include/vptype.h |    55 -
 .../BridgePointDeliverables/MinGW/include/w32api.h |    52 -
 .../BridgePointDeliverables/MinGW/include/wchar.h  |   510 -
 .../BridgePointDeliverables/MinGW/include/wctype.h |   168 -
 .../MinGW/include/winable.h                        |    99 -
 .../MinGW/include/winbase.h                        |  2435 --
 .../BridgePointDeliverables/MinGW/include/winber.h |    67 -
 .../BridgePointDeliverables/MinGW/include/wincon.h |   232 -
 .../MinGW/include/wincrypt.h                       |  1138 -
 .../BridgePointDeliverables/MinGW/include/windef.h |   337 -
 .../BridgePointDeliverables/MinGW/include/windns.h |   405 -
 .../MinGW/include/windows.h                        |   131 -
 .../MinGW/include/windowsx.h                       |   544 -
 .../MinGW/include/winerror.h                       |  2264 --
 .../BridgePointDeliverables/MinGW/include/wingdi.h |  3226 ---
 .../MinGW/include/wininet.h                        |   986 -
 .../MinGW/include/winioctl.h                       |   554 -
 .../MinGW/include/winldap.h                        |   713 -
 .../MinGW/include/winnetwk.h                       |   350 -
 .../BridgePointDeliverables/MinGW/include/winnls.h |   734 -
 .../BridgePointDeliverables/MinGW/include/winnt.h  |  3929 ---
 .../MinGW/include/winperf.h                        |   139 -
 .../BridgePointDeliverables/MinGW/include/winreg.h |   180 -
 .../MinGW/include/winresrc.h                       |    14 -
 .../MinGW/include/winsnmp.h                        |   329 -
 .../MinGW/include/winsock.h                        |   536 -
 .../MinGW/include/winsock2.h                       |  1303 -
 .../MinGW/include/winspool.h                       |   982 -
 .../BridgePointDeliverables/MinGW/include/winsvc.h |   313 -
 .../MinGW/include/winuser.h                        |  4411 ---
 .../BridgePointDeliverables/MinGW/include/winver.h |   133 -
 .../BridgePointDeliverables/MinGW/include/ws2spi.h |   202 -
 .../MinGW/include/ws2tcpip.h                       |   379 -
 .../MinGW/include/wsahelp.h                        |    98 -
 .../BridgePointDeliverables/MinGW/include/wsipx.h  |    28 -
 .../MinGW/include/wsnetbs.h                        |    35 -
 .../MinGW/include/wtsapi32.h                       |    62 -
 .../BridgePointDeliverables/MinGW/include/wtypes.h |   171 -
 .../MinGW/include/xprtdefs.h                       |    11 -
 .../BridgePointDeliverables/MinGW/include/zmouse.h |    36 -
 .../BridgePointDeliverables/MinGW/info/as.info     | 18833 -------------
 .../BridgePointDeliverables/MinGW/info/bfd.info    | 10438 -------
 .../MinGW/info/binutils.info                       |  3842 ---
 .../MinGW/info/configure.info                      |  2773 --
 .../BridgePointDeliverables/MinGW/info/cpp.info    |  5166 ----
 .../MinGW/info/cppinternals.info                   |  1035 -
 .../BridgePointDeliverables/MinGW/info/dir         |    50 -
 .../BridgePointDeliverables/MinGW/info/gcc.info    | 27178 -------------------
 .../MinGW/info/gccinstall.info                     |  3822 ---
 .../BridgePointDeliverables/MinGW/info/gccint.info | 27029 ------------------
 .../BridgePointDeliverables/MinGW/info/gprof.info  |  2313 --
 .../BridgePointDeliverables/MinGW/info/ld.info     |  6738 -----
 .../MinGW/info/standards.info                      |  4930 ----
 .../BridgePointDeliverables/MinGW/lib/CRT_fp10.o   |   Bin 368 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/CRT_fp8.o    |   Bin 412 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/CRT_noglob.o |   Bin 317 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/binmode.o    |   Bin 322 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/crt1.o       |   Bin 2192 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/crt2.o       |   Bin 2288 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/crtmt.o      |   Bin 322 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/crtst.o      |   Bin 306 -> 0 bytes
 .../MinGW/lib/debug/libstdc++.a                    |   Bin 6924998 -> 0 bytes
 .../MinGW/lib/debug/libstdc++.la                   |    32 -
 .../BridgePointDeliverables/MinGW/lib/dllcrt1.o    |   Bin 1239 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/dllcrt2.o    |   Bin 1239 -> 0 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/crtbegin.o         |   Bin 412 -> 0 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/crtend.o           |   Bin 492 -> 0 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/include/README     |    14 -
 .../lib/gcc/mingw32/3.4.5/include/emmintrin.h      |  1491 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/float.h    |   162 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/iso646.h   |    48 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/limits.h   |   125 -
 .../lib/gcc/mingw32/3.4.5/include/mm_malloc.h      |    77 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/mmintrin.h |   917 -
 .../gcc/mingw32/3.4.5/include/objc/NXConstStr.h    |    44 -
 .../lib/gcc/mingw32/3.4.5/include/objc/Object.h    |   124 -
 .../lib/gcc/mingw32/3.4.5/include/objc/Protocol.h  |    58 -
 .../lib/gcc/mingw32/3.4.5/include/objc/encoding.h  |    99 -
 .../lib/gcc/mingw32/3.4.5/include/objc/hash.h      |   207 -
 .../lib/gcc/mingw32/3.4.5/include/objc/objc-api.h  |   607 -
 .../lib/gcc/mingw32/3.4.5/include/objc/objc-list.h |   147 -
 .../lib/gcc/mingw32/3.4.5/include/objc/objc.h      |   165 -
 .../lib/gcc/mingw32/3.4.5/include/objc/sarray.h    |   237 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/objc/thr.h |   145 -
 .../gcc/mingw32/3.4.5/include/objc/typedstream.h   |   132 -
 .../lib/gcc/mingw32/3.4.5/include/pmmintrin.h      |   132 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/stdarg.h   |   135 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/stdbool.h  |    53 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/stddef.h   |   426 -
 .../lib/gcc/mingw32/3.4.5/include/syslimits.h      |     8 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/unwind.h   |   232 -
 .../MinGW/lib/gcc/mingw32/3.4.5/include/varargs.h  |     7 -
 .../lib/gcc/mingw32/3.4.5/include/xmmintrin.h      |  1222 -
 .../gcc/mingw32/3.4.5/install-tools/gsyslimits.h   |     8 -
 .../gcc/mingw32/3.4.5/install-tools/include/README |    14 -
 .../3.4.5/install-tools/include/emmintrin.h        |  1491 -
 .../mingw32/3.4.5/install-tools/include/float.h    |   162 -
 .../mingw32/3.4.5/install-tools/include/iso646.h   |    48 -
 .../mingw32/3.4.5/install-tools/include/limits.h   |   125 -
 .../3.4.5/install-tools/include/mm_malloc.h        |    77 -
 .../mingw32/3.4.5/install-tools/include/mmintrin.h |   917 -
 .../3.4.5/install-tools/include/pmmintrin.h        |   132 -
 .../mingw32/3.4.5/install-tools/include/stdarg.h   |   135 -
 .../mingw32/3.4.5/install-tools/include/stdbool.h  |    53 -
 .../mingw32/3.4.5/install-tools/include/stddef.h   |   426 -
 .../mingw32/3.4.5/install-tools/include/unwind.h   |   232 -
 .../mingw32/3.4.5/install-tools/include/varargs.h  |     7 -
 .../3.4.5/install-tools/include/xmmintrin.h        |  1222 -
 .../gcc/mingw32/3.4.5/install-tools/mkheaders.conf |     5 -
 .../MinGW/lib/gcc/mingw32/3.4.5/libgcc.a           |   Bin 52846 -> 0 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/libgcov.a          |   Bin 9772 -> 0 bytes
 .../MinGW/lib/gcc/mingw32/3.4.5/specs              |   127 -
 .../BridgePointDeliverables/MinGW/lib/gcrt1.o      |   Bin 692 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/gcrt2.o      |   Bin 692 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libaclui.a   |   Bin 3654 -> 0 bytes
 .../MinGW/lib/libadvapi32.a                        |   Bin 411648 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libapcups.a  |   Bin 5832 -> 0 bytes
 .../MinGW/lib/libavicap32.a                        |   Bin 6114 -> 0 bytes
 .../MinGW/lib/libavifil32.a                        |   Bin 56844 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libbfd.a     |   Bin 3550064 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libbfd.la    |    32 -
 .../BridgePointDeliverables/MinGW/lib/libcap.a     |   Bin 4254 -> 0 bytes
 .../MinGW/lib/libcfgmgr32.a                        |   Bin 62924 -> 0 bytes
 .../MinGW/lib/libcoldname.a                        |   Bin 80600 -> 0 bytes
 .../MinGW/lib/libcomctl32.a                        |   Bin 98306 -> 0 bytes
 .../MinGW/lib/libcomdlg32.a                        |   Bin 19752 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libcrtdll.a  |   Bin 357874 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libcrypt32.a |   Bin 27730 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libctl3d32.a |   Bin 20062 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3d8.a    |   Bin 4718 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3d9.a    |   Bin 6350 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3dim.a   |   Bin 6758 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3drm.a   |   Bin 17462 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3dx8d.a  |   Bin 162892 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3dx9d.a  |   Bin 211092 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libd3dxof.a  |   Bin 2248 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libddraw.a   |   Bin 11334 -> 0 bytes
 .../MinGW/lib/libdhcpcsvc.a                        |   Bin 6166 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdinput.a  |   Bin 18086 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdinput8.a |   Bin 16568 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdlcapi.a  |   Bin 3636 -> 0 bytes
 .../MinGW/lib/libdmoguids.a                        |   Bin 2342 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdnsapi.a  |   Bin 22992 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdplayx.a  |   Bin 6096 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdpnaddr.a |   Bin 2294 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdpnet.a   |   Bin 2246 -> 0 bytes
 .../MinGW/lib/libdpnlobby.a                        |   Bin 2296 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdpvoice.a |   Bin 2278 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdsetup.a  |   Bin 15412 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdsound.a  |   Bin 9260 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdxapi.a   |   Bin 1496 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdxerr8.a  |   Bin 160852 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdxerr9.a  |   Bin 194508 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libdxguid.a  |   Bin 50754 -> 0 bytes
 .../MinGW/lib/libfaultrep.a                        |   Bin 3816 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libgdi32.a   |   Bin 255422 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libglaux.a   |   Bin 126282 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libglu32.a   |   Bin 39872 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libglut.a    |   Bin 86446 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libglut32.a  |   Bin 86684 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libgmon.a    |   Bin 5360 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libhal.a     |   Bin 34142 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libhid.a     |   Bin 35186 -> 0 bytes
 .../MinGW/lib/libhidparse.a                        |   Bin 20282 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libiberty.a  |   Bin 589800 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libicmui.a   |   Bin 3012 -> 0 bytes
 .../MinGW/lib/libigmpagnt.a                        |   Bin 4542 -> 0 bytes
 .../MinGW/lib/libimagehlp.a                        |   Bin 86386 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libimm32.a   |   Bin 58756 -> 0 bytes
 .../MinGW/lib/libiphlpapi.a                        |   Bin 35738 -> 0 bytes
 .../MinGW/lib/libkernel32.a                        |   Bin 657818 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libksproxy.a |   Bin 6182 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libksuser.a  |   Bin 4476 -> 0 bytes
 .../MinGW/lib/liblargeint.a                        |   Bin 2548 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/liblz32.a    |   Bin 10006 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libm.a       |   Bin 458 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmapi32.a  |   Bin 121098 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmcd.a     |   Bin 5386 -> 0 bytes
 .../MinGW/lib/libmfcuia32.a                        |   Bin 8970 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmgmtapi.a |   Bin 8874 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmingw32.a |   Bin 7514 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmingwex.a |   Bin 267880 -> 0 bytes
 .../MinGW/lib/libmingwthrd.a                       |   Bin 3458 -> 0 bytes
 .../MinGW/lib/libmoldname.a                        |   Bin 82558 -> 0 bytes
 .../MinGW/lib/libmoldnamed.a                       |   Bin 82656 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmpr.a     |   Bin 54878 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmprapi.a  |   Bin 96220 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmqrt.a    |   Bin 30046 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsacm32.a |   Bin 34326 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmscms.a   |   Bin 42602 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsdmo.a   |   Bin 12454 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsimg32.a |   Bin 5162 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcp60.a |   Bin 8366 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcr70.a |   Bin 533872 -> 0 bytes
 .../MinGW/lib/libmsvcr70d.a                        |   Bin 534958 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcr71.a |   Bin 546712 -> 0 bytes
 .../MinGW/lib/libmsvcr71d.a                        |   Bin 547832 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcr80.a |   Bin 554136 -> 0 bytes
 .../MinGW/lib/libmsvcr80d.a                        |   Bin 555276 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcr90.a |   Bin 554136 -> 0 bytes
 .../MinGW/lib/libmsvcr90d.a                        |   Bin 555910 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcrt.a  |   Bin 503692 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvcrtd.a |   Bin 504146 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmsvfw32.a |   Bin 36118 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libmswsock.a |   Bin 20470 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libnddeapi.a |   Bin 22786 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libndis.a    |   Bin 130602 -> 0 bytes
 .../MinGW/lib/libnetapi32.a                        |   Bin 187092 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libnewdev.a  |   Bin 4836 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libntdll.a   |   Bin 764276 -> 0 bytes
 .../MinGW/lib/libntoskrnl.a                        |   Bin 539254 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libobjc.a    |   Bin 117904 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libobjc.la   |    32 -
 .../BridgePointDeliverables/MinGW/lib/libodbc32.a  |   Bin 136948 -> 0 bytes
 .../MinGW/lib/libodbccp32.a                        |   Bin 41294 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libole32.a   |   Bin 192226 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/liboleacc.a  |   Bin 13004 -> 0 bytes
 .../MinGW/lib/liboleaut32.a                        |   Bin 262040 -> 0 bytes
 .../MinGW/lib/libolecli32.a                        |   Bin 42380 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/liboledlg.a  |   Bin 18728 -> 0 bytes
 .../MinGW/lib/libolepro32.a                        |   Bin 6916 -> 0 bytes
 .../MinGW/lib/libolesvr32.a                        |   Bin 9800 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libopcodes.a |   Bin 360786 -> 0 bytes
 .../MinGW/lib/libopcodes.la                        |    32 -
 .../MinGW/lib/libopengl32.a                        |   Bin 269134 -> 0 bytes
 .../MinGW/lib/libpenwin32.a                        |   Bin 75528 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libpkpd32.a  |   Bin 27088 -> 0 bytes
 .../MinGW/lib/libpowrprof.a                        |   Bin 19072 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libpsapi.a   |   Bin 15978 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libquartz.a  |   Bin 4444 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librapi.a    |   Bin 61850 -> 0 bytes
 .../MinGW/lib/librasapi32.a                        |   Bin 112510 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librasdlg.a  |   Bin 5900 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librpcdce4.a |   Bin 19674 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librpcns4.a  |   Bin 46260 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librpcrt4.a  |   Bin 278240 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librtm.a     |   Bin 13778 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/librtutils.a |   Bin 40722 -> 0 bytes
 .../MinGW/lib/libscrnsave.a                        |   Bin 7110 -> 0 bytes
 .../MinGW/lib/libscrnsavw.a                        |   Bin 7254 -> 0 bytes
 .../MinGW/lib/libscsiport.a                        |   Bin 37802 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libsecur32.a |   Bin 31630 -> 0 bytes
 .../MinGW/lib/libsetupapi.a                        |   Bin 361106 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libshell32.a |   Bin 142490 -> 0 bytes
 .../MinGW/lib/libshfolder.a                        |   Bin 3014 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libshlwapi.a |   Bin 218782 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libsnmpapi.a |   Bin 30374 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libstdc++.a  |   Bin 1063794 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libstdc++.la |    32 -
 .../MinGW/lib/libstrmiids.a                        |   Bin 80834 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libsupc++.a  |   Bin 115964 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libsupc++.la |    32 -
 .../BridgePointDeliverables/MinGW/lib/libsvrapi.a  |   Bin 16292 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libtapi32.a  |   Bin 86704 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libtdi.a     |   Bin 28158 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libth32.a    |   Bin 10342 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libthunk32.a |   Bin 48894 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/liburl.a     |   Bin 6750 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libusbcamd.a |   Bin 7042 -> 0 bytes
 .../MinGW/lib/libusbcamd2.a                        |   Bin 7064 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libuser32.a  |   Bin 472330 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libuserenv.a |   Bin 8414 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libusp10.a   |   Bin 31908 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libuuid.a    |   Bin 43388 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libuxtheme.a |   Bin 37758 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libvdmdbg.a  |   Bin 13448 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libversion.a |   Bin 12042 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libvfw32.a   |   Bin 98930 -> 0 bytes
 .../MinGW/lib/libvideoprt.a                        |   Bin 89216 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwin32k.a  |   Bin 149756 -> 0 bytes
 .../MinGW/lib/libwin32spl.a                        |   Bin 11666 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwininet.a |   Bin 186898 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwinmm.a   |   Bin 144884 -> 0 bytes
 .../MinGW/lib/libwinspool.a                        |   Bin 99412 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwinstrm.a |   Bin 6410 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwldap32.a |   Bin 182062 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwow32.a   |   Bin 14138 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libws2_32.a  |   Bin 83372 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwsnmp32.a |   Bin 35262 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwsock32.a |   Bin 54478 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/libwst.a     |   Bin 2162 -> 0 bytes
 .../MinGW/lib/libwtsapi32.a                        |   Bin 26944 -> 0 bytes
 .../BridgePointDeliverables/MinGW/lib/txtmode.o    |   Bin 322 -> 0 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/cc1.exe        |   Bin 4424704 -> 0 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/cc1obj.exe     |   Bin 4488704 -> 0 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/cc1plus.exe    |   Bin 4901376 -> 0 bytes
 .../MinGW/libexec/gcc/mingw32/3.4.5/collect2.exe   |   Bin 87552 -> 0 bytes
 .../gcc/mingw32/3.4.5/install-tools/fixinc.sh      |     2 -
 .../gcc/mingw32/3.4.5/install-tools/mkheaders      |   105 -
 .../MinGW/man/man1/addr2line.1                     |   265 -
 .../BridgePointDeliverables/MinGW/man/man1/ar.1    |   390 -
 .../BridgePointDeliverables/MinGW/man/man1/as.1    |  1120 -
 .../MinGW/man/man1/c++filt.1                       |   345 -
 .../BridgePointDeliverables/MinGW/man/man1/cpp.1   |   918 -
 .../MinGW/man/man1/dlltool.1                       |   478 -
 .../BridgePointDeliverables/MinGW/man/man1/g++.1   | 10714 --------
 .../BridgePointDeliverables/MinGW/man/man1/gcc.1   | 10714 --------
 .../BridgePointDeliverables/MinGW/man/man1/gcov.1  |   607 -
 .../BridgePointDeliverables/MinGW/man/man1/gprof.1 |   742 -
 .../BridgePointDeliverables/MinGW/man/man1/ld.1    |  2103 --
 .../MinGW/man/man1/mingw32-make.1                  |   365 -
 .../MinGW/man/man1/nlmconv.1                       |   243 -
 .../BridgePointDeliverables/MinGW/man/man1/nm.1    |   449 -
 .../MinGW/man/man1/objcopy.1                       |   801 -
 .../MinGW/man/man1/objdump.1                       |   634 -
 .../MinGW/man/man1/ranlib.1                        |   188 -
 .../MinGW/man/man1/readelf.1                       |   376 -
 .../BridgePointDeliverables/MinGW/man/man1/size.1  |   263 -
 .../MinGW/man/man1/strings.1                       |   253 -
 .../BridgePointDeliverables/MinGW/man/man1/strip.1 |   383 -
 .../MinGW/man/man1/windres.1                       |   343 -
 .../MinGW/man/man3/basename.3                      |   474 -
 .../MinGW/man/man3/dirname.3                       |   474 -
 .../MinGW/man/man7/fsf-funding.7                   |   185 -
 .../BridgePointDeliverables/MinGW/man/man7/gfdl.7  |   561 -
 .../BridgePointDeliverables/MinGW/man/man7/gpl.7   |   535 -
 .../BridgePointDeliverables/MinGW/minGNUtoc.xml    |     6 -
 .../MinGW/mingw32/bin/ar.exe                       |   Bin 525951 -> 0 bytes
 .../MinGW/mingw32/bin/as.exe                       |   Bin 792360 -> 0 bytes
 .../MinGW/mingw32/bin/dlltool.exe                  |   Bin 611153 -> 0 bytes
 .../MinGW/mingw32/bin/ld.exe                       |   Bin 806237 -> 0 bytes
 .../MinGW/mingw32/bin/nm.exe                       |   Bin 570469 -> 0 bytes
 .../MinGW/mingw32/bin/objdump.exe                  |   Bin 849949 -> 0 bytes
 .../MinGW/mingw32/bin/ranlib.exe                   |   Bin 525951 -> 0 bytes
 .../MinGW/mingw32/bin/strip.exe                    |   Bin 717083 -> 0 bytes
 .../MinGW/mingw32/lib/ldscripts/i386pe.x           |   212 -
 .../MinGW/mingw32/lib/ldscripts/i386pe.xbn         |   212 -
 .../MinGW/mingw32/lib/ldscripts/i386pe.xn          |   212 -
 .../MinGW/mingw32/lib/ldscripts/i386pe.xr          |   147 -
 .../MinGW/mingw32/lib/ldscripts/i386pe.xu          |   161 -
 .../BridgePointDeliverables/MinGW/mingwgnu.bat     |    20 -
 .../tools/CheckPrivileges.exe                      |   Bin 105984 -> 0 bytes
 .../tools/docgen/docbook/docbook-xml-4.5/ChangeLog |   106 -
 .../tools/docgen/docbook/docbook-xml-4.5/README    |     8 -
 .../docgen/docbook/docbook-xml-4.5/calstblx.dtd    |   215 -
 .../docgen/docbook/docbook-xml-4.5/catalog.xml     |   124 -
 .../docgen/docbook/docbook-xml-4.5/dbcentx.mod     |   384 -
 .../docgen/docbook/docbook-xml-4.5/dbgenent.mod    |    41 -
 .../docgen/docbook/docbook-xml-4.5/dbhierx.mod     |  2193 --
 .../docgen/docbook/docbook-xml-4.5/dbnotnx.mod     |   101 -
 .../docgen/docbook/docbook-xml-4.5/dbpoolx.mod     |  8701 ------
 .../docgen/docbook/docbook-xml-4.5/docbook.cat     |   113 -
 .../docgen/docbook/docbook-xml-4.5/docbookx.dtd    |   170 -
 .../docgen/docbook/docbook-xml-4.5/ent/README      |    14 -
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsa.ent |    97 -
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsb.ent |    83 -
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsc.ent |    51 -
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsn.ent |   103 -
 .../docgen/docbook/docbook-xml-4.5/ent/isoamso.ent |    59 -
 .../docgen/docbook/docbook-xml-4.5/ent/isoamsr.ent |   125 -
 .../docgen/docbook/docbook-xml-4.5/ent/isobox.ent  |    81 -
 .../docgen/docbook/docbook-xml-4.5/ent/isocyr1.ent |   108 -
 .../docgen/docbook/docbook-xml-4.5/ent/isocyr2.ent |    67 -
 .../docgen/docbook/docbook-xml-4.5/ent/isodia.ent  |    55 -
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk1.ent |    90 -
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk2.ent |    61 -
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk3.ent |    84 -
 .../docgen/docbook/docbook-xml-4.5/ent/isogrk4.ent |    84 -
 .../docgen/docbook/docbook-xml-4.5/ent/isolat1.ent |   103 -
 .../docgen/docbook/docbook-xml-4.5/ent/isolat2.ent |   162 -
 .../docgen/docbook/docbook-xml-4.5/ent/isonum.ent  |   117 -
 .../docgen/docbook/docbook-xml-4.5/ent/isopub.ent  |   125 -
 .../docgen/docbook/docbook-xml-4.5/ent/isotech.ent |   103 -
 .../docgen/docbook/docbook-xml-4.5/htmltblx.mod    |   245 -
 .../docgen/docbook/docbook-xml-4.5/soextblx.dtd    |   321 -
 .../.CatalogManager.properties.example             |    61 -
 .../docgen/docbook/docbook-xsl-1.75.1/.urilist     |     1 -
 .../docgen/docbook/docbook-xsl-1.75.1/AUTHORS      |     4 -
 .../tools/docgen/docbook/docbook-xsl-1.75.1/BUGS   |    21 -
 .../docgen/docbook/docbook-xsl-1.75.1/COPYING      |    47 -
 .../docgen/docbook/docbook-xsl-1.75.1/INSTALL      |    88 -
 .../docgen/docbook/docbook-xsl-1.75.1/Makefile     |    83 -
 .../tools/docgen/docbook/docbook-xsl-1.75.1/NEWS   |    75 -
 .../docgen/docbook/docbook-xsl-1.75.1/NEWS.html    |    18 -
 .../docgen/docbook/docbook-xsl-1.75.1/NEWS.xml     |    84 -
 .../tools/docgen/docbook/docbook-xsl-1.75.1/README |   158 -
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.html  |  7427 -----
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.pdf   | 19594 -------------
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.txt   |  7792 ------
 .../docbook/docbook-xsl-1.75.1/RELEASE-NOTES.xml   |  9240 -------
 .../tools/docgen/docbook/docbook-xsl-1.75.1/TODO   |    23 -
 .../docgen/docbook/docbook-xsl-1.75.1/VERSION      |   115 -
 .../docgen/docbook/docbook-xsl-1.75.1/catalog.xml  |     8 -
 .../docbook/docbook-xsl-1.75.1/common/af.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/am.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/ar.xml       |  1230 -
 .../docbook-xsl-1.75.1/common/autoidx-kimber.xsl   |    43 -
 .../docbook-xsl-1.75.1/common/autoidx-kosek.xsl    |   150 -
 .../docbook/docbook-xsl-1.75.1/common/az.xml       |   673 -
 .../docbook/docbook-xsl-1.75.1/common/bg.xml       |   725 -
 .../docbook/docbook-xsl-1.75.1/common/bn.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/bs.xml       |   663 -
 .../docbook/docbook-xsl-1.75.1/common/ca.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/charmap.xml  |   185 -
 .../docbook/docbook-xsl-1.75.1/common/charmap.xsl  |   221 -
 .../docbook/docbook-xsl-1.75.1/common/common.xml   |   622 -
 .../docbook/docbook-xsl-1.75.1/common/common.xsl   |  2039 --
 .../docbook/docbook-xsl-1.75.1/common/cs.xml       |   701 -
 .../docbook/docbook-xsl-1.75.1/common/cy.xml       |  1246 -
 .../docbook/docbook-xsl-1.75.1/common/da.xml       |   665 -
 .../docbook/docbook-xsl-1.75.1/common/de.xml       |   667 -
 .../docbook/docbook-xsl-1.75.1/common/el.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/en.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/entities.ent |    60 -
 .../docbook/docbook-xsl-1.75.1/common/eo.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/es.xml       |   677 -
 .../docbook/docbook-xsl-1.75.1/common/et.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/eu.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/fa.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/fi.xml       |   671 -
 .../docbook/docbook-xsl-1.75.1/common/fr.xml       |   691 -
 .../docbook/docbook-xsl-1.75.1/common/ga.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/gentext.xsl  |   836 -
 .../docbook/docbook-xsl-1.75.1/common/gl.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/gu.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/he.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/hi.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/hr.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/hu.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/id.xml       |  1230 -
 .../docbook-xsl-1.75.1/common/insertfile.xsl       |   111 -
 .../docbook/docbook-xsl-1.75.1/common/it.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/ja.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/kn.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/ko.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/ky.xml       |   733 -
 .../docbook/docbook-xsl-1.75.1/common/l10n.dtd     |    63 -
 .../docbook/docbook-xsl-1.75.1/common/l10n.xml     |   131 -
 .../docbook/docbook-xsl-1.75.1/common/l10n.xsl     |   497 -
 .../docbook/docbook-xsl-1.75.1/common/la.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/labels.xsl   |   890 -
 .../docbook/docbook-xsl-1.75.1/common/lt.xml       |   679 -
 .../docbook/docbook-xsl-1.75.1/common/lv.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/mn.xml       |   731 -
 .../docbook/docbook-xsl-1.75.1/common/nb.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/nl.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/nn.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/olink.xsl    |  1215 -
 .../docbook/docbook-xsl-1.75.1/common/or.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/pa.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/pi.xml       |   165 -
 .../docbook/docbook-xsl-1.75.1/common/pi.xsl       |   344 -
 .../docbook/docbook-xsl-1.75.1/common/pl.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/pt.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/pt_br.xml    |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/refentry.xml |   781 -
 .../docbook/docbook-xsl-1.75.1/common/refentry.xsl |  1352 -
 .../docbook/docbook-xsl-1.75.1/common/ro.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/ru.xml       |   727 -
 .../docbook/docbook-xsl-1.75.1/common/sk.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/sl.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/sq.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/sr.xml       |   721 -
 .../docbook/docbook-xsl-1.75.1/common/sr_Latn.xml  |   680 -
 .../docbook/docbook-xsl-1.75.1/common/stripns.xsl  |   287 -
 .../docbook-xsl-1.75.1/common/subtitles.xsl        |   155 -
 .../docbook/docbook-xsl-1.75.1/common/sv.xml       |   665 -
 .../docbook/docbook-xsl-1.75.1/common/ta.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/table.xsl    |   514 -
 .../docbook-xsl-1.75.1/common/targetdatabase.dtd   |    49 -
 .../docbook/docbook-xsl-1.75.1/common/targets.xsl  |   333 -
 .../docbook/docbook-xsl-1.75.1/common/th.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/titles.xsl   |   798 -
 .../docbook/docbook-xsl-1.75.1/common/tl.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/tr.xml       |   667 -
 .../docbook/docbook-xsl-1.75.1/common/uk.xml       |   727 -
 .../docbook/docbook-xsl-1.75.1/common/utility.xml  |   259 -
 .../docbook/docbook-xsl-1.75.1/common/utility.xsl  |   290 -
 .../docbook/docbook-xsl-1.75.1/common/vi.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/xh.xml       |  1230 -
 .../docbook/docbook-xsl-1.75.1/common/zh.xml       |   661 -
 .../docbook/docbook-xsl-1.75.1/common/zh_cn.xml    |   661 -
 .../docbook/docbook-xsl-1.75.1/common/zh_tw.xml    |  1230 -
 .../docbook/docbook-xsl-1.75.1/eclipse/eclipse.xsl |   306 -
 .../docbook-xsl-1.75.1/eclipse/profile-eclipse.xsl |   269 -
 .../docgen/docbook/docbook-xsl-1.75.1/epub/README  |    88 -
 .../docbook/docbook-xsl-1.75.1/epub/bin/dbtoepub   |    72 -
 .../docbook-xsl-1.75.1/epub/bin/lib/docbook.rb     |   226 -
 .../docbook/docbook-xsl-1.75.1/epub/docbook.xsl    |  1694 --
 .../docbook-xsl-1.75.1/extensions/README.LIBXSLT   |    52 -
 .../docbook-xsl-1.75.1/extensions/docbook.py       |   239 -
 .../docbook-xsl-1.75.1/extensions/saxon65.jar      |   Bin 80674 -> 0 bytes
 .../docbook-xsl-1.75.1/extensions/xalan27.jar      |   Bin 57506 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/extensions/xslt.py  |    84 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/admon.xsl |   130 -
 .../docbook/docbook-xsl-1.75.1/fo/annotations.xsl  |    18 -
 .../docbook-xsl-1.75.1/fo/autoidx-kimber.xsl       |   178 -
 .../docbook-xsl-1.75.1/fo/autoidx-kosek.xsl        |   149 -
 .../docbook/docbook-xsl-1.75.1/fo/autoidx-ng.xsl   |    20 -
 .../docbook/docbook-xsl-1.75.1/fo/autoidx.xsl      |  1330 -
 .../docbook/docbook-xsl-1.75.1/fo/autotoc.xsl      |   915 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/axf.xsl   |   110 -
 .../docbook-xsl-1.75.1/fo/biblio-iso690.xsl        |  1300 -
 .../docbook/docbook-xsl-1.75.1/fo/biblio.xsl       |  1169 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/block.xsl |   643 -
 .../docbook/docbook-xsl-1.75.1/fo/callout.xsl      |   231 -
 .../docbook/docbook-xsl-1.75.1/fo/component.xsl    |   887 -
 .../docbook/docbook-xsl-1.75.1/fo/division.xsl     |   612 -
 .../docbook/docbook-xsl-1.75.1/fo/docbook.xsl      |   333 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/ebnf.xsl  |   325 -
 .../docbook/docbook-xsl-1.75.1/fo/fo-rtf.xsl       |   154 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/fo.xsl    |   117 -
 .../docbook/docbook-xsl-1.75.1/fo/footnote.xsl     |   220 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/fop.xsl   |    93 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/fop1.xsl  |   188 -
 .../docbook/docbook-xsl-1.75.1/fo/formal.xsl       |   618 -
 .../docbook/docbook-xsl-1.75.1/fo/glossary.xsl     |  1144 -
 .../docbook/docbook-xsl-1.75.1/fo/graphics.xsl     |   642 -
 .../docbook/docbook-xsl-1.75.1/fo/highlight.xsl    |    77 -
 .../docbook/docbook-xsl-1.75.1/fo/htmltbl.xsl      |   425 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/index.xsl |   485 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/info.xsl  |    34 -
 .../docbook/docbook-xsl-1.75.1/fo/inline.xsl       |  1286 -
 .../docbook/docbook-xsl-1.75.1/fo/keywords.xsl     |    21 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/lists.xsl |  1393 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/math.xsl  |   140 -
 .../docbook/docbook-xsl-1.75.1/fo/pagesetup.xsl    |  2567 --
 .../docgen/docbook/docbook-xsl-1.75.1/fo/param.xml | 12413 ---------
 .../docgen/docbook/docbook-xsl-1.75.1/fo/param.xsl |   942 -
 .../docbook/docbook-xsl-1.75.1/fo/passivetex.xsl   |    36 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/pdf2index |   140 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/pi.xml    |  1002 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/pi.xsl    |  1086 -
 .../docbook-xsl-1.75.1/fo/profile-docbook.xsl      |   288 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/ptc.xsl   |    77 -
 .../docbook/docbook-xsl-1.75.1/fo/qandaset.xsl     |   395 -
 .../docbook/docbook-xsl-1.75.1/fo/refentry.xsl     |   637 -
 .../docbook/docbook-xsl-1.75.1/fo/sections.xsl     |   764 -
 .../docbook/docbook-xsl-1.75.1/fo/spaces.xsl       |   274 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/synop.xsl |  1007 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/table.xml |   135 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/table.xsl |  1621 --
 .../docgen/docbook/docbook-xsl-1.75.1/fo/task.xsl  |    91 -
 .../docbook-xsl-1.75.1/fo/titlepage.templates.xml  |  1354 -
 .../docbook-xsl-1.75.1/fo/titlepage.templates.xsl  |  5182 ----
 .../docbook/docbook-xsl-1.75.1/fo/titlepage.xsl    |   760 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/toc.xsl   |   332 -
 .../docbook/docbook-xsl-1.75.1/fo/verbatim.xsl     |   465 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/xep.xsl   |   180 -
 .../docgen/docbook/docbook-xsl-1.75.1/fo/xref.xsl  |  1519 --
 .../docbook/docbook-xsl-1.75.1/highlighting/README |    16 -
 .../docbook-xsl-1.75.1/highlighting/c-hl.xml       |   101 -
 .../docbook-xsl-1.75.1/highlighting/common.xsl     |   120 -
 .../docbook-xsl-1.75.1/highlighting/cpp-hl.xml     |   150 -
 .../docbook-xsl-1.75.1/highlighting/csharp-hl.xml  |   187 -
 .../docbook-xsl-1.75.1/highlighting/delphi-hl.xml  |   200 -
 .../docbook-xsl-1.75.1/highlighting/ini-hl.xml     |    45 -
 .../docbook-xsl-1.75.1/highlighting/java-hl.xml    |   117 -
 .../highlighting/javascript-hl.xml                 |   147 -
 .../docbook-xsl-1.75.1/highlighting/m2-hl.xml      |    90 -
 .../docbook-xsl-1.75.1/highlighting/myxml-hl.xml   |   116 -
 .../docbook-xsl-1.75.1/highlighting/perl-hl.xml    |   120 -
 .../docbook-xsl-1.75.1/highlighting/php-hl.xml     |   149 -
 .../docbook-xsl-1.75.1/highlighting/python-hl.xml  |   100 -
 .../docbook-xsl-1.75.1/highlighting/ruby-hl.xml    |   109 -
 .../docbook-xsl-1.75.1/highlighting/tcl-hl.xml     |   180 -
 .../highlighting/xslthl-config.xml                 |    46 -
 .../docbook/docbook-xsl-1.75.1/html/admon.xsl      |   134 -
 .../docbook-xsl-1.75.1/html/annotations.xsl        |   169 -
 .../docbook-xsl-1.75.1/html/autoidx-kimber.xsl     |   168 -
 .../docbook-xsl-1.75.1/html/autoidx-kosek.xsl      |   124 -
 .../docbook/docbook-xsl-1.75.1/html/autoidx-ng.xsl |    20 -
 .../docbook/docbook-xsl-1.75.1/html/autoidx.xsl    |   712 -
 .../docbook/docbook-xsl-1.75.1/html/autotoc.xsl    |   676 -
 .../docbook-xsl-1.75.1/html/biblio-iso690.xsl      |  1300 -
 .../docbook/docbook-xsl-1.75.1/html/biblio.xsl     |  1253 -
 .../docbook/docbook-xsl-1.75.1/html/block.xsl      |   437 -
 .../docbook/docbook-xsl-1.75.1/html/callout.xsl    |   201 -
 .../docbook/docbook-xsl-1.75.1/html/changebars.xsl |   121 -
 .../docbook-xsl-1.75.1/html/chunk-changebars.xsl   |    99 -
 .../docbook/docbook-xsl-1.75.1/html/chunk-code.xsl |   667 -
 .../docbook-xsl-1.75.1/html/chunk-common.xsl       |  1920 --
 .../docbook/docbook-xsl-1.75.1/html/chunk.xsl      |    52 -
 .../docbook/docbook-xsl-1.75.1/html/chunker.xsl    |   439 -
 .../docbook/docbook-xsl-1.75.1/html/chunkfast.xsl  |    72 -
 .../docbook/docbook-xsl-1.75.1/html/chunktoc.xsl   |   544 -
 .../docbook/docbook-xsl-1.75.1/html/component.xsl  |   425 -
 .../docbook/docbook-xsl-1.75.1/html/division.xsl   |   217 -
 .../docbook/docbook-xsl-1.75.1/html/docbook.xsl    |   481 -
 .../docbook/docbook-xsl-1.75.1/html/ebnf.xsl       |   329 -
 .../docbook/docbook-xsl-1.75.1/html/footnote.xsl   |   310 -
 .../docbook/docbook-xsl-1.75.1/html/formal.xsl     |   404 -
 .../docbook/docbook-xsl-1.75.1/html/glossary.xsl   |   492 -
 .../docbook/docbook-xsl-1.75.1/html/graphics.xsl   |  1515 --
 .../docbook/docbook-xsl-1.75.1/html/highlight.xsl  |    73 -
 .../docbook/docbook-xsl-1.75.1/html/html-rtf.xsl   |   336 -
 .../docbook/docbook-xsl-1.75.1/html/html.xsl       |   370 -
 .../docbook/docbook-xsl-1.75.1/html/htmltbl.xsl    |   138 -
 .../docbook/docbook-xsl-1.75.1/html/index.xsl      |   288 -
 .../docbook/docbook-xsl-1.75.1/html/info.xsl       |    43 -
 .../docbook/docbook-xsl-1.75.1/html/inline.xsl     |  1485 -
 .../docbook/docbook-xsl-1.75.1/html/keywords.xsl   |    35 -
 .../docbook/docbook-xsl-1.75.1/html/lists.xsl      |  1183 -
 .../docbook/docbook-xsl-1.75.1/html/maketoc.xsl    |    86 -
 .../docbook/docbook-xsl-1.75.1/html/manifest.xsl   |    22 -
 .../docbook/docbook-xsl-1.75.1/html/math.xsl       |   270 -
 .../docbook/docbook-xsl-1.75.1/html/oldchunker.xsl |   202 -
 .../docbook/docbook-xsl-1.75.1/html/onechunk.xsl   |    37 -
 .../docbook/docbook-xsl-1.75.1/html/param.xml      | 10584 --------
 .../docbook/docbook-xsl-1.75.1/html/param.xsl      |   429 -
 .../docgen/docbook/docbook-xsl-1.75.1/html/pi.xml  |  1113 -
 .../docgen/docbook/docbook-xsl-1.75.1/html/pi.xsl  |  1263 -
 .../docbook-xsl-1.75.1/html/profile-chunk-code.xsl |   608 -
 .../docbook-xsl-1.75.1/html/profile-chunk.xsl      |    52 -
 .../docbook-xsl-1.75.1/html/profile-docbook.xsl    |   414 -
 .../docbook-xsl-1.75.1/html/profile-onechunk.xsl   |    37 -
 .../docbook/docbook-xsl-1.75.1/html/qandaset.xsl   |   437 -
 .../docbook/docbook-xsl-1.75.1/html/refentry.xsl   |   299 -
 .../docbook/docbook-xsl-1.75.1/html/sections.xsl   |   615 -
 .../docbook/docbook-xsl-1.75.1/html/synop.xsl      |  1554 --
 .../docbook/docbook-xsl-1.75.1/html/table.xsl      |  1147 -
 .../docbook/docbook-xsl-1.75.1/html/task.xsl       |    76 -
 .../html/titlepage.templates.xml                   |   686 -
 .../html/titlepage.templates.xsl                   |  3710 ---
 .../docbook/docbook-xsl-1.75.1/html/titlepage.xsl  |  1043 -
 .../docgen/docbook/docbook-xsl-1.75.1/html/toc.xsl |   350 -
 .../docbook/docbook-xsl-1.75.1/html/verbatim.xsl   |   403 -
 .../docbook/docbook-xsl-1.75.1/html/xref.xsl       |  1377 -
 .../htmlhelp/htmlhelp-common.xsl                   |  1120 -
 .../docbook-xsl-1.75.1/htmlhelp/htmlhelp.xsl       |    22 -
 .../htmlhelp/profile-htmlhelp-common.xsl           |  1083 -
 .../htmlhelp/profile-htmlhelp.xsl                  |    22 -
 .../docbook-xsl-1.75.1/images/annot-close.png      |   Bin 207 -> 0 bytes
 .../docbook-xsl-1.75.1/images/annot-open.png       |   Bin 837 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/blank.png    |   Bin 374 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/1.gif       |   Bin 889 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/1.png       |   Bin 329 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/1.svg       |    15 -
 .../docbook-xsl-1.75.1/images/callouts/10.gif      |   Bin 929 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/10.png      |   Bin 361 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/10.svg      |    18 -
 .../docbook-xsl-1.75.1/images/callouts/11.gif      |   Bin 202 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/11.png      |   Bin 565 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/11.svg      |    16 -
 .../docbook-xsl-1.75.1/images/callouts/12.gif      |   Bin 210 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/12.png      |   Bin 617 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/12.svg      |    18 -
 .../docbook-xsl-1.75.1/images/callouts/13.gif      |   Bin 209 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/13.png      |   Bin 623 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/13.svg      |    20 -
 .../docbook-xsl-1.75.1/images/callouts/14.gif      |   Bin 205 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/14.png      |   Bin 411 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/14.svg      |    17 -
 .../docbook-xsl-1.75.1/images/callouts/15.gif      |   Bin 210 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/15.png      |   Bin 640 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/15.svg      |    19 -
 .../docbook-xsl-1.75.1/images/callouts/16.svg      |    20 -
 .../docbook-xsl-1.75.1/images/callouts/17.svg      |    17 -
 .../docbook-xsl-1.75.1/images/callouts/18.svg      |    21 -
 .../docbook-xsl-1.75.1/images/callouts/19.svg      |    20 -
 .../docbook-xsl-1.75.1/images/callouts/2.gif       |   Bin 907 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/2.png       |   Bin 353 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/2.svg       |    17 -
 .../docbook-xsl-1.75.1/images/callouts/20.svg      |    20 -
 .../docbook-xsl-1.75.1/images/callouts/21.svg      |    18 -
 .../docbook-xsl-1.75.1/images/callouts/22.svg      |    20 -
 .../docbook-xsl-1.75.1/images/callouts/23.svg      |    22 -
 .../docbook-xsl-1.75.1/images/callouts/24.svg      |    19 -
 .../docbook-xsl-1.75.1/images/callouts/25.svg      |    21 -
 .../docbook-xsl-1.75.1/images/callouts/26.svg      |    22 -
 .../docbook-xsl-1.75.1/images/callouts/27.svg      |    19 -
 .../docbook-xsl-1.75.1/images/callouts/28.svg      |    23 -
 .../docbook-xsl-1.75.1/images/callouts/29.svg      |    22 -
 .../docbook-xsl-1.75.1/images/callouts/3.gif       |   Bin 914 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/3.png       |   Bin 350 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/3.svg       |    19 -
 .../docbook-xsl-1.75.1/images/callouts/30.svg      |    22 -
 .../docbook-xsl-1.75.1/images/callouts/4.gif       |   Bin 907 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/4.png       |   Bin 345 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/4.svg       |    16 -
 .../docbook-xsl-1.75.1/images/callouts/5.gif       |   Bin 916 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/5.png       |   Bin 348 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/5.svg       |    18 -
 .../docbook-xsl-1.75.1/images/callouts/6.gif       |   Bin 218 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/6.png       |   Bin 355 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/6.svg       |    19 -
 .../docbook-xsl-1.75.1/images/callouts/7.gif       |   Bin 907 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/7.png       |   Bin 344 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/7.svg       |    16 -
 .../docbook-xsl-1.75.1/images/callouts/8.gif       |   Bin 918 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/8.png       |   Bin 357 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/8.svg       |    20 -
 .../docbook-xsl-1.75.1/images/callouts/9.gif       |   Bin 923 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/9.png       |   Bin 357 -> 0 bytes
 .../docbook-xsl-1.75.1/images/callouts/9.svg       |    19 -
 .../docbook/docbook-xsl-1.75.1/images/caution.gif  |   Bin 743 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/caution.png  |   Bin 1250 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/caution.svg  |    25 -
 .../docbook/docbook-xsl-1.75.1/images/caution.tif  |   Bin 1978 -> 0 bytes
 .../docbook-xsl-1.75.1/images/colorsvg/caution.svg |   141 -
 .../docbook-xsl-1.75.1/images/colorsvg/home.svg    |   498 -
 .../images/colorsvg/important.svg                  |   239 -
 .../docbook-xsl-1.75.1/images/colorsvg/next.svg    |   338 -
 .../docbook-xsl-1.75.1/images/colorsvg/note.svg    |   200 -
 .../docbook-xsl-1.75.1/images/colorsvg/prev.svg    |   338 -
 .../docbook-xsl-1.75.1/images/colorsvg/tip.svg     |   367 -
 .../docbook-xsl-1.75.1/images/colorsvg/up.svg      |   338 -
 .../docbook-xsl-1.75.1/images/colorsvg/warning.svg |   232 -
 .../docbook/docbook-xsl-1.75.1/images/draft.png    |   Bin 16150 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/home.gif     |   Bin 321 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/home.png     |   Bin 1156 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/home.svg     |    26 -
 .../docbook-xsl-1.75.1/images/important.gif        |   Bin 1003 -> 0 bytes
 .../docbook-xsl-1.75.1/images/important.png        |   Bin 722 -> 0 bytes
 .../docbook-xsl-1.75.1/images/important.svg        |    25 -
 .../docbook-xsl-1.75.1/images/important.tif        |   Bin 2020 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/next.gif     |   Bin 1083 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/next.png     |   Bin 1150 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/next.svg     |    19 -
 .../docbook/docbook-xsl-1.75.1/images/note.gif     |   Bin 580 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/note.png     |   Bin 490 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/note.svg     |    33 -
 .../docbook/docbook-xsl-1.75.1/images/note.tif     |   Bin 460 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/prev.gif     |   Bin 1118 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/prev.png     |   Bin 1132 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/prev.svg     |    19 -
 .../docbook/docbook-xsl-1.75.1/images/tip.gif      |   Bin 598 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/tip.png      |   Bin 449 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/tip.svg      |    31 -
 .../docbook/docbook-xsl-1.75.1/images/tip.tif      |   Bin 420 -> 0 bytes
 .../docbook-xsl-1.75.1/images/toc-blank.png        |   Bin 318 -> 0 bytes
 .../docbook-xsl-1.75.1/images/toc-minus.png        |   Bin 259 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/toc-plus.png |   Bin 264 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/up.gif       |   Bin 1089 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/up.png       |   Bin 1111 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/up.svg       |    19 -
 .../docbook/docbook-xsl-1.75.1/images/warning.gif  |   Bin 743 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/warning.png  |   Bin 1241 -> 0 bytes
 .../docbook/docbook-xsl-1.75.1/images/warning.svg  |    23 -
 .../docbook/docbook-xsl-1.75.1/images/warning.tif  |   Bin 1990 -> 0 bytes
 .../docgen/docbook/docbook-xsl-1.75.1/install.sh   |   977 -
 .../docbook-xsl-1.75.1/javahelp/javahelp.xsl       |   625 -
 .../javahelp/profile-javahelp.xsl                  |   549 -
 .../docgen/docbook/docbook-xsl-1.75.1/lib/lib.xsl  |   480 -
 .../params/abstract.notitle.enabled.xml            |    22 -
 .../params/abstract.properties.xml                 |    32 -
 .../params/abstract.title.properties.xml           |    39 -
 .../docbook-xsl-1.75.1/params/active.toc.xml       |    29 -
 .../docbook-xsl-1.75.1/params/ade.extensions.xml   |    32 -
 .../params/admon.graphics.extension.xml            |    27 -
 .../params/admon.graphics.path.xml                 |    27 -
 .../docbook-xsl-1.75.1/params/admon.graphics.xml   |    29 -
 .../docbook-xsl-1.75.1/params/admon.style.xml      |    31 -
 .../docbook-xsl-1.75.1/params/admon.textlabel.xml  |    32 -
 .../params/admonition.properties.xml               |    25 -
 .../params/admonition.title.properties.xml         |    32 -
 .../docbook-xsl-1.75.1/params/alignment.xml        |    41 -
 .../docbook-xsl-1.75.1/params/annotate.toc.xml     |    28 -
 .../docbook-xsl-1.75.1/params/annotation.css.xml   |    71 -
 .../params/annotation.graphic.close.xml            |    31 -
 .../params/annotation.graphic.open.xml             |    28 -
 .../docbook-xsl-1.75.1/params/annotation.js.xml    |    33 -
 .../params/annotation.support.xml                  |    29 -
 .../params/appendix.autolabel.xml                  |    73 -
 .../params/arbortext.extensions.xml                |    30 -
 .../params/article.appendix.title.properties.xml   |    33 -
 .../params/author.othername.in.middle.xml          |    31 -
 .../docbook-xsl-1.75.1/params/autolayout-file.xml  |    29 -
 .../params/autotoc.label.in.hyperlink.xml          |    29 -
 .../params/autotoc.label.separator.xml             |    27 -
 .../docbook-xsl-1.75.1/params/axf.extensions.xml   |    33 -
 .../params/banner.before.navigation.xml            |    25 -
 .../docbook/docbook-xsl-1.75.1/params/base.dir.xml |    29 -
 .../params/biblioentry.item.separator.xml          |    26 -
 .../params/biblioentry.properties.xml              |    28 -
 .../params/bibliography.collection.xml             |   104 -
 .../params/bibliography.numbered.xml               |    27 -
 .../params/bibliography.style.xml                  |    35 -
 .../params/blockquote.properties.xml               |    34 -
 .../params/blurb.on.titlepage.enabled.xml          |    31 -
 .../docbook-xsl-1.75.1/params/body.attributes.xml  |    31 -
 .../docbook-xsl-1.75.1/params/body.bg.color.xml    |    28 -
 .../docbook-xsl-1.75.1/params/body.end.indent.xml  |    37 -
 .../docbook-xsl-1.75.1/params/body.font.family.xml |    32 -
 .../docbook-xsl-1.75.1/params/body.font.master.xml |    30 -
 .../docbook-xsl-1.75.1/params/body.font.size.xml   |    31 -
 .../params/body.margin.bottom.xml                  |    29 -
 .../docbook-xsl-1.75.1/params/body.margin.top.xml  |    28 -
 .../params/body.start.indent.xml                   |    64 -
 .../params/bookmarks.collapse.xml                  |    31 -
 .../params/bridgehead.in.toc.xml                   |    28 -
 .../docbook-xsl-1.75.1/params/bullet.image.xml     |    28 -
 .../params/callout.defaultcolumn.xml               |    30 -
 .../params/callout.graphics.extension.xml          |    33 -
 .../params/callout.graphics.number.limit.xml       |    34 -
 .../params/callout.graphics.path.xml               |    31 -
 .../docbook-xsl-1.75.1/params/callout.graphics.xml |    30 -
 .../params/callout.icon.size.xml                   |    28 -
 .../params/callout.list.table.xml                  |    32 -
 .../params/callout.unicode.font.xml                |    29 -
 .../params/callout.unicode.number.limit.xml        |    35 -
 .../params/callout.unicode.start.character.xml     |    33 -
 .../docbook-xsl-1.75.1/params/callout.unicode.xml  |    26 -
 .../params/callouts.extension.xml                  |    30 -
 .../params/chapter.autolabel.xml                   |    71 -
 .../docbook-xsl-1.75.1/params/chunk.append.xml     |    30 -
 .../params/chunk.first.sections.xml                |    31 -
 .../docbook-xsl-1.75.1/params/chunk.quietly.xml    |    30 -
 .../params/chunk.section.depth.xml                 |    27 -
 .../docbook-xsl-1.75.1/params/chunk.sections.xml   |    30 -
 .../params/chunk.separate.lots.xml                 |    36 -
 .../docbook-xsl-1.75.1/params/chunk.toc.xml        |    30 -
 .../params/chunk.tocs.and.lots.has.title.xml       |    28 -
 .../params/chunk.tocs.and.lots.xml                 |    32 -
 .../chunker.output.cdata-section-elements.xml      |    30 -
 .../params/chunker.output.doctype-public.xml       |    31 -
 .../params/chunker.output.doctype-system.xml       |    31 -
 .../params/chunker.output.encoding.xml             |    31 -
 .../params/chunker.output.indent.xml               |    30 -
 .../params/chunker.output.media-type.xml           |    35 -
 .../params/chunker.output.method.xml               |    32 -
 .../params/chunker.output.omit-xml-declaration.xml |    30 -
 .../params/chunker.output.standalone.xml           |    31 -
 .../params/citerefentry.link.xml                   |    29 -
 .../params/collect.xref.targets.xml                |    33 -
 .../params/column.count.back.xml                   |    27 -
 .../params/column.count.body.xml                   |    27 -
 .../params/column.count.front.xml                  |    27 -
 .../params/column.count.index.xml                  |    27 -
 .../docbook-xsl-1.75.1/params/column.count.lot.xml |    28 -
 .../params/column.count.titlepage.xml              |    27 -
 .../docbook-xsl-1.75.1/params/column.gap.back.xml  |    28 -
 .../docbook-xsl-1.75.1/params/column.gap.body.xml  |    28 -
 .../docbook-xsl-1.75.1/params/column.gap.front.xml |    28 -
 .../docbook-xsl-1.75.1/params/column.gap.index.xml |    28 -
 .../docbook-xsl-1.75.1/params/column.gap.lot.xml   |    28 -
 .../params/column.gap.titlepage.xml                |    29 -
 .../params/compact.list.item.spacing.xml           |    28 -
 .../params/component.label.includes.part.label.xml |    39 -
 .../params/component.title.properties.xml          |    40 -
 .../params/component.titlepage.properties.xml      |    33 -
 .../params/contrib.inline.enabled.xml              |    26 -
 .../docbook-xsl-1.75.1/params/crop.mark.bleed.xml  |    28 -
 .../docbook-xsl-1.75.1/params/crop.mark.offset.xml |    28 -
 .../docbook-xsl-1.75.1/params/crop.mark.width.xml  |    28 -
 .../docbook-xsl-1.75.1/params/crop.marks.xml       |    28 -
 .../docbook-xsl-1.75.1/params/css.decoration.xml   |    33 -
 .../params/css.stylesheet.dir.xml                  |    33 -
 .../docbook-xsl-1.75.1/params/css.stylesheet.xml   |    29 -
 .../docbook-xsl-1.75.1/params/current.docid.xml    |    27 -
 .../params/currentpage.marker.xml                  |    25 -
 .../params/default.float.class.xml                 |    34 -
 .../params/default.image.width.xml                 |    31 -
 .../params/default.table.frame.xml                 |    28 -
 .../params/default.table.rules.xml                 |    76 -
 .../params/default.table.width.xml                 |    26 -
 .../docbook-xsl-1.75.1/params/default.units.xml    |    37 -
 .../params/dingbat.font.family.xml                 |    33 -
 .../docbook-xsl-1.75.1/params/double.sided.xml     |    31 -
 .../docbook-xsl-1.75.1/params/draft.mode.xml       |    36 -
 .../params/draft.watermark.image.xml               |    27 -
 .../docbook/docbook-xsl-1.75.1/params/dry-run.xml  |    27 -
 .../docbook-xsl-1.75.1/params/dynamic.toc.xml      |    29 -
 .../docbook-xsl-1.75.1/params/ebnf.assignment.xml  |    39 -
 .../params/ebnf.statement.terminator.xml           |    32 -
 .../params/ebnf.table.bgcolor.xml                  |    30 -
 .../params/ebnf.table.border.xml                   |    26 -
 .../params/eclipse.autolabel.xml                   |    28 -
 .../params/eclipse.plugin.id.xml                   |    28 -
 .../params/eclipse.plugin.name.xml                 |    27 -
 .../params/eclipse.plugin.provider.xml             |    27 -
 .../docbook-xsl-1.75.1/params/editedby.enabled.xml |    27 -
 .../params/email.delimiters.enabled.xml            |    34 -
 .../params/email.mailto.enabled.xml                |    29 -
 .../params/emphasis.propagates.style.xml           |    26 -
 .../params/entry.propagates.style.xml              |    30 -
 .../docbook-xsl-1.75.1/params/epub.autolabel.xml   |    28 -
 .../params/equation.properties.xml                 |    27 -
 .../params/example.properties.xml                  |    27 -
 .../params/exsl.node.set.available.xml             |    44 -
 .../docbook-xsl-1.75.1/params/feedback.href.xml    |    28 -
 .../params/feedback.link.text.xml                  |    28 -
 .../params/feedback.with.ids.xml                   |    27 -
 .../params/figure.properties.xml                   |    27 -
 .../docbook-xsl-1.75.1/params/filename-prefix.xml  |    28 -
 .../params/firstterm.only.link.xml                 |    29 -
 .../docbook-xsl-1.75.1/params/foil.properties.xml  |    37 -
 .../params/foil.subtitle.properties.xml            |    36 -
 .../params/foil.title.master.xml                   |    29 -
 .../docbook-xsl-1.75.1/params/foil.title.size.xml  |    32 -
 .../params/foilgroup.properties.xml                |    31 -
 .../docbook-xsl-1.75.1/params/foilgroup.toc.xml    |    29 -
 .../params/footer.column.widths.xml                |    80 -
 .../params/footer.content.properties.xml           |    34 -
 .../docbook-xsl-1.75.1/params/footer.hr.xml        |    26 -
 .../docbook-xsl-1.75.1/params/footer.rule.xml      |    27 -
 .../params/footer.table.height.xml                 |    32 -
 .../params/footer.table.properties.xml             |    30 -
 .../params/footers.on.blank.pages.xml              |    27 -
 .../params/footnote.font.size.xml                  |    28 -
 .../params/footnote.mark.properties.xml            |    41 -
 .../params/footnote.number.format.xml              |    33 -
 .../params/footnote.number.symbols.xml             |    39 -
 .../params/footnote.properties.xml                 |    43 -
 .../params/footnote.sep.leader.properties.xml      |    39 -
 .../docbook-xsl-1.75.1/params/fop.extensions.xml   |    36 -
 .../docbook-xsl-1.75.1/params/fop1.extensions.xml  |    34 -
 .../params/formal.object.properties.xml            |    36 -
 .../params/formal.procedures.xml                   |    28 -
 .../params/formal.title.placement.xml              |    41 -
 .../params/formal.title.properties.xml             |    34 -
 .../params/funcsynopsis.decoration.xml             |    30 -
 .../params/funcsynopsis.style.xml                  |    31 -
 .../docbook-xsl-1.75.1/params/function.parens.xml  |    29 -
 .../params/generate.id.attributes.xml              |    59 -
 .../docbook-xsl-1.75.1/params/generate.index.xml   |    25 -
 .../params/generate.legalnotice.link.xml           |    72 -
 .../params/generate.manifest.xml                   |    27 -
 .../params/generate.meta.abstract.xml              |    29 -
 .../params/generate.revhistory.link.xml            |    50 -
 .../params/generate.section.toc.level.xml          |    35 -
 .../docbook-xsl-1.75.1/params/generate.toc.xml     |   108 -
 .../params/glossary.as.blocks.xml                  |    38 -
 .../params/glossary.collection.xml                 |   252 -
 .../docbook-xsl-1.75.1/params/glossary.sort.xml    |    32 -
 .../params/glossdef.block.properties.xml           |    32 -
 .../params/glossdef.list.properties.xml            |    30 -
 .../params/glossentry.list.item.properties.xml     |    32 -
 .../params/glossentry.show.acronym.xml             |    37 -
 .../params/glosslist.as.blocks.xml                 |    27 -
 .../params/glossterm.auto.link.xml                 |    33 -
 .../params/glossterm.block.properties.xml          |    35 -
 .../params/glossterm.list.properties.xml           |    30 -
 .../params/glossterm.separation.xml                |    31 -
 .../docbook-xsl-1.75.1/params/glossterm.width.xml  |    28 -
 .../params/graphic.default.extension.xml           |    29 -
 .../params/graphical.admonition.properties.xml     |    42 -
 .../docbook-xsl-1.75.1/params/graphics.dir.xml     |    33 -
 .../params/graphicsize.extension.xml               |    30 -
 .../params/graphicsize.use.img.src.path.xml        |    30 -
 .../params/header.column.widths.xml                |    80 -
 .../params/header.content.properties.xml           |    34 -
 .../docbook-xsl-1.75.1/params/header.hr.xml        |    26 -
 .../docbook-xsl-1.75.1/params/header.rule.xml      |    27 -
 .../params/header.table.height.xml                 |    32 -
 .../params/header.table.properties.xml             |    30 -
 .../params/headers.on.blank.pages.xml              |    27 -
 .../docbook-xsl-1.75.1/params/hidetoc.image.xml    |    29 -
 .../params/highlight.default.language.xml          |    27 -
 .../docbook-xsl-1.75.1/params/highlight.source.xml |    82 -
 .../params/highlight.xslthl.config.xml             |    27 -
 .../docbook-xsl-1.75.1/params/home.image.xml       |    27 -
 .../docbook-xsl-1.75.1/params/html.append.xml      |    30 -
 .../docbook-xsl-1.75.1/params/html.base.xml        |    30 -
 .../docbook-xsl-1.75.1/params/html.cellpadding.xml |    29 -
 .../docbook-xsl-1.75.1/params/html.cellspacing.xml |    29 -
 .../docbook-xsl-1.75.1/params/html.cleanup.xml     |    34 -
 .../docbook/docbook-xsl-1.75.1/params/html.ext.xml |    29 -
 .../params/html.extra.head.links.xml               |    31 -
 .../params/html.head.legalnotice.link.multiple.xml |    44 -
 .../params/html.head.legalnotice.link.types.xml    |    75 -
 .../params/html.longdesc.link.xml                  |    39 -
 .../docbook-xsl-1.75.1/params/html.longdesc.xml    |    28 -
 .../params/html.stylesheet.type.xml                |    26 -
 .../docbook-xsl-1.75.1/params/html.stylesheet.xml  |    36 -
 .../params/htmlhelp.alias.file.xml                 |    27 -
 .../params/htmlhelp.autolabel.xml                  |    28 -
 .../params/htmlhelp.button.back.xml                |    27 -
 .../params/htmlhelp.button.forward.xml             |    27 -
 .../params/htmlhelp.button.hideshow.xml            |    27 -
 .../params/htmlhelp.button.home.url.xml            |    27 -
 .../params/htmlhelp.button.home.xml                |    27 -
 .../params/htmlhelp.button.jump1.title.xml         |    27 -
 .../params/htmlhelp.button.jump1.url.xml           |    27 -
 .../params/htmlhelp.button.jump1.xml               |    23 -
 .../params/htmlhelp.button.jump2.title.xml         |    27 -
 .../params/htmlhelp.button.jump2.url.xml           |    27 -
 .../params/htmlhelp.button.jump2.xml               |    27 -
 .../params/htmlhelp.button.locate.xml              |    28 -
 .../params/htmlhelp.button.next.xml                |    27 -
 .../params/htmlhelp.button.options.xml             |    28 -
 .../params/htmlhelp.button.prev.xml                |    28 -
 .../params/htmlhelp.button.print.xml               |    28 -
 .../params/htmlhelp.button.refresh.xml             |    27 -
 .../params/htmlhelp.button.stop.xml                |    28 -
 .../params/htmlhelp.button.zoom.xml                |    28 -
 .../docbook-xsl-1.75.1/params/htmlhelp.chm.xml     |    27 -
 .../params/htmlhelp.default.topic.xml              |    37 -
 .../params/htmlhelp.display.progress.xml           |    28 -
 .../params/htmlhelp.encoding.xml                   |    28 -
 .../params/htmlhelp.enhanced.decompilation.xml     |    27 -
 .../params/htmlhelp.enumerate.images.xml           |    28 -
 .../params/htmlhelp.force.map.and.alias.xml        |    26 -
 .../params/htmlhelp.hhc.binary.xml                 |    29 -
 .../params/htmlhelp.hhc.folders.instead.books.xml  |    33 -
 .../params/htmlhelp.hhc.section.depth.xml          |    27 -
 .../params/htmlhelp.hhc.show.root.xml              |    29 -
 .../params/htmlhelp.hhc.width.xml                  |    28 -
 .../docbook-xsl-1.75.1/params/htmlhelp.hhc.xml     |    27 -
 .../docbook-xsl-1.75.1/params/htmlhelp.hhk.xml     |    27 -
 .../params/htmlhelp.hhp.tail.xml                   |    28 -
 .../params/htmlhelp.hhp.window.xml                 |    28 -
 .../params/htmlhelp.hhp.windows.xml                |    29 -
 .../docbook-xsl-1.75.1/params/htmlhelp.hhp.xml     |    28 -
 .../params/htmlhelp.map.file.xml                   |    25 -
 .../docbook-xsl-1.75.1/params/htmlhelp.only.xml    |    32 -
 .../params/htmlhelp.remember.window.position.xml   |    27 -
 .../params/htmlhelp.show.advanced.search.xml       |    28 -
 .../params/htmlhelp.show.favorities.xml            |    28 -
 .../params/htmlhelp.show.menu.xml                  |    28 -
 .../params/htmlhelp.show.toolbar.text.xml          |    28 -
 .../docbook-xsl-1.75.1/params/htmlhelp.title.xml   |    28 -
 .../docbook-xsl-1.75.1/params/htmlhelp.use.hhk.xml |    28 -
 .../params/htmlhelp.window.geometry.xml            |    30 -
 .../params/hyphenate.verbatim.characters.xml       |    30 -
 .../params/hyphenate.verbatim.xml                  |    45 -
 .../docbook-xsl-1.75.1/params/hyphenate.xml        |    29 -
 .../docbook-xsl-1.75.1/params/id.warnings.xml      |    25 -
 .../params/ignore.image.scaling.xml                |    28 -
 .../docbook-xsl-1.75.1/params/img.src.path.xml     |    40 -
 .../params/index.div.title.properties.xml          |    39 -
 .../params/index.entry.properties.xml              |    33 -
 .../params/index.links.to.section.xml              |    76 -
 .../docbook-xsl-1.75.1/params/index.method.xml     |   162 -
 .../params/index.number.separator.xml              |    54 -
 .../docbook-xsl-1.75.1/params/index.on.role.xml    |    48 -
 .../docbook-xsl-1.75.1/params/index.on.type.xml    |    52 -
 .../params/index.page.number.properties.xml        |    31 -
 .../params/index.prefer.titleabbrev.xml            |    29 -
 .../params/index.preferred.page.properties.xml     |    32 -
 .../params/index.range.separator.xml               |    57 -
 .../params/index.term.separator.xml                |    54 -
 .../params/informal.object.properties.xml          |    29 -
 .../params/informalequation.properties.xml         |    27 -
 .../params/informalexample.properties.xml          |    27 -
 .../params/informalfigure.properties.xml           |    27 -
 .../params/informaltable.properties.xml            |    32 -
 .../docbook-xsl-1.75.1/params/inherit.keywords.xml |    31 -
 .../params/insert.link.page.number.xml             |    69 -
 .../params/insert.olink.page.number.xml            |    83 -
 .../params/insert.olink.pdf.frag.xml               |    68 -
 .../params/insert.xref.page.number.xml             |    60 -
 .../params/itemizedlist.label.properties.xml       |    26 -
 .../params/itemizedlist.label.width.xml            |    28 -
 .../params/itemizedlist.properties.xml             |    23 -
 .../params/javahelp.encoding.xml                   |    31 -
 .../params/keep.relative.image.uris.xml            |    34 -
 .../docbook-xsl-1.75.1/params/keyboard.nav.xml     |    29 -
 .../params/l10n.gentext.default.language.xml       |    30 -
 .../params/l10n.gentext.language.xml               |    33 -
 .../params/l10n.gentext.use.xref.language.xml      |    53 -
 .../params/l10n.lang.value.rfc.compliant.xml       |    57 -
 .../docbook-xsl-1.75.1/params/label.from.part.xml  |    38 -
 .../docbook-xsl-1.75.1/params/line-height.xml      |    27 -
 .../params/linenumbering.everyNth.xml              |    29 -
 .../params/linenumbering.extension.xml             |    30 -
 .../params/linenumbering.separator.xml             |    30 -
 .../params/linenumbering.width.xml                 |    29 -
 .../docbook-xsl-1.75.1/params/link.mailto.url.xml  |    29 -
 .../params/list.block.properties.xml               |    25 -
 .../params/list.block.spacing.xml                  |    29 -
 .../params/list.item.spacing.xml                   |    26 -
 .../params/make.graphic.viewport.xml               |    35 -
 .../params/make.index.markup.xml                   |    73 -
 .../params/make.single.year.ranges.xml             |    28 -
 .../docbook-xsl-1.75.1/params/make.valid.html.xml  |    35 -
 .../docbook-xsl-1.75.1/params/make.year.ranges.xml |    32 -
 .../params/man.authors.section.enabled.xml         |    46 -
 .../params/man.base.url.for.relative.links.xml     |    76 -
 .../params/man.break.after.slash.xml               |    46 -
 .../params/man.charmap.enabled.xml                 |    55 -
 .../params/man.charmap.subset.profile.english.xml  |    80 -
 .../params/man.charmap.subset.profile.xml          |   297 -
 .../docbook-xsl-1.75.1/params/man.charmap.uri.xml  |    42 -
 .../params/man.charmap.use.subset.xml              |    80 -
 .../params/man.copyright.section.enabled.xml       |    46 -
 .../params/man.endnotes.are.numbered.xml           |   106 -
 .../params/man.endnotes.list.enabled.xml           |   105 -
 .../params/man.endnotes.list.heading.xml           |    36 -
 .../params/man.font.funcprototype.xml              |    30 -
 .../params/man.font.funcsynopsisinfo.xml           |    30 -
 .../docbook-xsl-1.75.1/params/man.font.links.xml   |    64 -
 .../params/man.font.table.headings.xml             |    30 -
 .../params/man.font.table.title.xml                |    30 -
 .../params/man.funcsynopsis.style.xml              |    26 -
 .../params/man.hyphenate.computer.inlines.xml      |    53 -
 .../params/man.hyphenate.filenames.xml             |    47 -
 .../params/man.hyphenate.urls.xml                  |    46 -
 .../docbook-xsl-1.75.1/params/man.hyphenate.xml    |    59 -
 .../params/man.indent.blurbs.xml                   |    33 -
 .../docbook-xsl-1.75.1/params/man.indent.lists.xml |    35 -
 .../params/man.indent.refsect.xml                  |    70 -
 .../params/man.indent.verbatims.xml                |    33 -
 .../docbook-xsl-1.75.1/params/man.indent.width.xml |    39 -
 .../docbook-xsl-1.75.1/params/man.justify.xml      |    52 -
 .../params/man.output.base.dir.xml                 |    39 -
 .../params/man.output.better.ps.enabled.xml        |    61 -
 .../params/man.output.encoding.xml                 |    53 -
 .../params/man.output.in.separate.dir.xml          |    32 -
 .../params/man.output.lang.in.name.enabled.xml     |    50 -
 .../params/man.output.manifest.enabled.xml         |    27 -
 .../params/man.output.manifest.filename.xml        |    29 -
 .../params/man.output.quietly.xml                  |    37 -
 .../params/man.output.subdirs.enabled.xml          |    40 -
 .../params/man.segtitle.suppress.xml               |    28 -
 .../params/man.string.subst.map.local.post.xml     |    34 -
 .../params/man.string.subst.map.local.pre.xml      |    34 -
 .../params/man.string.subst.map.xml                |   162 -
 .../params/man.subheading.divider.enabled.xml      |    37 -
 .../params/man.subheading.divider.xml              |    37 -
 .../params/man.table.footnotes.divider.xml         |    29 -
 .../params/man.th.extra1.suppress.xml              |    32 -
 .../params/man.th.extra2.max.length.xml            |    43 -
 .../params/man.th.extra2.suppress.xml              |    44 -
 .../params/man.th.extra3.max.length.xml            |    42 -
 .../params/man.th.extra3.suppress.xml              |    34 -
 .../params/man.th.title.max.length.xml             |    63 -
 .../params/manifest.in.base.dir.xml                |    29 -
 .../docbook/docbook-xsl-1.75.1/params/manifest.xml |    29 -
 .../docbook-xsl-1.75.1/params/manual.toc.xml       |    29 -
 .../params/margin.note.float.type.xml              |    77 -
 .../params/margin.note.properties.xml              |    54 -
 .../params/margin.note.title.properties.xml        |    32 -
 .../params/margin.note.width.xml                   |    35 -
 .../params/marker.section.level.xml                |    50 -
 .../params/menuchoice.menu.separator.xml           |    42 -
 .../params/menuchoice.separator.xml                |    32 -
 .../docbook-xsl-1.75.1/params/minus.image.xml      |    29 -
 .../params/monospace.font.family.xml               |    29 -
 .../params/monospace.properties.xml                |    38 -
 .../params/monospace.verbatim.font.width.xml       |    40 -
 .../params/monospace.verbatim.properties.xml       |    27 -
 .../params/multiframe.bottom.bgcolor.xml           |    28 -
 .../params/multiframe.navigation.height.xml        |    28 -
 .../params/multiframe.top.bgcolor.xml              |    28 -
 .../docbook-xsl-1.75.1/params/multiframe.xml       |    31 -
 .../docbook-xsl-1.75.1/params/nav.separator.xml    |    28 -
 .../params/nav.table.summary.xml                   |    27 -
 .../docbook-xsl-1.75.1/params/navbgcolor.xml       |    26 -
 .../docbook-xsl-1.75.1/params/navbodywidth.xml     |    26 -
 .../params/navig.graphics.extension.xml            |    28 -
 .../params/navig.graphics.path.xml                 |    30 -
 .../docbook-xsl-1.75.1/params/navig.graphics.xml   |    31 -
 .../docbook-xsl-1.75.1/params/navig.showtitles.xml |    32 -
 .../docbook-xsl-1.75.1/params/navtocwidth.xml      |    26 -
 .../docbook-xsl-1.75.1/params/next.image.xml       |    27 -
 .../docbook-xsl-1.75.1/params/no.home.image.xml    |    27 -
 .../docbook-xsl-1.75.1/params/no.next.image.xml    |    27 -
 .../docbook-xsl-1.75.1/params/no.prev.image.xml    |    27 -
 .../docbook-xsl-1.75.1/params/no.toc.image.xml     |    27 -
 .../docbook-xsl-1.75.1/params/no.up.image.xml      |    27 -
 .../params/nominal.image.depth.xml                 |    27 -
 .../params/nominal.image.width.xml                 |    43 -
 .../params/nominal.table.width.xml                 |    30 -
 .../params/nongraphical.admonition.properties.xml  |    41 -
 .../params/normal.para.spacing.xml                 |    26 -
 .../docbook-xsl-1.75.1/params/olink.base.uri.xml   |    35 -
 .../docbook-xsl-1.75.1/params/olink.debug.xml      |    36 -
 .../docbook-xsl-1.75.1/params/olink.doctitle.xml   |   146 -
 .../docbook-xsl-1.75.1/params/olink.fragid.xml     |    23 -
 .../params/olink.lang.fallback.sequence.xml        |    83 -
 .../params/olink.outline.ext.xml                   |    28 -
 .../docbook-xsl-1.75.1/params/olink.properties.xml |    33 -
 .../docbook-xsl-1.75.1/params/olink.pubid.xml      |    27 -
 .../docbook-xsl-1.75.1/params/olink.resolver.xml   |    23 -
 .../docbook-xsl-1.75.1/params/olink.sysid.xml      |    27 -
 .../params/orderedlist.label.properties.xml        |    26 -
 .../params/orderedlist.label.width.xml             |    28 -
 .../params/orderedlist.properties.xml              |    24 -
 .../params/othercredit.like.author.enabled.xml     |    31 -
 .../docbook-xsl-1.75.1/params/output-root.xml      |    28 -
 .../docbook-xsl-1.75.1/params/output.indent.xml    |    32 -
 .../docbook-xsl-1.75.1/params/overlay.js.xml       |    28 -
 .../docbook-xsl-1.75.1/params/overlay.logo.xml     |    28 -
 .../docbook/docbook-xsl-1.75.1/params/overlay.xml  |    32 -
 .../params/page.height.portrait.xml                |    69 -
 .../docbook-xsl-1.75.1/params/page.height.xml      |    37 -
 .../params/page.margin.bottom.xml                  |    29 -
 .../params/page.margin.inner.xml                   |    56 -
 .../params/page.margin.outer.xml                   |    53 -
 .../docbook-xsl-1.75.1/params/page.margin.top.xml  |    28 -
 .../docbook-xsl-1.75.1/params/page.orientation.xml |    32 -
 .../params/page.width.portrait.xml                 |    67 -
 .../docbook-xsl-1.75.1/params/page.width.xml       |    36 -
 .../docbook-xsl-1.75.1/params/pages.template.xml   |    29 -
 .../docbook-xsl-1.75.1/params/paper.type.xml       |    71 -
 .../params/para.propagates.style.xml               |    29 -
 .../docbook-xsl-1.75.1/params/part.autolabel.xml   |    73 -
 .../params/passivetex.extensions.xml               |    37 -
 .../params/pgwide.properties.xml                   |    52 -
 .../params/phrase.propagates.style.xml             |    29 -
 .../docbook-xsl-1.75.1/params/pixels.per.inch.xml  |    31 -
 .../docbook-xsl-1.75.1/params/plus.image.xml       |    29 -
 .../docbook-xsl-1.75.1/params/points.per.em.xml    |    29 -
 .../params/preface.autolabel.xml                   |    71 -
 .../params/prefer.internal.olink.xml               |    78 -
 .../params/preferred.mediaobject.role.xml          |    40 -
 .../docbook-xsl-1.75.1/params/prev.image.xml       |    27 -
 .../params/procedure.properties.xml                |    29 -
 .../params/process.empty.source.toc.xml            |    39 -
 .../params/process.source.toc.xml                  |    39 -
 .../docbook-xsl-1.75.1/params/profile.arch.xml     |    39 -
 .../params/profile.attribute.xml                   |    34 -
 .../docbook-xsl-1.75.1/params/profile.audience.xml |    38 -
 .../params/profile.condition.xml                   |    38 -
 .../params/profile.conformance.xml                 |    38 -
 .../docbook-xsl-1.75.1/params/profile.lang.xml     |    38 -
 .../docbook-xsl-1.75.1/params/profile.os.xml       |    38 -
 .../docbook-xsl-1.75.1/params/profile.revision.xml |    38 -
 .../params/profile.revisionflag.xml                |    38 -
 .../docbook-xsl-1.75.1/params/profile.role.xml     |    54 -
 .../docbook-xsl-1.75.1/params/profile.security.xml |    38 -
 .../params/profile.separator.xml                   |    27 -
 .../docbook-xsl-1.75.1/params/profile.status.xml   |    38 -
 .../params/profile.userlevel.xml                   |    38 -
 .../docbook-xsl-1.75.1/params/profile.value.xml    |    41 -
 .../docbook-xsl-1.75.1/params/profile.vendor.xml   |    38 -
 .../docbook-xsl-1.75.1/params/profile.wordsize.xml |    38 -
 .../docbook-xsl-1.75.1/params/punct.honorific.xml  |    28 -
 .../params/qanda.defaultlabel.xml                  |    86 -
 .../docbook-xsl-1.75.1/params/qanda.in.toc.xml     |    34 -
 .../params/qanda.inherit.numeration.xml            |    30 -
 .../params/qanda.nested.in.toc.xml                 |    29 -
 .../params/qanda.title.level1.properties.xml       |    32 -
 .../params/qanda.title.level2.properties.xml       |    32 -
 .../params/qanda.title.level3.properties.xml       |    32 -
 .../params/qanda.title.level4.properties.xml       |    32 -
 .../params/qanda.title.level5.properties.xml       |    32 -
 .../params/qanda.title.level6.properties.xml       |    34 -
 .../params/qanda.title.properties.xml              |    37 -
 .../params/qandadiv.autolabel.xml                  |    26 -
 .../docbook-xsl-1.75.1/params/rebuild-all.xml      |    33 -
 .../params/refclass.suppress.xml                   |    28 -
 .../params/refentry.date.profile.enabled.xml       |    46 -
 .../params/refentry.date.profile.xml               |    38 -
 .../params/refentry.generate.name.xml              |    33 -
 .../params/refentry.generate.title.xml             |    33 -
 .../params/refentry.manual.fallback.profile.xml    |    48 -
 .../params/refentry.manual.profile.enabled.xml     |    47 -
 .../params/refentry.manual.profile.xml             |    72 -
 .../params/refentry.meta.get.quietly.xml           |    37 -
 .../params/refentry.pagebreak.xml                  |    33 -
 .../params/refentry.separator.xml                  |    29 -
 .../params/refentry.source.fallback.profile.xml    |    49 -
 .../refentry.source.name.profile.enabled.xml       |    48 -
 .../params/refentry.source.name.profile.xml        |    89 -
 .../params/refentry.source.name.suppress.xml       |    42 -
 .../params/refentry.title.properties.xml           |    59 -
 .../params/refentry.version.profile.enabled.xml    |    47 -
 .../params/refentry.version.profile.xml            |    41 -
 .../params/refentry.version.suppress.xml           |    43 -
 .../params/refentry.xref.manvolnum.xml             |    31 -
 .../params/reference.autolabel.xml                 |    67 -
 .../params/region.after.extent.xml                 |    29 -
 .../params/region.before.extent.xml                |    29 -
 .../params/revhistory.table.cell.properties.xml    |    28 -
 .../params/revhistory.table.properties.xml         |    28 -
 .../params/revhistory.title.properties.xml         |    28 -
 .../docbook-xsl-1.75.1/params/root.filename.xml    |    29 -
 .../docbook-xsl-1.75.1/params/root.properties.xml  |    46 -
 .../docbook/docbook-xsl-1.75.1/params/rootid.xml   |    33 -
 .../params/runinhead.default.title.end.punct.xml   |    27 -
 .../params/runinhead.title.end.punct.xml           |    32 -
 .../params/running.foot.properties.xml             |    34 -
 .../docbook-xsl-1.75.1/params/sans.font.family.xml |    29 -
 .../docbook-xsl-1.75.1/params/saxon.callouts.xml   |    30 -
 .../params/saxon.character.representation.xml      |    38 -
 .../params/saxon.linenumbering.xml                 |    32 -
 .../params/saxon.tablecolumns.xml                  |    30 -
 .../docbook-xsl-1.75.1/params/script.dir.xml       |    33 -
 .../params/section.autolabel.max.depth.xml         |    32 -
 .../params/section.autolabel.xml                   |    26 -
 .../params/section.container.element.xml           |    62 -
 .../section.label.includes.component.label.xml     |    27 -
 .../params/section.level1.properties.xml           |    43 -
 .../params/section.level2.properties.xml           |    43 -
 .../params/section.level3.properties.xml           |    43 -
 .../params/section.level4.properties.xml           |    43 -
 .../params/section.level5.properties.xml           |    43 -
 .../params/section.level6.properties.xml           |    43 -
 .../params/section.properties.xml                  |    35 -
 .../params/section.title.level1.properties.xml     |    32 -
 .../params/section.title.level2.properties.xml     |    33 -
 .../params/section.title.level3.properties.xml     |    32 -
 .../params/section.title.level4.properties.xml     |    32 -
 .../params/section.title.level5.properties.xml     |    32 -
 .../params/section.title.level6.properties.xml     |    33 -
 .../params/section.title.properties.xml            |    39 -
 .../params/segmentedlist.as.table.xml              |    28 -
 .../docbook-xsl-1.75.1/params/sequential.links.xml |    25 -
 .../params/shade.verbatim.style.xml                |    36 -
 .../docbook-xsl-1.75.1/params/shade.verbatim.xml   |    30 -
 .../docbook-xsl-1.75.1/params/show.comments.xml    |    32 -
 .../docbook-xsl-1.75.1/params/show.foil.number.xml |    28 -
 .../params/show.revisionflag.xml                   |    42 -
 .../docbook-xsl-1.75.1/params/showtoc.image.xml    |    29 -
 .../params/side.float.properties.xml               |    50 -
 .../params/sidebar.float.type.xml                  |    90 -
 .../params/sidebar.float.width.xml                 |    35 -
 .../params/sidebar.properties.xml                  |    42 -
 .../params/sidebar.title.properties.xml            |    32 -
 .../params/simplesect.in.toc.xml                   |    26 -
 .../params/slide.font.family.xml                   |    31 -
 .../params/slide.title.font.family.xml             |    31 -
 .../docbook-xsl-1.75.1/params/slides.js.xml        |    28 -
 .../params/slides.properties.xml                   |    31 -
 .../docbook-xsl-1.75.1/params/spacing.paras.xml    |    30 -
 .../params/speakernote.properties.xml              |    32 -
 .../params/subscript.properties.xml                |    29 -
 .../params/superscript.properties.xml              |    29 -
 .../params/suppress.footer.navigation.xml          |    26 -
 .../params/suppress.header.navigation.xml          |    27 -
 .../params/suppress.homepage.title.xml             |    25 -
 .../params/suppress.navigation.xml                 |    28 -
 .../params/symbol.font.family.xml                  |    45 -
 .../params/table.borders.with.css.xml              |    28 -
 .../params/table.cell.border.color.xml             |    39 -
 .../params/table.cell.border.style.xml             |    42 -
 .../params/table.cell.border.thickness.xml         |    35 -
 .../params/table.cell.padding.xml                  |    32 -
 .../params/table.entry.padding.xml                 |    27 -
 .../params/table.footnote.number.format.xml        |    33 -
 .../params/table.footnote.number.symbols.xml       |    39 -
 .../params/table.footnote.properties.xml           |    39 -
 .../params/table.frame.border.color.xml            |    28 -
 .../params/table.frame.border.style.xml            |    37 -
 .../params/table.frame.border.thickness.xml        |    27 -
 .../docbook-xsl-1.75.1/params/table.properties.xml |    34 -
 .../params/table.spacer.image.xml                  |    26 -
 .../params/table.table.properties.xml              |    36 -
 .../params/tablecolumns.extension.xml              |    30 -
 .../params/target.database.document.xml            |    37 -
 .../docbook-xsl-1.75.1/params/targets.filename.xml |    32 -
 .../docbook/docbook-xsl-1.75.1/params/template.xml |    27 -
 .../docbook-xsl-1.75.1/params/tex.math.delims.xml  |    47 -
 .../docbook-xsl-1.75.1/params/tex.math.file.xml    |    42 -
 .../docbook-xsl-1.75.1/params/tex.math.in.alt.xml  |    83 -
 .../docbook-xsl-1.75.1/params/text.home.xml        |    27 -
 .../docbook-xsl-1.75.1/params/text.next.xml        |    27 -
 .../docbook-xsl-1.75.1/params/text.prev.xml        |    27 -
 .../docbook/docbook-xsl-1.75.1/params/text.toc.xml |    27 -
 .../docbook/docbook-xsl-1.75.1/params/text.up.xml  |    27 -
 .../docbook-xsl-1.75.1/params/textbgcolor.xml      |    26 -
 .../params/textdata.default.encoding.xml           |    32 -
 .../params/textinsert.extension.xml                |    62 -
 .../params/title.font.family.xml                   |    33 -
 .../params/title.margin.left.xml                   |    65 -
 .../docbook-xsl-1.75.1/params/titlefoil.html.xml   |    27 -
 .../docbook-xsl-1.75.1/params/toc.bg.color.xml     |    27 -
 .../params/toc.blank.graphic.xml                   |    28 -
 .../docbook-xsl-1.75.1/params/toc.blank.image.xml  |    27 -
 .../docbook-xsl-1.75.1/params/toc.blank.text.xml   |    27 -
 .../docbook-xsl-1.75.1/params/toc.hide.show.xml    |    33 -
 .../docbook/docbook-xsl-1.75.1/params/toc.html.xml |    27 -
 .../docbook-xsl-1.75.1/params/toc.image.xml        |    27 -
 .../docbook-xsl-1.75.1/params/toc.indent.width.xml |    34 -
 .../params/toc.line.properties.xml                 |    42 -
 .../docbook-xsl-1.75.1/params/toc.list.type.xml    |    30 -
 .../params/toc.margin.properties.xml               |    33 -
 .../docbook-xsl-1.75.1/params/toc.max.depth.xml    |    25 -
 .../params/toc.pointer.graphic.xml                 |    28 -
 .../params/toc.pointer.image.xml                   |    27 -
 .../docbook-xsl-1.75.1/params/toc.pointer.text.xml |    27 -
 .../docbook-xsl-1.75.1/params/toc.row.height.xml   |    33 -
 .../params/toc.section.depth.xml                   |    28 -
 .../params/toc.spacer.graphic.xml                  |    28 -
 .../docbook-xsl-1.75.1/params/toc.spacer.image.xml |    27 -
 .../docbook-xsl-1.75.1/params/toc.spacer.text.xml  |    27 -
 .../docbook-xsl-1.75.1/params/toc.width.xml        |    28 -
 .../docbook/docbook-xsl-1.75.1/params/ua.js.xml    |    28 -
 .../docbook-xsl-1.75.1/params/ulink.footnotes.xml  |    34 -
 .../params/ulink.hyphenate.chars.xml               |    37 -
 .../docbook-xsl-1.75.1/params/ulink.hyphenate.xml  |    35 -
 .../docbook-xsl-1.75.1/params/ulink.show.xml       |    37 -
 .../docbook-xsl-1.75.1/params/ulink.target.xml     |    29 -
 .../docbook/docbook-xsl-1.75.1/params/up.image.xml |    27 -
 .../params/use.embed.for.svg.xml                   |    33 -
 .../docbook-xsl-1.75.1/params/use.extensions.xml   |    31 -
 .../params/use.id.as.filename.xml                  |    30 -
 .../docbook-xsl-1.75.1/params/use.id.function.xml  |    32 -
 .../params/use.local.olink.style.xml               |    28 -
 .../params/use.role.as.xrefstyle.xml               |    93 -
 .../params/use.role.for.mediaobject.xml            |    56 -
 .../docbook/docbook-xsl-1.75.1/params/use.svg.xml  |    30 -
 .../params/variablelist.as.blocks.xml              |    62 -
 .../params/variablelist.as.table.xml               |    54 -
 .../params/variablelist.max.termlength.xml         |    46 -
 .../params/variablelist.term.break.after.xml       |    39 -
 .../params/variablelist.term.properties.xml        |    29 -
 .../params/variablelist.term.separator.xml         |    40 -
 .../params/verbatim.properties.xml                 |    38 -
 .../docbook-xsl-1.75.1/params/wordml.template.xml  |    29 -
 .../docbook-xsl-1.75.1/params/writing.mode.xml     |    83 -
 .../params/xbCollapsibleLists.js.xml               |    28 -
 .../docbook/docbook-xsl-1.75.1/params/xbDOM.js.xml |    28 -
 .../docbook-xsl-1.75.1/params/xbLibrary.js.xml     |    28 -
 .../docbook-xsl-1.75.1/params/xbStyle.js.xml       |    28 -
 .../docbook-xsl-1.75.1/params/xep.extensions.xml   |    31 -
 .../params/xep.index.item.properties.xml           |    36 -
 .../params/xref.label-page.separator.xml           |    38 -
 .../params/xref.label-title.separator.xml          |    36 -
 .../docbook-xsl-1.75.1/params/xref.properties.xml  |    29 -
 .../params/xref.title-page.separator.xml           |    36 -
 .../params/xref.with.number.and.title.xml          |    30 -
 .../docbook-xsl-1.75.1/profiling/profile-mode.xsl  |   239 -
 .../docbook-xsl-1.75.1/profiling/profile.xsl       |    49 -
 .../profiling/strip-attributes.xsl                 |    27 -
 .../docbook-xsl-1.75.1/profiling/xsl2profile.xsl   |   159 -
 .../docbook-xsl-1.75.1/template/titlepage.xml      |   478 -
 .../docbook-xsl-1.75.1/template/titlepage.xsl      |  1280 -
 .../docbook-xsl-1.75.1/tests/refentry.007.ns.xml   |   325 -
 .../docbook-xsl-1.75.1/tests/refentry.007.xml      |   340 -
 .../tools/bin/docbook-xsl-update                   |    53 -
 .../docbook-xsl-1.75.1/tools/make/Makefile.DocBook |   698 -
 .../docbook-xsl-1.75.1/tools/make/Makefile.combine |   182 -
 .../tools/make/Makefile.docParam                   |    59 -
 .../docbook-xsl-1.75.1/website/autolayout.xsl      |   258 -
 .../docbook-xsl-1.75.1/website/chunk-common.xsl    |   227 -
 .../docbook-xsl-1.75.1/website/chunk-tabular.xsl   |    12 -
 .../docbook-xsl-1.75.1/website/chunk-website.xsl   |    12 -
 .../docbook/docbook-xsl-1.75.1/website/head.xsl    |   316 -
 .../docbook-xsl-1.75.1/website/makefile-dep.xsl    |   143 -
 .../docbook/docbook-xsl-1.75.1/website/olink.xsl   |   297 -
 .../docbook/docbook-xsl-1.75.1/website/param.xml   |   788 -
 .../docbook/docbook-xsl-1.75.1/website/param.xsl   |    53 -
 .../docbook/docbook-xsl-1.75.1/website/rss.xsl     |   143 -
 .../docbook/docbook-xsl-1.75.1/website/tabular.xsl |   213 -
 .../docbook-xsl-1.75.1/website/toc-tabular.xsl     |   480 -
 .../docbook/docbook-xsl-1.75.1/website/toc.xsl     |   286 -
 .../docbook-xsl-1.75.1/website/website-common.xsl  |   821 -
 .../docbook-xsl-1.75.1/website/website-targets.xsl |    27 -
 .../docbook/docbook-xsl-1.75.1/website/website.xsl |   132 -
 .../docbook/docbook-xsl-1.75.1/website/xbel.xsl    |   114 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/admon.xsl |   133 -
 .../docbook-xsl-1.75.1/xhtml-1_1/annotations.xsl   |   158 -
 .../xhtml-1_1/autoidx-kimber.xsl                   |   139 -
 .../docbook-xsl-1.75.1/xhtml-1_1/autoidx-kosek.xsl |   109 -
 .../docbook-xsl-1.75.1/xhtml-1_1/autoidx-ng.xsl    |    21 -
 .../docbook-xsl-1.75.1/xhtml-1_1/autoidx.xsl       |   656 -
 .../docbook-xsl-1.75.1/xhtml-1_1/autotoc.xsl       |   632 -
 .../docbook-xsl-1.75.1/xhtml-1_1/biblio-iso690.xsl |  1300 -
 .../docbook-xsl-1.75.1/xhtml-1_1/biblio.xsl        |  1240 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/block.xsl |   435 -
 .../docbook-xsl-1.75.1/xhtml-1_1/callout.xsl       |   188 -
 .../docbook-xsl-1.75.1/xhtml-1_1/changebars.xsl    |    78 -
 .../xhtml-1_1/chunk-changebars.xsl                 |    96 -
 .../docbook-xsl-1.75.1/xhtml-1_1/chunk-code.xsl    |   638 -
 .../docbook-xsl-1.75.1/xhtml-1_1/chunk-common.xsl  |  1561 --
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/chunk.xsl |    52 -
 .../docbook-xsl-1.75.1/xhtml-1_1/chunker.xsl       |   302 -
 .../docbook-xsl-1.75.1/xhtml-1_1/chunkfast.xsl     |    69 -
 .../docbook-xsl-1.75.1/xhtml-1_1/chunktoc.xsl      |   532 -
 .../docbook-xsl-1.75.1/xhtml-1_1/component.xsl     |   395 -
 .../docbook-xsl-1.75.1/xhtml-1_1/division.xsl      |   217 -
 .../docbook-xsl-1.75.1/xhtml-1_1/docbook.xsl       |   447 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/ebnf.xsl  |   328 -
 .../docbook-xsl-1.75.1/xhtml-1_1/footnote.xsl      |   302 -
 .../docbook-xsl-1.75.1/xhtml-1_1/formal.xsl        |   390 -
 .../docbook-xsl-1.75.1/xhtml-1_1/glossary.xsl      |   564 -
 .../docbook-xsl-1.75.1/xhtml-1_1/graphics.xsl      |  1436 -
 .../docbook-xsl-1.75.1/xhtml-1_1/highlight.xsl     |    72 -
 .../docbook-xsl-1.75.1/xhtml-1_1/html-rtf.xsl      |   321 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/html.xsl  |   364 -
 .../docbook-xsl-1.75.1/xhtml-1_1/htmltbl.xsl       |   102 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/index.xsl |   264 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/info.xsl  |    44 -
 .../docbook-xsl-1.75.1/xhtml-1_1/inline.xsl        |  1445 -
 .../docbook-xsl-1.75.1/xhtml-1_1/keywords.xsl      |    36 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/lists.xsl |  1088 -
 .../docbook-xsl-1.75.1/xhtml-1_1/maketoc.xsl       |    91 -
 .../docbook-xsl-1.75.1/xhtml-1_1/manifest.xsl      |    22 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/math.xsl  |   284 -
 .../docbook-xsl-1.75.1/xhtml-1_1/oldchunker.xsl    |   176 -
 .../docbook-xsl-1.75.1/xhtml-1_1/onechunk.xsl      |    36 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/param.xsl |   431 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/pi.xsl    |  1203 -
 .../xhtml-1_1/profile-chunk-code.xsl               |   610 -
 .../docbook-xsl-1.75.1/xhtml-1_1/profile-chunk.xsl |    52 -
 .../xhtml-1_1/profile-docbook.xsl                  |   409 -
 .../xhtml-1_1/profile-onechunk.xsl                 |    36 -
 .../docbook-xsl-1.75.1/xhtml-1_1/qandaset.xsl      |   420 -
 .../docbook-xsl-1.75.1/xhtml-1_1/refentry.xsl      |   299 -
 .../docbook-xsl-1.75.1/xhtml-1_1/sections.xsl      |   541 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/synop.xsl |  1513 --
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/table.xsl |  1118 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/task.xsl  |    73 -
 .../xhtml-1_1/titlepage.templates.xsl              |  3710 ---
 .../docbook-xsl-1.75.1/xhtml-1_1/titlepage.xsl     |  1027 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/toc.xsl   |   330 -
 .../docbook-xsl-1.75.1/xhtml-1_1/verbatim.xsl      |   381 -
 .../docbook/docbook-xsl-1.75.1/xhtml-1_1/xref.xsl  |  1316 -
 .../docbook/docbook-xsl-1.75.1/xhtml/admon.xsl     |   135 -
 .../docbook-xsl-1.75.1/xhtml/annotations.xsl       |   158 -
 .../docbook-xsl-1.75.1/xhtml/autoidx-kimber.xsl    |   139 -
 .../docbook-xsl-1.75.1/xhtml/autoidx-kosek.xsl     |   109 -
 .../docbook-xsl-1.75.1/xhtml/autoidx-ng.xsl        |    21 -
 .../docbook/docbook-xsl-1.75.1/xhtml/autoidx.xsl   |   656 -
 .../docbook/docbook-xsl-1.75.1/xhtml/autotoc.xsl   |   632 -
 .../docbook-xsl-1.75.1/xhtml/biblio-iso690.xsl     |  1300 -
 .../docbook/docbook-xsl-1.75.1/xhtml/biblio.xsl    |  1240 -
 .../docbook/docbook-xsl-1.75.1/xhtml/block.xsl     |   435 -
 .../docbook/docbook-xsl-1.75.1/xhtml/callout.xsl   |   188 -
 .../docbook-xsl-1.75.1/xhtml/changebars.xsl        |    78 -
 .../docbook-xsl-1.75.1/xhtml/chunk-changebars.xsl  |    96 -
 .../docbook-xsl-1.75.1/xhtml/chunk-code.xsl        |   638 -
 .../docbook-xsl-1.75.1/xhtml/chunk-common.xsl      |  1561 --
 .../docbook/docbook-xsl-1.75.1/xhtml/chunk.xsl     |    52 -
 .../docbook/docbook-xsl-1.75.1/xhtml/chunker.xsl   |   302 -
 .../docbook/docbook-xsl-1.75.1/xhtml/chunkfast.xsl |    69 -
 .../docbook/docbook-xsl-1.75.1/xhtml/chunktoc.xsl  |   532 -
 .../docbook/docbook-xsl-1.75.1/xhtml/component.xsl |   395 -
 .../docbook/docbook-xsl-1.75.1/xhtml/division.xsl  |   217 -
 .../docbook/docbook-xsl-1.75.1/xhtml/docbook.xsl   |   447 -
 .../docbook/docbook-xsl-1.75.1/xhtml/ebnf.xsl      |   328 -
 .../docbook/docbook-xsl-1.75.1/xhtml/footnote.xsl  |   302 -
 .../docbook/docbook-xsl-1.75.1/xhtml/formal.xsl    |   390 -
 .../docbook/docbook-xsl-1.75.1/xhtml/glossary.xsl  |   564 -
 .../docbook/docbook-xsl-1.75.1/xhtml/graphics.xsl  |  1436 -
 .../docbook/docbook-xsl-1.75.1/xhtml/highlight.xsl |    72 -
 .../docbook/docbook-xsl-1.75.1/xhtml/html-rtf.xsl  |   321 -
 .../docbook/docbook-xsl-1.75.1/xhtml/html.xsl      |   364 -
 .../docbook/docbook-xsl-1.75.1/xhtml/htmltbl.xsl   |   102 -
 .../docbook/docbook-xsl-1.75.1/xhtml/index.xsl     |   264 -
 .../docbook/docbook-xsl-1.75.1/xhtml/info.xsl      |    44 -
 .../docbook/docbook-xsl-1.75.1/xhtml/inline.xsl    |  1445 -
 .../docbook/docbook-xsl-1.75.1/xhtml/keywords.xsl  |    36 -
 .../docbook/docbook-xsl-1.75.1/xhtml/lists.xsl     |  1121 -
 .../docbook/docbook-xsl-1.75.1/xhtml/maketoc.xsl   |    91 -
 .../docbook/docbook-xsl-1.75.1/xhtml/manifest.xsl  |    22 -
 .../docbook/docbook-xsl-1.75.1/xhtml/math.xsl      |   284 -
 .../docbook-xsl-1.75.1/xhtml/oldchunker.xsl        |   176 -
 .../docbook/docbook-xsl-1.75.1/xhtml/onechunk.xsl  |    36 -
 .../docbook/docbook-xsl-1.75.1/xhtml/param.xsl     |   431 -
 .../docgen/docbook/docbook-xsl-1.75.1/xhtml/pi.xsl |  1203 -
 .../xhtml/profile-chunk-code.xsl                   |   610 -
 .../docbook-xsl-1.75.1/xhtml/profile-chunk.xsl     |    52 -
 .../docbook-xsl-1.75.1/xhtml/profile-docbook.xsl   |   409 -
 .../docbook-xsl-1.75.1/xhtml/profile-onechunk.xsl  |    36 -
 .../docbook/docbook-xsl-1.75.1/xhtml/qandaset.xsl  |   420 -
 .../docbook/docbook-xsl-1.75.1/xhtml/refentry.xsl  |   299 -
 .../docbook/docbook-xsl-1.75.1/xhtml/sections.xsl  |   541 -
 .../docbook/docbook-xsl-1.75.1/xhtml/synop.xsl     |  1513 --
 .../docbook/docbook-xsl-1.75.1/xhtml/table.xsl     |  1118 -
 .../docbook/docbook-xsl-1.75.1/xhtml/task.xsl      |    73 -
 .../xhtml/titlepage.templates.xsl                  |  3710 ---
 .../docbook/docbook-xsl-1.75.1/xhtml/titlepage.xsl |  1027 -
 .../docbook/docbook-xsl-1.75.1/xhtml/toc.xsl       |   330 -
 .../docbook/docbook-xsl-1.75.1/xhtml/verbatim.xsl  |   381 -
 .../docbook/docbook-xsl-1.75.1/xhtml/xref.xsl      |  1316 -
 .../tools/docgen/docbook/iconv.dll                 |   Bin 888832 -> 0 bytes
 .../tools/docgen/docbook/libexslt.dll              |   Bin 53760 -> 0 bytes
 .../tools/docgen/docbook/libxml2.dll               |   Bin 976384 -> 0 bytes
 .../tools/docgen/docbook/libxslt.dll               |   Bin 166400 -> 0 bytes
 .../tools/docgen/docbook/xsltproc.exe              |   Bin 14336 -> 0 bytes
 .../tools/docgen/docbook/zlib1.dll                 |   Bin 73728 -> 0 bytes
 .../tools/docgen/docgen.xsl                        |    22 -
 .../tools/docgen/techpub.css                       |   245 -
 .../tools/docgen/template_engine.exe               |   Bin 58216 -> 0 bytes
 .../tools/fontchecker/.readme                      |     1 -
 .../tools/docgen/docbook/xsltproc                  |   Bin 24960 -> 0 bytes
 5301 files changed, 826612 insertions(+), 732083 deletions(-)

</pre>

End
---

