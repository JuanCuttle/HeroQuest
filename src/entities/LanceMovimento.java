package entities;

public class LanceMovimento extends Lance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6681660893398112322L;

	protected byte sourceL;
	protected byte sourceC;
	protected byte destinationL;
	protected byte destinationC;

	protected byte dano;
	
	protected Directions direction;
	protected byte opcao;

	public byte getOpcao() {
		return opcao;
	}

	public void setOpcao(byte opcao) {
		this.opcao = opcao;
	}

	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public byte getDano() {
		return dano;
	}

	public void setDano(byte dano) {
		this.dano = dano;
	}

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

	public byte getDestinationL() {
		return destinationL;
	}

	public void setDestinationL(byte destinationL) {
		this.destinationL = destinationL;
	}

	public byte getDestinationC() {
		return destinationC;
	}

	public void setDestinationC(byte destinationC) {
		this.destinationC = destinationC;
	}

}
