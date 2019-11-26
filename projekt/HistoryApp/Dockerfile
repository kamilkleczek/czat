FROM openjdk:8
COPY target/docker-history-service.jar docker-history-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar","docker-history-service.jar"]