package entities;

import entities.players.PlayableCharacter;

public class Adventurer extends Player {

	private static final long serialVersionUID = 476483639541190443L;
	protected PlayableCharacter playerCharacter;
	
	
	public Adventurer(String playerName) {
		super(playerName);
	}
	
	public Adventurer() {}

	public PlayableCharacter getPlayableCharacter() {
		return this.playerCharacter;
	}

	public void setPlayableCharacter(PlayableCharacter character) {
		this.playerCharacter = character;
	}
}