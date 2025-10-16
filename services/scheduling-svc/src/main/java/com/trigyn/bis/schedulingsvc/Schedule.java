
package com.trigyn.bis.schedulingsvc;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Schedule {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private Long appId;
  private String inspector;
  private String region;
  private Instant at;
}
