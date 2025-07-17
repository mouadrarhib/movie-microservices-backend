FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/discovery-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]
