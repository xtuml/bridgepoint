@echo off

:: This command gets the location of the directory that holds this script.  We
:: use this to set up the rest of the paths for launching.  If you want to copy
:: this script elsewhere, you should modify BPHOMEDIR to explicitly set the 
:: location where BridgePoint is installed to.
set BPHOMEDIR=%~dp0\..

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

if not "%WORKSPACE%"=="" goto RunApp
  ::
  :: Since the workspace to use is not set, use the standard location.  Users may
  :: choose to set the value in the environment, or modify the following line to
  :: point to the correct location.
  ::
  set WORKSPACE=%~dp0\..\workspace
goto RunApp

:RunApp
echo Using WORKSPACE=%WORKSPACE%

::
:: DO NOT MODIFY ANY OF THE FOLLOWING LINES.
::
set LAUNCHER=%BPHOMEDIR%\eclipse\plugins\org.eclipse.equinox.launcher_1.2.0.v20110502.jar
set APPLICATION=org.xtuml.bp.cli.%1
set BP_JVM=%BPHOMEDIR%\jre\bin\java.exe

:: Save the user current working directory
pushd .

:: Check for fonts that trip up generator
cd %BPHOMEDIR%\tools\fontchecker
fontchecker.exe

:: Configure to run MinGW
call %BPHOMEDIR%\MinGW\mingwgnu.bat
@echo off

:: Run command line action
cd %BPHOMEDIR%\eclipse
set COMMAND=eclipsec.exe --launcher.suppressErrors -vm %BP_JVM% -clean -noSplash -data %WORKSPACE% -application %APPLICATION% %2 %3 %4 %5 %6 %7 %8 %9
echo %COMMAND%
%COMMAND%

:: Change back to users directory and restore the PATH
popd

:exit
