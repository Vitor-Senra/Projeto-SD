package Aux;

import Conn.Type;
import java.io.*;

// Tipo 10: Vendas Simultâneas
public class SimultaneousSaleMessage extends Message {
    private String prod1;
    private String prod2;

    public SimultaneousSaleMessage(int id, int skeletonId, Type type, String prod1, String prod2) {
        super(id, skeletonId, type);
        this.prod1 = prod1;
        this.prod2 = prod2;
    }

    public SimultaneousSaleMessage(int id, int skeletonId, String prod1, String prod2) {
        super(id, skeletonId, Type.NOTIFY_SIMULTANEOUS_SALES);
        this.prod1 = prod1;
        this.prod2 = prod2;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeUTF(prod1);
        out.writeUTF(prod2);
    }

    public static SimultaneousSaleMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        return new SimultaneousSaleMessage(id, skeletonId, in.readUTF(), in.readUTF());
    }

    public String getProduct1() { return prod1; }
    public String getProduct2() { return prod2; }

}