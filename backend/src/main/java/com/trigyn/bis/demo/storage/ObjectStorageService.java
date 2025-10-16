package com.trigyn.bis.demo.storage;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
public class ObjectStorageService {
  private final MinioClient client;
  private final String bucket;
  private final int expiry;

  public ObjectStorageService(
    @Value("${storage.s3.endpoint}") String endpoint,
    @Value("${storage.s3.access-key}") String ak,
    @Value("${storage.s3.secret-key}") String sk,
    @Value("${storage.s3.bucket}") String bucket,
    @Value("${storage.s3.presign-expiry-seconds}") int expiry
  ){
    this.client = MinioClient.builder().endpoint(endpoint).credentials(ak, sk).build();
    this.bucket = bucket;
    this.expiry = expiry;
  }

  public void put(String key, InputStream data, long size, String contentType) throws Exception {
    client.putObject(PutObjectArgs.builder()
      .bucket(bucket).object(key)
      .contentType(contentType)
      .stream(data, size, -1)
      .build());
  }

  public String presign(String key) throws Exception {
    return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
      .bucket(bucket).object(key)
      .method(Method.GET).expiry(expiry, TimeUnit.SECONDS).build());
  }
}
