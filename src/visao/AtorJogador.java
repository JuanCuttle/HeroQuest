package visao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import modelo.HeroQuest;
import modelo.Position;
import modelo.Spell;
import modelo.Status;
import modelo.Strings;

public class AtorJogador extends JFrame implements InterfaceGUI {

	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected HeroQuest heroQuest;
	protected JButton[][] botoesTabuleiro;
	protected JButton botaoConectar;
	protected JButton botaoDesconectar;
	protected JButton botaoIniciarPartida;
	protected JButton botaoFinalizarJogada;
	protected JButton botaoMostrarInventario;
	protected JButton botaoAtacar;
	protected JButton botaoUsarMagia;
	protected JButton botaoProcurarArmadilha;
	protected JButton botaoProcurarTesouro;
	protected ArrayList<JButton> botoesCriaturas;
	protected JMenuBar barraDeMenu;

	public ListenerDoTeclado listener = new ListenerDoTeclado(this);
	public MusicThread musicThread;
	
	public static Languages language = Languages.English;

	public static Boolean autoConnectToServer = true;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AtorJogador frame = new AtorJogador();
					frame.setVisible(true);
					if (autoConnectToServer) {
						frame.conectar();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AtorJogador() {
		try {
			createMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AtorJogador.class.getResource("/imagens/Wizard.png")));
		setTitle(Strings.HEROQUEST.toString());
		// Atributos do AtorJogador

		this.botoesTabuleiro = new JButton[27][50];
		this.botoesCriaturas = new ArrayList<JButton>();
		this.heroQuest = new HeroQuest(this);
		addKeyListener(listener);

		// Configurar a janela
		setSize(1300, 770);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		barraDeMenu = new JMenuBar();
		setJMenuBar(barraDeMenu);

		JMenu mnHelp = new JMenu(Strings.MENU.toString());
		barraDeMenu.add(mnHelp);

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
				selecionarPersonagem();
			}
		});

		mnHelp.add(btnCharSelect);

		JMenu mnSettings = new JMenu(Strings.SETTINGS.toString());
		barraDeMenu.add(mnSettings);

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

		// Cria os bot√µes do tabuleiro
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 50; j++) {
				JButton botao = new JButton();
				botao.setName("" + i + j);
				botao.setBounds(150 + (j * 23), 89 + (i * 23), 23, 23);
				botao.setBorder(invisivel);
				botao.setVisible(true);
				botao.addKeyListener(listener);
				botao.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						abrirPorta(Integer.parseInt(botao.getName()));
					}
				});
				contentPane.add(botao);
				this.botoesTabuleiro[i][j] = botao;
			}
		}

		// Cria os bot√µes "Fila de criaturas"
		JButton inutil = new JButton("");
		inutil.setVisible(false);
		this.botoesCriaturas.add(inutil);
		for (int i = 1; i <= 23; i++) {
			JButton botao = new JButton();
			botao.setName("" + i);
			botao.setBounds(0, 27 * (i - 1) + 89, 150, 27);
			botao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarInformacoes(Integer.parseInt(botao.getName()));
				}
			});
			botao.addKeyListener(listener);
			this.botoesCriaturas.add(i, botao);
			this.contentPane.add(botao);
		}

		// Cria os outros bot√µes
		ImageIcon iconeConectar = new ImageIcon(getClass().getResource(
				"/imagens/BotaoConectar.png"));
		this.botaoConectar = new JButton(iconeConectar);
		this.botaoConectar.setBounds(22 * 1 + 120 * 0, 0, 120, 89);
		this.botaoConectar.setBorder(invisivel);
		this.botaoConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectar();
			}
		});
		this.botaoConectar.addKeyListener(listener);
		this.contentPane.add(this.botaoConectar);

		ImageIcon iconeDesconectar = new ImageIcon(getClass().getResource(
				"/imagens/BotaoDesconectar.png"));
		this.botaoDesconectar = new JButton(iconeDesconectar);
		this.botaoDesconectar.setBounds(22 * 2 + 120 * 1, 0, 120, 89);
		this.botaoDesconectar.setBorder(invisivel);
		this.botaoDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desconectar();
			}
		});
		this.botaoDesconectar.addKeyListener(listener);
		this.contentPane.add(this.botaoDesconectar);

		ImageIcon iconeIniciarPartida = new ImageIcon(getClass().getResource(
				"/imagens/BotaoIniciarPartida.png"));
		this.botaoIniciarPartida = new JButton(iconeIniciarPartida);
		this.botaoIniciarPartida.setBounds(22 * 3 + 120 * 2, 0, 120, 89);
		this.botaoIniciarPartida.setBorder(invisivel);
		this.botaoIniciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarPartida();
			}
		});
		this.botaoIniciarPartida.addKeyListener(listener);
		this.contentPane.add(this.botaoIniciarPartida);

		ImageIcon iconeFinalizarJogada = new ImageIcon(getClass().getResource(
				"/imagens/BotaoFinalizarJogada.png"));
		this.botaoFinalizarJogada = new JButton(iconeFinalizarJogada);
		this.botaoFinalizarJogada.setBounds(22 * 4 + 120 * 3, 0, 120, 89);
		this.botaoFinalizarJogada.setBorder(invisivel);
		this.botaoFinalizarJogada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalizarJogada();
			}
		});
		this.botaoFinalizarJogada.addKeyListener(listener);
		this.contentPane.add(this.botaoFinalizarJogada);

		ImageIcon iconeBotaoMostrarInventario = new ImageIcon(getClass()
				.getResource("/imagens/BotaoMostrarInventario.png"));
		this.botaoMostrarInventario = new JButton(iconeBotaoMostrarInventario);
		this.botaoMostrarInventario.setBounds(22 * 5 + 120 * 4, 0, 120, 89);
		this.botaoMostrarInventario.setBorder(invisivel);
		this.botaoMostrarInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarInventario();
			}
		});
		this.botaoMostrarInventario.addKeyListener(listener);
		this.contentPane.add(this.botaoMostrarInventario);

		ImageIcon iconeBotaoAtacar = new ImageIcon(getClass().getResource(
				"/imagens/BotaoAtacar.png"));
		this.botaoAtacar = new JButton(iconeBotaoAtacar);
		this.botaoAtacar.setBounds(22 * 6 + 120 * 5, 0, 120, 89);
		this.botaoAtacar.setBorder(invisivel);
		this.botaoAtacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atacar();
			}
		});
		this.botaoAtacar.addKeyListener(listener);
		this.contentPane.add(this.botaoAtacar);

		ImageIcon iconeBotaoUsarMagia = new ImageIcon(getClass().getResource(
				"/imagens/BotaoUsarMagia.png"));
		this.botaoUsarMagia = new JButton(iconeBotaoUsarMagia);
		this.botaoUsarMagia.setBounds(22 * 7 + 120 * 6, 0, 120, 89);
		this.botaoUsarMagia.setBorder(invisivel);
		this.botaoUsarMagia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usarMagia();
			}
		});
		this.botaoUsarMagia.addKeyListener(listener);
		this.contentPane.add(this.botaoUsarMagia);

		ImageIcon iconeBotaoProcurarArmadilha = new ImageIcon(getClass()
				.getResource("/imagens/BotaoProcurarArmadilha.png"));
		this.botaoProcurarArmadilha = new JButton(iconeBotaoProcurarArmadilha);
		this.botaoProcurarArmadilha.setBounds(22 * 8 + 120 * 7, 0, 120, 89);
		this.botaoProcurarArmadilha.setBorder(invisivel);
		this.botaoProcurarArmadilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurarArmadilhaOuPortaSecreta();
			}
		});
		this.botaoProcurarArmadilha.addKeyListener(listener);
		this.contentPane.add(botaoProcurarArmadilha);

		ImageIcon iconeBotaoProcurarTesouro = new ImageIcon(getClass()
				.getResource("/imagens/BotaoProcurarTesouro.png"));
		this.botaoProcurarTesouro = new JButton(iconeBotaoProcurarTesouro);
		this.botaoProcurarTesouro.setBounds(22 * 9 + 120 * 8, 0, 120, 89);
		this.botaoProcurarTesouro.setBorder(invisivel);
		this.botaoProcurarTesouro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurarTesouro();
			}
		});
		this.botaoProcurarTesouro.addKeyListener(listener);
		this.contentPane.add(this.botaoProcurarTesouro);
		
		if (autoConnectToServer){
			this.conectar();
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void selecionarPersonagem() {
		this.heroQuest.selecionarPersonagem();
	}

	public void abrirPortaTeclado() {
		this.heroQuest.abrirPortaTeclado();
	}

	public void abrirPorta(int idPorta) {
		this.heroQuest.abrirPorta(idPorta);
	}

	public void mostrarMensagem(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void reportarErro(String msg) {
		JOptionPane.showMessageDialog(null, msg);
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
		for (byte i = 0; i < 27; i++) {
			for (byte j = 0; j < 50; j++) {
				Position posicao = this.heroQuest.getPosition(i, j);
				this.atualizarBotao(this.botoesTabuleiro[i][j], posicao);
			}
		}
		this.exibirCriaturas();
	}

	public void procurarTesouro() {
		this.heroQuest.procurarTesouro();
	}

	// Chamar TelaSelecionarPersonagem!
	public void mostrarOsCincoPersonagens() {
/*		String inputDialog = "Digite o n˙mero correspondente ao personagem escolhido: \nObs.: Zargon controla os monstros, n„o um aventureiro";
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

	// CÔøΩdigo nÔøΩo usado
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
		JOptionPane.showMessageDialog(null, mensagem);
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
	 * resultado 6 - solicitaÔøΩÔøΩo de inicio procedida com exito 7 - tentativa
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
		JOptionPane.showMessageDialog(null,
				Strings.HEROWIN.toString());
	}

	public void anunciarVitoriaDoZargon() {
		JOptionPane.showMessageDialog(null,
				Strings.ZARGONWIN.toString());
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
		JOptionPane.showMessageDialog(null, Strings.YOUHAVE.toString() + gold
				+ Strings.INVYCOINS.toString());
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
		JOptionPane.showMessageDialog(null, output);
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

				if (linha == 24 && coluna == 24) {
					path = "/imagens/2424.png";
				} else if (linha == 24 && coluna == 25) {
					path = "/imagens/2425.png";
				} else if (linha == 25 && coluna == 24) {
					path = "/imagens/2524.png";
				} else if (linha == 25 && coluna == 25) {
					path = "/imagens/2525.png";
				}
			}
		}
		img = new ImageIcon(getClass().getResource(path));
		botao.setIcon(img);
		botao.invalidate();
		botao.revalidate();
		botao.repaint();
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
		ArrayList<Creature> criaturas = this.heroQuest.getCreatureQueue();
		for (int i = 0; i < criaturas.size(); i++) {
			Creature criatura = criaturas.get(i);
			this.atualizarBotao(this.botoesCriaturas.get(criatura.getID()),
					criatura, i);
		}
	}

	public void mostrarAcaoTrap(byte dano, Creature criatura) {
		JOptionPane
				.showMessageDialog(null, Strings.OHNO.toString()
						+ criatura.getClass().getSimpleName()
						+ Strings.ACTIVATEDTRAP.toString() + dano
						+ Strings.OFBP.toString());
	}

	public void mostrarDano(Creature alvo, byte dano, boolean seAtacou) {
		if (!seAtacou) {
			JOptionPane.showMessageDialog(null, Strings.THECREATURE.toString()
					+ alvo.getClass().getSimpleName() + Strings.RECEIVED.toString() + dano
					+ Strings.OFDAMAGE.toString());
		} else {
			JOptionPane.showMessageDialog(null, Strings.THECREATURE.toString()
					+ alvo.getClass().getSimpleName()
					+ Strings.ATTEMPTSSEPPUKU.toString() + dano + Strings.OFDAMAGE.toString());
		}
	}

	public void anunciarMorteDeCriatura(Creature alvo) {
		JOptionPane.showMessageDialog(null, Strings.THECREATURE.toString()
				+ alvo.getClass().getSimpleName()
				+ Strings.DIEDHONORABLY.toString());
	}

	public void anunciarUsoDeMagia(Creature caster, Spell magia, Creature alvo,
			byte dano, Status status) {
		if (status != null) {
			JOptionPane.showMessageDialog(null, Strings.THE.toString()
					+ caster.getClass().getSimpleName()
					+ Strings.MURMUREDSPELL.toString() + magia.getNome()
					+ Strings.ANDTHECREATURE.toString() + alvo.getClass().getSimpleName()
					+ Strings.MODIFIEDIN.toString() + dano
					+ Strings.BPMODSTATUS.toString() + status
					+ Strings.EXCLMARK.toString());
		} else {
			JOptionPane.showMessageDialog(null, Strings.THE.toString()
					+ caster.getClass().getSimpleName()
					+ Strings.MURMUREDSPELL.toString() + magia.getNome()
					+ Strings.ANDTHECREATURE.toString() + alvo.getClass().getSimpleName()
					+ Strings.MODIFIEDIN.toString() + dano + Strings.BPMODSNOTATUS.toString());
		}

	}

	public void anunciarMorteDesafortunada(Creature criatura) {
		JOptionPane.showMessageDialog(null, Strings.OHNO.toString()+" "+Strings.THECREATURE.toString()
				+ criatura.getClass().getSimpleName()
				+ Strings.DIEDONTRAP.toString());
	}

	public void anunciarDaVez(Creature criatura) {
		Position posicaoCriatura = criatura.getCurrentPosition();
		int linha = posicaoCriatura.getRow();
		int coluna = posicaoCriatura.getColumn();
		JOptionPane.showMessageDialog(null, Strings.CREATURESTURN.toString()
				+ criatura.getClass().getSimpleName()
				+ Strings.ONLINE.toString() + linha + Strings.COMMACOLUMN.toString() + coluna
				+ Strings.OFGAMEBOARD.toString());
	}

	public void atualizarArredoresJogador() {
		Creature atual = this.heroQuest.getCriaturaDaVez();
		Position p = atual.getCurrentPosition();
		byte linha = p.getRow();
		byte coluna = p.getColumn();

		for (byte i = (byte) (linha - 2); i <= linha + 2; i++) {
			for (byte j = (byte) (coluna - 2); j <= coluna + 2; j++) {
				if (i >= 0 && i < 27 && j >= 0 && j < 50) {
					Position posicao = this.heroQuest.getPosition(i, j);
					this.atualizarBotao(this.botoesTabuleiro[i][j], posicao);
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
		JOptionPane.showMessageDialog(null, Strings.DWARFDISARMEDTRAPS.toString());
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
		JMenuBar mBar = this.barraDeMenu;
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
				
		// Write data into file
		FileWriter fw = new FileWriter(saveDir + playerName + ".txt");
		fw.write(heroType+"\t");
		fw.write(gold+"\t");
		fw.close();
	}
	
	public ArrayList<String> readSaveFile(String playerName) throws IOException{
		
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
					// Open the file at /Saves/
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
					}
					
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
