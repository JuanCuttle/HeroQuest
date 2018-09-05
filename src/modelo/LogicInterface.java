package modelo;

import java.util.ArrayList;

import visao.AtorClientServer;

public interface LogicInterface {
	
	void abrirPorta(int id); // requerida por AtorJogador
	
	void abrirPortaTeclado(); // requerida por AtorJogador
	
	void movimentar(Directions direcao); // requerida por AtorJogador
	
	Creature getCriaturaDaVez(); // requerida por AtorJogador
	
	void atacar(); // requerida por AtorJogador
	
	void usarMagia(); // requerida por AtorJogador
	
	ArrayList<Creature> getCreatureQueue(); // requerida por AtorJogador
	
	void enviarLance(Lance lance); // Comunica diretamente com AtorClienteServidor (requere)
	
	void tratarLance(Lance lance); //requerida por AtorClientServer
	
	void procurarTesouro(); // requerida por AtorJogador
	
	void selecionarPersonagem(); // requerida por AtorJogador
	
	void selecionarPersonagemEscolhida(int resultado); // requerida por AtorJogador
	
	void procurarArmadilhaOuPortaSecreta(); // requerida por AtorJogador
	
	void finalizarJogada(); // requerida por AtorJogador
	
	boolean informarConectado(); // requerida por AtorJogador
	
	void estabelecerConectado(boolean valor); // requerida por AtorJogador
	
	boolean informarEmAndamento(); // requerida por AtorJogador
	
	void finalizarJogo(); //requerida por AtorClientServer
	
	void iniciarNovaPartida(int posicao); // requerida por AtorClientServer
	
	void mostrarInventario(); // requerida por AtorJogador
	
	void mostrarInformacoes(int creatureID); // requerida por AtorJogador
	
	Position getPosition(byte i, byte j); // requerida por AtorJogador
	
	void setNomeLocalPlayerAndServer(String idUsuario, String idServer); // requerida por AtorJogador
	
	AtorClientServer getAtorClienteServidor(); // requerida por AtorJogador
}
