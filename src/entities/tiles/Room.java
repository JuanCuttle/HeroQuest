package entities.tiles;

import entities.Position;

public class Room extends Position {
	
	private static final long serialVersionUID = -2514265334681042323L;

	public Room(byte row, byte column) {
		super(row, column);
	}
}