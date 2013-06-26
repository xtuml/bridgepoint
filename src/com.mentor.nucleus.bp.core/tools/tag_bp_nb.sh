#!/bin/bash

tagname="${1}"

if [ "${tagname}" == "" ]; then
  echo -e "\n\tERROR: You must give a tag name as an argument to the program.\n"
  exit
fi

echo -e "\n\tUsing tag: ${tagname}\n"

echo -e "Logging in to CVS as user 'build'"
export CVSROOT=":pserver:build@tucson.wv.mentorg.com:/arch1/products/tiger/repository"

cvs rtag -F ${tagname} MC-Java
cvs rtag -F ${tagname} MC-Java.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.als
cvs rtag -F ${tagname} com.mentor.nucleus.bp.als.oal
cvs rtag -F ${tagname} com.mentor.nucleus.bp.als.oal.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.bld.pkg
cvs rtag -F ${tagname} com.mentor.nucleus.bp.bld.pkg-feature
cvs rtag -F ${tagname} com.mentor.nucleus.bp.cdt
cvs rtag -F ${tagname} com.mentor.nucleus.bp.cli
cvs rtag -F ${tagname} com.mentor.nucleus.bp.compare.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.core
cvs rtag -F ${tagname} com.mentor.nucleus.bp.core.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.core.linux.x86
cvs rtag -F ${tagname} com.mentor.nucleus.bp.core.win32.x86
cvs rtag -F ${tagname} com.mentor.nucleus.bp.dap.pkg
cvs rtag -F ${tagname} com.mentor.nucleus.bp.dap.pkg-feature
cvs rtag -F ${tagname} com.mentor.nucleus.bp.debug.ui
cvs rtag -F ${tagname} com.mentor.nucleus.bp.debug.ui.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.doc
cvs rtag -F ${tagname} com.mentor.nucleus.bp.docgen
cvs rtag -F ${tagname} com.mentor.nucleus.bp.internal.metrics
cvs rtag -F ${tagname} com.mentor.nucleus.bp.internal.tools
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.core
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.image
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.image.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.mdl
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.mdl.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.sql
cvs rtag -F ${tagname} com.mentor.nucleus.bp.io.sql.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.c.binary
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.c.source
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.cpp.source
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.mc3020
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.mc3020.pkg
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.mc3020.pkg-feature
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.mc3020.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.systemc.source
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.template
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.vhdl.source
cvs rtag -F ${tagname} com.mentor.nucleus.bp.mc.xmiexport
cvs rtag -F ${tagname} com.mentor.nucleus.bp.model.compare
cvs rtag -F ${tagname} com.mentor.nucleus.bp.pkg
cvs rtag -F ${tagname} com.mentor.nucleus.bp.pkg-feature
cvs rtag -F ${tagname} com.mentor.nucleus.bp.search
cvs rtag -F ${tagname} com.mentor.nucleus.bp.search.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.sequencecapture
cvs rtag -F ${tagname} com.mentor.nucleus.bp.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.canvas
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.canvas.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.explorer
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.explorer.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.graphics
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.properties
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.properties.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.search
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.sem
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.session
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.text
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.text.test
cvs rtag -F ${tagname} com.mentor.nucleus.bp.ui.tree
cvs rtag -F ${tagname} com.mentor.nucleus.bp.utilities
cvs rtag -F ${tagname} com.mentor.nucleus.bp.verifier.pkg
cvs rtag -F ${tagname} com.mentor.nucleus.bp.verifier.pkg-feature
cvs rtag -F ${tagname} com.mentor.nucleus.bp.welcome
cvs rtag -F ${tagname} com.mentor.nucleus.bp.welcome.test
cvs rtag -F ${tagname} com.mentor.nucleus.help.bp.mc
cvs rtag -F ${tagname} com.mentor.nucleus.internal.test
cvs rtag -F ${tagname} libTRANS
cvs rtag -F ${tagname} mc
cvs rtag -F ${tagname} model_compilers
cvs rtag -F ${tagname} pt_antlr
cvs rtag -F ${tagname} Installer_MIP_MIMIC
cvs rtag -F ${tagname} utilities

exit 0
