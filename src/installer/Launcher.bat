@echo off

::
:: DO NOT MODIFY ANY OF THE FOLLOWING LINES.
::
set ORIGINAL_PATH=%PATH%
set PATH=%PATH%;%~d0\xtuml\BridgePoint\tools\docgen\docbook
set BPHOMEDIR=%~d0\xtuml\BridgePoint
set BP_JVM=%BPHOMEDIR%\jre\bin\javaw.exe

:: Check for fonts that trip up generator
cd %BPHOMEDIR%\tools\fontchecker
fontchecker.exe

:: Configure to run MinGW
call %BPHOMEDIR%\MinGW\mingwgnu.bat
@echo off

:: Run BridgePoint
cd %BPHOMEDIR%\eclipse
start eclipse.exe -vm %BP_JVM% %1 %2 %3 %4 %5 %6 %7 %8 %9

:: Restore the PATH
set PATH=%ORIGINAL_PATH%
