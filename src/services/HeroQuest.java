/*
           .--.              .--.
          : (\ ". _......_ ." /) :
           '.    `        `    .'
            /'   _        _   `\
           /     0}      {0     \
          |       /      \       |
          |     /'        `\     |
           \   | .  .==.  . |   /
            '._ \.' \__/ './ _.'
            /  ``'._-''-_.'``  \
                    `--`

 	Essa classe est� sendo observada pelo Polar Warbear
 */
package services;

import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import entities.*;
import entities.actions.*;
import entities.enemies.Monster;
import entities.enemies.Mummy;
import entities.enemies.Skeleton;
import entities.enemies.Zombie;
import entities.players.*;
import entities.tiles.*;
import entities.utils.Strings;
import enums.*;
import interfaces.LogicInterface;
import quests.BasicMap;
import quests.MelarsMaze;
import view.ClientServerProxy;
import view.GUI;
import exceptions.PositionNotEmptyException;

import static enums.TrapEvasionMovementEnum.FALLEN_INTO_PIT;

public class HeroQuest implements LogicInterface {

	private BasicMap map;
	private ArrayList<Player> players;
	private GUI gui;
	private final ClientServerProxy clientServerProxy;
	private Player localPlayer;
	private ArrayList<Creature> creatureQueue;
	private ArrayList<Door> doors;
	private static boolean zargonAvailable;
	private static boolean barbarianAvailable;
	private static boolean wizardAvailable;
	private static boolean elfAvailable;
	private static boolean dwarfAvailable;
	private boolean connected;
	private boolean gameInSession;
	private Adventurer localAdventurer;
	private Zargon localZargon;
	private String localPlayerName = "";
	private OpenDoorService openDoorService;
	private MovementService movementService;
	private AttackService attackService;
	private CastSpellService castSpellService;
	private EndTurnService endTurnService;
	private SearchForTrapsAndHiddenDoorsService searchForTrapsAndHiddenDoorsService;


