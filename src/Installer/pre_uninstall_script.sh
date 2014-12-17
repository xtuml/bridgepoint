#!/bin/bash
# Copyright 2008-2014 Mentor Graphics Corporation. All rights reserved.
# All Rights Reserved
#
#
# DESCRIPTION:                                                          
#                                                                      
# This is a pre uninstall script                                 
#                                                                                                                                                          
# PROTOCOL :                                                            
#                                                                       
# pre_uninstall_script -targetdir <target_directory>                     
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


echo Starting pre-uninstall script

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

function eclipseUpdateError()
{
	echo "Error during discovery of eclipse location.  ${ECLIPSECFGFILE} does not exist."
	exit 1
}

echo Starting file cleanup
# These files were all modified by the post-install script.  They had internal file path updates applied.  Hence, the installer
# sees them as changed and will not remove them.  Therefore, we remove them with this pre-uninstall script.

# Remove the files under the target
I'm if [ -f "$TARGET/tools/docgen/docgen.xsl" ]
then
	rm -f "$TARGET/tools/docgen/docgen.xsl"
fi
if [ -f "$TARGET/eclipse/eclipse.ini" ]
then
	rm -f "$TARGET/eclipse/eclipse.ini"
fi
# Remove the eclipse files that may be under the target or under the user's own eclipse
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
echo "Eclipse installation is at: $ECLIPSEDIR"
if [ -f "$ECLIPSEDIR/links/com.mentor.BridgePoint.ide.link" ]
then
	rm -f "$ECLIPSEDIR/links/com.mentor.BridgePoint.ide.link"
fi
rm -f "$ECLIPSECFGFILE"
echo Done

exit 0
