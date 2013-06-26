package shutterfly.com;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
 
/**
 * This class demonstrates how to load an Image from an external file
 */
public class RedeyeReduction extends Component {
           
    BufferedImage img;
    int offsetX;
    int offsetY;
 
    public void paint(Graphics g) {
        g.drawImage(img, offsetX, offsetY, null);
    }
 
    public RedeyeReduction(BufferedImage image, int x, int y) {
 this.img = image;
 offsetX = x;
 offsetY = y;
    }
 
    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
   
}