package com.trigyn.bis.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentRef {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String type;
  private String url; // for demo we store a mock URL
  @ManyToOne
  private CertificationApplication application;
}
