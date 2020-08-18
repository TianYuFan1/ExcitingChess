package edu.wpi.teamname.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Instruction {
  private final String operation;
  private final String user;
  private final String target;
  private final String payload;

  public String getOperation() {
    return operation;
  }

  public String getUser() {
    return user;
  }

  public String getTarget() {
    return target;
  }

  public String getPayload() {
    return payload;
  }

  public Instruction(String operation, String user, String target, String payload) {
    this.operation = operation;
    this.user = user;
    this.target = target;
    this.payload = payload;
  }

  /**
   * Static Constructor for an Instruction object
   *
   * @param data InputStream (Byte Array from server or client)
   * @return Instruction
   */
  public static Instruction parseStream(InputStream data) {
    try {
      if (data.available() > 0) {
        InputStreamReader input = new InputStreamReader(data, StandardCharsets.US_ASCII);
        StringBuilder build = new StringBuilder();
        int temp;
        while ((temp = input.read()) != '\0') {
          build.append((char) temp);
        }
        String[] content = build.toString().split("\\|");
        return new Instruction(content[0], content[1], content[2], content[3]);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
