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

docker swarm
docker swarm join --token SWMTKN-1-3utn48r6f82o8dq3s6y1xanhaudbrhhy7nu5hw79zgkc83yufz-6ua293in1pq3l4vpdeep3f2xr 10.0.2.15:2377