package com.trigyn.bis.demo.av;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClamAVClient {
  private final String host;
  private final int port;

  public ClamAVClient(String host, int port){
    this.host = host; this.port = port;
  }

  public boolean scan(InputStream in) throws Exception {
    try (Socket socket = new Socket(host, port)) {
      socket.setSoTimeout(10000);
      OutputStream out = socket.getOutputStream();
      out.write("zINSTREAM\0".getBytes(StandardCharsets.US_ASCII));
      byte[] buffer = new byte[2048];
      int read;
      while ((read = in.read(buffer)) != -1) {
        byte[] size = new byte[] {
          (byte)((read >> 24) & 0xff),
          (byte)((read >> 16) & 0xff),
          (byte)((read >> 8) & 0xff),
          (byte)(read & 0xff)
        };
        out.write(size);
        out.write(buffer, 0, read);
      }
      out.write(new byte[]{0,0,0,0});
      out.flush();
      InputStream resp = socket.getInputStream();
      StringBuilder sb = new StringBuilder();
      int c;
      while((c = resp.read()) != -1) sb.append((char)c);
      String s = sb.toString();
      return s.contains("OK");
    }
  }
}
