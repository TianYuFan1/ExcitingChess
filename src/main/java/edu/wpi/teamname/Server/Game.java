package edu.wpi.teamname.Server;

import java.util.ArrayList;

public class Game {

  private Server server;
  private String white;
  private String black;
  private ArrayList<String> moves;

  public String getWhite() {
    return white;
  }

  public ServerThread getWhiteThread() {
    return server.findUser(white);
  }

  public String getBlack() {
    return black;
  }

  public ServerThread getBlackThread() {
    return server.findUser(black);
  }

  public ArrayList<String> getMoves() {
    return moves;
  }

  public Game(Server server, String white, String black) {
    this.server = server;
    this.white = white;
    this.black = black;
    this.moves = new ArrayList<>();
  }

  public void saveMove(String move) {
    this.moves.add(move);
  }

  public String popMove() {
    String ret = this.moves.get(this.moves.size() - 1);
    this.moves.remove(this.moves.size() - 1);
    return ret;
  }

  public String getLastMove() {
    return this.moves.get(this.moves.size() - 1);
  }
}
