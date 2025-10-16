#!/usr/bin/env bash
# Registers S3/MinIO snapshot repo with object-lock enabled bucket (set MINIO credentials accordingly)
OPENSEARCH=http://localhost:9200
REPO=minio-repo
S3_ENDPOINT=http://localhost:9000
BUCKET=opensearch-snapshots
ACCESS_KEY=minioadmin
SECRET_KEY=minioadmin
REGION=us-east-1

curl -XPUT "$OPENSEARCH/_snapshot/$REPO" -H 'Content-Type: application/json' -d '{
  "type": "s3",
  "settings": {
    "endpoint": "'$S3_ENDPOINT'",
    "bucket": "'$BUCKET'",
    "base_path": "snapshots",
    "protocol": "http",
    "access_key": "'$ACCESS_KEY'",
    "secret_key": "'$SECRET_KEY'",
    "region": "'$REGION'"
  }
}'
echo
