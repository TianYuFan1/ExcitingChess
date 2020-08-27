package edu.wpi.teamname.Server;

import edu.wpi.teamname.Database.Database;
import edu.wpi.teamname.Instruction.Instruction;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Server extends Thread {
  private ServerSocket socket;
  private ArrayList<String> allUsers;
  private HashMap<String, ServerThread> activeUsers;
  private HashMap<String, Game> activeGames;
  private Database database;

  public Server(HashMap<String, ServerThread> activeUsers, ServerSocket socket) {
    this.activeUsers = activeUsers;
    this.socket = socket;
    this.allUsers = new ArrayList<>();
    this.activeGames = new HashMap<>();
  }

  public ServerSocket getSocket() {
    return socket;
  }

  public void addUser(String username, ServerThread clientConnection) {
    this.allUsers.add(username);
    this.activeUsers.put(username, clientConnection);
    if (allUsers.size() == 2) {
      Game game = new Game(this , allUsers.get(0), allUsers.get(1));
      String id = "test";
      activeGames.put(id, game);
      Instruction startGameWhite = new Instruction ("startgame", allUsers.get(0), allUsers.get(1), "white", id);
      Instruction startGameBlack = new Instruction ("startgame", allUsers.get(1), allUsers.get(0), "black", id);
      game.getWhiteThread().sendInstruction(startGameWhite);
      game.getBlackThread().sendInstruction(startGameBlack);
    }
  }

  public void removeUser(String username) {
    this.activeUsers.remove(username);
  }

  public ServerThread findUser(String username) {
    return this.activeUsers.get(username);
  }

  /**
   * Adds a move to an active game
   *
   * @param i Instruction Move Instruction
   */
  public void saveMove(Instruction i) {
    activeGames.get(i.getGame()).saveMove(i.getPayload());
  }

  /**
   * Processes a takeback request, acceptance, or denial
   *
   * @param i Instruction
   */
  public void processTakeBack(Instruction i) {
    switch (i.getPayload()) {
      case ("accept"):
        Game game = lookupGame(i.getGame());
        String lastMove = game.popMove();
        String firstMove = game.popMove();
        undoMove(lastMove, firstMove, i);
        break;
      case ("request"):
        this.activeUsers.get(i.getTarget()).sendInstruction(i);
        break;
      case ("deny"):
        break;
    }
  }

  /**
   * Sends an instruction List to a user List
   *
   * @param users String
   * @param instructions Instruction
   */
  public void sendInstructions(ArrayList<String> users, ArrayList<Instruction> instructions) {
    for (String user : users) {
      for (Instruction instruction : instructions) {
        this.activeUsers.get(user).sendInstruction(instruction);
      }
    }
  }

  /**
   * Undos 2 given moves
   *
   * @param last String Last move made
   * @param first String first move made
   * @param i Instruction
   */
  public void undoMove(String last, String first, Instruction i) {
    ArrayList<String> users =
            new ArrayList<>(Arrays.asList(lookupGame(i.getGame()).getWhite(), lookupGame(i.getGame()).getBlack()));
    Instruction undoLastMove = new Instruction("undo", "", "", last, i.getGame());
    Instruction undoFirstMove = new Instruction("undo", "", "", first, i.getGame());
    ArrayList<Instruction> instructions =
        new ArrayList<>(Arrays.asList(undoLastMove, undoFirstMove));
    sendInstructions(users, instructions);
  }

  /**
   * Stub until DB is implemented
   *
   * @param i
   */
  public void retrieveGame(Instruction i) {
    this.database.retrieveGame(i.getPayload());
  }

  /**
   * Sends Current game to both Clients in case of reset SEND ALL AT ONCE
   *
   * @param i Instruction to be processed
   */
  public void sendPastGame(Instruction i) {
    ServerThread destination = this.activeUsers.get(i.getTarget());
    ServerThread origin = this.activeUsers.get(i.getUser());
    StringBuilder moves = new StringBuilder();
    ArrayList<String> activeGame = lookupGame(i.getGame()).getMoves();
    for (String s : activeGame) {
      moves.append(s);
      moves.append('*');
    }
    Instruction instruction = new Instruction("loadgame", "server", "client", moves.toString(), i.getGame());
    destination.sendInstruction(instruction);
    origin.sendInstruction(instruction);
  }

  /**
   * Saves game to database
   *
   * @param i Instruction to be processed
   */
  public void saveGame(Instruction i) {
    this.database.saveGame(lookupGame(i.getGame()).getMoves(), i);
    this.activeGames.remove(i.getGame());
  }

  /** Thread which accepts new connections to the server */
  @Override
  public void run() {
    try {
      initializeDB();
      this.database.initTables();
      while (true) {
        Socket connection = getSocket().accept();
        System.out.println("Welcome to the server: " + connection.getInetAddress());
        ServerThread clientConnection = new ServerThread(this, connection);
        clientConnection.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Begins the DB connection and saves it for later use */
  public void initializeDB() {
    Connection c = null;
    try {
      Class.forName("org.postgresql.Driver");
      c =
          DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/chessdb", "patrick", "PASSWORD");
      Database database = new Database(c);
      this.database = database;
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  public Game lookupGame (String id) {
    return this.activeGames.get(id);
  }

  /**
   * Starts the server
   *
   * @param args
   */
  public static void main(String[] args) {
    try {
      Server server = new Server(new HashMap<>(), new ServerSocket(4999));
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
