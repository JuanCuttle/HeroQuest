package entities;

public class LanceProcTesouro extends Lance {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8334075615667812844L;
	
	protected byte sourceL;
	protected byte sourceC;
	
	public byte getSourceL() {
		return sourceL;
	}
	public void setSourceL(byte sourceL) {
		this.sourceL = sourceL;
	}
	public byte getSourceC() {
		return sourceC;
	}
	public void setSourceC(byte sourceC) {
		this.sourceC = sourceC;
	}
}
