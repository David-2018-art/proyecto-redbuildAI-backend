FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/Redbuild_AI_backend-0.0.1-SNAPSHOT.jar"]