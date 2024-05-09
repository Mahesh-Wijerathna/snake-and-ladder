// GUI.java
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ActionListener {
    
    
    public void actionPerformed(ActionEvent e)
	{
        if(e.getSource() == startGame)
		{
			
			String nameSecond = secondPlayerName.getText();
            
            

			if((firstPlayerName.getText().equals("")) && (secondPlayerName.getText().equals("")))
			{
				JOptionPane.showMessageDialog(enterNameDialog, "Please Enter Name of Both Players!!!");
			}
			else
			{
				if (player == null) {
                    player = new Player( firstPlayerName.getText(), Integer.parseInt(nameSecond));
                    System.out.println("Player host: " + player.host);
                    System.out.println("Player port: " + player.port);
                    player.host = firstPlayerName.getText();
                    player.port = Integer.parseInt(nameSecond);
                    
                }
                System.out.println("Player host: " + player.host);
                System.out.println("Player port: " + player.port);
                
                
                player.sendMessageToServer(setSendingMessage(1));
                new Thread(() -> {
                    String message = player.receiveMessageFromServer();
                    System.out.println("Message received: " + message);
                    player.storeReceivedMessage(message);
                    
                }).start();

                startGame();
				enterNameDialog.dispose();


			}
		}
        if( e.getSource() == diceButton )
        {
            System.out.println("Dice Button called");
            
            
            int number = new Random().nextInt(6)+1;

            
            
            diceButton.setIcon(new ImageIcon("dice_images/dice_"+number+".png"));
                //checking 
                System.out.println("Number : "+number);
                System.out.println("player.receiveMessage : "+player.receiveMessage[0]);
                System.out.println("player.receiveMessage[player.receiveMessage[0]] : "+player.receiveMessage[player.receiveMessage[0]]);
                System.out.println("new player.receiveMessage[player.receiveMessage[0]] : "+(player.receiveMessage[player.receiveMessage[0]] + number));
            
            
            player.sendMessageToServer(setSendingMessage(updateNumber(number)));
            
            // if(player.receiveMessage[player.receiveMessage[0]]  > 0)
                
            
 
            Thread thread = new Thread(() -> {
                String message = player.receiveMessageFromServer();
                System.out.println("Message received in thread: " + message);
                if(player.receiveMessage[player.receiveMessage[0]] > 0)
                    b[player.receiveMessage[player.receiveMessage[0]]-1].setIcon(new ImageIcon("images/img_"+player.receiveMessage[player.receiveMessage[0]]+".jpeg"));
                
                player.storeReceivedMessage(message);
                int player_id = player.receiveMessage[0];
                int player_position = player.receiveMessage[player_id];
                System.out.println("player_id : "+player_id);
                System.out.println("player_position : "+ player_position);



                if(player.receiveMessage[player.receiveMessage[0]] ==100 ){
                    
                // number of elemnets greter than 99 in player.receiveMessage assign place
                int place = 0;
                for(int i=1;i<player.receiveMessage.length;i++)
                    if(player.receiveMessage[i] == 100)
                        place++;



                    // show popup and disable dice button
                    
                    JOptionPane.showMessageDialog(mainFrame, " You are  " +place );
                    diceButton.setEnabled(false);


                    
                }
            
                for(int i=1;i<player.receiveMessage.length;i++) {
                    if(player.receiveMessage[0] == i || player.receiveMessage[i] == -1)
                        continue;
                    b[player.receiveMessage[i]-1].setIcon(new ImageIcon("player_images/doraemon.png"));
                }
                b[player.receiveMessage[player.receiveMessage[0]]-1 ].setIcon(new ImageIcon("player_images/nobita.png"));
            });
            
            thread.start();
            



            // To close the thread, you can call interrupt() method on the thread object
            
            
            // To close the thread, you can call interrupt() method on the thread object
            
            
            
            
            
            // for(int i=1;i<player.receiveMessage.length;i++)
            //     if(player.receiveMessage[i] != player.receiveMessage[player.receiveMessage[0]])
            //         b[player.receiveMessage[i]-1].setIcon(new ImageIcon("player_images/doraemon.png"));
            
            
                
                
                //b[player_position].setIcon(new ImageIcon("images/img_"+5+".jpeg"));
            
            

            System.out.println("End of Dice Button");
            thread.interrupt();

        }

    }
    int updateNumber (int number)
    {
        System.out.println("Update Number called");
        
        int tempPosition = player.receiveMessage[player.receiveMessage[0]] + number;
        if(tempPosition == 4)
            number = number + (14 -4) ;
        else if(tempPosition == 9)
            number = number + (31 - 9);
        else if(tempPosition == 17)
            number = number + (7 - 17);
        else if(tempPosition == 21)
            number = number + (42 - 21);
        else if(tempPosition == 28)
            number = number + (84 - 28);
        else if(tempPosition == 51)
            number = number + (67 - 51);
        else if(tempPosition == 54)
            number = number + (34 - 54);
        else if(tempPosition == 62)
            number = number + (19 - 62);
        else if(tempPosition == 64)
            number = number + (60 - 64);
        else if(tempPosition == 72)
            number = number + (91 - 72);
        else if(tempPosition == 80)
            number = number + (99 - 80);
        else if(tempPosition == 87)
            number = number + (36 - 87);
        else if(tempPosition == 93)
            number = number + (73 - 93);
        else if(tempPosition == 95)
            number = number + (75 - 95);
        else if(tempPosition == 98)
            number = number + (79 - 98);
         
        System.out.println("End of Update Number");
        return number;
    }
    public String setSendingMessage(int increment)
            {
                if(player == null)
                    return "0,0";
                String sendingMessage = "";
                sendingMessage += player.receiveMessage[0] + "," + increment;
                System.out.println("Sending message: " + sendingMessage);
                return sendingMessage;
            }
    
    
        
    JDialog enterNameDialog;
	JLabel enterNameLabel;
	JLabel namePlayerFirst,namePlayerSecond;
	JTextField firstPlayerName, secondPlayerName;
	JLabel imgFirstPlayerLabel, imgSecondPlayerLabel;
	JButton startGame;
		
	
	//startGameComponents
	JFrame mainFrame;
	JLabel b[];
	JLabel winLabel,winImage;
	JLabel turnLabel;
	JLabel firstPlayer,secondPlayer;
	
	//DefiningImageTiles
	String imgPath[];	
	ImageIcon imgIcon[];
	JButton diceButton;
	
	int x1=220,x2=760,x3=220,x4=760,x5=220,x6=760,x7=220,x8=760,x9=220,x10=760;
	int y=550;
	int w=60;
	int h=60;

    void onStart()
    {    
        enterNameDialog = new JDialog(mainFrame,"Snake & Ladder : Players",true);
		enterNameDialog.setSize(500,500);
		enterNameDialog.setLayout(null);
		
		
		//EnterNameLabel
		enterNameLabel = new JLabel("Host & Port");
		enterNameLabel.setFont(new java.awt.Font("Times New Roman", 2, 40));
		enterNameLabel.setBounds(80,50,450,50);
		enterNameDialog.add(enterNameLabel);
		
		//FirstPlayerName
		namePlayerFirst =new JLabel("Host:");
		namePlayerFirst.setFont(new java.awt.Font("Times New Roman", 2, 18));
		namePlayerFirst.setBounds(50,150,70,18);
		enterNameDialog.add(namePlayerFirst);
		
		firstPlayerName = new JTextField(null);
		firstPlayerName.setBounds(160,145,150,30);
		firstPlayerName.setText("localhost");
		enterNameDialog.add(firstPlayerName);
		
		//SecondPlayerName
		namePlayerSecond =new JLabel("Port:");
		namePlayerSecond.setFont(new java.awt.Font("Times New Roman", 2, 18));
		namePlayerSecond.setBounds(50,250,70,18);
		enterNameDialog.add(namePlayerSecond);
		
		secondPlayerName = new JTextField(null);
		secondPlayerName.setBounds(160,245,150,30);
		secondPlayerName.setText("12345");
		enterNameDialog.add(secondPlayerName);
		
		
		
		//StartGameButton
		startGame = new JButton("Start Game");
		startGame.setBounds(185,320,100,30);
		enterNameDialog.add(startGame);
		
		startGame.addActionListener(this);
		enterNameDialog.setVisible(true);    
    }

    
    
    void startGame()
	{
		mainFrame = new JFrame("Snake & Ladder ");
		
		//DefiningImageLabels
		b = new JLabel[101];
		for(int i = 0; i < b.length; i++)
		{
			b[i] = new JLabel();
		}
		
		//CreatingImageTiles
		imgPath = new String[100];
		for(int i=0;i<imgPath.length;i++)
		{
			imgPath[i] = "images/img_"+(i+1)+".jpeg";
		}
		
		//DefiningImageIcons
		imgIcon = new ImageIcon[100];
		for(int i = 0; i<imgIcon.length;i++)
		{
			imgIcon[i] = new ImageIcon(imgPath[i]);
			b[i].setIcon(imgIcon[i]);
		}
		
		//WinnerLabel
		
		
		
		
		//Defining_Layout
		for(int i=0;i<b.length;i++)
		{
			if(i<10)
			{
				b[i].setBounds(x1,y,w,h);
				x1+=w;
			}
			else if(i<20)
			{
				y=490;
				b[i].setBounds(x2,y,w,h);
				x2-=w;
			}
			else if(i<30)
			{
				y=430;
				b[i].setBounds(x3,y,w,h);
				x3+=w;
			}
			else if(i<40)
			{
				y=370;
				b[i].setBounds(x4,y,w,h);
				x4-=w;
			}
			else if(i<50)
			{
				y=310;
				b[i].setBounds(x5,y,w,h);
				x5+=w;
			}
			else if(i<60)
			{
				y=250;
				b[i].setBounds(x6,y,w,h);
				x6-=w;
			}
			else if(i<70)
			{
				y=190;
				b[i].setBounds(x7,y,w,h);
				x7+=w;
			}
			else if(i<80)
			{
				y=130;
				b[i].setBounds(x8,y,w,h);
				x8-=w;
			}
			else if(i<90)
			{
				y=70;
				b[i].setBounds(x9,y,w,h);
				x9+=w;
			}
			else if(i<100)
			{
				y=10;
				b[i].setBounds(x10,y,w,h);
				x10-=w;
			}
			else if( i == 100)
			{
				b[100].setBounds(160,10,60,60);
			}
		}
		
		//Pasting_Components_on_Frame
		for(int i=0;i<b.length;i++)
		{
			mainFrame.add(b[i]);
		}
		
		//
		firstPlayer = new JLabel(new ImageIcon("player_images/nobita.png"));
		firstPlayer.setBounds(150,550,60,60);
		
		
		
		mainFrame.add(firstPlayer);
		
		
		turnLabel = new JLabel();
		turnLabel.setFont(new java.awt.Font("Times New Roman", 1, 15));
		turnLabel.setBounds(875,70,150,50);
		turnLabel.setText("Click to Roll Dice");
		mainFrame.add(turnLabel);
		
		
		diceButton = new JButton();
		diceButton.setBounds(900,130,60,60);
		diceButton.setIcon(new ImageIcon("dice_images/dice.png"));
		diceButton.addActionListener(this);
		mainFrame.add(diceButton);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(null);
		mainFrame.setSize(1040,700);
		mainFrame.setVisible(true);
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private JFrame frame;
    private JLabel label;
    private Player player;

    public GUI() {
        onStart();

    //     frame = new JFrame("Player GUI");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //     label = new JLabel("Click the button to play.");
    //     JButton button = new JButton("Play");
    //     button.addActionListener(new ActionListener() {
            
            
    //         public String setSendingMessage(int increment)
    //         {
    //             if(player == null)
    //                 return "0,0";
    //             String sendingMessage = "";
    //             sendingMessage += player.receiveMessage[0] + "," + increment;
    //             System.out.println("Sending message: " + sendingMessage);
    //             return sendingMessage;
    //         }
    //         public void actionPerformed(ActionEvent e) {
    //             // Send message to server when button is clicked
    //             if (player == null) {
    //                 player = new Player();
    //             }

    //             player.sendMessageToServer(setSendingMessage(1));

    //             // Receive reply from server and display in GUI
    //             new Thread(() -> {
    //                 String message = player.receiveMessageFromServer();
    //                 player.storeReceivedMessage(message);
    //                 displayMessage(message);
    //             }).start();
    //         }
    //     });

    //     JPanel panel = new JPanel();
    //     panel.add(button);

    //     frame.getContentPane().setLayout(new BorderLayout());
    //     frame.getContentPane().add(label, BorderLayout.CENTER);
    //     frame.getContentPane().add(panel, BorderLayout.SOUTH);

    //     frame.setSize(400, 200);
    //     frame.setVisible(true);
    // }

    // public void displayMessage(String message) {
    //     label.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
