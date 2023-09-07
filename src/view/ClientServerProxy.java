package view;

import javax.swing.JOptionPane;

import entities.HeroQuest;
import entities.actions.Action;
import entities.utils.Strings;
import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import interfaces.NetgamesProxyInterface;

public class ClientServerProxy implements OuvidorProxy, NetgamesProxyInterface {

	private static final long serialVersionUID = 1L;
	protected Proxy proxy;
	protected HeroQuest heroQuest;

	public ClientServerProxy(HeroQuest heroQuest) {
		proxy = Proxy.getInstance();
		proxy.addOuvinte(this);
		this.heroQuest = heroQuest;
	}

	public boolean connect(String serverAddress, String playerName) {
		try {
			proxy.conectar(serverAddress, playerName);
		} catch (JahConectadoException | NaoPossivelConectarException | ArquivoMultiplayerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			return false;
		}
        return true;
	}

	public boolean disconnect() {
		try {
			proxy.desconectar();
		} catch (NaoConectadoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	public void startGame(int numberOfPlayers) {
		try {
			proxy.iniciarPartida(numberOfPlayers);
		} catch (NaoConectadoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendAction(Action action) {
		try {
			proxy.enviaJogada(action);
		} catch (NaoJogandoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void finalizarPartidaComErro(String message) {
		JOptionPane.showMessageDialog(null,
				Strings.ERROR_ABORTED_GAME.toString());
				
		this.heroQuest.endGame();
	}

	public void iniciarNovaPartida(Integer position) {
		this.heroQuest.startNewGame();
	}

	public void receberJogada(Jogada jogada) {
		Action action = (Action) jogada;
		this.heroQuest.processAction(action);
	}

	@Override
	public void receberMensagem(String msg) {
		// Auto-generated method stub
	}

	@Override
	public void tratarConexaoPerdida() {
		// Auto-generated method stub
	}

	@Override
	public void tratarPartidaNaoIniciada(String message) {
		// Auto-generated method stub
	}

}
