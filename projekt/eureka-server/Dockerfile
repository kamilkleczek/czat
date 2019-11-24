FROM openjdk:8
ADD target/docker-eureka-server.jar docker-eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar","docker-eureka-server"]
#
#FROM maven:3.5-jdk-8 AS build
#WORKDIR /
#RUN mvn -f eureka-server/pom.xml -U clean package
#
#FROM openjdk:8
#EXPOSE 8761
#COPY --from=build /eureka-server/target/docker-eureka-server.jar /
#CMD ["java", "-jar", "/demo-0.0.1-SNAPSHOT.jar", "-Dspring.datasource.url=jdbc:postgresql://database:5432/northwind"]