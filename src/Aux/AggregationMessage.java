package Aux;

import Conn.Type;
import java.io.*;

public class AggregationMessage extends Message {
    private String produto;
    private int dias;

    public AggregationMessage(int id, int skeletonId, Type type, String produto, int dias) {
        super(id, skeletonId, type);
        this.produto = produto;
        this.dias = dias;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeUTF(produto);
        out.writeInt(dias);
    }

    public static AggregationMessage deserialize(DataInputStream in, int id, int skeletonId, Type type) throws IOException {
        return new AggregationMessage(id, skeletonId, type, in.readUTF(), in.readInt());
    }

    public String getProductName() { return produto; }
    public int getDay() { return dias; }
}