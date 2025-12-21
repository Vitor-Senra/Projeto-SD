package Aux;

import Conn.Type;
import java.io.*;

public class ReplyIntMessage extends Message {
    private int value;

    public ReplyIntMessage(int id, int skeletonId, Type type, int value) {
        super(id, skeletonId, type);
        this.value = value;
    }

    public ReplyIntMessage(int id, int skeletonId, int value) {
        super(id, skeletonId, Type.REPLY_INT);
        this.value = value;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeInt(value);
    }

    public static ReplyIntMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        int val = in.readInt();
        return new ReplyIntMessage(id, skeletonId, Type.REPLY_INT, val);
    }

    public int getValue() { return value; }
}