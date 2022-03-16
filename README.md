# demospring

mvn spring-boot:run

docker build . -t demo:latest
docker run -p 8082:8080 demo:latest