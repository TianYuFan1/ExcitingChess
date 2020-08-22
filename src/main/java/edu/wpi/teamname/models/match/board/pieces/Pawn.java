package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
  // Records whether the pawn has moved
  boolean hasMoved;

  /**
   * Creates a new pawn piece
   *
   * @param color the color of the pawn
   */
  public Pawn(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhitePawn.png",
        "edu/wpi/teamname/images/pieces/BlackPawn.png");
    this.hasMoved = false;
    setupMoves();
  }

  /** Defines the move directions of the pawn */
  public void setupMoves() {
    super.shortMove = new ArrayList<>(Arrays.asList(new int[] {-1, 0}));
    super.longMove = new ArrayList<>();
  }

  /**
   * Sets whether the piece has moved
   *
   * @param hasMoved whether the piece has moved
   */
  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = true;
  }

  /**
   * Determines whether the piece moved
   *
   * @return whether the piece moved
   */
  public boolean getHasMoved() {
    return this.hasMoved;
  }
}
