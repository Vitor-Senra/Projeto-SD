package Aux;

import Conn.Type;
import java.io.*;

public class ReplyDoubleMessage extends Message {
    private double value;

    public ReplyDoubleMessage(int id, int skeletonId, Type type, double value) {
        super(id, skeletonId, type);
        this.value = value;
    }

    public ReplyDoubleMessage(int id, int skeletonId, double value) {
        super(id, skeletonId, Type.REPLY_DOUBLE);
        this.value = value;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeDouble(value);
    }

    public static ReplyDoubleMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        double val = in.readDouble();
        return new ReplyDoubleMessage(id, skeletonId, Type.REPLY_DOUBLE, val);
    }

    public double getValue() { return value; }
}