package edu.wpi.teamname.Client;

import edu.wpi.teamname.Instruction.Instruction;
import edu.wpi.teamname.views.match.MatchScreenController;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

  private String username;
  private Socket socket;
  private InputStream readIn;
  private OutputStream sendOut;
  private MatchScreenController matchScreenController;

  public Client(String user, MatchScreenController msc) {
    this.username = user;
    this.matchScreenController = msc;
  }

  /**
   * The initial connection between the server and client is established here
   *
   * @param user The username of the person connecting
   */
  public void connect(String user) {
    try {
      this.socket = new Socket(InetAddress.getLocalHost(), 4999);
      this.username = user;
      this.readIn = socket.getInputStream();
      this.sendOut = socket.getOutputStream();
      Instruction initialize = new Instruction("initialize", username, "", "", "");
      sendInstruction(initialize);
      receiveMsg();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** This method awaits Instruction from the server */
  public void receiveMsg() {
    Thread awaitInput =
        new Thread(
            () -> {
              try {
                while (true) {
                  if (this.readIn.available() > 0) {
                    Instruction instruction = Instruction.parseStream(this.readIn);
                    processInstruction(instruction);
                  }
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
    awaitInput.start();
  }

  /**
   * This Takes a received instruction and applies the operation requested
   *
   * @param instruction The instruction to be acted upon
   */
  public void processInstruction(Instruction instruction) {
    String op = instruction.getOperation();
    String user = instruction.getUser();
    String target = instruction.getTarget();
    String payload = instruction.getPayload();
    switch (op) {
      case ("startgame"):
        startGame(instruction);
        break;
      case ("move"):
        slideMoveOverKnowWhatImsayin(instruction);
        break;
      case ("takeback"):
        // TODO Apply Redo method
        break;
      case ("loadgame"):
        parseMoves(payload);
        break;
      case ("undo"):
        undoMove(payload);
        break;
    }
  }

  /**
   * Reaches into controller to reverse a given move
   *
   * @param payload String
   */
  public void undoMove(String payload) {}

  /**
   * Turns a String of form move*move*move*move*...move* into an ArrayList of form move, move,
   * move... move
   *
   * @param moves String
   * @return ArrayList
   */
  public ArrayList<String> parseMoves(String moves) {
    ArrayList<String> moveList = new ArrayList<>();
    StringBuilder sr = new StringBuilder();
    for (int i = 0; i < moves.length(); i++) {
      char currChar = moves.charAt(i);
      if (currChar == '*') {
        moveList.add(sr.toString());
        sr = new StringBuilder();
      } else sr.append(currChar);
    }
    return moveList;
  }

  /**
   * This sends a given instruction to the server
   *
   * @param i Instruction to be sent
   */
  public void sendInstruction(Instruction i) {
    try {
      this.sendOut.write(
          (i.getOperation()
                  + '|'
                  + i.getUser()
                  + '|'
                  + i.getTarget()
                  + '|'
                  + i.getPayload()
                  + "|"
                  + i.getGame()
                  + "|"
                  + " "
                  + '\0')
              .getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends moves to controller
   *
   * @param instruction Instruction
   */
  public void slideMoveOverKnowWhatImsayin(Instruction instruction) {
    matchScreenController.getMatchBoardController().getTiles().movePiece(instruction.getPayload());
  }

  public void startGame(Instruction instruction) {
    this.matchScreenController.getMatchBoardController().setColor(instruction.getPayload());
    this.matchScreenController.getMatchBoardController().createBase();
    this.matchScreenController.getMatchBoardController().createPieces();
    this.matchScreenController.getMatchBoardController().setGameID(instruction.getGame());
  }

  public void requestGame(String username) {}
}
