package edu.wpi.teamname.Database;

import edu.wpi.teamname.Server.Instruction;

import javax.xml.transform.Result;
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
   * Saves the current game in the server Buffer to the Games table
   *
   * @param moveList ArrayList<String>
   * @param instruction Instruction
   */
  public void saveGame(ArrayList<String> moveList, Instruction instruction) {
    try {
      String gameID = produceGameID(instruction);
      PreparedStatement insertGame =
          connection.prepareStatement("INSERT INTO games " +
                  "VALUES (?,?,?,?, CURRENT DATE)");
      insertGame.setString(1, gameID);
      insertGame.setString(2, instruction.getUser());
      insertGame.setString(3, instruction.getTarget());
      insertGame.setInt(4, retrieveGameCount(instruction));
      insertGame.execute();
      saveMoves(moveList, instruction);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves the current moveList in the server Buffer to the Moves table
   *
   * @param moveList ArrayList<String>
   * @param instruction Instruction
   */
  public void saveMoves(ArrayList<String> moveList, Instruction instruction) {
    try {
      String gameID = produceGameID(instruction);
      PreparedStatement insertMoves =
              connection.prepareStatement(
                      "INSERT INTO moves " +
                              "(moveNumber, gameID, whiteMove, blackMove) " +
                              "VALUES (?,?,?,?)");
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
    sr.append(retrieveGameCount(i));
    return sr.toString();
  }

  /**
   * Retrieves all games where user X played user Y
   *
   * @param x String
   * @param y String
   *
   * @return ArrayList of game info
   */
  public ArrayList<String> retrieveGameSet(String x, String y) {
    ArrayList<String> gameSet = new ArrayList<>();
    try {
      PreparedStatement retrieveGames =
              connection.prepareStatement(
                      ""
                              + "SELECT * "
                              + "FROM  games "
                              + "WHERE white = ? AND black = ? "
                              + "OR "
                              + "black = ? and white = ?");
      retrieveGames.setString(1, x);
      retrieveGames.setString(2, y);
      retrieveGames.setString(3, x);
      retrieveGames.setString(4, y);
      ResultSet rs = retrieveGames.executeQuery();
      while (rs.next()) {
        gameSet.add(rs.getString("gameID"));
        gameSet.add(rs.getString("white"));
        gameSet.add(rs.getString("black"));
        gameSet.add(Integer.toString(rs.getInt("gameNumber")));
        gameSet.add(rs.getDate("date").toString());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return gameSet;
  }

  /**
   * Pulls count of games played between two people for storage consistency
   * @param i Instruction
   * @return integer
   */
  public int retrieveGameCount (Instruction i) {
    try {
      PreparedStatement selectGameCount =
              connection.prepareStatement(
                      ""
                              + "SELECT COUNT(white) AS number "
                              + "FROM  games "
                              + "WHERE white = ? AND black = ? "
                              + "OR "
                              + "black = ? and white = ?");
      selectGameCount.setString(1, i.getUser());
      selectGameCount.setString(2, i.getTarget());
      selectGameCount.setString(3, i.getUser());
      selectGameCount.setString(4, i.getTarget());
      ResultSet rs = selectGameCount.executeQuery();
      return rs.getInt("number");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 1;
  }

  /**
   * Pulls a specific game by gameID
   * @param gameID String gameID you want
   * @return ArrayList of game info
   */
  public ArrayList<String> retrieveGame (String gameID) {
    ArrayList<String> game = new ArrayList<>();
    try {
      PreparedStatement retrieveGame =
              connection.prepareStatement(
                      ""
                              + "SELECT * "
                              + "FROM  games "
                              + "WHERE gameID = ?");
      retrieveGame.setString(1, gameID);
      ResultSet rs = retrieveGame.executeQuery();
      game.add(rs.getString("gameID"));
      game.add(rs.getString("white"));
      game.add(rs.getString("black"));
      game.add(Integer.toString(rs.getInt("gameNumber")));
      game.add(rs.getDate("date").toString());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return game;
  }


}
