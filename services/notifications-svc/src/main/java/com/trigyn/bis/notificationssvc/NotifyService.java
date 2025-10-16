
package com.trigyn.bis.notificationssvc;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class NotifyService {
  @Value("${smtp.host:mailhog}")
  private String host;
  @Value("${smtp.port:1025}")
  private int port;
  @Value("${smtp.from:no-reply@bis.local}")
  private String from;

  public void sendEmail(String to, String subject, String body){
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", String.valueOf(port));
    Session session = Session.getInstance(props, null);
    try{
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(from));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
      msg.setSubject(subject);
      msg.setText(body);
      Transport.send(msg);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void sendSms(String msisdn, String text){
    System.out.println("SMS -> " + msisdn + ": " + text); // mock
  }

  public void sendPush(String deviceToken, String title, String body){
    System.out.println("PUSH -> " + deviceToken + ": " + title + " | " + body); // stub
  }
}
