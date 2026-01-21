# 1. Giai đoạn chế biến
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# --- THÊM DÒNG NÀY ---
# Thay chữ 'webbangao' bằng tên thư mục thật bạn thấy trên GitHub
# Nếu code nằm ngay bên ngoài thì xóa dòng này đi
WORKDIR /app/webbangao
# ---------------------

RUN mvn clean package -DskipTests

# 2. Giai đoạn chạy
FROM eclipse-temurin:17-jre-jammy

# --- SỬA LẠI DÒNG COPY NÀY ---
# Nhớ thêm tên thư mục vào đường dẫn copy
COPY --from=build /app/webbangao/target/webbangao-0.0.1-SNAPSHOT.jar app.jar
# -----------------------------

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]