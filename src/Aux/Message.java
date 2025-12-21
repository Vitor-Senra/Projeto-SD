package Aux;

import Conn.Type;
import java.io.*;

public abstract class Message {
    protected int skeletonId;
    protected int id;
    protected Type type;

    public Message(int id, int skeletonId, Type type) {
        this.id = id;
        this.skeletonId = skeletonId;
        this.type = type;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(skeletonId);
        out.writeInt(id);
        out.writeInt(type.getValue());
        writeContent(out);
    }

    public static Message deserialize(DataInputStream in) throws IOException {
        int skeletonId = in.readInt();
        int id = in.readInt();
        int typeVal = in.readInt();
        Type type = Type.fromInt(typeVal);

        if (type == null) throw new IOException("Tipo desconhecido: " + typeVal);

        switch (type) {
            case LOGIN:
            case REGISTER:
                return AuthMessage.deserialize(in, id, skeletonId, type);
            case REGISTER_EVENT:
                return RegisterEventMessage.deserialize(in, id, skeletonId);
            case NEW_DAY:
                return new EmptyMessage(id, skeletonId, Type.NEW_DAY);
            case GET_SALES_QUANTITY:
            case GET_SALES_VOLUME:
            case GET_AVERAGE_SALES_PRICE:
            case GET_MAX_SALES_PRICE:
                return AggregationMessage.deserialize(in, id, skeletonId, type);
            case FILTER_EVENTS:
                return FilterMessage.deserialize(in, id, skeletonId);
            case NOTIFY_SIMULTANEOUS_SALES:
                return SimultaneousSaleMessage.deserialize(in, id, skeletonId);
            case NOTIFY_CONSECUTIVE_SALES:
                return ConsecutiveSaleMessage.deserialize(in, id, skeletonId);
            case REPLY_BOOLEAN:
                return ReplyBooleanMessage.deserialize(in, id, skeletonId);
            case REPLY_INT:
                return ReplyIntMessage.deserialize(in, id, skeletonId);
            case REPLY_DOUBLE:
                return ReplyDoubleMessage.deserialize(in, id, skeletonId);
            case REPLY_LIST:
                return ReplyListMessage.deserialize(in, id, skeletonId);
            default:
                throw new IOException("Mensagem não implementada: " + type);
        }
    }

    public abstract void writeContent(DataOutputStream out) throws IOException;

    public int getId() { return id; }
    public int getSkeletonId() { return skeletonId; }
    public Type getType() { return type; }
}