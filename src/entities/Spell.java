package entities;

import br.ufsc.inf.leobr.cliente.Jogada;
import enums.StatusEnum;

public class Spell implements Jogada {
	
	private static final long serialVersionUID = -5887762451618737398L;
	protected String name;
	protected StatusEnum deliveredStatus;
	protected byte deliveredDamage;
	
	public Spell(String name, StatusEnum statusEnum, byte damage) {
		this.name = name;
		this.deliveredStatus = statusEnum;
		this.deliveredDamage = damage;
	}

	public byte getDamage() {
		return this.deliveredDamage;
	}

	public StatusEnum getStatus() {
		return this.deliveredStatus;
	}

	public String getName() {
		return this.name;
	}
	
	public void setDamage(byte damage) {
		this.deliveredDamage = damage;
	}

}