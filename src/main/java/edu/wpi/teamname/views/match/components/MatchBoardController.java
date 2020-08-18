package edu.wpi.teamname.views.match.components;

import edu.wpi.teamname.views.match.MatchController;
import edu.wpi.teamname.views.match.MatchScreenController;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MatchBoardController implements MatchController {
  MatchScreenController matchScreenController;

  @FXML GridPane gp_board;

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
    int pos = ((col % 2) + (row % 2)) % 2;
    Pane tile = new Pane();
    tile.setPrefHeight(135);
    tile.setPrefWidth(135);
    if (pos % 2 == 0) {
      tile.setStyle("-fx-background-color: white;");
    } else {
      tile.setStyle("-fx-background-color: black;");
    }

    return tile;
  }
}
