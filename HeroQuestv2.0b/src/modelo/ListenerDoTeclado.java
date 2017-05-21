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
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}