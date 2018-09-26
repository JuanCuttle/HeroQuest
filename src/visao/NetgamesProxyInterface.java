package visao;

import br.ufsc.inf.leobr.cliente.Jogada;
import modelo.Lance;

public interface NetgamesProxyInterface {

	 boolean conectar(String servidor, String nome); // requerida por AtorJogador
	
	 boolean desconectar(); // requerida por AtorJogador
	 
	 void iniciarPartida(int numJog); // requerida por AtorJogador e HeroQuest (nao usado neste)
	 
	 void enviarJogada(Lance lance); // requerida por HeroQuest
	 
	 void finalizarPartidaComErro(String message); // requerida por NetgamesServer
	 
	 void iniciarNovaPartida(Integer posicao); // requerida por NetgamesServer
	 
	 void receberJogada(Jogada jogada); // requerida por NetgamesServer
	 
	 void receberMensagem(String msg); // requerida por NetgamesServer
	 
	 void tratarConexaoPerdida(); // requerida por NetgamesServer
	 
	 void tratarPartidaNaoIniciada(String message); // requerida por NetgamesServer
}
