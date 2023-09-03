package entities.actions;

import entities.Spell;

public class CastSpell extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5796576882256976829L;

	protected Spell spell;
	protected byte targetID;
	protected byte roundsToSleep;

	public Spell getSpell() {
		return spell;
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

	public byte getTargetID() {
		return targetID;
	}

	public void setTargetID(byte targetID) {
		this.targetID = targetID;
	}

	public byte getRoundsToSleep() {
		return roundsToSleep;
	}

	public void setRoundsToSleep(byte roundsToSleep) {
		this.roundsToSleep = roundsToSleep;
	}
}
