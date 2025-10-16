package com.trigyn.bis.demo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import com.trigyn.bis.demo.repo.*;
import com.trigyn.bis.demo.domain.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicApi {
  private final AppUserRepository users;
  private record OtpRequest(String mobile, String email) {}
  private record RegisterRequest(@NotBlank String name, @NotBlank String mobile, @Email String email, @NotBlank String password) {}
  private record ApplicationRequest(@NotBlank String scheme, @NotBlank String isNumber, @NotBlank String product, @NotNull Long userId){}

  @PostMapping("/otp/send")
  public ResponseEntity<?> sendOtp(@RequestBody OtpRequest req){
    return ResponseEntity.ok().body(java.util.Map.of("status","OTP_SENT","otp","123456"));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest req){
    if(users.findByMobile(req.mobile()).isPresent()) return ResponseEntity.badRequest().body("Duplicate mobile");
    if(users.findByEmail(req.email()).isPresent()) return ResponseEntity.badRequest().body("Duplicate email");
    AppUser u = AppUser.builder().name(req.name()).mobile(req.mobile()).email(req.email()).passwordHash("demo").role("APPLICANT").build();
    users.save(u);
    return ResponseEntity.ok(u);
  }
}
