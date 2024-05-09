// Player.java
import java.io.*;
import java.net.*;

public class Player {
    private Socket socket;
    public int receiveMessage [] = new int[6];
    public String host = "localhost";
    public int port = 12345;

    public Player() {
        try {
            // take from cmd and assign to host and port
            
            socket = new Socket(host, port); // Connect to server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Player(String host, int port) {
        try {
            // take from cmd and assign to host and port
            
            socket = new Socket(host, port); // Connect to server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeReceivedMessage(String message) {
        String[] messageArray = message.split(",");
        for (int i = 0; i < messageArray.length; i++) {
            receiveMessage[i] = Integer.parseInt(messageArray[i]);
            if(receiveMessage[i] > 100)
                receiveMessage[i] = 100;
        }
        for (int i = 0; i < receiveMessage.length; i++) {
            System.out.println("Player " + i + " is at position " + receiveMessage[i]);
        }
    }

    public void sendMessageToServer(String message) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String receiveMessageFromServer() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.sendMessageToServer("1");
        String message = player.receiveMessageFromServer();
        System.out.println("Server says: " + message);
        player.closeConnection();
    }
}
