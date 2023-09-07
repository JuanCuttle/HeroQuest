package entities.players;

import java.util.ArrayList;

import entities.Creature;
import enums.ItemEnum;
import quests.BasicMap;
import quests.LegacyOfTheOrcWarlord;

public class PlayableCharacter extends Creature {

	private static final long serialVersionUID = -1118331420839898080L;
	protected int gold;
	protected ArrayList<ItemEnum> items;
	
	public PlayableCharacter(int body, int mind, int atk, int def) {
		super(body, mind, atk, def);
		items = new ArrayList<>();
	}

	public void increaseGold(int value) {
		this.gold += value;
	}

	public int getGold() {
		return this.gold;
	}

	public void addItemToBag(ItemEnum item) {
		this.items.add(item);
	}
	
	public ArrayList<ItemEnum> getItems(BasicMap map){
		if (map instanceof LegacyOfTheOrcWarlord && !((LegacyOfTheOrcWarlord) map).foundEquipment()){
			return new ArrayList<>();
		}
		return this.items;
	}
}