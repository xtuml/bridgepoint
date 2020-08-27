#!/bin/bash

declare -r default_container_name="bridgepoint"
declare container_name=""

function usage {
    echo "Usage: "
    echo "remove-bridgepoint-container.sh [c:d:]"
    echo "  -c <container>  The name of the container, defaults to '${default_container_name}'"
    echo "  -h              Display the help message"
    exit 0
}

function check_container_param {
    if [[ "${container_name}" == "" ]]; then
        echo "---- Warning: Container name not provided, using default '${default_container_name}'"
        container_name=${default_container_name}
    else
        echo "---- Using container name provided '${container_name}'"
    fi
}

function check_bridgepoint_exists {
    exists=$(docker ps --all | grep ${container_name})
    if [[ ! -z "${exists}" ]]; then
        echo "---- Found running instance of '${container_name}', removing..."
        docker rm bridgepoint
    else
        echo "---- Not found any running instance of '${container_name}', nothing to remove."
    fi   
}

while getopts ":c:h" opt; do
    case ${opt} in
        d ) dir_name=${OPTARG};;
        h ) usage;;
        \? ) usage;;
    esac
done
shift $((${OPTIND} -1))

check_container_param
check_bridgepoint_exists