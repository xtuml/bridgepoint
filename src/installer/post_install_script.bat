@echo off

echo Starting post-install script
SET BPVER=4.2.0

:: Localization Note: To support UNICODE paths for Source, Target, or MIP 
:: location, the scripts should ignore the command line $1, $2 arguments and use
:: the MG_INSTALL_TARGET and MG_INSTALL_MSI_COMMANDER environment variables 
:: instead.

SET TARGET=%1
SET ECLIPSEDIR=%2

:: Update the following files to reflect the target location provided by the user...
echo Updating install path in config files
SET BP_PATH=MentorGraphics\BridgePoint
SET TGT_PATH=%TARGET:~3%
"%TARGET%\tools\update_text" "%TARGET%\extras\Launcher.bat" "%BP_PATH%" "%TGT_PATH%"
"%TARGET%\tools\update_text" "%TARGET%\extras\CLI.bat" "%BP_PATH%" "%TGT_PATH%"
SET BP_PATH=C:\xtuml\BridgePoint
SET TGT_PATH=%TARGET%
"%TARGET%\tools\update_text" -c "%TARGET%\tools\docgen\docgen.xsl" "%BP_PATH%" "%TGT_PATH%"
"%TARGET%\tools\update_text" "%TARGET%\MinGW\mingwgnu.bat" "%BP_PATH%" "%TGT_PATH%"
"%TARGET%\tools\update_text" -c "%TARGET%\extras\links\BridgePoint.ide.link" "%BP_PATH%" "%TGT_PATH%"
IF NOT EXIST "%TARGET%\eclipse\eclipse.ini" GOTO NoBPEclipse
  "%TARGET%\tools\update_text" "%TARGET%\eclipse\eclipse.ini" "%BP_PATH%" "%TGT_PATH%"
  :NoBPEclipse
echo Done
"%TARGET%\tools\update_text" -c "%TARGET%\eclipse_extensions\BridgePoint\eclipse\plugins\org.xtuml.bp.welcome_%BPVER%\models\GPS Watch\.classpath" "C:/MentorGraphics/BridgePoint" "%TGT_PATH%" 

:: Move the eclipse-related files from "extras/" to the eclipse directory specified in "extras/eclipsedir.txt".  This will either
:: be the eclipse installation under the BP install, or the users pre-existing eclipse installation.
echo Updating Eclipse configuration
IF EXIST "%ECLIPSEDIR%/links" GOTO LinksDirReady
  echo Creating %ECLIPSEDIR%/links
  MKDIR "%ECLIPSEDIR%/links"
  :LinksDirReady
echo Eclipse installation is at: %ECLIPSEDIR%
MOVE "%TARGET%\extras\links\com.mentor.BridgePoint.ide.link" "%ECLIPSEDIR%\links\"
MOVE "%TARGET%\extras\Launcher.bat" "%ECLIPSEDIR%/"
MOVE "%TARGET%\extras\CLI.bat" "%ECLIPSEDIR%/"
GOTO EclipseUpdateDone
:EclipseUpdateError
echo Error during discovery of eclipse location.  %ECLIPSECFGFILE% does not exist.
:EclipseUpdateDone
echo Done

:: Show release notes or not depending on their selection in the installer.
echo Release notes display (or not)
SET RNFLAGFILE=%TARGET%\extras\rnflag.txt
IF EXIST "%RNFLAGFILE%" ECHO Found the Release notes flag file %RNFLAGFILE%
START iexplore "file://%TARGET%\eclipse_extensions\BridgePoint\eclipse\plugins\org.xtuml.bp.doc_%BPVER%\ReleaseNotes\HTML\ReleaseNotes.htm"
DEL "%RNFLAGFILE%"
GOTO ReleaseNotesDone
:ReleaseNotesDone
echo Done

::Run the vcredist_x86.exe to update the runtime libraries. The redist package
::is only installed if the user has admin rights.  We determine this by checking
::the return value of the helper app.
echo Launching vcredist installer
"%TARGET%\tools\CheckPrivileges.exe"
IF %ERRORLEVEL% NEQ 1 "%TARGET%\vcredist_x86_2005SP1.exe" /q:a
echo Done

:: Initialize eclipse p2 and configuration data
echo Initializing eclipse configuration
set BP_JVM=%TARGET%\jre\bin\javaw.exe
"%TARGET%\eclipse\eclipse.exe" -vm %BP_JVM% -initialize
echo Done

:end
echo Post-install script complete
