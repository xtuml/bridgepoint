#!/bin/bash
# Copyright 2008-2014 Mentor Graphics Corporation. All rights reserved.
#
#
# DESCRIPTION:                                                          
#                                                                      
# This is a post install script                                 
#                                                                                                                                                          
# PROTOCOL :                                                            
#                                                                       
# post_install_script -targetdir <target_directory>                     
#    -msicmd <msi_cmd> -mgchome <mgc_home_path> -vco <mgc_vco>          
#                                                                                                                                         
# STANDARD SCRIPT INPUTS:                                                              
# -target <targetdir> - path to the root installation directory               
#               
# -msicmd <path_to_msi_cmd> - program for logging install actions
#                            
# -mgchome <path_to_mgc_home> - MGC_HOME setting 
#
# -vco <mgc_vco> - the Vendor-Cpu-Os code for the platform being installed
#
# THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY
# INFORMATION WHICH IS THE PROPERTY OF MENTOR
# GRAPHICS CORPORATION OR ITS LICENSORS AND IS
# SUBJECT TO LICENSE TERMS.
#

echo Starting post-install script
BPVER=4.2.1

#===============================================================================
# UTILITY FUNCTIONS
#===============================================================================
configure_mc_files()
{
  echo "Moving files for wine-based generator for: $MC_NAME."
  ${MSI_CMD} -c "$TARGET/extras/wine_addons/MGLS.DLL" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin"
  ${MSI_CMD} -c "$TARGET/extras/wine_addons/mgc.pkginfo" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin"
  ${MSI_CMD} -c "$TARGET/extras/wine_addons/msvcirt.dll" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin"
  ${MSI_CMD} -c "$TARGET/extras/wine_addons/msvcp60.dll" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin"
  ${MSI_CMD} -c "$TARGET/extras/wine_addons/msvcrt.dll" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin"

  ${MSI_CMD} -c "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build.exe" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build.exe.win"
  dos2unix < "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build" > "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build.exe"
  chmod 775 "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/${MC_NAME}_${BPVER}/mc3020/bin/xtumlmc_build.exe"
  echo "Done"
}

#===============================================================================
# PARSE THE COMMAND OPTIONS
#===============================================================================
TARGET=""
MSI_CMD=""
MGC_HOME=""
VCO=""

while [ $# -ne 0 ] ; do
   case $1 in

 -target )
   shift
   TARGET="$1"
   ;;

 -msicmd )
   shift
   MSI_CMD="$1"
   ;;

 -mgchome )
   shift
   MGC_HOME="$1"
   ;;

 -vco )
   shift
   VCO="$1"
   ;;
 *)
 ;;
esac
shift
done

# Update the following files to reflect the target location provided by the user...
echo "Updating install path in config files"
BP_PATH=C:/mgc/BridgePoint
sed -e 's;'"$BP_PATH"';'"$TARGET"';g' -i "$TARGET/extras/links/com.mentor.BridgePoint.ide.link"
sed -e 's;'"$BP_PATH"';'"$TARGET"';g' -i "$TARGET/extras/Launcher.sh"
sed -e 's;'"$BP_PATH"';'"$TARGET"';g' -i "$TARGET/extras/CLI.sh"
sed -e 's;'"$BP_PATH"';'"$TARGET"';g' -i "$TARGET/tools/docgen/docgen.xsl"
if [ -f "$TARGET/eclipse/eclipse.ini" ]
then
  sed -e 's;'"$BP_PATH"';'"$TARGET"';g' -i "$TARGET/eclipse/eclipse.ini"
fi
sed -e 's;'"C:/MentorGraphics/BridgePoint"';'"$TARGET"';g' -i "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.welcome_$BPVER/models/GPS Watch/.classpath"

# Move the eclipse-related files from "extras/" to the eclipse directory specified in "extras/eclipsedir.txt".  This will either
# be the eclipse installation under the BP install, or the users pre-existing eclipse installation.
function eclipseUpdateError()
{
	echo "Error during discovery of eclipse location.  ${ECLIPSECFGFILE} does not exist."
	exit 1;
}

echo "Updating Eclipse configuration"

ECLIPSECFGFILE="$TARGET/extras/eclipsedir.txt"
if [ -f "$ECLIPSECFGFILE" ]
then
	echo "Found the Eclipse config file $ECLIPSECFGFILE"
fi
if [ ! -f "$ECLIPSECFGFILE" ]
then
	eclipseUpdateError
fi
ECLIPSEDIR=`grep e "$ECLIPSECFGFILE" | sed -e "s/;//"`
if [ ! -d "$ECLIPSEDIR/links" ]
then
  echo "Creating $ECLIPSEDIR/links"
  ${MSI_CMD} -d "$ECLIPSEDIR/links"
fi
echo "Eclipse installation is at: $ECLIPSEDIR"

${MSI_CMD} -m "$TARGET/extras/links/com.mentor.BridgePoint.ide.link" "$ECLIPSEDIR/links/"
${MSI_CMD} -m "$TARGET/extras/Launcher.sh" "$ECLIPSEDIR/"
${MSI_CMD} -m "$TARGET/extras/CLI.sh" "$ECLIPSEDIR/"
echo "Done"

dos2unix "$TARGET/eclipse/eclipse.ini"
dos2unix "$TARGET/eclipse/Launcher.sh"
dos2unix "$TARGET/eclipse/CLI.sh"

# Move files need by Wine-based generator
echo "Moving files for wine-based generator for bp.dap."
${MSI_CMD} -c "$TARGET/extras/wine_addons/MGLS.DLL" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
${MSI_CMD} -c "$TARGET/extras/wine_addons/mgc.pkginfo" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
${MSI_CMD} -c "$TARGET/extras/wine_addons/msvcirt.dll" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
${MSI_CMD} -c "$TARGET/extras/wine_addons/msvcp60.dll" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
${MSI_CMD} -c "$TARGET/extras/wine_addons/msvcrt.dll" "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.dap.pkg_$BPVER/bridgepoint/win32/client/bin"
echo "Done"

MC_NAME="com.mentor.nucleus.bp.mc.c.binary"
configure_mc_files

MC_NAME="com.mentor.nucleus.bp.mc.c.source"
configure_mc_files

MC_NAME="com.mentor.nucleus.bp.mc.cpp.source"
configure_mc_files

MC_NAME="com.mentor.nucleus.bp.mc.vhdl.source"
configure_mc_files

MC_NAME="com.mentor.nucleus.bp.mc.systemc.source"
configure_mc_files
# Done moving files for wine-based generation

# Show release notes or not depending on their selection in the installer.
echo "Release notes display (or not)"
RNFLAGFILE="$TARGET/extras/rnflag.txt"
if [ -f "$RNFLAGFILE" ]
then
	echo "Found the Release notes flag file $RNFLAGFILE"
	firefox "$TARGET/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.doc_$BPVER/ReleaseNotes/HTML/ReleaseNotes.htm" &
	rm -f "$RNFLAGFILE"
fi
echo Done

# Initialize eclipse p2 and configuration data
echo "Initializing eclipse configuration"
export BP_JVM=$TARGET/jre/lib/i386/client/libjvm.so
"$TARGET/eclipse/eclipse" -vm $BP_JVM -initialize &
echo "Done"

echo Post-install script complete
