package enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionTypeEnumTest {

    @Test
    public void shouldReturnMovementEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.MOVEMENT;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("Movement"));
    }

    @Test
    public void shouldReturnAttackEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.ATTACK;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("Attack"));
    }

    @Test
    public void shouldReturnCastSpellEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.CAST_SPELL;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("CastSpell"));
    }

    @Test
    public void shouldReturnSearchForTreasureEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.SEARCH_FOR_TREASURE;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("SearchForTreasure"));
    }

    @Test
    public void shouldReturnSearchForTrapsAndHiddenDoorsEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.SEARCH_FOR_TRAPS_AND_HIDDEN_DOORS;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("SearchForTrapsAndHiddenDoors"));
    }

    @Test
    public void shouldReturnOpenDoorEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.OPEN_DOOR;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("OpenDoor"));
    }

    @Test
    public void shouldReturnEndTurnEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.END_TURN;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("EndTurn"));
    }

    @Test
    public void shouldReturnSelectCharacterEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.SELECT_CHARACTER;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("SelectCharacter"));
    }

    @Test
    public void shouldReturnSendPlayerEnum() {
        ActionTypeEnum expectedEnum = ActionTypeEnum.SEND_PLAYER;
        assertEquals(expectedEnum, ActionTypeEnum.getByName("SendPlayer"));
    }
}
