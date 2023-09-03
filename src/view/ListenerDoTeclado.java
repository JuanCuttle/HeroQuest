package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.Directions;

public class ListenerDoTeclado implements KeyListener {
	
	protected AtorJogador ator;
	
	public ListenerDoTeclado(AtorJogador ator) {
		this.ator = ator;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		int tecla = e.getKeyCode();
		switch(tecla) {
			case KeyEvent.VK_RIGHT: this.ator.movimentar(Directions.RIGHT);
				break;
			case KeyEvent.VK_LEFT: this.ator.movimentar(Directions.LEFT);
				break;
			case KeyEvent.VK_UP: this.ator.movimentar(Directions.UP);
				break;
			case KeyEvent.VK_DOWN: this.ator.movimentar(Directions.DOWN);
				break;
			case KeyEvent.VK_1: this.ator.conectar();
				break;
			case KeyEvent.VK_2: this.ator.desconectar();
				break;
			case KeyEvent.VK_3: this.ator.iniciarPartida();
				break;
			case KeyEvent.VK_E: this.ator.finalizarJogada();
				break;
			case KeyEvent.VK_I: this.ator.mostrarInventario();
				break;
			case KeyEvent.VK_A: this.ator.atacar();
				break;
			case KeyEvent.VK_S: this.ator.usarMagia();
				break;
			case KeyEvent.VK_R: this.ator.procurarArmadilhaOuPortaSecreta();
				break;
			case KeyEvent.VK_T: this.ator.procurarTesouro();
				break;
			case KeyEvent.VK_O: this.ator.abrirPortaTeclado();
				break;
			case KeyEvent.VK_P: try {
				this.ator.selecionarPersonagem();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case KeyEvent.VK_M: this.ator.music();
				break;
		}
	}
	public void keyTyped(KeyEvent e) {}
}