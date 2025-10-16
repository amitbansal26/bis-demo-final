package com.trigyn.bis.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String mobile;
  @Column(unique = true)
  private String email;
  private String name;
  private String passwordHash;
  private String role; // APPLICANT, OFFICER, AUDITOR, ADMIN
}
