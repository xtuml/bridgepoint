#!/bin/ksh
#=====================================================================
#
# File:      $RCSfile: mk_cds_rdme_mrc_BridgePoint.ksh,v $
# Version:   $Revision: 1.21 $
# Modified:  $Date: 2013/06/14 20:06:03 $
#
#(c) Copyright 2004-2010 by Mentor Graphics Corp. All rights reserved.
#
#=====================================================================
# This document contains information proprietary and confidential to
# Mentor Graphics Corp. and is not for external distribution.
#=====================================================================
# This script is used to create the README.txt, release_documents directory, and
# mrc.html files for BridgePoint CDs.
#
# file used:
# templates
#

MGLS_DOC_DIR=mgc_lic_v2013_2

# Debug trace command
# set -x
if [ `/usr/mgc/bin/mgcvco` = "ixl" ] ; then
   VI=/bin/vi
else 
   VI=/usr/bin/vi
fi
# who to mail data to
read email?"Enter your e-mail address [keith_brown@mentor.com]: "
if [ "${email}" = "" ]; then
  email=keith_brown@mentor.com
fi
MAIL_TO=${email}
# variable to check for valid input
good=1

# What version of software is the CD for
read version?"What version of software? (example 3.0): "
under_version=`echo $version | sed "s/\./_/g"`

# loop until we get a valid release location
good=1
use_existing_dir=0
while [ $good -eq 1 ] ; do
   for dir in `ls -d /wv/svd_for_cd/*_${version}_* /wv/svd_for_cd/*_${version}` ; do
      if [ -d $dir ] ; then
         echo $dir
      fi
   done
   read rls_loc?"What release location to use? [/wv/svd_for_cd/BridgePoint_${version}]: "
   if [ -z ${rls_loc} ]; then
      rls_loc=/wv/svd_for_cd/BridgePoint_${version}
   fi
   if [ -d $rls_loc ] ; then
      TITLE=`echo ${rls_loc} | sed -e "s#.*/For_CD/##g" -e "s#.*/svd_for_cd/##g"`
      SUBJECT=`echo ${TITLE} | sed "s#_# #g"`
      DATE=`date "+%B %d, %Y"`
      good=0
      use_existing_dir=1
      read create?"Directory $rls_loc already exists.  Do you wish to delete it before proceeding? "
      case $create in
         y|Y)
            rm -rf $rls_loc
            mkdir -p $rls_loc
            use_existing_dir=0
         ;;
      esac
   else
      read create?"Directory $rls_loc does not exist.  Do you wish to create it? "
      case $create in
         y|Y)
            mkdir -p $rls_loc
            TITLE=`echo ${rls_loc} | sed -e "s#.*/For_CD/##g" -e "s#.*/svd_for_cd/##g"`
            SUBJECT=`echo ${TITLE} | sed "s#_# #g"`
            DATE=`date "+%B %d, %Y"`
            good=0
         ;;
         *)
            echo "Exiting..."
            exit 1
         ;;
      esac
   fi
done

read rls_num?"What release number is this release? (SKU) "
ESDM_PATH="${rls_num}_esdm"
read REASON?"Why are we doing this release? "

# copy from release install creation area to cd and web locations
mkdir -p $rls_loc/web
for installer in `ls -1 /user/$USER/downloads/BridgePoint/installers/BridgePoint_*${under_version}*.ixl /user/$USER/downloads/BridgePoint/installers/BridgePoint_*${version}*.ixl` ; do
   mkdir -p ${rls_loc}/cd_1
   echo "Found $installer"
   read type?"What type of release? (release|patch) "
   if [ -f ${rls_loc}/cd_1/BridgePoint*ixl ] ; then
     echo "Removing existing Linux installer in ${rls_loc}/cd_1/"
     rm -f ${rls_loc}/cd_1/BridgePoint*ixl
   fi
   if [ -f ${rls_loc}/web/BridgePoint*ixl ] ; then
     echo "Removing existing Linux installer in ${rls_loc}/web/"
     rm -f ${rls_loc}/web/BridgePoint*ixl
   fi   
   echo "Copying $installer..."
   cp $installer ${rls_loc}/cd_1
   cp $installer ${rls_loc}/web
