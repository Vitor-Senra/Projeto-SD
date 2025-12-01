package Conn;

public class Frame {
    private final Type type;
    private final byte[] data;

    public Frame(Type type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return this.type;
    }

    public byte[] getData() {
        return this.data;
    }
}
