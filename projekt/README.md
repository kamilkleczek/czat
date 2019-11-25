# How to
eureka - server
docker build -f Dockerfile -t docker-eureka-server .
docker run -p 8761:8761 docker-eureka-server

front
docker build -t docker-front .
docker run -it -p 3000:80 docker-front