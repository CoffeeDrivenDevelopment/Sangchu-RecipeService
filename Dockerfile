FROM amazoncorretto:17-alpine
LABEL MAINTAINER="KIMSEI <workju1124@gmail.com>"

ARG JAR_FILE=../build/libs/*.jar
WORKDIR /app
COPY ${JAR_FILE} recipe-service.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","/app/recipe-service.jar"]