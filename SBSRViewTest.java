//SuperBrawlStarsRun SBSR View (Graphical User Interfaces)
//Authors: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: Friday, May 24, 2024
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
    public JFrame theframe = new JFrame("Super Mario Brawlstars");

    //main menu
    public JPanel MenuPanel = new JPanel();
    public JButton PlayMenuButton;
    public JButton ConnectMenuButton;
    public JButton HelpMenuButton;

    //Connect screen
    public JPanel ConnectPanel = new JPanel();
    public JTextField ipField;
    public JTextField portField;
	public JButton ConnectButton;
    public JButton BackConnectButton;
    public JLabel ConnectionStatusLabel;
    public JLabel UsernameLabel;
    public JLabel IPLabel;
    public JLabel PortLabel;
    public JTextField UsernameField;

    //Play screen
    //Play(Chat screen)
    public JPanel PlayPanel = new JPanel();
    public JTextArea ChatArea;
    public JScrollPane ChatScroll;
    public JTextField ChatTextInput;

    //Play(game screen)

    
    //map screen
    public JPanel MapPanel = new JPanel();
    public JLabel Map1Label;
    public JLabel Map2Label;
    public JButton Map1Button;
    public JButton Map2Button;

    //Character selection screen
    public JPanel CharacterPanel = new JPanel();
    public JLabel Character1Label;
    public JLabel Character2Label;
    public JButton Character1Button;
    public JButton Character2Button;    

    //Help screen
    public JPanel HelpPanel = new JPanel();

    AnimationPanelTest AniPanel = new AnimationPanelTest();

    //Methods

    //Constructor
    public SBSRViewTest(){
        
    
        //Menu options 
        MenuPanel.setPreferredSize(new Dimension(1280,720));
        MenuPanel.setLayout(null);
        

        PlayMenuButton = new JButton("Play");
        PlayMenuButton.setSize(300,60);
        PlayMenuButton.setLocation(490,400);
        MenuPanel.add(PlayMenuButton);

        ConnectMenuButton = new JButton("Connect");
        ConnectMenuButton.setSize(300,60);
        ConnectMenuButton.setLocation(490,500);
        MenuPanel.add(ConnectMenuButton);

        HelpMenuButton = new JButton("Help");
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

        //Map Panel
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

        //Character Panel 
        CharacterPanel.setLayout(null);

        Character1Label = new JLabel("Colt");
        Character1Label.setSize(200,50);
        Character1Label.setLocation(300,75);
        Character1Label.setFont(new Font("Arial",Font.BOLD,30));
        CharacterPanel.add(Character1Label);

        Character2Label = new JLabel("El Primo");
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

        //Play screen 
        AniPanel.setLayout(null);


        //Play(Chat screen)
        ChatArea = new JTextArea();
        ChatArea.setEditable(false);

        ChatScroll = new JScrollPane(ChatArea);
        ChatScroll.setSize(350,400);
		ChatScroll.setLocation(850,50);
		AniPanel.add(ChatScroll);
        
        ChatTextInput = new JTextField();
        ChatTextInput.setSize(350,50);
        ChatTextInput.setLocation(850,450);
        AniPanel.add(ChatTextInput);

        //putting the panel inside the frame
        AniPanel.setPreferredSize(new Dimension(1280,720));
        theframe.setContentPane(MenuPanel);
        theframe.pack();
        theframe.setResizable(false);
        theframe.setVisible(true);
        theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}





 
