@echo off
set BP_VERSION=4.1.9

if not "%MGLS_LICENSE_FILE%"=="" goto SetCommonVariables
if not "%LM_LICENSE_FILE%"=="" goto LMLicenseConfigured
  ::
  :: Since the license path is not already set, use the standard location.  Users may
  :: choose to set the value in the environment, or modify the following line to point to the correct
  :: location (either local or remote).
  ::
  :: example configuration for a remote license server
  ::    set MGLS_LICENSE_FILE=1717@svr-taz-eng-05
  ::
  :: example configuration to query the user for the remote license server IP address
  ::   set /p ip=Type in the IP address of the license server:
  ::  set MGLS_LICENSE_FILE=1717@%ip%

  set MGLS_LICENSE_FILE=%~d0\mgc\BridgePoint\license\license.dat
goto SetCommonVariables

:LMLicenseConfigured
set MGLS_LICENSE_FILE=%LM_LICENSE_FILE%

:SetCommonVariables
echo Using MGLS_LICENSE_FILE=%MGLS_LICENSE_FILE%

::
:: DO NOT MODIFY ANY OF THE FOLLOWING LINES.
::
set ORIGINAL_PATH=%PATH%
set PATH=%PATH%;%~d0\mgc\BridgePoint\tools\docgen\docbook
set MGC_EMBEDDED_HOME=%~d0\mgc\BridgePoint
set MGLS_DLL=
set MGLS_PKGINFO_FILE=
set MGLS_HOME=
set MGC_HOME=%MGC_EMBEDDED_HOME%\eclipse_extensions\BridgePoint\eclipse\plugins\com.mentor.nucleus.bp.core.win32.x86_%BP_VERSION%\os\win32\x86
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
