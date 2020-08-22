package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece {

  /**
   * Creates a new knight piece
   *
   * @param color the color of the knight
   */
  public Knight(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteKnight.png",
        "edu/wpi/teamname/images/pieces/BlackKnight.png");
    setupMoves();
  }

  /** Defines the move directions of the knight */
  public void setupMoves() {
    super.shortMove =
        new ArrayList<>(
            Arrays.asList(
                new int[] {1, 2},
                new int[] {2, 1},
                new int[] {-1, 2},
                new int[] {2, -1},
                new int[] {1, -2},
                new int[] {-2, 1},
                new int[] {-1, -2},
                new int[] {-2, -1}));
    super.longMove = new ArrayList<>();
  }
}
