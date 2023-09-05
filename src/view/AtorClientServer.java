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

public class AtorClientServer implements OuvidorProxy, NetgamesProxyInterface {

	private static final long serialVersionUID = 1L;
	protected Proxy proxy;
	protected HeroQuest heroQuest;

	public AtorClientServer(HeroQuest heroQuest) {
		proxy = Proxy.getInstance();
		proxy.addOuvinte(this);
		this.heroQuest = heroQuest;
	}

	public boolean conectar(String servidor, String nome) {
		try {
			proxy.conectar(servidor, nome);
		} catch (JahConectadoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			return false;
		} catch (NaoPossivelConectarException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			return false;
		} catch (ArquivoMultiplayerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean desconectar() {
		try {
			proxy.desconectar();
		} catch (NaoConectadoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			//return false;
		}
		return true;
	}

	public void iniciarPartida(int numJog) {
		try {
			proxy.iniciarPartida(numJog);
		} catch (NaoConectadoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void enviarJogada(Action action) {
		try {
			proxy.enviaJogada(action);
		} catch (NaoJogandoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void finalizarPartidaComErro(String message) {
		JOptionPane.showMessageDialog(null,
				Strings.ERRORABORT.toString());
				
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
