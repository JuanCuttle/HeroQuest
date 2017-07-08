package modelo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import exceptions.PositionNotEmptyException;

public class HeroQuest {

	protected Map map;
	protected ArrayList<Player> players;
	protected AtorJogador atorJogador;
	protected AtorClientServer atorClienteServidor;
	protected Player localPlayer;
	protected ArrayList<Creature> creatureQueue;
	protected ArrayList<Door> doors;
	protected boolean zargonAvailable;
	protected boolean barbarianAvailable;
	protected boolean wizardAvailable;
	protected boolean elfAvailable;
	protected boolean dwarfAvailable;
	protected boolean conectado;
	protected boolean emAndamento;
	protected Adventurer localAdventurer;
	protected Zargon localZargon;

	public HeroQuest(AtorJogador ator) {
		this.players = new ArrayList<Player>();
		this.atorJogador = ator;
		this.atorClienteServidor = new AtorClientServer(this);
		this.creatureQueue = new ArrayList<Creature>();
		this.doors = new ArrayList<Door>();
		this.zargonAvailable = true;
		this.barbarianAvailable = true;
		this.wizardAvailable = true;
		this.elfAvailable = true;
		this.dwarfAvailable = true;
		this.conectado = false;
		this.emAndamento = false;
		this.localAdventurer = null;
		this.localZargon = null;
		
		
		
		this.startMusic();
	}

	private void startMusic() {
		File f = new File("src/musicas/Castlevania Symphony of the Night Track 03 Dance Of Illusions.wav");
		AudioInputStream audioIn = null;

		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedAudioFileException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
		///////////////////////////////////////////
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		///////////////////////////////////////////
	}

