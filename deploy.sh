rm -rf RSO
git clone https://github.com/ArturB/RSO
cd RSO
cat ~/password | sudo -S docker stack deploy -c docker-stack.yml rso

