-- V4: migra os IDs de NUMBER (sequence) para UUID em RAW(16).
-- Ver docs/adr/0002-ids-uuid-raw16.md.
--
-- O Oracle nao converte NUMBER para RAW(16) in-place com dados, entao as tabelas
-- de dominio sao recriadas e o seed de V2/V3 e reaplicado com UUIDs fixos.
-- ATENCAO: dados criados manualmente em ambientes locais sao descartados.
--
-- Convencao dos UUIDs de seed (v4 valido, prefixo legivel por tabela):
--   categories ca7e0000-0000-4000-8000-00000000000N
--   cities     c1770000-0000-4000-8000-00000000000N
--   flights    f1170000-0000-4000-8000-00000000000N
--   packages   bac00000-0000-4000-8000-00000000000N
--   clients    c11e0000-0000-4000-8000-00000000000N
--   ratings    4a7e0000-0000-4000-8000-00000000000N

DROP TABLE client_flights CASCADE CONSTRAINTS PURGE;
DROP TABLE client_favorites CASCADE CONSTRAINTS PURGE;
DROP TABLE ratings CASCADE CONSTRAINTS PURGE;
DROP TABLE travel_packages CASCADE CONSTRAINTS PURGE;
DROP TABLE clients CASCADE CONSTRAINTS PURGE;
DROP TABLE flights CASCADE CONSTRAINTS PURGE;
DROP TABLE cities CASCADE CONSTRAINTS PURGE;
DROP TABLE categories CASCADE CONSTRAINTS PURGE;

DROP SEQUENCE seq_categories;
DROP SEQUENCE seq_cities;
DROP SEQUENCE seq_flights;
DROP SEQUENCE seq_clients;
DROP SEQUENCE seq_travel_packages;
DROP SEQUENCE seq_ratings;

