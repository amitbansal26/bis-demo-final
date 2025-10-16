package com.trigyn.bis.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityLog {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String action; // APPLICATION_RECEIVED, FEE_PAID, PI_DONE, LICENSE_GRANTED, etc.
  private Instant at;
  private String details;
  @ManyToOne
  private CertificationApplication application;
}
