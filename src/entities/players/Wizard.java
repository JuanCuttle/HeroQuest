package entities.players;

import entities.Spell;
import enums.SpellNameEnum;
import enums.StatusEnum;

import java.util.*;

public class Wizard extends PlayableCharacter {

	private static final long serialVersionUID = 7405881828653705414L;
	protected ArrayList<Spell> spells;

	public Wizard() {
		super(4, 6, 1, 2);
		this.spells = new ArrayList<>();
	}

	public ArrayList<Spell> getSpells() {
		return this.spells;
	}
	
	public void createSpells() {
		Spell swiftWind = new Spell(SpellNameEnum.SWIFT_WIND.getId(), StatusEnum.AGILITY_UP, (byte) 0);
		Spell tempest = new Spell(SpellNameEnum.TEMPEST.getId(), StatusEnum.CURSED, (byte) 0);
		Spell ballOfFlame = new Spell(SpellNameEnum.BALL_OF_FLAME.getId(), null, (byte) -2);
		Spell courage = new Spell(SpellNameEnum.COURAGE.getId(), StatusEnum.COURAGE, (byte) 0);
		Spell fireOfWrath = new Spell(SpellNameEnum.FIRE_OF_WRATH.getId(), null, (byte) -1);
		Spell sleep = new Spell(SpellNameEnum.SLEEP.getId(), StatusEnum.SLEEPING, (byte) 0);
		Spell waterOfHealing = new Spell(SpellNameEnum.WATER_OF_HEALING.getId(), null, (byte) 4);
		this.spells.add(swiftWind);
		this.spells.add(tempest);
		this.spells.add(ballOfFlame);
		this.spells.add(courage);
		this.spells.add(fireOfWrath);
		this.spells.add(sleep);
		this.spells.add(waterOfHealing);
	}
	
	public void removeSpellFromBook(Spell spell) {
		this.spells.remove(spell);
	}
}