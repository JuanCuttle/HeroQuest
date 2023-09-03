package view;

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

    public ImagePanel(ImageEnum type) {
       try {     
    	   switch(type){
    	   		case INSTRUCTIONS: image = ImageIO.read(getClass().getResource(
						"/images/backgrounds/Instructions.png"));
    	   			break;
    	   		case ZARGON: image = ImageIO.read(getClass().getResource(
						"/images/playerCards/Zargon.png"));
    	   				break;
    	   		case BARBARIAN: image = ImageIO.read(getClass().getResource(
						"/images/playerCards/BarbarianCard.png"));
    	   				break;
    	   		case WIZARD: image = ImageIO.read(getClass().getResource(
						"/images/playerCards/WizardCard.png"));
    	   				break;
    	   		case ELF: image = ImageIO.read(getClass().getResource(
						"/images/playerCards/ElfCard.png"));
    	   				break;
    	   		case DWARF: image = ImageIO.read(getClass().getResource(
						"/images/playerCards/DwarfCard.png"));
    	   				break;
    	   		default: image = ImageIO.read(getClass().getResource(
						"/images/backgrounds/TitleScreen.png"));
    	   				break;
    	   }
    	   
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
