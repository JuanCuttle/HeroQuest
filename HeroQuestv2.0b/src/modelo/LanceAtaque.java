package modelo;

public class LanceAtaque extends Lance {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8489937208477113086L;
	
	protected byte targetID;
	protected byte value;
	
	
	public byte getValue() {
		return value;
	}
	public byte getTargetID() {
		return targetID;
	}
	public void setTargetID(byte targetID) {
		this.targetID = targetID;
	}
	public void setValue(byte value) {
		this.value = value;
	}
	
	
	
}
