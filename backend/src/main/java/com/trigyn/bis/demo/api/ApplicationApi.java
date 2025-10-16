package com.trigyn.bis.demo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import com.trigyn.bis.demo.repo.*;
import com.trigyn.bis.demo.domain.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationApi {
  private final CertificationApplicationRepository apps;
  private final AppUserRepository users;
  private final ActivityLogRepository logs;

  private record CreateApp(String scheme, String isNumber, String product, Long userId) {}
  private record Transition(String status, Long appId, String details){}

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody CreateApp r){
    AppUser u = users.findById(r.userId()).orElseThrow();
    var app = CertificationApplication.builder()
      .scheme(r.scheme()).isNumber(r.isNumber()).product(r.product())
      .status("SUBMITTED").applicant(u).createdAt(Instant.now()).updatedAt(Instant.now()).build();
    apps.save(app);
    logs.save(ActivityLog.builder().action("APPLICATION_RECEIVED").at(Instant.now()).details("App submitted").application(app).build());
    return ResponseEntity.ok(app);
  }

  @PostMapping("/transition")
  public ResponseEntity<?> transition(@RequestBody Transition t){
    var app = apps.findById(t.appId()).orElseThrow();
    app.setStatus(t.status()); app.setUpdatedAt(Instant.now());
    apps.save(app);
    logs.save(ActivityLog.builder().action(t.status()).at(Instant.now()).details(t.details()).application(app).build());
    return ResponseEntity.ok(app);
  }

  @GetMapping("/{userId}")
  public List<CertificationApplication> list(@PathVariable Long userId){
    AppUser u = users.findById(userId).orElseThrow();
    return apps.findByApplicant(u);
  }
}
