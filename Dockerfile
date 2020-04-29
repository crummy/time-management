FROM node:12 AS ui
WORKDIR /ui
COPY ui/ /ui
RUN npm install
RUN npm run build

FROM maven:alpine AS backend
WORKDIR /app
COPY src/ /app/src
COPY pom.xml /app/pom.xml
RUN mvn package

FROM openjdk:15-alpine
WORKDIR /app
COPY --from=ui ui/public /app/ui/public
COPY --from=backend /app/target/time-management-1.0-SNAPSHOT-jar-with-dependencies.jar /app/time-management.jar
CMD java -jar /app/time-management.jar