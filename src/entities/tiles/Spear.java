package entities.tiles;

public class Spear extends Trap {
	
	private static final long serialVersionUID = 8790635054092561498L;

	public Spear() {
		this.deliveredDamage = calculateDamage();
		this.visible = false;
		this.triggered = false;
	}
	
	public byte calculateDamage() {
		if ((int)(Math.random()*2) == 1) {
			return 1;
		}
		return 0;
	}
}