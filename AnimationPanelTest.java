import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.security.Key;
import java.awt.image.*;
import java.awt.event.*;

public class AnimationPanelTest extends JPanel{
    //Properties
    //Images and Terrain Tiles
    public Image imgOpponent;
    public Image imgOpponentRight;
    public Image imgOpponentLeft;
    public Image imgCharacter;
    public Image imgCharacterRight;
    public Image imgCharacterLeft;
    public Image imgGrass;
    public Image imgBrick;
    public Image imgAir;
    public Image imgDirt;
    public Image imgPole;
    public Image imgStair;
    public Image imgUndergroundDirt;
    public Image imgHardBlock;
    public Image imgFlag;
    public Image imgEnemy;

    //Map
    BufferedReader br;
    public char[][] Map;//2D array to hold map layout
    public int MapWidth = 3600;
    public int MapHeight =720;
    public int TilePixels = 36;

    //area that is being viewed
    public double dblViewportX = 0;

    //Character
    public double CharacterX;
    public double CharacterY; 
    public String strCharacterDir = "right";
    public int intCharacterHP = 3;

    //Opponent 
    public double OpponentX = 324;
    public double OpponentY = 612;
    public String strOpponentDir = "right";
  
    public int intEnemyX=100;
    public int intEnemyY=600;

	public int intMapX = 0;
	public int intMapY = 0;

    Timer timer;
    SBSRModelControl model;

    //Methods
    public void paintComponent(Graphics g){
        super.paintComponent(g);
		
		
		for (int intCount = 0; intCount < 20; intCount++){
			for (int intCount2 = 0; intCount2 < 100; intCount2++){
					
					
					g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
					
					switch(Map[intCount2][intCount]){
						//Grass
						case 'g':
							g.drawImage(imgGrass, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//Dirt
						case 'd':
							g.drawImage(imgDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//Brick
						case 'b':
							g.drawImage(imgBrick, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//Air
						case 'a':
							g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'p':
							g.drawImage(imgPole, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 's':
							g.drawImage(imgStair, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'h':
							g.drawImage(imgHardBlock, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'u':
							g.drawImage(imgUndergroundDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        case 'f':
							g.drawImage(imgFlag, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        }	
			}
		}
		
       
        //Load and draw character image according to which way he/she is facing.
        if (strCharacterDir.equals("right")){
				imgCharacter = imgCharacterRight;
		} else if (strCharacterDir.equals("left")){
				imgCharacter = imgCharacterLeft;
		} else {
				imgCharacter = imgCharacterRight;
		}

        //load and draw opponent image according to which way he/she is facing.
        if (strOpponentDir.equals("right")){
            imgOpponent = imgOpponentRight;
        } else if (strOpponentDir.equals("left")){
            imgOpponent = imgOpponentLeft;
        }

		if (intCharacterHP > 0){
			g.drawImage(imgCharacter,(int)((CharacterX - dblViewportX)), (int)((CharacterY)), 36, 36, null);
		} else {
			System.out.println("Character dead");
		}
        g.drawImage(imgOpponent, (int)((OpponentX - dblViewportX)), (int)((OpponentY)), 36, 36, null);
        g.drawImage(imgEnemy, (int)(intEnemyX-dblViewportX), (int)(intEnemyY), null);
        repaint();

    }
    
    //load Map layout from csv file
    public void loadMap(int intMapSelection){
        Map = new char[MapWidth][MapHeight];
        try{
            if(intMapSelection == 1){
                br = new BufferedReader(new FileReader("Map1.csv"));
                System.out.println("map 1 shown");
                CharacterX = 324;
				CharacterY = 612; 
				dblViewportX = 0;
				intCharacterHP = 3;
            }else if(intMapSelection == 2){
                br = new BufferedReader(new FileReader("Map2.csv"));
                System.out.println("map 2 shown");
                CharacterX = 324;
                CharacterY = 0;
                dblViewportX = 0;
                intCharacterHP = 3;
            }
            String line;
            int row = 0;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                for(int col = 0; col < parts.length; col++){
                    Map[col][row] = parts[col].charAt(0);
                }
                row++;
            }
            br.close(); 
        }catch(IOException e){
            System.out.println("Error reading map file");
        }catch(NullPointerException e){

        }
        repaint();
    }
    
    //loading the proper character image
    public void loadCharacter(int intCharacterSelection){
        try{
            if(intCharacterSelection == 2){
                imgCharacterRight = ImageIO.read(new File("Dynamike.png"));
                imgCharacterLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            }else if(intCharacterSelection == 1){
                imgCharacterRight = ImageIO.read(new File("Colt.png"));
                imgCharacterLeft = ImageIO.read(new File("Colt(Left).png"));
                System.out.println("loaded colt");
            }
        }catch(IOException e){
            System.out.println("Error loading character image");
        }
        repaint();
    }

    //loading the proper opponent image
    public void loadOpponent(int intOpponentSelection){
        try{
            if(intOpponentSelection == 2){
                imgOpponentRight = ImageIO.read(new File("Dynamike.png"));
                imgOpponentLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dinamike");
            }else if(intOpponentSelection == 1){
                imgOpponentRight = ImageIO.read(new File("Colt.png"));
                imgOpponentLeft = ImageIO.read(new File("Colt(Left).png"));
                System.out.println("loaded colt");
            }
        }catch(IOException e){
            System.out.println("Error loading opponent image");
        }
        repaint();
    }


    //Constructor
    public AnimationPanelTest(){
        //load images  
        try{
            imgDirt = ImageIO.read(new File("Dirt.png"));
            imgGrass = ImageIO.read(new File("Grass.png"));
            imgBrick = ImageIO.read(new File("Brick.png"));
            imgAir = ImageIO.read(new File("Air.png"));
            imgPole = ImageIO.read(new File("Pole.png"));
            imgStair = ImageIO.read(new File("Stair.png"));
            imgUndergroundDirt = ImageIO.read(new File("UGDirt.png"));
            imgHardBlock = ImageIO.read(new File("HardBlock.png"));
            imgFlag = ImageIO.read(new File("Flag.png"));
            imgEnemy = ImageIO.read(new File("Enemy.png"));
        }catch(IOException e){
            System.out.println("Error loading images");
        }
    }
}
