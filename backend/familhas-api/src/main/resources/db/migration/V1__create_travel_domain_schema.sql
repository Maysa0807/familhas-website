CREATE TABLE categories (
    id NUMBER(10,0) NOT NULL,
    title VARCHAR2(120 CHAR) NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    image_url VARCHAR2(500 CHAR),
    description VARCHAR2(1000 CHAR),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE cities (
    id NUMBER(10,0) NOT NULL,
    name VARCHAR2(120 CHAR) NOT NULL,
    country VARCHAR2(120 CHAR) NOT NULL,
    city_type VARCHAR2(20 CHAR) NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT pk_cities PRIMARY KEY (id)
);

CREATE TABLE flights (
    id NUMBER(10,0) NOT NULL,
    company_name VARCHAR2(120 CHAR) NOT NULL,
    boarding_date TIMESTAMP(6) NOT NULL,
    return_date TIMESTAMP(6) NOT NULL,
    boarding_airport VARCHAR2(120 CHAR) NOT NULL,
    return_airport VARCHAR2(120 CHAR) NOT NULL,
    CONSTRAINT pk_flights PRIMARY KEY (id)
);

CREATE TABLE clients (
    id NUMBER(10,0) NOT NULL,
    name VARCHAR2(180 CHAR) NOT NULL,
    email VARCHAR2(180 CHAR) NOT NULL,
    phone_number VARCHAR2(40 CHAR),
    password VARCHAR2(225 CHAR) NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT pk_clients PRIMARY KEY (id),
    CONSTRAINT uk_clients_email UNIQUE (email)
);

CREATE TABLE travel_packages (
    id NUMBER(10,0) NOT NULL,
    title VARCHAR2(180 CHAR) NOT NULL,
    price NUMBER(12, 2) NOT NULL,
    price_promotion NUMBER(12, 2),
    status VARCHAR2(20 CHAR) NOT NULL,
    image_url VARCHAR2(500 CHAR),
    flight_id NUMBER(10,0) NOT NULL,
    category_id NUMBER(10,0) NOT NULL,
    origin_city_id NUMBER(10,0) NOT NULL,
    destination_city_id NUMBER(10,0) NOT NULL,
    CONSTRAINT pk_travel_packages PRIMARY KEY (id),
    CONSTRAINT fk_tp_flight FOREIGN KEY (flight_id) REFERENCES flights (id),
    CONSTRAINT fk_tp_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_tp_origin FOREIGN KEY (origin_city_id) REFERENCES cities (id),
    CONSTRAINT fk_tp_destination FOREIGN KEY (destination_city_id) REFERENCES cities (id)
);

CREATE TABLE ratings (
    id NUMBER(10,0) NOT NULL,
    rating NUMBER(10) NOT NULL,
    client_id NUMBER(10,0) NOT NULL,
    travel_package_id NUMBER(10,0) NOT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (id),
    CONSTRAINT fk_ratings_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_ratings_travel_package FOREIGN KEY (travel_package_id) REFERENCES travel_packages (id),
    CONSTRAINT uk_rating_client_package UNIQUE (client_id, travel_package_id)
);

CREATE TABLE client_favorites (
    client_id NUMBER(10,0) NOT NULL,
    travel_package_id NUMBER(10,0) NOT NULL,
    CONSTRAINT pk_client_favorites PRIMARY KEY (client_id, travel_package_id),
    CONSTRAINT fk_client_favorites_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_client_favorites_package FOREIGN KEY (travel_package_id) REFERENCES travel_packages (id)
);

CREATE TABLE client_flights (
    client_id NUMBER(10,0) NOT NULL,
    travel_package_id NUMBER(10,0) NOT NULL,
    CONSTRAINT pk_client_flights PRIMARY KEY (client_id, travel_package_id),
    CONSTRAINT fk_client_flights_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_client_flights_package FOREIGN KEY (travel_package_id) REFERENCES travel_packages (id)
);

CREATE SEQUENCE seq_categories START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_cities START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_flights START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_clients START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_travel_packages START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_ratings START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
