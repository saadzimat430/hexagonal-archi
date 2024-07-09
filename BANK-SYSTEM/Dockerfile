FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /BANK-SYSTEM
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim-buster
WORKDIR /BANK-SYSTEM
COPY --from=build /BANK-SYSTEM/target/BANK-SYSTEM-0.0.1-SNAPSHOT.jar ./BANK-SYSTEM-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "BANK-SYSTEM-0.0.1-SNAPSHOT.jar"]

