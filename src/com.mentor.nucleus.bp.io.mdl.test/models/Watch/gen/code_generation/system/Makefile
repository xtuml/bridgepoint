#=============================================================================
# File: Makefile
#
# Description:
#  Application system level makefile.
#
# Warnings:
# !!! THIS IS AN AUTO-GENERATED FILE.  PLEASE DO NOT EDIT. !!!
#     ROX_CreateSystemMakefile @ Thu Jun  5 22:35:41 2008
#=============================================================================

include ../Makefile.xtumlmc
SYSTEM_SQL=schema/sql/system.sql
SYSTEM_GEN_DB=schema/gen/system.gen

# SQL files required for system database generation.
SYSTEM_SQLDB_FILES = \
	../tracking/schema/sql/tracking_populator.sql \
	schema/sql/sys_root.sql

# Rules for system base object(s) insertion.
schema/sql/sys_root.sql:
	@set -a ; \
	ROX_MC_ROOT_DIR="${ROX_MC_ROOT_DIR}" ; \
	ROX_MC_BIN_DIR="${ROX_MC_BIN_DIR}" ; \
	ROX_MC_ARC_DIR="${ROX_MC_ARC_DIR}" ; \
	ROX_MC_MAKE_DIR="${ROX_MC_MAKE_DIR}" ; \
	ROX_MC_TRANSLATE_DIR="${ROX_MC_TRANSLATE_DIR}" ; \
	ROX_MC_PRODUCT_ID="${ROX_MC_PRODUCT_ID}" ; \
	ROX_MC_PRODUCT_VER="${ROX_MC_PRODUCT_VER}" ; \
	ROX_MC_PRODUCT_SN="${ROX_MC_PRODUCT_SN}" ; \
	ROX_MC_PRODUCT_ID_EXTENSION="${ROX_MC_PRODUCT_ID_EXTENSION}" ; \
	ROX_MC_TARGET_PLATFORM="${ROX_MC_TARGET_PLATFORM}" ; \
	ROX_MC_TARGET_COMPILER="${ROX_MC_TARGET_COMPILER}" ; \
	ROX_APP_ROOT_DIR="${ROX_APP_ROOT_DIR}" ; \
	set +a ; \
	${ROX_MC_BIN_DIR}/xtumlmc_build xtumlmc_sys_init ${ROX_APP_ROOT_DIR} 

# Rules for domain to system populators.
../tracking/schema/sql/tracking_populator.sql:
	'cd' ${ROX_APP_ROOT_DIR} ; \
	'make' domain=tracking gen_dom
# Schema schema sql file.
${SYSTEM_SQL}: ${SYSTEM_SQLDB_FILES}
	@'cat' ${SYSTEM_SQLDB_FILES} > $@



#=============================================================================

# Generate system level code.

#=============================================================================

gen_sys: ${SYSTEM_SQL}
	@set -a ; \
	ROX_MC_ROOT_DIR="${ROX_MC_ROOT_DIR}" ; \
	ROX_MC_BIN_DIR="${ROX_MC_BIN_DIR}" ; \
	ROX_MC_ARC_DIR="${ROX_MC_ARC_DIR}" ; \
	ROX_MC_MAKE_DIR="${ROX_MC_MAKE_DIR}" ; \
	ROX_MC_TRANSLATE_DIR="${ROX_MC_TRANSLATE_DIR}" ; \
	ROX_MC_PRODUCT_ID="${ROX_MC_PRODUCT_ID}" ; \
	ROX_MC_PRODUCT_VER="${ROX_MC_PRODUCT_VER}" ; \
	ROX_MC_PRODUCT_SN="${ROX_MC_PRODUCT_SN}" ; \
	ROX_MC_PRODUCT_ID_EXTENSION="${ROX_MC_PRODUCT_ID_EXTENSION}" ; \
	ROX_MC_TARGET_PLATFORM="${ROX_MC_TARGET_PLATFORM}" ; \
	ROX_MC_TARGET_COMPILER="${ROX_MC_TARGET_COMPILER}" ; \
	ROX_APP_ROOT_DIR="${ROX_APP_ROOT_DIR}" ; \
	ROX_APP_SYSTEM_DIR="${ROX_APP_SYSTEM_DIR}" ; \
	ROX_APP_SYS_COLOR_DIR="${ROX_APP_SYS_COLOR_DIR}" ; \
	PT_GEN_DOMAIN_PATH="${ROX_MC_TRANSLATE_DIR}" ; \
	SYSTEM_SQL="schema/sql/system.sql" ; \
	SYSTEM_GEN_DB="schema/gen/system.gen" ; \
	SYSTEM_SQL_DIR="$${ROX_APP_SYSTEM_DIR}/schema/sql" ; \
	SYSTEM_GEN_DIR="$${ROX_APP_SYSTEM_DIR}/schema/gen" ; \
	SYSTEM_TOUCH_DIR="schema/touch" ; \
	set +a ; \
	make -f $${ROX_MC_MAKE_DIR}/Makefile.system $@


#===========================================================================

# Default action if we don't understand the target.

# Let the node level make see if it understands it.

#===========================================================================

.DEFAULT:
	@'cd' ${ROX_APP_ROOT_DIR} ; \
	'make' $<
