package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import entities.utils.Strings;
import enums.ImageEnum;

public class Instructions extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagePanel contentPane;
	

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Instructions() throws IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Instructions.class.getResource("/images/players/Wizard.png")));
		
		setResizable(false);
		setTitle(Strings.INSTRUCTMANUAL.toString());

		setSize(1000, 720);
		setResizable(false);

		contentPane = new ImagePanel(ImageEnum.INSTRUCTIONS);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTextPane txtpnYouAreIn = new JTextPane();
		txtpnYouAreIn.setForeground(new Color(178, 34, 34));
		txtpnYouAreIn.setEditable(false);
		txtpnYouAreIn.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		txtpnYouAreIn
				.setText(Strings.GUIDE.toString());
		contentPane.add(txtpnYouAreIn, BorderLayout.CENTER);
		
		txtpnYouAreIn.setOpaque(false);
		
	}

}
