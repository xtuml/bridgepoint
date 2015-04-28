@echo off

echo Starting post-install script
SET TARGET=%1
SET ECLIPSEDIR=%2

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
