package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Trap implements Jogada {

	private static final long serialVersionUID = 8734364272715828524L;
	protected byte deliveredDamage;
	protected boolean visible;

	public void makeTrapVisible() {
		this.visible = true;
	}

	public byte getDeliveredDamage() {
		return this.deliveredDamage;
	}
	
	public boolean getVisible() {
		return this.visible;
	}

}