# Spring Boot Thymeleaf Keycloak

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-thymeleaf-keycloak/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-thymeleaf-keycloak/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot)
![Keycloak](https://img.shields.io/badge/Keycloak-26-4D4D4D?logo=keycloak)

A Thymeleaf web UI served by Spring Boot and secured with **Keycloak** using OAuth2 / OpenID Connect
(`spring-boot-starter-oauth2-client`). Keycloak realm roles are mapped to Spring Security authorities, so a
page can be restricted to users having a specific role (here: `NICE`).

## Tech Stack

| Component        | Version / Notes                                    |
|------------------|----------------------------------------------------|
| Java             | 25 (Temurin in CI)                                 |
| Spring Boot      | 4.1.2 (Web MVC, Security OAuth2 Client, Thymeleaf) |
| Keycloak         | 26.x (via Docker Compose)                          |
| PostgreSQL       | 17 (Keycloak database)                             |
| Build            | Maven Wrapper (`./mvnw`)                           |
| Testing          | JUnit 5, Spring Security Test, spring-addons       |

## Features

- Login / logout through Keycloak (OIDC Authorization Code flow, RP-initiated logout).
- Keycloak `realm_access.roles` claim mapped to Spring Security `GrantedAuthority`.
- Access rules (`SecurityConfig`):
  - `/` and `/favicon.ico` → public
  - `/nice` → requires the `NICE` authority
  - everything else → denied
- Spring Boot Docker Compose support: Keycloak & PostgreSQL start automatically with the app.
- Actuator with liveness / readiness probes.

## Prerequisites

- JDK 25
- Docker & Docker Compose

## Getting Started

1. Clone the repository

   ```shell
   git clone https://github.com/hendisantika/spring-boot-thymeleaf-keycloak.git
   cd spring-boot-thymeleaf-keycloak
   ```

2. Run the application (Docker Compose for Keycloak + PostgreSQL is started automatically by
   `spring-boot-docker-compose`)

   ```shell
   ./mvnw spring-boot:run
   ```

   Or start the containers manually first:

   ```shell
   docker compose up -d
   ```

3. Open the app at <http://localhost:8080> and click **Login**.

   - Keycloak admin console: <http://localhost:7080> (`admin` / `admin`)
   - Realm `Greylian` is imported from [`keycloak/Greylian-realm.json`](keycloak/Greylian-realm.json)
     and contains the client `spring-boot-thymeleaf-keycloak`, the realm role `NICE` and the user `yuji`.
   - Users having the `NICE` role will see the **Browse to NICE users page** button.

## Configuration

Main settings in [`src/main/resources/application.yml`](src/main/resources/application.yml):

| Property                                                          | Value                                    |
|-------------------------------------------------------------------|------------------------------------------|
| `server.port`                                                     | `8080`                                   |
| `spring.security.oauth2.client.provider.Greylian.issuer-uri`      | `http://localhost:7080/realms/Greylian`  |
| `spring.security.oauth2.client.registration.keycloak.client-id`   | `spring-boot-thymeleaf-keycloak`         |
| `spring.security.oauth2.client.registration.keycloak.scope`       | `openid`                                 |

> ⚠️ The client secret in `application.yml` is for local development only. Use environment variables or a
> secret manager in real deployments.

## Build & Test

```shell
./mvnw clean verify
```

Tests run with the `test` profile ([`src/test/resources/application-test.yml`](src/test/resources/application-test.yml)),
which declares the Keycloak endpoints explicitly so the application context loads **without** a running
Keycloak instance (no OIDC discovery call).

## Continuous Integration & Dependency Updates

- **GitHub Actions** ([`.github/workflows/maven.yml`](.github/workflows/maven.yml)): builds and tests on JDK 25
  for every push / pull request to `main`, and submits the Maven dependency graph on `main`.
- **Dependabot** ([`.github/dependabot.yml`](.github/dependabot.yml)): grouped updates for Maven dependencies
  (Spring, spring-addons, Maven plugins, test deps), GitHub Actions and Docker Compose images.

## Author

**Hendi Santika** — [s.id/hendisantika](https://s.id/hendisantika) · Telegram [@hendisantika34](https://t.me/hendisantika34)
