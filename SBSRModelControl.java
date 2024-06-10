import java.awt.*;
import java.io.*;
import java.util.Map;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SBSRModelControl extends JPanel implements ActionListener, KeyListener{
	//Properties **************************************************************************************************************

	//Connection
	public String strConnectionResult;
	public String strHostUsername;
	public String strClientUsername;
	public String strIp;
	public String strPort;
	public String strUsername;
	public String strResult;
	public boolean blnHost = false;
	public int intNumPlayers = 0;

	//Chacter selection
	public int intHostCharacter = 0;
	public int intClientCharacter = 0;

	//Play
	public int intPlayersReady = 0;

	//splitting ssm messages -> mode(chat/charcter/play/game/connection),user(host/client),action(message/game input),xcord,ycord
	String[] ssmMessage;

	SBSRViewTest view;
	SuperSocketMaster ssm;

	//AnimationPanel
	public Timer theTimer = new Timer(1000/60,this);


	//Methods **************************************************************************************************************

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
	public void checkPlay(){
		if(intPlayersReady == 2){
			System.out.println("Both players are ready");
			theTimer.start();
		}
	}

	
	//Player movement 
	public void keyPressed(KeyEvent evt){
		//checking if key is pressed
		boolean PositionChanged = false;

		//return focus to animation panel when enter is pressed 
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			view.AniPanel.requestFocusInWindow();
			return;
		}

		//setting step size for character movement
		double step = 1/6;

		//Character movement with collision detection 
		if(evt.getKeyCode() == KeyEvent.VK_UP ){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.CharacterX+","+view.AniPanel.CharacterY+")");
			//checking for up collision
			try{
			if(view.AniPanel.CharacterY > 0 && view.AniPanel.Map[(int) (view.AniPanel.CharacterX + 1)][(int) (view.AniPanel.CharacterY - step)] == 'a' && view.AniPanel.Map[(int) (view.AniPanel.CharacterX)][(int) (view.AniPanel.CharacterY - step)] == 'a'){
				double newY = view.AniPanel.CharacterY - step;
				view.AniPanel.CharacterY = newY;
				PositionChanged = true;
			}
		}catch(ArrayIndexOutOfBoundsException e){

		}

		}else if(evt.getKeyCode() == KeyEvent.VK_DOWN ){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.CharacterX+","+view.AniPanel.CharacterY+")");
			//Checking for down collision
			try{
			if(view.AniPanel.CharacterY < view.AniPanel.MapHeight && view.AniPanel.Map[(int) (view.AniPanel.CharacterX + 1)][(int) (view.AniPanel.CharacterY + 1 + step)] == 'a' && view.AniPanel.Map[(int) (view.AniPanel.CharacterX)][(int) (view.AniPanel.CharacterY + 1 + step)] == 'a'){
				double newY = view.AniPanel.CharacterY + step;
				view.AniPanel.CharacterY = newY;
				PositionChanged = true;
			}
		}catch(ArrayIndexOutOfBoundsException e){
			
		}

		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.CharacterX+","+view.AniPanel.CharacterY+")");
			//Checking for left collision
			try{
			if(view.AniPanel.CharacterX > 0 && view.AniPanel.Map[(int) (view.AniPanel.CharacterX - step)][(int) (view.AniPanel.CharacterY + 1)] == 'a' && view.AniPanel.Map[(int) (view.AniPanel.CharacterX - step)][(int) (view.AniPanel.CharacterY)] == 'a'){
				double newX = view.AniPanel.CharacterX - step;
				view.AniPanel.CharacterX = newX;
				view.AniPanel.dblViewportX = view.AniPanel.dblViewportX - 6;
				PositionChanged = true;
			}
		}catch(ArrayIndexOutOfBoundsException e){

		}

		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT ){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.CharacterX+","+view.AniPanel.CharacterY+")");
			//Checking for right collision
			try{
			if(view.AniPanel.CharacterX < view.AniPanel.MapWidth && view.AniPanel.Map[(int) (view.AniPanel.CharacterX + 1 + step)][(int) (view.AniPanel.CharacterY + 1)] == 'a' && view.AniPanel.Map[(int) (view.AniPanel.CharacterX + 1 + step)][(int) (view.AniPanel.CharacterY)] == 'a'){
				double newX = view.AniPanel.CharacterX + step;
				view.AniPanel.CharacterX = newX;
				view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + 6;
				PositionChanged = true;
			}
		}catch(ArrayIndexOutOfBoundsException e){

		}
		}else{
			System.out.println("Invalid key");
		}

		//sending position to opponent
		if(PositionChanged){
			ssm.sendText("position,"+view.AniPanel.CharacterX+","+view.AniPanel.CharacterY);
		}

		repaint();
	}

	

	public void keyReleased(KeyEvent e) {
        // Optional: Handle key release events
    }

    public void keyTyped(KeyEvent e) {
        // Optional: Handle key typed events
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
			view.AniPanel.loadMap(1);
			ssm.sendText("Map,1");

			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		}else if(evt.getSource() == view.Map2Button){
			view.AniPanel.loadMap(2);
			ssm.sendText("Map,2");

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
			view.AniPanel.loadCharacter(1);
			ssm.sendText("characterChosen,1");


			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			view.AniPanel.addKeyListener(this);
			
			ssm.sendText("connection,"+strUsername);

			intPlayersReady += 1;

			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			
			checkPlay();
		}else if(evt.getSource() == view.Character2Button){
			if(blnHost){
				intHostCharacter = 2;
				System.out.println("Host Character: El Primo");
			}else{
				intClientCharacter = 2;
				System.out.println("Client Character: El Primo");
			}
			view.AniPanel.loadCharacter(2);
			ssm.sendText("characterChosen,2");

			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			view.AniPanel.addKeyListener(this);

			ssm.sendText("connection,"+strUsername);

			intPlayersReady += 1;

			view.ChatArea.append(intPlayersReady + " joined\n");
			view.ChatArea.append(strUsername + " has connected\n");
			
			checkPlay();
		//Text input Chat
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
		//Timer
		}else if(evt.getSource() == theTimer){
			//System.out.println("Timer is running");
			view.AniPanel.repaint();
			
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

				checkPlay();
			//Getting position updates of the opponent
			}else if(ssmMessage[0].equals("position")){
				view.AniPanel.OpponentX = Double.parseDouble(ssmMessage[1]);
				view.AniPanel.OpponentY = Double.parseDouble(ssmMessage[2]);
			//loading the proper map for both players 
			}else if(ssmMessage[0].equals("Map")){
				view.AniPanel.loadMap(Integer.parseInt(ssmMessage[1]));
			//loading the proper character for both players
			}else if(ssmMessage[0].equals("characterChosen")){
				view.AniPanel.loadOpponent(Integer.parseInt(ssmMessage[1]));
			}else{
				System.out.println("Invalid SSM message");
			}
		} else if (evt.getSource() == view.PlayBackButton){
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
			intPlayersReady--;
			if (blnHost == true){
				ssm.sendText("chat,[ Server ], "+strHostUsername+" left\n");
				view.ChatArea.append("[ Server ]: "+strHostUsername+" left\n");
			} else if (blnHost == false){
				ssm.sendText("chat,[ Server ], "+strClientUsername+" left\n");
				view.ChatArea.append("[ Server ]: "+strClientUsername+" left\n");
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//Constructor
	public SBSRModelControl(SBSRViewTest view){
		this.view = view;

		

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
		view.ChatTextInput.addKeyListener(this);
		view.PlayBackButton.addActionListener(this);
	}

	//Main program
	public static void main(String[] args){
		SBSRViewTest view = new SBSRViewTest();
		new SBSRModelControl(view);
	}
	
}
