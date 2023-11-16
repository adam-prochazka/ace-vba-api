# syntax=docker/dockerfile:1
#FROM gradle:jdk20-alpine as build
FROM openjdk:20-slim as build
WORKDIR /app
COPY . .
#RUN gradle bootJar
RUN ./gradlew bootJar

FROM openjdk:20-slim as run
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY run.sh .
EXPOSE 8080
ENTRYPOINT ./run.sh