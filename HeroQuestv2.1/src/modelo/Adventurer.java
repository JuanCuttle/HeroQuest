package modelo;

public class Adventurer extends Player {

	private static final long serialVersionUID = 476483639541190443L;
	protected PlayableCharacter player_character;
	
	
	public Adventurer(String idJogador) {
		super(idJogador);
	}
	
	public Adventurer() {}
	

	public PlayableCharacter getPlayableCharacter() {
		return this.player_character;
	}

	public void setPlayableCharacter(PlayableCharacter character) {
		this.player_character = character;
	}
}