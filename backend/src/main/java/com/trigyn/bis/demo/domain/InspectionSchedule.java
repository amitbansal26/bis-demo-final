package com.trigyn.bis.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InspectionSchedule {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private CertificationApplication application;
  private String inspector; // officer username or id
  private String region;    // region name
  private Instant scheduledAt;
  private String status;    // SCHEDULED, DONE, CANCELLED
}
