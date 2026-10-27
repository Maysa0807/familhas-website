# ADR 0001: Monorepo e pacote base br.com.familhas

- Status: aceito
- Data: 27/10/2026
- Fase: 0

## Contexto

O repositório tinha estrutura plana (`src/`, `pom.xml` na raiz) e pacote `com.app.familhas_website`. O guia técnico (seção 6, item 1) prevê quatro aplicações (`familhas-api`, `familhas-email`, `familhas-web`, `familhas-admin`) com nomes padronizados e um pacote base único.

## Decisão

- Monorepo com a estrutura:
  - `backend/familhas-api` (Spring Boot, monólito modular)
  - `backend/familhas-email` (Fase 4)
  - `frontend/familhas-web` (Fase 7)
  - `frontend/familhas-admin` (Fase 10)
  - `infra/` (Docker Compose, Keycloak, Oracle)
  - `docs/adr/`
- Arquivos movidos com `git mv` para preservar o histórico (`git log --follow` funciona).
- Pacote base `br.com.familhas`, groupId `br.com.familhas`, artifactId `familhas-api`.
- Cada aplicação tem o próprio build (Maven wrapper no backend, npm nos frontends). Não há POM agregador por enquanto. Ele só entra se surgir código compartilhado entre `familhas-api` e `familhas-email`.

## Consequências

- Comandos Maven passam a rodar dentro de `backend/familhas-api`.
- As pastas das aplicações futuras só são criadas na fase correspondente, para não adiantar escopo.
- Branches abertas antes desta mudança precisam de rebase. Git detecta os renames, mas conflitos em arquivos movidos e editados ao mesmo tempo podem exigir resolução manual.
