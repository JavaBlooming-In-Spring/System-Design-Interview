FROM arm64v8/amazoncorretto:17-alpine-jdk

ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
