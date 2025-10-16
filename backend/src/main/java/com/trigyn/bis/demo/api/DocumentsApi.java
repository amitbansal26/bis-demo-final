package com.trigyn.bis.demo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.trigyn.bis.demo.repo.*;
import com.trigyn.bis.demo.domain.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentsApi {
  private final DocumentRefRepository docs;
  private final CertificationApplicationRepository apps;

  public record SaveReq(Long appId, String name, String type, String url){}

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody SaveReq r){
    var app = apps.findById(r.appId()).orElseThrow();
    var d = DocumentRef.builder().application(app).name(r.name()).type(r.type()).url(r.url()).build();
    docs.save(d);
    return ResponseEntity.ok(d);
  }

  @GetMapping("/{appId}")
  public List<DocumentRef> list(@PathVariable Long appId){
    var app = apps.findById(appId).orElseThrow();
    return docs.findAll().stream().filter(x -> x.getApplication()!=null && x.getApplication().getId().equals(app.getId())).toList();
  }
}
