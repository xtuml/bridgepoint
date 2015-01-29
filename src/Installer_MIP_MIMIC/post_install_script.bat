:: Copyright 2008-2014 Mentor Graphics Corporation. All rights reserved.
:: Version:   $Revision: 1.74 $
::

:: THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY
:: INFORMATION WHICH IS THE PROPERTY OF MENTOR
:: GRAPHICS CORPORATION OR ITS LICENSORS AND IS
:: SUBJECT TO LICENSE TERMS.
::

@echo off

echo Starting post-install script
SET BPVER=4.2.1

:: Localization Note: To support UNICODE paths for Source, Target, or MIP 
:: location, the scripts should ignore the command line $1, $2 arguments and use
:: the MG_INSTALL_TARGET and MG_INSTALL_MSI_COMMANDER environment variables 
:: instead.

:: Exit the bat file if the needed parameters are not passed.
if "%MG_INSTALL_TARGET%"=="" goto end
if "%MG_INSTALL_MSI_COMMANDER%"=="" goto end

:: Set short variable names.  Remove quotes that wrap MG_INSTALL_TARGET
for /f "useback tokens=*" %%a in ('%MG_INSTALL_TARGET%') do set TARGET=%%~a
set MSI_CMD=%MG_INSTALL_MSI_COMMANDER%

echo MG_INSTALL_TARGET is %MG_INSTALL_TARGET%
echo MG_INSTALL_MSI_COMMANDER is %MG_INSTALL_MSI_COMMANDER%
echo TARGET is %TARGET%
echo MSI_CMD is %MSI_CMD%

:: Update the following files to reflect the target location provided by the user...
echo Updating install path in config files
SET BP_PATH=mgc\BridgePoint
SET TGT_PATH=%TARGET:~3%
"%TARGET%\tools\update_text" "%TARGET%\extras\Launcher.bat" "%BP_PATH%" "%TGT_PATH%"
"%TARGET%\tools\update_text" "%TARGET%\extras\CLI.bat" "%BP_PATH%" "%TGT_PATH%"
SET BP_PATH=C:\mgc\BridgePoint
SET TGT_PATH=%TARGET%
"%TARGET%\tools\update_text" -c "%TARGET%\tools\docgen\docgen.xsl" "%BP_PATH%" "%TGT_PATH%"
"%TARGET%\tools\update_text" "%TARGET%\MinGW\mingwgnu.bat" "%BP_PATH%" "%TGT_PATH%"
"%TARGET%\tools\update_text" -c "%TARGET%\extras\links\com.mentor.BridgePoint.ide.link" "%BP_PATH%" "%TGT_PATH%"
IF NOT EXIST "%TARGET%\eclipse\eclipse.ini" GOTO NoBPEclipse
  "%TARGET%\tools\update_text" "%TARGET%\eclipse\eclipse.ini" "%BP_PATH%" "%TGT_PATH%"
  :NoBPEclipse
echo Done
"%TARGET%\tools\update_text" -c "%TARGET%\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.welcome_%BPVER%\models\GPS Watch\.classpath" "C:/MentorGraphics/BridgePoint" "%TGT_PATH%" 

:: Move the eclipse-related files from "extras/" to the eclipse directory specified in "extras/eclipsedir.txt".  This will either
:: be the eclipse installation under the BP install, or the users pre-existing eclipse installation.
echo Updating Eclipse configuration
SET ECLIPSECFGFILE=%TARGET%\extras\eclipsedir.txt
IF EXIST "%ECLIPSECFGFILE%" ECHO Found the Eclipse config file %ECLIPSECFGFILE%
IF NOT EXIST "%ECLIPSECFGFILE%" GOTO EclipseUpdateError
FOR /F "tokens=1 delims=; usebackq" %%a IN ("%ECLIPSECFGFILE%") DO SET ECLIPSEDIR=%%a
IF EXIST "%ECLIPSEDIR%/links" GOTO LinksDirReady
  echo Creating %ECLIPSEDIR%/links
  %MSI_CMD% -d "%ECLIPSEDIR%/links"
  :LinksDirReady
