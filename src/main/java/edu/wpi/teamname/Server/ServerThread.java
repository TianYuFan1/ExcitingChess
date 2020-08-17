package edu.wpi.teamname.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private final Socket socket;
    private final Server server;
    private String username;

    public ServerThread(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Thread method which waits for client input and responds to it
     */
    @Override
    public void run() {
        try {
            Instruction initialize = Instruction.parseStream(socket.getInputStream());
            initializeThread(initialize);
            boolean online = true;
            while (online) {
                try {
                    Instruction instruction = Instruction.parseStream(socket.getInputStream());
                    if (instruction != null) {
                        processInstruction(instruction);
                    }
                } catch (IOException e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeThread (Instruction instruction) {
        String username = instruction.getUser();
        this.server.addUser(username, this);
        this.username = username;
    }

    public void processInstruction (Instruction instruction) {
        String op = instruction.getOperation();
        String user = instruction.getUser();
        String target = instruction.getTarget();
        String payload = instruction.getPayload();
        try {
            switch (op) {
                case ("move"):
                    ServerThread destination = this.server.findUser(target);
                    this.server.saveMove(payload);
                    destination.socket.getOutputStream().write((op + '|' + user + '|' + target + '|' + payload + '\0').getBytes());
                    break;
                case ("logoff"):
                    this.server.removeUser(user);
                    break;
                case ("takeback"):
                    break;
                case ("viewgame"):
                    break;
                case ("savegame"):
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}