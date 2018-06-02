package modelo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ListenerDoTeclado implements KeyListener {
	
	protected AtorJogador ator;
	
	public ListenerDoTeclado(AtorJogador ator) {
		this.ator = ator;
	}


	@Override
	public void keyPressed(KeyEvent e) {
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
			case KeyEvent.VK_4: this.ator.finalizarJogada();
				break;
			case KeyEvent.VK_5: this.ator.mostrarInventario();
				break;
			case KeyEvent.VK_6: this.ator.atacar();
				break;
			case KeyEvent.VK_7: this.ator.usarMagia();
				break;
			case KeyEvent.VK_8: this.ator.procurarArmadilhaOuPortaSecreta();
				break;
			case KeyEvent.VK_9: this.ator.procurarTesouro();
				break;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}