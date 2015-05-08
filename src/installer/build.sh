#!/bin/bash

# check arguments
if [ $# -ne 3 ]; then
    echo
    echo "Usage: ./build.sh <staging_path> <izpack_path> <output_dir>"
    echo "      staging_path -- path to the location of the Eclipse bases and BridgePoint deliverables"
    echo "      izpack_path -- path to the root of the izpack installation"
    echo "      output_dir -- path to the location to output the installers"
    echo
    exit 0
fi

# copy additional files to staging directory

mkdir $1/installer_extras && cp 1F.png TARGET_LINUX.txt TARGET_WINDOWS.txt bp.ico post_install_script.bat post_install_script.sh shortcutSpec.xml welcome.html $1/installer_extras
if [ $? -ne 0 ]; then
    rm -rf $1/installer_extras
    exit $?
fi

# build windows installer
$2/install/bin/compile install_windows.xml -b $1 -o $3/BrigePoint_Windows.jar

# build linux installer
$2/install/bin/compile install_linux.xml -b $1 -o $3/BrigePoint_Linux.jar

# clean up by removing temporary directory
rm -rf $1/installer_extras
