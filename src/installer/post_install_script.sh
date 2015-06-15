#!/bin/bash
#
# DESCRIPTION:                                                          
#                                                                      
# This is a post install script                                 
#                                                                                                                                                          
# PROTOCOL :                                                            
#                                                                       
# post_install_script <target_directory> <eclipse_directory>
#                                                                                                                                         
# STANDARD SCRIPT INPUTS:                                                              
# -target_directory - path to the root installation directory               
#
# -eclipse_directory - path to the root of the eclipse installation
#               

echo Starting post-install script
BPVER=5.1.0

#===============================================================================
# UTILITY FUNCTIONS
#===============================================================================
configure_mc_files()
{
  echo "Configuring translation support files for: $MC_NAME."
  chmod 775 "$TARGET/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build"
  chmod 775 "$TARGET/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build.exe"
  chmod 775 "$TARGET/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/gen_erate.exe"
  chmod 775 "$TARGET/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/mcmc"
  echo "Done"
}

#===============================================================================
# PARSE THE COMMAND OPTIONS
#===============================================================================
TARGET=$1
ECLIPSEDIR=$2

chmod 775 "$TARGET/tools/docgen/docgen"

# Make sure these key files are in UNIX format
tr -d '\r' < "$TARGET/eclipse/eclipse.ini" > "$TARGET/eclipse/eclipse.d2u"
tr -d '\r' < "$TARGET/eclipse/Launcher.sh" > "$TARGET/eclipse/Launcher.d2u"
tr -d '\r' < "$TARGET/eclipse/CLI.sh" > "$TARGET/eclipse/CLI.d2u"
mv -f "$TARGET/eclipse/eclipse.d2u" "$TARGET/eclipse/eclipse.ini"
mv -f "$TARGET/eclipse/Launcher.d2u" "$TARGET/eclipse/Launcher.sh"
mv -f "$TARGET/eclipse/CLI.d2u" "$TARGET/eclipse/CLI.sh"
chmod +x "$TARGET/eclipse/Launcher.sh"
chmod +x "$TARGET/eclipse/CLI.sh"

echo "Copying files for wine-based generator for bp.dap."
cp "$TARGET/eclipse/plugins/org.xtuml.bp.mc.c.binary_$BPVER/mc3020/bin/msvcirt.dll" "$TARGET/eclipse/plugins/org.xtuml.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
cp "$TARGET/eclipse/plugins/org.xtuml.bp.mc.c.binary_$BPVER/mc3020/bin/msvcp60.dll" "$TARGET/eclipse/plugins/org.xtuml.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
cp "$TARGET/eclipse/plugins/org.xtuml.bp.mc.c.binary_$BPVER/mc3020/bin/msvcrt.dll" "$TARGET/eclipse/plugins/org.xtuml.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
echo "Done"

MC_NAME="org.xtuml.bp.mc.c.binary"
configure_mc_files

MC_NAME="org.xtuml.bp.mc.c.source"
configure_mc_files

MC_NAME="org.xtuml.bp.mc.cpp.source"
configure_mc_files

MC_NAME="org.xtuml.bp.mc.vhdl.source"
configure_mc_files

MC_NAME="org.xtuml.bp.mc.systemc.source"
configure_mc_files

MC_NAME="org.xtuml.bp.mc.java.source"
configure_mc_files

# Initialize eclipse p2 and configuration data
echo "Initializing eclipse configuration"
export BP_JVM=$TARGET/jre/lib/i386/client/libjvm.so
chmod +x "$TARGET/jre/bin/java"
chmod +x "$TARGET/eclipse/eclipse"
"$TARGET/eclipse/eclipse" -vm $BP_JVM -initialize &
echo "Done"

echo Post-install script complete
