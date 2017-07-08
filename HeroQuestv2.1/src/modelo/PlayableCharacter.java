package modelo;

public class PlayableCharacter extends Creature{

	private static final long serialVersionUID = -1118331420839898080L;
	protected int gold;
	
	public PlayableCharacter(int body, int mind, int atk, int def) {
		super(body, mind, atk, def);
	}

	public void increaseGold(int value) {
		this.gold += value;
	}

	public int getGold() {
		return this.gold;
	}
}