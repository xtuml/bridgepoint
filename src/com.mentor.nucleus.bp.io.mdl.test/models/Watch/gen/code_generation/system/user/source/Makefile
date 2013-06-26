#=============================================================================
# File: Makefile
#
# Warnings:
#   !!! THIS IS AN AUTO-GENERATED FILE. PLEASE DO NOT EDIT. !!!
#=============================================================================

.POSIX:
.SCCS_GET:

include C:/test2.0.2/BridgePoint/workspace/Watch/gen/code_generation/Makefile.in
include C:/test2.0.2/BridgePoint/workspace/Watch/gen/code_generation/Makefile.user
# Relative paths for object and source files.
SRC_PATH=
OBJ_PATH=../object/
#=============================================================================
# List of all object files to be compiled.
#=============================================================================
USER_OBJ_TARGETS = \
	${OBJ_PATH}sys_user_co.o


#=============================================================================
# Source file target recipes.
#=============================================================================
${OBJ_PATH}sys_user_co.o : ${SRC_PATH}sys_user_co.c
	${CMD_COMPILE}
	${CMD_ASSEMBLE}


#=============================================================================
# List of object files to link.
#=============================================================================
USER_OBJ_LIST = \
	C:/test2.0.2/BridgePoint/workspace/Watch/gen/code_generation/system/user/object/sys_user_co.o

#=============================================================================
# Build environment targets.
#=============================================================================

# Clean out compiled object files.
clean_user_obj :
	@'rm' -f ${OBJ_PATH}*.o

# Target for compiling objects.
comp_user_src : clean_user_obj ${USER_OBJ_TARGETS}

# Object file list for linking purposes.
object_list : always
	@'echo' "$(USER_OBJ_LIST)"  | 'sed' 's/( 	)( 	)*/ /g' | 'tr' '\040' '\012'

