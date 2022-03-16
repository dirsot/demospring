FROM openjdk:11
ENV SPRING_PROFILES_ACTIVE docker
#EXPOSE 8080
VOLUME /tmp
#ARG JAR_FILE
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java",\
            "-Djava.security.egd=file:/dev/./urandom",\
            "-jar",\
            "/app.jar"]