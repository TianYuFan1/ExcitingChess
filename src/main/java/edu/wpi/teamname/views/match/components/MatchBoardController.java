package edu.wpi.teamname.views.match.components;

import edu.wpi.teamname.models.match.board.PieceSet;
import edu.wpi.teamname.models.match.board.TileGrid;
import edu.wpi.teamname.models.match.board.pieces.*;
import edu.wpi.teamname.views.match.MatchController;
import edu.wpi.teamname.views.match.MatchScreenController;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MatchBoardController implements MatchController {
  MatchScreenController matchScreenController;

  @FXML GridPane gp_board;

  private TileGrid tiles;
  private PieceSet pieces;

  @Override
  public void setMatchScreenController(MatchScreenController matchScreenController) {
    this.matchScreenController = matchScreenController;
  }

  public void initialize() {
    createBase();
    createPieces();
  }

  public void createBase() {
    tiles = new TileGrid(this);
    for (int col = 0; col < 8; col++) {
      for (int row = 0; row < 8; row++) {
        gp_board.add(tiles.addTile(row, col), col, row);
      }
    }
  }

  public void createPieces() {
    pieces = new PieceSet("black", this);
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
}
