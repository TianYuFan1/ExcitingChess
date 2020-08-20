package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.models.match.board.pieces.Piece;
import javafx.scene.layout.Pane;

public class Tile {
  String pos;
  Piece piece = null;
  Pane pane;

  public Tile(Pane pane, String pos) {
    this.pos = pos;
    this.pane = pane;
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
  }

  public Piece getPiece() {
    return this.piece;
  }

  public Pane getPane() {
    return this.pane;
  }

  @Override
  public String toString() {
    return this.pos;
  }
}
