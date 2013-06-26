rem File:      $RCSfile: generate_context_menus.bat,v $
rem Version:   $Revision: 1.1 $
rem Modified:  $Date: 2003/07/18 20:24:05 $

rem assume we're starting at ECLIPSE_HOME

cd workspace/com.mentor.nucleus.bp.core

set PTC_MC_ARC_DIR=../TinyJava

del core_ui.gen
call pt_gen_import core_ui.gen %PTC_MC_ARC_DIR%/ooa_schema.sql
call pt_gen_import core_ui.gen sql/ooaofooa.sql
call pt_gen_import core_ui.gen sql/object_contribution_schema.sql
call pt_gen_import core_ui.gen sql/object_contribution.pei.sql
call pt_gen_file core_ui.gen ./arc/create_context_menus.arc
