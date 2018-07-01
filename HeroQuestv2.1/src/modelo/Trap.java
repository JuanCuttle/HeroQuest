package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Trap implements Jogada {

	private static final long serialVersionUID = 8734364272715828524L;
	protected byte deliveredDamage;
	protected boolean visible;
	
	protected boolean triggered;

	public void makeTrapVisible() {
		this.visible = true;
	}
	
	public void makeTrapTriggered() {
		this.triggered = true;
	}

	public byte getDeliveredDamage() {
		if (!triggered){ // needed?
			return this.deliveredDamage;
		} else {
			return 0;
		}
	}
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public boolean getTriggered() {
		return this.triggered;
	}

}