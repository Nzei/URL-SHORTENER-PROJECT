FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/url_shortener-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} shortener.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/shortener.jar"]
