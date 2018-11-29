package modelo;

public class Treasure {

	protected int goldAmount;
	protected boolean isTrap;
	private Items item;

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
	
	public void setItem(Items item){
		this.item = item;
	}


	public Items getItem() {
		return item;
	}

}