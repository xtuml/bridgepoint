@echo off
REM ==========================================================================
REM
REM File:      $RCSfile: pt_debugger.cmd,v $
REM Version:   $Revision: 1.10 $
REM Modified:  $Date: 2013/01/10 22:54:31 $
REM
REM (c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
REM
REM ==========================================================================
REM This document contains information proprietary and confidential to
REM Mentor Graphics Corp., and is not for external distribution.
REM ==========================================================================
REM This file is intended to run on Windows <95|98|NT|2000>.

setlocal

set tool_name=Model Debugger
set tool_exe=bp_md.exe

set errmsg=
set actmsg=
set hdrmsg=FATAL ERROR starting %tool_name%:

REM Debug flag: set trace=x to emit trace info
set trace=

set platform=win32

if NOT .%trace% == . echo Console log for %tool_name%

REM Set the environment variables.  The "for" line sets the MD_BASE_DIR variable 
REM to the directory that contains this script.
for /f %%i in ("%0") do set MD_BASE_DIR=%%~dpi
set LOGNAME=%USERNAME: =%

if NOT exist "%MD_BASE_DIR%\%platform%" goto ercbnf

if NOT defined HOME set HOME=%USERPROFILE%
if NOT exist %HOME% set HOME=%USERPROFILE%
if NOT exist %HOME% goto erhdnf

set CLIENT_DIR=%MD_BASE_DIR%\%platform%\client
set CLIENT_BIN_DIR=%CLIENT_DIR%\bin
set CLIENT_LIB_DIR=%CLIENT_DIR%\lib

set NEW_PATH=%CLIENT_BIN_DIR%;%CLIENT_LIB_DIR%;%PATH%

set PATH=%NEW_PATH%

if NOT .%trace% == . goto trcvrs
:trcvre

if NOT exist "%CLIENT_BIN_DIR%/%tool_exe%" goto ercbnf

REM Get to the client_core directory
SET CLIENT_DIR=%CLIENT_DIR:/=\%
cd "%CLIENT_DIR%\client_core"

REM Start the tool
if NOT .%trace% == . echo - starting %tool_name%
start /b %tool_exe% %*
if ERRORLEVEL 1 goto erfail
if NOT .%trace% == . echo - %tool_name% has started successfully
goto end

:ercbnf
  set errmsg=Unable to find CLIENT files for BridgePoint installation
  set actmsg=Perhaps the BridgePoint installation is not complete
  goto errend

:erhdnf
  set errmsg=Unable to locate HOME directory for user defaults
  set actmsg=Create directory '%HOME%' and re-run %tool_name%
  goto errend

:erfail
  set errmsg=Return code %ERRORLEVEL% from %tool_name%
  set actmsg=Check output or log file for errors
  set hdrmsg=ERROR running %tool_name%:
  goto errend

:errend
  echo - %hdrmsg%
  echo -- %errmsg%
  echo -- %actmsg%
  pause
  goto end

:trcvrs
  echo - Environment variables:
  echo -- MD_BASE_DIR=%MD_BASE_DIR%
  echo -- HOME=%HOME%
  echo -- TEMP=%TEMP%
  echo -- PATH=%PATH%
  goto trcvre
:end
  endlocal
