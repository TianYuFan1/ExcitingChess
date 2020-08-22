package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {

  /**
   * Creates a new king piece
   *
   * @param color the color of the king
   */
  public King(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteKing.png",
        "edu/wpi/teamname/images/pieces/BlackKing.png");
    setupMoves();
  }

  /** Defines the move directions of the king */
  public void setupMoves() {
    super.shortMove =
        new ArrayList<>(
            Arrays.asList(
                new int[] {-1, 1},
                new int[] {1, 1},
                new int[] {1, -1},
                new int[] {-1, -1},
                new int[] {-1, 0},
                new int[] {1, 0},
                new int[] {0, 1},
                new int[] {0, -1}));
    super.longMove = new ArrayList<>();
  }
}