	public HeroQuest() {
		this.players = new ArrayList<>();
		this.clientServerProxy = new ClientServerProxy(this);
		this.creatureQueue = new ArrayList<>();
		this.doors = new ArrayList<>();
		zargonAvailable = true;
		barbarianAvailable = true;
		wizardAvailable = true;
		elfAvailable = true;
		dwarfAvailable = true;
		this.connected = false;
		this.gameInSession = false;
		this.localAdventurer = null;
		this.localZargon = null;
		this.endTurnService = new EndTurnService(this);
		this.openDoorService = new OpenDoorService(this);
		this.movementService = new MovementService(this, endTurnService);
		this.attackService = new AttackService(this);
		this.castSpellService = new CastSpellService(this);
		this.searchForTrapsAndHiddenDoorsService = new SearchForTrapsAndHiddenDoorsService(this);
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isGameInSession() {
		return this.gameInSession;
	}

	public void setGameInSession(boolean gameInSession) {
		this.gameInSession = gameInSession;
	}

	private boolean isBarbarianAvailable() {
		return barbarianAvailable;
	}

	private boolean isWizardAvailable() {
		return wizardAvailable;
	}

	private boolean isElfAvailable() {
		return elfAvailable;
	}

	private boolean isDwarfAvailable() {
		return dwarfAvailable;
	}

	private boolean getZargonAvailable() {
		return zargonAvailable;
	}

	private void setDwarfUnavailable() {
		dwarfAvailable = false;
	}

	private void setElfUnavailable() {
		elfAvailable = false;
	}

	private void setWizardUnavailable() {
		wizardAvailable = false;
	}

	private void setBarbarianUnavailable() {
		barbarianAvailable = false;
	}

	private void setZargonUnavailable() {
		zargonAvailable = false;
	}

	public Creature getCurrentCreature() {
		return this.creatureQueue.get(0);
	}

	public ArrayList<Creature> getCreatureQueue() {
		return this.creatureQueue;
	}

	public void sortCreatureQueueByID() {
		Collections.sort(this.creatureQueue);
	}

	public Creature removeCreatureFromQueue() {
		Creature creature = this.creatureQueue.remove(0);
		this.creatureQueue.trimToSize();
		return creature;
	}

	public void insertCreatureIntoQueue(Creature creature) {
		int index = this.creatureQueue.size();
		this.creatureQueue.add(index, creature);
	}

	public Player removePlayerFromQueue() {
		return this.players.remove(0);
	}

	public void insertPlayerIntoQueue(Player player) {
		int index = this.players.size();
		this.players.add(index, player);
	}

	public ArrayList<Player> getPlayerQueue() {
		return this.players;
	}

	public ArrayList<Door> getDoors() {
		return this.doors;
	}
	
	public void openDoor(int doorId) {
		String error = openDoorService.openDoor(doorId);
		if (error != null) {
			gui.reportError(error);
		}
	}
	
	public void openDoorWithKeyboard() {
		String error = openDoorService.openDoorWithKeyboard();
		if (error != null) {
			gui.reportError(error);
		}
	}

	public boolean verifyIfItIsCurrentPlayersTurn() {
		int localCreatureId, currentTurnCreatureId;
		currentTurnCreatureId = this.getCurrentCreature().getID();
		if (this.localAdventurer != null) {
			localCreatureId = (this.localAdventurer).getPlayableCharacter()
					.getID();
			return localCreatureId == currentTurnCreatureId;
		} else {
			for (int i = 0; i < this.creatureQueue.size() - players.size(); i++) {
				localCreatureId = (this.localZargon).getMonster(i).getID();
				if (currentTurnCreatureId == localCreatureId)
					return true;
			}
			return false;

		}
	}

	public void move(DirectionEnum direction) {
		String error = movementService.move(direction);
		if (error != null) {
			gui.reportError(error);
		}
	}

	public void attack() {
		String error = attackService.attack();
		if (error != null) {
			gui.reportError(error);
		}
	}

	public ArrayList<Creature> getAvailableTargets(int area, Position sourcePosition) {
		ArrayList<Creature> availableTargets = new ArrayList<>();
		byte sourceRow = sourcePosition.getRow();
		byte sourceColumn = sourcePosition.getColumn();
		for (int i = sourceRow - area; i <= sourceRow + area; i++) {
			for (int j = sourceColumn - area; j <= sourceColumn + area; j++) {
				if (i >= 0 && i < this.map.getTotalNumberOfRows() && j >= 0 && j < this.map.getTotalNumberOfColumns()) {
					Position position = this.map.getPosition((byte)i, (byte)j);
					if (position.getCreature() != null) {
						availableTargets.add(position.getCreature());
					}
				}
			}
		}
		return availableTargets;
	}

	public void castSpell() {
		String error = castSpellService.castSpell();
		if (error != null) {
			gui.reportError(error);
		}
	}

	public void sendAction(Action action) {
		clientServerProxy.sendAction(action);
	}

	public void processAction(Action action) {
		String actionType = action.getClass().getSimpleName();
		switch (ActionTypeEnum.getByName(actionType)) {
			case MOVEMENT:
				movementService.processMovement((Movement) action);
				break;
			case OPEN_DOOR:
				openDoorService.processOpenDoor((OpenDoor) action);
				break;
			case ATTACK:
				attackService.processAttack((Attack) action);
				endTurnService.processEndTurn();
				break;
			case SEND_PLAYER:
				processSendPlayer((SendPlayer) action);
				break;
			case END_TURN:
				endTurnService.processEndTurn();
				gui.showVisibleCreaturesInQueue();
				break;
			case CAST_SPELL:
				castSpellService.processCastSpell((CastSpell) action);
				endTurnService.processEndTurn();
				break;
			case SEARCH_FOR_TRAPS_AND_HIDDEN_DOORS:
				searchForTrapsAndHiddenDoorsService.processSearchForTrapsAndHiddenDoors((SearchForTrapsAndHiddenDoors) action);
				break;
			case SEARCH_FOR_TREASURE:
				this.processSearchForTreasure((SearchForTreasure) action);
				break;
			case SELECT_CHARACTER:
				this.processSelectCharacter((SelectCharacter) action);
				this.gui.refreshGUI();
				break;
		}
		this.gui.updatePlayerSurroundings();
	}
	
	private void processSelectCharacter(SelectCharacter action) {
		PlayableCharacter character;
		Adventurer playerA;
		byte[] position;
		byte selectedCharacterId = action.getSelectedCharacterId();

		switch (CharacterEnum.getEnumById(selectedCharacterId)) {
			case ZARGON:
				this.setZargonUnavailable();
				
				Zargon playerZ = action.getZargon();
				for (int i = 0; i < playerZ.getMonsters().size(); i++) {
					this.insertCreatureIntoQueue(playerZ.getMonster(i));
				}
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerZ);
				
				break;
			case BARBARIAN:
				this.setBarbarianUnavailable();
				
				playerA = action.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				this.insertCreatureIntoQueue(character);

				position = map.getBarbarianInitialPosition();
				this.setCreatureInPosition(character, position[0], position[1]);

				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
	
				break;
			case WIZARD:
				this.setWizardUnavailable();
				
				playerA = action.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				((Wizard) character).createSpells();
				this.insertCreatureIntoQueue(character);
				position = map.getWizInitialPosition();
				this.setCreatureInPosition(character, position[0], position[1]);

				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
	
				break;
			case ELF:
				this.setElfUnavailable();
				
				playerA = action.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				((Elf) character).createSpells();
				this.insertCreatureIntoQueue(character);
				position = map.getElfInitialPosition();
				this.setCreatureInPosition(character, position[0], position[1]);

				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
				
				break;
			case DWARF:
				this.setDwarfUnavailable();
				
				playerA = action.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				this.insertCreatureIntoQueue(character);
				
				position = map.getDwarfInitialPosition();
				this.setCreatureInPosition(character, position[0], position[1]);

				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
				
				break;
			default:
				this.gui.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
				break;
		}
		this.sortCreatureQueueByID();
		this.gui.showVisibleCreaturesInQueue();
	}

