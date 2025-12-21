package Aux;

import Conn.Type;
import java.io.*;

public class ReplyBooleanMessage extends Message {
    private boolean success;

    // Construtor completo
    public ReplyBooleanMessage(int id, int skeletonId, Type type, boolean success) {
        super(id, skeletonId, type);
        this.success = success;
    }

    // Construtor utilitário
    public ReplyBooleanMessage(int id, int skeletonId, boolean success) {
        super(id, skeletonId, Type.REPLY_BOOLEAN);
        this.success = success;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeBoolean(success);
    }

    public static ReplyBooleanMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        boolean val = in.readBoolean();
        return new ReplyBooleanMessage(id, skeletonId, Type.REPLY_BOOLEAN, val);
    }

    public boolean getSuccess() { return success; }
}