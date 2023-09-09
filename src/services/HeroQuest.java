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
		this.openDoorService = new OpenDoorService(this);
		this.movementService = new MovementService(this);
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

	private boolean checkIfAttackerReachesTarget(Position attackerPosition, Position targetPosition, boolean attackerHasSpear) {
		int attackerRow = attackerPosition.getRow();
		int attackerColumn = attackerPosition.getColumn();
		int targetRow = targetPosition.getRow();
		int targetColumn = targetPosition.getColumn();
		boolean result = false;
		if (attackerHasSpear){
			// add diagonal
			result = (attackerRow == targetRow-1 && attackerColumn == targetColumn-1)
					|| (attackerRow == targetRow+1 && attackerColumn == targetColumn+1)
					|| (attackerRow == targetRow+1 && attackerColumn == targetColumn-1)
					|| (attackerRow == targetRow-1 && attackerColumn == targetColumn+1);
		}
		return result
				|| (attackerRow == targetRow && attackerColumn == targetColumn - 1)
				|| (attackerRow == targetRow && attackerColumn == targetColumn + 1)
				|| (attackerColumn == targetColumn && attackerRow == targetRow - 1)
				|| (attackerColumn == targetColumn && attackerRow == targetRow + 1)
				|| (attackerPosition.equals(targetPosition));
	}

	public void move(DirectionEnum direction) {
		String error = movementService.move(direction);
		if (error != null) {
			gui.reportError(error);
		}
	}

	public void attack() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature attacker = this.getCurrentCreature();
			
			if (StatusEnum.SLEEPING.equals(attacker.getStatus())) {
				this.gui.reportError(Strings.SLEEP_NO_ATTACK.toString());
			} else {
				Position attackerPosition = attacker.getCurrentPosition();
				boolean hasSpear = checkIfAttackerIsAHeroAndHasSpear(attacker);
                ArrayList<Creature> availableTargets = this.getAvailableTargets(1,
						attackerPosition);
				Creature selectedTarget = this.gui.selectTarget(availableTargets);
				Position selectedTargetPosition = selectedTarget.getCurrentPosition();
				boolean isTargetReachable = this.checkIfAttackerReachesTarget(
						attackerPosition, selectedTargetPosition, hasSpear);
				if (isTargetReachable) {
					byte damage = this.calculateAttackDamage(attacker, selectedTarget);
					Attack action = new Attack();
					action.setValue(damage);
					action.setTargetID(selectedTarget.getID());
					this.processAction(action);
					this.sendAction(action);
				} else {
					this.gui.reportError(Strings.TARGET_OUT_OF_RANGE.toString());
				}
			}
		} else {
			this.gui.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	private boolean checkIfAttackerIsAHeroAndHasSpear(Creature attacker) {
		return attacker instanceof PlayableCharacter &&
				((PlayableCharacter) attacker).getItems(this.map).contains(ItemEnum.Spear);
	}

	private ArrayList<Creature> getAvailableTargets(int area, Position sourcePosition) {
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

	private byte calculateAttackDamage(Creature attacker, Creature defender) {
		boolean hit;
		int damage = 0;
		int defence = 0;
		int atkDiceAmount = attacker.getAttackDiceAmount();
		if (isCreatureInAPit(attacker)) {
			atkDiceAmount--;
		}
		int defDiceAmount = defender.getDefenceDiceAmount();
		if (isCreatureInAPit(defender)) {
				defDiceAmount--;
		}
		if (StatusEnum.ROCK_SKIN.equals(defender.getStatus())) {
			defDiceAmount++;
		}
		if (StatusEnum.COURAGE.equals(attacker.getStatus())) {
			atkDiceAmount += 2;
		}
		int probability = 2;
		for (byte i = 1; i <= atkDiceAmount; i++) {
			hit = new Random().nextInt(probability) == 0;
			if (hit) {
				damage++;
			}
		}
		if (defender instanceof Monster) {
			probability = 6;
		} else {
			probability = 3;
		}
		if (StatusEnum.SLEEPING.equals(defender.getStatus())) {
			defence = 0;
		} else {
			for (byte i = 1; i <= defDiceAmount; i++) {
				hit = new Random().nextInt(probability) == 0;
				if (hit) {
					defence++;
				}
			}
		}
		byte result = (byte) (damage - defence);
		
		if (result >= 0) {
			/*if(alvo.getStatus() == Status.ROCK_SKIN){
				alvo.setStatus(Status.NEUTRAL);
			}*/
			return result;
		} else {
			return 0;
		}
	}

	private static boolean isCreatureInAPit(Creature creature) {
		return creature.getCurrentPosition().getTrap() != null
				&& creature.getCurrentPosition().getTrap() instanceof Pit;
	}

	public void castSpell() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature caster = this.getCurrentCreature();
			if (caster instanceof Wizard) {
				ArrayList<Spell> availableSpells = ((Wizard) caster)
						.getSpells();
				byte availableMind = caster.getMind();
				if (availableMind > 0) {
					Spell selectedSpell = this.gui.selectSpell(availableSpells);
					Position casterCurrentPosition = caster.getCurrentPosition();
					ArrayList<Creature> availableTargets = this.getAvailableTargets((byte) 2, casterCurrentPosition);
					Creature selectedTarget = this.gui.selectTarget(availableTargets);
					boolean wasSpellSuccessful = this.calculateSpellSuccess(
							selectedTarget, selectedSpell);
					if (wasSpellSuccessful) {
						CastSpell lance = new CastSpell();
						if (StatusEnum.SLEEPING.equals(selectedSpell.getStatus())) {
							byte roundsToSleep = 0;
							byte dieRoll = 0;
							byte targetMind = selectedTarget.getMind();
							while(dieRoll != 5) {
								for (byte i = 0; i < targetMind; i++) {
									roundsToSleep++;
									dieRoll = (byte)(Math.random()*6);
									if (dieRoll == 5) {
										break;
									}
								}
							}
							lance.setRoundsToSleep(roundsToSleep);
						}
						lance.setSpell(selectedSpell);
						lance.setTargetID(selectedTarget.getID());
						this.processAction(lance);
						this.sendAction(lance);
					}
				} else {
					this.gui.reportError(Strings.NO_MIND_LEFT.toString());
				}
			} else if (caster instanceof Elf) {
				ArrayList<Spell> availableSpells = ((Elf) caster).getSpells();
				byte mind = caster.getMind();
				if (mind > 0) {
					Spell selectedSpell = this.gui.selectSpell(availableSpells);
					Position casterPosition = caster.getCurrentPosition();
					ArrayList<Creature> availableTargets = this.getAvailableTargets((byte) 2, casterPosition);
					Creature selectedTarget = this.gui.selectTarget(availableTargets);
					boolean wasSpellSuccessful = this.calculateSpellSuccess(selectedTarget, selectedSpell);
					if (wasSpellSuccessful) {
						CastSpell action = new CastSpell();
						action.setSpell(selectedSpell);
						action.setTargetID(selectedTarget.getID());
						this.processAction(action);
						this.sendAction(action);
					}
				} else {
					this.gui.reportError(Strings.NO_MIND_LEFT.toString());
				}
			} else {
				this.gui.reportError(Strings.CANT_USE_SPELLS.toString());
			}
		} else {
			this.gui.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	private boolean calculateSpellSuccess(Creature target, Spell spell) {
		byte spellDamage;
		boolean success = true;
		spellDamage = spell.getDamage();

		if (spell.getSpellId() == SpellNameEnum.BALL_OF_FLAME.getId()) {
			byte valueOnFirstDie = (byte)(Math.random() * 6 + 1);
			byte valueOnSecondDie = (byte)(Math.random() * 6 + 1);
			
			if (valueOnFirstDie > 4) {
				spellDamage++;
			}

			if (valueOnSecondDie > 4) {
				spellDamage++;
			}

			spell.setDamage(spellDamage);
			
			if (spellDamage == 0) {
				success = false;
				this.gui.showMessagePopup(Strings.SPELL_FAILED.toString());
			}
		}
		
		if (spell.getSpellId() == SpellNameEnum.FIRE_OF_WRATH.getId()) {
			byte valueOnDie = (byte)(Math.random() * 6 + 1);
			
			if (valueOnDie > 4) {
				spellDamage++;
			}

			spell.setDamage(spellDamage);
			
			if (spellDamage == 0) {
				success = false;
				this.gui.showMessagePopup(Strings.SPELL_FAILED.toString());
			}
		}
		
		if (spell.getSpellId() == SpellNameEnum.SLEEP.getId()) {

			if (isTargetUndead(target)) {
				success = false;
			} else {
				byte targetMind = target.getMind();
				byte valueOnDie;
				for (byte i = 0; i < targetMind; i++) {
					valueOnDie = (byte)(Math.random() * 6 + 1);
					if (valueOnDie == 6) {
						success = false;
						this.gui.showMessagePopup(Strings.SPELL_FAILED.toString());
						break;
					}
				}
			}
		}
		return success;
	}

	private static boolean isTargetUndead(Creature target) {
		return target instanceof Zombie || target instanceof Mummy || target instanceof Skeleton;
	}

	public void sendAction(Action action) {
		this.getClientServerProxy().sendAction(action);
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
				this.processAttack((Attack) action);
				this.processEndTurn();
				break;
			case SEND_PLAYER:
				this.processSendPlayer((SendPlayer) action);
				break;
			case END_TURN:
				this.processEndTurn();
				this.gui.showVisibleCreaturesInQueue();
				break;
			case CAST_SPELL:
				this.processCastSpell((CastSpell) action);
				this.processEndTurn();
				break;
			case SEARCH_FOR_TRAPS_AND_HIDDEN_DOORS:
				this.processSearchForTrapsAndHiddenDoors((SearchForTrapsAndHiddenDoors) action);
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
						getCreatureByID(position.getCreature().getID()).setVisible(true);
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

	private void processSearchForTrapsAndHiddenDoors(SearchForTrapsAndHiddenDoors action) {
		int sourceRow = action.getSourceRow();
		int sourceColumn = action.getSourceColumn();
		boolean isActionSourceADwarf = this.getPosition((byte)sourceRow, (byte)sourceColumn).getCreature() instanceof Dwarf;
		Position position;
		
		boolean removedTraps = false;
		for (int i = sourceRow - 2; i <= sourceRow + 2; i++) {
			for (int j = sourceColumn - 2; j <= sourceColumn + 2; j++) {
				if (i >= 0 && i < this.map.getTotalNumberOfRows() && j >= 0 && j < this.map.getTotalNumberOfColumns()) {
					position = this.map.getPosition((byte)i, (byte)j);
					if (position.getTrap() != null) {
						position.makeTrapVisible();
						
						if (position.getTrap() instanceof Pit) {
							position.getTrap().setTriggered(true);
						}
						
						if (isActionSourceADwarf) {
							position.removeTrap();
							removedTraps = true;
						}
						
					}
					if (position.getTreasure() != null && position.getTreasure().isTrap()) {
						position.getTreasure().setAsTrap(false);
						this.gui.showMessagePopup(Strings.DISARMED_TREASURE_TRAP.toString());
					}
					if (position instanceof Door && ((Door) position).isSecret()) {
						((Door) position).setSecret(false);
						if (this.map instanceof MelarsMaze) {
							if (((Door) position).getID() == 118) { // Throne room door
								((MelarsMaze) map).moveThrone(this);	
							}
						}
					}
				}
			}
		}
		
		if (removedTraps){
			this.gui.showTrapRemovalMessage();
		}
	}

	private void processCastSpell(CastSpell action) {
		byte roundsToSleep = action.getRoundsToSleep();
		Spell castSpell = action.getSpell();
		byte targetId = action.getTargetID();
		Creature target = this.getCreatureById(targetId);
		byte damageDealt = castSpell.getDamage();
		StatusEnum spellStatusEffect = castSpell.getStatus();
		if (spellStatusEffect != null) {
			target.setStatus(spellStatusEffect);
		}
		if (roundsToSleep > 0) {
			target.setRoundsToSleep(roundsToSleep);
		}
		this.getCurrentCreature().spendSpell(castSpell);
		this.gui.showEffectOfCastSpell(this.getCurrentCreature(), castSpell,
				target, damageDealt, spellStatusEffect);
		target.increaseBody(damageDealt);
		byte targetBP = target.getBody();
		if (targetBP <= 0) {
			this.gui.announceCreatureDeath(target);
			this.killCreature(targetId);
		}
	}

	private void processSendPlayer(SendPlayer action) {
		Player player = action.getPlayer();
		this.insertPlayerIntoQueue(player);
	}

	private void processAttack(Attack action) {
		byte attackerId = this.getCurrentCreature().getID();
		int targetID = action.getTargetID();
		Creature target = this.getCreatureById(targetID);

		byte damage = action.getValue();
		target.decreaseBody(damage);

		if (damage > 0) {
			if (StatusEnum.ROCK_SKIN.equals(target.getStatus())){
				target.setStatus(StatusEnum.NEUTRAL);
			}
		}

		Creature attacker = this.getCreatureByID(attackerId);
		if (StatusEnum.COURAGE.equals(attacker.getStatus())) {
			attacker.setStatus(StatusEnum.NEUTRAL);
		}
		
		boolean isSeppuku = attackerId == targetID;
		this.gui.showAttackDamageMessage(this.getCreatureByID(targetID), damage, isSeppuku);

		int targetRemainingBp = target.getBody();
		if (targetRemainingBp <= 0) {
			this.gui.announceCreatureDeath(target);
			this.killCreature(targetID);
		}
	}

	private Creature getCreatureById(int creatureId) {
        for (Creature creature : this.creatureQueue) {
            int id = creature.getID();
            if (id == creatureId) {
                return creature;
            }
        }
		return null;
	}

	public void processEndTurn() {
		this.gui.updatePlayerSurroundings(); // added for GUI refresh

		Creature endingCreature = this.removeCreatureFromQueue();
		this.creatureQueue.trimToSize();
		this.insertCreatureIntoQueue(endingCreature);
		
		if (!(endingCreature instanceof Wizard) && !(endingCreature instanceof Elf)) {
			StatusEnum creatureWhoEndedTurnStatus = endingCreature.getStatus();
			if (StatusEnum.AGILITY_UP.equals(creatureWhoEndedTurnStatus)
				|| StatusEnum.AGILITY_DOWN.equals(creatureWhoEndedTurnStatus)) {

				endingCreature.setStatus(StatusEnum.NEUTRAL);
			}
		}

		Creature newCurrentCreature = this.getCurrentCreature();
		StatusEnum newCurrentCreatureStatus = newCurrentCreature.getStatus();
		
		byte verifiedCreatures = 0;
		while ((StatusEnum.DEAD.equals(newCurrentCreatureStatus)
				|| !newCurrentCreature.isVisible()
				|| StatusEnum.CURSED.equals(newCurrentCreatureStatus)
				|| StatusEnum.SLEEPING.equals(newCurrentCreatureStatus)) && verifiedCreatures <= this.getCreatureQueue().size()) {
			
			if (StatusEnum.CURSED.equals(newCurrentCreatureStatus)
					|| StatusEnum.AGILITY_UP.equals(newCurrentCreatureStatus)
					|| StatusEnum.AGILITY_DOWN.equals(newCurrentCreatureStatus)) {
				
				newCurrentCreature.setStatus(StatusEnum.NEUTRAL);
			}
			
			if (StatusEnum.SLEEPING.equals(newCurrentCreatureStatus)) {
				byte roundsToSleep = (byte)(newCurrentCreature.getRoundsToSleep()-1);
				newCurrentCreature.setRoundsToSleep(roundsToSleep);
				if (roundsToSleep == 0){
					newCurrentCreature.setStatus(StatusEnum.NEUTRAL);
					this.gui.showMessagePopup(Strings.THE_CREATURE + newCurrentCreature.getClass().getSimpleName()+ Strings.WOKE_UP);
				}
			}
			endingCreature = this.removeCreatureFromQueue();
			this.creatureQueue.trimToSize();
			this.insertCreatureIntoQueue(endingCreature);
			newCurrentCreature = this.getCurrentCreature();
			newCurrentCreatureStatus = newCurrentCreature.getStatus();
			
			verifiedCreatures++;
		}
		newCurrentCreature.setMovement();

		if (StatusEnum.COURAGE.equals(newCurrentCreature.getStatus()) && areThereNoEnemiesOnSight(newCurrentCreature)) {
			newCurrentCreature.setStatus(StatusEnum.NEUTRAL);
		}
		this.map.specialOccurrence(this);
		this.endTheGame();
	}

	private boolean areThereNoEnemiesOnSight(Creature sourceCreature) {
		return this.getAvailableTargets(1, sourceCreature.getCurrentPosition()).size() == 1;
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
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature caster = this.getCurrentCreature();
			if (caster instanceof PlayableCharacter){
				Position casterPosition = caster.getCurrentPosition();
				SearchForTrapsAndHiddenDoors action = new SearchForTrapsAndHiddenDoors();
				action.setSourceColumn(casterPosition.getColumn());
				action.setSourceRow(casterPosition.getRow());
				processAction(action);
				sendAction(action);
			} else {
				this.gui.reportError(Strings.MONSTER_CANT_UNDERSTAND_COMMAND.toString());
			}
		} else {
			this.gui.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	public void endTurn() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			EndTurn action = new EndTurn();
			this.processAction(action);
			this.sendAction(action);
		} else {
			this.gui.reportError(Strings.NOT_YOUR_TURN.toString());
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

	private void endTheGame() {
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
	
	public Creature getCreatureByID(int creatureID) {
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			Creature creature = this.creatureQueue.get(i);
			if (creature.getID() == creatureID) {
				return creature;
			}
		}
		return null;
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
}
