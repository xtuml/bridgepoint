@echo off

rem
rem  This script runs the nightly build.
rem
rem  Since it is the starting point for the build chain, it must be manually put into 
rem  place for the build server to run.
rem 


set BUILD_ROOT=C:\builds
set BRANCH=master
set GIT_REPO_ROOT="/git/xtuml"
set PT_HOME='\utilities\bp_build_tools\bridgepoint'
set PT_HOME_DRIVE=c:
set XTUMLGEN_HOME='c:\utilities\bp_build_tools\bridgepoint'
echo set BUILD_ROOT=%BUILD_ROOT%
echo set BRANCH=%BRANCH%
echo set GIT_REPO_ROOT=%GIT_REPO_ROOT%
echo set PT_HOME=%PT_HOME%
echo set PT_HOME_DRIVE=%PT_HOME_DRIVE%
echo set XTUMLGEN_HOME=%XTUMLGEN_HOME%

C:
chdir %BUILD_ROOT%
pushd .

path c:\cygwin\bin;%Path%

echo Initializing git repositories...
dos2unix -q init_git_repositories.sh
bash init_git_repositories.sh "%BRANCH%" "%GIT_REPO_ROOT%" "yes" > cfg_output.log
echo Done. 

echo Initializing the eclipse bases and build tools from SVN...
cp -f %GIT_REPO_ROOT%/internal/utilities/build/init_svn_tools.sh .
dos2unix -q init_svn_tools.sh
bash init_svn_tools.sh "%BRANCH%" "nonrelease" > cfg_output.log
echo Done. 

echo Configuring build process...
cp -f %GIT_REPO_ROOT%/internal/utilities/build/configure_build_process.sh .
dos2unix -q configure_build_process.sh
bash configure_build_process.sh %BRANCH% %GIT_REPO_ROOT% nonrelease > cfg_output.log
echo Done. 

echo Processing the build...
chdir %BRANCH%
bash process_build.sh %BRANCH% %GIT_REPO_ROOT% nonrelease > build_output.log 
echo Done.

rem Clean up build files
popd
move configure_build_process.sh %BRANCH%
move init_svn_tools.sh %BRANCH%
move %BRANCH%\build_output.log %BRANCH%\log

chdir %BUILD_ROOT%

echo end of run_nightly_build_git.bat
