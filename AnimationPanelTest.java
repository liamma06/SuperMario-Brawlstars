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
    //Opponent image
    public Image imgOpponent;
    //Opponent image facing right
    public Image imgOpponentRight;
    //Opponent image facing left
    public Image imgOpponentLeft;
    //Character image (your character)
    public Image imgCharacter;
    //Character image facing right
    public Image imgCharacterRight;
    //Character image facing left
    public Image imgCharacterLeft;
    //Grass image
    public Image imgGrass;
    //Brick image
    public Image imgBrick;
    //Air image
    public Image imgAir;
    //Dirt image
    public Image imgDirt;
    //Pole image
    public Image imgPole;
    //Stair image
    public Image imgStair;
    //Underground dirt image
    public Image imgUndergroundDirt;
    //Hard block image
    public Image imgHardBlock;
    //Flag image
    public Image imgFlag;
    //Terrain image
    public Image imgTerrain;

    //Map
    BufferedReader br;
    //2D array to hold map layout
    public char[][] chrMap;
    //Width of map
    public int intMapWidth = 3600;
    //Height of map
    public int intMapHeight =720;
    //Tile pixels
    public int intTilePixels = 36;

    //area that is being viewed in the x-axis
    public double dblViewportX = 0;

    //Character
    //Characters X location
    public double dblCharacterX;
    //Characters Y location
    public double dblCharacterY;
    //Characters direction 
    public String strCharacterDir = "right";
    //Character health
    public int intCharacterHP = 3;

    //Opponent 
    //Opponent X location
    public double dblOpponentX = 324;
    //Opponent Y location
    public double dblOpponentY = 612;
    //Opponent direction
    public String strOpponentDir = "right";
  

    //Methods
    public void paintComponent(Graphics g){
        //Calls paintComponent constructor
        super.paintComponent(g);
		//Draw background terrain
		g.drawImage(imgTerrain,(int)(0-dblViewportX), 0, 3600, 720, null);
		//For loop to check all the letters in the csv file
		for (int intCount = 0; intCount < 20; intCount++){
			for (int intCount2 = 0; intCount2 < 100; intCount2++){
					
					//Draw air
					g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
					
					switch(chrMap[intCount2][intCount]){
						//For all g's in the csv file
						case 'g':
                            //Draw grass image
							g.drawImage(imgGrass, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//For all d's in the csv file
						case 'd':
                            //Draw dirt image
							g.drawImage(imgDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//For all b's in the csv file
						case 'b':
                            //Draw brick image
							g.drawImage(imgBrick, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
						//For all a's in the csv file
						case 'a':
                            //Draw air image
							g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //For all p's in the csv file
                        case 'p':
                            //Draw pole image
							g.drawImage(imgPole, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //For all s's in the csv file
                        case 's':
                            //Draw stair image
							g.drawImage(imgStair, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //For all h's in the csv file
                        case 'h':
                            //Draw hard block image
							g.drawImage(imgHardBlock, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //For all u's in the csv file
                        case 'u':
                            //Draw underground dirt image
							g.drawImage(imgUndergroundDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //For all f's in the csv file
                        case 'f':
                            //Draw flag image
							g.drawImage(imgFlag, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        }	
			}
		}
		
        //Load and draw character image according to which way he/she is facing.
        //If character direction is right
        if (strCharacterDir.equals("right")){
                //switch to the character right image
				imgCharacter = imgCharacterRight;
        //If character direction is left
		} else if (strCharacterDir.equals("left")){
				//switch to character left image
                imgCharacter = imgCharacterLeft;
		} else {
                //Otherwise default to character right image
				imgCharacter = imgCharacterRight;
		}

        //load and draw opponent image according to which way he/she is facing.
        //If opponent direction is right
        if (strOpponentDir.equals("right")){
            //switch to opponent right image
            imgOpponent = imgOpponentRight;
        //If opponent direction is left
        } else if (strOpponentDir.equals("left")){
            //switch to opponent left image
            imgOpponent = imgOpponentLeft;
        }
        //If character health is grater than 0 (alive)
		if (intCharacterHP > 0){
            //draw character
			g.drawImage(imgCharacter,(int)((dblCharacterX - dblViewportX)), (int)((dblCharacterY)), 36, 36, null);
		//If character is dead...
        } else {
            //Print the character is dead
			System.out.println("Character dead");
		}
        //Draw opponent
        g.drawImage(imgOpponent, (int)((dblOpponentX - dblViewportX)), (int)((dblOpponentY)), 36, 36, null);
       
        repaint();
    }
    
    //load Map layout from csv file
    public void loadMap(int intMapSelection){
        chrMap = new char[intMapWidth][intMapHeight];
        try{
            //if the first map is selected
            if(intMapSelection == 1){
                //read from Map1.csv
                br = new BufferedReader(new FileReader("Map1.csv"));
                System.out.println("map 1 shown");
                //set character x
                dblCharacterX = 324;
                //set character y
				dblCharacterY = 612;
                //set viewport x to 0 
				dblViewportX = 0;
                //set character health
				intCharacterHP = 3;
				try{
                    //try to read the terrain file
					imgTerrain = ImageIO.read(new File("Map1Terrain.png"));
				} catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            //if map 2 is selected
            }else if(intMapSelection == 2){
                //read from Map2.csv
                br = new BufferedReader(new FileReader("Map2.csv"));
                System.out.println("map 2 shown");
                //set character x
                dblCharacterX = 324;
                //set character y
                dblCharacterY = 252;
                //set viewport x to 0
                dblViewportX = 0;
                //set character health
                intCharacterHP = 3;
                try{
                    //try to read the terrain file
					imgTerrain = ImageIO.read(new File("Map2Terrain.png"));
                } catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            }else if(intMapSelection == 3){
                br = new BufferedReader(new FileReader("Map3.csv"));
                System.out.println("map 3 shown");
                dblCharacterX = 324;
                dblCharacterY = 612;
                dblViewportX = 0;
                intCharacterHP = 3;
                try{
					imgTerrain = ImageIO.read(new File("Map1Terrain.png"));
                } catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            }
            String line;
            int row = 0;
            //read lines of csv file
            while((line = br.readLine()) != null){
                //splits at commas in csv file
                String[] parts = line.split(",");
                for(int col = 0; col < parts.length; col++){
                    chrMap[col][row] = parts[col].charAt(0);
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
            //if character 2 is selected
            if(intCharacterSelection == 2){
                //load right image
                imgCharacterRight = ImageIO.read(new File("Dynamike.png"));
                //load left image
                imgCharacterLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            //if character 1 is selected
            }else if(intCharacterSelection == 1){
                //load right image
                imgCharacterRight = ImageIO.read(new File("Colt.png"));
                //load left image
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
            //if opponent selects character 2
            if(intOpponentSelection == 2){
                //load right image
                imgOpponentRight = ImageIO.read(new File("Dynamike.png"));
                //load left image
                imgOpponentLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            //if opponent selects character 2
            }else if(intOpponentSelection == 1){
                //load right image
                imgOpponentRight = ImageIO.read(new File("Colt.png"));
                //load left image
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
            //try loading dirt image
            imgDirt = ImageIO.read(new File("Dirt.png"));
            //try loading grass image
            imgGrass = ImageIO.read(new File("Grass.png"));
            //try loading brick image
            imgBrick = ImageIO.read(new File("Brick.png"));
            //try loading air image
            imgAir = ImageIO.read(new File("Air.png"));
            //try loading pole image
            imgPole = ImageIO.read(new File("Pole.png"));
            //try loading stair image
            imgStair = ImageIO.read(new File("Stair.png"));
            //try loading underground dirt image
            imgUndergroundDirt = ImageIO.read(new File("UGDirt.png"));
            //try loading hard block image
            imgHardBlock = ImageIO.read(new File("HardBlock.png"));
            //try loading flag image
            imgFlag = ImageIO.read(new File("Flag.png"));
            
        }catch(IOException e){
            System.out.println("Error loading images");
        }
    }
}
