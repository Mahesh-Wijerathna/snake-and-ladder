// ServerThread.java (unchanged)
import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private Socket playerId;

    public ServerThread(Socket playerId) {
        this.playerId = playerId;
    }

    
    public int playerPositions[] = new int[6];

    public int assignPlayerId() {
        for (int i = 1; i < Server.playerPositions.length; i++) 
            if (Server.playerPositions[i] == -1) {
                Server.playerPositions[i] = 0;
                return i;
            }
        
        return -1;
    }

    private String sendingMessage = "";

    public void setSendingMessage(int playerId) {
                
        
        if(playerId == 0)            
            playerId = assignPlayerId();
        
            sendingMessage = Integer.toString(playerId);
        for (int i = 1; i < Server.playerPositions.length; i++) 
                sendingMessage += ","+ Server.playerPositions[i] ;
        System.out.println("Say to  " + playerId + " result is  : " + sendingMessage);    
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(playerId.getInputStream()));
            PrintWriter out = new PrintWriter(playerId.getOutputStream(), true);

            while (true) {

                String receivedMessage = in.readLine();
                System.out.println("Received message: " + receivedMessage);
                int playerIdAndIncrement[] = new int[2];
                String[] messageArray = receivedMessage.split(",");
                for (int i = 0; i < messageArray.length; i++) {
                    playerIdAndIncrement[i] = Integer.parseInt(messageArray[i]);
                }
                Server.playerPositions[playerIdAndIncrement[0]] += playerIdAndIncrement[1];
                if (receivedMessage != null) {
                    System.out.println("Player " + playerIdAndIncrement[0] + " sent: " + receivedMessage);
                    setSendingMessage(playerIdAndIncrement[0]);
                    out.println(this.sendingMessage); // Send reply to player
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
