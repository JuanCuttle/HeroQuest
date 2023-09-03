package entities;

public class Pit extends Trap {
	
	private static final long serialVersionUID = -2180148066287180431L;

	public Pit() {
		this.deliveredDamage = calcularDano();
		this.visible = false;
	}
	
	private byte calcularDano() {
		/*if ((int)(Math.random()*2) == 1) {
			return 1;
		}
		return 0;*/
		return 1;
	}
}