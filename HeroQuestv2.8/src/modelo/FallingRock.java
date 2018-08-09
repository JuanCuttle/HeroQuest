package modelo;

public class FallingRock extends Trap {
	
	private static final long serialVersionUID = 7563304684185503785L;

	public FallingRock() {
		this.deliveredDamage = calcularDano();
		this.visible = false;
		
		this.triggered = false;
	}
	

	public byte calcularDano() {
		byte dano = 0;
		for(int i = 0; i < 3; i++) {
			if((int)(Math.random()*2) == 1) {
				dano++;
			}
		}
		return dano;
	}
}