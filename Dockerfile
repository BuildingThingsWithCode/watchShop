FROM eclipse-temurin:8-jdk

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/watchShop-0.0.1-SNAPSHOT.jar"]