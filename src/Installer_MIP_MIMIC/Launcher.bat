@echo off
set BP_VERSION=4.2.1

::
:: DO NOT MODIFY ANY OF THE FOLLOWING LINES.
::
set ORIGINAL_PATH=%PATH%
set PATH=%PATH%;%~d0\mgc\BridgePoint\tools\docgen\docbook
set MGC_EMBEDDED_HOME=%~d0\mgc\BridgePoint
set BP_JVM=%MGC_EMBEDDED_HOME%\jre\bin\javaw.exe

:: Check for fonts that trip up generator
cd %MGC_EMBEDDED_HOME%\tools\fontchecker
fontchecker.exe

:: Configure to run MinGW
call %MGC_EMBEDDED_HOME%\MinGW\mingwgnu.bat
@echo off

:: Run BridgePoint
cd %MGC_EMBEDDED_HOME%\eclipse
start eclipse.exe -vm %BP_JVM% %1 %2 %3 %4 %5 %6 %7 %8 %9

:: Restore the PATH
set PATH=%ORIGINAL_PATH%
