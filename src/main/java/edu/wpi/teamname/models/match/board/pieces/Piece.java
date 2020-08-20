package edu.wpi.teamname.models.match.board.pieces;

import edu.wpi.teamname.models.match.board.Tile;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class Piece {
  String color;
  ImageView image;
  Tile currentTile;
  MatchBoardController mbc;
  ArrayList<int[]> shortMove;
  ArrayList<int[]> longMove;

  public Piece(String color, String whitePath, String blackPath) {
    this.color = color;

    if (color.equals("white")) {
      createImage(whitePath);
    } else {
      createImage(blackPath);
    }

    //    image.setOnMouseClicked(event -> setupOnClick());
  }

  public void createImage(String path) {
    this.image = new ImageView(path);
    this.image.setFitHeight(90);
    this.image.setPreserveRatio(true);
    this.image.setMouseTransparent(true);
  }

  public ImageView getImage() {
    return image;
  }

  public void setTile(Tile tile) {
    this.currentTile = tile;
  }

  public Tile getTile() {
    return this.currentTile;
  }

  public void setMatchBoardController(MatchBoardController mbc) {
    this.mbc = mbc;
  }

  public ArrayList<int[]> getShortMoves() {
    return this.shortMove;
  }

  public ArrayList<int[]> getLongMoves() {
    return this.longMove;
  }

  //  public void setupOnClick() {
  //    TileGrid tg = mbc.getTiles();
  //    int[] pos = currentTile.getPos();
  //
  //    for (int i = 0; i < shortMove.size(); i++) {
  //      int[] dir = shortMove.get(i);
  //      Tile tile = tg.getTile(pos[0] + dir[0], pos[1] + dir[1]);
  //      tile.getPane().getChildren().add(new Circle(10));
  //    }
  //  }
}
