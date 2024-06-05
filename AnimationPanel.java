import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel{
    //Properties
    public String strLine;		
	public String strSplit[];
	public int intRowNum;
	public int intColumnNum;	
	public int intColumnNum2;
	public int intRowNum2;
	public String strMap[][] = new String[20][20];
	BufferedImage imgGrass = null;
    BufferedImage imgAir = null;
    BufferedImage imgBrick = null;
    //Methods

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(intRowNum2 = 0; intRowNum2 < 20; intRowNum2++){
        
            for(intColumnNum2 = 0; intColumnNum2 < 20; intColumnNum2++){
                
                if(strMap[intRowNum2][intColumnNum2].equals("g")){
                    g.drawImage(imgGrass, (20 * intColumnNum2), ((20 * intRowNum2) + 100),null);
                    
                }
                
                if(strMap[intRowNum2][intColumnNum2].equals("a")){
                    g.drawImage(imgAir, (20 * intColumnNum2), ((20 * intRowNum2) + 100),null);
                    
                }
                
                if(strMap[intRowNum2][intColumnNum2].equals("b")){
                    g.drawImage(imgBrick, (20 * intColumnNum2), ((20 * intRowNum2) + 100),null);
                    
                }
                
                /*if(strMap[intRowNum2][intColumnNum2].equals("h")){
                    con.drawImage(imgHealth, (20 * intColumnNum2), ((20 * intRowNum2) + 100));
                    
                }
                
                if(strMap[intRowNum2][intColumnNum2].equals("f")){
                    con.drawImage(imgForce, (20 * intColumnNum2), ((20 * intRowNum2) + 100));
                    
                }*/
            }
        }

    }

    //Constructor
    public AnimationPanel(){
        super();

        try{
            BufferedReader mapFile = new BufferedReader(new FileReader("Map1.csv")); 
            imgGrass = ImageIO.read(new File("Colt.jpg"));
            imgAir = ImageIO.read(new File("Shelly.jpg"));
            imgBrick = ImageIO.read(new File("Brick.png"));
            //Methods

        }catch(IOException e){
    
        }catch(ArrayIndexOutOfBoundsException e){

        }
        for(intRowNum = 0; intRowNum < 20; intRowNum++){
        
            // reads the line of text from the map file, returns a string, and puts it into the variable strLine
            try{
                BufferedReader mapFile = new BufferedReader(new FileReader("Map1.csv")); 
                imgGrass = ImageIO.read(new File("Colt.jpg"));
                imgAir = ImageIO.read(new File("Shelly.jpg"));
                imgBrick = ImageIO.read(new File("Brick.png"));
                strLine = mapFile.readLine();
                //Methods

            }catch(IOException e){
        
            }catch(ArrayIndexOutOfBoundsException e){

            }catch(NullPointerException e){

            }
            
                       
           for(intColumnNum = 0; intColumnNum < 100; intColumnNum++){
               // Need a 1d array to split that line based on commas
               try{
               strSplit = strLine.split(",");
               }catch(NullPointerException e){

               }
               // copy to the 2d array
               try{
               strMap[intRowNum][intColumnNum] = strSplit[intColumnNum]; 
                }catch(NullPointerException e){

            }
           }
       }
    }
    
}
