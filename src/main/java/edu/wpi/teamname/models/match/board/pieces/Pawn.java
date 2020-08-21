package edu.wpi.teamname.models.match.board.pieces;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
  boolean hasMoved;

  public Pawn(String color) {
    super(
        color,
        "edu/wpi/teamname/images/pieces/WhitePawn.png",
        "edu/wpi/teamname/images/pieces/BlackPawn.png");
    this.hasMoved = false;
    setupMoves();
  }

  public void setupMoves() {
    super.shortMove = new ArrayList<>(Arrays.asList(new int[] {-1, 0}));
    super.longMove = new ArrayList<>();
  }

  public void setHasMoved(boolean hasMoved) {
    this.hasMoved = true;
  }

  public boolean getHasMoved() {
    return this.hasMoved;
  }
}
