import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel{
    //Properties
    //images
    BufferedImage imgGrass = null;
    BufferedImage imgAir = null;
    BufferedImage imgBrick = null;

    //Map
    String strMap[][] = new String[100][20];

    // Need a variable to read a line at a time and then split it
    String strLine;
				
    // Need a array to split that line based on commas
    String strSplit[];
    
    // copy to the 2d array
    int introw;
    int intcolumn;

    //reading the map file
    BufferedReader Mapfile = null;

    //Methods

    public void paintComponent(Graphics g){
    //iterating through each row
        for(introw = 0; introw < 20; introw++){
            //reading the line of the map
            strLine = Mapfile.readLine();
            
            //iterating through each column
            for(intcolumn = 0; intcolumn < 100; intcolumn++){
                //spliting by the comma and putting it into the new array
                strSplit = strLine.split(",");
                strMap[introw][intcolumn] = strSplit[intcolumn];
            }
        }
        
        //Going through the board and putting the images in accordingly.
        int intRow2;
        int intCol2;
        
        for(intRow2 = 0; intRow2 < 20; intRow2++){
            for(intCol2 = 0; intCol2 < 20; intCol2++){
                if(strMap[intRow2][intCol2].equals("G")){
                    con.drawImage(imgGrass, intCol2*20, intRow2*20);
                }else if(strMap[intRow2][intCol2].equals("W")){
                    con.drawImage(imgWater, intCol2*20, intRow2*20);
                }else if(strMap[intRow2][intCol2].equals("B")){
                    con.drawImage(imgBlock, intCol2*20, intRow2*20);
                }else if(strMap[intRow2][intCol2].equals("H")){
                    con.drawImage(imgHealth, intCol2*20, intRow2*20);
                }else if(strMap[intRow2][intCol2].equals("E")){
                    con.drawImage(imgEnemy, intCol2*20, intRow2*20);
                }
        }
        SBSRViewTest.PlayPanel.repaint();
    }

    //Constructor
    public AnimationPanel(){
        super();
        try{
			imgGrass = ImageIO.read(new File("Grass.png"));
            imgAir = ImageIO.read(new File("Air.png"));
            imgBrick = ImageIO.read(new File("Brick.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
			System.out.println(e.toString());
		}
        try{
            BufferedReader MapReader = new BufferedReader(new FileReader("Map1.csv"));
        }catch(IOException e){
            System.out.println("Unable to load map");
            System.out.println(e.toString());
        }
    }
    
}