done
for installer in `ls -1 /user/$USER/downloads/BridgePoint/installers/BridgePoint_*${under_version}*.exe /user/$USER/downloads/BridgePoint/installers/BridgePoint_*${version}*.exe` ; do
   mkdir -p ${rls_loc}/cd_2
   echo "Found $installer"
   read type?"What type of release? (release|patch) "
   if [ -f ${rls_loc}/cd_2/BridgePoint*exe ] ; then
     echo "Removing existing Windows installer in ${rls_loc}/cd_2/"
     rm -f ${rls_loc}/cd_2/BridgePoint*exe
   fi
   if [ -f ${rls_loc}/web/BridgePoint*exe ] ; then
     echo "Removing existing Windows installer in ${rls_loc}/web/"
     rm -f ${rls_loc}/web/BridgePoint*exe
   fi   
   echo "Copying $installer..."
   cp $installer ${rls_loc}/cd_2
   cp $installer ${rls_loc}/web
done

if [ $use_existing_dir -eq 1 ] ; then
    read do_skip_docs?"Do you want to skip re-creating the documentation? "
    case $do_skip_docs in
      y|Y)
          echo "Exiting..."
          exit 1
       ;;
    esac    
fi

rls_nam=`basename ${rls_loc}`
rm ${rls_loc}/${rls_nam}_mrc.txt
mkdir -p $rls_loc/cd_1/release_documents

