package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece {

  public Bishop(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhiteBishop.png",
        "edu/wpi/teamname/images/pieces/BlackBishop.png");
    super.character = "B";
    setupMoves();
  }

  public void setupMoves() {
    super.shortMove = new ArrayList<>();
    super.longMove =
        new ArrayList<>(
            Arrays.asList(
                new int[] {-1, 1}, new int[] {1, 1}, new int[] {1, -1}, new int[] {-1, -1}));
  }
}
