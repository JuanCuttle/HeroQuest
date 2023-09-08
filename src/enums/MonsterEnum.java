package enums;

public enum MonsterEnum {
    GOBLIN("Goblin"),
    ORC("Orc"),
    FIMIR("Fimir"),
    SKELETON("Skeleton"),
    ZOMBIE("Zombie"),
    MUMMY("Mummy"),
    CHAOS_WARRIOR("ChaosWarrior"),
    GARGOYLE("Gargoyle"),
    POLAR_WARBEAR("PolarWarbear"),
    CHAOS_SORCERER("ChaosSorcerer");

    private final String name;

    MonsterEnum(String name) {
        this.name = name;
    }

    public static MonsterEnum getEnumByName(String monsterName) {
        for (MonsterEnum monsterEnum : MonsterEnum.values()) {
            if (monsterEnum.name.equalsIgnoreCase(monsterName))
                return monsterEnum;
        }
        return GOBLIN;
    }
}
