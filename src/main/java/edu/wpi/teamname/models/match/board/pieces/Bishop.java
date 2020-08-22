package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece {

  /**
   * Creates a new bishop piece
   *
   * @param color the color of the bishop
   */
  public Bishop(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteBishop.png",
        "edu/wpi/teamname/images/pieces/BlackBishop.png");
    setupMoves();
  }

  /** Defines the move directions of the bishop */
  public void setupMoves() {
    super.shortMove = new ArrayList<>();
    super.longMove =
        new ArrayList<>(
            Arrays.asList(
                new int[] {-1, 1}, new int[] {1, 1}, new int[] {1, -1}, new int[] {-1, -1}));
  }
}
