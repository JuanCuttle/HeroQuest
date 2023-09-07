package entities.players;

import entities.Spell;
import enums.SpellNameEnum;
import enums.StatusEnum;

import java.util.*;

public class Elf extends PlayableCharacter {

	private static final long serialVersionUID = 7870527566006224546L;
	protected ArrayList<Spell> spells;

	public Elf() {
		super(6, 4, 2, 2);
		this.spells = new ArrayList<>();
	}

	public ArrayList<Spell> getSpells() {
		return this.spells;
	}
	
	public void createSpells() {
		Spell healBody = new Spell(SpellNameEnum.HEAL_BODY.getId(), null, (byte) 4);
		Spell rockFeet = new Spell(SpellNameEnum.ROCK_FEET.getId(), StatusEnum.AGILITY_DOWN, (byte) 0);
		Spell rockSkin = new Spell(SpellNameEnum.ROCK_SKIN.getId(), StatusEnum.ROCK_SKIN, (byte) 0);
		this.spells.add(healBody);
		this.spells.add(rockFeet);
		this.spells.add(rockSkin);
	}
	
	public void removeSpellFromBook(Spell spell) {
		this.spells.remove(spell);
	}
}