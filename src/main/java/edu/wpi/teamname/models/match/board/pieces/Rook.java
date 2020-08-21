package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece {
  public Rook(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteRook.png",
        "edu/wpi/teamname/images/pieces/BlackRook.png");
    super.character = "R";
    setupMoves();
  }

  public void setupMoves() {
    super.shortMove = new ArrayList<>();
    super.longMove =
        new ArrayList<>(
            Arrays.asList(
                new int[] {0, 1}, new int[] {0, -1}, new int[] {1, 0}, new int[] {-1, 0}));
  }
}
