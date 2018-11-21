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
import modelo.Furniture;
import modelo.HeroQuest;
import modelo.Position;
import modelo.Strings;

public class GUIStub extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JButton[][] boardButtons;
	
	protected BasicMap map;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HeroQuest game = new HeroQuest(null);
					BasicMap map = new TheTrial(game);
					GUIStub frame = new GUIStub(map);
					game.setMap(map);
					frame.setVisible(true);
					map.createMonsters(game);
					frame.atualizarInterfaceGrafica();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUIStub(BasicMap map) {
		this.map = map;
		
		setTitle(Strings.HEROQUEST.toString());

		this.boardButtons = new JButton[map.getNumberOfRows()][map.getNumberOfColumns()];
		// this.botoesCriaturas = new ArrayList<JButton>();
		// this.heroQuest = new HeroQuest(new AtorJogador());

		// Configure the window
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

		// Create the board's buttons
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
				this.boardButtons[i][j] = botao;
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
				
				if (map.getPosition((byte)linha, (byte)coluna) instanceof Furniture){
					byte[] tblpos = map.getTable1Position();
					if (tblpos != null){
						path = tablePath(map, linha, coluna, tblpos);
					}
					tblpos = map.getTable2Position();
					if (tblpos != null && path == ""){
						path = tablePath(map, linha, coluna, tblpos);
					}
				}
			}
		}
		img = new ImageIcon(getClass().getResource(path));
		botao.setIcon(img);
		botao.invalidate();
		botao.revalidate();
		botao.repaint();
	}
	
	private String tablePath(BasicMap map, int linha, int coluna, byte[] tblpos) {
		String path = "";

		int tableRow = tblpos[1];
		int tableCol = tblpos[2];
		
		if (tblpos[0] == 0){
			if (linha == tableRow && coluna == tableCol) {
				path = "/imagens/TableH00.png";
			} else if (linha == tableRow && coluna == tableCol+1) {
				path = "/imagens/TableH01.png";
			} else if (linha == tableRow && coluna == tableCol+2) {
				path = "/imagens/TableH02.png";
			} else if (linha == tableRow+1 && coluna == tableCol) {
				path = "/imagens/TableH10.png";
			} else if (linha == tableRow+1 && coluna == tableCol+1) {
				path = "/imagens/TableH11.png";
			} else if (linha == tableRow+1 && coluna == tableCol+2) {
				path = "/imagens/TableH12.png";
			}
		} else {
			if (linha == tableRow && coluna == tableCol) {
				path = "/imagens/TableV00.png";
			} else if (linha == tableRow && coluna == tableCol+1) {
				path = "/imagens/TableV01.png";
			} else if (linha == tableRow+1 && coluna == tableCol) {
				path = "/imagens/TableV10.png";
			} else if (linha == tableRow+1 && coluna == tableCol+1) {
				path = "/imagens/TableV11.png";
			} else if (linha == tableRow+2 && coluna == tableCol) {
				path = "/imagens/TableV20.png";
			} else if (linha == tableRow+2 && coluna == tableCol+1) {
				path = "/imagens/TableV21.png";
			}
		}
		return path;
	}

	public void atualizarInterfaceGrafica() {
		for (byte i = 0; i < map.getNumberOfRows(); i++) {
			for (byte j = 0; j < map.getNumberOfColumns(); j++) {
				//Position posicao = this.heroQuest.getPosition(i, j);
				Position posicao = this.map.getPosition(i, j);
				this.atualizarBotao(this.boardButtons[i][j], posicao);
			}
		}
	}
}
