@echo off

set ORIGINAL_DIR=%CD%

:: This command gets the location of the directory that holds this script.  We
:: use this to set up the rest of the paths for launching.  If you want to copy
:: this script elsewhere, you should modify BPHOMEDIR to explicitly set the 
:: location where BridgePoint is installed to.
set BPHOMEDIR=%~dp0%

::
:: DO NOT MODIFY ANY OF THE FOLLOWING LINES.
::
set BP_JVM=%BPHOMEDIR%/../jre/bin/javaw.exe

:: Check for fonts that trip up generator
cd %BPHOMEDIR%/../tools/fontchecker
fontchecker.exe

:: Configure to run MinGW
call %BPHOMEDIR%/../MinGW/mingwgnu.bat

:: Run BridgePoint
cd %BPHOMEDIR%
start eclipse.exe -vm %BP_JVM% %1 %2 %3 %4 %5 %6 %7 %8 %9

cd %ORIGINAL_DIR%
