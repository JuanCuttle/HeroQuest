package entities;

public enum ActionType {

	MOVE(1, "Move"),
	ATTACK(2, "Attack"),
	CAST_SPELL(3, "CastSpell"),
	SEARCH_TREASURE(4, "SearchTreasure"),
	SEARCH_TRAPS(5, "SearchTraps"),
	OPEN_DOOR(6, "OpenDoor"),
	END_TURN(7, "EndTurn"),
	CHOOSE_CHARACTER(8, "ChooseCharacter"),
	SEND_PLAYER(9, "SendPlayer");

	private int id;
	private String name;

	ActionType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ActionType getByName(String actionName) {
		for (ActionType actionType : ActionType.values()) {
			if (actionType.name.equalsIgnoreCase(actionName))
				return actionType;
		}
		return CHOOSE_CHARACTER;
	}
}