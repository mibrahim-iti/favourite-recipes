#
# Build stage
#
FROM maven:3.8.6-jdk-11-slim AS build

MAINTAINER mibrahim.iti@gmail.com

WORKDIR /favourite-recipe-app
COPY src /favourite-recipe-app/src
COPY pom.xml /favourite-recipe-app

RUN mvn -f /favourite-recipe-app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jre-slim

COPY --from=build /favourite-recipe-app/target/favourite-recipes-0.0.1-SNAPSHOT.jar /favourite-recipe-app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local", "/favourite-recipe-app/app.jar"]