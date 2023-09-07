package view;

import enums.ImageEnum;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private BufferedImage image;

    public ImagePanel(ImageEnum type) {
       try {
    	   switch(type){
    	   		case INSTRUCTIONS: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/backgrounds/Instructions.png")));
    	   			break;
    	   		case ZARGON: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/playerCards/Zargon.png")));
    	   				break;
    	   		case BARBARIAN: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/playerCards/BarbarianCard.png")));
    	   				break;
    	   		case WIZARD: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/playerCards/WizardCard.png")));
    	   				break;
    	   		case ELF: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/playerCards/ElfCard.png")));
    	   				break;
    	   		case DWARF: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/playerCards/DwarfCard.png")));
    	   				break;
    	   		default: image = ImageIO.read(Objects.requireNonNull(getClass().getResource(
						"/images/backgrounds/TitleScreen.png")));
    	   				break;
    	   }

       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
