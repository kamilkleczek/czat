FROM openjdk:8
COPY target/docker-user-service.jar docker-user-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar","docker-user-service.jar"]