# demospring

mvn spring-boot:run

docker build . -t demo:latest docker run -p 8082:8080 demo:latest

http://localhost:8080/person/add?name=a&surname=b

http://localhost:8080/aop/2