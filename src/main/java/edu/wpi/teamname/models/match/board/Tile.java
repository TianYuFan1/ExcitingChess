package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.helper.match.board.MatchBoardHelper;
import edu.wpi.teamname.models.match.board.pieces.Pawn;
import edu.wpi.teamname.models.match.board.pieces.Piece;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tile {
  // the parent board controller
  MatchBoardController mbc;
  // the default color of tile
  String defaultColor;
  // the (row, col) position of tile
  int[] pos;
  // the current piece of tile
  Piece piece;
  // the pane representing tile
  Pane pane;

  /**
   * Instantiates a new tile object
   *
   * @param pane the pane representing the tile
   * @param pos the position of the tile in the gridPane
   * @param mbc the parent board controller
   */
  public Tile(Pane pane, int[] pos, MatchBoardController mbc, String defaultColor) {
    this.mbc = mbc;
    this.defaultColor = defaultColor;
    this.pos = pos;
    this.piece = null;
    this.pane = pane;
    resetColor();
  }

  /**
   * Returns the row and column of the tile
   *
   * @return the (row, col) of the tile
   */
  public int[] getPos() {
    return this.pos;
  }

  /**
   * Returns the index of the tile in an array
   *
   * @return the index of the tile
   */
  public int getIndex() {
    int row = this.pos[0];
    int col = this.pos[1];
    return row * 8 + col;
  }

  /**
   * Returns the absolute position of the tile
   *
   * @return the absolute position of the tile
   */
  public String getAbsPos() {
    String colName;
    String rowName;
    if (mbc.getColor().equals("white")) {
      colName = String.valueOf((char) (97 + this.pos[1]));
      rowName = Integer.toString(8 - this.pos[0]);
    } else {
      colName = String.valueOf((char) (104 - this.pos[1]));
      rowName = Integer.toString(1 + this.pos[0]);
    }
    return colName + rowName;
  }

  /**
   * Returns the piece on the tile
   *
   * @return the piece on the tile
   */
  public Piece getPiece() {
    return this.piece;
  }

  /**
   * Returns whether the tile has a piece
   *
   * @return whether the tile has a piece
   */
  public boolean hasPiece() {
    return !(this.piece == null);
  }

  /**
   * Returns the pane representing the tile
   *
   * @return the pane representing the tile
   */
  public Pane getPane() {
    return this.pane;
  }

  /** Reset tile color to default */
  public void resetColor() {
    this.pane.setStyle("-fx-background-color: " + defaultColor);
  }

  /**
   * Place a new piece on the tile
   *
   * @param piece the piece to be placed on the tile
   */
  public void setPiece(Piece piece) {
    this.piece = piece;
    if (this.hasPiece() && this.piece.isUserPiece()) {
      this.pane.setOnMouseClicked(event -> setupOnClick(event));
      this.pane.setOnMouseDragged(event -> setupOnDragged(event));
      this.pane.setOnMouseReleased(event -> setupOnDraggedReleased(event));
    }
  }

  /**
   * Handles a click event on a pane
   *
   * @param event the mouse event
   */
  public void setupOnClick(MouseEvent event) {
    TileGrid tg = mbc.getTiles();
    tg.clearMoves();
    calculateShortMoves(tg);
    calculateLongMoves(tg);
    calculateSpecialMoves(tg);
  }

  /**
   * Calculates all possible short moves of piece
   *
   * @param tg the tile grid containing all tiles on board
   */
  public void calculateShortMoves(TileGrid tg) {
    ArrayList<int[]> move = this.piece.getShortMoves();
    for (int[] dir : move) {
      int x = this.pos[0] + dir[0];
      int y = this.pos[1] + dir[1];
      if (isWithinBoard(x, y)) {
        Tile tile = tg.getTile(x, y);
        if (this.piece instanceof Pawn && tile.hasPiece()) {
          break;
        }
        if (!tile.hasPiece() || !tile.getPiece().isUserPiece()) {
          tile.getPane().getChildren().add(createMoveCircle());
        }
      }
    }
  }

  /**
   * Calculates all possible long moves of piece
   *
   * @param tg the tile grid containing all tiles on board
   */
  public void calculateLongMoves(TileGrid tg) {
    ArrayList<int[]> move = piece.getLongMoves();
    for (int[] dir : move) {
      int x = this.pos[0] + dir[0];
      int y = this.pos[1] + dir[1];
      for (int i = 1; isWithinBoard(x, y); i++) {
        Tile tile = tg.getTile(x, y);
        if (tile.hasPiece()) {
          if (!tile.piece.isUserPiece()) {
            tile.getPane().getChildren().add(createMoveCircle());
          }
          break;
        }
        tile.getPane().getChildren().add(createMoveCircle());
        x = this.pos[0] + i * dir[0];
        y = this.pos[1] + i * dir[1];
      }
    }
  }

  /**
   * Calculates special moves
   *
   * @param tg the tile grid which contains the tile
   */
  public void calculateSpecialMoves(TileGrid tg) {
    if (this.piece instanceof Pawn) {
      handlePawnSpecialMoves(tg);
    }
  }

  /**
   * Calculates special pawn moves
   *
   * @param tg the tile grid which contains the tile
   */
  public void handlePawnSpecialMoves(TileGrid tg) {
    // Move two spaces forward
    if (!((Pawn) this.piece).getHasMoved()) {
      Tile tile = tg.getTile(this.pos[0] - 2, this.pos[1]);
      tile.getPane().getChildren().add(createMoveCircle());
    }
    // Capture
    Tile left = tg.getTile(this.pos[0] - 1, this.pos[1] - 1);
    Tile right = tg.getTile(this.pos[0] - 1, this.pos[1] + 1);
    if (left.hasPiece() && !left.getPiece().isUserPiece()) {
      left.getPane().getChildren().add(createMoveCircle());
    }
    if (right.hasPiece() && !right.getPiece().isUserPiece()) {
      right.getPane().getChildren().add(createMoveCircle());
    }
  }

  public void setupOnDraggedReleased(MouseEvent event) {
    this.piece.getImage().setOpacity(1.0);
    this.mbc.getCopyPane().getChildren().clear();
    this.mbc.getTiles().resetAllPaneColor();
    movePiece(event);
    this.mbc.getTiles().clearAllCircle();
  }


  public void movePiece(MouseEvent event) {
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        GridPane grid = mbc.getBoardGrid();
        Tile newTile = mbc.getTiles().getTile(col, row);
        boolean inBound =
            grid.getCellBounds(col, row)
                .contains(
                    event.getSceneY(), event.getSceneX() - mbc.boardOffset + mbc.imageSize / 2);
        if (inBound && hasCircleInTile(newTile)) {
          // Set pawn as moved
          if (!newTile.equals(this) && this.piece instanceof Pawn) {
            ((Pawn) this.piece).setHasMoved(true);
          }
          // Capture piece
          if (newTile.hasPiece()) {
            grid.getChildren().remove(newTile.getPiece().getImage());
          }
          // Move piece
          removeTileEventListeners();
          grid.getChildren().remove(this.piece.getImage());
          grid.add(this.piece.getImage(), row, col);
          newTile.setPiece(this.piece);
          this.piece.setTile(newTile);
          this.piece = null;
        }
      }
    }
  }

  public void removeTileEventListeners() {
    this.pane.setOnMouseClicked(e -> {});
    this.pane.setOnMouseDragged(e -> {});
    this.pane.setOnMouseReleased(e -> {});
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
        boolean hasCircle = hasCircleInTile(mbc.getTiles().getTile(col, row));

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

  public boolean isWithinBoard(int x, int y) {
    return (x < 8 && x > -1 && y < 8 && y > -1);
  }

  public Circle createMoveCircle() {
    Circle circle = new Circle(50);
    circle.setFill(Color.GREEN);
    circle.setLayoutX(135 / 2);
    circle.setLayoutY(135 / 2);
    return circle;
  }

  public boolean hasCircleInTile(Tile tile) {
    Pane pane = tile.getPane();
    boolean hasCircle = false;
    for (Node node : pane.getChildren()) {
      if (node instanceof Circle) {
        hasCircle = true;
      }
    }
    return hasCircle;
  }
}
