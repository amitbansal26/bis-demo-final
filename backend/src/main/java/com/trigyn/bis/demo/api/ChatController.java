package com.trigyn.bis.demo.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  public static record ChatMsg(String from, String text) {}
  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public ChatMsg broadcast(ChatMsg msg) {
    return msg;
  }
}
