package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Queen extends Piece {
  public Queen(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteQueen.png",
        "edu/wpi/teamname/images/pieces/BlackQueen.png");
    super.character = "Q";
    setupMoves();
  }

  public void setupMoves() {
    super.shortMove = new ArrayList<>();
    super.longMove =
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
  }
}
