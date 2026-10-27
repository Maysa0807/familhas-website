# ADR 0002: IDs UUID armazenados como RAW(16) no Oracle

- Status: aceito
- Data: 27/10/2026
- Fase: 0

## Contexto

As entidades usavam `Long` com sequences Oracle (`seq_*`). O guia técnico (seção 6, item 4) pede IDs UUID com mapeamento compatível com Oracle, documentando a escolha entre `RAW(16)` e `VARCHAR2(36)`.

IDs sequenciais expõem volume de negócio e permitem enumeração de recursos pela API (`/clients/1`, `/clients/2`...). UUID também evita ida ao banco para gerar ID e facilita eventos e integrações futuras (outbox, RabbitMQ).

## Decisão

- Tipo Java `java.util.UUID` em todas as entidades, com `@GeneratedValue(strategy = GenerationType.UUID)`.
- Coluna `RAW(16)` no Oracle. É o mapeamento padrão do Hibernate 6 para `UUID` em bancos sem tipo UUID nativo, ocupa 16 bytes (contra 36 de `VARCHAR2(36)`) e gera índices menores.
- Migration `V4__migrate_ids_to_uuid.sql` recria as tabelas de domínio e reaplica o seed de V2/V3 com UUIDs fixos e legíveis por tabela (ex.: cidades `c1770000-0000-4000-8000-00000000000N`). V1 a V3 não foram alteradas, para não quebrar o checksum do Flyway em bancos já migrados.
- Profile `local` passa a usar `ddl-auto=validate`, para que divergências entre entidades e schema falhem na subida.

## Consequências

- Bancos locais existentes perdem dados criados manualmente ao aplicar V4. Aceitável: só há ambiente de desenvolvimento.
- A API passa a receber e devolver IDs como string UUID. IDs numéricos antigos retornam 400.
- Para consultar no SQL Developer, use `RAWTOHEX(id)` ou `HEXTORAW('...')` no lugar do UUID com hífens.
- `OracleMigrationIntegrationTests` (Testcontainers, `gvenzl/oracle-free`) valida migrations, mapeamento e seed contra um Oracle real. Ele é pulado quando não há Docker disponível.
- Ordem de inserção não é mais dedutível pelo ID. Quando for necessário ordenar, usar as colunas de auditoria `created_at`, previstas na Fase 2.
