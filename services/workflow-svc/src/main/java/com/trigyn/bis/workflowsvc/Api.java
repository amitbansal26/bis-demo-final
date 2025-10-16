
package com.trigyn.bis.workflowsvc;

import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class Api {
  private final RuntimeService runtime;
  private final TaskService tasks;

  @PostMapping("/start/{key}")
  public String start(@PathVariable String key){
    return runtime.startProcessInstanceByKey(key).getId();
  }

  @GetMapping("/tasks")
  public List<Task> list(){ return tasks.createTaskQuery().list(); }

  @PostMapping("/complete/{taskId}")
  public String complete(@PathVariable String taskId){
    tasks.complete(taskId);
    return "OK";
  }
}
