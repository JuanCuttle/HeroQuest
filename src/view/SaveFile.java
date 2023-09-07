package view;

import java.io.Serializable;
import java.util.ArrayList;

import enums.ItemEnum;

public class SaveFile implements Serializable {

	private static final long serialVersionUID = -1215397092889062634L;
	private int characterClass;
	private int gold;
	private ArrayList<ItemEnum> items;
	
	public SaveFile(int heroType, int gold, ArrayList<ItemEnum> items){
		this.characterClass = heroType;
		this.gold = gold;
		this.items = items;
	}
	
	public int getCharacterClass() {
		return characterClass;
	}
	public void setCharacterClass(byte characterClass) {
		this.characterClass = characterClass;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(byte gold) {
		this.gold = gold;
	}

	public ArrayList<ItemEnum> getItems() {
		return items;
	}
	
	public void setItems(ArrayList<ItemEnum> items) {
		this.items = items;
	}
	
}
