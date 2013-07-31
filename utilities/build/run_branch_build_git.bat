@echo off

rem
rem  This script runs a branch build.
rem
rem  Since it is the starting point for the build chain, it must be manually put into 
rem  place for the build server to run.
rem 

set BRANCH=1_skb_build_test


set BUILD_ROOT=C:\builds
set GIT_REPO_ROOT="/git/xtuml"
set PT_HOME=\utilities\bp_build_tools\bridgepoint
set PT_HOME_DRIVE=c:
set XTUMLGEN_HOME=c:\utilities\bp_build_tools\bridgepoint
set MGLS_DLL=c:\utilities\mgls\Mgls.dll
set MGLS_PKGINFO_FILE=c:\utilities\mgls\mgc.pkginfo
set MGLS_LICENSE_FILE=1717@wv-lic-01.wv.mentorg.com;1717@wv-lic-02.wv.mentorg.com
echo BUILD_ROOT=%BUILD_ROOT%
echo BRANCH=%BRANCH%
echo GIT_REPO_ROOT=%GIT_REPO_ROOT%
echo PT_HOME=%PT_HOME%
echo PT_HOME_DRIVE=%PT_HOME_DRIVE%
echo XTUMLGEN_HOME=%XTUMLGEN_HOME%
echo MGLS_DLL=%MGLS_DLL%
echo MGLS_PKGINFO_FILE=%MGLS_PKGINFO_FILE%
echo MGLS_LICENSE_FILE=%MGLS_LICENSE_FILE%

C:
chdir %BUILD_ROOT%
pushd .

path c:\cygwin\bin;%Path%

echo "Initializing git repositories..."
dos2unix -q init_git_repositories.sh
bash init_git_repositories.sh "%BRANCH%" "%GIT_REPO_ROOT%" "yes" > cfg_output.log
echo "Done."

echo "Initializing the eclipse bases and build tools from SVN..."
cp -f %GIT_REPO_ROOT%/internal/utilities/build/init_svn_tools.sh .
dos2unix -q init_svn_tools.sh
bash init_svn_tools.sh "%BRANCH%" "nonrelease" >> cfg_output.log
echo "Done."

echo "Setting Windows permissions on tool directories..."
icacls "c:\\utilities\\bp_build_tools\\" /grant everyone:F /t /c
icacls "c:\\utilities\\mgls\\" /grant everyone:F /t /c
icacls "c:\\MIMIC\\" /grant everyone:F /t /c
echo "Done."

echo "Configuring build process..."
cp -f %GIT_REPO_ROOT%/internal/utilities/build/configure_build_process.sh .
dos2unix -q configure_build_process.sh
bash configure_build_process.sh %BRANCH% %GIT_REPO_ROOT% nonrelease >> cfg_output.log
echo "Done."

echo "Processing the build..."
chdir %BRANCH%
bash process_build.sh %BRANCH% %GIT_REPO_ROOT% nonrelease > build_output.log 
echo "Done."

rem Clean up build files
popd
move configure_build_process.sh %BRANCH%
move init_svn_tools.sh %BRANCH%
move %BRANCH%\build_output.log %BRANCH%\log

chdir %BUILD_ROOT%

echo "End of run_nightly_build_git.bat"
