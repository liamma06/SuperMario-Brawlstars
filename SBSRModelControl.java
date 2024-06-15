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
	public int intRaceTimer = 0;

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

	/**Character deflection in X */
	public double dblCharacterDefX = 0;

	/**Character deflection in Y */
	public double dblCharacterDefY = 0;

	//Timers are assigned ActionListeners with (this). 


	//Methods **************************************************************************************************************

	//setting up connection

	/**Method used to host and connect client to host*/
	public String connect(String ipField, String portField, String UsernameField){

		//If IP and Port Field are left blank print connection result
		if(ipField.equals("") && portField.equals("")){
			strConnectionResult = "Enter a port number and/or IP Address\n";
		}else if(ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			//If username and IP field is not entered
			strConnectionResult = "Enter Username";
		}else if(ipField.equals("") && !portField.equals("") && !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in server mode\n");
			try{
				ssm = new SuperSocketMaster(Integer.parseInt(portField),this);
				ssm.connect();
				//Sets host username to username entered
				strHostUsername = UsernameField;
				System.out.println("Host: "+strHostUsername);
				strConnectionResult = "(HOST) Your IP:" + ssm.getMyAddress();
				//You are host
				blnHost = true;
				//Enables PlayBackButton
				view.PlayBackButton.setEnabled(true);
				//Number of players = 1
				intNumPlayers = 1;
				//Can't change username
				view.UsernameField.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		}else if(!ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			//If username is blank
			strConnectionResult = "Enter Username";
		}else if(!ipField.equals("") && !portField.equals("")&& !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in client  mode\n");
			try{
				ssm = new SuperSocketMaster(ipField,Integer.parseInt(portField),this);
				ssm.connect();
				//Sets client username to username entered
				strClientUsername = UsernameField;
				System.out.println("Client: " +strClientUsername);
				strConnectionResult = "(Client) Connected to: " + ipField;
				//You are not the nost
				blnHost = false;
				//PlayBackButton is not enabled
				view.PlayBackButton.setEnabled(false);
				//Number of players increase by one
				intNumPlayers +=1;
				//Username field is disabled
				view.UsernameField.setEnabled(false);
			}catch(NumberFormatException e){
				strConnectionResult = "Invalid Port Number";
			}
		}else if(!ipField.equals("") && portField.equals("")){
			//If IP is entered but port is not
			strConnectionResult.equals("Need a portnumber or port/ip \n");
		}
		//Returns connectionResult
		return strConnectionResult;
	}

	// method when someone died

	/**Method to run when player dies */
	public void playerDied(String playerUsername){
		//send message to chat
		intRaceTimer = 0;
		ssm.sendText("server,death,"+playerUsername);
		//Says who dies in the chat
		view.ChatArea.append("[ Server ]: "+playerUsername + " has died\n");
		//Stop the timer
		theTimer.stop();
		RaceTimer.stop();
		//Makes left side show Death panel
		view.PlaySplitPane.setLeftComponent(view.DeathPanel);
		//Sets divider location at 720
		view.PlaySplitPane.setDividerLocation(720);

		view.theframe.revalidate();	
	}

	// method when someone reached the end

	/**Method to run when player reaches the end */
	public void playerReachedEnd(String playerUsername){
		//send message to chat
		ssm.sendText("server,win,"+playerUsername);
		view.ChatArea.append("[ Server ]: "+playerUsername + " has reached the end in "+intRaceTimer+" s\n");
		theTimer.stop();
		RaceTimer.stop();
		//Says who wins in the chat
		view.ChatArea.append("[ Server ]: "+playerUsername + " has reached the end\n");
		//stops the timer
		theTimer.stop();
		//Set left side of split pane to win panel
		view.PlaySplitPane.setLeftComponent(view.WinPanel);
		//set divider location at 720
		view.PlaySplitPane.setDividerLocation(720);

		view.theframe.revalidate();	
		intRaceTimer = 0;
	}

	//check play method
	/**Used to check if both players are ready so game can begin */
	public void checkPlay(){
		//If two players are ready
		if(intPlayersReady == 2){
			//The host is ready
			blnHostReady = true;
			//The client is ready
			blnClientReady = true;
			//Print out both players are ready
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

		
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			//focus on animation panel when enter is pressed
			view.AniPanel.requestFocusInWindow();
			return;
		}

		//Character movement 
		//If up key is pressed
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			//Areas in the map where jumping is allowed
			if (view.AniPanel.dblCharacterX < 3168 && view.AniPanel.dblCharacterY <=684){
				view.AniPanel.grabFocus();
				System.out.println("Key pressed JUMP: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				//Adds to the jump cooldown
				intJumpCooldown++;
				//Sets Characters deflection in y-axis to -36
				dblCharacterDefY = -36;
			}
		//If the character is between two blocks, then both blocks underneath/above must be air in order for the character to move vertically.
		//If left key is pressed
		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			//Area in the map where moving left is allowed
			if (view.AniPanel.dblCharacterX < 3168){
				view.AniPanel.grabFocus();
				//Character direction is left
				view.AniPanel.strCharacterDir = "left";
				System.out.println("Key pressed LEFT: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				//Character deflection in x-axis is -6
				dblCharacterDefX = -6;
			}
		//If right key is pressed
		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
			//Area in the map where moving left is allowed
			if (view.AniPanel.dblCharacterX < 3168){
				view.AniPanel.grabFocus();
				// Character direction is right
				view.AniPanel.strCharacterDir = "right";
				System.out.println("Key pressed RIGHT: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
				// Character deflection in x-axis is 6
				dblCharacterDefX = 6;
			}
		//if any other key pressed
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
		//If up key is released
		if (evt.getKeyCode() == KeyEvent.VK_UP){
			//You are not jumping
			blnjump = false;
			//Jump Cooldown is 0
			intJumpCooldown = 0;
			//Character deflection in y-axis is 0
			dblCharacterDefY = 0;
		//If left key is released	
		}else if(evt.getKeyCode() == KeyEvent.VK_LEFT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
			//character deflection in x-axis is 0
			dblCharacterDefX = 0;
			ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+",left");
			
		//If right key is released
		}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
			view.AniPanel.grabFocus();
			System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
			//character deflection in x-axis is 0
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
		//If Connect button on the menu panel is pressed
		if(evt.getSource() == view.ConnectMenuButton){
			//Enter connect panel
			view.theframe.setContentPane(view.ConnectPanel);
			view.theframe.revalidate();
		//back button
		
		//If back button on the play panel is pressed
		}else if(evt.getSource() == view.BackConnectButton){
			//Go back to menu panel
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();
			
		//If connect button is pressed in the connect panel
		}else if(evt.getSource() == view.ConnectButton){
			//Get entered IP
			strIp = view.ipField.getText();
			//Get entered port field
			strPort = view.portField.getText();
			//Get entered username
			strUsername = view.UsernameField.getText();
			//getting the status result from the connect method
			String strResult = connect(strIp, strPort, strUsername);
			//Set the text of the ConnectionStatusLabel
			view.ConnectionStatusLabel.setText(strResult);

		
		//If PlayMenuButton on menu panel is pressed
		}else if(evt.getSource() == view.PlayMenuButton){
			//If you are the host
			if(blnHost){
				//Go to map selection panel
				view.theframe.setContentPane(view.MapPanel);
				view.theframe.revalidate();
			//If you are not host or did not connect yet
			}else{
				System.out.println("You are not Host or have not connected yet");
			}
		
		//Map selection
		//If map button 1 is pressed
		}else if (evt.getSource() == view.Map1Button){
			//Runs the loadMap method in animation panel with an input of 1
			view.AniPanel.loadMap(1);
			ssm.sendText("Map,1");
			
			//Enters character selection panel
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		//If map button 2 is pressed 
		}else if(evt.getSource() == view.Map2Button){
			//Runs the loadMap method in animation panel with an input of 2
			view.AniPanel.loadMap(2);
			ssm.sendText("Map,2");
			
			//Enters character selection panel
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		//Character selection
		//If Button is pressed
		}else if(evt.getSource()==view.HelpMenuButton){
			//Runs the loadMap method in animation panel with an input of 3
			view.AniPanel.loadMap(3);
			ssm.sendText("Map,3");
			
			//Enters character selection panel
			view.theframe.setContentPane(view.CharacterPanel);
			view.theframe.revalidate();
			ssm.sendText("character");
		//If the first character button is pressed
		}else if((evt.getSource() == view.Character1Button)){

			intRaceTimer = 0;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTimer)+" s elapsed");

			//If you are the host
			if(blnHost){
				//Host character = 1
				intHostCharacter = 1;
				//Print out that host character is colt
				System.out.println("Host Character: Colt");
			//If you are not the host
			}else {
				// Client character = 1
				intClientCharacter = 1;
				//Print out that client character is colt
				System.out.println("Client Character: Colt");
			}
			//run loadCharacter method in animation panel with an input of 1
			view.AniPanel.loadCharacter(1);
			ssm.sendText("characterChosen,1");

			//Sets screen to the PlaySplitPane
			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			//Listens to keyboard input
			view.AniPanel.addKeyListener(this);
			
			ssm.sendText("connection,"+strUsername);
			//Adds one to the number of players ready
			intPlayersReady++;
			//Says name of player connected in the chat
			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			//Says number of players ready in the chat
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			//Runs checkPlay method above
			checkPlay();
			//The timer starts again
			theTimer.restart();
			RaceTimer.restart();

			//Jump cooldown =0

			intJumpCooldown = 0;
			//You are not jumping
			blnjump = false;

			
			
		}else if((evt.getSource() == view.Character2Button)){
			intRaceTimer = 0;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTimer)+" s elapsed");
			

		
		//If the second character button is pressed
		}else if((evt.getSource() == view.Character2Button)){
			//If you are the host

			if(blnHost){
				//Host character is = 2
				intHostCharacter = 2;
				//Print out that host character is dynamike
				System.out.println("Host Character: Dynamike");
			//If you are the client
			}else{
				//Client character is = 2
				intClientCharacter = 2;
				//Print out that client character is dynamike
				System.out.println("Client Character: Dynamike");
			}
			//run loadCharacter method in animation panel with an input of 2
			view.AniPanel.loadCharacter(2);
			ssm.sendText("characterChosen,2");

			//Set screen to the PlaySplitPane screen
			view.theframe.setContentPane(view.PlaySplitPane);
			view.theframe.revalidate();
			view.AniPanel.requestFocusInWindow();
        	view.AniPanel.setFocusable(true);
			//Listens to the keyboard
			view.AniPanel.addKeyListener(this);

			ssm.sendText("connection,"+strUsername);
			//Increase the number of players ready
			intPlayersReady++;
			//Says name of player connected in chat
			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			//Says number of players ready in chat
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			//Run checkPlay method above
			checkPlay();
			//The timer starts again
			theTimer.restart();

			RaceTimer.restart();
			//Jump cooldown = 0
			intJumpCooldown = 0;
			//You are not jumping
			blnjump = false;
			
			
		//Text input Chat
		//If the event being triggered is the chat input
		}else if(evt.getSource() == view.ChatTextInput){
			//If you are the host
			if(blnHost == true){
				ssm.sendText("chat,"+strHostUsername+","+view.ChatTextInput.getText());
				//Puts the sent message in the chat area and includes host username
				view.ChatArea.append(strHostUsername+": "+view.ChatTextInput.getText()+ "\n");
				//Sets the text in the ChatTextInput to no text
				view.ChatTextInput.setText("");
			//If you are not the host
			}else if(blnHost == false){
				ssm.sendText("chat,"+strClientUsername+","+view.ChatTextInput.getText());
				//Puts the sent message in the chat area and includes client username 
				view.ChatArea.append(strClientUsername+": "+view.ChatTextInput.getText()+ "\n");
				//Sets the text in the ChatTextInput to no text
				view.ChatTextInput.setText("");
			}
		//Animation Timer
		//If the event being triggere is the timer
		}else if(evt.getSource() == theTimer){
			//You are not jumping
			blnjump = false;
			//If you can jump
			if(view.AniPanel.dblCharacterY >= 36 && intJumpCooldown < 4 && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY - 36)/36))] == 'a' && view.AniPanel.chrMap[(int) (Math.ceil((view.AniPanel.dblCharacterX)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY - 36)/36))] == 'a' && (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY+36)/36))] != 'a' || view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY+36)/36))] != 'a')){
				//Changing characters y value
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + dblCharacterDefY;
				//You are jumping
				blnjump = true;
			//If jump cooldown is greater than 3
			} else if (intJumpCooldown > 3){
				//You are not jumping
				blnjump = false;
			}
			
			if (this.ssm != null){
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
			}
			
			if(view.AniPanel.chrMap != null && blnjump == false && (view.AniPanel.dblCharacterY) < view.AniPanel.intMapHeight-6 && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY + 6)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY + 6)/36))] == 'a'){
				//Add 6 to the characters y
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + 6;
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
			}
				
			//Bypass the border when the character falls out of the map to incite death 
			//If character y is greater than or equal to 684 and less than 720	
			if (view.AniPanel.dblCharacterY >= 684 && view.AniPanel.dblCharacterY < 720){
				//Add 6 to the characters y
				view.AniPanel.dblCharacterY = view.AniPanel.dblCharacterY + 6;
				ssm.sendText("position,"+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY);
				//If character falls off the map
			} else if (view.AniPanel.dblCharacterY >= 720){
				//Character health =0
				view.AniPanel.intCharacterHP = 0;
				//run playerDied method above
				playerDied(strUsername);
				
			}

			//checking of bottom of pole is reached
			if(((int) view.AniPanel.dblCharacterX == 3168 && (int) view.AniPanel.dblCharacterY == 612)||((int) view.AniPanel.dblCharacterX == 3168 && (int) view.AniPanel.dblCharacterY == 348) ){
				System.out.println("end is reached");
				//runs playerReachedEnd method above
				playerReachedEnd(strUsername);
			}
			//if character is facing right
			if (view.AniPanel.strCharacterDir == "right"){
				if (view.AniPanel.dblCharacterX < 3168 && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a'){
					//If jumping
					if (blnjump == true){
						if (view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY-6)/36))] == 'a'){
							//Change character x
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							//Change viewport x
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else if (blnjump != true && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))]=='a'){
						if (view.AniPanel.chrMap[(int)(Math.ceil((view.AniPanel.dblCharacterX + 6)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))] == 'a'){
							//Change character x
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							//Change viewport x
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else {
						//Change character x
						view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
						//Change viewport x
						view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
					}
				} 
			//if character is facing left
			} else if (view.AniPanel.strCharacterDir == "left"){
				if (view.AniPanel.dblCharacterX > 324 && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a' && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int) (Math.floor((view.AniPanel.dblCharacterY)/36))] == 'a'){
					//If jumping
					if (blnjump == true){
						if (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.floor((view.AniPanel.dblCharacterY-6)/36))] == 'a'){
							//Change character x
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							//Change viewport x
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						} 
					} else if (blnjump != true && view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))]=='a'){
						if (view.AniPanel.chrMap[(int)(Math.floor((view.AniPanel.dblCharacterX - 6)/36))][(int)(Math.ceil((view.AniPanel.dblCharacterY+6)/36))] == 'a'){
							//Change character x
							view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
							//Change viewport x
							view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
						}
					} else {
						//Change character x
						view.AniPanel.dblCharacterX = view.AniPanel.dblCharacterX + dblCharacterDefX;
						//Change viewport x
						view.AniPanel.dblViewportX = view.AniPanel.dblViewportX + dblCharacterDefX;
					}
				}
			} 
			
			repaint();
			
			
		//Detecting Second-Based Signals from Race Timer and Counting them
		} else if (evt.getSource() == RaceTimer){
			intRaceTimer++;
			view.RaceTimerLabel.setText(String.valueOf(intRaceTimer)+" s elapsed");
		
		//Detecting SSM 
		}else if(evt.getSource() == ssm){
			ssmMessage = ssm.readText().split(",");
			//sending both host and client to the play screen
			if(ssmMessage[0].equals("character")){
				//Go to character selection panel
				view.theframe.setContentPane(view.CharacterPanel);
				view.theframe.revalidate();
				System.out.println("Host has selected map, character selection");
			//Getting chat responses
			}else if(ssmMessage[0].equals("chat")){
				if(ssmMessage[1].equals("server")){
					//Put who died in the chat
					view.ChatArea.append("[ Server ]: "+ssmMessage[3] + " has died\n");
				}else{
					view.ChatArea.append(ssmMessage[1] + ": " + ssmMessage[2] + "\n");
				}
			//reseting game for both players
			}else if(ssmMessage[0].equals("reset")){
				//Make left side contain animation panel
				view.PlaySplitPane.setLeftComponent(view.AniPanel);
				//Set the divider at 720
				view.PlaySplitPane.setDividerLocation(720);
				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();

				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();
			//getting server responses for player death and win
			}else if(ssmMessage[0].equals("server")){
				if(ssmMessage[1].equals("death")){
					//Write in the chat the player that died
					view.ChatArea.append("[ Server ]: "+ssmMessage[2] + " has died\n");
				}else if(ssmMessage[1].equals("win")){
					//Write in the chat area if player has reached the end
					view.ChatArea.append("[ Server ]: "+ssmMessage[2] + " has reached the end\n");
					//Stop the timer
					theTimer.stop();
					//Make left side show death panel
					view.PlaySplitPane.setLeftComponent(view.DeathPanel);
					//set divider at location 720
					view.PlaySplitPane.setDividerLocation(720);
					view.theframe.revalidate();	
				}
			//Getting connection responses
			}else if(ssmMessage[0].equals("connection")){
				//add 1 to the number of ready players
				intPlayersReady += 1;
				//Write in the chat that the player has connected
				view.ChatArea.append(ssmMessage[1] + " has connected\n");
				//Print out the number of players ready
				System.out.println("Players Ready: "+intPlayersReady);
				//run checkPlay method above
				checkPlay();
			//Getting position updates of the opponent
			}else if(ssmMessage[0].equals("position")){
				//Updating opponents x-position
				view.AniPanel.dblOpponentX = Double.parseDouble(ssmMessage[1]);
				//Updating the opponents y-position
				view.AniPanel.dblOpponentY = Double.parseDouble(ssmMessage[2]);
				if(ssmMessage.length == 4){
					//Updating opponents direction
					view.AniPanel.strOpponentDir = ssmMessage[3];
				}else{
					//The opponent direction stays the same
					view.AniPanel.strOpponentDir = view.AniPanel.strOpponentDir;
				}
				
			
			}else if(ssmMessage[0].equals("Map")){
				//loads the map
				view.AniPanel.loadMap(Integer.parseInt(ssmMessage[1]));
			
			}else if(ssmMessage[0].equals("characterChosen")){
				//load the opponent
				view.AniPanel.loadOpponent(Integer.parseInt(ssmMessage[1]));
			}else{
				System.out.println("Invalid SSM message");
			}
		//If the PlayBackButton is pressed
		} else if (evt.getSource() == view.PlayBackButton){
			view.PlaySplitPane.setLeftComponent(view.AniPanel);
			view.PlaySplitPane.setDividerLocation(720);
			view.theframe.setContentPane(view.MenuPanel);
			view.theframe.revalidate();

			ssm.sendText("reset");

			intPlayersReady--;

			ssm.sendText("chat,[ Server ], "+strHostUsername+" left\n");
			view.ChatArea.append("[ Server ]: "+strHostUsername+" left\n");
			intRaceTimer = 0;

				//Set left part of the screen to animation panel
				view.PlaySplitPane.setLeftComponent(view.AniPanel);
				//Put a divider at location 720
				view.PlaySplitPane.setDividerLocation(720);
				//Go to the menu panel
				view.theframe.setContentPane(view.MenuPanel);
				view.theframe.revalidate();

				ssm.sendText("reset");
				//Decrease the number of players ready
				intPlayersReady--;

				ssm.sendText("chat,[ Server ], "+strHostUsername+" left\n");
				//Write who left in the chat
				view.ChatArea.append("[ Server ]: "+strHostUsername+" left\n");
	

		}
	}

	//Constructor  **************************************************************************************************************
	/**Adding action listeners from the view */
	public SBSRModelControl(SBSRViewTest view){
		this.view = view;

		// Adding Event listeners

		//Main Menu
		//Listen to the play menu button
		view.PlayMenuButton.addActionListener(this);
		//Listen to the connect menu button
		view.ConnectMenuButton.addActionListener(this);

		view.HelpMenuButton.addActionListener(this);
		
		//Connect Page
		//Listen to the connect button
		view.ConnectButton.addActionListener(this);
		//Listen to the back connect button
		view.BackConnectButton.addActionListener(this);

		//Map page
		//Listen to the Map 1 button
		view.Map1Button.addActionListener(this);
		//Listen to the Map 2 button
		view.Map2Button.addActionListener(this);

		//Help page
		//view.HelpMenuButton.addActionListener(this);

		//Character page
		//Listen to the character 1 button
		view.Character1Button.addActionListener(this);
		//Listen to the character 2 button
		view.Character2Button.addActionListener(this);

		//Chat
		//Listen to the chat input
		view.ChatTextInput.addActionListener(this);
		view.ChatTextInput.addKeyListener(this);

		//Listen to the play back button
		view.PlayBackButton.addActionListener(this);
		
	}

	//Main program
	public static void main(String[] args){
		SBSRViewTest view = new SBSRViewTest();
		new SBSRModelControl(view);
	}
	
}
