package entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

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
import view.AtorClientServer;
import view.GUI;
import exceptions.PositionNotEmptyException;

public class HeroQuest implements LogicInterface {

	protected BasicMap map;
	protected ArrayList<Player> players;

	protected GUI GUI;
	private AtorClientServer atorClienteServidor;
	protected Player localPlayer;
	protected ArrayList<Creature> creatureQueue;
	public ArrayList<Door> doors;
	protected static boolean zargonAvailable;
	protected static boolean barbarianAvailable;
	protected static boolean wizardAvailable;
	protected static boolean elfAvailable;
	protected static boolean dwarfAvailable;
	protected boolean connected;
	protected boolean gameInSession;
	protected Adventurer localAdventurer;
	protected Zargon localZargon;
	public String localPlayerName = "";


	public HeroQuest(){
		this.players = new ArrayList<>();
		//this.atorJogador = ator; // link later
		this.atorClienteServidor = new AtorClientServer(this);
		this.creatureQueue = new ArrayList<Creature>();
		this.doors = new ArrayList<Door>();
		zargonAvailable = true;
		barbarianAvailable = true;
		wizardAvailable = true;
		elfAvailable = true;
		dwarfAvailable = true;
		this.connected = false;
		this.gameInSession = false;
		this.localAdventurer = null;
		this.localZargon = null;
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean getIsGameInSession() {
		return this.gameInSession;
	}

	public void setGameInSession(boolean gameInSession) {
		this.gameInSession = gameInSession;
	}
	
	public void openDoor(int doorId) {
		Creature creature = this.getCurrentCreature();
		if (creature instanceof PlayableCharacter){
			boolean isItTheCurrentPlayersTurn = this.verifyIfItIsCurrentPlayersTurn();
			if (isItTheCurrentPlayersTurn) {
				Door door = this.getPorta(doorId);
				boolean isDoorReachable = this.verifyIfPlayerIsNearDoor(
						(PlayableCharacter) creature, door);
				
				boolean isDoorHidden = door.isSecret();
				if (!isDoorHidden) {
					if (isDoorReachable) {
						OpenDoor lance = new OpenDoor();
						lance.setDoorId(doorId);
						this.processAction(lance);
						this.sendAction(lance);
					} else {
						this.GUI.reportError(Strings.DOOR_OUT_OF_RANGE.toString());
					}
				}
			} else {
				this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
			}
		} else {
			this.GUI.reportError(Strings.CANT_OPEN_DOOR.toString());
		}
	}
	
	public void openDoorWithKeyboard() {
		Creature creature = this.getCurrentCreature();
		if (creature instanceof PlayableCharacter) {
			boolean isItTheCurrentPlayersTurn = this.verifyIfItIsCurrentPlayersTurn();
			if (isItTheCurrentPlayersTurn) {
				byte creatureRow = creature.getCurrentPosition().getRow();
				byte creatureColumn = creature.getCurrentPosition().getColumn();

				Position northTile = this.getPosition((byte)(creatureRow-1), creatureColumn);
				Position eastTile = this.getPosition(creatureRow, (byte)(creatureColumn+1));
				Position southTile = this.getPosition((byte)(creatureRow+1), creatureColumn);
				Position westTile = this.getPosition(creatureRow, (byte)(creatureColumn-1));
				
				ArrayList<String> directionsWithOpenableDoors = new ArrayList<>();
				ArrayList<String> openableDoorIds = new ArrayList<>();
				String doorId;
				
				if (northTile instanceof Door) {
					if (!((Door) northTile).isSecret()) {
						doorId = "" + northTile.getRow() + northTile.getColumn();
						openableDoorIds.add(doorId);
						directionsWithOpenableDoors.add(Strings.NORTH.toString());
					}
				} if (eastTile instanceof Door) {
					if (!((Door) eastTile).isSecret()) {
						doorId = "" + eastTile.getRow() + eastTile.getColumn();
						openableDoorIds.add(doorId);
						directionsWithOpenableDoors.add(Strings.EAST.toString());
					}
				} if (southTile instanceof Door) {
					if (!((Door) southTile).isSecret()) {
						doorId = "" + southTile.getRow() + southTile.getColumn();
						openableDoorIds.add(doorId);
						directionsWithOpenableDoors.add(Strings.SOUTH.toString());
					}
				} if (westTile instanceof Door) {
					if (!((Door) westTile).isSecret()) {
						doorId = "" + westTile.getRow() + westTile.getColumn();
						openableDoorIds.add(doorId);
						directionsWithOpenableDoors.add(Strings.WEST.toString());
					}
				}

				if (!directionsWithOpenableDoors.isEmpty()) {
					
					int chosenDoorDirection = this.GUI.selectDoorToOpenOrClose(directionsWithOpenableDoors);
					int selectedDoorId = Integer.parseInt(openableDoorIds.get(chosenDoorDirection));
					
					OpenDoor lance = new OpenDoor();
					lance.setDoorId(selectedDoorId);
					this.processAction(lance);
					this.sendAction(lance);
				} else {
					this.GUI.reportError(Strings.DOOR_OUT_OF_RANGE.toString());
				}
			} else {
				this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
			}
		} else {
			this.GUI.reportError(Strings.CANT_OPEN_DOOR.toString());
		}
	}
	

	private boolean verifyIfItIsCurrentPlayersTurn() {
		int localCreatureId, currentTurnCreatureId;
		currentTurnCreatureId = this.getCurrentCreature().getID();
		if (this.localAdventurer != null) {
			localCreatureId = (this.localAdventurer).getPlayableCharacter()
					.getID();
			return localCreatureId == currentTurnCreatureId;
		} else {
			// Attempt of fixing null pointer exception (index 19 inaccessible)
			for (int i = 0; i < this.creatureQueue.size() - players.size(); i++) {
				localCreatureId = (this.localZargon).getMonster(i).getID();
				if (currentTurnCreatureId == localCreatureId)
					return true;
			}
			return false;

		}
	}

	private Door getPorta(int idPorta) {
		for (int i = 0; i < this.doors.size(); i++) {
			if (this.doors.get(i).getID() == idPorta)
				return this.doors.get(i);
		}
		return null;
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

	private boolean verifyIfPlayerIsNearDoor(PlayableCharacter hero,
											 Door door) {
		byte heroRow = hero.getCurrentPosition().getRow();
		byte heroColumn = hero.getCurrentPosition().getColumn();
		byte doorRow = door.getRow();
		byte doorColumn = door.getColumn();
		return (heroRow == doorRow && heroColumn == doorColumn - 1)
				|| (heroRow == doorRow && heroColumn == doorColumn + 1)
				|| (heroColumn == doorColumn && heroRow == doorRow - 1)
				|| (heroColumn == doorColumn && heroRow == doorRow + 1);
	}

	public void move(DirectionEnum direction) {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature creature = this.getCurrentCreature();
			
			if (StatusEnum.SLEEPING.equals(creature.getStatus())){
				this.GUI.reportError(Strings.SLEEP_FREEZE.toString());
			} else {
				byte remainingMovement = creature.getMovement();
				if (remainingMovement > 0) {
					Position currentPosition = creature.getCurrentPosition();
					byte row = currentPosition.getRow();
					byte column = currentPosition.getColumn();
					Position newPosition;
					try {
						newPosition = this.getNovaPosicao(direction, row, column);

						Movement movement = new Movement();
						movement.setSourceRow(row);
						movement.setSourceColumn(column);
						movement.setDestinationRow(newPosition.getRow());
						movement.setDestinationColumn(newPosition.getColumn());

						Trap trap = newPosition.getTrap();
					
						if (trap != null) {
							byte trapDamage = trap.getDeliveredDamage();
							movement.setDamage(trapDamage);
							
							if (trap instanceof FallingRock) {
								TrapEvasionMovementEnum evasionOption = this.GUI.showFallingRockMovementOptions();

								switch(direction){
									case UP: {
										if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
											newPosition = map.getPosition((byte) (newPosition.getRow()-1), movement.getDestinationColumn());
										}
										break;
									}
									case DOWN: {
										if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
											newPosition = map.getPosition((byte) (newPosition.getRow()+1), movement.getDestinationColumn());
										}
										break;
									}
									case LEFT: {
										if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
											newPosition = map.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()-1));
										}
										break;
									}
									default: {
										if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
											newPosition = map.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()+1));
										}
										break;
									}
								}
								if (newPosition instanceof Wall) {
									evasionOption = TrapEvasionMovementEnum.BACKWARD;
								}
								movement.setDirection(direction);
								movement.setTrapEvasionMovementId(TrapEvasionMovementEnum.getIdByEnum(evasionOption));

							} else if (trap instanceof Pit) {
								
								if (trap.getVisible()) {
								
									TrapEvasionMovementEnum evasionOption = this.GUI.showPitJumpingOptions();
									
									switch(direction) {
										case UP: {
											if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
												newPosition = map.getPosition((byte) (newPosition.getRow()-1), movement.getDestinationColumn());
											}
											break;
										}
										case DOWN: {
											if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
												newPosition = map.getPosition((byte) (newPosition.getRow()+1), movement.getDestinationColumn());
											}
											break;
										}
										case LEFT: {
											if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
												newPosition = map.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()-1));
											}
											break;
										}
										default: {
											if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
												newPosition = map.getPosition(movement.getDestinationRow(), (byte) (newPosition.getColumn()+1));
											}
											break;
										}
									}
									if (newPosition instanceof Wall) {
										evasionOption = TrapEvasionMovementEnum.BACKWARD;
									}
									movement.setDirection(direction);
									movement.setTrapEvasionMovementId(TrapEvasionMovementEnum.getIdByEnum(evasionOption));

								} else {
									movement.setDirection(direction);
									movement.setTrapEvasionMovementId(TrapEvasionMovementEnum.getIdByEnum(TrapEvasionMovementEnum.FALLEN_INTO_PIT));
								}
							}
						}
					
						this.processAction(movement);
						this.sendAction(movement);

					} catch (PositionNotEmptyException e) {
						this.GUI.reportError(Strings.PHYSICS_LAWS.toString());
					}
				} else {
					this.GUI.reportError(Strings.NO_MOVE_LEFT.toString());
					}
				}
			} else {
				this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
			}
	}

	public Creature getCurrentCreature() {
		return this.creatureQueue.get(0);
	}

	private Position getNovaPosicao(DirectionEnum direcao, byte linha, byte coluna)
			throws PositionNotEmptyException {
		Position novaPosicao = null;
		switch (direcao) {
		case DOWN:
			novaPosicao = this.map.getPosition((byte) (linha + 1), coluna);
			break;
		case UP:
			novaPosicao = this.map.getPosition((byte) (linha - 1), coluna);
			break;
		case LEFT:
			novaPosicao = this.map.getPosition(linha, (byte) (coluna - 1));
			break;
		case RIGHT:
			novaPosicao = this.map.getPosition(linha, (byte) (coluna + 1));
			break;
		}
		if (novaPosicao.getCreature() != null
				|| novaPosicao instanceof Wall
				|| novaPosicao instanceof Furniture
				|| (novaPosicao instanceof Door && !((Door) novaPosicao)
						.isOpen())
				|| (novaPosicao.getTrap() != null && novaPosicao.getTrap() instanceof FallingRock && novaPosicao.getTrap().getTriggered())) {
			throw new PositionNotEmptyException();
		} else {
			return novaPosicao;
		}
	}

	public void attack() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature attacker = this.getCurrentCreature();
			
			if (StatusEnum.SLEEPING.equals(attacker.getStatus())) {
				this.GUI.reportError(Strings.SLEEP_NO_ATTACK.toString());
			} else {
				Position attackerPosition = attacker.getCurrentPosition();
				boolean hasSpear = false;
				if (checkIfAttackerIsAHeroAndHasSpear(attacker)){
					hasSpear = true;
				}
				ArrayList<Creature> availableTargets = this.getAvailableTargets(1,
						attackerPosition);
				Creature selectedTarget = this.GUI.selectTarget(availableTargets);
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
					this.GUI.reportError(Strings.TARGET_OUT_OF_RANGE.toString());
				}
			}
		} else {
			this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	private boolean checkIfAttackerIsAHeroAndHasSpear(Creature attacker) {
		return attacker instanceof PlayableCharacter &&
				((PlayableCharacter) attacker).getItems(this.map).contains(Items.Spear);
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

	private byte calculateAttackDamage(Creature atacante, Creature alvo) {
		byte atkDiceAmount, defDiceAmount, damage, defence, probabilidade, result;
		boolean hit;
		damage = 0;
		defence = 0;
		atkDiceAmount = atacante.getAttackDiceAmount();
		if (atacante.getCurrentPosition().getTrap() != null){
			if (atacante.getCurrentPosition().getTrap() instanceof Pit){
				atkDiceAmount--;
			}
		}
		defDiceAmount = alvo.getDefenceDiceAmount();
		if (alvo.getCurrentPosition().getTrap() != null){
			if (alvo.getCurrentPosition().getTrap() instanceof Pit){
				defDiceAmount--;
			}
		}
		if (alvo.getStatus() == StatusEnum.ROCK_SKIN){
			defDiceAmount++;
		}
		if (atacante.getStatus() == StatusEnum.COURAGE){
			atkDiceAmount += 2;
		}
		probabilidade = 2;
		for (byte i = 1; i <= atkDiceAmount; i++) {
			hit = new Random().nextInt(probabilidade) == 0;
			if (hit) {
				damage++;
			}
		}
		if (alvo instanceof Monster) {
			probabilidade = 6;
		} else {
			probabilidade = 3;
		}
		if (alvo.getStatus() == StatusEnum.SLEEPING){
			defence = 0;
		} else {
			for (byte i = 1; i <= defDiceAmount; i++) {
				hit = new Random().nextInt(probabilidade) == 0;
				if (hit) {
					defence++;
				}
			}
		}
		result = (byte) (damage - defence);
		
		if (result >= 0) {
			/*if(alvo.getStatus() == Status.ROCK_SKIN){
				alvo.setStatus(Status.NEUTRAL);
			}*/
			return result;
		} else {
			return 0;
		}
	}

	public void castSpell() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			Creature caster = this.getCurrentCreature();
			if (caster instanceof Wizard) {
				ArrayList<Spell> availableSpells = ((Wizard) caster)
						.getSpells();
				byte availableMind = caster.getMind();
				if (availableMind > 0) {
					Spell selectedSpell = this.GUI.selectSpell(availableSpells);
					Position casterCurrentPosition = caster.getCurrentPosition();
					ArrayList<Creature> availableTargets = this.getAvailableTargets((byte) 2, casterCurrentPosition);
					Creature selectedTarget = this.GUI.selectTarget(availableTargets);
					boolean wasSpellSuccessful = this.calculateSpellSuccess(
							selectedTarget, selectedSpell);
					if (wasSpellSuccessful) {
						CastSpell lance = new CastSpell();
						if (selectedSpell.getStatus() == StatusEnum.SLEEPING){ // determinar o numero de rodadas a dormir
							byte roundsToSleep = 0;
							byte dado = 0;
							byte mindAlvo = selectedTarget.getMind();
							while(dado != 5){
								for (byte i = 0; i < mindAlvo; i++){
									roundsToSleep++;
									dado = (byte)(Math.random()*6);
									if (dado == 5){
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
					this.GUI.reportError(Strings.NOMIND.toString());
				}
			} else if (caster instanceof Elf) {
				ArrayList<Spell> magiasDisponiveis = ((Elf) caster)
						.getSpells();
				byte mind = caster.getMind();
				if (mind > 0) {
					Spell magia = this.GUI
							.selectSpell(magiasDisponiveis);
					Position posicaoAtual = caster.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this
							.getAvailableTargets((byte) 2, posicaoAtual);
					Creature alvo = this.GUI
							.selectTarget(possiveisAlvos);
					boolean sucesso = this.calculateSpellSuccess(
							alvo, magia);
					if (sucesso) {
						CastSpell lance = new CastSpell();
						lance.setSpell(magia);
						lance.setTargetID(alvo.getID());
						this.processAction(lance);
						this.sendAction(lance);
					}
				} else {
					this.GUI.reportError(Strings.NOMIND.toString());
				}
			} else {
				this.GUI.reportError(Strings.DOESNTUSESPELLS.toString());
			}
		} else {
			this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	public ArrayList<Creature> getCreatureQueue() {
		return this.creatureQueue;
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
				this.GUI.showMessagePopup(Strings.SPELL_FAILED.toString());
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
				this.GUI.showMessagePopup(Strings.SPELL_FAILED.toString());
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
						this.GUI.showMessagePopup(Strings.SPELL_FAILED.toString());
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
		this.getAtorClienteServidor().enviarJogada(action);
	}

	public void processAction(Action action) {
		String actionType = action.getClass().getSimpleName();
		switch (ActionTypeEnum.getByName(actionType)) {
			case MOVEMENT:
				this.tratarMovimento((Movement) action);
				break;
			case OPEN_DOOR:
				this.tratarAbrirPorta((OpenDoor) action);
				break;
			case ATTACK:
				this.tratarAtaque((Attack) action);
				this.tratarFinalizarJogada(action);
				break;
			case SEND_PLAYER:
				this.tratarEnviarPlayer((SendPlayer) action);
				break;
			case END_TURN:
				this.tratarFinalizarJogada(action);
				this.GUI.showVisibleCreaturesInQueue();
				break;
			case CAST_SPELL:
				this.tratarMagia((CastSpell) action);
				//this.atorJogador.exibirCriaturas();
				this.tratarFinalizarJogada(action);
				break;
			case SEARCH_FOR_TRAPS_AND_HIDDEN_DOORS:
				this.tratarProcurarArmadilha((SearchForTrapsAndHiddenDoors) action);
				break;
			case SEARCH_FOR_TREASURE:
				this.tratarProcurarTesouro((SearchForTreasure) action);
				break;
			case SELECT_CHARACTER:
				this.tratarSelecionarPersonagem((SelectCharacter) action);
				this.GUI.refreshGUI();
				break;
		}
		//this.atorJogador.atualizarInterfaceGrafica();
		this.GUI.updatePlayerSurroundings();
	}
	
	// Inserir aqui a area visivel inicial por personagem
	private void tratarSelecionarPersonagem(SelectCharacter lance) {
		PlayableCharacter character;
		Adventurer playerA;
		byte personagem = lance.getSelectedCharacterId();
		byte[] position = new byte[2];

		switch (personagem) {
			case 0:
				this.setZargonAvailable(false);
				
				Zargon playerZ = lance.getZargon();
				for (int i = 0; i < playerZ.getMonsters().size(); i++) {
					this.insertCreatureIntoQueue(playerZ.getMonster(i));
				}
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerZ);
				
				break;
			case 1:
				this.setBarbarianAvailable(false);
				
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				this.insertCreatureIntoQueue(character);
				
				
				position = map.getBarbInitialPosition();
				this.creatureInPosition(character, position[0], position[1]);
				//this.creatureInPosition(character, 25, 25);
				
				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
	
				break;
			case 2:			
				this.setWizardAvailable(false);
				
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				((Wizard) character).createSpells();
				this.insertCreatureIntoQueue(character);
				position = map.getWizInitialPosition();
				this.creatureInPosition(character, position[0], position[1]);
				//this.creatureInPosition(character, 25, 25);
				
				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
	
				break;
			case 3:
				this.setElfAvailable(false);
				
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				((Elf) character).createSpells();
				this.insertCreatureIntoQueue(character);
				position = map.getElfInitialPosition();
				this.creatureInPosition(character, position[0], position[1]);
				//this.creatureInPosition(character, 25, 25);
				
				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
				
				break;
			case 4:
				this.setDwarfAvailable(false);
				
				playerA = lance.getAdventurer();
				this.removePlayerFromQueue();
				this.insertPlayerIntoQueue(playerA);
				character = playerA.getPlayableCharacter();
				this.insertCreatureIntoQueue(character);
				
				position = map.getDwarfInitialPosition();
				this.creatureInPosition(character, position[0], position[1]);
				//this.creatureInPosition(character, 20, 25);
				
				character.setMovement();
				this.setAreaVisible(position[0], position[1]);
				
				break;
			default:
				this.GUI.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
				break;
		}
		this.sortCreatureQueueByID();
		this.GUI.showVisibleCreaturesInQueue();
	}

	private void tratarProcurarTesouro(SearchForTreasure lance) {
		boolean foundGold = false;
		boolean foundItem = false;
		String itemName = "";
		
		PlayableCharacter character;
		byte linha = lance.getSourceRow();
		byte coluna = lance.getSourceColumn();
		
		Position posicaoAtual = this.map.getPosition(linha, coluna);

		character = (PlayableCharacter) posicaoAtual.getCreature();
		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				if (i >= 0 && i < this.map.getTotalNumberOfRows() && j >= 0 && j < this.map.getTotalNumberOfColumns()){
					posicaoAtual = this.map.getPosition((byte)i, (byte)j);
					Treasure tesouro = posicaoAtual.getTreasure();
					if (tesouro != null) {
						int gold = tesouro.getGoldAmount();
						Items item = tesouro.getItem();
						
						// Has a trap
						if (tesouro.isTrap()){
							character.decreaseBody((byte) 1);
							tesouro.setAsTrap(false);
							this.GUI.showTrapActivationMessage((byte) 1, character);
						} else {
							if (gold >= 0){
								tesouro.setGoldAmount(-1);
								character.increaseGold(gold);
								foundGold = true;
							}
							if (item != null){
								character.addItemToBag(item); // add item
								tesouro.setItem(null); // remove item
								foundItem = true;
								itemName = item.name();
							}
						}
						

					}
				}
			}
		}
		if (foundGold){
			this.GUI.showMessagePopup(Strings.THEPLAYER.toString()
					+ character.getClass().getSimpleName()
					+ Strings.FOUNDGOLD.toString());
		}
		if (foundItem){
			this.GUI.showMessagePopup(Strings.THEPLAYER.toString()
					+ character.getClass().getSimpleName()
					+ Strings.FOUNDITEM.toString()+itemName);
		}
	}

	private void tratarProcurarArmadilha(SearchForTrapsAndHiddenDoors lance) {
		int linha = lance.getSourceRow();
		int coluna = lance.getSourceColumn();
		Position posicaoAtual;
		
		boolean removeuArmadilhas = false;
		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				if (i >= 0 && i < this.map.getTotalNumberOfRows() && j >= 0 && j < this.map.getTotalNumberOfColumns()) {
					posicaoAtual = this.map.getPosition((byte)i, (byte)j);
					if (posicaoAtual.getTrap() != null) {
						posicaoAtual.makeTrapVisible();
						
						// If Pit and visible, disarm it
						if (posicaoAtual.getTrap() instanceof Pit){
							posicaoAtual.getTrap().setTriggered(true);
						}
						
						// If dwarf, disarm the traps
						if (this.getPosition((byte)linha, (byte)coluna).getCreature() instanceof Dwarf){
							//posicaoAtual.makeTrapTriggered();
							posicaoAtual.removeTrap();
							removeuArmadilhas = true;
						}
						
					}
					// If found a trap in a treasure chest
					if (posicaoAtual.getTreasure() != null){
						if (posicaoAtual.getTreasure().isTrap()){
							posicaoAtual.getTreasure().setAsTrap(false);
							this.GUI.showMessagePopup(Strings.DISARMED_TREASURE_TRAP.toString());
						}
					}
					if (posicaoAtual instanceof Door){
						if (((Door) posicaoAtual).isSecret()){
							((Door) posicaoAtual).setSecret(false);
						}
						if (this.map instanceof MelarsMaze){
							if (((Door) posicaoAtual).getID() == 118){ // Throne room door
								((MelarsMaze) map).moveThrone(this);	
							}
						}
					}
				}
			}
		}
		
		if (removeuArmadilhas){
			this.GUI.showTrapRemovalMessage();
		}
	}

	private void tratarMagia(CastSpell lance) {
		byte alvo;
		byte dano;
		byte body;
		Byte roundsToSleep = lance.getRoundsToSleep();
		Spell magia = lance.getSpell();
		alvo = lance.getTargetID();
		//int id = alvo.getID();
		Creature criatura = this.getCriaturaPorID(alvo);
		dano = magia.getDamage();
		StatusEnum statusEnum = magia.getStatus();
		if (statusEnum != null) {
			criatura.setStatus(statusEnum);
		}
		if (roundsToSleep != null){
			criatura.setRoundsToSleep(roundsToSleep);
		}
		this.getCurrentCreature().usarMagia(magia);
		this.GUI.showEffectOfCastSpell(this.getCurrentCreature(), magia,
				criatura, dano, statusEnum); /////////////// alvo -> criatura
		criatura.increaseBody(dano);
		body = criatura.getBody();
		if (body <= 0) {
			this.GUI.announceCreatureDeath(criatura);
			this.killCreature(alvo);
		}
	}

	private void tratarEnviarPlayer(SendPlayer lance) {
		Player player;
		player = lance.getPlayer();
		this.insertPlayerIntoQueue(player);
	}

	private void tratarAtaque(Attack lance) {
		byte idAtacante = this.getCurrentCreature().getID();
		byte idAlvo;
		byte dano;
		byte body;
		idAlvo = lance.getTargetID();
		dano = lance.getValue();
		Creature criatura = this.getCriaturaPorID(idAlvo);
		criatura.decreaseBody(dano);
		body = criatura.getBody();
		
		if (dano > 0){
			if (criatura.getStatus() == StatusEnum.ROCK_SKIN){
				criatura.setStatus(StatusEnum.NEUTRAL);
			}
		}
		// Courage status removal
		Creature attacker = this.getCreaturePorID(idAtacante);
		if (attacker.getStatus() == StatusEnum.COURAGE){
			attacker.setStatus(StatusEnum.NEUTRAL);
		}
		
		boolean seAtacou = idAtacante == idAlvo;
		this.GUI.showAttackDamageMessage(this.getCreaturePorID(idAlvo), dano, seAtacou);
		if (body <= 0) {
			this.GUI.announceCreatureDeath(criatura);
			this.killCreature(idAlvo);
		}
	}

	private Creature getCriaturaPorID(int idAlvo) {
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			int idLocal = this.creatureQueue.get(i).getID();
			if (idLocal == idAlvo) {
				return this.creatureQueue.get(i);
			}
		}
		return null;
	}

	private void tratarMovimento(Movement lance) {
		Creature criatura;
		byte body;
		byte linha;
		byte coluna;
		

		Position posicaoAtual = map.getPosition(lance.getSourceRow(), lance.getSourceColumn());
		
		Position novaPosicao = map.getPosition(lance.getDestinationRow(), lance.getDestinationColumn());
		
		
		criatura = this.getCurrentCreature();
		
		posicaoAtual.removeCreature();
		criatura.decreaseMovement();
		
		this.map.updatePosition(posicaoAtual);
		
		novaPosicao.setCreature(criatura);
		criatura.setCurrentPosition(novaPosicao);

		this.map.updatePosition(novaPosicao);
		
		this.setAreaVisible(novaPosicao.getRow(), novaPosicao.getColumn());

		byte dano = lance.getDamage();
		if (novaPosicao.getTrap() != null) {
			//boolean visivel = trap.getVisible();
			//System.out.println(""+visivel);
			//if (true) {//(!visivel) { //////////////////// Fonte do problema!!!!!!!!
				//dano = trap.getDeliveredDamage();
				
				if (!novaPosicao.getTrap().getTriggered()){
					criatura.decreaseBody(dano);
					this.GUI.showTrapActivationMessage(dano, criatura);
					novaPosicao.getTrap().makeTrapVisible(); ////////
					
					novaPosicao.getTrap().makeTrapTriggered();
					
					/*if (novaPosicao.getTrap() instanceof FallingRock){ // make it wall, works but doesn't look right
						novaPosicao.removeTrap();
						Position p = getPosition(novaPosicao.getRow(), novaPosicao.getColumn());
						p = new Wall(novaPosicao.getRow(), novaPosicao.getColumn());
						this.map.atualizarPosicao(p, novaPosicao.getRow(), novaPosicao.getColumn());
					}*/
				}
				
				if (novaPosicao.getTrap() instanceof Pit){
					//novaPosicao.getTrap().setTriggered(false);
					
					// se foi para frente ou para tras (se caiu no pit, fica nele)
					if (!TrapEvasionMovementEnum.FALLEN_INTO_PIT.equals(lance.getTrapEvasionMovementId())){
						novaPosicao.removeCreature();
						
						TrapEvasionMovementEnum evasionOption = TrapEvasionMovementEnum.getEnumById(lance.getTrapEvasionMovementId());
						switch(lance.getDirection()){
						case UP: {	if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
									} else {
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
									}
									break;
									}
						case DOWN: {if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
									} else {
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
									}
									break;
									}
						case LEFT: {if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
										novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
									} else {
										novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
									}
									break;
									}
						default: {if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)){
										novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
									} else{
										novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
									}
									break;
									}
						}
						novaPosicao.setCreature(criatura);
						criatura.setCurrentPosition(novaPosicao);
						
						linha = novaPosicao.getRow();
						coluna = novaPosicao.getColumn();
						this.map.updatePosition(novaPosicao);
						
						this.setAreaVisible(linha, coluna);
					}
				}
				
				if (novaPosicao.getTrap() instanceof FallingRock){
					novaPosicao.removeCreature();

					TrapEvasionMovementEnum evasionOption = TrapEvasionMovementEnum.getEnumById(lance.getTrapEvasionMovementId());
					switch(lance.getDirection()){
					case UP: {	if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
								} else {
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
								}
								break;
								}
					case DOWN: {if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
								} else {
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
								}
								break;
								}
					case LEFT: {if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
									novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
								} else {
									novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
								}
								break;
								}
					default: {if (TrapEvasionMovementEnum.FORWARD.equals(evasionOption)) {
									novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
								} else {
									novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
								}
								break;
								}
					}
					novaPosicao.setCreature(criatura);
					criatura.setCurrentPosition(novaPosicao);
					
					linha = novaPosicao.getRow();
					coluna = novaPosicao.getColumn();
					this.map.updatePosition(novaPosicao);
					
					this.setAreaVisible(linha, coluna);
					
				}
				
				body = criatura.getBody();
				if (body <= 0) {
					criatura.setStatus(StatusEnum.DEAD);
					this.GUI.announceUnfortunateDeath(criatura);
					this.killCreature(criatura.getID());
					
					this.tratarFinalizarJogada(lance);

				}
			//}
		}
	}

	private void setAreaVisible(byte linha, byte coluna) {

		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				if (i >= 0 && j >= 0 && i < this.map.getTotalNumberOfRows() && j < this.map.getTotalNumberOfColumns()) {
					Position posicao = this.map.getPosition((byte)i, (byte)j);
					posicao.setVisible(true);
					if (posicao.getCreature() != null)
						this.getCreaturePorID(posicao.getCreature().getID()).setVisible(true);
						
					//this.map.atualizarPosicao(posicao, (byte)i, (byte)j);
				}
			}
		}
		
	}

	private void tratarAbrirPorta(OpenDoor lance) {
		int doorId = lance.getDoorId();
		Door door = this.getPorta(doorId);
		if (!door.isOpen()){
			door.openDoor();
		} else {
			door.closeDoor();
		}
	}
	
	private void tratarFinalizarJogada(Action action) {
		this.GUI.updatePlayerSurroundings(); // added for GUI refresh
		
		Creature daVez;
		Creature finalizada = this.removeCreatureFromQueue();
		this.creatureQueue.trimToSize();
		this.insertCreatureIntoQueue(finalizada);
		
		if (!(finalizada instanceof Wizard) && !(finalizada instanceof Elf)){
			StatusEnum finalizadaStatusEnum = finalizada.getStatus();
			if (finalizadaStatusEnum == StatusEnum.AGILITY_UP
				|| finalizadaStatusEnum == StatusEnum.AGILITY_DOWN){
					
				finalizada.setStatus(StatusEnum.NEUTRAL);
			}
		}
		
		
		daVez = this.getCurrentCreature();
		// int body = criatura.getBody();
		StatusEnum statusEnum = daVez.getStatus();
		
		byte verificados = 0;
		while ((statusEnum == StatusEnum.DEAD || daVez.isVisible() == false || statusEnum == StatusEnum.CURSED
			|| statusEnum == StatusEnum.SLEEPING)
			&& verificados <= this.getCreatureQueue().size()) {
			
			if (statusEnum == StatusEnum.CURSED ||
				statusEnum == StatusEnum.AGILITY_UP ||
				statusEnum == StatusEnum.AGILITY_DOWN){
				
				daVez.setStatus(StatusEnum.NEUTRAL);
			}
			
			if (statusEnum == StatusEnum.SLEEPING){
				byte roundsToSleep = (byte)(daVez.getRoundsToSleep()-1);
				daVez.setRoundsToSleep(roundsToSleep);
				if (roundsToSleep == 0){
					daVez.setStatus(StatusEnum.NEUTRAL);
					this.GUI.showMessagePopup(Strings.THECREATURE.toString()+daVez.getClass().getSimpleName()+Strings.WOKEUP.toString());
				}
			}
			finalizada = this.removeCreatureFromQueue();
			this.creatureQueue.trimToSize();
			this.insertCreatureIntoQueue(finalizada);
			daVez = this.getCurrentCreature();
			statusEnum = daVez.getStatus();
			
			verificados++;
			
			// body = criatura.getBody();
		}
		daVez.setMovement();
		
		// Courage status removal
		ArrayList<Creature> possiveisAlvos = this.getAvailableTargets(1, daVez.getCurrentPosition());
		if (possiveisAlvos.size() == 1){ // Cannot any enemies
			if (daVez.getStatus() == StatusEnum.COURAGE){
				daVez.setStatus(StatusEnum.NEUTRAL);
			}
		}
		
		this.map.specialOcurrence(this); // For TheRescueOfSirRagnar and TheStoneHunter
		
		this.encerramentoDaPartida();
		
		/*Adventurer a = this.localAdventurer; 
		if (a != null){
			String creatureName = a.getPlayableCharacter().getClass().getSimpleName();
			if (creatureName != null){
				if (daVez.getClass().getSimpleName().equals(creatureName)){
					this.atorJogador.mostrarMensagem(Strings.YOURTURN.toString()+Strings.REMAININGMOVES.toString()+daVez.getMovement());
				}			
			}
		}*/
		//System.out.println("" + criatura.getClass().getSimpleName());
	}

	private void sortCreatureQueueByID() {
		Collections.sort(this.creatureQueue);
	}

	private void setDwarfAvailable(boolean b) {
		dwarfAvailable = b;
	}

	private void setElfAvailable(boolean b) {
		elfAvailable = b;
	}

	private void setWizardAvailable(boolean b) {
		wizardAvailable = b;
	}

	private void setBarbarianAvailable(boolean b) {
		barbarianAvailable = b;
	}

	private void setZargonAvailable(boolean b) {
		zargonAvailable = b;
	}

	private Creature removeCreatureFromQueue() {
		Creature criatura = this.creatureQueue.remove(0);
		this.creatureQueue.trimToSize();
		return criatura;
	}

	private void insertCreatureIntoQueue(Creature creature) {
		int index = this.creatureQueue.size();
		this.creatureQueue.add(index, creature);
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
				this.GUI.reportError(Strings.MONSTER_CANT_UNDERSTAND_COMMAND.toString());
			}
		} else {
			this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	public void selectCharacter() throws ClassNotFoundException {
		boolean doesSaveFileWithPlayerNameExist = this.GUI.checkSaveFileExists(localPlayerName);
		if (doesSaveFileWithPlayerNameExist) {
			int choice = JOptionPane.showConfirmDialog(null, Strings.CONFIRM_LOAD_FILE);
			if (choice == JOptionPane.YES_OPTION) {
				ArrayList<String> fileInformation = null;
				try {
					fileInformation = this.GUI.readSaveFile(localPlayerName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.processCharacterSelection(Integer.parseInt(fileInformation.get(0)));
				this.localAdventurer.getPlayableCharacter().increaseGold(Integer.parseInt(fileInformation.get(1)));
			} else {
				this.GUI.showCharacterSelectionScreen();
			}
		} else {
			this.GUI.showCharacterSelectionScreen();
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
				isCharacterAvailable = this.getBarbarianAvailable();
				break;
			case WIZARD:
				isCharacterAvailable = this.getWizardAvailable();
				break;
			case ELF:
				isCharacterAvailable = this.getElfAvailable();
				break;
			case DWARF:
				isCharacterAvailable = this.getDwarfAvailable();
				break;
			default:
				this.GUI.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
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
					this.GUI.reportError(Strings.CHARACTER_SELECTION_ERROR.toString());
					break;
			}
			this.processAction(action);
			this.sendAction(action);
		} else {
			this.GUI.reportError(Strings.CHARACTER_UNAVAILABLE.toString());
			this.selectCharacter();
		}
	}

	private boolean getZargonAvailable() {
		return zargonAvailable;
	}

	private Player removePlayerFromQueue() {
		return this.players.remove(0);
	}

	private void insertPlayerIntoQueue(Player player) {
		int index = this.players.size();
		this.players.add(index, player);
	}

	private boolean getBarbarianAvailable() {
		return barbarianAvailable;
	}

	private boolean getWizardAvailable() {
		return wizardAvailable;
	}

	private boolean getElfAvailable() {
		return elfAvailable;
	}

	private boolean getDwarfAvailable() {
		return dwarfAvailable;
	}

	private void killCreature(int creatureID) {
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			Creature creature = this.creatureQueue.get(i);
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
				this.GUI.reportError(Strings.MONSTER_CANT_UNDERSTAND_COMMAND.toString());
			}
		} else {
			this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	public void endTurn() {
		if (this.verifyIfItIsCurrentPlayersTurn()) {
			EndTurn action = new EndTurn();
			this.processAction(action);
			this.sendAction(action);
		} else {
			this.GUI.reportError(Strings.NOT_YOUR_TURN.toString());
		}
	}

	public void endGame() {
		int option = JOptionPane.showConfirmDialog(null, Strings.END_GAME.toString());
		if (option == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}

	public void iniciarNovaPartida(int posicao) {
		this.setGameInSession(true);
		
		//this.map = new Map(this);
		
		//String idJogador = this.atorJogador.informarNomeJogador();
		String idJogador = this.localPlayerName;
		Player player = this.criarJogador(idJogador);
		this.setLocalPlayer(player);
		SendPlayer lance = new SendPlayer();
		lance.setPlayer(player);
		this.processAction(lance);
		this.sendAction(lance);
		this.GUI.refreshGUI();
	}

	public void iniciarPartida(int numJog) {
		this.getAtorClienteServidor().iniciarPartida(numJog);
	}

	private void encerramentoDaPartida() {
		boolean aventureirosVivos = this.verificarSeJogadoresVivos();
		if (aventureirosVivos) {
			boolean condicoesCumpridas = this.map.verifyWinningConditions(this);
			if (condicoesCumpridas) {
				// Save player file
				if (this.localAdventurer != null) {
					try {
						int heroType;
						PlayableCharacter a = this.localAdventurer.getPlayableCharacter();
						if (a.getStatus() != StatusEnum.DEAD){
							switch (a.getClass().getSimpleName()){
								case "Barbarian": heroType = 1;
												break;
								case "Wizard": heroType = 2;
												break;
								case "Elf": heroType = 3;
												break;
								default: heroType = 4;
												break;
										
							}
							this.GUI.writeSaveFile(localPlayerName, heroType, a.getGold(), a.getItems(this.map));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.GUI.announceHeroesWon();
				this.endGame();
			}
		} else {
			// Announce villain victory
			this.GUI.announceZargonWon();
			// End game
			this.endGame();
		}
	}

	private boolean verificarSeJogadoresVivos() {
		int qtdCriaturas = this.creatureQueue.size();
		for (int i = 0; i < qtdCriaturas; i++) {
			Creature criatura = this.creatureQueue.get(i);
			if (criatura instanceof Barbarian || criatura instanceof Wizard
					|| criatura instanceof Elf || criatura instanceof Dwarf) {
				if (criatura.getStatus() != StatusEnum.DEAD)
					return true;
			}
		}
		return false;
		/*
		 * int qtdPlayers = this.players.size(); int qtdadeMortos = 0; for(int
		 * i=0; i<qtdPlayers; i++) { if (players.get(i) instanceof Adventurer) {
		 * PlayableCharacter player = ((Adventurer)
		 * players.get(i)).getPlayableCharacter(); if (player instanceof
		 * Barbarian || player instanceof Wizard || player instanceof Elf ||
		 * player instanceof Dwarf) { if (player.status == Status.DEAD) {
		 * qtdadeMortos++; System.out.println(""+qtdadeMortos); } } } } if
		 * (qtdadeMortos == qtdPlayers-1) { return true; } return false;
		 */
	}

	private Player criarJogador(String idJogador) {
		return new Player(idJogador);
	}

	public void mostrarInventario() {
		//if (localAdventurer.getPlayableCharacter() != null) {
		if (this.localAdventurer != null) {
			PlayableCharacter character = localAdventurer
					.getPlayableCharacter();
			int gold = character.getGold();
			ArrayList<Items> items = character.getItems(this.map);
			this.GUI.showInventory(gold, items);
		} else {
			this.GUI.reportError(Strings.ZARGONNOGOLD.toString());
		}
	}

	public void mostrarInformacoes(int creatureID) {
		Creature criatura = this.getCreatureFromQueue(creatureID);
		if (criatura.isVisible() == true){
			byte body = criatura.getBody();
			byte mind = criatura.getMind();
			byte movement = criatura.getMovement();
			StatusEnum statusEnum = criatura.getStatus();
			Position posicao = criatura.getCurrentPosition();
			byte linha = posicao.getRow();
			byte coluna = posicao.getColumn();
			byte roundsToSleep = criatura.getRoundsToSleep();
		
			this.GUI.showCreatureInformation(body, mind, movement, statusEnum,
					linha, coluna, roundsToSleep);
		} else {
			this.GUI.showMessagePopup(Strings.UNKNOWN.toString());
		}
	}

	private Creature getCreatureFromQueue(int creatureID) {
		Creature criatura = null;
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			if (this.creatureQueue.get(i).getID() == creatureID) {
				criatura = this.creatureQueue.get(i);
			}
		}
		return criatura;
	}

	public Player getPlayerDaVez() {
		return this.players.get(0);
	}

	private void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	public Creature getLastCreatureFromQueue() {
		int last = this.creatureQueue.size() - 1;
		return this.creatureQueue.get(last);
	}

	public Position getPosition(byte i, byte j) {
		return this.map.getPosition(i, j);
	}

	public void creatureInPosition(Creature creature, int row, int column) {
		this.map.positions[row][column].setCreature(creature);
		creature.setCurrentPosition(this.map.positions[row][column]);
	}
	
	public Creature getCreaturePorID(int creatureID) {
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			Creature criatura = this.creatureQueue.get(i);
			if (criatura.getID() == creatureID) {
				return criatura;
			}
		}
		return null;
	}

	public void setLocalPlayerName(String playerName) {
		this.localPlayerName = playerName;
	}

	public void setServerAddress(String serverAddress) {
		this.GUI.setTitle(this.GUI.getTitle()+ Strings.SERVER + serverAddress + Strings.COMMAPLAYER + this.localPlayerName);
	}

	public AtorClientServer getAtorClienteServidor() {
		return atorClienteServidor;
	}
	
	public BasicMap getMap() {
		return map;
	}

	public void setMap(BasicMap map) {
		this.map = map;
	}

	public void setAtorJogador(GUI GUI) {
		this.GUI = GUI;
	}

	public GUI getAtorJogador() {
		return GUI;
	}
}
