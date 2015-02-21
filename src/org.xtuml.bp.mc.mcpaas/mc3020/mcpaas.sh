#!/bin/bash
#
# 1) Execute the rsync "push" command above to push the gen/ folder 
#    to the server
# 2) Send a simple HTTP request to the MC-PaaS server. This will cause 
#    the node.js server script to run, which will do the build.
# 3) Execute the rsync "pull" command every 10 seconds until some 
#    files show up, then exit.
#

cd ..

mcpaas_server=54.234.91.250

# Remove the flag we use to know if MC-PaaS completed
echo "Preparing to build"
rm -f src/.mcpaas_done

# Push
echo "Pushing files to MC-PaaS server"
rsync -vrtz --password-file /etc/rsyncd.secret --delete gen/ kbrown@${mcpaas_server}::incoming

# Notify the server via http request that the files are ready
echo ""
echo "Notifying MC-PaaS server the files are ready to build"
curl http://${mcpaas_server}:8000/

# Pull loop
echo ""
echo -n "Waiting for build to complete"
iter=0
while [ ! -e "src/.mcpaas_done" ]
do
  echo -n "."
  let "iter++"
  sleep 10
  rsync -avrtz --password-file /etc/rsyncd.secret kbrown@${mcpaas_server}::outgoing src/

  if [ "$iter" == "6" ]
  then
    echo ""
    echo "Timed out waiting for build to complete"
    touch src/.mcpaas_done 
  fi
done
echo ""
echo "Done."

