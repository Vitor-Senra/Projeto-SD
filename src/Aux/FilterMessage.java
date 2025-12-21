package Aux;

import Conn.Type;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilterMessage extends Message {
    private int dia;
    private List<String> produtos;

    public FilterMessage(int id, int skeletonId, Type type, List<String> produtos, int day) {
        super(id, skeletonId, type);
        this.produtos = produtos;
        this.dia = day;
    }

    public FilterMessage(int id, int skeletonId, List<String> produtos, int day) {
        super(id, skeletonId, Type.FILTER_EVENTS);
        this.produtos = produtos;
        this.dia = day;
    }

    @Override
    public void writeContent(DataOutputStream out) throws IOException {
        out.writeInt(dia);
        out.writeInt(produtos.size());
        for (String s : produtos) {
            out.writeUTF(s);
        }
    }

    public static FilterMessage deserialize(DataInputStream in, int id, int skeletonId) throws IOException {
        int dia = in.readInt();
        int size = in.readInt();
        List<String> lista = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            lista.add(in.readUTF());
        }
        return new FilterMessage(id, skeletonId, lista, dia);
    }

    public int getDay() { return dia; }
    public List<String> getProducts() { return produtos; }
}