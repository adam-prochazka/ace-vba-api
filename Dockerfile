# syntax=docker/dockerfile:1
#FROM gradle:jdk17-alpine as build
FROM openjdk:17-slim as build
WORKDIR /app
COPY . .
#RUN gradle bootJar
RUN ./gradlew bootJar

FROM openjdk:17-slim as run
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY run.sh .
EXPOSE 8080
ENTRYPOINT ./run.sh