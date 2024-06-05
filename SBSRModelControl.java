import java.awt.*;
import java.io.*;
<<<<<<< HEAD

import javax.swing.JPanel;

import java.awt.event.*;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

import java.awt.geom.AffineTransform;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
=======
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
>>>>>>> cf78f6a267133e9d7c482fd6015db26e8551ea42

public class SBSRModelControl implements ActionListener{
	//Properties

	//Connection
	public String strConnectionResult;
	public String strHostUsername;
	public String strClientUsername;
	public boolean blnHost = false;
	public int intNumPlayers = 0;
	public String strIp;
	public String strPort;
	public String strUsername;
	public String strResult;

	//Map selection
	public int intMapSelection = 0;

	//Chacter selection
	public int intHostCharacter = 0;
	public int intClientCharacter = 0;

	//Play
	public int intPlayersReady = 0;

	public String strLine;		
	public String strSplit[];
	public int intRowNum;
	public int intColumnNum;	
	public int intColumnNum2;
	public int intRowNum2;
	public String strMap[][] = new String[20][20];
	BufferedImage imgGrass = null;
    BufferedImage imgAir = null;
    BufferedImage imgBrick = null;
	
	//splitting ssm messages -> mode(chat/charcter/play/game/connection),user(host/client),action(message/game input),xcord,ycord
	String[] ssmMessage;
	SBSRViewTest view;
	SuperSocketMaster ssm;

	//AnimationPanel
	AnimationPanelTest AniPanel = new AnimationPanelTest();
	public Timer theTimer = new Timer(1000/60,this);
	
	//Methods

