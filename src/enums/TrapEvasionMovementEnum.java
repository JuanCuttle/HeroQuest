package enums;

public enum TrapEvasionMovementEnum {
    FORWARD,
    BACKWARD,
    FALLEN_INTO_PIT;

    public static TrapEvasionMovementEnum getEnumById(int id) {
        if (id == 0) {
            return FORWARD;
        }
        if (id == 1){
            return BACKWARD;
        }
        return FALLEN_INTO_PIT;
    }

    public static byte getIdByEnum(TrapEvasionMovementEnum trapEvasionMovementEnum) {
        if (TrapEvasionMovementEnum.FORWARD.equals(trapEvasionMovementEnum)) {
            return 0;
        }
        if (TrapEvasionMovementEnum.BACKWARD.equals(trapEvasionMovementEnum)){
            return 1;
        }
        return 2;
    }
}
