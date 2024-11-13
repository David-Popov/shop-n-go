# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM maven:3.9.4-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/target/shop-n-go-0.0.1-SNAPSHOT.jar app.jar
COPY pom.xml .
COPY src ./src
ENTRYPOINT ["java", "-jar", "app.jar"]