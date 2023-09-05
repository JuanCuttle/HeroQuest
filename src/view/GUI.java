package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import entities.*;
import entities.enemies.Monster;
import entities.players.PlayableCharacter;
import entities.tiles.Door;
import entities.tiles.FallingRock;
import entities.tiles.Furniture;
import entities.utils.Strings;
import enums.*;
import interfaces.GUIInterface;
import quests.BasicMap;

public class GUI extends JFrame implements GUIInterface {

	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected HeroQuest heroQuest;
	protected JButton[][] boardButtons;
	protected JButton connectButton;
	protected JButton disconnectButton;
	protected JButton startGameButton;
	protected JButton endTurnButton;
	protected JButton showInventoryButton;
	protected JButton attackButton;
	protected JButton useSpellButton;
	protected JButton searchForTrapsButton;
	protected JButton searchForTreasureButton;
	protected ArrayList<JButton> creatureButtons;
	protected JMenuBar menuBar;
	protected TextArea textArea;

	public KeyboardListener listener = new KeyboardListener(this);
	public MusicThread musicThread;
	
	public static LanguageEnum language = LanguageEnum.English;

	public static Boolean autoConnectToServer = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new QuestSelector();
/*					AtorJogador frame = new AtorJogador();
					frame.setVisible(true);
					BasicMap map = frame.heroQuest.getMap();
					if (map instanceof TheTrial){
						frame.textArea.setText(Strings.THETRIAL.toString());
						//JOptionPane.showMessageDialog(null, Strings.THETRIAL);
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI(HeroQuest game) {
		try {
			createMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				GUI.class.getResource("/images/players/Wizard.png")));
		setTitle(Strings.HEROQUEST.toString());
		
		// GUI attributes
		game.setAtorJogador(this);
		this.heroQuest = game;//new HeroQuest(this);
		BasicMap map = this.heroQuest.getMap();
		this.boardButtons = new JButton[map.getTotalNumberOfRows()][map.getTotalNumberOfColumns()];
		this.creatureButtons = new ArrayList<JButton>();
		addKeyListener(listener);

		// Configure the window
		// Max size of creature turn viewer
		int maxCreatureQueueSize = 27 * (map.getCreatureQueueSize() + 2) + 89;
		setSize(1300, Math.max(835, maxCreatureQueueSize));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnHelp = new JMenu(Strings.MENU.toString());
		menuBar.add(mnHelp);

		JButton btnInstructions = new JButton(Strings.INSTRUCTIONS.toString());
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Instructions instr = null;
				try {
					instr = new Instructions();
				} catch (IOException e) {
					e.printStackTrace();
				}
				instr.setVisible(true);
			}
		});
		mnHelp.add(btnInstructions);

		JButton btnCharSelect = new JButton(Strings.SELECTCHAR.toString());
		btnCharSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					chooseCharacter();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mnHelp.add(btnCharSelect);

		JMenu mnSettings = new JMenu(Strings.SETTINGS.toString());
		menuBar.add(mnSettings);

		JButton btnMusic = new JButton(Strings.TRIGGERMUSIC.toString());
		mnSettings.add(btnMusic);
		
		JButton btnLanguage = new JButton(Strings.LANGUAGEBUTTON.toString());
		btnLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LanguageSelector ls = new LanguageSelector(getThis());
				ls.setVisible(true);
			}
		});
		mnSettings.add(btnLanguage);
		btnMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggleMusic();
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		Border invisivel = BorderFactory.createEmptyBorder();
		setFocusable(true);
		requestFocusInWindow();

		// Create Column and Row index buttons, to orient the player
		for (int j = 0; j < map.getTotalNumberOfColumns(); j++) {
			JButton botao = new JButton();
			botao.setText(""+j);
			botao.setBounds(173 + (j * 23), 112 + (-1 * 23), 23, 23);
			botao.setBorder(invisivel);
			botao.setVisible(true);
			contentPane.add(botao);
		}
		for (int i = 0; i < map.getTotalNumberOfRows(); i++) {
			JButton botao = new JButton();
			botao.setText(""+i);
			botao.setBounds(173 + (-1 * 23), 112 + (i * 23), 23, 23);
			botao.setBorder(invisivel);
			botao.setVisible(true);
			contentPane.add(botao);
		}
		
		// Create game board buttons
		for (int i = 0; i < map.getTotalNumberOfRows(); i++) {
			for (int j = 0; j < map.getTotalNumberOfColumns(); j++) {
				JButton botao = new JButton();
				botao.setName("" + i + j);
				botao.setBounds(173 + (j * 23), 112 + (i * 23), 23, 23);
				botao.setBorder(invisivel);
				botao.setVisible(true);
				botao.addKeyListener(listener);
				botao.addActionListener(e -> openDoor(Integer.parseInt(botao.getName())));
				contentPane.add(botao);
				this.boardButtons[i][j] = botao;
			}
		}

		// Create creature queue buttons
		JButton creatureQueueButton = new JButton("");
		creatureQueueButton.setVisible(false);
		this.creatureButtons.add(creatureQueueButton);
		for (int i = 1; i <= map.getCreatureQueueSize(); i++) {
			JButton button = new JButton();
			button.setName("" + i);
			button.setBounds(0, 27 * (i - 1) + 89, 150, 27);
			button.addActionListener(e -> showCreatureInformation(Integer.parseInt(button.getName())));
			button.addKeyListener(listener);
			this.creatureButtons.add(i, button);
			this.contentPane.add(button);
		}
		
		this.textArea = new TextArea();
		textArea.setFont(new Font("Viner Hand ITC", Font.BOLD, 15));
		textArea.setBounds(0, 0, 1294, 89);
		contentPane.add(textArea);
		
		if (autoConnectToServer){
			this.connectToServer();
		}
	}
	
	@SuppressWarnings("unused")
	private void generateActionButtons(){
		Border invisivel = BorderFactory.createEmptyBorder();
		
		ImageIcon iconeConectar = new ImageIcon(getClass().getResource(
				"/images/buttons/BotaoConectar.png"));
		this.connectButton = new JButton(iconeConectar);
		connectButton.setEnabled(false);
		this.connectButton.setBounds(22 * 1 + 120 * 0, 0, 120, 89);
		this.connectButton.setBorder(invisivel);
		this.connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToServer();
			}
		});
		this.connectButton.addKeyListener(listener);
		this.contentPane.add(this.connectButton);

		ImageIcon iconeDesconectar = new ImageIcon(getClass().getResource(
				"/images/buttons/BotaoDesconectar.png"));
		this.disconnectButton = new JButton(iconeDesconectar);
		disconnectButton.setEnabled(false);
		this.disconnectButton.setBounds(22 * 2 + 120 * 1, 0, 120, 89);
		this.disconnectButton.setBorder(invisivel);
		this.disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disconnectFromServer();
			}
		});
		this.disconnectButton.addKeyListener(listener);
		this.contentPane.add(this.disconnectButton);

		ImageIcon iconeIniciarPartida = new ImageIcon(getClass().getResource(
				"/images/buttons/BotaoIniciarPartida.png"));
		this.startGameButton = new JButton(iconeIniciarPartida);
		startGameButton.setEnabled(false);
		this.startGameButton.setBounds(22 * 3 + 120 * 2, 0, 120, 89);
		this.startGameButton.setBorder(invisivel);
		this.startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		this.startGameButton.addKeyListener(listener);
		this.contentPane.add(this.startGameButton);

		ImageIcon iconeFinalizarJogada = new ImageIcon(getClass().getResource(
				"/images/buttons/BotaoFinalizarJogada.png"));
		this.endTurnButton = new JButton(iconeFinalizarJogada);
		endTurnButton.setEnabled(false);
		this.endTurnButton.setBounds(22 * 4 + 120 * 3, 0, 120, 89);
		this.endTurnButton.setBorder(invisivel);
		this.endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endTurn();
			}
		});
		this.endTurnButton.addKeyListener(listener);
		this.contentPane.add(this.endTurnButton);

		ImageIcon iconeBotaoMostrarInventario = new ImageIcon(getClass()
				.getResource("/images/buttons/BotaoMostrarInventario.png"));
		this.showInventoryButton = new JButton(iconeBotaoMostrarInventario);
		showInventoryButton.setEnabled(false);
		this.showInventoryButton.setBounds(22 * 5 + 120 * 4, 0, 120, 89);
		this.showInventoryButton.setBorder(invisivel);
		this.showInventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInventory();
			}
		});
		this.showInventoryButton.addKeyListener(listener);
		this.contentPane.add(this.showInventoryButton);

		ImageIcon iconeBotaoAtacar = new ImageIcon(getClass().getResource(
				"/images/buttons/BotaoAtacar.png"));
		this.attackButton = new JButton(iconeBotaoAtacar);
		attackButton.setEnabled(false);
		this.attackButton.setBounds(22 * 6 + 120 * 5, 0, 120, 89);
		this.attackButton.setBorder(invisivel);
		this.attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				attack();
			}
		});
		this.attackButton.addKeyListener(listener);
		this.contentPane.add(this.attackButton);

		ImageIcon iconeBotaoUsarMagia = new ImageIcon(getClass().getResource(
				"/images/buttons/BotaoUsarMagia.png"));
		this.useSpellButton = new JButton(iconeBotaoUsarMagia);
		useSpellButton.setEnabled(false);
		this.useSpellButton.setBounds(22 * 7 + 120 * 6, 0, 120, 89);
		this.useSpellButton.setBorder(invisivel);
		this.useSpellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				castSpell();
			}
		});
		this.useSpellButton.addKeyListener(listener);
		this.contentPane.add(this.useSpellButton);

		ImageIcon iconeBotaoProcurarArmadilha = new ImageIcon(getClass()
				.getResource("/images/buttons/BotaoProcurarArmadilha.png"));
		this.searchForTrapsButton = new JButton(iconeBotaoProcurarArmadilha);
		searchForTrapsButton.setEnabled(false);
		this.searchForTrapsButton.setBounds(22 * 8 + 120 * 7, 0, 120, 89);
		this.searchForTrapsButton.setBorder(invisivel);
		this.searchForTrapsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTrapsAndHiddenDoors();
			}
		});
		this.searchForTrapsButton.addKeyListener(listener);
		this.contentPane.add(searchForTrapsButton);

		ImageIcon iconeBotaoProcurarTesouro = new ImageIcon(getClass()
				.getResource("/images/buttons/BotaoProcurarTesouro.png"));
		this.searchForTreasureButton = new JButton(iconeBotaoProcurarTesouro);
		searchForTreasureButton.setEnabled(false);
		this.searchForTreasureButton.setBounds(22 * 9 + 120 * 8, 0, 120, 89);
		this.searchForTreasureButton.setBorder(invisivel);
		this.searchForTreasureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchTreasure();
			}
		});
		this.searchForTreasureButton.addKeyListener(listener);
		this.contentPane.add(this.searchForTreasureButton);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void chooseCharacter() throws ClassNotFoundException {
		this.heroQuest.selecionarPersonagem();
	}

	public void openDoorWithKeyboard() {
		this.heroQuest.openDoorWithKeyboard();
	}

	public void openDoor(int doorId) {
		this.heroQuest.openDoor(doorId);
	}

	public void showMessagePopup(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void reportError(String msg) {
		this.textArea.setText(msg);
	}

	public void moveCreature(DirectionEnum direction) {
		this.heroQuest.move(direction);
	}

	public void attack() {
		this.heroQuest.attack();
	}

	public void castSpell() {
		this.heroQuest.castSpell();
	}

	public Spell selectSpell(ArrayList<Spell> availableSpells) {
		String inputDialog = Strings.SELECT_SPELL.toString();
		for (int i = 0; i < availableSpells.size(); i++) {
			inputDialog += "\n" + i + " - "
					+ SpellNameEnum.getEnumNameById(availableSpells.get(i).getSpellId());
		}
		String option = JOptionPane.showInputDialog(inputDialog);
		int index = Integer.parseInt(option);
		return availableSpells.get(index);
	}

	public Creature selectTarget(ArrayList<Creature> availableTargets) {
		String inputDialog = Strings.SELECT_TARGET.toString();
		for (int i = 0; i < availableTargets.size(); i++) {
			inputDialog += "\n" + i + " - "
					+ availableTargets.get(i).getClass().getSimpleName();
		}
		String option = JOptionPane.showInputDialog(inputDialog);
		int index = Integer.parseInt(option);
		return availableTargets.get(index);
	}

	public void refreshGUI() {
		for (byte i = 0; i < this.heroQuest.getMap().getTotalNumberOfRows(); i++) {
			for (byte j = 0; j < this.heroQuest.getMap().getTotalNumberOfColumns(); j++) {
				Position position = this.heroQuest.getPosition(i, j);
				this.refreshTile(this.boardButtons[i][j], position);
			}
		}
		this.showVisibleCreaturesInQueue();
	}

	public void searchTreasure() {
		this.heroQuest.procurarTesouro();
	}

	public void showCharacterSelectionScreen() {
		CharacterSelection cs = new CharacterSelection(this);
		cs.setVisible(true);
	}

	public void searchTrapsAndHiddenDoors() {
		this.heroQuest.procurarArmadilhaOuPortaSecreta();
	}

	public void endTurn() {
		this.heroQuest.finalizarJogada();
	}

	public String obtainPlayerName() {
		String playerName = JOptionPane
				.showInputDialog(Strings.INPUT_NAME.toString());
		return playerName;
	}

	public String obtainServerAddress() {
		String serverAddress = (Strings.OBTAIN_SERVER_ADDRESS.toString());
		if (!autoConnectToServer) {
			serverAddress = JOptionPane.showInputDialog(this,
					(Strings.INPUTSERVERADDRESS.toString()), serverAddress);
		}
		return serverAddress;
	}

	public void connectToServer() {
		boolean isConnected = this.heroQuest.isConnected();
		if (!isConnected) {
			String serverAddress = this.obtainServerAddress();
			String playerName = obtainPlayerName();
			boolean success = this.heroQuest.getAtorClienteServidor().conectar(
					serverAddress, playerName);
			if (success) {
				this.heroQuest.setConnected(true);
				this.heroQuest.setLocalPlayerName(playerName);
				this.heroQuest.setServerAddress(serverAddress);
				showConnectionResultMessage(ConnectionResultEnum.SUCCESSFULCONNECT.getId());
				showQuestDescription();
			} else {
				showConnectionResultMessage(ConnectionResultEnum.FAILEDCONNECT.getId());
			}
		} else {
			showConnectionResultMessage(ConnectionResultEnum.ALREADYCONNECTED.getId());
		}
	}

	private void showQuestDescription() {
		this.textArea.setText(this.heroQuest.getMap().description);
	}

	public void showConnectionResultMessage(int result) {
		String connectionResultMessage;
		switch (result) {
			case 0:
				connectionResultMessage = Strings.SUCCESSFULCONNECT.toString();
				break;
			case 1:
				connectionResultMessage = Strings.ALREADYCONNECTED.toString();
				break;
			case 2:
				connectionResultMessage = Strings.FAILEDCONNECT.toString();
				break;
			case 3:
				connectionResultMessage = Strings.SUCCESSFULDISCONNECT.toString();
				break;
			case 4:
				connectionResultMessage = Strings.DISCBEFORECONNECT.toString();
				break;
			case 5:
				connectionResultMessage = Strings.FAILEDDISCONNECT.toString();
				break;
			case 6:
				connectionResultMessage = Strings.SUCCESSFULSTART.toString();
				break;
			case 7:
				connectionResultMessage = Strings.STARTBEFORECONNECT.toString();
				break;
			case 13:
				connectionResultMessage = Strings.UNINTERRUPTEDGAME.toString();
				break;
			default:
				connectionResultMessage = "";
				break;
		}
		this.textArea.setText(connectionResultMessage);
	}

	public void disconnectFromServer() {
		boolean isConnected = this.heroQuest.isConnected();
		if (isConnected) {
			boolean success = this.heroQuest.getAtorClienteServidor().desconectar();
			if (success) {
				this.heroQuest.setConnected(false);
				showConnectionResultMessage(ConnectionResultEnum.SUCCESSFULDISCONNECT.getId());
			} else {
				showConnectionResultMessage(ConnectionResultEnum.FAILEDDISCONNECT.getId());
			}
		} else {
			showConnectionResultMessage(ConnectionResultEnum.DISCBEFORECONNECT.getId());
		}
	}

	public void startGame() {
		boolean isConnected = false;
		boolean isInterrupted = false;
		boolean inSession = this.heroQuest.getInSession();
		if (inSession) {
			isInterrupted = true;
		} else {
			isConnected = this.heroQuest.isConnected();
		}
		if (isInterrupted || ((!inSession) && isConnected)) {
			int numberOfPlayersInGame = this.setTotalNumberOfPlayersInTheGame();
			this.heroQuest.getAtorClienteServidor().iniciarPartida(numberOfPlayersInGame);
			showConnectionResultMessage(ConnectionResultEnum.SUCCESSFULSTART.getId());
		}
		if (!isConnected) {
			showConnectionResultMessage(ConnectionResultEnum.STARTBEFORECONNECT.getId());
		}
		showConnectionResultMessage(ConnectionResultEnum.UNINTERRUPTEDGAME.getId());
	}

	public void announceHeroesWon() {
		this.textArea.setText(Strings.HEROES_WON.toString());
	}

	public void announceZargonWon() {
		this.textArea.setText(Strings.ZARGON_WON.toString());
	}

	public String informarNomeJogador() {
		String nomeJogador = JOptionPane
				.showInputDialog(Strings.INPUT_NAME.toString());
		return nomeJogador;
	}

	public void showInventory() {
		this.heroQuest.mostrarInventario();
	}

	public void showInventory(int gold, ArrayList<Items> items) {
		String itemString = Strings.ITEMSOWNED.toString();
		for (Items item : items){
			itemString += item + "\n";
		}
		this.textArea.setText(Strings.YOUHAVE.toString() + gold
				+ Strings.INVYCOINS +itemString);
	}

	public void showCreatureInformation(int characterID) {
		this.heroQuest.mostrarInformacoes(characterID);
	}

	public void showCreatureInformation(byte body, byte mind, byte movement,
										StatusEnum statusEnum, int row, int column, Byte roundsToSleep) {
		String output = Strings.CURRENTBP.toString() + body
				+ Strings.CURRENTMP + mind + Strings.REMAININGMOVES
				+ movement + Strings.CURRENTSTATUS + statusEnum + Strings.LINE + row
				+ Strings.COLUMN + column;
		if (roundsToSleep != null){
			if (roundsToSleep != 0){
				output += Strings.TTW.toString() + roundsToSleep;
			}
		}
		this.textArea.setText(output);
	}

	public int setTotalNumberOfPlayersInTheGame() {
		int numberOfPlayersInGame;
		do {
			String numberOfPlayers = JOptionPane
					.showInputDialog(Strings.NUMBEROFPLAYERS.toString());
			numberOfPlayersInGame = Integer.parseInt(numberOfPlayers);
		} while (numberOfPlayersInGame < 2);
		return numberOfPlayersInGame;
	}

	public void refreshTile(JButton button, Position position) {
		ImageIcon img;
		String path = "";
		int linha = position.getRow();
		int coluna = position.getColumn();
		if (!position.isVisible()) {
			path = "images/tiles/Wall.png";

		} else {
			Creature creatureInPosition = position.getCreature();
			if (creatureInPosition != null) {
				if (creatureInPosition instanceof PlayableCharacter) {
					path = "/images/players/";
				} else if (creatureInPosition instanceof Monster) {
					path = "/images/enemies/";
				}
				path += position.getCreature().getClass().getSimpleName()
						+ ".png";
			} else if (position.getTrap() != null) {
					if (position.getTrap().getVisible()) {
						if (position.getTrap() instanceof FallingRock && position.getTrap().getTriggered()){
							path = "/images/tiles/traps/Rubble.png";
						} else {
							path = "/images/tiles/traps/"
									+ position.getTrap().getClass().getSimpleName()
									+ ".png";
						}
					} else {
						path = "/images/tiles/"
								+ position.getClass().getSimpleName()
								+ ".png";
					}
			} else {
				if (position instanceof Door) {
					if (!((Door) position).isSecret()) {
						if (((Door) position).isOpen()) {
							path = "/images/tiles/doors/OpenDoor.png";
						} else {
							path = "/images/tiles/doors/ClosedDoor.png";
						}
					} else {
						path = "/images/tiles/Wall.png";
					}
				} else {
					path = "/images/tiles/" + position.getClass().getSimpleName()
							+ ".png";
				}
				//Map map = this.heroQuest.getMap();
				BasicMap map = this.heroQuest.getMap();
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
		button.setIcon(img);
		button.invalidate();
		button.revalidate();
		button.repaint();
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
			if (linha == botRow && coluna == botCol) { // Change if and when there is this piece horizontally
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

	private void refreshCreatureInQueue(JButton button, Creature creature,
										int buttonPositionInQueue) {
		if (creature.isVisible()) {
			String creatureName = creature.getClass().getSimpleName();
			button.setText(creatureName);
		}
		button.setBounds(0, 27 * buttonPositionInQueue + 89, 150, 27);
		button.invalidate();
		button.revalidate();
		button.repaint();
	}

	public void showVisibleCreaturesInQueue() {
		// Each button was assigned to a creature via creature.ID at button initialize
		// For each creature in the queue, we find its button, and move it to its new position
		// in the GUI button list
		ArrayList<Creature> creaturesInQuest = this.heroQuest.getCreatureQueue();
		for (int i = 0; i < creaturesInQuest.size(); i++) {
			Creature creature = creaturesInQuest.get(i);
			this.refreshCreatureInQueue(this.creatureButtons.get(creature.getID()), creature, i);
		}
	}

	public void showTrapActivationMessage(byte damage, Creature creature) {
		this.textArea.setText(Strings.OHNO
						+ creature.getClass().getSimpleName()
						+ Strings.ACTIVATEDTRAP + damage
						+ Strings.OFBP);
	}

	public void showAttackDamageMessage(Creature target, byte damage, boolean selfInflicted) {
		if (selfInflicted) {
			this.textArea.setText(Strings.THECREATURE
					+ target.getClass().getSimpleName()
					+ Strings.ATTEMPTSSEPPUKU + damage + Strings.OFDAMAGE);
		} else {
			this.textArea.setText(Strings.THECREATURE
					+ target.getClass().getSimpleName() + Strings.RECEIVED + damage
					+ Strings.OFDAMAGE);
		}
	}

	public void announceCreatureDeath(Creature creature) {
		this.textArea.setText(Strings.THECREATURE
				+ creature.getClass().getSimpleName()
				+ Strings.DIEDHONORABLY);
	}

	public void showEffectOfCastSpell(Creature caster, Spell spell, Creature target,
									  byte damage, StatusEnum statusEnum) {
		if (statusEnum != null) {
			this.textArea.setText(Strings.THE
					+ caster.getClass().getSimpleName()
					+ Strings.MURMURED_SPELL + SpellNameEnum.getEnumNameById(spell.getSpellId())
					+ Strings.AND_THE_CREATURE + target.getClass().getSimpleName()
					+ Strings.MODIFIED_IN + damage
					+ Strings.BP_MODIFIED_STATUS + statusEnum
					+ Strings.EXCLAMATION_MARK);
		} else {
			this.textArea.setText(Strings.THE
					+ caster.getClass().getSimpleName()
					+ Strings.MURMURED_SPELL + SpellNameEnum.getEnumNameById(spell.getSpellId())
					+ Strings.AND_THE_CREATURE + target.getClass().getSimpleName()
					+ Strings.MODIFIED_IN + damage + Strings.BP_MODIFIED_NOT_STATUS);
		}

	}

	public void announceUnfortunateDeath(Creature creature) {
		this.textArea.setText(Strings.OHNO + " " + Strings.THECREATURE
				+ creature.getClass().getSimpleName()
				+ Strings.DIEDONTRAP);
	}

	public void updatePlayerSurroundings() {
		Creature currentCreature = this.heroQuest.getCurrentCreature();
		Position currentPosition = currentCreature.getCurrentPosition();
		byte currentRow = currentPosition.getRow();
		byte currentColumn = currentPosition.getColumn();

		for (byte i = (byte) (currentRow - 2); i <= currentRow + 2; i++) {
			for (byte j = (byte) (currentColumn - 2); j <= currentColumn + 2; j++) {
				if (i >= 0 && i < this.heroQuest.getMap().getTotalNumberOfRows() && j >= 0 && j < this.heroQuest.getMap().getTotalNumberOfColumns()) {
					Position position = this.heroQuest.getPosition(i, j);
					this.refreshTile(this.boardButtons[i][j], position);
				}
			}
		}
		this.showVisibleCreaturesInQueue(); //precisa?
	}

	public int selectDoorToOpenOrClose(ArrayList<String> doorIds) {
		String availableDoors = Strings.SELECT_DOOR.toString();
		for (int i = 0; i < doorIds.size(); i++) {
			availableDoors += i + " - " + doorIds.get(i) + "\n";
		}
		String chosenDoorId = JOptionPane.showInputDialog(availableDoors);
		return Integer.parseInt(chosenDoorId);

	}

	public void createMusic() throws Exception {
		String f = "/music/Castlevania Symphony of the Night Track 03 Dance Of Illusions.wav";
		AudioInputStream audioIn = null;
		
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getResource(f));
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (UnsupportedAudioFileException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		final Clip clip = AudioSystem.getClip();

		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.musicThread = new MusicThread(clip) {
		};
		SwingUtilities.invokeLater(musicThread);
	}

	public void toggleMusic() {
		this.musicThread.toggleMusic();
	}

	public void showTrapRemovalMessage() {
		this.textArea.setText(Strings.DWARF_DISARMED_TRAPS.toString());
	}

	public TrapEvasionMovementEnum showFallingRockMovementOptions() {
		String input = Strings.ROCKFALL.toString();
		input += "0 - " + Strings.FORWARD;
		input += "1 - " + Strings.BACKWARD;
		String option = JOptionPane.showInputDialog(input);
		int selectedMovement = Integer.parseInt(option);
		return TrapEvasionMovementEnum.getEnumById(selectedMovement);
	}
	
	public TrapEvasionMovementEnum showPitJumpingOptions() {
		String input = Strings.PIT_JUMP.toString();
		input += "0 - " + Strings.YES;
		input += "1 - " + Strings.NO;
		String option = JOptionPane.showInputDialog(input);
		int selectedMovement = Integer.parseInt(option);
		return TrapEvasionMovementEnum.getEnumById(selectedMovement);
	}
	
	public void setLanguage(LanguageEnum lang) {
		language = lang;
	}
	
	public GUI getThis(){
		return this;
	}
	
	// Bad, look for alternatives
	public void atualizarBotoesLingua(){
		JMenuBar mBar = this.menuBar;
		mBar.getMenu(0).setText(Strings.MENU.toString());
		((JButton) mBar.getMenu(0).getAccessibleContext().getAccessibleChild(0)).setText(Strings.INSTRUCTIONS.toString());
		((JButton) mBar.getMenu(0).getAccessibleContext().getAccessibleChild(1)).setText(Strings.SELECTCHAR.toString());
		mBar.getMenu(1).setText(Strings.SETTINGS.toString());
		((JButton) mBar.getMenu(1).getAccessibleContext().getAccessibleChild(0)).setText(Strings.TRIGGERMUSIC.toString());
		((JButton) mBar.getMenu(1).getAccessibleContext().getAccessibleChild(1)).setText(Strings.LANGUAGEBUTTON.toString());
	}
	
	public void writeSaveFile(String playerName, int heroType, int gold, ArrayList<Items> items) throws IOException{
		// Retrieve current working directory
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
				
		// Create new directory for the save files, if not yet created
		String saveDir = currentDir + "/HeroQuest_Saves/";
		boolean dir = new File(saveDir).mkdir();
		System.out.println(dir);
				
		FileOutputStream fos = new FileOutputStream(saveDir + playerName + ".txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new SaveFile(heroType, gold, items));
		oos.close();
		fos.close();
		
/*		// Write data into file
		FileWriter fw = new FileWriter(saveDir + playerName + ".txt");
		fw.write(heroType+"\t");
		fw.write(gold+"\t");
		fw.write(Status.AGILITY_DOWN+"\t");
		fw.write(Status.AGILITY_UP+"\t");
		fw.close();*/
	}
	
	public ArrayList<String> readSaveFile(String playerName) throws IOException, ClassNotFoundException{
		
		ArrayList<String> returnValues = new ArrayList<String>();
		
		ArrayList<String> results = new ArrayList<String>();

		String currentDir = System.getProperty("user.dir");
		
		String saveDir = currentDir + "/HeroQuest_Saves/";
		
		//																														name.endsWith(".txt")
		//File[] files = new File(saveDir).listFiles(new FilenameFilter() { @Override public boolean accept(File dir, String name) { return true; } });
		
		File[] files = new File(saveDir).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		if (files != null){
			for (File file : files) {
			    if (file.isFile()) {
			        results.add(file.getName());
			    }
			}
			
			for (int i = 0; i < results.size(); i++){
				String res = results.get(i);
				
				if (res.endsWith(playerName+".txt")){
					
					 FileInputStream fis = new FileInputStream(saveDir+res);
			         ObjectInputStream ois = new ObjectInputStream(fis);
			         SaveFile sf = (SaveFile) ois.readObject();
			         ois.close();
			         fis.close();
			         
			         returnValues.add(sf.getCharacterClass()+"");
			         returnValues.add(sf.getGold()+"");
			         returnValues.add(sf.getItems()+"");
			            
					/*// Open the file at /Saves/
					FileReader fr = new FileReader(saveDir+res);
					
					// Instantiate reading buffer and reads into it
					char[] cbuf = new char[(int) files[i].length()];
					fr.read(cbuf);
					
					// Close the file
					fr.close();
					
					// Convert buffer to string for splitting
					String content = "";
					for (char c : cbuf){
						content += c;
					}
					
					// Separate values using tab symbol
					String[] values = content.split("\t");
					
					// Retrieve information from file data
					//gold = Integer.parseInt(values[0]);
					
					for (String value : values){
						returnValues.add(value);
					}*/
					
					break;
				}
			}
			//System.out.println("Save files found: "+results);
		}
		return returnValues;
	}
	

	public boolean checkSaveFileExists(String playerName) {
		ArrayList<String> results = new ArrayList<String>();

		String currentDir = System.getProperty("user.dir");
		
		String saveDir = currentDir + "/HeroQuest_Saves/";
		
		//																														name.endsWith(".txt")
		//File[] files = new File(saveDir).listFiles(new FilenameFilter() { @Override public boolean accept(File dir, String name) { return true; } });
		
		File[] files = new File(saveDir).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		if (files != null){
			for (File file : files) {
			    if (file.isFile()) {
			        results.add(file.getName());
			    }
			}
			
			for (int i = 0; i < results.size(); i++){
				String res = results.get(i);
				
				if (res.endsWith(playerName+".txt")){
					//JOptionPane.showMessageDialog(null, Strings.FILEFOUND);
					return true;
				}
			}
		}
		//JOptionPane.showMessageDialog(null, Strings.FILENOTFOUND);
		return false;
	}

}
