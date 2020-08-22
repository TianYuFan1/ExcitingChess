package edu.wpi.teamname.models.match.board.pieces;

import edu.wpi.teamname.models.match.board.Tile;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class Piece {
  // The color of the piece
  String color;
  // The image representing the piece
  ImageView image;
  // The tile hosting the piece
  Tile currentTile;
  // The board controller hosting the piece
  MatchBoardController mbc;
  // The set of short moves
  ArrayList<int[]> shortMove;
  // The set of long moves
  ArrayList<int[]> longMove;

  /**
   * Creates a new piece
   *
   * @param color the color of the piece
   * @param whitePath the image url for a white piece
   * @param blackPath the image url for a black piece
   */
  public Piece(String color, String whitePath, String blackPath) {
    this.color = color;

    if (color.equals("white")) {
      createImage(whitePath);
    } else {
      createImage(blackPath);
    }
  }

  /**
   * Creates the image view for the piece
   *
   * @param path the image url for the piece
   */
  public void createImage(String path) {
    this.image = formatImage(path);
  }

  /**
   * Retrieves the image view for the piece
   *
   * @return the image view for the piece
   */
  public ImageView getImage() {
    return image;
  }

  /**
   * Sets the tile which hosts the piece
   *
   * @param tile the tile which hosts the piece
   */
  public void setTile(Tile tile) {
    tile.setPiece(this);
    this.currentTile = tile;
  }

  /**
   * Returns the tile which hosts the piece
   *
   * @return the tile which hosts the piece
   */
  public Tile getTile() {
    return this.currentTile;
  }

  /**
   * Returns the board controller
   *
   * @param mbc the board controller
   */
  public void setMatchBoardController(MatchBoardController mbc) {
    this.mbc = mbc;
  }

  /**
   * Returns the set of short moves
   *
   * @return the set of short moves
   */
  public ArrayList<int[]> getShortMoves() {
    return this.shortMove;
  }

  /**
   * Returns the set of long moves
   *
   * @return the set of long moves
   */
  public ArrayList<int[]> getLongMoves() {
    return this.longMove;
  }

  /**
   * Returns the color of the piece
   *
   * @return the color of the piece
   */
  public String getColor() {
    return this.color;
  }

  /**
   * Returns whether the piece is of the user
   *
   * @return whether the piece is of the user
   */
  public boolean isUserPiece() {
    return this.mbc.getColor().equals(this.color);
  }

  /**
   * Returns the image view representing the piece
   *
   * @param path the url of the image view
   * @return the image view representing the piece
   */
  public ImageView formatImage(String path) {
    ImageView imageView = new ImageView(path);
    imageView.setFitHeight(this.mbc.imageSize);
    imageView.setPreserveRatio(true);
    imageView.setMouseTransparent(true);
    return imageView;
  }
}
