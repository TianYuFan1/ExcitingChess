package edu.wpi.teamname.Server;

import edu.wpi.teamname.Database.Database;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread {
  private ServerSocket socket;
  private HashMap<String, ServerThread> activeUsers;
  private ArrayList<String> activeGame;
  private Database database;

  public Server(HashMap<String, ServerThread> activeUsers, ServerSocket socket) {
    this.activeUsers = activeUsers;
    this.socket = socket;
    this.activeGame = new ArrayList<>();
  }

  public ServerSocket getSocket() {
    return socket;
  }

  public void addUser(String username, ServerThread clientConnection) {
    this.activeUsers.put(username, clientConnection);
  }

  public void removeUser(String username) {
    this.activeUsers.remove(username);
  }

  public ServerThread findUser(String username) {
    return this.activeUsers.get(username);
  }

  /**
   * Adds a move to an active game
   * @param move String move that will be added to the moveList
   */
  public void saveMove(String move) {
    this.activeGame.add(move);
  }

  /**
   * Processes a takeback request, acceptance, or denial
   * @param i
   */
  public void processTakeBack(Instruction i) {
    switch (i.getPayload()) {
      case ("accept"):
        this.activeGame.remove(activeGame.size() - 1);
        this.activeGame.remove(activeGame.size() - 2);
        sendCurrentGame(i);
        break;
      case ("request"):
        this.activeUsers.get(i.getTarget()).sendInstruction(i);
        break;
      case ("deny"):
        break;
    }
  }

  /**
   * Stub until DB is implemented
   *
   * @param i
   */
  public void retrieveGame(Instruction i) {}

  /**
   * Sends Current game to both Clients in case of reset
   * @param i Instruction to be processed
   */
  public void sendCurrentGame(Instruction i) {
    ServerThread destination = this.activeUsers.get(i.getTarget());
    ServerThread origin = this.activeUsers.get(i.getUser());

    for (int count = 0; count < activeGame.size() - 1; count++) {

    }
  }

  /**
   * Saves game to database
   * @param i Instruction to be processed
   */
  public void saveGame(Instruction i) {
    this.database.saveGame(this.activeGame, i);
    this.activeGame = new ArrayList<>();
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

  /**
   * Begins the DB connection and saves it for later use
   */
  public void initializeDB () {
    Connection c = null;
    try {
      Class.forName("org.postgresql.Driver");
      c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chessdb",
              "patrick", "PASSWORD");
      Database database = new Database (c);
      this.database = database;
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
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
