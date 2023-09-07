package entities.tiles;

public class FallingRock extends Trap {
	
	private static final long serialVersionUID = 7563304684185503785L;

	public FallingRock() {
		this.deliveredDamage = calculateDamage();
		this.visible = false;
		this.triggered = false;
	}

	public byte calculateDamage() {
		byte damage = 0;
		for (int i = 0; i < 3; i++) {
			if ((int)(Math.random()*2) == 1) {
				damage++;
			}
		}
		return damage;
	}
}