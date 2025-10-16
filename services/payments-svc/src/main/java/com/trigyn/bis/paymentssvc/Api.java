
package com.trigyn.bis.paymentssvc;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class Api {
  private final KafkaTemplate<String,String> kafka;

  record InitReq(@NotBlank String appRef, @Min(1) long amountInPaise, @NotBlank String mode) {}
  record Ack(String orderId, String status) {}

  @PostMapping("/init")
  public Ack init(@RequestBody InitReq r){
    String orderId = java.util.UUID.randomUUID().toString();
    kafka.send("payments.events", "{\"type\":\"ORDER_CREATED\",\"orderId\":\""+orderId+"\"}");
    return new Ack(orderId, "CREATED");
  }
}
