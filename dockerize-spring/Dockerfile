FROM openjdk:17
EXPOSE 8080
ADD target/dockerize-spring.jar dockerize-spring.jar
ENTRYPOINT [ "java", "-jar", "/dockerize-spring.jar" ]