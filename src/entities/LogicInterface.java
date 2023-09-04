package entities;

import java.util.ArrayList;

import entities.actions.Action;
import enums.DirectionEnum;
import view.AtorClientServer;

public interface LogicInterface {
	
	void abrirPorta(int id); // requerida por AtorJogador
	
	void abrirPortaTeclado(); // requerida por AtorJogador
	
	void movimentar(DirectionEnum direcao); // requerida por AtorJogador
	
	Creature getCurrentCreature(); // requerida por AtorJogador
	
	void atacar(); // requerida por AtorJogador
	
	void usarMagia(); // requerida por AtorJogador
	
	ArrayList<Creature> getCreatureQueue(); // requerida por AtorJogador
	
	void enviarLance(Action action); // Comunica diretamente com AtorClienteServidor (requere)
	
	void tratarLance(Action action); //requerida por AtorClientServer
	
	void procurarTesouro(); // requerida por AtorJogador
	
	void selecionarPersonagem() throws ClassNotFoundException; // requerida por AtorJogador
	
	void selecionarPersonagemEscolhida(int resultado) throws ClassNotFoundException; // requerida por AtorJogador
	
	void procurarArmadilhaOuPortaSecreta(); // requerida por AtorJogador
	
	void finalizarJogada(); // requerida por AtorJogador
	
	boolean isConnected();
	
	void setConnected(boolean connected);
	
	boolean getInSession(); // requerida por AtorJogador
	
	void finalizarJogo(); //requerida por AtorClientServer
	
	void iniciarNovaPartida(int posicao); // requerida por AtorClientServer
	
	void mostrarInventario(); // requerida por AtorJogador
	
	void mostrarInformacoes(int creatureID); // requerida por AtorJogador
	
	Position getPosition(byte i, byte j); // requerida por AtorJogador
	
	void setLocalPlayerName(String playerName);

	void setServerAddress(String serverAddress);
	
	AtorClientServer getAtorClienteServidor(); // requerida por AtorJogador
}
