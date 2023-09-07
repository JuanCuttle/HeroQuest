package entities.tiles;

import entities.Position;

public class Door extends Position {

	private static final long serialVersionUID = -4132807911449425844L;
	protected boolean open;
	private final int id;
	
	private boolean secret = false;
	
	public Door(int row, int column, int id) {
		super((byte)row, (byte)column);
		this.id = id;
	}

	public boolean isOpen() {
		return this.open;
	}

	public void openDoor() {
		this.open = true;
	}

	public int getID() {
		return id;
	}
	
	public void closeDoor(){
		this.open = false;
	}
	
	public boolean isSecret(){
		return this.secret;
	}

	public void setSecret(boolean secret){
		this.secret = secret;
	}

}