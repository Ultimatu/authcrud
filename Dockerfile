# Utiliser une image de base officielle OpenJDK
FROM adoptopenjdk:11-jdk-hotspot

# Définir le répertoire de travail dans le conteneur
WORKDIR /authcrud

# Copier le fichier JAR de votre application dans le conteneur
COPY target/authcrud-0.0.1-SNAPSHOT.jar /authcrud/authcrud-0.0.1-SNAPSHOT.jar

# Spécifier la commande pour exécuter votre application Spring Boot
CMD ["java", "-jar", "/authcrud/authcrud-0.0.1-SNAPSHOT.jar"]
