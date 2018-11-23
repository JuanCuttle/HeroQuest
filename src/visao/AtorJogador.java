package visao;

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

import modelo.Creature;
import modelo.Directions;
import modelo.Door;
import modelo.FallingRock;
import modelo.Furniture;
import modelo.HeroQuest;
import modelo.Position;
import modelo.Spell;
import modelo.Status;
import modelo.Strings;
import quests.BasicMap;

public class AtorJogador extends JFrame implements InterfaceGUI {

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

	public ListenerDoTeclado listener = new ListenerDoTeclado(this);
	public MusicThread musicThread;
	
	public static Languages language = Languages.English;

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

	public AtorJogador(HeroQuest game) {
		try {
			createMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AtorJogador.class.getResource("/imagens/Wizard.png")));
		setTitle(Strings.HEROQUEST.toString());
		
		// GUI attributes
		game.setAtorJogador(this);
		this.heroQuest = game;//new HeroQuest(this);
		BasicMap map = this.heroQuest.getMap();
		this.boardButtons = new JButton[map.getNumberOfRows()][map.getNumberOfColumns()];
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
				Instrucoes instr = null;
				try {
					instr = new Instrucoes();
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
					selecionarPersonagem();
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
				music();
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
		for (int j = 0; j < map.getNumberOfColumns(); j++) {
			JButton botao = new JButton();
			botao.setText(""+j);
			botao.setBounds(173 + (j * 23), 112 + (-1 * 23), 23, 23);
			botao.setBorder(invisivel);
			botao.setVisible(true);
			contentPane.add(botao);
		}
		for (int i = 0; i < map.getNumberOfRows(); i++) {
			JButton botao = new JButton();
			botao.setText(""+i);
			botao.setBounds(173 + (-1 * 23), 112 + (i * 23), 23, 23);
			botao.setBorder(invisivel);
			botao.setVisible(true);
			contentPane.add(botao);
		}
		
		// Create game board buttons
		for (int i = 0; i < map.getNumberOfRows(); i++) {
			for (int j = 0; j < map.getNumberOfColumns(); j++) {
				JButton botao = new JButton();
				botao.setName("" + i + j);
				botao.setBounds(173 + (j * 23), 112 + (i * 23), 23, 23);
				botao.setBorder(invisivel);
				botao.setVisible(true);
				botao.addKeyListener(listener);
				botao.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						abrirPorta(Integer.parseInt(botao.getName()));
					}
				});
				contentPane.add(botao);
				this.boardButtons[i][j] = botao;
			}
		}

		// Create creature queue buttons
		JButton useless = new JButton("");
		useless.setVisible(false);
		this.creatureButtons.add(useless);
		for (int i = 1; i <= map.getCreatureQueueSize(); i++) {
			JButton botao = new JButton();
			botao.setName("" + i);
			botao.setBounds(0, 27 * (i - 1) + 89, 150, 27);
			botao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarInformacoes(Integer.parseInt(botao.getName()));
				}
			});
			botao.addKeyListener(listener);
			this.creatureButtons.add(i, botao);
			this.contentPane.add(botao);
		}

		// Create user action buttons
		//generateActionButtons();
		
		this.textArea = new TextArea();
		textArea.setFont(new Font("Viner Hand ITC", Font.BOLD, 15));
		textArea.setBounds(0, 0, 1294, 89);
		contentPane.add(textArea);
		
		if (autoConnectToServer){
			this.conectar();
		}
	}
	
	@SuppressWarnings("unused")
	private void generateActionButtons(){
		Border invisivel = BorderFactory.createEmptyBorder();
		
		ImageIcon iconeConectar = new ImageIcon(getClass().getResource(
				"/imagens/BotaoConectar.png"));
		this.connectButton = new JButton(iconeConectar);
		connectButton.setEnabled(false);
		this.connectButton.setBounds(22 * 1 + 120 * 0, 0, 120, 89);
		this.connectButton.setBorder(invisivel);
		this.connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectar();
			}
		});
		this.connectButton.addKeyListener(listener);
		this.contentPane.add(this.connectButton);

		ImageIcon iconeDesconectar = new ImageIcon(getClass().getResource(
				"/imagens/BotaoDesconectar.png"));
		this.disconnectButton = new JButton(iconeDesconectar);
		disconnectButton.setEnabled(false);
		this.disconnectButton.setBounds(22 * 2 + 120 * 1, 0, 120, 89);
		this.disconnectButton.setBorder(invisivel);
		this.disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desconectar();
			}
		});
		this.disconnectButton.addKeyListener(listener);
		this.contentPane.add(this.disconnectButton);

		ImageIcon iconeIniciarPartida = new ImageIcon(getClass().getResource(
				"/imagens/BotaoIniciarPartida.png"));
		this.startGameButton = new JButton(iconeIniciarPartida);
		startGameButton.setEnabled(false);
		this.startGameButton.setBounds(22 * 3 + 120 * 2, 0, 120, 89);
		this.startGameButton.setBorder(invisivel);
		this.startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarPartida();
			}
		});
		this.startGameButton.addKeyListener(listener);
		this.contentPane.add(this.startGameButton);

		ImageIcon iconeFinalizarJogada = new ImageIcon(getClass().getResource(
				"/imagens/BotaoFinalizarJogada.png"));
		this.endTurnButton = new JButton(iconeFinalizarJogada);
		endTurnButton.setEnabled(false);
		this.endTurnButton.setBounds(22 * 4 + 120 * 3, 0, 120, 89);
		this.endTurnButton.setBorder(invisivel);
		this.endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalizarJogada();
			}
		});
		this.endTurnButton.addKeyListener(listener);
		this.contentPane.add(this.endTurnButton);

		ImageIcon iconeBotaoMostrarInventario = new ImageIcon(getClass()
				.getResource("/imagens/BotaoMostrarInventario.png"));
		this.showInventoryButton = new JButton(iconeBotaoMostrarInventario);
		showInventoryButton.setEnabled(false);
		this.showInventoryButton.setBounds(22 * 5 + 120 * 4, 0, 120, 89);
		this.showInventoryButton.setBorder(invisivel);
		this.showInventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarInventario();
			}
		});
		this.showInventoryButton.addKeyListener(listener);
		this.contentPane.add(this.showInventoryButton);

		ImageIcon iconeBotaoAtacar = new ImageIcon(getClass().getResource(
				"/imagens/BotaoAtacar.png"));
		this.attackButton = new JButton(iconeBotaoAtacar);
		attackButton.setEnabled(false);
		this.attackButton.setBounds(22 * 6 + 120 * 5, 0, 120, 89);
		this.attackButton.setBorder(invisivel);
		this.attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atacar();
			}
		});
		this.attackButton.addKeyListener(listener);
		this.contentPane.add(this.attackButton);

		ImageIcon iconeBotaoUsarMagia = new ImageIcon(getClass().getResource(
				"/imagens/BotaoUsarMagia.png"));
		this.useSpellButton = new JButton(iconeBotaoUsarMagia);
		useSpellButton.setEnabled(false);
		this.useSpellButton.setBounds(22 * 7 + 120 * 6, 0, 120, 89);
		this.useSpellButton.setBorder(invisivel);
		this.useSpellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usarMagia();
			}
		});
		this.useSpellButton.addKeyListener(listener);
		this.contentPane.add(this.useSpellButton);

		ImageIcon iconeBotaoProcurarArmadilha = new ImageIcon(getClass()
				.getResource("/imagens/BotaoProcurarArmadilha.png"));
		this.searchForTrapsButton = new JButton(iconeBotaoProcurarArmadilha);
		searchForTrapsButton.setEnabled(false);
		this.searchForTrapsButton.setBounds(22 * 8 + 120 * 7, 0, 120, 89);
		this.searchForTrapsButton.setBorder(invisivel);
		this.searchForTrapsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurarArmadilhaOuPortaSecreta();
			}
		});
		this.searchForTrapsButton.addKeyListener(listener);
		this.contentPane.add(searchForTrapsButton);

		ImageIcon iconeBotaoProcurarTesouro = new ImageIcon(getClass()
				.getResource("/imagens/BotaoProcurarTesouro.png"));
		this.searchForTreasureButton = new JButton(iconeBotaoProcurarTesouro);
		searchForTreasureButton.setEnabled(false);
		this.searchForTreasureButton.setBounds(22 * 9 + 120 * 8, 0, 120, 89);
		this.searchForTreasureButton.setBorder(invisivel);
		this.searchForTreasureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurarTesouro();
			}
		});
		this.searchForTreasureButton.addKeyListener(listener);
		this.contentPane.add(this.searchForTreasureButton);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void selecionarPersonagem() throws ClassNotFoundException {
		this.heroQuest.selecionarPersonagem();
	}

	public void abrirPortaTeclado() {
		this.heroQuest.abrirPortaTeclado();
	}

	public void abrirPorta(int idPorta) {
		this.heroQuest.abrirPorta(idPorta);
	}

	public void mostrarMensagem(String msg) {
		//this.textArea.setText(msg);
		JOptionPane.showMessageDialog(null, msg);
	}

	public void reportarErro(String msg) {
		this.textArea.setText(msg);
		//JOptionPane.showMessageDialog(null, msg);
	}

	public void movimentar(Directions direcao) {
		this.heroQuest.movimentar(direcao);
	}

	public void atacar() {
		this.heroQuest.atacar();
	}

	public void usarMagia() {
		this.heroQuest.usarMagia();
	}

	public Spell selecionarMagia(ArrayList<Spell> magiasDisponiveis) {
		String inputDialog = Strings.SELECTSPELL.toString();
		for (int i = 0; i < magiasDisponiveis.size(); i++) {
			inputDialog += "\n" + i + " - "
					+ magiasDisponiveis.get(i).getNome();
		}
		String opcao = JOptionPane.showInputDialog(inputDialog);
		int index = Integer.parseInt(opcao);
		return magiasDisponiveis.get(index);
	}

	public Creature selecionarAlvo(ArrayList<Creature> possiveisAlvos) {
		String inputDialog = Strings.SELECTTARGET.toString();
		for (int i = 0; i < possiveisAlvos.size(); i++) {
			inputDialog += "\n" + i + " - "
					+ possiveisAlvos.get(i).getClass().getSimpleName();
		}
		String opcao = JOptionPane.showInputDialog(inputDialog);
		int index = Integer.parseInt(opcao);
		return possiveisAlvos.get(index);
	}

	public void atualizarInterfaceGrafica() {
		for (byte i = 0; i < this.heroQuest.getMap().getNumberOfRows(); i++) {
			for (byte j = 0; j < this.heroQuest.getMap().getNumberOfColumns(); j++) {
				Position posicao = this.heroQuest.getPosition(i, j);
/*				if (posicao.getCreature() != null){
					posicao.getCreature().setVisible(true);
				}*/
				this.atualizarBotao(this.boardButtons[i][j], posicao);
			}
		}
		this.exibirCriaturas();
	}

	public void procurarTesouro() {
		this.heroQuest.procurarTesouro();
	}

	// Chamar TelaSelecionarPersonagem!
	public void mostrarOsCincoPersonagens() {
/*		String inputDialog = "Digite o número correspondente ao personagem escolhido: \nObs.: Zargon controla os monstros, não um aventureiro";
		inputDialog += "\n0 - Zargon";
		inputDialog += "\n1 - Barbarian";
		inputDialog += "\n2 - Wizard";
		inputDialog += "\n3 - Elf";
		inputDialog += "\n4 - Dwarf";*/
		
		CharacterSelection cs = new CharacterSelection(this);
		cs.setVisible(true);
		
		/*String opcao = JOptionPane.showInputDialog(null, inputDialog);
		
		int index = Integer.parseInt(opcao);
		return index;*/
	}

	public void procurarArmadilhaOuPortaSecreta() {
		this.heroQuest.procurarArmadilhaOuPortaSecreta();
	}

	public void finalizarJogada() {
		this.heroQuest.finalizarJogada();
	}

	// Cï¿½digo nï¿½o usado
	public String obterIdJogador() {
		String idJogador = JOptionPane
				.showInputDialog(Strings.INPUTNAME.toString());
		return idJogador;
	}

	public String obterIdServidor() {
		String idServidor = (Strings.OBTAINSERVERID.toString());
		if (!autoConnectToServer) {
			idServidor = JOptionPane.showInputDialog(this,
					(Strings.INPUTSERVERADDRESS.toString()), idServidor);
		}
		return idServidor;
	}

	/*
	 * resultado 0 - conexao com exito 1 - tentativa de conexao com conexao
	 * previamente estabelecida 2 - tentativa de conexao falhou
	 */
	public void conectar() {
		boolean conectado = this.heroQuest.informarConectado();
		if (!conectado) {
			String servidor = this.obterDadosConexao();
			String idUsuario = obterIdJogador();
			boolean exito = this.heroQuest.getAtorClienteServidor().conectar(
					servidor, idUsuario);
			if (exito) {
				this.heroQuest.estabelecerConectado(true);

				this.heroQuest.setNomeLocalPlayerAndServer(idUsuario, servidor);
				
				//this.selectQuest(heroQuest);

				notificarResultado(0);
			} else {
				notificarResultado(2);
			}
		} else {
			notificarResultado(1);
		}

	}

	/**
	 * 
	 * @param resultado
	 */
	public void notificarResultado(int resultado) {
		String mensagem = "";
		switch (resultado) {
		case 0:
			mensagem = Strings.SUCCESSFULCONNECT.toString();
			break;
		case 1:
			mensagem = Strings.ALREADYCONNECTED.toString();
			break;
		case 2:
			mensagem = Strings.FAILEDCONNECT.toString();
			break;
		case 3:
			mensagem = Strings.SUCCESSFULDISCONNECT.toString();
			break;
		case 4:
			mensagem = Strings.DISCBEFORECONNECT.toString();
			break;
		case 5:
			mensagem = Strings.FAILEDDISCONNECT.toString();
			break;
		case 6:
			mensagem = Strings.SUCCESSFULSTART.toString();
			break;
		case 7:
			mensagem = Strings.STARTBEFORECONNECT.toString();
			break;
		case 13:
			mensagem = Strings.UNINTERRUPTEDGAME.toString();
			break;
		default:
			mensagem = "";
			break;
		}
		this.textArea.setText(mensagem);
		//JOptionPane.showMessageDialog(null, mensagem);
	}

	public String obterDadosConexao() {
		String servidor = this.obterIdServidor();
		return servidor;
	}

	/*
	 * resultado 3 - desconexao com exito 4 - tentativa de desconexao sem
	 * conexao previamente estabelecida 5 - tentativa de desconexao falhou
	 */
	public void desconectar() {
		boolean conectado = this.heroQuest.informarConectado();
		if (conectado) {
			boolean exito = this.heroQuest.getAtorClienteServidor().desconectar();
			if (exito) {
				this.heroQuest.estabelecerConectado(false);
				notificarResultado(3);
			} else {
				notificarResultado(5);
			}
		} else {
			notificarResultado(4);
		}
	}

	/*
	 * resultado 6 - solicitaï¿½ï¿½o de inicio procedida com exito 7 - tentativa
	 * de inicio sem conexao previamente estabelecida 13 - partida corrente nao
	 * interrompida
	 */
	public void iniciarPartida() {
		boolean conectado = false;
		boolean interromper = false;
		boolean emAndamento = this.heroQuest.informarEmAndamento();
		if (emAndamento) {
			interromper = this.avaliarInterrupcao();
		} else {
			conectado = this.heroQuest.informarConectado();
		}
		if (interromper || ((!emAndamento) && conectado)) {
			int numJog = this.informarQuantidadeDePlayers();
			this.heroQuest.getAtorClienteServidor().iniciarPartida(numJog);
			notificarResultado(6);
		}
		if (!conectado) {
			notificarResultado(7);
		}
		notificarResultado(13);
	}

	public boolean avaliarInterrupcao() {
		return true;
	}

	public void anunciarVitoriaDosJogadores() {
		this.textArea.setText(Strings.HEROWIN.toString());
		//JOptionPane.showMessageDialog(null,
			//	Strings.HEROWIN.toString());
	}

	public void anunciarVitoriaDoZargon() {
		this.textArea.setText(Strings.ZARGONWIN.toString());
		/*JOptionPane.showMessageDialog(null,
				Strings.ZARGONWIN.toString());*/
	}

	public String informarNomeJogador() {
		String nomeJogador = JOptionPane
				.showInputDialog(Strings.INPUTNAME.toString());
		return nomeJogador;
	}

	public void mostrarInventario() {
		this.heroQuest.mostrarInventario();
	}

	public void mostrarInventario(int gold) {
		this.textArea.setText(Strings.YOUHAVE.toString() + gold
				+ Strings.INVYCOINS.toString());
		/*JOptionPane.showMessageDialog(null, Strings.YOUHAVE.toString() + gold
				+ Strings.INVYCOINS.toString());*/
	}

	public void mostrarInformacoes(int characterID) {
		this.heroQuest.mostrarInformacoes(characterID);
	}

	public void mostrarInformacoes(byte body, byte mind, byte movement,
			Status status, int linha, int coluna, Byte roundsToSleep) {
		String output = Strings.CURRENTBP.toString() + body
				+ Strings.CURRENTMP.toString() + mind + Strings.REMAININGMOVES.toString()
				+ movement + Strings.CURRENTSTATUS.toString() + status + Strings.LINE.toString()+ linha
				+ Strings.COLUMN.toString() + coluna;
		if (roundsToSleep != null){
			if (roundsToSleep != 0){
				output += Strings.TTW.toString() + roundsToSleep;
			}
		}
		this.textArea.setText(output);
		//JOptionPane.showMessageDialog(null, output);
	}

	public int informarQuantidadeDePlayers() {
		int numJogadores;
		do {
			String numjog = JOptionPane
					.showInputDialog(Strings.NUMBEROFPLAYERS.toString());
			numJogadores = Integer.parseInt(numjog);
		} while (numJogadores < 2);
		return numJogadores;
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
				//Map map = this.heroQuest.getMap();
				BasicMap map = this.heroQuest.getMap();
				int stairRow = map.getStairsPosition()[0];
				int stairColumn = map.getStairsPosition()[1];
				if (stairRow != 0){
					if (linha == stairRow && coluna == stairColumn) {
						path = "/imagens/2424.png";
					} else if (linha == stairRow && coluna == stairColumn+1) {
						path = "/imagens/2425.png";
					} else if (linha == stairRow+1 && coluna == stairColumn) {
						path = "/imagens/2524.png";
					} else if (linha == stairRow+1 && coluna == stairColumn+1) {
						path = "/imagens/2525.png";
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
			if (linha == botRow && coluna == botCol) { // Change if and when there is this piece horizontally
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
	
	private String fireplacePath(BasicMap map, int linha, int coluna, byte[] fireplacepos, String path) {

		int fireplaceRow = fireplacepos[1];
		int fireplaceCol = fireplacepos[2];
		
		if (fireplacepos[0] == 0){ // facing down
			if (linha == fireplaceRow && coluna == fireplaceCol) {
				path = "/imagens/fireplace/FireplaceD0.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+1){
				path = "/imagens/fireplace/FireplaceD1.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+2){
				path = "/imagens/fireplace/FireplaceD2.png";
			}
		} else {
			if (linha == fireplaceRow && coluna == fireplaceCol) {
				path = "/imagens/fireplace/FireplaceU0.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+1){
				path = "/imagens/fireplace/FireplaceU1.png";
			} else if(linha == fireplaceRow && coluna == fireplaceCol+2){
				path = "/imagens/fireplace/FireplaceU2.png";
			}
		}
		return path;
	}
	
	private String bcPath(BasicMap map, int linha, int coluna, byte[] bcpos, String path) {

		int bcRow = bcpos[1];
		int bcCol = bcpos[2];
		
		if (bcpos[0] == 0){ // horizontal
			if (linha == bcRow && coluna == bcCol) {
				path = "/imagens/bookcase/BookcaseH0.png";
			} else if(linha == bcRow && coluna == bcCol+1){
				path = "/imagens/bookcase/BookcaseH1.png";
			} else if(linha == bcRow && coluna == bcCol+2){
				path = "/imagens/bookcase/BookcaseH2.png";
			}
		} else { // vertical
			if (linha == bcRow && coluna == bcCol) {
				path = "/imagens/bookcase/BookcaseV0.png";
			} else if(linha == bcRow+1 && coluna == bcCol){
				path = "/imagens/bookcase/BookcaseV1.png";
			} else if(linha == bcRow+2 && coluna == bcCol){
				path = "/imagens/bookcase/BookcaseV2.png";
			}
		}
		return path;
	}

	private void atualizarBotao(JButton botao, Creature criatura,
			int posicaoBotao) {
		if (criatura.isVisible() == true) {
			String nome = criatura.getClass().getSimpleName();
			botao.setText(nome);
		}
		botao.setBounds(0, 27 * posicaoBotao + 89, 150, 27);
		botao.invalidate();
		botao.revalidate();
		botao.repaint();
	}

	public void exibirCriaturas() {
		// Each button was assigned to a creature via creature.ID at button initialize
		// For each creature in the queue, we find its button, and move it to its new position
		// in the GUI button list
		ArrayList<Creature> criaturas = this.heroQuest.getCreatureQueue();
		for (int i = 0; i < criaturas.size(); i++) {
			Creature criatura = criaturas.get(i);

			this.atualizarBotao(this.creatureButtons.get(criatura.getID()), criatura, i);
		}
	}

	public void mostrarAcaoTrap(byte dano, Creature criatura) {
		this.textArea.setText(Strings.OHNO.toString()
						+ criatura.getClass().getSimpleName()
						+ Strings.ACTIVATEDTRAP.toString() + dano
						+ Strings.OFBP.toString());
/*		JOptionPane
				.showMessageDialog(null, Strings.OHNO.toString()
						+ criatura.getClass().getSimpleName()
						+ Strings.ACTIVATEDTRAP.toString() + dano
						+ Strings.OFBP.toString());*/
	}

	public void mostrarDano(Creature alvo, byte dano, boolean seAtacou) {
		if (!seAtacou) {
			this.textArea.setText(Strings.THECREATURE.toString()
					+ alvo.getClass().getSimpleName() + Strings.RECEIVED.toString() + dano
					+ Strings.OFDAMAGE.toString());
/*			JOptionPane.showMessageDialog(null, Strings.THECREATURE.toString()
					+ alvo.getClass().getSimpleName() + Strings.RECEIVED.toString() + dano
					+ Strings.OFDAMAGE.toString());*/
		} else {
			this.textArea.setText(Strings.THECREATURE.toString()
					+ alvo.getClass().getSimpleName()
					+ Strings.ATTEMPTSSEPPUKU.toString() + dano + Strings.OFDAMAGE.toString());
/*			JOptionPane.showMessageDialog(null, Strings.THECREATURE.toString()
					+ alvo.getClass().getSimpleName()
					+ Strings.ATTEMPTSSEPPUKU.toString() + dano + Strings.OFDAMAGE.toString());*/
		}
	}

	public void anunciarMorteDeCriatura(Creature alvo) {
		this.textArea.setText(Strings.THECREATURE.toString()
				+ alvo.getClass().getSimpleName()
				+ Strings.DIEDHONORABLY.toString());
/*		JOptionPane.showMessageDialog(null, Strings.THECREATURE.toString()
				+ alvo.getClass().getSimpleName()
				+ Strings.DIEDHONORABLY.toString());*/
	}

	public void anunciarUsoDeMagia(Creature caster, Spell magia, Creature alvo,
			byte dano, Status status) {
		if (status != null) {
			this.textArea.setText(Strings.THE.toString()
					+ caster.getClass().getSimpleName()
					+ Strings.MURMUREDSPELL.toString() + magia.getNome()
					+ Strings.ANDTHECREATURE.toString() + alvo.getClass().getSimpleName()
					+ Strings.MODIFIEDIN.toString() + dano
					+ Strings.BPMODSTATUS.toString() + status
					+ Strings.EXCLMARK.toString());
/*			JOptionPane.showMessageDialog(null, Strings.THE.toString()
					+ caster.getClass().getSimpleName()
					+ Strings.MURMUREDSPELL.toString() + magia.getNome()
					+ Strings.ANDTHECREATURE.toString() + alvo.getClass().getSimpleName()
					+ Strings.MODIFIEDIN.toString() + dano
					+ Strings.BPMODSTATUS.toString() + status
					+ Strings.EXCLMARK.toString());*/
		} else {
			this.textArea.setText(Strings.THE.toString()
					+ caster.getClass().getSimpleName()
					+ Strings.MURMUREDSPELL.toString() + magia.getNome()
					+ Strings.ANDTHECREATURE.toString() + alvo.getClass().getSimpleName()
					+ Strings.MODIFIEDIN.toString() + dano + Strings.BPMODSNOTATUS.toString());
/*			JOptionPane.showMessageDialog(null, Strings.THE.toString()
					+ caster.getClass().getSimpleName()
					+ Strings.MURMUREDSPELL.toString() + magia.getNome()
					+ Strings.ANDTHECREATURE.toString() + alvo.getClass().getSimpleName()
					+ Strings.MODIFIEDIN.toString() + dano + Strings.BPMODSNOTATUS.toString());*/
		}

	}

	public void anunciarMorteDesafortunada(Creature criatura) {
		this.textArea.setText(Strings.OHNO.toString()+" "+Strings.THECREATURE.toString()
				+ criatura.getClass().getSimpleName()
				+ Strings.DIEDONTRAP.toString());
/*		JOptionPane.showMessageDialog(null, Strings.OHNO.toString()+" "+Strings.THECREATURE.toString()
				+ criatura.getClass().getSimpleName()
				+ Strings.DIEDONTRAP.toString());*/
	}

	public void anunciarDaVez(Creature criatura) {
		Position posicaoCriatura = criatura.getCurrentPosition();
		int linha = posicaoCriatura.getRow();
		int coluna = posicaoCriatura.getColumn();
		this.textArea.setText(Strings.CREATURESTURN.toString()
				+ criatura.getClass().getSimpleName()
				+ Strings.ONLINE.toString() + linha + Strings.COMMACOLUMN.toString() + coluna
				+ Strings.OFGAMEBOARD.toString());
/*		JOptionPane.showMessageDialog(null, Strings.CREATURESTURN.toString()
				+ criatura.getClass().getSimpleName()
				+ Strings.ONLINE.toString() + linha + Strings.COMMACOLUMN.toString() + coluna
				+ Strings.OFGAMEBOARD.toString());*/
	}

	public void atualizarArredoresJogador() {
		Creature atual = this.heroQuest.getCriaturaDaVez();
		Position p = atual.getCurrentPosition();
		byte linha = p.getRow();
		byte coluna = p.getColumn();

		for (byte i = (byte) (linha - 2); i <= linha + 2; i++) {
			for (byte j = (byte) (coluna - 2); j <= coluna + 2; j++) {
				if (i >= 0 && i < this.heroQuest.getMap().getNumberOfRows() && j >= 0 && j < this.heroQuest.getMap().getNumberOfColumns()) {
					Position posicao = this.heroQuest.getPosition(i, j);
					this.atualizarBotao(this.boardButtons[i][j], posicao);
				}
			}
		}
		
		this.exibirCriaturas(); //precisa?
	}

	public int escolherPorta(ArrayList<String> portaIds) {
		String inputDialog = Strings.SELECTDOOR.toString();
		for (int i = 0; i < portaIds.size(); i++) {
			inputDialog += i + " - " + portaIds.get(i) + "\n";
		}
		String opcao = JOptionPane.showInputDialog(inputDialog);
		return Integer.parseInt(opcao);

	}

	public void createMusic() throws Exception {

		/*File f = new File("src/musicas/Castlevania Symphony of the Night Track 03 Dance Of Illusions.wav");*/
		String f = "/musicas/Castlevania Symphony of the Night Track 03 Dance Of Illusions.wav";
		AudioInputStream audioIn = null;
		
		try {
			//audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
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

	public void music() {
		this.musicThread.music();
	}

	public void mostrarRemocaoTrap() {
		this.textArea.setText(Strings.DWARFDISARMEDTRAPS.toString());
		//JOptionPane.showMessageDialog(null, Strings.DWARFDISARMEDTRAPS.toString());
	}

	public byte mostrarOpcoesFallingRock() {
		String input = Strings.ROCKFALL.toString();
		input += "0 - " + Strings.FORWARD.toString();
		input += "1 - " + Strings.BACKWARD.toString();
		String option = JOptionPane.showInputDialog(input);
		return (byte)Integer.parseInt(option);
	}
	
	public byte mostrarOpcoesPit() {
		String input = Strings.PITJUMP.toString();
		input += "0 - " + Strings.YES.toString();
		input += "1 - " + Strings.NO.toString();
		String option = JOptionPane.showInputDialog(input);
		return (byte)Integer.parseInt(option);
	}
	
	public void setLanguage(Languages lang) {
		language = lang;
	}
	
	public AtorJogador getThis(){
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
	
	public void writeSaveFile(String playerName, int heroType, int gold) throws IOException{
		// Retrieve current working directory
		String currentDir = System.getProperty("user.dir");
		System.out.println(currentDir);
				
		// Create new directory for the save files, if not yet created
		String saveDir = currentDir + "/HeroQuest_Saves/";
		boolean dir = new File(saveDir).mkdir();
		System.out.println(dir);
				
		FileOutputStream fos = new FileOutputStream(saveDir + playerName + ".txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new SaveFile(heroType, gold));
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
			         
			         returnValues.add(sf.getCharClass()+"");
			         returnValues.add(sf.getGold()+"");
			            
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
