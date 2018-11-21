package visao;

import java.util.ArrayList;

import javax.swing.JButton;

import modelo.Creature;
import modelo.Directions;
import modelo.Position;
import modelo.Spell;
import modelo.Status;

public interface InterfaceGUI {
	
	public void selecionarPersonagem() throws ClassNotFoundException;
	
	public void abrirPorta(int idPorta);
	
	public void mostrarMensagem(String msg); // requerida por HeroQuest
	
	public void reportarErro(String msg); // requerida por HeroQuest
	
	public void movimentar(Directions direcao);
	
	public void atacar();
	
	public void usarMagia();
	
	public Spell selecionarMagia(ArrayList<Spell> magiasDisponiveis); // requerida por HeroQuest
	
	public Creature selecionarAlvo(ArrayList<Creature> possiveisAlvos); // requerida por HeroQuest
	
	public void atualizarInterfaceGrafica(); // requerida por HeroQuest
	
	public void procurarTesouro();
	
	public void mostrarOsCincoPersonagens(); // requerida por HeroQuest
	
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
	
	public void anunciarVitoriaDosJogadores(); // requerida por HeroQuest
	
	public void anunciarVitoriaDoZargon(); // requerida por HeroQuest
	
	public void mostrarInventario();
	
	public void mostrarInventario(int gold); // requerida por HeroQuest
	
	public void mostrarInformacoes(int characterID);
	
	public void mostrarInformacoes(byte body, byte mind, byte movement,
			Status status, int linha, int coluna, Byte roundsToSleep); // requerida por HeroQuest
	
	public int informarQuantidadeDePlayers();
	
	public void atualizarBotao(JButton botao, Position posicao);
	
	public void exibirCriaturas(); // requerida por HeroQuest
	
	public void mostrarAcaoTrap(byte dano, Creature criatura); // requerida por HeroQuest
	
	public void mostrarDano(Creature alvo, byte dano, boolean seAtacou); // requerida por HeroQuest
	
	public void anunciarMorteDeCriatura(Creature alvo); // requerida por HeroQuest
	
	public void anunciarUsoDeMagia(Creature caster, Spell magia, Creature alvo,
			byte dano, Status status); // requerida por HeroQuest
	
	public void anunciarMorteDesafortunada(Creature criatura); // requerida por HeroQuest
	
	public void atualizarArredoresJogador(); // requerida por HeroQuest
	
	public int escolherPorta(ArrayList<String> portaIds); // requerida por HeroQuest
	
	public void createMusic() throws Exception;
	
	public void music();
	
	public void mostrarRemocaoTrap(); // requerida por HeroQuest
	
	public byte mostrarOpcoesFallingRock(); // requerida por HeroQuest
	
	public byte mostrarOpcoesPit(); // requerida por HeroQuest
}
