# Movie Microservices Platform

Une plateforme de catalogue de films construite avec une architecture microservices utilisant Spring Boot, Spring Cloud, et PostgreSQL.

## 🏗️ Architecture

Le projet est composé de 6 microservices :

- **discovery-server** (Port 8761) - Serveur de découverte Eureka
- **api-gateway** (Port 8080) - Passerelle API Spring Cloud Gateway
- **movie-service** (Port 8081) - Gestion du catalogue de films
- **rating-service** (Port 8082) - Gestion des notes (1-5 étoiles)
- **review-service** (Port 8083) - Gestion des commentaires anonymes
- **stats-service** (Port 8084) - Agrégation des statistiques

## 🚀 Fonctionnalités

### Movie Service
- CRUD complet pour les films (titre, genre, année de sortie, description)
- Recherche par titre, genre, année
- Filtrage et pagination
- API REST avec documentation Swagger

### Rating Service
- Système de notation de 1 à 5 étoiles
- Une note par session utilisateur par film
- Calcul de la moyenne des notes
- Distribution des notes

### Review Service
- Commentaires anonymes avec nom optionnel
- Un commentaire par session utilisateur par film
- Recherche dans les commentaires
- Validation de contenu (10-1000 caractères)

### Stats Service
- Agrégation des données via Feign clients
- Statistiques complètes par film (moyenne, nombre de notes/commentaires)
- Communication inter-services

## 🛠️ Technologies

- **Backend**: Spring Boot 3.2.0, Spring Cloud 2023.0.0
- **Base de données**: PostgreSQL 15
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Communication**: OpenFeign
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Conteneurisation**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
- **Build**: Maven

## 📦 Structure du projet

```
movie-microservices/
├── discovery-server/          # Serveur Eureka
├── api-gateway/              # Passerelle API
├── movie-service/            # Service de films
├── rating-service/           # Service de notes
├── review-service/           # Service de commentaires
├── stats-service/            # Service de statistiques
├── .github/workflows/        # Pipeline CI/CD
├── docker-compose.yml        # Orchestration Docker
├── init-db.sql              # Script d'initialisation DB
└── README.md                 # Documentation
```

## 🚀 Démarrage rapide

### Prérequis
- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL (si exécution locale)

### Option 1: Avec Docker Compose (Recommandé)

1. **Cloner le projet**
```bash
git clone <repository-url>
cd movie-microservices
```

2. **Construire les services**
```bash
mvn clean package -DskipTests
```

3. **Démarrer avec Docker Compose**
```bash
docker-compose up -d
```

4. **Vérifier le déploiement**
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Swagger UI des services: http://localhost:808X/swagger-ui.html

### Option 2: Exécution locale

1. **Démarrer PostgreSQL**
```bash
# Créer les bases de données
createdb movie_db
createdb rating_db
createdb review_db
```

2. **Démarrer les services dans l'ordre**
```bash
# 1. Discovery Server
cd discovery-server && mvn spring-boot:run

# 2. API Gateway
cd api-gateway && mvn spring-boot:run

# 3. Services métier (en parallèle)
cd movie-service && mvn spring-boot:run
cd rating-service && mvn spring-boot:run
cd review-service && mvn spring-boot:run
cd stats-service && mvn spring-boot:run
```

## 📚 API Documentation

### Endpoints principaux via API Gateway (http://localhost:8080)

#### Movies
- `GET /api/movies` - Liste paginée des films
- `POST /api/movies` - Créer un film
- `GET /api/movies/{id}` - Détails d'un film
- `PUT /api/movies/{id}` - Mettre à jour un film
- `DELETE /api/movies/{id}` - Supprimer un film
- `GET /api/movies/search?title=&genre=&releaseYear=` - Recherche

#### Ratings
- `POST /api/ratings` - Ajouter/modifier une note
- `GET /api/ratings/movie/{movieId}` - Notes d'un film
- `GET /api/ratings/movie/{movieId}/average` - Note moyenne
- `GET /api/ratings/movie/{movieId}/count` - Nombre de notes

#### Reviews
- `POST /api/reviews` - Ajouter/modifier un commentaire
- `GET /api/reviews/movie/{movieId}` - Commentaires d'un film
- `GET /api/reviews/movie/{movieId}/count` - Nombre de commentaires

#### Stats
- `GET /api/stats/movie/{movieId}` - Statistiques complètes d'un film

### Documentation Swagger
Chaque service expose sa documentation Swagger :
- Movie Service: http://localhost:8081/swagger-ui.html
- Rating Service: http://localhost:8082/swagger-ui.html
- Review Service: http://localhost:8083/swagger-ui.html
- Stats Service: http://localhost:8084/swagger-ui.html

## 🧪 Tests

### Exécuter les tests
```bash
# Tests unitaires pour tous les services
mvn test

# Tests pour un service spécifique
cd movie-service && mvn test
```

### Tests d'intégration
```bash
# Démarrer l'environnement de test
docker-compose -f docker-compose.test.yml up -d

# Exécuter les tests d'intégration
mvn verify
```

## 🔧 Configuration

### Variables d'environnement Docker
- `SPRING_DATASOURCE_URL` - URL de la base de données
- `SPRING_DATASOURCE_USERNAME` - Utilisateur DB
- `SPRING_DATASOURCE_PASSWORD` - Mot de passe DB
- `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` - URL Eureka

### Profils Spring
- `dev` - Développement local
- `docker` - Conteneurs Docker
- `prod` - Production

## 🚀 Déploiement

### CI/CD avec GitHub Actions
Le pipeline automatique :
1. **Test** - Tests unitaires pour chaque service
2. **Build** - Compilation et packaging Maven
3. **Docker** - Construction des images Docker (branche main)

### Déploiement en production
```bash
# Construire pour la production
mvn clean package -Pprod

# Déployer avec Docker Compose
docker-compose -f docker-compose.prod.yml up -d
```

## 📊 Monitoring

### Health Checks
- Discovery Server: http://localhost:8761/actuator/health
- API Gateway: http://localhost:8080/actuator/health
- Services: http://localhost:808X/actuator/health

### Métriques
Chaque service expose des métriques via Spring Boot Actuator.

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -am 'Ajouter nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Créer une Pull Request

## 📝 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 🆘 Support

Pour toute question ou problème :
1. Vérifier la documentation Swagger
2. Consulter les logs des services
3. Ouvrir une issue sur GitHub

## 🔮 Roadmap

- [ ] Authentification JWT
- [ ] Upload d'images de films
- [ ] Système de recommandations
- [ ] Cache Redis
- [ ] Monitoring avec Prometheus/Grafana
- [ ] Tests end-to-end avec Testcontainers

