package entities.actions;

import entities.Directions;

public class Move extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6681660893398112322L;

	protected byte sourceRow;
	protected byte sourceColumn;
	protected byte destinationRow;
	protected byte destinationColumn;

	protected byte damage;
	
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

	public byte getDamage() {
		return damage;
	}

	public void setDamage(byte damage) {
		this.damage = damage;
	}

	public byte getSourceRow() {
		return sourceRow;
	}

	public void setSourceRow(byte sourceRow) {
		this.sourceRow = sourceRow;
	}

	public byte getSourceColumn() {
		return sourceColumn;
	}

	public void setSourceColumn(byte sourceColumn) {
		this.sourceColumn = sourceColumn;
	}

	public byte getDestinationRow() {
		return destinationRow;
	}

	public void setDestinationRow(byte destinationRow) {
		this.destinationRow = destinationRow;
	}

	public byte getDestinationColumn() {
		return destinationColumn;
	}

	public void setDestinationColumn(byte destinationColumn) {
		this.destinationColumn = destinationColumn;
	}

}
