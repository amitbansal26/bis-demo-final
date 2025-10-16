
package com.trigyn.bis.documentssvc;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Document {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private Long appId;
  private String name;
  private String type;
  @Column(length=2048)
  private String url;
}
