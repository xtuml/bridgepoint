#=============================================================================
# File: Makefile
#
# Warnings:
#   !!! THIS IS AN AUTO-GENERATED FILE. PLEASE DO NOT EDIT. !!!
#=============================================================================

.POSIX:
.SCCS_GET:

# Displays the available targets in tabular format.
help :
    @echo "============================================================"
    @echo " Primary Targets       | Compile     Link"
    @echo "============================================================"
    @echo "   all                 |   YES        YES"
    @echo "   comp_src            |   YES         NO"
    @echo "   link_sys            |   YES        YES"
    @echo "   clean               |   "
    @echo "============================================================="

include D:/gitRepos/w/internal/src/com.mentor.nucleus.bp.welcome/models/avpace/gen/code_generation/Makefile.in
include D:/gitRepos/w/internal/src/com.mentor.nucleus.bp.welcome/models/avpace/gen/code_generation/Makefile.user

# use Target C Cross Compiler
CMD_TARGET_COMPILE    = ${CMD_COMPILE}
CMD_TARGET_ASSEMBLE   =
CMD_TARGET_PREPROCESS =
CMD_TARGET_LINK       = ${CMD_LINK}

# Relative paths for object and source files.
SRC_PATH=
OBJ_PATH=
#=============================================================================
# List of all object files to be compiled.
#=============================================================================
USER_OBJ_TARGETS = \
	${OBJ_PATH}avpace_sys_main.o \
	${OBJ_PATH}heart.o \
	${OBJ_PATH}heart_HEART_class.o \
	${OBJ_PATH}heart_LOG_bridge.o \
	${OBJ_PATH}heart_SINUS_NODE_class.o \
	${OBJ_PATH}hostmonitor.o \
	${OBJ_PATH}hostmonitor_HM_class.o \
	${OBJ_PATH}hostmonitor_RM_class.o \
	${OBJ_PATH}hostmonitor_TM_class.o \
	${OBJ_PATH}pacemaker.o \
	${OBJ_PATH}pacemaker_LOG_bridge.o \
	${OBJ_PATH}pacemaker_PACER_class.o \
	${OBJ_PATH}pacer.o \
	${OBJ_PATH}sys_user_co.o \
	${OBJ_PATH}sys_xtuml.o \
	${OBJ_PATH}TIM_bridge.o \

#=============================================================================
# Source file target recipes.
#=============================================================================
${OBJ_PATH}avpace_sys_main.o : ${SRC_PATH}avpace_sys_main.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}heart.o : ${SRC_PATH}heart.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}heart_HEART_class.o : ${SRC_PATH}heart_HEART_class.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}heart_LOG_bridge.o : ${SRC_PATH}heart_LOG_bridge.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}heart_SINUS_NODE_class.o : ${SRC_PATH}heart_SINUS_NODE_class.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}hostmonitor.o : ${SRC_PATH}hostmonitor.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}hostmonitor_HM_class.o : ${SRC_PATH}hostmonitor_HM_class.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}hostmonitor_RM_class.o : ${SRC_PATH}hostmonitor_RM_class.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}hostmonitor_TM_class.o : ${SRC_PATH}hostmonitor_TM_class.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}pacemaker.o : ${SRC_PATH}pacemaker.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}pacemaker_LOG_bridge.o : ${SRC_PATH}pacemaker_LOG_bridge.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}pacemaker_PACER_class.o : ${SRC_PATH}pacemaker_PACER_class.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}pacer.o : ${SRC_PATH}pacer.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}sys_user_co.o : ${SRC_PATH}sys_user_co.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}sys_xtuml.o : ${SRC_PATH}sys_xtuml.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

${OBJ_PATH}TIM_bridge.o : ${SRC_PATH}TIM_bridge.c
	${CMD_TARGET_COMPILE}
	${CMD_TARGET_ASSEMBLE}

#=============================================================================
# Build environment targets.
#=============================================================================
# Clean out compiled object files.
clean :
    @'rm' -f ${OBJ_PATH}*.o

# Target for compiling objects.
comp_src : ${USER_OBJ_TARGETS}

# Target for link system.
link_sys : ${USER_OBJ_TARGETS}
    $(CMD_TARGET_LINK) $(USER_OBJ_TARGETS)

all : comp_src link_sys
