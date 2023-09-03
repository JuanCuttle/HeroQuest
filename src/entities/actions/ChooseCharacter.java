package entities.actions;

import entities.Adventurer;
import entities.Zargon;

public class ChooseCharacter extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9130280307360862113L;
	
	protected byte value;
	protected Zargon zargon;
	protected Adventurer adventurer;

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public Zargon getZargon() {
		return zargon;
	}

	public void setZargon(Zargon zargon) {
		this.zargon = zargon;
	}

	public Adventurer getAdventurer() {
		return adventurer;
	}

	public void setAdventurer(Adventurer adventurer) {
		this.adventurer = adventurer;
	}
	
	

}
