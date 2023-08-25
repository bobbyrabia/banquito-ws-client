FROM eclipse-temurin:17-jre-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} clients.jar
ENTRYPOINT ["java", "-jar", "/clients.jar"]