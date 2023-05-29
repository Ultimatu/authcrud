# AuthCRUD

AuthCRUD est une application de démonstration qui met en œuvre les fonctionnalités d'authentification, de gestion des utilisateurs et de gestion des produits.

## Architecture du projet

Le projet AuthCRUD suit une structure d'architecture standard pour une application Spring Boot :

- `controllers` : Contient les contrôleurs qui gèrent les requêtes HTTP et les retours de vue.
- `exception` : Contient les classes de gestion des exceptions personnalisées.
- `models` : Contient les classes de modèle représentant les entités du système.
- `repositories` : Contient les interfaces de repository pour l'accès aux données.
- `services` : Contient les interfaces et les implémentations des services métier.
- `resources` : Contient les ressources statiques (images, fichiers CSS, etc.) et les fichiers de modèle HTML.

## Configuration du service de messagerie

Le projet utilise le service de messagerie Gmail SMTP pour l'envoi de courriers électroniques. Afin de configurer correctement le service de messagerie, suivez les étapes suivantes :

1. Créez un compte Gmail si vous n'en avez pas déjà un.
2. Accédez aux paramètres de sécurité du compte Google et activez l'accès des applications moins sécurisées. NB: vous devrez peut-être activer l'authentification à deux facteurs pour accéder à cette option.
3. Dans le fichier `application.yaml`, configurez les propriétés suivantes :
Remplacez `your-email@gmail.com` par votre adresse e-mail Gmail et `your-password-gmail` par le mot de passe de l'utilisateur temporaire que vous avez créé dans votre gmail.

spring:
  datasource: 
    url: jdbc:mysql://localhost:3306/your-bd-name
    username: your-username
    password: your-password
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-password-gmail
    properties:
      mail:
        smtp:
          auth: true
          starttls.enable: true

N B : data source properties sont utilisées pour configurer la base de données MySQL.
__ code sql pour creer la base de donnees et le user si vous n'avez pas encore creer la base de donnees et le user __
CREATE DATABASE IF NOT EXISTS `your-db-name`;
CREATE USER IF NOT EXISTS 'your-user-name'@'localhost' IDENTIFIED BY 'your-password';
GRANT  PRIVILEGES ON `your-db-name`.* TO 'your-user-name'@'localhost';


## Utilisation de l'application

L'application AuthCRUD permet de gérer les utilisateurs et les produits. Voici comment créer un nouvel utilisateur et définir son adresse e-mail :

1. Accédez à l'URL de l'application dans votre navigateur.  http://localhost:8080
2. Cliquez sur le lien "S'inscrire" pour créer un nouveau compte utilisateur.
3. Remplissez le formulaire d'inscription avec les informations requises, y compris votre adresse e-mail.
4. Un e-mail de vérification sera envoyé à l'adresse e-mail que vous avez fournie.
5. Accédez à votre boîte de réception et ouvrez l'e-mail de vérification.
6. Entrrez le code de validation.

Une fois votre compte utilisateur activé, vous pourrez vous connecter à l'application et commencer à utiliser les fonctionnalités de gestion des utilisateurs et des produits.

