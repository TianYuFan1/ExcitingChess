package edu.wpi.teamname.Server;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
  private final Socket socket;
  private final Server server;
  private String username;

  public ServerThread(Server server, Socket socket) {
    this.socket = socket;
    this.server = server;
  }

  public Socket getSocket() {
    return socket;
  }

  /** Thread method which waits for client input and responds to it */
  @Override
  public void run() {
    try {
      Instruction initialize = Instruction.parseStream(socket.getInputStream());
      initializeThread(initialize);
      boolean online = true;
      while (online) {
        try {
          Instruction instruction = Instruction.parseStream(socket.getInputStream());
          if (instruction != null) {
            processInstruction(instruction);
          }
        } catch (IOException e) {

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void initializeThread(Instruction instruction) {
    String username = instruction.getUser();
    this.server.addUser(username, this);
    this.username = username;
  }

  public void processInstruction(Instruction instruction) {
    String op = instruction.getOperation();
    String user = instruction.getUser();
    String target = instruction.getTarget();
    String payload = instruction.getPayload();
    try {
      switch (op) {
        case ("move"):
          ServerThread destination = this.server.findUser(target);
          this.server.saveMove(payload);
          destination.sendInstruction(instruction);
          break;
        case ("logoff"):
          this.server.removeUser(user);
          this.socket.close();
          break;
        case ("takeback"):
          this.server.processTakeBack(instruction);
          break;
        case ("viewgame"):
          this.server.retrieveGame(instruction);
          break;
        case ("savegame"):
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendInstruction(Instruction i) {
    try {
      this.getSocket()
          .getOutputStream()
          .write(
              (i.getOperation() + '|' + i.getUser() + '|' + i.getTarget() + '|' + i.getPayload())
                  .getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
