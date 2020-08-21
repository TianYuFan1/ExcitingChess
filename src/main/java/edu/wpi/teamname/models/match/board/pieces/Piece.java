package edu.wpi.teamname.models.match.board.pieces;

import edu.wpi.teamname.helper.match.board.MatchBoardHelper;
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
  String character;

  public Piece(String color, String whitePath, String blackPath) {
    this.color = color;

    if (color.equals("white")) {
      createImage(whitePath);
    } else {
      createImage(blackPath);
    }
  }

  public void createImage(String path) {
    this.image = MatchBoardHelper.formatImage(path);
  }

  public ImageView getImage() {
    return image;
  }

  public void setTile(Tile tile) {
    tile.setPiece(this);
    this.currentTile = tile;
  }

  public String getCharacter() {
    return this.character;
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

  public String getColor() {
    return color;
  }

  public boolean isUserPiece() {
    return this.mbc.getColor().equals(this.color);
  }
}
