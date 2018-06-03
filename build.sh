# Optional additional tasks
( cd msapp ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
( cd mygateway ; mvn package -DskipTests -P"prod,no-liquibase,zuul,no-eureka" ; cd target ; )
( cd myuaa ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
( cd msrodo ; mvn package -DskipTests -P"dev,no-liquibase,zuul,no-eureka" ; cd target ; )
