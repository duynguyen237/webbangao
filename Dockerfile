# 1. Giai đoạn chế biến (Dùng Maven mới nhất)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Giai đoạn chạy (Dùng Eclipse Temurin thay cho OpenJDK cũ)
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/target/webbangao-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]