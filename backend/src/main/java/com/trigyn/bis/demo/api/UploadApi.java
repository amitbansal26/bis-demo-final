package com.trigyn.bis.demo.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import com.trigyn.bis.demo.av.ClamAVClient;
import com.trigyn.bis.demo.storage.ObjectStorageService;

@RestController
@RequestMapping("/api/public/upload")
public class UploadApi {

  private final ClamAVClient clam;
  private final ObjectStorageService storage;
  private final Set<String> allowedMime;

  public UploadApi(
      @Value("${clamav.host}") String host,
      @Value("${clamav.port}") int port,
      @Value("${clamav.allowed-mime}") String allowedMime,
      ObjectStorageService storage
  ){
    this.clam = new ClamAVClient(host, port);
    this.storage = storage;
    this.allowedMime = new HashSet<>(Arrays.asList(allowedMime.split(",")));
  }

  record UploadResponse(String filename, String status, String presignedUrl, String details) {}

  @PostMapping(consumes={"multipart/form-data"})
  public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) throws Exception {
    if (file.getSize() > 20 * 1024 * 1024) {
      return ResponseEntity.badRequest().body("File too large");
    }
    String ctype = Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");
    if(!allowedMime.contains(ctype)){
      return ResponseEntity.badRequest().body("MIME not allowed: " + ctype);
    }
    boolean clean = clam.scan(file.getInputStream());
    if(!clean){
      return ResponseEntity.ok(new UploadResponse(file.getOriginalFilename(), "INFECTED", null, "ClamAV found threats"));
    }
    String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
    storage.put(key, file.getInputStream(), file.getSize(), ctype);
    String url = storage.presign(key);
    return ResponseEntity.ok(new UploadResponse(file.getOriginalFilename(), "CLEAN", url, "Uploaded to object storage"));
  }
}
