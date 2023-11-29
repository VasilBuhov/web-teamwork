FROM ubuntu:latest

LABEL authors="TeDi"

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk

WORKDIR /app

COPY ../../../../../../../.github/workflows .

RUN chmod +x ./gradlew
RUN ./gradlew build

ENTRYPOINT ["top", "-b"]
