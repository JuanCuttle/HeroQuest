package modelo;

import java.util.Random;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Creature implements Jogada, Comparable<Creature> {

	private static final long serialVersionUID = -2186498705939032580L;
	protected boolean turn;
	protected byte body;
	protected byte mind;
	protected byte movement;
	protected Status status;
	protected byte attackDiceAmount;
	protected byte defenceDiceAmount;
	protected Position currentPosition;
	protected byte id;
	
	protected boolean isVisible;
	
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
		this.status = Status.NEUTRAL;
		this.attackDiceAmount = (byte) atk;
		this.defenceDiceAmount = (byte) def;
		
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

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
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
		Random rand = new Random();
		int movement = rand.nextInt(12 - 2 + 1) + 2;
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
}