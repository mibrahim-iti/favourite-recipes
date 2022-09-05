#
# Build stage
#
FROM maven:3.8.6-jdk-11-slim AS build

WORKDIR /favourite-recipe
COPY src /favourite-recipe/src
COPY pom.xml /favourite-recipe

RUN mvn -f /favourite-recipe/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jre-slim

COPY --from=build /favourite-recipe/target/favourite-recipes-0.0.1-SNAPSHOT.jar /favourite-recipe/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/favourite-recipe/app.jar"]