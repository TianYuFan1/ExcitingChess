package edu.wpi.teamname.views.match.components;

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
      tile.getChildren().add(createRowLabel(row, pos));
    }
    if (row == 0) {
      tile.getChildren().add(createColumnLabel(col, pos));
    }
    return tile;
  }

  public Label createRowLabel(int row, int pos) {
    Label label = new Label(Integer.toString(row));
    label.setStyle("-fx-font-size: 14;");
    label.setLayoutX(5);
    label.setLayoutY(112);
    if (pos == 0) {
      label.setTextFill(Color.web(darkColor));
    } else {
      label.setTextFill(Color.web(lightColor));
    }
    return label;
  }

  public Label createColumnLabel(int col, int pos) {
    Label label = new Label(String.valueOf((char) (col + 65)));
    label.setStyle("-fx-font-size: 14;");
    label.setLayoutX(117);
    label.setLayoutY(5);
    if (pos == 0) {
      label.setTextFill(Color.web(darkColor));
    } else {
      label.setTextFill(Color.web(lightColor));
    }
    return label;
  }
}
