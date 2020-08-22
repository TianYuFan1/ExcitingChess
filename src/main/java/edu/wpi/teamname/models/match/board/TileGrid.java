package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.models.match.board.pieces.Piece;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TileGrid {
  // The light tile color
  final String lightColor = "#C7AD7F";
  // The dark tile color
  final String darkColor = "#654321";
  // The board controller
  MatchBoardController mbc;
  // All tiles on board
  private Tile[] tiles = new Tile[64];

  /**
   * Initializes a set of tiles representing the board
   * @param mbc the board controller
   */
  public TileGrid(MatchBoardController mbc) {
    this.mbc = mbc;
  }

  /**
   * Returns the tile at (row, col)
   * @param row the row
   * @param col the column
   * @return the tile at (row, col)
   */
  public Tile getTile(int row, int col) {
    return tiles[8 * row + col];
  }

  /**
   * Returns the tile at an array index
   * @param index the array index of the tile
   * @return the tile
   */
  public Tile getTile(int index) {
    return tiles[index];
  }

  /**
   * Sets a tile at a (row, col)
   * @param tile the tile
   * @param row the row
   * @param col the column
   */
  public void setTile(Tile tile, int row, int col) {
    this.tiles[8 * row + col] = tile;
  }

  /**
   * Returns the tile grid
   * @return the tile grid
   */
  public Tile[] getTileGrid() {
    return tiles;
  }

  /**
   * Add the piece to the tile at (row, col)
   * @param piece the piece to be added
   * @param row the row
   * @param col the col
   */
  public void addPiece(Piece piece, int row, int col) {
    Tile tile = tiles[8 * row + col];
    tile.setPiece(piece);
  }

  /**
   * Adds a new tile at (row, col)
   * @param row the row
   * @param col the column
   * @return the pane of the tile
   */
  public Pane addTile(int row, int col) {
    Pane pane = new Pane();
    pane.setPrefHeight(135);
    pane.setPrefWidth(135);

    String colName;
    String rowName;

    if (mbc.getColor().equals("white")) {
      colName = String.valueOf((char) (97 + col));
      rowName = Integer.toString(8 - row);
    } else {
      colName = String.valueOf((char) (104 - col));
      rowName = Integer.toString(1 + row);
    }
    Tile tile;

    int pos = ((col % 2) + (row % 2)) % 2;
    if (pos == 0) {
      tile = new Tile(pane, new int[] {row, col}, mbc, lightColor);
    } else {
      tile = new Tile(pane, new int[] {row, col}, mbc, darkColor);
    }

    if (col == 0) {
      pane.getChildren().add(createLabel(rowName, 5, 112, pos));
    }

    if (row == 0) {
      pane.getChildren().add(createLabel(colName, 117, 5, pos));
    }
    this.setTile(tile, row, col);
    return pane;
  }

  /**
   * @param text the text at the label
   * @param x the row
   * @param y the column
   * @param pos the tile position
   * @return the label for the tile
   */
  public Label createLabel(String text, int x, int y, int pos) {
    Label label = new Label(text);
    label.setStyle("-fx-font-size: 14;");
    label.setLayoutX(x);
    label.setLayoutY(y);
    if (pos == 0) {
      label.setTextFill(Color.web(darkColor));
    } else {
      label.setTextFill(Color.web(lightColor));
    }
    return label;
  }

  /** Reset the color of all tiles on the board */
  public void resetAllPaneColor() {
    for (Tile tile : tiles) {
      tile.resetColor();
    }
  }

  /** Clear the board from all circles */
  public void clearAllCircle() {
    for (Tile tile : tiles) {
      Pane pane = tile.getPane();
      pane.getChildren().removeIf(n -> n instanceof Circle);
    }
  }
}
