package edu.wpi.teamname.models.match.board.pieces;

import javafx.scene.image.ImageView;

public class Bishop extends Piece {
  private ImageView image;

  public Bishop(String color) {
    super(color);
    createImage();
  }

  private void createImage() {
    if (super.color.equals("white")) {
      this.image = new ImageView("images/pieces/BlackBishop.png");
    } else {
      this.image = new ImageView("images/pieces/WhiteBishop.png");
    }
  }

  public ImageView getImage() {
    return image;
  }
}
