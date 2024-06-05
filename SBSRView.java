// SuperBrawlStarsRun SBSR View (Graphical User Interfaces)
// Authors: Bosco Zhang, Liam Ma, Nihal Sidhu
// Last Modified: Friday, May 24, 2024
// Version Number: 2.0 Beta

//Importing Java Swing and GUI toolkits for graphical setup
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

//Defining Graphical J Components
public class SBSRView implements ActionListener{
   
	//Properties
	
	//Main Frame
	public JFrame theframe = new JFrame("Super Mario Brawlstars");

    	//Main Menu
    	public JPanel MenuPanel = new JPanel();
    	public JButton PlayMenuButton;
    	public JButton ConnectMenuButton;
    	public JButton HelpMenuButton;

    	//Connect screen
    	public JPanel ConnectPanel = new JPanel();
    	public JTextField ipField;
    	public JTextField portField;
	public JButton ConnectButton;
   	public JButton BackButton;
    	public JLabel ConnectionStatusLabel;
    	public JLabel UsernameLabel;
    	public JLabel IPLabel;
    	public JLabel PortLabel;
    	public JTextField UsernameField;
    	public String strHostUsername;
    	public String strClientUsername; 
    	public boolean blnHost = true;
    	public int intNumPlayers = 0;

    	//Map Screen
    	public JPanel MapPanel = new JPanel();
    	public JLabel Map1Label;
    	public JLabel Map2Label;
    	public JButton Map1Button;
    	public JButton Map2Button;

    	//Play Screen 
    	public JPanel PlayPanel = new JPanel();

	SuperSocketMaster ssm;
    

	//Methods
    	public void actionPerformed(ActionEvent evt){
        	if(evt.getSource() == ConnectMenuButton){
	        	theframe.setContentPane(ConnectPanel);
        	   	theframe.revalidate();
        	} else if(evt.getSource() == BackButton){
        		theframe.setContentPane(MenuPanel);
           		theframe.revalidate();
        	} else if(evt.getSource() == ConnectButton){
			if(ipField.getText().equals("") && portField.getText().equals("")){
				ConnectionStatusLabel.setText("Enter a port number and/or IP Address\n");
			} else if(ipField.getText().equals("") && !portField.getText().equals("") && UsernameField.getText().equals("")){
             			ConnectionStatusLabel.setText("Enter Username");
            		} else if(ipField.getText().equals("") && !portField.getText().equals("") && !UsernameField.getText().equals("")){
				//ConnectionStatusLabel.setText("Starting chat in server mode\n");
				ssm = new SuperSocketMaster(Integer.parseInt(portField.getText()),this);
				ssm.connect();
                		strHostUsername = UsernameField.getText();
                		UsernameField.setEnabled(false);
                		System.out.println(strHostUsername);
                		ConnectionStatusLabel.setText("(HOST) Your IP:" + ssm.getMyAddress());
                		blnHost = true;
                		intNumPlayers =1;
			} else if(!ipField.getText().equals("") && !portField.getText().equals("") && UsernameField.getText().equals("")){
            			ConnectionStatusLabel.setText("Enter Username");
            		} else if(!ipField.getText().equals("") && !portField.getText().equals("")&& !UsernameField.getText().equals("")){
                		//ConnectionStatusLabel.setText("Starting chat in client  mode\n");
				ssm = new SuperSocketMaster(ipField.getText(),Integer.parseInt(portField.getText()),this);
				ssm.connect();
                		strClientUsername = UsernameField.getText();
                		UsernameField.setEnabled(false);
                		System.out.println(strClientUsername);
                		ConnectionStatusLabel.setText("(Client) Connected to: " + ipField.getText());
                		blnHost = false;
                		intNumPlayers +=1;
            		} else if(!ipField.getText().equals("") && portField.getText().equals("")){
                		ConnectionStatusLabel.setText("Need a portnumber or port/ip \n");
            		}
		} else if(evt.getSource()== PlayMenuButton){
            		if(blnHost == true){
                		System.out.println("Host pressed play");
                		theframe.setContentPane(MapPanel);
                		theframe.revalidate();
            		} else if(blnHost == false){
                		System.out.println("you are not host");
            		}
        	} else if(evt.getSource() == Map1Button){
            		theframe.setContentPane(PlayPanel);
           		 theframe.revalidate();
        	} else if(evt.getSource() == Map2Button){
            		theframe.setContentPane(PlayPanel);
            		theframe.revalidate();
        	}
    	}

	//Constructor
	public SBSRView(){
        	MenuPanel.setPreferredSize(new Dimension(1280,720));
        	MenuPanel.setLayout(null);


        	//Menu options 
       		PlayMenuButton = new JButton("Play");
        	PlayMenuButton.setSize(300,60);
        	PlayMenuButton.setLocation(490,400);
        	PlayMenuButton.addActionListener(this);
        	MenuPanel.add(PlayMenuButton);

        	ConnectMenuButton = new JButton("Connect");
        	ConnectMenuButton.setSize(300,60);
        	ConnectMenuButton.setLocation(490,500);
        	ConnectMenuButton.addActionListener(this);
        	MenuPanel.add(ConnectMenuButton);

        	HelpMenuButton = new JButton("Help");
        	HelpMenuButton.setSize(300,60);
        	HelpMenuButton.setLocation(490,600);
        	MenuPanel.add(HelpMenuButton);
	

        	//Map Level Selection Panel
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
        	Map1Button.addActionListener(this);
	        MapPanel.add(Map1Button);

        	Map2Button = new JButton("Select");
        	Map2Button.setSize(200,50);
        	Map2Button.setLocation(830,550);
        Map2Button.setFont(new Font("Arial",Font.PLAIN,20));
        Map2Button.addActionListener(this);
        MapPanel.add(Map2Button);

        //Play Screen

        //Connection screen
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
        ConnectButton.addActionListener(this);
        ConnectPanel.add(ConnectButton);

        ConnectionStatusLabel = new JLabel("");
        ConnectionStatusLabel.setSize(400,100);
        ConnectionStatusLabel.setLocation(850,20);
        ConnectionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        ConnectPanel.add(ConnectionStatusLabel);
        
        //putting the panel inside the frame
        theframe.setContentPane(MenuPanel);
        theframe.pack();
        theframe.setResizable(false);
        theframe.setVisible(true);
        theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new SBSRView();
    }
}





 