# loop on cd locations
found="false"
for cd in `find $rls_loc -follow -type d -print | egrep "/cd_" | egrep -v "release_documents|cd_[0-9][0-9]*/" ` ; do
   RELEASE_PATH="${RELEASE_PATH}<u>$cd</u><br>"
   cd $cd
   echo "Working on $cd"
   if [ ! -d release_documents ] ; then
      mkdir release_documents
   fi
   PLATS="ixn and ixe"

   # copy documents to release_docs from install at /wv/svd_for_cd/install/BridgePoint/<type>/<version>/<plat>/...
   cp /wv/kry121/For_CD/templates/MGC_LICENSE.txt $cd
   # create README.txt
   newtitle=`echo $TITLE | sed "s/_/ /g"`
   today=`date "+%B %d, %Y"`
   year=`date "+%Y"`

   cd $cd

   BP_doc=/wv/seamlessweb/usr3/downloads/BridgePoint/MentorGraphics/BridgePoint/eclipse_extensions/BridgePoint/eclipse/plugins/com.mentor.nucleus.bp.doc_${version}
   if [ ! -d ../web/${rls_num}/docs/htmldocs ] ; then
      mkdir -p ../web/${rls_num}/docs/htmldocs
   fi
   if [ ! -d ../web/${rls_num}/docs/pdfdocs ] ; then
      mkdir -p ../web/${rls_num}/docs/pdfdocs
   fi
   if [ ! -d ../web/${rls_num}/release_docs ] ; then
      mkdir -p ../web/${rls_num}/release_docs
   fi

   echo "===========================================================================" > README.txt
   echo "$newtitle Release README                  $today" >> README.txt
   echo "" >> README.txt
   echo "Copyright $year Mentor Graphics Corporation." >> README.txt
   echo "" >> README.txt
   echo "The following products are available on this CD." >> README.txt
   echo "Part Number      Price List Description" >> README.txt
   echo "" >> README.txt
   
   
   echo "255043,          BP/xtUML Basic Translate C Ap SW"  >> README.txt
   echo "254988,          BP/xtUML Custom Translate C Ap SW"  >> README.txt
   echo "254990,          BP/xtUML Custom Translate C++ Ap SW"  >> README.txt
   echo "255018,          BP/xtUML Custom Translate SysC Ap SW"  >> README.txt
   echo "255017,          BP/xtUML Custom Translate SysC-TLM Ap SW"  >> README.txt
   echo "255044,          BP/xtUML Translate C Op SW"  >> README.txt
   echo "255031,          BP/xtUML Translate C++ Op SW"  >> README.txt
   echo "254989,          BP/xtUML Translate SysC Op SW"  >> README.txt
   echo "255015,          BP/xtUML Translate SysC-TLM Op SW"  >> README.txt
   echo "255041,          BP/xtUML eXecute Ap SW"  >> README.txt

   echo "255043,          BP/xtUML Basic Translate C Ap SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "254988,          BP/xtUML Custom Translate C Ap SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "254990,          BP/xtUML Custom Translate C++ Ap SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "255018,          BP/xtUML Custom Translate SysC Ap SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "255017,          BP/xtUML Custom Translate SysC-TLM Ap SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "255044,          BP/xtUML Translate C Op SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "255031,          BP/xtUML Translate C++ Op SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "254989,          BP/xtUML Translate SysC Op SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "255015,          BP/xtUML Translate SysC-TLM Op SW"  >> ${rls_loc}/${rls_nam}_mrc.txt
   echo "255041,          BP/xtUML eXecute Ap SW"  >> ${rls_loc}/${rls_nam}_mrc.txt

   echo "" >> README.txt
   echo "This CD contains the following install executables: " >> README.txt
   if [ -f BridgePoint*.exe ] ; then
       echo "BridgePoint_<version>.exe" >> README.txt
   else
       echo "BridgePoint_<version>.ixl" >> README.txt
   fi
   echo "" >> README.txt
   echo "===========================================================================" >> README.txt
   echo "" >> README.txt
   echo "This CD contains release highlights and addtional documentation to help" >> README.txt
   echo "you with the installation and licensing of Mentor Graphics tools. The " >> README.txt
   echo "following documents are available at the top level of this CD or in the" >> README.txt
   echo "'release_documents' directory and can be accessed before you install" >> README.txt
   echo "your software:" >> README.txt
   echo "" >> README.txt
   echo "+ $newtitle Installation Instructions" >> README.txt
   echo "  =========================================================================" >> README.txt
   echo "  Filename: release_documents/${rls_nam}_Install.pdf" >> README.txt
   echo "  Contains instructions for installing this release." >> README.txt
   echo "" >> README.txt
   echo "+ $newtitle Release Highlights" >> README.txt
   echo "  =========================================================================" >> README.txt
   echo "  Filename: release_documents/${rls_nam}_Release_Highlights.pdf" >> README.txt
   echo "  Contains highlights of the new features and changes for this release." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint Release Notes" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/ReleaseNotes/HTML/ReleaseNotes.htm ] ; then
      cp -r ${BP_doc}/ReleaseNotes release_documents
      cp -r ${BP_doc}/ReleaseNotes ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/ReleaseNotes/HTML/ReleaseNotes.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint Release Notes"
      echo ""
      echo ""
   fi
   echo "  Details important information about the release." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint What's New" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/WhatsNew/HTML/WhatsNew.htm ] ; then
      cp -r ${BP_doc}/WhatsNew release_documents
      cp -r ${BP_doc}/WhatsNew ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/WhatsNew/HTML/WhatsNew.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint What's New"
      echo ""
      echo ""
   fi
   echo "  Details what is new." >> README.txt
   echo "" >> README.txt
   echo "+ The Style Guide to xtUML Modeling" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/Reference/StyleGuide/PDF/StyleGuide.pdf ] ; then
      cp -r ${BP_doc}/Reference/StyleGuide release_documents
      cp -r ${BP_doc}/Reference/StyleGuide ../web/${rls_num}/docs/pdfdocs
      echo "  Filename: release_documents/Reference/StyleGuide/PDF/StyleGuide.pdf" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find PDF for xtUML Style Guide"
      echo ""
      echo ""
   fi
   echo "  Provide detailed input for the successful use and application of xtUML model elements." >> README.txt
   echo "" >> README.txt
   echo "+ The xtUML Modeling Guide" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/Reference/DesignGuide/PDF/DesignGuide.pdf ] ; then
      cp -r ${BP_doc}/Reference/DesignGuide release_documents
      cp -r ${BP_doc}/Reference/DesignGuide ../web/${rls_num}/docs/pdfdocs
      echo "  Filename: release_documents/Reference/DesignGuide/PDF/DesignGuide.pdf" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find PDF for xtUML Design Guide"
      echo ""
      echo ""
   fi
   echo "  Sets out the application and methodology to achieve effective results with xtUML, and help new users quickly become productive." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint Getting Started" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/GettingStarted/HTML/GettingStarted.htm ] ; then
      cp -r ${BP_doc}/GettingStarted release_documents
      cp -r ${BP_doc}/GettingStarted ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/GettingStarted/HTML/GettingStarted.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint Getting Started"
      echo ""
      echo ""
   fi
   echo "  Describes how to start using BridgePoint." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint Tips and Tricks" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d ${BP_doc}/TipsAndTricks ] ; then
      cp -r ${BP_doc}/TipsAndTricks release_documents
      cp -r ${BP_doc}/TipsAndTricks ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/TipsAndTricks/HTML/TipsAndTricks.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint Tips And Tricks"
      echo ""
      echo ""
   fi
   echo "  Helpful ideas for increasing your productivity with BridgePoint." >> README.txt
   echo "" >> README.txt
   echo "+ Generic Package Migration Guide" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/GPMigrationGuide/HTML/GPMigrationGuide.htm ] ; then
      cp -r ${BP_doc}/GPMigrationGuide release_documents
      cp -r ${BP_doc}/GPMigrationGuide ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/GPMigrationGuide/HTML/GPMigrationGuide.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Generic Package Migration Guide"
      echo ""
      echo ""
   fi
   echo "  Instructions and tips for migrating from specialized packages to generic packages." >> README.txt
   echo "" >> README.txt
   echo "+ Linux Guide" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/LinuxGuide/HTML/LinuxGuide.htm ] ; then
      cp -r ${BP_doc}/LinuxGuide release_documents
      cp -r ${BP_doc}/LinuxGuide ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/LinuxGuide/HTML/LinuxGuide.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Linux Guide"
      echo ""
      echo ""
   fi
   echo "  Instructions and tips for using BridgePoint on Linux." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint User Interface xtUMLModeling Perspective" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d ${BP_doc}/Reference/UserInterface/xtUMLModeling ] ; then
      cp -r ${BP_doc}/Reference/UserInterface/xtUMLModeling release_documents
      cp -r ${BP_doc}/Reference/UserInterface/xtUMLModeling ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/xtUMLModeling/HTML/xtUMLModeling.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint User Interface xtUMLModeling Manual"
      echo ""
      echo ""
   fi
   echo "  Describes the xtUML Modeling perspective and what tools it provides the user for developing xtUML Models." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint and Configuration Management" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/BridgePointAndConfigurationManagement/HTML/BridgePointAndConfigurationManagement.htm ] ; then
      cp -r ${BP_doc}/BridgePointAndConfigurationManagement release_documents
      cp -r ${BP_doc}/BridgePointAndConfigurationManagement ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/BridgePointAndConfigurationManagement/HTML/BridgePointAndConfigurationManagement.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint And Configuration Management"
      echo ""
      echo ""
   fi
   echo "  Instructions and tips for integrating BridgePoint with a configuration management system." >> README.txt
   echo "" >> README.txt
   echo "+ Object Action Language Manual" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d  ${BP_doc}/Reference/OAL ] ; then
      cp -r ${BP_doc}/Reference/OAL release_documents
      cp -r ${BP_doc}/Reference/OAL ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/OAL/HTML/bpalref.book.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Object Action Language Manual"
      echo ""
      echo ""
   fi
   echo "  Reference and general users guide to aid in the correct specification of action semantics for UML models. " >> README.txt
   echo "" >> README.txt
   echo "+ Rule Specification Language" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d ${BP_doc}/Reference/RSL ] ; then
      cp -r ${BP_doc}/Reference/RSL release_documents
      cp -r ${BP_doc}/Reference/RSL ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/RSL/HTML/archetypes-2.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Rule Specification Language"
      echo ""
      echo ""
   fi
   echo "  Describes the Rule Specification Language which is used to write the" >> README.txt
   echo "  translation rules that make up a model compiler." >> README.txt
   echo "" >> README.txt
   echo "+ Translation Reference" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d  ${BP_doc}/Reference/Translation ] ; then
      cp -r ${BP_doc}/Reference/Translation release_documents
      cp -r ${BP_doc}/Reference/Translation ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/Translation/HTML/Translation.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Translation Reference"
      echo ""
      echo ""
   fi
   echo "  Describes the process used to translate your xtUML models." >> README.txt
   echo "" >> README.txt
   echo "+ Revision Control Reference" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d  ${BP_doc}/Reference/UserInterface/RevisionControl ] ; then
      cp -r ${BP_doc}/Reference/UserInterface/RevisionControl release_documents
      cp -r ${BP_doc}/Reference/UserInterface/RevisionControl ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/RevisionControl/HTML/RevisionControl.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Revision Control Reference"
      echo ""
      echo ""
   fi
   echo "  Describes how to use version control with BridgePoint models." >> README.txt
   echo "" >> README.txt
   echo "+  System Modeling Reference" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d ${BP_doc}/Reference/UserInterface/SystemModeling ] ; then
      cp -r ${BP_doc}/Reference/UserInterface/SystemModeling release_documents
      cp -r ${BP_doc}/Reference/UserInterface/SystemModeling ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/SystemModeling/HTML/SystemModeling.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for System Modeling Reference"
      echo ""
      echo ""
   fi
   echo "  Describes how to use BridgePoint to create and test system models." >> README.txt
   echo "" >> README.txt
   echo "+ Metamodel Reference" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d ${BP_doc}/Reference/Metamodel ] ; then
      cp -r ${BP_doc}/Reference/Metamodel release_documents
      cp -r ${BP_doc}/Reference/Metamodel ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/Metamodel/HTML/Association.htm" >> README.txt
      echo "  Filename: release_documents/Metamodel/HTML/Domain.htm" >> README.txt
      echo "  Filename: release_documents/Metamodel/HTML/State_Machine.htm" >> README.txt
      echo "  Filename: release_documents/Metamodel/HTML/Subsystem.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for Metamodel Reference"
      echo ""
      echo ""
   fi
   echo "  Describes the classes described in the class diagram." >> README.txt
   echo "" >> README.txt
   echo "+ Model Verifier Java Interface" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -f ${BP_doc}/Reference/UserInterface/ModelVerifierJavaInterface/ModelVerifierJavaInterface.htm ] ; then
      cp -r ${BP_doc}/Reference/UserInterface/ModelVerifierJavaInterface release_documents
      cp -r ${BP_doc}/Reference/UserInterface/ModelVerifierJavaInterface ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/ModelVerifierJavaInterface/ModelVerifierJavaInterface.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for ModelVerifierJavaInterface Reference"
      echo ""
      echo ""
   fi
   echo "  Describes how to extend an application model by interfacing the model to native Java code." >> README.txt
   echo "" >> README.txt
   echo "+ BridgePoint Licensing Help" >> README.txt
   echo "  =========================================================================" >> README.txt
   if [ -d  ${BP_doc}/license ] ; then
      cp -r ${BP_doc}/license release_documents
      cp -r ${BP_doc}/license ../web/${rls_num}/docs/htmldocs
      echo "  Filename: release_documents/license/EDGE_Licensing_Help.htm" >> README.txt
   else
      echo ""
      echo ""
      echo "ERROR: Could not find HTML for BridgePoint Licensing Help"
      echo ""
      echo ""
   fi
   echo "  How to setup licensing for BridgePoint. " >> README.txt
   echo "" >> README.txt
   echo "+ Licensing Mentor Graphics Software" >> README.txt
   echo "  =========================================================================" >> README.txt
   cp /project/miami/For_CD/templates/${MGLS_DOC_DIR}/mgc_licen.pdf  release_documents
   cp /project/miami/For_CD/templates/${MGLS_DOC_DIR}/mgc_licen.pdf  ../web
   echo "  Filename: release_documents/mgc_licen.pdf" >> README.txt
   echo "  This manual provides instructions and information for the system or license" >> README.txt
   echo "  administrator on how to use Mentor Graphics Licensing System (MGLS) and the" >> README.txt
   echo "  PC Licensing System (PCLS) to license Mentor Graphics software." >> README.txt
   echo "" >> README.txt
   echo "+ License Administration Guide" >> README.txt
   echo "  =========================================================================" >> README.txt
   cp /project/miami/For_CD/templates/${MGLS_DOC_DIR}/flexnet_lic_admin.pdf  release_documents
   cp /project/miami/For_CD/templates/${MGLS_DOC_DIR}/flexnet_lic_admin.pdf  ../web
   echo "  Filename: release_documents/flexnet_lic_admin.pdf" >> README.txt
   echo "  Contains additional licensing information from Macrovision" >> README.txt
   echo "" >> README.txt
   echo "+ Release Notes for Licensing Mentor Graphics Software" >> README.txt
   echo "  =========================================================================" >> README.txt
   cp /project/miami/For_CD/templates/${MGLS_DOC_DIR}/mgc_lic_rn.pdf  release_documents
   cp /project/miami/For_CD/templates/${MGLS_DOC_DIR}/mgc_lic_rn.pdf  ../web
   echo "  Filename: release_documents/mgc_lic_rn.pdf" >> README.txt
   echo "  Contains important release notes about Mentor Graphics licensing." >> README.txt
   echo "  " >> README.txt
   echo "You can view these PDF files using Acrobat Reader 4.0.5 or newer. If" >> README.txt
   echo "needed, you can download Acrobat Reader from www.adobe.com." >> README.txt
   echo "" >> README.txt
   echo "" >> README.txt
   cd $cwd
