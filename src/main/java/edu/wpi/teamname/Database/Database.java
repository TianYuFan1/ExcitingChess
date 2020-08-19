package edu.wpi.teamname.Database;

import edu.wpi.teamname.Server.Instruction;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

  private final Connection connection;

  public Database(Connection connection) {
    this.connection = connection;
  }

  /** Initializes table, does not insert any info */
  public boolean initTables() {
    Statement statement = null;
    try {
      statement = connection.createStatement();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      statement.execute(
          ""
              + "CREATE TABLE users ("
              + "username VARCHAR(20) not null,"
              + "password VARCHAR(100) not null,"
              + "rating integer not null DEFAULT 1200,"
              + "constraint user_PK primary key (username))");
    } catch (SQLException e) {
      return false;
    }
    try {
      statement.execute(
          ""
              + "CREATE TABLE games ("
              + "gameID VARCHAR(45) NOT NULL,"
              + "white VARCHAR(20) NOT NULL,"
              + "black VARCHAR(20) NOT NULL,"
              + "gameNumber INTEGER NOT NULL,"
              + "date DATE NOT NULL,"
              + "CONSTRAINT game_PK PRIMARY KEY (gameID))");
    } catch (SQLException e) {
      return false;
    }
    try {
      statement.execute(
          ""
              + "CREATE TABLE moves ("
              + "moveNumber INTEGER NOT NULL, "
              + "gameID VARCHAR(45) NOT NULL,"
              + "whiteMove VARCHAR(10),"
              + "blackMove varchar(10),"
              + "CONSTRAINT move_PK PRIMARY KEY (moveNumber, gameID))");
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  /**
   * Saves the current game in the server Buffer
   *
   * @param moveList ArrayList<String>
   * @param instruction Instruction
   */
  public void saveGame(ArrayList<String> moveList, Instruction instruction) {
    try {
      String gameID = produceGameID(instruction);
      PreparedStatement insertGame =
          connection.prepareStatement("insert into games values (?,?,?,?)");
      PreparedStatement insertMoves =
          connection.prepareStatement(
              "insert into moves (moveNumber, gameID, whiteMove, blackMove) values (?,?,?,?)"); // gets node id's of
      for (int i = 0; i < moveList.size(); i += 2) {
        insertMoves.setInt(1, i / 2);
        insertMoves.setString(2, gameID);
        insertMoves.setString(3, moveList.get(i));
        if (i + 1 < moveList.size()) insertMoves.setString(4, moveList.get(i + 1));
        else insertMoves.setString(4, "");
        insertMoves.execute();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Produces a game ID given an instruction, used to query or save games
   *
   * @param i Instruction
   * @return String Game ID
   */
  public String produceGameID(Instruction i) {
    StringBuilder sr = new StringBuilder();
    sr.append(i.getUser() + i.getTarget());
    try {
      PreparedStatement selectGameCount =
          connection.prepareStatement(
              ""
                  + "SELECT COUNT(white) "
                  + "FROM  games "
                  + "WHERE white = ? AND black = ? "
                  + "OR "
                  + "black = ? and white = ?");
      selectGameCount.setString(1, i.getUser());
      selectGameCount.setString(2, i.getTarget());
      selectGameCount.setString(3, i.getUser());
      selectGameCount.setString(4, i.getTarget());

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return sr.toString();
  }
}