FROM ubuntu:latest
LABEL authors="TeDi"

ENTRYPOINT ["top", "-b"]

FROM openjdk:11

WORKDIR /app

COPY ../../../../../../../.github/workflows .

RUN chmod +x ./gradlew
RUN ./gradlew build