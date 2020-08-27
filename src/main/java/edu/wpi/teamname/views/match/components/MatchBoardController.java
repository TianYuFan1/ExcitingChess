package edu.wpi.teamname.views.match.components;

import edu.wpi.teamname.models.match.board.PieceSet;
import edu.wpi.teamname.models.match.board.TileGrid;
import edu.wpi.teamname.models.match.board.pieces.*;
import edu.wpi.teamname.views.match.MatchController;
import edu.wpi.teamname.views.match.MatchScreenController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MatchBoardController implements MatchController {

  MatchScreenController matchScreenController;

  @FXML GridPane gp_board;
  @FXML AnchorPane pn_copy;

  public static final int imageSize = 90;
  public static final int boardOffset = 420;

  private TileGrid tiles;
  private PieceSet pieces;
  private String gameID;

  public void setGameID(String gameID) {
    this.gameID = gameID;
  }

  public void setColor(String color) {
    this.color = color;
  }

  private String color;

  public String getGameID() {
    return gameID;
  }
  @Override
  public void setMatchScreenController(MatchScreenController matchScreenController) {
    this.matchScreenController = matchScreenController;
  }

  public MatchScreenController getMatchScreenController() {
    return matchScreenController;
  }

  public void initialize() {
    createBase();
  }

  private void createBase() {
    tiles = new TileGrid(this);
    for (int col = 0; col < 8; col++) {
      for (int row = 0; row < 8; row++) {
        gp_board.add(tiles.addTile(row, col), col, row);
      }
    }
    for (int i = 0; i < 64; i++) {
      if (i % 8 == 0) {
        System.out.println("");
      }
      System.out.print(tiles.getTile(i).getAbsPos() + " ");
    }
    System.out.println("");
  }

  public void createPieces() {
    pieces = new PieceSet(this.color, this);
  }

  public AnchorPane getCopyPane() {
    return this.pn_copy;
  }

  public GridPane getBoardGrid() {
    return this.gp_board;
  }

  public TileGrid getTiles() {
    return this.tiles;
  }

  public PieceSet getPieces() {
    return this.pieces;
  }

  public String getColor() {
    return this.color;
  }
}
