package edu.wpi.teamname.helper.match.board;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MatchBoardHelper {
  static final int imgDim = 90;

  public static ImageView formatImage(String path) {
    ImageView imageView = new ImageView(path);
    imageView.setFitHeight(imgDim);
    imageView.setPreserveRatio(true);
    imageView.setMouseTransparent(true);
    return imageView;
  }

  public static boolean hasCircleInPane(Pane pain) {
    boolean hasCircle = false;
    for (Node node : pain.getChildren()) {
      if (node instanceof Circle) {
        hasCircle = true;
      }
    }
    return hasCircle;
  }
}
