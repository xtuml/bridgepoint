rem
rem  This script runs the nightly build.
rem 

@echo off

set BUILD_ROOT=C:\builds
set BRANCH=master
set GIT_REPO_ROOT="/git/xtuml"
echo set BUILD_ROOT=%BUILD_ROOT%
echo set BRANCH=%BRANCH%
echo set GIT_REPO_ROOT=%GIT_REPO_ROOT%

C:
chdir %BUILD_ROOT%

path c:\cygwin\bin;%Path%

unix2dos init_git_repositories.sh
bash init_git_repositories.sh "%BRANCH%" "%GIT_REPO_ROOT%" "yes"

cp -fv %GIT_REPO_ROOT%/internal/utilities/build/configure_build_process.sh .
unix2dos configure_build_process.sh
bash configure_build_process.sh %BRANCH% %GIT_REPO_ROOT% nonrelease 1>2> cfg_output.log

bash create_bp_release.sh %BRANCH% %GIT_REPO_ROOT% 1>2> nb_output.log
rem change to post_process_build bash create_nightly_build.sh %BRANCH% nonrelease 1>2> nb_output.log

echo end of run_nightly_build_git.bat
