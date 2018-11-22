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
						path = tablePath(map, linha, coluna, tblpos, path);
					}
					tblpos = map.getTable2Position();
					if (tblpos != null){
						path = tablePath(map, linha, coluna, tblpos, path);
					}
					
					byte[] rackpos = map.getRackPosition();
					if (rackpos != null){
						path = rackPath(map, linha, coluna, rackpos, path);
					}
					
					byte[] botpos = map.getBookOnTablePosition();
					if (botpos != null){
						path = botPath(map, linha, coluna, botpos, path);
					}
					
					byte[] tombpos = map.getTombPosition();
					if (tombpos != null){
						path = tombPath(map, linha, coluna, tombpos, path);
					}
					
					byte[] thronepos = map.getThronePosition();
					if (thronepos != null){
						path = thronePath(map, linha, coluna, thronepos, path);
					}
					
					byte[] weprackpos = map.getWepRackPosition();
					if (weprackpos != null){
						path = wepRackPath(map, linha, coluna, weprackpos, path);
					}
					
					byte[] deskpos = map.getDeskPosition();
					if (deskpos != null){
						path = deskPath(map, linha, coluna, deskpos, path);
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
	
	private String tablePath(BasicMap map, int linha, int coluna, byte[] tblpos, String path) {

		int tableRow = tblpos[1];
		int tableCol = tblpos[2];
		
		if (tblpos[0] == 0){
			if (linha == tableRow && coluna == tableCol) {
				path = "/imagens/tables/TableH00.png";
			} else if (linha == tableRow && coluna == tableCol+1) {
				path = "/imagens/tables/TableH01.png";
			} else if (linha == tableRow && coluna == tableCol+2) {
				path = "/imagens/tables/TableH02.png";
			} else if (linha == tableRow+1 && coluna == tableCol) {
				path = "/imagens/tables/TableH10.png";
			} else if (linha == tableRow+1 && coluna == tableCol+1) {
				path = "/imagens/tables/TableH11.png";
			} else if (linha == tableRow+1 && coluna == tableCol+2) {
				path = "/imagens/tables/TableH12.png";
			}
		} else {
			if (linha == tableRow && coluna == tableCol) {
				path = "/imagens/tables/TableV00.png";
			} else if (linha == tableRow && coluna == tableCol+1) {
				path = "/imagens/tables/TableV01.png";
			} else if (linha == tableRow+1 && coluna == tableCol) {
				path = "/imagens/tables/TableV10.png";
			} else if (linha == tableRow+1 && coluna == tableCol+1) {
				path = "/imagens/tables/TableV11.png";
			} else if (linha == tableRow+2 && coluna == tableCol) {
				path = "/imagens/tables/TableV20.png";
			} else if (linha == tableRow+2 && coluna == tableCol+1) {
				path = "/imagens/tables/TableV21.png";
			}
		}
		return path;
	}
	
	private String rackPath(BasicMap map, int linha, int coluna, byte[] rackpos, String path) {

		int rackRow = rackpos[1];
		int rackCol = rackpos[2];
		
		if (rackpos[0] == 0){
			if (linha == rackRow && coluna == rackCol) {
				path = "/imagens/racks/RackH00.png";
			} else if (linha == rackRow && coluna == rackCol+1) {
				path = "/imagens/racks/RackH01.png";
			} else if (linha == rackRow && coluna == rackCol+2) {
				path = "/imagens/racks/RackH02.png";
			} else if (linha == rackRow+1 && coluna == rackCol) {
				path = "/imagens/racks/RackH10.png";
			} else if (linha == rackRow+1 && coluna == rackCol+1) {
				path = "/imagens/racks/RackH11.png";
			} else if (linha == rackRow+1 && coluna == rackCol+2) {
				path = "/imagens/racks/RackH12.png";
			}
		} else {
			if (linha == rackRow && coluna == rackCol) {
				path = "/imagens/racks/RackV00.png";
			} else if (linha == rackRow && coluna == rackCol+1) {
				path = "/imagens/racks/RackV01.png";
			} else if (linha == rackRow+1 && coluna == rackCol) {
				path = "/imagens/racks/RackV10.png";
			} else if (linha == rackRow+1 && coluna == rackCol+1) {
				path = "/imagens/racks/RackV11.png";
			} else if (linha == rackRow+2 && coluna == rackCol) {
				path = "/imagens/racks/RackV20.png";
			} else if (linha == rackRow+2 && coluna == rackCol+1) {
				path = "/imagens/racks/RackV21.png";
			}
		}
		return path;
	}
	
	private String botPath(BasicMap map, int linha, int coluna, byte[] botpos, String path) {

		int botRow = botpos[1];
		int botCol = botpos[2];
		
		if (botpos[0] == 0){
			if (linha == botRow && coluna == botCol) { // Change if and when there is this piece horizontally or facing other direction
				path = "/imagens/bookOnTable/RackH00.png";
			} else if (linha == botRow && coluna == botCol+1) {
				path = "/imagens/bookOnTable/RackH01.png";
			} else if (linha == botRow && coluna == botCol+2) {
				path = "/imagens/bookOnTable/RackH02.png";
			} else if (linha == botRow+1 && coluna == botCol) {
				path = "/imagens/bookOnTable/RackH10.png";
			} else if (linha == botRow+1 && coluna == botCol+1) {
				path = "/imagens/bookOnTable/RackH11.png";
			} else if (linha == botRow+1 && coluna == botCol+2) {
				path = "/imagens/bookOnTable/RackH12.png";
			}
		} else {
			if (linha == botRow && coluna == botCol) {
				path = "/imagens/bookOnTable/BookOnTable00.png";
			} else if (linha == botRow && coluna == botCol+1) {
				path = "/imagens/bookOnTable/BookOnTable01.png";
			} else if (linha == botRow+1 && coluna == botCol) {
				path = "/imagens/bookOnTable/BookOnTable10.png";
			} else if (linha == botRow+1 && coluna == botCol+1) {
				path = "/imagens/bookOnTable/BookOnTable11.png";
			} else if (linha == botRow+2 && coluna == botCol) {
				path = "/imagens/bookOnTable/BookOnTable20.png";
			} else if (linha == botRow+2 && coluna == botCol+1) {
				path = "/imagens/bookOnTable/BookOnTable21.png";
			}
		}
		return path;
	}
	
	private String tombPath(BasicMap map, int linha, int coluna, byte[] tombpos, String path) {

		int tombRow = tombpos[1];
		int tombCol = tombpos[2];
		
		if (tombpos[0] == 0){
			if (linha == tombRow && coluna == tombCol) { // Change if and when there is this piece horizontally
				path = "/imagens/tomb/RackH00.png";
			} else if (linha == tombRow && coluna == tombCol+1) {
				path = "/imagens/tomb/RackH01.png";
			} else if (linha == tombRow && coluna == tombCol+2) {
				path = "/imagens/tomb/RackH02.png";
			} else if (linha == tombRow+1 && coluna == tombCol) {
				path = "/imagens/tomb/RackH10.png";
			} else if (linha == tombRow+1 && coluna == tombCol+1) {
				path = "/imagens/tomb/RackH11.png";
			} else if (linha == tombRow+1 && coluna == tombCol+2) {
				path = "/imagens/tomb/RackH12.png";
			}
		} else {
			if (linha == tombRow && coluna == tombCol) {
				path = "/imagens/tomb/Tomb00.png";
			} else if (linha == tombRow && coluna == tombCol+1) {
				path = "/imagens/tomb/Tomb01.png";
			} else if (linha == tombRow+1 && coluna == tombCol) {
				path = "/imagens/tomb/Tomb10.png";
			} else if (linha == tombRow+1 && coluna == tombCol+1) {
				path = "/imagens/tomb/Tomb11.png";
			} else if (linha == tombRow+2 && coluna == tombCol) {
				path = "/imagens/tomb/Tomb20.png";
			} else if (linha == tombRow+2 && coluna == tombCol+1) {
				path = "/imagens/tomb/Tomb21.png";
			}
		}
		return path;
	}
	
	private String thronePath(BasicMap map, int linha, int coluna, byte[] thronepos, String path) {

		int throneRow = thronepos[1];
		int throneCol = thronepos[2];
		
		if (thronepos[0] == 0){
			if (linha == throneRow && coluna == throneCol) {
				path = "/imagens/ThroneR.png";
			}
		} else {
			if (linha == throneRow && coluna == throneCol) {
				path = "/imagens/ThroneL.png";
			}
		}
		return path;
	}
	
	private String wepRackPath(BasicMap map, int linha, int coluna, byte[] wepRackpos, String path) {

		int wepRackRow = wepRackpos[1];
		int wepRackCol = wepRackpos[2];
		
		if (wepRackpos[0] == 0){
			if (linha == wepRackRow && coluna == wepRackCol) {
				path = "/imagens/wepRack/WepRackR0.png";
			} else if(linha == wepRackRow+1 && coluna == wepRackCol){
				path = "/imagens/wepRack/WepRackR1.png";
			} else if(linha == wepRackRow+2 && coluna == wepRackCol){
				path = "/imagens/wepRack/WepRackR2.png";
			}
		} else {
			if (linha == wepRackRow && coluna == wepRackCol) {
				path = "/imagens/wepRack/WepRackL0.png";
			} else if(linha == wepRackRow+1 && coluna == wepRackCol){
				path = "/imagens/wepRack/WepRackL1.png";
			} else if(linha == wepRackRow+2 && coluna == wepRackCol){
				path = "/imagens/wepRack/WepRackL2.png";
			}
		}
		return path;
	}
	
	private String deskPath(BasicMap map, int linha, int coluna, byte[] deskpos, String path) {

		int deskRow = deskpos[1];
		int deskCol = deskpos[2];
		
		if (deskpos[0] == 0){ // facing right
			if (linha == deskRow && coluna == deskCol) {
				path = "/imagens/desk/DeskR00.png";
			} else if (linha == deskRow && coluna == deskCol+1) {
				path = "/imagens/desk/DeskR01.png";
			} else if (linha == deskRow+1 && coluna == deskCol) {
				path = "/imagens/desk/DeskR10.png";
			} else if (linha == deskRow+1 && coluna == deskCol+1) {
				path = "/imagens/desk/DeskR11.png";
			} else if (linha == deskRow+2 && coluna == deskCol) {
				path = "/imagens/desk/DeskR20.png";
			} else if (linha == deskRow+2 && coluna == deskCol+1) {
				path = "/imagens/desk/DeskR21.png";
			}
		} else {
			if (linha == deskRow && coluna == deskCol) {
				path = "/imagens/desk/DeskL00.png";
			} else if (linha == deskRow && coluna == deskCol+1) {
				path = "/imagens/desk/DeskL01.png";
			} else if (linha == deskRow+1 && coluna == deskCol) {
				path = "/imagens/desk/DeskL10.png";
			} else if (linha == deskRow+1 && coluna == deskCol+1) {
				path = "/imagens/desk/DeskL11.png";
			} else if (linha == deskRow+2 && coluna == deskCol) {
				path = "/imagens/desk/DeskL20.png";
			} else if (linha == deskRow+2 && coluna == deskCol+1) {
				path = "/imagens/desk/DeskL21.png";
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
