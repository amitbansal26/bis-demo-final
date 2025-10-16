package com.trigyn.bis.demo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.Instant;
import com.trigyn.bis.demo.repo.*;
import com.trigyn.bis.demo.domain.*;

@RestController
@RequestMapping("/api/officer")
@RequiredArgsConstructor
public class OfficerApi {
  private final CertificationApplicationRepository apps;
  private final ActivityLogRepository logs;
  private final InspectionScheduleRepository inspections;

  @GetMapping("/queue")
  @PreAuthorize("hasAnyRole('OFFICER','ADMIN')")
  public List<CertificationApplication> queue(@RequestParam(required=false) String scheme,
                                             @RequestParam(required=false) String region){
    // demo: filter by scheme only (region can be extended via applicant/org mapping)
    var all = apps.findAll();
    return all.stream().filter(a -> scheme == null || a.getScheme().equalsIgnoreCase(scheme)).toList();
  }

  record ScheduleReq(Long appId, String inspector, String region, long epochMillis) {}

  @PostMapping("/pi/schedule")
  @PreAuthorize("hasAnyRole('OFFICER','ADMIN')")
  public ResponseEntity<?> schedule(@RequestBody ScheduleReq r){
    var app = apps.findById(r.appId()).orElseThrow();
    var sc = InspectionSchedule.builder().application(app).inspector(r.inspector()).region(r.region())
        .scheduledAt(Instant.ofEpochMilli(r.epochMillis())).status("SCHEDULED").build();
    inspections.save(sc);
    logs.save(ActivityLog.builder().application(app).action("PI_SCHEDULED").at(Instant.now())
        .details("PI by " + r.inspector() + " in " + r.region()).build());
    return ResponseEntity.ok(sc);
  }
}
