@echo off

set ORIGINAL_DIR=%CD%

:: BPHOMEDIR is used at runtime to determine the base installation folder.
:: It is the BridgePoint folder.
cd "%~dp0/.."
set BPHOMEDIR=%CD%

:: The JVM tested with BridgePoint
set BP_JVM=%BPHOMEDIR%/jre/bin/javaw.exe

:: Check for fonts that trip up generator
cd %BPHOMEDIR%/tools/fontchecker
fontchecker.exe

:: Configure to run MinGW
call %BPHOMEDIR%/MinGW/mingwgnu.bat

:: Run BridgePoint
cd %BPHOMEDIR%/eclipse
start eclipse.exe -vm %BP_JVM% %1 %2 %3 %4 %5 %6 %7 %8 %9

cd %ORIGINAL_DIR%
