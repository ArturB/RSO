# check if repo dir exists
if [ -d RSO ]; then
# if so, update git repository
  cd RSO && git pull
else
# otherwise, clone whole repo
  git clone https://github.com/ArturB/RSO && cd RSO
fi

# deploy all docker-compose containers. 
# sudo password is securely kept at the remote host. 
cat ~/password | sudo -S docker stack deploy -c docker-compose.yml rso
