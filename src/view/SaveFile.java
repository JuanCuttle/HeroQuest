package view;

import java.io.Serializable;
import java.util.ArrayList;

import enums.ItemEnum;

public class SaveFile implements Serializable {

	private static final long serialVersionUID = -1215397092889062634L;
	private final int characterClass;
	private final int gold;
	private final ArrayList<ItemEnum> items;
	
	public SaveFile(int heroType, int gold, ArrayList<ItemEnum> items){
		this.characterClass = heroType;
		this.gold = gold;
		this.items = items;
	}
	
	public int getCharacterClass() {
		return characterClass;
	}

	public int getGold() {
		return gold;
	}

	public ArrayList<ItemEnum> getItems() {
		return items;
	}

}
