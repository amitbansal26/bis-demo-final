
package com.trigyn.bis.documentssvc;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class Api {
  private final DocumentRepository repo;

  @PostMapping("/save")
  public Document save(@RequestBody Document d){
    return repo.save(d);
  }

  @GetMapping("/{appId}")
  public List<Document> list(@PathVariable Long appId){
    return repo.findByAppId(appId);
  }
}
