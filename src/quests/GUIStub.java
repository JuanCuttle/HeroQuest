package quests;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import modelo.Door;
import modelo.FallingRock;
import modelo.HeroQuest;
import modelo.Position;
import modelo.Strings;

public class GUIStub extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JButton[][] botoesTabuleiro;
	
	protected BasicMap map;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIStub frame = new GUIStub(new TheTrial(new HeroQuest(null)));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIStub(BasicMap map) {
		this.map = map;
		
		setTitle(Strings.HEROQUEST.toString());

		this.botoesTabuleiro = new JButton[map.getNumberOfRows()][map.getNumberOfColumns()];
		// this.botoesCriaturas = new ArrayList<JButton>();
		// this.heroQuest = new HeroQuest(new AtorJogador());

		// Configurar a janela
		setSize(1300, 770);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		Border invisivel = BorderFactory.createEmptyBorder();
		setFocusable(true);
		requestFocusInWindow();
		
		for (int j = 0; j < map.getNumberOfColumns(); j++) {
			JButton botao = new JButton();
			botao.setText(""+j);
			botao.setBounds(120 + (j * 23), 49 + (-1 * 23), 23, 23);
			botao.setBorder(invisivel);
			botao.setVisible(true);
			contentPane.add(botao);
		}
		for (int i = 0; i < map.getNumberOfRows(); i++) {
			JButton botao = new JButton();
			botao.setText(""+i);
			botao.setBounds(120 + (-1 * 23), 49 + (i * 23), 23, 23);
			botao.setBorder(invisivel);
			botao.setVisible(true);
			contentPane.add(botao);
		}

		// Cria os botões do tabuleiro
		for (int i = 0; i < map.getNumberOfRows(); i++) {
			for (int j = 0; j < map.getNumberOfColumns(); j++) {
				JButton botao = new JButton();
				botao.setName("" + i + j);
				botao.setBounds(120 + (j * 23), 49 + (i * 23), 23, 23);
				botao.setBorder(invisivel);
				botao.setVisible(true);
				botao.addKeyListener(null);
				botao.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// abrirPorta(Integer.parseInt(botao.getName()));
					}
				});
				contentPane.add(botao);
				this.botoesTabuleiro[i][j] = botao;
			}
		}
		
		atualizarInterfaceGrafica();
	}
	
	public void atualizarBotao(JButton botao, Position posicao) {
		ImageIcon img;
		String path = "";
		int linha = posicao.getRow();
		int coluna = posicao.getColumn();
		if (!posicao.isVisible()) {
			path = "/imagens/" + "Wall" + ".png";

		} else {
			if (posicao.getCreature() != null) {
				path = "/imagens/"
						+ posicao.getCreature().getClass().getSimpleName()
						+ ".png";

			} else if (posicao.getTrap() != null) {
					if (posicao.getTrap().getVisible()) {
						if (posicao.getTrap() instanceof FallingRock && posicao.getTrap().getTriggered()){
							path = "/imagens/"
									+ "Rubble"
									+ ".png";
						} else {
							path = "/imagens/"
									+ posicao.getTrap().getClass().getSimpleName()
									+ ".png";
						}
					} else {
						path = "/imagens/" + posicao.getClass().getSimpleName()
								+ ".png";
					}
			} else {
				if (posicao instanceof Door) {
					if (!((Door) posicao).isSecreta()) {
						if (((Door) posicao).getPortaEstaAberta()) {
							path = "/imagens/PortaAberta.png";
						} else {
							path = "/imagens/PortaFechada.png";
						}
					} else {
						path = "/imagens/" + "Wall" + ".png";
					}
				} else {
					path = "/imagens/" + posicao.getClass().getSimpleName()
							+ ".png";
				}
				int stairRow = map.getStairsPosition()[0];
				int stairColumn = map.getStairsPosition()[1];
				if (linha == stairRow && coluna == stairColumn) {
					path = "/imagens/2424.png";
				} else if (linha == stairRow && coluna == stairColumn+1) {
					path = "/imagens/2425.png";
				} else if (linha == stairRow+1 && coluna == stairColumn) {
					path = "/imagens/2524.png";
				} else if (linha == stairRow+1 && coluna == stairColumn+1) {
					path = "/imagens/2525.png";
				}
			}
		}
		img = new ImageIcon(getClass().getResource(path));
		botao.setIcon(img);
		botao.invalidate();
		botao.revalidate();
		botao.repaint();
	}
	
	public void atualizarInterfaceGrafica() {
		for (byte i = 0; i < map.getNumberOfRows(); i++) {
			for (byte j = 0; j < map.getNumberOfColumns(); j++) {
				//Position posicao = this.heroQuest.getPosition(i, j);
				Position posicao = this.map.getPosition(i, j);
				this.atualizarBotao(this.botoesTabuleiro[i][j], posicao);
			}
		}
	}
}
