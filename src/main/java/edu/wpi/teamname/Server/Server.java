package edu.wpi.teamname.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread{
    private ServerSocket socket;
    private HashMap<String, ServerThread> activeUsers;
    private ArrayList<String> activeGame;

    public Server (HashMap<String, ServerThread> activeUsers, ServerSocket socket) {
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

    public ServerThread findUser (String username) {
        return this.activeUsers.get(username);
    }

    public void saveMove(String move) {
        this.activeGame.add(move);
    }

    /**
     * Thread which accepts new connections to the server
     */
    @Override
    public void run() {
        try {
            while (true){
                Socket connection = getSocket().accept();
                System.out.println("Welcome to the server: " + connection.getInetAddress());
                ServerThread clientConnection = new ServerThread(this, connection);
                this.activeUsers.put("temp", clientConnection);
                clientConnection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Starts the server
     * @param args
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(new HashMap<>(), new ServerSocket(4999));
            server.start();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}
