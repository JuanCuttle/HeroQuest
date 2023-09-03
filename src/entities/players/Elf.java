package entities.players;

import entities.PlayableCharacter;
import entities.Spell;
import entities.Status;

import java.util.*;

public class Elf extends PlayableCharacter {

	private static final long serialVersionUID = 7870527566006224546L;
	protected ArrayList<Spell> spells;

	public Elf() {
		super(6, 4, 2, 2);
		this.spells = new ArrayList<Spell>();
	}

	public ArrayList<Spell> getSpells() {
		return this.spells;
	}
	
	public void createSpells() {
		Spell heal_Body = new Spell("Heal Body", null, (byte) 4);
		Spell rock_Feet = new Spell("Rock Feet", Status.AGILITY_DOWN, (byte) 0);
		Spell rock_Skin = new Spell("Rock Skin", Status.ROCK_SKIN, (byte) 0);
		this.spells.add(heal_Body);
		this.spells.add(rock_Feet);
		this.spells.add(rock_Skin);
	}
	
	public void removeSpellFromBook(Spell magia) {
		this.spells.remove(magia);
	}
}