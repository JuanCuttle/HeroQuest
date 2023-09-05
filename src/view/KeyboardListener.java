package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enums.DirectionEnum;

public class KeyboardListener implements KeyListener {
	
	protected GUI user;
	
	public KeyboardListener(GUI user) {
		this.user = user;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		int tecla = e.getKeyCode();
		switch(tecla) {
			case KeyEvent.VK_RIGHT: this.user.moveCreature(DirectionEnum.RIGHT);
				break;
			case KeyEvent.VK_LEFT: this.user.moveCreature(DirectionEnum.LEFT);
				break;
			case KeyEvent.VK_UP: this.user.moveCreature(DirectionEnum.UP);
				break;
			case KeyEvent.VK_DOWN: this.user.moveCreature(DirectionEnum.DOWN);
				break;
			case KeyEvent.VK_1: this.user.connectToServer();
				break;
			case KeyEvent.VK_2: this.user.disconnectFromServer();
				break;
			case KeyEvent.VK_3: this.user.startGame();
				break;
			case KeyEvent.VK_E: this.user.endTurn();
				break;
			case KeyEvent.VK_I: this.user.showInventory();
				break;
			case KeyEvent.VK_A: this.user.attack();
				break;
			case KeyEvent.VK_S: this.user.castSpell();
				break;
			case KeyEvent.VK_R: this.user.searchTrapsAndHiddenDoors();
				break;
			case KeyEvent.VK_T: this.user.searchTreasure();
				break;
			case KeyEvent.VK_O: this.user.openDoorWithKeyboard();
				break;
			case KeyEvent.VK_P: try {
				this.user.chooseCharacter();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
				break;
			case KeyEvent.VK_M: this.user.toggleMusic();
				break;
		}
	}
	public void keyTyped(KeyEvent e) {}
}