done

# remove temp file
# put subject line into mail data file
if [ `/usr/mgc/bin/mgcvco` != "ixl" ] ; then
   echo "Subject:  $SUBJECT Release which supports ${PLATS} completed." > /tmp/maildata
   echo "Forward to bp_team" >> /tmp/maildata
else
   echo "Forward to bp_team" >> /tmp/maildata
fi
# echo any messages to /tmp/maildata
echo "" >> /tmp/maildata
cat ${rls_loc}/${rls_nam}_mrc.txt >> /tmp/maildata
echo "" >> /tmp/maildata
echo "" >> /tmp/maildata
echo "If you would like to be removed from this email please send email to keith_brown@mentor.com" >> /tmp/maildata
echo "asking to be removed from the notify field mailing list." >> /tmp/maildata
echo "" >> /tmp/maildata
echo "Thanks!" >> /tmp/maildata
echo "Keith" >> /tmp/maildata
echo "System Level Engineering" >> /tmp/maildata
echo "Mentor Graphics Inc." >> /tmp/maildata
# send mail to mail list
if [ `/usr/mgc/bin/mgcvco` = "ixl" ] || [ `/usr/mgc/bin/mgcvco` = "ixe" ] ; then
   cat /tmp/maildata | mail -s"$SUBJECT Release which supports ${PLATS} completed." $MAIL_TO