CREATE TABLE categories (
    id RAW(16) NOT NULL,
    title VARCHAR2(120 CHAR) NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    image_url VARCHAR2(500 CHAR),
    description VARCHAR2(1000 CHAR),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE cities (
    id RAW(16) NOT NULL,
    name VARCHAR2(120 CHAR) NOT NULL,
    country VARCHAR2(120 CHAR) NOT NULL,
    city_type VARCHAR2(20 CHAR) NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT pk_cities PRIMARY KEY (id)
);

CREATE TABLE flights (
    id RAW(16) NOT NULL,
    company_name VARCHAR2(120 CHAR) NOT NULL,
    boarding_date TIMESTAMP(6) NOT NULL,
    return_date TIMESTAMP(6) NOT NULL,
    boarding_airport VARCHAR2(120 CHAR) NOT NULL,
    return_airport VARCHAR2(120 CHAR) NOT NULL,
    CONSTRAINT pk_flights PRIMARY KEY (id)
);

CREATE TABLE clients (
    id RAW(16) NOT NULL,
    name VARCHAR2(180 CHAR) NOT NULL,
    email VARCHAR2(180 CHAR) NOT NULL,
    phone_number VARCHAR2(40 CHAR),
    password VARCHAR2(225 CHAR) NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT pk_clients PRIMARY KEY (id),
    CONSTRAINT uk_clients_email UNIQUE (email)
);

CREATE TABLE travel_packages (
    id RAW(16) NOT NULL,
    title VARCHAR2(180 CHAR) NOT NULL,
    price NUMBER(12, 2) NOT NULL,
    price_promotion NUMBER(12, 2),
    status VARCHAR2(20 CHAR) NOT NULL,
    image_url VARCHAR2(500 CHAR),
    flight_id RAW(16) NOT NULL,
    category_id RAW(16) NOT NULL,
    origin_city_id RAW(16) NOT NULL,
    destination_city_id RAW(16) NOT NULL,
    CONSTRAINT pk_travel_packages PRIMARY KEY (id),
    CONSTRAINT fk_tp_flight FOREIGN KEY (flight_id) REFERENCES flights (id),
    CONSTRAINT fk_tp_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_tp_origin FOREIGN KEY (origin_city_id) REFERENCES cities (id),
    CONSTRAINT fk_tp_destination FOREIGN KEY (destination_city_id) REFERENCES cities (id)
);

CREATE TABLE ratings (
    id RAW(16) NOT NULL,
    rating NUMBER(10) NOT NULL,
    client_id RAW(16) NOT NULL,
    travel_package_id RAW(16) NOT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (id),
    CONSTRAINT fk_ratings_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_ratings_travel_package FOREIGN KEY (travel_package_id) REFERENCES travel_packages (id),
    CONSTRAINT uk_rating_client_package UNIQUE (client_id, travel_package_id)
);

CREATE TABLE client_favorites (
    client_id RAW(16) NOT NULL,
    travel_package_id RAW(16) NOT NULL,
    CONSTRAINT pk_client_favorites PRIMARY KEY (client_id, travel_package_id),
    CONSTRAINT fk_client_favorites_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_client_favorites_package FOREIGN KEY (travel_package_id) REFERENCES travel_packages (id)
);

CREATE TABLE client_flights (
    client_id RAW(16) NOT NULL,
    travel_package_id RAW(16) NOT NULL,
    CONSTRAINT pk_client_flights PRIMARY KEY (client_id, travel_package_id),
    CONSTRAINT fk_client_flights_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_client_flights_package FOREIGN KEY (travel_package_id) REFERENCES travel_packages (id)
);

-- Seed: estado final de V2 + V3, com UUIDs fixos

INSERT INTO categories (id, title, status, image_url, description) VALUES (
    HEXTORAW('CA7E0000000040008000000000000001'),
    'Praia e Resort',
    'ACTIVE',
    'https://harus.ind.br/wp-content/uploads/2023/02/5-praias-brasileiras.jpg',
    'Pacotes para destinos de praia com foco em descanso, mar e hospedagem confortavel.');

INSERT INTO categories (id, title, status, image_url, description) VALUES (
    HEXTORAW('CA7E0000000040008000000000000002'),
    'Urbano e Cultura',
    'ACTIVE',
    'https://blog.lelloimoveis.com.br/wp-content/uploads/2022/04/Arquitetura-em-Sao-Paulo-lugares-para-se-apaixonar-2-1.jpg',
    'Roteiros para capitais e destinos historicos com experiencias gastronomicas e culturais.');

INSERT INTO categories (id, title, status, image_url, description) VALUES (
    HEXTORAW('CA7E0000000040008000000000000003'),
    'Negocios e Trabalho',
    'ACTIVE',
    'https://images.ctfassets.net/1aemqu6a6t65/5rPsNLkgpwvZPSmjhE5ChB/ab1a60f5fa9ca6e331833e97044a6345/wall-street-photo-tagger-yancey-iv-nyc-and-company-02-2?w=1200&h=800&q=75',
    'Viagens profissionais para reunioes, feiras, congressos e eventos, impulsionando a economia');

INSERT INTO cities (id, name, country, city_type, status) VALUES (HEXTORAW('C1770000000040008000000000000001'), 'Sao Paulo', 'Brasil', 'ORIGIN', 'ACTIVE');
INSERT INTO cities (id, name, country, city_type, status) VALUES (HEXTORAW('C1770000000040008000000000000002'), 'Maceio', 'Brasil', 'DESTINATION', 'ACTIVE');
INSERT INTO cities (id, name, country, city_type, status) VALUES (HEXTORAW('C1770000000040008000000000000003'), 'Buenos Aires', 'Argentina', 'DESTINATION', 'ACTIVE');
INSERT INTO cities (id, name, country, city_type, status) VALUES (HEXTORAW('C1770000000040008000000000000004'), 'Santiago', 'Chile', 'DESTINATION', 'ACTIVE');
INSERT INTO cities (id, name, country, city_type, status) VALUES (HEXTORAW('C1770000000040008000000000000005'), 'Orlando', 'Estados Unidos', 'DESTINATION', 'ACTIVE');
INSERT INTO cities (id, name, country, city_type, status) VALUES (HEXTORAW('C1770000000040008000000000000006'), 'Brasilia', 'Brasil', 'ORIGIN', 'ACTIVE');

INSERT INTO flights (id, company_name, boarding_date, return_date, boarding_airport, return_airport) VALUES (
    HEXTORAW('F1170000000040008000000000000001'), 'Gol Linhas Aereas',
    TIMESTAMP '2026-09-10 06:20:00', TIMESTAMP '2026-09-17 20:45:00',
    'GRU - Guarulhos', 'MCZ - Zumbi dos Palmares');

INSERT INTO flights (id, company_name, boarding_date, return_date, boarding_airport, return_airport) VALUES (
    HEXTORAW('F1170000000040008000000000000002'), 'LATAM Airlines',
    TIMESTAMP '2026-10-03 11:10:00', TIMESTAMP '2026-10-10 22:30:00',
    'GRU - Guarulhos', 'AEP - Aeroparque Jorge Newbery');

INSERT INTO flights (id, company_name, boarding_date, return_date, boarding_airport, return_airport) VALUES (
    HEXTORAW('F1170000000040008000000000000003'), 'AZUL',
    TIMESTAMP '2026-05-22 06:20:00', TIMESTAMP '2026-05-25 20:45:00',
    'BSB - Aeroporto Internacional de Brasilia', 'CGH - Aeroporto de Sao Paulo/Congonhas- Deputado Freitas Nobre');

INSERT INTO travel_packages (id, title, price, price_promotion, status, image_url, flight_id, category_id, origin_city_id, destination_city_id) VALUES (
    HEXTORAW('BAC00000000040008000000000000001'),
    'Maceio Resort All Inclusive - 7 noites', 3299.90, 2799.90, 'ACTIVE',
    'https://images.example.com/packages/maceio-resort.jpg',
    HEXTORAW('F1170000000040008000000000000001'),
    HEXTORAW('CA7E0000000040008000000000000001'),
    HEXTORAW('C1770000000040008000000000000001'),
    HEXTORAW('C1770000000040008000000000000002'));

INSERT INTO travel_packages (id, title, price, price_promotion, status, image_url, flight_id, category_id, origin_city_id, destination_city_id) VALUES (
    HEXTORAW('BAC00000000040008000000000000002'),
    'Buenos Aires Classica com City Tour - 7 noites', 3599.00, 3099.00, 'ACTIVE',
    'https://images.example.com/packages/buenos-aires-classica.jpg',
    HEXTORAW('F1170000000040008000000000000002'),
    HEXTORAW('CA7E0000000040008000000000000002'),
    HEXTORAW('C1770000000040008000000000000001'),
    HEXTORAW('C1770000000040008000000000000003'));

-- Clientes de teste. O campo password sai na Fase 3 (Keycloak como fonte de identidade).
INSERT INTO clients (id, name, email, phone_number, password, status) VALUES (
    HEXTORAW('C11E0000000040008000000000000001'), 'Ana Souza', 'ana.souza@familhas.com', '+55-11-99888-1001', 'senha-teste-123', 'ACTIVE');

INSERT INTO clients (id, name, email, phone_number, password, status) VALUES (
    HEXTORAW('C11E0000000040008000000000000002'), 'Bruno Lima', 'bruno.lima@familhas.com', '+55-11-97777-2002', 'senha-teste-456', 'ACTIVE');

INSERT INTO ratings (id, rating, client_id, travel_package_id) VALUES (
    HEXTORAW('4A7E0000000040008000000000000001'), 5,
    HEXTORAW('C11E0000000040008000000000000001'), HEXTORAW('BAC00000000040008000000000000001'));

INSERT INTO ratings (id, rating, client_id, travel_package_id) VALUES (
    HEXTORAW('4A7E0000000040008000000000000002'), 4,
    HEXTORAW('C11E0000000040008000000000000002'), HEXTORAW('BAC00000000040008000000000000002'));

INSERT INTO client_favorites (client_id, travel_package_id) VALUES (HEXTORAW('C11E0000000040008000000000000001'), HEXTORAW('BAC00000000040008000000000000001'));
INSERT INTO client_favorites (client_id, travel_package_id) VALUES (HEXTORAW('C11E0000000040008000000000000001'), HEXTORAW('BAC00000000040008000000000000002'));
INSERT INTO client_favorites (client_id, travel_package_id) VALUES (HEXTORAW('C11E0000000040008000000000000002'), HEXTORAW('BAC00000000040008000000000000001'));

INSERT INTO client_flights (client_id, travel_package_id) VALUES (HEXTORAW('C11E0000000040008000000000000001'), HEXTORAW('BAC00000000040008000000000000001'));
INSERT INTO client_flights (client_id, travel_package_id) VALUES (HEXTORAW('C11E0000000040008000000000000002'), HEXTORAW('BAC00000000040008000000000000002'));
