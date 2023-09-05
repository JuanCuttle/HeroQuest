package entities;

import java.util.Random;

import br.ufsc.inf.leobr.cliente.Jogada;
import enums.StatusEnum;

public class Creature implements Jogada, Comparable<Creature> {

	private static final long serialVersionUID = -2186498705939032580L;
	protected boolean turn;
	protected byte body;
	protected byte mind;
	protected byte movement;
	protected StatusEnum statusEnum;
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
		this.statusEnum = StatusEnum.NEUTRAL;
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
		return this.statusEnum;
	}

	public void setStatus(StatusEnum statusEnum) {
		this.statusEnum = statusEnum;
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
		int movement = 0;
		if (!(this.getClass().getSuperclass().getSimpleName().equals("Monster"))){
			Random rand = new Random();
			movement = rand.nextInt(12 - 2 + 1) + 2; // 12 max vaue, + 2 because of minimum roll
			if (this.statusEnum == StatusEnum.AGILITY_DOWN){
				movement /= 2;
				this.statusEnum = StatusEnum.NEUTRAL;
			} else if (this.statusEnum == StatusEnum.AGILITY_UP){
				movement *= 2;
				this.statusEnum = StatusEnum.NEUTRAL;
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

	public void setCurrentPosition(Position novaPosicao) {
		this.currentPosition = novaPosicao;
	}
	
	public void setID(byte id) {
		this.id = id;
	}
	
	public void decreaseMovement() {
		this.movement -= 1;
	}

	@Override
	public int compareTo(Creature arg0) {
		if (this.getID() < arg0.getID()) {
			return 1;
		}
		if (this.getID() > arg0.getID()) {
			return -1;
		}
		return 0;
	}

	public void usarMagia(Spell magia) {
		this.mind--;
		this.removeSpellFromBook(magia);
	}

	public void removeSpellFromBook(Spell magia) {
	}
	
	public byte getRoundsToSleep() {
		return roundsToSleep;
	}

	public void setRoundsToSleep(byte roundsToSleep) {
		this.roundsToSleep = roundsToSleep;
	}
	
	public byte getMovementModifier() {
		return movementModifier;
	}

	public void setMovementModifier(byte movementModifier) {
		this.movementModifier = movementModifier;
	}
	
	public int getMonsterMovement(){
		int movement = 0;
		switch(this.getClass().getSimpleName()){
			case "Goblin": movement = 10;
				break;
			case "Orc": movement = 8;
				break;
			case "Fimir": movement = 6;
				break;
			case "Skeleton": movement = 6;
				break;			
			case "Zombie": movement = 4;
				break;
			case "Mummy": movement = 4;
				break;
			case "ChaosWarrior": movement = 6;
				break;
			case "Gargoyle": movement = 6;
				break;
			case "PolarWarbear": movement = 6;
				break;
			case "ChaosSorcerer": movement = 8;
				break;
			default: movement = 5;
		}
		return movement+movementModifier;
	}
}