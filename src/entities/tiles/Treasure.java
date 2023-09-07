package entities.tiles;

import enums.ItemEnum;

public class Treasure {

	protected int goldAmount;
	protected boolean isTrap;
	private ItemEnum item;

	public Treasure(int goldAmount) {
		this.goldAmount = goldAmount;
		this.isTrap = false;
	}

	public int getGoldAmount() {
		return this.goldAmount;
	}
	
	public void setGoldAmount(int goldAmount) {
		this.goldAmount = goldAmount;
	}
	
	public void setAsTrap(boolean trap){
		this.isTrap = trap;
	}
	
	public boolean isTrap(){
		return this.isTrap;
	}
	
	public void setItem(ItemEnum item){
		this.item = item;
	}

	public ItemEnum getItem() {
		return item;
	}

}