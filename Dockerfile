FROM eclipse-temurin:8-jdk

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

ENTRYPOINT ["java", "-jar", "target/watchShop-0.0.1-SNAPSHOT.jar"]