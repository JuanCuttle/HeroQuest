package visao;

import java.util.ArrayList;

import javax.swing.JButton;

import modelo.Creature;
import modelo.Directions;
import modelo.Position;
import modelo.Spell;
import modelo.Status;

public interface InterfaceGUI {
	
	public void selecionarPersonagem();
	
	public void abrirPorta(int idPorta);
	
	public void mostrarMensagem(String msg);
	
	public void reportarErro(String msg);
	
	public void movimentar(Directions direcao);
	
	public void atacar();
	
	public void usarMagia();
	
	public Spell selecionarMagia(ArrayList<Spell> magiasDisponiveis);
	
	public Creature selecionarAlvo(ArrayList<Creature> possiveisAlvos);
	
	public void atualizarInterfaceGrafica();
	
	public void procurarTesouro();
	
	public void mostrarOsCincoPersonagens();
	
	public void procurarArmadilhaOuPortaSecreta();
	
	public void finalizarJogada();
	
	public String obterIdJogador();
	
	public String obterIdServidor();
	
	public void conectar();
	
	public void notificarResultado(int resultado);
	
	public String obterDadosConexao();
	
	public void desconectar();
	
	public void iniciarPartida();
	
	public boolean avaliarInterrupcao();
	
	public void anunciarVitoriaDosJogadores();
	
	public void anunciarVitoriaDoZargon();
	
	public void mostrarInventario();
	
	public void mostrarInventario(int gold);
	
	public void mostrarInformacoes(int characterID);
	
	public void mostrarInformacoes(byte body, byte mind, byte movement,
			Status status, int linha, int coluna, Byte roundsToSleep);
	
	public int informarQuantidadeDePlayers();
	
	public void atualizarBotao(JButton botao, Position posicao);
	
	public void exibirCriaturas();
	
	public void mostrarAcaoTrap(byte dano, Creature criatura);
	
	public void mostrarDano(Creature alvo, byte dano, boolean seAtacou);
	
	public void anunciarMorteDeCriatura(Creature alvo);
	
	public void anunciarUsoDeMagia(Creature caster, Spell magia, Creature alvo,
			byte dano, Status status);
	
	public void anunciarMorteDesafortunada(Creature criatura);
	
	public void atualizarArredoresJogador();
	
	public int escolherPorta(ArrayList<String> portaIds);
	
	public void createMusic() throws Exception;
	
	public void music();
	
	public void mostrarRemocaoTrap();
	
	public byte mostrarOpcoesFallingRock();
	
	public byte mostrarOpcoesPit();
}
