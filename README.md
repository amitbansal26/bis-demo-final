
# BIS Manakonline Demo — Spring Boot 3 + Vue 3 (Hindi default)

A runnable demo that models core flows of the BIS Manakonline Portal (MOP).  
Back end: Spring Boot 3 (Java 21, H2, JPA).  
Front end: Vue 3 (Vite, Router, Pinia, vue-i18n), **Hindi (hi) is default**.

## Quick start

### Backend
```bash
cd backend
./mvnw spring-boot:run   # or mvn spring-boot:run
```
Open Swagger UI: http://localhost:8080/swagger-ui/index.html

### Frontend
```bash
cd frontend
npm i
npm run dev
```
Open http://localhost:5173

### QA sandbox environment

Use the dedicated Docker Compose file to spin up an isolated "QA" stack that
won't clash with the default local ports:

```bash
docker compose -f docker-compose.qa.yml up -d --build
# Backend:   http://localhost:8180
# Frontend:  http://localhost:6173
# Keycloak:  http://localhost:8181
# MinIO UI:  http://localhost:9101
```
Tear it down with `docker compose -f docker-compose.qa.yml down` when done.

## What this demo covers

- **User Registration with OTP mock**, duplicate checks for mobile/email, welcome message stub.  
- **Application submission** for multiple schemes (I/III/IV/X) capturing IS No., product, and scheme, with status machine (SUBMITTED → UNDER_SCRUTINY → PI_SCHEDULED → LICENSE_GRANTED).  
- **Activity log** tracks key actions (APPLICATION_RECEIVED, FEE_PAID, PI_DONE, LICENSE_GRANTED).  
- **Document references** stubbed.  
- **Hindi-first UI** with runtime language toggle (hi default, en fallback).

## Functional requirements → Technical mapping

- **Modules & scope** → We model *User Management* and *Core Activities (Product Certification, CoC, MCS, OTR)* with minimal entities and APIs.  
- **User Registration (mobile/email unique, Aadhaar/GST/MSME validations)** → `PublicApi.register`, uniqueness checks at DB level; external validations mocked, to be integrated later via adapters.  
- **SSO-ready** → Security scaffold with Spring Security; in demo, `/api/public/**` open and `/api/admin/**` protected.  
- **Application Submission** → `ApplicationApi.create` creates Scheme-specific applications with reuse of user info, status transitions via `/api/applications/transition`.  
- **Activity Log export/reporting** → `ActivityLog` entity; add an endpoint to export CSV (left as exercise).  
- **Integrations (GST, MSME, LIMS)** → Represented as stubs/adapters (to be added under `integration/` package), keeping hexagonal architecture.  
- **GoL/GoR document generation with QR** → Demo stubs via `DocumentRef`; hook a PDF generator later.  
- **Change in Scope** → Modeled as a status transition + new application record linked to original (omitted in code for brevity).  
- **Help tooltips, guidance, duplicate checks** → Frontend forms provide minimal hints; extend with per-field help and validations.  
- **Admin/Officer dashboard** → Not included; route placeholder can be added at `/admin` with auth.

## Structure

```
backend/
  src/main/java/com/trigyn/bis/demo
    domain/    # JPA entities
    repo/      # Spring Data repositories
    api/       # REST controllers
    SecurityConfig.java
  src/main/resources/application.yml
  pom.xml

frontend/
  src/
    plugins/i18n.js         # Hindi default, English fallback
    router/index.js         # Vue Router
    views/{Home,Register,Apply,Track}.vue
    App.vue, main.js
  package.json, vite.config.js, index.html
```

## Next steps (fit to RFP)
- Add workflow engine or state machine with role-based guards.
- Implement file upload service with virus scan.
- Add payments (UPI/NetBanking) mock.
- Integrate GIS (tiles & location capture) and chat.
- Implement CSV/Excel export for logs and applications.


## Extended Features
- **File uploads + AV scan (stub)**: `/api/public/upload` rejects obvious threats; pluggable for ClamAV.
- **Payments mock**: `/api/public/payments/init` returns UPI intent; `/status/{txnId}` simulates success.
- **Officer dashboard (role-guarded)**: `/api/officer/**` (use `officer/officer`) acts on applications.
- **CSV export**: `/api/admin/export/applications.csv` (use `admin/admin`) for bulk reporting.
- **GIS widget**: Leaflet map with OSM tiles, easy to extend for inspections/geofencing.
- **In‑app chat**: STOMP over SockJS (`/ws`) broadcasting to `/topic/messages`.

### Demo credentials
- Applicant: `applicant/applicant`
- Officer: `officer/officer`
- Admin: `admin/admin`

### Notes
- Security uses in‑memory users for demo; wire to Keycloak or DB-backed users for production.
- AV scanning is a stub; integrate ClamAV/ICAP in `UploadApi` for real scanning.
- Payments are mocked; replace with gateway SDKs/webhooks (UPI, NetBanking).


## Security: Keycloak (OIDC) + RBAC
- Uses Spring Security OAuth2 Resource Server (JWT). Configure your Keycloak realm at:
  - `spring.security.oauth2.resourceserver.jwt.issuer-uri`
  - `spring.security.oauth2.resourceserver.jwt.jwk-set-uri`
- Map realm/client roles to `ROLE_OFFICER`, `ROLE_ADMIN`, etc.
- CORS locked to `cors.allowed-origins` (defaults to `http://localhost:5173`).

## File uploads with AV scan + S3/MinIO
- ClamAV streaming scan via `ClamAVClient` before storage.
- Allowed MIME types via `clamav.allowed-mime`.
- Clean files uploaded to S3-compatible storage with presigned URL returned.
- Configure MinIO/S3 in `storage.s3.*` props. Requires bucket pre-created.

## Payments: gateway + webhook verification
- `POST /api/public/payments/init` returns gateway checkout URL (placeholder).
- Webhook endpoint `/api/public/webhook/payments` validates HMAC signature (`payments.webhook.secret`).

## Officer Queues + PI Scheduling
- `/api/officer/queue?scheme=Scheme-I` lists apps by scheme.
- `/api/officer/pi/schedule` to schedule Product Inspection; persists `InspectionSchedule` and logs `PI_SCHEDULED`.

## Exports
- CSV: `/api/admin/export/applications.csv`
- Excel (XLSX): `/api/admin/export/activity.xlsx`

## CSRF
- Stateless JWT APIs: CSRF disabled; Webhook and WebSocket paths are ignored. For cookie-based sessions, enable Cookie CSRF.


## Postgres + Liquibase profile
Run backend with Postgres & Liquibase:
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```
This uses `application-postgres.yml` (disables DDL auto, applies Liquibase changelog).
"# bis-demo-final" 
