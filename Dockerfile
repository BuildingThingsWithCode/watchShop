FROM eclipse-temurin:8-jdk-alpine

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/watchShop-0.0.1-SNAPSHOT.jar"]