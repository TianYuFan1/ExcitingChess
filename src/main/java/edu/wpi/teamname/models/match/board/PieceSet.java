package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.models.match.board.pieces.*;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import javafx.scene.layout.GridPane;

public class PieceSet {
  // The board controller
  MatchBoardController mbc;

  /**
   * Instantiates a set of chess pieces
   *
   * @param color the color of the user
   * @param mbc the board controller
   */
  public PieceSet(String color, MatchBoardController mbc) {
    this.mbc = mbc;
    createRows(color);
  }

  /**
   * Populates the board
   *
   * @param color the color of the user
   */
  public void createRows(String color) {
    String opponent = color.equals("white") ? "black" : "white";

    createPawnRow(color, 6);
    createPieceRow(color, 7);
    createPawnRow(opponent, 1);
    createPieceRow(opponent, 0);
  }

  /**
   * Fills a row with pawns
   *
   * @param color the color of the user
   * @param row the row hosting the pawns
   */
  public void createPawnRow(String color, int row) {
    for (int col = 0; col < 8; col++) {
      addPiece(new Pawn(color), row, col);
    }
  }

  /**
   * Fills a row with pieces
   *
   * @param color the color of the user
   * @param row the row hosting the pieces
   */
  public void createPieceRow(String color, int row) {
    addPiece(new Rook(color), row, 0);
    addPiece(new Knight(color), row, 1);
    addPiece(new Bishop(color), row, 2);
    addPiece(new Queen(color), row, 3);
    addPiece(new King(color), row, 4);
    addPiece(new Bishop(color), row, 5);
    addPiece(new Knight(color), row, 6);
    addPiece(new Rook(color), row, 7);
  }

  /**
   * Adds a piece on the board
   *
   * @param piece the new piece on the board
   * @param row the row of the piece
   * @param col the column of the piece
   */
  public void addPiece(Piece piece, int row, int col) {
    GridPane gp = mbc.getBoardGrid();
    TileGrid tiles = mbc.getTiles();
    piece.setMatchBoardController(mbc);
    gp.add(piece.getImage(), col, row);
    tiles.addPiece(piece, row, col);
    piece.setTile(tiles.getTile(row, col));
  }
}
