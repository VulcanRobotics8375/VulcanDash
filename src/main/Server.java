package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    ServerSocket serverSocket;
    Socket clientSocket;

    DataOutputStream outputStream;
    DataInputStream inputStream;

    public Server(int port) {
        super();
        try {
            //create a Server endpoint
            System.out.println("connecting to the server at port " + port);
            serverSocket = new ServerSocket(port);

            //wait for a client response
            clientSocket = serverSocket.accept();

            //set up datastreams
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            inputStream = new DataInputStream(clientSocket.getInputStream());

            System.out.println("connected");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {

            while(true) {
                MessageHandler.parseMessage(inputStream.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToRobot(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Thread server = new Server(8375);

        server.start();
    }



}