package enums;

public enum CharacterEnum {
    ZARGON(0, "Zargon"),
    BARBARIAN(1, "Barbarian"),
    WIZARD(2, "Wizard"),
    ELF(3, "Elf"),
    DWARF(4, "Dwarf");

    private int id;
    private String name;

    CharacterEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CharacterEnum getEnumById(int id) {
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (characterEnum.getId() == id) {
                return characterEnum;
            }
        }
        return ZARGON;
    }

    public static int getIdByName(String name) {
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (characterEnum.getName().equals(name)) {
                return characterEnum.getId();
            }
        }
        return BARBARIAN.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
