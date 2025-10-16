package com.trigyn.bis.demo.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@RestController
@RequestMapping("/api/public")
public class PaymentsApi {

  @Value("${payments.webhook.secret:changeme}")
  private String webhookSecret;

  record PayInit(String appRef, int amountInPaise, String mode) {}
  record PayResp(String orderId, String providerCheckoutUrl) {}
  record Webhook(String payload, String signature) {}
  record Ack(String status){}

  @PostMapping("/payments/init")
  public ResponseEntity<?> init(@RequestBody PayInit req){
    // Create order at gateway via SDK (placeholder here)
    String orderId = UUID.randomUUID().toString();
    String checkout = "https://sandbox-gateway.example/checkout?orderId=" + orderId;
    return ResponseEntity.ok(new PayResp(orderId, checkout));
  }

  @PostMapping("/webhook/payments")
  public ResponseEntity<?> webhook(@RequestBody Webhook wh){
    try{
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(webhookSecret.getBytes(), "HmacSHA256"));
      byte[] sig = mac.doFinal(wh.payload().getBytes());
      String hex = javax.xml.bind.DatatypeConverter.printHexBinary(sig).toLowerCase();
      if(!hex.equalsIgnoreCase(wh.signature())){
        return ResponseEntity.status(401).body(new Ack("INVALID_SIGNATURE"));
      }
      // TODO: parse payload -> update payment status in DB
      return ResponseEntity.ok(new Ack("OK"));
    }catch(Exception e){
      return ResponseEntity.internalServerError().body(new Ack("ERROR"));
    }
  }
}
