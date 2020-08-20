package edu.wpi.teamname.models.match.board;

import edu.wpi.teamname.models.match.board.pieces.*;
import edu.wpi.teamname.views.match.components.MatchBoardController;
import javafx.scene.layout.GridPane;

public class PieceSet {
  MatchBoardController mbc;

  public PieceSet(String color, MatchBoardController mbc) {
    this.mbc = mbc;
    createRows(color);
  }

  public void createRows(String color) {
    String opponent = color.equals("white") ? "black" : "white";

    createPawnRow(color, 6);
    createPieceRow(color, 7);
    createPawnRow(opponent, 1);
    createPieceRow(opponent, 0);
  }

  public void createPawnRow(String color, int row) {
    for (int col = 0; col < 8; col++) {
      addPiece(new Pawn(color), row, col);
    }
  }

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

  public void addPiece(Piece piece, int row, int col) {
    GridPane gp = mbc.getBoardGrid();
    TileGrid tiles = mbc.getTiles();
    piece.setMatchBoardController(mbc);
    gp.add(piece.getImage(), col, row);
    tiles.addPiece(piece, row, col);
    piece.setTile(tiles.getTile(row, col));
  }
}
