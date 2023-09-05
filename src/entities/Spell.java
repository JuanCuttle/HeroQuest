package entities;

import br.ufsc.inf.leobr.cliente.Jogada;
import enums.StatusEnum;

public class Spell implements Jogada {
	
	private static final long serialVersionUID = -5887762451618737398L;
	protected byte spellId;
	protected StatusEnum deliveredStatus;
	protected byte deliveredDamage;
	
	public Spell(byte spellId, StatusEnum statusEnum, byte damage) {
		this.spellId = spellId;
		this.deliveredStatus = statusEnum;
		this.deliveredDamage = damage;
	}

	public byte getDamage() {
		return this.deliveredDamage;
	}

	public StatusEnum getStatus() {
		return this.deliveredStatus;
	}

	public byte getSpellId() {
		return this.spellId;
	}
	
	public void setDamage(byte damage) {
		this.deliveredDamage = damage;
	}

}