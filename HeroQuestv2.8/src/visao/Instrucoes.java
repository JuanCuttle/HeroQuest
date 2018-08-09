package visao;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Toolkit;

public class Instrucoes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagePanel contentPane;
	

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Instrucoes() throws IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Instrucoes.class.getResource("/imagens/Wizard.png")));
		
		setResizable(false);
		setTitle("Instruction Manual");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);

		setSize(1000, 650);
		setResizable(false);

		contentPane = new ImagePanel(ImageEnum.INSTRUCTIONS);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTextPane txtpnYouAreIn = new JTextPane();
		txtpnYouAreIn.setForeground(new Color(178, 34, 34));
		txtpnYouAreIn.setEditable(false);
		//txtpnYouAreIn.setBackground(new Color(222, 184, 135));
		txtpnYouAreIn.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		txtpnYouAreIn
				.setText("You are in a dungeon. It is cold, damp and dark. The only sounds are your own heartbeat, breathing and dripping water ecoing through the passageways. There are no lights, even in the distance, except for the torches you and your allies carry. Your objective is to reach the ladder and end your turn to escape the terrible fate that awaits all who get lost in Zargon's halls. Have I mentioned Zargon? He is a sorcerer who specializes in dark magic. He controls monsters with it and revels in destroying all who dare enter his lair. Fight your way through and survive, or die trying. Good luck, and may the Gods have mercy on your brave soul.\r\n\r\nUse the arrows on your keyboard to move.\r\nClick on the buttons with your mouse to pick an action. Alternatively, see Keyboard below.\r\nOpen/close doors by standing next to them and clicking on them directly in the map, or choosing which number door you wish on the menu from Keyboard.\r\nKeyboard: 1, 2 and 3 to connect, disconnect and start game, respectively. A to attack, S to use spells, R to look for traps, T to search for treasure, O to open doors, P to select character, I to open inventory, E to end your turn and M to play/pause music.\r\n\r\n*To start playing, connect if not connected already. Then, one player presses \"start game\" and types the number of players in that game. Then everyone selects their characters and start playing!");
		contentPane.add(txtpnYouAreIn, BorderLayout.CENTER);
		
		txtpnYouAreIn.setOpaque(false);
		
	}

}