	//setting up connection
	public String connect(String ipField, String portField, String UsernameField){
		if(ipField.equals("") && portField.equals("")){
			strConnectionResult = "Enter a port number and/or IP Address\n";
		}else if(ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult = "Enter Username";
		}else if(ipField.equals("") && !portField.equals("") && !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in server mode\n");
			try{
				ssm = new SuperSocketMaster(Integer.parseInt(portField),this);
				ssm.connect();
				strHostUsername = UsernameField;
				System.out.println("Host: "+strHostUsername);
				strConnectionResult = "(HOST) Your IP:" + ssm.getMyAddress();
				blnHost = true;
				intNumPlayers = 1;
				view.UsernameField.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		}else if(!ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult = "Enter Username";
		}else if(!ipField.equals("") && !portField.equals("")&& !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in client  mode\n");
			try{
				ssm = new SuperSocketMaster(ipField,Integer.parseInt(portField),this);
				ssm.connect();
				strClientUsername = UsernameField;
				System.out.println("Client: " +strClientUsername);
				strConnectionResult = "(Client) Connected to: " + ipField;
				blnHost = false;
				intNumPlayers +=1;
				view.UsernameField.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		}else if(!ipField.equals("") && portField.equals("")){
			strConnectionResult.equals("Need a portnumber or port/ip \n");
		}
		return strConnectionResult;
	}

	//check play method
	public void checkPlay(Graphics g){
		//Graphics2D g2d = (Graphics2D) g;
		try{
			BufferedReader mapFile = new BufferedReader(new FileReader("Map1.csv")); 
			imgGrass = ImageIO.read(new File("Colt.jpg"));
			imgAir = ImageIO.read(new File("Shelly.jpg"));
			imgBrick = ImageIO.read(new File("Brick.png"));
			strLine = mapFile.readLine();
			//Methods

		}catch(IOException e){
	
		}catch(ArrayIndexOutOfBoundsException e){

		}catch(NullPointerException e){

		}		
		if (intPlayersReady == 2){
			System.out.println("Both players are ready");
<<<<<<< HEAD
			try{
				BufferedReader mapFile = new BufferedReader(new FileReader("Map1.csv")); 
				imgGrass = ImageIO.read(new File("Colt.jpg"));
            	imgAir = ImageIO.read(new File("Shelly.jpg"));
            	imgBrick = ImageIO.read(new File("Brick.png"));
				//Methods

			}catch(IOException e){
		
			}catch(ArrayIndexOutOfBoundsException e){

			}
			for(intRowNum = 0; intRowNum < 20; intRowNum++){
			
				// reads the line of text from the map file, returns a string, and puts it into the variable strLine
				try{
					BufferedReader mapFile = new BufferedReader(new FileReader("Map1.csv")); 
					imgGrass = ImageIO.read(new File("Colt.jpg"));
					imgAir = ImageIO.read(new File("Shelly.jpg"));
					imgBrick = ImageIO.read(new File("Brick.png"));
					strLine = mapFile.readLine();
					//Methods
	
				}catch(IOException e){
			
				}catch(ArrayIndexOutOfBoundsException e){
	
				}catch(NullPointerException e){

				}
				
						   
			   for(intColumnNum = 0; intColumnNum < 100; intColumnNum++){
				   // Need a 1d array to split that line based on commas
				   try{
				   strSplit = strLine.split(",");
				   }catch(NullPointerException e){

				   }
				   // copy to the 2d array
				   try{
				   strMap[intRowNum][intColumnNum] = strSplit[intColumnNum]; 
					}catch(NullPointerException e){

				}
			   }
		
		   }
			
		for(intRowNum2 = 0; intRowNum2 < 20; intRowNum2++){
			
			for(intColumnNum2 = 0; intColumnNum2 < 20; intColumnNum2++){
				
				if(strMap[intRowNum2][intColumnNum2].equals("g")){
					g.drawImage(imgGrass, (20 * intColumnNum2), ((20 * intRowNum2) + 100),null);
					
				}
				
				if(strMap[intRowNum2][intColumnNum2].equals("a")){
					g.drawImage(imgAir, (20 * intColumnNum2), ((20 * intRowNum2) + 100),null);
					
				}
				
				if(strMap[intRowNum2][intColumnNum2].equals("b")){
					g.drawImage(imgBrick, (20 * intColumnNum2), ((20 * intRowNum2) + 100),null);
					
				}
				
				/*if(strMap[intRowNum2][intColumnNum2].equals("h")){
					con.drawImage(imgHealth, (20 * intColumnNum2), ((20 * intRowNum2) + 100));
					
				}
				
				if(strMap[intRowNum2][intColumnNum2].equals("f")){
					con.drawImage(imgForce, (20 * intColumnNum2), ((20 * intRowNum2) + 100));
					
				}*/
			}
		}

=======
			theTimer.start();
>>>>>>> cf78f6a267133e9d7c482fd6015db26e8551ea42
		}
	}

	//overridding action listener
	public void actionPerformed(ActionEvent evt){
		//Connect menu button
		if(evt.getSource() == view.ConnectMenuButton){
			view.theframe.setContentPane(view.ConnectPanel);
			view.theframe.revalidate();
		//back button
		}else if(evt.getSource() == view.BackConnectButton){
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
		//Help Menu button
		}else if(evt.getSource() == view.HelpMenuButton){
			view.theframe.setContentPane(view.HelpPanel);
		//Connect button
		}else if(evt.getSource() == view.ConnectButton){
			strIp = view.ipField.getText();
			strPort = view.portField.getText();
			strUsername = view.UsernameField.getText();
			//getting the status result from the connect method
			String strResult = connect(strIp, strPort, strUsername);
			view.ConnectionStatusLabel.setText(strResult);
		//Play menu button
		}else if(evt.getSource() == view.PlayMenuButton){
			if(blnHost){
				view.theframe.setContentPane(view.MapPanel);
				view.theframe.revalidate();
			} else{
				System.out.println("You are not Host or have not connected yet");
			}
		//Map selection
		}else if (evt.getSource() == view.Map1Button){
			intMapSelection = 1;
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		}else if(evt.getSource() == view.Map2Button){
			intMapSelection = 2;
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		//Character selection
		}else if(evt.getSource() == view.Character1Button){
			if(blnHost){
				intHostCharacter = 1;
				System.out.println("Host Character: Colt");
			}else {
				intClientCharacter = 1;
				System.out.println("Client Character: Colt");
			}
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("connection,"+strUsername);

			intPlayersReady += 1;

			view.ChatArea.append(intPlayersReady + " joined\n");
			view.ChatArea.append(strUsername + " has connected\n");
			
<<<<<<< HEAD
			

			checkPlay(null);

=======
			checkPlay();
>>>>>>> cf78f6a267133e9d7c482fd6015db26e8551ea42
		}else if(evt.getSource() == view.Character2Button){
			if(blnHost){
				intHostCharacter = 2;
				System.out.println("Host Character: El Primo");
			}else{
				intClientCharacter = 2;
				System.out.println("Client Character: El Primo");
			}
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("connection,"+strUsername);

			intPlayersReady += 1;

			view.ChatArea.append(intPlayersReady + " joined\n");
			view.ChatArea.append(strUsername + " has connected\n");
			
<<<<<<< HEAD

			checkPlay(null);
			
		//Text input 
=======
			checkPlay();
		//Text input Chat
>>>>>>> cf78f6a267133e9d7c482fd6015db26e8551ea42
		}else if(evt.getSource() == view.ChatTextInput){
			if(blnHost == true){
				ssm.sendText("chat,"+strHostUsername+","+view.ChatTextInput.getText());
				view.ChatArea.append(strHostUsername+": "+view.ChatTextInput.getText()+ "\n");
				view.ChatTextInput.setText("");
			}else if(blnHost == false){
				ssm.sendText("chat,"+strClientUsername+","+view.ChatTextInput.getText());
				view.ChatArea.append(strClientUsername+": "+view.ChatTextInput.getText()+ "\n");
				view.ChatTextInput.setText("");
			}
		}else if(evt.getSource() == theTimer){
			
			System.out.println("Timer is running");
			AniPanel.repaint();
		//Detecting SSM 
		}else if(evt.getSource() == ssm){
			ssmMessage = ssm.readText().split(",");
			//sending both host and client to the play screen
			if(ssmMessage[0].equals("character")){
				view.theframe.setContentPane(view.CharacterPanel);
				view.theframe.revalidate();
				System.out.println("Host has selected map, character selection");
			//Getting chat responses
			}else if(ssmMessage[0].equals("chat")){
				view.ChatArea.append(ssmMessage[1] + ": " + ssmMessage[2] + "\n");
			//Getting connection responses
			}else if(ssmMessage[0].equals("connection")){
				intPlayersReady += 1;
				view.ChatArea.append(ssmMessage[1] + " has connected\n");
				System.out.println("Players Ready: "+intPlayersReady);
<<<<<<< HEAD
				try{
				checkPlay(null);
				}catch(NullPointerException e){

			}
=======

				checkPlay();
>>>>>>> cf78f6a267133e9d7c482fd6015db26e8551ea42

			}else{
				System.out.println("Invalid SSM message");
			}
		}
	}

	//Constructor
	public SBSRModelControl(SBSRViewTest view){
		this.view = view;

		view.PlayPanel.add(AniPanel);
		view.PlayPanel.repaint();
		view.PlayPanel.revalidate();

		// adding Action listeners

		//Main Menu
		view.PlayMenuButton.addActionListener(this);
		view.ConnectMenuButton.addActionListener(this);
		view.HelpMenuButton.addActionListener(this);
		
		//Connect Page
		view.ConnectButton.addActionListener(this);
		view.BackConnectButton.addActionListener(this);

		//Map page
		view.Map1Button.addActionListener(this);
		view.Map2Button.addActionListener(this);

		//Help page
		view.HelpMenuButton.addActionListener(this);

		//Character page
		view.Character1Button.addActionListener(this);
		view.Character2Button.addActionListener(this);

		//Chat
		view.ChatTextInput.addActionListener(this);
	}

	//Main program
	public static void main(String[] args){
		SBSRViewTest view = new SBSRViewTest();
		new SBSRModelControl(view);
	}
	
}
