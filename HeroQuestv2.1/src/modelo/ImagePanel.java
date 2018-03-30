package modelo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;

    public ImagePanel() {
       try {                
    	   image = ImageIO.read(getClass().getResource(
   				"/imagens/Dungeon.png"));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
