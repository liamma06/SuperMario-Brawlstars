//SuperBrawlStarsRun SBSR View (Graphical User Interfaces)
//Programmers: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: Thursday, June 6, 2024
//Version Number: 2.0 Beta

//Importing Java Swing and GUI toolkits for graphical setup
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.*;

//Defining
public class SBSRViewTest{

		//Properties
		
		//Main frame with title
    	public JFrame theframe = new JFrame("Super Mario Brawlstars");

    	//Main Menu
		
		//Main menu panel
    	public JPanel MenuPanel = new JPanel();
		
		//Play button
    	public JButton PlayMenuButton;
		
		//Connect menu button
		public JButton ConnectMenuButton;

		public JButton HelpMenuButton;

		//Connect screen
		
		//Connect panel where client tries to connect to host
		public JPanel ConnectPanel = new JPanel();
		
		//Where players enter IP address of host
		public JTextField ipField;

		//Where players enter the port to connect to
		public JTextField portField;

		//Connect button used to connect to a host or become one
		public JButton ConnectButton;

		//Back button to be used to go back from the connect panel to the menu panel
		public JButton BackConnectButton;

		//Tells you if you have successfully conected or not
		public JLabel ConnectionStatusLabel;

		//Used to label the username field
		public JLabel UsernameLabel;

		//Used to name the IP field
		public JLabel IPLabel;

		//Used to name the port field
		public JLabel PortLabel;

		//Space where player enters their username
		public JTextField UsernameField;

		//Back button on the split panel
		public JButton PlayBackButton;

		//Play(Chat screen)
		//Panel containing chat while you play
		public JPanel ChatPanel = new JPanel();
		public JTextArea ChatArea;
		public JScrollPane ChatScroll;
		public JTextField ChatTextInput;
		


		//Area that displays chat messages
		public JTextArea ChatArea;

		//Scroll pane for the chat
		public JScrollPane ChatScroll;

		//Text field where chat messages are inputted
		public JTextField ChatTextInput;
		public JLabel RaceTimerLabel;

		

		
		//Map Selection Screen
		//Map selection panel
		public JPanel MapPanel = new JPanel();
		//Used to name map 1
		public JLabel Map1Label;
		//Used to name map 2
		public JLabel Map2Label;
		//Button used to select map 1
		public JButton Map1Button;
		//Button used to select map 2
		public JButton Map2Button;

		//Character selection screen
		//Character selection panel
		public JPanel CharacterPanel = new JPanel();
		//Used to name character 1
		public JLabel Character1Label;
		//Used to name character 2
		public JLabel Character2Label;
		//Button used to select character 1
		public JButton Character1Button;
		//Button used to select character 2
		public JButton Character2Button;    

		//Help screen
		public JPanel HelpPanel = new JPanel();

		//Animation Panel
		AnimationPanelTest AniPanel = new AnimationPanelTest();

		//Split Pane for game and chat
		public JSplitPane PlaySplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		//Death panel
		public JPanel DeathPanel = new JPanel();
		//Label shown when dead
		public JLabel DeathLabel;

		//Win panel
		public JPanel WinPanel = new JPanel();
		//Label shown when won
		public JLabel WinLabel;

		//GUI Methods ~ See "SBSRModelControl.java"

