UPDATE categories
SET image_url = 'https://harus.ind.br/wp-content/uploads/2023/02/5-praias-brasileiras.jpg'
WHERE id = 1;

UPDATE categories
SET image_url = 'https://blog.lelloimoveis.com.br/wp-content/uploads/2022/04/Arquitetura-em-Sao-Paulo-lugares-para-se-apaixonar-2-1.jpg'
WHERE id = 2;

MERGE INTO cities c
    USING (
        SELECT 4 AS id, 'Santiago' AS name, 'Chile' AS country, 'DESTINATION' AS city_type, 'ACTIVE' AS status FROM dual
        UNION ALL
        SELECT 5, 'Orlando', 'Estados Unidos', 'DESTINATION', 'ACTIVE' FROM dual
        UNION ALL
        SELECT 6, 'Brasilia', 'Brasil', 'ORIGIN', 'ACTIVE' FROM dual
    ) src
    ON (c.id = src.id)
    WHEN MATCHED THEN
        UPDATE SET
            c.name = src.name,
            c.country = src.country,
            c.city_type = src.city_type,
            c.status = src.status
    WHEN NOT MATCHED THEN
        INSERT (id, name, country, city_type, status)
            VALUES (src.id, src.name, src.country, src.city_type, src.status);

MERGE INTO categories c
    USING (
        SELECT
            3 AS id,
            'Negocios e Trabalho' AS title,
            'ACTIVE' AS status,
            'https://images.ctfassets.net/1aemqu6a6t65/5rPsNLkgpwvZPSmjhE5ChB/ab1a60f5fa9ca6e331833e97044a6345/wall-street-photo-tagger-yancey-iv-nyc-and-company-02-2?w=1200&h=800&q=75' AS image_url,
            'Viagens profissionais para reunioes, feiras, congressos e eventos, impulsionando a economia' AS description
        FROM dual
    ) src
    ON (c.id = src.id)
    WHEN MATCHED THEN
        UPDATE SET
            c.title = src.title,
            c.status = src.status,
            c.image_url = src.image_url,
            c.description = src.description
    WHEN NOT MATCHED THEN
        INSERT (id, title, status, image_url, description)
            VALUES (src.id, src.title, src.status, src.image_url, src.description);

MERGE INTO flights f
    USING (
        SELECT
            3 AS id,
            'AZUL' AS company_name,
            TIMESTAMP '2026-05-22 06:20:00' AS boarding_date,
            TIMESTAMP '2026-05-25 20:45:00' AS return_date,
            'BSB - Aeroporto Internacional de Brasilia' AS boarding_airport,
            'CGH - Aeroporto de Sao Paulo/Congonhas- Deputado Freitas Nobre' AS return_airport
        FROM dual
    ) src
    ON (f.id = src.id)
    WHEN MATCHED THEN
        UPDATE SET
            f.company_name = src.company_name,
            f.boarding_date = src.boarding_date,
            f.return_date = src.return_date,
            f.boarding_airport = src.boarding_airport,
            f.return_airport = src.return_airport
    WHEN NOT MATCHED THEN
        INSERT (id, company_name, boarding_date, return_date, boarding_airport, return_airport)
            VALUES (src.id, src.company_name, src.boarding_date, src.return_date, src.boarding_airport, src.return_airport);