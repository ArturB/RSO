# POM files to build
mvn -f service1/pom.xml install
mvn -f service2/pom.xml install

# Optional additional tasks
msapp/mvnw package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" dockerfile:build
mygateway/mvnw package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" dockerfile:build
myuaa/mvnw package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" dockerfile:build