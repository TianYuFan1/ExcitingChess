package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
  public King(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteKing.png",
        "edu/wpi/teamname/images/pieces/BlackKing.png");
    super.character = "K";
    setupMoves();
  }

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
