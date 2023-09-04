package entities.actions;

import entities.Player;

public class SendPlayer extends Action {

	private static final long serialVersionUID = 1496481667237326600L;
	
	protected Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
