INSERT INTO categories (id, title, status, image_url, description)
VALUES (
    1,
    'Praia e Resort',
    'ACTIVE',
    'https://images.example.com/categories/praia-resort.jpg',
    'Pacotes para destinos de praia com foco em descanso, mar e hospedagem confortavel.'
);

INSERT INTO categories (id, title, status, image_url, description)
VALUES (
    2,
    'Urbano e Cultura',
    'ACTIVE',
    'https://images.example.com/categories/urbano-cultura.jpg',
    'Roteiros para capitais e destinos historicos com experiencias gastronomicas e culturais.'
);

INSERT INTO cities (id, name, country, city_type, status)
VALUES (1, 'Sao Paulo', 'Brasil', 'ORIGIN', 'ACTIVE');

INSERT INTO cities (id, name, country, city_type, status)
VALUES (2, 'Maceio', 'Brasil', 'DESTINATION', 'ACTIVE');

INSERT INTO cities (id, name, country, city_type, status)
VALUES (3, 'Buenos Aires', 'Argentina', 'DESTINATION', 'ACTIVE');

INSERT INTO flights (id, company_name, boarding_date, return_date, boarding_airport, return_airport)
VALUES (
    1,
    'Gol Linhas Aereas',
    TIMESTAMP '2026-09-10 06:20:00',
    TIMESTAMP '2026-09-17 20:45:00',
    'GRU - Guarulhos',
    'MCZ - Zumbi dos Palmares'
);

INSERT INTO flights (id, company_name, boarding_date, return_date, boarding_airport, return_airport)
VALUES (
    2,
    'LATAM Airlines',
    TIMESTAMP '2026-10-03 11:10:00',
    TIMESTAMP '2026-10-10 22:30:00',
    'GRU - Guarulhos',
    'AEP - Aeroparque Jorge Newbery'
);

INSERT INTO travel_packages (
    id,
    title,
    price,
    price_promotion,
    status,
    image_url,
    flight_id,
    category_id,
    origin_city_id,
    destination_city_id
) VALUES (
    1,
    'Maceio Resort All Inclusive - 7 noites',
    3299.90,
    2799.90,
    'ACTIVE',
    'https://images.example.com/packages/maceio-resort.jpg',
    1,
    1,
    1,
    2
);

INSERT INTO travel_packages (
    id,
    title,
    price,
    price_promotion,
    status,
    image_url,
    flight_id,
    category_id,
    origin_city_id,
    destination_city_id
) VALUES (
    2,
    'Buenos Aires Classica com City Tour - 7 noites',
    3599.00,
    3099.00,
    'ACTIVE',
    'https://images.example.com/packages/buenos-aires-classica.jpg',
    2,
    2,
    1,
    3
);

INSERT INTO clients (id, name, email, phone_number, password, status)
VALUES (
    1,
    'Ana Souza',
    'ana.souza@familhas.com',
    '+55-11-99888-1001',
    'senha-teste-123',
    'ACTIVE'
);

INSERT INTO clients (id, name, email, phone_number, password, status)
VALUES (
    2,
    'Bruno Lima',
    'bruno.lima@familhas.com',
    '+55-11-97777-2002',
    'senha-teste-456',
    'ACTIVE'
);

INSERT INTO ratings (id, rating, client_id, travel_package_id)
VALUES (
    1,
    5,
    1,
    1
);

INSERT INTO ratings (id, rating, client_id, travel_package_id)
VALUES (
    2,
    4,
    2,
    2
);

INSERT INTO client_favorites (client_id, travel_package_id)
VALUES (1, 1);

INSERT INTO client_favorites (client_id, travel_package_id)
VALUES (1, 2);

INSERT INTO client_favorites (client_id, travel_package_id)
VALUES (2, 1);

INSERT INTO client_flights (client_id, travel_package_id)
VALUES (1, 1);

INSERT INTO client_flights (client_id, travel_package_id)
VALUES (2, 2);

