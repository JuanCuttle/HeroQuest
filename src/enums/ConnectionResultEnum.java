package enums;

public enum ConnectionResultEnum {
    SUCCESSFUL_CONNECTION(0),
    ALREADY_CONNECTED(1),
    FAILED_TO_CONNECT(2),
    SUCCESSFUL_DISCONNECTION(3),
    DISCONNECTION_ATTEMPT_BEFORE_BEING_CONNECTED(4),
    FAILED_DISCONNECTION(5),
    SUCCESSFUL_START(6),
    START_ATTEMPT_BEFORE_CONNECTING(7),
    GAME_NOT_INTERRUPTED(13);

    private final int id;

    ConnectionResultEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
