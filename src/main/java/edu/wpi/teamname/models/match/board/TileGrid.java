package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.models.match.board.pieces.Piece;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TileGrid {

  final String lightColor = "#C7AD7F";
  final String darkColor = "#654321";

  MatchBoardController mbc;
  private Tile[] tiles = new Tile[64];

  public TileGrid(MatchBoardController mbc) {
    this.mbc = mbc;
  }

  public Tile getTile(int row, int col) {
    return tiles[8 * row + col];
  }

  public Tile getTile(int index) {
    return tiles[index];
  }

  public void setTile(Tile tile, int row, int col) {
    this.tiles[8 * row + col] = tile;
  }

  public Tile[] getTileGrid() {
    return tiles;
  }

  public void addPiece(Piece piece, int row, int col) {
    Tile tile = tiles[8 * row + col];
    tile.setPiece(piece);
  }

  public Pane addTile(int row, int col) {
    Pane pane = new Pane();
    pane.setPrefHeight(135);
    pane.setPrefWidth(135);

    String colName = String.valueOf((char) (col + 97));
    String rowName = Integer.toString(8 - row);

    Tile tile = new Tile(pane, new int[] {row, col}, mbc);
    this.setTile(tile, row, col);

    int pos = ((col % 2) + (row % 2)) % 2;
    if (pos == 0) {
      pane.setStyle("-fx-background-color:" + lightColor);
    } else {
      pane.setStyle("-fx-background-color:" + darkColor);
    }

    if (col == 0) {
      pane.getChildren().add(createLabel(rowName, 5, 112, pos));
    }

    if (row == 0) {
      pane.getChildren().add(createLabel(colName, 117, 5, pos));
    }

    return pane;
  }

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

  public void clearMoves() {
    for (Tile tile : tiles) {
      Pane pane = tile.getPane();
      pane.getChildren().removeIf(n -> n instanceof Circle);
    }
  }
}
