-- Script d'initialisation des bases de données pour les microservices

-- Créer les bases de données pour chaque service
CREATE DATABASE movie_db;
CREATE DATABASE rating_db;
CREATE DATABASE review_db;

-- Optionnel: Créer des utilisateurs spécifiques pour chaque service
-- CREATE USER movie_user WITH PASSWORD 'movie_password';
-- CREATE USER rating_user WITH PASSWORD 'rating_password';
-- CREATE USER review_user WITH PASSWORD 'review_password';

-- Optionnel: Accorder les privilèges
-- GRANT ALL PRIVILEGES ON DATABASE movie_db TO movie_user;
-- GRANT ALL PRIVILEGES ON DATABASE rating_db TO rating_user;
-- GRANT ALL PRIVILEGES ON DATABASE review_db TO review_user;

