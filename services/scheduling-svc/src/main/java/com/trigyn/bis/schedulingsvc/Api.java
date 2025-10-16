
package com.trigyn.bis.schedulingsvc;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/scheduling")
@RequiredArgsConstructor
public class Api {
  private final ScheduleRepository repo;

  @PostMapping("/pi")
  public Schedule create(@RequestBody Schedule s){
    return repo.save(s);
  }

  @GetMapping("/{appId}")
  public List<Schedule> list(@PathVariable Long appId){
    return repo.findByAppId(appId);
  }
}
