package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
  public Pawn(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhitePawn.png",
        "edu/wpi/teamname/images/pieces/BlackPawn.png");
    setupMoves();
  }

  public void setupMoves() {
    super.shortMove = new ArrayList<>(Arrays.asList(new int[]{0, 1}, new int[]{0, 2}));
    super.longMove = new ArrayList<>();
  }
}
