package edu.wpi.teamname.models.match.board.pieces;

import edu.wpi.teamname.models.match.board.MoveDir;
import edu.wpi.teamname.models.match.board.Tile;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import javafx.scene.image.ImageView;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

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
  }

  public void createImage(String path) {
    this.image = new ImageView(path);
    this.image.setFitHeight(90);
    this.image.setPreserveRatio(true);
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

}
