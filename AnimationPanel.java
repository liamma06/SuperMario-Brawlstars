import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class AnimationPanel extends JPanel{
    //Properties
    BufferedImage imgGrass = null;
    BufferedImage imgAir = null;
    BufferedImage imgBrick = null;

    //Methods

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
    }
    
}
