#!/bin/bash
CWD=$(pwd)
NO_CACHE=
image_tag=

function usage {
    echo "Usage: "
    echo "build-image.sh [-th] <image-name>"
    echo "  -t <tag> The string used to tag the built image"
    echo "  -n       build with the docker --no-cache option"
    echo "  -h       Display the help message"
    exit 0
}

while getopts ":t:nh" opt; do
    case ${opt} in
        t) image_tag=${OPTARG};;
        h) usage;;
        n) NO_CACHE="--no-cache";;
    esac
done
shift $((${OPTIND} -1))

if [[ -z "$1" ]]; then
    echo "No image directory supplied"
    usage
fi

image_dir=${CWD}/$1
echo "image_dir: ${image_dir}"

# Check the given directory exists
if [[ ! -d "${image_dir}" ]]; then
    echo "${image_dir} does not exist in current directory $(pwd)"
    exit 1
fi

# Create the build directory and copy everything in to it
build_dir=$(mktemp -d -t ci-XXXXXXXXXX)
cd ${image_dir}
cp -RH * ${build_dir}
cd ${build_dir}

# Build the image
if [[ ! -z "${image_tag}" ]]; then
    docker build ${NO_CACHE} -t ${image_tag} .
else
    docker build ${NO_CACHE} -t ${1%/} .
fi

# Change back to the original directory and remove the temp build dircetory
cd ${CWD}
rm -rf ${build_dir}
