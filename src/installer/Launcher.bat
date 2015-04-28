@echo off

set ORIGINAL_DIR=%CD%

:: The eclipse directory. The Launcher is expected to be in this folder
set DIR=%~dp0%

:: BPHOMEDIR is used at runtime to determine the base installation folder.
set BPHOMEDIR=%~dp0%/..

:: The JVM tested with BridgePoint
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
