package view;

import java.io.Serializable;
import java.util.ArrayList;

import entities.Items;

public class SaveFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1215397092889062634L;
	private int charClass;
	private int gold;
	private ArrayList<Items> items;
	
	public SaveFile(int heroType, int gold, ArrayList<Items> items){
		this.charClass = heroType;
		this.gold = gold;
		this.items = items;
	}
	
	public int getCharClass() {
		return charClass;
	}
	public void setCharClass(byte charClass) {
		this.charClass = charClass;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(byte gold) {
		this.gold = gold;
	}

	public ArrayList<Items> getItems() {
		return items;
	}
	
	public void setItems(ArrayList<Items> items) {
		this.items = items;
	}
	
}
