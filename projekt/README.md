# How to
eureka - server
docker build -t docker-eureka-server .
docker run -p 8761:8761 docker-eureka-server

main - service
docker build -t docker-main-service .
docker run -p 8080:8080 docker-main-service

user - service
docker build -t docker-user-service .
docker run -p 8081:8081 docker-user-service

history - service
docker build -t docker-history-service .
docker run -p 8082:8082 docker-history-service

front
docker build -t docker-front .
docker run -it -p 3000:80 docker-front