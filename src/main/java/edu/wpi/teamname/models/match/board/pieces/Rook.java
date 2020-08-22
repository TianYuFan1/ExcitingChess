package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece {

  /**
   * Creates a new rook piece
   *
   * @param color the color of the rook
   */
  public Rook(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteRook.png",
        "edu/wpi/teamname/images/pieces/BlackRook.png");
    setupMoves();
  }

  /** Defines the move directions of the rook */
  public void setupMoves() {
    super.shortMove = new ArrayList<>();
    super.longMove =
        new ArrayList<>(
            Arrays.asList(
                new int[] {0, 1}, new int[] {0, -1}, new int[] {1, 0}, new int[] {-1, 0}));
  }
}
