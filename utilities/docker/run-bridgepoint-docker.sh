#!/bin/bash

declare -r default_dir_name=`pwd`
declare -r default_container_name="bridgepoint"
declare container_name=""
declare dir_name=""
declare already_running="FALSE"

function usage {
    echo "Usage: "
    echo "run-bridgepoint-docker.sh [c:d:]"
    echo "  -c <container>  The name of the container, defaults to '${default_container_name}'"
    echo "  -d <dir>        The directory to mount, defaults to '${default_dir_name}'"
    echo "  -h              Display the help message"
    exit 0
}

function check_bridgepoint_exists {
    exists=$(docker ps --all | grep ${container_name})
    # If already running, sets value to "TRUE". If statement is checking for lack of a string 
    if [[ ! -z "${exists}" ]]; then
        echo "---- Found running instance of '${container_name}'"
        already_running="TRUE"
    else
        echo "---- Not found any running instance of '${container_name}'"
    fi   
}

function check_container_param {
    if [[ "${container_name}" == "" ]]; then
        echo "---- Warning: Container name not provided, using default '${default_container_name}'"
        container_name=${default_container_name}
    else
        echo "---- Using container name provided '${container_name}'"
    fi
}

function check_directory_exists {
    if [[ -d "${dir_name}" ]]; then
        echo "---- Directory '${dir_name}' found"
    else
        echo "---- Error: Directory '${dir_name}' not found, unable to continue"
        exit 1
    fi
}

function check_working_directory {
    if [[ "${dir_name}" == "" ]]; then
        echo "---- Working directory set to default '${default_dir_name}'"
        dir_name=${default_dir_name}
    else
        echo "---- Working directory set to '${dir_name}'"
    fi

    check_directory_exists
}

while getopts ":c:d:h" opt; do
    case ${opt} in
        c ) container_name=${OPTARG};;
        d ) dir_name=${OPTARG};;
        h ) usage;;
        \? ) usage;;
    esac
done
shift $((${OPTIND} -1))

check_working_directory
check_container_param
check_bridgepoint_exists

ip=$(ifconfig en0 | grep inet | awk '$1=="inet" {print $2}')
echo "---- Adding local ip address '${ip}' to xhosts"
xhost + $ip

# Start the container
if [[ "${already_running}" == "TRUE" ]]; then
    echo "---- Bridgepoint container already exists, starting..."
    docker start -ia ${container_name}
else
    echo "---- Creating a new Bridgepoint container..."
    docker run \
        -it \
        -e DISPLAY="${ip}:0" \
        --name ${container_name} \
        --mount type=bind,source="${dir_name}",target=/development \
        --net=host \
        ${container_name}:0.1
fi
