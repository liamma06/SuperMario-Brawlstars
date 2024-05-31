import java.io.*;

public class SBSRModelControl{
	//Properties
	public String strConnectionResult;
	SuperSocketMaster ssm;

	//Methods
	public String connection(String ipField, String portField, String UsernameField){
		if(ipField.equals("") && portField.equals("")){
			strConnectionResult.equals("Enter a port number and/or IP Address\n");
		}else if(ipField.equals("") && !portField.equals("") && UsernameField.equals("")){
			strConnectionResult.equals("Enter Username");
		}else if(ipField.equals("") && !portField.equals("") && !UsernameField.equals("")){
			//ConnectionStatusLabel.setText("Starting chat in server mode\n");
			try{
				ssm = new SuperSocketMaster(Integer.parseInt(portField),this);
				ssm.connect();
			}catch(NumberFormatException){
				ssm = new SuperSocketMaster(8080, this);
			}
			strHostUsername = UsernameField.getText();
			UsernameField.setEnabled(false);
			System.out.println(strHostUsername);
			ConnectionStatusLabel.setText("(HOST) Your IP:" + ssm.getMyAddress());
			blnHost = true;
			intNumPlayers =1;
		}else if(!ipField.getText().equals("") && !portField.getText().equals("") && UsernameField.getText().equals("")){
			ConnectionStatusLabel.setText("Enter Username");
		}else if(!ipField.getText().equals("") && !portField.getText().equals("")&& !UsernameField.getText().equals("")){
			//ConnectionStatusLabel.setText("Starting chat in client  mode\n");
			ssm = new SuperSocketMaster(ipField.getText(),Integer.parseInt(portField.getText()),this);
			ssm.connect();
			strClientUsername = UsernameField.getText();
			UsernameField.setEnabled(false);
			System.out.println(strClientUsername);
			ConnectionStatusLabel.setText("(Client) Connected to: " + ipField.getText());
			blnHost = false;
			intNumPlayers +=1;
		}else if(!ipField.getText().equals("") && portField.getText().equals("")){
			ConnectionStatusLabel.setText("Need a portnumber or port/ip \n");
		}
		return strConnectionResult;

	}

	//Constructor
	public SBSRModelControl(){
	}
	
}
