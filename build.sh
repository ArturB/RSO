# POM files to build
mvn -f service1/pom.xml install
mvn -f service2/pom.xml install

# Optional additional tasks
( cd msapp ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
ls
ls mygateway

( cd mygateway ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
mkdir mygateway/target/ssl
mv mygateway/ssl/keystore.p12 mygateway/target/ssl/keystore.p12

( cd myuaa ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )

