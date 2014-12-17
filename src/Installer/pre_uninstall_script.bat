:: Copyright 2008-2014 Mentor Graphics Corporation. All rights reserved.
::

:: THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY
:: INFORMATION WHICH IS THE PROPERTY OF MENTOR
:: GRAPHICS CORPORATION OR ITS LICENSORS AND IS
:: SUBJECT TO LICENSE TERMS.
::

@echo off

echo Starting pre-uninstall script

:: Exit the bat file if the needed parameters are not passed.
if "%MG_INSTALL_TARGET%"=="" goto end
if "%MG_INSTALL_MSI_COMMANDER%"=="" goto end

for /f "useback tokens=*" %%a in ('%MG_INSTALL_TARGET%') do set TARGET=%%~a
set MSI_CMD=%MG_INSTALL_MSI_COMMANDER%

echo Starting file cleanup
:: These files were all modified by the post-install script.  They had internal file path updates applied.  Hence, the installer
:: sees them as changed and will not remove them.  Therefore, we remove them with this pre-uninstall script.

:: Remove the files under the target
IF EXIST "%TARGET%\tools\docgen\docgen.xsl" DEL "%TARGET%\tools\docgen\docgen.xsl"
IF EXIST "%TARGET%\MinGW\mingwgnu.bat" DEL "%TARGET%\MinGW\mingwgnu.bat"
IF EXIST "%TARGET%\eclipse\eclipse.ini" DEL "%TARGET%\eclipse\eclipse.ini"
:: Remove the eclipse files that may be under the target or under the user's own eclipse
SET ECLIPSECFGFILE=%TARGET%\extras\eclipsedir.txt
IF EXIST "%ECLIPSECFGFILE%" ECHO Found the Eclipse config file %ECLIPSECFGFILE%
IF NOT EXIST "%ECLIPSECFGFILE%" GOTO EclipseUpdateError
FOR /F "tokens=1 delims=; usebackq" %%a IN ("%ECLIPSECFGFILE%") DO SET ECLIPSEDIR=%%a
ECHO Eclipse installation is at: %ECLIPSEDIR%
IF EXIST "%ECLIPSEDIR%\links\com.mentor.BridgePoint.ide.link" DEL "%ECLIPSEDIR%\links\com.mentor.BridgePoint.ide.link"
IF EXIST "%ECLIPSEDIR%\Launcher.bat" DEL "%ECLIPSEDIR%\Launcher.bat"
IF EXIST "%ECLIPSEDIR%\CLI.bat" DEL "%ECLIPSEDIR%\CLI.bat"
DEL "%ECLIPSECFGFILE%"
GOTO EclipseUpdateDone
:EclipseUpdateError
echo Error during discovery of eclipse location.  %ECLIPSECFGFILE% does not exist.
:EclipseUpdateDone
echo Done


:end
echo Pre-uninstall script complete
