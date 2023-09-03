package entities.tiles;

public class Spear extends Trap {
	
	private static final long serialVersionUID = 8790635054092561498L;

	public Spear() {
		this.deliveredDamage = calcularDano();
		this.visible = false;
		
		this.triggered = false;
	}
	
	public byte calcularDano() {
		if ((int)(Math.random()*2) == 1) {
			return 1;
		}
		return 0;
	}
}