	public void setAreaVisible(byte sourceRow, byte sourceColumn) {
		for (int i = sourceRow - 2; i <= sourceRow + 2; i++) {
			for (int j = sourceColumn - 2; j <= sourceColumn + 2; j++) {
				if (i >= 0 && j >= 0 && i < getTotalNumberOfRows() && j < getTotalNumberOfColumns()) {
					Position position = getPosition((byte)i, (byte)j);
					position.setVisible(true);
					if (position.getCreature() != null)
						getCreatureById(position.getCreature().getID()).setVisible(true);
				}
			}
		}
	}

	private void processSearchForTreasure(SearchForTreasure action) {
		boolean foundGold = false;
		boolean foundItem = false;
		String itemName = "";

		byte sourceRow = action.getSourceRow();
		byte sourceColumn = action.getSourceColumn();
		
		Position position = this.map.getPosition(sourceRow, sourceColumn);
		PlayableCharacter character = (PlayableCharacter) position.getCreature();
		for (int i = sourceRow - 2; i <= sourceRow + 2; i++) {
			for (int j = sourceColumn - 2; j <= sourceColumn + 2; j++) {
				if (i >= 0 && i < this.map.getTotalNumberOfRows() && j >= 0 && j < this.map.getTotalNumberOfColumns()) {
					position = this.map.getPosition((byte)i, (byte)j);
					Treasure treasureInPosition = position.getTreasure();
					if (treasureInPosition != null) {
						int gold = treasureInPosition.getGoldAmount();
						ItemEnum item = treasureInPosition.getItem();
						
						if (treasureInPosition.isTrap()) {
							character.decreaseBody((byte) 1);
							treasureInPosition.setAsTrap(false);
							this.gui.showTrapActivationMessage((byte) 1, character);
						} else {
							if (gold >= 0) {
								treasureInPosition.setGoldAmount(-1);
								character.increaseGold(gold);
								foundGold = true;
							}
							if (item != null) {
								character.addItemToBag(item);
								treasureInPosition.setItem(null);
								foundItem = true;
								itemName = item.name();
							}
						}
					}
				}
			}
		}
		if (foundGold) {
			this.gui.showMessagePopup(Strings.THE_PLAYER
					+ character.getClass().getSimpleName()
					+ Strings.FOUND_GOLD);
		}
		if (foundItem) {
			this.gui.showMessagePopup(Strings.THE_PLAYER
					+ character.getClass().getSimpleName()
					+ Strings.FOUND_ITEM +itemName);
		}
	}

