
# Microservices + OSS stack add-on

## New services
- `services/payments-svc` (Spring Boot 3, Kafka producer)
- `services/documents-svc` (Spring Boot 3)
- `services/scheduling-svc` (Spring Boot 3)
- `services/notifications-svc` (Spring Boot 3, Kafka consumer)
- `services/workflow-svc` (Spring Boot 3 + Flowable BPMN; processes: Scheme-I/IV/X)

## API Gateway
- **Kong (OSS)** with declarative config at `infra/kong/kong.yml`
- Routes mounted under `/api/*` → respective services

## Event Bus
- **Apache Kafka** (bitnami) in KRaft mode + **Kafka-UI** at `http://localhost:8088`

## Observability (OSS)
- **Prometheus** + **Grafana** (basic scrape config included)
- **OpenSearch** + **OpenSearch Dashboards** for logs & security analytics
  - ISM policy at `infra/opensearch/ism-policy.json`
  - Snapshot repo script `infra/opensearch/add_snapshot_repo.sh` (configure a MinIO bucket with Object Lock for WORM)
  - Apply ISM to indices:
    ```bash
    curl -XPUT localhost:9200/_plugins/_ism/policies/default-policy -H 'Content-Type: application/json' --data-binary @infra/opensearch/ism-policy.json
    ```

## Run (microservices & gateway & observability)
```bash
docker compose -f docker-compose.micro.yml up -d --build
# Kong: http://localhost:8000
# Kafka-UI: http://localhost:8088
# OpenSearch Dashboards: http://localhost:5601
# Prometheus: http://localhost:9090
# Grafana: http://localhost:3000 (admin/admin)
```
