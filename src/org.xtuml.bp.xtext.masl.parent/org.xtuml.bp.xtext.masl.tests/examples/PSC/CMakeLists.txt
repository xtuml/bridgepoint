#
# UK Crown Copyright (c) 2016. All Rights Reserved
#

project(Petrol VERSION 0.1)

include(Masl)

set(MaslExportTarget ${PROJECT_NAME})

add_masl_domain(PSC_OOA/PSC.mod)
add_masl_project(PETROL_PROC/PETROL_PROC.prj)

generate_masl()

include(CMakePackageConfigHelpers)

write_basic_package_version_file (
  ${CMAKE_CURRENT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake
  VERSION       ${PROJECT_VERSION}
  COMPATIBILITY ExactVersion
)

export( EXPORT ${PROJECT_NAME}Targets 
        NAMESPACE ${PROJECT_NAME}:: )

install(
  EXPORT      ${PROJECT_NAME}Targets
  FILE        ${PROJECT_NAME}Config.cmake
  NAMESPACE   ${PROJECT_NAME}::
  DESTINATION ${INSTALL_LIB_DIR}/cmake/${PROJECT_NAME}
  )

install(
  FILES
              ${CMAKE_CURRENT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake
  DESTINATION ${INSTALL_LIB_DIR}/cmake/${PROJECT_NAME}
  )
