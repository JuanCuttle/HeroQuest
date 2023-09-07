package view;

import enums.ImageEnum;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TitleScreen extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                TitleScreen frame = new TitleScreen();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	public TitleScreen() {
		setTitle("HeroQuest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(1000, 600);
		contentPane = new ImagePanel(ImageEnum.HEROQUEST);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		

		JButton btnNewButton = new JButton("Start game");
		btnNewButton.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnNewButton.setBounds(402, 270, 200, 50);
		btnNewButton.addActionListener(arg0 -> {
            new QuestSelector();
            dispose(); // Exits initial window
        });
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("How to play");
		btnNewButton_1.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnNewButton_1.setBounds(400, 357, 200, 50);
		btnNewButton_1.addActionListener(e -> {
            Instructions instr = null;
            try {
                instr = new Instructions();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
				Objects.requireNonNull(instr).setVisible(true);
			}
        });
		contentPane.add(btnNewButton_1);
	}
}
