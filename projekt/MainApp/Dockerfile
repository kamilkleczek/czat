FROM openjdk:8
COPY target/docker-main-service.jar docker-main-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","docker-main-service.jar"]