	private void processSendPlayer(SendPlayer action) {
		Player player = action.getPlayer();
		this.insertPlayerIntoQueue(player);
	}

	public Creature getCreatureById(int creatureId) {
        for (Creature creature : this.creatureQueue) {
            int id = creature.getID();
            if (id == creatureId) {
                return creature;
            }
        }
		return null;
	}

	public void searchForTreasure() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature caster = this.getCurrentCreature();
			if (caster instanceof PlayableCharacter) {
				Position source = caster.getCurrentPosition();
				SearchForTreasure action = new SearchForTreasure();
				action.setSourceRow(source.getRow());
				action.setSourceColumn(source.getColumn());
				this.processAction(action);
				this.sendAction(action);
			} else {
				this.gui.reportError(Strings.MONSTER_CANT_UNDERSTAND_COMMAND.toString());
			}
		} else {
			this.gui.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	public void selectCharacter() throws ClassNotFoundException {
		boolean doesSaveFileWithPlayerNameExist = this.gui.checkSaveFileExists(localPlayerName);
		if (doesSaveFileWithPlayerNameExist) {
			int choice = JOptionPane.showConfirmDialog(null, Strings.CONFIRM_LOAD_FILE);
			if (choice == JOptionPane.YES_OPTION) {
				ArrayList<String> fileInformation = null;
				try {
					fileInformation = this.gui.readSaveFile(localPlayerName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.processCharacterSelection(Integer.parseInt(Objects.requireNonNull(fileInformation).get(0)));
				this.localAdventurer.getPlayableCharacter().increaseGold(Integer.parseInt(fileInformation.get(1)));
			} else {
				this.gui.showCharacterSelectionScreen();
			}
		} else {
			this.gui.showCharacterSelectionScreen();
		}
	}
	
	public void processCharacterSelection(int selectedCharacterId) throws ClassNotFoundException {
		boolean isCharacterAvailable;
		Zargon playerZargon = new Zargon(this);
		Adventurer playerAdventurer = new Adventurer();
		PlayableCharacter character;
		SelectCharacter action = new SelectCharacter();
		action.setSelectedCharacterId((byte)selectedCharacterId);
		switch (CharacterEnum.getEnumById(selectedCharacterId)) {
			case ZARGON:
				isCharacterAvailable = this.getZargonAvailable();
				break;
			case BARBARIAN:
				isCharacterAvailable = this.isBarbarianAvailable();
				break;
			case WIZARD:
				isCharacterAvailable = this.isWizardAvailable();
				break;
			case ELF:
				isCharacterAvailable = this.isElfAvailable();
				break;
			case DWARF:
				isCharacterAvailable = this.isDwarfAvailable();
				break;
			default:
				this.gui.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
				isCharacterAvailable = false;
				break;
		}
		if (isCharacterAvailable) {
			switch (CharacterEnum.getEnumById(selectedCharacterId)) {
				case ZARGON:
					this.localZargon = playerZargon;
					action.setZargon(playerZargon);
					break;
				case BARBARIAN:
					character = new Barbarian();
					character.setID((byte) (map.getCreatureQueueSize()-3));
					playerAdventurer.setPlayableCharacter(character);
					this.localAdventurer = playerAdventurer;
					action.setAdventurer(playerAdventurer);
					break;
				case WIZARD:
					character = new Wizard();
					character.setID((byte) (map.getCreatureQueueSize()-2));
					playerAdventurer.setPlayableCharacter(character);
					this.localAdventurer = playerAdventurer;
					action.setAdventurer(playerAdventurer);
					break;
				case ELF:
					character = new Elf();
					character.setID((byte) (map.getCreatureQueueSize()-1));
					playerAdventurer.setPlayableCharacter(character);
					this.localAdventurer = playerAdventurer;
					action.setAdventurer(playerAdventurer);
					break;
				case DWARF:
					character = new Dwarf();
					character.setID((byte) (map.getCreatureQueueSize()));
					playerAdventurer.setPlayableCharacter(character);
					this.localAdventurer = playerAdventurer;
					action.setAdventurer(playerAdventurer);
					break;
				default:
					this.gui.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
					break;
			}
			this.processAction(action);
			this.sendAction(action);
		} else {
			this.gui.reportError(Strings.CHARACTER_UNAVAILABLE.toString());
			this.selectCharacter();
		}
	}

	public void killCreature(int creatureID) {
        for (Creature creature : this.creatureQueue) {
            if (creature.getID() == creatureID) {
                creature.setStatus(StatusEnum.DEAD);
                Position pos = creature.getCurrentPosition();
                pos.removeCreature();
                this.map.updatePosition(pos);
                pos.setVisible(true);
            }
        }
	}

	public void searchForTrapsAndHiddenDoors() {
		String error = searchForTrapsAndHiddenDoorsService.searchForTrapsAndHiddenDoors();
		if (error != null) {
			gui.reportError(error);
		}
	}

	public void endTurn() {
		String error = endTurnService.endTurn();
		if (error != null) {
			gui.reportError(error);
		}
	}

	public void endGame() {
		int option = JOptionPane.showConfirmDialog(null, Strings.END_GAME.toString());
		if (option == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}

	public void startNewGame() {
		this.setGameInSession(true);

		Player player = this.createPlayer(this.localPlayerName);
		this.setLocalPlayer(player);
		SendPlayer action = new SendPlayer();
		action.setPlayer(player);
		this.processAction(action);
		this.sendAction(action);
		this.gui.refreshGUI();
	}

	public void endTheGame() {
		if (this.areHeroesAlive()) {
			boolean haveConditionsBeenMet = this.map.verifyWinningConditions(this);
			if (haveConditionsBeenMet) {
				if (this.localAdventurer != null) {
					try {
						int heroType;
						PlayableCharacter localPlayableCharacter = this.localAdventurer.getPlayableCharacter();
						if (!StatusEnum.DEAD.equals(localPlayableCharacter.getStatus())) {
							heroType = CharacterEnum.getIdByName(localPlayableCharacter.getClass().getSimpleName());
							this.gui.writeSaveFile(localPlayerName, heroType, localPlayableCharacter.getGold(), localPlayableCharacter.getItems(this.map));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				this.gui.announceHeroesWon();
				this.endGame();
			}
		} else {
			this.gui.announceZargonWon();
			this.endGame();
		}
	}

	private boolean areHeroesAlive() {
		for (Creature creature : this.creatureQueue) {
            if (creature instanceof PlayableCharacter) {
                if (!StatusEnum.DEAD.equals(creature.getStatus()))
                    return true;
            }
        }
		return false;
	}

	private Player createPlayer(String playerName) {
		return new Player(playerName);
	}

	public void showInventory() {
		if (this.localAdventurer != null) {
			PlayableCharacter character = localAdventurer.getPlayableCharacter();
			int localPlayerGold = character.getGold();
			ArrayList<ItemEnum> localPlayerItems = character.getItems(this.map);
			this.gui.showInventory(localPlayerGold, localPlayerItems);
		} else {
			this.gui.reportError(Strings.ZARGON_DOES_NOT_CARRY_GOLD.toString());
		}
	}

	public void showCreatureInformation(int creatureID) {
		Creature creature = this.getCreatureFromQueue(creatureID);
		if (creature.isVisible()) {
			byte body = creature.getBody();
			byte mind = creature.getMind();
			byte movement = creature.getMovement();
			StatusEnum status = creature.getStatus();
			Position creatureCurrentPosition = creature.getCurrentPosition();
			byte currentCreatureRow = creatureCurrentPosition.getRow();
			byte currentCreatureColumn = creatureCurrentPosition.getColumn();
			byte roundsToSleep = creature.getRoundsToSleep();
		
			this.gui.showCreatureInformation(body, mind, movement, status,
					currentCreatureRow, currentCreatureColumn, roundsToSleep);
		} else {
			this.gui.showMessagePopup(Strings.UNKNOWN.toString());
		}
	}

	private Creature getCreatureFromQueue(int creatureID) {
		Creature criatura = null;
        for (Creature creature : this.creatureQueue) {
            if (creature.getID() == creatureID) {
                criatura = creature;
            }
        }
		return criatura;
	}

	private void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	public Position getPosition(byte i, byte j) {
		return this.map.getPosition(i, j);
	}

	public void setCreatureInPosition(Creature creature, int row, int column) {
		this.map.positions[row][column].setCreature(creature);
		creature.setCurrentPosition(this.map.positions[row][column]);
	}

	public void setLocalPlayerName(String playerName) {
		this.localPlayerName = playerName;
	}

	public void setServerAddress(String serverAddress) {
		this.gui.setTitle(this.gui.getTitle()+ Strings.SERVER + serverAddress + Strings.COMMA_PLAYER + this.localPlayerName);
	}

	public ClientServerProxy getClientServerProxy() {
		return clientServerProxy;
	}
	
	public BasicMap getMap() {
		return map;
	}

	public void setMap(BasicMap map) {
		this.map = map;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public GUI getGui() {
		return gui;
	}

	public int selectDoorToOpenOrClose(ArrayList<String> directionsWithOpenableDoors) {
		return gui.selectDoorToOpenOrClose(directionsWithOpenableDoors);
	}

	public TrapEvasionMovementEnum showPitJumpingOptions() {
		return gui.showPitJumpingOptions();
	}

	public TrapEvasionMovementEnum showFallingRockMovementOptions() {
		return gui.showFallingRockMovementOptions();
	}

	public void updatePosition(Position currentPosition) {
		map.updatePosition(currentPosition);
	}

	public int getTotalNumberOfRows() {
		return map.getTotalNumberOfRows();
	}

	public int getTotalNumberOfColumns() {
		return map.getTotalNumberOfColumns();
	}

	public void announceUnfortunateDeath(Creature creature) {
		gui.announceUnfortunateDeath(creature);
	}

	public void showTrapActivationMessage(byte damageTaken, Creature creature) {
		gui.showTrapActivationMessage(damageTaken, creature);
	}

	public Creature selectTarget(ArrayList<Creature> availableTargets) {
		return gui.selectTarget(availableTargets);
	}

	public Spell selectSpell(ArrayList<Spell> availableSpells) {
		return gui.selectSpell(availableSpells);
	}

	public void showMessagePopup(String message) {
		gui.showMessagePopup(message);
	}

	public void showAttackDamageMessage(Creature target, byte damage, boolean isSeppuku) {
		gui.showAttackDamageMessage(target, damage, isSeppuku);
	}

	public void announceCreatureDeath(Creature creature) {
		gui.announceCreatureDeath(creature);
	}

	public void showEffectOfCastSpell(Creature caster, Spell castSpell, Creature target, byte damageDealt, StatusEnum spellStatusEffect) {
		gui.showEffectOfCastSpell(caster, castSpell, target, damageDealt, spellStatusEffect);
	}

	public void updatePlayerSurroundings() {
		gui.updatePlayerSurroundings();
	}

	public void specialOccurrence() {
		map.specialOccurrence(this);
	}

	public void showTrapRemovalMessage() {
		gui.showTrapRemovalMessage();
	}
}