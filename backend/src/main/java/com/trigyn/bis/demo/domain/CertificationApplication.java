package com.trigyn.bis.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CertificationApplication {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String scheme; // Scheme-I/IV/X/III etc.
  private String status; // DRAFT,SUBMITTED,UNDER_SCRUTINY,PI_SCHEDULED,LICENSE_GRANTED,REJECTED
  private String isNumber;
  private String product;
  @ManyToOne
  private AppUser applicant;
  private Instant createdAt;
  private Instant updatedAt;
}
