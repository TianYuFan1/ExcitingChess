package edu.wpi.teamname.Client;

import edu.wpi.teamname.Client.Instruction;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private String username;
    private Socket socket;
    private InputStream readIn;
    private OutputStream sendOut;

    public Client (String user , Socket socket) {
        this.username = user;
        this.socket = socket;
    }

    /**
     * The initial connection between the server and client is established here
     * @param user The username of the person connecting
     */
    public void connect(String user) {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 4999);
            this.username = user;
            this.socket = socket;
            this.readIn = socket.getInputStream();
            this.sendOut = socket.getOutputStream();
            Instruction initialize = new Instruction("initialize", username, "", "");
            sendInstruction(initialize);
            receiveMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method awaits Instruction from the server
     */
    public void receiveMsg() {
        Thread awaitInput = new Thread(() -> {
            try {
                while (true) {
                    if (this.readIn.available() > 0) {
                        Instruction instruction = Instruction.parseStream(this.readIn);
                        processInstruction(instruction);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        awaitInput.start();
    }

    /**
     * This Takes a received instruction and applies the operation requested
     * @param instruction The instruction to be acted upon
     */
    public void processInstruction(Instruction instruction) {
        String op = instruction.getOperation();
        String user = instruction.getUser();
        String target = instruction.getTarget();
        String payload = instruction.getPayload();
        switch (op) {
            case ("move"):
                    //TODO Have this modify the client state
                break;
            case ("takeback"):
                    //TODO Apply Redo method
                break;
            case ("viewgame"):
                    //TODO Apply viewgame
                break;
        }
    }

    /**
     * This sends a given instruction to the server
     * @param i Instruction to be sent
     */
    public void sendInstruction(Instruction i) {
        try {
            this.sendOut
                    .write(
                            (i.getOperation() + '|' + i.getUser() + '|' + i.getTarget() + '|' + i.getPayload())
                                    .getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
