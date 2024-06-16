//SuperBrawlStarsRun SBSR Child Class for Graphical Animation Panel
//Programmers: Bosco Zhang, Liam Ma, Nihal Sidhu
//Last Modified: Saturday, June 15, 2024
//Version Number: 2.0 Beta

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
    //Width of the map
    public int intMapWidth = 3600;
    //Height of the map
    public int intMapHeight =720;

    //area that is being viewed
    public double dblViewportX = 0;

    //Character
    //Character X
    public double dblCharacterX;
    //Character Y
    public double dblCharacterY; 
    //Character direction
    public String strCharacterDir = "right";
    //Character health
    public int intCharacterHP = 3;

    //Opponent 
    //Opponent X
    public double dblOpponentX = 324;
    //Opponent Y
    public double dblOpponentY = 612;
    //Opponent direction
    public String strOpponentDir = "right";
  
    



    //Methods
    public void paintComponent(Graphics g){
        super.paintComponent(g);
		
		g.drawImage(imgTerrain,(int)(0-dblViewportX), 0, 3600, 720, null);
		
		for (int intCount = 0; intCount < 20; intCount++){
			for (int intCount2 = 0; intCount2 < 100; intCount2++){
					
					
					g.drawImage(imgAir, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
					
					switch(chrMap[intCount2][intCount]){
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
                        //Pole
                        case 'p':
							g.drawImage(imgPole, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //Stair
                        case 's':
							g.drawImage(imgStair, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //Hard Block
                        case 'h':
							g.drawImage(imgHardBlock, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //Underground Dirt
                        case 'u':
							g.drawImage(imgUndergroundDirt, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        //Flag
                        case 'f':
							g.drawImage(imgFlag, (int)((double)(intCount2)*36-dblViewportX), intCount*36, 36, 36, null);
							break;
                        }	
			}
		}
		
        //Load and draw character image according to which way he/she is facing.
        if (strCharacterDir.equals("right")){
                //Chage character image to proper side
				imgCharacter = imgCharacterRight;
		} else if (strCharacterDir.equals("left")){
                //Change character image to proper side
				imgCharacter = imgCharacterLeft;
		} else {
				imgCharacter = imgCharacterRight;
		}

        //load and draw opponent image according to which way he/she is facing.
        if (strOpponentDir.equals("right")){
            //Change opponent image to proper side
            imgOpponent = imgOpponentRight;
        } else if (strOpponentDir.equals("left")){
            //Change opponent image to proper side
            imgOpponent = imgOpponentLeft;
        }
        //if character dies
		if (intCharacterHP > 0){
			g.drawImage(imgCharacter,(int)((dblCharacterX - dblViewportX)), (int)((dblCharacterY)), 36, 36, null);
		} else {
			System.out.println("Character dead");
		}
        g.drawImage(imgOpponent, (int)((dblOpponentX - dblViewportX)), (int)((dblOpponentY)), 36, 36, null);
       
        repaint();
    }
    
    //load Map layout from csv file (Method is called upon in ModelControl)
    public void loadMap(int intMapSelection){
        chrMap = new char[intMapWidth][intMapHeight];
        try{
            //If first map chosen
            if(intMapSelection == 1){
                br = new BufferedReader(new FileReader("Map1.csv"));
                System.out.println("map 1 shown");
                dblCharacterX = 324;
				dblCharacterY = 612; 
				dblViewportX = 0;
				intCharacterHP = 3;
				try{
					imgTerrain = ImageIO.read(new File("Map1Terrain.png"));
				} catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            //If second map chosen
            }else if(intMapSelection == 2){
                br = new BufferedReader(new FileReader("Map2.csv"));
                System.out.println("map 2 shown");
                dblCharacterX = 324;
                dblCharacterY = 252;
                dblViewportX = 0;
                intCharacterHP = 3;
                try{
					imgTerrain = ImageIO.read(new File("Map2Terrain.png"));
                } catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            //If third map chosen
            }else if(intMapSelection == 3){
                br = new BufferedReader(new FileReader("Map3.csv"));
                System.out.println("map 3 shown");
                dblCharacterX = 324;
                dblCharacterY = 612;
                dblViewportX = 0;
                intCharacterHP = 3;
                try{
					imgTerrain = ImageIO.read(new File("Map3Terrain.png"));
                } catch (IOException e){
					System.out.println("Error loading map terrain.");
				}
            }
            String line;
            int row = 0;
            while((line = br.readLine()) != null){
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
            //If selected character 2
            if(intCharacterSelection == 2){
                imgCharacterRight = ImageIO.read(new File("Dynamike.png"));
                imgCharacterLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            //If selected character 1
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
            //Iff opponent selected character 2
            if(intOpponentSelection == 2){
                imgOpponentRight = ImageIO.read(new File("Dynamike.png"));
                imgOpponentLeft = ImageIO.read(new File("Dynamike(Left).png"));
                System.out.println("loaded dynamike");
            //If opponent selected character 1
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
            //try loading dirt image
            imgDirt = ImageIO.read(new File("Dirt.png"));
            //try loading grass image
            imgGrass = ImageIO.read(new File("Grass.png"));
            //try loading brick image
            imgBrick = ImageIO.read(new File("imagefolder/Brick.png"));
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
