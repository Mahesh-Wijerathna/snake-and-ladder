// Server.java
import java.io.*;
import java.net.*;


public class Server {
    public static int playerPositions[] = new int[] {0 ,-1, -1, -1, -1, -1};
    public static void main(String[] args) {
		
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Choose any available port
            System.out.println("Server started.");

            while (true) {
                Socket playerId = serverSocket.accept();
                System.out.println("Player connected.");
				

                ServerThread serverThread = new ServerThread(playerId);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
