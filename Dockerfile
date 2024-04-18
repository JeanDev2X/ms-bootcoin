FROM openjdk:17
VOLUME /tmp
EXPOSE 8025
ADD ./target/spring.boot.webflu.ms.bootcoin.app-0.0.1-SNAPSHOT.jar ms-bootcoin.jar
ENTRYPOINT ["java","-jar","/ms.bootcoin.jar"]