FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=target/BANK-SYSTEM-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} banque_system.jar
ENTRYPOINT ["java","-jar","/banque_system.jar"]