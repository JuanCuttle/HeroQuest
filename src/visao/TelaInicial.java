package visao;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TelaInicial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaInicial() {
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
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			/*	AtorJogador frame = new AtorJogador(); // Change for new init
				frame.setVisible(true);
*/				
				new QuestSelector();

				dispose(); // Exits initial window
			}
		});
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("How to play");
		btnNewButton_1.setFont(new Font("Viner Hand ITC", Font.PLAIN, 20));
		btnNewButton_1.setBounds(400, 357, 200, 50);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Instrucoes instr = null;
				try {
					instr = new Instrucoes();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				instr.setVisible(true);
			}
		});
		contentPane.add(btnNewButton_1);
	}

}
