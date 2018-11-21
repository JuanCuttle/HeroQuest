package visao;

import java.io.Serializable;

public class SaveFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1215397092889062634L;
	private int charClass;
	private int gold;
	//private Item[] items;
	
	public SaveFile(int heroType, int gold2){
		this.charClass = heroType;
		this.gold = gold2;
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
	
}
