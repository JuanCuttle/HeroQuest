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

import entities.tiles.Door;
import entities.tiles.FallingRock;
import entities.tiles.Furniture;
import entities.HeroQuest;
import entities.Position;
import entities.utils.Strings;

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
					HeroQuest game = new HeroQuest();
					BasicMap map = new TheFireMage(game);
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
			path = "/images/" + "Wall" + ".png";

		} else {
			if (posicao.getCreature() != null) {
				path = "/images/"
						+ posicao.getCreature().getClass().getSimpleName()
						+ ".png";

			} else if (posicao.getTrap() != null) {
					if (posicao.getTrap().getVisible()) {
						if (posicao.getTrap() instanceof FallingRock && posicao.getTrap().getTriggered()){
							path = "/images/"
									+ "Rubble"
									+ ".png";
						} else {
							path = "/images/"
									+ posicao.getTrap().getClass().getSimpleName()
									+ ".png";
						}
					} else {
						path = "/images/" + posicao.getClass().getSimpleName()
								+ ".png";
					}
			} else {
				if (posicao instanceof Door) {
					if (!((Door) posicao).isSecret()) {
						if (((Door) posicao).isOpen()) {
							path = "/images/tiles/doors/OpenDoor.png";
						} else {
							path = "/images/tiles/doors/ClosedDoor.png";
						}
					} else {
						path = "/images/" + "Wall" + ".png";
					}
				} else {
					path = "/images/" + posicao.getClass().getSimpleName()
							+ ".png";
				}
				int stairRow = map.getStairsPosition()[0];
				int stairColumn = map.getStairsPosition()[1];
				if (stairRow != 0){
					if (linha == stairRow && coluna == stairColumn) {
						path = "/images/stairs/Stairs00.png";
					} else if (linha == stairRow && coluna == stairColumn+1) {
						path = "/images/stairs/Stairs01.png";
					} else if (linha == stairRow+1 && coluna == stairColumn) {
						path = "/images/stairs/Stairs10.png";
					} else if (linha == stairRow+1 && coluna == stairColumn+1) {
						path = "/images/stairs/Stairs11.png";
					}
				}
				
				if (map.getPosition((byte)linha, (byte)coluna) instanceof Furniture){
					
					byte[] pos = map.getTable1Position();
					if (pos != null){
						path = tablePath(map, linha, coluna, pos, path);
					}
					pos = map.getTable2Position();
					if (pos != null){
						path = tablePath(map, linha, coluna, pos, path);
					}
					
					pos = map.getRackPosition();
					if (pos != null){
						path = rackPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getBookOnTablePosition();
					if (pos != null){
						path = botPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getTombPosition();
					if (pos != null){
						path = tombPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getThronePosition();
					if (pos != null){
						path = thronePath(map, linha, coluna, pos, path);
					}
					
					pos = map.getWepRackPosition();
					if (pos != null){
						path = wepRackPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getDeskPosition();
					if (pos != null){
						path = deskPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getFireplacePosition();
					if (pos != null){
						path = fireplacePath(map, linha, coluna, pos, path);
					}
					
					pos = map.getBookcase1Position();
					if (pos != null){
						path = bcPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getBookcase2Position();
					if (pos != null){
						path = bcPath(map, linha, coluna, pos, path);
					}
					
					pos = map.getBookcase3Position();
					if (pos != null){
						path = bcPath(map, linha, coluna, pos, path);
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
				path = "/images/tables/TableH00.png";
			} else if (linha == tableRow && coluna == tableCol+1) {
				path = "/images/tables/TableH01.png";
			} else if (linha == tableRow && coluna == tableCol+2) {
				path = "/images/tables/TableH02.png";
			} else if (linha == tableRow+1 && coluna == tableCol) {
				path = "/images/tables/TableH10.png";
			} else if (linha == tableRow+1 && coluna == tableCol+1) {
				path = "/images/tables/TableH11.png";
			} else if (linha == tableRow+1 && coluna == tableCol+2) {
				path = "/images/tables/TableH12.png";
			}
		} else {
			if (linha == tableRow && coluna == tableCol) {
				path = "/images/tables/TableV00.png";
			} else if (linha == tableRow && coluna == tableCol+1) {
				path = "/images/tables/TableV01.png";
			} else if (linha == tableRow+1 && coluna == tableCol) {
				path = "/images/tables/TableV10.png";
			} else if (linha == tableRow+1 && coluna == tableCol+1) {
				path = "/images/tables/TableV11.png";
			} else if (linha == tableRow+2 && coluna == tableCol) {
				path = "/images/tables/TableV20.png";
			} else if (linha == tableRow+2 && coluna == tableCol+1) {
				path = "/images/tables/TableV21.png";
			}
		}
		return path;
	}
	
	private String rackPath(BasicMap map, int linha, int coluna, byte[] rackpos, String path) {

		int rackRow = rackpos[1];
		int rackCol = rackpos[2];
		
		if (rackpos[0] == 0){
			if (linha == rackRow && coluna == rackCol) {
				path = "/images/racks/RackH00.png";
			} else if (linha == rackRow && coluna == rackCol+1) {
				path = "/images/racks/RackH01.png";
			} else if (linha == rackRow && coluna == rackCol+2) {
				path = "/images/racks/RackH02.png";
			} else if (linha == rackRow+1 && coluna == rackCol) {
				path = "/images/racks/RackH10.png";
			} else if (linha == rackRow+1 && coluna == rackCol+1) {
				path = "/images/racks/RackH11.png";
			} else if (linha == rackRow+1 && coluna == rackCol+2) {
				path = "/images/racks/RackH12.png";
			}
		} else {
			if (linha == rackRow && coluna == rackCol) {
				path = "/images/racks/RackV00.png";
			} else if (linha == rackRow && coluna == rackCol+1) {
				path = "/images/racks/RackV01.png";
			} else if (linha == rackRow+1 && coluna == rackCol) {
				path = "/images/racks/RackV10.png";
			} else if (linha == rackRow+1 && coluna == rackCol+1) {
				path = "/images/racks/RackV11.png";
			} else if (linha == rackRow+2 && coluna == rackCol) {
				path = "/images/racks/RackV20.png";
			} else if (linha == rackRow+2 && coluna == rackCol+1) {
				path = "/images/racks/RackV21.png";
			}
		}
		return path;
	}
	
	private String botPath(BasicMap map, int linha, int coluna, byte[] botpos, String path) {

		int botRow = botpos[1];
		int botCol = botpos[2];
		
		if (botpos[0] == 0){
			if (linha == botRow && coluna == botCol) { // Change if and when there is this piece horizontally or facing other direction
				path = "/images/bookOnTable/RackH00.png";
			} else if (linha == botRow && coluna == botCol+1) {
				path = "/images/bookOnTable/RackH01.png";
			} else if (linha == botRow && coluna == botCol+2) {
				path = "/images/bookOnTable/RackH02.png";
			} else if (linha == botRow+1 && coluna == botCol) {
				path = "/images/bookOnTable/RackH10.png";
			} else if (linha == botRow+1 && coluna == botCol+1) {
				path = "/images/bookOnTable/RackH11.png";
			} else if (linha == botRow+1 && coluna == botCol+2) {
				path = "/images/bookOnTable/RackH12.png";
			}
		} else {
			if (linha == botRow && coluna == botCol) {
				path = "/images/bookOnTable/BookOnTable00.png";
			} else if (linha == botRow && coluna == botCol+1) {
				path = "/images/bookOnTable/BookOnTable01.png";
			} else if (linha == botRow+1 && coluna == botCol) {
				path = "/images/bookOnTable/BookOnTable10.png";
			} else if (linha == botRow+1 && coluna == botCol+1) {
				path = "/images/bookOnTable/BookOnTable11.png";
			} else if (linha == botRow+2 && coluna == botCol) {
				path = "/images/bookOnTable/BookOnTable20.png";
			} else if (linha == botRow+2 && coluna == botCol+1) {
				path = "/images/bookOnTable/BookOnTable21.png";
			}
		}
		return path;
	}
	
	private String tombPath(BasicMap map, int linha, int coluna, byte[] tombpos, String path) {

		int tombRow = tombpos[1];
		int tombCol = tombpos[2];
		
		if (tombpos[0] == 0){
			if (linha == tombRow && coluna == tombCol) { // Change if and when there is this piece horizontally
				path = "/images/tomb/RackH00.png";
			} else if (linha == tombRow && coluna == tombCol+1) {
				path = "/images/tomb/RackH01.png";
			} else if (linha == tombRow && coluna == tombCol+2) {
				path = "/images/tomb/RackH02.png";
			} else if (linha == tombRow+1 && coluna == tombCol) {
				path = "/images/tomb/RackH10.png";
			} else if (linha == tombRow+1 && coluna == tombCol+1) {
				path = "/images/tomb/RackH11.png";
			} else if (linha == tombRow+1 && coluna == tombCol+2) {
				path = "/images/tomb/RackH12.png";
			}
		} else {
			if (linha == tombRow && coluna == tombCol) {
				path = "/images/tomb/Tomb00.png";
			} else if (linha == tombRow && coluna == tombCol+1) {
				path = "/images/tomb/Tomb01.png";
			} else if (linha == tombRow+1 && coluna == tombCol) {
				path = "/images/tomb/Tomb10.png";
			} else if (linha == tombRow+1 && coluna == tombCol+1) {
				path = "/images/tomb/Tomb11.png";
			} else if (linha == tombRow+2 && coluna == tombCol) {
				path = "/images/tomb/Tomb20.png";
			} else if (linha == tombRow+2 && coluna == tombCol+1) {
				path = "/images/tomb/Tomb21.png";
			}
		}
		return path;
	}
	
	private String thronePath(BasicMap map, int linha, int coluna, byte[] thronepos, String path) {

		int throneRow = thronepos[1];
		int throneCol = thronepos[2];
		
		if (thronepos[0] == 0){
			if (linha == throneRow && coluna == throneCol) {
				path = "/images/throne/ThroneR.png";
			}
		} else {
			if (linha == throneRow && coluna == throneCol) {
				path = "/images/throne/ThroneL.png";
			}
		}
		return path;
	}
	
	private String wepRackPath(BasicMap map, int linha, int coluna, byte[] wepRackpos, String path) {

		int wepRackRow = wepRackpos[1];
		int wepRackCol = wepRackpos[2];
		
		if (wepRackpos[0] == 0){
			if (linha == wepRackRow && coluna == wepRackCol) {
				path = "/images/wepRack/WepRackR0.png";
			} else if(linha == wepRackRow+1 && coluna == wepRackCol){
				path = "/images/wepRack/WepRackR1.png";
			} else if(linha == wepRackRow+2 && coluna == wepRackCol){
				path = "/images/wepRack/WepRackR2.png";
			}
		} else {
			if (linha == wepRackRow && coluna == wepRackCol) {
				path = "/images/wepRack/WepRackL0.png";
			} else if(linha == wepRackRow+1 && coluna == wepRackCol){
				path = "/images/wepRack/WepRackL1.png";
			} else if(linha == wepRackRow+2 && coluna == wepRackCol){
				path = "/images/wepRack/WepRackL2.png";
			}
		}
		return path;
	}
	
	private String deskPath(BasicMap map, int linha, int coluna, byte[] deskpos, String path) {

		int deskRow = deskpos[1];
		int deskCol = deskpos[2];
		
		if (deskpos[0] == 0){ // facing right
			if (linha == deskRow && coluna == deskCol) {
				path = "/images/desk/DeskR00.png";
			} else if (linha == deskRow && coluna == deskCol+1) {
				path = "/images/desk/DeskR01.png";
			} else if (linha == deskRow+1 && coluna == deskCol) {
				path = "/images/desk/DeskR10.png";
			} else if (linha == deskRow+1 && coluna == deskCol+1) {
				path = "/images/desk/DeskR11.png";
			} else if (linha == deskRow+2 && coluna == deskCol) {
				path = "/images/desk/DeskR20.png";
			} else if (linha == deskRow+2 && coluna == deskCol+1) {
				path = "/images/desk/DeskR21.png";
			}
		} else {
			if (linha == deskRow && coluna == deskCol) {
				path = "/images/desk/DeskL00.png";
			} else if (linha == deskRow && coluna == deskCol+1) {
				path = "/images/desk/DeskL01.png";
			} else if (linha == deskRow+1 && coluna == deskCol) {
				path = "/images/desk/DeskL10.png";
			} else if (linha == deskRow+1 && coluna == deskCol+1) {
				path = "/images/desk/DeskL11.png";
			} else if (linha == deskRow+2 && coluna == deskCol) {
				path = "/images/desk/DeskL20.png";
			} else if (linha == deskRow+2 && coluna == deskCol+1) {
				path = "/images/desk/DeskL21.png";
			}
		}
		return path;
	}
	
	private String fireplacePath(BasicMap map, int linha, int coluna, byte[] fireplacepos, String path) {

		int fireplaceRow = fireplacepos[1];
		int fireplaceCol = fireplacepos[2];
		
		if (fireplacepos[0] == 0){ // facing down
			if (linha == fireplaceRow && coluna == fireplaceCol) {
				path = "/images/fireplace/FireplaceD0.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+1){
				path = "/images/fireplace/FireplaceD1.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+2){
				path = "/images/fireplace/FireplaceD2.png";
			}
		} else {
			if (linha == fireplaceRow && coluna == fireplaceCol) {
				path = "/images/fireplace/FireplaceU0.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+1){
				path = "/images/fireplace/FireplaceU1.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+2){
				path = "/images/fireplace/FireplaceU2.png";
			}
		}
		return path;
	}
	
	private String bcPath(BasicMap map, int linha, int coluna, byte[] bcpos, String path) {

		int bcRow = bcpos[1];
		int bcCol = bcpos[2];
		
		if (bcpos[0] == 0){ // horizontal
			if (linha == bcRow && coluna == bcCol) {
				path = "/images/bookcase/BookcaseH0.png";
			} else if(linha == bcRow && coluna == bcCol+1){
				path = "/images/bookcase/BookcaseH1.png";
			} else if(linha == bcRow && coluna == bcCol+2){
				path = "/images/bookcase/BookcaseH2.png";
			}
		} else { // vertical
			if (linha == bcRow && coluna == bcCol) {
				path = "/images/bookcase/BookcaseV0.png";
			} else if(linha == bcRow+1 && coluna == bcCol){
				path = "/images/bookcase/BookcaseV1.png";
			} else if(linha == bcRow+2 && coluna == bcCol){
				path = "/images/bookcase/BookcaseV2.png";
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
