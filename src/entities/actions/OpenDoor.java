package entities.actions;

public class OpenDoor extends Action {

	private static final long serialVersionUID = 2278410214890287242L;
	
	protected int doorId;

	public int getDoorId() {
		return this.doorId;
	}

	public void setDoorId(int doorId) {
		this.doorId = doorId;
	}
}
