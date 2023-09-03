package view;

import br.ufsc.inf.leobr.cliente.Jogada;
import entities.actions.Action;

public interface NetgamesProxyInterface {

	 boolean conectar(String servidor, String nome); // requerida por AtorJogador
	
	 boolean desconectar(); // requerida por AtorJogador
	 
	 void iniciarPartida(int numJog); // requerida por AtorJogador e HeroQuest (nao usado neste)
	 
	 void enviarJogada(Action action); // requerida por HeroQuest
	 
	 void finalizarPartidaComErro(String message); // requerida por NetgamesServer
	 
	 void iniciarNovaPartida(Integer posicao); // requerida por NetgamesServer
	 
	 void receberJogada(Jogada jogada); // requerida por NetgamesServer
	 
	 void receberMensagem(String msg); // requerida por NetgamesServer
	 
	 void tratarConexaoPerdida(); // requerida por NetgamesServer
	 
	 void tratarPartidaNaoIniciada(String message); // requerida por NetgamesServer
}
