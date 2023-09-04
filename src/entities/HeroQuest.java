package entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import entities.actions.*;
import entities.enemies.Monster;
import entities.players.*;
import entities.tiles.*;
import entities.utils.Strings;
import enums.ActionTypeEnum;
import enums.DirectionEnum;
import enums.StatusEnum;
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
	protected boolean inSession;
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
		this.inSession = false;
		this.localAdventurer = null;
		this.localZargon = null;
		
		//this.map = new Map(this);
		//this.atorJogador.selectQuest(this);
		//this.map = new TheTrial(this); // Uncomment to make game work, comment to make stub work
	}

	public boolean isConnected() {
		return this.connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean getInSession() {
		return this.inSession;
	}

	public void setInSession(boolean valor) {
		this.inSession = valor;
	}
	
	public void abrirPorta(int idPorta) {
		Creature criatura = this.getCurrentCreature();
		if (criatura instanceof Barbarian || criatura instanceof Wizard
				|| criatura instanceof Elf || criatura instanceof Dwarf){
			
			boolean daVez = this.verificaSeJogadorDaVez();
			if (daVez) {
				Door porta = this.getPorta(idPorta);
				boolean perto = this.verificaSePertoDaPorta(
						(PlayableCharacter) criatura, porta);
				
				boolean secreta = porta.isSecreta();
				if (!secreta){
					if (perto) {
						//boolean aberta = porta.getPortaEstaAberta();
						
							OpenDoor lance = new OpenDoor();
							lance.setDoorId(idPorta);
							this.tratarLance(lance);
							this.enviarLance(lance);
							
					} else {
						this.GUI.reportError(Strings.DOOROUTOFRANGE.toString());
					}
				}
			} else {
				this.GUI
						.reportError(Strings.NOTYOURTURN.toString());
			}
		} else {
			this.GUI
			.reportError(Strings.CANTOPENDOOR.toString());
		}
	}
	
	public void abrirPortaTeclado() {
		Creature criatura = this.getCurrentCreature();
		if (criatura instanceof Barbarian || criatura instanceof Wizard
				|| criatura instanceof Elf || criatura instanceof Dwarf){

			boolean daVez = this.verificaSeJogadorDaVez();
			if (daVez) {
				byte linha = criatura.getCurrentPosition().getRow();
				byte coluna = criatura.getCurrentPosition().getColumn();
				Position norte = this.getPosition((byte)(linha-1), (byte)coluna);
				Position leste = this.getPosition((byte)(linha), (byte)(coluna+1));
				Position sul = this.getPosition((byte)(linha+1), (byte)coluna);
				Position oeste = this.getPosition((byte)(linha), (byte)(coluna-1));
				
				ArrayList<String> portaIds = new ArrayList<>();
				ArrayList<String> portaId = new ArrayList<>();
				
				if (norte instanceof Door){
					if (!((Door) norte).isSecreta()){
						portaId.add(""+norte.getRow()+norte.getColumn());
						portaIds.add(Strings.NORTH.toString());
					}
				} if (leste instanceof Door){
					if (!((Door) leste).isSecreta()){
						portaId.add(""+leste.getRow()+leste.getColumn());
						portaIds.add(Strings.EAST.toString());
					}
				} if (sul instanceof Door){
					if (!((Door) sul).isSecreta()){
						portaId.add(""+sul.getRow()+sul.getColumn());
						portaIds.add(Strings.SOUTH.toString());
					}
				} if (oeste instanceof Door){
					if (!((Door) oeste).isSecreta()){
						portaId.add(""+oeste.getRow()+oeste.getColumn());
						portaIds.add(Strings.WEST.toString());
					}
				}
				
				
				if (!portaIds.isEmpty()) {
					
					int escolhida = this.GUI.selectDoorToOpen(portaIds);
					int idPorta = Integer.parseInt(portaId.get(escolhida));
				
					//Door porta = this.getPorta(idPorta);
				
					/*boolean perto = this.verificaSePertoDaPorta(
							(PlayableCharacter) criatura, porta);*/
					//if (perto) {
					
						OpenDoor lance = new OpenDoor();
						lance.setDoorId(idPorta);
						this.tratarLance(lance);
						this.enviarLance(lance);
		 
				} else {
					this.GUI.reportError(Strings.DOOROUTOFRANGE.toString());
				}
			} else {
				this.GUI
						.reportError(Strings.NOTYOURTURN.toString());
			}
		} else {
			this.GUI
			.reportError(Strings.CANTOPENDOOR.toString());
		}
	}
	

	private boolean verificaSeJogadorDaVez() {
		int idCriaturaLocal, idCriaturaDaVez;
		idCriaturaDaVez = this.getCurrentCreature().getID();
		if (this.localAdventurer != null) {
			idCriaturaLocal = (this.localAdventurer).getPlayableCharacter()
					.getID();
			return idCriaturaLocal == idCriaturaDaVez;
		} else {
			//for (int i = 0; i < this.creatureQueue.size() - players.size() + 1; i++) {
			
			// Attempt of fixing null pointer exception (index 19 inaccessible)
			for (int i = 0; i < this.creatureQueue.size() - players.size(); i++) {
				idCriaturaLocal = (this.localZargon).getMonster(i).getID();
				if (idCriaturaDaVez == idCriaturaLocal)
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

	private boolean verificaSeDistanciaPossivel(Position pos1, Position pos2, boolean hasSpear) {
		int linhaAtacante, colunaAtacante, linhaAlvo, colunaAlvo;
		linhaAtacante = pos1.getRow();
		colunaAtacante = pos1.getColumn();
		linhaAlvo = pos2.getRow();
		colunaAlvo = pos2.getColumn();
		boolean result = false;
		if (hasSpear){
			// add diagonal
			result = (linhaAtacante == linhaAlvo-1 && colunaAtacante == colunaAlvo-1)
					|| (linhaAtacante == linhaAlvo+1 && colunaAtacante == colunaAlvo+1)
					|| (linhaAtacante == linhaAlvo+1 && colunaAtacante == colunaAlvo-1)
					|| (linhaAtacante == linhaAlvo-1 && colunaAtacante == colunaAlvo+1);
		}
		return result
				|| (linhaAtacante == linhaAlvo && colunaAtacante == colunaAlvo - 1)
				|| (linhaAtacante == linhaAlvo && colunaAtacante == colunaAlvo + 1)
				|| (colunaAtacante == colunaAlvo && linhaAtacante == linhaAlvo - 1)
				|| (colunaAtacante == colunaAlvo && linhaAtacante == linhaAlvo + 1)
				|| (pos1.equals(pos2));
	}

	private boolean verificaSePertoDaPorta(PlayableCharacter character,
			Door porta) {
		byte linhaAventureiro, colunaAventureiro, linhaPorta, colunaPorta;
		linhaAventureiro = character.getCurrentPosition().getRow();
		colunaAventureiro = character.getCurrentPosition().getColumn();
		linhaPorta = porta.getRow();
		colunaPorta = porta.getColumn();
		return (linhaAventureiro == linhaPorta && colunaAventureiro == colunaPorta - 1)
				|| (linhaAventureiro == linhaPorta && colunaAventureiro == colunaPorta + 1)
				|| (colunaAventureiro == colunaPorta && linhaAventureiro == linhaPorta - 1)
				|| (colunaAventureiro == colunaPorta && linhaAventureiro == linhaPorta + 1);
	}

	public void movimentar(DirectionEnum direcao) {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature criatura = this.getCurrentCreature();
			
			if (criatura.getStatus() == StatusEnum.SLEEPING){
				this.GUI.reportError(Strings.SLEEPFREEZE.toString());
			} else {
			
				byte movimento = criatura.getMovement();
				if (movimento > 0) {
					Position posicaoAtual = criatura.getCurrentPosition();
					byte linha = posicaoAtual.getRow();
					byte coluna = posicaoAtual.getColumn();
					Position novaPosicao;
					try {
						novaPosicao = this.getNovaPosicao(direcao, linha, coluna);
						Move lance = new Move();
					
					
						lance.setSourceRow(linha);
						lance.setSourceColumn(coluna);
						lance.setDestinationRow(novaPosicao.getRow());
						lance.setDestinationColumn(novaPosicao.getColumn());

						Trap trap = novaPosicao.getTrap();
					
						if (trap != null) {
							byte dano = trap.getDeliveredDamage();
							lance.setDamage(dano);
							
							if (trap instanceof FallingRock){
								byte opcao = this.GUI.showFallingRockMovementOptions();
								
								
								switch(direcao){
								case UP: {	if (opcao == 0){
												novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
												if (novaPosicao instanceof Wall){
													opcao = 1;
												}
											}
											break;
											}
								case DOWN: {if (opcao == 0){
												novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
												if (novaPosicao instanceof Wall){
													opcao = 1;
												}
											}
											break;
											}
								case LEFT: {if (opcao == 0){
												novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
												if (novaPosicao instanceof Wall){
													opcao = 1;
												}
											}
											break;
											}
								default: {if (opcao == 0){
												novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
												if (novaPosicao instanceof Wall){
													opcao = 1;
												}
											}
											break;
											}
								}
								
								lance.setDirection(direcao);
								lance.setOption(opcao);
							} else if (trap instanceof Pit){
								
								if (trap.getVisible()){
								
									byte opcao = this.GUI.showPitJumpingOptions();
									
									switch(direcao){
									case UP: {	if (opcao == 0){
													novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
													if (novaPosicao instanceof Wall){
														opcao = 1;
													}
												}
												break;
												}
									case DOWN: {if (opcao == 0){
													novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
													if (novaPosicao instanceof Wall){
														opcao = 1;
													}
												}
												break;
												}
									case LEFT: {if (opcao == 0){
													novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
													if (novaPosicao instanceof Wall){
														opcao = 1;
													}
												}
												break;
												}
									default: {if (opcao == 0){
													novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
													if (novaPosicao instanceof Wall){
														opcao = 1;
													}
												}
												break;
												}
									}
									
									lance.setDirection(direcao);
									lance.setOption(opcao);
									} else {
										lance.setDirection(direcao);
										lance.setOption((byte)2);
									}
							}
						}
					
						this.tratarLance(lance);
						this.enviarLance(lance);

					} catch (PositionNotEmptyException e) {
						this.GUI.reportError(Strings.PHYSICSLAWS.toString());
					}
				} else {
					this.GUI
							.reportError(Strings.NOMOVELEFT.toString());
					}
				}
			} else {
				this.GUI.reportError(Strings.NOTYOURTURN.toString());
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
						.getPortaEstaAberta())
				|| (novaPosicao.getTrap() != null && novaPosicao.getTrap() instanceof FallingRock && novaPosicao.getTrap().getTriggered())) {
			throw new PositionNotEmptyException();
		} else {
			return novaPosicao;
		}
	}

	public void atacar() {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature atacante = this.getCurrentCreature();
			
			if (atacante.getStatus() == StatusEnum.SLEEPING){
				this.GUI.reportError(Strings.SLEEPNOATT.toString());
			} else{
				Position posicaoAtacante = atacante.getCurrentPosition();
				boolean hasSpear = false;
				// If its an adventurer and has a spear, can attack diagonally
				if (!(atacante.getClass().getSuperclass().getSimpleName().equals("Monster")) &&
						((PlayableCharacter) atacante).getItems(this.map).contains(Items.Spear)){
					hasSpear = true;
				}
				ArrayList<Creature> possiveisAlvos = this.getPossiveisAlvos(1,
						posicaoAtacante);
				Creature alvo = this.GUI.selectTarget(possiveisAlvos);
				Position posicaoAlvo = alvo.getCurrentPosition();
				boolean possivel = this.verificaSeDistanciaPossivel(
						posicaoAtacante, posicaoAlvo, hasSpear);
				if (possivel) {
					byte dano = this.calcularDanoDoAtaque(atacante, alvo);
					Attack lance = new Attack();
					lance.setValue(dano);
					lance.setTargetID(alvo.getID()); ///////////////////////////////////////////
					this.tratarLance(lance);
					this.enviarLance(lance);
				} else {
					this.GUI
						.reportError(Strings.OUTOFRANGE.toString());
				}
			}
		} else {
			this.GUI.reportError(Strings.NOTYOURTURN.toString());
		}
	}

	private ArrayList<Creature> getPossiveisAlvos(int area, Position pos) {
		ArrayList<Creature> possiveisAlvos = new ArrayList<Creature>();
		byte linha = pos.getRow();
		byte coluna = pos.getColumn();
		for (int i = linha - area; i <= linha + area; i++) {
			for (int j = coluna - area; j <= coluna + area; j++) {
				if (i >= 0 && i < this.map.getNumberOfRows() && j >= 0 && j < this.map.getNumberOfColumns()) {
					Position posicao = this.map.getPosition((byte)i, (byte)j);
					if (posicao.getCreature() != null) {
						possiveisAlvos.add(posicao.getCreature());
					}
				}
			}
		}
		return possiveisAlvos;
	}

	private byte calcularDanoDoAtaque(Creature atacante, Creature alvo) {
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

	public void usarMagia() {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature atacante = this.getCurrentCreature();
			if (atacante instanceof Wizard) {
				ArrayList<Spell> magiasDisponiveis = ((Wizard) atacante)
						.getSpells();
				byte mind = atacante.getMind();
				if (mind > 0) {
					Spell magia = this.GUI
							.selectSpell(magiasDisponiveis);
					Position posicaoAtual = atacante.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this
							.getPossiveisAlvos((byte) 2, posicaoAtual);
					Creature alvo = this.GUI
							.selectTarget(possiveisAlvos);
					boolean sucesso = this.calcularSucessoDaMagia(atacante,
							alvo, magia);
					if (sucesso) {
						CastSpell lance = new CastSpell();
						if (magia.getStatus() == StatusEnum.SLEEPING){ // determinar o numero de rodadas a dormir
							byte roundsToSleep = 0;
							byte dado = 0;
							byte mindAlvo = alvo.getMind();
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
						lance.setSpell(magia);
						lance.setTargetID(alvo.getID());
						this.tratarLance(lance);
						this.enviarLance(lance);
					}
				} else {
					this.GUI.reportError(Strings.NOMIND.toString());
				}
			} else if (atacante instanceof Elf) {
				ArrayList<Spell> magiasDisponiveis = ((Elf) atacante)
						.getSpells();
				byte mind = atacante.getMind();
				if (mind > 0) {
					Spell magia = this.GUI
							.selectSpell(magiasDisponiveis);
					Position posicaoAtual = atacante.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this
							.getPossiveisAlvos((byte) 2, posicaoAtual);
					Creature alvo = this.GUI
							.selectTarget(possiveisAlvos);
					boolean sucesso = this.calcularSucessoDaMagia(atacante,
							alvo, magia);
					if (sucesso) {
						CastSpell lance = new CastSpell();
						lance.setSpell(magia);
						lance.setTargetID(alvo.getID());
						this.tratarLance(lance);
						this.enviarLance(lance);
					}
				} else {
					this.GUI.reportError(Strings.NOMIND.toString());
				}
			} else {
				this.GUI.reportError(Strings.DOESNTUSESPELLS.toString());
			}
		} else {
			this.GUI.reportError(Strings.NOTYOURTURN.toString());
		}
	}

	public ArrayList<Creature> getCreatureQueue() {
		return this.creatureQueue;
	}

	private boolean calcularSucessoDaMagia(Creature caster, Creature target,
			Spell spell) {
		byte dano;
		//int probabilidade;
		boolean success; //, defendeu;
		dano = spell.getDamage();
		success = true;

		if (spell.getName() == "Ball of Flame"){
			// 2 de dano, menos 1 por cada 5 ou 6 rolados em 2 dados
			byte dado1 = (byte)(Math.random()*6);
			byte dado2 = (byte)(Math.random()*6);
			
			if (dado1 > 3) {
				dano++;
			}
			if (dado2 > 3) {
				dano++;
			}
			spell.setDamage(dano);
			
			if (dano == 0) {
				success = false;
				this.GUI.showMessagePopup(Strings.MAGICFAIL.toString());
			}
		}
		
		if (spell.getName() == "Fire of Wrath") {
			// 1 de dano, 0 se conseguir rolar 5 ou 6 em um dado
			byte dado = (byte)(Math.random()*6);
			
			if (dado > 3) {
				dano++;
			}
			spell.setDamage(dano);
			
			if (dano == 0) {
				success = false;
				this.GUI.showMessagePopup(Strings.MAGICFAIL.toString());
			}
		}
		
		if (spell.getName() == "Sleep"){
			
			String nomeAlvo = target.getClass().getSimpleName();
			if (nomeAlvo == "Zombie" || nomeAlvo == "Mummy" || nomeAlvo == "Skeleton"){
				success = false;
			} else {
				// bota para dormir, se n�o rolar 1 6 em (mind) dados
				byte mind = target.getMind();
				byte dado;
				for (byte i = 0; i < mind; i++){
					dado = (byte)(Math.random()*6);
					if (dado == 5){
						success = false;
						this.GUI.showMessagePopup(Strings.MAGICFAIL.toString());
						break;
					}
				}
			}
		}
		
		/*if (dano < 0) {
			probabilidade = 3;
			defendeu = new Random().nextInt(probabilidade) == 0;
			if (defendeu) {
				success = false;
			}
		}*/
		return success;
	}

	public void enviarLance(Action action) {
		this.getAtorClienteServidor().enviarJogada(action);
	}

	public void tratarLance(Action action) {
		String actionType = action.getClass().getSimpleName();
		switch (ActionTypeEnum.getByName(actionType)) {
			case MOVE:
				this.tratarMovimento((Move) action);
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
			case SEARCH_TRAPS:
				this.tratarProcurarArmadilha((SearchTraps) action);
				break;
			case SEARCH_TREASURE:
				this.tratarProcurarTesouro((SearchTreasure) action);
				break;
			case CHOOSE_CHARACTER:
				this.tratarSelecionarPersonagem((ChooseCharacter) action);
				this.GUI.refreshGUI();
				break;
		}
		//this.atorJogador.atualizarInterfaceGrafica();
		this.GUI.updatePlayerSurroundings();
	}
	
	// Inserir aqui a area visivel inicial por personagem
	private void tratarSelecionarPersonagem(ChooseCharacter lance) {
		PlayableCharacter character;
		Adventurer playerA;
		byte personagem = lance.getValue();
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
				this.GUI.reportError(Strings.CHARSELECTERROR.toString());
				break;
		}
		this.sortCreatureQueueByID();
		this.GUI.showVisibleCreaturesInQueue();
	}

	private void tratarProcurarTesouro(SearchTreasure lance) {
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
				if (i >= 0 && i < this.map.getNumberOfRows() && j >= 0 && j < this.map.getNumberOfColumns()){
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

	private void tratarProcurarArmadilha(SearchTraps lance) {
		int linha = lance.getSourceRow();
		int coluna = lance.getSourceColumn();
		Position posicaoAtual;
		
		boolean removeuArmadilhas = false;
		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				if (i >= 0 && i < this.map.getNumberOfRows() && j >= 0 && j < this.map.getNumberOfColumns()) {
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
							this.GUI.showMessagePopup(Strings.DISARMTREASURETRAP.toString());
						}
					}
					if (posicaoAtual instanceof Door){
						if (((Door) posicaoAtual).isSecreta()){
							((Door) posicaoAtual).setSecreta(false);
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

	private void tratarMovimento(Move lance) {
		Creature criatura;
		byte body;
		byte linha;
		byte coluna;
		
		
		
		Position posicaoAtual = map.getPosition(lance.getSourceRow(), lance.getSourceColumn());
		
		Position novaPosicao = map.getPosition(lance.getDestinationRow(), lance.getDestinationColumn());
		
		
		criatura = this.getCurrentCreature();
		
		
		linha = posicaoAtual.getRow();
		coluna = posicaoAtual.getColumn();
		
		posicaoAtual.removeCreature();
		criatura.decreaseMovement();
		
		this.map.atualizarPosicao(posicaoAtual, linha, coluna);
		
		novaPosicao.setCreature(criatura);
		criatura.setCurrentPosition(novaPosicao);
		
		linha = novaPosicao.getRow();
		coluna = novaPosicao.getColumn();
		this.map.atualizarPosicao(novaPosicao, linha, coluna);
		
		this.setAreaVisible(linha, coluna);
		
		//this.setAreavisibleTeste(linha, coluna, novaPosicao.getClass().getSimpleName());
		
		byte dano = lance.getDamage();
		////////Trap trap = novaPosicao.getTrap();   gerada nova se a posicao nao e enviada
		// solucao = enviar dano da trap do pc da origem do movimento
		////if (trap != null) {
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
					if (lance.getOption() != 2){
						novaPosicao.removeCreature();
						
						byte opcao = lance.getOption();
						switch(lance.getDirection()){
						case UP: {	if (opcao == 0){
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
									} else{
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
									}
									break;
									}
						case DOWN: {if (opcao == 0){
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
									} else{
										novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
									}
									break;
									}
						case LEFT: {if (opcao == 0){
										novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
									} else{
										novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
									}
									break;
									}
						default: {if (opcao == 0){
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
						this.map.atualizarPosicao(novaPosicao, linha, coluna);
						
						this.setAreaVisible(linha, coluna);
					}
				}
				
				if (novaPosicao.getTrap() instanceof FallingRock){
					novaPosicao.removeCreature();
					
					byte opcao = lance.getOption();
					switch(lance.getDirection()){
					case UP: {	if (opcao == 0){
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
								} else{
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
								}
								break;
								}
					case DOWN: {if (opcao == 0){
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()+1), lance.getDestinationColumn());
								} else{
									novaPosicao = map.getPosition((byte) (novaPosicao.getRow()-1), lance.getDestinationColumn());
								}
								break;
								}
					case LEFT: {if (opcao == 0){
									novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()-1));
								} else{
									novaPosicao = map.getPosition(lance.getDestinationRow(), (byte) (novaPosicao.getColumn()+1));
								}
								break;
								}
					default: {if (opcao == 0){
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
					this.map.atualizarPosicao(novaPosicao, linha, coluna);
					
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
				if (i >= 0 && j >= 0 && i < this.map.getNumberOfRows() && j < this.map.getNumberOfColumns()) {
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
		int idPorta = lance.getDoorId();
		Door porta = this.getPorta(idPorta);
		if (!porta.getPortaEstaAberta()){
			porta.abrirPorta();
		} else {
			porta.fecharPorta();
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
		ArrayList<Creature> possiveisAlvos = this.getPossiveisAlvos(1, daVez.getCurrentPosition());
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

	public void procurarTesouro() {
		Creature caster = this.getCurrentCreature();
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			if (caster instanceof Barbarian || caster instanceof Wizard
					|| caster instanceof Elf || caster instanceof Dwarf){
				Position source = caster.getCurrentPosition();
				SearchTreasure lance = new SearchTreasure();
				lance.setSourceRow(source.getRow());
				lance.setSourceColumn(source.getColumn());
				this.tratarLance(lance);
				this.enviarLance(lance);
			} else {
				this.GUI
					.reportError(Strings.MONSTERCANTUNDERSTAND.toString());
			}
		} else {
			this.GUI
				.reportError(Strings.NOTYOURTURN.toString());
		}
	}

	public void selecionarPersonagem() throws ClassNotFoundException {
		boolean exists = this.GUI.checkSaveFileExists(localPlayerName);
		if (exists){
			int choice = JOptionPane.showConfirmDialog(null, Strings.CONFIRMLOADGAME);
			if (choice == 0){
				ArrayList<String> values = null;
				try {
					values = this.GUI.readSaveFile(localPlayerName);
					//System.out.println(values);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.selecionarPersonagemEscolhida(Integer.parseInt(values.get(0)));
				this.localAdventurer.getPlayableCharacter().increaseGold(Integer.parseInt(values.get(1)));
			} else {
				this.GUI.showCharacterSelectionScreen();
			}
		} else {
			this.GUI.showCharacterSelectionScreen();
		}
		//int resultado = this.atorJogador.mostrarOsCincoPersonagens();
	}
	
	public void selecionarPersonagemEscolhida(int resultado) throws ClassNotFoundException{
		boolean disponivel = false;
		Zargon playerZ = new Zargon(this);
		Adventurer playerA = new Adventurer();
		PlayableCharacter character;
		ChooseCharacter lance = new ChooseCharacter();
		lance.setValue((byte)resultado);
		//JOptionPane.showMessageDialog(null, resultado);
		switch (resultado) {
			case 0:
				disponivel = this.getZargonAvailable();
				break;
			case 1:
				disponivel = this.getBarbarianAvailable();
				break;
			case 2:
				disponivel = this.getWizardAvailable();
				break;
			case 3:
				disponivel = this.getElfAvailable();
				break;
			case 4:
				disponivel = this.getDwarfAvailable();
				break;
			default:
				this.GUI.reportError(Strings.CHARSELECTERROR.toString());
				disponivel = false;
				break;
		}
		//System.out.println(this.getZargonAvailable()+"\n"+this.getBarbarianAvailable()+"\n"+this.getWizardAvailable()+"\n"+this.getElfAvailable()+"\n"+this.getDwarfAvailable());
		if (disponivel) {
			switch (resultado) {
				case 0:
					this.localZargon = playerZ;
					lance.setZargon(playerZ);
					
					break;
				case 1:
					character = new Barbarian();
					character.setID((byte) (map.getCreatureQueueSize()-3));//26);
					playerA.setPlayableCharacter(character);
					this.localAdventurer = playerA;
					lance.setAdventurer(playerA);
	
					break;
				case 2:
					character = new Wizard();
					character.setID((byte) (map.getCreatureQueueSize()-2));//27);
					playerA.setPlayableCharacter(character);
					this.localAdventurer = playerA;
					lance.setAdventurer(playerA);
	
					break;
				case 3:
					character = new Elf();
					character.setID((byte) (map.getCreatureQueueSize()-1));//28);
					playerA.setPlayableCharacter(character);
					this.localAdventurer = playerA;
					lance.setAdventurer(playerA);
	
					break;
				case 4:
					character = new Dwarf();
					character.setID((byte) (map.getCreatureQueueSize()));//29);
					playerA.setPlayableCharacter(character);
					this.localAdventurer = playerA;
					lance.setAdventurer(playerA);
	
					break;
				default:
					this.GUI.reportError(Strings.CHARSELECTERROR.toString());
					break;
			}
			this.tratarLance(lance);
			this.enviarLance(lance);
			
		} else {
			this.GUI.reportError(Strings.CHARUNAVAILABLE.toString());
			this.selecionarPersonagem();
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
			Creature criatura = this.creatureQueue.get(i);
			if (criatura.getID() == creatureID) {
				criatura.setStatus(StatusEnum.DEAD);
				Position pos = criatura.getCurrentPosition();
				pos.removeCreature();
				this.map.atualizarPosicao(pos, pos.getRow(), pos.getColumn());
				pos.setVisible(true);
				

				
				//this.creatureQueue.remove(criatura);
				//this.localZargon.monsters.remove(criatura);
				//this.creatureQueue.trimToSize();
				//this.localZargon.monsters.trimToSize();
				//this.removerCriaturaPorID(creatureID);
				
				//this.atorJogador.atualizarInterfaceGrafica();
			}
		}
	}

	/*
	 * private void removerCriaturaPorID(int creatureID) { int i = 0; int j = 0;
	 * for (i = 0; i < this.creatureQueue.size(); i++) { if
	 * (this.creatureQueue.get(i).getID() == creatureID) {
	 * this.creatureQueue.remove(i); break; } } this.creatureQueue.trimToSize();
	 * /*for (j = i; j < this.creatureQueue.size()-1; j++) {
	 * this.creatureQueue.set(j, this.creatureQueue.get(j+1)); }
	 * this.creatureQueue.remove(j+1);
	 * 
	 * this.atorJogador.exibirCriaturas(); }
	 */

	public void procurarArmadilhaOuPortaSecreta() {
		Creature caster = this.getCurrentCreature();
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez ) {
			if (caster instanceof Barbarian || caster instanceof Wizard
					|| caster instanceof Elf || caster instanceof Dwarf){
				Position posicao = caster.getCurrentPosition();
				SearchTraps lance = new SearchTraps();
				lance.setSourceColumn(posicao.getColumn());
				lance.setSourceRow(posicao.getRow());
				tratarLance(lance);
				enviarLance(lance);
			} else {
				this.GUI.reportError(Strings.MONSTERCANTUNDERSTAND.toString());
			}
		} else {
			this.GUI.reportError(Strings.NOTYOURTURN.toString());
		}
	}

	public void finalizarJogada() {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			EndTurn lance = new EndTurn();
			this.tratarLance(lance);
			this.enviarLance(lance);
		} else {
			this.GUI.reportError(Strings.NOTYOURTURN.toString());
		}
	}

	public void finalizarJogo() {
		//System.exit(0);
		int option = JOptionPane.showConfirmDialog(null, Strings.ENDGAME.toString());
		if (option == 0){
			System.exit(0);
		}
	}

	public void iniciarNovaPartida(int posicao) {
		this.setInSession(true);
		
		//this.map = new Map(this);
		
		//String idJogador = this.atorJogador.informarNomeJogador();
		String idJogador = this.localPlayerName;
		Player player = this.criarJogador(idJogador);
		this.setLocalPlayer(player);
		SendPlayer lance = new SendPlayer();
		lance.setPlayer(player);
		this.tratarLance(lance);
		this.enviarLance(lance);
		this.GUI.refreshGUI();
	}

	public void iniciarPartida(int numJog) {
		this.getAtorClienteServidor().iniciarPartida(numJog);
	}

	private void encerramentoDaPartida() {
		boolean aventureirosVivos = this.verificarSeJogadoresVivos();
		if (aventureirosVivos) {
			boolean condicoesCumpridas = this.map.verificarCondicoesDeVitoria(this);
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
				this.finalizarJogo();
			}
		} else {
			// Announce villain victory
			this.GUI.announceZargonWon();
			// End game
			this.finalizarJogo();
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
