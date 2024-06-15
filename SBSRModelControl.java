//SuperBrawlStarsRun SBSR ModelControl (Logic, Computation, Network, Algorithm, and Data File Processing)
//Programmers: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: Monday, June 10, 2024
//Version Number: 2.0 Beta

import java.awt.*;
import java.io.*;
import java.util.Map;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SBSRModelControl extends JPanel implements ActionListener, KeyListener{
	//Properties **************************************************************************************************************

	//Connection
	/**Tells user things like host/client status and errors in the connection process when connect buton is pressed */
	public String strConnectionResult;

	/**The name the host enters in connection process*/
	public String strHostUsername;

	/**The name the client enters in connection process */
	public String strClientUsername;

	/**The IP of the computer */
	public String strIp;

	/**Port of the computer */
	public String strPort;

	/**The entered username */
	public String strUsername;

	/**The result of the connection method */
	public String strResult;

	/**Used to see who is host for extra privileges */
	public boolean blnHost = false;

	/**Used to see if jumping or not */
	public boolean blnjump = false;

	/**Used to count the number of players */
	public int intNumPlayers = 0;

	/**Cooldown for jump */
	public int intJumpCooldown = 0;

	//Chacter selection
	/**Sets which character the host is */
	public int intHostCharacter = 0;

	/**Sets which character the client is */
	public int intClientCharacter = 0;


	//Players entered
	/**Checks to see if the host is ready */
	public boolean blnHostReady = false;

	/**Checks to see if the client is ready */
	public boolean blnClientReady = false;

	//Play
	/**The number of players ready */
	public int intPlayersReady = 0;

	/**Timer to count the seconds it takes for players to reach end of map */
	public int intRaceTime = 0;

	//splitting ssm messages -> mode(chat/charcter/play/game/connection),user(host/client),action(message/game input),xcord,ycord
	
	/**Messages you send over SuperSocketMaster */
	String[] ssmMessage;

	/**Calls on SBSRViewTest */
	SBSRViewTest view;

	/**Calls on SuperSocketMaster */
	SuperSocketMaster ssm;

	//AnimationPanel

	/**Timer that runs 60fps */
	public Timer theTimer = new Timer(1000/60,this);

	public Timer RaceTimer = new Timer (1000, this);

	/**Character displacement in X */
	public double dblCharacterDefX = 0;

	/**Character displacement in Y */
	public double dblCharacterDefY = 0;
	
	/**Integer value for the y-coordinate component denoting the bottom point of the pole */
	public int intEndY = 0;


	//Methods **************************************************************************************************************

	//setting up connection

	/**Method used to host and connect client to host*/
	public String connect(String ipField, String portField, String UsernameField){

		//If IP and Port Fields are left blank print connection result
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
				view.PlayBackButton.setEnabled(true);
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
				view.PlayBackButton.setEnabled(false);
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

	// method when someone died
	/**Method to run when player dies */
	public void playerDied(String playerUsername){
		//send message to chat
		intRaceTime = 0;
		ssm.sendText("server,death,"+playerUsername);
		view.ChatArea.append("[ Server ]: "+playerUsername + " has died\n");
		theTimer.stop();
		RaceTimer.stop();
		view.PlaySplitPane.setLeftComponent(view.DeathPanel);
		view.PlaySplitPane.setDividerLocation(720);
		view.theframe.revalidate();	
	}

	// method when someone reached the end
	/**Method to run when player reaches the end */
	public void playerReachedEnd(String playerUsername){
		//send message to chat
		ssm.sendText("server,win,"+playerUsername);
		view.ChatArea.append("[ Server ]: "+playerUsername + " has reached the end in "+intRaceTime+" s\n");
		theTimer.stop();
		RaceTimer.stop();
		view.PlaySplitPane.setLeftComponent(view.WinPanel);
		view.PlaySplitPane.setDividerLocation(720);
		view.theframe.revalidate();	
		intRaceTime = 0;
	}

	//check play method
	/**Used to check if both players are ready so game can begin */
	public void checkPlay(){
		if(intPlayersReady == 2){
			blnHostReady = true;
			blnClientReady = true;
			System.out.println("Both players are ready");
		}
	}
	
	//Player movement 

	/**Checks which keys are pressed and excecutes code such as movement depending on which key is pressed */
	public void keyPressed(KeyEvent evt){
		/*
		//checking if both players have entered the game
		if(!blnHostReady || !blnClientReady){
			System.out.println("Both players have not entered the game yet");
			return;
		}
		*/

		//checking if key is pressed

		//return focus to animation panel when enter is pressed 
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			view.AniPanel.requestFocusInWindow();
			return;
		}

		//Character movement 
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			if (view.AniPanel.dblCharacterX < 3168 && view.AniPanel.dblCharacterY <=684){
				view.AniPanel.grabFocus();
				System.out.println("Key pressed JUMP: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				intJumpCooldown++;
				dblCharacterDefY = -36;
			}
		//If the character is between two blocks, then both blocks underneath/above must be air in order for the character to move vertically.

		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			if (view.AniPanel.dblCharacterX < 3168){
				view.AniPanel.grabFocus();
				view.AniPanel.strCharacterDir = "left";
				System.out.println("Key pressed LEFT: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				dblCharacterDefX = -6;
			}
		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
			if (view.AniPanel.dblCharacterX < 3168){
				view.AniPanel.grabFocus();
				view.AniPanel.strCharacterDir = "right";
				System.out.println("Key pressed RIGHT: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				dblCharacterDefX = 6;
			}
		}else{
			System.out.println("Invalid key");
		}
	}
	/**Checks which key is typed and focuses back to animation panel when enter is typed */
	public void keyTyped(KeyEvent evt){
		
		//return focus to animation panel when enter is pressed 
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			view.AniPanel.requestFocusInWindow();
			return;
		}
	}
	
	/**Checks keys released and stops player movement */
	public void keyReleased(KeyEvent evt) {
		/*
		//Checking if both players have entered the game
		if (!blnHostReady || !blnClientReady) {
            System.out.println("Both players are not ready yet.");
            return;
        }
        */
		
		//checking if key is released
		
		if (evt.getKeyCode() == KeyEvent.VK_UP){
			blnjump = false;
			intJumpCooldown = 0;
			dblCharacterDefY = 0;
			
		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
			dblCharacterDefX = 0;
			ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+",left");
			

		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
			dblCharacterDefX = 0;
			ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+",right");
	
		}else{
			System.out.println("Invalid key");
		}

    }

  
	//Overriding action listener
	/**Overrides  action listener and check things like button presses*/
	public void actionPerformed(ActionEvent evt){
		//Connect menu button
		if(evt.getSource() == view.ConnectMenuButton){
			view.theframe.setContentPane(view.ConnectPanel);
			view.theframe.revalidate();
		//back button
		
		//If back button on the play panel is pressed
		}else if(evt.getSource() == view.BackConnectButton){
			//Go back to menu panel
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
			
		//If connect button is pressed in the connect panel
		}else if(evt.getSource() == view.BackConnectButton){
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
	
		}else if(evt.getSource() == view.ConnectButton){
			strIp = view.ipField.getText();
			strPort = view.portField.getText();
			strUsername = view.UsernameField.getText();
			//getting the status result from the connect method
			String strResult = connect(strIp, strPort, strUsername);
			view.ConnectionStatusLabel.setText(strResult);

		
		//If PlayMenuButton on menu panel is pressed

		//Play menu button
		}else if(evt.getSource() == view.PlayMenuButton){
			if(blnHost){
				view.theframe.setContentPane(view.MapPanel);
				view.theframe.revalidate();
			} else{
				System.out.println("You are not Host or have not connected yet");
			}
		
		//Map selection
		//Level 1
		}else if (evt.getSource() == view.Map1Button){
			view.AniPanel.loadMap(1);
			ssm.sendText("Map,1");
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
			intEndY = 612;
		//Level 2
		}else if(evt.getSource() == view.Map2Button){
			view.AniPanel.loadMap(2);
			ssm.sendText("Map,2");
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
			intEndY = 504;
		
		//Tutorial (Demo) Map
		}else if(evt.getSource()==view.HelpMenuButton){
			view.AniPanel.loadMap(3);
			ssm.sendText("Map,3");
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		//Character selection
		}else if((evt.getSource() == view.Character1Button)){
			intRaceTime = 0;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTime)+" s elapsed");
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

			intPlayersReady++;

			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			checkPlay();
			theTimer.restart();
			RaceTimer.restart();
			intJumpCooldown = 0;
			blnjump = false;
			
			
		}else if((evt.getSource() == view.Character2Button)){
			intRaceTime = 0;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTime)+" s elapsed");
			
			if(blnHost){
				intHostCharacter = 2;
				System.out.println("Host Character: Dynamike");
			}else{
				intClientCharacter = 2;
				System.out.println("Client Character: Dynamike");
			}
			view.AniPanel.loadCharacter(2);
			ssm.sendText("characterChosen,2");

			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			view.AniPanel.addKeyListener(this);

			ssm.sendText("connection,"+strUsername);

			intPlayersReady++;

			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			checkPlay();
			theTimer.restart();
			RaceTimer.restart();
			intJumpCooldown = 0;
			blnjump = false;
			
			
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
		//Animation Timer
		}else if(evt.getSource() == theTimer){
			System.out.println(intEndY);
			blnjump = false;
			if(view.AniPanel.dblCharacterY >= 36 && intJumpCooldown < 4 && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY - 36)/36))] == 'a' && view.AniPanel.chrMap[(int) (Math.ceil((view.AniPanel.dblCharacterX)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY - 36)/36))] == 'a' && (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY+36)/36))] != 'a' || view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY+36)/36))] != 'a')){
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + dblCharacterDefY;
				blnjump = true;
			} else if (intJumpCooldown > 3){
				blnjump = false;
			}
			
			if (this.ssm != null){
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
			}
			
			if(view.AniPanel.chrMap != null && blnjump == false && (view.AniPanel.dblCharacterY) < view.AniPanel.intMapHeight-6 && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY + 6)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY + 6)/36))] == 'a'){
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + 6;
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
			}
				
			//Bypass the border when the character falls out of the map to incite death 
				
			if (view.AniPanel.dblCharacterY >= 684 && view.AniPanel.dblCharacterY < 720){
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + 6;
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
			} else if (view.AniPanel.dblCharacterY >= 720){
				view.AniPanel.intCharacterHP = 0;
				playerDied(strUsername);
				//Kill character
			}

			//checking of bottom of pole is reached
			if(((int) view.AniPanel.dblCharacterX) == 3168 && ((int) view.AniPanel.dblCharacterY) == intEndY){
				System.out.println("end is reached");
				playerReachedEnd(strUsername);
			}

			if (view.AniPanel.strCharacterDir == "right"){
				if (view.AniPanel.dblCharacterX < 3168 && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a'){
					if (blnjump == true){
						if (view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY-6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else if (blnjump != true && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))]=='a'){
						if (view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else {
						view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
						view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
					}
				} 
			} else if (view.AniPanel.strCharacterDir == "left"){
				if (view.AniPanel.dblCharacterX > 324 && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a'){
					if (blnjump == true){
						if (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY-6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						} 
					} else if (blnjump != true && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))]=='a'){
						if (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))] == 'a'){
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else {
						view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
						view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
					}
				}
			} 
			
			repaint();
			
			
		//Detecting Second-Based Signals from Race Timer and Counting them
		} else if (evt.getSource() == RaceTimer){
			intRaceTime++;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTime)+" s elapsed");
		
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
				if(ssmMessage[1].equals("server")){
					view.ChatArea.append("[ Server ]: "+ssmMessage[3] + " has died\n");
				}else{
					view.ChatArea.append(ssmMessage[1] + ": " + ssmMessage[2] + "\n");
				}
			//reseting game for both players
			}else if(ssmMessage[0].equals("reset")){
				view.PlaySplitPane.setLeftComponent(view.AniPanel);
				view.PlaySplitPane.setDividerLocation(720);
				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();

				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();
			//getting server responses for player death and win
			}else if(ssmMessage[0].equals("server")){
				if(ssmMessage[1].equals("death")){
					view.ChatArea.append("[ Server ]: "+ssmMessage[2] + " has died\n");
				}else if(ssmMessage[1].equals("win")){
					view.ChatArea.append("[ Server ]: "+ssmMessage[2] + " has reached the end\n");
					theTimer.stop();
					view.PlaySplitPane.setLeftComponent(view.DeathPanel);
					view.PlaySplitPane.setDividerLocation(720);
					view.theframe.revalidate();	
				}
			//Getting connection responses
			}else if(ssmMessage[0].equals("connection")){
				intPlayersReady += 1;
				view.ChatArea.append(ssmMessage[1] + " has connected\n");
				System.out.println("Players Ready: "+intPlayersReady);
				checkPlay();
			//Getting position updates of the opponent
			}else if(ssmMessage[0].equals("position")){
				view.AniPanel.dblOpponentX = Double.parseDouble(ssmMessage[1]);
				view.AniPanel.dblOpponentY = Double.parseDouble(ssmMessage[2]);
				if(ssmMessage.length == 4){
					view.AniPanel.strOpponentDir = ssmMessage[3];
				}else{
					view.AniPanel.strOpponentDir = view.AniPanel.strOpponentDir;
				}
				
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
			view.PlaySplitPane.setLeftComponent(view.AniPanel);
			view.PlaySplitPane.setDividerLocation(720);
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();

			ssm.sendText("reset");

			intPlayersReady--;

			ssm.sendText("chat,[ Server ], "+strHostUsername+" left\n");
			view.ChatArea.append("[ Server ]: "+strHostUsername+" left\n");
			intRaceTime = 0;

		}
	}

	//Constructor  **************************************************************************************************************
	/**Adding action listeners from the view */
	public SBSRModelControl(SBSRViewTest view){
		this.view = view;

		// Adding Event listeners

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
		//view.HelpMenuButton.addActionListener(this);

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