echo Eclipse installation is at: %ECLIPSEDIR%
%MSI_CMD% -m "%TARGET%\extras\links\com.mentor.BridgePoint.ide.link" "%ECLIPSEDIR%/links/"
%MSI_CMD% -m "%TARGET%\extras\Launcher.bat" "%ECLIPSEDIR%/"
%MSI_CMD% -m "%TARGET%\extras\CLI.bat" "%ECLIPSEDIR%/"
GOTO EclipseUpdateDone
:EclipseUpdateError
echo Error during discovery of eclipse location.  %ECLIPSECFGFILE% does not exist.
:EclipseUpdateDone
echo Done

:: Create the Start Menu entries. This creates the shortcut menu start->Mentor Graphics->BridgePoint which is the short cut to
:: Launcher.bat located within the <BP Dir>/eclipse folder.  Also creates desktop shortcut.  If the same named shortcut
:: exists in USERPROFILE as well as ALLUSERSPROFILE, the USERPROFILE one will be used.  Since we are storing these shortcuts
:: in ALLUSERSPROFILE, get rid of any that exist in USERPROFILE.  We also create documentation shortcuts.  Tweak the shortuct
:: we copy to the desktop to run minimized
echo Creating shortcuts
IF EXIST "%USERPROFILE%\Start Menu\Programs\Mentor Graphics\BridgePoint.lnk" DEL "%USERPROFILE%\Start Menu\Programs\Mentor Graphics\BridgePoint.lnk"
IF EXIST "%USERPROFILE%\Desktop\BridgePoint.lnk" DEL "%USERPROFILE%\Desktop\BridgePoint.lnk"
IF EXIST "%USERPROFILE%\Start Menu\Programs\Mentor Graphics\BridgePoint Documentation" RMDIR /S /Q "%USERPROFILE%\Start Menu\Programs\Mentor Graphics\BridgePoint Documentation"
%MSI_CMD% -a "Mentor Graphics" BridgePoint  "%ECLIPSEDIR%\Launcher.bat" "" "%ECLIPSEDIR%" "%TARGET%\bp.ico" 0 "" TRUE
echo Done

:: Create desktop shortcut or not depending on their selection in the installer.
echo Create desktop shortcut (or not)
SET CSFLAGFILE=%TARGET%\extras\csflag.txt
IF EXIST "%CSFLAGFILE%" ECHO Found the create shortcut flag file %CSFLAGFILE%
IF NOT EXIST "%CSFLAGFILE%" GOTO CreateShortcutDone
::"msi_cmd -k <AllUsersDesktop> <name> <command> <params> <workingDir> <iconPath> <nIcon> <shortcut> <replace> <runMinimized>"
%MSI_CMD% -k TRUE "BridgePoint" "%ECLIPSEDIR%\Launcher.bat" "" "%ECLIPSEDIR%" "%TARGET%\bp.ico" 0 "" TRUE TRUE
::"%TARGET%\tools\bp_run_min.vbs"
::cscript "%TARGET%\tools\create_shortcut.vbs" TRUE "BridgePoint" "%ECLIPSEDIR%\Launcher.bat" "" "%ECLIPSEDIR%" "%TARGET%\bp.ico" 0 "" TRUE
DEL "%CSFLAGFILE%"
GOTO CreateShortcutDone
:CreateShortcutDone
echo Done

:: Show release notes or not depending on their selection in the installer.
echo Release notes display (or not)
SET RNFLAGFILE=%TARGET%\extras\rnflag.txt
IF EXIST "%RNFLAGFILE%" ECHO Found the Release notes flag file %RNFLAGFILE%
IF NOT EXIST "%RNFLAGFILE%" GOTO ReleaseNotesDone
rundll32.exe url.dll,FileProtocolHandler "%TARGET%\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.doc_%BPVER%\ReleaseNotes\HTML\ReleaseNotes.htm"
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
