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
echo set BUILD_ROOT=%BUILD_ROOT%
echo set BRANCH=%BRANCH%
echo set GIT_REPO_ROOT=%GIT_REPO_ROOT%

C:
chdir %BUILD_ROOT%

path c:\cygwin\bin;%Path%

dos2unix init_git_repositories.sh
echo Initializing git repositories.
bash init_git_repositories.sh "%BRANCH%" "%GIT_REPO_ROOT%" "yes"
echo Done. 

cp -fv %GIT_REPO_ROOT%/internal/utilities/build/configure_eclipse_bases.sh .
dos2unix configure_eclipse_bases.sh
echo Configuring eclipse bases.
bash configure_eclipse_bases.sh "%BRANCH%" "nonrelease" 1>2> cfg_output.log
echo Done. 

cp -fv %GIT_REPO_ROOT%/internal/utilities/build/configure_build_process.sh .
dos2unix configure_build_process.sh
echo Configuring build process.
bash configure_build_process.sh %BRANCH% %GIT_REPO_ROOT% nonrelease 1>2> cfg_output.log
echo Done. 

rem bash create_bp_release.sh %BRANCH% %GIT_REPO_ROOT% 1>2> nb_output.log
rem change to post_process_build bash create_nightly_build.sh %BRANCH% nonrelease 1>2> nb_output.log

echo end of run_nightly_build_git.bat
