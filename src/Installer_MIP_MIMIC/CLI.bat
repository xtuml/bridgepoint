@echo off
set BP_VERSION=4.2.1
set WORKSPACE=%~d0\mgc\BridgePoint\workspace

::Check that a valid argument is specified
if "%1"=="Build" goto ArgsOK
if "%1"=="Execute" goto ArgsOK
if "%1"=="Import" goto ArgsOK
if "%1"=="Merge" goto ArgsOK
echo.
echo Usage:  CLI ^<Build ^| Import ^| Execute^> ^<Argument List^>
echo.
echo Example:
echo     CLI Build -project MicrowaveOven -buildConfig Debug -cleanCLI
echo. 
echo To list complete usage information for a command:
echo     CLI Build -help
echo     CLI Import -help
echo     CLI Execute -help
echo     CLI Merge -help
echo.
echo Note that you must edit the WORKSPACE variable inside this CLI.bat
echo to point at the appropriate workspace. For example: 
echo     set WORKSPACE=c:\workspaces\mydevelopment
echo.
echo For additional information, see the Command Line Interface 
echo documentation inside the Help system.
echo. 
goto exit
:ArgsOK

  ::
:: DO NOT MODIFY ANY OF THE FOLLOWING LINES.
::
set ORIGINAL_PATH=%PATH%
set PATH=%PATH%;%~d0\mgc\BridgePoint\tools\docgen\docbook
set MGC_EMBEDDED_HOME=%~d0\mgc\BridgePoint
set LAUNCHER=%MGC_EMBEDDED_HOME%\eclipse\plugins\org.eclipse.equinox.launcher_1.2.0.v20110502.jar
set APPLICATION=com.mentor.nucleus.bp.cli.%1
set BP_JVM=%MGC_EMBEDDED_HOME%\jre\bin\java.exe

:: Save the user current working directory
pushd .

:: Check for fonts that trip up generator
cd %MGC_EMBEDDED_HOME%\tools\fontchecker
fontchecker.exe

:: Configure to run MinGW
call %MGC_EMBEDDED_HOME%\MinGW\mingwgnu.bat
@echo off

:: Run command line action
cd %MGC_EMBEDDED_HOME%\eclipse
set COMMAND=eclipsec.exe --launcher.suppressErrors -vm %BP_JVM% -clean -noSplash -data %WORKSPACE% -application %APPLICATION% %2 %3 %4 %5 %6 %7 %8 %9
echo %COMMAND%
%COMMAND%

:: Change back to users directory and restore the PATH
popd
set PATH=%ORIGINAL_PATH%

:exit
