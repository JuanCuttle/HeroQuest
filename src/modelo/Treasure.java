package modelo;

public class Treasure {

	protected int goldAmount;

	public Treasure(int goldAmount) {
		this.goldAmount = goldAmount;
	}
	
	
	public int getGoldAmount() {
		return this.goldAmount;
	}
	
	public void setGoldAmount(int goldAmount) {
		this.goldAmount = goldAmount;
	}

}