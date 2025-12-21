package Conn;

public enum Type {
    LOGIN(1),
    REGISTER(2),
    REGISTER_EVENT(3),
    NEW_DAY(4),
    GET_SALES_QUANTITY(5),
    GET_SALES_VOLUME(6),
    GET_AVERAGE_SALES_PRICE(7),
    GET_MAX_SALES_PRICE(8),
    FILTER_EVENTS(9),
    NOTIFY_SIMULTANEOUS_SALES(10),
    NOTIFY_CONSECUTIVE_SALES(11),
    REPLY_BOOLEAN(-1),
    REPLY_INT(-2),
    REPLY_DOUBLE(-3),
    REPLY_LIST(-4);



    private final int value;

    Type(int value) {
        this.value = value;
    }

    public static Type fromInt(int val) {
        for (Type type : Type.values()) {
            if (type.getValue() == val) {
                return type;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
