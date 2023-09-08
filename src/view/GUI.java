package view;

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

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class GUI extends JFrame implements GUIInterface {

	private static final long serialVersionUID = 1L;
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
		EventQueue.invokeLater(() -> {
            try {
                new QuestSelector();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	public GUI() {
	}

	public void setHeroQuest(HeroQuest game) {
		this.heroQuest = game;
	}

	public void generateBoardButtons() {
		this.boardButtons = new JButton[29][38];
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 38; j++) {
				this.boardButtons[i][j] = new JButton();
			}
		}
	}

	public void generateCreatureButtons() {
		this.creatureButtons = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			this.creatureButtons.add(new JButton());
		}
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
		game.setGui(this);
		this.heroQuest = game;
		BasicMap map = this.heroQuest.getMap();
		this.boardButtons = new JButton[map.getTotalNumberOfRows()][map.getTotalNumberOfColumns()];
		this.creatureButtons = new ArrayList<>();
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
		btnInstructions.addActionListener(arg0 -> {
            Instructions instr = null;
            try {
                instr = new Instructions();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
				Objects.requireNonNull(instr).setVisible(true);
			}
        });
		mnHelp.add(btnInstructions);

		JButton btnCharSelect = new JButton(Strings.SELECT_CHARACTER.toString());
		btnCharSelect.addActionListener(arg0 -> {
            try {
                selectCharacter();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

		mnHelp.add(btnCharSelect);

		JMenu mnSettings = new JMenu(Strings.SETTINGS.toString());
		menuBar.add(mnSettings);

		JButton btnMusic = new JButton(Strings.TOGGLE_MUSIC.toString());
		mnSettings.add(btnMusic);
		
		JButton btnLanguage = new JButton(Strings.LANGUAGE_BUTTON.toString());
		btnLanguage.addActionListener(arg0 -> {
            LanguageSelector ls = new LanguageSelector(getThis());
            ls.setVisible(true);
        });
		mnSettings.add(btnLanguage);
		btnMusic.addActionListener(arg0 -> toggleMusic());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		setFocusable(true);
		requestFocusInWindow();

		for (int j = 0; j < map.getTotalNumberOfColumns(); j++) {
			JButton columnNumberButtons = new JButton();
			columnNumberButtons.setText(""+j);
			columnNumberButtons.setBounds(173 + (j * 23), 112 + (-1 * 23), 23, 23);
			columnNumberButtons.setBorder(emptyBorder);
			columnNumberButtons.setVisible(true);
			contentPane.add(columnNumberButtons);
		}
		for (int i = 0; i < map.getTotalNumberOfRows(); i++) {
			JButton rowNumberButtons = new JButton();
			rowNumberButtons.setText(""+i);
			rowNumberButtons.setBounds(173 + (-1 * 23), 112 + (i * 23), 23, 23);
			rowNumberButtons.setBorder(emptyBorder);
			rowNumberButtons.setVisible(true);
			contentPane.add(rowNumberButtons);
		}
		
		for (int i = 0; i < map.getTotalNumberOfRows(); i++) {
			for (int j = 0; j < map.getTotalNumberOfColumns(); j++) {
				JButton gameBoardButtons = new JButton();
				gameBoardButtons.setName("" + i + j);
				gameBoardButtons.setBounds(173 + (j * 23), 112 + (i * 23), 23, 23);
				gameBoardButtons.setBorder(emptyBorder);
				gameBoardButtons.setVisible(true);
				gameBoardButtons.addKeyListener(listener);
				gameBoardButtons.addActionListener(e -> openDoor(Integer.parseInt(gameBoardButtons.getName())));
				contentPane.add(gameBoardButtons);
				this.boardButtons[i][j] = gameBoardButtons;
			}
		}

		JButton creatureQueueButton = new JButton("");
		creatureQueueButton.setVisible(false);
		this.creatureButtons.add(creatureQueueButton);
		for (int i = 1; i <= map.getCreatureQueueSize(); i++) {
			JButton remainingCreatureQueueButtons = new JButton();
			remainingCreatureQueueButtons.setName("" + i);
			remainingCreatureQueueButtons.setBounds(0, 27 * (i - 1) + 89, 150, 27);
			remainingCreatureQueueButtons.addActionListener(e -> showCreatureInformation(Integer.parseInt(remainingCreatureQueueButtons.getName())));
			remainingCreatureQueueButtons.addKeyListener(listener);
			this.creatureButtons.add(i, remainingCreatureQueueButtons);
			this.contentPane.add(remainingCreatureQueueButtons);
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
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		ImageIcon iconConnectButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(
				"/images/buttons/ConnectButton.png")));
		this.connectButton = new JButton(iconConnectButton);
		connectButton.setEnabled(false);
		this.connectButton.setBounds(22, 0, 120, 89);
		this.connectButton.setBorder(emptyBorder);
		this.connectButton.addActionListener(e -> connectToServer());
		this.connectButton.addKeyListener(listener);
		this.contentPane.add(this.connectButton);

		ImageIcon iconDisconnectButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(
				"/images/buttons/DisconnectButton.png")));
		this.disconnectButton = new JButton(iconDisconnectButton);
		disconnectButton.setEnabled(false);
		this.disconnectButton.setBounds(22 * 2 + 120, 0, 120, 89);
		this.disconnectButton.setBorder(emptyBorder);
		this.disconnectButton.addActionListener(e -> disconnectFromServer());
		this.disconnectButton.addKeyListener(listener);
		this.contentPane.add(this.disconnectButton);

		ImageIcon iconStartGameButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(
				"/images/buttons/StartGameButton.png")));
		this.startGameButton = new JButton(iconStartGameButton);
		startGameButton.setEnabled(false);
		this.startGameButton.setBounds(22 * 3 + 120 * 2, 0, 120, 89);
		this.startGameButton.setBorder(emptyBorder);
		this.startGameButton.addActionListener(e -> startGame());
		this.startGameButton.addKeyListener(listener);
		this.contentPane.add(this.startGameButton);

		ImageIcon iconEndTurnButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(
				"/images/buttons/EndTurnButton.png")));
		this.endTurnButton = new JButton(iconEndTurnButton);
		endTurnButton.setEnabled(false);
		this.endTurnButton.setBounds(22 * 4 + 120 * 3, 0, 120, 89);
		this.endTurnButton.setBorder(emptyBorder);
		this.endTurnButton.addActionListener(e -> endTurn());
		this.endTurnButton.addKeyListener(listener);
		this.contentPane.add(this.endTurnButton);

		ImageIcon iconShowInventoryButton = new ImageIcon(Objects.requireNonNull(getClass()
				.getResource("/images/buttons/ShowInventoryButton.png")));
		this.showInventoryButton = new JButton(iconShowInventoryButton);
		showInventoryButton.setEnabled(false);
		this.showInventoryButton.setBounds(22 * 5 + 120 * 4, 0, 120, 89);
		this.showInventoryButton.setBorder(emptyBorder);
		this.showInventoryButton.addActionListener(e -> showInventory());
		this.showInventoryButton.addKeyListener(listener);
		this.contentPane.add(this.showInventoryButton);

		ImageIcon iconAttackButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(
				"/images/buttons/AttackButton.png")));
		this.attackButton = new JButton(iconAttackButton);
		attackButton.setEnabled(false);
		this.attackButton.setBounds(22 * 6 + 120 * 5, 0, 120, 89);
		this.attackButton.setBorder(emptyBorder);
		this.attackButton.addActionListener(e -> attack());
		this.attackButton.addKeyListener(listener);
		this.contentPane.add(this.attackButton);

		ImageIcon iconCastSpellButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(
				"/images/buttons/CastSpellButton.png")));
		this.useSpellButton = new JButton(iconCastSpellButton);
		useSpellButton.setEnabled(false);
		this.useSpellButton.setBounds(22 * 7 + 120 * 6, 0, 120, 89);
		this.useSpellButton.setBorder(emptyBorder);
		this.useSpellButton.addActionListener(e -> castSpell());
		this.useSpellButton.addKeyListener(listener);
		this.contentPane.add(this.useSpellButton);

		ImageIcon iconSearchForTrapsAndHiddenDoorsButton = new ImageIcon(Objects.requireNonNull(getClass()
				.getResource("/images/buttons/SearchForTrapsAndHiddenDoorsButton.png")));
		this.searchForTrapsButton = new JButton(iconSearchForTrapsAndHiddenDoorsButton);
		searchForTrapsButton.setEnabled(false);
		this.searchForTrapsButton.setBounds(22 * 8 + 120 * 7, 0, 120, 89);
		this.searchForTrapsButton.setBorder(emptyBorder);
		this.searchForTrapsButton.addActionListener(e -> searchForTrapsAndHiddenDoors());
		this.searchForTrapsButton.addKeyListener(listener);
		this.contentPane.add(searchForTrapsButton);

		ImageIcon iconSearchForTreasureButton = new ImageIcon(Objects.requireNonNull(getClass()
				.getResource("/images/buttons/SearchForTreasureButton.png")));
		this.searchForTreasureButton = new JButton(iconSearchForTreasureButton);
		searchForTreasureButton.setEnabled(false);
		this.searchForTreasureButton.setBounds(22 * 9 + 120 * 8, 0, 120, 89);
		this.searchForTreasureButton.setBorder(emptyBorder);
		this.searchForTreasureButton.addActionListener(e -> searchForTreasure());
		this.searchForTreasureButton.addKeyListener(listener);
		this.contentPane.add(this.searchForTreasureButton);
	}

	public void selectCharacter() throws ClassNotFoundException {
		this.heroQuest.selectCharacter();
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
		StringBuilder inputDialog = new StringBuilder(Strings.SELECT_SPELL.toString());
		for (int i = 0; i < availableSpells.size(); i++) {
			inputDialog.append("\n").append(i).append(" - ").append(SpellNameEnum.getNameById(availableSpells.get(i).getSpellId()));
		}
		String option = JOptionPane.showInputDialog(inputDialog.toString());
		int index = Integer.parseInt(option);
		return availableSpells.get(index);
	}

	public Creature selectTarget(ArrayList<Creature> availableTargets) {
		StringBuilder inputDialog = new StringBuilder(Strings.SELECT_TARGET.toString());
		for (int i = 0; i < availableTargets.size(); i++) {
			inputDialog.append("\n").append(i).append(" - ").append(availableTargets.get(i).getClass().getSimpleName());
		}
		String option = JOptionPane.showInputDialog(inputDialog.toString());
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

	public void searchForTreasure() {
		this.heroQuest.searchForTreasure();
	}

	public void showCharacterSelectionScreen() {
		CharacterSelection cs = new CharacterSelection(this);
		cs.setVisible(true);
	}

	public void searchForTrapsAndHiddenDoors() {
		this.heroQuest.searchForTrapsAndHiddenDoors();
	}

	public void endTurn() {
		this.heroQuest.endTurn();
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
					(Strings.INPUT_SERVER_ADDRESS.toString()), serverAddress);
		}
		return serverAddress;
	}

	public void connectToServer() {
		boolean isConnected = this.heroQuest.isConnected();
		if (!isConnected) {
			String serverAddress = this.obtainServerAddress();
			String playerName = obtainPlayerName();
			boolean success = this.heroQuest.getClientServerProxy().connect(serverAddress, playerName);
			if (success) {
				this.heroQuest.setConnected(true);
				this.heroQuest.setLocalPlayerName(playerName);
				this.heroQuest.setServerAddress(serverAddress);
				showConnectionResultMessage(ConnectionResultEnum.SUCCESSFUL_CONNECTION.getId());
				showQuestDescription();
			} else {
				showConnectionResultMessage(ConnectionResultEnum.FAILED_TO_CONNECT.getId());
			}
		} else {
			showConnectionResultMessage(ConnectionResultEnum.ALREADY_CONNECTED.getId());
		}
	}

	private void showQuestDescription() {
		this.textArea.setText(this.heroQuest.getMap().description);
	}

	public void showConnectionResultMessage(int result) {
		String connectionResultMessage;
		switch (result) {
			case 0:
				connectionResultMessage = Strings.SUCCESSFUL_CONNECTION.toString();
				break;
			case 1:
				connectionResultMessage = Strings.ALREADY_CONNECTED.toString();
				break;
			case 2:
				connectionResultMessage = Strings.FAILED_TO_CONNECT.toString();
				break;
			case 3:
				connectionResultMessage = Strings.SUCCESSFUL_DISCONNECTION.toString();
				break;
			case 4:
				connectionResultMessage = Strings.DISCONNECTION_ATTEMPT_BEFORE_BEING_CONNECTED.toString();
				break;
			case 5:
				connectionResultMessage = Strings.FAILED_DISCONNECTION.toString();
				break;
			case 6:
				connectionResultMessage = Strings.SUCCESSFUL_START.toString();
				break;
			case 7:
				connectionResultMessage = Strings.START_ATTEMPT_BEFORE_CONNECTING.toString();
				break;
			case 13:
				connectionResultMessage = Strings.GAME_NOT_INTERRUPTED.toString();
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
			boolean success = this.heroQuest.getClientServerProxy().disconnect();
			if (success) {
				this.heroQuest.setConnected(false);
				showConnectionResultMessage(ConnectionResultEnum.SUCCESSFUL_DISCONNECTION.getId());
			} else {
				showConnectionResultMessage(ConnectionResultEnum.FAILED_DISCONNECTION.getId());
			}
		} else {
			showConnectionResultMessage(ConnectionResultEnum.DISCONNECTION_ATTEMPT_BEFORE_BEING_CONNECTED.getId());
		}
	}

	public void startGame() {
		boolean isConnected = false;
		boolean isInterrupted = false;
		boolean inSession = this.heroQuest.isGameInSession();
		if (inSession) {
			isInterrupted = true;
		} else {
			isConnected = this.heroQuest.isConnected();
		}
		if (isInterrupted || isConnected) {
			int numberOfPlayersInGame = this.setTotalNumberOfPlayersInTheGame();
			this.heroQuest.getClientServerProxy().startGame(numberOfPlayersInGame);
			showConnectionResultMessage(ConnectionResultEnum.SUCCESSFUL_START.getId());
		}
		if (!isConnected) {
			showConnectionResultMessage(ConnectionResultEnum.START_ATTEMPT_BEFORE_CONNECTING.getId());
		}
		showConnectionResultMessage(ConnectionResultEnum.GAME_NOT_INTERRUPTED.getId());
	}

	public void announceHeroesWon() {
		this.textArea.setText(Strings.HEROES_WON.toString());
	}

	public void announceZargonWon() {
		this.textArea.setText(Strings.ZARGON_WON.toString());
	}

	public void showInventory() {
		this.heroQuest.showInventory();
	}

	public void showInventory(int gold, ArrayList<ItemEnum> items) {
		StringBuilder itemList = new StringBuilder(Strings.ITEMS_OWNED.toString());
		for (ItemEnum item : items){
			itemList.append(item).append("\n");
		}
		this.textArea.setText(Strings.YOU_HAVE.toString() + gold
				+ Strings.COINS_IN_INVENTORY + itemList);
	}

	public void showCreatureInformation(int creatureId) {
		this.heroQuest.showCreatureInformation(creatureId);
	}

	public void showCreatureInformation(byte body, byte mind, byte movement,
										StatusEnum statusEnum, int row, int column, Byte roundsToSleep) {
		String output = Strings.CURRENT_BP.toString() + body
				+ Strings.CURRENT_MP + mind + Strings.REMAINING_MOVEMENT
				+ movement + Strings.CURRENT_STATUS + statusEnum + Strings.ROW + row
				+ Strings.COLUMN + column;
		if (roundsToSleep != null && roundsToSleep != 0){
			output += Strings.TURNS_LEFT_TO_WAKE_UP.toString() + roundsToSleep;
		}
		this.textArea.setText(output);
	}

	public int setTotalNumberOfPlayersInTheGame() {
		int numberOfPlayersInGame;
		do {
			String numberOfPlayers = JOptionPane
					.showInputDialog(Strings.TYPE_THE_NUMBER_OF_PLAYERS.toString());
			numberOfPlayersInGame = Integer.parseInt(numberOfPlayers);
		} while (numberOfPlayersInGame < 2);
		return numberOfPlayersInGame;
	}

	public void refreshTile(JButton button, Position position) {
		String path = "";
		int positionRow = position.getRow();
		int positionColumn = position.getColumn();
		if (!position.isVisible()) {
			path = "/images/tiles/Wall.png";

		} else {
			Creature creatureInPosition = position.getCreature();
			if (creatureInPosition != null) {
				if (creatureInPosition instanceof PlayableCharacter) {
					path = "/images/players/";
				} else if (creatureInPosition instanceof SirRagnar) {
					path = "/images/allies/";
				} else if (creatureInPosition instanceof Monster) {
					path = "/images/enemies/";
				}
				path += position.getCreature().getClass().getSimpleName()
						+ ".png";
			} else if (position.getTrap() != null) {
					if (position.getTrap().getVisible()) {
						if (position.getTrap() instanceof FallingRock && position.getTrap().getTriggered()) {
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
				BasicMap map = this.heroQuest.getMap();
				int stairRow = map.getStairsPosition()[0];
				int stairColumn = map.getStairsPosition()[1];
				if (stairRow != 0) {
					if (positionRow == stairRow && positionColumn == stairColumn) {
						path = "/images/stairs/Stairs00.png";
					} else if (positionRow == stairRow && positionColumn == stairColumn+1) {
						path = "/images/stairs/Stairs01.png";
					} else if (positionRow == stairRow+1 && positionColumn == stairColumn) {
						path = "/images/stairs/Stairs10.png";
					} else if (positionRow == stairRow+1 && positionColumn == stairColumn+1) {
						path = "/images/stairs/Stairs11.png";
					}
				}
				
				if (map.getPosition((byte)positionRow, (byte)positionColumn) instanceof Furniture) {
					
					byte[] furnitureStartingPosition = map.getTable1Position();
					if (furnitureStartingPosition != null){
						path = generateTablePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					furnitureStartingPosition = map.getTable2Position();
					if (furnitureStartingPosition != null){
						path = generateTablePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getRackPosition();
					if (furnitureStartingPosition != null){
						path = generateRackPath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getBookOnTablePosition();
					if (furnitureStartingPosition != null){
						path = generateBookOnTablePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getTombPosition();
					if (furnitureStartingPosition != null){
						path = generateTombPath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getThronePosition();
					if (furnitureStartingPosition != null){
						path = generateThronePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getWepRackPosition();
					if (furnitureStartingPosition != null){
						path = generateWeaponRackPath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getDeskPosition();
					if (furnitureStartingPosition != null){
						path = generateDeskPath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getFireplacePosition();
					if (furnitureStartingPosition != null){
						path = generateFireplacePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getBookcase1Position();
					if (furnitureStartingPosition != null){
						path = generateBookcasePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getBookcase2Position();
					if (furnitureStartingPosition != null){
						path = generateBookcasePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
					
					furnitureStartingPosition = map.getBookcase3Position();
					if (furnitureStartingPosition != null){
						path = generateBookcasePath(positionRow, positionColumn, furnitureStartingPosition, path);
					}
				}
				
			}
		}
		ImageIcon img = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
		button.setIcon(img);
		button.invalidate();
		button.revalidate();
		button.repaint();
	}

	private String generateTablePath(int row, int column, byte[] tablePosition, String path) {
		int tableRow = tablePosition[1];
		int tableColumn = tablePosition[2];
		
		if (tablePosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == tableRow && column == tableColumn) {
				path = "/images/tables/TableH00.png";
			} else if (row == tableRow && column == tableColumn+1) {
				path = "/images/tables/TableH01.png";
			} else if (row == tableRow && column == tableColumn+2) {
				path = "/images/tables/TableH02.png";
			} else if (row == tableRow+1 && column == tableColumn) {
				path = "/images/tables/TableH10.png";
			} else if (row == tableRow+1 && column == tableColumn+1) {
				path = "/images/tables/TableH11.png";
			} else if (row == tableRow+1 && column == tableColumn+2) {
				path = "/images/tables/TableH12.png";
			}
		} else {
			if (row == tableRow && column == tableColumn) {
				path = "/images/tables/TableV00.png";
			} else if (row == tableRow && column == tableColumn+1) {
				path = "/images/tables/TableV01.png";
			} else if (row == tableRow+1 && column == tableColumn) {
				path = "/images/tables/TableV10.png";
			} else if (row == tableRow+1 && column == tableColumn+1) {
				path = "/images/tables/TableV11.png";
			} else if (row == tableRow+2 && column == tableColumn) {
				path = "/images/tables/TableV20.png";
			} else if (row == tableRow+2 && column == tableColumn+1) {
				path = "/images/tables/TableV21.png";
			}
		}
		return path;
	}
	
	private String generateRackPath(int row, int column, byte[] rackPosition, String path) {
		int rackRow = rackPosition[1];
		int rackColumn = rackPosition[2];
		
		if (rackPosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == rackRow && column == rackColumn) {
				path = "/images/racks/RackH00.png";
			} else if (row == rackRow && column == rackColumn+1) {
				path = "/images/racks/RackH01.png";
			} else if (row == rackRow && column == rackColumn+2) {
				path = "/images/racks/RackH02.png";
			} else if (row == rackRow+1 && column == rackColumn) {
				path = "/images/racks/RackH10.png";
			} else if (row == rackRow+1 && column == rackColumn+1) {
				path = "/images/racks/RackH11.png";
			} else if (row == rackRow+1 && column == rackColumn+2) {
				path = "/images/racks/RackH12.png";
			}
		} else {
			if (row == rackRow && column == rackColumn) {
				path = "/images/racks/RackV00.png";
			} else if (row == rackRow && column == rackColumn+1) {
				path = "/images/racks/RackV01.png";
			} else if (row == rackRow+1 && column == rackColumn) {
				path = "/images/racks/RackV10.png";
			} else if (row == rackRow+1 && column == rackColumn+1) {
				path = "/images/racks/RackV11.png";
			} else if (row == rackRow+2 && column == rackColumn) {
				path = "/images/racks/RackV20.png";
			} else if (row == rackRow+2 && column == rackColumn+1) {
				path = "/images/racks/RackV21.png";
			}
		}
		return path;
	}
	
	private String generateBookOnTablePath(int row, int column, byte[] bookOnTablePosition, String path) {
		int bookOnTableRow = bookOnTablePosition[1];
		int bookOnTableColumn = bookOnTablePosition[2];
		
		if (bookOnTablePosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == bookOnTableRow && column == bookOnTableColumn) {
				path = "/images/bookOnTable/BookOnTable01.png";
			} else if (row == bookOnTableRow && column == bookOnTableColumn+1) {
				path = "/images/bookOnTable/BookOnTable11.png";
			} else if (row == bookOnTableRow && column == bookOnTableColumn+2) {
				path = "/images/bookOnTable/BookOnTable21.png";
			} else if (row == bookOnTableRow+1 && column == bookOnTableColumn) {
				path = "/images/bookOnTable/BookOnTable00.png";
			} else if (row == bookOnTableRow+1 && column == bookOnTableColumn+1) {
				path = "/images/bookOnTable/BookOnTable10.png";
			} else if (row == bookOnTableRow+1 && column == bookOnTableColumn+2) {
				path = "/images/bookOnTable/BookOnTable20.png";
			}
		} else {
			if (row == bookOnTableRow && column == bookOnTableColumn) {
				path = "/images/bookOnTable/BookOnTable00.png";
			} else if (row == bookOnTableRow && column == bookOnTableColumn+1) {
				path = "/images/bookOnTable/BookOnTable01.png";
			} else if (row == bookOnTableRow+1 && column == bookOnTableColumn) {
				path = "/images/bookOnTable/BookOnTable10.png";
			} else if (row == bookOnTableRow+1 && column == bookOnTableColumn+1) {
				path = "/images/bookOnTable/BookOnTable11.png";
			} else if (row == bookOnTableRow+2 && column == bookOnTableColumn) {
				path = "/images/bookOnTable/BookOnTable20.png";
			} else if (row == bookOnTableRow+2 && column == bookOnTableColumn+1) {
				path = "/images/bookOnTable/BookOnTable21.png";
			}
		}
		return path;
	}
	
	private String generateTombPath(int row, int column, byte[] tombPosition, String path) {
		int tombRow = tombPosition[1];
		int tombColumn = tombPosition[2];
		
		if (tombPosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == tombRow && column == tombColumn) {
				path = "/images/tomb/Tomb01.png";
			} else if (row == tombRow && column == tombColumn+1) {
				path = "/images/tomb/Tomb11.png";
			} else if (row == tombRow && column == tombColumn+2) {
				path = "/images/tomb/Tomb21.png";
			} else if (row == tombRow+1 && column == tombColumn) {
				path = "/images/tomb/Tomb00.png";
			} else if (row == tombRow+1 && column == tombColumn+1) {
				path = "/images/tomb/Tomb10.png";
			} else if (row == tombRow+1 && column == tombColumn+2) {
				path = "/images/tomb/Tomb20.png";
			}
		} else {
			if (row == tombRow && column == tombColumn) {
				path = "/images/tomb/Tomb00.png";
			} else if (row == tombRow && column == tombColumn+1) {
				path = "/images/tomb/Tomb01.png";
			} else if (row == tombRow+1 && column == tombColumn) {
				path = "/images/tomb/Tomb10.png";
			} else if (row == tombRow+1 && column == tombColumn+1) {
				path = "/images/tomb/Tomb11.png";
			} else if (row == tombRow+2 && column == tombColumn) {
				path = "/images/tomb/Tomb20.png";
			} else if (row == tombRow+2 && column == tombColumn+1) {
				path = "/images/tomb/Tomb21.png";
			}
		}
		return path;
	}
	
	private String generateThronePath(int row, int column, byte[] thronePosition, String path) {
		int throneRow = thronePosition[1];
		int throneColumn = thronePosition[2];
		
		if (thronePosition[0] == FurnitureDirectionEnum.RIGHT.getId()) {
			if (row == throneRow && column == throneColumn) {
				path = "/images/throne/ThroneR.png";
			}
		} else {
			if (row == throneRow && column == throneColumn) {
				path = "/images/throne/ThroneL.png";
			}
		}
		return path;
	}
	
	private String generateWeaponRackPath(int row, int column, byte[] weaponRackPosition, String path) {
		int weaponRackRow = weaponRackPosition[1];
		int weaponRackColumn = weaponRackPosition[2];
		
		if (weaponRackPosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == weaponRackRow && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackR0.png";
			} else if (row == weaponRackRow+1 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackR1.png";
			} else if (row == weaponRackRow+2 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackR2.png";
			}
		} else {
			if (row == weaponRackRow && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackL0.png";
			} else if (row == weaponRackRow+1 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackL1.png";
			} else if (row == weaponRackRow+2 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackL2.png";
			}
		}
		return path;
	}
	
	private String generateDeskPath(int row, int column, byte[] deskPosition, String path) {
		int deskRow = deskPosition[1];
		int deskCol = deskPosition[2];
		
		if (deskPosition[0] == FurnitureDirectionEnum.RIGHT.getId()) {
			if (row == deskRow && column == deskCol) {
				path = "/images/desk/DeskR00.png";
			} else if (row == deskRow && column == deskCol+1) {
				path = "/images/desk/DeskR01.png";
			} else if (row == deskRow+1 && column == deskCol) {
				path = "/images/desk/DeskR10.png";
			} else if (row == deskRow+1 && column == deskCol+1) {
				path = "/images/desk/DeskR11.png";
			} else if (row == deskRow+2 && column == deskCol) {
				path = "/images/desk/DeskR20.png";
			} else if (row == deskRow+2 && column == deskCol+1) {
				path = "/images/desk/DeskR21.png";
			}
		} else {
			if (row == deskRow && column == deskCol) {
				path = "/images/desk/DeskL00.png";
			} else if (row == deskRow && column == deskCol+1) {
				path = "/images/desk/DeskL01.png";
			} else if (row == deskRow+1 && column == deskCol) {
				path = "/images/desk/DeskL10.png";
			} else if (row == deskRow+1 && column == deskCol+1) {
				path = "/images/desk/DeskL11.png";
			} else if (row == deskRow+2 && column == deskCol) {
				path = "/images/desk/DeskL20.png";
			} else if (row == deskRow+2 && column == deskCol+1) {
				path = "/images/desk/DeskL21.png";
			}
		}
		return path;
	}
	
	private String generateFireplacePath(int row, int column, byte[] fireplacePosition, String path) {
		int fireplaceRow = fireplacePosition[1];
		int fireplaceCol = fireplacePosition[2];
		
		if (fireplacePosition[0] == FurnitureDirectionEnum.DOWN.getId()) {
			if (row == fireplaceRow && column == fireplaceCol) {
				path = "/images/fireplace/FireplaceD0.png";
			} else if (row == fireplaceRow && column == fireplaceCol+1) {
				path = "/images/fireplace/FireplaceD1.png";
			} else if (row == fireplaceRow && column == fireplaceCol+2) {
				path = "/images/fireplace/FireplaceD2.png";
			}
		} else {
			if (row == fireplaceRow && column == fireplaceCol) {
				path = "/images/fireplace/FireplaceU0.png";
			} else if (row == fireplaceRow && column == fireplaceCol+1) {
				path = "/images/fireplace/FireplaceU1.png";
			} else if (row == fireplaceRow && column == fireplaceCol+2) {
				path = "/images/fireplace/FireplaceU2.png";
			}
		}
		return path;
	}
	
	private String generateBookcasePath(int row, int column, byte[] bookcasePosition, String path) {
		int bookcaseRow = bookcasePosition[1];
		int bookcaseColumn = bookcasePosition[2];
		
		if (bookcasePosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == bookcaseRow && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseH0.png";
			} else if (row == bookcaseRow && column == bookcaseColumn+1) {
				path = "/images/bookcase/BookcaseH1.png";
			} else if (row == bookcaseRow && column == bookcaseColumn+2) {
				path = "/images/bookcase/BookcaseH2.png";
			}
		} else {
			if (row == bookcaseRow && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseV0.png";
			} else if (row == bookcaseRow+1 && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseV1.png";
			} else if (row == bookcaseRow+2 && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseV2.png";
			}
		}
		return path;
	}

	private void refreshCreatureInQueue(JButton button, Creature creature, int buttonPositionInQueue) {
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
		this.textArea.setText(Strings.OH_NO
						+ creature.getClass().getSimpleName()
						+ Strings.ACTIVATED_TRAP + damage
						+ Strings.OF_BP);
	}

	public void showAttackDamageMessage(Creature target, byte damage, boolean selfInflicted) {
		if (selfInflicted) {
			this.textArea.setText(Strings.THE_CREATURE
					+ target.getClass().getSimpleName()
					+ Strings.ATTEMPTS_SEPPUKU + damage + Strings.OF_DAMAGE);
		} else {
			this.textArea.setText(Strings.THE_CREATURE
					+ target.getClass().getSimpleName() + Strings.RECEIVED + damage
					+ Strings.OF_DAMAGE);
		}
	}

	public void announceCreatureDeath(Creature creature) {
		this.textArea.setText(Strings.THE_CREATURE
				+ creature.getClass().getSimpleName()
				+ Strings.DIED_HONORABLY);
	}

	public void showEffectOfCastSpell(Creature caster, Spell spell, Creature target,
									  byte damage, StatusEnum statusEnum) {
		if (statusEnum != null) {
			this.textArea.setText(Strings.THE
					+ caster.getClass().getSimpleName()
					+ Strings.WHISPERED_SPELL + SpellNameEnum.getNameById(spell.getSpellId())
					+ Strings.AND_THE_CREATURE + target.getClass().getSimpleName()
					+ Strings.MODIFIED_IN + damage
					+ Strings.BP_MODIFIED_STATUS + statusEnum
					+ Strings.EXCLAMATION_MARK);
		} else {
			this.textArea.setText(Strings.THE
					+ caster.getClass().getSimpleName()
					+ Strings.WHISPERED_SPELL + SpellNameEnum.getNameById(spell.getSpellId())
					+ Strings.AND_THE_CREATURE + target.getClass().getSimpleName()
					+ Strings.MODIFIED_IN + damage + Strings.BP_MODIFIED_NOT_STATUS);
		}

	}

	public void announceUnfortunateDeath(Creature creature) {
		this.textArea.setText(Strings.OH_NO + " " + Strings.THE_CREATURE
				+ creature.getClass().getSimpleName()
				+ Strings.WAS_KILLED_BY_TRAP);
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
		StringBuilder availableDoors = new StringBuilder(Strings.SELECT_DOOR.toString());
		for (int i = 0; i < doorIds.size(); i++) {
			availableDoors.append(i).append(" - ").append(doorIds.get(i)).append("\n");
		}
		String chosenDoorId = JOptionPane.showInputDialog(availableDoors.toString());
		return Integer.parseInt(chosenDoorId);

	}

	public void createMusic() throws Exception {
		String f = "/music/Castlevania Symphony of the Night Track 03 Dance Of Illusions.wav";
		AudioInputStream audioIn = null;
		
		try {
			audioIn = AudioSystem.getAudioInputStream(getClass().getResource(f));
		} catch (IOException | UnsupportedAudioFileException e2) {
			e2.printStackTrace();
		}
        final Clip clip = AudioSystem.getClip();

		try {
			clip.open(audioIn);
		} catch (LineUnavailableException | IOException e) {
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
		String input = Strings.ROCK_FALL.toString();
		input += "0 - " + Strings.FORWARD;
		input += "1 - " + Strings.BACKWARD;
		String option = JOptionPane.showInputDialog(input);
		byte selectedMovement = Byte.parseByte(option);
		return TrapEvasionMovementEnum.getEnumById(selectedMovement);
	}
	
	public TrapEvasionMovementEnum showPitJumpingOptions() {
		String input = Strings.PIT_JUMP.toString();
		input += "0 - " + Strings.YES;
		input += "1 - " + Strings.NO;
		String option = JOptionPane.showInputDialog(input);
		byte selectedMovement = Byte.parseByte(option);
		return TrapEvasionMovementEnum.getEnumById(selectedMovement);
	}
	
	public void setLanguage(LanguageEnum lang) {
		language = lang;
	}
	
	public GUI getThis(){
		return this;
	}
	
	// Bad, look for alternatives
	public void updateLanguageButtons() {
		JMenuBar mBar = this.menuBar;
		mBar.getMenu(0).setText(Strings.MENU.toString());
		((JButton) mBar.getMenu(0).getAccessibleContext().getAccessibleChild(0)).setText(Strings.INSTRUCTIONS.toString());
		((JButton) mBar.getMenu(0).getAccessibleContext().getAccessibleChild(1)).setText(Strings.SELECT_CHARACTER.toString());
		mBar.getMenu(1).setText(Strings.SETTINGS.toString());
		((JButton) mBar.getMenu(1).getAccessibleContext().getAccessibleChild(0)).setText(Strings.TOGGLE_MUSIC.toString());
		((JButton) mBar.getMenu(1).getAccessibleContext().getAccessibleChild(1)).setText(Strings.LANGUAGE_BUTTON.toString());
	}
	
	public void writeSaveFile(String playerName, int heroType, int gold, ArrayList<ItemEnum> items) throws IOException {
		// Retrieve current working directory
		String currentDir = System.getProperty("user.dir");

		// Create new directory for the save files, if not yet created
		String saveDir = currentDir + "/HeroQuest_Saves/";
		new File(saveDir).mkdir();

		FileOutputStream fos = new FileOutputStream(saveDir + playerName + ".txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new SaveFile(heroType, gold, items));
		oos.close();
		fos.close();
	}
	
	public ArrayList<String> readSaveFile(String playerName) throws IOException, ClassNotFoundException {
		
		ArrayList<String> returnValues = new ArrayList<>();
		
		ArrayList<String> results = new ArrayList<>();

		String currentDir = System.getProperty("user.dir");
		
		String saveDir = currentDir + "/HeroQuest_Saves/";

		File[] files = new File(saveDir).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		if (files != null) {
			for (File file : files) {
			    if (file.isFile()) {
			        results.add(file.getName());
			    }
			}

            for (String res : results) {
                if (res.endsWith(playerName + ".txt")) {

                    FileInputStream fis = new FileInputStream(saveDir + res);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    SaveFile sf = (SaveFile) ois.readObject();
                    ois.close();
                    fis.close();

                    returnValues.add(sf.getCharacterClass() + "");
                    returnValues.add(sf.getGold() + "");
                    returnValues.add(sf.getItems() + "");

                    break;
                }
            }
		}
		return returnValues;
	}
	

	public boolean checkSaveFileExists(String playerName) {
		ArrayList<String> results = new ArrayList<>();

		String currentDir = System.getProperty("user.dir");
		
		String saveDir = currentDir + "/HeroQuest_Saves/";

		File[] files = new File(saveDir).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		if (files != null) {
			for (File file : files) {
			    if (file.isFile()) {
			        results.add(file.getName());
			    }
			}

            for (String res : results) {
                if (res.endsWith(playerName + ".txt")) {
                    return true;
                }
            }
		}
		return false;
	}
}
