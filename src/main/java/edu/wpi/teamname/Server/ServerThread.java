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

  /**
   * Special initialize instruction which ties the username to the server thread
   *
   * @param instruction Instruction to be processed
   */
  public void initializeThread(Instruction instruction) {
    String username = instruction.getUser();
    this.server.addUser(username, this);
    this.username = username;
  }

  /**
   * This method processes a given Instruction and applies the correct operation
   *
   * @param instruction Instruction to be processed
   */
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
          this.server.saveGame(instruction);
          break;
        case ("initialize"):
          initializeThread(instruction);
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends an Instruction to the Client associated with the thread this is invoked on
   *
   * @param i Instruction to be sent
   */
  public void sendInstruction(Instruction i) {
    try {
      this.getSocket()
          .getOutputStream()
          .write(
              (i.getOperation() + '|' + i.getUser() + '|' + i.getTarget() + '|' + i.getPayload() + '\0')
                  .getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
