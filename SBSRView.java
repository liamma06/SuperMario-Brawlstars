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

//Defining
public class SBSRView implements ActionListener{
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
    public JLabel ConnectionStatusLabel;
    public JLabel UsernameLabel;
    public JTextField UsernameField;
    public String strUsername; 

    //Methods
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == ConnectMenuButton){
            theframe.setContentPane(ConnectPanel);
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

        //ConnectPanel
        ConnectPanel.setLayout(null);

        UsernameLabel = new JLabel("Username:");
        UsernameLabel.setSize(150,50);
        UsernameLabel.setLocation(50,50);
        ConnectPanel.add(UsernameLabel);

        UsernameField = new JTextField();
        UsernameField.setSize(200,50);
        UsernameField.setLocation(120,50);
        ConnectPanel.add(UsernameField);

        ipField = new JTextField();
        ipField.setSize(200,200);
        ipField.setLocation(120,200);
        ConnectPanel.add(ipField);

        
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





 
