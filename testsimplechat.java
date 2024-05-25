import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class testsimplechat implements ActionListener{
    //Properties
	JFrame theFrame = new JFrame("Simple chat");
	JPanel thePanel = new JPanel();
	JTextArea theArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(theArea);
	JTextField theText = new JTextField();
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField("8080");
	JButton theButton = new JButton("Connect");
    
	SuperSocketMaster ssm;
    JLabel IpLabel = new JLabel();
    JLabel UsernameLabel = new JLabel("Username: ");
    JTextField UserField = new JTextField();

    String strUsername; 


	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theButton){
			//System.out.println("Connect Button Pressed");
			if(ipField.getText().equals("") && portField.getText().equals("")){
				theArea.append("Enter a port number and/or IP Address\n");
			}else if(ipField.getText().equals("") && !portField.getText().equals("")){
				theArea.append("Starting chat in server mode\n");
				ssm = new SuperSocketMaster(Integer.parseInt(portField.getText()),this);
				ssm.connect();
                IpLabel.setText("(HOST) Your IP:" + ssm.getMyAddress());
			}else if(!ipField.getText().equals("") && !portField.getText().equals("")){
                                theArea.append("Starting chat in client  mode\n");
				ssm = new SuperSocketMaster(ipField.getText(),Integer.parseInt(portField.getText()),this);
				ssm.connect();
                IpLabel.setText("(Client) Connected to: " + ipField.getText());
            }else if(!ipField.getText().equals("") && portField.getText().equals("")){
                 theArea.append("Need a portnumber or port/ip \n");
            }
		}else if(evt.getSource() == theText){
			ssm.sendText(strUsername+": "+theText.getText());
			theArea.append(strUsername+": "+theText.getText()+ "\n");
			theText.setText("");
		}else if(evt.getSource() == ssm){
			theArea.append(ssm.readText() + "\n");
		}else if(evt.getSource() == UserField){
            strUsername = UserField.getText();
            UserField.setEnabled(false);
        }
	}

	//Constructor
	public testsimplechat(){
		thePanel.setLayout(null);
		thePanel.setPreferredSize(new Dimension(500, 600));

		theScroll.setSize(500,300);
		theScroll.setLocation(0,0);
		thePanel.add(theScroll);
		
		theText.setSize(500,100);
		theText.setLocation(0,300);
		theText.addActionListener(this);
		thePanel.add(theText);

		ipField.setSize(125,100);
		ipField.setLocation(0,400);
		thePanel.add(ipField);

		portField.setSize(125,100);
		portField.setLocation(125,400);
		thePanel.add(portField);

		theButton.setSize(250, 100);
		theButton.setLocation(0,500);
		theButton.addActionListener(this);
		thePanel.add(theButton);

        IpLabel.setSize(500,15);
        IpLabel.setLocation(265, 530);
        thePanel.add(IpLabel);

        UsernameLabel.setSize(200,15);
        UsernameLabel.setLocation(265,425);
        thePanel.add(UsernameLabel);

        UserField.setSize(150,20);
        UserField.setLocation(335,425);
        UserField.addActionListener(this);
        thePanel.add(UserField);

		theFrame.setContentPane(thePanel);
		theFrame.pack();
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setVisible(true);	
	}

	//Main method
	public static void main(String[] args){
		new testsimplechat();
	}
}
