# Image de base Java 17
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
# Copie du JAR généré par le build Jenkins
COPY target/*.jar app.jar
# Exposition du port 8085
EXPOSE 8085
# Lancement de l'application
ENTRYPOINT ["java","-jar","/app.jar","--server.port=8085"]