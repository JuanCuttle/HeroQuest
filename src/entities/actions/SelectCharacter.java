package entities.actions;

import entities.Adventurer;
import entities.Zargon;

public class SelectCharacter extends Action {

	private static final long serialVersionUID = 9130280307360862113L;
	
	protected byte selectedCharacterId;
	protected Zargon zargon;
	protected Adventurer adventurer;

	public byte getSelectedCharacterId() {
		return selectedCharacterId;
	}

	public void setSelectedCharacterId(byte selectedCharacterId) {
		this.selectedCharacterId = selectedCharacterId;
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
