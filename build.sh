# POM files to build
mvn -f service1/pom.xml install
mvn -f service2/pom.xml install

# Generate Docker and push containers
docker-compose build && docker-compose push

# Optional additional tasks
