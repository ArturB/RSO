# POM files to build
mvn -f service1/pom.xml install
mvn -f service2/pom.xml install

# Optional additional tasks
( cd msapp ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
( cd mygateway ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
( cd myuaa ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
