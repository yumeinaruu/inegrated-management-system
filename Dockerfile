FROM openjdk:17-alpine
ARG JAR_FILE=target/iis-0.0.1-SNAPSHOT.jar
RUN mkdir /jars
WORKDIR /jars
COPY ${JAR_FILE} /jars
ENTRYPOINT java -jar /jars/iis-0.0.1-SNAPSHOT.jar