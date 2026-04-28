# Infra local

Este diretório concentra o bootstrap da stack local da aplicação (`oracle`, `rabbitmq`, `keycloak` e `mailhog`) sem configuração manual no painel do Keycloak. 

## Subida da infraestrutura

```bash
docker compose -f infra/docker-compose.yml up -d
```

## O que sobe automaticamente no Keycloak

- Realm `familhas` importado de `infra/keycloak/data/import/familhas-realm.json`
- Roles: `ROLE_USER`, `ROLE_ADMIN`
- Clients:
  - `familhas-api` (confidential)
  - `familhas-web` (public)
- Usuários de teste:
  - `admin.familhas` / `admin123`
  - `user.familhas` / `user123`

## HTTPS local do Keycloak

- O container executa `infra/keycloak/scripts/start-with-keystore.sh`.
- Se `infra/keycloak/keystore/keystore.p12` nao existir, o script gera automaticamente um keystore self-signed para uso local.
- Porta exposta: `8443` (ou `KEYCLOAK_HTTPS_PORT` definida no `.env`).

## Driver Oracle no Keycloak

- A imagem do Keycloak e buildada localmente por `infra/keycloak/Dockerfile`.
- O Dockerfile adiciona o `ojdbc17.jar` (em `infra/keycloak/providers/ojdbc17.jar`) para habilitar `KC_DB=oracle`.

## Endpoints úteis para validação

- Admin Console: `https://localhost:8443`
- OpenID configuration:
  - `https://localhost:8443/realms/familhas/.well-known/openid-configuration`
- Token endpoint:
  - `https://localhost:8443/realms/familhas/protocol/openid-connect/token`

## Observação sobre Oracle

- O Keycloak usa o mesmo Oracle, com usuário/schema dedicado (`KEYCLOAK` por padrão), criado via `infra/oracle/init/01-create-keycloak-user.sql`.