else
   cat /tmp/maildata | mail -t  $MAIL_TO
fi

# create mrc html doc from ${rls_loc}/${rls_nam}_mrc.txt
sed -e"s#%RELEASE_NAME%#${SUBJECT}#g"\
    -e"s#%DATE%#${DATE}#g"\
    -e"s#%RELEASE_RESTRICTIONS%#None#g"\
    -e"s#%RELEASE_DESCRIPTION%#${REASON}#g"\
    -e"s#%RELEASE_PATH%#${RELEASE_PATH}#g"\
    -e"s#%ESDM_PATH%#${ESDM_PATH}#g"\
    -e"s#</body>##g"\
    -e"s#</html>##g"\
    /project/miami/For_CD/templates/BridgePoint_head_mrc_template.html > ${rls_loc}/${rls_nam}_mrc.html

echo '<table BORDER=3 CELLSPACING=3 CELLPADDING=3 >' >> ${rls_loc}/${rls_nam}_mrc.html
awk '/^ BridgePoint/{printf("<tr>\n<td ALIGN=CENTER COLSPAN=\"3\">%s</td>\n</tr>\n\n",$0)}' ${rls_loc}/${rls_nam}_mrc.txt >> ${rls_loc}/${rls_nam}_mrc.html
echo titleline | awk '{printf("<td>%8s</td>\n<td>%30s</td>\n<td>%18s</td>\n</tr>\n", "Part #","Price List Description","License Feature")}' >> ${rls_loc}/${rls_nam}_mrc.html
awk -F',' '/^ *[0-9][0-9]*/{printf("<td>%8s</td>\n<td>%30s</td>\n<td>%18s</td>\n</tr>\n",$1,$2,$3)}' ${rls_loc}/${rls_nam}_mrc.txt >> ${rls_loc}/${rls_nam}_mrc.html
echo '</table>' >> ${rls_loc}/${rls_nam}_mrc.html
echo '<br>' >> ${rls_loc}/${rls_nam}_mrc.html
cat /project/miami/For_CD/templates/tail_mrc_template.html >> ${rls_loc}/${rls_nam}_mrc.html
rm /tmp/maildata
echo "/wv/svd_for_cd/bin/create_xml_BridgePoint ${rls_loc} \"${SUBJECT}\" bridgepoint ${rls_num} ${version}"
/wv/svd_for_cd/bin/create_xml_BridgePoint ${rls_loc} "${SUBJECT}" bridgepoint ${rls_num} ${version}
echo ""
echo "You need to create ${rls_nam}_Release_Highlights.pdf and ${rls_nam}_Install.pdf....after creation copy it to cd_1 and"
echo "print it out as the additional information to be sent with the ${rls_loc}/${rls_nam}_mrc.html for"
echo "sign off."
echo ""

