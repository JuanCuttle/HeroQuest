package modelo;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Position implements Jogada {

	private static final long serialVersionUID = -2914404127760458829L;
	protected Trap trap;
	protected Creature creature;
	protected byte row;
	protected byte column;
	protected Treasure treasure;
	
	protected boolean visible;
	
	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Position(int row, int column) {
		this.row = (byte) row;
		this.column = (byte) column;
		this.visible = false;
	}

	
	public byte getRow() {
		return this.row;
	}

	public byte getColumn() {
		return this.column;
	}

	public void removeCreature() {
		this.creature = null;
	}

	public void setCreature(Creature criatura) {
		this.creature = criatura;
	}

	public void setTrap(Trap trap) {
		this.trap = trap;
	}
	
	public Trap getTrap() {
		return this.trap;
	}

	public Creature getCreature() {
		return this.creature;
	}

	public void makeTrapVisible() {
		this.trap.makeTrapVisible();
	}

	public Treasure getTreasure() {
		return this.treasure;
	}


	public void setTreasure(Treasure treasure) {
		this.treasure = treasure;
	}

}