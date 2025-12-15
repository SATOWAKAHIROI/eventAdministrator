# Build stage
FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

# 依存関係をダウンロード
COPY pom.xml .
RUN mvn dependency:go-offline -B

# ソースコードコピー & ビルト
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# ビルドステージからjarをコピー
COPY --from=build /app/target/*.jar app.jar

# ポート公開(情報用)
EXPOSE 8080

# アプリ起動
CMD ["java", "-jar", "app.jar"]
