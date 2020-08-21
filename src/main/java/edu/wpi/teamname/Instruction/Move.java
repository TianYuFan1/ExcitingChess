package edu.wpi.teamname.Instruction;

public class Move {

  private String piece;
  private String origin;
  private String destination;
  private String modifier;

  public String getPiece() {
    return piece;
  }

  public void setPiece(String piece) {
    this.piece = piece;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getModifier() {
    return modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public Move(String piece, String origin, String destination, String modifier) {
    this.piece = piece;
    this.origin = origin;
    this.destination = destination;
    this.modifier = modifier;
  }

  public Move(String piece, String modifier) {
    this.piece = piece;
    this.origin = "";
    this.destination = "";
    this.modifier = modifier;
  }
  /**
   * Static Constructor for a Move object
   *
   * @param move String
   * @return Move
   */
  public static Move parseMove(String move) {
    if (move.contains("O-O")) {
      return new Move(move.substring(0, move.length() - 1), move.substring(move.length() - 1));
    } else {
      return new Move(
          Character.toString(move.charAt(0)),
          move.substring(1, 3),
          move.substring(3, 5),
          move.substring(5));
    }
  }
}
