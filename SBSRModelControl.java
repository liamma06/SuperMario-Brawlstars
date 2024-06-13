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
	public String strConnectionResult;
	public String strHostUsername;
	public String strClientUsername;
	public String strIp;
	public String strPort;
	public String strUsername;
	public String strResult;
	public boolean blnHost = false;
	public boolean blnjump = false;
	public int intNumPlayers = 0;
	public int intJumpCooldown = 0;

	//Chacter selection
	public int intHostCharacter = 0;
	public int intClientCharacter = 0;

	//Players entered
	public boolean blnHostReady = false;
	public boolean blnClientReady = false;

	//Play
	public int intPlayersReady = 0;

	//splitting ssm messages -> mode(chat/charcter/play/game/connection),user(host/client),action(message/game input),xcord,ycord
	String[] ssmMessage;

	SBSRViewTest view;
	SuperSocketMaster ssm;

	//AnimationPanel
	public Timer theTimer = new Timer(1000/60,this);
	public double dblCharacterDefX = 0;
	public double dblCharacterDefY = 0;


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
			blnHostReady = true;
			blnClientReady = true;
			System.out.println("Both players are ready");
		}
	}
	
	
	
	
	//Player movement 
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
		if(evt.getKeyCode() == KeyEvent.VK_SPACE){
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
				System.out.println("Key pressed: ("+view.AniPanel.dblCharacterX+","+view.AniPanel.dblCharacterY+")");
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
	public void keyTyped(KeyEvent evt){
		
		//return focus to animation panel when enter is pressed 
		if(evt.getSource() == view.ChatTextInput && evt.getKeyCode() == KeyEvent.VK_ENTER){
			view.AniPanel.requestFocusInWindow();
			return;
		}
	}
	
	
	public void keyReleased(KeyEvent evt) {
		/*
		//Checking if both players have entered the game
		if (!blnHostReady || !blnClientReady) {
            System.out.println("Both players are not ready yet.");
            return;
        }
        */
		
		//checking if key is released
		
		if (evt.getKeyCode() == KeyEvent.VK_SPACE){
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

  
	//Overridding action listener
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

			intPlayersReady++;

			view.ChatArea.append("[ Server ]: "+strUsername + " has connected\n");
			view.ChatArea.append("[ Server ]: "+intPlayersReady + " players connected\n");
			checkPlay();
			theTimer.restart();
			intJumpCooldown = 0;
			blnjump = false;
			
		}else if(evt.getSource() == view.Character2Button){
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
		//Timer
		}else if(evt.getSource() == theTimer){
			
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
				//Kill character
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
			view.AniPanel.intEnemyX += 1;
			if(((view.AniPanel.intEnemyX-view.AniPanel.dblViewportX-36)<(view.AniPanel.dblCharacterX-view.AniPanel.dblViewportX))&&((view.AniPanel.intEnemyX-view.AniPanel.dblViewportX+36)>(view.AniPanel.dblCharacterX-view.AniPanel.dblViewportX))){
				//view.AniPanel.dblCharacterY =0;
			}
			repaint();
			
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

	//Constructor  **************************************************************************************************************
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
