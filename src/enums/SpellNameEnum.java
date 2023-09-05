package enums;

public enum SpellNameEnum {
    SWIFT_WIND((byte) 1, "Swift Wind", "The target Creature can roll twice as many movement dice in the next turn"),
    TEMPEST((byte) 2, "Tempest", "The target misses their next turn"),
    BALL_OF_FLAME((byte) 3, "Ball of Flame", "Maximum 2 BP of damage. 2 dice are rolled. For every 5 or 6 rolled, 1 BP less damage is taken"),
    COURAGE((byte) 4, "Courage", "The target may roll 2 extra combat dice when attacking, until there are no more monsters in sight"),
    FIRE_OF_WRATH((byte) 5, "Fire of Wrath", "Maximum 1 BP of damage. 1 die is rolled. If a 5 or a 6 is rolled, no damage is taken"),
    SLEEP((byte) 6, "Sleep", "Monster rolls 1 die for each MP they have. If a 6 is rolled, spell fails. If successful, the monster cannot attack or defend until they break the spell"),
    WATER_OF_HEALING((byte) 7, "Water of Healing", "The target is healed up to 4 lost BP"),
    HEAL_BODY((byte) 8, "Heal Body", "The target is healed up to 4 lost BP"),
    ROCK_FEET((byte) 9, "Rock Feet", "Halves the target's next movement dice roll"),
    ROCK_SKIN((byte) 10, "Rock Skin", "The target may throw an extra combat die when defending. The spell is broken when the target suffers damage");

    private byte id;
    private String name;
    private String rule;

    SpellNameEnum(byte id, String name, String rule) {
        this.id = id;
        this.name = name;
        this.rule = rule;
    }

    public static String getNameById(int id) {
        for (SpellNameEnum spellNameEnum : SpellNameEnum.values()) {
            if (spellNameEnum.equals(id)) {
                return spellNameEnum.getName();
            }
        }
        return SWIFT_WIND.getName();
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
