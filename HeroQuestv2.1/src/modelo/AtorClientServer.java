package modelo;

import javax.swing.JOptionPane;

import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;

public class AtorClientServer implements OuvidorProxy {

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
		} catch (NaoPossivelConectarException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (ArquivoMultiplayerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	public boolean desconectar() {
		try {
			proxy.desconectar();
		} catch (NaoConectadoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
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

	public void enviarJogada(Lance lance) {
		try {
			proxy.enviaJogada((Jogada) lance);
		} catch (NaoJogandoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void enviarJogada(LanceAbrirPorta lance) {
		try {
			proxy.enviaJogada((Jogada) lance);
		} catch (NaoJogandoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void finalizarPartidaComErro(String message) {
		JOptionPane.showMessageDialog(null,
				"Ocorreu um erro e o jogo teve de ser abortado");
		this.heroQuest.finalizarJogo();
	}

	public void iniciarNovaPartida(Integer posicao) {
		this.heroQuest.iniciarNovaPartida(posicao);
		//this.heroQuest.selecionarPersonagem();
	}

	public void receberJogada(Jogada jogada) {
		Lance lance = (Lance) jogada;
		this.heroQuest.tratarLance(lance);
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