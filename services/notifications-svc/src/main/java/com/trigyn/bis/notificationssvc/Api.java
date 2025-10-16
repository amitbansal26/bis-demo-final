
package com.trigyn.bis.notificationssvc;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notify")
@RequiredArgsConstructor
public class Api {
  private final NotifyService notify;

  @PostMapping("/email")
  public String email(@RequestParam String to, @RequestParam String subject, @RequestParam String body){
    notify.sendEmail(to, subject, body);
    return "OK";
  }

  @KafkaListener(topics="payments.events", groupId="notifications")
  public void onPaymentEvent(String payload){
    notify.sendEmail("applicant@example.com", "Payment Event", payload);
  }
}
