package enums;

public enum CharacterEnum {
    ZARGON(0),
    BARBARIAN(1),
    WIZARD(2),
    ELF(3),
    DWARF(4);

    private int id;

    CharacterEnum(int id) {
        this.id = id;
    }

    public static CharacterEnum getEnumById(int id) {
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (characterEnum.getId() == id) {
                return characterEnum;
            }
        }
        return ZARGON;
    }

    public int getId() {
        return id;
    }
}
