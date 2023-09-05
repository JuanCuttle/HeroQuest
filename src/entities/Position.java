package entities;

import br.ufsc.inf.leobr.cliente.Jogada;
import entities.tiles.Trap;
import entities.tiles.Treasure;

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
		this.visible = true;
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

	public void setCreature(Creature creature) {
		this.creature = creature;
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
	
	public void makeTrapTriggered(){
		this.trap.makeTrapTriggered();
	}

	public Treasure getTreasure() {
		return this.treasure;
	}

	public void setTreasure(Treasure treasure) {
		this.treasure = treasure;
	}
	
	public boolean equals(Position pos){
		return this.row == pos.getRow() && this.column == pos.getColumn();
	}

	public void removeTrap() {
		this.trap = null;
	}

}