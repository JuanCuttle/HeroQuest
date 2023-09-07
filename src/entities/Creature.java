package entities;

import java.util.Random;

import br.ufsc.inf.leobr.cliente.Jogada;
import entities.enemies.Monster;
import enums.MonsterEnum;
import enums.StatusEnum;

public class Creature implements Jogada, Comparable<Creature> {

	private static final long serialVersionUID = -2186498705939032580L;
	protected boolean turn;
	protected byte body;
	protected byte mind;
	protected byte movement;
	protected StatusEnum status;
	protected byte attackDiceAmount;
	protected byte defenceDiceAmount;
	protected Position currentPosition;
	protected byte id;
	
	protected boolean isVisible;
	protected byte roundsToSleep;
	protected byte movementModifier; // For bosses

	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Creature(int body, int mind, int atk, int def) {
		this.turn = false;
		this.body = (byte) body;
		this.mind = (byte) mind;
		this.status = StatusEnum.NEUTRAL;
		this.attackDiceAmount = (byte) atk;
		this.defenceDiceAmount = (byte) def;
		
		this.movementModifier = 0;
		
		this.isVisible = false;
	}

	public byte getMovement() {
		return this.movement;
	}

	public Position getCurrentPosition() {
		return this.currentPosition;
	}

	public void decreaseBody(byte value) {
		this.body -= value;
	}

	public void increaseBody(byte value) {
		this.body += value;
	}

	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public byte getMind() {
		return this.mind;
	}

	public byte getBody() {
		return this.body;
	}

	public byte getID() {
		return this.id;
	}

	public byte getAttackDiceAmount() {
		return this.attackDiceAmount;
	}

	public byte getDefenceDiceAmount() {
		return this.defenceDiceAmount;
	}

	public void setAttackDiceAmount(byte diceAmount) {
		this.attackDiceAmount = diceAmount;
	}

	public void setDefenceDiceAmount(byte diceAmount) {
		this.defenceDiceAmount = diceAmount;
	}
	
	public void setMovement() {
		int movement;
		if (!(this instanceof Monster)) {
			Random rand = new Random();
			movement = rand.nextInt(12 - 2 + 1) + 2; // 12 max value, + 2 because of minimum roll
			if (StatusEnum.AGILITY_DOWN.equals(this.status)) {
				movement /= 2;
				this.status = StatusEnum.NEUTRAL;
			} else if (StatusEnum.AGILITY_UP.equals(this.status)) {
				movement *= 2;
				this.status = StatusEnum.NEUTRAL;
			}
		} else {
			if (this instanceof SirRagnar) {
				Random rand = new Random();
				movement = rand.nextInt(6) + 1; // Hurt ally
			} else {
				movement = this.getMonsterMovement();
			}
		}
		this.movement = (byte) movement;
	}

	public void setCurrentPosition(Position newPosition) {
		this.currentPosition = newPosition;
	}
	
	public void setID(byte id) {
		this.id = id;
	}
	
	public void decreaseMovement() {
		this.movement -= 1;
	}

	@Override
	public int compareTo(Creature comparedCreature) {
		return Byte.compare(comparedCreature.getID(), this.getID());
	}

	public void spendSpell(Spell spell) {
		this.mind--;
		this.removeSpellFromBook(spell);
	}

	public void removeSpellFromBook(Spell spell) {
	}
	
	public byte getRoundsToSleep() {
		return roundsToSleep;
	}

	public void setRoundsToSleep(byte roundsToSleep) {
		this.roundsToSleep = roundsToSleep;
	}

	public void setMovementModifier(byte movementModifier) {
		this.movementModifier = movementModifier;
	}
	
	public int getMonsterMovement(){
		int movement;
		switch(MonsterEnum.getByName(this.getClass().getSimpleName())){
			case GOBLIN: movement = 10;
				break;
			case ORC: movement = 8;
				break;
			case FIMIR: movement = 6;
				break;
			case SKELETON: movement = 6;
				break;
			case ZOMBIE: movement = 4;
				break;
			case MUMMY: movement = 4;
				break;
			case CHAOS_WARRIOR: movement = 6;
				break;
			case GARGOYLE: movement = 6;
				break;
			case POLAR_WARBEAR: movement = 6;
				break;
			case CHAOS_SORCERER: movement = 8;
				break;
			default: movement = 5;
		}
		return movement+movementModifier;
	}
}