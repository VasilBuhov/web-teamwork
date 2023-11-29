FROM openjdk:11

WORKDIR /usr/src/app

COPY . .

# Make the gradlew script executable
RUN chmod +x ./gradlew

RUN ./gradlew build
