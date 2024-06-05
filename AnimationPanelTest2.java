import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class AnimationPanelTest2 extends JPanel{
    //Properties

    //Methods
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(70, 0, 100, 100);
    }
    //Constructor
    public AnimationPanelTest2(){
        super();
    }
}
