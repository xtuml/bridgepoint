#!/bin/bash

site="https://s3.amazonaws.com/xtuml-releases/nightly-build"
versionPage="buildfiles.html"

versionDefault=""
version=${versionDefault}

# Get the version of product from website version page
version=$(curl ${site}/${versionPage} | grep "Version:" | cut -d' ' -f2)
if [[ -z "${version}" ]]
then
    echo "Version tag empty, cannot continue"
    exit 1
else
    echo "--- Version marked as ${version}"
fi

installDir="/opt/bridgepoint"

if [[ ! -d ${installDir} ]]
then
    echo "--- Creating parent installation directory ${installDir}"
    mkdir -p -m 755 ${installDir}
fi

rm -rf ${installDir}/${version}

file="org.xtuml.bp.product-linux.gtk.x86_64.zip"
target=${site}/${file}
unpackDir=${installDir}/unpack

echo "--- Downloading bridgepoint to ${unpackDir}"
rm -rf ${unpackDir}
wget -P ${unpackDir} ${target}

unzip ${unpackDir}/${file} -d ${unpackDir} && rm -f ${unpackDir}/${file}

installed=$(ls ${unpackDir})
mv ${unpackDir}/${installed} ${installDir}/${version}
rmdir ${unpackDir}

cd ${installDir}
rm -f latest
ln -s ${version} latest

profile="/etc/profile.d/bridgepoint.sh"

cat <<END > ${profile}
export BRIDGEPOINT_HOME=${installDir}/${version}
export PATH=\${BRIDGEPOINT_HOME}:\${PATH}
END

chmod 644 ${profile}
