FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/watchShop-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "watchShop-0.0.1-SNAPSHOT.jar"]