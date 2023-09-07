package enums;

public enum ActionTypeEnum {

	MOVEMENT(1, "Movement"),
	ATTACK(2, "Attack"),
	CAST_SPELL(3, "CastSpell"),
	SEARCH_FOR_TREASURE(4, "SearchForTreasure"),
	SEARCH_FOR_TRAPS_AND_HIDDEN_DOORS(5, "SearchForTrapsAndHiddenDoors"),
	OPEN_DOOR(6, "OpenDoor"),
	END_TURN(7, "EndTurn"),
	SELECT_CHARACTER(8, "SelectCharacter"),
	SEND_PLAYER(9, "SendPlayer");

	private final int id;
	private final String name;

	ActionTypeEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public static ActionTypeEnum getByName(String actionName) {
		for (ActionTypeEnum actionTypeEnum : ActionTypeEnum.values()) {
			if (actionTypeEnum.name.equalsIgnoreCase(actionName))
				return actionTypeEnum;
		}
		return SELECT_CHARACTER;
	}
}