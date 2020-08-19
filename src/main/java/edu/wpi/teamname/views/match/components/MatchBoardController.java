package edu.wpi.teamname.views.match.components;

import edu.wpi.teamname.models.match.board.pieces.Bishop;
import edu.wpi.teamname.views.match.MatchController;
import edu.wpi.teamname.views.match.MatchScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MatchBoardController implements MatchController {
  MatchScreenController matchScreenController;

  @FXML GridPane gp_board;

  final String lightColor = "#C7AD7F";
  final String darkColor = "#654321";

  @Override
  public void setMatchScreenController(MatchScreenController matchScreenController) {
    this.matchScreenController = matchScreenController;
  }

  public void initialize() {
    createBase();
    createPieces();
  }

  public void createBase() {
    for (int col = 0; col < 8; col++) {
      for (int row = 0; row < 8; row++) {
        gp_board.add(createBoardTile(row, col), col, row);
      }
    }
  }

  public Pane createBoardTile(int row, int col) {
    // Create tile
    Pane tile = new Pane();
    // Define tile size
    tile.setPrefHeight(135);
    tile.setPrefWidth(135);
    // Determine tile color
    int pos = ((col % 2) + (row % 2)) % 2;
    if (pos == 0) {
      tile.setStyle("-fx-background-color:" + lightColor);
    } else {
      tile.setStyle("-fx-background-color:" + darkColor);
    }
    // Create row numbers
    if (col == 0) {
      tile.getChildren().add(createLabel(Integer.toString(row + 1), 5, 112, pos));
    }
    if (row == 0) {
      tile.getChildren().add(createLabel(String.valueOf((char) (col + 65)), 117, 5, pos));
    }
    return tile;
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

  public void createPieces() {
    gp_board.add(new Bishop("white").getImage(), 0, 0);
  }
}