		//Constructor
		public SBSRViewTest(){
        

		//Menu options
		//Set size of menu panel
		MenuPanel.setPreferredSize(new Dimension(1280,720));
		MenuPanel.setLayout(null);
        
		//Set play menu button text
		PlayMenuButton = new JButton("Play");
		//set play menu button size
		PlayMenuButton.setSize(300,60);
		//set play menu button location
		PlayMenuButton.setLocation(490,400);
		//add play menu button to menu panel
		MenuPanel.add(PlayMenuButton);

		//set connect menu button text
		ConnectMenuButton = new JButton("Connect");
		//set connect menu button size
		ConnectMenuButton.setSize(300,60);
		//set conncect menu button location
		ConnectMenuButton.setLocation(490,500);
		//add connect menu button to menu panel
		MenuPanel.add(ConnectMenuButton);

		HelpMenuButton = new JButton("Help");
		HelpMenuButton.setSize(300,60);
		HelpMenuButton.setLocation(490,600);
		MenuPanel.add(HelpMenuButton);

		//ConnectPanel
		ConnectPanel.setLayout(null);
		
		//set username label text
		UsernameLabel = new JLabel("Username:");
		//set username label size
		UsernameLabel.setSize(200,50);
		//set username label location
		UsernameLabel.setLocation(50,50);
		//set username label font
		UsernameLabel.setFont(new Font("Arial", Font.BOLD,30));
		//add username label to connect panel
		ConnectPanel.add(UsernameLabel);
		

		UsernameField = new JTextField();
		//set username field size
		UsernameField.setSize(250,50);
		//set username field location
		UsernameField.setLocation(210,50);
		//set username field font
		UsernameField.setFont(new Font("Arial", Font.PLAIN, 30));
		//add username field to connect panel
		ConnectPanel.add(UsernameField);
		
		//set ip label text
		IPLabel = new JLabel("IP:");
		//set ip label size
		IPLabel.setSize(200,50);
		//set ip label location
		IPLabel.setLocation(340,150);
		//set ip label font
		IPLabel.setFont(new Font("Arial", Font.BOLD,30));
		//add ip label to connect panel
		ConnectPanel.add(IPLabel);

		ipField = new JTextField();
		// set ip field size
		ipField.setSize(500,200);
		//set ip field location
		ipField.setLocation(100,200);
		//set ip field font
		ipField.setFont(new Font("Arial", Font.PLAIN, 40));
		//add ip field to connect panel
		ConnectPanel.add(ipField);

		//set port label text
		PortLabel = new JLabel("Port:");
		//set prot label size
		PortLabel.setSize(200,50);
		//set port label location
		PortLabel.setLocation(900,150);
		//set port label font
		PortLabel.setFont(new Font("Arial", Font.BOLD,30));
		//add port label to connect panel
		ConnectPanel.add(PortLabel);

		portField = new JTextField();
		//set port field size
		portField.setSize(500,200);
		//set port field location
		portField.setLocation(680,200);
		//set port field font
		portField.setFont(new Font("Arial", Font.PLAIN, 40));
		//add port field to connect panel
		ConnectPanel.add(portField);

		//set connect button text
		ConnectButton = new JButton("Connect");
		//set connect button size
		ConnectButton.setSize(300,100);
		//set connect button location
		ConnectButton.setLocation(490, 500);
		//set connect button font
		ConnectButton.setFont(new Font("Arial", Font.BOLD, 30));
		//add connect button to connect panel
		ConnectPanel.add(ConnectButton);

		ConnectionStatusLabel = new JLabel("");
		//set conection status label size
		ConnectionStatusLabel.setSize(400,100);
		//set connection status label location
		ConnectionStatusLabel.setLocation(850,20);
		//set connection status label font
		ConnectionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		//add connection status label to connect panel
		ConnectPanel.add(ConnectionStatusLabel);
		
		//set back connect button text
		BackConnectButton = new JButton("Back");
		//set back connect button size
		BackConnectButton.setSize(200,75);
		//set back connect button location
		BackConnectButton.setLocation(50,550);
		//add back connect button ot connect panel
		ConnectPanel.add(BackConnectButton);

		//Map Selection Panel
		MapPanel.setLayout(null);
		
		//set map 1 label text
		Map1Label = new JLabel("Map 1");
		//set map 1 label size
		Map1Label.setSize(200,50);
		//set map 1 label location
		Map1Label.setLocation(300,75);
		//set map 1 label font
		Map1Label.setFont(new Font("Arial",Font.BOLD,30));
		//add map 1 label to map panel
		MapPanel.add(Map1Label);

		//set map 2 label text
		Map2Label = new JLabel("Map 2");
		//set map 2 label size
		Map2Label.setSize(200,50);
		//set map 2 label location
		Map2Label.setLocation(880,75);
		//set map 2 label font
		Map2Label.setFont(new Font("Arial",Font.BOLD,30));
		//add map 2 label to map panel
		MapPanel.add(Map2Label);

		//set map 1 button text
		Map1Button = new JButton("Select");
		//set map 1 button size
		Map1Button.setSize(200,50);
		//set map 1 button location
		Map1Button.setLocation(250,550);
		//set map 1 button font
		Map1Button.setFont(new Font("Arial",Font.PLAIN,20));
		//add map 1 button to map panel
		MapPanel.add(Map1Button);

		//set map 2 button text
		Map2Button = new JButton("Select");
		//set map 2 button size
		Map2Button.setSize(200,50);
		//set map 2 button location
		Map2Button.setLocation(830,550);
		//set map 2 button font
		Map2Button.setFont(new Font("Arial",Font.PLAIN,20));
		//add map 2 button to map panel
		MapPanel.add(Map2Button);

		//Character Selection Panel 
		CharacterPanel.setLayout(null);
		
		//set character 1 label text
		Character1Label = new JLabel("Colt");
		//set character 1 label size
		Character1Label.setSize(200,50);
		//set character 1 label location
		Character1Label.setLocation(300,75);
		//set character 1 label font
		Character1Label.setFont(new Font("Arial",Font.BOLD,30));
		//add character 1 label to character panel
		CharacterPanel.add(Character1Label);

		//set character 2 label text
		Character2Label = new JLabel("Dynamike");
		//set character 2 label size
		Character2Label.setSize(200,50);
		//set character 2 label location
		Character2Label.setLocation(880,75);
		//set character 2 label font
		Character2Label.setFont(new Font("Arial",Font.BOLD,30));
		//add character 2 label to character panel
		CharacterPanel.add(Character2Label);
		
		//set character 1 button text
		Character1Button = new JButton("Select");
		//set character 1 button size
		Character1Button.setSize(200,50);
		//set character 1 button location
		Character1Button.setLocation(250,550);
		//set character 1 button font
		Character1Button.setFont(new Font("Arial",Font.PLAIN,20));
		//add character 1 button to character panel
		CharacterPanel.add(Character1Button);

		//set character 2 button text
		Character2Button = new JButton("Select");
		//set character 2 button size
		Character2Button.setSize(200,50);
		//set character 2 button location
		Character2Button.setLocation(830,550);
		//set character 2 button font
		Character2Button.setFont(new Font("Arial",Font.PLAIN,20));
		//add character 2 button to character panel
		CharacterPanel.add(Character2Button);

		//Help Panel
		HelpPanel.setLayout(null);


		//Death screen
		DeathPanel.setLayout(null);
		//set death panel dimensions
		DeathPanel.setPreferredSize(new Dimension(880,720));
		//set death panel background color
		DeathPanel.setBackground(Color.BLACK);

		//set death label text
		DeathLabel = new JLabel("You lost.");
		//set death label size
		DeathLabel.setSize(400,100);
		//set death label location
		DeathLabel.setLocation(50, 50);
		//set death label font color
		DeathLabel.setForeground(Color.RED);
		//set death label font
		DeathLabel.setFont(new Font("Arial", Font.BOLD, 50));
		//add death label to death panel
		DeathPanel.add(DeathLabel);

		//Win screen
		WinPanel.setLayout(null);
		//set win panel size
		WinPanel.setPreferredSize(new Dimension(880,720));
		//set win panel background color
		WinPanel.setBackground(Color.BLACK);
		
		//set win label text
		WinLabel = new JLabel("You have won!");
		//set win label size
		WinLabel.setSize(400,100);
		//set win label location
		WinLabel.setLocation(50, 50);
		//set win label font color
		WinLabel.setForeground(Color.GREEN);
		//set win label font
		WinLabel.setFont(new Font("Arial", Font.BOLD, 50));
		//add win label to win panel
		WinPanel.add(WinLabel);


        //PlayScreen Components
		//set play back button text
        PlayBackButton = new JButton ("Back");
		//set play back button size
		PlayBackButton.setSize(170, 70);
		//set play back button location
        PlayBackButton.setLocation(350, 600);
		//set play back button font
        PlayBackButton.setFont(new Font("Arial", Font.PLAIN, 30));
		//add play back button to chat panel
		ChatPanel.add(PlayBackButton);
        
        ChatPanel.setLayout(null);
		//set chat panel size
        ChatPanel.setPreferredSize(new Dimension(450,720));

        ChatArea = new JTextArea();
		//cannot edit/move the chat area
        ChatArea.setEditable(false);
		
		//scroll for the chat area
        ChatScroll = new JScrollPane(ChatArea);
		//set chat scroll size
        ChatScroll.setSize(350,400);
		//set chat scroll location
		ChatScroll.setLocation(85,50);
		//add chat scroll to chat panel
		ChatPanel.add(ChatScroll);
        
        ChatTextInput = new JTextField();
		//set chat text input size
        ChatTextInput.setSize(350,50);
		//set chat text input location
        ChatTextInput.setLocation(85,450);
		//add chat text input to chat panel
        ChatPanel.add(ChatTextInput);
        
        RaceTimerLabel = new JLabel();
        

        //Animation Panel
		//set animation panel size
        AniPanel.setPreferredSize(new Dimension(880,720));
        
			ConnectMenuButton = new JButton("Connect");
			ConnectMenuButton.setSize(300,60);
			ConnectMenuButton.setLocation(490,500);
			MenuPanel.add(ConnectMenuButton);

			HelpMenuButton = new JButton("Tutorial (Demo)");
			HelpMenuButton.setSize(300,60);
			HelpMenuButton.setLocation(490,600);
			MenuPanel.add(HelpMenuButton);

			//ConnectPanel
			ConnectPanel.setLayout(null);

			UsernameLabel = new JLabel("Username:");
			UsernameLabel.setSize(200,50);
			UsernameLabel.setLocation(50,50);
			UsernameLabel.setFont(new Font("Arial", Font.BOLD,30));
			ConnectPanel.add(UsernameLabel);

			UsernameField = new JTextField();
			UsernameField.setSize(250,50);
			UsernameField.setLocation(210,50);
			UsernameField.setFont(new Font("Arial", Font.PLAIN, 30));
			ConnectPanel.add(UsernameField);

			IPLabel = new JLabel("IP:");
			IPLabel.setSize(200,50);
			IPLabel.setLocation(340,150);
			IPLabel.setFont(new Font("Arial", Font.BOLD,30));
			ConnectPanel.add(IPLabel);

			ipField = new JTextField();
			ipField.setSize(500,200);
			ipField.setLocation(100,200);
			ipField.setFont(new Font("Arial", Font.PLAIN, 40));
			ConnectPanel.add(ipField);

			PortLabel = new JLabel("Port:");
			PortLabel.setSize(200,50);
			PortLabel.setLocation(900,150);
			PortLabel.setFont(new Font("Arial", Font.BOLD,30));
			ConnectPanel.add(PortLabel);

			portField = new JTextField();
			portField.setSize(500,200);
			portField.setLocation(680,200);
			portField.setFont(new Font("Arial", Font.PLAIN, 40));
			ConnectPanel.add(portField);

			ConnectButton = new JButton("Connect");
			ConnectButton.setSize(300,100);
			ConnectButton.setLocation(490, 500);
			ConnectButton.setFont(new Font("Arial", Font.BOLD, 30));
			ConnectPanel.add(ConnectButton);

			ConnectionStatusLabel = new JLabel("");
			ConnectionStatusLabel.setSize(400,100);
			ConnectionStatusLabel.setLocation(850,20);
			ConnectionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			ConnectPanel.add(ConnectionStatusLabel);

			BackConnectButton = new JButton("Back");
			BackConnectButton.setSize(200,75);
			BackConnectButton.setLocation(50,550);
			ConnectPanel.add(BackConnectButton);

			//Map Selection Panel
			MapPanel.setLayout(null);

			Map1Label = new JLabel("Map 1");
			Map1Label.setSize(200,50);
			Map1Label.setLocation(300,75);
			Map1Label.setFont(new Font("Arial",Font.BOLD,30));
			MapPanel.add(Map1Label);

			Map2Label = new JLabel("Map 2");
			Map2Label.setSize(200,50);
			Map2Label.setLocation(880,75);
			Map2Label.setFont(new Font("Arial",Font.BOLD,30));
			MapPanel.add(Map2Label);

			Map1Button = new JButton("Select");
			Map1Button.setSize(200,50);
			Map1Button.setLocation(250,550);
			Map1Button.setFont(new Font("Arial",Font.PLAIN,20));
			MapPanel.add(Map1Button);

			Map2Button = new JButton("Select");
			Map2Button.setSize(200,50);
			Map2Button.setLocation(830,550);
			Map2Button.setFont(new Font("Arial",Font.PLAIN,20));
			MapPanel.add(Map2Button);

			//Character Selection Panel 
			CharacterPanel.setLayout(null);

			Character1Label = new JLabel("Colt");
			Character1Label.setSize(200,50);
			Character1Label.setLocation(300,75);
			Character1Label.setFont(new Font("Arial",Font.BOLD,30));
			CharacterPanel.add(Character1Label);

			Character2Label = new JLabel("Dynamike");
			Character2Label.setSize(200,50);
			Character2Label.setLocation(880,75);
			Character2Label.setFont(new Font("Arial",Font.BOLD,30));
			CharacterPanel.add(Character2Label);

			Character1Button = new JButton("Select");
			Character1Button.setSize(200,50);
			Character1Button.setLocation(250,550);
			Character1Button.setFont(new Font("Arial",Font.PLAIN,20));
			CharacterPanel.add(Character1Button);

			Character2Button = new JButton("Select");
			Character2Button.setSize(200,50);
			Character2Button.setLocation(830,550);
			Character2Button.setFont(new Font("Arial",Font.PLAIN,20));
			CharacterPanel.add(Character2Button);

			//Help Panel
			HelpPanel.setLayout(null);


			//Death screen
			DeathPanel.setLayout(null);
			DeathPanel.setPreferredSize(new Dimension(880,720));
			DeathPanel.setBackground(Color.BLACK);

			DeathLabel = new JLabel("You lost.");
			DeathLabel.setSize(400,100);
			DeathLabel.setLocation(50, 50);
			DeathLabel.setForeground(Color.RED);
			DeathLabel.setFont(new Font("Arial", Font.BOLD, 50));
			DeathPanel.add(DeathLabel);

			//Win screen
			WinPanel.setLayout(null);
			WinPanel.setPreferredSize(new Dimension(880,720));
			WinPanel.setBackground(Color.BLACK);

			WinLabel = new JLabel("You have won!");
			WinLabel.setSize(400,100);
			WinLabel.setLocation(50, 50);
			WinLabel.setForeground(Color.GREEN);
			WinLabel.setFont(new Font("Arial", Font.BOLD, 50));
			WinPanel.add(WinLabel);


			//PlayScreen Components
			PlayBackButton = new JButton ("Back");
			PlayBackButton.setSize(170, 70);
			PlayBackButton.setLocation(350, 600);
			PlayBackButton.setFont(new Font("Arial", Font.PLAIN, 30));
			ChatPanel.add(PlayBackButton);
			
			ChatPanel.setLayout(null);
			ChatPanel.setPreferredSize(new Dimension(450,720));

			ChatArea = new JTextArea();
			ChatArea.setEditable(false);

			ChatScroll = new JScrollPane(ChatArea);
			ChatScroll.setSize(350,400);
			ChatScroll.setLocation(85,50);
			ChatPanel.add(ChatScroll);
			
			ChatTextInput = new JTextField();
			ChatTextInput.setSize(350,50);
			ChatTextInput.setLocation(85,450);
			ChatPanel.add(ChatTextInput);

			//Animation Panel
			AniPanel.setPreferredSize(new Dimension(880,720));
			
			RaceTimerLabel = new JLabel();
			RaceTimerLabel.setSize(170, 70);
			RaceTimerLabel.setLocation(0,0);
			//RaceTimerLabel.setLocation(680, 0);
			RaceTimerLabel.setForeground(Color.WHITE);
			RaceTimerLabel.setFont(new Font("Monospaced", Font.PLAIN, 30));
			AniPanel.add(RaceTimerLabel);
			

			//Split Pane for game and chat
			PlaySplitPane.setLeftComponent(AniPanel);
			PlaySplitPane.setRightComponent(ChatPanel);
			PlaySplitPane.setDividerLocation(720);
			PlaySplitPane.setOneTouchExpandable(false);
			PlaySplitPane.setDividerSize(0);
			
			theframe.setContentPane(MenuPanel);
			theframe.pack();
			theframe.setResizable(false);
			theframe.setVisible(true);
			theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Split Pane for game and chat
		//set left part of play split pane to animation panel
        PlaySplitPane.setLeftComponent(AniPanel);
		//set right side of play split pane to chat panel
        PlaySplitPane.setRightComponent(ChatPanel);
		//set play split plane divider location
        PlaySplitPane.setDividerLocation(720);
		//Not allowed to expand or collapse the split pane
		PlaySplitPane.setOneTouchExpandable(false);
		//set play split pane divider size
		PlaySplitPane.setDividerSize(0);
        
		//Show the menu panel
        theframe.setContentPane(MenuPanel);
        theframe.pack();
		//the frame is not resizable
        theframe.setResizable(false);
		//the frame is visible
        theframe.setVisible(true);
		//program exits when the window is closed
        theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}





 
