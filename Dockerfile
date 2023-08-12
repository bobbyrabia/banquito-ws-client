FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bquito-clients.jar
ENTRYPOINT ["java", "-jar", "/bquito-clients.jar"]