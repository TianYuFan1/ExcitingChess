package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.models.match.board.pieces.Piece;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tile {
  MatchBoardController mbc;
  int[] pos;
  Piece piece = null;
  Pane pane;

  public Tile(Pane pane, int[] pos, MatchBoardController mbc) {
    this.pos = pos;
    this.pane = pane;
    this.mbc = mbc;
    this.pane.setOnMouseClicked(event -> setupOnClick());
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

  public String getAbsPos() {
    String colName = String.valueOf((char) (this.pos[0] + 97));
    String rowName = Integer.toString(8 - this.pos[1]);
    return colName + rowName;
  }

  public int[] getPos() {
    return this.pos;
  }

  public void setupOnClick() {
    TileGrid tg = mbc.getTiles();
    tg.clearMoves();
    ArrayList<int[]> shortMoves = piece.getShortMoves();
    ArrayList<int[]> longMoves = piece.getLongMoves();

    for (int i = 0; i < shortMoves.size(); i++) {
      int[] dir = shortMoves.get(i);
      int x = this.pos[0] + dir[0];
      int y = this.pos[1] + dir[1];
      if (x > -1 && x < 8 && y > -1 && y < 8) {
        Tile tile = tg.getTile(this.pos[0] + dir[0], this.pos[1] + dir[1]);
        tile.getPane().getChildren().add(createMoveCircle());
      }
    }
  }

  public Circle createMoveCircle() {
    Circle circle = new Circle(50);
    circle.setFill(Color.GREEN);
    circle.setLayoutX(135 / 2);
    circle.setLayoutY(135 / 2);
    return circle;
  }
}
