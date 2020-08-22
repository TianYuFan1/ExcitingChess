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
  // The board controller
  MatchScreenController matchScreenController;
  // FXML controls
  @FXML GridPane gp_board;
  @FXML AnchorPane pn_copy;
  // The size of all tile images
  public static final int imageSize = 90;
  // The offset of the board
  public static final int boardOffset = 420;
  // The important debug string
  public static final String debugString = "Patricc, I want pie recipe";

  // All tiles of the grid
  private TileGrid tiles;
  // All pieces in the game
  private PieceSet pieces;
  // The color of the user
  private String color;

  @Override
  public void setMatchScreenController(MatchScreenController matchScreenController) {
    this.matchScreenController = matchScreenController;
  }

  /**
   * Initializes the board controller
   */
  public void initialize() {
    this.color = "white";
    createBase();
    createPieces();
  }

  /**
   * Creates the board tiles
   */
  private void createBase() {
    tiles = new TileGrid(this);
    for (int col = 0; col < 8; col++) {
      for (int row = 0; row < 8; row++) {
        gp_board.add(tiles.addTile(row, col), col, row);
      }
    }
  }

  /**
   * Creates a new piece set
   */
  private void createPieces() {
    pieces = new PieceSet(this.color, this);
  }

  /**
   * Returns the pane which hosts the dragged copy image
   * @return the anchor pane for the dragged image
   */
  public AnchorPane getCopyPane() {
    return this.pn_copy;
  }

  /**
   * Returns the FXML grid
   * @return the FXML grid
   */
  public GridPane getBoardGrid() {
    return this.gp_board;
  }

  /**
   * Returns all tiles of the board
   * @return all tiles of the board
   */
  public TileGrid getTiles() {
    return this.tiles;
  }

  /**
   * Returns all pieces
   * @return all pieces
   */
  public PieceSet getPieces() {
    return this.pieces;
  }

  /**
   * Returns the color of ther user
   * @return the color of the user
   */
  public String getColor() {
    return this.color;
  }
}