	public void abrirPorta(int idPorta) {
		Creature criatura = this.getCriaturaDaVez();
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez
				&& (criatura instanceof Barbarian || criatura instanceof Wizard
						|| criatura instanceof Elf || criatura instanceof Dwarf)) {
			Door porta = this.getPorta(idPorta);
			boolean perto = this.verificaSePertoDaPorta(
					(PlayableCharacter) criatura, porta);
			if (perto) {
				boolean aberta = porta.getPortaEstaAberta();
				if (aberta) {
					this.atorJogador.reportarErro("A porta já está aberta");
				} else {
					LanceAbrirPorta lance = new LanceAbrirPorta();
					lance.setObjectID(idPorta);
					this.tratarLance(lance);
					this.enviarLance(lance);
					
				}
			} else {
				this.atorJogador.reportarErro("Não está perto da porta.");
			}
		} else {
			this.atorJogador
					.reportarErro("Não é jogador da vez ou seu personagem é um monstro.");
		}
	}

	private boolean verificaSeJogadorDaVez() {
		int idCriaturaLocal, idCriaturaDaVez;
		idCriaturaDaVez = this.getCriaturaDaVez().getID();
		if (this.localAdventurer != null) {
			idCriaturaLocal = (this.localAdventurer).getPlayableCharacter()
					.getID();
			return idCriaturaLocal == idCriaturaDaVez;
		} else {
			for (int i = 0; i < this.creatureQueue.size() - players.size() + 1; i++) {
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

	private boolean verificaSeDistanciaPossivel(Position pos1, Position pos2) {
		int linhaAtacante, colunaAtacante, linhaAlvo, colunaAlvo;
		linhaAtacante = pos1.getRow();
		colunaAtacante = pos1.getColumn();
		linhaAlvo = pos2.getRow();
		colunaAlvo = pos2.getColumn();
		return (linhaAtacante == linhaAlvo && colunaAtacante == colunaAlvo - 1)
				|| (linhaAtacante == linhaAlvo && colunaAtacante == colunaAlvo + 1)
				|| (colunaAtacante == colunaAlvo && linhaAtacante == linhaAlvo - 1)
				|| (colunaAtacante == colunaAlvo && linhaAtacante == linhaAlvo + 1);
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

	public void movimentar(Directions direcao) {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature criatura = this.getCriaturaDaVez();
			byte movimento = criatura.getMovement();
			if (movimento > 0) {
				Position posicaoAtual = criatura.getCurrentPosition();
				byte linha = posicaoAtual.getRow();
				byte coluna = posicaoAtual.getColumn();
				Position novaPosicao;
				try {
					novaPosicao = this.getNovaPosicao(direcao, linha, coluna);
					LanceMovimento lance = new LanceMovimento();
					
					
					lance.setSourceL(linha);
					lance.setSourceC(coluna);
					lance.setDestinationL(novaPosicao.getRow());
					lance.setDestinationC(novaPosicao.getColumn());

					Trap trap = novaPosicao.getTrap();
					
					if (trap != null) {
						byte dano = trap.getDeliveredDamage();
						lance.setDano(dano);
					}
					
					this.tratarLance(lance);
					this.enviarLance(lance);

				} catch (PositionNotEmptyException e) {
					this.atorJogador.reportarErro("Respeite as leis da física");
					//novaPosicao = posicaoAtual;
				}
			} else {
				this.atorJogador
						.reportarErro("Você não tem movimento suficiente nessa rodada.");
			}
		} else {
			this.atorJogador.reportarErro("Não é o jogador da vez.");
		}
	}

	public Creature getCriaturaDaVez() {
		return this.creatureQueue.get(0);
	}

	private Position getNovaPosicao(Directions direcao, byte linha, byte coluna)
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
				|| (novaPosicao instanceof Door && !((Door) novaPosicao)
						.getPortaEstaAberta())) {
			throw new PositionNotEmptyException();
		} else {
			return novaPosicao;
		}
	}

	public void atacar() {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature atacante = this.getCriaturaDaVez();
			Position posicaoAtacante = atacante.getCurrentPosition();
			ArrayList<Creature> possiveisAlvos = this.getPossiveisAlvos(1,
					posicaoAtacante);
			Creature alvo = this.atorJogador.selecionarAlvo(possiveisAlvos);
			Position posicaoAlvo = alvo.getCurrentPosition();
			boolean possivel = this.verificaSeDistanciaPossivel(
					posicaoAtacante, posicaoAlvo);
			if (possivel) {
				byte dano = this.calcularDanoDoAtaque(atacante, alvo);
				LanceAtaque lance = new LanceAtaque();
				lance.setValue(dano);
				lance.setTargetID(alvo.getID()); ///////////////////////////////////////////
				this.tratarLance(lance);
				this.enviarLance(lance);
			} else {
				this.atorJogador
						.reportarErro("Não é possível atacar um alvo tão distante.");
			}
		}
	}

	private ArrayList<Creature> getPossiveisAlvos(int area, Position pos) {
		ArrayList<Creature> possiveisAlvos = new ArrayList<Creature>();
		byte linha = pos.getRow();
		byte coluna = pos.getColumn();
		for (int i = linha - area; i <= linha + area; i++) {
			for (int j = coluna - area; j <= coluna + area; j++) {
				if (i >= 0 && j >= 0) {
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
		defDiceAmount = alvo.getDefenceDiceAmount();
		if (alvo.getStatus() == Status.ROCK_SKIN){
			defDiceAmount++;
		}
		if (atacante.getStatus() == Status.COURAGE){
			atkDiceAmount += 2;
			atacante.setStatus(Status.NEUTRAL);
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
		for (byte i = 1; i <= defDiceAmount; i++) {
			hit = new Random().nextInt(probabilidade) == 0;
			if (hit) {
				defence++;
			}
		}
		result = (byte) (damage - defence);
		if (result >= 0) {
			if(alvo.getStatus() == Status.ROCK_SKIN){
				alvo.setStatus(Status.NEUTRAL);
			}
			return result;
		} else {
			return 0;
		}
	}

	public void usarMagia() {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature atacante = this.getCriaturaDaVez();
			if (atacante instanceof Wizard) {
				ArrayList<Spell> magiasDisponiveis = ((Wizard) atacante)
						.getSpells();
				byte mind = atacante.getMind();
				if (mind > 0) {
					Spell magia = this.atorJogador
							.selecionarMagia(magiasDisponiveis);
					Position posicaoAtual = atacante.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this
							.getPossiveisAlvos((byte) 2, posicaoAtual);
					Creature alvo = this.atorJogador
							.selecionarAlvo(possiveisAlvos);
					boolean sucesso = this.calcularSucessoDaMagia(atacante,
							alvo, magia);
					if (sucesso) {
						LanceMagia lance = new LanceMagia();
						lance.setSpell(magia);
						lance.setTargetID(alvo.getID());
						this.tratarLance(lance);
						this.enviarLance(lance);
					}
				} else {
					this.atorJogador.reportarErro("Mind insuficiente.");
				}
			} else if (atacante instanceof Elf) {
				ArrayList<Spell> magiasDisponiveis = ((Elf) atacante)
						.getSpells();
				byte mind = atacante.getMind();
				if (mind > 0) {
					Spell magia = this.atorJogador
							.selecionarMagia(magiasDisponiveis);
					Position posicaoAtual = atacante.getCurrentPosition();
					ArrayList<Creature> possiveisAlvos = this
							.getPossiveisAlvos((byte) 2, posicaoAtual);
					Creature alvo = this.atorJogador
							.selecionarAlvo(possiveisAlvos);
					boolean sucesso = this.calcularSucessoDaMagia(atacante,
							alvo, magia);
					if (sucesso) {
						LanceMagia lance = new LanceMagia();
						lance.setSpell(magia);
						lance.setTargetID(alvo.getID());
						this.tratarLance(lance);
						this.enviarLance(lance);
					}
				} else {
					this.atorJogador.reportarErro("Mind insuficiente.");
				}
			} else {
				this.atorJogador.reportarErro("Seu personagem não usa magia.");
			}
		} else {
			this.atorJogador.reportarErro("Não é o jogador da vez.");
		}
	}

	public ArrayList<Creature> getCreatureQueue() {
		return this.creatureQueue;
	}

	public boolean calcularSucessoDaMagia(Creature caster, Creature target,
			Spell spell) {
		byte dano;
		int probabilidade;
		boolean success, defendeu;
		dano = spell.getDamage();
		success = true;
		if (dano < 0) {
			probabilidade = 3;
			defendeu = new Random().nextInt(probabilidade) == 0;
			if (defendeu) {
				success = false;
			}
		}
		return success;
	}

	public void enviarLance(Lance lance) {
		this.atorClienteServidor.enviarJogada(lance);
	}

	// Melhorar (uso de interface com Jogada?)
	public void tratarLance(Lance lance) {
		String tipo = lance.getClass().getSimpleName();
		switch (tipo ) {
		case "LanceMovimento":
			this.tratarMovimento((LanceMovimento) lance);
			break;
		case "LanceAbrirPorta":
			this.tratarAbrirPorta((LanceAbrirPorta) lance);
			break;
		case "LanceAtaque":
			this.tratarAtaque((LanceAtaque) lance);
			this.tratarFinalizarJogada(lance);
			break;
		case "LanceEnviarPlayer":
			this.tratarEnviarPlayer((LanceEnviarPlayer) lance);
			break;
		case "LanceFinalizarJogada":
			this.tratarFinalizarJogada(lance);
			break;
		case "LanceMagia":
			this.tratarMagia((LanceMagia) lance);
			this.tratarFinalizarJogada(lance);
			break;
		case "LanceProcArmadilha":
			this.tratarProcurarArmadilha((LanceProcArmadilha) lance);
			break;
		case "LanceProcTesouro":
			this.tratarProcurarTesouro((LanceProcTesouro) lance);
			break;
		case "LanceSelecionarPersonagem":
			this.tratarSelecionarPersonagem((LanceSelecionarPersonagem) lance);
			break;
		}
		this.atorJogador.atualizarInterfaceGrafica();
	}
	
	// Inserir aqui a area visivel inicial por personagem
	private void tratarSelecionarPersonagem(LanceSelecionarPersonagem lance) {
		PlayableCharacter character;
		Adventurer playerA;
		byte personagem = lance.getValue();
		switch (personagem) {
		case 0:
			Zargon playerZ = lance.getZargon();
			for (int i = 0; i < playerZ.getMonsters().size(); i++) {
				this.insertCreatureIntoQueue(playerZ.getMonster(i));
			}
			this.removePlayerFromQueue();
			this.insertPlayerIntoQueue(playerZ);
			this.setZargonAvailable(false);
			break;
		case 1:
			playerA = lance.getAdventurer();
			this.removePlayerFromQueue();
			this.insertPlayerIntoQueue(playerA);
			character = playerA.getPlayableCharacter();
			this.insertCreatureIntoQueue(character);
			this.creatureInPosition(character, 1, 24);
			
			character.setMovement();
			this.setAreaVisible((byte)1, (byte)24);
			
			this.setBarbarianAvailable(false);
			break;
		case 2:
			playerA = lance.getAdventurer();
			this.removePlayerFromQueue();
			this.insertPlayerIntoQueue(playerA);
			character = playerA.getPlayableCharacter();
			((Wizard) character).createSpells();
			this.insertCreatureIntoQueue(character);
			this.creatureInPosition(character, 1, 25);
			
			character.setMovement();
			this.setAreaVisible((byte)1, (byte)25);
			
			this.setWizardAvailable(false);
			break;
		case 3:
			playerA = lance.getAdventurer();
			this.removePlayerFromQueue();
			this.insertPlayerIntoQueue(playerA);
			character = playerA.getPlayableCharacter();
			((Elf) character).createSpells();
			this.insertCreatureIntoQueue(character);
			this.creatureInPosition(character, 2, 24);
			
			character.setMovement();
			this.setAreaVisible((byte)2, (byte)24);
			
			this.setElfAvailable(false);
			break;
		case 4:
			playerA = lance.getAdventurer();
			this.removePlayerFromQueue();
			this.insertPlayerIntoQueue(playerA);
			character = playerA.getPlayableCharacter();
			this.insertCreatureIntoQueue(character);
			this.creatureInPosition(character, 2, 25);
			
			character.setMovement();
			this.setAreaVisible((byte)2, (byte)25);
			
			this.setDwarfAvailable(false);
			break;
		default:
			this.atorJogador.reportarErro("Erro na seleção de personagem.");
			break;
		}
		this.sortCreatureQueueByID();
		this.atorJogador.exibirCriaturas();
	}

	private void tratarProcurarTesouro(LanceProcTesouro lance) {
		PlayableCharacter character;
		byte linha = lance.getSourceL();
		byte coluna = lance.getSourceC();
		
		Position posicaoAtual = this.map.getPosition(linha, coluna);

		character = (PlayableCharacter) posicaoAtual.getCreature();
		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				posicaoAtual = this.map.getPosition((byte)i, (byte)j);
				Treasure tesouro = posicaoAtual.getTreasure();
				if (tesouro != null) {
					int gold = tesouro.getGoldAmount();
					tesouro.setGoldAmount(0);
					character.increaseGold(gold);
					JOptionPane.showMessageDialog(null, "O jogador "
							+ character.getClass().getSimpleName()
							+ " encontrou algumas moedas de ouro.");
				}
			}
		}
	}

	private void tratarProcurarArmadilha(LanceProcArmadilha lance) {
		int linha = lance.getSourceL();
		int coluna = lance.getSourceC();
		Position posicaoAtual;
		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				posicaoAtual = this.map.getPosition((byte)i, (byte)j);
				if (posicaoAtual.getTrap() != null) {
					posicaoAtual.makeTrapVisible();
				}
			}
		}
	}

	private void tratarMagia(LanceMagia lance) {
		byte alvo;
		byte dano;
		byte body;
		Spell magia = lance.getSpell();
		alvo = lance.getTargetID();
		//int id = alvo.getID();
		Creature criatura = this.getCriaturaPorID(alvo);
		dano = magia.getDamage();
		Status status = magia.getStatus();
		if (status != null) {
			criatura.setStatus(status);
		}
		this.getCriaturaDaVez().usarMagia(magia);
		this.atorJogador.anunciarUsoDeMagia(this.getCriaturaDaVez(), magia,
				criatura, dano, status); /////////////// alvo -> criatura
		criatura.increaseBody(dano);
		body = criatura.getBody();
		if (body <= 0) {
			this.atorJogador.anunciarMorteDeCriatura(criatura);
			this.killCreature(alvo);
		}
	}

	private void tratarEnviarPlayer(LanceEnviarPlayer lance) {
		Player player;
		player = lance.getPlayer();
		this.insertPlayerIntoQueue(player);
	}

	private void tratarAtaque(LanceAtaque lance) {
		byte idAlvo;
		byte dano;
		byte body;
		idAlvo = lance.getTargetID();
		dano = lance.getValue();
		//int idAlvo = alvo.getID();
		Creature criatura = this.getCriaturaPorID(idAlvo);
		criatura.decreaseBody(dano);
		body = criatura.getBody();
		this.atorJogador.mostrarDano(this.getCreaturePorID(idAlvo), dano);
		if (body <= 0) {
			this.atorJogador.anunciarMorteDeCriatura(criatura);
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

	private void tratarMovimento(LanceMovimento lance) {
		Creature criatura;
		byte body;
		byte linha;
		byte coluna;
		
		
		
		Position posicaoAtual = map.getPosition(lance.getSourceL(), lance.getSourceC());
		
		Position novaPosicao = map.getPosition(lance.getDestinationL(), lance.getDestinationC());
		
		
		criatura = this.getCriaturaDaVez();
		
		
		
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
		
		byte dano = lance.getDano();
		////////Trap trap = novaPosicao.getTrap();   gerada nova se a posicao nao e enviada
		// solucao = enviar dano da trap do pc da origem do movimento
		////if (trap != null) {
		if (novaPosicao.getTrap() != null) {
			//boolean visivel = trap.getVisible();
			//System.out.println(""+visivel);
			//if (true) {//(!visivel) { //////////////////// Fonte do problema!!!!!!!!
				//dano = trap.getDeliveredDamage();
				criatura.decreaseBody(dano);
				this.atorJogador.mostrarAcaoTrap(dano, criatura);
				novaPosicao.getTrap().makeTrapVisible(); ////////
				
				body = criatura.getBody();
				if (body <= 0) {
					criatura.setStatus(Status.DEAD);
					this.atorJogador.anunciarMorteDesafortunada(criatura);
					this.killCreature(criatura.getID());
					
					this.tratarFinalizarJogada(lance);

				}
			//}
		}
	}

	private void setAreaVisible(byte linha, byte coluna) {

		for (int i = linha - 2; i <= linha + 2; i++) {
			for (int j = coluna - 2; j <= coluna + 2; j++) {
				if (i >= 0 && j >= 0 && i < 27 && j < 50) {
					Position posicao = this.map.getPosition((byte)i, (byte)j);
					posicao.setVisible(true);
					if (posicao.getCreature() != null)
						this.getCreaturePorID(posicao.getCreature().getID()).setVisible(true);
						
					//this.map.atualizarPosicao(posicao, (byte)i, (byte)j);
				}
			}
		}
		
	}

	private void tratarAbrirPorta(LanceAbrirPorta lance) {
		int idPorta = lance.getObjectID();
		Door porta = this.getPorta(idPorta);
		porta.abrirPorta();
	}
	
	private void tratarFinalizarJogada(Lance lance) {
		Creature daVez;
		Creature finalizada = this.removeCreatureFromQueue();
		this.creatureQueue.trimToSize();
		this.insertCreatureIntoQueue(finalizada);
		daVez = this.getCriaturaDaVez();
		// int body = criatura.getBody();
		Status status = daVez.getStatus();
		while (status == Status.DEAD || daVez.isVisible() == false || status == Status.CURSED) {
			finalizada = this.removeCreatureFromQueue();
			this.creatureQueue.trimToSize();
			this.insertCreatureIntoQueue(finalizada);
			daVez = this.getCriaturaDaVez();
			status = daVez.getStatus();
			
			if (finalizada.getStatus() == Status.CURSED){
				finalizada.setStatus(Status.NEUTRAL);
			}
			// body = criatura.getBody();
		}
		daVez.setMovement();
		this.encerramentoDaPartida();
		//System.out.println("" + criatura.getClass().getSimpleName());
	}

	public void sortCreatureQueueByID() {
		Collections.sort(this.creatureQueue);
	}

	public void setDwarfAvailable(boolean b) {
		this.dwarfAvailable = b;
	}

	public void setElfAvailable(boolean b) {
		this.elfAvailable = b;
	}

	public void setWizardAvailable(boolean b) {
		this.wizardAvailable = b;
	}

	public void setBarbarianAvailable(boolean b) {
		this.barbarianAvailable = b;
	}

	public void setZargonAvailable(boolean b) {
		this.zargonAvailable = b;
	}

	public Creature removeCreatureFromQueue() {
		Creature criatura = this.creatureQueue.remove(0);
		this.creatureQueue.trimToSize();
		return criatura;
	}

	public void insertCreatureIntoQueue(Creature creature) {
		int index = this.creatureQueue.size();
		this.creatureQueue.add(index, creature);
	}

	public void procurarTesouro() {
		Creature caster = this.getCriaturaDaVez();
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez
				&& (caster instanceof Barbarian || caster instanceof Wizard
						|| caster instanceof Elf || caster instanceof Dwarf)) {
			Position source = caster.getCurrentPosition();
			LanceProcTesouro lance = new LanceProcTesouro();
			lance.setSourceL(source.getRow());
			lance.setSourceC(source.getColumn());
			this.tratarLance(lance);
			this.enviarLance(lance);
		} else {
			this.atorJogador
					.reportarErro("Não é jogador da vez ou é o Zargon.");
		}
	}

	public void selecionarPersonagem() {
		boolean disponivel = false;
		int resultado = this.atorJogador.mostrarOsCincoPersonagens();
		Zargon playerZ = new Zargon(this);
		Adventurer playerA = new Adventurer();
		PlayableCharacter character;
		LanceSelecionarPersonagem lance = new LanceSelecionarPersonagem();
		lance.setValue((byte)resultado);
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
			this.atorJogador.reportarErro("Erro na seleção de personagem.");
			break;
		}
		if (disponivel) {
			switch (resultado) {
			case 0:
				this.localZargon = playerZ;
				lance.setZargon(playerZ);
				break;
			case 1:
				character = new Barbarian();
				character.setID((byte) 20);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			case 2:
				character = new Wizard();
				character.setID((byte) 21);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			case 3:
				character = new Elf();
				character.setID((byte) 22);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			case 4:
				character = new Dwarf();
				character.setID((byte) 23);
				playerA.setPlayableCharacter(character);
				this.localAdventurer = playerA;
				lance.setAdventurer(playerA);
				break;
			default:
				this.atorJogador.reportarErro("Erro na seleção de personagem.");
				break;
			}
			this.tratarLance(lance);
			this.enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("Personagem não disponível");
		}
	}

	public boolean getZargonAvailable() {
		return this.zargonAvailable;
	}

	private Player removePlayerFromQueue() {
		return this.players.remove(0);
	}

	public void insertPlayerIntoQueue(Player player) {
		int index = this.players.size();
		this.players.add(index, player);
	}

	public boolean getBarbarianAvailable() {
		return this.barbarianAvailable;
	}

	public boolean getWizardAvailable() {
		return this.wizardAvailable;
	}

	public boolean getElfAvailable() {
		return this.elfAvailable;
	}

	public boolean getDwarfAvailable() {
		return this.dwarfAvailable;
	}

	public void killCreature(int creatureID) {
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			Creature criatura = this.creatureQueue.get(i);
			if (criatura.getID() == creatureID) {
				criatura.setStatus(Status.DEAD);
				Position pos = criatura.getCurrentPosition();
				pos.removeCreature();
				this.map.atualizarPosicao(pos, pos.getRow(), pos.getColumn());
				//this.creatureQueue.remove(criatura);
				//this.localZargon.monsters.remove(criatura);
				//this.creatureQueue.trimToSize();
				//this.localZargon.monsters.trimToSize();
				//this.removerCriaturaPorID(creatureID);
				this.atorJogador.atualizarInterfaceGrafica();
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
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			Creature criatura = this.getCriaturaDaVez();
			Position posicao = criatura.getCurrentPosition();
			LanceProcArmadilha lance = new LanceProcArmadilha();
			lance.setSourceC(posicao.getColumn());
			lance.setSourceL(posicao.getRow());
			tratarLance(lance);
			enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("Não é jogador da vez.");
		}
	}

	public void finalizarJogada() {
		boolean daVez = this.verificaSeJogadorDaVez();
		if (daVez) {
			LanceFinalizarJogada lance = new LanceFinalizarJogada();
			this.tratarLance(lance);
			this.enviarLance(lance);
		} else {
			this.atorJogador.reportarErro("Não é o jogador da vez.");
		}
	}

	public boolean informarConectado() {
		return this.conectado;
	}

	public void estabelecerConectado(boolean valor) {
		this.conectado = valor;
	}

	public boolean informarEmAndamento() {
		return this.emAndamento;
	}

	public void setEmAndamento(boolean valor) {
		this.emAndamento = valor;
	}

	public boolean verificarCondicoesDeVitoria() {
		boolean vitoria = false;
		for (int i = 0; i < this.creatureQueue.size(); i++) {
			if (this.creatureQueue.get(i).getID() > 19
					&& this.creatureQueue.get(i).getID() < 24) {
				Position pos = this.creatureQueue.get(i).getCurrentPosition();
				int linha = pos.getRow();
				int coluna = pos.getColumn();
				if (linha == 24 && coluna == 24 || linha == 24 && coluna == 25
						|| linha == 25 && coluna == 24 || linha == 25
						&& coluna == 25) {
					vitoria = true;
				}
			}
		}
		return vitoria;
	}

	public void finalizarJogo() {
		System.exit(0);
	}

	public void iniciarNovaPartida(int posicao) {
		this.setEmAndamento(true);
		this.map = new Map(this);
		String idJogador = this.atorJogador.informarNomeJogador();
		Player player = this.criarJogador(idJogador);
		this.setLocalPlayer(player);
		LanceEnviarPlayer lance = new LanceEnviarPlayer();
		lance.setPlayer(player);
		this.tratarLance(lance);
		this.enviarLance(lance);
		this.atorJogador.atualizarInterfaceGrafica();
	}

	public void iniciarPartida(int numJog) {
		this.atorClienteServidor.iniciarPartida(numJog);
	}

	public void encerramentoDaPartida() {
		boolean aventureirosVivos = this.verificarSeJogadoresVivos();
		if (aventureirosVivos) {
			boolean condicoesCumpridas = this.verificarCondicoesDeVitoria();
			if (condicoesCumpridas) {
				this.atorJogador.anunciarVitoriaDosJogadores();
				this.finalizarJogo();
			}
		} else {
			this.atorJogador.anunciarVitoriaDoZargon();
			this.finalizarJogo();
		}
	}

	public boolean verificarSeJogadoresVivos() {
		int qtdCriaturas = this.creatureQueue.size();
		for (int i = 0; i < qtdCriaturas; i++) {
			Creature criatura = this.creatureQueue.get(i);
			if (criatura instanceof Barbarian || criatura instanceof Wizard
					|| criatura instanceof Elf || criatura instanceof Dwarf) {
				if (criatura.getStatus() != Status.DEAD)
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
		if (localAdventurer.getPlayableCharacter() != null) {
			PlayableCharacter character = localAdventurer
					.getPlayableCharacter();
			int gold = character.getGold();
			this.atorJogador.mostrarInventario(gold);
		} else {
			this.atorJogador.reportarErro("Zargon não carrega gold");
		}
	}

	public void mostrarInformacoes(int creatureID) {
		Creature criatura = this.getCreatureFromQueue(creatureID);
		if (criatura.isVisible() == true){
			byte body = criatura.getBody();
			byte mind = criatura.getMind();
			byte movement = criatura.getMovement();
			Status status = criatura.getStatus();
			Position posicao = criatura.getCurrentPosition();
			byte linha = posicao.getRow();
			byte coluna = posicao.getColumn();
		
			this.atorJogador.mostrarInformacoes(body, mind, movement, status,
					linha, coluna);
		} else {
			JOptionPane.showMessageDialog(null, "???????");
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
}
