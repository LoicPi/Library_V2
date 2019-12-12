# Library Release 1.0
## **Développez le nouveau système d’information de la bibliothèque d’une grande ville**

Ce site web permettra aux usagers de suivre les prêts de leurs ouvrages et de leur permettre de les renouveller.
Ils pourront également consulter et ou rechercher les livres de la bibliothèque et savoir si ils sont disponibles ou non.

### **Liste des fonctionnalités :**
- Rechercher des ouvrages et voir le nombre d’exemplaires disponibles.
- Consulter leurs prêts en cours. Les prêts sont pour une période de 4 semaines.
- Prolonger un prêt en cours. Le prêt d’un ouvrage n’est prolongeable qu’une seule fois. La prolongation ajoute une nouvelle période de prêt (4 semaines) à la période initiale.
- Mise en place d'un batch permettant l'envoi des mails de relance aux usagers n’ayant pas rendu les livres en fin de période de prêt. L’envoi est automatique à la fréquence d’un par jour.
- Dès la release 1.0, les actions de création d’un prêt et retour d’un prêt seront implémentés même si elles ne seront utilisés par le logiciel.

### **Architecture de l'application**

Le projet est découpé en plusieurs microservices :
- config-server : microservice qui appelle un repository git contenant l'ensemble des configurations des microservices
- eureka-server : microservice qui récupère et enregistrel'ensemble des instances des microservices afin de les exposer via une API REST
- zuul-server : microservice qui est le point d'entrée de l'application
- microservice-book : microservice qui gère les livres, auteurs, catégories, exemplaires, emprunts, relié à la BDD librarymbook. Il gère également le batch de relance des livres en retard.
- microservice-user : microservice qui gère les utilisateurs et leurs rôles, relié à la BDD librarymuser
- microservice-clientui : microservice qui permet l'affichage des données du site

## **Déploiement**
1. Installer le JRE d'Oracle 1.8
2. Cloner le projet sous GitHub
3. Installer Maven version minimum 4
4. Installer Tomcat 9
5. Créer deux bases de données sous PostgreSQL :
	- une base de données pour le microservice-user
	- une base de données pour le microservice-book
6. Créer un repository GitHub pour le dossier library-config-repo ou cloner le projet GitHub : https://github.com/LoicPi/Library-config-repo.git
7. Dans le microservice config-server modifier dans le fichier application.properties se trouvant dans le dossier src/main/resources :
	spring.cloud.config.server.git.uri=*"l'adresse de votre repository GitHub"*
8. Dans votre repository library-config-repo, modifier pour les deux microservices (book et user) les informations suivantes concernant les bases de données :
	- le nom : spring.datasource.url=jdbc:postgresql://localhost:5432/*"nom de votre base de données"*
	- l'utilisateur : spring.datasource.username = *"username de la bdd"*
	- le mot de passe : spring.datasource.password =*"votre mot de passe"*
9. Lancez les microservices dans l'ordre suivant :
	1. config-server
	2. eureka-service
	3. zuul-server
	4. microservice-user
	5. microservice-book
	6. clientui
10. Dans PGAdmin via QueryTools implémentez les jeu de données :
	1. Pour le microservice book le fichier de jeu de données se trouve dans le microservice book sous src/main/resources/db/data_librarymbook.sql
	2. Pour le microservice user le fichier de jeu de données se trouve dans le microservice user sous src/main/resources/db/data_librarymuser.sql
11. Dans votre navigateur allez sur la page http://localhost:8080/accueil
12. Vous pouvez vous connectez grâce aux logins suivants :
	- adresse mail : test@test.fr
	- mot de passe : test
13. Vous pouvez dès lors naviguez sur le site de la bibliothèque et accéder au compte test



