package Aux;

import Aux.Message;
import Conn.Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConsecutiveSaleMessage extends Message {
    private int nVendas;

    public ConsecutiveSaleMessage(int id, int skeletonId, Type type, int nVendas) {
        super(id, skeletonId, type);
        this.nVendas = nVendas;
    }

    public ConsecutiveSaleMessage(int id, int skeletonId, int nVendas) {
        super(id, skeletonId, Type.NOTIFY_CONSECUTIVE_SALES);
        this.nVendas = nVendas;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeInt(nVendas);
    }

    public static Aux.ConsecutiveSaleMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        return new Aux.ConsecutiveSaleMessage(id, skeletonId, in.readInt());
    }

    public int getNVendas() { return nVendas; }
}