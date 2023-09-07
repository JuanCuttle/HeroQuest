package entities.tiles;

public class Pit extends Trap {
	
	private static final long serialVersionUID = -2180148066287180431L;

	public Pit() {
		this.deliveredDamage = calculateDamage();
		this.visible = false;
	}
	
	private byte calculateDamage() {
		return 1;
	}
}