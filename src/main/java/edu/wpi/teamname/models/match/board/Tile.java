package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.helper.match.board.MatchBoardHelper;
import edu.wpi.teamname.models.match.board.pieces.Pawn;
import edu.wpi.teamname.models.match.board.pieces.Piece;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tile {

  MatchBoardController mbc;
  int[] pos;
  Piece piece = null;
  Pane pane;
  String defaultColor;

  public Tile(Pane pane, int[] pos, MatchBoardController mbc) {
    this.pos = pos;
    this.pane = pane;
    this.mbc = mbc;
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
    if (this.piece != null && this.mbc.getColor().equals(this.piece.getColor())) {
      this.pane.setOnMouseClicked(event -> setupOnClick(event));
      this.pane.setOnMouseDragged(event -> setupOnDragged(event));
      this.pane.setOnMouseReleased(event -> setupOnDraggedReleased(event));
    }
  }

  public Piece getPiece() {
    return this.piece;
  }

  public Pane getPane() {
    return this.pane;
  }

  public String getAbsPos() {
    // TODO Create different absolute position for each player
    String colName = String.valueOf((char) (this.pos[0] + 97));
    String rowName = Integer.toString(8 - this.pos[1]);
    return colName + rowName;
  }

  public int[] getPos() {
    return this.pos;
  }

  public void setupOnDraggedReleased(MouseEvent event) {
    this.piece.getImage().setOpacity(1.0);
    this.mbc.getCopyPane().getChildren().clear();
    this.mbc.getTiles().resetAllPaneColor();
    movePiece(event);
    this.mbc.getTiles().clearAllCircle();
  }

  public void movePiece(MouseEvent event) {
    for (int col = 0; col < 8; col++) {
      for (int row = 0; row < 8; row++) {
        Pane pane = (Pane) mbc.getBoardGrid().getChildren().get(8 * row + col);
        boolean hasCircle = MatchBoardHelper.hasCircleInPane(pane);
        if (mbc.getBoardGrid()
                .getCellBounds(col, row)
                .contains(event.getSceneY(), event.getSceneX() - 420 + 90 / 2)
            && hasCircle) {
          Tile newTile = mbc.getTiles().getTile(col, row);
          // Take piece
          if (newTile.getPiece() != null) {
            mbc.getBoardGrid().getChildren().remove(newTile.getPiece().getImage());
          }
          this.piece.setTile(newTile);
          this.pane.setOnMouseClicked(e -> {});
          this.pane.setOnMouseDragged(e -> {});
          this.pane.setOnMouseReleased(e -> {});
          newTile.setPiece(this.piece);
          mbc.getBoardGrid().getChildren().remove(this.piece.getImage());
          mbc.getBoardGrid().add(newTile.getPiece().getImage(), row, col);
          this.piece = null;
        }
      }
    }
  }

  public void movePiece(String move) {}

  public void setupOnDragged(MouseEvent event) {
    setupOnClick(event);
    this.piece.getImage().setOpacity(0.5);

    ImageView copy = MatchBoardHelper.formatImage(piece.getImage().getImage().getUrl());

    mbc.getTiles().resetAllPaneColor();
    copy.setLayoutX(event.getSceneX() - 420 - 90 / 2);
    copy.setLayoutY(event.getSceneY() - 90 / 2);

    for (int col = 0; col < 8; col++) {
      for (int row = 0; row < 8; row++) {
        Pane pane = (Pane) mbc.getBoardGrid().getChildren().get(8 * row + col);
        boolean hasCircle = MatchBoardHelper.hasCircleInPane(pane);
        if (mbc.getBoardGrid()
                .getCellBounds(col, row)
                .contains(event.getSceneY(), event.getSceneX() - 420 + 90 / 2)
            && hasCircle) {
          pane.setStyle("-fx-background-color: GREEN");
        }
      }
    }

    this.mbc.getCopyPane().getChildren().clear();
    this.mbc.getCopyPane().getChildren().add(copy);
  }

  public void setupOnClick(MouseEvent event) {
    TileGrid tg = mbc.getTiles();
    tg.clearMoves();
    ArrayList<int[]> shortMoves = piece.getShortMoves();
    ArrayList<int[]> longMoves = piece.getLongMoves();

    for (int i = 0; i < shortMoves.size(); i++) {
      int[] dir = shortMoves.get(i);
      if (piece instanceof Pawn && dir[0] == -2 && this.pos[0] != 6) {
        continue;
      }
      int x = this.pos[0] + dir[0];
      int y = this.pos[1] + dir[1];
      if (x > -1 && x < 8 && y > -1 && y < 8) {
        Tile tile = tg.getTile(x, y);
        if (tile.getPiece() == null || !tile.getPiece().getColor().equals(mbc.getColor())) {
          tile.getPane().getChildren().add(createMoveCircle());
        }
      }
    }

    for (int i = 0; i < longMoves.size(); i++) {
      int[] dir = longMoves.get(i);
      int x = this.pos[0] + dir[0];
      int y = this.pos[1] + dir[1];

      for (int j = 1; x > -1 && x < 8 && y > -1 && y < 8; j++) {
        Tile tile = tg.getTile(x, y);

        if (tile.getPiece() != null) {
          if (!tile.getPiece().getColor().equals(mbc.getColor())) {
            tile.getPane().getChildren().add(createMoveCircle());
          }
          break;
        }

        tile.getPane().getChildren().add(createMoveCircle());
        x = this.pos[0] + j * dir[0];
        y = this.pos[1] + j * dir[1];
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

  public void setDefaultColor(String defaultColor) {
    this.defaultColor = defaultColor;
    this.pane.setStyle("-fx-background-color: " + defaultColor);
  }

  public void resetColor() {
    this.pane.setStyle("-fx-background-color: " + defaultColor);
  }
}
