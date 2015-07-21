#
# File:      $RCSfile: libTRANS.mk,v $
# Version:   $Revision: 1.7 $
# Modified:  $Date: 2013/01/10 23:35:13 $
#
# (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
#
# This file builds the libREPB.dll with the following command:
#
#   nmake libTRANS.mk
#
# All files must be in the current working directory.

SOURCE=pt_trans.c
DEP_CPP_PTREP=pt_trans.h
DEF_FILE=libTRANS.def

OUTDIR=.
INTDIR=.
INCLDIR="$(SOURCE_HOME)\translate\include"

ALL : "$(OUTDIR)\libTRANS.dll"

CLEAN :
	-@erase "$(INTDIR)\pt_trans.obj"
	-@erase "$(INTDIR)\vc50.idb"
	-@erase "$(OUTDIR)\libTRANS.dll"
	-@erase "$(OUTDIR)\libTRANS.exp"
	-@erase "$(OUTDIR)\libTRANS.lib"

CPP=cl.exe
CPP_PROJ=/nologo /MT /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D\
 "WINNT" /Fp"$(INTDIR)\libTRANS.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c\
 

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib\
 advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib\
 odbccp32.lib /nologo /subsystem:windows /dll /incremental:no\
 /pdb:"$(OUTDIR)\libTRANS.pdb" /machine:Ix86 /def:"$(DEF_FILE)"\
 /out:"$(OUTDIR)\libTRANS.dll" /implib:"$(OUTDIR)\libTRANS.lib" 
LINK32_OBJS= \
	"$(INTDIR)\pt_trans.obj"

"$(OUTDIR)\libTRANS.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<


"$(INTDIR)\pt_trans.obj" : $(SOURCE) $(DEP_CPP_PTREP)
	$(CPP) $(CPP_PROJ) $(SOURCE)


