rem
rem  This script runs the nightly build.
rem 

@echo off

C:
chdir C:\nightly_build

path c:\cygwin\bin;%Path%

set BRANCH=HEAD
echo set BRANCH=%BRANCH%

set CVSROOT=:pserver:build:bui!db0y@tucson.wv.mentorg.com:/arch1/products/tiger/repository
cvs -d'%CVSROOT%' export -f -r '%BRANCH%' 'com.mentor.nucleus.bp.core/tools/configure_build_process.sh'
cp -fv com.mentor.nucleus.bp.core/tools/configure_build_process.sh .
rm -rf com.mentor.nucleus.bp.core
unix2dos configure_build_process.sh

bash configure_build_process.sh %BRANCH% 1>2> cfg_output.log
bash create_nightly_build.sh %BRANCH% nonrelease 1>2> nb_output.log

echo end of run_nightly_build.bat
