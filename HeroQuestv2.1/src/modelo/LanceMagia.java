package modelo;


public class LanceMagia extends Lance {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5796576882256976829L;
	
	protected Spell spell;
	protected byte targetID;
	
	
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
	
}
