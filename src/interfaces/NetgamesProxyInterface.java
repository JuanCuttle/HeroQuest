package interfaces;

import br.ufsc.inf.leobr.cliente.Jogada;
import entities.actions.Action;
@SuppressWarnings("unused")
public interface NetgamesProxyInterface {

	 boolean connect(String serverAddress, String playerName);
	
	 boolean disconnect();
	 
	 void startGame(int numberOfPlayers);
	 
	 void sendAction(Action action);
	 void finalizarPartidaComErro(String message); // required by NetgamesServer
	void iniciarNovaPartida(Integer posicao); // required by NetgamesServer
	void receberJogada(Jogada jogada); // required by NetgamesServer
	void receberMensagem(String msg); // required by NetgamesServer
	void tratarConexaoPerdida(); // required by NetgamesServer
	void tratarPartidaNaoIniciada(String message); // required by NetgamesServer
}
