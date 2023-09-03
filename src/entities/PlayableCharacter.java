package entities;

import java.util.ArrayList;

import quests.BasicMap;
import quests.LegacyOfTheOrcWarlord;

public class PlayableCharacter extends Creature {

	private static final long serialVersionUID = -1118331420839898080L;
	protected int gold;
	protected ArrayList<Items> items;
	
	public PlayableCharacter(int body, int mind, int atk, int def) {
		super(body, mind, atk, def);
		items = new ArrayList<Items>();
	}

	public void increaseGold(int value) {
		this.gold += value;
	}

	public int getGold() {
		return this.gold;
	}

	public void addItemToBag(Items item) {
		this.items.add(item);
	}
	
	public ArrayList<Items> getItems(BasicMap map){
		if (map instanceof LegacyOfTheOrcWarlord && !((LegacyOfTheOrcWarlord) map).foundEquipment()){
			return new ArrayList<Items>();
		}
		return this.items;
	}
}