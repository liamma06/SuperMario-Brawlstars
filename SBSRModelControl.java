import java.io.*;
import java.awt.event.*;



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

	//splitting ssm messages -> mode(chat/charcter/play/game/connection),user(host/client),action(message/game input),xcord,ycord
	String[] ssmMessage;

	SBSRViewTest view;
	SuperSocketMaster ssm;
	
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
		}else if(evt.getSource() == view.Character1Button && blnHost){
			intHostCharacter = 1;
			System.out.println("Host Character: Colt");
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("connection,"+strHostUsername);
		}else if(evt.getSource() == view.Character2Button && blnHost){
			intHostCharacter= 2;
			System.out.println("Host Character: El Primo");
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("connection,"+strHostUsername);
		}else if(evt.getSource() == view.Character1Button){
			intClientCharacter = 1;
			System.out.println("Client Character: Colt");
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("connection,"+strClientUsername);
		}else if(evt.getSource() == view.Character2Button){
			intClientCharacter = 2;
			System.out.println("Client Character: El Primo");
			view.theframe.setContentPane(view.PlayPanel);
			view.theframe.revalidate();
			ssm.sendText("connection,"+strClientUsername);
		//Text input 
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
			}else if(ssmMessage[0].equals("connection")){
				intPlayersReady += 1;
				view.ChatArea.append(ssmMessage[1] + " has connected\n");
				System.out.println("Players Ready: "+intPlayersReady);
			}else{
				System.out.println("Invalid SSM message");
			}
		}
	}

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
	}

	//Main program
	public static void main(String[] args){
		SBSRViewTest view = new SBSRViewTest();
		new SBSRModelControl(view);
	}
	
}
