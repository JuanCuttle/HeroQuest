package enums;

public enum FurnitureDirectionEnum {
    HORIZONTAL((byte) 0),
    VERTICAL((byte) 1),
    DOWN((byte) 0),
    UP((byte) 1),
    RIGHT((byte) 0),
    LEFT((byte) 1);

    private final byte id;

    FurnitureDirectionEnum(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
