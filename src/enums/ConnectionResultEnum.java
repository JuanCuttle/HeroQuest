package enums;

public enum ConnectionResultEnum {
    SUCCESSFULCONNECT(0),
    ALREADYCONNECTED(1),
    FAILEDCONNECT(2),
    SUCCESSFULDISCONNECT(3),
    DISCBEFORECONNECT(4),
    FAILEDDISCONNECT(5),
    SUCCESSFULSTART(6),
    STARTBEFORECONNECT(7),
    UNINTERRUPTEDGAME(13);

    private int id;

    ConnectionResultEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
