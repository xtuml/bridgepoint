@echo off
REM ==========================================================================
REM 
REM File:      $RCSfile: pt_gen_file.cmd,v $
REM Version:   $Revision: 1.13 $
REM Modified:  $Date: 2011/05/31 01:46:06 $
REM 
REM (c) Copyright 2005-2011 by Mentor Graphics Corp. All rights reserved.
REM 
REM ==========================================================================
REM This document contains information proprietary and confidential to
REM Mentor Graphics Corp. and is not for external distribution.
REM ==========================================================================
REM This file is intended to run on Windows <95|98|NT|2000>.

setlocal

set tool_name=gen_file
set tool_exe=gen_file.exe

set errmsg=
set actmsg=
set hdrmsg=FATAL ERROR starting %tool_name%:

REM Debug flag: set trace=x to emit trace info
set trace=

if NOT .%trace% == . echo Console log for %tool_name%

set platform=win32

if NOT defined XTUMLGEN_HOME goto erenv
set PT_LOG_DIR=%XTUMLGEN_HOME%/log_dir

set CLIENT_DIR=%XTUMLGEN_HOME%/%platform%/client
set CLIENT_BIN_DIR=%CLIENT_DIR%/bin
set CLIENT_LIB_DIR=%CLIENT_DIR%/lib

set NEW_PATH=%CLIENT_BIN_DIR%;%CLIENT_LIB_DIR%;%PATH%
set PATH=%NEW_PATH%

if NOT exist "%CLIENT_BIN_DIR%/%tool_exe%" goto ercbnf

REM Start the tool
if NOT .%trace% == . echo - starting %tool_name%
%CLIENT_BIN_DIR%/%tool_exe% %*
if ERRORLEVEL 1 goto erfail
if NOT .%trace% == . echo - %tool_name% has run successfully
goto end

:erenv
  set errmsg=Environment variable XTUMLGEN_HOME is not set
  set actmsg="Please set XTUMLGEN_HOME to be: <full path to DAP plugin>/bridgepoint"
  goto errend

:ercbnf
  set errmsg=Unable to find CLIENT files for BridgePoint installation
  set actmsg=Perhaps the BridgePoint installation is not complete
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

:end
  endlocal
